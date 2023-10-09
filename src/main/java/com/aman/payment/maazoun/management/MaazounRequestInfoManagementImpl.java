package com.aman.payment.maazoun.management;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.aman.payment.maazoun.service.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.auth.model.MaazounMaazouniaChurch;
import com.aman.payment.auth.model.Pos;
import com.aman.payment.auth.model.Sector;
import com.aman.payment.auth.model.SubService;
import com.aman.payment.auth.model.SubServiceQuota;
import com.aman.payment.auth.model.User;
import com.aman.payment.auth.service.SubServiceService;
import com.aman.payment.auth.service.impl.UserService;
import com.aman.payment.core.management.TransactionManagement;
import com.aman.payment.core.model.PullAccount;
import com.aman.payment.core.model.TransactionECR;
import com.aman.payment.core.model.audit.TransactionECRAudit;
import com.aman.payment.core.model.dto.TransactionMidsDTO;
import com.aman.payment.core.model.dto.TransactionResponseDTO;
import com.aman.payment.core.model.payload.PosMidRequest;
import com.aman.payment.core.model.payload.TransactionPaymentRequest;
import com.aman.payment.core.model.payload.TransactionRefundRequest;
import com.aman.payment.core.service.PullAccountService;
import com.aman.payment.core.service.TransactionECRService;
import com.aman.payment.core.util.UtilCore;
import com.aman.payment.maazoun.event.GenerateTempBookRequestInfoEvent;
import com.aman.payment.maazoun.mapper.MaazounRequestInfoMapper;
import com.aman.payment.maazoun.model.MaazounBookRequestInfo;
import com.aman.payment.maazoun.model.MaazounBookWarehouse;
import com.aman.payment.maazoun.model.MaazounProfile;
import com.aman.payment.maazoun.model.MaazounRefundAtt;
import com.aman.payment.maazoun.model.dto.MaazounBookAuditDTO;
import com.aman.payment.maazoun.model.dto.MaazounBookAuditSectorDTO;
import com.aman.payment.maazoun.model.dto.MaazounBookAvailableDTO;
import com.aman.payment.maazoun.model.dto.MaazounRequestInfoPagingDTO;
import com.aman.payment.maazoun.model.dto.MaazounRequestInfoReportDTO;
import com.aman.payment.maazoun.model.dto.PagingDTO;
import com.aman.payment.maazoun.model.payload.AddMaazounRequestInfoRequest;
import com.aman.payment.maazoun.model.payload.AdvancedSearchBookRequestInfoTransactionRequest;
import com.aman.payment.maazoun.model.payload.BookListRequest;
import com.aman.payment.maazoun.model.payload.GetBookRequestInfoById;
import com.aman.payment.maazoun.model.payload.GetBookRequestInfoByMaazounId;
import com.aman.payment.maazoun.model.payload.MaazounAuditRequest;
import com.aman.payment.maazoun.model.payload.MaazounBookRequestInfoRefundRequest;
import com.aman.payment.maazoun.model.payload.QuickSearchBookRequestInfoTransactionRequest;
import com.aman.payment.maazoun.model.payload.URLRequest;
import com.aman.payment.util.ConfigurationConstant;
import com.aman.payment.util.StatusConstant;
import com.aman.payment.util.Util;

@Component
public class MaazounRequestInfoManagementImpl extends ValidationAndPopulateManagement
        implements MaazounRequestInfoManagement {

    private final MaazounRequestInfoService maazounRequestInfoService;
    private final CryptoMngrMaazounService cryptoMngrMaazounService;
    private final MaazounRequestInfoMapper maazounRequestInfoMapper;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final MaazounBookWarehouseService maazounBookWarehouseService;
    private final MaazounRefundAttService maazounRefundAttService;
    private final TransactionManagement transactionManagement;
    private final TransactionECRService transactionECRService;
    private final PullAccountService pullAccountService;
    private final SubServiceService subServiceService;
    private final MaazounBookStockLabelService maazounBookStockLabelService;
    private final UserService userService;

    @Value("${attachment.maazoun.request.orders}")
    private String bookRequestInfoPathAtt;

    @Value("${reports.maazoun.request.orders}")
    private String bookRequestInfoPathReport;

    @Value("${aman.logo}")
    private String amanLogoUrl;

    @Value("${prosecutor.logo}")
    private String prosecutorLogoUrl;

    @Autowired
    public MaazounRequestInfoManagementImpl(MaazounRequestInfoService maazounRequestInfoService,
                                            CryptoMngrMaazounService cryptoMngrMaazounService, MaazounRequestInfoMapper maazounRequestInfoMapper,
                                            ApplicationEventPublisher applicationEventPublisher, TransactionManagement transactionManagement,
                                            MaazounBookWarehouseService maazounBookWarehouseService, MaazounRefundAttService maazounRefundAttService,
                                            TransactionECRService transactionECRService,
                                            PullAccountService pullAccountService, SubServiceService subServiceService,
                                            MaazounBookStockLabelService maazounBookStockLabelService,
                                            UserService userService) {
        super();
        this.maazounRequestInfoService = maazounRequestInfoService;
        this.cryptoMngrMaazounService = cryptoMngrMaazounService;
        this.maazounRequestInfoMapper = maazounRequestInfoMapper;
        this.applicationEventPublisher = applicationEventPublisher;
        this.transactionManagement = transactionManagement;
        this.maazounBookWarehouseService = maazounBookWarehouseService;
        this.maazounRefundAttService = maazounRefundAttService;
        this.transactionECRService = transactionECRService;
        this.pullAccountService = pullAccountService;
        this.subServiceService = subServiceService;
        this.userService = userService;
        this.maazounBookStockLabelService = maazounBookStockLabelService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addBookRequestInfo(CustomUserDetails customUserDetails,
                                     AddMaazounRequestInfoRequest addMaazounRequestInfoRequest, TransactionECRAudit eTransactionECRAudit) {

        Date createdAt = Date.from(Instant.now());

        Pos pos = getAssignedPOS(customUserDetails.getPosSet(), addMaazounRequestInfoRequest.getSectorId());
        Sector sector = getSectorFromPOSOrThrow(pos, addMaazounRequestInfoRequest.getSectorId());

        validateSelectedBooks(addMaazounRequestInfoRequest.getBookList(), sector);

        // validate this contract number with maazoun and the maazounia
        MaazounProfile maazounProfileFk = maazounMaazouniaValidation(addMaazounRequestInfoRequest.getMaazounId(),
                addMaazounRequestInfoRequest.getSectorId());

        /*
         * Validate Maazoun Quota
         * in case the maazoun profile has exception = false
         */
        if (!maazounProfileFk.getHasExeption()) {
            validateMaazounQuota(addMaazounRequestInfoRequest.getBookList(), maazounProfileFk);
        }

        JSONObject jsonBody = new JSONObject(addMaazounRequestInfoRequest.getPaymentEcrRequest());
        String refRequestNumber = jsonBody.getString("ecr_ref");

        String basePath = bookRequestInfoPathReport + "/" + UtilCore.saveFolderNamingFormat(String.valueOf(createdAt));
        String receiptUrl = "/" + refRequestNumber + ConfigurationConstant.MAAZOUN_BOOK_REQUEST_REPORT_NAME + ".pdf";

        TransactionECR transactionECRFk = saveTransactionECR(addMaazounRequestInfoRequest);

        String authCode = getValidationAuthCode(eTransactionECRAudit, addMaazounRequestInfoRequest.getPaymentCode());
        int paymentProcessFlag = getPaymentProcess(eTransactionECRAudit);

        List<MaazounBookRequestInfo> eMaazounBookRequestInfo = new ArrayList<MaazounBookRequestInfo>();

        for (BookListRequest book : addMaazounRequestInfoRequest.getBookList()) {

            // Get book from stock label by serial number
            long bookTierId = getBookTierId(book.getSerialNumber());

            Set<TransactionMidsDTO> transactionMids = new HashSet<TransactionMidsDTO>();
            SubService subService = subServiceService.findById(book.getTypeId()).get();
            // TODO ANDREW Check here transaction mids
            transactionMids.addAll(calculateMids(transactionMids, subService, book.getContractCount(), bookTierId));


            TransactionPaymentRequest transactionPaymentRequest = populateTransactionPaymentRequest(
                    String.valueOf(book.getTypeId()), authCode,
                    addMaazounRequestInfoRequest.getVisaNumber(), pos, customUserDetails.getUsername(), book.getFees(),
                    StatusConstant.BOOK_SERVICE_ID, transactionECRFk, 0, book.getContractCount(), transactionMids, sector);

            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            TransactionResponseDTO transactionResponseDTO = transactionManagement
                    .createTransaction(transactionPaymentRequest);
            String transactionCode = transactionResponseDTO.getTransactionCode();

            MaazounBookRequestInfo maazounRequestInfoFk = populateMaazounBookRequestInfo(createdAt,
                    customUserDetails.getUsername(), maazounProfileFk, pos, transactionCode, refRequestNumber,
                    book.getSerialNumber(), book.getFees(), basePath.concat(receiptUrl),
                    addMaazounRequestInfoRequest.getPaymentTypeId(), authCode,
                    addMaazounRequestInfoRequest.getVisaNumber(), StatusConstant.STATUS_NEW,
                    book.getTypeId(), sector, book.getType(), Long.valueOf(book.getMaazouniaChurchId()),
                    book.getMaazouniaChurchNameType(), paymentProcessFlag, null);

            maazounRequestInfoFk = maazounRequestInfoService.save(maazounRequestInfoFk);

            maazounBookWarehouseService.updateStatusBySerialNumber(StatusConstant.STATUS_SOLD, maazounRequestInfoFk,
                    maazounRequestInfoFk.getBookSerialNumber());

            eMaazounBookRequestInfo.add(maazounRequestInfoFk);

        }

        /*
         * rollback to default maazounProfile quota exception to be false
         */
        maazounProfileFk.setHasExeption(false);
        maazounProfileService.save(maazounProfileFk);

        List<MaazounRequestInfoReportDTO> eMaazounRequestInfoReportDTO = populateMaazounRequestInfoReport(
                eMaazounBookRequestInfo, addMaazounRequestInfoRequest.getAmount(),
                addMaazounRequestInfoRequest.getBookList(), createdAt,
                Util.getCreatedByFullName(customUserDetails),
                pos, amanLogoUrl, prosecutorLogoUrl, sector);

        GenerateTempBookRequestInfoEvent generateTempRequestInfoEvent = new GenerateTempBookRequestInfoEvent(
                eMaazounRequestInfoReportDTO, String.valueOf(createdAt), receiptUrl, 0);
        applicationEventPublisher.publishEvent(generateTempRequestInfoEvent);

        return cryptoMngrMaazounService.encrypt(basePath.concat(receiptUrl));
    }

    private long getBookTierId(String serialNumber) {
        return maazounBookStockLabelService
                .findByLabelCode(serialNumber)
                .orElseThrow(() -> new RuntimeException("Book not found"))
                .getBookTierId();
    }

    private void reGenerateRequestOrderReport(String reqReferenceNUmber) {

        List<MaazounBookRequestInfo> requests = maazounRequestInfoService.findByRefRequestNumber(reqReferenceNUmber);
        MaazounBookRequestInfo globalMaazounBookRequestInfo = requests.stream().findFirst().get();
        User user = userService.findByUsername(globalMaazounBookRequestInfo.getCreatedBy()).get();
        List<BookListRequest> bookListRequest = new ArrayList<BookListRequest>();

        for (MaazounBookRequestInfo eMaazounBookRequestInfo : requests) {

            MaazounBookWarehouse eMaazounBookWarehouse = maazounBookWarehouseService.
                    findBySerialNumberOrBookFinancialNumber(eMaazounBookRequestInfo.getBookSerialNumber(),
                            eMaazounBookRequestInfo.getBookSerialNumber()).get();

            BookListRequest bookListRequestObj = new BookListRequest();
//			MaazounBookWarehouse eMaazounBookWarehouse = eMaazounBookRequestInfo.getMaazounBookWarehouseSet().stream().findFirst().get();
            bookListRequestObj.setBookFinancialNumber(eMaazounBookWarehouse.getBookFinancialNumber());
            bookListRequestObj.setContractCount(eMaazounBookWarehouse.getContractCount());
            bookListRequestObj.setFees(eMaazounBookRequestInfo.getAmount());
            bookListRequestObj.setMaazouniaChurchId(String.valueOf(eMaazounBookRequestInfo.getMaazouniaId()));
            bookListRequestObj.setMaazouniaChurchNameType(eMaazounBookRequestInfo.getMaazouniaName() != null ? eMaazounBookRequestInfo.getMaazouniaName() : "");

            bookListRequestObj.setQuotaBookDarebtDamgha((double) 0);
            bookListRequestObj.setQuotaBookDarebtMabeat((double) 0);
            bookListRequestObj.setQuotaBookFahsFany((double) 0);
            bookListRequestObj.setQuotaBookRasmTanmya((double) 0);
            bookListRequestObj.setQuotaBookTahweelRakmy((double) 0);
            bookListRequestObj.setQuotaBookTahweelRakmyAman((double) 0);
            bookListRequestObj.setQuotaBookTahweelRakmyNyaba((double) 0);
            bookListRequestObj.setQuotaBookTaklefa((double) 0);

            String bookSerialNumber = eMaazounBookWarehouse.getSerialNumber();
            bookListRequestObj.setSerialNumber(bookSerialNumber == null ? "" : bookSerialNumber);
            bookListRequestObj.setType(eMaazounBookRequestInfo.getBookTypeName());
            bookListRequestObj.setTypeId(eMaazounBookRequestInfo.getBookTypeId());

            bookListRequest.add(bookListRequestObj);
        }

        List<MaazounRequestInfoReportDTO> eMaazounRequestInfoReportDTO = populateMaazounRequestInfoReport(
                requests, (double) 0, bookListRequest, globalMaazounBookRequestInfo.getCreatedAt(),
                Util.getCreatedByFullName(user), globalMaazounBookRequestInfo.getPosFk(),
                amanLogoUrl, prosecutorLogoUrl, globalMaazounBookRequestInfo.getSectorFk());

        GenerateTempBookRequestInfoEvent generateTempRequestInfoEvent = new GenerateTempBookRequestInfoEvent(
                eMaazounRequestInfoReportDTO, String.valueOf(globalMaazounBookRequestInfo.getCreatedAt()),
                globalMaazounBookRequestInfo.getReceiptUrl(), 1);
        applicationEventPublisher.publishEvent(generateTempRequestInfoEvent);

    }

    @Override
    public String createRefundBookRequestInfo(CustomUserDetails customUserDetails,
                                              MaazounBookRequestInfoRefundRequest maazounBookRequestInfoRefundRequest,
                                              TransactionECRAudit eTransactionECRAudit) {

        Date createdAt = Date.from(Instant.now());
        JSONObject jsonObject = new JSONObject();
        /*
         * validation settlement code
         */
//		Optional<PullAccount> pullAccountOptional = pullAccountService.findByStatusFkAndPosIdAndServiceId(
//				StatusConstant.STATUS_NEW, maazounBookRequestInfoRefundRequest.getPosId(), StatusConstant.BOOK_SERVICE_ID);

        Optional<PullAccount> pullAccountOptional = pullAccountService.findBySettlementCode(
                maazounBookRequestInfoRefundRequest.getSettlementCode());

        if (!pullAccountOptional.isPresent()) {
            jsonObject.put("response", "نأسف نقطة البيع التى يتم عليها استرجاع العملية ليست لديها محفظة مفتوحة لأتمام العملية");
            return cryptoMngrMaazounService.encrypt(jsonObject.toString());
        }

        User pullAccountCreatedBy = userService.findByUsername(pullAccountOptional.get().getCreatedBy()).get();
        Pos pos = pullAccountCreatedBy.getPosSet().stream()
                .filter(x -> x.getId().equals(maazounBookRequestInfoRefundRequest.getPosId())).findAny()
                .orElse(null);

        /*
         * validation visa number should to be the same of sale request
         */
        MaazounBookRequestInfo maazounBookRequestInfo = maazounRequestInfoService
                .findById(Long.valueOf(maazounBookRequestInfoRefundRequest.getMaazounRequestInfoIds().get(0))).get();

        if (!maazounBookRequestInfoRefundRequest.getVisaNumber().equals(maazounBookRequestInfo.getVisaNumber())) {
            jsonObject.put("response", "نأسف رقم الفيزا المستخدم فى عملية الأسترجاع غير مطابق مع رقم الفيزا التى استخدم من قبل فى عملية الشراء");
            return cryptoMngrMaazounService.encrypt(jsonObject.toString());
        }

        /*
         * validation book not have any contract received
         * should all contracts in this book to be status SOLD
         */
        boolean isValidateStatus = false;
        Set<MaazounBookRequestInfo> refundMaazounBookRequestInfoSet = new HashSet<MaazounBookRequestInfo>();
        Set<MaazounBookWarehouse> refundMaazounBookWarehouseSet = new HashSet<MaazounBookWarehouse>();
        for (String requestInfoId : maazounBookRequestInfoRefundRequest.getMaazounRequestInfoIds()) {

            MaazounBookRequestInfo eMaazounBookRequestInfo =
                    maazounRequestInfoService.findById(Long.valueOf(requestInfoId)).get();

            for (MaazounBookWarehouse eMaazounBookWarehouse : maazounBookWarehouseService.
                    findByMaazounBookRequestInfoFk(eMaazounBookRequestInfo)) {
                if (!eMaazounBookWarehouse.getStatusFk().equals(StatusConstant.STATUS_SOLD)) {
                    isValidateStatus = true;
                    break;
                } else {
                    refundMaazounBookWarehouseSet.add(eMaazounBookWarehouse);
                }
            }

            if (isValidateStatus) {
                refundMaazounBookWarehouseSet.clear();
                refundMaazounBookRequestInfoSet.clear();
                break;
            } else {
                refundMaazounBookRequestInfoSet.add(eMaazounBookRequestInfo);
            }

        }

        if (isValidateStatus) {
            jsonObject.put("response", "لا يمكن استرجاع العملية نظرا لوجود عقود حالاتها محصلة من قبل");
            return cryptoMngrMaazounService.encrypt(jsonObject.toString());
        }


        for (MaazounBookRequestInfo eMaazounBookRequestInfo : refundMaazounBookRequestInfoSet) {

            TransactionRefundRequest transactionRefundRequest = populateTransactionRefundRequest(
                    maazounBookRequestInfoRefundRequest.getAttRefund(), pullAccountOptional.get().getSettlementCode(),
                    eMaazounBookRequestInfo.getTransactionCode(), pullAccountCreatedBy.getUsername(),
                    maazounBookRequestInfoRefundRequest.getComment(),
                    maazounBookRequestInfoRefundRequest.getPaymentCode(),
                    maazounBookRequestInfoRefundRequest.getPaymentEcrResponse(),
                    maazounBookRequestInfoRefundRequest.getPaymentEcrRequest());

            TransactionResponseDTO eTransactionResponseDTO = transactionManagement
                    .refundTransaction(transactionRefundRequest);
            if (eTransactionResponseDTO != null) {

                eMaazounBookRequestInfo.setStatusFk(StatusConstant.STATUS_CANCELED);
                eMaazounBookRequestInfo.setUpdatedAt(createdAt);
                eMaazounBookRequestInfo.setUpdatedBy(customUserDetails.getUsername());
                maazounRequestInfoService.save(eMaazounBookRequestInfo);

                String attachedFile = null;
                try {
                    attachedFile = saveBrowseAttFile(maazounBookRequestInfoRefundRequest.getAttRefund(),
                            bookRequestInfoPathAtt, "REFUND_" + eMaazounBookRequestInfo.getRefRequestNumber());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                MaazounBookRequestInfo refundMaazounRequestInfoFk = populateMaazounBookRequestInfo(createdAt,
                        customUserDetails.getUsername(), eMaazounBookRequestInfo.getMaazounProfileFk(), pos,
                        eTransactionResponseDTO.getTransactionCode(), eMaazounBookRequestInfo.getRefRequestNumber(),
                        eMaazounBookRequestInfo.getBookSerialNumber(), eMaazounBookRequestInfo.getAmount(),
                        eMaazounBookRequestInfo.getReceiptUrl(), StatusConstant.PAYMENT_VISA,
                        maazounBookRequestInfoRefundRequest.getPaymentCode(),
                        maazounBookRequestInfoRefundRequest.getVisaNumber(), StatusConstant.STATUS_REFUND,
                        eMaazounBookRequestInfo.getBookTypeId(), eMaazounBookRequestInfo.getSectorFk(),
                        eMaazounBookRequestInfo.getBookTypeName(), eMaazounBookRequestInfo.getMaazouniaId(),
                        eMaazounBookRequestInfo.getMaazouniaName(), 2, attachedFile);


                MaazounBookRequestInfo eRefundMaazounRequestInfo = maazounRequestInfoService
                        .save(refundMaazounRequestInfoFk);

                MaazounRefundAtt eMaazounRefundAtt = new MaazounRefundAtt();
                eMaazounRefundAtt.setMaazounBookRequestInfoId(eRefundMaazounRequestInfo.getId());
                eMaazounRefundAtt.setRefundTransactionCode(eRefundMaazounRequestInfo.getTransactionCode());
                eMaazounRefundAtt.setRefundUrl(eTransactionResponseDTO.getRefundUrl());

                maazounRefundAttService.save(eMaazounRefundAtt);

                for (MaazounBookWarehouse eMaazounBookWarehouse : refundMaazounBookWarehouseSet) {
                    if (eMaazounBookWarehouse.getMaazounBookRequestInfoFk().getId().equals(eMaazounBookRequestInfo.getId())) {
                        eMaazounBookWarehouse.setStatusFk(StatusConstant.STATUS_NEW);
                        maazounBookWarehouseService.save(eMaazounBookWarehouse);
                    }
                }

            }

        }

        JSONObject jsonResult = new JSONObject();
        jsonResult.put("response", "تم عملية الأسترجاع بنجاح");

        return cryptoMngrMaazounService.encrypt(jsonResult.toString());

    }

    @Override
    public MaazounRequestInfoPagingDTO quickSeracBookRequestInfoTransactions(
            QuickSearchBookRequestInfoTransactionRequest quickSearchBookRequestInfoTransactionRequest,
            CustomUserDetails customUserDetails) {

        Pageable pageable = PageRequest.of(Integer.valueOf(quickSearchBookRequestInfoTransactionRequest.getPageNo()),
                Integer.valueOf(quickSearchBookRequestInfoTransactionRequest.getPageSize()));
        Page<MaazounBookRequestInfo> pageResult = null;

        if (quickSearchBookRequestInfoTransactionRequest.getBookSerialNumber() != null
                && !quickSearchBookRequestInfoTransactionRequest.getBookSerialNumber().isEmpty()) {

            if (isRoleAgentOrAreaOrSupervisor(customUserDetails.getRoleFk().getName())) {
                pageResult = maazounRequestInfoService.findByBookSerialNumberAndPosFkInAndStatusFkNot(
                        quickSearchBookRequestInfoTransactionRequest.getBookSerialNumber(), customUserDetails.getPosSet(),
                        StatusConstant.STATUS_CANCELED, pageable);
            } else {
                pageResult = maazounRequestInfoService.findByBookSerialNumberAndStatusFkNot(
                        quickSearchBookRequestInfoTransactionRequest.getBookSerialNumber(),
                        StatusConstant.STATUS_CANCELED, pageable);
            }


        } else if (quickSearchBookRequestInfoTransactionRequest.getTransactionCode() != null
                && !quickSearchBookRequestInfoTransactionRequest.getTransactionCode().isEmpty()) {

            if (isRoleAgentOrAreaOrSupervisor(customUserDetails.getRoleFk().getName())) {
                pageResult = maazounRequestInfoService.findByTransactionCodeAndPosFkInAndStatusFkNot(
                        quickSearchBookRequestInfoTransactionRequest.getTransactionCode(), customUserDetails.getPosSet(),
                        StatusConstant.STATUS_CANCELED, pageable);
            } else {
                pageResult = maazounRequestInfoService.findByTransactionCodeAndStatusFkNot(
                        quickSearchBookRequestInfoTransactionRequest.getTransactionCode(),
                        StatusConstant.STATUS_CANCELED, pageable);
            }

        } else if (quickSearchBookRequestInfoTransactionRequest.getRefRequestNumber() != null
                && !quickSearchBookRequestInfoTransactionRequest.getRefRequestNumber().isEmpty()) {

            if (isRoleAgentOrAreaOrSupervisor(customUserDetails.getRoleFk().getName())) {
                pageResult = maazounRequestInfoService.findByRefRequestNumberAndPosFkInAndStatusFkNot(
                        quickSearchBookRequestInfoTransactionRequest.getRefRequestNumber(), customUserDetails.getPosSet(),
                        StatusConstant.STATUS_CANCELED, pageable);
            } else {
                pageResult = maazounRequestInfoService.findByRefRequestNumberAndStatusFkNot(
                        quickSearchBookRequestInfoTransactionRequest.getRefRequestNumber(),
                        StatusConstant.STATUS_CANCELED, pageable);
            }

        }

        MaazounRequestInfoPagingDTO maazounRequestInfoPagingDTO = new MaazounRequestInfoPagingDTO();
        maazounRequestInfoPagingDTO.setCount(pageResult.getTotalElements());
        maazounRequestInfoPagingDTO.setTotalPages(pageResult.getTotalPages());

        List<String> eTransaction = new ArrayList<String>();
        maazounRequestInfoMapper.maazounBookSupplyOrdersToMaazounBookSupplyOrderDTOs(pageResult.getContent()).stream()
                .forEach(s -> {
                    eTransaction.add(cryptoMngrMaazounService.encrypt(s.toString()));
                });

        maazounRequestInfoPagingDTO.setBookRequests(eTransaction);

        return maazounRequestInfoPagingDTO;

    }

    @Override
    public MaazounRequestInfoPagingDTO advancedSerachBookRequestInfoTransactions(
            AdvancedSearchBookRequestInfoTransactionRequest advancedSearchBookRequestInfoTransactionRequest,
            CustomUserDetails customUserDetails) {

        Page<MaazounBookRequestInfo> pageResult = null;
        MaazounRequestInfoPagingDTO obj = new MaazounRequestInfoPagingDTO();

        try {
            pageResult = maazounRequestInfoService.advancedSearch(advancedSearchBookRequestInfoTransactionRequest, customUserDetails);

            obj.setCount(pageResult.getTotalElements());
            obj.setTotalPages(pageResult.getTotalPages());

            List<String> eTransaction = new ArrayList<String>();
            maazounRequestInfoMapper.maazounBookSupplyOrdersToMaazounBookSupplyOrderDTOs(pageResult.getContent())
                    .stream().forEach(s -> {
                        eTransaction.add(cryptoMngrMaazounService.encrypt(s.toString()));
                    });

            obj.setBookRequests(eTransaction);
            return obj;

        } catch (Exception e) {
            obj.setCount((long) 0);
            obj.setTotalPages(0);
            obj.setBookRequests(new ArrayList<String>());
            return obj;
        }

    }

    @Override
    public InputStream downloadFile(URLRequest uRLRequest) {

        File pdfFile = new File(uRLRequest.getUrl());
        InputStream is = null;
        try {
            is = new FileInputStream(pdfFile);
        } catch (FileNotFoundException e) {

            reGenerateRequestOrderReport(uRLRequest.getRefRequestNumber());
            try {
                is = new FileInputStream(pdfFile);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
        return is;
    }

    @Override
    public MaazounRequestInfoPagingDTO getAuditByCreatedDate(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails) {

        Page<MaazounBookAuditDTO> pageResult = maazounRequestInfoService.getAuditByCreatedDate(
                maazounAuditRequest, customUserDetails);

        MaazounRequestInfoPagingDTO maazounRequestInfoPagingDTO = new MaazounRequestInfoPagingDTO();
        maazounRequestInfoPagingDTO.setCount(pageResult.getTotalElements());
        maazounRequestInfoPagingDTO.setTotalPages(pageResult.getTotalPages());

        List<String> eTransaction = new ArrayList<String>();
        pageResult.getContent().stream().forEach(s -> {
            eTransaction.add(cryptoMngrMaazounService.encrypt(s.toString()));
        });
        maazounRequestInfoPagingDTO.setBookRequests(eTransaction);

        return maazounRequestInfoPagingDTO;

    }

    @Override
    public MaazounRequestInfoPagingDTO getAuditByCreatedDateAndCourt(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails) {

        Page<MaazounBookAuditDTO> pageResult = maazounRequestInfoService
                .getAuditByCreatedDateAndCourt(maazounAuditRequest, customUserDetails);

        MaazounRequestInfoPagingDTO maazounRequestInfoPagingDTO = new MaazounRequestInfoPagingDTO();
        maazounRequestInfoPagingDTO.setCount(pageResult.getTotalElements());
        maazounRequestInfoPagingDTO.setTotalPages(pageResult.getTotalPages());

        List<String> eTransaction = new ArrayList<String>();
        pageResult.getContent().stream().forEach(s -> {
            eTransaction.add(cryptoMngrMaazounService.encrypt(s.toString()));
        });
        maazounRequestInfoPagingDTO.setBookRequests(eTransaction);

        return maazounRequestInfoPagingDTO;

    }

    @Override
    public MaazounRequestInfoPagingDTO getAuditByCreatedDateAndAgentAndCourt(
            MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails) {

        Page<MaazounBookAuditDTO> pageResult = maazounRequestInfoService
                .getAuditByCreatedDateAndAgentAndCourt(maazounAuditRequest, customUserDetails);

        MaazounRequestInfoPagingDTO maazounRequestInfoPagingDTO = new MaazounRequestInfoPagingDTO();
        maazounRequestInfoPagingDTO.setCount(pageResult.getTotalElements());
        maazounRequestInfoPagingDTO.setTotalPages(pageResult.getTotalPages());

        List<String> eTransaction = new ArrayList<String>();
        pageResult.getContent().stream().forEach(s -> {
            eTransaction.add(cryptoMngrMaazounService.encrypt(s.toString()));
        });
        maazounRequestInfoPagingDTO.setBookRequests(eTransaction);

        return maazounRequestInfoPagingDTO;

    }

    @Override
    public PagingDTO getBookCustomAuditBySectors(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails) {

        Page<MaazounBookAuditSectorDTO> pageResult = maazounRequestInfoService
                .getBookCustomAuditBySectors(maazounAuditRequest, customUserDetails);

        PagingDTO pagingDTO = new PagingDTO();
        pagingDTO.setCount(pageResult.getTotalElements());
        pagingDTO.setTotalPages(pageResult.getTotalPages());

        List<String> eTransaction = new ArrayList<String>();
        pageResult.getContent().stream().forEach(s -> {
            eTransaction.add(cryptoMngrMaazounService.encrypt(s.toString()));
        });
        pagingDTO.setBookRequests(eTransaction);

        return pagingDTO;
    }

    @Override
    public PagingDTO getBookCustomAuditByCourts(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails) {

        Page<MaazounBookAuditSectorDTO> pageResult = maazounRequestInfoService
                .getBookCustomAuditByCourts(maazounAuditRequest, customUserDetails);

        PagingDTO pagingDTO = new PagingDTO();
        pagingDTO.setCount(pageResult.getTotalElements());
        pagingDTO.setTotalPages(pageResult.getTotalPages());

        List<String> eTransaction = new ArrayList<String>();
        pageResult.getContent().stream().forEach(s -> {
            eTransaction.add(cryptoMngrMaazounService.encrypt(s.toString()));
        });
        pagingDTO.setBookRequests(eTransaction);

        return pagingDTO;
    }

    @Override
    public String calculateSalePosMidRequestAmountFormat(PosMidRequest posMidRequest, CustomUserDetails customUserDetails) {

        Date createdAt = Date.from(Instant.now());
        Pos pos = getAssignedPOS(customUserDetails.getPosSet(), posMidRequest.getSectorId());
        Sector sector = pos.getSectors().stream()
                .filter(x -> x.getId().equals(posMidRequest.getSectorId())).findAny()
                .orElse(null);

        String refRequestNumber = sector.getLocationFk().getId() + "-"
                + new SimpleDateFormat("yyDkmsS").format(createdAt);

        Set<TransactionMidsDTO> transactionMids = new HashSet<TransactionMidsDTO>();
        for (String bookFinancialNumber : posMidRequest.getMaazounBookFinancialNumner()) {

            MaazounBookWarehouse eMaazounBookWarehouse = maazounBookWarehouseService
                    .findBySerialNumberAndStatusFk(bookFinancialNumber, StatusConstant.STATUS_NEW).get();

            SubService subService = subServiceService.findById(eMaazounBookWarehouse.getBookTypeId()).get();

            long tierId = getBookTierId(eMaazounBookWarehouse.getSerialNumber());
            transactionMids.addAll(calculateMids(transactionMids, subService, eMaazounBookWarehouse.getContractCount(), tierId));

        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("transType", 2);
        jsonObject.put("Amount", String.format("%.2f", posMidRequest.getTotalAmount()));
        jsonObject.put("ecr_ref", refRequestNumber);

        JSONArray accountsJsonArr = new JSONArray();
        JSONArray feesJsonArr = new JSONArray();

        int sequance = 1;

        for (TransactionMidsDTO mid : transactionMids) {

            JSONObject jsonAccountObject = new JSONObject();
            jsonAccountObject.put("X" + sequance, mid.getMidAccount());
            accountsJsonArr.put(jsonAccountObject);

            JSONObject jsonFeesObject = new JSONObject();
            jsonFeesObject.put("F" + sequance, String.format("%.2f", mid.getMidValue()));
            feesJsonArr.put(jsonFeesObject);

            sequance++;
        }

        jsonObject.put("accounts", accountsJsonArr);
        jsonObject.put("fees", feesJsonArr);

        return cryptoMngrMaazounService.encrypt(jsonObject.toString());

    }

    @Override
    public String calculateRefundPosMidRequestAmountFormat(PosMidRequest posMidRequest) {

        Date createdAt = Date.from(Instant.now());
        JSONObject jsonObject = new JSONObject();

        String refRequestNumberTimestamp = new SimpleDateFormat("sS").format(createdAt);

        Set<TransactionMidsDTO> transactionMids = new HashSet<TransactionMidsDTO>();
        for (String bookFinancialNumber : posMidRequest.getMaazounBookFinancialNumner()) {

            try {
                MaazounBookWarehouse eMaazounBookWarehouse = maazounBookWarehouseService
                        .findBySerialNumberAndStatusFk(bookFinancialNumber, StatusConstant.STATUS_SOLD).get();

                SubService subService = subServiceService.findById(eMaazounBookWarehouse.getBookTypeId()).get();
                long bookTierId = getBookTierId(eMaazounBookWarehouse.getSerialNumber());


                transactionMids.addAll(calculateMids(transactionMids, subService, eMaazounBookWarehouse.getContractCount(), bookTierId));

            } catch (Exception e) {
                // TODO: handle exception
                jsonObject.put("response", "Can't Refund Maazoun Book Process, it is not SOLD status...");
                return cryptoMngrMaazounService.encrypt(jsonObject.toString());
            }

        }

        jsonObject.put("transType", 4);
        jsonObject.put("Amount", String.format("%.2f", posMidRequest.getTotalAmount()));
        jsonObject.put("ecr_ref", "r-" + posMidRequest.getRefundRefRequestNumber() + "-" + refRequestNumberTimestamp);

        JSONArray accountsJsonArr = new JSONArray();
        JSONArray feesJsonArr = new JSONArray();

        int sequance = 1;

        for (TransactionMidsDTO mid : transactionMids) {

            JSONObject jsonAccountObject = new JSONObject();
            jsonAccountObject.put("X" + sequance, mid.getMidAccount());
            accountsJsonArr.put(jsonAccountObject);

            JSONObject jsonFeesObject = new JSONObject();
            jsonFeesObject.put("F" + sequance, String.format("%.2f", mid.getMidValue()));
            feesJsonArr.put(jsonFeesObject);

            sequance++;
        }

        jsonObject.put("accounts", accountsJsonArr);
        jsonObject.put("fees", feesJsonArr);

        return cryptoMngrMaazounService.encrypt(jsonObject.toString());

    }

    private Set<TransactionMidsDTO> calculateMids(Set<TransactionMidsDTO> transactionMids, SubService subService,
                                                  long contractCount, long bookTierId) {
        double sumAmanAndNyabaFeesForDaryba = 0;
        for (SubServiceQuota mid : subService.getSubServicesQuota()) {
            if (mid.getSubServicePriceTierFk().getId() != bookTierId) {
                continue;
            }
            TransactionMidsDTO eTransactionMids = transactionMids.stream()
                    .filter(x -> x.getMidAccount().equals(cryptoMngrMaazounService.decrypt(mid.getMidAccount())))
                    .findAny()
                    .orElse(null);

            if (mid.getStatusFk().equals(StatusConstant.STATUS_ACTIVE) && mid.getFeesType().equals("n")) {

                if (eTransactionMids == null) {
                    TransactionMidsDTO eTransactionMidFkQouta = new TransactionMidsDTO();
                    eTransactionMidFkQouta.setMidAccount(cryptoMngrMaazounService.decrypt(mid.getMidAccount()));
                    eTransactionMidFkQouta.setMidAccountEnc(mid.getMidAccount());
                    eTransactionMidFkQouta.setMidBank(mid.getMidBank());
                    eTransactionMidFkQouta.setBeneficiary(mid.getBeneficiary());
                    if (mid.getName().equals(StatusConstant.BOOK_QUOTA_TAHWEEL_RAKMY_AMAN) ||
                            mid.getName().equals(StatusConstant.BOOK_QUOTA_TAHWEEL_RAKMY_NYABA)) {
                        sumAmanAndNyabaFeesForDaryba = sumAmanAndNyabaFeesForDaryba + (mid.getFees() * contractCount);
                        eTransactionMidFkQouta.setMidValue(Double.valueOf(String.format("%.2f", mid.getFees() * contractCount)));
                    } else {
                        eTransactionMidFkQouta.setMidValue(Double.valueOf(String.format("%.2f", mid.getFees())));
                    }
                    transactionMids.add(eTransactionMidFkQouta);
                } else {
                    if (mid.getName().equals(StatusConstant.BOOK_QUOTA_TAHWEEL_RAKMY_AMAN) ||
                            mid.getName().equals(StatusConstant.BOOK_QUOTA_TAHWEEL_RAKMY_NYABA)) {
                        sumAmanAndNyabaFeesForDaryba = sumAmanAndNyabaFeesForDaryba + (mid.getFees() * contractCount);
                        eTransactionMids.setMidValue(Double.valueOf(String.format("%.2f", (eTransactionMids.getMidValue() + (mid.getFees() * contractCount)))));
                    } else {
                        eTransactionMids.setMidValue(Double.valueOf(String.format("%.2f", (eTransactionMids.getMidValue() + mid.getFees()))));
                    }

                }


            }

        }

        SubServiceQuota eSubServiceQuotaPercentage = subService.getSubServicesQuota().stream()
                .filter(x -> x.getStatusFk().equals(StatusConstant.STATUS_ACTIVE)
                        && x.getFeesType().equals("%")
                        && x.getSubServicePriceTierFk().getId() == bookTierId)
                .findAny().orElse(null);

        if (eSubServiceQuotaPercentage != null) {
            TransactionMidsDTO eTransactionMidAman = transactionMids.stream()
                    .filter(x -> x.getBeneficiary().equals("امان")).findAny().orElse(null);

            double darebtMabeatValue = sumAmanAndNyabaFeesForDaryba / 100 * eSubServiceQuotaPercentage.getFees();
            eTransactionMidAman.setMidValue(
                    Double.valueOf(String.format("%.2f", (eTransactionMidAman.getMidValue() + darebtMabeatValue))));
        }
        return transactionMids;
    }

    @Override
    public PagingDTO getBookRequestInfoByMaazounId(
            GetBookRequestInfoByMaazounId getBookRequestInfoByMaazounId) {

        Page<MaazounBookAvailableDTO> pageResult = maazounRequestInfoService.
                getBookRequestInfoByMaazounId(getBookRequestInfoByMaazounId);

        PagingDTO pagingDTO = new PagingDTO();
        pagingDTO.setCount(pageResult.getTotalElements());
        pagingDTO.setTotalPages(pageResult.getTotalPages());

        List<String> eTransaction = new ArrayList<String>();
        pageResult.getContent().stream().forEach(s -> {
            eTransaction.add(cryptoMngrMaazounService.encrypt(s.toString()));
        });
        pagingDTO.setBookRequests(eTransaction);

        return pagingDTO;

    }

    @Override
    public String editBookRequestInfoMaazounId(CustomUserDetails customUserDetails,
                                               GetBookRequestInfoById getBookRequestInfoById) {

        Date updatedAt = Date.from(Instant.now());

        MaazounBookRequestInfo maazounBookRequestInfo = maazounRequestInfoService.findById(
                getBookRequestInfoById.getRequestInfoId()).get();
        String comment = maazounBookRequestInfo.getComment() + "," +
                "assignedBy:" + maazounBookRequestInfo.getUpdatedBy() + "-"
                + "assignedAt:" + maazounBookRequestInfo.getUpdatedBy() + "-"
                + "MaazounaId:" + maazounBookRequestInfo.getMaazouniaId() + "-"
                + "MaazouniaName:" + maazounBookRequestInfo.getMaazouniaName();

        MaazounProfile maazounProfileFk = getMaazounProfile(getBookRequestInfoById.getMaazounId());
        MaazounMaazouniaChurch eMaazounMaazouniaChurch = maazounProfileFk.getMaazounMaazouniaChurch().stream()
                .filter(p -> p.getMaazouniaChurchFk().getId().equals(getBookRequestInfoById.getMaazouniaId())).findAny().orElse(null);

        if (eMaazounMaazouniaChurch != null) {
            maazounBookRequestInfo.setMaazouniaId(eMaazounMaazouniaChurch.getMaazouniaChurchFk().getId());
            maazounBookRequestInfo.setMaazouniaName(
                    eMaazounMaazouniaChurch.getMaazouniaChurchFk().getName() + "-" + eMaazounMaazouniaChurch.getMaazouniaType());
        }
        maazounBookRequestInfo.setMaazounProfileFk(maazounProfileFk);
        maazounBookRequestInfo.setUpdatedAt(updatedAt);
        maazounBookRequestInfo.setUpdatedBy(customUserDetails.getUsername());
        maazounBookRequestInfo.setComment(comment);

        return cryptoMngrMaazounService.encrypt(maazounRequestInfoService.save(maazounBookRequestInfo).toString());
    }

    private void validateSelectedBooks(List<BookListRequest> bookList, Sector sector) {
        for (BookListRequest book : bookList) {
            createRequestBookSerialIsValidation(book.getSerialNumber(), sector);
        }
    }

    private TransactionECR saveTransactionECR(AddMaazounRequestInfoRequest request) {
        if (request.getPaymentEcrResponse() != null && !request.getPaymentEcrResponse().isEmpty()) {
            return transactionECRService.save(new TransactionECR(request.getPaymentEcrResponse(),
                    request.getPaymentEcrRequest()));
        }
        return null;
    }

    // TODO :: ANDREW CHECK QUOTA HERE
    private void validateMaazounQuota(List<BookListRequest> maazounBooks, MaazounProfile maazounProfile) {

        Map<String, List<BookListRequest>> groupedBooks = groupBooksByType(maazounBooks);

        groupedBooks.forEach((id, items) -> {

            long bookTypeId8 = Long.valueOf(id.split("\\-")[0]);
            long bookTypeId15 = Long.valueOf(id.split("\\-")[1]);
            long setupBookQuota = 0;
            long setupContractQuota = 0;
            long allowedCount = items.size();
            String bookName = "";

            if (id.equals("9-15")) {
                setupBookQuota = maazounProfile.getBookZawagMuslimQuota();
                setupContractQuota = maazounProfile.getBookZawagMuslimQuotaContractCount();
                bookName = "زواج مسلمين";
            } else if (id.equals("10-20")) {
                setupBookQuota = maazounProfile.getBookTalakQuota();
                setupContractQuota = maazounProfile.getBookTalakQuotaContractCount();
                bookName = "طلاق";
            } else if (id.equals("11-21")) {
                setupBookQuota = maazounProfile.getBookTasadokQuota();
                setupContractQuota = maazounProfile.getBookTasadokQuotaContractCount();
                bookName = "تصادق";
            } else if (id.equals("12-22")) {
                setupBookQuota = maazounProfile.getBookMoragaaQuota();
                setupContractQuota = maazounProfile.getBookMoragaaQuotaContractCount();
                bookName = "مراجعة";
            } else if (id.equals("13-23")) {
                setupBookQuota = maazounProfile.getBookZawagMellyQuota();
                setupContractQuota = maazounProfile.getBookZawagMellyQuotaContractCount();
                bookName = "زواج مللى";
            }

            long bookCountStillWithMaazoun = maazounBookWarehouseService.
                    findCountDistinctMaazounBookSoldAndCollectedStatusByMaazounId(
                            maazounProfile.getId(), bookTypeId8, bookTypeId15);

            long avaliableOrder = setupBookQuota - bookCountStillWithMaazoun;

            if (allowedCount > avaliableOrder) {
                long diff = allowedCount - avaliableOrder;
                if (diff == 1) {
                    long contractSoldCountStillWithMaazoun = maazounBookWarehouseService.
                            findCountMaazounBookSoldAndCollectedStatusByMaazounId(
                                    maazounProfile.getId(), bookTypeId8, bookTypeId15);

                    if (contractSoldCountStillWithMaazoun > setupContractQuota) {
                        throw new IllegalArgumentException(
                                "نأسف هذا العدد (" + allowedCount + ") من الدفاتر " + bookName +
                                        " غير مسموح به, العدد المسموح به = " + avaliableOrder + " ");
                    }
                } else {
                    throw new IllegalArgumentException(
                            "نأسف هذا العدد (" + allowedCount + ") من الدفاتر " + bookName +
                                    " غير مسموح به, العدد المسموح به = " + avaliableOrder + " ");
                }

            }

        });

    }

    private Map<String, List<BookListRequest>> groupBooksByType(List<BookListRequest> maazounBooks) {

        Map<String, List<BookListRequest>> newGroupedItems = new HashMap<>();
        for (BookListRequest book : maazounBooks) {
            String groupId = null;
            String typeId = String.valueOf(book.getTypeId());

            if (typeId.equals(StatusConstant.BOOK_ZAWAG_MUSLIM_ID_8) ||
                    typeId.equals(StatusConstant.BOOK_ZAWAG_MUSLIM_ID_15)) {
                groupId = "9-15";
            } else if (typeId.equals(StatusConstant.BOOK_TALAK_ID_8) ||
                    typeId.equals(StatusConstant.BOOK_TALAK_ID_15)) {
                groupId = "10-20";
            } else if (typeId.equals(StatusConstant.BOOK_TASADOK_ID_8) ||
                    typeId.equals(StatusConstant.BOOK_TASADOK_ID_15)) {
                groupId = "11-21";
            } else if (typeId.equals(StatusConstant.BOOK_MORAGAA_ID_8) ||
                    typeId.equals(StatusConstant.BOOK_MORAGAA_ID_15)) {
                groupId = "12-22";
            } else if (typeId.equals(StatusConstant.BOOK_ZAWAG_MELALY_ID_8) ||
                    typeId.equals(StatusConstant.BOOK_ZAWAG_MELALY_ID_15)) {
                groupId = "13-23";
            }

            if (groupId != null) {
                newGroupedItems.computeIfAbsent(groupId, k -> new ArrayList<>()).add(book);
            }
        }

        return newGroupedItems;
    }

}

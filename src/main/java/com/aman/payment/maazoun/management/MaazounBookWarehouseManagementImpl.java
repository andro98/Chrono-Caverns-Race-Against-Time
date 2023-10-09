package com.aman.payment.maazoun.management;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.aman.payment.maazoun.model.*;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aman.payment.auth.management.LookupManagement;
import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.auth.model.Pos;
import com.aman.payment.auth.model.Sector;
import com.aman.payment.auth.model.SubService;
import com.aman.payment.auth.service.SubServiceService;
import com.aman.payment.core.exception.ResourceAlreadyInUseException;
import com.aman.payment.core.model.payload.URLRequest;
import com.aman.payment.maazoun.mapper.MaazounBookSupplyOrderMapper;
import com.aman.payment.maazoun.mapper.MaazounBookWarehouseMapper;
import com.aman.payment.maazoun.model.dto.BookDTO;
import com.aman.payment.maazoun.model.dto.BooksPagingDTO;
import com.aman.payment.maazoun.model.dto.MaazounBookHistoryDTO;
import com.aman.payment.maazoun.model.dto.MaazounContractHistoryDTO;
import com.aman.payment.maazoun.model.dto.SupplyOrderDTO;
import com.aman.payment.maazoun.model.dto.SupplyOrderPagingDTO;
import com.aman.payment.maazoun.model.payload.AddBookSupplyOrder;
import com.aman.payment.maazoun.model.payload.AddContractListRequest;
import com.aman.payment.maazoun.model.payload.BookBySupplyOrderRefNumber;
import com.aman.payment.maazoun.model.payload.BookListRequest;
import com.aman.payment.maazoun.model.payload.BooksByIdRequest;
import com.aman.payment.maazoun.model.payload.BooksFilterRequest;
import com.aman.payment.maazoun.model.payload.BooksRequest;
import com.aman.payment.maazoun.model.payload.ContractByIdRequest;
import com.aman.payment.maazoun.model.payload.EditBookFinancialNumberRequest;
import com.aman.payment.maazoun.model.payload.GenerateBookSerialNumberRequest;
import com.aman.payment.maazoun.model.payload.GetContractRequest;
import com.aman.payment.maazoun.model.payload.ReviewRequest;
import com.aman.payment.maazoun.model.payload.SupplyOrderDetailsRequest;
import com.aman.payment.maazoun.model.payload.SupplyOrderRequest;
import com.aman.payment.maazoun.model.payload.WarehouseBookRequest;
import com.aman.payment.maazoun.service.CryptoMngrMaazounService;
import com.aman.payment.maazoun.service.MaazounBookStockLabelService;
import com.aman.payment.maazoun.service.MaazounBookSupplyOrderService;
import com.aman.payment.maazoun.service.MaazounBookWarehouseService;
import com.aman.payment.maazoun.service.MaazounProfileService;
import com.aman.payment.maazoun.service.MaazounRequestInfoService;
import com.aman.payment.maazoun.service.SupplyOrderDetailsService;
import com.aman.payment.maazoun.service.SupplyOrderService;
import com.aman.payment.util.StatusConstant;
import com.aman.payment.util.Util;

@Component
public class MaazounBookWarehouseManagementImpl extends ValidationAndPopulateManagement
        implements MaazounBookWarehouseManagement {

    private final MaazounBookWarehouseService maazounBookWarehouseService;
    private final CryptoMngrMaazounService cryptoMngrMaazounService;
    private final MaazounBookWarehouseMapper maazounBookWarehouseMapper;
    private final MaazounBookSupplyOrderService maazounBookSupplyOrderService;
    private final SupplyOrderService supplyOrderService;
    private final MaazounBookSupplyOrderMapper maazounBookSupplyOrderMapper;
    private final SubServiceService subServiceService;
    private final LookupManagement authLookupManagement;
    private final MaazounBookStockLabelService maazounBookStockLabelService;
    private final MaazounRequestInfoService maazounRequestInfoService;
    private final MaazounProfileService maazounProfileService;
    private final SupplyOrderDetailsService supplyOrderDetailsService;

    @Value("${attachment.maazoun.supply.orders}")
    private String supplyOrderPathAtt;

    @Value("${reports.maazoun.supply.orders}")
    private String supplyOrderPathReport;

    @Value("${aman.logo}")
    private String amanLogoUrl;

    final static Logger logger = Logger.getLogger("maazoun");

    @Autowired
    public MaazounBookWarehouseManagementImpl(MaazounBookWarehouseService maazounBookWarehouseService,
                                              CryptoMngrMaazounService cryptoMngrMaazounService, MaazounBookWarehouseMapper maazounBookWarehouseMapper,
                                              MaazounBookSupplyOrderService maazounBookSupplyOrderService, LookupManagement authLookupManagement,
                                              MaazounBookSupplyOrderMapper maazounBookSupplyOrderMapper, SubServiceService subServiceService,
                                              MaazounBookStockLabelService maazounBookStockLabelService,
                                              MaazounRequestInfoService maazounRequestInfoService, MaazounProfileService maazounProfileService,
                                              SupplyOrderDetailsService supplyOrderDetailsService, SupplyOrderService supplyOrderService) {
        super();
        this.maazounBookWarehouseService = maazounBookWarehouseService;
        this.cryptoMngrMaazounService = cryptoMngrMaazounService;
        this.maazounBookWarehouseMapper = maazounBookWarehouseMapper;
        this.maazounBookSupplyOrderService = maazounBookSupplyOrderService;
        this.authLookupManagement = authLookupManagement;
        this.maazounBookSupplyOrderMapper = maazounBookSupplyOrderMapper;
        this.subServiceService = subServiceService;
        this.maazounBookStockLabelService = maazounBookStockLabelService;
        this.maazounRequestInfoService = maazounRequestInfoService;
        this.maazounProfileService = maazounProfileService;
        this.supplyOrderDetailsService = supplyOrderDetailsService;
        this.supplyOrderService = supplyOrderService;
    }

    @Override
    public List<String> getAllBooks() {
        List<String> eBookDTOs = new ArrayList<String>();
        maazounBookWarehouseMapper.maazounBookWarehouseSetToBookDTOs(maazounBookWarehouseService.findAll()).stream()
                .forEach(s -> {
                    eBookDTOs.add(cryptoMngrMaazounService.encrypt(s.toString()));
                });
        return eBookDTOs;
    }

    @Override
    public BooksPagingDTO getAllBooks(BooksRequest booksRequest, CustomUserDetails customUserDetails) {

        Pageable pageable = PageRequest.of(Integer.valueOf(booksRequest.getPageNo()),
                Integer.valueOf(booksRequest.getPageSize()));

        Set<Long> sectorIds = customUserDetails.getPosSet().stream()
                .flatMap(pos -> pos.getSectors().stream().map(Sector::getId)).collect(Collectors.toSet());

        Page<MaazounBookWarehouse> pageResult = maazounBookWarehouseService.findBySectorIn(sectorIds, pageable,
                customUserDetails);

        BooksPagingDTO booksPagingDTO = new BooksPagingDTO();
        booksPagingDTO.setCount(pageResult.getTotalElements());
        booksPagingDTO.setTotalPages(pageResult.getTotalPages());

        List<String> encryptedBooks = pageResult.getContent().stream()
                .map(bookWarehouse -> cryptoMngrMaazounService
                        .encrypt(maazounBookWarehouseMapper.maazounBookWarehouseToBookDTO(bookWarehouse).toString()))
                .collect(Collectors.toList());

        booksPagingDTO.setBooks(encryptedBooks);

        return booksPagingDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addBookSupplyOrderCoding(CustomUserDetails customUserDetails, AddBookSupplyOrder addBookSupplyOrder) {

        Optional<SupplyOrder> eSupplyOrder = supplyOrderService
                .findByRefSupplyOrderNumber(addBookSupplyOrder.getRefSupplyOrderNumber());

        if (!eSupplyOrder.isPresent() || !eSupplyOrder.get().getStatusFk().equals(StatusConstant.STATUS_APPROVED)) {
            return cryptoMngrMaazounService.encrypt("Sorry, Reference Supply Order Number is Rejected or not present");
        }

        /*
         * validate the remaining count from books related to the ref_supply_order_number
         */
        validateTheRemainingCountInSupplyOrderDetails(addBookSupplyOrder, eSupplyOrder.get());

        Date createdAt = Date.from(Instant.now());
        Pos pos = getAssignedPOS(customUserDetails.getPosSet(), addBookSupplyOrder.getSectorId());
        Sector sector = getSectorById(pos.getSectors(), addBookSupplyOrder.getSectorId());

        MaazounBookSupplyOrder maazounBookSupplyOrderFk = populateMaazounBookSupplyOrder(pos,
                customUserDetails.getUsername(), createdAt, addBookSupplyOrder.getImageUrl(),
                Boolean.valueOf(addBookSupplyOrder.getCustody()), supplyOrderPathAtt,
                addBookSupplyOrder.getRefSupplyOrderNumber(), sector);

        maazounBookSupplyOrderFk.setImageUrl(eSupplyOrder.get().getImageUrl());
        maazounBookSupplyOrderFk = maazounBookSupplyOrderService.save(maazounBookSupplyOrderFk);

        /*
         * remove stock label validation by assem
         */
        List<MaazounBookWarehouse> batchBooks = null;
//			populateMaazounWarehouseWithoutValidation(
//					pos, customUserDetails.getUsername(), createdAt, addBookSupplyOrder, maazounBookSupplyOrderFk);

        /*
         * add the validation with stock label
         */
        try {
            batchBooks = populateAndValidateBatchMaazounWarehouseWithStockLabel(pos, customUserDetails.getUsername(),
                    createdAt, addBookSupplyOrder.getBookList(), maazounBookSupplyOrderFk);
        } catch (Exception e) {
            throw new ResourceAlreadyInUseException("addBookSupplyOrder exception when adding new maazoun books", "",
                    "no serial number found");
        }

        maazounBookWarehouseService.save(batchBooks);

//			GenerateTempSupplyOrderEvent generateTempSupplyOrderEvent = new GenerateTempSupplyOrderEvent(
//					maazounBookWarehouseMapper.maazounBookWarehouseSetToBookDTOs(maazounBookWarehouseSet),
//					String.valueOf(maazounBookSupplyOrderFk.getCreatedAt()), String.valueOf(maazounBookSupplyOrderFk.getId()));
//			applicationEventPublisher.publishEvent(generateTempSupplyOrderEvent);

        return cryptoMngrMaazounService.encrypt(maazounBookSupplyOrderMapper
                .maazounBookSupplyOrderToMaazounBookSupplyOrderDTO(maazounBookSupplyOrderFk).toString());

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addCustodyBookSupplyOrder(CustomUserDetails customUserDetails,
                                            AddBookSupplyOrder addBookSupplyOrder) {

        Date createdAt = Date.from(Instant.now());

        Pos pos = getAssignedPOS(customUserDetails.getPosSet(), addBookSupplyOrder.getSectorId());
        Sector sector = pos.getSectors().stream().filter(x -> x.getId().equals(addBookSupplyOrder.getSectorId()))
                .findAny().orElse(null);

        // validate this contract number with maazoun and the maazounia
        MaazounProfile maazounProfileFk = maazounMaazouniaValidation(addBookSupplyOrder.getMaazounId(), sector.getId());

        MaazounBookSupplyOrder maazounBookSupplyOrderFk = populateMaazounBookSupplyOrder(pos,
                customUserDetails.getUsername(), createdAt, addBookSupplyOrder.getImageUrl(),
                Boolean.valueOf(addBookSupplyOrder.getCustody()), supplyOrderPathAtt,
                addBookSupplyOrder.getRefSupplyOrderNumber(), sector);

        maazounBookSupplyOrderFk = maazounBookSupplyOrderService.save(maazounBookSupplyOrderFk);

        List<MaazounBookWarehouse> batchBooks = populateAndValidateBatchCustodyMaazounWarehouse(pos,
                customUserDetails.getUsername(), createdAt, addBookSupplyOrder, maazounBookSupplyOrderFk);

        maazounBookWarehouseService.save(batchBooks);

        for (BookListRequest book : addBookSupplyOrder.getBookList()) {

            MaazounBookRequestInfo maazounRequestInfoFk = populateMaazounBookRequestInfo(createdAt,
                    customUserDetails.getUsername(), maazounProfileFk, pos, null, null, book.getBookFinancialNumber(),
                    (double) 0, null, null, null, null, StatusConstant.STATUS_NEW, book.getTypeId(), sector,
                    book.getType(), Long.valueOf(book.getMaazouniaChurchId()), book.getMaazouniaChurchNameType(), null,
                    null);

            maazounRequestInfoFk = maazounRequestInfoService.save(maazounRequestInfoFk);

            maazounBookWarehouseService.updateStatusByBookFinancialNumber(StatusConstant.STATUS_PENDING,
                    maazounRequestInfoFk, book.getBookFinancialNumber());
        }

        MaazounProfile maazounProfile = maazounProfileService.findById(addBookSupplyOrder.getMaazounId()).get();
        maazounProfile.setIsCustody(true);
        maazounProfileService.save(maazounProfile);

//		GenerateTempSupplyOrderEvent generateTempSupplyOrderEvent = new GenerateTempSupplyOrderEvent(
//				maazounBookWarehouseMapper.maazounBookWarehouseSetToBookDTOs(maazounBookWarehouseSet),
//				String.valueOf(maazounBookSupplyOrderFk.getCreatedAt()), String.valueOf(maazounBookSupplyOrderFk.getId()));
//		applicationEventPublisher.publishEvent(generateTempSupplyOrderEvent);

        return cryptoMngrMaazounService.encrypt(maazounBookSupplyOrderMapper
                .maazounBookSupplyOrderToMaazounBookSupplyOrderDTO(maazounBookSupplyOrderFk).toString());

    }

    private List<MaazounBookWarehouse> populateAndValidateBatchCustodyMaazounWarehouse(Pos pos, String username,
                                                                                       Date updatedAt, AddBookSupplyOrder addBookSupplyOrder, MaazounBookSupplyOrder maazounBookSupplyOrderFk) {

        List<MaazounBookWarehouse> batchBooks = new ArrayList<MaazounBookWarehouse>();

        for (BookListRequest book : addBookSupplyOrder.getBookList()) {

            for (AddContractListRequest contract : book.getContracts()) {

                MaazounBookWarehouse obj = new MaazounBookWarehouse();
                obj.setBookTypeId(Long.valueOf(book.getTypeId()));
                obj.setBookTypeName(book.getType());
                obj.setMaazounBookSupplyOrderFk(maazounBookSupplyOrderFk);
                obj.setStatusFk(StatusConstant.STATUS_PENDING);
                obj.setContractCount(book.getContractCount());
                obj.setBookFinancialNumber(book.getBookFinancialNumber());
                obj.setContractFinancialNumber(contract.getContractFinancialNumber());

                batchBooks.add(obj);

            }

        }

        return batchBooks;
    }

    private List<MaazounBookWarehouse> populateMaazounWarehouseWithoutValidation(Pos pos, String username,
                                                                                 Date createdAt, AddBookSupplyOrder addBookSupplyOrder, MaazounBookSupplyOrder maazounBookSupplyOrderFk) {

        List<MaazounBookWarehouse> batchBooks = new ArrayList<MaazounBookWarehouse>();

        for (BookListRequest book : addBookSupplyOrder.getBookList()) {

            for (AddContractListRequest contract : book.getContracts()) {

                MaazounBookWarehouse obj = new MaazounBookWarehouse();
                obj.setBookTypeId(Long.valueOf(book.getTypeId()));
                obj.setBookTypeName(book.getType());
                obj.setSerialNumber(book.getSerialNumber());
                obj.setContractNumber(contract.getContractSerialNumber());
                obj.setMaazounBookSupplyOrderFk(maazounBookSupplyOrderFk);
                obj.setStatusFk(StatusConstant.STATUS_PENDING);
                obj.setContractCount(book.getContractCount());
                obj.setBookFinancialNumber(book.getBookFinancialNumber());
                obj.setContractFinancialNumber(contract.getContractFinancialNumber());

                batchBooks.add(obj);

            }

        }

        return batchBooks;
    }

    @Override
    public String getBookBySerialNumber(BooksByIdRequest booksByIdRequest, CustomUserDetails customUserDetails) {

        Optional<MaazounBookWarehouse> bookWarehouseOptional = null;
        JSONObject jsonObject = new JSONObject();

        if (booksByIdRequest.getSerialNumber() != null) {
            bookWarehouseOptional = maazounBookWarehouseService
                    .findBySerialNumberAndStatusFk(booksByIdRequest.getSerialNumber(), StatusConstant.STATUS_NEW);
        } else {
            bookWarehouseOptional = maazounBookWarehouseService.findByBookFinancialNumberAndStatusFk(
                    booksByIdRequest.getBookFinancialNumber(), StatusConstant.STATUS_NEW);
        }

        if (bookWarehouseOptional.isPresent()) {

            List<Long> sectorIds = getSectorIds(customUserDetails.getPosSet());

            if (customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_SUPPORT)
                    || customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_ADMIN)) {

                SubService subService = subServiceService.findById(bookWarehouseOptional.get().getBookTypeId()).get();
                BookDTO bookDto = maazounBookWarehouseMapper.maazounBookWarehouseToBookDTO(bookWarehouseOptional.get(),
                        subService, null);

                return cryptoMngrMaazounService.encrypt(bookDto.toString());

            } else {
                if (sectorIds
                        .contains(bookWarehouseOptional.get().getMaazounBookSupplyOrderFk().getSectorFk().getId())) {

                    SubService subService = subServiceService.findById(bookWarehouseOptional.get().getBookTypeId())
                            .get();
                    BookDTO bookDto = maazounBookWarehouseMapper
                            .maazounBookWarehouseToBookDTO(bookWarehouseOptional.get(), subService, null);

                    return cryptoMngrMaazounService.encrypt(bookDto.toString());

                } else {

                    jsonObject.put("response", "نأسف لعدم البيع لهذا الرقم الألكترونى للدفتر بسبب ارتباطه بنيابة أخرى");
                    return cryptoMngrMaazounService.encrypt(jsonObject.toString());

                }
            }
        } else {
            jsonObject.put("response", "نأسف هذا الرقم الألكترونى للدفتر غير موجود بالمخازن");
            return cryptoMngrMaazounService.encrypt(jsonObject.toString());
        }

    }

    @Override
    public String getBookBySerialNumberForSell(BooksByIdRequest booksByIdRequest, CustomUserDetails customUserDetails) {

        Optional<MaazounBookWarehouse> bookWarehouseOptional = maazounBookWarehouseService
                .findBySerialNumberAndStatusFk(booksByIdRequest.getSerialNumber(), StatusConstant.STATUS_NEW);

        JSONObject jsonObject = new JSONObject();

        if (bookWarehouseOptional.isPresent()) {

            maazounMaazouniaValidation(booksByIdRequest.getMaazounId(),
                    bookWarehouseOptional.get().getMaazounBookSupplyOrderFk().getSectorFk().getId());

            List<Long> sectorIds = getSectorIds(customUserDetails.getPosSet());

            if (sectorIds.contains(bookWarehouseOptional.get().getMaazounBookSupplyOrderFk().getSectorFk().getId())) {

                SubService subService = subServiceService.findById(bookWarehouseOptional.get().getBookTypeId()).get();

                Optional<MaazounBookStockLabel> maazounBookStockLabel = maazounBookStockLabelService.findByLabelCode(bookWarehouseOptional.get().getSerialNumber());
                if (!maazounBookStockLabel.isPresent()) {
                    jsonObject.put("response", "نأسف لعدم البيع لهذا الرقم الألكترونى للدفتر");
                    return cryptoMngrMaazounService.encrypt(jsonObject.toString());
                }

                Long tierId = maazounBookStockLabel.get().getBookTierId();

                BookDTO bookDto = maazounBookWarehouseMapper.maazounBookWarehouseToBookDTO(bookWarehouseOptional.get(),
                        subService, tierId);

                return cryptoMngrMaazounService.encrypt(bookDto.toString());

            } else {

                jsonObject.put("response", "نأسف لعدم البيع لهذا الرقم الألكترونى للدفتر بسبب ارتباطه بنيابة أخرى");
                return cryptoMngrMaazounService.encrypt(jsonObject.toString());

            }

        } else {
            jsonObject.put("response", "نأسف هذا الرقم الألكترونى للدفتر غير موجود بالمخازن");
            return cryptoMngrMaazounService.encrypt(jsonObject.toString());
        }

    }

    @Override
    public String bookDistinctBySerialNumberAndStatus(BooksByIdRequest booksByIdRequest,
                                                      CustomUserDetails customUserDetails) {

        List<MaazounBookWarehouse> bookWarehouseList = null;
        JSONObject jsonObject = new JSONObject();

        if (booksByIdRequest.getSerialNumber() != null && !booksByIdRequest.getSerialNumber().isEmpty()) {
            bookWarehouseList = maazounBookWarehouseService.findAllBySerialNumberAndStatusFk(
                    booksByIdRequest.getSerialNumber(), StatusConstant.STATUS_RECEIVED);
//			bookWarehouseOptional = maazounBookWarehouseService
//					.findBySerialNumberAndStatusFk(booksByIdRequest.getSerialNumber(), StatusConstant.STATUS_RECEIVED);
        } else {
            bookWarehouseList = maazounBookWarehouseService.findAllByBookFinancialNumberAndStatusFk(
                    booksByIdRequest.getBookFinancialNumber(), StatusConstant.STATUS_RECEIVED);
//			bookWarehouseOptional = maazounBookWarehouseService.findByBookFinancialNumberAndStatusFk(
//					booksByIdRequest.getBookFinancialNumber(), StatusConstant.STATUS_RECEIVED);
        }

        if (bookWarehouseList != null && bookWarehouseList.size() > 0) {

            if (bookWarehouseList.size() == 8 || bookWarehouseList.size() == 15) {

                List<Long> sectorIds = getSectorIds(customUserDetails.getPosSet());

                if (sectorIds.contains(bookWarehouseList.get(0).getMaazounBookSupplyOrderFk().getSectorFk().getId())) {

                    BookDTO bookDto = maazounBookWarehouseMapper
                            .maazounBookWarehouseToBookDTO(bookWarehouseList.get(0));

                    return cryptoMngrMaazounService.encrypt(bookDto.toString());

                } else {
                    jsonObject.put("response", "للأسف هذا الدفتر مرتبط بنيابة أخري");
                    return cryptoMngrMaazounService.encrypt(jsonObject.toString());

                }

            } else {
                jsonObject.put("response", "للأسف هذا الدفتر مازال به عقود غير مستلمه");
                return cryptoMngrMaazounService.encrypt(jsonObject.toString());
            }

        } else {
            jsonObject.put("response", "للأسف هذا الدفتر غير موجود بالمخزن");
            return cryptoMngrMaazounService.encrypt(jsonObject.toString());
        }

    }

    @Override
    public String getBookContructsBySerialNumber(BooksByIdRequest booksByIdRequest,
                                                 CustomUserDetails customUserDetails) {

        Set<Long> locationIds = getLocationsFromPos(customUserDetails.getPosSet());

        // Optional<MaazounBookWarehouse> bookWarehouseOptional = null;

        List<MaazounBookWarehouse> contractList = new ArrayList<MaazounBookWarehouse>();
        List<MaazounBookWarehouse> result = new ArrayList<MaazounBookWarehouse>();
        if (booksByIdRequest.getSerialNumber() != null) {
            result = maazounBookWarehouseService.findBySerialNumber(booksByIdRequest.getSerialNumber());
        } else {
            result = maazounBookWarehouseService.findByBookFinancialNumber(booksByIdRequest.getBookFinancialNumber());
        }

        for (MaazounBookWarehouse eMaazounBookWarehouse : result) {
            boolean isCheck = checkAssignedPOS(locationIds,
                    eMaazounBookWarehouse.getMaazounBookSupplyOrderFk().getLocationId());
            if (isCheck) {
                contractList.add(eMaazounBookWarehouse);
            }
        }

        if (contractList != null && contractList.size() > 0) {

            return cryptoMngrMaazounService
                    .encrypt(maazounBookWarehouseMapper.maazounBookWarehouseSetToBookDTOs(contractList).toString());

        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("response", "This Serial Number or Financial Number not related with your pos...");

            return cryptoMngrMaazounService.encrypt(jsonObject.toString());
        }

    }

    @Override
    public String generateBookSerialNumber(CustomUserDetails customUserDetails,
                                           GenerateBookSerialNumberRequest generateBookSerialNumberRequest) {

        Pos pos = getAssignedPOS(customUserDetails.getPosSet(), generateBookSerialNumberRequest.getSectorId());
        Sector sector = pos.getSectors().stream()
                .filter(x -> x.getId().equals(generateBookSerialNumberRequest.getSectorId())).findAny().orElse(null);

        long locationId = sector.getLocationFk().getId();
        long posId = pos.getId();

        long serialNumber = authLookupManagement.getInsuranceNumber(
                Long.parseLong(generateBookSerialNumberRequest.getTypeId()), locationId,
                Long.parseLong(generateBookSerialNumberRequest.getServiceId()));

        String serialNumberFormat = String.format("%03d", posId) + String.format("%08d", serialNumber);

        BookDTO bookDTO = new BookDTO();

        bookDTO.setSerialNumber(serialNumberFormat);
        bookDTO.setType(generateBookSerialNumberRequest.getTypeName());
        bookDTO.setTypeId(generateBookSerialNumberRequest.getTypeId());
        bookDTO.setContractCount(generateBookSerialNumberRequest.getContractCount());
        bookDTO.setFees(generateBookSerialNumberRequest.getFees());
        bookDTO.setBookFinancialNumber(generateBookSerialNumberRequest.getBookFinancialNumber());
        bookDTO.setSectorId(String.valueOf(sector.getId()));
        bookDTO.setSectorName(sector.getName());

        return cryptoMngrMaazounService.encrypt(bookDTO.toString());

    }

    @Override
    public SupplyOrderPagingDTO getAllSupplyOrdersCoding(SupplyOrderRequest supplyOrderRequest,
                                                         CustomUserDetails customUserDetails) {

        Pageable pageable = PageRequest.of(Integer.valueOf(supplyOrderRequest.getPageNo()),
                Integer.valueOf(supplyOrderRequest.getPageSize()));

        Page<MaazounBookSupplyOrder> pageResult = retriveMaazounBookSypplyOrder(supplyOrderRequest, customUserDetails,
                pageable);

        SupplyOrderPagingDTO supplyOrderPagingDTO = new SupplyOrderPagingDTO();
        supplyOrderPagingDTO.setCount(pageResult.getTotalElements());
        supplyOrderPagingDTO.setTotalPages(pageResult.getTotalPages());

        List<String> encryptedSupplyOrders = maazounBookSupplyOrderMapper
                .maazounBookSupplyOrdersToMaazounBookSupplyOrderDTOs(pageResult.getContent()).stream()
                .map(supplyOrderDTO -> cryptoMngrMaazounService.encrypt(supplyOrderDTO.toString()))
                .collect(Collectors.toList());

        supplyOrderPagingDTO.setSupplyOrders(encryptedSupplyOrders);

        return supplyOrderPagingDTO;
    }

    @Override
    public SupplyOrderPagingDTO getAllSupplyOrders(SupplyOrderRequest supplyOrderRequest,
                                                   CustomUserDetails customUserDetails) {

        Pageable pageable = PageRequest.of(Integer.valueOf(supplyOrderRequest.getPageNo()),
                Integer.valueOf(supplyOrderRequest.getPageSize()));

        Page<SupplyOrder> pageResult = retrieveSupplyOrders(supplyOrderRequest, customUserDetails, pageable);

        SupplyOrderPagingDTO supplyOrderPagingDTO = new SupplyOrderPagingDTO();
        supplyOrderPagingDTO.setCount(pageResult.getTotalElements());
        supplyOrderPagingDTO.setTotalPages(pageResult.getTotalPages());

        List<String> encryptedSupplyOrders = maazounBookSupplyOrderMapper
                .supplyOrdersToSupplyOrderDTOs(pageResult.getContent()).stream()
                .map(supplyOrderDTO -> cryptoMngrMaazounService.encrypt(supplyOrderDTO.toString()))
                .collect(Collectors.toList());

        supplyOrderPagingDTO.setSupplyOrders(encryptedSupplyOrders);

        return supplyOrderPagingDTO;
    }

    @Override
    public List<String> warehouseReportByLocationAndBookType(CustomUserDetails customUserDetails) {

        List<String> eCalculations = new ArrayList<String>();
        maazounBookWarehouseService.warehouseReportByLocationAndBookType(customUserDetails).stream().forEach(s -> {
            eCalculations.add(cryptoMngrMaazounService.encrypt(s.toString()));
        });

        return eCalculations;
    }

    @Override
    public List<String> warehouseReportByBookType(CustomUserDetails customUserDetails,
                                                  WarehouseBookRequest warehouseBookRequest) {

        List<String> eCalculations = new ArrayList<String>();
        maazounBookWarehouseService.warehouseReportByBookType(customUserDetails, warehouseBookRequest).stream()
                .forEach(s -> {
                    eCalculations.add(cryptoMngrMaazounService.encrypt(s.toString()));
                });

        return eCalculations;
    }

    @Override
    public List<String> warehouseReportByDaily(CustomUserDetails customUserDetails,
                                               WarehouseBookRequest warehouseBookRequest) {

        List<String> eCalculations = new ArrayList<String>();
        maazounBookWarehouseService.warehouseReportByDaily(customUserDetails, warehouseBookRequest).stream()
                .forEach(s -> {
                    eCalculations.add(cryptoMngrMaazounService.encrypt(s.toString()));
                });

        return eCalculations;
    }

    @Override
    public String reviewSupplyOrderCoding(CustomUserDetails customUserDetails, ReviewRequest reviewSupplyOrderRequest) {

        MaazounBookSupplyOrder eMaazounBookSupplyOrder = maazounBookSupplyOrderService
                .findById(Long.valueOf(reviewSupplyOrderRequest.getId())).get();

        if (reviewSupplyOrderRequest.getStatus().equalsIgnoreCase(StatusConstant.STATUS_APPROVED)) {
            eMaazounBookSupplyOrder.setStatusFk(StatusConstant.STATUS_APPROVED);
            updateRemainingBookTypeCountInMainSupplyOrderDetails(eMaazounBookSupplyOrder);
        } else {
            eMaazounBookSupplyOrder.setStatusFk(StatusConstant.STATUS_REJECTED);
        }

        eMaazounBookSupplyOrder.setIsReviewed(true);
        eMaazounBookSupplyOrder.setUpdatedAt(Date.from(Instant.now()));
        eMaazounBookSupplyOrder.setUpdatedBy(customUserDetails.getUsername());
        eMaazounBookSupplyOrder.setComment(reviewSupplyOrderRequest.getComment());

        eMaazounBookSupplyOrder = maazounBookSupplyOrderService.save(eMaazounBookSupplyOrder);

        SupplyOrderDTO eSupplyOrderDTO = maazounBookSupplyOrderMapper
                .maazounBookSupplyOrderToMaazounBookSupplyOrderDTO(eMaazounBookSupplyOrder);

        doWarehouseUpdate(reviewSupplyOrderRequest, eMaazounBookSupplyOrder, customUserDetails.getUsername());

        return cryptoMngrMaazounService.encrypt(eSupplyOrderDTO.toString());
    }

    @Override
    public InputStream downloadFile(URLRequest uRLRequest) {

        File pdfFile = new File(uRLRequest.getUrl());
        InputStream is = null;
        try {
            is = new FileInputStream(pdfFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return is;
    }

    @Override
    public String getSupplyOrderByRefrenceNumber(BookBySupplyOrderRefNumber bookBySupplyOrderRefNumber) {
        MaazounBookSupplyOrder obj = maazounBookSupplyOrderService
                .findByRefSupplyOrderNumber(bookBySupplyOrderRefNumber.getSupplyOrderRefNumber());
        return cryptoMngrMaazounService.encrypt(
                (maazounBookSupplyOrderMapper.maazounBookSupplyOrderToMaazounBookSupplyOrderDTO(obj).toString()));
    }

    @Override
    public String getContractBySerialNumber(CustomUserDetails customUserDetails,
                                            ContractByIdRequest contractByIdRequest) {

        Optional<MaazounBookWarehouse> contractObj = maazounBookWarehouseService
                .findByContractNumberAndStatusFk(contractByIdRequest.getSerialNumber(), StatusConstant.STATUS_SOLD);

        if (contractObj.isPresent()) {

            Optional<SubService> subService = subServiceService.findById(contractObj.get().getBookTypeId());
            if (!subService.isPresent()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("response", "This Contract Type is not applicable");

                return cryptoMngrMaazounService.encrypt(jsonObject.toString());

            }

            List<Long> sectorIds = getSectorIds(customUserDetails.getPosSet());

            if (sectorIds.contains(contractObj.get().getMaazounBookSupplyOrderFk().getSectorFk().getId())) {

                return cryptoMngrMaazounService
                        .encrypt(maazounBookWarehouseMapper.previewContractCollectionDTO(contractObj.get(), subService.get()).toString());

            } else {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("response", "This Contract Serial Number Maazounia not Applicable with this Sector");

                return cryptoMngrMaazounService.encrypt(jsonObject.toString());

            }

        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("response", "This Contract Serial Number Not Found In Warehouse");

            return cryptoMngrMaazounService.encrypt(jsonObject.toString());
        }

    }

    @Override
    public List<String> getBookReceivedContractsBySerialNumber(BooksByIdRequest booksByIdRequest,
                                                               CustomUserDetails customUserDetails) {

        List<String> eTransaction = new ArrayList<String>();

        maazounBookWarehouseService
                .findAllBySerialNumberAndStatusFk(booksByIdRequest.getSerialNumber(), StatusConstant.STATUS_RECEIVED)
                .stream().forEach(s -> {
                    eTransaction.add(cryptoMngrMaazounService
                            .encrypt(maazounBookWarehouseMapper.maazounBookWarehouseToBookDTO(s).toString()));
                });

        return eTransaction;

    }

    private void doWarehouseUpdate(ReviewRequest reviewSupplyOrderRequest,
                                   MaazounBookSupplyOrder eMaazounBookSupplyOrder, String updatedBy) {

        Date updatedAt = Date.from(Instant.now());

        if (reviewSupplyOrderRequest.getStatus().equalsIgnoreCase(StatusConstant.STATUS_APPROVED)) {

            if (Boolean.valueOf(reviewSupplyOrderRequest.getCustody())) {
                maazounBookWarehouseService.updateStatusByMaazounBookSupplyOrderFk(StatusConstant.STATUS_SOLD,
                        eMaazounBookSupplyOrder);
            } else {
                maazounBookWarehouseService.updateStatusByMaazounBookSupplyOrderFk(StatusConstant.STATUS_NEW,
                        eMaazounBookSupplyOrder);
            }

        } else {
            Set<MaazounBookWarehouse> bookList = eMaazounBookSupplyOrder.getMaazounBookWarehouseSet();
            if (!Boolean.valueOf(reviewSupplyOrderRequest.getCustody())) {
                /*
                 * remove stock label validation by assem
                 */
                Set<String> labelCodes = new HashSet<String>();
                labelCodes.add(bookList.iterator().next().getSerialNumber());
                bookList.stream().forEach(s -> {
                    labelCodes.add(s.getContractNumber());
                });
                maazounBookStockLabelService.updateStatusByLabelCode(StatusConstant.STATUS_PRINTED, labelCodes);

                maazounBookSupplyOrderService.deleteById(eMaazounBookSupplyOrder.getId());
            } else {
                eMaazounBookSupplyOrder.getMaazounBookWarehouseSet().stream().forEach(s -> {
                    MaazounBookRequestInfo eMaazounBookRequestInfo = s.getMaazounBookRequestInfoFk();
                    eMaazounBookRequestInfo.setStatusFk(StatusConstant.STATUS_REJECTED);
                    eMaazounBookRequestInfo.setUpdatedAt(updatedAt);
                    eMaazounBookRequestInfo.setUpdatedBy(updatedBy);

                    maazounRequestInfoService.save(eMaazounBookRequestInfo);
                });
                maazounBookSupplyOrderService.deleteById(eMaazounBookSupplyOrder.getId());
            }

        }

    }

    @Override
    public String getContractByContractFinancialNumber(CustomUserDetails customUserDetails,
                                                       ContractByIdRequest contractByIdRequest) {

        Optional<MaazounBookWarehouse> contractObj = maazounBookWarehouseService
                .findByContractFinancialNumberAndStatusFk(contractByIdRequest.getContractFinancialNumber(),
                        StatusConstant.STATUS_SOLD);

        if (contractObj.isPresent()) {

            Optional<SubService> subService = subServiceService.findById(contractObj.get().getBookTypeId());
            if (!subService.isPresent()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("response", "This Contract Type is not applicable");

                return cryptoMngrMaazounService.encrypt(jsonObject.toString());

            }

            List<Long> sectorIds = getSectorIds(customUserDetails.getPosSet());

            if (sectorIds.contains(contractObj.get().getMaazounBookSupplyOrderFk().getSectorFk().getId())) {

                return cryptoMngrMaazounService
                        .encrypt(maazounBookWarehouseMapper.previewContractCollectionDTO(contractObj.get(), subService.get()).toString());

            } else {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("response", "This Contract Financial Number Maazounia not Applicable with this Sector");

                return cryptoMngrMaazounService.encrypt(jsonObject.toString());

            }

        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("response", "This Contract Financial Number Not Found In Warehouse");

            return cryptoMngrMaazounService.encrypt(jsonObject.toString());
        }
    }

    @Override
    public String getMaazounBookHistory(BooksByIdRequest booksByIdRequest) {
        List<MaazounBookWarehouse> contractsBookInWarehouse = null;

        if (booksByIdRequest.getBookFinancialNumber() != null && !booksByIdRequest.getBookFinancialNumber().isEmpty()) {
            contractsBookInWarehouse = maazounBookWarehouseService
                    .findByBookFinancialNumber(booksByIdRequest.getBookFinancialNumber());
        } else {
            contractsBookInWarehouse = maazounBookWarehouseService
                    .findBySerialNumber(booksByIdRequest.getSerialNumber());
        }

        MaazounBookHistoryDTO eMaazounBookHistoryDTO = new MaazounBookHistoryDTO();

        if (contractsBookInWarehouse != null && contractsBookInWarehouse.size() > 0) {
            // 1- Maazoun Book Public Info
            MaazounBookWarehouse eMaazounBookWarehouse = contractsBookInWarehouse.get(0);

            eMaazounBookHistoryDTO.setBookType(eMaazounBookWarehouse.getBookTypeName());
            eMaazounBookHistoryDTO.setSerialNumber(eMaazounBookWarehouse.getSerialNumber());
            eMaazounBookHistoryDTO.setFinancialNumber(eMaazounBookWarehouse.getBookFinancialNumber());
            eMaazounBookHistoryDTO.setBookLastStatus(eMaazounBookWarehouse.getStatusFk());
            eMaazounBookHistoryDTO.setNumberOfContracts(eMaazounBookWarehouse.getContractCount());

            MaazounBookSupplyOrder eMaazounBookSupplyOrder = eMaazounBookWarehouse.getMaazounBookSupplyOrderFk();

            eMaazounBookHistoryDTO.setSuppliedDateBy(Util.dateFormat(eMaazounBookSupplyOrder.getCreatedAt()) + "/"
                    + eMaazounBookSupplyOrder.getCreatedBy());
            eMaazounBookHistoryDTO.setSuppliedReviewedDateBy(Util.dateFormat(eMaazounBookSupplyOrder.getUpdatedAt())
                    + "/" + eMaazounBookSupplyOrder.getUpdatedBy());
            eMaazounBookHistoryDTO.setCustody(String.valueOf(eMaazounBookSupplyOrder.getIsCustody()));

            // 3- Maazoun book sold info
            MaazounBookRequestInfo eMaazounBookRequestInfo = eMaazounBookWarehouse.getMaazounBookRequestInfoFk();
            if (eMaazounBookRequestInfo != null) {

                eMaazounBookHistoryDTO.setSoldDateBy(Util.dateFormat(eMaazounBookRequestInfo.getCreatedAt()) + "/"
                        + eMaazounBookRequestInfo.getCreatedBy());
                eMaazounBookHistoryDTO.setMaazounNameAndType(eMaazounBookRequestInfo.getMaazounProfileFk().getName()
                        + "/" + eMaazounBookRequestInfo.getMaazounProfileFk().getMaazounType());
                eMaazounBookHistoryDTO
                        .setMaazounNationalId(eMaazounBookRequestInfo.getMaazounProfileFk().getNationalId());
                eMaazounBookHistoryDTO
                        .setSectorName(eMaazounBookRequestInfo.getSectorFk().getLocationFk().getCityFk().getNameAr()
                                + "/" + eMaazounBookRequestInfo.getSectorFk().getLocationFk().getName() + "/"
                                + eMaazounBookRequestInfo.getSectorFk().getName() + "/"
                                + eMaazounBookRequestInfo.getPosFk().getName());

            }

            List<String> contracts = new ArrayList<String>();
            // 4- Maazoun book contracts info
            for (MaazounBookWarehouse eContract : contractsBookInWarehouse) {

                MaazounBookCollectionInfo eMaazounBookCollectionInfo = eContract.getMaazounBookCollectionInfoFk();

                MaazounContractHistoryDTO eMaazounContractHistoryDTO = new MaazounContractHistoryDTO();
                eMaazounContractHistoryDTO.setContractFinancialNumber(eContract.getContractFinancialNumber());
                eMaazounContractHistoryDTO.setContractSerialNumber(eContract.getContractNumber());

                if (eMaazounBookCollectionInfo != null) {
                    eMaazounContractHistoryDTO.setCollectedDateBy(
                            eMaazounBookCollectionInfo.getCreatedAt() != null
                                    ? Util.dateFormat(eMaazounBookCollectionInfo.getCreatedAt()) + "/"
                                    + eMaazounBookCollectionInfo.getCreatedBy()
                                    : null);
                    eMaazounContractHistoryDTO.setReceivedDateBy(
                            eMaazounBookCollectionInfo.getReviewedAt() != null
                                    ? Util.dateFormat(eMaazounBookCollectionInfo.getReviewedAt()) + "/"
                                    + eMaazounBookCollectionInfo.getReviewedBy()
                                    : null);
                    eMaazounContractHistoryDTO.setReceivedStatus(eMaazounBookCollectionInfo.getReceivedStatus());
                    eMaazounContractHistoryDTO.setReviewedDateBy(
                            eMaazounBookCollectionInfo.getReceivedAt() != null
                                    ? Util.dateFormat(eMaazounBookCollectionInfo.getReceivedAt()) + "/"
                                    + eMaazounBookCollectionInfo.getReceivedBy()
                                    : null);
                }

                contracts.add(eMaazounContractHistoryDTO.toString());

            }

            // 5- Maazoun book delivered info
            MaazounBookDeliverInfo eMaazounBookDeliverInfo = eMaazounBookWarehouse.getMaazounBookDeliverInfoFk();
            if (eMaazounBookDeliverInfo != null) {

                eMaazounBookHistoryDTO.setDeliveryDateBy(Util.dateFormat(eMaazounBookDeliverInfo.getCreatedAt()) + "/"
                        + eMaazounBookDeliverInfo.getCreatedBy());
                eMaazounBookHistoryDTO.setDeliveryApprovedDateBy(Util.dateFormat(eMaazounBookDeliverInfo.getUpdatedAt())
                        + "/" + eMaazounBookDeliverInfo.getUpdatedBy());

            }

            eMaazounBookHistoryDTO.setContracts(contracts);
            return cryptoMngrMaazounService.encrypt(eMaazounBookHistoryDTO.toString());

        } else {
            throw new IllegalArgumentException("نأسف هذا الدفتر غير موجود بالمخزن");
        }

    }

    @Override
    public BooksPagingDTO getBookByStatus(BooksFilterRequest booksByStatusRequest,
                                          CustomUserDetails customUserDetails) {

        Set<Long> posIds = new HashSet<Long>();
        customUserDetails.getPosSet().stream().forEach(s -> {
            posIds.add(s.getId());
        });

        Pageable pageable = PageRequest.of(booksByStatusRequest.getPageNo(), booksByStatusRequest.getPageSize());

        Page<MaazounBookWarehouse> pageResult = null;

        if (customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT)
                || customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT_SUPERVISOR)
                || customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AREA_MANAGER)
                || customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_SUPERVISOR)) {

            pageResult = maazounBookWarehouseService.findByStatusFkAndPosIn(booksByStatusRequest.getStatus(), posIds,
                    pageable);
        } else {
            pageResult = maazounBookWarehouseService.findByStatusFk(booksByStatusRequest.getStatus(), pageable);
        }

        BooksPagingDTO booksPagingDTO = new BooksPagingDTO();
        booksPagingDTO.setCount(pageResult.getTotalElements());
        booksPagingDTO.setTotalPages(pageResult.getTotalPages());

        List<String> eBooks = new ArrayList<String>();
        pageResult.getContent().stream().forEach(s -> {
            eBooks.add(cryptoMngrMaazounService
                    .encrypt(maazounBookWarehouseMapper.maazounBookWarehouseToBookDTO(s).toString()));
        });

        booksPagingDTO.setBooks(eBooks);

        return booksPagingDTO;
    }

    @Override
    public BooksPagingDTO getBookBySectorId(BooksFilterRequest booksByStatusRequest,
                                            CustomUserDetails customUserDetails) {

        Set<Long> sectorIds = new HashSet<Long>();
        customUserDetails.getPosSet().stream().forEach(s -> {
            for (Sector sector : s.getSectors()) {
                sectorIds.add(sector.getId());
            }
        });

        Pageable pageable = PageRequest.of(booksByStatusRequest.getPageNo(), booksByStatusRequest.getPageSize());

        Page<MaazounBookWarehouse> pageResult = maazounBookWarehouseService.findBySectorFk(booksByStatusRequest,
                sectorIds, customUserDetails, pageable);

        BooksPagingDTO booksPagingDTO = new BooksPagingDTO();
        booksPagingDTO.setCount(pageResult.getTotalElements());
        booksPagingDTO.setTotalPages(pageResult.getTotalPages());

        List<String> eBooks = new ArrayList<String>();
        pageResult.getContent().stream().forEach(s -> {
            eBooks.add(cryptoMngrMaazounService
                    .encrypt(maazounBookWarehouseMapper.maazounBookWarehouseToBookDTO(s).toString()));
        });

        booksPagingDTO.setBooks(eBooks);

        return booksPagingDTO;
    }

    @Override
    public List<String> getAllSupplyOrderDetails(SupplyOrderDetailsRequest supplyOrderDetailsRequest,
                                                 CustomUserDetails customUserDetails) {

        MaazounBookSupplyOrder maazounBookSupplyOrderFk = maazounBookSupplyOrderService
                .findById(supplyOrderDetailsRequest.getSupplyOrderId()).get();

        List<String> eBooks = new ArrayList<String>();

        maazounBookWarehouseService.findByMaazounBookSupplyOrderFk(maazounBookSupplyOrderFk).stream().forEach(s -> {
            eBooks.add(cryptoMngrMaazounService
                    .encrypt(maazounBookWarehouseMapper.maazounBookWarehouseToBookDTO(s).toString()));
        });

        return eBooks;
    }

    @Override
    public String editBookFinancialNumber(EditBookFinancialNumberRequest editBookFinancialNumberRequest) {

        List<MaazounBookWarehouse> newBooks = new ArrayList<MaazounBookWarehouse>();

        List<MaazounBookWarehouse> eBooks = maazounBookWarehouseService
                .findByBookFinancialNumber(editBookFinancialNumberRequest.getBookFinancialNumber());

        for (MaazounBookWarehouse bookWarehouse : eBooks) {
            bookWarehouse.setBookFinancialNumber(editBookFinancialNumberRequest.getNewBookFinancialNumber());
            String contractIndex = bookWarehouse.getContractFinancialNumber().split("-")[1];
            bookWarehouse.setContractFinancialNumber(
                    editBookFinancialNumberRequest.getNewBookFinancialNumber() + "-" + contractIndex);

            if (bookWarehouse.getMaazounBookCollectionInfoFk() != null) {
                bookWarehouse.getMaazounBookCollectionInfoFk().setContractNumber(
                        editBookFinancialNumberRequest.getNewBookFinancialNumber() + "-" + contractIndex);
                bookWarehouse.getMaazounBookCollectionInfoFk()
                        .setBookSerialNumber(editBookFinancialNumberRequest.getNewBookFinancialNumber());
            }
            if (bookWarehouse.getMaazounBookRequestInfoFk() != null) {
                bookWarehouse.getMaazounBookRequestInfoFk()
                        .setBookSerialNumber(editBookFinancialNumberRequest.getNewBookFinancialNumber());
            }

            newBooks.add(bookWarehouse);

        }
        maazounBookWarehouseService.save(newBooks);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("response", "sucsse");

        return cryptoMngrMaazounService.encrypt(jsonObject.toString());

    }

    @Override
    public String getBookExistingStatusByBookFinancialNumber(BooksByIdRequest booksByIdRequest) {

//		Optional<MaazounBookWarehouse> bookWarehouseOptional =maazounBookWarehouseService.
//				findFirstByBookFinancialNumber(booksByIdRequest.getBookFinancialNumber());

        List<MaazounBookWarehouse> bookWarehouseList = maazounBookWarehouseService
                .findByBookFinancialNumber(booksByIdRequest.getBookFinancialNumber());

        JSONObject jsonObject = new JSONObject();
        if (bookWarehouseList != null && bookWarehouseList.size() > 0) {
            jsonObject.put("response", "found");

        } else {
            jsonObject.put("response", "NotFound");
        }
        return cryptoMngrMaazounService.encrypt(jsonObject.toString());
    }

    @Override
    public String getBookExistingStatusByBookSerialNumber(BooksByIdRequest booksByIdRequest) {

//		Optional<MaazounBookWarehouse> bookWarehouseOptional =maazounBookWarehouseService.
//				findFirstBySerialNumber(booksByIdRequest.getSerialNumber());

        List<MaazounBookWarehouse> bookWarehouseList = maazounBookWarehouseService
                .findBySerialNumber(booksByIdRequest.getSerialNumber());

        JSONObject jsonObject = new JSONObject();
        if (bookWarehouseList != null && bookWarehouseList.size() > 0) {
            jsonObject.put("response", "found");

        } else {
            jsonObject.put("response", "NotFound");
        }
        return cryptoMngrMaazounService.encrypt(jsonObject.toString());
    }

    @Override
    public String getBookExistingStatusByContractNumber(GetContractRequest getContractRequest) {

        Optional<MaazounBookWarehouse> bookWarehouseOptional = maazounBookWarehouseService
                .findByContractNumber(getContractRequest.getContractNumber());
        // findFirstByContractNumber(getContractRequest.getContractNumber());

        JSONObject jsonObject = new JSONObject();
        if (bookWarehouseOptional.isPresent()) {
            jsonObject.put("response", "found");

        } else {
            jsonObject.put("response", "NotFound");
        }
        return cryptoMngrMaazounService.encrypt(jsonObject.toString());
    }

    @Override
    public String findBySerialNumberOrBookFinancialNumber(BooksByIdRequest decryptBooksRequest) {

        List<MaazounBookWarehouse> bookWarehouseOptional = null;
        JSONObject jsonObject = new JSONObject();

        if (decryptBooksRequest.getSerialNumber() != null) {

            bookWarehouseOptional = maazounBookWarehouseService
                    .findBySerialNumber(decryptBooksRequest.getSerialNumber());

            if (bookWarehouseOptional != null && bookWarehouseOptional.size() > 0) {
                jsonObject.put("response", "found");
                return cryptoMngrMaazounService.encrypt(jsonObject.toString());

            }

        }

        if (decryptBooksRequest.getBookFinancialNumber() != null) {

            bookWarehouseOptional = maazounBookWarehouseService
                    .findByBookFinancialNumber(decryptBooksRequest.getBookFinancialNumber());

            if (bookWarehouseOptional != null && bookWarehouseOptional.size() > 0) {
                jsonObject.put("response", "found");
                return cryptoMngrMaazounService.encrypt(jsonObject.toString());

            }
        }

        if (bookWarehouseOptional == null) {

            jsonObject.put("response", "NotFound");
        }

        return cryptoMngrMaazounService.encrypt(jsonObject.toString());
    }

    @Override
    public String getSoldOrNewBySerialNumber(BooksByIdRequest booksByIdRequest, CustomUserDetails customUserDetails) {
//		Optional<MaazounBookWarehouse> bookWarehouseOptional = null;
        MaazounBookWarehouse bookWarehouseOptional = null;

        try {
            if (booksByIdRequest.getSerialNumber() != null) {
//				bookWarehouseOptional = maazounBookWarehouseService.findBySerialNumberAndStatusFkOrStatusFk(
//						booksByIdRequest.getSerialNumber(), StatusConstant.STATUS_NEW, StatusConstant.STATUS_SOLD);
                bookWarehouseOptional = maazounBookWarehouseService
                        .findBySerialNumber(booksByIdRequest.getSerialNumber()).get(0);
            } else {
//				bookWarehouseOptional = maazounBookWarehouseService.findByBookFinancialNumberAndStatusFkOrStatusFk(
//						booksByIdRequest.getBookFinancialNumber(), StatusConstant.STATUS_NEW, StatusConstant.STATUS_SOLD);
                bookWarehouseOptional = maazounBookWarehouseService
                        .findByBookFinancialNumber(booksByIdRequest.getBookFinancialNumber()).get(0);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("نأسف هذا الرقم المالى او الألكترونى غير موجود بالمخازن");
        }

        if (bookWarehouseOptional != null) {

            List<Long> sectorIds = getSectorIds(customUserDetails.getPosSet());

            if (customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_SUPPORT)
                    || customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_ADMIN)) {

                SubService subService = subServiceService.findById(bookWarehouseOptional.getBookTypeId()).get();
                BookDTO bookDto = maazounBookWarehouseMapper.maazounBookWarehouseToBookDTO(bookWarehouseOptional,
                        subService, null);

                return cryptoMngrMaazounService.encrypt(bookDto.toString());

            } else {
                if (sectorIds.contains(bookWarehouseOptional.getMaazounBookSupplyOrderFk().getSectorFk().getId())) {

                    SubService subService = subServiceService.findById(bookWarehouseOptional.getBookTypeId()).get();
                    BookDTO bookDto = maazounBookWarehouseMapper.maazounBookWarehouseToBookDTO(bookWarehouseOptional,
                            subService, null);

                    return cryptoMngrMaazounService.encrypt(bookDto.toString());

                } else {
                    throw new IllegalArgumentException(
                            "نأسف هذا الرقم موجود بالمخازن ولكن غير مسموح به مع هذا المستخدم");
//					JSONObject jsonObject = new JSONObject();
//					jsonObject.put("response", "نأسف هذا الرقم موجود بالمخازن ولكن غير مسموح به مع هذا المستخدم");
//
//					return cryptoMngrMaazounService.encrypt(jsonObject.toString());--

                }
            }
        } else {
            throw new IllegalArgumentException("نأسف هذا الرقم المالى او الألكترونى غير موجود بالمخازن");
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("response", "نأسف هذا الرقم المالى او الألكترونى غير موجود بالمخازن");
//
//			return cryptoMngrMaazounService.encrypt(jsonObject.toString());
        }
    }

    @Override
    public String deleteBookByBookFinancialNumber(CustomUserDetails customUserDetails,
                                                  BooksByIdRequest decryptBooksRequest) {

        JSONObject jsonObject = new JSONObject();
        Date updatedAt = Date.from(Instant.now());

        List<MaazounBookWarehouse> contractsBookInWarehouse = null;
        if (decryptBooksRequest.getBookFinancialNumber() != null
                && !decryptBooksRequest.getBookFinancialNumber().isEmpty()) {
            contractsBookInWarehouse = maazounBookWarehouseService
                    .findByBookFinancialNumber(decryptBooksRequest.getBookFinancialNumber());
        } else {
            contractsBookInWarehouse = maazounBookWarehouseService
                    .findBySerialNumber(decryptBooksRequest.getSerialNumber());
        }

        for (MaazounBookWarehouse eMaazounBookWarehouse : contractsBookInWarehouse) {

            if (!eMaazounBookWarehouse.getMaazounBookSupplyOrderFk().getIsCustody()
                    && decryptBooksRequest.getSerialNumber() != null
                    && !decryptBooksRequest.getSerialNumber().isEmpty()) {
                if (!eMaazounBookWarehouse.getStatusFk().equals(StatusConstant.STATUS_NEW)) {
                    jsonObject.put("response", "Sorry, There are contracts already sold before for this serial book");
                    return jsonObject.toString();
                }
            } else if (eMaazounBookWarehouse.getMaazounBookSupplyOrderFk().getIsCustody()
                    && decryptBooksRequest.getBookFinancialNumber() != null
                    && !decryptBooksRequest.getBookFinancialNumber().isEmpty()) {

                if (!eMaazounBookWarehouse.getStatusFk().equals(StatusConstant.STATUS_SOLD)) {
                    jsonObject.put("response",
                            "Sorry, There are contracts already sold before for this financial book");
                    return jsonObject.toString();
                }

            }
        }

        Set<String> labelCodes = new HashSet<String>();
        for (MaazounBookWarehouse eMaazounBookWarehouse : contractsBookInWarehouse) {
            try {
                if (eMaazounBookWarehouse.getMaazounBookSupplyOrderFk().getIsCustody()
                        && decryptBooksRequest.getBookFinancialNumber() != null
                        && !decryptBooksRequest.getBookFinancialNumber().isEmpty()) {

                    MaazounBookRequestInfo eMaazounBookRequestInfo = eMaazounBookWarehouse
                            .getMaazounBookRequestInfoFk();
                    eMaazounBookRequestInfo.setStatusFk(StatusConstant.STATUS_REJECTED);
                    eMaazounBookRequestInfo.setUpdatedAt(updatedAt);
                    eMaazounBookRequestInfo.setUpdatedBy(customUserDetails.getUsername());

                    maazounRequestInfoService.save(eMaazounBookRequestInfo);
                } else {
                    labelCodes.add(eMaazounBookWarehouse.getSerialNumber());
                    labelCodes.add(eMaazounBookWarehouse.getContractNumber());
                }
                maazounBookWarehouseService.deleteByWarehouseId(eMaazounBookWarehouse.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        /*
         * remove stock label validation by assem
         */
        if (labelCodes.size() > 0) {
            maazounBookStockLabelService.updateStatusByLabelCode(StatusConstant.STATUS_PRINTED, labelCodes);
        }

        jsonObject.put("response", "Delete Operation Succeed");

        return jsonObject.toString();
    }

    @Override
    public String editBookTypeByBookFinancialNumber(EditBookFinancialNumberRequest editBookFinancialNumberRequest) {
        List<MaazounBookWarehouse> newBooks = new ArrayList<MaazounBookWarehouse>();

        List<MaazounBookWarehouse> eBooks = maazounBookWarehouseService
                .findByBookFinancialNumber(editBookFinancialNumberRequest.getBookFinancialNumber());

        for (MaazounBookWarehouse bookWarehouse : eBooks) {

            bookWarehouse.setBookTypeId(Long.valueOf(editBookFinancialNumberRequest.getBookTypeId()));
            bookWarehouse.setBookTypeName(editBookFinancialNumberRequest.getBookType());
//				  
            if (bookWarehouse.getMaazounBookRequestInfoFk() != null) {
                bookWarehouse.getMaazounBookRequestInfoFk()
                        .setBookTypeId(Long.valueOf(editBookFinancialNumberRequest.getBookTypeId()));
                bookWarehouse.getMaazounBookRequestInfoFk()
                        .setBookTypeName(editBookFinancialNumberRequest.getBookType());
            }

            newBooks.add(bookWarehouse);

        }
        maazounBookWarehouseService.save(newBooks);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("response", "sucsse");

        return cryptoMngrMaazounService.encrypt(jsonObject.toString());
    }

    @Override
    public String editBookContractCountByBookFinancialNumber(
            EditBookFinancialNumberRequest editBookFinancialNumberRequest) {

        List<MaazounBookWarehouse> newBooks = new ArrayList<MaazounBookWarehouse>(0);
        List<MaazounBookWarehouse> increasedContracts = new ArrayList<MaazounBookWarehouse>(0);

        List<MaazounBookWarehouse> eBooks = maazounBookWarehouseService
                .findByBookFinancialNumber(editBookFinancialNumberRequest.getBookFinancialNumber());

        long bookContractNumber = eBooks.get(0).getContractCount();

        if (bookContractNumber > Long.valueOf(editBookFinancialNumberRequest.getContractCount())) {

            for (MaazounBookWarehouse bookWarehouse : eBooks) {

                String contractIndex = bookWarehouse.getContractFinancialNumber().split("-")[1];

                bookWarehouse.setContractCount(Long.valueOf(editBookFinancialNumberRequest.getContractCount()));

                if (Long.valueOf(contractIndex) <= Long.valueOf(editBookFinancialNumberRequest.getContractCount())) {
                    newBooks.add(bookWarehouse);
                } else {
                    // increasedContracts.add(bookWarehouse);
                    maazounBookWarehouseService.deleteById(bookWarehouse.getId());
                }

            }

        } else {
            for (MaazounBookWarehouse bookWarehouse : eBooks) {

                // String contractIndex =
                // bookWarehouse.getContractFinancialNumber().split("-")[1];
                bookWarehouse.setContractCount(Long.valueOf(editBookFinancialNumberRequest.getContractCount()));
                newBooks.add(bookWarehouse);
            }

            for (int i = 9; i <= Long.valueOf(editBookFinancialNumberRequest.getContractCount()); i++) {
                MaazounBookWarehouse newBookWarehouse = new MaazounBookWarehouse();

                newBookWarehouse.setBookFinancialNumber(eBooks.get(0).getBookFinancialNumber());
//				newBookWarehouse.setBookTypeId(eBooks.get(0).getBookTypeId());
//				newBookWarehouse.setBookInventoryNumber(eBooks.get(0).getBookInventoryNumber());
//				newBookWarehouse.setBookInventoryReference(eBooks.get(0).getBookInventoryReference());
                newBookWarehouse.setBookTypeId(eBooks.get(0).getBookTypeId());
                newBookWarehouse.setBookTypeName(eBooks.get(0).getBookTypeName());
                newBookWarehouse.setContractCount(Long.valueOf(editBookFinancialNumberRequest.getContractCount()));
//				newBookWarehouse.setSerialNumber(eBooks.get(0).getSerialNumber());
//				newBookWarehouse.setContractNumber(eBooks.get(0).getContractNumber());
                // newBookWarehouse.setMaazounBookCollectionInfoFk(eBooks.get(0).getMaazounBookCollectionInfoFk());
                // newBookWarehouse.setMaazounBookDeliverInfoFk(eBooks.get(0).getMaazounBookDeliverInfoFk());
                newBookWarehouse.setMaazounBookRequestInfoFk(eBooks.get(0).getMaazounBookRequestInfoFk());
                newBookWarehouse.setMaazounBookSupplyOrderFk(eBooks.get(0).getMaazounBookSupplyOrderFk());

                newBookWarehouse.setStatusFk(StatusConstant.STATUS_SOLD);
                newBookWarehouse.setContractFinancialNumber(eBooks.get(0).getBookFinancialNumber() + "-" + i);

                increasedContracts.add(newBookWarehouse);
            }

        }
        maazounBookWarehouseService.save(increasedContracts);

        maazounBookWarehouseService.save(newBooks);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("response", "sucsse");

        return cryptoMngrMaazounService.encrypt(jsonObject.toString());
    }

    @Override
    public String reviewSupplyOrderDetails(CustomUserDetails customUserDetails,
                                           ReviewRequest reviewSupplyOrderRequest) {

        SupplyOrder eSupplyOrder = supplyOrderService.findById(Long.valueOf(reviewSupplyOrderRequest.getId())).get();

        if (reviewSupplyOrderRequest.getStatus().equalsIgnoreCase(StatusConstant.STATUS_APPROVED)) {
            eSupplyOrder.setStatusFk(StatusConstant.STATUS_APPROVED);
        } else {
            eSupplyOrder.setStatusFk(StatusConstant.STATUS_REJECTED);
        }

        eSupplyOrder.setIsReviewed(true);
        eSupplyOrder.setUpdatedAt(Date.from(Instant.now()));
        eSupplyOrder.setUpdatedBy(customUserDetails.getUsername());
        eSupplyOrder.setComment(reviewSupplyOrderRequest.getComment());

        eSupplyOrder = supplyOrderService.save(eSupplyOrder);

        SupplyOrderDTO eSupplyOrderDTO = maazounBookSupplyOrderMapper.supplyOrderToSupplyOrderDTO(eSupplyOrder);

        return cryptoMngrMaazounService.encrypt(eSupplyOrderDTO.toString());
    }

    private boolean hasRoleForSupplyOrderRetrieval(CustomUserDetails customUserDetails) {
        String userRoleName = customUserDetails.getRoleFk().getName();
        Set<String> allowedRoles = new HashSet<>(
                Arrays.asList(StatusConstant.ROLE_AGENT, StatusConstant.ROLE_AGENT_SUPERVISOR,
                        StatusConstant.ROLE_AREA_MANAGER, StatusConstant.ROLE_SUPERVISOR));
        return allowedRoles.contains(userRoleName);
    }

    private Page<SupplyOrder> retrieveSupplyOrders(SupplyOrderRequest supplyOrderRequest,
                                                   CustomUserDetails customUserDetails, Pageable pageable) {

        if (hasRoleForSupplyOrderRetrieval(customUserDetails)) {
            Set<Pos> posSet = new HashSet<>(customUserDetails.getPosSet());
            return supplyOrderService.findByStatusFkAndPosIn(supplyOrderRequest.getStatus(), posSet, pageable,
                    Util.convertStringToEndDateFormatSql(supplyOrderRequest.getDurationFrom()),
                    Util.convertStringToEndDateFormatSql(supplyOrderRequest.getDurationTo()));

        } else {
            return supplyOrderService.findByCreatedAtAfterAndCreatedAtBeforeAndStatusFk(
                    Util.convertStringToEndDateFormatSql(supplyOrderRequest.getDurationFrom()),
                    Util.convertStringToEndDateFormatSql(supplyOrderRequest.getDurationTo()),
                    supplyOrderRequest.getStatus(), pageable);
        }

    }

    private Page<MaazounBookSupplyOrder> retriveMaazounBookSypplyOrder(SupplyOrderRequest supplyOrderRequest,
                                                                       CustomUserDetails customUserDetails, Pageable pageable) {

        if (hasRoleForSupplyOrderRetrieval(customUserDetails)) {
            Set<Pos> posSet = new HashSet<>(customUserDetails.getPosSet());
            return maazounBookSupplyOrderService.findByStatusFkAndPosIn(supplyOrderRequest.getStatus(), posSet,
                    pageable, Util.convertStringToEndDateFormatSql(supplyOrderRequest.getDurationFrom()),
                    Util.convertStringToEndDateFormatSql(supplyOrderRequest.getDurationTo()));

        } else {
            return maazounBookSupplyOrderService.findByCreatedAtAfterAndCreatedAtBeforeAndStatusFk(
                    Util.convertStringToEndDateFormatSql(supplyOrderRequest.getDurationFrom()),
                    Util.convertStringToEndDateFormatSql(supplyOrderRequest.getDurationTo()),
                    supplyOrderRequest.getStatus(), pageable);
        }
    }

    private Sector getSectorById(Set<Sector> sectors, Long sectorId) {
        return sectors.stream().filter(sector -> sector.getId().equals(sectorId)).findFirst().orElse(null);
    }

    private void updateRemainingBookTypeCountInMainSupplyOrderDetails(MaazounBookSupplyOrder eMaazounBookSupplyOrder) {
        if (eMaazounBookSupplyOrder.getRefSupplyOrderNumber() != null) {

            /*
             * duple check on validatin count naumber remaining
             */
            validationRemainingCount(eMaazounBookSupplyOrder);

            Set<Long> maazounBookWarehouseSet = eMaazounBookSupplyOrder.getMaazounBookWarehouseSet()
                    .stream()
                    .map(MaazounBookWarehouse::getBookTypeId)
                    .collect(Collectors.toSet());

            SupplyOrder eSupplyOrder = supplyOrderService
                    .findByRefSupplyOrderNumber(eMaazounBookSupplyOrder.getRefSupplyOrderNumber()).orElse(null);

            for (SupplyOrderDetails supplyOrderDetailsFk : eSupplyOrder.getSupplyOrderDetailsSet()) {

                Long bookType = maazounBookWarehouseSet.stream()
                        .filter(f -> f.equals(Long.valueOf(supplyOrderDetailsFk.getBookTypeFK())))
                        .findAny().orElse(null);

                if (bookType != null) {
                    supplyOrderDetailsFk.setRemainingBookTypeCount(supplyOrderDetailsFk.getRemainingBookTypeCount() - 1);
                    supplyOrderDetailsService.save(supplyOrderDetailsFk);
                }

            }

        }

    }

    private void validationRemainingCount(MaazounBookSupplyOrder eMaazounBookSupplyOrder) {

        SupplyOrder eSupplyOrder = supplyOrderService
                .findByRefSupplyOrderNumber(eMaazounBookSupplyOrder.getRefSupplyOrderNumber()).orElse(null);

        Set<Long> maazounBookWarehouseSet = eMaazounBookSupplyOrder.getMaazounBookWarehouseSet()
                .stream()
                .map(MaazounBookWarehouse::getBookTypeId)
                .collect(Collectors.toSet());

        for (SupplyOrderDetails supplyOrderDetailsFk : eSupplyOrder.getSupplyOrderDetailsSet()) {

            Long bookType = maazounBookWarehouseSet.stream()
                    .filter(f -> f.equals(Long.valueOf(supplyOrderDetailsFk.getBookTypeFK())))
                    .findAny().orElse(null);

            if (bookType != null) {
                if (supplyOrderDetailsFk.getRemainingBookTypeCount() <= 0) {
                    throw new IllegalArgumentException("Sorry, the remaining count in this ref_supply_order_number"
                            + " with bookTypeId " + bookType + " count = " + supplyOrderDetailsFk.getRemainingBookTypeCount());
                }

                Set<String> bookList = new HashSet<String>();
                for (MaazounBookWarehouse obj : eMaazounBookSupplyOrder.getMaazounBookWarehouseSet()) {
                    if (String.valueOf(bookType).equals(String.valueOf(obj.getBookTypeId()))) {
                        bookList.add(obj.getSerialNumber());
                    }

                }
                if (bookList.size() > supplyOrderDetailsFk.getRemainingBookTypeCount()) {
                    throw new IllegalArgumentException("Sorry, the remaining count in this ref_supply_order_number"
                            + " with bookTypeId " + bookType + " count = " +
                            supplyOrderDetailsFk.getRemainingBookTypeCount() + " < coding book count = " + bookList.size());
                }

            }

        }
    }

    private void validateTheRemainingCountInSupplyOrderDetails(AddBookSupplyOrder addBookSupplyOrder, SupplyOrder eSupplyOrder) {

        for (BookListRequest book : addBookSupplyOrder.getBookList()) {
            long bookTypeId = book.getTypeId();

            SupplyOrderDetails eSupplyOrderDetails = eSupplyOrder.getSupplyOrderDetailsSet().stream()
                    .filter(p -> p.getBookTypeFK().equals(String.valueOf(bookTypeId))).findAny().orElse(null);

            if (eSupplyOrderDetails == null) {
                throw new IllegalArgumentException("Sorry, not exist bookType Id = " + bookTypeId + " in this ref_supply_order_number");
            }

            long remainingCount = eSupplyOrderDetails.getRemainingBookTypeCount();

            List<BookListRequest> bookList = addBookSupplyOrder.getBookList().stream()
                    .filter(x -> x.getTypeId() == bookTypeId).collect(Collectors.toList());

            if (bookList.size() > remainingCount) {
                throw new IllegalArgumentException("Sorry, the coding request for bookType Id = " + bookTypeId + " "
                        + "more than exist in the remaining count in ref_supply_order_number");
            }
        }

    }

}

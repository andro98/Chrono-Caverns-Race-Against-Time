package com.aman.payment.maazoun.management;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.imageio.ImageIO;

import com.aman.payment.core.exception.BadRequestException;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.aman.payment.auth.model.Pos;
import com.aman.payment.auth.model.Sector;
import com.aman.payment.auth.model.SubService;
import com.aman.payment.auth.model.SubServiceQuota;
import com.aman.payment.core.exception.ResourceAlreadyInUseException;
import com.aman.payment.core.exception.ResourceNotFoundException;
import com.aman.payment.core.exception.TransactionException;
import com.aman.payment.core.model.TransactionECR;
import com.aman.payment.core.model.audit.TransactionECRAudit;
import com.aman.payment.core.model.dto.TransactionMidsDTO;
import com.aman.payment.core.model.payload.TransactionItemsRequest;
import com.aman.payment.core.model.payload.TransactionPaymentRequest;
import com.aman.payment.core.model.payload.TransactionRefundRequest;
import com.aman.payment.core.util.UtilCore;
import com.aman.payment.maazoun.model.MaazounBookCollectionInfo;
import com.aman.payment.maazoun.model.MaazounBookDeliverInfo;
import com.aman.payment.maazoun.model.MaazounBookRequestInfo;
import com.aman.payment.maazoun.model.MaazounBookStockLabel;
import com.aman.payment.maazoun.model.MaazounBookSupplyOrder;
import com.aman.payment.maazoun.model.MaazounBookValidation;
import com.aman.payment.maazoun.model.MaazounBookWarehouse;
import com.aman.payment.maazoun.model.MaazounProfile;
import com.aman.payment.maazoun.model.dto.MaazounCollectInfoDTO;
import com.aman.payment.maazoun.model.dto.MaazounCollectInfoReportDTO;
import com.aman.payment.maazoun.model.dto.MaazounDeliverInfoReportDTO;
import com.aman.payment.maazoun.model.dto.MaazounReceivedReportDTO;
import com.aman.payment.maazoun.model.dto.MaazounRequestInfoReportDTO;
import com.aman.payment.maazoun.model.payload.AddContractListRequest;
import com.aman.payment.maazoun.model.payload.AddMaazounContractRequest;
import com.aman.payment.maazoun.model.payload.AddMaazounCustodyContractRequest;
import com.aman.payment.maazoun.model.payload.AddReceivedMaazounBookRequest;
import com.aman.payment.maazoun.model.payload.AddReceivedMaazounContractRequest;
import com.aman.payment.maazoun.model.payload.BookListRequest;
import com.aman.payment.maazoun.model.payload.ContractChecklistValidationRequest;
import com.aman.payment.maazoun.model.payload.ContractDeliverRequest;
import com.aman.payment.maazoun.model.payload.ContractListRequest;
import com.aman.payment.maazoun.model.payload.EditMaazounContractRequest;
import com.aman.payment.maazoun.service.MaazounBookStockLabelService;
import com.aman.payment.maazoun.service.MaazounCheckListService;
import com.aman.payment.maazoun.service.MaazounProfileService;
import com.aman.payment.util.StatusConstant;
import com.aman.payment.util.Util;
import com.lowagie.text.pdf.codec.Base64;

import freemarker.template.utility.NullArgumentException;

public abstract class ValidationAndPopulateManagement {

    @Autowired
    protected MaazounProfileService maazounProfileService;
    @Autowired
    private MaazounBookStockLabelService maazounBookStockLabelService;
    @Autowired
    private MaazounCheckListService maazounCheckListService;

    final static Logger logger = Logger.getLogger("maazoun");

    public MaazounProfile maazounMaazouniaValidation(Long maazounId, Long sectorId) {

        Optional<MaazounProfile> maazounOptional = maazounProfileService.findById(maazounId);
        if (!maazounOptional.isPresent()) {
            throw new IllegalArgumentException(
                    "نأسف هذا المأذون/الموثق غير مسجل بالتطبيق يرجوه تسجيله من خلال الدعم الفنى");
        }

        MaazounProfile maazounProfile = maazounOptional.get();

        if (!maazounProfile.getSectors().stream().anyMatch(s -> s.getId().equals(sectorId))) {
            throw new IllegalArgumentException(
                    "نأسف هذا العقد مرتبط بمأذونية/كنيسة أخري غير مفعله مع هذا المأذون/الموثق. برجاء المراجعة مع الدعم الفنى");
        }

        return maazounProfile;
    }

    public MaazounProfile getMaazounProfile(Long maazounId) {

        Optional<MaazounProfile> maazounOptional = maazounProfileService.findById(maazounId);
        MaazounProfile maazounProfile = null;
        if (maazounOptional.isPresent()) {
            maazounProfile = maazounOptional.get();
        } else {
            throw new ResourceNotFoundException("Sorry, This Maazoun not registered");
        }
        return maazounProfile;
    }

    /*
     * remove stock label validation by assem
     */
    public void supplyOrderBookSerialIsValidation(String bookSerialNumber, Sector sector) {

        String serialLocation = bookSerialNumber.substring(0, 3);
        if (!serialLocation.equals(String.format("%03d", sector.getLocationFk().getId()))) {
            throw new ResourceNotFoundException("Sorry, This Maazoun Book Serial Number " + bookSerialNumber
                    + " not applicable with selected sector");
        }

        Optional<MaazounBookStockLabel> MaazounBookStockLabelOptional = maazounBookStockLabelService
                .findByLabelCodeAndStatusFk(bookSerialNumber, StatusConstant.STATUS_PRINTED);
        if (!MaazounBookStockLabelOptional.isPresent()) {
            throw new ResourceNotFoundException(
                    "Sorry, This Maazoun Book Serial Number " + bookSerialNumber + " not exist in the Label Stock");
        }
    }

    /*
     * remove stock label validation by assem
     */
    public void createRequestBookSerialIsValidation(String bookSerialNumber, Sector sector) {
        validateSerialLocation(bookSerialNumber, sector);
        validateBookSerialNumberExistence(bookSerialNumber);
    }

    private void validateSerialLocation(String bookSerialNumber, Sector sector) {
        String serialLocation = bookSerialNumber.substring(0, 3);
        String expectedSerialLocation = String.format("%03d", sector.getLocationFk().getId());

        if (!serialLocation.equals(expectedSerialLocation)) {
            throw new ResourceNotFoundException("Sorry, This Maazoun Book Serial Number " + bookSerialNumber
                    + " is not applicable with the selected sector");
        }
    }

    private void validateBookSerialNumberExistence(String bookSerialNumber) {
        Optional<MaazounBookStockLabel> MaazounBookStockLabelOptional = maazounBookStockLabelService
                .findByLabelCodeAndStatusFk(bookSerialNumber, StatusConstant.STATUS_PRINT_OUT);

        if (!MaazounBookStockLabelOptional.isPresent()) {
            throw new ResourceNotFoundException("Sorry, This Maazoun Book Serial Number " + bookSerialNumber
                    + " does not exist in the Label Stock");
        }
    }

    public MaazounBookRequestInfo populateMaazounBookRequestInfo(Date createdAt, String username,
                                                                 MaazounProfile maazounProfileFk, Pos pos, String transactionCode, String refRequestNumber,
                                                                 String bookSerialNumber, Double fees, String receiptUrl, String paymentMethod, String paymentCode,
                                                                 String visaNumber, String status, Long bookTypeId, Sector sector, String bootTypeName, Long maazouniaId,
                                                                 String maazouniaName, Integer paymentProcessFlag, String refundAttachUrl) {

        MaazounBookRequestInfo maazounRequestInfo = new MaazounBookRequestInfo();

        maazounRequestInfo.setCreatedAt(createdAt);
        maazounRequestInfo.setCreatedBy(username);
        maazounRequestInfo.setMaazounProfileFk(maazounProfileFk);
        maazounRequestInfo.setPosFk(pos);
        maazounRequestInfo.setLocationId(sector.getLocationFk().getId());
        maazounRequestInfo.setStatusFk(status);
        maazounRequestInfo.setTransactionCode(transactionCode);
        maazounRequestInfo.setRefRequestNumber(refRequestNumber);
        maazounRequestInfo.setBookSerialNumber(bookSerialNumber);
        maazounRequestInfo.setAmount(fees);
        maazounRequestInfo.setReceiptUrl(receiptUrl);
        maazounRequestInfo.setPaymentMethod("2");
        maazounRequestInfo.setPaymentCode(paymentCode);
        maazounRequestInfo.setVisaNumber(visaNumber);
        maazounRequestInfo.setSectorFk(sector);
        maazounRequestInfo.setBookTypeId(bookTypeId);
        maazounRequestInfo.setBookTypeName(bootTypeName);
        maazounRequestInfo.setMaazouniaId(maazouniaId);
        maazounRequestInfo.setMaazouniaName(maazouniaName);
        maazounRequestInfo.setPaymentProcessFlag(paymentProcessFlag);
        maazounRequestInfo.setRefundAttachUrl(refundAttachUrl);

        return maazounRequestInfo;
    }

    public List<MaazounRequestInfoReportDTO> populateMaazounRequestInfoReport(
            List<MaazounBookRequestInfo> eMaazounBookRequestInfo, Double amount, List<BookListRequest> bookListRequest,
            Date createdAt, String createdBy, Pos posFk, String amanLogoUrl, String prosecutorLogoUrl, Sector sector) {

        List<MaazounRequestInfoReportDTO> maazounRequestInfoReportDTOs = new ArrayList<MaazounRequestInfoReportDTO>();
        int i = 1;
        for (BookListRequest bookListRequestObj : bookListRequest) {

            MaazounBookRequestInfo maazounRequestInfo = eMaazounBookRequestInfo.stream()
                    .filter(s -> bookListRequestObj.getSerialNumber().equals(s.getBookSerialNumber())).findAny()
                    .orElse(null);

            MaazounRequestInfoReportDTO eMaazounRequestInfoReportDTO = new MaazounRequestInfoReportDTO();
            eMaazounRequestInfoReportDTO.setBookTypeName(bookListRequestObj.getType());
            eMaazounRequestInfoReportDTO.setCreateAt(Util.dateFormat(createdAt));
            eMaazounRequestInfoReportDTO.setCreateBy(createdBy);

            eMaazounRequestInfoReportDTO.setLocationName(sector.getLocationFk().getName());
            eMaazounRequestInfoReportDTO.setSectorName(sector.getName());
            eMaazounRequestInfoReportDTO.setMaazounCardNumber(maazounRequestInfo.getMaazounProfileFk().getCardNumber());
            eMaazounRequestInfoReportDTO.setMaazounName(maazounRequestInfo.getMaazounProfileFk().getName());
            eMaazounRequestInfoReportDTO.setMaazounNationalId(maazounRequestInfo.getMaazounProfileFk().getNationalId());
            eMaazounRequestInfoReportDTO.setPosName(posFk.getName());
            eMaazounRequestInfoReportDTO.setTransactionCode(maazounRequestInfo.getTransactionCode());
            eMaazounRequestInfoReportDTO.setBookSerialNumber(bookListRequestObj.getSerialNumber());
            eMaazounRequestInfoReportDTO.setBookFinancialNumber(bookListRequestObj.getBookFinancialNumber());
            eMaazounRequestInfoReportDTO.setContractCount(String.valueOf(bookListRequestObj.getContractCount()));
            eMaazounRequestInfoReportDTO.setAmanLogoUrl(amanLogoUrl);
            eMaazounRequestInfoReportDTO.setProsecutorLogoUrl(prosecutorLogoUrl);
            eMaazounRequestInfoReportDTO.setSequance(String.valueOf(i));
            eMaazounRequestInfoReportDTO.setRefRequestNumber(maazounRequestInfo.getRefRequestNumber());

            eMaazounRequestInfoReportDTO.setQuotaBookDarebtDamgha(bookListRequestObj.getQuotaBookDarebtDamgha());
            eMaazounRequestInfoReportDTO.setQuotaBookFahsFany(bookListRequestObj.getQuotaBookFahsFany());
            eMaazounRequestInfoReportDTO.setQuotaBookRasmTanmya(bookListRequestObj.getQuotaBookRasmTanmya());
            eMaazounRequestInfoReportDTO.setQuotaBookTahweelRakmy(
                    bookListRequestObj.getQuotaBookTahweelRakmy() + bookListRequestObj.getQuotaBookDarebtMabeat());
            eMaazounRequestInfoReportDTO.setQuotaBookTaklefa(bookListRequestObj.getQuotaBookTaklefa());
            eMaazounRequestInfoReportDTO.setQuotaBookDarebtMabeat(bookListRequestObj.getQuotaBookDarebtMabeat());
            eMaazounRequestInfoReportDTO.setFees(bookListRequestObj.getFees());

            eMaazounRequestInfoReportDTO.setAmount(amount);
            eMaazounRequestInfoReportDTO.setMaazouniaName(bookListRequestObj.getMaazouniaChurchNameType());

            maazounRequestInfoReportDTOs.add(eMaazounRequestInfoReportDTO);
            i++;
        }

        return maazounRequestInfoReportDTOs;

    }

    public String saveBrowseAttFile(MultipartFile att, String url, String fileName) throws Exception {
        Path fileStorageLocation = null;
        fileName = fileName + "_" + Date.from(Instant.now()).getTime();
        if (att != null && !att.getOriginalFilename().equals("foo.txt")) {
            try {
                fileStorageLocation = Paths.get(url + "/" + Util.dateFormat() + "/" + fileName + "." + "png")
                        .toAbsolutePath().normalize();

                Path path = Paths.get(fileStorageLocation.toString());
                // System.gc();
                File f = new File(fileStorageLocation.toString());
                if (f.exists()) {
                    FileUtils.forceDelete(new File(fileStorageLocation.toString()));

                }
//				Files.deleteIfExists(Paths.get(fileStorageLocation.toUri()));
                Files.createDirectories(path);
                Files.copy(att.getInputStream(), Paths.get(fileStorageLocation.toUri()),
                        StandardCopyOption.REPLACE_EXISTING);

            } catch (Exception ex) {
                ex.printStackTrace();
                logger.error("saveAttFile An exception when saveAttachedFile! ", ex);
                throw new TransactionException("saveAttFile An exception when saveAttachedFile with! " + url);
            }

        }
        return (fileStorageLocation != null) ? fileStorageLocation.toString() : null;

    }

    public String saveImageAttFile(String imge, String url, String fileName) throws IOException {
        BufferedImage image = null;
        byte[] imageByte;
        File outputfile = null;
        Path fileStorageLocation = null;
        try {

            imageByte = Base64.decode(imge);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
            // finalBasePackagePath+File.pathSeparator+File.pathSeparator+userName+File.pathSeparator+nationalId
            fileName = fileName + "_" + Date.from(Instant.now()).getTime();

            fileStorageLocation = Paths.get(url + "/" + Util.dateFormat() + "/" + fileName + "." + "png")
                    .toAbsolutePath().normalize();

            // fileStorageLocation = Paths.get(url + fileName +
            // "."+"png").toAbsolutePath().normalize();
            Files.deleteIfExists(Paths.get(fileStorageLocation.toUri()));
            Files.createDirectories(fileStorageLocation);
            // write the image to a file
            outputfile = new File(fileStorageLocation.toString());
            ImageIO.write(image, "png", outputfile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return fileStorageLocation.toString();
    }

    public MaazounBookSupplyOrder populateMaazounBookSupplyOrder(Pos pos, String username, Date createdAt,
                                                                 MultipartFile attachedFile, Boolean custody, String supplyOrderPathAtt, String refSupplyOrderNumber,
                                                                 Sector sector) {

        MaazounBookSupplyOrder maazounBookSupplyOrder = new MaazounBookSupplyOrder();
        maazounBookSupplyOrder.setPosFk(pos);
        maazounBookSupplyOrder.setLocationId(sector.getLocationFk().getId());
        maazounBookSupplyOrder.setSectorFk(sector);
        maazounBookSupplyOrder.setCreatedAt(createdAt);
        maazounBookSupplyOrder.setCreatedBy(username);
        maazounBookSupplyOrder.setStatusFk(StatusConstant.STATUS_PENDING);
        maazounBookSupplyOrder.setIsReviewed(false);
        maazounBookSupplyOrder.setIsCustody(custody);
        maazounBookSupplyOrder.setRefSupplyOrderNumber(refSupplyOrderNumber);

//		try {
//			String imageUrl = saveBrowseAttFile(attachedFile, supplyOrderPathAtt, refSupplyOrderNumber);
//			maazounBookSupplyOrder.setImageUrl(imageUrl);
//		} catch (Exception e) {
//			throw new NullPointerException("imge must not be null");
//			//e.printStackTrace();
//		}

        return maazounBookSupplyOrder;
    }

    public List<MaazounBookWarehouse> populateAndValidateBatchMaazounWarehouseWithStockLabel(Pos pos, String username,
                                                                                             Date updatedAt, List<BookListRequest> books, MaazounBookSupplyOrder maazounBookSupplyOrderFk, String locationId)
            throws Exception {

        List<MaazounBookWarehouse> batchBooks = new ArrayList<MaazounBookWarehouse>();

        for (BookListRequest book : books) {

//			if(!book.getSerialNumber().substring(book.getSerialNumber().length() - 2).equals(String.format("%02d", book.getTypeId()))) {
//					throw new ResourceAlreadyInUseException("barcode label selected not related with the book type", "",
//							"please select correct barcode label");
//			}

            Optional<MaazounBookStockLabel> bookLabelCodeOptional = maazounBookStockLabelService
                    .findByLabelCode(book.getSerialNumber());
            if (bookLabelCodeOptional.isPresent()) {

                if (!bookLabelCodeOptional.get().getStatusFk().equals(StatusConstant.STATUS_PRINTED)) {
                    throw new BadRequestException("Maazoun book serial number status is not Printed");
                }

                if (!bookLabelCodeOptional.get().getLabelCode().substring(0, 3).equals(locationId)) {
                    throw new BadRequestException("This Book Serial Number " + book.getSerialNumber() + " is not applicable with the selected sector");
                }

                MaazounBookStockLabel bookLabelCode = bookLabelCodeOptional.get();
                bookLabelCode.setStatusFk(StatusConstant.STATUS_PRINT_OUT);
                bookLabelCode.setUpdatedAt(updatedAt);
                bookLabelCode.setUpdatedBy(username);
                maazounBookStockLabelService.save(bookLabelCode);

                for (AddContractListRequest contract : book.getContracts()) {

//					if(!contract.getContractSerialNumber().substring(contract.getContractSerialNumber().length() - 2).equals(String.format("%02d", book.getTypeId()))) {
//						throw new ResourceAlreadyInUseException("barcode label selected not related with the contract type", "",
//								"please select correct barcode label");
//					}

                    Optional<MaazounBookStockLabel> contractLabelCodeOptional = maazounBookStockLabelService
                            .findByLabelCode(contract.getContractSerialNumber());
                    if (contractLabelCodeOptional.isPresent()) {

                        if (!contractLabelCodeOptional.get().getStatusFk().equals(StatusConstant.STATUS_PRINTED)) {
                            throw new BadRequestException("Maazoun book serial number status is not Printed");
                        }

                        if (!contractLabelCodeOptional.get().getLabelCode().substring(0, 3).equals(locationId)) {
                            throw new BadRequestException("This Contract Serial Number " + book.getSerialNumber() + " is not applicable with the selected sector");
                        }

                        if (book.getSerialNumber() == null || book.getSerialNumber().isEmpty()
                                || contract.getContractSerialNumber() == null
                                || contract.getContractSerialNumber().isEmpty() || book.getBookFinancialNumber() == null
                                || book.getBookFinancialNumber().isEmpty()
                                || contract.getContractFinancialNumber() == null
                                || contract.getContractFinancialNumber().isEmpty()) {

                            throw new ResourceAlreadyInUseException("This Book Missing Serial Number...", "",
                                    contract.getContractSerialNumber());
                        } else {

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
                            maazounBookStockLabelService.findByLabelCode(book.getSerialNumber()).ifPresent(x -> {
                                obj.setBookTierId(x.getBookTierId());
                            });
                            batchBooks.add(obj);

                            MaazounBookStockLabel contractLabelCode = contractLabelCodeOptional.get();
                            contractLabelCode.setStatusFk(StatusConstant.STATUS_PRINT_OUT);
                            contractLabelCode.setUpdatedAt(updatedAt);
                            contractLabelCode.setUpdatedBy(username);
                            maazounBookStockLabelService.save(contractLabelCode);

                        }

                    } else {
                        throw new ResourceAlreadyInUseException("Maazoun contract serial number not exist", "",
                                contract.getContractSerialNumber());
                    }

                }

            } else {
                throw new ResourceAlreadyInUseException("Maazoun book serial number not exist in warehouse", "",
                        book.getSerialNumber());
            }

        }

        return batchBooks;
    }

    public double reCalculateZawagContractFees(SubService subService, Long typeId, double feeAmount,
                                               double contractPaidAmount, double contractPaidBetweenusFees) {

        if (String.valueOf(typeId).equals(StatusConstant.CONTRACT_ZAWAG_MUSLIM_ID)
                || String.valueOf(typeId).equals(StatusConstant.CONTRACT_TASADOK_ID)
                || String.valueOf(typeId).equals(StatusConstant.CONTRACT_ZAWAG_MELALY_ID)) {

//			if(contractPaidBetweenusFees > 0 && !String.valueOf(typeId).equals(StatusConstant.CONTRACT_ZAWAG_MELALY_ID))
//				return feeAmount + contractPaidAmount;

            double subtractBetweenusFromContractPaidAmount = contractPaidAmount - contractPaidBetweenusFees;

            SubServiceQuota subServiceQuotaFirstThousand = subService.getSubServicesQuota().stream()
                    .filter(x -> x.getName().equalsIgnoreCase(StatusConstant.CONTRACT_QUOTA_PERCENTAGE_FIRST_THOUSAND))
                    .findAny().orElse(null);

            SubServiceQuota subServiceQuotaRemainingAmount = subService.getSubServicesQuota().stream().filter(
                            x -> x.getName().equalsIgnoreCase(StatusConstant.CONTRACT_QUOTA_PERCENTAGE_REMAINING_AMOUNT))
                    .findAny().orElse(null);

            // double contractFees = Double.valueOf(contractPaidAmount);
            Double perecntFirstThousand = Util.calculateFirstThousandAndRemainingContractPaid(
                    subtractBetweenusFromContractPaidAmount, subServiceQuotaFirstThousand.getFees());

            Double perecntRemainingAmount = Util.calculateSecondRemainingContractPaid(
                    subtractBetweenusFromContractPaidAmount, subServiceQuotaRemainingAmount.getFees());

            return feeAmount + (perecntFirstThousand + perecntRemainingAmount + contractPaidBetweenusFees);

        }

        return feeAmount;

    }

    public TransactionPaymentRequest populateTransactionPaymentRequest(String subServiceId, String paymentCode,
                                                                       String visaNumber, Pos pos, String username, double feeAmount, long serviceId,
                                                                       TransactionECR transactionECRFk, double contractPaidAmount, long contractCount,
                                                                       Set<TransactionMidsDTO> transactionMids, Sector sector) {

        Set<TransactionItemsRequest> transactionItemsCoreRequests = new HashSet<TransactionItemsRequest>();
        transactionItemsCoreRequests.add(new TransactionItemsRequest(subServiceId, String.valueOf(feeAmount)));

        TransactionPaymentRequest transactionPaymentRequest = new TransactionPaymentRequest();

        transactionPaymentRequest.setAmount(String.format("%.2f", feeAmount));
        transactionPaymentRequest.setLocationCode(sector.getLocationFk().getCode());
        transactionPaymentRequest.setMerchantId("3");
        transactionPaymentRequest.setPaymentTypeId("2");
        transactionPaymentRequest.setPosId(String.valueOf(pos.getId()));
        transactionPaymentRequest.setServiceId(String.valueOf(serviceId));
        transactionPaymentRequest.setSubServiceIds(transactionItemsCoreRequests);
        transactionPaymentRequest.setUserName(username);
        transactionPaymentRequest.setLocationName(sector.getName() + "-" + sector.getLocationFk().getName() + "-"
                + sector.getLocationFk().getCityFk().getNameAr());
        transactionPaymentRequest.setLocationCode(String.valueOf(sector.getLocationFk().getId()));
        transactionPaymentRequest.setPaymentCode(paymentCode);
        transactionPaymentRequest.setVisaNumber(visaNumber);
        transactionPaymentRequest.setTransactionECRFk(transactionECRFk);
        transactionPaymentRequest.setContractPaidAmount(contractPaidAmount);
        transactionPaymentRequest.setContractCount(contractCount);
        transactionPaymentRequest.setTransactionMids(transactionMids);
        return transactionPaymentRequest;
    }

    public MaazounBookCollectionInfo populateMaazounCollectionInfo(String transactionCode, double feeAmount,
                                                                   AddMaazounContractRequest addMaazounContractRequest, String username, Date createdAt, Pos pos,
                                                                   String subServiceName, MaazounProfile maazounProfile, String url, Sector sector) {

        MaazounBookCollectionInfo eMaazounBookCollectionInfo = new MaazounBookCollectionInfo();
        eMaazounBookCollectionInfo.setBookSerialNumber(addMaazounContractRequest.getBookSerialNumber());
        eMaazounBookCollectionInfo.setContractDate(
                UtilCore.convertStringToStartDateFormatSql(addMaazounContractRequest.getContractDate()));
        eMaazounBookCollectionInfo.setContractNumber(addMaazounContractRequest.getContractNumber());
        eMaazounBookCollectionInfo.setContractPaidAmount(addMaazounContractRequest.getContractPaidAmount());

        if (addMaazounContractRequest.getContractPaidBetweenusFees() > 0) {
            eMaazounBookCollectionInfo
                    .setContractPaidBetweenusFees(addMaazounContractRequest.getContractPaidBetweenusFees());
            eMaazounBookCollectionInfo.setContractPaidAdvanceFees((double) 0);
            eMaazounBookCollectionInfo.setContractPaidDelayedFees((double) 0);

        } else {
            eMaazounBookCollectionInfo.setContractPaidBetweenusFees((double) 0);
            eMaazounBookCollectionInfo
                    .setContractPaidAdvanceFees(addMaazounContractRequest.getContractPaidAdvanceFees());
            eMaazounBookCollectionInfo
                    .setContractPaidDelayedFees(addMaazounContractRequest.getContractPaidDelayedFees());
        }

        eMaazounBookCollectionInfo
                .setContractPaidConditionsFees(addMaazounContractRequest.getContractPaidConditionsFees());

        eMaazounBookCollectionInfo.setCreatedAt(createdAt);
        eMaazounBookCollectionInfo.setCreatedBy(username);
        eMaazounBookCollectionInfo.setFeeAmount(Double.valueOf(String.format("%.2f", feeAmount)));
        eMaazounBookCollectionInfo.setHusbandName(addMaazounContractRequest.getHusbandName());
        eMaazounBookCollectionInfo.setPosFk(pos);
        eMaazounBookCollectionInfo.setLocationId(sector.getLocationFk().getId());
        eMaazounBookCollectionInfo.setSectorFk(sector);
        eMaazounBookCollectionInfo.setPaymentMethod(StatusConstant.PAYMENT_VISA);
        eMaazounBookCollectionInfo.setContractTypeId(addMaazounContractRequest.getSubServiceId());
        eMaazounBookCollectionInfo.setContractTypeName(subServiceName);

        eMaazounBookCollectionInfo.setIsReviewed(false);
        eMaazounBookCollectionInfo.setMaazounProfileFk(maazounProfile);
        eMaazounBookCollectionInfo.setMaazounNationalId(maazounProfile.getNationalId());

        if (String.valueOf(addMaazounContractRequest.getSubServiceId()).equals(StatusConstant.CONTRACT_TALAK_ID)
                || String.valueOf(addMaazounContractRequest.getSubServiceId())
                .equals(StatusConstant.CONTRACT_MORAGAA_ID)) {

            eMaazounBookCollectionInfo.setStatusFk(StatusConstant.STATUS_REVIEWED);
        } else {
            eMaazounBookCollectionInfo.setStatusFk(StatusConstant.STATUS_UNDER_REVIEWED);
        }

        eMaazounBookCollectionInfo.setTransactionCode(transactionCode);
        eMaazounBookCollectionInfo.setWifeName(addMaazounContractRequest.getWifeName());
        eMaazounBookCollectionInfo.setWifeNationalId(addMaazounContractRequest.getWifeNationalId());
        eMaazounBookCollectionInfo.setHusbandNationalId(addMaazounContractRequest.getHusbandNationalId());
        eMaazounBookCollectionInfo
                .setWifeNationalIdExpiryDate(addMaazounContractRequest.getWifeNationalIdExpiryDate() != null ? UtilCore
                        .convertStringToStartDateFormatSql(addMaazounContractRequest.getWifeNationalIdExpiryDate())
                        : null);
        eMaazounBookCollectionInfo.setHusbandNationalIdExpiryDate(
                addMaazounContractRequest.getHusbandNationalIdExpiryDate() != null ? UtilCore
                        .convertStringToStartDateFormatSql(addMaazounContractRequest.getHusbandNationalIdExpiryDate())
                        : null);
        eMaazounBookCollectionInfo.setTalakCount(addMaazounContractRequest.getTalakCount());
        eMaazounBookCollectionInfo.setTalakStatus(addMaazounContractRequest.getTalakStatus());
        eMaazounBookCollectionInfo.setTalakType(addMaazounContractRequest.getTalakType());
        eMaazounBookCollectionInfo.setWifeRepresentedStatus(addMaazounContractRequest.getWifeRepresentedStatus());

        try {

            if (addMaazounContractRequest.getAttContract() != null
                    && !addMaazounContractRequest.getAttContract().getOriginalFilename().equals("foo.txt")) {
                String imageUrl = saveBrowseAttFile(addMaazounContractRequest.getAttContract(), url,
                        "Contract_" + addMaazounContractRequest.getContractNumber());
                eMaazounBookCollectionInfo.setImageUrl(imageUrl);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new NullArgumentException("Image URL Must Not Be Null");
        }

        Set<MaazounBookValidation> maazounBookValidationSet = new HashSet<MaazounBookValidation>(0);
        for (ContractChecklistValidationRequest validationObj : addMaazounContractRequest
                .getContractChecklistValidationRequest()) {

            MaazounBookValidation eMaazounBookValidation = new MaazounBookValidation();
            eMaazounBookValidation.setMaazounBookCollectionInfoFk(eMaazounBookCollectionInfo);
            eMaazounBookValidation.setMaazounCheckListLookupFk(
                    maazounCheckListService.findById(Long.valueOf(validationObj.getCheckListId())).get());
            eMaazounBookValidation.setValueDesc(Boolean.valueOf(validationObj.getCheckValue()));

            maazounBookValidationSet.add(eMaazounBookValidation);
        }

        eMaazounBookCollectionInfo.setMaazounBookValidationSet(maazounBookValidationSet);

        return eMaazounBookCollectionInfo;
    }

    public MaazounBookCollectionInfo populateRefundMaazounCollectionInfo(
            MaazounBookCollectionInfo sourceMaazounBookCollectionInfo, String transactionCode, String username,
            Date createdAt, Pos pos, String visaNumber, Sector sector, String refundAttUrl, String refRequestNumber) {

        MaazounBookCollectionInfo eMaazounBookCollectionInfo = new MaazounBookCollectionInfo();

        eMaazounBookCollectionInfo.setBookSerialNumber(sourceMaazounBookCollectionInfo.getBookSerialNumber());
        eMaazounBookCollectionInfo.setContractDate(sourceMaazounBookCollectionInfo.getContractDate());
        eMaazounBookCollectionInfo.setContractNumber(sourceMaazounBookCollectionInfo.getContractNumber());
        eMaazounBookCollectionInfo.setContractPaidAmount(sourceMaazounBookCollectionInfo.getContractPaidAmount());
        eMaazounBookCollectionInfo
                .setContractPaidAdvanceFees(sourceMaazounBookCollectionInfo.getContractPaidAdvanceFees());
        eMaazounBookCollectionInfo
                .setContractPaidBetweenusFees(sourceMaazounBookCollectionInfo.getContractPaidBetweenusFees());
        eMaazounBookCollectionInfo
                .setContractPaidConditionsFees(sourceMaazounBookCollectionInfo.getContractPaidConditionsFees());
        eMaazounBookCollectionInfo
                .setContractPaidDelayedFees(sourceMaazounBookCollectionInfo.getContractPaidDelayedFees());
        eMaazounBookCollectionInfo.setCreatedAt(createdAt);
        eMaazounBookCollectionInfo.setCreatedBy(username);
        eMaazounBookCollectionInfo.setFeeAmount(sourceMaazounBookCollectionInfo.getFeeAmount());
        eMaazounBookCollectionInfo.setHusbandName(sourceMaazounBookCollectionInfo.getHusbandName());
        eMaazounBookCollectionInfo.setPosFk(pos);
        eMaazounBookCollectionInfo.setLocationId(sector.getLocationFk().getId());
        eMaazounBookCollectionInfo.setSectorFk(sector);
        eMaazounBookCollectionInfo.setPaymentMethod(StatusConstant.PAYMENT_VISA);
        eMaazounBookCollectionInfo.setContractTypeId(sourceMaazounBookCollectionInfo.getContractTypeId());
        eMaazounBookCollectionInfo.setRefundAttUrl(refundAttUrl);

        eMaazounBookCollectionInfo.setIsReviewed(false);
        eMaazounBookCollectionInfo.setMaazounProfileFk(sourceMaazounBookCollectionInfo.getMaazounProfileFk());
        eMaazounBookCollectionInfo.setMaazounNationalId(sourceMaazounBookCollectionInfo.getMaazounNationalId());
        eMaazounBookCollectionInfo.setStatusFk(StatusConstant.STATUS_REFUND);

        eMaazounBookCollectionInfo.setTransactionCode(transactionCode);
        eMaazounBookCollectionInfo.setWifeName(sourceMaazounBookCollectionInfo.getWifeName());
        eMaazounBookCollectionInfo.setWifeNationalId(sourceMaazounBookCollectionInfo.getWifeNationalId());
        eMaazounBookCollectionInfo.setHusbandNationalId(sourceMaazounBookCollectionInfo.getHusbandNationalId());
        eMaazounBookCollectionInfo
                .setWifeNationalIdExpiryDate(sourceMaazounBookCollectionInfo.getWifeNationalIdExpiryDate());
        eMaazounBookCollectionInfo
                .setHusbandNationalIdExpiryDate(sourceMaazounBookCollectionInfo.getHusbandNationalIdExpiryDate());

        eMaazounBookCollectionInfo.setVisaNumber(visaNumber);
        eMaazounBookCollectionInfo.setRefRequestNumber(refRequestNumber);

        return eMaazounBookCollectionInfo;
    }

    public MaazounBookCollectionInfo populateMaazounCustodyCollectionInfo(
            AddMaazounCustodyContractRequest addMaazounCustodyContractRequest, String username, Date createdAt, Pos pos,
            String subServiceName, MaazounProfile maazounProfile, Sector sector,
            MaazounBookWarehouse eMaazounBookWarehouse) {

        MaazounBookCollectionInfo eMaazounBookCollectionInfo = new MaazounBookCollectionInfo();
        eMaazounBookCollectionInfo.setBookSerialNumber(eMaazounBookWarehouse.getBookFinancialNumber());
        eMaazounBookCollectionInfo.setContractDate(
                UtilCore.convertStringToStartDateFormatSql(addMaazounCustodyContractRequest.getContractDate()));
        eMaazounBookCollectionInfo.setContractNumber(eMaazounBookWarehouse.getContractFinancialNumber());
        eMaazounBookCollectionInfo.setCreatedAt(createdAt);
        eMaazounBookCollectionInfo.setCreatedBy(username);
        eMaazounBookCollectionInfo.setFeeAmount((double) 0);
        eMaazounBookCollectionInfo.setPosFk(pos);
        eMaazounBookCollectionInfo.setLocationId(sector.getLocationFk().getId());
        eMaazounBookCollectionInfo.setSectorFk(sector);
        eMaazounBookCollectionInfo.setContractTypeId(addMaazounCustodyContractRequest.getSubServiceId());
        eMaazounBookCollectionInfo.setContractTypeName(subServiceName);

        eMaazounBookCollectionInfo.setMaazounProfileFk(maazounProfile);
        eMaazounBookCollectionInfo.setMaazounNationalId(maazounProfile.getNationalId());

        eMaazounBookCollectionInfo.setStatusFk(StatusConstant.STATUS_REVIEWED_CUSTODY);
        eMaazounBookCollectionInfo.setIsReviewed(false);
        eMaazounBookCollectionInfo.setReviewedAt(createdAt);
        eMaazounBookCollectionInfo.setReviewedBy(username);

        eMaazounBookCollectionInfo.setWifeName(addMaazounCustodyContractRequest.getWifeName());
        eMaazounBookCollectionInfo.setWifeNationalId(addMaazounCustodyContractRequest.getWifeNationalId());
        eMaazounBookCollectionInfo.setHusbandNationalId(addMaazounCustodyContractRequest.getHusbandNationalId());
        eMaazounBookCollectionInfo.setHusbandName(addMaazounCustodyContractRequest.getHusbandName());
        eMaazounBookCollectionInfo
                .setWifeNationalIdExpiryDate(addMaazounCustodyContractRequest.getWifeNationalIdExpiryDate() != null
                        ? UtilCore.convertStringToStartDateFormatSql(
                        addMaazounCustodyContractRequest.getWifeNationalIdExpiryDate())
                        : null);
        eMaazounBookCollectionInfo.setHusbandNationalIdExpiryDate(
                addMaazounCustodyContractRequest.getHusbandNationalIdExpiryDate() != null
                        ? UtilCore.convertStringToStartDateFormatSql(
                        addMaazounCustodyContractRequest.getHusbandNationalIdExpiryDate())
                        : null);

        return eMaazounBookCollectionInfo;
    }

    public MaazounCollectInfoReportDTO populateMaazounCollectInfoReportDTO(ContractListRequest eContractListRequest,
                                                                           String amanLogoUrl, String prosecutorLogoUrl, String totalAmount, String username, Date createdAt,
                                                                           MaazounBookCollectionInfo eMaazounBookCollectionInfo, String refRequestNumber, Pos pos, int counter,
                                                                           MaazounBookWarehouse eMaazounBookWarehouse, Sector sector, String maazouniaChurchNameType) {

        MaazounCollectInfoReportDTO eMaazounCollectInfoReportDTO = new MaazounCollectInfoReportDTO();
        eMaazounCollectInfoReportDTO.setAmanLogoUrl(amanLogoUrl);
        eMaazounCollectInfoReportDTO.setProsecutorLogoUrl(prosecutorLogoUrl);
        eMaazounCollectInfoReportDTO.setBookSerialNumber(eMaazounBookCollectionInfo.getBookSerialNumber());
        eMaazounCollectInfoReportDTO.setContractNumber(eMaazounBookCollectionInfo.getContractNumber());

//		String contractNumber = eMaazounBookCollectionInfo.getContractNumber();
//		MaazounBookWarehouse maazounBookWarehouseObj = eMaazounBookCollectionInfo.getMaazounBookWarehouseSet().stream()
//				.filter(x -> x.getContractNumber().equals(contractNumber) || x.getContractFinancialNumber().equals(contractNumber))
//				.findAny().orElse(null);
        if (eMaazounBookWarehouse.getMaazounBookSupplyOrderFk().getIsCustody()) {
            eMaazounCollectInfoReportDTO.setContractNumber("");
        }

        eMaazounCollectInfoReportDTO.setBookFinancialNumber(eMaazounBookWarehouse.getBookFinancialNumber());
        eMaazounCollectInfoReportDTO.setContractFinancialNumber(eMaazounBookWarehouse.getContractFinancialNumber());

        eMaazounCollectInfoReportDTO.setBookTypeId(String.valueOf(eContractListRequest.getTypeId()));
        eMaazounCollectInfoReportDTO.setBookTypeName(eContractListRequest.getTypeName());
        eMaazounCollectInfoReportDTO
                .setContractDate(Util.dateFormatReport(eMaazounBookCollectionInfo.getContractDate()));
        eMaazounCollectInfoReportDTO
                .setContractPaidAmount(String.valueOf(eMaazounBookCollectionInfo.getContractPaidAmount()));
        eMaazounCollectInfoReportDTO.setCreateAt(Util.dateFormat(createdAt));
        eMaazounCollectInfoReportDTO.setCreateBy(username);
        eMaazounCollectInfoReportDTO.setFeeAmount(String.valueOf(eMaazounBookCollectionInfo.getFeeAmount()));
        eMaazounCollectInfoReportDTO.setHusbandName(eMaazounBookCollectionInfo.getHusbandName());
        eMaazounCollectInfoReportDTO.setLocationName(sector.getLocationFk().getName());
        eMaazounCollectInfoReportDTO.setMaazounName(eMaazounBookCollectionInfo.getMaazounProfileFk().getName());
        eMaazounCollectInfoReportDTO
                .setMaazounNationalId(eMaazounBookCollectionInfo.getMaazounProfileFk().getNationalId());
        eMaazounCollectInfoReportDTO
                .setMaazounCardNumber(eMaazounBookCollectionInfo.getMaazounProfileFk().getCardNumber());
        eMaazounCollectInfoReportDTO.setPosName(pos.getName());
        eMaazounCollectInfoReportDTO.setSectorName(sector.getName());
        eMaazounCollectInfoReportDTO.setRefRequestNumber(refRequestNumber);
        eMaazounCollectInfoReportDTO.setSequance(String.valueOf(counter));
        eMaazounCollectInfoReportDTO.setTransactionCode(eMaazounBookCollectionInfo.getTransactionCode());
        eMaazounCollectInfoReportDTO.setWifeName(eMaazounBookCollectionInfo.getWifeName());
        eMaazounCollectInfoReportDTO.setTotalAmount(totalAmount);

        eMaazounCollectInfoReportDTO.setQuotaContractAbnytMhakm(eContractListRequest.getQuotaContractAbnytMhakm());
        eMaazounCollectInfoReportDTO
                .setQuotaContractGamiyaMazouneen(eContractListRequest.getQuotaContractGamiyaMazouneen());
        eMaazounCollectInfoReportDTO.setQuotaContractIdafy(eContractListRequest.getQuotaContractIdafy());
        eMaazounCollectInfoReportDTO.setQuotaContractMokrr(eContractListRequest.getQuotaContractMokrr());
        eMaazounCollectInfoReportDTO.setQuotaContractTameenOsra(eContractListRequest.getQuotaContractTameenOsra());
        eMaazounCollectInfoReportDTO
                .setQuotaContractPercentFirstThousand(eContractListRequest.getQuotaContractPercentFirstThousand());
        eMaazounCollectInfoReportDTO
                .setQuotaContractPercentRemainingAmount(eContractListRequest.getQuotaContractPercentRemainingAmount());

        eMaazounCollectInfoReportDTO
                .setContractPaidAdvanceFees(eMaazounBookCollectionInfo.getContractPaidAdvanceFees());
        eMaazounCollectInfoReportDTO
                .setContractPaidDelayedFees(eMaazounBookCollectionInfo.getContractPaidDelayedFees());
        eMaazounCollectInfoReportDTO
                .setContractPaidConditionsFees(eMaazounBookCollectionInfo.getContractPaidConditionsFees());

        eMaazounCollectInfoReportDTO.setMaazouniaName(maazouniaChurchNameType);

        return eMaazounCollectInfoReportDTO;
    }

    public MaazounReceivedReportDTO populateMaazounReceivedReportDTO(Pos pos, Date receivedAt, String username,
                                                                     String prosecutorLogoUrl, AddReceivedMaazounBookRequest addReceivedMaazounBookRequest,
                                                                     AddReceivedMaazounContractRequest eAddReceivedMaazounContractRequest, int counter, Sector sector,
                                                                     String maazouniaChurchNameType, String bookInventoryReference) {

        MaazounReceivedReportDTO eMaazounReceivedReportDTO = new MaazounReceivedReportDTO();
        eMaazounReceivedReportDTO.setBookFinancialNumber(addReceivedMaazounBookRequest.getBookFinancialNumber());
        eMaazounReceivedReportDTO.setBookSerialNumber(addReceivedMaazounBookRequest.getBookSerialNumber());
        eMaazounReceivedReportDTO.setBookTypeName(eAddReceivedMaazounContractRequest.getBookTypeName());
        eMaazounReceivedReportDTO.setContractNumber(eAddReceivedMaazounContractRequest.getContractNumber());
        eMaazounReceivedReportDTO
                .setContractFinancialNumber(eAddReceivedMaazounContractRequest.getContractFinancialNumber());
        eMaazounReceivedReportDTO.setLocationName(sector.getLocationFk().getName());
        eMaazounReceivedReportDTO.setMaazounName(eAddReceivedMaazounContractRequest.getMaazounName());
        eMaazounReceivedReportDTO.setMaazounNationalId(eAddReceivedMaazounContractRequest.getMaazounNationalId());
        eMaazounReceivedReportDTO.setPosName(pos.getName());
        eMaazounReceivedReportDTO.setProsecutorLogoUrl(prosecutorLogoUrl);
        eMaazounReceivedReportDTO.setReceivedAt(Util.dateFormat(receivedAt));
        eMaazounReceivedReportDTO.setReceivedBy(username);
        eMaazounReceivedReportDTO.setSectorName(sector.getName());
        eMaazounReceivedReportDTO.setStatus(eAddReceivedMaazounContractRequest.getReceivedStatus());
        eMaazounReceivedReportDTO.setSequance(String.valueOf(counter));
        eMaazounReceivedReportDTO.setMaazouniaName(maazouniaChurchNameType);
        eMaazounReceivedReportDTO.setBookInventoryReference(bookInventoryReference);

        return eMaazounReceivedReportDTO;
    }

    public MaazounBookDeliverInfo populateMaazounBookDeliverInfo(Date createdAt, String username, Pos pos,
                                                                 String receiptUrl, String status, String refRequestNumber, Sector sector) {

        MaazounBookDeliverInfo eMaazounBookDeliverInfo = new MaazounBookDeliverInfo();
        eMaazounBookDeliverInfo.setCreatedAt(createdAt);
        eMaazounBookDeliverInfo.setCreatedBy(username);
        eMaazounBookDeliverInfo.setPosFk(pos);
        eMaazounBookDeliverInfo.setSectorFk(sector);
        eMaazounBookDeliverInfo.setLocationId(sector.getLocationFk().getId());
        eMaazounBookDeliverInfo.setReceiptUrl(receiptUrl);
        eMaazounBookDeliverInfo.setStatusFk(StatusConstant.STATUS_PENDING);
        eMaazounBookDeliverInfo.setRefRequestNumber(refRequestNumber);
        eMaazounBookDeliverInfo.setIsReviewed(false);
        return eMaazounBookDeliverInfo;

    }

    public MaazounDeliverInfoReportDTO populateMaazounDeliverInfoReportDTO(String prosecutorLogoUrl,
                                                                           ContractDeliverRequest eContractDeliverRequest, Pos pos, String refRequestNumber, Date createdAt,
                                                                           String username, int sequance, Sector sector) {

        MaazounDeliverInfoReportDTO eMaazounCollectInfoReportDTO = new MaazounDeliverInfoReportDTO();
        eMaazounCollectInfoReportDTO.setProsecutorLogoUrl(prosecutorLogoUrl);
        eMaazounCollectInfoReportDTO.setBookFinancialNumber(eContractDeliverRequest.getBookFinancialNumber());
        eMaazounCollectInfoReportDTO.setBookTypeName(eContractDeliverRequest.getBookTypeName());
        eMaazounCollectInfoReportDTO.setCreateAt(Util.dateFormatReport(createdAt));
        eMaazounCollectInfoReportDTO.setCreateBy(username);
        eMaazounCollectInfoReportDTO.setLocationName(sector.getLocationFk().getName());
        eMaazounCollectInfoReportDTO.setMaazounName(eContractDeliverRequest.getMaazounName());
        eMaazounCollectInfoReportDTO.setMaazounNationalId(eContractDeliverRequest.getMaazounNationalId());
        eMaazounCollectInfoReportDTO.setPosName(pos.getName());
        eMaazounCollectInfoReportDTO.setSequance(String.valueOf(sequance));
        eMaazounCollectInfoReportDTO.setRefRequestNumber(refRequestNumber);
        eMaazounCollectInfoReportDTO.setSectorName(eContractDeliverRequest.getSectorName());
        eMaazounCollectInfoReportDTO.setBookInventoryReference(eContractDeliverRequest.getBookInventoryReference());

        return eMaazounCollectInfoReportDTO;
    }

    public TransactionRefundRequest populateTransactionRefundRequest(MultipartFile refundAttached,
                                                                     String settlementCode, String refundTransactionCode, String username, String comment, String paycode,
                                                                     String eCrResponse, String eCrRequest) {

        TransactionRefundRequest transactionRefundRequest = new TransactionRefundRequest();
        transactionRefundRequest.setAttRefund(refundAttached);
        transactionRefundRequest.setSettlementCode(settlementCode);
        transactionRefundRequest.setRefundTransactionCode(refundTransactionCode);
        transactionRefundRequest.setUserName(username);
        transactionRefundRequest.setComment(comment);
        transactionRefundRequest.setRefundPaymentCode(paycode);
        transactionRefundRequest.setRefundECRresponse(eCrResponse);
        transactionRefundRequest.setRefundECRrequest(eCrRequest);

        return transactionRefundRequest;
    }

    public MaazounBookCollectionInfo populateMaazounCollectionInfo(MaazounBookCollectionInfo eMaazounBookCollectionInfo,
                                                                   EditMaazounContractRequest editMaazounContractRequest, String username, Date updatedAt, String url) {

        // eMaazounBookCollectionInfo.setBookSerialNumber(editMaazounContractRequest.getBookSerialNumber());
        eMaazounBookCollectionInfo.setContractDate(
                UtilCore.convertStringToStartDateFormatSql(editMaazounContractRequest.getContractDate()));
        // eMaazounBookCollectionInfo.setContractNumber(editMaazounContractRequest.getContractNumber());
        eMaazounBookCollectionInfo.setUpdatedAt(updatedAt);
        eMaazounBookCollectionInfo.setUpdatedBy(username);
        eMaazounBookCollectionInfo.setHusbandName(editMaazounContractRequest.getHusbandName());
        // eMaazounBookCollectionInfo.setContractTypeId(editMaazounContractRequest.getSubServiceId());
        eMaazounBookCollectionInfo.setWifeName(editMaazounContractRequest.getWifeName());
        eMaazounBookCollectionInfo.setWifeNationalId(editMaazounContractRequest.getWifeNationalId());
        eMaazounBookCollectionInfo.setHusbandNationalId(editMaazounContractRequest.getHusbandNationalId());

        try {

            if (editMaazounContractRequest.getAttContract() != null
                    && !editMaazounContractRequest.getAttContract().getOriginalFilename().equals("foo.txt")) {
                String imageUrl = saveBrowseAttFile(editMaazounContractRequest.getAttContract(), url,
                        "Contract_" + editMaazounContractRequest.getContractNumber());
                eMaazounBookCollectionInfo.setImageUrl(imageUrl);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new NullArgumentException("Image URL Must Not Be Null");
        }

        return eMaazounBookCollectionInfo;
    }

    public MaazounCollectInfoDTO populateContractQuota(SubService subService, Double contractPaidAmount,
                                                       Double contractPaidBetweenusFees) {

        MaazounCollectInfoDTO eMaazounCollectInfoDTO = new MaazounCollectInfoDTO();

        SubServiceQuota subServiceQuotaAbnytMhakm = subService.getSubServicesQuota().stream()
                .filter(x -> x.getName().equalsIgnoreCase(StatusConstant.CONTRACT_QUOTA_ABNYT_MHAKM)).findAny()
                .orElse(null);
        if (subServiceQuotaAbnytMhakm != null)
            eMaazounCollectInfoDTO.setQuotaContractAbnytMhakm(subServiceQuotaAbnytMhakm.getFees());

        SubServiceQuota subServiceQuotaGamiyaMazouneen = subService.getSubServicesQuota().stream()
                .filter(x -> x.getName().equalsIgnoreCase(StatusConstant.CONTRACT_QUOTA_GAMIYA_MAZOUNEEN)).findAny()
                .orElse(null);
        if (subServiceQuotaGamiyaMazouneen != null)
            eMaazounCollectInfoDTO.setQuotaContractGamiyaMazouneen(subServiceQuotaGamiyaMazouneen.getFees());

        SubServiceQuota subServiceQuotaIdafy = subService.getSubServicesQuota().stream()
                .filter(x -> x.getName().equalsIgnoreCase(StatusConstant.CONTRACT_QUOTA_IDAFY)).findAny().orElse(null);
        if (subServiceQuotaIdafy != null)
            eMaazounCollectInfoDTO.setQuotaContractIdafy(subServiceQuotaIdafy.getFees());

        SubServiceQuota subServiceQuotaMokrr = subService.getSubServicesQuota().stream()
                .filter(x -> x.getName().equalsIgnoreCase(StatusConstant.CONTRACT_QUOTA_MOKRR)).findAny().orElse(null);
        if (subServiceQuotaMokrr != null)
            eMaazounCollectInfoDTO.setQuotaContractMokrr(subServiceQuotaMokrr.getFees());

        SubServiceQuota subServiceQuotaTameenOsra = subService.getSubServicesQuota().stream()
                .filter(x -> x.getName().equalsIgnoreCase(StatusConstant.CONTRACT_QUOTA_TAMEEN_OSRA)).findAny()
                .orElse(null);
        if (subServiceQuotaTameenOsra != null)
            eMaazounCollectInfoDTO.setQuotaContractTameenOsra(subServiceQuotaTameenOsra.getFees());

        if (String.valueOf(subService.getId()).equals(StatusConstant.CONTRACT_ZAWAG_MUSLIM_ID)
                || String.valueOf(subService.getId()).equals(StatusConstant.CONTRACT_TASADOK_ID)
                || String.valueOf(subService.getId()).equals(StatusConstant.CONTRACT_ZAWAG_MELALY_ID)) {

            double subtractBetweenusFromContractPaidAmount = contractPaidAmount - contractPaidBetweenusFees;

            SubServiceQuota subServiceQuotaFirstThousand = subService.getSubServicesQuota().stream()
                    .filter(x -> x.getName().equalsIgnoreCase(StatusConstant.CONTRACT_QUOTA_PERCENTAGE_FIRST_THOUSAND))
                    .findAny().orElse(null);

            SubServiceQuota subServiceQuotaRemainingAmount = subService.getSubServicesQuota().stream().filter(
                            x -> x.getName().equalsIgnoreCase(StatusConstant.CONTRACT_QUOTA_PERCENTAGE_REMAINING_AMOUNT))
                    .findAny().orElse(null);

//			double contractFees = contractPaidAmount;
            double perecntFirstThousand = Util.calculateFirstThousandAndRemainingContractPaid(
                    subtractBetweenusFromContractPaidAmount, subServiceQuotaFirstThousand.getFees());
            double perecntRemainingAmount = Util.calculateSecondRemainingContractPaid(
                    subtractBetweenusFromContractPaidAmount, subServiceQuotaRemainingAmount.getFees());

            eMaazounCollectInfoDTO.setQuotaContractPercentFirstThousand(
                    Double.valueOf(String.format("%.2f", perecntFirstThousand)) + contractPaidBetweenusFees);
            eMaazounCollectInfoDTO.setQuotaContractPercentRemainingAmount(
                    Double.valueOf(String.format("%.2f", perecntRemainingAmount)));

//			if(contractFees <= 1000) {
//				perecntFirstThousand = (subServiceQuotaFirstThousand.getFees()/100)*contractFees;
//				eMaazounCollectInfoDTO.setQuotaContractPercentFirstThousand(Double.valueOf(String.format("%.2f", perecntFirstThousand)));
//			}else if (contractFees > 1000) {
//				perecntFirstThousand = (subServiceQuotaFirstThousand.getFees()/100)*1000;
//				eMaazounCollectInfoDTO.setQuotaContractPercentFirstThousand(Double.valueOf(String.format("%.2f", perecntFirstThousand)));
//				
//				double calculateSecondContractFees = contractFees - 1000;
//				perecntRemainingAmount = (subServiceQuotaRemainingAmount.getFees()/100)*calculateSecondContractFees;
//				eMaazounCollectInfoDTO.setQuotaContractPercentRemainingAmount(Double.valueOf(String.format("%.2f", perecntRemainingAmount)));
//			}

        }

        return eMaazounCollectInfoDTO;

    }

    public boolean checkAssignedPOS(Set<Long> poses, Long selectedPosId) {
        boolean isSelectedPos = false;
        for (Long posId : poses) {
            if (posId.equals(selectedPosId)) {
                return true;
            }
        }
        return isSelectedPos;
    }

    public boolean checkAssignedLocation(Set<Long> locationIds, Long selectedLocationId) {
        boolean isSelectedLocation = false;
        for (Long locationId : locationIds) {
            if (locationId.equals(selectedLocationId)) {
                return true;
            }
        }
        return isSelectedLocation;
    }

    public Pos getAssignedPOS(Set<Pos> poses, long selectedSectorId) {
        Pos selectedPos = null;
        for (Pos pos : poses) {
            for (Sector sector : pos.getSectors()) {
                long sectorId = sector.getId();
                if (sectorId == selectedSectorId) {
                    selectedPos = pos;
                    break;
                }
            }
        }
        return selectedPos;
    }

    public Set<Long> getLocationsFromPos(Set<Pos> poses) {
        Set<Long> locationIds = new HashSet<Long>();
        poses.stream().forEach(s -> {
            for (Sector sector : s.getSectors()) {
                locationIds.add(sector.getLocationFk().getId());
            }
        });
        return locationIds;
    }

    public List<Long> getSectorIds(Set<Pos> poses) {
        List<Long> sectorIds = new ArrayList<Long>();
        poses.stream().forEach(s -> {
            for (Sector sector : s.getSectors()) {
                sectorIds.add(sector.getId());
            }

        });
        return sectorIds;
    }

    public List<Sector> getSectors(Set<Pos> poses) {
        List<Sector> sectorIds = new ArrayList<Sector>();
        poses.stream().forEach(s -> {
            for (Sector sector : s.getSectors()) {
                sectorIds.add(sector);
            }

        });
        return sectorIds;
    }

    public Set<Sector> getSectorsSet(Set<Pos> poses) {
        Set<Sector> sectorIds = new HashSet<Sector>();
        poses.stream().forEach(s -> {
            for (Sector sector : s.getSectors()) {
                sectorIds.add(sector);
            }

        });
        return sectorIds;
    }

    public String getValidationAuthCode(TransactionECRAudit eTransactionECRAudit, String paymentCode) {
        String authCode = null;
        if (eTransactionECRAudit != null) {
//			if(eTransactionECRAudit.getAuthCode() == null || eTransactionECRAudit.getAuthCode().isEmpty()) {
//				if(Util.isValidationAuthCode(paymentCode)) {
//					authCode = paymentCode;
//				}else {
//					throw new ResourceNotFoundException("Sorry, This Payment Code Incorrect format");
//				} 
//			}else {
            if (eTransactionECRAudit.getTransStatus().equals("0")) {
                if (eTransactionECRAudit.getAuthCode() == null || eTransactionECRAudit.getAuthCode().isEmpty()) {
                    if (Util.isValidationAuthCode(paymentCode)) {
                        authCode = paymentCode;
                    } else {
                        throw new ResourceNotFoundException("Sorry, This Payment Code Incorrect format");
                    }
                } else {
                    authCode = eTransactionECRAudit.getAuthCode();
                }
            } else if (eTransactionECRAudit.getTransStatus().equals("1")) {
                throw new ResourceNotFoundException("Sorry, Payment HOST_DECLINED");
            } else if (eTransactionECRAudit.getTransStatus().equals("2")) {
                throw new ResourceNotFoundException("Sorry, Payment CARD_DECLINED");
            } else if (eTransactionECRAudit.getTransStatus().equals("3")) {
                throw new ResourceNotFoundException("Sorry, Payment FAILED");
            }

//			}
        } else {
            if (Util.isValidationAuthCode(paymentCode)) {
                authCode = paymentCode;
            } else {
                throw new ResourceNotFoundException("Sorry, This Payment Code Incorrect format");
            }

        }
        return authCode;
    }

    public int getPaymentProcess(TransactionECRAudit eTransactionECRAudit) {
        int paymentProcess;
        if (eTransactionECRAudit != null) {
            if (eTransactionECRAudit.getAuthCode() == null || eTransactionECRAudit.getAuthCode().isEmpty()) {
                paymentProcess = 1;
            } else {
                paymentProcess = 0;
            }
        } else {
            paymentProcess = 2;
        }
        return paymentProcess;
    }

    public boolean isRoleAgentOrAreaOrSupervisor(String roleName) {
        if (roleName.equals(StatusConstant.ROLE_AGENT) || roleName.equals(StatusConstant.ROLE_AGENT_SUPERVISOR)
                || roleName.equals(StatusConstant.ROLE_AREA_MANAGER)
                || roleName.equals(StatusConstant.ROLE_SUPERVISOR)) {
            return true;
        } else {
            return false;
        }
    }

    // Helper method to get the MaazounProfile by ID or throw an exception if not
    // found
    public MaazounProfile getMaazounProfileOrThrow(long maazounId) {
        return maazounProfileService.findById(maazounId)
                .orElseThrow(() -> new IllegalArgumentException("Maazoun not found or not registered"));
    }

    // Helper method to get Sector from POS or throw an exception if not found
    public Sector getSectorFromPOSOrThrow(Pos pos, Long sectorId) {
        return pos.getSectors().stream().filter(x -> x.getId().equals(sectorId)).findAny()
                .orElseThrow(() -> new IllegalArgumentException("Sector not found"));
    }

}

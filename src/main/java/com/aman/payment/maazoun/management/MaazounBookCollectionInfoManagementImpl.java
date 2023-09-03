package com.aman.payment.maazoun.management;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
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
import org.springframework.web.multipart.MultipartFile;

import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.auth.model.Pos;
import com.aman.payment.auth.model.Sector;
import com.aman.payment.auth.model.SubService;
import com.aman.payment.auth.model.SubServiceQuota;
import com.aman.payment.auth.model.User;
import com.aman.payment.auth.service.PosService;
import com.aman.payment.auth.service.SettingService;
import com.aman.payment.auth.service.SubServiceService;
import com.aman.payment.auth.service.impl.UserService;
import com.aman.payment.core.exception.ResourceNotFoundException;
import com.aman.payment.core.exception.TransactionException;
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
import com.aman.payment.maazoun.event.GenerateTempBookCollectionInfoEvent;
import com.aman.payment.maazoun.event.GenerateTempBookReceivedEvent;
import com.aman.payment.maazoun.mapper.MaazounCollectInfoMapper;
import com.aman.payment.maazoun.model.MaazounBookCollectionInfo;
import com.aman.payment.maazoun.model.MaazounBookWarehouse;
import com.aman.payment.maazoun.model.MaazounCheckListLookup;
import com.aman.payment.maazoun.model.MaazounContractRefundAtt;
import com.aman.payment.maazoun.model.MaazounInventoryNumber;
import com.aman.payment.maazoun.model.MaazounProfile;
import com.aman.payment.maazoun.model.dto.MaazounBookAuditSectorDTO;
import com.aman.payment.maazoun.model.dto.MaazounCollectInfoDTO;
import com.aman.payment.maazoun.model.dto.MaazounCollectInfoPagingDTO;
import com.aman.payment.maazoun.model.dto.MaazounCollectInfoReportDTO;
import com.aman.payment.maazoun.model.dto.MaazounContractAuditDTO;
import com.aman.payment.maazoun.model.dto.MaazounReceivedReportDTO;
import com.aman.payment.maazoun.model.dto.PagingDTO;
import com.aman.payment.maazoun.model.payload.AddMaazounCollectionInfoRequest;
import com.aman.payment.maazoun.model.payload.AddMaazounContractRequest;
import com.aman.payment.maazoun.model.payload.AddMaazounCustodyContractRequest;
import com.aman.payment.maazoun.model.payload.AddReceivedContractStatusRequest;
import com.aman.payment.maazoun.model.payload.AddReceivedMaazounBookRequest;
import com.aman.payment.maazoun.model.payload.AddReceivedMaazounContractRequest;
import com.aman.payment.maazoun.model.payload.AdvancedSearchCollectionInfoTransactionRequest;
import com.aman.payment.maazoun.model.payload.CollectionListSearchRequest;
import com.aman.payment.maazoun.model.payload.ContractByNationalIdRequest;
import com.aman.payment.maazoun.model.payload.ContractListRequest;
import com.aman.payment.maazoun.model.payload.DeleteMaazounContractRequest;
import com.aman.payment.maazoun.model.payload.EditMaazounContractRequest;
import com.aman.payment.maazoun.model.payload.MaazounAuditRequest;
import com.aman.payment.maazoun.model.payload.MaazounContractInfoRefundRequest;
import com.aman.payment.maazoun.model.payload.QuickSearchCollectiontInfoTransactionRequest;
import com.aman.payment.maazoun.model.payload.ReviewRequest;
import com.aman.payment.maazoun.model.payload.URLRequest;
import com.aman.payment.maazoun.service.CryptoMngrMaazounService;
import com.aman.payment.maazoun.service.MaazounBookWarehouseService;
import com.aman.payment.maazoun.service.MaazounCheckListService;
import com.aman.payment.maazoun.service.MaazounCollectionInfoService;
import com.aman.payment.maazoun.service.MaazounContractRefundAttService;
import com.aman.payment.maazoun.service.MaazounInventoryNumberService;
import com.aman.payment.maazoun.service.MaazounProfileService;
import com.aman.payment.maazoun.service.impl.MaazounInventoryInfoService;
import com.aman.payment.util.ConfigurationConstant;
import com.aman.payment.util.StatusConstant;
import com.aman.payment.util.Util;

@Component
public class MaazounBookCollectionInfoManagementImpl extends ValidationAndPopulateManagement
		implements MaazounBookCollectionInfoManagement {

	final static Logger logger = Logger.getLogger("maazoun");

	private final MaazounCollectionInfoService maazounCollectInfoService;
	private final CryptoMngrMaazounService cryptoMngrMaazounService;
	private final MaazounCollectInfoMapper maazounCollectInfoMapper;
	private final ApplicationEventPublisher applicationEventPublisher;
	private final MaazounProfileService maazounProfileService;
	private final MaazounBookWarehouseService maazounBookWarehouseService;
	private final MaazounCheckListService maazounCheckListService;
	private final SubServiceService subServiceService;
	private final TransactionECRService transactionECRService;
	private final PullAccountService pullAccountService;
	private final TransactionManagement transactionManagement;
	private final SettingService settingService;
	private final MaazounContractRefundAttService maazounContractRefundAttService;
	private final MaazounInventoryNumberService maazounInventoryNumberService;
	private final UserService userService;

	@Value("${attachment.maazoun.collection}")
	private String bookCollectionInfoPathAtt;

	@Value("${attachment.maazoun.contract.status}")
	private String contractStatusPathAtt;

	@Value("${reports.maazoun.collection}")
	private String bookCollectionInfoPathReport;

	@Value("${reports.maazoun.received}")
	private String bookReceivedPathReport;

	@Value("${aman.logo}")
	private String amanLogoUrl;

	@Value("${prosecutor.logo}")
	private String prosecutorLogoUrl;

	@Autowired
	private MaazounInventoryInfoService eMaazounInventoryInfoService;
	@Autowired
	private PosService posService;

	@Autowired
	public MaazounBookCollectionInfoManagementImpl(MaazounCollectionInfoService maazounCollectInfoService,
			CryptoMngrMaazounService cryptoMngrMaazounService, MaazounCollectInfoMapper maazounCollectInfoMapper,
			ApplicationEventPublisher applicationEventPublisher, TransactionManagement transactionManagement,
			MaazounProfileService maazounProfileService, MaazounBookWarehouseService maazounBookWarehouseService,
			MaazounCheckListService maazounCheckListService, SubServiceService subServiceService,
			TransactionECRService transactionECRService, SettingService settingService,
			PullAccountService pullAccountService, MaazounContractRefundAttService maazounContractRefundAttService,
			MaazounInventoryNumberService maazounInventoryNumberService, UserService userService) {
		super();
		this.maazounCollectInfoService = maazounCollectInfoService;
		this.cryptoMngrMaazounService = cryptoMngrMaazounService;
		this.maazounCollectInfoMapper = maazounCollectInfoMapper;
		this.applicationEventPublisher = applicationEventPublisher;
		this.transactionManagement = transactionManagement;
		this.maazounProfileService = maazounProfileService;
		this.maazounBookWarehouseService = maazounBookWarehouseService;
		this.maazounCheckListService = maazounCheckListService;
		this.subServiceService = subServiceService;
		this.transactionECRService = transactionECRService;
		this.settingService = settingService;
		this.pullAccountService = pullAccountService;
		this.maazounContractRefundAttService = maazounContractRefundAttService;
		this.maazounInventoryNumberService = maazounInventoryNumberService;
		this.userService = userService;
	}

	@Override
	public List<String> getBookChecklist(long subServiceId) {
		List<MaazounCheckListLookup> bookChecklist = maazounCheckListService.findBySubServiceId(subServiceId);

		List<String> eBookChecklist = new ArrayList<String>();
		bookChecklist.stream().forEach(s -> {
			eBookChecklist.add(cryptoMngrMaazounService.encrypt(s.toString()));
		});

		return eBookChecklist;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String addContractRequest(CustomUserDetails customUserDetails,
			AddMaazounContractRequest addMaazounContractRequest) {

		// Step 1: Validate and retrieve the required entities
		SubService subService = getSubServiceOrThrow(addMaazounContractRequest.getSubServiceId());
		Pos pos = getAssignedPOS(customUserDetails.getPosSet(), addMaazounContractRequest.getSectorId());
		Sector sector = getSectorFromPOSOrThrow(pos, addMaazounContractRequest.getSectorId());
		MaazounProfile maazounProfile = getMaazounProfileOrThrow(addMaazounContractRequest.getMaazounId());
		MaazounBookWarehouse eMaazounBookWarehouse = getMaazounBookWarehouseOrThrow(
				addMaazounContractRequest.getBookWarehouseId());

		// Step 2: Perform contract data validation
		contractDataValidation(addMaazounContractRequest.getHusbandNationalId(),
				addMaazounContractRequest.getWifeNationalId(), addMaazounContractRequest.getHusbandName(),
				addMaazounContractRequest.getWifeName());

		// Step 3: Recalculate fee amount
		double feeAmount = reCalculateZawagContractFees(subService, addMaazounContractRequest.getSubServiceId(),
				subService.getFees(), addMaazounContractRequest.getContractPaidAmount(),
				addMaazounContractRequest.getContractPaidBetweenusFees());

		// Step 4: Set book serial number and contract number
		addMaazounContractRequest.setBookSerialNumber(
				eMaazounBookWarehouse.getSerialNumber() != null ? eMaazounBookWarehouse.getSerialNumber()
						: eMaazounBookWarehouse.getBookFinancialNumber());
		addMaazounContractRequest.setContractNumber(
				eMaazounBookWarehouse.getContractNumber() != null ? eMaazounBookWarehouse.getContractNumber()
						: eMaazounBookWarehouse.getContractFinancialNumber());

		// Step 5: Populate MaazounBookCollectionInfo and save it
		Date createdAt = new Date();
		MaazounBookCollectionInfo eMaazounBookCollectionInfo = populateMaazounCollectionInfo(null, feeAmount,
				addMaazounContractRequest, customUserDetails.getUsername(), createdAt, pos, subService.getName(),
				maazounProfile, bookCollectionInfoPathAtt, sector);
		MaazounBookCollectionInfo maazounBookCollectionInfoFk = maazounCollectInfoService
				.save(eMaazounBookCollectionInfo);

		// Step 6: Update MaazounBookWarehouse
		eMaazounBookWarehouse.setStatusFk(
				String.valueOf(addMaazounContractRequest.getSubServiceId()).equals(StatusConstant.CONTRACT_TALAK_ID)
						|| String.valueOf(addMaazounContractRequest.getSubServiceId())
								.equals(StatusConstant.CONTRACT_MORAGAA_ID) ? StatusConstant.STATUS_REVIEWED
										: StatusConstant.STATUS_UNDER_REVIEWED);
		eMaazounBookWarehouse.setMaazounBookCollectionInfoFk(maazounBookCollectionInfoFk);
		maazounBookWarehouseService.save(eMaazounBookWarehouse);

		String response = maazounCollectInfoMapper.createPreviewAddCollectionInfoDTO(maazounBookCollectionInfoFk)
				.toString();

		return cryptoMngrMaazounService.encrypt(response);

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String editCustodyContractRequest(CustomUserDetails customUserDetails,
			AddMaazounCustodyContractRequest addMaazounCustodyContractRequest) {

		SubService subService = subServiceService.findById(addMaazounCustodyContractRequest.getSubServiceId()).get();

		Pos pos = getAssignedPOS(customUserDetails.getPosSet(), addMaazounCustodyContractRequest.getSectorId());
		Sector sector = pos.getSectors().stream()
				.filter(x -> x.getId().equals(addMaazounCustodyContractRequest.getSectorId())).findAny().orElse(null);

		Date createdAt = Date.from(Instant.now());

		// validate this contract number with maazoun and the maazounia
//		MaazounProfile maazounProfile = maazounMaazouniaValidation(addMaazounCustodyContractRequest.getMaazounId(),
//				addMaazounCustodyContractRequest.getSectorId());

		Optional<MaazounProfile> maazounOptional = maazounProfileService
				.findById(addMaazounCustodyContractRequest.getMaazounId());
		MaazounProfile maazounProfile = null;
		if (maazounOptional.isPresent()) {
			maazounProfile = maazounOptional.get();
		} else {
			// throw new ResourceNotFoundException("Sorry, This Maazoun not Registered", "",
			// "");
			throw new IllegalArgumentException(
					"نأسف هذا المأذون/الموثق غير مسجل بالتطبيق يرجوه تسجيله من خلال الدعم الفنى");
		}

		// valid this contract exist in warehouse or not
		MaazounBookWarehouse eMaazounBookWarehouse = null;
		try {
			eMaazounBookWarehouse = maazounBookWarehouseService
					.findById(addMaazounCustodyContractRequest.getBookWarehouseId()).get();
//			eMaazounBookWarehouse = maazounBookWarehouseService.findByContractNumberOrContractFinancialNumber(
//					addMaazounCustodyContractRequest.getContractFinancialNumber(), 
//					addMaazounCustodyContractRequest.getContractFinancialNumber()).get();
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"نأسف رقم العقد غير مسجل فى مخزن التطبيق. الرجاء المراجعة مع الدعم الفنى ");
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("response", "This Contract Number not exist in the warehouse");
//			return cryptoMngrMaazounService.encrypt(jsonObject.toString());
		}

		MaazounBookCollectionInfo eMaazounBookCollectionInfo = populateMaazounCustodyCollectionInfo(
				addMaazounCustodyContractRequest, customUserDetails.getUsername(), createdAt, pos, subService.getName(),
				maazounProfile, sector, eMaazounBookWarehouse);

		MaazounBookCollectionInfo maazounBookCollectionInfoFk = maazounCollectInfoService
				.save(eMaazounBookCollectionInfo);

		eMaazounBookWarehouse.setStatusFk(StatusConstant.STATUS_REVIEWED_CUSTODY);
		eMaazounBookWarehouse.setMaazounBookCollectionInfoFk(maazounBookCollectionInfoFk);
		maazounBookWarehouseService.save(eMaazounBookWarehouse);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("response", "Save Custody Contract Successfully");
		return cryptoMngrMaazounService.encrypt(jsonObject.toString());
	}

	@Override
	public String deleteContractRequest(CustomUserDetails customUserDetails,
			DeleteMaazounContractRequest deleteMaazounContractRequest) {

		Long collectionInfoId = Long.valueOf(deleteMaazounContractRequest.getCollectionInfoId());

		// Find the MaazounBookCollectionInfo by ID or throw an exception if not found
		MaazounBookCollectionInfo eMaazounBookCollectionInfo = maazounCollectInfoService.findById(collectionInfoId)
				.orElseThrow(() -> new ResourceNotFoundException("MaazounBookCollectionInfo not found"));

		// Find the related MaazounBookWarehouse
		Optional<MaazounBookWarehouse> eMaazounBookWarehouseOptional = eMaazounBookCollectionInfo
				.getMaazounBookWarehouseSet().stream()
				.filter(warehouseObj -> warehouseObj.getMaazounBookCollectionInfoFk().getId().equals(collectionInfoId))
				.findFirst();

		// Throw an exception if the related MaazounBookWarehouse is not found
		// (inconsistent data)
		MaazounBookWarehouse eMaazounBookWarehouse = eMaazounBookWarehouseOptional
				.orElseThrow(() -> new IllegalStateException("Related MaazounBookWarehouse not found"));

		eMaazounBookCollectionInfo.setStatusFk(StatusConstant.STATUS_REJECTED);
		maazounCollectInfoService.save(eMaazounBookCollectionInfo);

		eMaazounBookWarehouse.setStatusFk(StatusConstant.STATUS_SOLD);
		maazounBookWarehouseService.save(eMaazounBookWarehouse);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("response", "Successfully");

		return cryptoMngrMaazounService.encrypt(jsonObject.toString());

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String createCollectionRequest(CustomUserDetails customUserDetails,
			AddMaazounCollectionInfoRequest addMaazounCollectionInfoRequest, TransactionECRAudit eTransactionECRAudit) {

		Pos pos = getAssignedPOS(customUserDetails.getPosSet(), addMaazounCollectionInfoRequest.getSectorId());
		Sector sector = getSectorFromPOSOrThrow(pos, addMaazounCollectionInfoRequest.getSectorId());

		Date collectedAt = Date.from(Instant.now());
		String refRequestNumber = getRefRequestNumber(addMaazounCollectionInfoRequest.getPaymentEcrRequest());
		String basePath = bookCollectionInfoPathReport + "/"
				+ UtilCore.saveFolderNamingFormat(String.valueOf(collectedAt));
		String receiptUrl = "/" + refRequestNumber + ConfigurationConstant.MAAZOUN_BOOK_COLLECTION_REPORT_NAME + ".pdf";

		TransactionECR transactionECRFk = createTransactionECRIfPresent(addMaazounCollectionInfoRequest);

		String authCode = getValidationAuthCode(eTransactionECRAudit, addMaazounCollectionInfoRequest.getPaymentCode());
		int paymentProcessFlag = getPaymentProcess(eTransactionECRAudit);

		List<MaazounCollectInfoReportDTO> eMaazounCollectInfoReportDTOSet = new ArrayList<MaazounCollectInfoReportDTO>();
		String typeId = null;
		int i = 1;
		List<ContractListRequest> uniqueContracts = removeDuplicateContracts(
				addMaazounCollectionInfoRequest.getContractListRequest());
		for (ContractListRequest eContract : uniqueContracts) {

			MaazounBookCollectionInfo eMaazounBookCollectionInfo = maazounCollectInfoService
					.findById(Long.valueOf(eContract.getCollectionInfoId())).get();

			if (eMaazounBookCollectionInfo.getStatusFk().equals(StatusConstant.STATUS_REVIEWED)) {

				Set<TransactionMidsDTO> transactionMids = new HashSet<TransactionMidsDTO>();
				SubService subService = subServiceService.findById(eMaazounBookCollectionInfo.getContractTypeId())
						.get();

				transactionMids.addAll(
						calculateMids(transactionMids, subService, eMaazounBookCollectionInfo.getContractPaidAmount(),
								eMaazounBookCollectionInfo.getContractPaidBetweenusFees()));

				TransactionPaymentRequest transactionPaymentRequest = populateTransactionPaymentRequest(
						String.valueOf(eMaazounBookCollectionInfo.getContractTypeId()), authCode,
						addMaazounCollectionInfoRequest.getVisaNumber(), pos, customUserDetails.getUsername(),
						eMaazounBookCollectionInfo.getFeeAmount(), StatusConstant.CONTRACT_SERVICE_ID, transactionECRFk,
						eMaazounBookCollectionInfo.getContractPaidAmount(), 0, transactionMids, sector);

				try {
					TimeUnit.MILLISECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				TransactionResponseDTO transactionResponseDTO = transactionManagement
						.createTransaction(transactionPaymentRequest);
				String transactionCode = transactionResponseDTO.getTransactionCode();

				eMaazounBookCollectionInfo.setTransactionCode(transactionCode);
				eMaazounBookCollectionInfo.setRefRequestNumber(refRequestNumber);
				eMaazounBookCollectionInfo.setReceiptUrl(basePath.concat(receiptUrl));
				eMaazounBookCollectionInfo
						.setPaymentCode((eTransactionECRAudit != null) ? eTransactionECRAudit.getAuthCode() : null);
				eMaazounBookCollectionInfo.setVisaNumber(addMaazounCollectionInfoRequest.getVisaNumber());
				eMaazounBookCollectionInfo.setPaymentProcessFlag(paymentProcessFlag);
				eMaazounBookCollectionInfo.setPaymentCode(authCode);
				eMaazounBookCollectionInfo.setStatusFk(StatusConstant.STATUS_COLLECTED);
				eMaazounBookCollectionInfo.setCollectedAt(collectedAt);
				eMaazounBookCollectionInfo.setCollectedBy(customUserDetails.getUsername());
				eMaazounBookCollectionInfo.setUpdatedAt(collectedAt);
				eMaazounBookCollectionInfo.setUpdatedBy(customUserDetails.getUsername());

				MaazounBookCollectionInfo maazounBookCollectionInfo = maazounCollectInfoService
						.save(eMaazounBookCollectionInfo);

				MaazounBookWarehouse warehouseObj = maazounBookWarehouseService
						.findByContractNumberOrContractFinancialNumber(eMaazounBookCollectionInfo.getContractNumber(),
								eMaazounBookCollectionInfo.getContractNumber())
						.orElse(null);
				warehouseObj.setStatusFk(StatusConstant.STATUS_COLLECTED);
				maazounBookWarehouseService.save(warehouseObj);

				MaazounCollectInfoReportDTO eMaazounCollectInfoReportDTO = populateMaazounCollectInfoReportDTO(
						eContract, amanLogoUrl, prosecutorLogoUrl, addMaazounCollectionInfoRequest.getTotalAmount(),
						Util.getCreatedByFullName(customUserDetails), collectedAt, eMaazounBookCollectionInfo,
						refRequestNumber, pos, i, warehouseObj, sector,
						addMaazounCollectionInfoRequest.getMaazouniaChurchNameType());

				eMaazounCollectInfoReportDTOSet.add(eMaazounCollectInfoReportDTO);

			} else if (eMaazounBookCollectionInfo.getStatusFk().equals(StatusConstant.STATUS_REVIEW_REJECTED)) {

				eMaazounBookCollectionInfo.setStatusFk(StatusConstant.STATUS_REJECTED);
				maazounCollectInfoService.save(eMaazounBookCollectionInfo);
			}

			i++;
		}

		GenerateTempBookCollectionInfoEvent generateTempCollectionInfoEvent = new GenerateTempBookCollectionInfoEvent(
				eMaazounCollectInfoReportDTOSet, String.valueOf(collectedAt), receiptUrl, typeId, 0);
		applicationEventPublisher.publishEvent(generateTempCollectionInfoEvent);

		return cryptoMngrMaazounService.encrypt(basePath.concat(receiptUrl));
	}

	public void reGenerateCollectionReport(String refRequestNumber) {

		List<MaazounBookCollectionInfo> collections = maazounCollectInfoService
				.findByRefRequestNumber(refRequestNumber);
		User user = userService.findByUsername(collections.stream().findFirst().get().getCreatedBy()).get();
		List<MaazounCollectInfoReportDTO> eMaazounCollectInfoReportDTOSet = new ArrayList<MaazounCollectInfoReportDTO>();
		String typeId = null;
		String receiptUrl = null;
		Date createdAt = null;
		int i = 1;
		for (MaazounBookCollectionInfo eMaazounBookCollectionInfo : collections) {

			SubService subService = subServiceService.findById(eMaazounBookCollectionInfo.getContractTypeId()).get();

			typeId = String.valueOf(eMaazounBookCollectionInfo.getContractTypeId());
			receiptUrl = eMaazounBookCollectionInfo.getReceiptUrl();
			createdAt = eMaazounBookCollectionInfo.getCreatedAt();

			MaazounCollectInfoDTO eMaazounCollectInfoDTO = populateContractQuota(subService,
					eMaazounBookCollectionInfo.getContractPaidAmount(),
					eMaazounBookCollectionInfo.getContractPaidBetweenusFees());

			ContractListRequest eContractListRequest = new ContractListRequest();
			eContractListRequest.setCollectionInfoId(eMaazounBookCollectionInfo.getId());
			eContractListRequest.setQuotaContractAbnytMhakm(eMaazounCollectInfoDTO.getQuotaContractAbnytMhakm());
			eContractListRequest
					.setQuotaContractGamiyaMazouneen(eMaazounCollectInfoDTO.getQuotaContractGamiyaMazouneen());
			eContractListRequest.setQuotaContractIdafy(eMaazounCollectInfoDTO.getQuotaContractIdafy());
			eContractListRequest.setQuotaContractMokrr(eMaazounCollectInfoDTO.getQuotaContractMokrr());
			eContractListRequest.setQuotaContractPercentFirstThousand(
					eMaazounCollectInfoDTO.getQuotaContractPercentFirstThousand());
			eContractListRequest.setQuotaContractPercentRemainingAmount(
					eMaazounCollectInfoDTO.getQuotaContractPercentRemainingAmount());
			eContractListRequest.setQuotaContractTameenOsra(eMaazounCollectInfoDTO.getQuotaContractTameenOsra());
			eContractListRequest.setTransactionCode(eMaazounBookCollectionInfo.getTransactionCode());
			eContractListRequest.setTypeId(typeId);
			eContractListRequest.setTypeName(subService.getName());

			MaazounBookWarehouse eMaazounBookWarehouse = maazounBookWarehouseService
					.findByContractNumberOrContractFinancialNumber(eMaazounBookCollectionInfo.getContractNumber(),
							eMaazounBookCollectionInfo.getContractNumber())
					.get();
//					eMaazounBookCollectionInfo.getMaazounBookWarehouseSet()
//					.stream().filter(x -> x.getMaazounBookCollectionInfoFk().getId() == eMaazounBookCollectionInfo.getId())
//					.findFirst().get();

			MaazounCollectInfoReportDTO eMaazounCollectInfoReportDTO = populateMaazounCollectInfoReportDTO(
					eContractListRequest, amanLogoUrl, prosecutorLogoUrl,
					String.valueOf(eMaazounBookCollectionInfo.getFeeAmount()), Util.getCreatedByFullName(user),
					createdAt, eMaazounBookCollectionInfo, refRequestNumber, eMaazounBookCollectionInfo.getPosFk(), i,
					eMaazounBookWarehouse, eMaazounBookCollectionInfo.getSectorFk(),
					eMaazounBookWarehouse.getMaazounBookRequestInfoFk().getMaazouniaName() != null
							? eMaazounBookWarehouse.getMaazounBookRequestInfoFk().getMaazouniaName()
							: eMaazounBookCollectionInfo.getMaazounProfileFk().getMaazounMaazouniaChurch().stream()
									.findFirst().get().getMaazouniaChurchFk().getName());

			eMaazounCollectInfoReportDTOSet.add(eMaazounCollectInfoReportDTO);

			i++;
		}

		GenerateTempBookCollectionInfoEvent generateTempCollectionInfoEvent = new GenerateTempBookCollectionInfoEvent(
				eMaazounCollectInfoReportDTOSet, String.valueOf(createdAt), receiptUrl, typeId, 1);
		applicationEventPublisher.publishEvent(generateTempCollectionInfoEvent);

	}

	private String saveContractAttFile(MultipartFile att, String currentDate, String contractNumber) throws Exception {
		Path fileStorageLocation = null;

		if (att != null && !att.getOriginalFilename().equals("foo.txt")) {
			try {
				fileStorageLocation = Paths
						.get(bookCollectionInfoPathAtt + "/" + currentDate + "/" + contractNumber + "." + "png")
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
				logger.error("saveContractAttFile An exception when saveAttachedFile! ", ex);
				throw new TransactionException("saveContractAttFile An exception when saveAttachedFile! ");
			}

		}
		return fileStorageLocation.toString();

	}

	private String saveReceivedContractStatusAttFile(MultipartFile att, String currentDate, String contractNumber)
			throws Exception {
		Path fileStorageLocation = null;

		if (att != null && !att.getOriginalFilename().equals("foo.txt")) {
			try {
				fileStorageLocation = Paths
						.get(contractStatusPathAtt + "/" + currentDate + "/" + contractNumber + "." + "png")
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
				logger.error("saveContractAttFile An exception when saveAttachedFile! ", ex);
				throw new TransactionException("saveContractAttFile An exception when saveAttachedFile! ");
			}

		}
		return fileStorageLocation.toString();

	}

	@Override
	public MaazounCollectInfoPagingDTO quickSerachCollectionInfoTransactions(
			QuickSearchCollectiontInfoTransactionRequest quickSearchCollectiontInfoTransactionRequest,
			CustomUserDetails customUserDetails) {

		Pageable pageable = PageRequest.of(Integer.valueOf(quickSearchCollectiontInfoTransactionRequest.getPageNo()),
				Integer.valueOf(quickSearchCollectiontInfoTransactionRequest.getPageSize()));
		Page<MaazounBookCollectionInfo> pageResult = null;

		if (quickSearchCollectiontInfoTransactionRequest.getBookSerialNumber() != null
				&& !quickSearchCollectiontInfoTransactionRequest.getBookSerialNumber().isEmpty()) {

			if (isRoleAgentOrAreaOrSupervisor(customUserDetails.getRoleFk().getName())) {
				pageResult = maazounCollectInfoService.findByContractNumberAndPosFkIn(
						quickSearchCollectiontInfoTransactionRequest.getBookSerialNumber(),
						customUserDetails.getPosSet(), pageable);
			} else {
				pageResult = maazounCollectInfoService.findByContractNumber(
						quickSearchCollectiontInfoTransactionRequest.getBookSerialNumber(), pageable);
			}

		} else if (quickSearchCollectiontInfoTransactionRequest.getTransactionCode() != null
				&& !quickSearchCollectiontInfoTransactionRequest.getTransactionCode().isEmpty()) {

			if (isRoleAgentOrAreaOrSupervisor(customUserDetails.getRoleFk().getName())) {
				pageResult = maazounCollectInfoService.findByTransactionCodeAndPosFkIn(
						quickSearchCollectiontInfoTransactionRequest.getTransactionCode(),
						customUserDetails.getPosSet(), pageable);
			} else {
				pageResult = maazounCollectInfoService.findByTransactionCode(
						quickSearchCollectiontInfoTransactionRequest.getTransactionCode(), pageable);
			}

		} else if (quickSearchCollectiontInfoTransactionRequest.getRefRequestNumber() != null
				&& !quickSearchCollectiontInfoTransactionRequest.getRefRequestNumber().isEmpty()) {

			if (isRoleAgentOrAreaOrSupervisor(customUserDetails.getRoleFk().getName())) {
				pageResult = maazounCollectInfoService.findByRefRequestNumberAndPosFkIn(
						quickSearchCollectiontInfoTransactionRequest.getRefRequestNumber(),
						customUserDetails.getPosSet(), pageable);
			} else {
				pageResult = maazounCollectInfoService.findByRefRequestNumber(
						quickSearchCollectiontInfoTransactionRequest.getRefRequestNumber(), pageable);
			}

		}

		MaazounCollectInfoPagingDTO eMaazounCollectInfoPagingDTO = new MaazounCollectInfoPagingDTO();
		eMaazounCollectInfoPagingDTO.setCount(pageResult.getTotalElements());
		eMaazounCollectInfoPagingDTO.setTotalPages(pageResult.getTotalPages());

		List<String> eTransaction = new ArrayList<String>();
		pageResult.getContent().stream().forEach(s -> {
			eTransaction.add(cryptoMngrMaazounService
					.encrypt(maazounCollectInfoMapper.maazounCollectionInfoToMaazounCollectionInfoDTO(s).toString()));
		});

		eMaazounCollectInfoPagingDTO.setContracts(eTransaction);

		return eMaazounCollectInfoPagingDTO;

	}

	@Override
	public MaazounCollectInfoPagingDTO advancedSerachCollectionInfoTransactions(
			AdvancedSearchCollectionInfoTransactionRequest advancedSearchCollectionInfoTransactionRequest,
			CustomUserDetails customUserDetails) {

		Page<MaazounBookCollectionInfo> pageResult = null;
		MaazounCollectInfoPagingDTO obj = new MaazounCollectInfoPagingDTO();

		try {
			pageResult = maazounCollectInfoService.advancedSearch(advancedSearchCollectionInfoTransactionRequest,
					customUserDetails);

			obj.setCount(pageResult.getTotalElements());
			obj.setTotalPages(pageResult.getTotalPages());

			List<String> eTransaction = new ArrayList<String>();
			pageResult.getContent().stream().forEach(s -> {
				eTransaction.add(cryptoMngrMaazounService.encrypt(
						maazounCollectInfoMapper.maazounCollectionInfoToMaazounCollectionInfoDTO(s).toString()));
			});
			obj.setContracts(eTransaction);
			return obj;

		} catch (Exception e) {
			obj.setCount((long) 0);
			obj.setTotalPages(0);
			obj.setContracts(new ArrayList<String>());
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

			reGenerateCollectionReport(uRLRequest.getRefRequestNumber());
			try {
				is = new FileInputStream(pdfFile);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		return is;
	}

	@Override
	public List<String> getCollectedDatalist(CustomUserDetails customUserDetails) {

		Set<Long> locationIds = getLocationsFromPos(customUserDetails.getPosSet());

		List<String> eTransaction = new ArrayList<String>();
		maazounCollectInfoService.getContractsUnderReview(StatusConstant.STATUS_COLLECTED, locationIds).stream()
				.forEach(s -> {
					eTransaction.add(cryptoMngrMaazounService.encrypt(s.toString()));
				});

		return eTransaction;

	}

	@Override
	public String reviewContract(CustomUserDetails customUserDetails, ReviewRequest reviewRequest) {

		Date reviewDate = Date.from(Instant.now());

		MaazounBookCollectionInfo eMaazounBookCollectionInfo = maazounCollectInfoService
				.findById(Long.valueOf(reviewRequest.getId())).get();

		MaazounBookWarehouse eMaazounBookWarehouse = maazounBookWarehouseService
				.findByContractNumberOrContractFinancialNumber(eMaazounBookCollectionInfo.getContractNumber(),
						eMaazounBookCollectionInfo.getContractNumber())
				.get();

		if (reviewRequest.getStatus().equalsIgnoreCase(StatusConstant.STATUS_APPROVED)) {

			eMaazounBookCollectionInfo.setStatusFk(StatusConstant.STATUS_REVIEWED);
			eMaazounBookWarehouse.setStatusFk(StatusConstant.STATUS_REVIEWED);

		} else {
			eMaazounBookCollectionInfo.setStatusFk(StatusConstant.STATUS_REVIEW_REJECTED);

			eMaazounBookWarehouse.setStatusFk(StatusConstant.STATUS_SOLD);
			// eMaazounBookWarehouse.setMaazounBookCollectionInfoFk(null);

		}

		eMaazounBookCollectionInfo.setIsReviewed(true);
		eMaazounBookCollectionInfo.setReviewedAt(reviewDate);
		eMaazounBookCollectionInfo.setReviewedBy(customUserDetails.getUsername());

		maazounCollectInfoService.save(eMaazounBookCollectionInfo);
		maazounBookWarehouseService.save(eMaazounBookWarehouse);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("response", "Review Contract Succeed");
		return cryptoMngrMaazounService.encrypt(jsonObject.toString());
	}

	@Override
	public MaazounCollectInfoPagingDTO getAuditByCreatedDate(MaazounAuditRequest maazounAuditRequest,
			CustomUserDetails customUserDetails) {

		Page<MaazounContractAuditDTO> pageResult = maazounCollectInfoService.getAuditByCreatedDate(maazounAuditRequest,
				customUserDetails);

		MaazounCollectInfoPagingDTO maazounCollectInfoPagingDTO = new MaazounCollectInfoPagingDTO();
		maazounCollectInfoPagingDTO.setCount(pageResult.getTotalElements());
		maazounCollectInfoPagingDTO.setTotalPages(pageResult.getTotalPages());

		List<String> eTransaction = new ArrayList<String>();
		pageResult.getContent().stream().forEach(s -> {
			eTransaction.add(cryptoMngrMaazounService.encrypt(s.toString()));
		});
		maazounCollectInfoPagingDTO.setContracts(eTransaction);

		return maazounCollectInfoPagingDTO;
	}

	@Override
	public MaazounCollectInfoPagingDTO getAuditByCreatedDateAndLocation(MaazounAuditRequest maazounAuditRequest,
			CustomUserDetails customUserDetails) {

		Page<MaazounContractAuditDTO> pageResult = maazounCollectInfoService
				.getAuditByCreatedDateAndLocation(maazounAuditRequest, customUserDetails);

		MaazounCollectInfoPagingDTO maazounCollectInfoPagingDTO = new MaazounCollectInfoPagingDTO();
		maazounCollectInfoPagingDTO.setCount(pageResult.getTotalElements());
		maazounCollectInfoPagingDTO.setTotalPages(pageResult.getTotalPages());

		List<String> eTransaction = new ArrayList<String>();
		pageResult.getContent().stream().forEach(s -> {
			eTransaction.add(cryptoMngrMaazounService.encrypt(s.toString()));
		});
		maazounCollectInfoPagingDTO.setContracts(eTransaction);

		return maazounCollectInfoPagingDTO;

	}

	@Override
	public MaazounCollectInfoPagingDTO getAuditByCreatedDateAndCourt(MaazounAuditRequest maazounAuditRequest,
			CustomUserDetails customUserDetails) {

		Page<MaazounContractAuditDTO> pageResult = maazounCollectInfoService
				.getAuditByCreatedDateAndCourt(maazounAuditRequest, customUserDetails);

		MaazounCollectInfoPagingDTO maazounCollectInfoPagingDTO = new MaazounCollectInfoPagingDTO();
		maazounCollectInfoPagingDTO.setCount(pageResult.getTotalElements());
		maazounCollectInfoPagingDTO.setTotalPages(pageResult.getTotalPages());

		List<String> eTransaction = new ArrayList<String>();
		pageResult.getContent().stream().forEach(s -> {
			eTransaction.add(cryptoMngrMaazounService.encrypt(s.toString()));
		});
		maazounCollectInfoPagingDTO.setContracts(eTransaction);

		return maazounCollectInfoPagingDTO;

	}

	@Override
	public MaazounCollectInfoPagingDTO getAuditByCreatedDateAndAgentAndLocation(MaazounAuditRequest maazounAuditRequest,
			CustomUserDetails customUserDetails) {

		Page<MaazounContractAuditDTO> pageResult = maazounCollectInfoService
				.getAuditByCreatedDateAndAgentAndLocation(maazounAuditRequest, customUserDetails);

		MaazounCollectInfoPagingDTO maazounCollectInfoPagingDTO = new MaazounCollectInfoPagingDTO();
		maazounCollectInfoPagingDTO.setCount(pageResult.getTotalElements());
		maazounCollectInfoPagingDTO.setTotalPages(pageResult.getTotalPages());

		List<String> eTransaction = new ArrayList<String>();
		pageResult.getContent().stream().forEach(s -> {
			eTransaction.add(cryptoMngrMaazounService.encrypt(s.toString()));
		});
		maazounCollectInfoPagingDTO.setContracts(eTransaction);

		return maazounCollectInfoPagingDTO;

	}

	private void updateMaazounWarehouseContract(String typeId, MaazounBookCollectionInfo maazounBookCollectionInfoFk) {

		if (typeId.equals(StatusConstant.CONTRACT_ZAWAG_MUSLIM_ID) || typeId.equals(StatusConstant.CONTRACT_TASADOK_ID)
				|| typeId.equals(StatusConstant.CONTRACT_ZAWAG_MELALY_ID)) {

			if (maazounBookCollectionInfoFk.getStatusFk().equals(StatusConstant.STATUS_REVIEWED)) {
				maazounBookWarehouseService.updateStatusByContractNumber(StatusConstant.STATUS_COLLECTED,
						maazounBookCollectionInfoFk, maazounBookCollectionInfoFk.getContractNumber());
			} else {
				maazounBookWarehouseService.updateStatusByContractNumber(StatusConstant.STATUS_COLLECTED_UNDER_REVIEWED,
						maazounBookCollectionInfoFk, maazounBookCollectionInfoFk.getContractNumber());
			}

		} else {
			maazounBookWarehouseService.updateStatusByContractNumber(StatusConstant.STATUS_COLLECTED,
					maazounBookCollectionInfoFk, maazounBookCollectionInfoFk.getContractNumber());
		}

	}

	private MaazounBookWarehouse addNewMaazounContractInWarehouse(AddMaazounContractRequest addMaazounContractRequest) {

		MaazounBookWarehouse obj = new MaazounBookWarehouse();
		if (addMaazounContractRequest.getSubServiceId().equals(StatusConstant.CONTRACT_ZAWAG_MUSLIM_ID)) {
			obj.setBookTypeId(9);
			obj.setBookTypeName(subServiceService.findById((long) 9).get().getName());
		} else if (addMaazounContractRequest.getSubServiceId().equals(StatusConstant.CONTRACT_TALAK_ID)) {
			obj.setBookTypeId(10);
			obj.setBookTypeName(subServiceService.findById((long) 10).get().getName());
		} else if (addMaazounContractRequest.getSubServiceId().equals(StatusConstant.CONTRACT_TASADOK_ID)) {
			obj.setBookTypeId(11);
			obj.setBookTypeName(subServiceService.findById((long) 11).get().getName());
		} else if (addMaazounContractRequest.getSubServiceId().equals(StatusConstant.CONTRACT_MORAGAA_ID)) {
			obj.setBookTypeId(12);
			obj.setBookTypeName(subServiceService.findById((long) 12).get().getName());
		} else if (addMaazounContractRequest.getSubServiceId().equals(StatusConstant.CONTRACT_ZAWAG_MELALY_ID)) {
			obj.setBookTypeId(13);
			obj.setBookTypeName(subServiceService.findById((long) 13).get().getName());
		}

		obj.setSerialNumber(addMaazounContractRequest.getBookSerialNumber());
		obj.setStatusFk(StatusConstant.STATUS_SOLD);

		obj.setContractNumber(addMaazounContractRequest.getContractNumber());

		return maazounBookWarehouseService.save(obj);

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String addReceivedBook(CustomUserDetails customUserDetails,
			AddReceivedMaazounBookRequest addReceivedMaazounBookRequest) {

		Date receivedAt = Date.from(Instant.now());

		Pos pos = getAssignedPOS(customUserDetails.getPosSet(), addReceivedMaazounBookRequest.getSectorId());
		Sector sector = getSectorFromPOSOrThrow(pos, addReceivedMaazounBookRequest.getSectorId());

		String receiptName = (addReceivedMaazounBookRequest.getBookSerialNumber() != null)
				? addReceivedMaazounBookRequest.getBookSerialNumber()
				: addReceivedMaazounBookRequest.getBookFinancialNumber();
		String basePath = bookReceivedPathReport + "/" + UtilCore.saveFolderNamingFormat(String.valueOf(receivedAt));
		String receiptUrl = "/" + receiptName + ConfigurationConstant.MAAZOUN_RECEIVED_REQUEST_NAME + ".pdf";

		MaazounBookWarehouse maazounBookWarehouse = getMaazounBookWarehouse(addReceivedMaazounBookRequest);

		validateBookWarehouseExistence(maazounBookWarehouse, receiptName);

		if (maazounBookWarehouse.getBookInventoryNumber() == null) {

			maazounBookWarehouse = eMaazounInventoryInfoService.incrementCounter(maazounBookWarehouse, sector.getId(),
					receivedAt);
			validateBookInventoryNumbers(maazounBookWarehouse);
			maazounBookWarehouseService.updateBookFinancialNumberInventoryReference(
					maazounBookWarehouse.getBookFinancialNumber(), maazounBookWarehouse.getBookInventoryNumber(),
					maazounBookWarehouse.getBookInventoryReference());
		}

		List<MaazounReceivedReportDTO> eMaazounReceivedReportDTOSet = new ArrayList<MaazounReceivedReportDTO>();
		int i = 1;
		for (AddReceivedMaazounContractRequest eAddReceivedMaazounContractRequest : addReceivedMaazounBookRequest
				.getContractList()) {

			MaazounReceivedReportDTO eMaazounReceivedReportDTO = populateMaazounReceivedReportDTO(pos, receivedAt,
					Util.getCreatedByFullName(customUserDetails), prosecutorLogoUrl, addReceivedMaazounBookRequest,
					eAddReceivedMaazounContractRequest, i, sector,
					maazounBookWarehouse.getMaazounBookRequestInfoFk().getMaazouniaName() != null
							? maazounBookWarehouse.getMaazounBookRequestInfoFk().getMaazouniaName()
							: maazounBookWarehouse.getMaazounBookRequestInfoFk().getMaazounProfileFk()
									.getMaazounMaazouniaChurch().stream().findAny().get().getMaazouniaChurchFk()
									.getName(),
					maazounBookWarehouse.getBookInventoryNumber() + "/"
							+ maazounBookWarehouse.getBookInventoryReference());

			eMaazounReceivedReportDTOSet.add(eMaazounReceivedReportDTO);
			i++;
		}

		GenerateTempBookReceivedEvent generateTempBookReceivedEvent = new GenerateTempBookReceivedEvent(
				eMaazounReceivedReportDTOSet, String.valueOf(receivedAt), receiptUrl);
		applicationEventPublisher.publishEvent(generateTempBookReceivedEvent);

		return cryptoMngrMaazounService.encrypt(basePath.concat(receiptUrl));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String addReceivedBookStatus(CustomUserDetails customUserDetails,
			AddReceivedContractStatusRequest addReceivedContractStatusRequest, MultipartFile attContract) {

		Date receivedAt = Date.from(Instant.now());

		MaazounBookWarehouse maazounBookWarehouse = maazounBookWarehouseService
				.findById(Long.valueOf(addReceivedContractStatusRequest.getMaazounWarehouseId())).get();

		if (maazounBookWarehouse.getMaazounBookCollectionInfoFk() != null) {

			MaazounBookCollectionInfo eMaazounBookCollectionInfo = maazounBookWarehouse
					.getMaazounBookCollectionInfoFk();

			eMaazounBookCollectionInfo.setReceivedStatus(addReceivedContractStatusRequest.getReceivedStatus());
			eMaazounBookCollectionInfo.setReceivedAt(receivedAt);
			eMaazounBookCollectionInfo.setReceivedBy(customUserDetails.getUsername());
			eMaazounBookCollectionInfo.setReceivedComment(addReceivedContractStatusRequest.getReceivedComment());
			eMaazounBookCollectionInfo.setStatusFk(StatusConstant.STATUS_RECEIVED);

			if (attContract != null && !attContract.getOriginalFilename().contains("foo.txt")) {
				String imageUrl;
				try {
					imageUrl = saveReceivedContractStatusAttFile(attContract, Util.dateFormat(),
							eMaazounBookCollectionInfo.getContractNumber());
					eMaazounBookCollectionInfo.setReceivedAttUrl(imageUrl);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			maazounCollectInfoService.save(eMaazounBookCollectionInfo);
		}
		maazounBookWarehouse.setStatusFk(StatusConstant.STATUS_RECEIVED);
		maazounBookWarehouseService.save(maazounBookWarehouse);

		return cryptoMngrMaazounService.encrypt(addReceivedContractStatusRequest.getReceivedStatus());

//		}else {
//			
//			JSONObject jsonResult = new JSONObject();
//			jsonResult.put("response", "للأسف لا يوجد عقد مسجل فى المخازن لهذه العملية");
//			
//			return cryptoMngrMaazounService.encrypt(jsonResult.toString());
//		}

	}

	@Override
	public List<String> getContractsUnderReview(CustomUserDetails customUserDetails) {

		List<String> result = new ArrayList<String>();
		maazounCollectInfoService
				.findByStatusFkAndPosIds(StatusConstant.STATUS_UNDER_REVIEWED, customUserDetails.getPosSet()).stream()
				.forEach(s -> {
					result.add(
							cryptoMngrMaazounService.encrypt(maazounCollectInfoMapper.reviewContractDTO(s).toString()));
				});

		return result;
	}

	@Override
	public PagingDTO getContractCustomAuditBySectors(MaazounAuditRequest maazounAuditRequest,
			CustomUserDetails customUserDetails) {

		Page<MaazounBookAuditSectorDTO> pageResult = maazounCollectInfoService
				.getContractCustomAuditBySectors(maazounAuditRequest, customUserDetails);

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
	public PagingDTO getContractCustomAuditByCourts(MaazounAuditRequest maazounAuditRequest,
			CustomUserDetails customUserDetails) {

		Page<MaazounBookAuditSectorDTO> pageResult = maazounCollectInfoService
				.getContractCustomAuditByCourts(maazounAuditRequest, customUserDetails);

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
	public List<String> getHoldCollectionRequest(CustomUserDetails customUserDetails,
			ContractByNationalIdRequest contractByNationalIdRequest) {

		List<MaazounBookCollectionInfo> maazounBookCollections = maazounCollectInfoService
				.findByStatusFkAndMaazounNationalId(contractByNationalIdRequest.getMaazounNationalId(),
						StatusConstant.STATUS_REVIEWED);

		// remove duplicate collections that have same contract number
		List<MaazounBookCollectionInfo> uniqueCollections = removeDuplicateCollections(maazounBookCollections);

		List<String> result = new ArrayList<String>();

		uniqueCollections.stream().forEach(collectionInfo -> {
			String contractNumber = collectionInfo.getContractNumber();
			MaazounBookWarehouse eMaazounBookWarehouse = maazounBookWarehouseService
					.findByContractNumberOrContractFinancialNumber(contractNumber, contractNumber).orElse(null);

			if (eMaazounBookWarehouse != null) {
				if (eMaazounBookWarehouse.getMaazounBookRequestInfoFk() != null
						&& Long.valueOf(contractByNationalIdRequest.getMaazouniaId()) != null
						&& eMaazounBookWarehouse.getMaazounBookRequestInfoFk().getMaazouniaId()
								.equals(contractByNationalIdRequest.getMaazouniaId())) {

					SubService subService = subServiceService.findById(collectionInfo.getContractTypeId()).orElse(null);
					if (subService != null) {
						MaazounCollectInfoDTO maazounCollectInfoQuota = populateContractQuota(subService,
								collectionInfo.getContractPaidAmount(), collectionInfo.getContractPaidBetweenusFees());

						result.add(cryptoMngrMaazounService.encrypt(maazounCollectInfoMapper
								.previewPayCollectionInfoDTO(collectionInfo, maazounCollectInfoQuota).toString()));

					}

				}
			}

		});

		return result;

	}

	@Override
	public String createRefundContractInfo(CustomUserDetails customUserDetails,
			MaazounContractInfoRefundRequest maazounContractInfoRefundRequest,
			TransactionECRAudit eTransactionECRAudit) {

		Date createdAt = Date.from(Instant.now());
		JSONObject jsonObject = new JSONObject();

		/*
		 * validation settlement code
		 */
//		Optional<PullAccount> pullAccountOptional = pullAccountService.findByStatusFkAndPosIdAndServiceId(
//				StatusConstant.STATUS_NEW, maazounContractInfoRefundRequest.getPosId(),
//				StatusConstant.CONTRACT_SERVICE_ID);

		PullAccount pullAccount = pullAccountService
				.findBySettlementCode(maazounContractInfoRefundRequest.getSettlementCode()).orElse(null);

		if (pullAccount == null) {
			jsonObject.put("response",
					"نأسف نقطة البيع التى يتم عليها استرجاع العملية ليست لديها محفظة مفتوحة لأتمام العملية");
			return cryptoMngrMaazounService.encrypt(jsonObject.toString());
		}

		String pullAccountCreatedBy = pullAccount.getCreatedBy();
		Pos pos = posService.findById(pullAccount.getPosId()).get();
		/*
		 * validation visa number should to be the same of sale request
		 */
		MaazounBookCollectionInfo maazounBookCollectionInfo = maazounCollectInfoService
				.findById(Long.valueOf(maazounContractInfoRefundRequest.getMaazounCollectionInfoIds().get(0))).get();

		if (!maazounContractInfoRefundRequest.getVisaNumber().equals(maazounBookCollectionInfo.getVisaNumber())) {
			jsonObject.put("response",
					"نأسف رقم الفيزا المستخدم فى عملية الأسترجاع غير مطابق مع رقم الفيزا التى استخدم من قبل فى عملية الشراء");
			return cryptoMngrMaazounService.encrypt(jsonObject.toString());
		}

		/*
		 * validation contract not have any contract received should all contracts to be
		 * status Collected
		 */
//		boolean isValidateStatus = false;
//		Set<MaazounBookCollectionInfo> refundMaazounBookCollectionInfoSet = new HashSet<MaazounBookCollectionInfo>();
//		Set<MaazounBookWarehouse> refundMaazounBookWarehouseSet = new HashSet<MaazounBookWarehouse>();
//		for (String collectionInfoId : maazounContractInfoRefundRequest.getMaazounCollectionInfoIds()) {
//
//			MaazounBookCollectionInfo eMaazounBookCollectionInfo = maazounCollectInfoService
//					.findById(Long.valueOf(collectionInfoId)).get();
//
//			MaazounBookWarehouse eMaazounBookWarehouse = maazounBookWarehouseService
//					.findByMaazounBookCollectionInfoFk(eMaazounBookCollectionInfo).get();
//
//			if (!eMaazounBookWarehouse.getStatusFk().equals(StatusConstant.STATUS_COLLECTED)) {
//				isValidateStatus = true;
//				break;
//			} else {
//				refundMaazounBookWarehouseSet.add(eMaazounBookWarehouse);
//			}
//
//			if (isValidateStatus) {
//				refundMaazounBookWarehouseSet.clear();
//				refundMaazounBookCollectionInfoSet.clear();
//				break;
//			} else {
//				refundMaazounBookCollectionInfoSet.add(eMaazounBookCollectionInfo);
//			}
//		}
//
//		if (isValidateStatus) {
//			jsonObject.put("response",
//					"لا يمكن استرجاع العملية نظرا لوجود عقود حالاتها غير قابلة للتنفيذ يجب ان تكون حالتها محصلة");
//			return cryptoMngrMaazounService.encrypt(jsonObject.toString());
//		}

		for (String collectionInfoId : maazounContractInfoRefundRequest.getMaazounCollectionInfoIds()) {

			MaazounBookCollectionInfo eMaazounBookCollectionInfo = maazounCollectInfoService
					.findById(Long.valueOf(collectionInfoId)).get();

			TransactionRefundRequest transactionRefundRequest = populateTransactionRefundRequest(
					maazounContractInfoRefundRequest.getAttRefund(), pullAccount.getSettlementCode(),
					eMaazounBookCollectionInfo.getTransactionCode(), pullAccountCreatedBy,
					maazounContractInfoRefundRequest.getComment(), maazounContractInfoRefundRequest.getPaymentCode(),
					maazounContractInfoRefundRequest.getPaymentEcrResponse(),
					maazounContractInfoRefundRequest.getPaymentEcrRequest());

			TransactionResponseDTO eTransactionResponseDTO = transactionManagement
					.refundTransaction(transactionRefundRequest);
			if (eTransactionResponseDTO != null) {

				eMaazounBookCollectionInfo.setStatusFk(StatusConstant.STATUS_CANCELED);
				eMaazounBookCollectionInfo.setUpdatedAt(createdAt);
				eMaazounBookCollectionInfo.setUpdatedBy(customUserDetails.getUsername());
				maazounCollectInfoService.save(eMaazounBookCollectionInfo);

				String attachedFile = null;
				try {
					attachedFile = saveBrowseAttFile(maazounContractInfoRefundRequest.getAttRefund(),
							bookCollectionInfoPathAtt, "REFUND_" + eMaazounBookCollectionInfo.getRefRequestNumber());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				MaazounBookCollectionInfo refundMaazounBookCollectionInfo = populateRefundMaazounCollectionInfo(
						eMaazounBookCollectionInfo, eTransactionResponseDTO.getTransactionCode(),
						customUserDetails.getUsername(), createdAt, pos,
						maazounContractInfoRefundRequest.getVisaNumber(), eMaazounBookCollectionInfo.getSectorFk(),
						attachedFile, eMaazounBookCollectionInfo.getRefRequestNumber());

				MaazounBookCollectionInfo eRefundMaazounBookCollectionInfo = maazounCollectInfoService
						.save(refundMaazounBookCollectionInfo);

				MaazounContractRefundAtt eMaazounRefundAtt = new MaazounContractRefundAtt();
				eMaazounRefundAtt.setMaazounBookCollectionInfoId(eRefundMaazounBookCollectionInfo.getId());
				eMaazounRefundAtt.setRefundTransactionCode(eRefundMaazounBookCollectionInfo.getTransactionCode());
				eMaazounRefundAtt.setRefundUrl(eTransactionResponseDTO.getRefundUrl());

				maazounContractRefundAttService.save(eMaazounRefundAtt);

				MaazounBookWarehouse eMaazounBookWarehouse = maazounBookWarehouseService
						.findByContractNumberOrContractFinancialNumber(eMaazounBookCollectionInfo.getContractNumber(),
								eMaazounBookCollectionInfo.getContractNumber())
						.orElse(null);
				if (eMaazounBookWarehouse != null) {
					eMaazounBookWarehouse.setStatusFk(StatusConstant.STATUS_SOLD);
					maazounBookWarehouseService.save(eMaazounBookWarehouse);
				}

			}

		}

		JSONObject jsonResult = new JSONObject();
		jsonResult.put("response", "تم عملية الأسترجاع بنجاح");

		return cryptoMngrMaazounService.encrypt(jsonResult.toString());

	}

	@Override
	public String editContractData(CustomUserDetails customUserDetails,
			EditMaazounContractRequest editMaazounContractRequest) {

		Date updatedAt = Date.from(Instant.now());

		MaazounBookCollectionInfo maazounBookCollectionInfo = maazounCollectInfoService
				.findById(Long.valueOf(editMaazounContractRequest.getId())).get();

		contractDataValidation(editMaazounContractRequest.getHusbandNationalId(),
				editMaazounContractRequest.getWifeNationalId(), editMaazounContractRequest.getHusbandName(),
				editMaazounContractRequest.getWifeName());

		MaazounBookCollectionInfo eMaazounBookCollectionInfo = populateMaazounCollectionInfo(maazounBookCollectionInfo,
				editMaazounContractRequest, customUserDetails.getUsername(), updatedAt, bookCollectionInfoPathAtt);

		MaazounBookCollectionInfo maazounBookCollectionInfoFk = maazounCollectInfoService
				.save(eMaazounBookCollectionInfo);

		if (maazounBookCollectionInfoFk.getRefRequestNumber() != null
				&& !maazounBookCollectionInfoFk.getRefRequestNumber().isEmpty()) {

			if (maazounBookCollectionInfoFk.getReceiptUrl() != null) {
				File pdfFile = new File(maazounBookCollectionInfoFk.getReceiptUrl());
				pdfFile.delete();
			}

//			String basePath = bookCollectionInfoPathReport + "/"
//					+ UtilCore.saveFolderNamingFormat(String.valueOf(maazounBookCollectionInfo.getCreatedAt()));
//			String receiptUrl = "/" + maazounBookCollectionInfo.getRefRequestNumber()
//					+ ConfigurationConstant.MAAZOUN_BOOK_COLLECTION_REPORT_NAME + ".pdf";
//
//			maazounCollectInfoService.editCollection(basePath.concat(receiptUrl),
//					maazounBookCollectionInfoFk.getRefRequestNumber());

		}

		JSONObject jsonResult = new JSONObject();
		jsonResult.put("response", "Edit Maazoun Contract Process Successfully");

		return cryptoMngrMaazounService.encrypt(jsonResult.toString());
	}

	@Override
	public String calculateSalePosMidRequestAmountFormat(PosMidRequest posMidRequest,
			CustomUserDetails customUserDetails) {
		Date createdAt = Date.from(Instant.now());
		Pos pos = getAssignedPOS(customUserDetails.getPosSet(), posMidRequest.getSectorId());
		Sector sector = pos.getSectors().stream().filter(x -> x.getId().equals(posMidRequest.getSectorId())).findAny()
				.orElse(null);

		String refRequestNumber = sector.getLocationFk().getId() + "-"
				+ new SimpleDateFormat("yyDkmsS").format(createdAt);

		Set<TransactionMidsDTO> transactionMids = new HashSet<TransactionMidsDTO>();
		for (String collectionInfoId : posMidRequest.getMaazounBookFinancialNumner()) {

			MaazounBookCollectionInfo eMaazounBookCollectionInfo = maazounCollectInfoService
					.findById(Long.valueOf(collectionInfoId)).get();

			SubService subService = subServiceService.findById(eMaazounBookCollectionInfo.getContractTypeId()).get();

			transactionMids.addAll(
					calculateMids(transactionMids, subService, eMaazounBookCollectionInfo.getContractPaidAmount(),
							eMaazounBookCollectionInfo.getContractPaidBetweenusFees()));

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

		String refRequestNumberTimestamp = new SimpleDateFormat("sS").format(createdAt);

		Set<TransactionMidsDTO> transactionMids = new HashSet<TransactionMidsDTO>();
		for (String collectionInfoId : posMidRequest.getMaazounBookFinancialNumner()) {

			MaazounBookCollectionInfo eMaazounBookCollectionInfo = maazounCollectInfoService
					.findById(Long.valueOf(collectionInfoId)).get();

			SubService subService = subServiceService.findById(eMaazounBookCollectionInfo.getContractTypeId()).get();

			transactionMids.addAll(
					calculateMids(transactionMids, subService, eMaazounBookCollectionInfo.getContractPaidAmount(),
							eMaazounBookCollectionInfo.getContractPaidBetweenusFees()));

		}

		JSONObject jsonObject = new JSONObject();
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
			double contractPaidAmount, double contractPaidBetweenusFees) {

		for (SubServiceQuota mid : subService.getSubServicesQuota()) {

//			String midAccount = cryptoMngrMaazounService.decrypt(mid.getMidAccount());
//	        TransactionMidsDTO eTransactionMids = findMidsByAccount(transactionMids, midAccount);
//	        if (mid.getStatusFk().equals(StatusConstant.STATUS_ACTIVE)) {
//	            if (mid.getFeesType().equals("n")) {
//	                double fees = mid.getFees();
//	                updateMids(transactionMids, midAccount, mid, fees);
//	            } else if (mid.getFeesType().equals("%")) {
//	                double percentFees = calculatePercentFees(contractPaidAmount, contractPaidBetweenusFees, mid);
//	                updateMids(transactionMids, midAccount, mid, percentFees);
//	            }
//	        }

			TransactionMidsDTO eTransactionMids = transactionMids.stream()
					.filter(x -> x.getMidAccount().equals(cryptoMngrMaazounService.decrypt(mid.getMidAccount())))
					.findAny().orElse(null);

			if (mid.getStatusFk().equals(StatusConstant.STATUS_ACTIVE) && mid.getFeesType().equals("n")) {

				if (eTransactionMids == null) {
					TransactionMidsDTO eTransactionMidFkQouta = new TransactionMidsDTO();
					eTransactionMidFkQouta.setMidAccount(cryptoMngrMaazounService.decrypt(mid.getMidAccount()));
					eTransactionMidFkQouta.setMidAccountEnc(mid.getMidAccount());
					eTransactionMidFkQouta.setMidValue(mid.getFees());
					eTransactionMidFkQouta.setMidBank(mid.getMidBank());
					eTransactionMidFkQouta.setBeneficiary(mid.getBeneficiary());
					transactionMids.add(eTransactionMidFkQouta);
				} else {
					eTransactionMids.setMidValue(eTransactionMids.getMidValue() + mid.getFees());
				}

			} else if (mid.getStatusFk().equals(StatusConstant.STATUS_ACTIVE) && mid.getFeesType().equals("%")) {

				double perecntFirstThousand = 0;
				double perecntRemainingAmount = 0;
				double subtractBetweenusFromContractPaidAmount = contractPaidAmount - contractPaidBetweenusFees;

				if (eTransactionMids == null) {
					TransactionMidsDTO eTransactionMidFkQouta = new TransactionMidsDTO();
					eTransactionMidFkQouta.setMidAccount(cryptoMngrMaazounService.decrypt(mid.getMidAccount()));
					eTransactionMidFkQouta.setMidAccountEnc(mid.getMidAccount());

					if (subtractBetweenusFromContractPaidAmount > 0 && mid.getFees() == 1.5) {
						perecntFirstThousand = Util.calculateFirstThousandAndRemainingContractPaid(
								subtractBetweenusFromContractPaidAmount, mid.getFees());
					} else if (subtractBetweenusFromContractPaidAmount > 0 && mid.getFees() == 2) {
						perecntRemainingAmount = Util.calculateSecondRemainingContractPaid(
								subtractBetweenusFromContractPaidAmount, mid.getFees());
					}

					eTransactionMidFkQouta
							.setMidValue(perecntFirstThousand + perecntRemainingAmount + contractPaidBetweenusFees);
					eTransactionMidFkQouta.setMidBank(mid.getMidBank());
					eTransactionMidFkQouta.setBeneficiary(mid.getBeneficiary());
					transactionMids.add(eTransactionMidFkQouta);

				} else {

					if (mid.getFees() == 1.5) {
						perecntFirstThousand = Util.calculateFirstThousandAndRemainingContractPaid(
								subtractBetweenusFromContractPaidAmount, mid.getFees());
					} else if (mid.getFees() == 2) {
						perecntRemainingAmount = Util.calculateSecondRemainingContractPaid(
								subtractBetweenusFromContractPaidAmount, mid.getFees());
					}

					eTransactionMids.setMidValue(
							eTransactionMids.getMidValue() + perecntFirstThousand + perecntRemainingAmount);
				}

			}

		}

		return transactionMids;
	}

	private TransactionMidsDTO findMidsByAccount(Set<TransactionMidsDTO> transactionMids, String midAccount) {
		return transactionMids.stream().filter(x -> x.getMidAccount().equals(midAccount)).findAny().orElse(null);
	}

	private double calculatePercentFees(double contractPaidAmount, double contractPaidBetweenusFees,
			SubServiceQuota mid, Set<TransactionMidsDTO> transactionMids, String midAccount) {
		double remainingAmount = contractPaidAmount - contractPaidBetweenusFees;
		double percentFees = 0;
		double perecntFirstThousand = 0;
		double perecntRemainingAmount = 0;
		TransactionMidsDTO eTransactionMids = findMidsByAccount(transactionMids, midAccount);

		if (eTransactionMids == null) {
			if (mid.getFees() == 1.5) {
				perecntFirstThousand = Util.calculateFirstThousandAndRemainingContractPaid(remainingAmount,
						mid.getFees());
			} else if (mid.getFees() == 2) {
				perecntRemainingAmount = Util.calculateSecondRemainingContractPaid(remainingAmount, mid.getFees());
			}
			percentFees = perecntFirstThousand + perecntRemainingAmount + contractPaidBetweenusFees;
		} else {
			if (mid.getFees() == 1.5) {
				perecntFirstThousand = Util.calculateFirstThousandAndRemainingContractPaid(remainingAmount,
						mid.getFees());
			} else if (mid.getFees() == 2) {
				perecntRemainingAmount = Util.calculateSecondRemainingContractPaid(remainingAmount, mid.getFees());
			}
			percentFees = perecntFirstThousand + perecntRemainingAmount;
		}
		return percentFees;
	}

	private void updateMids(Set<TransactionMidsDTO> transactionMids, String midAccount, SubServiceQuota mid,
			double fees) {
		TransactionMidsDTO eTransactionMids = findMidsByAccount(transactionMids, midAccount);
		if (eTransactionMids == null) {
			eTransactionMids = new TransactionMidsDTO();
			eTransactionMids.setMidAccount(midAccount);
			eTransactionMids.setMidAccountEnc(mid.getMidAccount());
			eTransactionMids.setMidValue(fees);
			eTransactionMids.setMidBank(mid.getMidBank());
			eTransactionMids.setBeneficiary(mid.getBeneficiary());
			transactionMids.add(eTransactionMids);
		} else {
			eTransactionMids.setMidValue(eTransactionMids.getMidValue() + fees);
		}
	}

	private MaazounBookWarehouse getInventoryNumber(MaazounBookWarehouse maazounBookWarehouse,
			AddReceivedMaazounBookRequest addReceivedMaazounBookRequest, Date receivedAt) {

		long bookInventoryNumber = 0;
		long year = Year.now().getValue();
		String typeId = null;

		if (String.valueOf(maazounBookWarehouse.getBookTypeId()).equals(StatusConstant.BOOK_ZAWAG_MUSLIM_ID_8)
				|| String.valueOf(maazounBookWarehouse.getBookTypeId()).equals(StatusConstant.BOOK_TASADOK_ID_8)
				|| String.valueOf(maazounBookWarehouse.getBookTypeId()).equals(StatusConstant.BOOK_MORAGAA_ID_8)
				|| String.valueOf(maazounBookWarehouse.getBookTypeId()).equals(StatusConstant.BOOK_ZAWAG_MUSLIM_ID_15)
				|| String.valueOf(maazounBookWarehouse.getBookTypeId()).equals(StatusConstant.BOOK_TASADOK_ID_15)
				|| String.valueOf(maazounBookWarehouse.getBookTypeId()).equals(StatusConstant.BOOK_MORAGAA_ID_15)) {
			typeId = StatusConstant.BOOK_ZAWAG_MUSLIM_ID_8 + "-" + StatusConstant.BOOK_TASADOK_ID_8 + "-"
					+ StatusConstant.BOOK_MORAGAA_ID_8; // 9-11-12
		} else if (String.valueOf(maazounBookWarehouse.getBookTypeId()).equals(StatusConstant.BOOK_ZAWAG_MELALY_ID_8)
				|| String.valueOf(maazounBookWarehouse.getBookTypeId())
						.equals(StatusConstant.BOOK_ZAWAG_MELALY_ID_15)) {
			typeId = StatusConstant.BOOK_ZAWAG_MELALY_ID_8; // 13
		} else if (String.valueOf(maazounBookWarehouse.getBookTypeId()).equals(StatusConstant.BOOK_TALAK_ID_8)
				|| String.valueOf(maazounBookWarehouse.getBookTypeId()).equals(StatusConstant.BOOK_TALAK_ID_15)) {
			typeId = StatusConstant.BOOK_TALAK_ID_8; // 10
		}

		Optional<MaazounInventoryNumber> inventoryNumberOptional = maazounInventoryNumberService
				.findBySectorIdAndTypeIdAndYear(addReceivedMaazounBookRequest.getSectorId(), typeId, year);

		if (inventoryNumberOptional.isPresent()) {
			MaazounInventoryNumber eMaazounInventoryNumber = inventoryNumberOptional.get();

			bookInventoryNumber = eMaazounInventoryNumber.getInventorySequance() + 1;

			eMaazounInventoryNumber.setInventorySequance(bookInventoryNumber);
			eMaazounInventoryNumber.setUpdatedAt(receivedAt);
			maazounInventoryNumberService.save(eMaazounInventoryNumber);
		} else {
			MaazounInventoryNumber eMaazounInventoryNumber = new MaazounInventoryNumber();
			eMaazounInventoryNumber.setInventorySequance((long) 1);
			eMaazounInventoryNumber.setSectorId(addReceivedMaazounBookRequest.getSectorId());
			eMaazounInventoryNumber.setTypeId(typeId);
			eMaazounInventoryNumber.setYear(year);
			eMaazounInventoryNumber.setUpdatedAt(receivedAt);
			maazounInventoryNumberService.save(eMaazounInventoryNumber);

			bookInventoryNumber = 1;
		}

		maazounBookWarehouse.setBookInventoryNumber(bookInventoryNumber);
		maazounBookWarehouse.setBookInventoryReference(year);

		return maazounBookWarehouse;

	}

	private List<MaazounBookCollectionInfo> removeDuplicateCollections(
			List<MaazounBookCollectionInfo> maazounBookCollectionList) {

		Set<String> uniqueContractNumbers = new HashSet<>();
		List<MaazounBookCollectionInfo> result = new ArrayList<>();

		for (MaazounBookCollectionInfo t : maazounBookCollectionList) {

			String contractNumber = t.getContractNumber();

			if (uniqueContractNumbers.add(contractNumber)) {
				if (checkHasAssocciatedFkInWarehouse(t)) {
					result.add(t);
				} else {
					maazounCollectInfoService.deleteById(t.getId());
				}
			}
		}

		return result;
	}

	private boolean checkHasAssocciatedFkInWarehouse(MaazounBookCollectionInfo maazounBookCollectionInfo) {
		Set<MaazounBookWarehouse> maazounBookWarehouseSet = maazounBookCollectionInfo.getMaazounBookWarehouseSet();
		return maazounBookWarehouseSet != null && !maazounBookWarehouseSet.isEmpty();
	}

	private List<ContractListRequest> removeDuplicateContracts(List<ContractListRequest> contractListRequest) {

		Set<Long> uniqueCollectionInfoIds = new HashSet<>();
		List<ContractListRequest> result = new ArrayList<>();

		for (ContractListRequest t : contractListRequest) {

			Long collectionInfoId = t.getCollectionInfoId();
			if (uniqueCollectionInfoIds.add(collectionInfoId)) {
				result.add(t);
			}

		}

		return result;
	}

	@Override
	public MaazounCollectInfoPagingDTO getContractsListByDateAndStatus(CustomUserDetails customUserDetails,
			CollectionListSearchRequest collectionListSearchRequest) {

//		if(!customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AREA_MANAGER)){
//			throw new IllegalArgumentException("نأسف هذه الخدمة تقتصر فقط على مديري المناطق");
//		}

		Pageable pageable = PageRequest.of(Integer.valueOf(collectionListSearchRequest.getPageNo()),
				Integer.valueOf(collectionListSearchRequest.getPageSize()));

		Page<MaazounBookCollectionInfo> pageResult = maazounCollectInfoService.findByStatusFkAndPosIdsAndDate(
				collectionListSearchRequest.getStatus(), customUserDetails.getPosSet(),
				UtilCore.convertStringToEndDateFormatSql(collectionListSearchRequest.getDurationFrom()),
				UtilCore.convertStringToEndDateFormatSql(collectionListSearchRequest.getDurationTo()), pageable);

		MaazounCollectInfoPagingDTO eMaazounCollectInfoPagingDTO = new MaazounCollectInfoPagingDTO();
		eMaazounCollectInfoPagingDTO.setCount(pageResult.getTotalElements());
		eMaazounCollectInfoPagingDTO.setTotalPages(pageResult.getTotalPages());

		List<String> eTransaction = new ArrayList<String>();
		pageResult.getContent().stream().forEach(s -> {
			eTransaction
					.add(cryptoMngrMaazounService.encrypt(maazounCollectInfoMapper.reviewContractDTO(s).toString()));
		});

		eMaazounCollectInfoPagingDTO.setContracts(eTransaction);

		return eMaazounCollectInfoPagingDTO;
	}

	private void contractDataValidation(String husbandNationalId, String wifeNationalId, String husbandName,
			String wifeName) {
		validateApplicantAge(husbandNationalId,
				"نأسف لأتمام التوثيق بسبب أن عمر الزوج تحت السن القانوني أقل من 18 عام");
		validateApplicantAge(wifeNationalId, "نأسف لأتمام التوثيق بسبب أن عمر الزوجة تحت السن القانوني أقل من 18 عام");
		validateNationalIdsNotSame(husbandNationalId, wifeNationalId,
				"نأسف لأتمام التوثيق بسبب أن الرقم القومي للزوج والزوجة متشابهان");
		validateNamesNotSame(husbandName, wifeName, "نأسف لأتمام التوثيق بسبب أن اسم الزوج والزوجة متشابهان");
	}

	private void validateApplicantAge(String nationalId, String errorMessage) {
		if (!Util.validateApplicantAge(nationalId)) {
			throw new IllegalArgumentException(errorMessage);
		}
	}

	private void validateNationalIdsNotSame(String husbandNationalId, String wifeNationalId, String errorMessage) {
		if (husbandNationalId.equals(wifeNationalId)) {
			throw new IllegalArgumentException(errorMessage);
		}
	}

	private void validateNamesNotSame(String husbandName, String wifeName, String errorMessage) {
		if (husbandName.equals(wifeName)) {
			throw new IllegalArgumentException(errorMessage);
		}
	}

	// Helper method to get SubService by ID or throw an exception if not found
	private SubService getSubServiceOrThrow(long subServiceId) {
		return subServiceService.findById(subServiceId)
				.orElseThrow(() -> new ResourceNotFoundException("SubService not found"));
	}

	// Helper method to get the MaazounBookWarehouse by ID or throw an exception if
	// not found
	private MaazounBookWarehouse getMaazounBookWarehouseOrThrow(long bookWarehouseId) {
		return maazounBookWarehouseService.findById(bookWarehouseId)
				.orElseThrow(() -> new IllegalArgumentException("Maazoun Book Warehouse not found"));
	}

	private String getRefRequestNumber(String paymentEcrRequest) {
		JSONObject jsonBody = new JSONObject(paymentEcrRequest);
		return jsonBody.getString("ecr_ref");
	}

	private TransactionECR createTransactionECRIfPresent(
			AddMaazounCollectionInfoRequest addMaazounCollectionInfoRequest) {
		if (addMaazounCollectionInfoRequest.getPaymentEcrResponse() != null
				&& !addMaazounCollectionInfoRequest.getPaymentEcrResponse().isEmpty()) {
			return transactionECRService
					.save(new TransactionECR(addMaazounCollectionInfoRequest.getPaymentEcrResponse(),
							addMaazounCollectionInfoRequest.getPaymentEcrRequest()));
		}
		return null;
	}

	private MaazounBookCollectionInfo getMaazounBookCollectionInfoById(Long collectionInfoId) {
		return maazounCollectInfoService.findById(collectionInfoId)
				.orElseThrow(() -> new ResourceNotFoundException("MaazounBookCollectionInfo not found"));
	}

	@Override
	public List<String> getContractsByMaazounAndMaazounia(CustomUserDetails customUserDetails,
			ContractByNationalIdRequest contractByNationalIdRequest) {
		List<MaazounBookCollectionInfo> maazounBookCollections = maazounCollectInfoService
				.findByMaazounNationalIdAndStatusFkOrStatusFkOrStatusFk(
						contractByNationalIdRequest.getMaazounNationalId(), StatusConstant.STATUS_REVIEWED,
						StatusConstant.STATUS_UNDER_REVIEWED, StatusConstant.STATUS_REVIEW_REJECTED);

		// remove duplicate collections that have same contract number
		List<MaazounBookCollectionInfo> uniqueCollections = removeDuplicateCollections(maazounBookCollections);

		List<String> result = new ArrayList<String>();

		uniqueCollections.stream().forEach(collectionInfo -> {
			String contractNumber = collectionInfo.getContractNumber();
			MaazounBookWarehouse eMaazounBookWarehouse = maazounBookWarehouseService
					.findByContractNumberOrContractFinancialNumber(contractNumber, contractNumber).orElse(null);

			if (eMaazounBookWarehouse != null) {
				if (eMaazounBookWarehouse.getMaazounBookRequestInfoFk() != null
						&& Long.valueOf(contractByNationalIdRequest.getMaazouniaId()) != null
						&& eMaazounBookWarehouse.getMaazounBookRequestInfoFk().getMaazouniaId()
								.equals(contractByNationalIdRequest.getMaazouniaId())) {

					SubService subService = subServiceService.findById(collectionInfo.getContractTypeId()).orElse(null);
					if (subService != null) {
						MaazounCollectInfoDTO maazounCollectInfoQuota = populateContractQuota(subService,
								collectionInfo.getContractPaidAmount(), collectionInfo.getContractPaidBetweenusFees());

						result.add(cryptoMngrMaazounService.encrypt(maazounCollectInfoMapper
								.previewPayCollectionInfoDTO(collectionInfo, maazounCollectInfoQuota).toString()));

					}

				}
			}

		});

		return result;
	}

	private MaazounBookWarehouse getMaazounBookWarehouse(AddReceivedMaazounBookRequest request) {
		return (request.getBookSerialNumber() != null)
				? maazounBookWarehouseService.findBySerialNumber(request.getBookSerialNumber()).get(0)
				: maazounBookWarehouseService.findByBookFinancialNumber(request.getBookFinancialNumber()).get(0);
	}

	private void validateBookWarehouseExistence(MaazounBookWarehouse bookWarehouse, String receiptName) {
		if (bookWarehouse == null) {
			throw new IllegalArgumentException(
					"هذا الرقم المالى/الألكترونى " + receiptName + " للدفتر غير موجود بالمخزن");
		}
	}

	private void validateBookInventoryNumbers(MaazounBookWarehouse bookWarehouse) {
		if (bookWarehouse.getBookInventoryNumber() == null || bookWarehouse.getBookInventoryReference() == null) {
			throw new IllegalArgumentException(
					"يوجد مشكلة فى رقم الحصر من فضلك حاول الضغط على أمر إستلام الدفتر مرة أخري");
		}
	}

}

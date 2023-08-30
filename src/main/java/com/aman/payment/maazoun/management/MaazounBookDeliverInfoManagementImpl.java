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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.auth.model.Pos;
import com.aman.payment.auth.model.Sector;
import com.aman.payment.auth.model.User;
import com.aman.payment.auth.service.impl.UserService;
import com.aman.payment.core.exception.TransactionException;
import com.aman.payment.core.util.UtilCore;
import com.aman.payment.maazoun.event.GenerateTempBookDeliverInfoEvent;
import com.aman.payment.maazoun.mapper.MaazounCollectInfoMapper;
import com.aman.payment.maazoun.mapper.MaazounDeliverInfoMapper;
import com.aman.payment.maazoun.model.MaazounBookCollectionInfo;
import com.aman.payment.maazoun.model.MaazounBookDeliverInfo;
import com.aman.payment.maazoun.model.MaazounBookRequestInfo;
import com.aman.payment.maazoun.model.MaazounBookWarehouse;
import com.aman.payment.maazoun.model.dto.MaazounDeliverInfoPagingDTO;
import com.aman.payment.maazoun.model.dto.MaazounDeliverInfoReportDTO;
import com.aman.payment.maazoun.model.payload.AddMaazounDeliverInfoRequest;
import com.aman.payment.maazoun.model.payload.CloseMaazounDeliverInfoRequest;
import com.aman.payment.maazoun.model.payload.ContractDeliverRequest;
import com.aman.payment.maazoun.model.payload.GetContractRequest;
import com.aman.payment.maazoun.model.payload.SearchDeliveredInfoTransactionRequest;
import com.aman.payment.maazoun.model.payload.URLRequest;
import com.aman.payment.maazoun.service.CryptoMngrMaazounService;
import com.aman.payment.maazoun.service.MaazounBookWarehouseService;
import com.aman.payment.maazoun.service.MaazounCollectionInfoService;
import com.aman.payment.maazoun.service.MaazounDeliverInfoService;
import com.aman.payment.util.ConfigurationConstant;
import com.aman.payment.util.StatusConstant;
import com.aman.payment.util.Util;

@Component
public class MaazounBookDeliverInfoManagementImpl extends ValidationAndPopulateManagement implements MaazounBookDeliverInfoManagement {

	final static Logger logger = Logger.getLogger("maazoun");

	private final MaazounCollectionInfoService maazounCollectInfoService;
	private final CryptoMngrMaazounService cryptoMngrMaazounService;
	private final MaazounCollectInfoMapper maazounCollectInfoMapper;
	private final ApplicationEventPublisher applicationEventPublisher;
	private final MaazounDeliverInfoService maazounDeliverInfoService;
	private final MaazounDeliverInfoMapper maazounDelivernfoMapper;
	private final MaazounBookWarehouseService maazounBookWarehouseService;
	private final UserService userService;

	@Value("${attachment.maazoun.delivered}")
	private String bookDeliverPathAtt;

	@Value("${reports.maazoun.delivered}")
	private String bookDeliverPathReport;

	@Value("${aman.logo}")
	private String amanLogoUrl;
	
	@Value("${prosecutor.logo}")
	private String prosecutorLogoUrl;

	@Autowired
	public MaazounBookDeliverInfoManagementImpl(MaazounCollectionInfoService maazounCollectInfoService,
			CryptoMngrMaazounService cryptoMngrMaazounService, MaazounCollectInfoMapper maazounCollectInfoMapper,
			ApplicationEventPublisher applicationEventPublisher,
			MaazounBookWarehouseService maazounBookWarehouseService,
			MaazounDeliverInfoService maazounDeliverInfoService, MaazounDeliverInfoMapper maazounDelivernfoMapper,
			UserService userService) {
		super();
		this.maazounCollectInfoService = maazounCollectInfoService;
		this.cryptoMngrMaazounService = cryptoMngrMaazounService;
		this.maazounCollectInfoMapper = maazounCollectInfoMapper;
		this.applicationEventPublisher = applicationEventPublisher;
		this.maazounDeliverInfoService = maazounDeliverInfoService;
		this.maazounDelivernfoMapper = maazounDelivernfoMapper;
		this.maazounBookWarehouseService = maazounBookWarehouseService;
		this.userService = userService;
	}

	@Override
	public List<String> getReviewedContract(CustomUserDetails customUserDetails,
			GetContractRequest decryptGetContractRequest) {

		List<String> eTransaction = new ArrayList<String>();

		if (decryptGetContractRequest.getBookSerialNumber() != null
				&& !decryptGetContractRequest.getBookSerialNumber().isEmpty()) {
			maazounCollectInfoService.findByBookSerialNumber(decryptGetContractRequest.getBookSerialNumber(),
					StatusConstant.STATUS_REVIEWED).stream().forEach(s -> {
						eTransaction.add(cryptoMngrMaazounService.encrypt(maazounCollectInfoMapper
								.maazounCollectionInfoToMaazounCollectionInfoDTO(null, s, null, null, null).toString()));
					});

		} else if (decryptGetContractRequest.getContractNumber() != null
				&& !decryptGetContractRequest.getContractNumber().isEmpty()) {
			Optional<MaazounBookCollectionInfo> collectionObj = maazounCollectInfoService.findByContractNumber(
					decryptGetContractRequest.getContractNumber(), StatusConstant.STATUS_REVIEWED);
			if (collectionObj.isPresent()) {
				eTransaction.add(cryptoMngrMaazounService.encrypt(maazounCollectInfoMapper
						.maazounCollectionInfoToMaazounCollectionInfoDTO(null, collectionObj.get(), null, null, null).toString()));

			}

		}

		return eTransaction;

	}
	
	@Override
	public List<String> getReceivedContract(CustomUserDetails customUserDetails,
			GetContractRequest decryptGetContractRequest) {

		List<String> eTransaction = new ArrayList<String>();

		if (decryptGetContractRequest.getBookSerialNumber() != null
				&& !decryptGetContractRequest.getBookSerialNumber().isEmpty()) {
			maazounCollectInfoService.findByBookSerialNumber(decryptGetContractRequest.getBookSerialNumber(),
					StatusConstant.STATUS_RECEIVED).stream().forEach(s -> {
						eTransaction.add(cryptoMngrMaazounService.encrypt(maazounCollectInfoMapper
								.maazounCollectionInfoToMaazounCollectionInfoDTO(null, s, null, null, null).toString()));
					});

		}

		return eTransaction;

	}

	@Override
	public String createDeliverRequest(CustomUserDetails customUserDetails,
			AddMaazounDeliverInfoRequest addMaazounDeliverInfoRequest) {
		
		Date createdAt = Date.from(Instant.now());
		
		Pos pos = getAssignedPOS(customUserDetails.getPosSet(), addMaazounDeliverInfoRequest.getSectorId());
		
		Sector sector = pos.getSectors().stream()
				.filter(x -> x.getId().equals(addMaazounDeliverInfoRequest.getSectorId())).findAny()
				.orElse(null);

		String refRequestNumber = sector.getLocationFk().getId() + "-" + new SimpleDateFormat("yyDkmsS").format(createdAt);
		String basePath = bookDeliverPathReport + "/" + UtilCore.saveFolderNamingFormat(String.valueOf(createdAt));
		String receiptUrl = "/" + refRequestNumber + ConfigurationConstant.MAAZOUN_BOOK_DELIVER_REPORT_NAME + ".pdf";

		MaazounBookDeliverInfo eMaazounBookDeliverInfo = populateMaazounBookDeliverInfo(
				createdAt, customUserDetails.getUsername(), pos, basePath.concat(receiptUrl), 
				StatusConstant.STATUS_PENDING, refRequestNumber, sector);
		
		eMaazounBookDeliverInfo = maazounDeliverInfoService.save(eMaazounBookDeliverInfo);

		List<MaazounDeliverInfoReportDTO> eMaazounCollectInfoReportDTOSet = new ArrayList<MaazounDeliverInfoReportDTO>();
		int i = 1;
		for (ContractDeliverRequest eContractDeliverRequest : addMaazounDeliverInfoRequest
				.getBooksDeliverRequest()) {

			MaazounDeliverInfoReportDTO eMaazounCollectInfoReportDTO = populateMaazounDeliverInfoReportDTO(
					prosecutorLogoUrl, eContractDeliverRequest, pos, refRequestNumber, createdAt, 
					Util.getCreatedByFullName(customUserDetails), i, sector);
			
			maazounBookWarehouseService.updateStatusAndBookDeliveryByBookFinancialNumber(StatusConstant.STATUS_UNDERDELIVERY,
					eMaazounBookDeliverInfo, eContractDeliverRequest.getBookFinancialNumber());

			eMaazounCollectInfoReportDTOSet.add(eMaazounCollectInfoReportDTO);
			i++;
		}

		GenerateTempBookDeliverInfoEvent generateTempBookDeliverInfoEvent = new GenerateTempBookDeliverInfoEvent(
				eMaazounCollectInfoReportDTOSet, String.valueOf(createdAt), receiptUrl, 0);
		applicationEventPublisher.publishEvent(generateTempBookDeliverInfoEvent);

		return cryptoMngrMaazounService.encrypt(basePath.concat(receiptUrl));
	}
	
	public void reGenerateDeliveryReport(String refRequestNumber){
		
		List<MaazounBookDeliverInfo> results = maazounDeliverInfoService.findByRefRequestNumber(refRequestNumber);
		MaazounBookDeliverInfo maazounBookDeliverInfo = results.stream().findFirst().get();
		User user = userService.findByUsername(maazounBookDeliverInfo.getCreatedBy()).get();
		String receiptUrl = maazounBookDeliverInfo.getReceiptUrl();
		Date createdAt = maazounBookDeliverInfo.getCreatedAt();
		
		List<MaazounDeliverInfoReportDTO> eMaazounCollectInfoReportDTOSet = new ArrayList<MaazounDeliverInfoReportDTO>();
		int i = 1;
		for (MaazounBookDeliverInfo eMaazounBookDeliverInfo : results) {

			MaazounBookWarehouse eMaazounBookWarehouse = eMaazounBookDeliverInfo.getMaazounBookWarehouseSet().stream().findFirst().get();
			MaazounBookRequestInfo eMaazounBookRequestInfo = eMaazounBookWarehouse.getMaazounBookRequestInfoFk();
			
			ContractDeliverRequest eContractDeliverRequest = new ContractDeliverRequest();
			eContractDeliverRequest.setBookFinancialNumber(eMaazounBookWarehouse.getBookFinancialNumber());
			eContractDeliverRequest.setBookInventoryReference(eMaazounBookWarehouse.getBookInventoryNumber()+"/"+eMaazounBookWarehouse.getBookInventoryReference());
			eContractDeliverRequest.setBookTypeName(eMaazounBookWarehouse.getBookTypeName());
			eContractDeliverRequest.setLocationName(eMaazounBookDeliverInfo.getSectorFk().getLocationFk().getName());
			eContractDeliverRequest.setMaazounName(eMaazounBookRequestInfo.getMaazounProfileFk().getName());
			eContractDeliverRequest.setMaazounNationalId(eMaazounBookRequestInfo.getMaazounProfileFk().getNationalId());
			eContractDeliverRequest.setSectorName(eMaazounBookDeliverInfo.getSectorFk().getName());
			
			MaazounDeliverInfoReportDTO eMaazounCollectInfoReportDTO = populateMaazounDeliverInfoReportDTO(
					prosecutorLogoUrl, eContractDeliverRequest, eMaazounBookDeliverInfo.getPosFk(), 
					refRequestNumber, eMaazounBookDeliverInfo.getCreatedAt(), 
					Util.getCreatedByFullName(user), i, eMaazounBookDeliverInfo.getSectorFk());

			eMaazounCollectInfoReportDTOSet.add(eMaazounCollectInfoReportDTO);
			i++;
		}

		GenerateTempBookDeliverInfoEvent generateTempBookDeliverInfoEvent = new GenerateTempBookDeliverInfoEvent(
				eMaazounCollectInfoReportDTOSet, String.valueOf(createdAt), receiptUrl, 1);
		applicationEventPublisher.publishEvent(generateTempBookDeliverInfoEvent);
		
	}

	@Override
	public String closeActionDeliverRequest(CustomUserDetails customUserDetails,
			CloseMaazounDeliverInfoRequest closeMaazounDeliverInfoRequest, MultipartFile deliverAssignReport) {

		Date updateAt = Date.from(Instant.now());

		MaazounBookDeliverInfo eMaazounBookDeliverInfo = maazounDeliverInfoService
				.findById(Long.valueOf(closeMaazounDeliverInfoRequest.getId())).get();

		eMaazounBookDeliverInfo.setStatusFk(StatusConstant.STATUS_DELIVERED);
		eMaazounBookDeliverInfo.setIsReviewed(true);
		eMaazounBookDeliverInfo.setUpdatedAt(updateAt);
		eMaazounBookDeliverInfo.setUpdatedBy(customUserDetails.getUsername());

		if (deliverAssignReport != null && !deliverAssignReport.getOriginalFilename().contains("foo.txt")) {
			String imageUrl;
			try {
				imageUrl = saveContractAttFile(deliverAssignReport, Util.dateFormat(),
						eMaazounBookDeliverInfo.getRefRequestNumber());
				eMaazounBookDeliverInfo.setImageUrl(imageUrl);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		maazounDeliverInfoService.save(eMaazounBookDeliverInfo);

		maazounBookWarehouseService.updateStatusByMaazounBookDeliverInfoFk(StatusConstant.STATUS_DELIVERED,
				eMaazounBookDeliverInfo);

		return cryptoMngrMaazounService.encrypt(eMaazounBookDeliverInfo.getImageUrl());
	}
	
	@Override
	public String deleteActionDeliverRequest(CustomUserDetails customUserDetails,
			CloseMaazounDeliverInfoRequest closeMaazounDeliverInfoRequest) {

		Date updateAt = Date.from(Instant.now());

		MaazounBookDeliverInfo eMaazounBookDeliverInfo = maazounDeliverInfoService
				.findById(Long.valueOf(closeMaazounDeliverInfoRequest.getId())).get();

		eMaazounBookDeliverInfo.setStatusFk(StatusConstant.STATUS_REJECTED);
		eMaazounBookDeliverInfo.setIsReviewed(true);
		eMaazounBookDeliverInfo.setUpdatedAt(updateAt);
		eMaazounBookDeliverInfo.setUpdatedBy(customUserDetails.getUsername());
		
		maazounDeliverInfoService.save(eMaazounBookDeliverInfo);
		
		maazounBookWarehouseService.updateStatusByMaazounBookDeliverInfoFk(StatusConstant.STATUS_RECEIVED,
				eMaazounBookDeliverInfo);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("response", "Successfully");

		return cryptoMngrMaazounService.encrypt(eMaazounBookDeliverInfo.getImageUrl());
	}

	@Override
	public InputStream downloadFile(URLRequest uRLRequest) {
		
		File pdfFile = new File(uRLRequest.getUrl());
		InputStream is = null;
		try {
			is = new FileInputStream(pdfFile);
		} catch (FileNotFoundException e) {

			reGenerateDeliveryReport(uRLRequest.getRefRequestNumber());
			try {
				is = new FileInputStream(pdfFile);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		return is;
	}

	private String saveContractAttFile(MultipartFile att, String currentDate, String requestRefNumber)
			throws Exception {
		Path fileStorageLocation = null;

		if (att != null && !att.getOriginalFilename().equals("foo.txt")) {
			try {
				fileStorageLocation = Paths
						.get(bookDeliverPathAtt + "/" + currentDate + "/" + requestRefNumber + "." + "png")
						.toAbsolutePath().normalize();

				Path path = Paths.get(fileStorageLocation.toString());
				System.gc();
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
	public List<String> getPendingDeliverRequests(CustomUserDetails customUserDetails) {

		Set<Long> locationIds = getLocationsFromPos(customUserDetails.getPosSet());

		List<String> eTransaction = new ArrayList<String>();
		maazounDeliverInfoService.getDeliverRequests(StatusConstant.STATUS_PENDING, locationIds).stream().forEach(s -> {
			eTransaction.add(cryptoMngrMaazounService
					.encrypt(maazounDelivernfoMapper.maazounDeliverinfoToMaazounDelivernfoDTO(s).toString()));
		});

		return eTransaction;
	}

	@Override
	public MaazounDeliverInfoPagingDTO searchDeliveredRequests(CustomUserDetails customUserDetails,
			SearchDeliveredInfoTransactionRequest searchObj) {

		Page<MaazounBookDeliverInfo> pageResult = null;
		MaazounDeliverInfoPagingDTO obj = new MaazounDeliverInfoPagingDTO();

		try {
			pageResult = maazounDeliverInfoService.searchDeliveredRequests(customUserDetails, searchObj);

			obj.setCount(pageResult.getTotalElements());
			obj.setTotalPages(pageResult.getTotalPages());

			List<String> eTransaction = new ArrayList<String>();
			pageResult.getContent().stream().forEach(s -> {
				eTransaction.add(cryptoMngrMaazounService
						.encrypt(maazounDelivernfoMapper.maazounDeliverinfoToMaazounDelivernfoDTO(s).toString()));
			});
			obj.setTransactions(eTransaction);
			return obj;

		} catch (Exception e) {
			obj.setCount((long) 0);
			obj.setTotalPages(0);
			obj.setTransactions(new ArrayList<String>());
			return obj;
		}

	}

}

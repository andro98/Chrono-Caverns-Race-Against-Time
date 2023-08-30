package com.aman.payment.core.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aman.payment.core.model.PullAccount;
import com.aman.payment.core.model.dto.FinancialAuditDTO;
import com.aman.payment.core.model.payload.ApprovedClaimsSearchRequest;
import com.aman.payment.core.model.payload.ApprovedPullAccountRequest;
import com.aman.payment.core.model.payload.FinancialReportRequest;
import com.aman.payment.core.model.payload.GetPullAccountRequest;
import com.aman.payment.core.repository.PullAccountRepository;
import com.aman.payment.core.service.PullAccountService;
import com.aman.payment.core.util.UtilCore;
import com.aman.payment.util.StatusConstant;

@Service
@Transactional
public class PullAccountServiceImpl implements PullAccountService {
	private final PullAccountRepository repository;

	public PullAccountServiceImpl(PullAccountRepository repository) {
		this.repository = repository;
	}

	@Override
	public PullAccount save(PullAccount entity) {
		return repository.save(entity);
	}

	@Override
	public List<PullAccount> save(List<PullAccount> entities) {
		return (List<PullAccount>) repository.saveAll(entities);
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	@Override
	@Cacheable(value = "pullAccountCacheId", key = "#id", unless = "#result==null")
	public Optional<PullAccount> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public List<PullAccount> findAll() {
		return (List<PullAccount>) repository.findAll();
	}

	@Override
	public Page<PullAccount> findAll(Pageable pageable) {
		Page<PullAccount> entityPage = repository.findAll(pageable);
		List<PullAccount> entities = entityPage.getContent();
		return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
	}

	@Override
	public PullAccount update(PullAccount entity, Long id) {
		Optional<PullAccount> optional = findById(id);
		if (optional.isPresent()) {
			return save(entity);
		}
		return null;
	}

	@Override
	public Optional<PullAccount> findByStatusFkAndCreatedByAndSettlementCodeAndServiceId(String status, String username,
			String settlementCode, long serviceId) {
		return repository.findByStatusFkAndCreatedByAndSettlementCodeAndServiceId(status, username, settlementCode,
				serviceId);
	}

	@Override
	public List<PullAccount> findByStatusFkAndCreatedByAndServiceId(String status, String username, long serviceId) {
		return repository.findByStatusFkAndCreatedByAndServiceId(status, username, serviceId);
	}

	@Override
	public List<PullAccount> getNewOrRejectedPullAccountByUser(String username, String status1, String status2) {
		return repository.findByCreatedByAndStatusFkOrStatusFkOrderByCreatedAtAsc(username, status1, status2);
	}

	@Override
	public Page<PullAccount> findByStatusFkAndCreatedBy(ApprovedPullAccountRequest approvedPullAccountRequest,
			String statusFk, String createdBy) {

		Pageable pageable = PageRequest.of(Integer.valueOf(approvedPullAccountRequest.getPageNo()),
				Integer.valueOf(approvedPullAccountRequest.getPageSize()));
		Page<PullAccount> entityPage = repository.findByStatusFkAndCreatedByOrderByCreatedAtDesc(statusFk, createdBy,
				pageable);

		return new PageImpl<>(entityPage.getContent(), pageable, entityPage.getTotalElements());

	}

	@Override
	public String saveAttachedFile(MultipartFile attFile, String settlementCode, String attType, String filePath) {
		String targetPath = null;

		if (attFile != null && !attFile.getOriginalFilename().equals("foo.txt")) {
			String attUrl = filePath + "/" + UtilCore.saveFolderNamingFormat();
			String targetFileName = "Deposit_" + attType + "_" + settlementCode + "_" + attFile.getOriginalFilename();

			targetPath = attUrl + "/" + targetFileName;
			try {
				Files.createDirectories(Paths.get(attUrl));
				Files.copy(attFile.getInputStream(), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return targetPath;
	}

	@Override
	public void deleteAll(List<PullAccount> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<PullAccount> findByStatusFkNotAndPosIn(Set<Long> posIds, String status) {
		return repository.findByStatusFkNotAndPosIdInOrderByCreatedAtDesc(status, posIds);
	}

	@Override
	public Page<PullAccount> findByStatusFkAndPosIn(ApprovedPullAccountRequest approvedPullAccountRequest,
			Set<Long> posIds, String status) {
		Pageable pageable = PageRequest.of(Integer.valueOf(approvedPullAccountRequest.getPageNo()),
				Integer.valueOf(approvedPullAccountRequest.getPageSize()));
		Page<PullAccount> entityPage = repository.findByStatusFkAndPosIdInOrderByCreatedAtDesc(status, posIds,
				pageable);

		return new PageImpl<>(entityPage.getContent(), pageable, entityPage.getTotalElements());
	}

	@Override
	public Page<PullAccount> getFinancialReviewReport(FinancialReportRequest financialReportRequest) {
		Pageable pageable = PageRequest.of(Integer.valueOf(financialReportRequest.getPageNo()),
				Integer.valueOf(financialReportRequest.getPageSize()));

		return repository.getFinancialReviewReport(financialReportRequest);

//		if(financialReportRequest.getServiceId() != null && !financialReportRequest.getServiceId().isEmpty()) {
//			return repository.findByCreatedAtAfterAndCreatedAtBeforeAndServiceId(
//					UtilCore.convertStringToStartDateFormatSql(financialReportRequest.getDurationFrom()), 
//					UtilCore.convertStringToEndDateFormatSql(financialReportRequest.getDurationTo()), 
//					Long.valueOf(financialReportRequest.getServiceId()), pageable);
//		}else {
//			return repository.findByCreatedAtAfterAndCreatedAtBefore(
//					UtilCore.convertStringToStartDateFormatSql(financialReportRequest.getDurationFrom()), 
//					UtilCore.convertStringToEndDateFormatSql(financialReportRequest.getDurationTo()), 
//					pageable);
//		}

	}

	@Override
	public Page<FinancialAuditDTO> getAuditReportByDaily(FinancialReportRequest financialReportRequest) {
		return repository.getAuditReportByDaily(financialReportRequest);
	}

	@Override
	public Page<FinancialAuditDTO> getAuditReportByLocation(FinancialReportRequest financialReportRequest) {
		return repository.getAuditReportByLocation(financialReportRequest);
	}
	
	@Override
	public Page<FinancialAuditDTO> getAuditReportByCourt(FinancialReportRequest financialReportRequest) {
		return repository.getAuditReportByCourt(financialReportRequest);
	}
	
	@Override
	public Page<FinancialAuditDTO> getAuditReportBySector(FinancialReportRequest financialReportRequest) {
		return repository.getAuditReportBySector(financialReportRequest);
	}

	@Override
	public Page<FinancialAuditDTO> getAuditReportByPOS(FinancialReportRequest financialReportRequest) {
		return repository.getAuditReportByPOS(financialReportRequest);
	}

	@Override
	public Page<FinancialAuditDTO> getAuditReportByAgentAndLocation(FinancialReportRequest financialReportRequest) {
		return repository.getAuditReportByAgentAndLocation(financialReportRequest);
	}

	@Override
	public Optional<PullAccount> findBySettlementCode(String settlementCode) {
		return repository.findBySettlementCode(settlementCode);
	}

	@Override
	public List<PullAccount> findByStatusFkAndServiceId(String status, long serviceId) {
		return repository.findByStatusFkAndServiceId(status, serviceId);
	}

	@Override
	public Page<PullAccount> findApprovedClaimsAdvancedSearch(ApprovedClaimsSearchRequest approvedClaimsSearchRequest) {

		Set<Long> ePosIds = new HashSet<Long>();
		approvedClaimsSearchRequest.getPosIds().stream().forEach(s -> {
			ePosIds.add(Long.valueOf(s));
		});
		return repository.findApprovedClaimsAdvancedSearch(approvedClaimsSearchRequest);
	}

	@Override
	public Optional<PullAccount> findBySettlementCodeAndServiceId(String settlementCode, long serviceId) {
		return repository.findBySettlementCodeAndServiceIdAndStatusFk(settlementCode, serviceId,
				StatusConstant.STATUS_NEW);
	}

	@Override
	public Page<PullAccount> findByApprovedBy(ApprovedPullAccountRequest approvedPullAccountRequest) {
		Pageable pageable = PageRequest.of(Integer.valueOf(approvedPullAccountRequest.getPageNo()),
				Integer.valueOf(approvedPullAccountRequest.getPageSize()));
		
		Set<Long> ePosIds = new HashSet<Long>();
		approvedPullAccountRequest.getPosIds().stream().forEach(s -> {
			ePosIds.add(Long.valueOf(s));
		});

		Page<PullAccount> entityPage = repository
				.findByApprovedByAndPosIdInOrderByCreatedAtDesc(approvedPullAccountRequest.getUsername(), ePosIds,pageable);

		return new PageImpl<>(entityPage.getContent(), pageable, entityPage.getTotalElements());
	}

	@Override
	public List<FinancialAuditDTO> getReportPermissionDetails(FinancialReportRequest financialReportRequest) {
		return repository.getReportPermissionDetails(financialReportRequest);
	}

	@Override
	public Page<PullAccount> findAllApprovedRequests(ApprovedPullAccountRequest approvedPullAccountRequest) {
		Pageable pageable = PageRequest.of(Integer.valueOf(approvedPullAccountRequest.getPageNo()),
				Integer.valueOf(approvedPullAccountRequest.getPageSize()));

		Page<PullAccount> entityPage = repository.findByStatusFk(StatusConstant.STATUS_APPROVED, pageable);
		return new PageImpl<>(entityPage.getContent(), pageable, entityPage.getTotalElements());
	}

	@Override
	public List<PullAccount> findByStatusFk(String statusFk) {
		return repository.findByStatusFk(statusFk);
	}

	@Override
	public Optional<PullAccount> findByStatusFkAndPosIdAndServiceId(String status, long posId, long serviceId) {
		return repository.findFirstByStatusFkAndPosIdAndServiceIdOrderByCreatedAtDesc(status, posId, serviceId);
	}

	@Override
	public Optional<PullAccount> getSettlementCode(GetPullAccountRequest decryptGetPullAccountRequest) {
		// TODO Auto-generated method stub
		return repository.findBySettlementCode(decryptGetPullAccountRequest.getSettlementCode());
	}

	@Override
	public String updateSettlementStatus(GetPullAccountRequest decryptGetPullAccountRequest) {
		
		repository.updateSettlementCode(StatusConstant.STATUS_NEW, decryptGetPullAccountRequest.getPullAccountId());
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("response", "Successfully");
		return jsonObject.toString();
	}

}
package com.aman.payment.core.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aman.payment.core.model.FinancialDeficit;
import com.aman.payment.core.model.PullAccount;
import com.aman.payment.core.model.payload.ApprovedFinancialDeficitRequest;
import com.aman.payment.core.repository.FinancialDeficitRepository;
import com.aman.payment.core.service.FinancialDeficitService;
import com.aman.payment.core.util.UtilCore;
import com.aman.payment.util.StatusConstant;

@Service
@Transactional
public class FinancialDeficitServiceImpl implements FinancialDeficitService {
    private final FinancialDeficitRepository repository;

    public FinancialDeficitServiceImpl(FinancialDeficitRepository repository) {
        this.repository = repository;
    }

    @Override
    public FinancialDeficit save(FinancialDeficit entity) {
        return repository.save(entity);
    }

    @Override
    public List<FinancialDeficit> save(List<FinancialDeficit> entities) {
        return (List<FinancialDeficit>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<FinancialDeficit> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<FinancialDeficit> findAll() {
        return (List<FinancialDeficit>) repository.findAll();
    }

    @Override
    public Page<FinancialDeficit> findAll(Pageable pageable) {
        Page<FinancialDeficit> entityPage = repository.findAll(pageable);
        List<FinancialDeficit> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public FinancialDeficit update(FinancialDeficit entity, Long id) {
        Optional<FinancialDeficit> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }

    @Override
	public List<FinancialDeficit> findByCreatedByAndStatusFk(String username, String statusFk1, String statusFk2) {
		return repository.findByCreatedByAndStatusFkOrStatusFk(username, statusFk1, statusFk2);
		
	}
    
    @Override
	public Page<FinancialDeficit> findByStatusFkAndCreatedBy(ApprovedFinancialDeficitRequest approvedFinancialDeficitRequest,
			String statusFk, String createdBy){
		
		Pageable pageable = PageRequest.of(Integer.valueOf(approvedFinancialDeficitRequest.getPageNo()), 
				Integer.valueOf(approvedFinancialDeficitRequest.getPageSize()));
		Page<FinancialDeficit> entityPage = repository.findByCreatedByAndStatusFkOrderByCreatedAtDesc(
				createdBy, StatusConstant.STATUS_APPROVED, pageable);
	
		return new PageImpl<>(entityPage.getContent(), pageable, entityPage.getTotalElements());
	
	}
    
    @Override
	public String saveAttachedFile(MultipartFile attFile, String settlementCode, String filePath) {
		String targetPath = null;
		
		if(attFile != null && !attFile.getOriginalFilename().equals("foo.txt")) {
			String attUrl = filePath+"/"+UtilCore.saveFolderNamingFormat();
			String targetFileName = "Deficit_"+settlementCode+"_"+attFile.getOriginalFilename();
			
			targetPath = attUrl+"/"+targetFileName;
			
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
	public void deleteAll(List<FinancialDeficit> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<FinancialDeficit> findByStatusFkNotAndPosIn(Set<Long> posIds, String status) {
		return repository.findByStatusFkNotAndPosIdInOrderByCreatedAtDesc(status,posIds);
	}

	@Override
	public Page<FinancialDeficit> findByStatusFkAndPosIn(ApprovedFinancialDeficitRequest approvedFinancialDeficitRequest,
			Set<Long> posIds, String status) {
		Pageable pageable = PageRequest.of(Integer.valueOf(approvedFinancialDeficitRequest.getPageNo()), 
				Integer.valueOf(approvedFinancialDeficitRequest.getPageSize()));
		Page<FinancialDeficit> entityPage = repository.
				findByStatusFkAndPosIdInOrderByCreatedAtDesc(status, posIds, pageable);
	
		return new PageImpl<>(entityPage.getContent(), pageable, entityPage.getTotalElements());
	}

	@Override
	public FinancialDeficit findByPullAccountFk(PullAccount pullAccountFk) {
		return repository.findByPullAccountFk(pullAccountFk);
	}

	@Override
	public Page<FinancialDeficit> findByApprovedBy(ApprovedFinancialDeficitRequest approvedFinancialDeficitRequest) {
		Pageable pageable = PageRequest.of(Integer.valueOf(approvedFinancialDeficitRequest.getPageNo()), 
				Integer.valueOf(approvedFinancialDeficitRequest.getPageSize()));
		Page<FinancialDeficit> pageResult =  repository.findByApprovedByOrderByCreatedAtDesc(
				approvedFinancialDeficitRequest.getUsername(), pageable);
		return new PageImpl<>(pageResult.getContent(), pageable, pageResult.getTotalElements());
	}

}
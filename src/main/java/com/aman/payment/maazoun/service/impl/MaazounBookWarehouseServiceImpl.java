package com.aman.payment.maazoun.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.maazoun.model.MaazounBookCollectionInfo;
import com.aman.payment.maazoun.model.MaazounBookDeliverInfo;
import com.aman.payment.maazoun.model.MaazounBookRequestInfo;
import com.aman.payment.maazoun.model.MaazounBookSupplyOrder;
import com.aman.payment.maazoun.model.MaazounBookWarehouse;
import com.aman.payment.maazoun.model.dto.WarehouseAuditDTO;
import com.aman.payment.maazoun.model.payload.BooksByIdRequest;
import com.aman.payment.maazoun.model.payload.BooksFilterRequest;
import com.aman.payment.maazoun.model.payload.WarehouseBookRequest;
import com.aman.payment.maazoun.repository.MaazounBoorkWarehouseRepository;
import com.aman.payment.maazoun.service.MaazounBookWarehouseService;

@Service
@Transactional
public class MaazounBookWarehouseServiceImpl implements MaazounBookWarehouseService {

	@Autowired
    private final MaazounBoorkWarehouseRepository repository;
	
	public MaazounBookWarehouseServiceImpl(MaazounBoorkWarehouseRepository repository) {
        this.repository = repository;
    }

	@Override
	public MaazounBookWarehouse save(MaazounBookWarehouse entity) {
		// TODO Auto-generated method stub
		return repository.save(entity);
	}

	@Override
	public List<MaazounBookWarehouse> save(List<MaazounBookWarehouse> entities) {
		// TODO Auto-generated method stub
		return (List<MaazounBookWarehouse>) repository.saveAll(entities);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	public void deleteAll(List<MaazounBookWarehouse> entities) {
		// TODO Auto-generated method stub
		repository.deleteAll();
	}

	@Override
	public Optional<MaazounBookWarehouse> findById(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public List<MaazounBookWarehouse> findAll() {
		// TODO Auto-generated method stub
		return (List<MaazounBookWarehouse>) repository.findAll();
	}

	@Override
	public Page<MaazounBookWarehouse> findAll(Pageable pageable) {
		Page<MaazounBookWarehouse> entityPage = repository.findAll(pageable);
        List<MaazounBookWarehouse> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
	}

	@Override
	public MaazounBookWarehouse update(MaazounBookWarehouse entity, Long id) {
		// TODO Auto-generated method stub
		Optional<MaazounBookWarehouse> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
	}
	
	@Override
	public Page<MaazounBookWarehouse> findByLocationIn(Set<Long> locationIds, Pageable pageable) {
		Page<MaazounBookWarehouse> entityPage = repository.
				findByLocationIdInOrderByCreatedAtDesc(locationIds, pageable);
	
		return new PageImpl<>(entityPage.getContent(), pageable, entityPage.getTotalElements());
	}
	
	@Override
	public Page<MaazounBookWarehouse> findBySectorIn(Set<Long> sectorIds, Pageable pageable, CustomUserDetails customUserDetails) {
		Page<MaazounBookWarehouse> entityPage = repository.
				findBySectorFkInOrderByCreatedAtDesc(sectorIds, pageable, customUserDetails);
	
		return new PageImpl<>(entityPage.getContent(), pageable, entityPage.getTotalElements());
	}

	@Override
	public Optional<MaazounBookWarehouse> findBySerialNumberAndStatusFk(String serialNumber, String statusFk) {
		// TODO Auto-generated method stub
		return repository.findFirstBySerialNumberAndStatusFk(serialNumber, statusFk);
	}

	@Override
	public List<WarehouseAuditDTO> warehouseReportByLocationAndBookType(CustomUserDetails customUserDetails) {
		// TODO Auto-generated method stub
		return repository.warehouseReportByLocationAndBookType(customUserDetails);
	}
	
	@Override
	public List<WarehouseAuditDTO> warehouseReportByBookType(CustomUserDetails customUserDetails, WarehouseBookRequest warehouseBookRequest) {
		return repository.warehouseReportByBookType(warehouseBookRequest, customUserDetails);
	}
	
	@Override
	public List<WarehouseAuditDTO> warehouseReportByDaily(CustomUserDetails customUserDetails, WarehouseBookRequest warehouseBookRequest) {
		return repository.warehouseReportByDaily(warehouseBookRequest, customUserDetails);
	}

//	@Override
//	public void deleteBySerialNumber(String serialNumber) {
//		repository.deleteBySerialNumber(serialNumber);
//		
//	}

	@Override
	public Page<MaazounBookWarehouse> findByStatusFk(String statusFk, Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findDistinctSerialNumberByStatusFkOrderByIdDesc(statusFk, pageable);
	}

	@Override
	public void updateStatusBySerialNumber(String statusFk, MaazounBookRequestInfo maazounRequestInfoFk, String serialNumber) {
		// TODO Auto-generated method stub
		repository.updateStatusBySerialNumber(statusFk, maazounRequestInfoFk, serialNumber);
	}
	
	@Override
	public void updateBookSerialNumberStatusFkFromSoldeToNew(String statusFk, String serialNumber) {
		repository.updateBookSerialNumberStatusFkFromSoldeToNew(statusFk, serialNumber);
	}
	
	@Override
	public void updateStatusByBookFinancialNumber(String statusFk, MaazounBookRequestInfo maazounRequestInfoFk, String bookFinancialNumber) {
		// TODO Auto-generated method stub
		repository.updateStatusByBookFinancialNumber(statusFk, maazounRequestInfoFk, bookFinancialNumber);
	}
	
	@Override
	public void updateStatusBySerialNumber(String statusFk, String serialNumber) {
		// TODO Auto-generated method stub
		repository.updateStatusBySerialNumber(statusFk, serialNumber);
	}
	
	@Override
	public void updateStatusByContractNumber(String statusFk, MaazounBookCollectionInfo maazounBookCollectionInfoFk, String contractNumber) {
		// TODO Auto-generated method stub
		repository.updateStatusByContractNumber(statusFk, maazounBookCollectionInfoFk, contractNumber);
	}

	@Override
	public void updateStatusByMaazounBookSupplyOrderFk(String statusFk, MaazounBookSupplyOrder maazounBookSupplyOrderFk) {
		// TODO Auto-generated method stub
		repository.updateStatusByMaazounBookSupplyOrderFk(statusFk,  maazounBookSupplyOrderFk);
	}

	@Override
	public void updateCollectionInfoFkWithNull(String contractNumber) {
		// TODO Auto-generated method stub
		repository.updateCollectionInfoFkWithNull(contractNumber, contractNumber);
	}
	
	@Override
	public void updateSupplyOrderFkWithNull(MaazounBookSupplyOrder eMaazounBookSupplyOrder) {
		// TODO Auto-generated method stub
		repository.updateSupplyOrderFkWithNull(eMaazounBookSupplyOrder);
	}

	@Override
	public Optional<MaazounBookWarehouse> findByContractNumber(String contractNumber) {
		// TODO Auto-generated method stub
		return repository.findByContractNumber(contractNumber);
	}

	@Override
	public void updateStatusByMaazounBookCollectionInfoFk(String statusFk, String contractNumber) {
		// TODO Auto-generated method stub
		repository.updateStatusByMaazounBookCollectionInfoFk(statusFk,  contractNumber);
	}

	@Override
	public Optional<MaazounBookWarehouse> findByContractNumberAndStatusFk(String contractNumber, String statusFk) {
		// TODO Auto-generated method stub
		return repository.findByContractNumberAndStatusFk(contractNumber, statusFk);
	}
	
	@Override
	public Optional<MaazounBookWarehouse> findByContractFinancialNumberAndStatusFk(String contractFinancialNumber, String statusFk) {
		// TODO Auto-generated method stub
		return repository.findByContractFinancialNumberAndStatusFk(contractFinancialNumber, statusFk);
	}
	
	

	@Override
	public void updateStatusAndBookDeliveryByContractNumber(String statusFk,
			MaazounBookDeliverInfo maazounBookDeliverInfo, String contractNumber) {
		// TODO Auto-generated method stub
		repository.updateStatusAndBookDeliveryByContractNumber(statusFk, maazounBookDeliverInfo, contractNumber);
	}
	
	@Override
	public void updateStatusAndBookDeliveryByBookFinancialNumber(String statusFk,
			MaazounBookDeliverInfo maazounBookDeliverInfo, String bookFinancialNumber) {
		// TODO Auto-generated method stub
		repository.updateStatusAndBookDeliveryByBookFinancialNumber(statusFk, maazounBookDeliverInfo, bookFinancialNumber);
	}

	@Override
	public void updateStatusByContractNumber(String statusFk, String contractNumber) {
		// TODO Auto-generated method stub
		repository.updateStatusByContractNumber(statusFk, contractNumber);
	}

	@Override
	public void updateStatusByMaazounBookDeliverInfoFk(String statusFk, MaazounBookDeliverInfo maazounBookDeliverInfo) {
		// TODO Auto-generated method stub
		repository.updateStatusByMaazounBookDeliverInfoFk(statusFk, maazounBookDeliverInfo);
	}

	@Override
	public Optional<MaazounBookWarehouse> findByBookFinancialNumberAndStatusFk(String bookFinancialNumber, String statusFk) {
		// TODO Auto-generated method stub
		 		return repository.findFirstByBookFinancialNumberAndStatusFk(bookFinancialNumber, statusFk);
	}
	
	@Override
	public Optional<MaazounBookWarehouse> findByBookSerialNumberAndStatusFk(String bookSerialNumber, String statusFk) {
		// TODO Auto-generated method stub
		 		return repository.findFirstBySerialNumberAndStatusFk(bookSerialNumber, statusFk);
	}

	@Override
	public List<MaazounBookWarehouse> findBySerialNumber(String serialNumber) {
		// TODO Auto-generated method stub
		return repository.findBySerialNumberOrderByIdAsc(serialNumber);
	}

	@Override
	public List<MaazounBookWarehouse> findAllBySerialNumberAndStatusFk(String serialNumber, String statusFk) {
		
		return repository.findBySerialNumberAndStatusFk(serialNumber, statusFk);

	}
	
	@Override
	public List<MaazounBookWarehouse> findAllByBookFinancialNumberAndStatusFk(String bookFinancialNumber, String statusFk) {
		
		return repository.findByBookFinancialNumberAndStatusFk(bookFinancialNumber, statusFk);

	}

//	@Override
//	public void deleteByMaazounBookSupplyOrderFk(MaazounBookSupplyOrder maazounBookSupplyOrderFk) {
//		// TODO Auto-generated method stub
//		repository.deleteByMaazounBookSupplyOrderFk(maazounBookSupplyOrderFk);
//	}

	@Override
	public List<MaazounBookWarehouse> findByBookFinancialNumber(String bookFinancialNumber) {
		// TODO Auto-generated method stub
		return repository.findByBookFinancialNumberOrderByIdAsc(bookFinancialNumber);
	}

	@Override
	public void updateStatusByContractFinancialNumber(String statusFk,
			MaazounBookCollectionInfo maazounBookCollectionInfoFk, String contractFinancialNumber) {
		repository.updateStatusByContractFinancialNumber(statusFk, maazounBookCollectionInfoFk, contractFinancialNumber);

		
	}

	@Override
	public Optional<MaazounBookWarehouse> findByContractNumberOrContractFinancialNumber(String contractNumber,
			String contractFinancialNumber) {
		// TODO Auto-generated method stub
		return repository.findFirstByContractNumberOrContractFinancialNumber(contractNumber, contractFinancialNumber);
	}
	
	@Override
	public Optional<MaazounBookWarehouse> findBySerialNumberOrBookFinancialNumber(String serialNumber, String bookFinancialNumber){
		return repository.findFirstBySerialNumberOrBookFinancialNumber(serialNumber, bookFinancialNumber);
	}

	@Override
	public Page<MaazounBookWarehouse> findByStatusFkAndPosIn(String statusFk, Set<Long> posIds, Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findByStatusFkAndPosIn(statusFk, posIds, pageable);
	}

	@Override
	public Page<MaazounBookWarehouse> findBySectorFk(BooksFilterRequest booksByStatusRequest, Set<Long> sectorIds,
			CustomUserDetails customUserDetails, Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findBySectorFk(booksByStatusRequest, sectorIds, customUserDetails, pageable);
		
		
	}

	@Override
	public List<MaazounBookWarehouse> findByMaazounBookSupplyOrderFk(MaazounBookSupplyOrder maazounBookSupplyOrderFk) {
		// TODO Auto-generated method stub
		return repository.findByMaazounBookSupplyOrderFk(maazounBookSupplyOrderFk);
	}

	@Override
	public void updateBookFinancialNumberInventoryReference(String bookFinancialNumber, 
			Long bookInventoryNumber, Long bookInventoryReference) {
		// TODO Auto-generated method stub
		repository.updateBookFinancialNumberInventoryReference(bookFinancialNumber, bookInventoryNumber, bookInventoryReference);
	}

	@Override
	public List<MaazounBookWarehouse> findAllByMaazounBookSupplyOrderFk(
			MaazounBookSupplyOrder maazounBookSupplyOrderFk) {
		// TODO Auto-generated method stub
		return  repository.findAllByMaazounBookSupplyOrderFk(maazounBookSupplyOrderFk);
	}

	@Override
	public Optional<MaazounBookWarehouse> findFirstByBookFinancialNumber(String bookFinancialNumber) {
		return repository.findFirstByBookFinancialNumber(bookFinancialNumber);

	}

	@Override
	public Optional<MaazounBookWarehouse> findFirstBySerialNumber(String serialNumber) {
		return repository.findFirstBySerialNumber(serialNumber);
	}
	
	@Override
	public Optional<MaazounBookWarehouse> findFirstByContractNumber(String contractNumber) {
		return repository.findFirstByContractNumber(contractNumber);
	}

	@Override
	public Optional<MaazounBookWarehouse> findBySerialNumberAndStatusFkOrStatusFk(String serialNumber, String statusFk,
			String statusFk2) {
		return repository.findFirstBySerialNumberAndStatusFkOrStatusFk(serialNumber, statusFk, statusFk2);

	}

	@Override
	public Optional<MaazounBookWarehouse> findByBookFinancialNumberAndStatusFkOrStatusFk(String bookFinancialNumber,
			String statusFk, String statusFk2) {
		return repository.findFirstByBookFinancialNumberAndStatusFkOrStatusFk(bookFinancialNumber, statusFk, statusFk2);
	}

	@Override
	public String deleteByBookFinancialNumber(BooksByIdRequest decryptBooksRequest) {
		
		repository.deleteByBookFinancialNumber(decryptBooksRequest.getBookFinancialNumber());
		return "completed";
	}

	@Override
	public void deleteByWarehouseId(Long warehouseId) {
		// TODO Auto-generated method stub
		repository.deleteByWarehouseId(warehouseId);
	}

	@Override
	public List<MaazounBookWarehouse> findByMaazounBookRequestInfoFk(MaazounBookRequestInfo maazounBookRequestInfo) {
		// TODO Auto-generated method stub
		return repository.findByMaazounBookRequestInfoFk(maazounBookRequestInfo);
	}

	@Override
	public Optional<MaazounBookWarehouse> findByMaazounBookCollectionInfoFk(
			MaazounBookCollectionInfo maazounBookCollectionInfo) {
		// TODO Auto-generated method stub
		return repository.findByMaazounBookCollectionInfoFk(maazounBookCollectionInfo);
	}

	@Override
	public long findCountMaazounBookSoldAndCollectedStatusByMaazounId(long maazounProfileId, 
			long bookTypeId8 , long bookTypeId15) {
		return repository.findCountMaazounBookSoldAndCollectedStatusByMaazounId(maazounProfileId, 
				bookTypeId8, bookTypeId15);
	}

	@Override
	public long findCountDistinctMaazounBookSoldAndCollectedStatusByMaazounId(long maazounProfileId, 
			long bookTypeId8 , long bookTypeId15) {
		return repository.findCountDistinctMaazounBookSoldAndCollectedStatusByMaazounId(maazounProfileId, 
				bookTypeId8, bookTypeId15);
	}


}

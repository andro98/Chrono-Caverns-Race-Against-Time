package com.aman.payment.maazoun.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.maazoun.model.MaazounBookCollectionInfo;
import com.aman.payment.maazoun.model.MaazounBookDeliverInfo;
import com.aman.payment.maazoun.model.MaazounBookRequestInfo;
import com.aman.payment.maazoun.model.MaazounBookSupplyOrder;
import com.aman.payment.maazoun.model.MaazounBookWarehouse;
import com.aman.payment.maazoun.model.dto.WarehouseAuditDTO;
import com.aman.payment.maazoun.model.payload.BookListRequest;
import com.aman.payment.maazoun.model.payload.BooksByIdRequest;
import com.aman.payment.maazoun.model.payload.BooksFilterRequest;
import com.aman.payment.maazoun.model.payload.WarehouseBookRequest;

public interface MaazounBookWarehouseService extends MaazounGenericService<MaazounBookWarehouse, Long> {
	
	public Page<MaazounBookWarehouse> findByLocationIn(Set<Long> locationIds, Pageable pageable);
	
	public Page<MaazounBookWarehouse> findBySectorIn(Set<Long> sectorIds, Pageable pageable, CustomUserDetails customUserDetails);
	
	public Optional<MaazounBookWarehouse> findBySerialNumberAndStatusFk(String serialNumber, String statusFk);
	
	public Optional<MaazounBookWarehouse> findBySerialNumberAndStatusFkOrStatusFk(String serialNumber, String statusFk, String statusFk2);

	public Optional<MaazounBookWarehouse> findByBookFinancialNumberAndStatusFkOrStatusFk(String bookFinancialNumber, String statusFk, String statusFk2);
	
	public Optional<MaazounBookWarehouse> findByBookFinancialNumberAndStatusFk(String bookFinancialNumber, String statusFk);
	
	public Optional<MaazounBookWarehouse> findByBookSerialNumberAndStatusFk(String bookSerialNumber, String statusFk);

	public Optional<MaazounBookWarehouse> findByContractNumber(String contractNumber);
	
	public Optional<MaazounBookWarehouse> findByContractNumberOrContractFinancialNumber(String contractNumber, String contractFinancialNumber);
	
	public Optional<MaazounBookWarehouse> findBySerialNumberOrBookFinancialNumber(String serialNumber, String bookFinancialNumber);
	
	public Optional<MaazounBookWarehouse> findByContractNumberAndStatusFk(String contractNumber, String statusFk);
	
	public Optional<MaazounBookWarehouse> findByContractFinancialNumberAndStatusFk(String contractFinancialNumber, String statusFk);
	
	public List<WarehouseAuditDTO> warehouseReportByLocationAndBookType(CustomUserDetails customUserDetails);
	
	public List<WarehouseAuditDTO> warehouseReportByBookType(CustomUserDetails customUserDetails, WarehouseBookRequest warehouseBookRequest);
	
	public List<WarehouseAuditDTO> warehouseReportByDaily(CustomUserDetails customUserDetails, WarehouseBookRequest warehouseBookRequest);
	
//	public void deleteBySerialNumber(String serialNumber);
	
//	public void deleteByMaazounBookSupplyOrderFk(MaazounBookSupplyOrder maazounBookSupplyOrderFk);
	
	public Page<MaazounBookWarehouse> findByStatusFk(String statusFk, Pageable pageable);
	
	public Page<MaazounBookWarehouse> findByStatusFkAndPosIn(String statusFk, Set<Long> posIds, Pageable pageable);

	public void updateStatusBySerialNumber(String statusFk, MaazounBookRequestInfo maazounRequestInfoFk, String serialNumber);
	
	public void updateBookSerialNumberStatusFkFromSoldeToNew(String statusFk, String serialNumber);
	
	public void updateBookFinancialNumberInventoryReference(String bookFinancialNumber, Long bookInventoryNumber, Long bookInventoryReference);
	
	public void updateStatusByBookFinancialNumber(String statusFk, MaazounBookRequestInfo maazounRequestInfoFk, String bookFinancialNumber);
	
	public void updateStatusBySerialNumber(String statusFk, String serialNumber) throws Exception;
	
	public void updateStatusByContractNumber(String statusFk, MaazounBookCollectionInfo maazounBookCollectionInfoFk, String contractNumber);
	
	public void updateStatusAndBookDeliveryByContractNumber(String statusFk, MaazounBookDeliverInfo maazounBookDeliverInfo, String contractNumber);
	
	public void updateStatusAndBookDeliveryByBookFinancialNumber(String statusFk, MaazounBookDeliverInfo maazounBookDeliverInfo, String bookFinancialNumber);
	
	public void updateStatusByMaazounBookDeliverInfoFk(String statusFk, MaazounBookDeliverInfo maazounBookDeliverInfo);
	
	public void updateStatusByContractNumber(String statusFk, String contractNumber);
	
	public void updateCollectionInfoFkWithNull(String contractNumber);
	
	public void updateSupplyOrderFkWithNull(MaazounBookSupplyOrder eMaazounBookSupplyOrder);
	
	public void updateStatusByMaazounBookSupplyOrderFk(String statusFk, MaazounBookSupplyOrder maazounBookSupplyOrderFk);
	
	public void updateStatusByMaazounBookCollectionInfoFk(String statusFk, String contractNumber);
	
	public List<MaazounBookWarehouse> findBySerialNumber(String serialNumber);
	
	public List<MaazounBookWarehouse> findByBookFinancialNumber(String bookFinancialNumber);
	
	public List<MaazounBookWarehouse> findAllBySerialNumberAndStatusFk(String serialNumber, String statusFk);
	
	public List<MaazounBookWarehouse> findAllByBookFinancialNumberAndStatusFk(String bookFinancialNumber, String statusFk);

	public void updateStatusByContractFinancialNumber(String statusFk, MaazounBookCollectionInfo maazounBookCollectionInfoFk, String contractFinancialNumber);

	public Page<MaazounBookWarehouse> findBySectorFk(BooksFilterRequest booksByStatusRequest, Set<Long> posIds , CustomUserDetails customUserDetails, Pageable pageable);
	
	public List<MaazounBookWarehouse> findByMaazounBookSupplyOrderFk(MaazounBookSupplyOrder maazounBookSupplyOrderFk);
	
	public List<MaazounBookWarehouse> findByMaazounBookRequestInfoFk(MaazounBookRequestInfo maazounBookRequestInfo);
	
	public Optional<MaazounBookWarehouse> findByMaazounBookCollectionInfoFk(MaazounBookCollectionInfo maazounBookCollectionInfo);
	
	public List<MaazounBookWarehouse> findAllByMaazounBookSupplyOrderFk(MaazounBookSupplyOrder maazounBookSupplyOrderFk);
	
	public Optional<MaazounBookWarehouse> findFirstByBookFinancialNumber(String bookFinancialNumber);

	public Optional<MaazounBookWarehouse> findFirstBySerialNumber(String serialNumber);
	
	public Optional<MaazounBookWarehouse> findFirstByContractNumber(String contractNumber);
	
	public String deleteByBookFinancialNumber(BooksByIdRequest decryptBooksRequest);

	public void deleteByWarehouseId(Long warehouseId);
	
	public long findCountDistinctMaazounBookSoldAndCollectedStatusByMaazounId(long maazounProfileId, 
			long bookTypeId8 , long bookTypeId15);
	
	public long findCountMaazounBookSoldAndCollectedStatusByMaazounId(long maazounProfileId, 
			long bookTypeId8 , long bookTypeId15);
}
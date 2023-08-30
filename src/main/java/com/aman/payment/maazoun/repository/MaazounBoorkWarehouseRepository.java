package com.aman.payment.maazoun.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aman.payment.maazoun.model.MaazounBookCollectionInfo;
import com.aman.payment.maazoun.model.MaazounBookDeliverInfo;
import com.aman.payment.maazoun.model.MaazounBookRequestInfo;
import com.aman.payment.maazoun.model.MaazounBookSupplyOrder;
import com.aman.payment.maazoun.model.MaazounBookWarehouse;


@Repository
public interface MaazounBoorkWarehouseRepository extends PagingAndSortingRepository<MaazounBookWarehouse, Long>,
 					JpaSpecificationExecutor<MaazounBookWarehouse>, MaazounBookWarehouseCustomRepository {
	
	public Optional<MaazounBookWarehouse> findFirstBySerialNumberAndStatusFk(String serialNumber, String statusFk);
	
	public Optional<MaazounBookWarehouse> findFirstByBookFinancialNumberAndStatusFk(String bookFinancialNumber, String statusFk);
	
	@Query("select m from MaazounBookWarehouse m where m.serialNumber = :serialNumber and (m.statusFk = :statusFk or m.statusFk = :statusFk2) group by m.bookFinancialNumber")
    public Optional<MaazounBookWarehouse> findFirstBySerialNumberAndStatusFkOrStatusFk(String serialNumber, String statusFk, String statusFk2);
	
	@Query("select  m from MaazounBookWarehouse m where m.bookFinancialNumber = :bookFinancialNumber and (m.statusFk = :statusFk or m.statusFk = :statusFk2) group by m.bookFinancialNumber")
	public Optional<MaazounBookWarehouse> findFirstByBookFinancialNumberAndStatusFkOrStatusFk(String bookFinancialNumber, String statusFk, String statusFk2);
	
//	public Optional<MaazounBookWarehouse> findFirstBySerialNumberAndStatusFk(String bookSerialNumber, String statusFk);
	
	public Optional<MaazounBookWarehouse> findFirstByContractNumberOrContractFinancialNumber(String contractNumber,
			String contractFinancialNumber);
	
	public Optional<MaazounBookWarehouse> findFirstBySerialNumberOrBookFinancialNumber(String serialNumber, String bookFinancialNumber);
	
	public Optional<MaazounBookWarehouse> findFirstByBookFinancialNumber(String bookFinancialNumber);
	
	public Optional<MaazounBookWarehouse> findFirstBySerialNumber(String serialNumber);
	
	public Optional<MaazounBookWarehouse> findFirstByContractNumber(String contractNumber);

//	@Modifying
//	@Query("delete from MaazounBookWarehouse u where u.serialNumber = :serialNumber")
//	public void deleteBySerialNumber(@Param("serialNumber") String serialNumber);
//	
//	@Modifying
//	@Query("delete from MaazounBookWarehouse u where u.bookFinancialNumber = :bookFinancialNumber")
//	public void deleteByBookFinancialNumber(@Param("bookFinancialNumber") String bookFinancialNumber);
//	
//	@Modifying
//	@Query("delete from MaazounBookWarehouse u where u.maazounBookSupplyOrderFk = :maazounBookSupplyOrderFk")
//	public void deleteByMaazounBookSupplyOrderFk(@Param("maazounBookSupplyOrderFk") MaazounBookSupplyOrder maazounBookSupplyOrderFk);
	
	public Page<MaazounBookWarehouse> findDistinctbookFinancialNumberByStatusFkOrderByIdDesc(String statusFk, Pageable pageable);
	
	public Page<MaazounBookWarehouse> findDistinctSerialNumberByStatusFkOrderByIdDesc(String statusFk, Pageable pageable);
	
	@Modifying
	@Query("update MaazounBookWarehouse u set u.statusFk = :statusFk, u.maazounBookRequestInfoFk = :maazounBookRequestInfoFk where u.serialNumber = :serialNumber")
	public void updateStatusBySerialNumber(@Param("statusFk") String statusFk, 
			@Param("maazounBookRequestInfoFk") MaazounBookRequestInfo maazounBookRequestInfoFk, @Param("serialNumber") String serialNumber);
	
	@Modifying
	@Query("update MaazounBookWarehouse u set u.statusFk = :statusFk, u.maazounBookRequestInfoFk = NULL where u.serialNumber = :serialNumber")
	public void updateBookSerialNumberStatusFkFromSoldeToNew(@Param("statusFk") String statusFk, @Param("serialNumber") String serialNumber);
	
	@Modifying
	@Query("update MaazounBookWarehouse u set u.bookInventoryNumber = :bookInventoryNumber, u.bookInventoryReference = :bookInventoryReference where u.bookFinancialNumber = :bookFinancialNumber")
	public void updateBookFinancialNumberInventoryReference(@Param("bookFinancialNumber") String bookFinancialNumber, 
			@Param("bookInventoryNumber") Long bookInventoryNumber, @Param("bookInventoryReference") Long bookInventoryReference);
	
	
	@Modifying
	@Query("update MaazounBookWarehouse u set u.statusFk = :statusFk, u.maazounBookRequestInfoFk = :maazounBookRequestInfoFk where u.bookFinancialNumber = :bookFinancialNumber")
	public void updateStatusByBookFinancialNumber(@Param("statusFk") String statusFk, 
			@Param("maazounBookRequestInfoFk") MaazounBookRequestInfo maazounBookRequestInfoFk, @Param("bookFinancialNumber") String bookFinancialNumber);
	
	@Modifying
	@Query("update MaazounBookWarehouse u set u.statusFk = :statusFk where u.serialNumber = :serialNumber")
	public void updateStatusBySerialNumber(@Param("statusFk") String statusFk, 
			@Param("serialNumber") String serialNumber);
	
	@Modifying
	@Query("update MaazounBookWarehouse u set u.statusFk = :statusFk, u.maazounBookCollectionInfoFk = :maazounBookCollectionInfoFk where u.contractFinancialNumber = :contractFinancialNumber")
	public void updateStatusByContractFinancialNumber(@Param("statusFk") String statusFk, 
			@Param("maazounBookCollectionInfoFk") MaazounBookCollectionInfo maazounBookCollectionInfoFk, @Param("contractFinancialNumber") String contractFinancialNumber);
	
	@Modifying
	@Query("update MaazounBookWarehouse u set u.statusFk = :statusFk, u.maazounBookCollectionInfoFk = :maazounBookCollectionInfoFk where u.contractNumber = :contractNumber")
	public void updateStatusByContractNumber(@Param("statusFk") String statusFk, 
			@Param("maazounBookCollectionInfoFk") MaazounBookCollectionInfo maazounBookCollectionInfoFk, @Param("contractNumber") String contractNumber);
	
	@Modifying
	@Query("update MaazounBookWarehouse u set u.statusFk = :statusFk, u.maazounBookDeliverInfoFk = :maazounBookDeliverInfoFk where u.contractNumber = :contractNumber")
	public void updateStatusAndBookDeliveryByContractNumber(@Param("statusFk") String statusFk, 
			@Param("maazounBookDeliverInfoFk") MaazounBookDeliverInfo maazounBookDeliverInfoFk, @Param("contractNumber") String contractNumber);
	
	@Modifying
	@Query("update MaazounBookWarehouse u set u.statusFk = :statusFk, u.maazounBookDeliverInfoFk = :maazounBookDeliverInfoFk where u.bookFinancialNumber = :bookFinancialNumber")
	public void updateStatusAndBookDeliveryByBookFinancialNumber(@Param("statusFk") String statusFk, 
			@Param("maazounBookDeliverInfoFk") MaazounBookDeliverInfo maazounBookDeliverInfoFk, @Param("bookFinancialNumber") String bookFinancialNumber);
	
	
	@Modifying
	@Query("update MaazounBookWarehouse u set u.statusFk = :statusFk where u.contractNumber = :contractNumber")
	public void updateStatusByContractNumber(@Param("statusFk") String statusFk, @Param("contractNumber") String contractNumber);
	
	
	@Modifying
	@Query("update MaazounBookWarehouse u set u.statusFk = :statusFk where u.maazounBookSupplyOrderFk = :maazounBookSupplyOrderFk")
	public void updateStatusByMaazounBookSupplyOrderFk(@Param("statusFk") String statusFk, 
			@Param("maazounBookSupplyOrderFk") MaazounBookSupplyOrder maazounBookSupplyOrderFk);
	
	@Modifying
	@Query("update MaazounBookWarehouse u set u.statusFk = :statusFk where u.maazounBookDeliverInfoFk = :maazounBookDeliverInfoFk")
	public void updateStatusByMaazounBookDeliverInfoFk(@Param("statusFk") String statusFk, 
			@Param("maazounBookDeliverInfoFk") MaazounBookDeliverInfo maazounBookDeliverInfoFk);
	
	@Modifying
	@Query("update MaazounBookWarehouse u set u.statusFk = :statusFk where u.contractNumber = :contractNumber or contractFinancialNumber = :contractNumber")
	public void updateStatusByMaazounBookCollectionInfoFk(@Param("statusFk") String statusFk, 
			@Param("contractNumber") String contractNumber);
	
	
	@Modifying
	@Query("update MaazounBookWarehouse u set u.maazounBookCollectionInfoFk = NULL , u.statusFk ='Sold' where contractNumber = :contractNumber or contractFinancialNumber = :contractFinancialNumber")
	public void updateCollectionInfoFkWithNull(@Param("contractNumber") String contractNumber, @Param("contractFinancialNumber") String contractFinancialNumber);
	
	@Modifying
	@Query("update MaazounBookWarehouse u set u.serialNumber = NULL, contractNumber = NULL, bookFinancialNumber = NULL, contractFinancialNumber = NULL, u.maazounBookRequestInfoFk = NULL, statusFk = 'Rejected' where maazounBookSupplyOrderFk = :maazounBookSupplyOrderFk")
	public void updateSupplyOrderFkWithNull(@Param("maazounBookSupplyOrderFk") MaazounBookSupplyOrder maazounBookSupplyOrderFk);
	
	public Optional<MaazounBookWarehouse> findByContractNumber(String contractNumber);
	
	public Optional<MaazounBookWarehouse> findByContractNumberAndStatusFk(String contractNumber, String statusFk);
	
	public Optional<MaazounBookWarehouse> findByContractFinancialNumberAndStatusFk(String contractFinancialNumber, String statusFk);
	
//	@Modifying
//	@Query("update MaazounBookWarehouse u set u.isPrinted = true where u.serialNumber = :serialNumber")
//	public void changePrintStatus(@Param("serialNumber") String serialNumber);
	
	public List<MaazounBookWarehouse> findBySerialNumberOrderByIdAsc(String serialNumber);
	
	public List<MaazounBookWarehouse> findByBookFinancialNumberOrderByIdAsc(String bookFinancialNumber);
	
	public List<MaazounBookWarehouse> findBySerialNumberAndStatusFk(String serialNumber, String statusFk);
	
	public List<MaazounBookWarehouse> findByBookFinancialNumberAndStatusFk(String bookFinancialNumber, String statusFk);
	
	@Query("select distinct m from MaazounBookWarehouse m where m.maazounBookSupplyOrderFk = :maazounBookSupplyOrderFk group by m.bookFinancialNumber")
	public List<MaazounBookWarehouse> findByMaazounBookSupplyOrderFk(MaazounBookSupplyOrder maazounBookSupplyOrderFk);
	
	@Query("select  m from MaazounBookWarehouse m where m.maazounBookSupplyOrderFk = :maazounBookSupplyOrderFk")
	public List<MaazounBookWarehouse> findAllByMaazounBookSupplyOrderFk(MaazounBookSupplyOrder maazounBookSupplyOrderFk);
	
 	@Modifying
 	@Query("delete from MaazounBookWarehouse u where u.bookFinancialNumber = :bookFinancialNumber")
 	public void deleteByBookFinancialNumber(@Param("bookFinancialNumber") String bookFinancialNumber);
 	
 	@Modifying
 	@Query("delete from MaazounBookWarehouse u where u.id = :id")
 	public void deleteByWarehouseId(@Param("id") Long id);
 	
 	@Modifying
	@Query("update MaazounBookWarehouse u set u.bookTypeId = :bookTypeId  where u.bookFinancialNumber = :bookFinancialNumber")
	public void updateBookTypeIdByBookFinancialNumber(@Param("bookTypeId") Long bookTypeId, @Param("bookFinancialNumber") String bookFinancialNumber);
	
 	public List<MaazounBookWarehouse> findByMaazounBookRequestInfoFk(MaazounBookRequestInfo maazounBookRequestInfo);
	
	public Optional<MaazounBookWarehouse> findByMaazounBookCollectionInfoFk(MaazounBookCollectionInfo maazounBookCollectionInfo);
}
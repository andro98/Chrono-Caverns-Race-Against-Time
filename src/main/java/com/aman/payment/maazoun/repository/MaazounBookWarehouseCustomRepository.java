package com.aman.payment.maazoun.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.maazoun.model.MaazounBookWarehouse;
import com.aman.payment.maazoun.model.dto.WarehouseAuditDTO;
import com.aman.payment.maazoun.model.payload.BooksFilterRequest;
import com.aman.payment.maazoun.model.payload.BooksRequest;
import com.aman.payment.maazoun.model.payload.WarehouseBookRequest;


@Repository
public interface MaazounBookWarehouseCustomRepository {

	public Page<MaazounBookWarehouse> findByLocationIdInOrderByCreatedAtDesc(Set<Long> locationIds, Pageable pageable);
	
	public Page<MaazounBookWarehouse> findBySectorFkInOrderByCreatedAtDesc(Set<Long> sectorIds, Pageable pageable, CustomUserDetails customUserDetail);
	
	public List<WarehouseAuditDTO> warehouseReportByLocationAndBookType(CustomUserDetails customUserDetails);
	
	public List<WarehouseAuditDTO> warehouseReportByBookType(WarehouseBookRequest warehouseBookRequest, CustomUserDetails customUserDetails);
	
	public List<WarehouseAuditDTO> warehouseReportByDaily(WarehouseBookRequest warehouseBookRequest, CustomUserDetails customUserDetails);
	
	public Set<WarehouseAuditDTO> warehouseCustodyReportByBookType(CustomUserDetails customUserDetails);
	
	public Page<MaazounBookWarehouse> findBySectorFk(BooksFilterRequest booksByStatusRequest, Set<Long> sectorIds, CustomUserDetails customUserDetails,  Pageable pageable);
	
	public Page<MaazounBookWarehouse> findByStatusFkAndPosIn(String statusFk, Set<Long> posIds, Pageable pageable);
	
	public long findCountDistinctMaazounBookSoldAndCollectedStatusByMaazounId(long maazounProfileId, long bookTypeId8 , long bookTypeId15);
	
	public long findCountMaazounBookSoldAndCollectedStatusByMaazounId(long maazounProfileId, long bookTypeId8 , long bookTypeId15);
}
package com.aman.payment.maazoun.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.maazoun.model.SupplyOrder;
import com.aman.payment.maazoun.model.SupplyOrderDetails;

@Repository
public interface SupplyOrderDetailsRepository extends PagingAndSortingRepository<SupplyOrderDetails, Long>,
						JpaSpecificationExecutor<SupplyOrderDetails>, SupplyOrderDetailsCustomRepository {
	
	public Page<SupplyOrderDetails> findAllBySectorFK(Set<Long> sectorIds, Pageable pageable,
			CustomUserDetails customUserDetails);

	public List<SupplyOrderDetails> findAllByCreatedAtBetween(Date createdAt, Date createdAtEnd);

	public Page<SupplyOrderDetails> findAllByCreatedAtBetweenAndSectorFK(String createdAt, String createdAtEnd,
			String sectorFK, Pageable pageable);

	public SupplyOrderDetails findFirstByBookTypeCountNotNullAndSectorFK(String sectorFK);

	public SupplyOrderDetails findBySectorFK(String sectorFK);

	public SupplyOrderDetails findBySectorFKAndAndBookTypeFK(String sectorFK, String bookTypeFK);

	@Query(value = "SELECT * FROM supply_order_details s WHERE s.current_book_type_count > 0 AND s.book_typefk = ?1 AND s.sectorfk = ?2 ORDER BY id Asc  LIMIT 1;", nativeQuery = true)
	public SupplyOrderDetails findNative(String sectorFK, String bookTypeFK);

	public SupplyOrderDetails findFirstBySectorFKAndBookTypeFKOrderByIdAsc(String bookTypeFK, String sectorFK);

	public SupplyOrderDetails findFirstBySectorFKAndCurrentBookTypeCountNotNullOrderByCreatedAtAsc(String sectorFK);

	public Page<SupplyOrderDetails> findBysectorFKIn(Set<String> sectors, Pageable pageable);
	
	public SupplyOrderDetails findByBookTypeFKAndSupplyOrderFk(String bookTypeFK, SupplyOrder supplyOrderFk);

}

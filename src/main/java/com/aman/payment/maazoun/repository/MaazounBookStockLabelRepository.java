package com.aman.payment.maazoun.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aman.payment.maazoun.model.MaazounBookStockLabel;

@Repository
public interface MaazounBookStockLabelRepository extends JpaRepository<MaazounBookStockLabel,Long> {

	public Page<MaazounBookStockLabel> findByLocationIdOrderByCreatedAtDesc(Long locationId, Pageable pageable);
	
	public Page<MaazounBookStockLabel> findByBookTypeIdAndLocationIdInOrderByCreatedAtDesc(Long bookTypeId, Set<Long> locationIds, Pageable pageable);
	
	public Page<MaazounBookStockLabel> findByStatusFkAndLocationIdInOrderByCreatedAtDesc(String statusFk, Set<Long> locationIds, Pageable pageable);
	
	public Page<MaazounBookStockLabel> findByStatusFkAndLocationIdOrderByCreatedAtDesc(String statusFk, Long locationId, Pageable pageable);
	
	public Page<MaazounBookStockLabel> findByStatusFkAndBookTypeIdAndLocationIdInOrderByCreatedAtDesc(String statusFk, Long bookTypeId, 
			Set<Long> locationIds, Pageable pageable);
	
	public Page<MaazounBookStockLabel> findByBookTypeIdAndLocationIdOrderByCreatedAtDesc(Long bookTypeId, Long locationId, Pageable pageable);
	
	public Page<MaazounBookStockLabel> findByStatusFkAndBookTypeIdAndLocationIdOrderByCreatedAtDesc(String statusFk, Long bookTypeId, Long locationId, Pageable pageable);
	
	@Modifying
	@Query("update MaazounBookStockLabel u set u.statusFk = :statusFk where u.id IN :ids")
	public void updateStatusByIds(@Param("statusFk") String statusFk, @Param("ids") Set<Long> ids);
	
	public Optional<MaazounBookStockLabel> findByLabelCode(String labelCode);
	
	public Optional<MaazounBookStockLabel> findByLabelCodeAndStatusFk(String labelCode, String statusFk);
	
	public List<MaazounBookStockLabel> findByStatusFkAndLocationIdInOrderByIdAsc(String statusFk, Set<Long> locationIds);
	
	@Modifying
	@Query("update MaazounBookStockLabel u set u.statusFk = :statusFk where u.labelCode IN :labelCodes")
	public void updateStatusByLabelCode(@Param("statusFk") String statusFk, @Param("labelCodes") Set<String> labelCodes);
	
	@Modifying
	@Query("update MaazounBookStockLabel u set u.statusFk = :statusFk where u.id = :id")
	public void updateStatusById(@Param("statusFk") String statusFk, @Param("id") Long id);

}

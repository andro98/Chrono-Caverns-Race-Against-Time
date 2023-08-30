package com.aman.payment.maazoun.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.aman.payment.maazoun.model.MaazounBookStockLabel;

public interface MaazounBookStockLabelService extends MaazounGenericService<MaazounBookStockLabel, Long> {
	
	public Page<MaazounBookStockLabel> findByLocationId(Long locationId, Pageable pageable);

	public void updateStatusByIds(String statusFk, Set<Long> ids);
	
	public Page<MaazounBookStockLabel> findByStatusFkAndLocationIdIn(String statusFk, Set<Long> locationIds, Pageable pageable);
	
	public Page<MaazounBookStockLabel> findByBookTypeIdAndLocationIdIn(Long bookTypeId, Set<Long> locationIds, Pageable pageable);
	
	public Page<MaazounBookStockLabel> findByBookTypeIdAndLocationId(Long bookTypeId, Long locationId, Pageable pageable);
	
	public Page<MaazounBookStockLabel> findByStatusFkAndLocationId(String statusFk, Long locationId, Pageable pageable);
	
	public Page<MaazounBookStockLabel> findByStatusFkAndBookTypeIdAndLocationIdIn(String statusFk, Long bookTypeId, Set<Long> locationIds, 
			Pageable pageable);
	
	public Page<MaazounBookStockLabel> findByStatusFkAndBookTypeIdAndLocationId(String statusFk, Long bookTypeId, 
			Long locationId, Pageable pageable);
	
	public Optional<MaazounBookStockLabel> findByLabelCode(String labelCode);
	
	public Optional<MaazounBookStockLabel> findByLabelCodeAndStatusFk(String labelCode, String statusFk);
	
	public List<MaazounBookStockLabel> findByStatusFkAndLocationIdInOrderByIdAsc(String statusFk, Set<Long> locationIds);
	
	public void updateStatusByLabelCode(@Param("statusFk") String statusFk, @Param("labelCodes") Set<String> labelCodes);
	
	public void updateStatusById(String statusFk, Long  id);

	
}
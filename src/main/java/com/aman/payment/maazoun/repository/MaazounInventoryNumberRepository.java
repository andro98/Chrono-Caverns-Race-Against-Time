package com.aman.payment.maazoun.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.aman.payment.maazoun.model.MaazounInventoryNumber;

@Repository
public interface MaazounInventoryNumberRepository extends PagingAndSortingRepository<MaazounInventoryNumber, Long> {

	public Optional<MaazounInventoryNumber> findBySectorId(Long sectorId);

	public Optional<MaazounInventoryNumber> findByTypeId(String typeId);

	public Optional<MaazounInventoryNumber> findByYear(String year);
	
	public Optional<MaazounInventoryNumber> findBySectorIdAndTypeIdAndYear(Long sectorId, String typeId, Long year);

}

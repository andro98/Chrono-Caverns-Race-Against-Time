package com.aman.payment.maazoun.service;

import java.util.Optional;

import com.aman.payment.maazoun.model.MaazounInventoryNumber;

public interface MaazounInventoryNumberService extends MaazounGenericService<MaazounInventoryNumber, Long> {
	
	public Optional<MaazounInventoryNumber> findBySectorId(Long sectorId);

	public Optional<MaazounInventoryNumber> findByTypeId(String typeId);

	public Optional<MaazounInventoryNumber> findByYear(String year);
	
	public Optional<MaazounInventoryNumber> findBySectorIdAndTypeIdAndYear(Long sectorId, String typeId, Long year);
	
}
package com.aman.payment.auth.service;

import java.util.List;

import com.aman.payment.auth.model.MaazouniaChurch;
import com.aman.payment.auth.model.Sector;
import com.aman.payment.core.service.GenericService;

public interface MaazouniaChurchService extends GenericService<MaazouniaChurch, Long> {
	
	public List<MaazouniaChurch> findBySectorFk(Sector sectorFk);
	
	public  List<MaazouniaChurch> lookforMaazouniaChurch(String searchBy);

	public Long getMaxId();


	
}
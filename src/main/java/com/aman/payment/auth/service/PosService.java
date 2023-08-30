package com.aman.payment.auth.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aman.payment.auth.model.Pos;
import com.aman.payment.auth.model.Sector;
import com.aman.payment.core.service.GenericService;

public interface PosService extends GenericService<Pos, Long> {
	
	public List<Pos> findBystatusFkOrderBySectorFkAsc(String status);
	
	public List<Pos> getAllPos(long id);
	
	public List<Pos> getAllPosBySectorId(Sector sectorFk);

	public Pos getPOSById(long posId);
	
	public Page<Pos> lookforPos(String searchBy, Pageable pageable);
	
	public List<Pos> serchPOS(String keyword);
	
	public List<String> getAgentUserForPOS(Long posId);
	
}
package com.aman.payment.auth.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.Pos;
import com.aman.payment.auth.model.Sector;
import com.aman.payment.auth.repository.PosRepository;
import com.aman.payment.auth.service.PosService;

@Service
@Transactional
public class PosServiceImpl implements PosService {
    private final PosRepository repository;

    public PosServiceImpl(PosRepository repository) {
        this.repository = repository;
    }

    @Override
    public Pos save(Pos entity) {
        return repository.save(entity);
    }

    @Override
    public List<Pos> save(List<Pos> entities) {
        return (List<Pos>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Cacheable(value = "posCache",key = "#id", unless = "#result==null")
    public Optional<Pos> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Pos> findAll() {
        return (List<Pos>) repository.findAll();
    }

    @Override
    public Page<Pos> findAll(Pageable pageable) {
        Page<Pos> entityPage = repository.findAll(pageable);
        List<Pos> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public Pos update(Pos entity, Long id) {
        Optional<Pos> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }

	@Override
	public List<Pos> findBystatusFkOrderBySectorFkAsc(String status) {
		// TODO Auto-generated method stub
		return null;
	}
	
	 @Override
	    public List<Pos> getAllPos(long id) {
	        return repository.getAllPos(id);
	    }

//	@Override
//	public List<UserPosDto>getAllUsersPosForAgent(Long posId) {
//		// TODO Auto-generated method stub
//		return (List<UserPosDto>) repository.listAllUsersForPOSForAgent(posId);
//	}
	
	@Override
	public void deleteAll(List<Pos> entities) {
		repository.deleteAll(entities);
	}

	@Override
	public List<Pos> getAllPosBySectorId(Sector sectorFk) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Pos getPOSById(long posId) {
		return repository.findById(posId).get();
	}

	@Override
	public Page<Pos> lookforPos(String searchBy, Pageable pageable) {

    	Page<Pos> entityPage = repository.findByNameContainsOrCodeContains
         (searchBy, searchBy, pageable);
        return new PageImpl<>(entityPage.getContent(), pageable, entityPage.getTotalElements());
	}

	public List<Pos> serchPOS(String keyword){
		Optional<List<Pos>> result = repository.findByNameLike("%"+keyword+"%");
		if(result.isPresent()) {			
			return result.get();
		}
		return null;
	}
	
	@Override
	public List<String> getAgentUserForPOS(Long posId){
		return repository.listAllUsersForPOSForAgent(posId);
	}
}
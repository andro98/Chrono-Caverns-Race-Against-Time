package com.aman.payment.maazoun.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aman.payment.maazoun.model.MaazounContractRefundAtt;
import com.aman.payment.maazoun.repository.MaazounContractRefundAttRepository;
import com.aman.payment.maazoun.service.MaazounContractRefundAttService;

@Service
@Transactional
public class MaazounContractRefundAttServiceImpl implements MaazounContractRefundAttService {

	@Autowired
    private final MaazounContractRefundAttRepository repository;
	
	public MaazounContractRefundAttServiceImpl(MaazounContractRefundAttRepository repository) {
        this.repository = repository;
    }

	@Override
	public MaazounContractRefundAtt save(MaazounContractRefundAtt entity) {
		// TODO Auto-generated method stub
		return repository.save(entity);
	}

	@Override
	public List<MaazounContractRefundAtt> save(List<MaazounContractRefundAtt> entities) {
		// TODO Auto-generated method stub
		return (List<MaazounContractRefundAtt>) repository.saveAll(entities);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	public void deleteAll(List<MaazounContractRefundAtt> entities) {
		// TODO Auto-generated method stub
		repository.deleteAll();
	}

	@Override
	public Optional<MaazounContractRefundAtt> findById(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public List<MaazounContractRefundAtt> findAll() {
		// TODO Auto-generated method stub
		return (List<MaazounContractRefundAtt>) repository.findAll();
	}

	@Override
	public Page<MaazounContractRefundAtt> findAll(Pageable pageable) {
		Page<MaazounContractRefundAtt> entityPage = repository.findAll(pageable);
        List<MaazounContractRefundAtt> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
	}

	@Override
	public MaazounContractRefundAtt update(MaazounContractRefundAtt entity, Long id) {
		// TODO Auto-generated method stub
		Optional<MaazounContractRefundAtt> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
	}

	@Override
	public Optional<MaazounContractRefundAtt> findByMaazounBookCollectionInfoId(long maazounBookCollectionInfoId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}

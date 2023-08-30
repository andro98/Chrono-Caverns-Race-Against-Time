package com.aman.payment.maazoun.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aman.payment.maazoun.model.MaazounRefundAtt;
import com.aman.payment.maazoun.repository.MaazounRefundAttRepository;
import com.aman.payment.maazoun.service.MaazounRefundAttService;

@Service
@Transactional
public class MaazounRefundAttServiceImpl implements MaazounRefundAttService {

	@Autowired
    private final MaazounRefundAttRepository repository;
	
	public MaazounRefundAttServiceImpl(MaazounRefundAttRepository repository) {
        this.repository = repository;
    }

	@Override
	public MaazounRefundAtt save(MaazounRefundAtt entity) {
		// TODO Auto-generated method stub
		return repository.save(entity);
	}

	@Override
	public List<MaazounRefundAtt> save(List<MaazounRefundAtt> entities) {
		// TODO Auto-generated method stub
		return (List<MaazounRefundAtt>) repository.saveAll(entities);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	public void deleteAll(List<MaazounRefundAtt> entities) {
		// TODO Auto-generated method stub
		repository.deleteAll();
	}

	@Override
	public Optional<MaazounRefundAtt> findById(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public List<MaazounRefundAtt> findAll() {
		// TODO Auto-generated method stub
		return (List<MaazounRefundAtt>) repository.findAll();
	}

	@Override
	public Page<MaazounRefundAtt> findAll(Pageable pageable) {
		Page<MaazounRefundAtt> entityPage = repository.findAll(pageable);
        List<MaazounRefundAtt> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
	}

	@Override
	public MaazounRefundAtt update(MaazounRefundAtt entity, Long id) {
		// TODO Auto-generated method stub
		Optional<MaazounRefundAtt> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
	}

	@Override
	public Optional<MaazounRefundAtt> findByMaazounBookRequestInfoId(long maazounBookRequestInfoId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}

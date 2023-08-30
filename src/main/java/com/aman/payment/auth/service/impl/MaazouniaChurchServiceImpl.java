package com.aman.payment.auth.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.MaazouniaChurch;
import com.aman.payment.auth.model.Sector;
import com.aman.payment.auth.repository.MaazouniaChurchRepository;
import com.aman.payment.auth.service.MaazouniaChurchService;

@Service
@Transactional
public class MaazouniaChurchServiceImpl implements MaazouniaChurchService {
    private final MaazouniaChurchRepository repository;

    public MaazouniaChurchServiceImpl(MaazouniaChurchRepository repository) {
        this.repository = repository;
    }

    @Override
    public MaazouniaChurch save(MaazouniaChurch entity) {
        return repository.save(entity);
    }

    @Override
    public List<MaazouniaChurch> save(List<MaazouniaChurch> entities) {
        return (List<MaazouniaChurch>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<MaazouniaChurch> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<MaazouniaChurch> findAll() {
        return (List<MaazouniaChurch>) repository.findAll();
    }

    @Override
    public Page<MaazouniaChurch> findAll(Pageable pageable) {
        Page<MaazouniaChurch> entityPage = repository.findAll(pageable);
        List<MaazouniaChurch> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public MaazouniaChurch update(MaazouniaChurch entity, Long id) {
        Optional<MaazouniaChurch> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }

	@Override
	public void deleteAll(List<MaazouniaChurch> entities) {
		repository.deleteAll(entities);
	}

	@Override
	public List<MaazouniaChurch> findBySectorFk(Sector sectorFk) {
		// TODO Auto-generated method stub
		return repository.findBySectorFk(sectorFk);
	}

	@Override
	public List<MaazouniaChurch> lookforMaazouniaChurch(String searchBy) {
		// TODO Auto-generated method stub
		return  repository.findByNameContainsOrStatusFkContains(searchBy, searchBy);
	}

	@Override
	public Long getMaxId() {
		// TODO Auto-generated method stub
		return repository.getMaxId();
	}
}
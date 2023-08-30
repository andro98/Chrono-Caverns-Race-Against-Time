package com.aman.payment.auth.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.MaazounMaazouniaChurch;
import com.aman.payment.auth.model.MaazouniaChurch;
import com.aman.payment.auth.model.Sector;
import com.aman.payment.auth.repository.MaazounMaazouniaChurchRepository;
import com.aman.payment.auth.service.MaazounMaazouniaChurchService;

@Service
@Transactional
public class MaazounMaazouniaChurchServiceImpl implements MaazounMaazouniaChurchService {
    private final MaazounMaazouniaChurchRepository repository;

    public MaazounMaazouniaChurchServiceImpl(MaazounMaazouniaChurchRepository repository) {
        this.repository = repository;
    }

    @Override
    public MaazounMaazouniaChurch save(MaazounMaazouniaChurch entity) {
        return repository.save(entity);
    }

    @Override
    public List<MaazounMaazouniaChurch> save(List<MaazounMaazouniaChurch> entities) {
        return (List<MaazounMaazouniaChurch>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<MaazounMaazouniaChurch> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<MaazounMaazouniaChurch> findAll() {
        return (List<MaazounMaazouniaChurch>) repository.findAll();
    }

    @Override
    public Page<MaazounMaazouniaChurch> findAll(Pageable pageable) {
        Page<MaazounMaazouniaChurch> entityPage = repository.findAll(pageable);
        List<MaazounMaazouniaChurch> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public MaazounMaazouniaChurch update(MaazounMaazouniaChurch entity, Long id) {
        Optional<MaazounMaazouniaChurch> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }

	@Override
	public void deleteAll(List<MaazounMaazouniaChurch> entities) {
		repository.deleteAll(entities);
	}

}
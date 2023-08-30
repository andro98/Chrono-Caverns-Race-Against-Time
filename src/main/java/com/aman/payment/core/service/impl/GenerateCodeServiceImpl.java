package com.aman.payment.core.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aman.payment.core.model.GenerateCode;
import com.aman.payment.core.repository.GenerateCodeRepository;
import com.aman.payment.core.service.GenerateCodeService;

@Service
@Transactional
public class GenerateCodeServiceImpl implements GenerateCodeService {
    private final GenerateCodeRepository repository;

	public GenerateCodeServiceImpl(GenerateCodeRepository repository) {
        this.repository = repository;
    }

    @Override
    public GenerateCode save(GenerateCode entity) {
        return repository.save(entity);
    }

    @Override
    public List<GenerateCode> save(List<GenerateCode> entities) {
        return (List<GenerateCode>) repository.saveAll(entities);
    }

    @Override
    @CacheEvict(value = "generateCodeCache", key = "#id")
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Cacheable(value = "generateCodeCache",key = "#id", unless = "#result==null")
    public synchronized Optional<GenerateCode> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<GenerateCode> findAll() {
        return (List<GenerateCode>) repository.findAll();
    }

    @Override
    public Page<GenerateCode> findAll(Pageable pageable) {
        Page<GenerateCode> entityPage = repository.findAll(pageable);
        List<GenerateCode> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    @CachePut(value = "generateCodeCache", key = "#id")
    public GenerateCode update(GenerateCode entity, Long id) {
        Optional<GenerateCode> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }

    @Override
	public GenerateCode findByKeyName(String keyName) {
		// TODO Auto-generated method stub
		return repository.findByKeyName(keyName);
	}

	@Override
	public void deleteAll(List<GenerateCode> entities) {
		repository.deleteAll(entities);
	}
}
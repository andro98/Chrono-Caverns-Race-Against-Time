package com.aman.payment.auth.service.impl;

import com.aman.payment.auth.model.SubService;
import com.aman.payment.auth.model.SubServicePriceTier;
import com.aman.payment.auth.repository.SubServicePriceTierRepository;
import com.aman.payment.auth.service.SubServicePriceTierService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SubServicePriceTierServiceImpl implements SubServicePriceTierService {

    private final SubServicePriceTierRepository repository;

    public SubServicePriceTierServiceImpl(SubServicePriceTierRepository repository) {
        this.repository = repository;
    }

//    @Override
    public List<SubServicePriceTier> getSubServicePriceTierBySubServiceFk(SubService subService) {
        return repository.getSubServiceBySubServiceFk(subService);
    }

    @Override
    public SubServicePriceTier save(SubServicePriceTier entity) {
        return repository.save(entity);
    }

    @Override
    public List<SubServicePriceTier> save(List<SubServicePriceTier> entities) {
        return (List<SubServicePriceTier>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAll(List<SubServicePriceTier> entities) {
        repository.deleteAll(entities);
    }

    @Override
//    @Cacheable(value = "serviceCache",key = "#id", unless = "#result==null")
    public Optional<SubServicePriceTier> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<SubServicePriceTier> findAll() {
        return (List<SubServicePriceTier>) repository.findAll();
    }

    @Override
    public Page<SubServicePriceTier> findAll(Pageable pageable) {
        Page<SubServicePriceTier> entityPage = repository.findAll(pageable);
        List<SubServicePriceTier> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public SubServicePriceTier update(SubServicePriceTier entity, Long id) {
        Optional<SubServicePriceTier> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }

    @Override
    public List<SubServicePriceTier> getSubServiceBySubServiceFk(SubService subService) {
        return repository.getSubServiceBySubServiceFk(subService);
    }
}

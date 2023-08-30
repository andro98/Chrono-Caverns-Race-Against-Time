package com.aman.payment.auth.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.Location;
import com.aman.payment.auth.model.Merchant;
import com.aman.payment.auth.repository.MerchantRepository;
import com.aman.payment.auth.service.MerchantService;

@Service
@Transactional
public class MerchantServiceImpl implements MerchantService {
    private final MerchantRepository repository;

    public MerchantServiceImpl(MerchantRepository repository) {
        this.repository = repository;
    }

    @Override
    public Merchant save(Merchant entity) {
        return repository.save(entity);
    }

    @Override
    public List<Merchant> save(List<Merchant> entities) {
        return (List<Merchant>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Cacheable(value = "merchantCache",key = "#id", unless = "#result==null")
    public Optional<Merchant> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Merchant> findAll() {
        return (List<Merchant>) repository.findAll();
    }

    @Override
    public Page<Merchant> findAll(Pageable pageable) {
        Page<Merchant> entityPage = repository.findAll(pageable);
        List<Merchant> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public Merchant update(Merchant entity, Long id) {
        Optional<Merchant> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }
    
    @Override
	public void deleteAll(List<Merchant> entities) {
		repository.deleteAll(entities);
	}

	@Override
	public Page<Merchant> lookforMerchant(String searchBy, Pageable pageable) {
		 Page<Merchant> entityPage = repository.findByNameContainsOrCodeContainsOrDescriptionContainsOrAddressContainsOrPhone1ContainsOrPhone2ContainsOrMobileContains
				 (searchBy,searchBy,searchBy,searchBy,searchBy,searchBy,searchBy,pageable);
	        List<Merchant> entities = entityPage.getContent();
	        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
	}
}
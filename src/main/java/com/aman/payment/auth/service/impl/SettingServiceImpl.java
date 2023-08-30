package com.aman.payment.auth.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.Setting;
import com.aman.payment.auth.repository.SettingRepository;
import com.aman.payment.auth.service.SettingService;

@Service
@Transactional
public class SettingServiceImpl implements SettingService {
    private final SettingRepository repository;

    public SettingServiceImpl(SettingRepository repository) {
        this.repository = repository;
    }

    @Override
    public Setting save(Setting entity) {
        return repository.save(entity);
    }

    @Override
    public List<Setting> save(List<Setting> entities) {
        return (List<Setting>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Setting> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Setting> findAll() {
        return (List<Setting>) repository.findAll();
    }

    @Override
    public Page<Setting> findAll(Pageable pageable) {
        Page<Setting> entityPage = repository.findAll(pageable);
        List<Setting> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public Setting update(Setting entity, Long id) {
        Optional<Setting> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }

	@Override
	public void deleteAll(List<Setting> entities) {
		repository.deleteAll(entities);
	}

	@Override
	public Optional<Setting> findByKeyOps(String keyOps) {
		// TODO Auto-generated method stub
		return repository.findByKeyOps(keyOps);
	}
}
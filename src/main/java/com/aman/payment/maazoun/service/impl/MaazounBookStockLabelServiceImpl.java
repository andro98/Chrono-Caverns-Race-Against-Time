package com.aman.payment.maazoun.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import com.aman.payment.maazoun.model.payload.SearchStockLabelRequest;
import com.aman.payment.maazoun.repository.MaazounBookStockLabelCustomRepository;
import com.aman.payment.maazoun.repository.MaazounBookStockLabelCustomRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aman.payment.maazoun.model.MaazounBookStockLabel;
import com.aman.payment.maazoun.repository.MaazounBookStockLabelRepository;
import com.aman.payment.maazoun.service.MaazounBookStockLabelService;

@Service
@Transactional
public class MaazounBookStockLabelServiceImpl implements MaazounBookStockLabelService {

    @Autowired
    private final MaazounBookStockLabelRepository repository;

    private final MaazounBookStockLabelCustomRepository customRepository;

    @Autowired
    public MaazounBookStockLabelServiceImpl(MaazounBookStockLabelRepository repository, MaazounBookStockLabelCustomRepository customRepository) {
        this.repository = repository;
        this.customRepository = customRepository;
    }

    @Override
    public MaazounBookStockLabel save(MaazounBookStockLabel entity) {
        // TODO Auto-generated method stub
        return repository.save(entity);
    }

    @Override
    public List<MaazounBookStockLabel> save(List<MaazounBookStockLabel> entities) {
        // TODO Auto-generated method stub
        return (List<MaazounBookStockLabel>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }

    @Override
    public void deleteAll(List<MaazounBookStockLabel> entities) {
        // TODO Auto-generated method stub
        repository.deleteAll();
    }

    @Override
    public Optional<MaazounBookStockLabel> findById(Long id) {
        // TODO Auto-generated method stub
        return repository.findById(id);
    }

    @Override
    public List<MaazounBookStockLabel> findAll() {
        // TODO Auto-generated method stub
        return (List<MaazounBookStockLabel>) repository.findAll();
    }

    @Override
    public Page<MaazounBookStockLabel> findAll(Pageable pageable) {
        Page<MaazounBookStockLabel> entityPage = repository.findAll(pageable);
        List<MaazounBookStockLabel> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public MaazounBookStockLabel update(MaazounBookStockLabel entity, Long id) {
        // TODO Auto-generated method stub
        Optional<MaazounBookStockLabel> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }

    @Override
    public void updateStatusByIds(String statusFk, Set<Long> locationIds) {
        repository.updateStatusByIds(statusFk, locationIds);
    }

    @Override
    public Page<MaazounBookStockLabel> findByStatusFkAndLocationIdIn(String statusFk, Set<Long> locationIds, Pageable pageable) {
        // TODO Auto-generated method stub
        return repository.findByStatusFkAndLocationIdInOrderByCreatedAtDesc(statusFk, locationIds, pageable);
    }

    @Override
    public Page<MaazounBookStockLabel> findByLocationId(Long locationId, Pageable pageable) {
        // TODO Auto-generated method stub
        return repository.findByLocationIdOrderByCreatedAtDesc(locationId, pageable);
    }

    @Override
    public Page<MaazounBookStockLabel> findByStatusFkAndLocationId(String statusFk, Long locationId,
                                                                   Pageable pageable) {
        // TODO Auto-generated method stub
        return repository.findByStatusFkAndLocationIdOrderByCreatedAtDesc(statusFk, locationId, pageable);
    }

    @Override
    public Optional<MaazounBookStockLabel> findByLabelCode(String labelCode) {
        // TODO Auto-generated method stub
        return repository.findByLabelCode(labelCode);
    }

    @Override
    public Optional<MaazounBookStockLabel> findByLabelCodeAndStatusFk(String labelCode, String statusFk) {
        // TODO Auto-generated method stub
        return repository.findByLabelCodeAndStatusFk(labelCode, statusFk);
    }

    @Override
    public Optional<MaazounBookStockLabel> findByLabelCodeAndLocationId(String labelCode, Set<Long> locationIds) {
        return repository.findByLabelCodeAndLocationId(labelCode, locationIds);
    }

    @Override
    public Page<MaazounBookStockLabel> findByBookTypeIdAndLocationIdIn(Long bookTypeId, Set<Long> locationIds, Pageable pageable) {
        // TODO Auto-generated method stub
        return repository.findByBookTypeIdAndLocationIdInOrderByCreatedAtDesc(bookTypeId, locationIds, pageable);
    }

    @Override
    public Page<MaazounBookStockLabel> findByStatusFkAndBookTypeIdAndLocationIdIn(String statusFk, Long bookTypeId,
                                                                                  Set<Long> locationIds, Pageable pageable) {
        // TODO Auto-generated method stub
        return repository.findByStatusFkAndBookTypeIdAndLocationIdInOrderByCreatedAtDesc(statusFk, bookTypeId, locationIds, pageable);
    }

    @Override
    public Page<MaazounBookStockLabel> findByBookTypeIdAndLocationId(Long bookTypeId, Long locationId,
                                                                     Pageable pageable) {
        // TODO Auto-generated method stub
        return repository.findByBookTypeIdAndLocationIdOrderByCreatedAtDesc(bookTypeId, locationId, pageable);
    }

    @Override
    public Page<MaazounBookStockLabel> findByStatusFkAndBookTypeIdAndLocationId(String statusFk, Long bookTypeId,
                                                                                Long locationId, Pageable pageable) {
        // TODO Auto-generated method stub
        return repository.findByStatusFkAndBookTypeIdAndLocationIdOrderByCreatedAtDesc(statusFk, bookTypeId, locationId, pageable);
    }

    @Override
    public List<MaazounBookStockLabel> findByStatusFkAndLocationIdInOrderByIdAsc(String statusFk, Set<Long> locationIds) {
        // TODO Auto-generated method stub
        return repository.findByStatusFkAndLocationIdInOrderByIdAsc(statusFk, locationIds);
    }

    @Override
    public void updateStatusByLabelCode(String statusFk, Set<String> labelCodes) {
        repository.updateStatusByLabelCode(statusFk, labelCodes);

    }

    @Override
    public void updateStatusById(String statusFk, Long id) {
        repository.updateStatusById(statusFk, id);


    }

    @Override
    public Page<MaazounBookStockLabel> findBy(SearchStockLabelRequest searchStockLabelRequest, Set<Long> locationIds, Pageable pageable) {
        return customRepository.findBy(
                searchStockLabelRequest,
                locationIds,
                pageable);
    }

    @Override
    public List<MaazounBookStockLabel> findBy(SearchStockLabelRequest searchStockLabelRequest, Set<Long> locationIds) {
        return customRepository.findBy(
                searchStockLabelRequest,
                locationIds);
    }
}

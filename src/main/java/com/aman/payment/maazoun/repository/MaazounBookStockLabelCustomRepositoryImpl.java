package com.aman.payment.maazoun.repository;

import com.aman.payment.maazoun.model.MaazounBookStockLabel;
import com.aman.payment.maazoun.model.MaazounBookWarehouse;
import com.aman.payment.maazoun.model.payload.SearchStockLabelRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

@Repository
public class MaazounBookStockLabelCustomRepositoryImpl implements MaazounBookStockLabelCustomRepository {

    @PersistenceContext
    private EntityManager em;

    public MaazounBookStockLabelCustomRepositoryImpl(EntityManager em) {
        super();
        this.em = em;
    }

    @Override
    public Page<MaazounBookStockLabel> findBy(SearchStockLabelRequest searchStockLabelRequest, Set<Long> locationIds, Pageable pageable) {
        String jpql = " SELECT mo FROM MaazounBookStockLabel mo ";
        String jpqlCount = " SELECT count(mo) FROM MaazounBookStockLabel mo ";

        jpql += " WHERE mo.locationId IN ("
                + locationIds.toString().replace("]", "").replace("[", "") + ")";

        jpqlCount += " WHERE mo.locationId IN (" + locationIds.toString().replace("]", "").replace("[", "") + ")";

        if (searchStockLabelRequest.getBookTypeId() != null) {
            jpql += " And mo.bookTypeId = " + searchStockLabelRequest.getBookTypeId();
            jpqlCount += " And mo.bookTypeId = " + searchStockLabelRequest.getBookTypeId();
        }

        if (searchStockLabelRequest.getBookTierId() != null) {
            jpql += " AND mo.bookTierId = " + searchStockLabelRequest.getBookTierId();
            jpqlCount += " AND mo.bookTierId = " + searchStockLabelRequest.getBookTierId();
        }

        if (searchStockLabelRequest.getStatus() != null) {
            jpql += " AND mo.statusFk = '" + searchStockLabelRequest.getStatus() + "'";
            jpqlCount += " AND mo.statusFk = '" + searchStockLabelRequest.getStatus() + "'";
        }


        TypedQuery<MaazounBookStockLabel> query = em.createQuery(jpql, MaazounBookStockLabel.class);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        TypedQuery<Long> queryCount = em.createQuery(jpqlCount, Long.class);
        long total = queryCount.getSingleResult();

        return new PageImpl<MaazounBookStockLabel>(query.getResultList(), pageable, total);
    }

    @Override
    public List<MaazounBookStockLabel> findBy(SearchStockLabelRequest searchStockLabelRequest, Set<Long> locationIds) {
        String jpql = " SELECT mo FROM MaazounBookStockLabel mo ";

        jpql += " WHERE mo.locationId IN ("
                + locationIds.toString().replace("]", "").replace("[", "") + ")";


        if (searchStockLabelRequest.getBookTypeId() != null) {
            jpql += " And mo.bookTypeId = " + searchStockLabelRequest.getBookTypeId();
        }

        if (searchStockLabelRequest.getBookTierId() != null) {
            jpql += " AND mo.bookTierId = " + searchStockLabelRequest.getBookTierId();
        }

        if (searchStockLabelRequest.getStatus() != null) {
            jpql += " AND mo.statusFk = '" + searchStockLabelRequest.getStatus() + "'";
        }

        TypedQuery<MaazounBookStockLabel> query = em.createQuery(jpql, MaazounBookStockLabel.class);
        return query.getResultList();
    }
}

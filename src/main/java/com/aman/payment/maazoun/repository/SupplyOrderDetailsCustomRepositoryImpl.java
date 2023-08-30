package com.aman.payment.maazoun.repository;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.aman.payment.maazoun.model.SupplyOrderDetails;
import com.aman.payment.maazoun.model.payload.SupplyOrderDetailsSearchRequest;

@Repository
public class SupplyOrderDetailsCustomRepositoryImpl implements SupplyOrderDetailsCustomRepository {

	@PersistenceContext
	private EntityManager em;

	public SupplyOrderDetailsCustomRepositoryImpl(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<SupplyOrderDetails> searchInSupplyOrderDetails(
			SupplyOrderDetailsSearchRequest searchSupplyOrderDetailsRequest, Set<Long> sectors) {

		String jpql = " SELECT sod FROM SupplyOrderDetails sod " + " JOIN SupplyOrder so ON so.id = sod.supplyOrderFk "
				+ " WHERE so.refSupplyOrderNumber = '" + searchSupplyOrderDetailsRequest.getRefSupplyOrderNumber()
				+ "' AND sod.sectorFK IN (" + sectors.toString().replace("]", "").replace("[", "") + ") ";

		TypedQuery<SupplyOrderDetails> query = em.createQuery(jpql, SupplyOrderDetails.class);

		return query.getResultList();
	}

}

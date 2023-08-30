package com.aman.payment.auth.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class LocationRepositoryCustomImpl implements LocationRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	public LocationRepositoryCustomImpl(EntityManager em) {
		this.em = em;
	}


	@Override
	public long getSequance(Long locationId) {

		String jpql = "SELECT seq_value FROM location"
				+ " WHERE id = " + locationId + "";
		Query query = em.createNativeQuery(jpql);
		Object seq = query.getSingleResult();

		long newSequance = Long.parseLong(seq.toString()) + 1;

		String jpql2 = "UPDATE location SET seq_value = "+newSequance+" "
				+ " WHERE id = " + locationId + "";
		Query query2 = em.createNativeQuery(jpql2);

		query2.executeUpdate();
		return newSequance;

	}

}

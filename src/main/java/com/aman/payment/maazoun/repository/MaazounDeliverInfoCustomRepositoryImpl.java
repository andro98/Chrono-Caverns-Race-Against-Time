package com.aman.payment.maazoun.repository;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.maazoun.model.MaazounBookDeliverInfo;
import com.aman.payment.maazoun.model.payload.SearchDeliveredInfoTransactionRequest;

@Repository
public class MaazounDeliverInfoCustomRepositoryImpl implements MaazounDeliverInfoCustomRepository {

	@PersistenceContext
	private EntityManager em;

	public MaazounDeliverInfoCustomRepositoryImpl(EntityManager em) {
		super();
		this.em = em;
	}

	@Override
	public Page<MaazounBookDeliverInfo> searchDeliveredRequests(CustomUserDetails customUserDetails, 
			SearchDeliveredInfoTransactionRequest obj) {
		
		Pageable pageable = PageRequest.of(Integer.valueOf(obj.getPageNo()), Integer.valueOf(obj.getPageSize()));

		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();
		
		Set<Long> posIds = new HashSet<Long>();
		customUserDetails.getPosSet().stream().forEach(s -> {
			posIds.add(s.getId());
		});

		jpql.append("SELECT mo FROM MaazounBookDeliverInfo mo "
				+ "JOIN Pos s ON s.id = mo.posFk "
				+ "JOIN Location l ON l.id = mo.locationId " 
				+ "WHERE mo.createdAt >= '"
				+ obj.getDurationFrom() + " 00:00:00' " + "AND mo.createdAt <= '" + obj.getDurationTo()
				+ " 23:59:59' ");
		jpqlCount.append("SELECT count(mo) FROM MaazounBookDeliverInfo mo "
				+ "JOIN Pos s ON s.id = mo.posFk "
				+ "JOIN Location l ON l.id = mo.locationId " 
				+ "WHERE " + "mo.createdAt >= '"
				+ obj.getDurationFrom() + " 00:00:00' " + "AND mo.createdAt <= '" + obj.getDurationTo()
				+ " 23:59:59' ");


		if (obj.getLocationId() != null && !obj.getLocationId().isEmpty()) {
			jpql.append("AND mo.locationId = " + obj.getLocationId().trim() + " ");
			jpqlCount.append("AND mo.locationId = " + obj.getLocationId().trim() + " ");
		}

//		if (obj.getPosId() != null && !obj.getPosId().isEmpty()) {
//			jpql.append("AND mo.posFk = " + obj.getPosId().trim() + " ");
//			jpqlCount.append("AND mo.posFk = " + obj.getPosId().trim() + " ");
//		}

		if (obj.getReqRefNumber() != null && !obj.getReqRefNumber().isEmpty()) {
			jpql.append("AND mo.reqRefNumber = '" + obj.getReqRefNumber().trim() + "' ");
			jpqlCount.append("AND mo.reqRefNumber  = '" + obj.getReqRefNumber().trim() + "' ");
		}

		jpql.append(" AND mo.posFk IN ("
				+ posIds.toString().replace("]", "").replace("[", "") + ") ");
		jpqlCount.append(" AND mo.posFk IN ("
				+ posIds.toString().replace("]", "").replace("[", "") + ") ");
		
		TypedQuery<MaazounBookDeliverInfo> query = em.createQuery(jpql.toString(), MaazounBookDeliverInfo.class);
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		TypedQuery<Long> queryCount = em.createQuery(jpqlCount.toString(), Long.class);
		long total = queryCount.getSingleResult();// .getResultList();//.size();

		return new PageImpl<MaazounBookDeliverInfo>(query.getResultList(), pageable, total);
		
	}

	
	
}
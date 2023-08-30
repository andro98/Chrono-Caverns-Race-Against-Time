package com.aman.payment.maazoun.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.maazoun.model.MaazounBookRequestInfo;
import com.aman.payment.maazoun.model.dto.MaazounBookAuditDTO;
import com.aman.payment.maazoun.model.dto.MaazounBookAuditSectorDTO;
import com.aman.payment.maazoun.model.dto.MaazounBookAvailableDTO;
import com.aman.payment.maazoun.model.payload.AdvancedSearchBookRequestInfoTransactionRequest;
import com.aman.payment.maazoun.model.payload.GetBookRequestInfoByMaazounId;
import com.aman.payment.maazoun.model.payload.MaazounAuditRequest;
import com.aman.payment.util.StatusConstant;

@Repository
public class MaazounBookRequestInfoCustomRepositoryImpl implements MaazounBookRequestInfoCustomRepository {

	@PersistenceContext
	private EntityManager em;

	public MaazounBookRequestInfoCustomRepositoryImpl(EntityManager em) {
		super();
		this.em = em;
	}

	@Override
	public Page<MaazounBookRequestInfo> findByMaazounNationalIdAndStatusFkNot(String nationalId, String status,
			Pageable pageable) {

		String jpql = "SELECT mo FROM MaazounBookRequestInfo mo "
				+ "JOIN MaazounProfile p ON p.id = mo.maazounProfileFk " + "WHERE p.nationalId = '" + nationalId
				+ "' AND mo.statusFk != '" + status + "' ";

		String jpqlCount = "SELECT count(mo) FROM MaazounBookRequestInfo mo "
				+ "JOIN MaazounProfile p ON p.id = mo.maazounProfileFk " + "WHERE p.nationalId = '" + nationalId
				+ "' AND mo.statusFk != '" + status + "' ";

		TypedQuery<MaazounBookRequestInfo> query = em.createQuery(jpql, MaazounBookRequestInfo.class);
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		TypedQuery<Long> queryCount = em.createQuery(jpqlCount, Long.class);
		long total = queryCount.getSingleResult();

		return new PageImpl<MaazounBookRequestInfo>(query.getResultList(), pageable, total);
	}

	@Override
	public Page<MaazounBookRequestInfo> findByMaazounCardNumberAndStatusFkNot(String cardNumber, String status,
			Pageable pageable) {

		String jpql = "SELECT mo FROM MaazounBookRequestInfo mo "
				+ "JOIN MaazounProfile p ON p.id = mo.maazounProfileFk " + "WHERE p.cardNumber = '" + cardNumber
				+ "' AND mo.statusFk != '" + status + "' ";

		String jpqlCount = " SELECT count(mo) FROM MaazounBookRequestInfo mo "
				+ "JOIN MaazounProfile p ON p.id = mo.maazounProfileFk " + "WHERE p.cardNumber = '" + cardNumber
				+ "' AND mo.statusFk != '" + status + "' ";

		TypedQuery<MaazounBookRequestInfo> query = em.createQuery(jpql, MaazounBookRequestInfo.class);
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		TypedQuery<Long> queryCount = em.createQuery(jpqlCount, Long.class);
		long total = queryCount.getSingleResult();

		return new PageImpl<MaazounBookRequestInfo>(query.getResultList(), pageable, total);

	}

	@Override
	public Page<MaazounBookRequestInfo> findByMaazounMobileAndStatusFkNot(String mobile, String status,
			Pageable pageable) {

		String jpql = "SELECT mo FROM MaazounBookRequestInfo mo "
				+ "JOIN MaazounProfile p ON p.id = mo.maazounProfileFk " + "WHERE p.mobile = '" + mobile
				+ "' AND mo.statusFk != '" + status + "' ";

		String jpqlCount = "SELECT count(mo) FROM MaazounBookRequestInfo mo "
				+ "JOIN MaazounProfile p ON p.id = mo.maazounProfileFk " + "WHERE p.mobile = '" + mobile
				+ "' AND mo.statusFk != '" + status + "' ";

		TypedQuery<MaazounBookRequestInfo> query = em.createQuery(jpql, MaazounBookRequestInfo.class);
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		TypedQuery<Long> queryCount = em.createQuery(jpqlCount, Long.class);
		long total = queryCount.getSingleResult();

		return new PageImpl<MaazounBookRequestInfo>(query.getResultList(), pageable, total);

	}

	@Override
	public Page<MaazounBookRequestInfo> advancedSearch(AdvancedSearchBookRequestInfoTransactionRequest obj,
			CustomUserDetails customUserDetails) {

		Set<Long> posIds = new HashSet<Long>();
		customUserDetails.getPosSet().stream().forEach(s -> {
			posIds.add(s.getId());
		});
		
		Pageable pageable = PageRequest.of(Integer.valueOf(obj.getPageNo()), Integer.valueOf(obj.getPageSize()));

		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();

		jpql.append("SELECT mo FROM MaazounBookRequestInfo mo " + "JOIN MaazounProfile p ON p.id = mo.maazounProfileFk "
				+ "JOIN Pos s ON s.id = mo.posFk.id " + "JOIN Location l ON l.id = mo.locationId "
				//+ "JOIN Transaction t ON t.transCode = mo.transactionCode "
				//+ "JOIN PaymentType pt ON pt.id = t.paymentTypeFk " 
				+ "WHERE " + "mo.createdAt >= '"
				+ obj.getDurationFrom() + " 00:00:00' " + "AND mo.createdAt <= '" + obj.getDurationTo()
				+ " 23:59:59' ");

		jpqlCount.append("SELECT count(mo) FROM MaazounBookRequestInfo mo "
				+ "JOIN MaazounProfile p ON p.id = mo.maazounProfileFk " + "JOIN Pos s ON s.id = mo.posFk.id "
				+ "JOIN Location l ON l.id = mo.locationId " 
				//+ "JOIN Transaction t ON t.transCode = mo.transactionCode "
				//+ "JOIN PaymentType pt ON pt.id = t.paymentTypeFk " 
				+ "WHERE " + "mo.createdAt >= '"
				+ obj.getDurationFrom() + " 00:00:00' " + "AND mo.createdAt <= '" + obj.getDurationTo()
				+ " 23:59:59' ");

		if (obj.getStatus() != null && !obj.getStatus().isEmpty()) {
			jpql.append("AND mo.statusFk = '" + obj.getStatus() + "' ");
			jpqlCount.append("AND mo.statusFk = '" + obj.getStatus() + "' ");
		}

		if (obj.getAgentName() != null && !obj.getAgentName().isEmpty()) {
			jpql.append("AND mo.createdBy = '" + obj.getAgentName().trim() + "' ");
			jpqlCount.append("AND mo.createdBy = '" + obj.getAgentName().trim() + "' ");
		}

		if (obj.getLocationName() != null && !obj.getLocationName().isEmpty()) {
			jpql.append("AND l.locationName = '" + obj.getLocationName().trim() + "' ");
			jpqlCount.append("AND l.locationName = '" + obj.getLocationName().trim() + "' ");
		}

		if (obj.getPosId() != null && !obj.getPosId().isEmpty()) {
			jpql.append("AND mo.posFk.id = " + obj.getPosId().trim() + " ");
			jpqlCount.append("AND mo.posFk.id = " + obj.getPosId().trim() + " ");
		}

		if (obj.getTransactionCode() != null && !obj.getTransactionCode().isEmpty()) {
			jpql.append("AND mo.transactionCode = '" + obj.getTransactionCode().trim() + "' ");
			jpqlCount.append("AND mo.transactionCode  = '" + obj.getTransactionCode().trim() + "' ");
		}

		if (obj.getNationalIds() != null && !obj.getNationalIds().isEmpty()) {
			jpql.append("AND p.nationalId = '" + obj.getNationalIdString() + "' ");
			jpqlCount.append("AND p.nationalId = '" + obj.getNationalIdString() + "' ");
		}

//		if (obj.getPaymentMethod() != null && !obj.getPaymentMethod().isEmpty()) {
//			jpql.append("AND pt.name = '" + obj.getPaymentMethod().trim() + "' ");
//			jpqlCount.append("AND pt.name  = '" + obj.getPaymentMethod().trim() + "' ");
//		}

		if (obj.getMaazounName() != null && !obj.getMaazounName().isEmpty()) {
			jpql.append("AND p.name LIKE'%" + obj.getMaazounName() + "%' ");
			jpqlCount.append("AND p.name LIKE  '%" + obj.getMaazounName() + "%' ");
		}

		if (obj.getMobileNumber() != null && !obj.getMobileNumber().isEmpty()) {
			jpql.append("AND p.mobile = '" + obj.getMobileNumber() + "' ");
			jpqlCount.append("AND p.mobile  = '" + obj.getMobileNumber() + "' ");
		}

		if (obj.getNationalId() != null && !obj.getNationalId().isEmpty()) {
			jpql.append("AND p.nationalId = '" + obj.getNationalId() + "' ");
			jpqlCount.append("AND p.nationalId  = '" + obj.getNationalId() + "' ");
		}
		if (obj.getSectorId() != null) {
			jpql.append("AND mo.sectorFk.id = " + obj.getSectorId() + " ");
			jpqlCount.append("AND mo.sectorFk.id = " + obj.getSectorId() + " ");
		}
		
		if (customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT_SUPERVISOR)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AREA_MANAGER)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_SUPERVISOR)) {

			jpql.append(" AND mo.posFk IN (" + posIds.toString().replace("]", "").replace("[", "") + ") ");
			jpqlCount.append(" AND mo.posFk IN (" + posIds.toString().replace("]", "").replace("[", "") + ") ");
		}

		TypedQuery<MaazounBookRequestInfo> query = em.createQuery(jpql.toString(), MaazounBookRequestInfo.class);
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		TypedQuery<Long> queryCount = em.createQuery(jpqlCount.toString(), Long.class);
		long total = queryCount.getSingleResult();// .getResultList();//.size();

		return new PageImpl<MaazounBookRequestInfo>(query.getResultList(), pageable, total);

	}

	@Override
	public Page<MaazounBookAuditDTO> getAuditByCreatedDate(MaazounAuditRequest maazounAuditRequest,
			CustomUserDetails customUserDetails) {

		Set<Long> posIds = new HashSet<Long>();
		customUserDetails.getPosSet().stream().forEach(s -> {
			posIds.add(s.getId());
		});

		Pageable pageable = PageRequest.of(Integer.valueOf(maazounAuditRequest.getPageNo()),
				Integer.valueOf(maazounAuditRequest.getPageSize()));

		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();

		List<MaazounBookAuditDTO> result = new ArrayList<MaazounBookAuditDTO>();
		jpql.append("SELECT"
//				+ " COUNT(*) as totalTransaction,"
				+ " SUM(IF (v.status_fk != 'Refund', 1, 0)) as totalTransactions,"
				+ " SUM(IF (v.status_fk = 'Refund', 1, 0)) as totalRefundTransaction,"
				+ " SUM(IF (v.status_fk = 'Canceled', 1, 0)) as totalCancelledTransaction,"

				+ " SUM(IF (v.status_fk != 'Refund', v.amount, 0)) as totalTransactionAmount,"
				+ " SUM(IF (v.status_fk = 'Refund', v.amount, 0)) as totalRefundAmount,"
				+ " SUM(IF (v.status_fk = 'Canceled', v.amount, 0)) as totalCanceledAmount,"

				+ " SUM(IF (v.status_fk != 'Refund' && v.payment_method = '1', v.amount, 0)) as totalTransactionsAmountCash,"
				+ " SUM(IF (v.status_fk != 'Refund' && v.payment_method = '2', v.amount, 0)) as totalTransactionsAmountVisa,"

				+ " SUM(IF (v.status_fk = 'Refund' && v.payment_method = '1', v.amount, 0)) as totalRefundAmountCash,"
				+ " SUM(IF (v.status_fk = 'Refund' && v.payment_method = '2', v.amount, 0)) as totalRefundAmountVisa,"

				+ " SUM(IF (v.status_fk = 'Hold', 1, 0)) as totalHoldTransaction,"
				+ " SUM(IF (v.status_fk = 'Final', 1, 0)) as totalFinalTransaction,"
				+ " SUM(IF (v.status_fk = 'Synched', 1, 0)) as totalSynchedTransaction,"

				+ " DATE_FORMAT(v.created_at, '%Y/%m/%d') as createDate" + " FROM maazoun_book_request_info v "
				+ " WHERE v.created_at >= '" + maazounAuditRequest.getDurationFrom() + " 00:00:00' "
				+ " AND v.created_at <= '" + maazounAuditRequest.getDurationTo() + " 23:59:59' "
				+ " AND v.transaction_code is not null");

		jpqlCount.append("SELECT COUNT(*) " + " FROM maazoun_book_request_info v " + " WHERE v.created_at >= '"
				+ maazounAuditRequest.getDurationFrom() + " 00:00:00' " + " AND v.created_at <= '"
				+ maazounAuditRequest.getDurationTo() + " 23:59:59' " + " AND v.transaction_code is not null");

		if (customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT_SUPERVISOR)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AREA_MANAGER)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_SUPERVISOR)) {

			jpql.append(" AND v.pos_fk IN (" + posIds.toString().replace("]", "").replace("[", "") + ") ");
			jpqlCount.append(" AND v.pos_fk IN (" + posIds.toString().replace("]", "").replace("[", "") + ") ");
		}

		jpql.append(" GROUP BY date(v.created_at) ORDER BY v.created_at desc");
		jpqlCount.append(" GROUP BY date(v.created_at)");

		Query query = em.createNativeQuery(jpql.toString());
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Query queryCount = em.createNativeQuery(jpqlCount.toString());
		long total = queryCount.getResultList().size();

		List<Object[]> list = query.getResultList();

		MaazounBookAuditDTO totalMaazounBookAuditDTO = new MaazounBookAuditDTO();
		totalMaazounBookAuditDTO.setCreateDate("الإجمالى");
		totalMaazounBookAuditDTO.setTotalTransaction("0");
		totalMaazounBookAuditDTO.setTotalCollectionTransaction("0");
		totalMaazounBookAuditDTO.setTotalSettlementTransaction("0");
		totalMaazounBookAuditDTO.setTotalRefundTransaction("0");
		totalMaazounBookAuditDTO.setTotalCancelledTransaction("0");
		totalMaazounBookAuditDTO.setSystemTotalCollectionAmount("0");
		totalMaazounBookAuditDTO.setSystemTotalSettlementAmount("0");
		totalMaazounBookAuditDTO.setSystemTotalAmountRefund("0");
		totalMaazounBookAuditDTO.setSystemTotalAmountCash("0");
		totalMaazounBookAuditDTO.setSystemTotalAmountVisa("0");
		totalMaazounBookAuditDTO.setTotalHoldTransaction("0");
		totalMaazounBookAuditDTO.setTotalFinalTransaction("0");
		totalMaazounBookAuditDTO.setTotalSynchedTransaction("0");
		totalMaazounBookAuditDTO.setTotalMatchedTransaction("0");

		for (Object[] q1 : list) {

			String totalTransactions = q1[0].toString();
			String totalRefundTransaction = q1[1].toString();
			String totalCancelledTransaction = q1[2].toString();

			String totalTransactionAmount = String.format("%.2f", q1[3]);
			String totalRefundAmount = String.format("%.2f", q1[4]);
			String totalCanceledAmount = String.format("%.2f", q1[5]);

			String totalTransactionsAmountCash = String.format("%.2f", q1[6]);
			String totalTransactionsAmountVisa = String.format("%.2f", q1[7]);

			String totalRefundAmountCash = String.format("%.2f", q1[8]);
			String totalRefundAmountVisa = String.format("%.2f", q1[9]);

			String totalHoldTransaction = q1[10].toString();
			String totalFinalTransaction = q1[11].toString();
			String totalSynchedTransaction = q1[12].toString();
			String totalMatchedTransaction = String
					.valueOf(Long.valueOf(totalFinalTransaction) + Long.valueOf(totalSynchedTransaction));

			String createDate = q1[13].toString();

			MaazounBookAuditDTO maazounBookAuditDTO = new MaazounBookAuditDTO();
			maazounBookAuditDTO.setTotalTransaction(totalTransactions);
			maazounBookAuditDTO.setTotalCollectionTransaction(
					String.valueOf(Long.valueOf(totalTransactions) - Long.valueOf(totalRefundTransaction)));
			maazounBookAuditDTO.setTotalSettlementTransaction(
					String.valueOf(Long.valueOf(totalTransactions) - Long.valueOf(totalCancelledTransaction)));
			maazounBookAuditDTO.setTotalRefundTransaction(totalRefundTransaction);
			maazounBookAuditDTO.setTotalCancelledTransaction(totalCancelledTransaction);

			maazounBookAuditDTO.setSystemTotalCollectionAmount(
					String.valueOf(Double.valueOf(totalTransactionAmount) - Double.valueOf(totalRefundAmount)));
			maazounBookAuditDTO.setSystemTotalSettlementAmount(
					String.valueOf(Double.valueOf(totalTransactionAmount) - Double.valueOf(totalCanceledAmount)));
			maazounBookAuditDTO.setSystemTotalAmountRefund(totalRefundAmount);

			maazounBookAuditDTO.setSystemTotalAmountCash(String
					.valueOf(Double.valueOf(totalTransactionsAmountCash) - Double.valueOf(totalRefundAmountCash)));
			maazounBookAuditDTO.setSystemTotalAmountVisa(String
					.valueOf(Double.valueOf(totalTransactionsAmountVisa) - Double.valueOf(totalRefundAmountVisa)));

			maazounBookAuditDTO.setTotalHoldTransaction(totalHoldTransaction);
			maazounBookAuditDTO.setTotalFinalTransaction(totalFinalTransaction);
			maazounBookAuditDTO.setTotalSynchedTransaction(totalSynchedTransaction);
			maazounBookAuditDTO.setTotalMatchedTransaction(totalMatchedTransaction);

			maazounBookAuditDTO.setCreateDate(createDate);

			result.add(maazounBookAuditDTO);

			accumalteObject(totalMaazounBookAuditDTO, maazounBookAuditDTO);

		}

		result.add(totalMaazounBookAuditDTO);

		return new PageImpl<MaazounBookAuditDTO>(result, pageable, total);
	}

	@Override
	public Page<MaazounBookAuditDTO> getAuditByCreatedDateAndCourt(MaazounAuditRequest maazounAuditRequest,
			CustomUserDetails customUserDetails) {

		Set<Long> posIds = new HashSet<Long>();
		customUserDetails.getPosSet().stream().forEach(s -> {
			posIds.add(s.getId());
		});

		Pageable pageable = PageRequest.of(Integer.valueOf(maazounAuditRequest.getPageNo()),
				Integer.valueOf(maazounAuditRequest.getPageSize()));

		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();

		List<MaazounBookAuditDTO> result = new ArrayList<MaazounBookAuditDTO>();
		jpql.append("SELECT" + " SUM(IF (v.status_fk != 'Refund', 1, 0)) as totalTransactions,"
				+ " SUM(IF (v.status_fk = 'Refund', 1, 0)) as totalRefundTransaction,"
				+ " SUM(IF (v.status_fk = 'Canceled', 1, 0)) as totalCancelledTransaction,"

				+ " SUM(IF (v.status_fk != 'Refund', v.amount, 0)) as totalTransactionAmount,"
				+ " SUM(IF (v.status_fk = 'Refund', v.amount, 0)) as totalRefundAmount,"
				+ " SUM(IF (v.status_fk = 'Canceled', v.amount, 0)) as totalCanceledAmount,"

				+ " SUM(IF (v.status_fk != 'Refund' && v.payment_method = '1', v.amount, 0)) as totalTransactionsAmountCash,"
				+ " SUM(IF (v.status_fk != 'Refund' && v.payment_method = '2', v.amount, 0)) as totalTransactionsAmountVisa,"

				+ " SUM(IF (v.status_fk = 'Refund' && v.payment_method = '1', v.amount, 0)) as totalRefundAmountCash,"
				+ " SUM(IF (v.status_fk = 'Refund' && v.payment_method = '2', v.amount, 0)) as totalRefundAmountVisa,"

				+ " SUM(IF (v.status_fk = 'Hold', 1, 0)) as totalHoldTransaction,"
				+ " SUM(IF (v.status_fk = 'Final', 1, 0)) as totalFinalTransaction,"
				+ " SUM(IF (v.status_fk = 'Synched', 1, 0)) as totalSynchedTransaction,"

				+ " DATE_FORMAT(v.created_at, '%Y/%m/%d') as createDate," + " v.location_id as locationId,"
				+ " c.name_ar as cityName," + " l.name as locationName," + " s.name as sectorName, co.name as courtName"
				+ " FROM maazoun_book_request_info v " + " JOIN sector s ON s.id = v.sector_fk"
				+ " JOIN courts co ON s.court_fk = co.id"
				+ " JOIN location l ON l.id = s.location_fk" + " JOIN city c ON c.id = l.city_fk"
				+ " WHERE v.created_at >= '" + maazounAuditRequest.getDurationFrom() + " 00:00:00' "
				+ " AND v.created_at <= '" + maazounAuditRequest.getDurationTo() + " 23:59:59' "
				+ " AND v.transaction_code is not null");

		jpqlCount.append("SELECT COUNT(*) " + " FROM maazoun_book_request_info v "
				+ " JOIN sector s ON s.id = v.sector_fk" 
				+ " JOIN courts co ON s.court_fk = co.id"
				+ " JOIN location l ON l.id = s.location_fk"
				+ " JOIN city c ON c.id = l.city_fk" + " WHERE v.created_at >= '"
				+ maazounAuditRequest.getDurationFrom() + " 00:00:00' " + " AND v.created_at <= '"
				+ maazounAuditRequest.getDurationTo() + " 23:59:59' " + " AND v.transaction_code is not null");

		if (customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT_SUPERVISOR)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AREA_MANAGER)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_SUPERVISOR)) {

			jpql.append(" AND v.pos_fk IN (" + posIds.toString().replace("]", "").replace("[", "") + ") ");
			jpqlCount.append(" AND v.pos_fk IN (" + posIds.toString().replace("]", "").replace("[", "") + ") ");
		}

		jpql.append(" GROUP BY date(v.created_at), co.id ORDER BY v.created_at desc");
		jpqlCount.append(" GROUP BY date(v.created_at), co.id");

		Query query = em.createNativeQuery(jpql.toString());
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Query queryCount = em.createNativeQuery(jpqlCount.toString());
		long total = queryCount.getResultList().size();

		List<Object[]> list = query.getResultList();

		MaazounBookAuditDTO totalMaazounBookAuditDTO = new MaazounBookAuditDTO();
		totalMaazounBookAuditDTO.setCourtName("الإجمالى");
		totalMaazounBookAuditDTO.setTotalTransaction("0");
		totalMaazounBookAuditDTO.setTotalCollectionTransaction("0");
		totalMaazounBookAuditDTO.setTotalSettlementTransaction("0");
		totalMaazounBookAuditDTO.setTotalRefundTransaction("0");
		totalMaazounBookAuditDTO.setTotalCancelledTransaction("0");
		totalMaazounBookAuditDTO.setSystemTotalCollectionAmount("0");
		totalMaazounBookAuditDTO.setSystemTotalSettlementAmount("0");
		totalMaazounBookAuditDTO.setSystemTotalAmountRefund("0");
		totalMaazounBookAuditDTO.setSystemTotalAmountCash("0");
		totalMaazounBookAuditDTO.setSystemTotalAmountVisa("0");
		totalMaazounBookAuditDTO.setTotalHoldTransaction("0");
		totalMaazounBookAuditDTO.setTotalFinalTransaction("0");
		totalMaazounBookAuditDTO.setTotalSynchedTransaction("0");
		totalMaazounBookAuditDTO.setTotalMatchedTransaction("0");

		for (Object[] q1 : list) {

			String totalTransactions = q1[0].toString();
			String totalRefundTransaction = q1[1].toString();
			String totalCancelledTransaction = q1[2].toString();

			String totalTransactionAmount = String.format("%.2f", q1[3]);
			String totalRefundAmount = String.format("%.2f", q1[4]);
			String totalCanceledAmount = String.format("%.2f", q1[5]);

			String totalTransactionsAmountCash = String.format("%.2f", q1[6]);
			String totalTransactionsAmountVisa = String.format("%.2f", q1[7]);

			String totalRefundAmountCash = String.format("%.2f", q1[8]);
			String totalRefundAmountVisa = String.format("%.2f", q1[9]);

			String totalHoldTransaction = q1[10].toString();
			String totalFinalTransaction = q1[11].toString();
			String totalSynchedTransaction = q1[12].toString();
			String totalMatchedTransaction = String
					.valueOf(Long.valueOf(totalFinalTransaction) + Long.valueOf(totalSynchedTransaction));

			String createDate = q1[13].toString();

			String locationId = q1[14].toString();
			String city = q1[15].toString();
			String location = q1[16].toString();
			String sectorName = q1[17].toString();
			String courtName = q1[18].toString();

			MaazounBookAuditDTO maazounBookAuditDTO = new MaazounBookAuditDTO();
			maazounBookAuditDTO.setTotalTransaction(totalTransactions);
			maazounBookAuditDTO.setTotalCollectionTransaction(
					String.valueOf(Long.valueOf(totalTransactions) - Long.valueOf(totalRefundTransaction)));
			maazounBookAuditDTO.setTotalSettlementTransaction(
					String.valueOf(Long.valueOf(totalTransactions) - Long.valueOf(totalCancelledTransaction)));
			maazounBookAuditDTO.setTotalRefundTransaction(totalRefundTransaction);
			maazounBookAuditDTO.setTotalCancelledTransaction(totalCancelledTransaction);

			maazounBookAuditDTO.setSystemTotalCollectionAmount(
					String.valueOf(Double.valueOf(totalTransactionAmount) - Double.valueOf(totalRefundAmount)));
			maazounBookAuditDTO.setSystemTotalSettlementAmount(
					String.valueOf(Double.valueOf(totalTransactionAmount) - Double.valueOf(totalCanceledAmount)));
			maazounBookAuditDTO.setSystemTotalAmountRefund(totalRefundAmount);

			maazounBookAuditDTO.setSystemTotalAmountCash(String
					.valueOf(Double.valueOf(totalTransactionsAmountCash) - Double.valueOf(totalRefundAmountCash)));
			maazounBookAuditDTO.setSystemTotalAmountVisa(String
					.valueOf(Double.valueOf(totalTransactionsAmountVisa) - Double.valueOf(totalRefundAmountVisa)));

			maazounBookAuditDTO.setTotalHoldTransaction(totalHoldTransaction);
			maazounBookAuditDTO.setTotalFinalTransaction(totalFinalTransaction);
			maazounBookAuditDTO.setTotalSynchedTransaction(totalSynchedTransaction);
			maazounBookAuditDTO.setTotalMatchedTransaction(totalMatchedTransaction);

			maazounBookAuditDTO.setCreateDate(createDate);
			maazounBookAuditDTO.setLocationName(city + " " + location + " " + sectorName);
			maazounBookAuditDTO.setSectorName(sectorName);
			maazounBookAuditDTO.setCourtName(courtName);

			result.add(maazounBookAuditDTO);

			accumalteObject(totalMaazounBookAuditDTO, maazounBookAuditDTO);

		}

		result.add(totalMaazounBookAuditDTO);

		return new PageImpl<MaazounBookAuditDTO>(result, pageable, total);
	}

	@Override
	public Page<MaazounBookAuditDTO> getAuditByCreatedDateAndAgentAndCourt(MaazounAuditRequest maazounAuditRequest,
			CustomUserDetails customUserDetails) {

		Set<Long> posIds = new HashSet<Long>();
		customUserDetails.getPosSet().stream().forEach(s -> {
			posIds.add(s.getId());
		});

		Pageable pageable = PageRequest.of(Integer.valueOf(maazounAuditRequest.getPageNo()),
				Integer.valueOf(maazounAuditRequest.getPageSize()));

		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();

		List<MaazounBookAuditDTO> result = new ArrayList<MaazounBookAuditDTO>();
		jpql.append("SELECT" + " SUM(IF (v.status_fk != 'Refund', 1, 0)) as totalTransactions,"
				+ " SUM(IF (v.status_fk = 'Refund', 1, 0)) as totalRefundTransaction,"
				+ " SUM(IF (v.status_fk = 'Canceled', 1, 0)) as totalCancelledTransaction,"

				+ " SUM(IF (v.status_fk != 'Refund', v.amount, 0)) as totalTransactionAmount,"
				+ " SUM(IF (v.status_fk = 'Refund', v.amount, 0)) as totalRefundAmount,"
				+ " SUM(IF (v.status_fk = 'Canceled', v.amount, 0)) as totalCanceledAmount,"

				+ " SUM(IF (v.status_fk != 'Refund' && v.payment_method = '1', v.amount, 0)) as totalTransactionsAmountCash,"
				+ " SUM(IF (v.status_fk != 'Refund' && v.payment_method = '2', v.amount, 0)) as totalTransactionsAmountVisa,"

				+ " SUM(IF (v.status_fk = 'Refund' && v.payment_method = '1', v.amount, 0)) as totalRefundAmountCash,"
				+ " SUM(IF (v.status_fk = 'Refund' && v.payment_method = '2', v.amount, 0)) as totalRefundAmountVisa,"

				+ " SUM(IF (v.status_fk = 'Hold', 1, 0)) as totalHoldTransaction,"
				+ " SUM(IF (v.status_fk = 'Final', 1, 0)) as totalFinalTransaction,"
				+ " SUM(IF (v.status_fk = 'Synched', 1, 0)) as totalSynchedTransaction,"

				+ " DATE_FORMAT(v.created_at, '%Y/%m/%d') as createDate," + " v.created_by as agentName,"
				+ " v.location_id as locationId," + " c.name_ar as cityName," + " l.name as locationName,"
				+ " s.name as sectorName, co.name as courtName" + " FROM maazoun_book_request_info v "
				+ " JOIN sector s ON s.id = v.sector_fk" 
				+ " JOIN courts co ON s.court_fk = co.id"
				+ " JOIN location l ON l.id = s.location_fk"
				+ " JOIN city c ON c.id = l.city_fk" + " WHERE v.created_at >= '"
				+ maazounAuditRequest.getDurationFrom() + " 00:00:00' " + " AND v.created_at <= '"
				+ maazounAuditRequest.getDurationTo() + " 23:59:59' " + " AND v.transaction_code is not null");

		jpqlCount.append("SELECT COUNT(*) " + " FROM maazoun_book_request_info v "
				+ " JOIN sector s ON s.id = v.sector_fk" 
				+ " JOIN courts co ON s.court_fk = co.id"
				+ " JOIN location l ON l.id = s.location_fk"
				+ " JOIN city c ON c.id = l.city_fk" + " WHERE v.created_at >= '"
				+ maazounAuditRequest.getDurationFrom() + " 00:00:00' " + " AND v.created_at <= '"
				+ maazounAuditRequest.getDurationTo() + " 23:59:59' " + " AND v.transaction_code is not null");

		if (customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT_SUPERVISOR)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AREA_MANAGER)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_SUPERVISOR)) {

			jpql.append(" AND v.pos_fk IN (" + posIds.toString().replace("]", "").replace("[", "") + ") ");
			jpqlCount.append(" AND v.pos_fk IN (" + posIds.toString().replace("]", "").replace("[", "") + ") ");
		}

		jpql.append(" GROUP BY date(v.created_at), v.created_by, co.id ORDER BY v.created_at desc");
		jpqlCount.append(" GROUP BY date(v.created_at), v.created_by, co.id");

		Query query = em.createNativeQuery(jpql.toString());
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Query queryCount = em.createNativeQuery(jpqlCount.toString());
		long total = queryCount.getResultList().size();

		List<Object[]> list = query.getResultList();
		
		MaazounBookAuditDTO totalMaazounBookAuditDTO = new MaazounBookAuditDTO();
		totalMaazounBookAuditDTO.setCourtName("الإجمالى");
		totalMaazounBookAuditDTO.setTotalTransaction("0");
		totalMaazounBookAuditDTO.setTotalCollectionTransaction("0");
		totalMaazounBookAuditDTO.setTotalSettlementTransaction("0");
		totalMaazounBookAuditDTO.setTotalRefundTransaction("0");
		totalMaazounBookAuditDTO.setTotalCancelledTransaction("0");
		totalMaazounBookAuditDTO.setSystemTotalCollectionAmount("0");
		totalMaazounBookAuditDTO.setSystemTotalSettlementAmount("0");
		totalMaazounBookAuditDTO.setSystemTotalAmountRefund("0");
		totalMaazounBookAuditDTO.setSystemTotalAmountCash("0");
		totalMaazounBookAuditDTO.setSystemTotalAmountVisa("0");
		totalMaazounBookAuditDTO.setTotalHoldTransaction("0");
		totalMaazounBookAuditDTO.setTotalFinalTransaction("0");
		totalMaazounBookAuditDTO.setTotalSynchedTransaction("0");
		totalMaazounBookAuditDTO.setTotalMatchedTransaction("0");

		for (Object[] q1 : list) {

			String totalTransactions = q1[0].toString();
			String totalRefundTransaction = q1[1].toString();
			String totalCancelledTransaction = q1[2].toString();

			String totalTransactionAmount = String.format("%.2f", q1[3]);
			String totalRefundAmount = String.format("%.2f", q1[4]);
			String totalCanceledAmount = String.format("%.2f", q1[5]);

			String totalTransactionsAmountCash = String.format("%.2f", q1[6]);
			String totalTransactionsAmountVisa = String.format("%.2f", q1[7]);

			String totalRefundAmountCash = String.format("%.2f", q1[8]);
			String totalRefundAmountVisa = String.format("%.2f", q1[9]);

			String totalHoldTransaction = q1[10].toString();
			String totalFinalTransaction = q1[11].toString();
			String totalSynchedTransaction = q1[12].toString();
			String totalMatchedTransaction = String
					.valueOf(Long.valueOf(totalFinalTransaction) + Long.valueOf(totalSynchedTransaction));

			String createDate = q1[13].toString();

			String agentName = q1[14].toString();
			String locationId = q1[15].toString();
			String city = q1[16].toString();
			String location = q1[17].toString();
			String sectorName = q1[18].toString();
			String courtName = q1[19].toString();

			MaazounBookAuditDTO maazounBookAuditDTO = new MaazounBookAuditDTO();
			maazounBookAuditDTO.setTotalTransaction(totalTransactions);
			maazounBookAuditDTO.setTotalCollectionTransaction(
					String.valueOf(Long.valueOf(totalTransactions) - Long.valueOf(totalRefundTransaction)));
			maazounBookAuditDTO.setTotalSettlementTransaction(
					String.valueOf(Long.valueOf(totalTransactions) - Long.valueOf(totalCancelledTransaction)));
			maazounBookAuditDTO.setTotalRefundTransaction(totalRefundTransaction);
			maazounBookAuditDTO.setTotalCancelledTransaction(totalCancelledTransaction);

			maazounBookAuditDTO.setSystemTotalCollectionAmount(
					String.valueOf(Double.valueOf(totalTransactionAmount) - Double.valueOf(totalRefundAmount)));
			maazounBookAuditDTO.setSystemTotalSettlementAmount(
					String.valueOf(Double.valueOf(totalTransactionAmount) - Double.valueOf(totalCanceledAmount)));
			maazounBookAuditDTO.setSystemTotalAmountRefund(totalRefundAmount);

			maazounBookAuditDTO.setSystemTotalAmountCash(String
					.valueOf(Double.valueOf(totalTransactionsAmountCash) - Double.valueOf(totalRefundAmountCash)));
			maazounBookAuditDTO.setSystemTotalAmountVisa(String
					.valueOf(Double.valueOf(totalTransactionsAmountVisa) - Double.valueOf(totalRefundAmountVisa)));

			maazounBookAuditDTO.setTotalHoldTransaction(totalHoldTransaction);
			maazounBookAuditDTO.setTotalFinalTransaction(totalFinalTransaction);
			maazounBookAuditDTO.setTotalSynchedTransaction(totalSynchedTransaction);
			maazounBookAuditDTO.setTotalMatchedTransaction(totalMatchedTransaction);

			maazounBookAuditDTO.setCreateDate(createDate);
			maazounBookAuditDTO.setAgentName(agentName);
			maazounBookAuditDTO.setLocationName(city + " " + location + " " + sectorName);
			maazounBookAuditDTO.setSectorName(sectorName);
			maazounBookAuditDTO.setCourtName(courtName);

			result.add(maazounBookAuditDTO);
			
			accumalteObject(totalMaazounBookAuditDTO, maazounBookAuditDTO);

		}
		
		result.add(totalMaazounBookAuditDTO);

		return new PageImpl<MaazounBookAuditDTO>(result, pageable, total);

	}

	@Override
	public Page<MaazounBookAuditSectorDTO> getBookCustomAuditBySectors(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails) {

		Set<Long> posIds = new HashSet<Long>();
		customUserDetails.getPosSet().stream().forEach(s -> {
			posIds.add(s.getId());
		});
		
		Pageable pageable = PageRequest.of(Integer.valueOf(maazounAuditRequest.getPageNo()),
				Integer.valueOf(maazounAuditRequest.getPageSize()));

		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();
		
		List<MaazounBookAuditSectorDTO> result = new ArrayList<MaazounBookAuditSectorDTO>();
		jpql.append("SELECT" + " COUNT(i.book_serial_number) as totalTransaction," + " SUM(i.amount) as totalAmount,"
				+ " SUM(IF (i.book_type_id = 9, 1, 0)) AS totalTransactionsZawagMuslim8,"
				+ " SUM(IF (i.book_type_id = 9, i.amount, 0)) AS totalAmountZawagMuslim8,"
				+ " SUM(IF (i.book_type_id = 19, 1, 0)) AS totalTransactionsZawagMuslim15,"
				+ " SUM(IF (i.book_type_id = 19, i.amount, 0)) AS totalAmountZawagMuslim15,"
				+ " SUM(IF (i.book_type_id = 10, 1, 0)) AS totalTransactionsTalak8,"
				+ " SUM(IF (i.book_type_id = 10, i.amount, 0)) AS totalAmountTalak8,"
				+ " SUM(IF (i.book_type_id = 20, 1, 0)) AS totalTransactionsTalak15,"
				+ " SUM(IF (i.book_type_id = 20, i.amount, 0)) AS totalAmountTalak15,"
				+ " SUM(IF (i.book_type_id = 11, 1, 0)) AS totalTransactionsTasadok8,"
				+ " SUM(IF (i.book_type_id = 11, i.amount, 0)) AS totalAmountTasadok8,"
				+ " SUM(IF (i.book_type_id = 21, 1, 0)) AS totalTransactionsTasadok15,"
				+ " SUM(IF (i.book_type_id = 21, i.amount, 0)) AS totalAmountTasadok15,"
				+ " SUM(IF (i.book_type_id = 12, 1, 0)) AS totalTransactionsMoragaa8,"
				+ " SUM(IF (i.book_type_id = 12, i.amount, 0)) AS totalAmountMoragaa8,"
				+ " SUM(IF (i.book_type_id = 22, 1, 0)) AS totalTransactionsMoragaa15,"
				+ " SUM(IF (i.book_type_id = 22, i.amount, 0)) AS totalAmountMoragaa15,"
				+ " SUM(IF (i.book_type_id = 13, 1, 0)) AS totalTransactionsZawagMelly8,"
				+ " SUM(IF (i.book_type_id = 13, i.amount, 0)) AS totalAmountZawagMelly8,"
				+ " SUM(IF (i.book_type_id = 23, 1, 0)) AS totalTransactionsZawagMelly15,"
				+ " SUM(IF (i.book_type_id = 23, i.amount, 0)) AS totalAmountZawagMelly15,"
				+ " c.name_ar as cityName," + " l.name as locationName," + " s.name as sectorName,"
				//+ " i.book_type_id as bookTypeId," + " u.name as bookTypeName," 
				+ " i.sector_fk as sectorId,"
				+ " l.id as locationId," + " c.id as cityId, co.name as courtName"

				+ " FROM maazoun_book_request_info i "

				+ " JOIN sector s ON s.id = i.sector_fk" 
				+ " JOIN courts co ON s.court_fk = co.id"
				+ " JOIN sub_service u ON u.id = i.book_type_id"
				+ " JOIN location l ON l.id = s.location_fk" + " JOIN city c ON c.id = l.city_fk"

				+ " WHERE i.created_at >= '" + maazounAuditRequest.getDurationFrom() + " 00:00:00' "
				+ " AND i.created_at <= '" + maazounAuditRequest.getDurationTo() + " 23:59:59' "
				+ " AND i.transaction_code is not null AND i.status_fk = 'New'");

		jpqlCount.append("SELECT COUNT(*) " + " FROM maazoun_book_request_info i "
				+ " JOIN sector s ON s.id = i.sector_fk" 
				+ " JOIN courts co ON s.court_fk = co.id"
				+ " JOIN sub_service u ON u.id = i.book_type_id"
				+ " JOIN location l ON l.id = s.location_fk" + " JOIN city c ON c.id = l.city_fk"
				+ " WHERE i.created_at >= '" + maazounAuditRequest.getDurationFrom() + " 00:00:00' "
				+ " AND i.created_at <= '" + maazounAuditRequest.getDurationTo() + " 23:59:59' "
				+ " AND i.transaction_code is not null AND i.status_fk = 'New' ");

		if (customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT_SUPERVISOR)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AREA_MANAGER)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_SUPERVISOR)) {

			jpql.append(" AND i.pos_fk IN (" + posIds.toString().replace("]", "").replace("[", "") + ") ");
			jpqlCount.append(" AND i.pos_fk IN (" + posIds.toString().replace("]", "").replace("[", "") + ") ");
		}
		
		jpql.append(" GROUP BY i.sector_fk ORDER BY s.name");
		jpqlCount.append(" GROUP BY i.sector_fk");
		
		Query query = em.createNativeQuery(jpql.toString());
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Query queryCount = em.createNativeQuery(jpqlCount.toString());
		long total = queryCount.getResultList().size();

		List<Object[]> list = query.getResultList();
		MaazounBookAuditSectorDTO totalMaazounBookAuditSectorDTO = new MaazounBookAuditSectorDTO();
		totalMaazounBookAuditSectorDTO.setSectorName("الإجمالى");

		for (Object[] q1 : list) {

			String totalTransactions = q1[0].toString();
			String totalAmount = String.format("%.2f", q1[1]);
			
			String totalTransactionsZawagMuslim8 = q1[2].toString();
			String totalAmountZawagMuslim8 = String.format("%.2f", q1[3]);
			String totalTransactionsZawagMuslim15 = q1[4].toString();
			String totalAmountZawagMuslim15 = String.format("%.2f", q1[5]);
			
			String totalTransactionsTalak8 = q1[6].toString();
			String totalAmountTalak8 = String.format("%.2f", q1[7]);
			String totalTransactionsTalak15 = q1[8].toString();
			String totalAmountTalak15 = String.format("%.2f", q1[9]);
			
			String totalTransactionsTasadok8 = q1[10].toString();
			String totalAmountTasadok8 = String.format("%.2f", q1[11]);
			String totalTransactionsTasadok15 = q1[12].toString();
			String totalAmountTasadok15 = String.format("%.2f", q1[13]);
			
			String totalTransactionsMoragaa8 = q1[14].toString();
			String totalAmountMoragaa8 = String.format("%.2f", q1[15]);
			String totalTransactionsMoragaa15 = q1[16].toString();
			String totalAmountMoragaa15 = String.format("%.2f", q1[17]);
			
			String totalTransactionsZawagMelly8 = q1[18].toString();
			String totalAmountZawagMelly8 = String.format("%.2f", q1[19]);
			String totalTransactionsZawagMelly15 = q1[20].toString();
			String totalAmountZawagMelly15 = String.format("%.2f", q1[21]);
			
			
			String cityName = q1[22].toString();

			String locationName = q1[23].toString();
			String sectorName = q1[24].toString();
//			String bookTypeId = q1[5].toString();

//			String bookTypeName = q1[6].toString();
			String sectorId = q1[25].toString();
			String locationId = q1[26].toString();
			String cityId = q1[27].toString();
			String courtName = q1[28].toString();
			
			MaazounBookAuditSectorDTO eMaazounBookAuditSectorDTO = new MaazounBookAuditSectorDTO();

			eMaazounBookAuditSectorDTO.setCityId(cityId);
			eMaazounBookAuditSectorDTO.setCityName(cityName);
			eMaazounBookAuditSectorDTO.setLocationId(locationId);
			eMaazounBookAuditSectorDTO.setLocationName(locationName);
			eMaazounBookAuditSectorDTO.setSectorId(sectorId);
			eMaazounBookAuditSectorDTO.setSectorName(sectorName);
			eMaazounBookAuditSectorDTO.setCourtName(courtName);
			
			eMaazounBookAuditSectorDTO.setBookMoragaaName(StatusConstant.BOOK_MORAGAA_NAME);
			eMaazounBookAuditSectorDTO.setBookMoragaaAmount8(Double.valueOf(totalAmountMoragaa8));
			eMaazounBookAuditSectorDTO.setBookMoragaaTransactions8(Long.valueOf(totalTransactionsMoragaa8));
			eMaazounBookAuditSectorDTO.setBookMoragaaAmount15(Double.valueOf(totalAmountMoragaa15));
			eMaazounBookAuditSectorDTO.setBookMoragaaTransactions15(Long.valueOf(totalTransactionsMoragaa15));
			
			eMaazounBookAuditSectorDTO.setBookTalakName(StatusConstant.BOOK_TALAK_NAME);
			eMaazounBookAuditSectorDTO.setBookTalakAmount8(Double.valueOf(totalAmountTalak8));
			eMaazounBookAuditSectorDTO.setBookTalakTransactions8(Long.valueOf(totalTransactionsTalak8));
			eMaazounBookAuditSectorDTO.setBookTalakAmount15(Double.valueOf(totalAmountTalak15));
			eMaazounBookAuditSectorDTO.setBookTalakTransactions15(Long.valueOf(totalTransactionsTalak15));
			
			eMaazounBookAuditSectorDTO.setBookTasadokName(StatusConstant.BOOK_TASADOK_NAME);
			eMaazounBookAuditSectorDTO.setBookTasadokAmount8(Double.valueOf(totalAmountTasadok8));
			eMaazounBookAuditSectorDTO.setBookTasadokTransactions8(Long.valueOf(totalTransactionsTasadok8));
			eMaazounBookAuditSectorDTO.setBookTasadokAmount15(Double.valueOf(totalAmountTasadok15));
			eMaazounBookAuditSectorDTO.setBookTasadokTransactions15(Long.valueOf(totalTransactionsTasadok15));
			
			eMaazounBookAuditSectorDTO.setBookZawagMelalyName(StatusConstant.BOOK_ZAWAG_MELALY_NAME);
			eMaazounBookAuditSectorDTO.setBookZawagMelalyAmount8(Double.valueOf(totalAmountZawagMelly8));
			eMaazounBookAuditSectorDTO.setBookZawagMelalyTransactions8(Long.valueOf(totalTransactionsZawagMelly8));
			eMaazounBookAuditSectorDTO.setBookZawagMelalyAmount15(Double.valueOf(totalAmountZawagMelly15));
			eMaazounBookAuditSectorDTO.setBookZawagMelalyTransactions15(Long.valueOf(totalTransactionsZawagMelly15));

			eMaazounBookAuditSectorDTO.setBookZawagMuslimName(StatusConstant.BOOK_ZAWAG_MUSLIM_NAME);
			eMaazounBookAuditSectorDTO.setBookZawagMuslimAmount8(Double.valueOf(totalAmountZawagMuslim8));
			eMaazounBookAuditSectorDTO.setBookZawagMuslimTransactions8(Long.valueOf(totalTransactionsZawagMuslim8));
			eMaazounBookAuditSectorDTO.setBookZawagMuslimAmount15(Double.valueOf(totalAmountZawagMuslim15));
			eMaazounBookAuditSectorDTO.setBookZawagMuslimTransactions15(Long.valueOf(totalTransactionsZawagMuslim15));


			totalMaazounBookAuditSectorDTO.setBookMoragaaAmount8(Double.valueOf(
					String.format("%.2f",
					(totalMaazounBookAuditSectorDTO.getBookMoragaaAmount8() + Double.valueOf(totalAmountMoragaa8))
					)));
			totalMaazounBookAuditSectorDTO.setBookMoragaaTransactions8(
					totalMaazounBookAuditSectorDTO.getBookMoragaaTransactions8() + Long.valueOf(totalTransactionsMoragaa8));
			totalMaazounBookAuditSectorDTO.setBookMoragaaAmount15(Double.valueOf(
					String.format("%.2f",
					(totalMaazounBookAuditSectorDTO.getBookMoragaaAmount15() + Double.valueOf(totalAmountMoragaa15))
					)));
			totalMaazounBookAuditSectorDTO.setBookMoragaaTransactions15(
					totalMaazounBookAuditSectorDTO.getBookMoragaaTransactions15() + Long.valueOf(totalTransactionsMoragaa15));
			
			totalMaazounBookAuditSectorDTO.setBookTalakAmount8(Double.valueOf(
					String.format("%.2f",
					(totalMaazounBookAuditSectorDTO.getBookTalakAmount8() + Double.valueOf(totalAmountTalak8))
					)));
			totalMaazounBookAuditSectorDTO.setBookTalakTransactions8(
					totalMaazounBookAuditSectorDTO.getBookTalakTransactions8() + Long.valueOf(totalTransactionsTalak8));
			totalMaazounBookAuditSectorDTO.setBookTalakAmount15(Double.valueOf(
					String.format("%.2f",
					(totalMaazounBookAuditSectorDTO.getBookTalakAmount15() + Double.valueOf(totalAmountTalak15))
					)));
			totalMaazounBookAuditSectorDTO.setBookTalakTransactions15(
					totalMaazounBookAuditSectorDTO.getBookTalakTransactions15() + Long.valueOf(totalTransactionsTalak15));
			
			totalMaazounBookAuditSectorDTO.setBookTasadokAmount8(Double.valueOf(
					String.format("%.2f",
					(totalMaazounBookAuditSectorDTO.getBookTasadokAmount8() + Double.valueOf(totalAmountTasadok8))
					)));
			totalMaazounBookAuditSectorDTO.setBookTasadokTransactions8(
					totalMaazounBookAuditSectorDTO.getBookTasadokTransactions8() + Long.valueOf(totalTransactionsTasadok8));
			totalMaazounBookAuditSectorDTO.setBookTasadokAmount15(Double.valueOf(
					String.format("%.2f",
					(totalMaazounBookAuditSectorDTO.getBookTasadokAmount15() + Double.valueOf(totalAmountTasadok15))
					)));
			totalMaazounBookAuditSectorDTO.setBookTasadokTransactions15(
					totalMaazounBookAuditSectorDTO.getBookTasadokTransactions15() + Long.valueOf(totalTransactionsTasadok15));

			totalMaazounBookAuditSectorDTO.setBookZawagMelalyAmount8(Double.valueOf(
					String.format("%.2f", 
							(totalMaazounBookAuditSectorDTO.getBookZawagMelalyAmount8() + Double.valueOf(totalAmountZawagMelly8))
							)));
			totalMaazounBookAuditSectorDTO.setBookZawagMelalyTransactions8(
					totalMaazounBookAuditSectorDTO.getBookZawagMelalyTransactions8() + Long.valueOf(totalTransactionsZawagMelly8));
			totalMaazounBookAuditSectorDTO.setBookZawagMelalyAmount15(Double.valueOf(
					String.format("%.2f", 
							(totalMaazounBookAuditSectorDTO.getBookZawagMelalyAmount15() + Double.valueOf(totalAmountZawagMelly15))
							)));
			totalMaazounBookAuditSectorDTO.setBookZawagMelalyTransactions15(
					totalMaazounBookAuditSectorDTO.getBookZawagMelalyTransactions15() + Long.valueOf(totalTransactionsZawagMelly15));

			
			totalMaazounBookAuditSectorDTO.setBookZawagMuslimAmount8(Double.valueOf(
					String.format("%.2f", 
							(totalMaazounBookAuditSectorDTO.getBookZawagMuslimAmount8() + Double.valueOf(totalAmountZawagMuslim8))
							)));
			totalMaazounBookAuditSectorDTO.setBookZawagMuslimTransactions8(
					totalMaazounBookAuditSectorDTO.getBookZawagMuslimTransactions8() + Long.valueOf(totalTransactionsZawagMuslim8));
			totalMaazounBookAuditSectorDTO.setBookZawagMuslimAmount15(Double.valueOf(
					String.format("%.2f", 
							(totalMaazounBookAuditSectorDTO.getBookZawagMuslimAmount15() + Double.valueOf(totalAmountZawagMuslim15))
							)));
			totalMaazounBookAuditSectorDTO.setBookZawagMuslimTransactions15(
					totalMaazounBookAuditSectorDTO.getBookZawagMuslimTransactions15() + Long.valueOf(totalTransactionsZawagMuslim15));
			
			result.add(eMaazounBookAuditSectorDTO);
			
//			MaazounBookAuditSectorDTO eMaazounBookAuditSectorDTO = result.stream().filter(x ->
////							x.getCityId().equals(cityId) &&
////							x.getLocationId().equals(locationId) &&
//			x.getSectorId().equals(sectorId)).findAny().orElse(null);
//			boolean checkObjExist = true;
//			if (eMaazounBookAuditSectorDTO == null) {
//				checkObjExist = false;
//				eMaazounBookAuditSectorDTO = new MaazounBookAuditSectorDTO();
//
//				eMaazounBookAuditSectorDTO.setCityId(cityId);
//				eMaazounBookAuditSectorDTO.setCityName(cityName);
//				eMaazounBookAuditSectorDTO.setLocationId(locationId);
//				eMaazounBookAuditSectorDTO.setLocationName(locationName);
//				eMaazounBookAuditSectorDTO.setSectorId(sectorId);
//				eMaazounBookAuditSectorDTO.setSectorName(sectorName);
//				
//				eMaazounBookAuditSectorDTO.setBookMoragaaAmount((double) 0);
//				eMaazounBookAuditSectorDTO.setBookMoragaaTransactions((long) 0);
//
//				eMaazounBookAuditSectorDTO.setBookTalakAmount((double) 0);
//				eMaazounBookAuditSectorDTO.setBookTalakTransactions((long) 0);
//
//				eMaazounBookAuditSectorDTO.setBookTasadokAmount((double) 0);
//				eMaazounBookAuditSectorDTO.setBookTasadokTransactions((long) 0);
//
//				eMaazounBookAuditSectorDTO.setBookZawagMelalyAmount((double) 0);
//				eMaazounBookAuditSectorDTO.setBookZawagMelalyTransactions((long) 0);
//
//				eMaazounBookAuditSectorDTO.setBookZawagMuslimAmount((double) 0);
//				eMaazounBookAuditSectorDTO.setBookZawagMuslimTransactions((long) 0);
//			}
//
//			if (bookTypeId.equals(StatusConstant.BOOK_MORAGAA_ID_8)
//					|| bookTypeId.equals(StatusConstant.BOOK_MORAGAA_ID_15)) {
//				eMaazounBookAuditSectorDTO.setBookMoragaaName(bookTypeName);
//				eMaazounBookAuditSectorDTO.setBookMoragaaAmount(Double.valueOf(totalAmount));
//				eMaazounBookAuditSectorDTO.setBookMoragaaTransactions(Long.valueOf(totalTransactions));
//
//				totalMaazounBookAuditSectorDTO.setBookMoragaaAmount(
//						totalMaazounBookAuditSectorDTO.getBookMoragaaAmount() + Double.valueOf(totalAmount));
//				totalMaazounBookAuditSectorDTO.setBookMoragaaTransactions(
//						totalMaazounBookAuditSectorDTO.getBookMoragaaTransactions() + Long.valueOf(totalTransactions));
//			} else if (bookTypeId.equals(StatusConstant.BOOK_TALAK_ID_8)
//					|| bookTypeId.equals(StatusConstant.BOOK_TALAK_ID_15)) {
//				eMaazounBookAuditSectorDTO.setBookTalakName(bookTypeName);
//				eMaazounBookAuditSectorDTO.setBookTalakAmount(Double.valueOf(totalAmount));
//				eMaazounBookAuditSectorDTO.setBookTalakTransactions(Long.valueOf(totalTransactions));
//
//				totalMaazounBookAuditSectorDTO.setBookTalakAmount(
//						totalMaazounBookAuditSectorDTO.getBookTalakAmount() + Double.valueOf(totalAmount));
//				totalMaazounBookAuditSectorDTO.setBookTalakTransactions(
//						totalMaazounBookAuditSectorDTO.getBookTalakTransactions() + Long.valueOf(totalTransactions));
//			} else if (bookTypeId.equals(StatusConstant.BOOK_TASADOK_ID_8)
//					|| bookTypeId.equals(StatusConstant.BOOK_TASADOK_ID_15)) {
//				eMaazounBookAuditSectorDTO.setBookTasadokName(bookTypeName);
//				eMaazounBookAuditSectorDTO.setBookTasadokAmount(Double.valueOf(totalAmount));
//				eMaazounBookAuditSectorDTO.setBookTasadokTransactions(Long.valueOf(totalTransactions));
//
//				totalMaazounBookAuditSectorDTO.setBookTasadokAmount(
//						totalMaazounBookAuditSectorDTO.getBookTasadokAmount() + Double.valueOf(totalAmount));
//				totalMaazounBookAuditSectorDTO.setBookTasadokTransactions(
//						totalMaazounBookAuditSectorDTO.getBookTasadokTransactions() + Long.valueOf(totalTransactions));
//			} else if (bookTypeId.equals(StatusConstant.BOOK_ZAWAG_MELALY_ID_8)
//					|| bookTypeId.equals(StatusConstant.BOOK_ZAWAG_MELALY_ID_15)) {
//				eMaazounBookAuditSectorDTO.setBookZawagMelalyName(bookTypeName);
//				eMaazounBookAuditSectorDTO.setBookZawagMelalyAmount(Double.valueOf(totalAmount));
//				eMaazounBookAuditSectorDTO.setBookZawagMelalyTransactions(Long.valueOf(totalTransactions));
//
//				totalMaazounBookAuditSectorDTO.setBookZawagMelalyAmount(
//						totalMaazounBookAuditSectorDTO.getBookZawagMelalyAmount() + Double.valueOf(totalAmount));
//				totalMaazounBookAuditSectorDTO
//						.setBookZawagMelalyTransactions(totalMaazounBookAuditSectorDTO.getBookZawagMelalyTransactions()
//								+ Long.valueOf(totalTransactions));
//			} else if (bookTypeId.equals(StatusConstant.BOOK_ZAWAG_MUSLIM_ID_8)
//					|| bookTypeId.equals(StatusConstant.BOOK_ZAWAG_MUSLIM_ID_15)) {
//				eMaazounBookAuditSectorDTO.setBookZawagMuslimName(bookTypeName);
//				eMaazounBookAuditSectorDTO.setBookZawagMuslimAmount(Double.valueOf(totalAmount));
//				eMaazounBookAuditSectorDTO.setBookZawagMuslimTransactions(Long.valueOf(totalTransactions));
//
//				totalMaazounBookAuditSectorDTO.setBookZawagMuslimAmount(
//						totalMaazounBookAuditSectorDTO.getBookZawagMuslimAmount() + Double.valueOf(totalAmount));
//				totalMaazounBookAuditSectorDTO
//						.setBookZawagMuslimTransactions(totalMaazounBookAuditSectorDTO.getBookZawagMuslimTransactions()
//								+ Long.valueOf(totalTransactions));
//			}
//
//			if (!checkObjExist)
//				result.add(eMaazounBookAuditSectorDTO);
		}

		result.add(totalMaazounBookAuditSectorDTO);

		return new PageImpl<MaazounBookAuditSectorDTO>(result, pageable, total);
	}
	
	@Override
	public Page<MaazounBookAuditSectorDTO> getBookCustomAuditByCourts(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails) {

		Set<Long> posIds = new HashSet<Long>();
		customUserDetails.getPosSet().stream().forEach(s -> {
			posIds.add(s.getId());
		});
		
		Pageable pageable = PageRequest.of(Integer.valueOf(maazounAuditRequest.getPageNo()),
				Integer.valueOf(maazounAuditRequest.getPageSize()));

		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();
		
		List<MaazounBookAuditSectorDTO> result = new ArrayList<MaazounBookAuditSectorDTO>();
		jpql.append("SELECT" + " COUNT(i.book_serial_number) as totalTransaction," + " SUM(i.amount) as totalAmount,"
				+ " SUM(IF (i.book_type_id = 9, 1, 0)) AS totalTransactionsZawagMuslim8,"
				+ " SUM(IF (i.book_type_id = 9, i.amount, 0)) AS totalAmountZawagMuslim8,"
				+ " SUM(IF (i.book_type_id = 19, 1, 0)) AS totalTransactionsZawagMuslim15,"
				+ " SUM(IF (i.book_type_id = 19, i.amount, 0)) AS totalAmountZawagMuslim15,"
				+ " SUM(IF (i.book_type_id = 10, 1, 0)) AS totalTransactionsTalak8,"
				+ " SUM(IF (i.book_type_id = 10, i.amount, 0)) AS totalAmountTalak8,"
				+ " SUM(IF (i.book_type_id = 20, 1, 0)) AS totalTransactionsTalak15,"
				+ " SUM(IF (i.book_type_id = 20, i.amount, 0)) AS totalAmountTalak15,"
				+ " SUM(IF (i.book_type_id = 11, 1, 0)) AS totalTransactionsTasadok8,"
				+ " SUM(IF (i.book_type_id = 11, i.amount, 0)) AS totalAmountTasadok8,"
				+ " SUM(IF (i.book_type_id = 21, 1, 0)) AS totalTransactionsTasadok15,"
				+ " SUM(IF (i.book_type_id = 21, i.amount, 0)) AS totalAmountTasadok15,"
				+ " SUM(IF (i.book_type_id = 12, 1, 0)) AS totalTransactionsMoragaa8,"
				+ " SUM(IF (i.book_type_id = 12, i.amount, 0)) AS totalAmountMoragaa8,"
				+ " SUM(IF (i.book_type_id = 22, 1, 0)) AS totalTransactionsMoragaa15,"
				+ " SUM(IF (i.book_type_id = 22, i.amount, 0)) AS totalAmountMoragaa15,"
				+ " SUM(IF (i.book_type_id = 13, 1, 0)) AS totalTransactionsZawagMelly8,"
				+ " SUM(IF (i.book_type_id = 13, i.amount, 0)) AS totalAmountZawagMelly8,"
				+ " SUM(IF (i.book_type_id = 23, 1, 0)) AS totalTransactionsZawagMelly15,"
				+ " SUM(IF (i.book_type_id = 23, i.amount, 0)) AS totalAmountZawagMelly15,"
				+ " c.name_ar as cityName," + " l.name as locationName," + " s.name as sectorName,"
				//+ " i.book_type_id as bookTypeId," + " u.name as bookTypeName," 
				+ " i.sector_fk as sectorId,"
				+ " l.id as locationId," + " c.id as cityId, co.name as courtName"

				+ " FROM maazoun_book_request_info i "

				+ " JOIN sector s ON s.id = i.sector_fk" 
				+ " JOIN courts co ON s.court_fk = co.id"
				+ " JOIN sub_service u ON u.id = i.book_type_id"
				+ " JOIN location l ON l.id = s.location_fk" + " JOIN city c ON c.id = l.city_fk"

				+ " WHERE i.created_at >= '" + maazounAuditRequest.getDurationFrom() + " 00:00:00' "
				+ " AND i.created_at <= '" + maazounAuditRequest.getDurationTo() + " 23:59:59' "
				+ " AND i.transaction_code is not null AND i.status_fk = 'New'");

		jpqlCount.append("SELECT COUNT(*) " + " FROM maazoun_book_request_info i "
				+ " JOIN sector s ON s.id = i.sector_fk" 
				+ " JOIN courts co ON s.court_fk = co.id"
				+ " JOIN sub_service u ON u.id = i.book_type_id"
				+ " JOIN location l ON l.id = s.location_fk" + " JOIN city c ON c.id = l.city_fk"
				+ " WHERE i.created_at >= '" + maazounAuditRequest.getDurationFrom() + " 00:00:00' "
				+ " AND i.created_at <= '" + maazounAuditRequest.getDurationTo() + " 23:59:59' "
				+ " AND i.transaction_code is not null AND i.status_fk = 'New' ");

		if (customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT_SUPERVISOR)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AREA_MANAGER)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_SUPERVISOR)) {

			jpql.append(" AND i.pos_fk IN (" + posIds.toString().replace("]", "").replace("[", "") + ") ");
			jpqlCount.append(" AND i.pos_fk IN (" + posIds.toString().replace("]", "").replace("[", "") + ") ");
		}
		
		jpql.append(" GROUP BY co.id ORDER BY s.name");
		jpqlCount.append(" GROUP BY co.id");
		
		Query query = em.createNativeQuery(jpql.toString());
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Query queryCount = em.createNativeQuery(jpqlCount.toString());
		long total = queryCount.getResultList().size();

		List<Object[]> list = query.getResultList();
		MaazounBookAuditSectorDTO totalMaazounBookAuditSectorDTO = new MaazounBookAuditSectorDTO();
		totalMaazounBookAuditSectorDTO.setSectorName("الإجمالى");

		for (Object[] q1 : list) {

			String totalTransactions = q1[0].toString();
			String totalAmount = String.format("%.2f", q1[1]);
			
			String totalTransactionsZawagMuslim8 = q1[2].toString();
			String totalAmountZawagMuslim8 = String.format("%.2f", q1[3]);
			String totalTransactionsZawagMuslim15 = q1[4].toString();
			String totalAmountZawagMuslim15 = String.format("%.2f", q1[5]);
			
			String totalTransactionsTalak8 = q1[6].toString();
			String totalAmountTalak8 = String.format("%.2f", q1[7]);
			String totalTransactionsTalak15 = q1[8].toString();
			String totalAmountTalak15 = String.format("%.2f", q1[9]);
			
			String totalTransactionsTasadok8 = q1[10].toString();
			String totalAmountTasadok8 = String.format("%.2f", q1[11]);
			String totalTransactionsTasadok15 = q1[12].toString();
			String totalAmountTasadok15 = String.format("%.2f", q1[13]);
			
			String totalTransactionsMoragaa8 = q1[14].toString();
			String totalAmountMoragaa8 = String.format("%.2f", q1[15]);
			String totalTransactionsMoragaa15 = q1[16].toString();
			String totalAmountMoragaa15 = String.format("%.2f", q1[17]);
			
			String totalTransactionsZawagMelly8 = q1[18].toString();
			String totalAmountZawagMelly8 = String.format("%.2f", q1[19]);
			String totalTransactionsZawagMelly15 = q1[20].toString();
			String totalAmountZawagMelly15 = String.format("%.2f", q1[21]);
			
			
			String cityName = q1[22].toString();

			String locationName = q1[23].toString();
			String sectorName = q1[24].toString();
//			String bookTypeId = q1[5].toString();

//			String bookTypeName = q1[6].toString();
			String sectorId = q1[25].toString();
			String locationId = q1[26].toString();
			String cityId = q1[27].toString();
			String courtName = q1[28].toString();
			
			MaazounBookAuditSectorDTO eMaazounBookAuditSectorDTO = new MaazounBookAuditSectorDTO();

			eMaazounBookAuditSectorDTO.setCityId(cityId);
			eMaazounBookAuditSectorDTO.setCityName(cityName);
			eMaazounBookAuditSectorDTO.setLocationId(locationId);
			eMaazounBookAuditSectorDTO.setLocationName(locationName);
			eMaazounBookAuditSectorDTO.setSectorId(sectorId);
			eMaazounBookAuditSectorDTO.setSectorName(sectorName);
			eMaazounBookAuditSectorDTO.setCourtName(courtName);
			
			eMaazounBookAuditSectorDTO.setBookMoragaaName(StatusConstant.BOOK_MORAGAA_NAME);
			eMaazounBookAuditSectorDTO.setBookMoragaaAmount8(Double.valueOf(totalAmountMoragaa8));
			eMaazounBookAuditSectorDTO.setBookMoragaaTransactions8(Long.valueOf(totalTransactionsMoragaa8));
			eMaazounBookAuditSectorDTO.setBookMoragaaAmount15(Double.valueOf(totalAmountMoragaa15));
			eMaazounBookAuditSectorDTO.setBookMoragaaTransactions15(Long.valueOf(totalTransactionsMoragaa15));
			
			eMaazounBookAuditSectorDTO.setBookTalakName(StatusConstant.BOOK_TALAK_NAME);
			eMaazounBookAuditSectorDTO.setBookTalakAmount8(Double.valueOf(totalAmountTalak8));
			eMaazounBookAuditSectorDTO.setBookTalakTransactions8(Long.valueOf(totalTransactionsTalak8));
			eMaazounBookAuditSectorDTO.setBookTalakAmount15(Double.valueOf(totalAmountTalak15));
			eMaazounBookAuditSectorDTO.setBookTalakTransactions15(Long.valueOf(totalTransactionsTalak15));
			
			eMaazounBookAuditSectorDTO.setBookTasadokName(StatusConstant.BOOK_TASADOK_NAME);
			eMaazounBookAuditSectorDTO.setBookTasadokAmount8(Double.valueOf(totalAmountTasadok8));
			eMaazounBookAuditSectorDTO.setBookTasadokTransactions8(Long.valueOf(totalTransactionsTasadok8));
			eMaazounBookAuditSectorDTO.setBookTasadokAmount15(Double.valueOf(totalAmountTasadok15));
			eMaazounBookAuditSectorDTO.setBookTasadokTransactions15(Long.valueOf(totalTransactionsTasadok15));
			
			eMaazounBookAuditSectorDTO.setBookZawagMelalyName(StatusConstant.BOOK_ZAWAG_MELALY_NAME);
			eMaazounBookAuditSectorDTO.setBookZawagMelalyAmount8(Double.valueOf(totalAmountZawagMelly8));
			eMaazounBookAuditSectorDTO.setBookZawagMelalyTransactions8(Long.valueOf(totalTransactionsZawagMelly8));
			eMaazounBookAuditSectorDTO.setBookZawagMelalyAmount15(Double.valueOf(totalAmountZawagMelly15));
			eMaazounBookAuditSectorDTO.setBookZawagMelalyTransactions15(Long.valueOf(totalTransactionsZawagMelly15));

			eMaazounBookAuditSectorDTO.setBookZawagMuslimName(StatusConstant.BOOK_ZAWAG_MUSLIM_NAME);
			eMaazounBookAuditSectorDTO.setBookZawagMuslimAmount8(Double.valueOf(totalAmountZawagMuslim8));
			eMaazounBookAuditSectorDTO.setBookZawagMuslimTransactions8(Long.valueOf(totalTransactionsZawagMuslim8));
			eMaazounBookAuditSectorDTO.setBookZawagMuslimAmount15(Double.valueOf(totalAmountZawagMuslim15));
			eMaazounBookAuditSectorDTO.setBookZawagMuslimTransactions15(Long.valueOf(totalTransactionsZawagMuslim15));


			totalMaazounBookAuditSectorDTO.setBookMoragaaAmount8(Double.valueOf(
					String.format("%.2f",
					(totalMaazounBookAuditSectorDTO.getBookMoragaaAmount8() + Double.valueOf(totalAmountMoragaa8))
					)));
			totalMaazounBookAuditSectorDTO.setBookMoragaaTransactions8(
					totalMaazounBookAuditSectorDTO.getBookMoragaaTransactions8() + Long.valueOf(totalTransactionsMoragaa8));
			totalMaazounBookAuditSectorDTO.setBookMoragaaAmount15(Double.valueOf(
					String.format("%.2f",
					(totalMaazounBookAuditSectorDTO.getBookMoragaaAmount15() + Double.valueOf(totalAmountMoragaa15))
					)));
			totalMaazounBookAuditSectorDTO.setBookMoragaaTransactions15(
					totalMaazounBookAuditSectorDTO.getBookMoragaaTransactions15() + Long.valueOf(totalTransactionsMoragaa15));
			
			totalMaazounBookAuditSectorDTO.setBookTalakAmount8(Double.valueOf(
					String.format("%.2f",
					(totalMaazounBookAuditSectorDTO.getBookTalakAmount8() + Double.valueOf(totalAmountTalak8))
					)));
			totalMaazounBookAuditSectorDTO.setBookTalakTransactions8(
					totalMaazounBookAuditSectorDTO.getBookTalakTransactions8() + Long.valueOf(totalTransactionsTalak8));
			totalMaazounBookAuditSectorDTO.setBookTalakAmount15(Double.valueOf(
					String.format("%.2f",
					(totalMaazounBookAuditSectorDTO.getBookTalakAmount15() + Double.valueOf(totalAmountTalak15))
					)));
			totalMaazounBookAuditSectorDTO.setBookTalakTransactions15(
					totalMaazounBookAuditSectorDTO.getBookTalakTransactions15() + Long.valueOf(totalTransactionsTalak15));
			
			totalMaazounBookAuditSectorDTO.setBookTasadokAmount8(Double.valueOf(
					String.format("%.2f",
					(totalMaazounBookAuditSectorDTO.getBookTasadokAmount8() + Double.valueOf(totalAmountTasadok8))
					)));
			totalMaazounBookAuditSectorDTO.setBookTasadokTransactions8(
					totalMaazounBookAuditSectorDTO.getBookTasadokTransactions8() + Long.valueOf(totalTransactionsTasadok8));
			totalMaazounBookAuditSectorDTO.setBookTasadokAmount15(Double.valueOf(
					String.format("%.2f",
					(totalMaazounBookAuditSectorDTO.getBookTasadokAmount15() + Double.valueOf(totalAmountTasadok15))
					)));
			totalMaazounBookAuditSectorDTO.setBookTasadokTransactions15(
					totalMaazounBookAuditSectorDTO.getBookTasadokTransactions15() + Long.valueOf(totalTransactionsTasadok15));

			totalMaazounBookAuditSectorDTO.setBookZawagMelalyAmount8(Double.valueOf(
					String.format("%.2f", 
							(totalMaazounBookAuditSectorDTO.getBookZawagMelalyAmount8() + Double.valueOf(totalAmountZawagMelly8))
							)));
			totalMaazounBookAuditSectorDTO.setBookZawagMelalyTransactions8(
					totalMaazounBookAuditSectorDTO.getBookZawagMelalyTransactions8() + Long.valueOf(totalTransactionsZawagMelly8));
			totalMaazounBookAuditSectorDTO.setBookZawagMelalyAmount15(Double.valueOf(
					String.format("%.2f", 
							(totalMaazounBookAuditSectorDTO.getBookZawagMelalyAmount15() + Double.valueOf(totalAmountZawagMelly15))
							)));
			totalMaazounBookAuditSectorDTO.setBookZawagMelalyTransactions15(
					totalMaazounBookAuditSectorDTO.getBookZawagMelalyTransactions15() + Long.valueOf(totalTransactionsZawagMelly15));

			
			totalMaazounBookAuditSectorDTO.setBookZawagMuslimAmount8(Double.valueOf(
					String.format("%.2f", 
							(totalMaazounBookAuditSectorDTO.getBookZawagMuslimAmount8() + Double.valueOf(totalAmountZawagMuslim8))
							)));
			totalMaazounBookAuditSectorDTO.setBookZawagMuslimTransactions8(
					totalMaazounBookAuditSectorDTO.getBookZawagMuslimTransactions8() + Long.valueOf(totalTransactionsZawagMuslim8));
			totalMaazounBookAuditSectorDTO.setBookZawagMuslimAmount15(Double.valueOf(
					String.format("%.2f", 
							(totalMaazounBookAuditSectorDTO.getBookZawagMuslimAmount15() + Double.valueOf(totalAmountZawagMuslim15))
							)));
			totalMaazounBookAuditSectorDTO.setBookZawagMuslimTransactions15(
					totalMaazounBookAuditSectorDTO.getBookZawagMuslimTransactions15() + Long.valueOf(totalTransactionsZawagMuslim15));
			
			result.add(eMaazounBookAuditSectorDTO);
			
		}

		result.add(totalMaazounBookAuditSectorDTO);

		return new PageImpl<MaazounBookAuditSectorDTO>(result, pageable, total);
	}

	@Override
	public Page<MaazounBookAvailableDTO> getBookRequestInfoByMaazounId(
			GetBookRequestInfoByMaazounId getBookRequestInfoByMaazounId) {

		Pageable pageable = PageRequest.of(Integer.valueOf(getBookRequestInfoByMaazounId.getPageNo()),
				Integer.valueOf(getBookRequestInfoByMaazounId.getPageSize()));

		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();

		jpql.append("SELECT w.book_financial_number as financialNumber, w.serial_number as serialNumber, "
				+ "w.status_fk as status, u.name as typeName,s.name as sectorName, l.name as locationName, c.name_ar as cityName, "
				+ "r.created_at as soldDate, r.created_by as soldBy, r.id as reqInfoId, "
				+ "r.maazounia_name as maazouniaName, u.contracts_count as contractsCount, "
				+ "p.name as maazounName, p.national_id as maazounNationalId "
				+ "FROM maazoun_book_request_info r " + "JOIN maazoun_profile p ON p.id = r.maazoun_profile_fk "
				// + "JOIN maazoun_book_warehouse w ON w.serial_number = r.book_serial_number or
				// w.book_financial_number = r.book_serial_number "
				+ "JOIN maazoun_book_warehouse w ON w.maazoun_book_request_info_fk = r.id "
				+ "JOIN sub_service u ON u.id = r.book_type_id " + "JOIN sector s ON s.id = r.sector_fk "
				+ "JOIN location l ON l.id = s.location_fk " + "JOIN city c ON c.id = l.city_fk " + "WHERE p.id = "
				+ getBookRequestInfoByMaazounId.getMaazounId() + " ");

		jpqlCount.append("SELECT count(*) FROM maazoun_book_request_info r "
				+ "JOIN maazoun_profile p ON p.id = r.maazoun_profile_fk "
				// + "JOIN maazoun_book_warehouse w ON w.serial_number = r.book_serial_number or
				// w.book_financial_number = r.book_serial_number "
				+ "JOIN maazoun_book_warehouse w ON w.maazoun_book_request_info_fk = r.id "
				+ "JOIN sub_service u ON u.id = r.book_type_id " + "JOIN sector s ON s.id = r.sector_fk "
				+ "JOIN location l ON l.id = s.location_fk " + "JOIN city c ON c.id = l.city_fk " + "WHERE p.id = "
				+ getBookRequestInfoByMaazounId.getMaazounId() + " ");

		if (Boolean.valueOf(getBookRequestInfoByMaazounId.getAvailableStatus())) {
			jpql.append(
					"AND (w.status_fk = 'Sold' OR w.status_fk = 'Collected' OR w.status_fk = 'Reviewed' OR w.status_fk = 'Reviewed Custody') ");
			jpqlCount.append(
					"AND (w.status_fk = 'Sold' OR w.status_fk = 'Collected' OR w.status_fk = 'Reviewed' OR w.status_fk = 'Reviewed Custody') ");
		}

		jpql.append("GROUP BY w.maazoun_book_request_info_fk");
		jpqlCount.append("GROUP BY w.maazoun_book_request_info_fk");

		Query query = em.createNativeQuery(jpql.toString());
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Query queryCount = em.createNativeQuery(jpqlCount.toString());
		long total = queryCount.getResultList().size();

		List<Object[]> list = query.getResultList();
		List<MaazounBookAvailableDTO> result = new ArrayList<MaazounBookAvailableDTO>();

		for (Object[] q1 : list) {

			String bookFininacialNumber = q1[0].toString();
			String bookSerialNumber = q1[1] != null ? q1[1].toString() : null;
			String bookStatus = q1[2].toString();

			String bookType = q1[3].toString();
			String sector = q1[4].toString();
			String location = q1[5].toString();

			String city = q1[6].toString();
			String soldDate = q1[7].toString();
			String soldBy = q1[8].toString();
			String reqInfoId = q1[9].toString();
			String maazouniaName = q1[10].toString();
			String contractsCount = q1[11].toString();
			String maazounName = q1[12].toString();
			String maazounNationalId = q1[13].toString();

			MaazounBookAvailableDTO eMaazounBookAvailableDTO = new MaazounBookAvailableDTO();
			eMaazounBookAvailableDTO.setBookFininacialNumber(bookFininacialNumber);
			eMaazounBookAvailableDTO.setBookSerialNumber(bookSerialNumber);
			eMaazounBookAvailableDTO.setBookStatus(bookStatus);
			eMaazounBookAvailableDTO.setBookType(bookType + "-" + contractsCount);
			eMaazounBookAvailableDTO.setSector(sector);
			eMaazounBookAvailableDTO.setSoldBy(soldBy);
			eMaazounBookAvailableDTO.setSoldDate(soldDate);
			eMaazounBookAvailableDTO.setReqInfoId(reqInfoId);
			eMaazounBookAvailableDTO.setMaazouniaName(maazouniaName);
			eMaazounBookAvailableDTO
					.setCustody((bookSerialNumber != null && !bookSerialNumber.isEmpty()) ? "الكترونى" : "عهده");
			eMaazounBookAvailableDTO.setMaazounName(maazounName);
			eMaazounBookAvailableDTO.setMaazounNationalId(maazounNationalId);

			result.add(eMaazounBookAvailableDTO);
		}

		return new PageImpl<MaazounBookAvailableDTO>(result, pageable, total);
	}

	private void accumalteObject(MaazounBookAuditDTO totalMaazounBookAuditDTO,
			MaazounBookAuditDTO maazounBookAuditDTO) {

		Long sumTotalTransaction = Long.valueOf(totalMaazounBookAuditDTO.getTotalTransaction())
				+ Long.valueOf(maazounBookAuditDTO.getTotalTransaction());
		totalMaazounBookAuditDTO.setTotalTransaction(String.valueOf(sumTotalTransaction));
		Long sumTotalCollectionTransaction = Long.valueOf(totalMaazounBookAuditDTO.getTotalCollectionTransaction())
				+ Long.valueOf(maazounBookAuditDTO.getTotalCollectionTransaction());
		totalMaazounBookAuditDTO.setTotalCollectionTransaction(String.valueOf(sumTotalCollectionTransaction));
		Long sumTotalSettlementTransaction = Long.valueOf(totalMaazounBookAuditDTO.getTotalSettlementTransaction())
				+ Long.valueOf(maazounBookAuditDTO.getTotalSettlementTransaction());
		totalMaazounBookAuditDTO.setTotalSettlementTransaction(String.valueOf(sumTotalSettlementTransaction));
		Long sumTotalRefundTransaction = Long.valueOf(totalMaazounBookAuditDTO.getTotalRefundTransaction())
				+ Long.valueOf(maazounBookAuditDTO.getTotalRefundTransaction());
		totalMaazounBookAuditDTO.setTotalRefundTransaction(String.valueOf(sumTotalRefundTransaction));
		Long sumTotalCancelledTransaction = Long.valueOf(totalMaazounBookAuditDTO.getTotalCancelledTransaction())
				+ Long.valueOf(maazounBookAuditDTO.getTotalCancelledTransaction());
		totalMaazounBookAuditDTO.setTotalCancelledTransaction(String.valueOf(sumTotalCancelledTransaction));

		Double sumSystemTotalCollectionAmount = Double
				.valueOf(totalMaazounBookAuditDTO.getSystemTotalCollectionAmount())
				+ Double.valueOf(maazounBookAuditDTO.getSystemTotalCollectionAmount());
		totalMaazounBookAuditDTO.setSystemTotalCollectionAmount(String.format("%.2f", sumSystemTotalCollectionAmount));
		Double sumSystemTotalSettlementAmount = Double
				.valueOf(totalMaazounBookAuditDTO.getSystemTotalSettlementAmount())
				+ Double.valueOf(maazounBookAuditDTO.getSystemTotalSettlementAmount());
		totalMaazounBookAuditDTO.setSystemTotalSettlementAmount(String.format("%.2f", sumSystemTotalSettlementAmount));
		Double sumSystemTotalAmountRefund = Double.valueOf(totalMaazounBookAuditDTO.getSystemTotalAmountRefund())
				+ Double.valueOf(maazounBookAuditDTO.getSystemTotalAmountRefund());
		totalMaazounBookAuditDTO.setSystemTotalAmountRefund(String.format("%.2f", sumSystemTotalAmountRefund));

		Double sumSystemTotalAmountCash = Double.valueOf(totalMaazounBookAuditDTO.getSystemTotalAmountCash())
				+ Double.valueOf(maazounBookAuditDTO.getSystemTotalAmountCash());
		totalMaazounBookAuditDTO.setSystemTotalAmountCash(String.format("%.2f", sumSystemTotalAmountCash));
		Double sumSystemTotalAmountVisa = Double.valueOf(totalMaazounBookAuditDTO.getSystemTotalAmountVisa())
				+ Double.valueOf(maazounBookAuditDTO.getSystemTotalAmountVisa());
		totalMaazounBookAuditDTO.setSystemTotalAmountVisa(String.format("%.2f", sumSystemTotalAmountVisa));

		Long sumTotalHoldTransaction = Long.valueOf(totalMaazounBookAuditDTO.getTotalHoldTransaction())
				+ Long.valueOf(maazounBookAuditDTO.getTotalHoldTransaction());
		totalMaazounBookAuditDTO.setTotalHoldTransaction(String.valueOf(sumTotalHoldTransaction));
		Long sumTotalFinalTransaction = Long.valueOf(totalMaazounBookAuditDTO.getTotalFinalTransaction())
				+ Long.valueOf(maazounBookAuditDTO.getTotalFinalTransaction());
		totalMaazounBookAuditDTO.setTotalFinalTransaction(String.valueOf(sumTotalFinalTransaction));
		Long sumTotalSynchedTransaction = Long.valueOf(totalMaazounBookAuditDTO.getTotalSynchedTransaction())
				+ Long.valueOf(maazounBookAuditDTO.getTotalSynchedTransaction());
		totalMaazounBookAuditDTO.setTotalSynchedTransaction(String.valueOf(sumTotalSynchedTransaction));
		Long sumTotalMatchedTransaction = Long.valueOf(totalMaazounBookAuditDTO.getTotalMatchedTransaction())
				+ Long.valueOf(maazounBookAuditDTO.getTotalMatchedTransaction());
		totalMaazounBookAuditDTO.setTotalMatchedTransaction(String.valueOf(sumTotalMatchedTransaction));

	}

}
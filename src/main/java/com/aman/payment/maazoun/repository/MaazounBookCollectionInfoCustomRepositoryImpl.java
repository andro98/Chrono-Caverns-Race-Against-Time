package com.aman.payment.maazoun.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.aman.payment.auth.model.Pos;
import com.aman.payment.maazoun.model.MaazounBookCollectionInfo;
import com.aman.payment.maazoun.model.dto.ContractValidationDTO;
import com.aman.payment.maazoun.model.dto.MaazounBookAuditSectorDTO;
import com.aman.payment.maazoun.model.dto.MaazounBookReviewDTO;
import com.aman.payment.maazoun.model.dto.MaazounContractAuditDTO;
import com.aman.payment.maazoun.model.payload.AdvancedSearchCollectionInfoTransactionRequest;
import com.aman.payment.maazoun.model.payload.MaazounAuditRequest;
import com.aman.payment.util.StatusConstant;

@Repository
public class MaazounBookCollectionInfoCustomRepositoryImpl implements MaazounBookCollectionInfoCustomRepository {

	@PersistenceContext
	private EntityManager em;

	public MaazounBookCollectionInfoCustomRepositoryImpl(EntityManager em) {
		super();
		this.em = em;
	}

	@Override
	public Page<MaazounBookCollectionInfo> findByMaazounNationalId(String nationalId, Pageable pageable) {

		String jpql = "SELECT mo FROM MaazounBookCollectionInfo mo "
				+ "JOIN MaazounProfile p ON p.id = mo.maazounProfileFk " + "WHERE p.nationalId = '" + nationalId + "' ";

		String jpqlCount = "SELECT count(mo) FROM MaazounBookCollectionInfo mo "
				+ "JOIN MaazounProfile p ON p.id = mo.maazounProfileFk " + "WHERE p.nationalId = '" + nationalId + "' ";

		TypedQuery<MaazounBookCollectionInfo> query = em.createQuery(jpql, MaazounBookCollectionInfo.class);
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		TypedQuery<Long> queryCount = em.createQuery(jpqlCount, Long.class);
		long total = queryCount.getSingleResult();

		return new PageImpl<MaazounBookCollectionInfo>(query.getResultList(), pageable, total);
	}

	@Override
	public Page<MaazounBookCollectionInfo> findByMaazounCardNumber(String cardNumber, Pageable pageable) {

		String jpql = "SELECT mo FROM MaazounBookCollectionInfo mo "
				+ "JOIN MaazounProfile p ON p.id = mo.maazounProfileFk " + "WHERE p.cardNumber = '" + cardNumber + "' ";

		String jpqlCount = " SELECT count(mo) FROM MaazounBookCollectionInfo mo "
				+ "JOIN MaazounProfile p ON p.id = mo.maazounProfileFk " + "WHERE p.cardNumber = '" + cardNumber + "' ";

		TypedQuery<MaazounBookCollectionInfo> query = em.createQuery(jpql, MaazounBookCollectionInfo.class);
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		TypedQuery<Long> queryCount = em.createQuery(jpqlCount, Long.class);
		long total = queryCount.getSingleResult();

		return new PageImpl<MaazounBookCollectionInfo>(query.getResultList(), pageable, total);

	}

	@Override
	public Page<MaazounBookCollectionInfo> findByMaazounMobile(String mobile, Pageable pageable) {

		String jpql = "SELECT mo FROM MaazounBookCollectionInfo mo "
				+ "JOIN MaazounProfile p ON p.id = mo.maazounProfileFk " + "WHERE p.mobile = '" + mobile + "' ";

		String jpqlCount = "SELECT count(mo) FROM MaazounBookCollectionInfo mo "
				+ "JOIN MaazounProfile p ON p.id = mo.maazounProfileFk " + "WHERE p.mobile = '" + mobile + "' ";

		TypedQuery<MaazounBookCollectionInfo> query = em.createQuery(jpql, MaazounBookCollectionInfo.class);
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		TypedQuery<Long> queryCount = em.createQuery(jpqlCount, Long.class);
		long total = queryCount.getSingleResult();

		return new PageImpl<MaazounBookCollectionInfo>(query.getResultList(), pageable, total);

	}

	@Override
	public Page<MaazounBookCollectionInfo> advancedSearch(AdvancedSearchCollectionInfoTransactionRequest obj,
			CustomUserDetails customUserDetails) {

		Set<Long> posIds = new HashSet<Long>();
		customUserDetails.getPosSet().stream().forEach(s -> {
			posIds.add(s.getId());
		});
		
		Pageable pageable = PageRequest.of(Integer.valueOf(obj.getPageNo()), Integer.valueOf(obj.getPageSize()));

		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();

		jpql.append("SELECT mo FROM MaazounBookCollectionInfo mo "
				+ "JOIN MaazounProfile p ON p.id = mo.maazounProfileFk " + "JOIN Pos s ON s.id = mo.posFk "
				+ "JOIN Location l ON l.id = mo.locationId " 
//				+ "JOIN Transaction t ON t.transCode = mo.transactionCode "
//				+ "JOIN PaymentType pt ON pt.id = t.paymentTypeFk " 
				+ "WHERE " + "mo.createdAt >= '"
				+ obj.getDurationFrom() + " 00:00:00' " + "AND mo.createdAt <= '" + obj.getDurationTo()
				+ " 23:59:59' ");
		jpqlCount.append("SELECT count(mo) FROM MaazounBookCollectionInfo mo "
				+ "JOIN MaazounProfile p ON p.id = mo.maazounProfileFk " + "JOIN Pos s ON s.id = mo.posFk "
				+ "JOIN Location l ON l.id = mo.locationId " 
//				+ "JOIN Transaction t ON t.transCode = mo.transactionCode "
//				+ "JOIN PaymentType pt ON pt.id = t.paymentTypeFk " 
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
			jpql.append("AND mo.posFk = '" + obj.getPosId().trim() + "' ");
			jpqlCount.append("AND mo.posFk = '" + obj.getPosId().trim() + "' ");
		}

		if (obj.getTransactionCode() != null && !obj.getTransactionCode().isEmpty()) {
			jpql.append("AND mo.transactionCode LIKE '%" + obj.getTransactionCode().trim() + "%' ");
			jpqlCount.append("AND mo.transactionCode  LIKE '%" + obj.getTransactionCode().trim() + "%' ");
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
			jpql.append("AND p.name Like '%" + obj.getMaazounName() + "%' ");
			jpqlCount.append("AND p.name  Like '%" + obj.getMaazounName() + "%' ");
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

		TypedQuery<MaazounBookCollectionInfo> query = em.createQuery(jpql.toString(), MaazounBookCollectionInfo.class);
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		TypedQuery<Long> queryCount = em.createQuery(jpqlCount.toString(), Long.class);
		long total = queryCount.getSingleResult();// .getResultList();//.size();

		return new PageImpl<MaazounBookCollectionInfo>(query.getResultList(), pageable, total);

	}

	@Override
	public List<MaazounBookReviewDTO> getContractCollectedStatusFk(String statusFk, Set<Long> locationIds) {

		List<MaazounBookReviewDTO> result = new ArrayList<MaazounBookReviewDTO>();

		String jpql = "SELECT m.id," + " m.status_fk, m.image_url, m.fee_amount, m.contract_paid_amount,"
				+ " m.contract_date, m.contract_number, m.husband_name, m.wife_name,"
				+ " mp.name as maazoun_name, mp.national_id, w.book_type_name,"
				+ " p.name as pos_name, l.name as location_name" + " FROM maazoun_book_collection_info m"
				+ " JOIN maazoun_profile mp ON mp.id = m.maazoun_profile_fk"
				+ " JOIN maazoun_book_warehouse w ON w.contract_number = m.contract_number"
				+ " JOIN pos p ON p.id = m.pos_fk " + " JOIN location l ON l.id = m.location_id "
				+ " WHERE m.location_id IN (" + locationIds.toString().replace("]", "").replace("[", "") + ") ";

		Query query = em.createNativeQuery(jpql.toString());

		List<Object[]> list = query.getResultList();

		for (Object[] q1 : list) {

			MaazounBookReviewDTO eMaazounBookReviewDTO = new MaazounBookReviewDTO();
			eMaazounBookReviewDTO.setId(q1[0].toString());
			eMaazounBookReviewDTO.setStatus(q1[1].toString());
			eMaazounBookReviewDTO.setImageUrl(q1[2].toString());
			eMaazounBookReviewDTO.setFeeAmount(q1[3].toString());
			eMaazounBookReviewDTO.setContractPaidAmount(q1[4].toString());
			eMaazounBookReviewDTO.setContractDate(q1[5].toString());
			eMaazounBookReviewDTO.setContractNumber(q1[6].toString());
			eMaazounBookReviewDTO.setHusbandName(q1[7].toString());
			eMaazounBookReviewDTO.setWifeName(q1[8].toString());
			eMaazounBookReviewDTO.setMaazounName(q1[9].toString());
			eMaazounBookReviewDTO.setMaazounNationalId(q1[10].toString());
			eMaazounBookReviewDTO.setBookTypeName(q1[11].toString());
			eMaazounBookReviewDTO.setPosName(q1[12].toString());
			eMaazounBookReviewDTO.setLocationName(q1[13].toString());

			String jpqlValidation = "SELECT m.value_desc, ch.name as checklist_name" + " FROM maazoun_book_validation m"
					+ " JOIN maazoun_check_list_lookup ch ON ch.id = m.maazoun_check_list_lookup_fk"
					+ " WHERE m.maazoun_book_collection_info_fk = " + Long.valueOf(eMaazounBookReviewDTO.getId()) + " ";
			Query jpqlValidationQuery = em.createNativeQuery(jpqlValidation.toString());

			List<Object[]> list2 = jpqlValidationQuery.getResultList();
			List<ContractValidationDTO> validationList = new ArrayList<ContractValidationDTO>();
			for (Object[] q2 : list2) {
				ContractValidationDTO eContractValidationDTO = new ContractValidationDTO();
				eContractValidationDTO.setKey(q2[0].toString());
				eContractValidationDTO.setValue(q2[1].toString());
				validationList.add(eContractValidationDTO);
			}

			eMaazounBookReviewDTO.setValidationSet(validationList);

			result.add(eMaazounBookReviewDTO);

		}

		return result;
	}

	@Override
	public Page<MaazounContractAuditDTO> getAuditByCreatedDate(MaazounAuditRequest maazounAuditRequest,
			CustomUserDetails customUserDetails) {

		Set<Long> posIds = customUserDetails.getPosSet().stream().map(Pos::getId).collect(Collectors.toSet());

		Pageable pageable = PageRequest.of(Integer.valueOf(maazounAuditRequest.getPageNo()),
				Integer.valueOf(maazounAuditRequest.getPageSize()));

		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();

//		List<MaazounContractAuditDTO> result = new ArrayList<MaazounContractAuditDTO>();
		jpql.append("SELECT"
//				+ " COUNT(*) as totalTransaction,"
				+ " SUM(IF (v.status_fk != 'Refund', 1, 0)) as totalTransactions,"
				+ " SUM(IF (v.status_fk = 'Refund', 1, 0)) as totalRefundTransaction,"
				+ " SUM(IF (v.status_fk = 'Canceled', 1, 0)) as totalCancelledTransaction,"

				+ " SUM(IF (v.status_fk != 'Refund', v.fee_amount, 0)) as totalTransactionAmount,"
				+ " SUM(IF (v.status_fk = 'Refund', v.fee_amount, 0)) as totalRefundAmount,"
				+ " SUM(IF (v.status_fk = 'Canceled', v.fee_amount, 0)) as totalCanceledAmount,"

				+ " SUM(IF (v.status_fk != 'Refund' && v.payment_method != 'Visa', v.fee_amount, 0)) as totalTransactionsAmountCash,"
				+ " SUM(IF (v.status_fk != 'Refund' && v.payment_method = 'Visa', v.fee_amount, 0)) as totalTransactionsAmountVisa,"

				+ " SUM(IF (v.status_fk = 'Refund' && v.payment_method != 'Visa', v.fee_amount, 0)) as totalRefundAmountCash,"
				+ " SUM(IF (v.status_fk = 'Refund' && v.payment_method = 'Visa', v.fee_amount, 0)) as totalRefundAmountVisa,"

				+ " SUM(IF (v.status_fk = 'Hold', 1, 0)) as totalHoldTransaction,"
				+ " SUM(IF (v.status_fk = 'Final', 1, 0)) as totalFinalTransaction,"
				+ " SUM(IF (v.status_fk = 'Synched', 1, 0)) as totalSynchedTransaction,"

				+ " DATE_FORMAT(t.created_at, '%Y/%m/%d') as createDate" 
				+ " FROM maazoun_book_collection_info v "
				+ " JOIN transaction t ON t.trans_code = v.transaction_code"
				+ " WHERE t.created_at >= :fromDate "
				+ " AND t.created_at <= :toDate "
				+ " AND v.transaction_code IS NOT NULL");

		jpqlCount.append("SELECT COUNT(*) " 
				+ " FROM maazoun_book_collection_info v " 
				+ " JOIN transaction t ON t.trans_code = v.transaction_code"
				+ " WHERE t.created_at >= '"
				+ maazounAuditRequest.getDurationFrom() + " 00:00:00' " + " AND t.created_at <= '"
				+ maazounAuditRequest.getDurationTo() + " 23:59:59' " + " AND v.transaction_code is not null");

		if (customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT_SUPERVISOR)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AREA_MANAGER)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_SUPERVISOR)) {

			jpql.append(" AND v.pos_fk IN :posIds ");
			jpqlCount.append(" AND v.pos_fk IN (" + posIds.toString().replace("]", "").replace("[", "") + ") ");
		}

		jpql.append(" GROUP BY date(t.created_at) ORDER BY t.created_at DESC");
		jpqlCount.append(" GROUP BY date(t.created_at)");

		Query query = em.createNativeQuery(jpql.toString());
	    query.setParameter("fromDate", maazounAuditRequest.getDurationFrom() + " 00:00:00");
	    query.setParameter("toDate", maazounAuditRequest.getDurationTo() + " 23:59:59");
	    
	    if (customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT)
	            || customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT_SUPERVISOR)
	            || customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AREA_MANAGER)
	            || customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_SUPERVISOR)) {
	        query.setParameter("posIds", posIds);
	    }
	    
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Query queryCount = em.createNativeQuery(jpqlCount.toString());
		long total = queryCount.getResultList().size();

		List<Object[]> list = query.getResultList();
	    List<MaazounContractAuditDTO> result = new ArrayList<>();
		
		MaazounContractAuditDTO totalMaazounContractAuditDTO = new MaazounContractAuditDTO();
		totalMaazounContractAuditDTO.setCreateDate("الإجمالى");
		totalMaazounContractAuditDTO.setTotalTransaction("0");
		totalMaazounContractAuditDTO.setTotalCollectionTransaction("0");
		totalMaazounContractAuditDTO.setTotalSettlementTransaction("0");
		totalMaazounContractAuditDTO.setTotalRefundTransaction("0");
		totalMaazounContractAuditDTO.setTotalCancelledTransaction("0");
		totalMaazounContractAuditDTO.setSystemTotalCollectionAmount("0");
		totalMaazounContractAuditDTO.setSystemTotalSettlementAmount("0");
		totalMaazounContractAuditDTO.setSystemTotalAmountRefund("0");
		totalMaazounContractAuditDTO.setSystemTotalAmountCash("0");
		totalMaazounContractAuditDTO.setSystemTotalAmountVisa("0");
		totalMaazounContractAuditDTO.setTotalHoldTransaction("0");
		totalMaazounContractAuditDTO.setTotalFinalTransaction("0");
		totalMaazounContractAuditDTO.setTotalSynchedTransaction("0");
		totalMaazounContractAuditDTO.setTotalMatchedTransaction("0");

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

			MaazounContractAuditDTO maazounContractAuditDTO = new MaazounContractAuditDTO();
			maazounContractAuditDTO.setTotalTransaction(totalTransactions);
			maazounContractAuditDTO.setTotalCollectionTransaction(
					String.valueOf(Long.valueOf(totalTransactions) - Long.valueOf(totalRefundTransaction)));
			maazounContractAuditDTO.setTotalSettlementTransaction(
					String.valueOf(Long.valueOf(totalTransactions) - Long.valueOf(totalCancelledTransaction)));
			maazounContractAuditDTO.setTotalRefundTransaction(totalRefundTransaction);
			maazounContractAuditDTO.setTotalCancelledTransaction(totalCancelledTransaction);

			maazounContractAuditDTO.setSystemTotalCollectionAmount(
					String.valueOf(Double.valueOf(totalTransactionAmount) - Double.valueOf(totalRefundAmount)));
			maazounContractAuditDTO.setSystemTotalSettlementAmount(
					String.valueOf(Double.valueOf(totalTransactionAmount) - Double.valueOf(totalCanceledAmount)));
			maazounContractAuditDTO.setSystemTotalAmountRefund(totalRefundAmount);

			maazounContractAuditDTO.setSystemTotalAmountCash(String
					.valueOf(Double.valueOf(totalTransactionsAmountCash) - Double.valueOf(totalRefundAmountCash)));
			maazounContractAuditDTO.setSystemTotalAmountVisa(String
					.valueOf(Double.valueOf(totalTransactionsAmountVisa) - Double.valueOf(totalRefundAmountVisa)));

			maazounContractAuditDTO.setTotalHoldTransaction(totalHoldTransaction);
			maazounContractAuditDTO.setTotalFinalTransaction(totalFinalTransaction);
			maazounContractAuditDTO.setTotalSynchedTransaction(totalSynchedTransaction);
			maazounContractAuditDTO.setTotalMatchedTransaction(totalMatchedTransaction);

			maazounContractAuditDTO.setCreateDate(createDate);

			result.add(maazounContractAuditDTO);
			
			accumalteObject(totalMaazounContractAuditDTO, maazounContractAuditDTO);

		}

		result.add(totalMaazounContractAuditDTO);
		
		return new PageImpl<MaazounContractAuditDTO>(result, pageable, total);
	}

	@Override
	public Page<MaazounContractAuditDTO> getAuditByCreatedDateAndLocation(MaazounAuditRequest maazounAuditRequest,
			CustomUserDetails customUserDetails) {

		Set<Long> posIds = new HashSet<Long>();
		customUserDetails.getPosSet().stream().forEach(s -> {
			posIds.add(s.getId());
		});

		Pageable pageable = PageRequest.of(Integer.valueOf(maazounAuditRequest.getPageNo()),
				Integer.valueOf(maazounAuditRequest.getPageSize()));

		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();

		List<MaazounContractAuditDTO> result = new ArrayList<MaazounContractAuditDTO>();
		jpql.append("SELECT" + " SUM(IF (v.status_fk != 'Refund', 1, 0)) as totalTransactions,"
				+ " SUM(IF (v.status_fk = 'Refund', 1, 0)) as totalRefundTransaction,"
				+ " SUM(IF (v.status_fk = 'Canceled', 1, 0)) as totalCancelledTransaction,"

				+ " SUM(IF (v.status_fk != 'Refund', v.fee_amount, 0)) as totalTransactionAmount,"
				+ " SUM(IF (v.status_fk = 'Refund', v.fee_amount, 0)) as totalRefundAmount,"
				+ " SUM(IF (v.status_fk = 'Canceled', v.fee_amount, 0)) as totalCanceledAmount,"

				+ " SUM(IF (v.status_fk != 'Refund' && v.payment_method != 'Visa', v.fee_amount, 0)) as totalTransactionsAmountCash,"
				+ " SUM(IF (v.status_fk != 'Refund' && v.payment_method = 'Visa', v.fee_amount, 0)) as totalTransactionsAmountVisa,"

				+ " SUM(IF (v.status_fk = 'Refund' && v.payment_method != 'Visa', v.fee_amount, 0)) as totalRefundAmountCash,"
				+ " SUM(IF (v.status_fk = 'Refund' && v.payment_method = 'Visa', v.fee_amount, 0)) as totalRefundAmountVisa,"

				+ " SUM(IF (v.status_fk = 'Hold', 1, 0)) as totalHoldTransaction,"
				+ " SUM(IF (v.status_fk = 'Final', 1, 0)) as totalFinalTransaction,"
				+ " SUM(IF (v.status_fk = 'Synched', 1, 0)) as totalSynchedTransaction,"

				+ " DATE_FORMAT(t.created_at, '%Y/%m/%d') as createDate," + " v.location_id as locationId,"
				+ " c.name_ar as cityName," + " l.name as locationName," + " s.name as sectorName"
				+ " FROM maazoun_book_collection_info v " 
				+ " JOIN transaction t ON t.trans_code = v.transaction_code"
				+ " JOIN sector s ON s.id = v.sector_fk"
				+ " JOIN location l ON l.id = s.location_fk" + " JOIN city c ON c.id = l.city_fk"
				+ " WHERE t.created_at >= '" + maazounAuditRequest.getDurationFrom() + " 00:00:00' "
				+ " AND t.created_at <= '" + maazounAuditRequest.getDurationTo() + " 23:59:59' "
				+ " AND v.transaction_code is not null");

		jpqlCount.append("SELECT COUNT(*) " + " FROM maazoun_book_collection_info v "
				+ " JOIN transaction t ON t.trans_code = v.transaction_code"
				+ " JOIN sector s ON s.id = v.sector_fk" + " JOIN location l ON l.id = s.location_fk"
				+ " JOIN city c ON c.id = l.city_fk" + " WHERE t.created_at >= '"
				+ maazounAuditRequest.getDurationFrom() + " 00:00:00' " + " AND t.created_at <= '"
				+ maazounAuditRequest.getDurationTo() + " 23:59:59' " + " AND v.transaction_code is not null");

		if (customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT_SUPERVISOR)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AREA_MANAGER)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_SUPERVISOR)) {

			jpql.append(" AND v.pos_fk IN (" + posIds.toString().replace("]", "").replace("[", "") + ") ");
			jpqlCount.append(" AND v.pos_fk IN (" + posIds.toString().replace("]", "").replace("[", "") + ") ");
		}

		jpql.append(" GROUP BY date(t.created_at), v.location_id ORDER BY t.created_at desc");
		jpqlCount.append(" GROUP BY date(t.created_at), v.location_id");

		Query query = em.createNativeQuery(jpql.toString());
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Query queryCount = em.createNativeQuery(jpqlCount.toString());
		long total = queryCount.getResultList().size();

		List<Object[]> list = query.getResultList();
		
		MaazounContractAuditDTO totalMaazounContractAuditDTO = new MaazounContractAuditDTO();
		totalMaazounContractAuditDTO.setLocationName("الإجمالى");
		totalMaazounContractAuditDTO.setTotalTransaction("0");
		totalMaazounContractAuditDTO.setTotalCollectionTransaction("0");
		totalMaazounContractAuditDTO.setTotalSettlementTransaction("0");
		totalMaazounContractAuditDTO.setTotalRefundTransaction("0");
		totalMaazounContractAuditDTO.setTotalCancelledTransaction("0");
		totalMaazounContractAuditDTO.setSystemTotalCollectionAmount("0");
		totalMaazounContractAuditDTO.setSystemTotalSettlementAmount("0");
		totalMaazounContractAuditDTO.setSystemTotalAmountRefund("0");
		totalMaazounContractAuditDTO.setSystemTotalAmountCash("0");
		totalMaazounContractAuditDTO.setSystemTotalAmountVisa("0");
		totalMaazounContractAuditDTO.setTotalHoldTransaction("0");
		totalMaazounContractAuditDTO.setTotalFinalTransaction("0");
		totalMaazounContractAuditDTO.setTotalSynchedTransaction("0");
		totalMaazounContractAuditDTO.setTotalMatchedTransaction("0");

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
			String sector = q1[17].toString();

			MaazounContractAuditDTO maazounContractAuditDTO = new MaazounContractAuditDTO();
			maazounContractAuditDTO.setTotalTransaction(totalTransactions);
			maazounContractAuditDTO.setTotalCollectionTransaction(
					String.valueOf(Long.valueOf(totalTransactions) - Long.valueOf(totalRefundTransaction)));
			maazounContractAuditDTO.setTotalSettlementTransaction(
					String.valueOf(Long.valueOf(totalTransactions) - Long.valueOf(totalCancelledTransaction)));
			maazounContractAuditDTO.setTotalRefundTransaction(totalRefundTransaction);
			maazounContractAuditDTO.setTotalCancelledTransaction(totalCancelledTransaction);

			maazounContractAuditDTO.setSystemTotalCollectionAmount(
					String.valueOf(Double.valueOf(totalTransactionAmount) - Double.valueOf(totalRefundAmount)));
			maazounContractAuditDTO.setSystemTotalSettlementAmount(
					String.valueOf(Double.valueOf(totalTransactionAmount) - Double.valueOf(totalCanceledAmount)));
			maazounContractAuditDTO.setSystemTotalAmountRefund(totalRefundAmount);

			maazounContractAuditDTO.setSystemTotalAmountCash(String
					.valueOf(Double.valueOf(totalTransactionsAmountCash) - Double.valueOf(totalRefundAmountCash)));
			maazounContractAuditDTO.setSystemTotalAmountVisa(String
					.valueOf(Double.valueOf(totalTransactionsAmountVisa) - Double.valueOf(totalRefundAmountVisa)));

			maazounContractAuditDTO.setTotalHoldTransaction(totalHoldTransaction);
			maazounContractAuditDTO.setTotalFinalTransaction(totalFinalTransaction);
			maazounContractAuditDTO.setTotalSynchedTransaction(totalSynchedTransaction);
			maazounContractAuditDTO.setTotalMatchedTransaction(totalMatchedTransaction);

			maazounContractAuditDTO.setCreateDate(createDate);
			maazounContractAuditDTO.setLocationName(city + " " + location + " " + sector);

			result.add(maazounContractAuditDTO);

			accumalteObject(totalMaazounContractAuditDTO, maazounContractAuditDTO);
		}

		result.add(totalMaazounContractAuditDTO);
		
		return new PageImpl<MaazounContractAuditDTO>(result, pageable, total);

	}
	
	@Override
	public Page<MaazounContractAuditDTO> getAuditByCreatedDateAndCourt(MaazounAuditRequest maazounAuditRequest,
			CustomUserDetails customUserDetails) {

		Set<Long> posIds = new HashSet<Long>();
		customUserDetails.getPosSet().stream().forEach(s -> {
			posIds.add(s.getId());
		});

		Pageable pageable = PageRequest.of(Integer.valueOf(maazounAuditRequest.getPageNo()),
				Integer.valueOf(maazounAuditRequest.getPageSize()));

		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();

		List<MaazounContractAuditDTO> result = new ArrayList<MaazounContractAuditDTO>();
		jpql.append("SELECT" + " SUM(IF (v.status_fk != 'Refund', 1, 0)) as totalTransactions,"
				+ " SUM(IF (v.status_fk = 'Refund', 1, 0)) as totalRefundTransaction,"
				+ " SUM(IF (v.status_fk = 'Canceled', 1, 0)) as totalCancelledTransaction,"

				+ " SUM(IF (v.status_fk != 'Refund', v.fee_amount, 0)) as totalTransactionAmount,"
				+ " SUM(IF (v.status_fk = 'Refund', v.fee_amount, 0)) as totalRefundAmount,"
				+ " SUM(IF (v.status_fk = 'Canceled', v.fee_amount, 0)) as totalCanceledAmount,"

				+ " SUM(IF (v.status_fk != 'Refund' && v.payment_method != 'Visa', v.fee_amount, 0)) as totalTransactionsAmountCash,"
				+ " SUM(IF (v.status_fk != 'Refund' && v.payment_method = 'Visa', v.fee_amount, 0)) as totalTransactionsAmountVisa,"

				+ " SUM(IF (v.status_fk = 'Refund' && v.payment_method != 'Visa', v.fee_amount, 0)) as totalRefundAmountCash,"
				+ " SUM(IF (v.status_fk = 'Refund' && v.payment_method = 'Visa', v.fee_amount, 0)) as totalRefundAmountVisa,"

				+ " SUM(IF (v.status_fk = 'Hold', 1, 0)) as totalHoldTransaction,"
				+ " SUM(IF (v.status_fk = 'Final', 1, 0)) as totalFinalTransaction,"
				+ " SUM(IF (v.status_fk = 'Synched', 1, 0)) as totalSynchedTransaction,"

				+ " DATE_FORMAT(t.created_at, '%Y/%m/%d') as createDate," + " v.location_id as locationId,"
				+ " c.name_ar as cityName," + " l.name as locationName," + " s.name as sectorName, co.name as courtName"
				+ " FROM maazoun_book_collection_info v " 
				+ " JOIN transaction t ON t.trans_code = v.transaction_code"
				+ " JOIN sector s ON s.id = v.sector_fk"
				+ " JOIN courts co ON s.court_fk = co.id"
				+ " JOIN location l ON l.id = s.location_fk" + " JOIN city c ON c.id = l.city_fk"
				+ " WHERE t.created_at >= '" + maazounAuditRequest.getDurationFrom() + " 00:00:00' "
				+ " AND t.created_at <= '" + maazounAuditRequest.getDurationTo() + " 23:59:59' "
				+ " AND v.transaction_code is not null");

		jpqlCount.append("SELECT COUNT(*) " + " FROM maazoun_book_collection_info v "
				+ " JOIN transaction t ON t.trans_code = v.transaction_code"
				+ " JOIN sector s ON s.id = v.sector_fk" 
				+ " JOIN courts co ON s.court_fk = co.id"
				+ " JOIN location l ON l.id = s.location_fk"
				+ " JOIN city c ON c.id = l.city_fk" + " WHERE t.created_at >= '"
				+ maazounAuditRequest.getDurationFrom() + " 00:00:00' " + " AND t.created_at <= '"
				+ maazounAuditRequest.getDurationTo() + " 23:59:59' " + " AND v.transaction_code is not null");

		if (customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT_SUPERVISOR)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AREA_MANAGER)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_SUPERVISOR)) {

			jpql.append(" AND v.pos_fk IN (" + posIds.toString().replace("]", "").replace("[", "") + ") ");
			jpqlCount.append(" AND v.pos_fk IN (" + posIds.toString().replace("]", "").replace("[", "") + ") ");
		}

		jpql.append(" GROUP BY date(t.created_at), co.id ORDER BY t.created_at desc");
		jpqlCount.append(" GROUP BY date(t.created_at), co.id");

		Query query = em.createNativeQuery(jpql.toString());
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Query queryCount = em.createNativeQuery(jpqlCount.toString());
		long total = queryCount.getResultList().size();

		List<Object[]> list = query.getResultList();
		
		MaazounContractAuditDTO totalMaazounContractAuditDTO = new MaazounContractAuditDTO();
		totalMaazounContractAuditDTO.setCourtName("الإجمالى");
		totalMaazounContractAuditDTO.setTotalTransaction("0");
		totalMaazounContractAuditDTO.setTotalCollectionTransaction("0");
		totalMaazounContractAuditDTO.setTotalSettlementTransaction("0");
		totalMaazounContractAuditDTO.setTotalRefundTransaction("0");
		totalMaazounContractAuditDTO.setTotalCancelledTransaction("0");
		totalMaazounContractAuditDTO.setSystemTotalCollectionAmount("0");
		totalMaazounContractAuditDTO.setSystemTotalSettlementAmount("0");
		totalMaazounContractAuditDTO.setSystemTotalAmountRefund("0");
		totalMaazounContractAuditDTO.setSystemTotalAmountCash("0");
		totalMaazounContractAuditDTO.setSystemTotalAmountVisa("0");
		totalMaazounContractAuditDTO.setTotalHoldTransaction("0");
		totalMaazounContractAuditDTO.setTotalFinalTransaction("0");
		totalMaazounContractAuditDTO.setTotalSynchedTransaction("0");
		totalMaazounContractAuditDTO.setTotalMatchedTransaction("0");

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

			MaazounContractAuditDTO maazounContractAuditDTO = new MaazounContractAuditDTO();
			maazounContractAuditDTO.setTotalTransaction(totalTransactions);
			maazounContractAuditDTO.setTotalCollectionTransaction(
					String.valueOf(Long.valueOf(totalTransactions) - Long.valueOf(totalRefundTransaction)));
			maazounContractAuditDTO.setTotalSettlementTransaction(
					String.valueOf(Long.valueOf(totalTransactions) - Long.valueOf(totalCancelledTransaction)));
			maazounContractAuditDTO.setTotalRefundTransaction(totalRefundTransaction);
			maazounContractAuditDTO.setTotalCancelledTransaction(totalCancelledTransaction);

			maazounContractAuditDTO.setSystemTotalCollectionAmount(
					String.valueOf(Double.valueOf(totalTransactionAmount) - Double.valueOf(totalRefundAmount)));
			maazounContractAuditDTO.setSystemTotalSettlementAmount(
					String.valueOf(Double.valueOf(totalTransactionAmount) - Double.valueOf(totalCanceledAmount)));
			maazounContractAuditDTO.setSystemTotalAmountRefund(totalRefundAmount);

			maazounContractAuditDTO.setSystemTotalAmountCash(String
					.valueOf(Double.valueOf(totalTransactionsAmountCash) - Double.valueOf(totalRefundAmountCash)));
			maazounContractAuditDTO.setSystemTotalAmountVisa(String
					.valueOf(Double.valueOf(totalTransactionsAmountVisa) - Double.valueOf(totalRefundAmountVisa)));

			maazounContractAuditDTO.setTotalHoldTransaction(totalHoldTransaction);
			maazounContractAuditDTO.setTotalFinalTransaction(totalFinalTransaction);
			maazounContractAuditDTO.setTotalSynchedTransaction(totalSynchedTransaction);
			maazounContractAuditDTO.setTotalMatchedTransaction(totalMatchedTransaction);

			maazounContractAuditDTO.setCreateDate(createDate);
			//maazounContractAuditDTO.setLocationName(city + " " + location + " " + sectorName);
			maazounContractAuditDTO.setCourtName(courtName);
			maazounContractAuditDTO.setSectorName(sectorName);

			result.add(maazounContractAuditDTO);

			accumalteObject(totalMaazounContractAuditDTO, maazounContractAuditDTO);
		}

		result.add(totalMaazounContractAuditDTO);
		
		return new PageImpl<MaazounContractAuditDTO>(result, pageable, total);

	}

	@Override
	public Page<MaazounContractAuditDTO> getAuditByCreatedDateAndAgentAndLocation(
			MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails) {

		Set<Long> posIds = new HashSet<Long>();
		customUserDetails.getPosSet().stream().forEach(s -> {
			posIds.add(s.getId());
		});

		Pageable pageable = PageRequest.of(Integer.valueOf(maazounAuditRequest.getPageNo()),
				Integer.valueOf(maazounAuditRequest.getPageSize()));

		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();

		List<MaazounContractAuditDTO> result = new ArrayList<MaazounContractAuditDTO>();
		jpql.append("SELECT" + " SUM(IF (v.status_fk != 'Refund', 1, 0)) as totalTransactions,"
				+ " SUM(IF (v.status_fk = 'Refund', 1, 0)) as totalRefundTransaction,"
				+ " SUM(IF (v.status_fk = 'Canceled', 1, 0)) as totalCancelledTransaction,"

				+ " SUM(IF (v.status_fk != 'Refund', v.fee_amount, 0)) as totalTransactionAmount,"
				+ " SUM(IF (v.status_fk = 'Refund', v.fee_amount, 0)) as totalRefundAmount,"
				+ " SUM(IF (v.status_fk = 'Canceled', v.fee_amount, 0)) as totalCanceledAmount,"

				+ " SUM(IF (v.status_fk != 'Refund' && v.payment_method != 'Visa', v.fee_amount, 0)) as totalTransactionsAmountCash,"
				+ " SUM(IF (v.status_fk != 'Refund' && v.payment_method = 'Visa', v.fee_amount, 0)) as totalTransactionsAmountVisa,"

				+ " SUM(IF (v.status_fk = 'Refund' && v.payment_method != 'Visa', v.fee_amount, 0)) as totalRefundAmountCash,"
				+ " SUM(IF (v.status_fk = 'Refund' && v.payment_method = 'Visa', v.fee_amount, 0)) as totalRefundAmountVisa,"

				+ " SUM(IF (v.status_fk = 'Hold', 1, 0)) as totalHoldTransaction,"
				+ " SUM(IF (v.status_fk = 'Final', 1, 0)) as totalFinalTransaction,"
				+ " SUM(IF (v.status_fk = 'Synched', 1, 0)) as totalSynchedTransaction,"

				+ " DATE_FORMAT(v.created_at, '%Y/%m/%d') as createDate," + " v.created_by as agentName,"
				+ " v.location_id as locationId" + " FROM maazoun_book_collection_info v " + " WHERE v.created_at >= '"
				+ maazounAuditRequest.getDurationFrom() + " 00:00:00' " + " AND v.created_at <= '"
				+ maazounAuditRequest.getDurationTo() + " 23:59:59' " + " AND v.transaction_code is not null");

		jpqlCount.append("SELECT COUNT(*) " + " FROM maazoun_book_collection_info v " + " WHERE v.created_at >= '"
				+ maazounAuditRequest.getDurationFrom() + " 00:00:00' " + " AND v.created_at <= '"
				+ maazounAuditRequest.getDurationTo() + " 23:59:59' " + " AND v.transaction_code is not null");

		if (customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT_SUPERVISOR)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AREA_MANAGER)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_SUPERVISOR)) {

			jpql.append(" AND v.pos_fk IN (" + posIds.toString().replace("]", "").replace("[", "") + ") ");
			jpqlCount.append(" AND v.pos_fk IN (" + posIds.toString().replace("]", "").replace("[", "") + ") ");
		}

		jpql.append(" GROUP BY date(v.created_at), v.created_by, v.location_id ORDER BY v.created_at desc");
		jpqlCount.append(" GROUP BY date(v.created_at), v.created_by, v.location_id");

		Query query = em.createNativeQuery(jpql.toString());
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Query queryCount = em.createNativeQuery(jpqlCount.toString());
		long total = queryCount.getResultList().size();

		List<Object[]> list = query.getResultList();
		
		MaazounContractAuditDTO totalMaazounContractAuditDTO = new MaazounContractAuditDTO();
		totalMaazounContractAuditDTO.setLocationId("الإجمالى");
		totalMaazounContractAuditDTO.setTotalTransaction("0");
		totalMaazounContractAuditDTO.setTotalCollectionTransaction("0");
		totalMaazounContractAuditDTO.setTotalSettlementTransaction("0");
		totalMaazounContractAuditDTO.setTotalRefundTransaction("0");
		totalMaazounContractAuditDTO.setTotalCancelledTransaction("0");
		totalMaazounContractAuditDTO.setSystemTotalCollectionAmount("0");
		totalMaazounContractAuditDTO.setSystemTotalSettlementAmount("0");
		totalMaazounContractAuditDTO.setSystemTotalAmountRefund("0");
		totalMaazounContractAuditDTO.setSystemTotalAmountCash("0");
		totalMaazounContractAuditDTO.setSystemTotalAmountVisa("0");
		totalMaazounContractAuditDTO.setTotalHoldTransaction("0");
		totalMaazounContractAuditDTO.setTotalFinalTransaction("0");
		totalMaazounContractAuditDTO.setTotalSynchedTransaction("0");
		totalMaazounContractAuditDTO.setTotalMatchedTransaction("0");
		

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


			MaazounContractAuditDTO maazounContractAuditDTO = new MaazounContractAuditDTO();
			maazounContractAuditDTO.setTotalTransaction(totalTransactions);
			maazounContractAuditDTO.setTotalCollectionTransaction(
					String.valueOf(Long.valueOf(totalTransactions) - Long.valueOf(totalRefundTransaction)));
			maazounContractAuditDTO.setTotalSettlementTransaction(
					String.valueOf(Long.valueOf(totalTransactions) - Long.valueOf(totalCancelledTransaction)));
			maazounContractAuditDTO.setTotalRefundTransaction(totalRefundTransaction);
			maazounContractAuditDTO.setTotalCancelledTransaction(totalCancelledTransaction);

			maazounContractAuditDTO.setSystemTotalCollectionAmount(
					String.valueOf(Double.valueOf(totalTransactionAmount) - Double.valueOf(totalRefundAmount)));
			maazounContractAuditDTO.setSystemTotalSettlementAmount(
					String.valueOf(Double.valueOf(totalTransactionAmount) - Double.valueOf(totalCanceledAmount)));
			maazounContractAuditDTO.setSystemTotalAmountRefund(totalRefundAmount);

			maazounContractAuditDTO.setSystemTotalAmountCash(String
					.valueOf(Double.valueOf(totalTransactionsAmountCash) - Double.valueOf(totalRefundAmountCash)));
			maazounContractAuditDTO.setSystemTotalAmountVisa(String
					.valueOf(Double.valueOf(totalTransactionsAmountVisa) - Double.valueOf(totalRefundAmountVisa)));

			maazounContractAuditDTO.setTotalHoldTransaction(totalHoldTransaction);
			maazounContractAuditDTO.setTotalFinalTransaction(totalFinalTransaction);
			maazounContractAuditDTO.setTotalSynchedTransaction(totalSynchedTransaction);
			maazounContractAuditDTO.setTotalMatchedTransaction(totalMatchedTransaction);

			maazounContractAuditDTO.setCreateDate(createDate);
			maazounContractAuditDTO.setAgentName(agentName);
			maazounContractAuditDTO.setLocationId(locationId);

			result.add(maazounContractAuditDTO);
			
			accumalteObject(totalMaazounContractAuditDTO, maazounContractAuditDTO);
			

		}

		result.add(totalMaazounContractAuditDTO);
		
		return new PageImpl<MaazounContractAuditDTO>(result, pageable, total);

	}

	@Override
	public Page<MaazounBookAuditSectorDTO> getContractCustomAuditBySectors(MaazounAuditRequest maazounAuditRequest,
			CustomUserDetails customUserDetails) {

		Set<Long> posIds = new HashSet<Long>();
		customUserDetails.getPosSet().stream().forEach(s -> {
			posIds.add(s.getId());
		});

		Pageable pageable = PageRequest.of(Integer.valueOf(maazounAuditRequest.getPageNo()),
				Integer.valueOf(maazounAuditRequest.getPageSize()));

		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();

		List<MaazounBookAuditSectorDTO> result = new ArrayList<MaazounBookAuditSectorDTO>();
		jpql.append("SELECT" + " COUNT(i.contract_number) as totalTransaction," + " SUM(i.fee_amount) as totalAmount,"
				+ " SUM(IF (i.contract_type_id = 14, 1, 0)) AS totalTransactionsZawagMuslim,"
				+ " SUM(IF (i.contract_type_id = 14, i.fee_amount, 0)) AS totalAmountZawagMuslim,"
				+ " SUM(IF (i.contract_type_id = 15, 1, 0)) AS totalTransactionsTalak,"
				+ " SUM(IF (i.contract_type_id = 15, i.fee_amount, 0)) AS totalAmountTalak,"
				+ " SUM(IF (i.contract_type_id = 16, 1, 0)) AS totalTransactionsTasadok,"
				+ " SUM(IF (i.contract_type_id = 16, i.fee_amount, 0)) AS totalAmountTasadok,"
				+ " SUM(IF (i.contract_type_id = 17, 1, 0)) AS totalTransactionsMoragaa,"
				+ " SUM(IF (i.contract_type_id = 17, i.fee_amount, 0)) AS totalAmountMoragaa,"
				+ " SUM(IF (i.contract_type_id = 18, 1, 0)) AS totalTransactionsZawagMelly,"
				+ " SUM(IF (i.contract_type_id = 18, i.fee_amount, 0)) AS totalAmountZawagMelly,"
				+ " c.name_ar as cityName," + " l.name as locationName," + " s.name as sectorName,"
				//+ " i.contract_type_id as contractTypeId," + " u.name as contractTypeName,"
				+ " i.sector_fk as sectorId," + " l.id as locationId," + " c.id as cityId, co.name as courtName"

				+ " FROM maazoun_book_collection_info i "
				+ " JOIN transaction t ON t.trans_code = i.transaction_code"
				+ " JOIN sector s ON s.id = i.sector_fk" 
				+ " JOIN courts co ON s.court_fk = co.id"
				+ " JOIN sub_service u ON u.id = i.contract_type_id"
				+ " JOIN location l ON l.id = s.location_fk" + " JOIN city c ON c.id = l.city_fk"

				+ " WHERE t.created_at >= '" + maazounAuditRequest.getDurationFrom() + " 00:00:00' "
				+ " AND t.created_at <= '" + maazounAuditRequest.getDurationTo() + " 23:59:59' "
				+ " AND i.transaction_code is not null AND (i.status_fk = 'Collected' or i.status_fk = 'Received') ");

		jpqlCount.append("SELECT COUNT(*) " + " FROM maazoun_book_collection_info i "
				+ " JOIN transaction t ON t.trans_code = i.transaction_code"
				+ " JOIN sector s ON s.id = i.sector_fk" 
				+ " JOIN courts co ON s.court_fk = co.id"
				+ " JOIN sub_service u ON u.id = i.contract_type_id"
				+ " JOIN location l ON l.id = s.location_fk" + " JOIN city c ON c.id = l.city_fk"
				+ " WHERE t.created_at >= '" + maazounAuditRequest.getDurationFrom() + " 00:00:00' "
				+ " AND t.created_at <= '" + maazounAuditRequest.getDurationTo() + " 23:59:59' "
				+ " AND i.transaction_code is not null AND (i.status_fk = 'Collected' or i.status_fk = 'Received') ");

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
			
			String totalTransactionsZawagMuslim = q1[2].toString();
			String totalAmountZawagMuslim = String.format("%.2f", q1[3]);
			
			String totalTransactionsTalak = q1[4].toString();
			String totalAmountTalak = String.format("%.2f", q1[5]);
			
			String totalTransactionsTasadok = q1[6].toString();
			String totalAmountTasadok = String.format("%.2f", q1[7]);
			
			String totalTransactionsMoragaa = q1[8].toString();
			String totalAmountMoragaa = String.format("%.2f", q1[9]);
			
			String totalTransactionsZawagMelly = q1[10].toString();
			String totalAmountZawagMelly = String.format("%.2f", q1[11]);
			
			String cityName = q1[12].toString();

			String locationName = q1[13].toString();
			String sectorName = q1[14].toString();
//			String contractTypeId = q1[15].toString();

//			String bookTypeName = q1[6].toString();
			String sectorId = q1[15].toString();
			String locationId = q1[16].toString();
			String cityId = q1[17].toString();
			String courtName = q1[18].toString();
			
			MaazounBookAuditSectorDTO eMaazounBookAuditSectorDTO = new MaazounBookAuditSectorDTO();

			eMaazounBookAuditSectorDTO.setCityId(cityId);
			eMaazounBookAuditSectorDTO.setCityName(cityName);
			eMaazounBookAuditSectorDTO.setLocationId(locationId);
			eMaazounBookAuditSectorDTO.setLocationName(locationName);
			eMaazounBookAuditSectorDTO.setSectorId(sectorId);
			eMaazounBookAuditSectorDTO.setSectorName(sectorName);
			eMaazounBookAuditSectorDTO.setCourtName(courtName);
			
			eMaazounBookAuditSectorDTO.setBookMoragaaName(StatusConstant.CONTRACT_MORAGAA_NAME);
			eMaazounBookAuditSectorDTO.setBookMoragaaAmount8(Double.valueOf(totalAmountMoragaa));
			eMaazounBookAuditSectorDTO.setBookMoragaaTransactions8(Long.valueOf(totalTransactionsMoragaa));

			eMaazounBookAuditSectorDTO.setBookTalakName(StatusConstant.CONTRACT_TALAK_NAME);
			eMaazounBookAuditSectorDTO.setBookTalakAmount8(Double.valueOf(totalAmountTalak));
			eMaazounBookAuditSectorDTO.setBookTalakTransactions8(Long.valueOf(totalTransactionsTalak));
			
			eMaazounBookAuditSectorDTO.setBookTasadokName(StatusConstant.CONTRACT_TASADOK_NAME);
			eMaazounBookAuditSectorDTO.setBookTasadokAmount8(Double.valueOf(totalAmountTasadok));
			eMaazounBookAuditSectorDTO.setBookTasadokTransactions8(Long.valueOf(totalTransactionsTasadok));
			
			eMaazounBookAuditSectorDTO.setBookZawagMelalyName(StatusConstant.CONTRACT_ZAWAG_MELALY_NAME);
			eMaazounBookAuditSectorDTO.setBookZawagMelalyAmount8(Double.valueOf(totalAmountZawagMelly));
			eMaazounBookAuditSectorDTO.setBookZawagMelalyTransactions8(Long.valueOf(totalTransactionsZawagMelly));
			
			eMaazounBookAuditSectorDTO.setBookZawagMuslimName(StatusConstant.CONTRACT_ZAWAG_MUSLIM_NAME);
			eMaazounBookAuditSectorDTO.setBookZawagMuslimAmount8(Double.valueOf(totalAmountZawagMuslim));
			eMaazounBookAuditSectorDTO.setBookZawagMuslimTransactions8(Long.valueOf(totalTransactionsZawagMuslim));

			totalMaazounBookAuditSectorDTO.setBookMoragaaAmount8(Double.valueOf(
					String.format("%.2f",
					(totalMaazounBookAuditSectorDTO.getBookMoragaaAmount8() + Double.valueOf(totalAmountMoragaa))
					)));
			totalMaazounBookAuditSectorDTO.setBookMoragaaTransactions8(
					totalMaazounBookAuditSectorDTO.getBookMoragaaTransactions8() + Long.valueOf(totalTransactionsMoragaa));
			
			totalMaazounBookAuditSectorDTO.setBookTalakAmount8(Double.valueOf(
					String.format("%.2f",
					(totalMaazounBookAuditSectorDTO.getBookTalakAmount8() + Double.valueOf(totalAmountTalak))
					)));
			totalMaazounBookAuditSectorDTO.setBookTalakTransactions8(
					totalMaazounBookAuditSectorDTO.getBookTalakTransactions8() + Long.valueOf(totalTransactionsTalak));
			
			totalMaazounBookAuditSectorDTO.setBookTasadokAmount8(Double.valueOf(
					String.format("%.2f",
					(totalMaazounBookAuditSectorDTO.getBookTasadokAmount8() + Double.valueOf(totalAmountTasadok))
					)));
			totalMaazounBookAuditSectorDTO.setBookTasadokTransactions8(
					totalMaazounBookAuditSectorDTO.getBookTasadokTransactions8() + Long.valueOf(totalTransactionsTasadok));
			
			totalMaazounBookAuditSectorDTO.setBookZawagMelalyAmount8(Double.valueOf(
					String.format("%.2f", 
							(totalMaazounBookAuditSectorDTO.getBookZawagMelalyAmount8() + Double.valueOf(totalAmountZawagMelly))
							)));
			totalMaazounBookAuditSectorDTO.setBookZawagMelalyTransactions8(
					totalMaazounBookAuditSectorDTO.getBookZawagMelalyTransactions8() + Long.valueOf(totalTransactionsZawagMelly));
			
			totalMaazounBookAuditSectorDTO.setBookZawagMuslimAmount8(Double.valueOf(
					String.format("%.2f", 
							(totalMaazounBookAuditSectorDTO.getBookZawagMuslimAmount8() + Double.valueOf(totalAmountZawagMuslim))
							)));
			totalMaazounBookAuditSectorDTO.setBookZawagMuslimTransactions8(
					totalMaazounBookAuditSectorDTO.getBookZawagMuslimTransactions8() + Long.valueOf(totalTransactionsZawagMuslim));
			
			result.add(eMaazounBookAuditSectorDTO);
			
//			MaazounBookAuditSectorDTO eMaazounBookAuditSectorDTO = result.stream().filter(x ->
////							x.getCityId().equals(cityId) &&
////							x.getLocationId().equals(locationId) &&
//			x.getSectorId().equals(sectorId)).findAny().orElse(null);
//
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
//			}
//
//			if (contractTypeId.equals(StatusConstant.CONTRACT_MORAGAA_ID)) {
//				eMaazounBookAuditSectorDTO.setBookMoragaaName(bookTypeName);
//				eMaazounBookAuditSectorDTO.setBookMoragaaAmount(Double.valueOf(totalAmount));
//				eMaazounBookAuditSectorDTO.setBookMoragaaTransactions(Long.valueOf(totalTransactions));
//
//				totalMaazounBookAuditSectorDTO.setBookMoragaaAmount(
//						totalMaazounBookAuditSectorDTO.getBookMoragaaAmount() + Double.valueOf(totalAmount));
//				totalMaazounBookAuditSectorDTO.setBookMoragaaTransactions(
//						totalMaazounBookAuditSectorDTO.getBookMoragaaTransactions() + Long.valueOf(totalTransactions));
//			} else if (contractTypeId.equals(StatusConstant.CONTRACT_TALAK_ID)) {
//				eMaazounBookAuditSectorDTO.setBookTalakName(bookTypeName);
//				eMaazounBookAuditSectorDTO.setBookTalakAmount(Double.valueOf(totalAmount));
//				eMaazounBookAuditSectorDTO.setBookTalakTransactions(Long.valueOf(totalTransactions));
//
//				totalMaazounBookAuditSectorDTO.setBookTalakAmount(
//						totalMaazounBookAuditSectorDTO.getBookTalakAmount() + Double.valueOf(totalAmount));
//				totalMaazounBookAuditSectorDTO.setBookTalakTransactions(
//						totalMaazounBookAuditSectorDTO.getBookTalakTransactions() + Long.valueOf(totalTransactions));
//			} else if (contractTypeId.equals(StatusConstant.CONTRACT_TASADOK_ID)) {
//				eMaazounBookAuditSectorDTO.setBookTasadokName(bookTypeName);
//				eMaazounBookAuditSectorDTO.setBookTasadokAmount(Double.valueOf(totalAmount));
//				eMaazounBookAuditSectorDTO.setBookTasadokTransactions(Long.valueOf(totalTransactions));
//
//				totalMaazounBookAuditSectorDTO.setBookTasadokAmount(
//						totalMaazounBookAuditSectorDTO.getBookTasadokAmount() + Double.valueOf(totalAmount));
//				totalMaazounBookAuditSectorDTO.setBookTasadokTransactions(
//						totalMaazounBookAuditSectorDTO.getBookTasadokTransactions() + Long.valueOf(totalTransactions));
//			} else if (contractTypeId.equals(StatusConstant.CONTRACT_ZAWAG_MELALY_ID)) {
//				eMaazounBookAuditSectorDTO.setBookZawagMelalyName(bookTypeName);
//				eMaazounBookAuditSectorDTO.setBookZawagMelalyAmount(Double.valueOf(totalAmount));
//				eMaazounBookAuditSectorDTO.setBookZawagMelalyTransactions(Long.valueOf(totalTransactions));
//
//				totalMaazounBookAuditSectorDTO.setBookZawagMelalyAmount(
//						totalMaazounBookAuditSectorDTO.getBookZawagMelalyAmount() + Double.valueOf(totalAmount));
//				totalMaazounBookAuditSectorDTO
//						.setBookZawagMelalyTransactions(totalMaazounBookAuditSectorDTO.getBookZawagMelalyTransactions()
//								+ Long.valueOf(totalTransactions));
//			} else if (contractTypeId.equals(StatusConstant.CONTRACT_ZAWAG_MUSLIM_ID)) {
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

		return new PageImpl<MaazounBookAuditSectorDTO>(new ArrayList<>(result), pageable, total);
	}
	
	@Override
	public Page<MaazounBookAuditSectorDTO> getContractCustomAuditByCourts(MaazounAuditRequest maazounAuditRequest,
			CustomUserDetails customUserDetails) {

		Set<Long> posIds = new HashSet<Long>();
		customUserDetails.getPosSet().stream().forEach(s -> {
			posIds.add(s.getId());
		});

		Pageable pageable = PageRequest.of(Integer.valueOf(maazounAuditRequest.getPageNo()),
				Integer.valueOf(maazounAuditRequest.getPageSize()));

		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();

		List<MaazounBookAuditSectorDTO> result = new ArrayList<MaazounBookAuditSectorDTO>();
		jpql.append("SELECT" + " COUNT(i.contract_number) as totalTransaction," + " SUM(i.fee_amount) as totalAmount,"
				+ " SUM(IF (i.contract_type_id = 14, 1, 0)) AS totalTransactionsZawagMuslim,"
				+ " SUM(IF (i.contract_type_id = 14, i.fee_amount, 0)) AS totalAmountZawagMuslim,"
				+ " SUM(IF (i.contract_type_id = 15, 1, 0)) AS totalTransactionsTalak,"
				+ " SUM(IF (i.contract_type_id = 15, i.fee_amount, 0)) AS totalAmountTalak,"
				+ " SUM(IF (i.contract_type_id = 16, 1, 0)) AS totalTransactionsTasadok,"
				+ " SUM(IF (i.contract_type_id = 16, i.fee_amount, 0)) AS totalAmountTasadok,"
				+ " SUM(IF (i.contract_type_id = 17, 1, 0)) AS totalTransactionsMoragaa,"
				+ " SUM(IF (i.contract_type_id = 17, i.fee_amount, 0)) AS totalAmountMoragaa,"
				+ " SUM(IF (i.contract_type_id = 18, 1, 0)) AS totalTransactionsZawagMelly,"
				+ " SUM(IF (i.contract_type_id = 18, i.fee_amount, 0)) AS totalAmountZawagMelly,"
				+ " c.name_ar as cityName," + " l.name as locationName," + " s.name as sectorName,"
				//+ " i.contract_type_id as contractTypeId," + " u.name as contractTypeName,"
				+ " i.sector_fk as sectorId," + " l.id as locationId," + " c.id as cityId, co.name as courtName"

				+ " FROM maazoun_book_collection_info i "
				+ " JOIN transaction t ON t.trans_code = i.transaction_code"
				+ " JOIN sector s ON s.id = i.sector_fk" 
				+ " JOIN courts co ON s.court_fk = co.id"
				+ " JOIN sub_service u ON u.id = i.contract_type_id"
				+ " JOIN location l ON l.id = s.location_fk" + " JOIN city c ON c.id = l.city_fk"

				+ " WHERE t.created_at >= '" + maazounAuditRequest.getDurationFrom() + " 00:00:00' "
				+ " AND t.created_at <= '" + maazounAuditRequest.getDurationTo() + " 23:59:59' "
				+ " AND i.transaction_code is not null AND (i.status_fk = 'Collected' or i.status_fk = 'Received') ");

		jpqlCount.append("SELECT COUNT(*) " + " FROM maazoun_book_collection_info i "
				+ " JOIN transaction t ON t.trans_code = i.transaction_code"
				+ " JOIN sector s ON s.id = i.sector_fk" 
				+ " JOIN courts co ON s.court_fk = co.id"
				+ " JOIN sub_service u ON u.id = i.contract_type_id"
				+ " JOIN location l ON l.id = s.location_fk" + " JOIN city c ON c.id = l.city_fk"
				+ " WHERE t.created_at >= '" + maazounAuditRequest.getDurationFrom() + " 00:00:00' "
				+ " AND t.created_at <= '" + maazounAuditRequest.getDurationTo() + " 23:59:59' "
				+ " AND i.transaction_code is not null AND (i.status_fk = 'Collected' or i.status_fk = 'Received') ");

		if (customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT_SUPERVISOR)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AREA_MANAGER)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_SUPERVISOR)) {

			jpql.append(" AND i.pos_fk IN (" + posIds.toString().replace("]", "").replace("[", "") + ") ");
			jpqlCount.append(" AND i.pos_fk IN (" + posIds.toString().replace("]", "").replace("[", "") + ") ");
		}

		jpql.append(" GROUP BY co.id ORDER BY co.name");
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
			
			String totalTransactionsZawagMuslim = q1[2].toString();
			String totalAmountZawagMuslim = String.format("%.2f", q1[3]);
			
			String totalTransactionsTalak = q1[4].toString();
			String totalAmountTalak = String.format("%.2f", q1[5]);
			
			String totalTransactionsTasadok = q1[6].toString();
			String totalAmountTasadok = String.format("%.2f", q1[7]);
			
			String totalTransactionsMoragaa = q1[8].toString();
			String totalAmountMoragaa = String.format("%.2f", q1[9]);
			
			String totalTransactionsZawagMelly = q1[10].toString();
			String totalAmountZawagMelly = String.format("%.2f", q1[11]);
			
			String cityName = q1[12].toString();

			String locationName = q1[13].toString();
			String sectorName = q1[14].toString();
//			String contractTypeId = q1[15].toString();

//			String bookTypeName = q1[6].toString();
			String sectorId = q1[15].toString();
			String locationId = q1[16].toString();
			String cityId = q1[17].toString();
			String courtName = q1[18].toString();
			
			MaazounBookAuditSectorDTO eMaazounBookAuditSectorDTO = new MaazounBookAuditSectorDTO();

			eMaazounBookAuditSectorDTO.setCityId(cityId);
			eMaazounBookAuditSectorDTO.setCityName(cityName);
			eMaazounBookAuditSectorDTO.setLocationId(locationId);
			eMaazounBookAuditSectorDTO.setLocationName(locationName);
			eMaazounBookAuditSectorDTO.setSectorId(sectorId);
			eMaazounBookAuditSectorDTO.setSectorName(sectorName);
			eMaazounBookAuditSectorDTO.setCourtName(courtName);
			
			eMaazounBookAuditSectorDTO.setBookMoragaaName(StatusConstant.CONTRACT_MORAGAA_NAME);
			eMaazounBookAuditSectorDTO.setBookMoragaaAmount8(Double.valueOf(totalAmountMoragaa));
			eMaazounBookAuditSectorDTO.setBookMoragaaTransactions8(Long.valueOf(totalTransactionsMoragaa));

			eMaazounBookAuditSectorDTO.setBookTalakName(StatusConstant.CONTRACT_TALAK_NAME);
			eMaazounBookAuditSectorDTO.setBookTalakAmount8(Double.valueOf(totalAmountTalak));
			eMaazounBookAuditSectorDTO.setBookTalakTransactions8(Long.valueOf(totalTransactionsTalak));
			
			eMaazounBookAuditSectorDTO.setBookTasadokName(StatusConstant.CONTRACT_TASADOK_NAME);
			eMaazounBookAuditSectorDTO.setBookTasadokAmount8(Double.valueOf(totalAmountTasadok));
			eMaazounBookAuditSectorDTO.setBookTasadokTransactions8(Long.valueOf(totalTransactionsTasadok));
			
			eMaazounBookAuditSectorDTO.setBookZawagMelalyName(StatusConstant.CONTRACT_ZAWAG_MELALY_NAME);
			eMaazounBookAuditSectorDTO.setBookZawagMelalyAmount8(Double.valueOf(totalAmountZawagMelly));
			eMaazounBookAuditSectorDTO.setBookZawagMelalyTransactions8(Long.valueOf(totalTransactionsZawagMelly));
			
			eMaazounBookAuditSectorDTO.setBookZawagMuslimName(StatusConstant.CONTRACT_ZAWAG_MUSLIM_NAME);
			eMaazounBookAuditSectorDTO.setBookZawagMuslimAmount8(Double.valueOf(totalAmountZawagMuslim));
			eMaazounBookAuditSectorDTO.setBookZawagMuslimTransactions8(Long.valueOf(totalTransactionsZawagMuslim));

			totalMaazounBookAuditSectorDTO.setBookMoragaaAmount8(Double.valueOf(
					String.format("%.2f",
					(totalMaazounBookAuditSectorDTO.getBookMoragaaAmount8() + Double.valueOf(totalAmountMoragaa))
					)));
			totalMaazounBookAuditSectorDTO.setBookMoragaaTransactions8(
					totalMaazounBookAuditSectorDTO.getBookMoragaaTransactions8() + Long.valueOf(totalTransactionsMoragaa));
			
			totalMaazounBookAuditSectorDTO.setBookTalakAmount8(Double.valueOf(
					String.format("%.2f",
					(totalMaazounBookAuditSectorDTO.getBookTalakAmount8() + Double.valueOf(totalAmountTalak))
					)));
			totalMaazounBookAuditSectorDTO.setBookTalakTransactions8(
					totalMaazounBookAuditSectorDTO.getBookTalakTransactions8() + Long.valueOf(totalTransactionsTalak));
			
			totalMaazounBookAuditSectorDTO.setBookTasadokAmount8(Double.valueOf(
					String.format("%.2f",
					(totalMaazounBookAuditSectorDTO.getBookTasadokAmount8() + Double.valueOf(totalAmountTasadok))
					)));
			totalMaazounBookAuditSectorDTO.setBookTasadokTransactions8(
					totalMaazounBookAuditSectorDTO.getBookTasadokTransactions8() + Long.valueOf(totalTransactionsTasadok));
			
			totalMaazounBookAuditSectorDTO.setBookZawagMelalyAmount8(Double.valueOf(
					String.format("%.2f", 
							(totalMaazounBookAuditSectorDTO.getBookZawagMelalyAmount8() + Double.valueOf(totalAmountZawagMelly))
							)));
			totalMaazounBookAuditSectorDTO.setBookZawagMelalyTransactions8(
					totalMaazounBookAuditSectorDTO.getBookZawagMelalyTransactions8() + Long.valueOf(totalTransactionsZawagMelly));
			
			totalMaazounBookAuditSectorDTO.setBookZawagMuslimAmount8(Double.valueOf(
					String.format("%.2f", 
							(totalMaazounBookAuditSectorDTO.getBookZawagMuslimAmount8() + Double.valueOf(totalAmountZawagMuslim))
							)));
			totalMaazounBookAuditSectorDTO.setBookZawagMuslimTransactions8(
					totalMaazounBookAuditSectorDTO.getBookZawagMuslimTransactions8() + Long.valueOf(totalTransactionsZawagMuslim));
			
			result.add(eMaazounBookAuditSectorDTO);
			
		}

		result.add(totalMaazounBookAuditSectorDTO);

		return new PageImpl<MaazounBookAuditSectorDTO>(new ArrayList<>(result), pageable, total);
	}

	
	private void accumalteObject(MaazounContractAuditDTO totalMaazounContractAuditDTO, MaazounContractAuditDTO maazounContractAuditDTO) {
		
		Long sumTotalTransaction = Long.valueOf(totalMaazounContractAuditDTO.getTotalTransaction()) + 
				Long.valueOf(maazounContractAuditDTO.getTotalTransaction());
		totalMaazounContractAuditDTO.setTotalTransaction(String.valueOf(sumTotalTransaction));
		Long sumTotalCollectionTransaction = Long.valueOf(totalMaazounContractAuditDTO.getTotalCollectionTransaction()) + 
				Long.valueOf(maazounContractAuditDTO.getTotalCollectionTransaction());
		totalMaazounContractAuditDTO.setTotalCollectionTransaction(String.valueOf(sumTotalCollectionTransaction));
		Long sumTotalSettlementTransaction = Long.valueOf(totalMaazounContractAuditDTO.getTotalSettlementTransaction()) + 
				Long.valueOf(maazounContractAuditDTO.getTotalSettlementTransaction());
		totalMaazounContractAuditDTO.setTotalSettlementTransaction(String.valueOf(sumTotalSettlementTransaction));
		Long sumTotalRefundTransaction = Long.valueOf(totalMaazounContractAuditDTO.getTotalRefundTransaction()) + 
				Long.valueOf(maazounContractAuditDTO.getTotalRefundTransaction());
		totalMaazounContractAuditDTO.setTotalRefundTransaction(String.valueOf(sumTotalRefundTransaction));
		Long sumTotalCancelledTransaction = Long.valueOf(totalMaazounContractAuditDTO.getTotalCancelledTransaction()) + 
				Long.valueOf(maazounContractAuditDTO.getTotalCancelledTransaction());
		totalMaazounContractAuditDTO.setTotalCancelledTransaction(String.valueOf(sumTotalCancelledTransaction));
		
		Double sumSystemTotalCollectionAmount = Double.valueOf(totalMaazounContractAuditDTO.getSystemTotalCollectionAmount()) + 
				Double.valueOf(maazounContractAuditDTO.getSystemTotalCollectionAmount());
		totalMaazounContractAuditDTO.setSystemTotalCollectionAmount(String.format("%.2f", sumSystemTotalCollectionAmount));
		Double sumSystemTotalSettlementAmount = Double.valueOf(totalMaazounContractAuditDTO.getSystemTotalSettlementAmount()) + 
				Double.valueOf(maazounContractAuditDTO.getSystemTotalSettlementAmount());
		totalMaazounContractAuditDTO.setSystemTotalSettlementAmount(String.format("%.2f", sumSystemTotalSettlementAmount));
		Double sumSystemTotalAmountRefund = Double.valueOf(totalMaazounContractAuditDTO.getSystemTotalAmountRefund()) + 
				Double.valueOf(maazounContractAuditDTO.getSystemTotalAmountRefund());
		totalMaazounContractAuditDTO.setSystemTotalAmountRefund(String.format("%.2f", sumSystemTotalAmountRefund));
		
		Double sumSystemTotalAmountCash = Double.valueOf(totalMaazounContractAuditDTO.getSystemTotalAmountCash()) + 
				Double.valueOf(maazounContractAuditDTO.getSystemTotalAmountCash());
		totalMaazounContractAuditDTO.setSystemTotalAmountCash(String.format("%.2f", sumSystemTotalAmountCash));
		Double sumSystemTotalAmountVisa = Double.valueOf(totalMaazounContractAuditDTO.getSystemTotalAmountVisa()) + 
				Double.valueOf(maazounContractAuditDTO.getSystemTotalAmountVisa());
		totalMaazounContractAuditDTO.setSystemTotalAmountVisa(String.format("%.2f", sumSystemTotalAmountVisa));
		
		Long sumTotalHoldTransaction = Long.valueOf(totalMaazounContractAuditDTO.getTotalHoldTransaction()) + 
				Long.valueOf(maazounContractAuditDTO.getTotalHoldTransaction());
		totalMaazounContractAuditDTO.setTotalHoldTransaction(String.valueOf(sumTotalHoldTransaction));
		Long sumTotalFinalTransaction = Long.valueOf(totalMaazounContractAuditDTO.getTotalFinalTransaction()) + 
				Long.valueOf(maazounContractAuditDTO.getTotalFinalTransaction());
		totalMaazounContractAuditDTO.setTotalFinalTransaction(String.valueOf(sumTotalFinalTransaction));
		Long sumTotalSynchedTransaction = Long.valueOf(totalMaazounContractAuditDTO.getTotalSynchedTransaction()) + 
				Long.valueOf(maazounContractAuditDTO.getTotalSynchedTransaction());
		totalMaazounContractAuditDTO.setTotalSynchedTransaction(String.valueOf(sumTotalSynchedTransaction));
		Long sumTotalMatchedTransaction = Long.valueOf(totalMaazounContractAuditDTO.getTotalMatchedTransaction()) + 
				Long.valueOf(maazounContractAuditDTO.getTotalMatchedTransaction());
		totalMaazounContractAuditDTO.setTotalMatchedTransaction(String.valueOf(sumTotalMatchedTransaction));
		
	}

}
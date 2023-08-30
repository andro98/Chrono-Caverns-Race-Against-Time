package com.aman.payment.core.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.aman.payment.core.model.PullAccount;
import com.aman.payment.core.model.dto.FinancialAuditDTO;
import com.aman.payment.core.model.payload.ApprovedClaimsSearchRequest;
import com.aman.payment.core.model.payload.FinancialReportRequest;
import com.aman.payment.maazoun.model.dto.MaazounBookAuditSectorDTO;

@Repository
public class PullAccountCustomRepositoryImpl implements PullAccountCustomRepository{

	@PersistenceContext
	private EntityManager em;

	public PullAccountCustomRepositoryImpl(EntityManager em) {
		super();
		this.em = em;
	}

	@Override
	public Page<FinancialAuditDTO> getAuditReportByDaily(FinancialReportRequest financialReportRequest) {
		Pageable pageable = PageRequest.of(Integer.valueOf(financialReportRequest.getPageNo()), 
				Integer.valueOf(financialReportRequest.getPageSize()));

		List<FinancialAuditDTO> result = new ArrayList<FinancialAuditDTO>();
		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();
		
		String selectQuery = "SELECT"
				+ " SUM(v.deposit_total_amount) as totalAmount," 
				+ " SUM(v.deposit_cash) as totalAmountCash,"
				+ " SUM(v.deposit_visa) as totalAmountVisa,"
				+ " SUM(v.deficit_amount) as totalAmountDeficit," 
				+ " SUM(v.over_amount) as totalAmountOver,"
				+ " SUM(v.system_total_collection_amount) as systemTotalCollectionAmount,"
				+ " SUM(v.system_total_settlement_amount) as systemTotalSettlementAmount,"
				+ " SUM(v.system_amount_refund) as systemTotalAmountRefund,"
				+ " SUM(v.system_amount_cash) as systemTotalAmountCash,"
				+ " SUM(v.system_amount_visa) as systemTotalAmountVisa,"
				+ " SUM(v.total_cancelled_transaction) as totalCancelledTransactions,"
				+ " SUM(v.total_settlement_transaction) as totalSettlementTransaction,"
				+ " SUM(v.total_collection_transaction) as totalCollectionTransaction,"
				+ " SUM(v.total_refund_transaction) as totalRefundTransaction,"
				+ " SUM(v.deficit_amount) as deficitAmount,"
				+ " SUM(v.over_amount) as overAmount,"
				+ " DATE_FORMAT(v.created_at, '%Y/%m/%d') as createDate,"
				+ " v.service_id as serviceId,"
				+ " SUM(v.total_transaction) as totalTransaction"
				+ " FROM pull_account v"
				+ " WHERE v.created_at >= '" + financialReportRequest.getDurationFrom() + " 00:00:00' "
				+ " AND v.created_at <= '" + financialReportRequest.getDurationTo() + " 23:59:59' "
				+ " AND (v.status_fk = 'Approved' OR v.status_fk = 'Pending') ";
		jpql.append(selectQuery);
		
		String countQuery = "SELECT COUNT(*) "
				+ " FROM pull_account v"
				+ " WHERE v.created_at >= '" + financialReportRequest.getDurationFrom() + " 00:00:00' "
				+ " AND v.created_at <= '" + financialReportRequest.getDurationTo() + " 23:59:59' "
				+ " AND (v.status_fk = 'Approved' OR v.status_fk = 'Pending') ";
		jpqlCount.append(countQuery);
		
		if(financialReportRequest.getServiceId() != null && !financialReportRequest.getServiceId().isEmpty()) {
			jpql.append(" AND v.service_id = "+financialReportRequest.getServiceId()+" ");
			jpqlCount.append(" AND v.service_id = "+financialReportRequest.getServiceId()+" ");
		}
		
		jpql.append(" GROUP BY date(v.created_at), v.service_id ORDER BY v.created_at desc");
		jpqlCount.append(" GROUP BY date(v.created_at), v.service_id");
		
		Query query = em.createNativeQuery(jpql.toString());
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Query queryCount = em.createNativeQuery(jpqlCount.toString());
		long total = queryCount.getResultList().size();
		
		List<Object[]> list = query.getResultList();
		FinancialAuditDTO totalFinancialAuditDTO = new FinancialAuditDTO();
		totalFinancialAuditDTO.setCreateDate("الإجمالى");
		totalFinancialAuditDTO.setTotalCancelledTransaction(String.valueOf(0));
		totalFinancialAuditDTO.setTotalCollectionTransaction(String.valueOf(0));
		totalFinancialAuditDTO.setTotalRefundTransaction(String.valueOf(0));
		totalFinancialAuditDTO.setTotalSettlementTransaction(String.valueOf(0));
		totalFinancialAuditDTO.setTotalTransaction(String.valueOf(0));
		totalFinancialAuditDTO.setTotalAmount(String.valueOf(0));
		totalFinancialAuditDTO.setTotalAmountCash(String.valueOf(0));
		totalFinancialAuditDTO.setTotalAmountVisa(String.valueOf(0));
		totalFinancialAuditDTO.setTotalAmountDeficit(String.valueOf(0));
		totalFinancialAuditDTO.setTotalAmountOver(String.valueOf(0));
		totalFinancialAuditDTO.setSystemTotalCollectionAmount(String.valueOf(0));
		totalFinancialAuditDTO.setSystemTotalSettlementAmount(String.valueOf(0));
		totalFinancialAuditDTO.setSystemTotalAmountRefund(String.valueOf(0));
		totalFinancialAuditDTO.setSystemTotalAmountCash(String.valueOf(0));
		totalFinancialAuditDTO.setSystemTotalAmountVisa(String.valueOf(0));
		
	    for(Object[] q1 : list){

	    	String totalAmount = String.format("%.2f", q1[0]);
	    	String totalAmountCash = String.format("%.2f", q1[1]);
	        String totalAmountVisa = String.format("%.2f", q1[2]);
	        String totalAmountDeficit = String.format("%.2f", q1[3]);
	        String totalAmountOver = String.format("%.2f", q1[4]);
	        String systemTotalCollectionAmount = String.format("%.2f", q1[5]);
	        String systemTotalSettlementAmount = String.format("%.2f", q1[6]);
	        String systemTotalAmountRefund = String.format("%.2f", q1[7]);
	        String systemTotalAmountCash = String.format("%.2f", q1[8]);
	        String systemTotalAmountVisa = String.format("%.2f", q1[9]);
	        String totalCancelledTransactions = q1[10].toString();
	        String totalSettlementTransaction = q1[11].toString();
	        String totalCollectionTransaction = q1[12].toString();
	        String totalRefundTransaction = q1[13].toString();
	        String deficitAmount = String.format("%.2f", q1[14]);
	        String overAmount = String.format("%.2f", q1[15]);
	        String createDate = q1[16].toString();
	        String serviceId = q1[17].toString();
	        String totalTransaction = q1[18].toString();
	        

	        FinancialAuditDTO tFinancialAuditDTO = new FinancialAuditDTO();
	        tFinancialAuditDTO.setTotalAmount(totalAmount);
	        tFinancialAuditDTO.setTotalAmountCash(totalAmountCash);
	        tFinancialAuditDTO.setTotalAmountVisa(totalAmountVisa);
	        tFinancialAuditDTO.setTotalAmountDeficit(totalAmountDeficit);
	        tFinancialAuditDTO.setTotalAmountOver(totalAmountOver);
	        tFinancialAuditDTO.setSystemTotalCollectionAmount(systemTotalCollectionAmount);
	        tFinancialAuditDTO.setSystemTotalSettlementAmount(systemTotalSettlementAmount);
	        tFinancialAuditDTO.setSystemTotalAmountRefund(systemTotalAmountRefund);
	        tFinancialAuditDTO.setSystemTotalAmountCash(systemTotalAmountCash);
	        tFinancialAuditDTO.setSystemTotalAmountVisa(systemTotalAmountVisa);
	        tFinancialAuditDTO.setTotalCancelledTransaction(totalCancelledTransactions);
	        tFinancialAuditDTO.setTotalSettlementTransaction(totalSettlementTransaction);
	        tFinancialAuditDTO.setTotalCollectionTransaction(totalCollectionTransaction);
	        tFinancialAuditDTO.setTotalRefundTransaction(totalRefundTransaction);
	        tFinancialAuditDTO.setCreateDate(createDate);
	        tFinancialAuditDTO.setServiceId(serviceId);
	        if(deficitAmount != null && Double.valueOf(deficitAmount) > 0) {
	        	tFinancialAuditDTO.setOverOrDeficitAmount("-"+deficitAmount);
			}else if(overAmount != null && Double.valueOf(overAmount) > 0){
				tFinancialAuditDTO.setOverOrDeficitAmount("+"+overAmount);
			}
	        tFinancialAuditDTO.setTotalTransaction(totalTransaction);
	        
	        long sumTotalCancelledTransaction = Long.valueOf(totalFinancialAuditDTO.getTotalCancelledTransaction()) + 
					Long.valueOf(totalCancelledTransactions);
	        totalFinancialAuditDTO.setTotalCancelledTransaction(String.valueOf(sumTotalCancelledTransaction));
			
	        long sumTotalCollectionTransaction = Long.valueOf(totalFinancialAuditDTO.getTotalCollectionTransaction()) + 
					Long.valueOf(totalCollectionTransaction);
	        totalFinancialAuditDTO.setTotalCollectionTransaction(String.valueOf(sumTotalCollectionTransaction));
			
			long sumTotalRefundTransaction = Long.valueOf(totalFinancialAuditDTO.getTotalRefundTransaction()) + 
					Long.valueOf(totalRefundTransaction);
			totalFinancialAuditDTO.setTotalRefundTransaction(String.valueOf(sumTotalRefundTransaction));
			
			long sumTotalSettlementTransaction = Long.valueOf(totalFinancialAuditDTO.getTotalSettlementTransaction()) + 
					Long.valueOf(totalSettlementTransaction);
			totalFinancialAuditDTO.setTotalSettlementTransaction(String.valueOf(sumTotalSettlementTransaction));
			
			long sumTotalTransaction = Long.valueOf(totalFinancialAuditDTO.getTotalTransaction()) + 
					Long.valueOf(totalTransaction);
			totalFinancialAuditDTO.setTotalTransaction(String.valueOf(sumTotalTransaction));
			
			double sumTotalAmount = Double.valueOf(totalFinancialAuditDTO.getTotalAmount()) + 
					Double.valueOf(totalAmount);
			totalFinancialAuditDTO.setTotalAmount(String.format("%.2f", sumTotalAmount));
			
			double sumTotalAmountCash = Double.valueOf(totalFinancialAuditDTO.getTotalAmountCash()) + 
					Double.valueOf(totalAmountCash);
			totalFinancialAuditDTO.setTotalAmountCash(String.format("%.2f", sumTotalAmountCash));
			
			double sumTotalAmountVisa = Double.valueOf(totalFinancialAuditDTO.getTotalAmountVisa()) + 
					Double.valueOf(totalAmountVisa);
			totalFinancialAuditDTO.setTotalAmountVisa(String.format("%.2f", sumTotalAmountVisa));
			
			double sumTotalAmountDificit = Double.valueOf(totalFinancialAuditDTO.getTotalAmountDeficit()) + 
					Double.valueOf(totalAmountDeficit);
			totalFinancialAuditDTO.setTotalAmountDeficit(String.format("%.2f", sumTotalAmountDificit));
			
			double sumTotalAmountOver = Double.valueOf(totalFinancialAuditDTO.getTotalAmountOver()) + 
					Double.valueOf(totalAmountOver);
			totalFinancialAuditDTO.setTotalAmountOver(String.format("%.2f", sumTotalAmountOver));
			
			double sumSystemTotalCollectionAmount = Double.valueOf(totalFinancialAuditDTO.getSystemTotalCollectionAmount()) + 
					Double.valueOf(systemTotalCollectionAmount);
			totalFinancialAuditDTO.setSystemTotalCollectionAmount(String.format("%.2f", sumSystemTotalCollectionAmount));
			
			double sumSystemTotalSettlementAmount = Double.valueOf(totalFinancialAuditDTO.getSystemTotalSettlementAmount()) + 
					Double.valueOf(systemTotalSettlementAmount);
			totalFinancialAuditDTO.setSystemTotalSettlementAmount(String.format("%.2f", sumSystemTotalSettlementAmount));
			
			double sumSystemTotalAmountRefund = Double.valueOf(totalFinancialAuditDTO.getSystemTotalAmountRefund()) + 
					Double.valueOf(systemTotalAmountRefund);
			totalFinancialAuditDTO.setSystemTotalAmountRefund(String.format("%.2f", sumSystemTotalAmountRefund));
			
			double sumSystemTotalAmountCash = Double.valueOf(totalFinancialAuditDTO.getSystemTotalAmountCash()) + 
					Double.valueOf(systemTotalAmountCash);
			totalFinancialAuditDTO.setSystemTotalAmountCash(String.format("%.2f", sumSystemTotalAmountCash));
			
			double sumSystemTotalAmountVisa = Double.valueOf(totalFinancialAuditDTO.getSystemTotalAmountVisa()) + 
					Double.valueOf(systemTotalAmountVisa);
			totalFinancialAuditDTO.setSystemTotalAmountVisa(String.format("%.2f", sumSystemTotalAmountVisa));
	        
	        result.add(tFinancialAuditDTO);	      
	        
	     }
	    
	    result.add(totalFinancialAuditDTO);	 

	    return new PageImpl<FinancialAuditDTO>(result, pageable, total);
	}

	@Override
	public Page<FinancialAuditDTO> getAuditReportByLocation(FinancialReportRequest financialReportRequest) {
		Pageable pageable = PageRequest.of(Integer.valueOf(financialReportRequest.getPageNo()), 
				Integer.valueOf(financialReportRequest.getPageSize()));
		
		List<FinancialAuditDTO> result = new ArrayList<FinancialAuditDTO>();
		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();
		
		String selectQuery = "SELECT"
				+ " SUM(v.deposit_total_amount) as totalAmount," 
				+ " SUM(v.deposit_cash) as totalAmountCash,"
				+ " SUM(v.deposit_visa) as totalAmountVisa,"
				+ " SUM(v.deficit_amount) as totalAmountDeficit," 
				+ " SUM(v.over_amount) as totalAmountOver,"
				+ " SUM(v.system_total_collection_amount) as systemTotalCollectionAmount,"
				+ " SUM(v.system_total_settlement_amount) as systemTotalSettlementAmount,"
				+ " SUM(v.system_amount_refund) as systemTotalAmountRefund,"
				+ " SUM(v.system_amount_cash) as systemTotalAmountCash,"
				+ " SUM(v.system_amount_visa) as systemTotalAmountVisa,"
				+ " SUM(v.total_cancelled_transaction) as totalCancelledTransactions,"
				+ " SUM(v.total_settlement_transaction) as totalSettlementTransaction,"
				+ " SUM(v.total_collection_transaction) as totalCollectionTransaction,"
				+ " SUM(v.total_refund_transaction) as totalRefundTransaction,"
				+ " SUM(v.deficit_amount) as deficitAmount,"
				+ " SUM(v.over_amount) as overAmount,"
				+ " v.location_name as locationName,"
				+ " v.location_code as locationCode,"
				+ " v.service_id as serviceId,"
				+ " SUM(v.total_transaction) as totalTransaction, s.name as sectorName, c.name as courtName"
				+ " FROM pull_account v"
				+ " JOIN sector s ON s.location_fk = v.location_code"
				+ " JOIN courts c ON s.court_fk = c.id"
				+ " WHERE v.created_at >= '" + financialReportRequest.getDurationFrom() + " 00:00:00' "
				+ " AND v.created_at <= '" + financialReportRequest.getDurationTo() + " 23:59:59'"
				+ " AND (v.status_fk = 'Approved' OR v.status_fk = 'Pending') ";
		jpql.append(selectQuery);
		
		String countQuery = "SELECT COUNT(*)"
				+ " FROM pull_account v"
				+ " JOIN sector s ON s.location_fk = v.location_code"
				+ " JOIN courts c ON s.court_fk = c.id"
				+ " WHERE v.created_at >= '" + financialReportRequest.getDurationFrom() + " 00:00:00' "
				+ " AND v.created_at <= '" + financialReportRequest.getDurationTo() + " 23:59:59' "
				+ " AND (v.status_fk = 'Approved' OR v.status_fk = 'Pending') ";
		jpqlCount.append(countQuery);
		
		if(financialReportRequest.getServiceId() != null && !financialReportRequest.getServiceId().isEmpty()) {
			jpql.append(" AND v.service_id = "+financialReportRequest.getServiceId()+" ");
			jpqlCount.append(" AND v.service_id = "+financialReportRequest.getServiceId()+" ");
		}
		
		jpql.append(" GROUP BY v.location_code, v.service_id ORDER BY v.created_at desc");
		jpqlCount.append(" GROUP BY v.location_code, v.service_id");
		
		System.out.println("getAuditReportByLocation = "+jpql.toString());
		
		Query query = em.createNativeQuery(jpql.toString());
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Query queryCount = em.createNativeQuery(jpqlCount.toString());
		long total = queryCount.getResultList().size();
		
		List<Object[]> list = query.getResultList();
		FinancialAuditDTO totalFinancialAuditDTO = new FinancialAuditDTO();
		totalFinancialAuditDTO.setLocationName("الإجمالى");
		totalFinancialAuditDTO.setTotalCancelledTransaction(String.valueOf(0));
		totalFinancialAuditDTO.setTotalCollectionTransaction(String.valueOf(0));
		totalFinancialAuditDTO.setTotalRefundTransaction(String.valueOf(0));
		totalFinancialAuditDTO.setTotalSettlementTransaction(String.valueOf(0));
		totalFinancialAuditDTO.setTotalTransaction(String.valueOf(0));
		totalFinancialAuditDTO.setTotalAmount(String.valueOf(0));
		totalFinancialAuditDTO.setTotalAmountCash(String.valueOf(0));
		totalFinancialAuditDTO.setTotalAmountVisa(String.valueOf(0));
		totalFinancialAuditDTO.setTotalAmountDeficit(String.valueOf(0));
		totalFinancialAuditDTO.setTotalAmountOver(String.valueOf(0));
		totalFinancialAuditDTO.setSystemTotalCollectionAmount(String.valueOf(0));
		totalFinancialAuditDTO.setSystemTotalSettlementAmount(String.valueOf(0));
		totalFinancialAuditDTO.setSystemTotalAmountRefund(String.valueOf(0));
		totalFinancialAuditDTO.setSystemTotalAmountCash(String.valueOf(0));
		totalFinancialAuditDTO.setSystemTotalAmountVisa(String.valueOf(0));
		
	    for(Object[] q1 : list){

	    	String totalAmount = String.format("%.2f", q1[0]);
	    	String totalAmountCash = String.format("%.2f", q1[1]);
	        String totalAmountVisa = String.format("%.2f", q1[2]);
	        String totalAmountDeficit = String.format("%.2f", q1[3]);
	        String totalAmountOver = String.format("%.2f", q1[4]);
	        String systemTotalCollectionAmount = String.format("%.2f", q1[5]);
	        String systemTotalSettlementAmount = String.format("%.2f", q1[6]);
	        String systemTotalAmountRefund = String.format("%.2f", q1[7]);
	        String systemTotalAmountCash = String.format("%.2f", q1[8]);
	        String systemTotalAmountVisa = String.format("%.2f", q1[9]);
	        String totalCancelledTransactions = q1[10].toString();
	        String totalSettlementTransaction = q1[11].toString();
	        String totalCollectionTransaction = q1[12].toString();
	        String totalRefundTransaction = q1[13].toString();
	        String deficitAmount = String.format("%.2f", q1[14]);
	        String overAmount = String.format("%.2f", q1[15]);
	        String locationName = q1[16].toString();
	        String locationCode = q1[17].toString();
	        String serviceId = q1[18].toString();
	        String totalTransaction = q1[19].toString();
	        String sectorName = q1[20].toString();
	        String courtName = q1[21].toString();

	        FinancialAuditDTO tFinancialAuditDTO = new FinancialAuditDTO();
	        tFinancialAuditDTO.setTotalAmount(totalAmount);
	        tFinancialAuditDTO.setTotalAmountCash(totalAmountCash);
	        tFinancialAuditDTO.setTotalAmountVisa(totalAmountVisa);
	        tFinancialAuditDTO.setTotalAmountDeficit(totalAmountDeficit);
	        tFinancialAuditDTO.setTotalAmountOver(totalAmountOver);
	        tFinancialAuditDTO.setSystemTotalCollectionAmount(systemTotalCollectionAmount);
	        tFinancialAuditDTO.setSystemTotalSettlementAmount(systemTotalSettlementAmount);
	        tFinancialAuditDTO.setSystemTotalAmountRefund(systemTotalAmountRefund);
	        tFinancialAuditDTO.setSystemTotalAmountCash(systemTotalAmountCash);
	        tFinancialAuditDTO.setSystemTotalAmountVisa(systemTotalAmountVisa);
	        tFinancialAuditDTO.setTotalCancelledTransaction(totalCancelledTransactions);
	        tFinancialAuditDTO.setTotalSettlementTransaction(totalSettlementTransaction);
	        tFinancialAuditDTO.setTotalCollectionTransaction(totalCollectionTransaction);
	        tFinancialAuditDTO.setTotalRefundTransaction(totalRefundTransaction);
	        tFinancialAuditDTO.setLocationName(locationName);
	        tFinancialAuditDTO.setLocationCode(locationCode); 
	        tFinancialAuditDTO.setServiceId(serviceId);
	        tFinancialAuditDTO.setSectorName(sectorName);
	        tFinancialAuditDTO.setCourtName(courtName);
	        if(deficitAmount != null && Double.valueOf(deficitAmount) > 0) {
	        	tFinancialAuditDTO.setOverOrDeficitAmount("-"+deficitAmount);
			}else if(overAmount != null && Double.valueOf(overAmount) > 0){
				tFinancialAuditDTO.setOverOrDeficitAmount("+"+overAmount);
			}
	        tFinancialAuditDTO.setTotalTransaction(totalTransaction);
	        
	        long sumTotalCancelledTransaction = Long.valueOf(totalFinancialAuditDTO.getTotalCancelledTransaction()) + 
					Long.valueOf(totalCancelledTransactions);
	        totalFinancialAuditDTO.setTotalCancelledTransaction(String.valueOf(sumTotalCancelledTransaction));
			
	        long sumTotalCollectionTransaction = Long.valueOf(totalFinancialAuditDTO.getTotalCollectionTransaction()) + 
					Long.valueOf(totalCollectionTransaction);
	        totalFinancialAuditDTO.setTotalCollectionTransaction(String.valueOf(sumTotalCollectionTransaction));
			
			long sumTotalRefundTransaction = Long.valueOf(totalFinancialAuditDTO.getTotalRefundTransaction()) + 
					Long.valueOf(totalRefundTransaction);
			totalFinancialAuditDTO.setTotalRefundTransaction(String.valueOf(sumTotalRefundTransaction));
			
			long sumTotalSettlementTransaction = Long.valueOf(totalFinancialAuditDTO.getTotalSettlementTransaction()) + 
					Long.valueOf(totalSettlementTransaction);
			totalFinancialAuditDTO.setTotalSettlementTransaction(String.valueOf(sumTotalSettlementTransaction));
			
			long sumTotalTransaction = Long.valueOf(totalFinancialAuditDTO.getTotalTransaction()) + 
					Long.valueOf(totalTransaction);
			totalFinancialAuditDTO.setTotalTransaction(String.valueOf(sumTotalTransaction));
			
			double sumTotalAmount = Double.valueOf(totalFinancialAuditDTO.getTotalAmount()) + 
					Double.valueOf(totalAmount);
			totalFinancialAuditDTO.setTotalAmount(String.format("%.2f", sumTotalAmount));
			
			double sumTotalAmountCash = Double.valueOf(totalFinancialAuditDTO.getTotalAmountCash()) + 
					Double.valueOf(totalAmountCash);
			totalFinancialAuditDTO.setTotalAmountCash(String.format("%.2f", sumTotalAmountCash));
			
			double sumTotalAmountVisa = Double.valueOf(totalFinancialAuditDTO.getTotalAmountVisa()) + 
					Double.valueOf(totalAmountVisa);
			totalFinancialAuditDTO.setTotalAmountVisa(String.format("%.2f", sumTotalAmountVisa));
			
			double sumTotalAmountDificit = Double.valueOf(totalFinancialAuditDTO.getTotalAmountDeficit()) + 
					Double.valueOf(totalAmountDeficit);
			totalFinancialAuditDTO.setTotalAmountDeficit(String.format("%.2f", sumTotalAmountDificit));
			
			double sumTotalAmountOver = Double.valueOf(totalFinancialAuditDTO.getTotalAmountOver()) + 
					Double.valueOf(totalAmountOver);
			totalFinancialAuditDTO.setTotalAmountOver(String.format("%.2f", sumTotalAmountOver));
			
			double sumSystemTotalCollectionAmount = Double.valueOf(totalFinancialAuditDTO.getSystemTotalCollectionAmount()) + 
					Double.valueOf(systemTotalCollectionAmount);
			totalFinancialAuditDTO.setSystemTotalCollectionAmount(String.format("%.2f", sumSystemTotalCollectionAmount));
			
			double sumSystemTotalSettlementAmount = Double.valueOf(totalFinancialAuditDTO.getSystemTotalSettlementAmount()) + 
					Double.valueOf(systemTotalSettlementAmount);
			totalFinancialAuditDTO.setSystemTotalSettlementAmount(String.format("%.2f", sumSystemTotalSettlementAmount));
			
			double sumSystemTotalAmountRefund = Double.valueOf(totalFinancialAuditDTO.getSystemTotalAmountRefund()) + 
					Double.valueOf(systemTotalAmountRefund);
			totalFinancialAuditDTO.setSystemTotalAmountRefund(String.format("%.2f", sumSystemTotalAmountRefund));
			
			double sumSystemTotalAmountCash = Double.valueOf(totalFinancialAuditDTO.getSystemTotalAmountCash()) + 
					Double.valueOf(systemTotalAmountCash);
			totalFinancialAuditDTO.setSystemTotalAmountCash(String.format("%.2f", sumSystemTotalAmountCash));
			
			double sumSystemTotalAmountVisa = Double.valueOf(totalFinancialAuditDTO.getSystemTotalAmountVisa()) + 
					Double.valueOf(systemTotalAmountVisa);
			totalFinancialAuditDTO.setSystemTotalAmountVisa(String.format("%.2f", sumSystemTotalAmountVisa));
			
	        result.add(tFinancialAuditDTO);
	        
	     }

	    result.add(totalFinancialAuditDTO);
	    
	    return new PageImpl<FinancialAuditDTO>(result, pageable, total);
	}
	
	@Override
	public Page<FinancialAuditDTO> getAuditReportByCourt(FinancialReportRequest financialReportRequest) {
		Pageable pageable = PageRequest.of(Integer.valueOf(financialReportRequest.getPageNo()), 
				Integer.valueOf(financialReportRequest.getPageSize()));
		
		List<FinancialAuditDTO> result = new ArrayList<FinancialAuditDTO>();
		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();
		
		String selectQuery = "SELECT"
				+ " SUM(v.deposit_total_amount) as totalAmount," 
				+ " SUM(v.deposit_cash) as totalAmountCash,"
				+ " SUM(v.deposit_visa) as totalAmountVisa,"
				+ " SUM(v.deficit_amount) as totalAmountDeficit," 
				+ " SUM(v.over_amount) as totalAmountOver,"
				+ " SUM(v.system_total_collection_amount) as systemTotalCollectionAmount,"
				+ " SUM(v.system_total_settlement_amount) as systemTotalSettlementAmount,"
				+ " SUM(v.system_amount_refund) as systemTotalAmountRefund,"
				+ " SUM(v.system_amount_cash) as systemTotalAmountCash,"
				+ " SUM(v.system_amount_visa) as systemTotalAmountVisa,"
				+ " SUM(v.total_cancelled_transaction) as totalCancelledTransactions,"
				+ " SUM(v.total_settlement_transaction) as totalSettlementTransaction,"
				+ " SUM(v.total_collection_transaction) as totalCollectionTransaction,"
				+ " SUM(v.total_refund_transaction) as totalRefundTransaction,"
				+ " SUM(v.deficit_amount) as deficitAmount,"
				+ " SUM(v.over_amount) as overAmount,"
				+ " v.location_name as locationName,"
				+ " v.location_code as locationCode,"
				+ " v.service_id as serviceId,"
				+ " SUM(v.total_transaction) as totalTransaction, latest_sector.name as sectorName, c.name as courtName"
				+ " FROM pull_account v"
				+ " JOIN (SELECT p.name, p.location_fk, p.court_fk, MAX(p.id) AS max_id"
				+ " FROM sector p"
				+ " GROUP BY p.location_fk) latest_sector ON v.location_code = latest_sector.location_fk"
				+ " JOIN courts c ON latest_sector.court_fk = c.id"
				+ " WHERE v.created_at >= '" + financialReportRequest.getDurationFrom() + " 00:00:00' "
				+ " AND v.created_at <= '" + financialReportRequest.getDurationTo() + " 23:59:59'"
				+ " AND (v.status_fk = 'Approved' OR v.status_fk = 'Pending') ";
		jpql.append(selectQuery);
		
		String countQuery = "SELECT COUNT(*)"
				+ " FROM pull_account v"
				+ " JOIN (SELECT p.name, p.location_fk, p.court_fk, MAX(p.id) AS max_id"
				+ " FROM sector p"
				+ " GROUP BY p.location_fk) latest_sector ON v.location_code = latest_sector.location_fk"
				+ " JOIN courts c ON latest_sector.court_fk = c.id"
				+ " WHERE v.created_at >= '" + financialReportRequest.getDurationFrom() + " 00:00:00' "
				+ " AND v.created_at <= '" + financialReportRequest.getDurationTo() + " 23:59:59' "
				+ " AND (v.status_fk = 'Approved' OR v.status_fk = 'Pending') ";
		jpqlCount.append(countQuery);
		
		if(financialReportRequest.getServiceId() != null && !financialReportRequest.getServiceId().isEmpty()) {
			jpql.append(" AND v.service_id = "+financialReportRequest.getServiceId()+" ");
			jpqlCount.append(" AND v.service_id = "+financialReportRequest.getServiceId()+" ");
		}
		
		jpql.append(" GROUP BY c.name, v.service_id ORDER BY v.created_at desc");
		jpqlCount.append(" GROUP BY c.name, v.service_id");
		
		System.out.println("getAuditReportByCourt = "+jpql.toString());
		
		Query query = em.createNativeQuery(jpql.toString());
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Query queryCount = em.createNativeQuery(jpqlCount.toString());
		long total = queryCount.getResultList().size();
		
		List<Object[]> list = query.getResultList();
		FinancialAuditDTO totalFinancialAuditDTO = new FinancialAuditDTO();
		totalFinancialAuditDTO.setCourtName("الإجمالى");
		totalFinancialAuditDTO.setTotalCancelledTransaction(String.valueOf(0));
		totalFinancialAuditDTO.setTotalCollectionTransaction(String.valueOf(0));
		totalFinancialAuditDTO.setTotalRefundTransaction(String.valueOf(0));
		totalFinancialAuditDTO.setTotalSettlementTransaction(String.valueOf(0));
		totalFinancialAuditDTO.setTotalTransaction(String.valueOf(0));
		totalFinancialAuditDTO.setTotalAmount(String.valueOf(0));
		totalFinancialAuditDTO.setTotalAmountCash(String.valueOf(0));
		totalFinancialAuditDTO.setTotalAmountVisa(String.valueOf(0));
		totalFinancialAuditDTO.setTotalAmountDeficit(String.valueOf(0));
		totalFinancialAuditDTO.setTotalAmountOver(String.valueOf(0));
		totalFinancialAuditDTO.setSystemTotalCollectionAmount(String.valueOf(0));
		totalFinancialAuditDTO.setSystemTotalSettlementAmount(String.valueOf(0));
		totalFinancialAuditDTO.setSystemTotalAmountRefund(String.valueOf(0));
		totalFinancialAuditDTO.setSystemTotalAmountCash(String.valueOf(0));
		totalFinancialAuditDTO.setSystemTotalAmountVisa(String.valueOf(0));
		
	    for(Object[] q1 : list){

	    	String totalAmount = String.format("%.2f", q1[0]);
	    	String totalAmountCash = String.format("%.2f", q1[1]);
	        String totalAmountVisa = String.format("%.2f", q1[2]);
	        String totalAmountDeficit = String.format("%.2f", q1[3]);
	        String totalAmountOver = String.format("%.2f", q1[4]);
	        String systemTotalCollectionAmount = String.format("%.2f", q1[5]);
	        String systemTotalSettlementAmount = String.format("%.2f", q1[6]);
	        String systemTotalAmountRefund = String.format("%.2f", q1[7]);
	        String systemTotalAmountCash = String.format("%.2f", q1[8]);
	        String systemTotalAmountVisa = String.format("%.2f", q1[9]);
	        String totalCancelledTransactions = q1[10].toString();
	        String totalSettlementTransaction = q1[11].toString();
	        String totalCollectionTransaction = q1[12].toString();
	        String totalRefundTransaction = q1[13].toString();
	        String deficitAmount = String.format("%.2f", q1[14]);
	        String overAmount = String.format("%.2f", q1[15]);
	        String locationName = q1[16].toString();
	        String locationCode = q1[17].toString();
	        String serviceId = q1[18].toString();
	        String totalTransaction = q1[19].toString();
	        String sectorName = q1[20].toString();
	        String courtName = q1[21].toString();

	        FinancialAuditDTO tFinancialAuditDTO = new FinancialAuditDTO();
	        tFinancialAuditDTO.setTotalAmount(totalAmount);
	        tFinancialAuditDTO.setTotalAmountCash(totalAmountCash);
	        tFinancialAuditDTO.setTotalAmountVisa(totalAmountVisa);
	        tFinancialAuditDTO.setTotalAmountDeficit(totalAmountDeficit);
	        tFinancialAuditDTO.setTotalAmountOver(totalAmountOver);
	        tFinancialAuditDTO.setSystemTotalCollectionAmount(systemTotalCollectionAmount);
	        tFinancialAuditDTO.setSystemTotalSettlementAmount(systemTotalSettlementAmount);
	        tFinancialAuditDTO.setSystemTotalAmountRefund(systemTotalAmountRefund);
	        tFinancialAuditDTO.setSystemTotalAmountCash(systemTotalAmountCash);
	        tFinancialAuditDTO.setSystemTotalAmountVisa(systemTotalAmountVisa);
	        tFinancialAuditDTO.setTotalCancelledTransaction(totalCancelledTransactions);
	        tFinancialAuditDTO.setTotalSettlementTransaction(totalSettlementTransaction);
	        tFinancialAuditDTO.setTotalCollectionTransaction(totalCollectionTransaction);
	        tFinancialAuditDTO.setTotalRefundTransaction(totalRefundTransaction);
	        tFinancialAuditDTO.setLocationName(locationName);
	        tFinancialAuditDTO.setLocationCode(locationCode); 
	        tFinancialAuditDTO.setServiceId(serviceId);
	        tFinancialAuditDTO.setSectorName(sectorName);
	        tFinancialAuditDTO.setCourtName(courtName);
	        if(deficitAmount != null && Double.valueOf(deficitAmount) > 0) {
	        	tFinancialAuditDTO.setOverOrDeficitAmount("-"+deficitAmount);
			}else if(overAmount != null && Double.valueOf(overAmount) > 0){
				tFinancialAuditDTO.setOverOrDeficitAmount("+"+overAmount);
			}
	        tFinancialAuditDTO.setTotalTransaction(totalTransaction);
	        
	        long sumTotalCancelledTransaction = Long.valueOf(totalFinancialAuditDTO.getTotalCancelledTransaction()) + 
					Long.valueOf(totalCancelledTransactions);
	        totalFinancialAuditDTO.setTotalCancelledTransaction(String.valueOf(sumTotalCancelledTransaction));
			
	        long sumTotalCollectionTransaction = Long.valueOf(totalFinancialAuditDTO.getTotalCollectionTransaction()) + 
					Long.valueOf(totalCollectionTransaction);
	        totalFinancialAuditDTO.setTotalCollectionTransaction(String.valueOf(sumTotalCollectionTransaction));
			
			long sumTotalRefundTransaction = Long.valueOf(totalFinancialAuditDTO.getTotalRefundTransaction()) + 
					Long.valueOf(totalRefundTransaction);
			totalFinancialAuditDTO.setTotalRefundTransaction(String.valueOf(sumTotalRefundTransaction));
			
			long sumTotalSettlementTransaction = Long.valueOf(totalFinancialAuditDTO.getTotalSettlementTransaction()) + 
					Long.valueOf(totalSettlementTransaction);
			totalFinancialAuditDTO.setTotalSettlementTransaction(String.valueOf(sumTotalSettlementTransaction));
			
			long sumTotalTransaction = Long.valueOf(totalFinancialAuditDTO.getTotalTransaction()) + 
					Long.valueOf(totalTransaction);
			totalFinancialAuditDTO.setTotalTransaction(String.valueOf(sumTotalTransaction));
			
			double sumTotalAmount = Double.valueOf(totalFinancialAuditDTO.getTotalAmount()) + 
					Double.valueOf(totalAmount);
			totalFinancialAuditDTO.setTotalAmount(String.format("%.2f", sumTotalAmount));
			
			double sumTotalAmountCash = Double.valueOf(totalFinancialAuditDTO.getTotalAmountCash()) + 
					Double.valueOf(totalAmountCash);
			totalFinancialAuditDTO.setTotalAmountCash(String.format("%.2f", sumTotalAmountCash));
			
			double sumTotalAmountVisa = Double.valueOf(totalFinancialAuditDTO.getTotalAmountVisa()) + 
					Double.valueOf(totalAmountVisa);
			totalFinancialAuditDTO.setTotalAmountVisa(String.format("%.2f", sumTotalAmountVisa));
			
			double sumTotalAmountDificit = Double.valueOf(totalFinancialAuditDTO.getTotalAmountDeficit()) + 
					Double.valueOf(totalAmountDeficit);
			totalFinancialAuditDTO.setTotalAmountDeficit(String.format("%.2f", sumTotalAmountDificit));
			
			double sumTotalAmountOver = Double.valueOf(totalFinancialAuditDTO.getTotalAmountOver()) + 
					Double.valueOf(totalAmountOver);
			totalFinancialAuditDTO.setTotalAmountOver(String.format("%.2f", sumTotalAmountOver));
			
			double sumSystemTotalCollectionAmount = Double.valueOf(totalFinancialAuditDTO.getSystemTotalCollectionAmount()) + 
					Double.valueOf(systemTotalCollectionAmount);
			totalFinancialAuditDTO.setSystemTotalCollectionAmount(String.format("%.2f", sumSystemTotalCollectionAmount));
			
			double sumSystemTotalSettlementAmount = Double.valueOf(totalFinancialAuditDTO.getSystemTotalSettlementAmount()) + 
					Double.valueOf(systemTotalSettlementAmount);
			totalFinancialAuditDTO.setSystemTotalSettlementAmount(String.format("%.2f", sumSystemTotalSettlementAmount));
			
			double sumSystemTotalAmountRefund = Double.valueOf(totalFinancialAuditDTO.getSystemTotalAmountRefund()) + 
					Double.valueOf(systemTotalAmountRefund);
			totalFinancialAuditDTO.setSystemTotalAmountRefund(String.format("%.2f", sumSystemTotalAmountRefund));
			
			double sumSystemTotalAmountCash = Double.valueOf(totalFinancialAuditDTO.getSystemTotalAmountCash()) + 
					Double.valueOf(systemTotalAmountCash);
			totalFinancialAuditDTO.setSystemTotalAmountCash(String.format("%.2f", sumSystemTotalAmountCash));
			
			double sumSystemTotalAmountVisa = Double.valueOf(totalFinancialAuditDTO.getSystemTotalAmountVisa()) + 
					Double.valueOf(systemTotalAmountVisa);
			totalFinancialAuditDTO.setSystemTotalAmountVisa(String.format("%.2f", sumSystemTotalAmountVisa));
			
	        result.add(tFinancialAuditDTO);
	        
	     }

	    result.add(totalFinancialAuditDTO);
	    
	    return new PageImpl<FinancialAuditDTO>(result, pageable, total);
	}
	
	@Override
	public Page<FinancialAuditDTO> getAuditReportBySector(FinancialReportRequest financialReportRequest) {
		Pageable pageable = PageRequest.of(Integer.valueOf(financialReportRequest.getPageNo()), 
				Integer.valueOf(financialReportRequest.getPageSize()));
		
		List<FinancialAuditDTO> result = new ArrayList<FinancialAuditDTO>();
		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();
		
		String selectQuery = "SELECT"
				+ " SUM(v.deposit_total_amount) as totalAmount," 
				+ " SUM(v.deposit_cash) as totalAmountCash,"
				+ " SUM(v.deposit_visa) as totalAmountVisa,"
				+ " SUM(v.deficit_amount) as totalAmountDeficit," 
				+ " SUM(v.over_amount) as totalAmountOver,"
				+ " SUM(v.system_total_collection_amount) as systemTotalCollectionAmount,"
				+ " SUM(v.system_total_settlement_amount) as systemTotalSettlementAmount,"
				+ " SUM(v.system_amount_refund) as systemTotalAmountRefund,"
				+ " SUM(v.system_amount_cash) as systemTotalAmountCash,"
				+ " SUM(v.system_amount_visa) as systemTotalAmountVisa,"
				+ " SUM(v.total_cancelled_transaction) as totalCancelledTransactions,"
				+ " SUM(v.total_settlement_transaction) as totalSettlementTransaction,"
				+ " SUM(v.total_collection_transaction) as totalCollectionTransaction,"
				+ " SUM(v.total_refund_transaction) as totalRefundTransaction,"
				+ " SUM(v.deficit_amount) as deficitAmount,"
				+ " SUM(v.over_amount) as overAmount,"
				+ " v.location_name as locationName,"
				+ " v.location_code as locationCode,"
				+ " v.service_id as serviceId,"
				+ " SUM(v.total_transaction) as totalTransaction, s.name as sectorName"
				+ " FROM pull_account v"
				+ " JOIN (SELECT p.name, p.location_fk, p.court_fk, MAX(p.id) AS max_id"
				+ " FROM sector p"
				+ " GROUP BY p.location_fk) s ON v.location_code = s.location_fk"
				+ " WHERE v.created_at >= '" + financialReportRequest.getDurationFrom() + " 00:00:00' "
				+ " AND v.created_at <= '" + financialReportRequest.getDurationTo() + " 23:59:59'"
				+ " AND (v.status_fk = 'Approved' OR v.status_fk = 'Pending') ";
		jpql.append(selectQuery);
		
		String countQuery = "SELECT COUNT(*)"
				+ " FROM pull_account v"
				+ " JOIN (SELECT p.name, p.location_fk, p.court_fk, MAX(p.id) AS max_id"
				+ " FROM sector p"
				+ " GROUP BY p.location_fk) s ON v.location_code = s.location_fk"
				+ " WHERE v.created_at >= '" + financialReportRequest.getDurationFrom() + " 00:00:00' "
				+ " AND v.created_at <= '" + financialReportRequest.getDurationTo() + " 23:59:59' "
				+ " AND (v.status_fk = 'Approved' OR v.status_fk = 'Pending') ";
		jpqlCount.append(countQuery);
		
		if(financialReportRequest.getServiceId() != null && !financialReportRequest.getServiceId().isEmpty()) {
			jpql.append(" AND v.service_id = "+financialReportRequest.getServiceId()+" ");
			jpqlCount.append(" AND v.service_id = "+financialReportRequest.getServiceId()+" ");
		}
		
		jpql.append(" GROUP BY s.name, v.service_id, v.location_code ORDER BY v.created_at desc");
		jpqlCount.append(" GROUP BY s.name, v.service_id, v.location_code");
		
		Query query = em.createNativeQuery(jpql.toString());
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Query queryCount = em.createNativeQuery(jpqlCount.toString());
		long total = queryCount.getResultList().size();
		
		List<Object[]> list = query.getResultList();
		FinancialAuditDTO totalFinancialAuditDTO = new FinancialAuditDTO();
		totalFinancialAuditDTO.setSectorName("الإجمالى");
		totalFinancialAuditDTO.setTotalCancelledTransaction(String.valueOf(0));
		totalFinancialAuditDTO.setTotalCollectionTransaction(String.valueOf(0));
		totalFinancialAuditDTO.setTotalRefundTransaction(String.valueOf(0));
		totalFinancialAuditDTO.setTotalSettlementTransaction(String.valueOf(0));
		totalFinancialAuditDTO.setTotalTransaction(String.valueOf(0));
		totalFinancialAuditDTO.setTotalAmount(String.valueOf(0));
		totalFinancialAuditDTO.setTotalAmountCash(String.valueOf(0));
		totalFinancialAuditDTO.setTotalAmountVisa(String.valueOf(0));
		totalFinancialAuditDTO.setTotalAmountDeficit(String.valueOf(0));
		totalFinancialAuditDTO.setTotalAmountOver(String.valueOf(0));
		totalFinancialAuditDTO.setSystemTotalCollectionAmount(String.valueOf(0));
		totalFinancialAuditDTO.setSystemTotalSettlementAmount(String.valueOf(0));
		totalFinancialAuditDTO.setSystemTotalAmountRefund(String.valueOf(0));
		totalFinancialAuditDTO.setSystemTotalAmountCash(String.valueOf(0));
		totalFinancialAuditDTO.setSystemTotalAmountVisa(String.valueOf(0));
		
	    for(Object[] q1 : list){

	    	String totalAmount = String.format("%.2f", q1[0]);
	    	String totalAmountCash = String.format("%.2f", q1[1]);
	        String totalAmountVisa = String.format("%.2f", q1[2]);
	        String totalAmountDeficit = String.format("%.2f", q1[3]);
	        String totalAmountOver = String.format("%.2f", q1[4]);
	        String systemTotalCollectionAmount = String.format("%.2f", q1[5]);
	        String systemTotalSettlementAmount = String.format("%.2f", q1[6]);
	        String systemTotalAmountRefund = String.format("%.2f", q1[7]);
	        String systemTotalAmountCash = String.format("%.2f", q1[8]);
	        String systemTotalAmountVisa = String.format("%.2f", q1[9]);
	        String totalCancelledTransactions = q1[10].toString();
	        String totalSettlementTransaction = q1[11].toString();
	        String totalCollectionTransaction = q1[12].toString();
	        String totalRefundTransaction = q1[13].toString();
	        String deficitAmount = String.format("%.2f", q1[14]);
	        String overAmount = String.format("%.2f", q1[15]);
	        String locationName = q1[16].toString();
	        String locationCode = q1[17].toString();
	        String serviceId = q1[18].toString();
	        String totalTransaction = q1[19].toString();
	        String sectorName = q1[20].toString();

	        FinancialAuditDTO tFinancialAuditDTO = new FinancialAuditDTO();
	        tFinancialAuditDTO.setTotalAmount(totalAmount);
	        tFinancialAuditDTO.setTotalAmountCash(totalAmountCash);
	        tFinancialAuditDTO.setTotalAmountVisa(totalAmountVisa);
	        tFinancialAuditDTO.setTotalAmountDeficit(totalAmountDeficit);
	        tFinancialAuditDTO.setTotalAmountOver(totalAmountOver);
	        tFinancialAuditDTO.setSystemTotalCollectionAmount(systemTotalCollectionAmount);
	        tFinancialAuditDTO.setSystemTotalSettlementAmount(systemTotalSettlementAmount);
	        tFinancialAuditDTO.setSystemTotalAmountRefund(systemTotalAmountRefund);
	        tFinancialAuditDTO.setSystemTotalAmountCash(systemTotalAmountCash);
	        tFinancialAuditDTO.setSystemTotalAmountVisa(systemTotalAmountVisa);
	        tFinancialAuditDTO.setTotalCancelledTransaction(totalCancelledTransactions);
	        tFinancialAuditDTO.setTotalSettlementTransaction(totalSettlementTransaction);
	        tFinancialAuditDTO.setTotalCollectionTransaction(totalCollectionTransaction);
	        tFinancialAuditDTO.setTotalRefundTransaction(totalRefundTransaction);
	        tFinancialAuditDTO.setLocationName(locationName);
	        tFinancialAuditDTO.setLocationCode(locationCode); 
	        tFinancialAuditDTO.setServiceId(serviceId);
	        tFinancialAuditDTO.setSectorName(sectorName);
	        if(deficitAmount != null && Double.valueOf(deficitAmount) > 0) {
	        	tFinancialAuditDTO.setOverOrDeficitAmount("-"+deficitAmount);
			}else if(overAmount != null && Double.valueOf(overAmount) > 0){
				tFinancialAuditDTO.setOverOrDeficitAmount("+"+overAmount);
			}
	        tFinancialAuditDTO.setTotalTransaction(totalTransaction);
	        
	        long sumTotalCancelledTransaction = Long.valueOf(totalFinancialAuditDTO.getTotalCancelledTransaction()) + 
					Long.valueOf(totalCancelledTransactions);
	        totalFinancialAuditDTO.setTotalCancelledTransaction(String.valueOf(sumTotalCancelledTransaction));
			
	        long sumTotalCollectionTransaction = Long.valueOf(totalFinancialAuditDTO.getTotalCollectionTransaction()) + 
					Long.valueOf(totalCollectionTransaction);
	        totalFinancialAuditDTO.setTotalCollectionTransaction(String.valueOf(sumTotalCollectionTransaction));
			
			long sumTotalRefundTransaction = Long.valueOf(totalFinancialAuditDTO.getTotalRefundTransaction()) + 
					Long.valueOf(totalRefundTransaction);
			totalFinancialAuditDTO.setTotalRefundTransaction(String.valueOf(sumTotalRefundTransaction));
			
			long sumTotalSettlementTransaction = Long.valueOf(totalFinancialAuditDTO.getTotalSettlementTransaction()) + 
					Long.valueOf(totalSettlementTransaction);
			totalFinancialAuditDTO.setTotalSettlementTransaction(String.valueOf(sumTotalSettlementTransaction));
			
			long sumTotalTransaction = Long.valueOf(totalFinancialAuditDTO.getTotalTransaction()) + 
					Long.valueOf(totalTransaction);
			totalFinancialAuditDTO.setTotalTransaction(String.valueOf(sumTotalTransaction));
			
			double sumTotalAmount = Double.valueOf(totalFinancialAuditDTO.getTotalAmount()) + 
					Double.valueOf(totalAmount);
			totalFinancialAuditDTO.setTotalAmount(String.format("%.2f", sumTotalAmount));
			
			double sumTotalAmountCash = Double.valueOf(totalFinancialAuditDTO.getTotalAmountCash()) + 
					Double.valueOf(totalAmountCash);
			totalFinancialAuditDTO.setTotalAmountCash(String.format("%.2f", sumTotalAmountCash));
			
			double sumTotalAmountVisa = Double.valueOf(totalFinancialAuditDTO.getTotalAmountVisa()) + 
					Double.valueOf(totalAmountVisa);
			totalFinancialAuditDTO.setTotalAmountVisa(String.format("%.2f", sumTotalAmountVisa));
			
			double sumTotalAmountDificit = Double.valueOf(totalFinancialAuditDTO.getTotalAmountDeficit()) + 
					Double.valueOf(totalAmountDeficit);
			totalFinancialAuditDTO.setTotalAmountDeficit(String.format("%.2f", sumTotalAmountDificit));
			
			double sumTotalAmountOver = Double.valueOf(totalFinancialAuditDTO.getTotalAmountOver()) + 
					Double.valueOf(totalAmountOver);
			totalFinancialAuditDTO.setTotalAmountOver(String.format("%.2f", sumTotalAmountOver));
			
			double sumSystemTotalCollectionAmount = Double.valueOf(totalFinancialAuditDTO.getSystemTotalCollectionAmount()) + 
					Double.valueOf(systemTotalCollectionAmount);
			totalFinancialAuditDTO.setSystemTotalCollectionAmount(String.format("%.2f", sumSystemTotalCollectionAmount));
			
			double sumSystemTotalSettlementAmount = Double.valueOf(totalFinancialAuditDTO.getSystemTotalSettlementAmount()) + 
					Double.valueOf(systemTotalSettlementAmount);
			totalFinancialAuditDTO.setSystemTotalSettlementAmount(String.format("%.2f", sumSystemTotalSettlementAmount));
			
			double sumSystemTotalAmountRefund = Double.valueOf(totalFinancialAuditDTO.getSystemTotalAmountRefund()) + 
					Double.valueOf(systemTotalAmountRefund);
			totalFinancialAuditDTO.setSystemTotalAmountRefund(String.format("%.2f", sumSystemTotalAmountRefund));
			
			double sumSystemTotalAmountCash = Double.valueOf(totalFinancialAuditDTO.getSystemTotalAmountCash()) + 
					Double.valueOf(systemTotalAmountCash);
			totalFinancialAuditDTO.setSystemTotalAmountCash(String.format("%.2f", sumSystemTotalAmountCash));
			
			double sumSystemTotalAmountVisa = Double.valueOf(totalFinancialAuditDTO.getSystemTotalAmountVisa()) + 
					Double.valueOf(systemTotalAmountVisa);
			totalFinancialAuditDTO.setSystemTotalAmountVisa(String.format("%.2f", sumSystemTotalAmountVisa));
			
	        result.add(tFinancialAuditDTO);
	        
	     }

	    result.add(totalFinancialAuditDTO);
	    
	    return new PageImpl<FinancialAuditDTO>(result, pageable, total);
	}

	@Override
	public Page<FinancialAuditDTO> getAuditReportByPOS(FinancialReportRequest financialReportRequest) {
		Pageable pageable = PageRequest.of(Integer.valueOf(financialReportRequest.getPageNo()), 
				Integer.valueOf(financialReportRequest.getPageSize()));
		
		List<FinancialAuditDTO> result = new ArrayList<FinancialAuditDTO>();
		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();
		
		String selectQuery = "SELECT"
				+ " SUM(v.deposit_total_amount) as totalAmount,"
				+ " SUM(v.deposit_cash) as totalAmountCash,"
				+ " SUM(v.deposit_visa) as totalAmountVisa,"
				+ " SUM(v.deficit_amount) as totalAmountDeficit," 
				+ " SUM(v.over_amount) as totalAmountOver,"
				+ " SUM(v.system_total_collection_amount) as systemTotalCollectionAmount,"
				+ " SUM(v.system_total_settlement_amount) as systemTotalSettlementAmount,"
				+ " SUM(v.system_amount_refund) as systemTotalAmountRefund,"
				+ " SUM(v.system_amount_cash) as systemTotalAmountCash,"
				+ " SUM(v.system_amount_visa) as systemTotalAmountVisa,"
				+ " SUM(v.total_cancelled_transaction) as totalCancelledTransactions,"
				+ " SUM(v.total_settlement_transaction) as totalSettlementTransaction,"
				+ " SUM(v.total_collection_transaction) as totalCollectionTransaction,"
				+ " SUM(v.total_refund_transaction) as totalRefundTransaction,"
				+ " SUM(v.deficit_amount) as deficitAmount,"
				+ " SUM(v.over_amount) as overAmount,"
				+ " v.pos_id as posId," 
				+ " v.location_name as locationName,"
				+ " v.service_id as serviceId,"
				+ " SUM(v.total_transaction) as totalTransaction, latest_sector.name as sectorName, c.name as courtName"
				+ " FROM pull_account v"
				+ " JOIN (SELECT p.name, p.location_fk, p.court_fk, MAX(p.id) AS max_id"
				+ " FROM sector p"
				+ " GROUP BY p.location_fk) latest_sector ON v.location_code = latest_sector.location_fk"
				+ " JOIN courts c ON latest_sector.court_fk = c.id"
				+ " WHERE v.created_at >= '" + financialReportRequest.getDurationFrom() + " 00:00:00' "
				+ " AND v.created_at <= '" + financialReportRequest.getDurationTo() + " 23:59:59' "
				+ " AND (v.status_fk = 'Approved' OR v.status_fk = 'Pending') ";
		jpql.append(selectQuery);
		
		String countQuery = "SELECT COUNT(*)"
				+ " FROM pull_account v"
				+ " JOIN (SELECT p.name, p.location_fk, p.court_fk, MAX(p.id) AS max_id"
				+ " FROM sector p"
				+ " GROUP BY p.location_fk) latest_sector ON v.location_code = latest_sector.location_fk"
				+ " JOIN courts c ON latest_sector.court_fk = c.id"
				+ " WHERE v.created_at >= '" + financialReportRequest.getDurationFrom() + " 00:00:00' "
				+ " AND v.created_at <= '" + financialReportRequest.getDurationTo() + " 23:59:59' "
				+ " AND (v.status_fk = 'Approved' OR v.status_fk = 'Pending') ";
		jpqlCount.append(countQuery);
		
		if(financialReportRequest.getServiceId() != null && !financialReportRequest.getServiceId().isEmpty()) {
			jpql.append(" AND v.service_id = "+financialReportRequest.getServiceId()+" ");
			jpqlCount.append(" AND v.service_id = "+financialReportRequest.getServiceId()+" ");
		}
		
		jpql.append(" GROUP BY v.pos_id, v.service_id ORDER BY v.created_at desc");
		jpqlCount.append(" GROUP BY v.pos_id, v.service_id ");
		
		Query query = em.createNativeQuery(jpql.toString());
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Query queryCount = em.createNativeQuery(jpqlCount.toString());
		long total = queryCount.getResultList().size();
		
		List<Object[]> list = query.getResultList();
		FinancialAuditDTO totalFinancialAuditDTO = new FinancialAuditDTO();
		totalFinancialAuditDTO.setCourtName("الإجمالى");
		totalFinancialAuditDTO.setTotalCancelledTransaction(String.valueOf(0));
		totalFinancialAuditDTO.setTotalCollectionTransaction(String.valueOf(0));
		totalFinancialAuditDTO.setTotalRefundTransaction(String.valueOf(0));
		totalFinancialAuditDTO.setTotalSettlementTransaction(String.valueOf(0));
		totalFinancialAuditDTO.setTotalTransaction(String.valueOf(0));
		totalFinancialAuditDTO.setTotalAmount(String.valueOf(0));
		totalFinancialAuditDTO.setTotalAmountCash(String.valueOf(0));
		totalFinancialAuditDTO.setTotalAmountVisa(String.valueOf(0));
		totalFinancialAuditDTO.setTotalAmountDeficit(String.valueOf(0));
		totalFinancialAuditDTO.setTotalAmountOver(String.valueOf(0));
		totalFinancialAuditDTO.setSystemTotalCollectionAmount(String.valueOf(0));
		totalFinancialAuditDTO.setSystemTotalSettlementAmount(String.valueOf(0));
		totalFinancialAuditDTO.setSystemTotalAmountRefund(String.valueOf(0));
		totalFinancialAuditDTO.setSystemTotalAmountCash(String.valueOf(0));
		totalFinancialAuditDTO.setSystemTotalAmountVisa(String.valueOf(0));
		
	    for(Object[] q1 : list){

	    	String totalAmount = String.format("%.2f", q1[0]);
	    	String totalAmountCash = String.format("%.2f", q1[1]);
	        String totalAmountVisa = String.format("%.2f", q1[2]);
	        String totalAmountDeficit = String.format("%.2f", q1[3]);
	        String totalAmountOver = String.format("%.2f", q1[4]);
	        String systemTotalCollectionAmount = String.format("%.2f", q1[5]);
	        String systemTotalSettlementAmount = String.format("%.2f", q1[6]);
	        String systemTotalAmountRefund = String.format("%.2f", q1[7]);
	        String systemTotalAmountCash = String.format("%.2f", q1[8]);
	        String systemTotalAmountVisa = String.format("%.2f", q1[9]);
	        String totalCancelledTransactions = q1[10].toString();
	        String totalSettlementTransaction = q1[11].toString();
	        String totalCollectionTransaction = q1[12].toString();
	        String totalRefundTransaction = q1[13].toString();
	        String deficitAmount = String.format("%.2f", q1[14]);
	        String overAmount = String.format("%.2f", q1[15]);
	        String posId = q1[16].toString();
	        String locationName = q1[17].toString();
	        String serviceId = q1[18].toString();
	        String totalTransaction = q1[19].toString();
	        String sectorName = q1[20].toString();
	        String courtName = q1[21].toString();

	        FinancialAuditDTO tFinancialAuditDTO = new FinancialAuditDTO();
	        tFinancialAuditDTO.setTotalAmount(totalAmount);
	        tFinancialAuditDTO.setTotalAmountCash(totalAmountCash);
	        tFinancialAuditDTO.setTotalAmountVisa(totalAmountVisa);
	        tFinancialAuditDTO.setTotalAmountDeficit(totalAmountDeficit);
	        tFinancialAuditDTO.setTotalAmountOver(totalAmountOver);
	        tFinancialAuditDTO.setSystemTotalCollectionAmount(systemTotalCollectionAmount);
	        tFinancialAuditDTO.setSystemTotalSettlementAmount(systemTotalSettlementAmount);
	        tFinancialAuditDTO.setSystemTotalAmountRefund(systemTotalAmountRefund);
	        tFinancialAuditDTO.setSystemTotalAmountCash(systemTotalAmountCash);
	        tFinancialAuditDTO.setSystemTotalAmountVisa(systemTotalAmountVisa);
	        tFinancialAuditDTO.setTotalCancelledTransaction(totalCancelledTransactions);
	        tFinancialAuditDTO.setTotalSettlementTransaction(totalSettlementTransaction);
	        tFinancialAuditDTO.setTotalCollectionTransaction(totalCollectionTransaction);
	        tFinancialAuditDTO.setTotalRefundTransaction(totalRefundTransaction);
	        tFinancialAuditDTO.setPosId(posId);
	        tFinancialAuditDTO.setLocationName(locationName);   
	        tFinancialAuditDTO.setServiceId(serviceId);
	        tFinancialAuditDTO.setSectorName(sectorName);
	        tFinancialAuditDTO.setCourtName(courtName);
	        if(deficitAmount != null && Double.valueOf(deficitAmount) > 0) {
	        	tFinancialAuditDTO.setOverOrDeficitAmount("-"+deficitAmount);
			}else if(overAmount != null && Double.valueOf(overAmount) > 0){
				tFinancialAuditDTO.setOverOrDeficitAmount("+"+overAmount);
			}
	        tFinancialAuditDTO.setTotalTransaction(totalTransaction);
	        
	        long sumTotalCancelledTransaction = Long.valueOf(totalFinancialAuditDTO.getTotalCancelledTransaction()) + 
					Long.valueOf(totalCancelledTransactions);
	        totalFinancialAuditDTO.setTotalCancelledTransaction(String.valueOf(sumTotalCancelledTransaction));
			
	        long sumTotalCollectionTransaction = Long.valueOf(totalFinancialAuditDTO.getTotalCollectionTransaction()) + 
					Long.valueOf(totalCollectionTransaction);
	        totalFinancialAuditDTO.setTotalCollectionTransaction(String.valueOf(sumTotalCollectionTransaction));
			
			long sumTotalRefundTransaction = Long.valueOf(totalFinancialAuditDTO.getTotalRefundTransaction()) + 
					Long.valueOf(totalRefundTransaction);
			totalFinancialAuditDTO.setTotalRefundTransaction(String.valueOf(sumTotalRefundTransaction));
			
			long sumTotalSettlementTransaction = Long.valueOf(totalFinancialAuditDTO.getTotalSettlementTransaction()) + 
					Long.valueOf(totalSettlementTransaction);
			totalFinancialAuditDTO.setTotalSettlementTransaction(String.valueOf(sumTotalSettlementTransaction));
			
			long sumTotalTransaction = Long.valueOf(totalFinancialAuditDTO.getTotalTransaction()) + 
					Long.valueOf(totalTransaction);
			totalFinancialAuditDTO.setTotalTransaction(String.valueOf(sumTotalTransaction));
			
			double sumTotalAmount = Double.valueOf(totalFinancialAuditDTO.getTotalAmount()) + 
					Double.valueOf(totalAmount);
			totalFinancialAuditDTO.setTotalAmount(String.format("%.2f", sumTotalAmount));
			
			double sumTotalAmountCash = Double.valueOf(totalFinancialAuditDTO.getTotalAmountCash()) + 
					Double.valueOf(totalAmountCash);
			totalFinancialAuditDTO.setTotalAmountCash(String.format("%.2f", sumTotalAmountCash));
			
			double sumTotalAmountVisa = Double.valueOf(totalFinancialAuditDTO.getTotalAmountVisa()) + 
					Double.valueOf(totalAmountVisa);
			totalFinancialAuditDTO.setTotalAmountVisa(String.format("%.2f", sumTotalAmountVisa));
			
			double sumTotalAmountDificit = Double.valueOf(totalFinancialAuditDTO.getTotalAmountDeficit()) + 
					Double.valueOf(totalAmountDeficit);
			totalFinancialAuditDTO.setTotalAmountDeficit(String.format("%.2f", sumTotalAmountDificit));
			
			double sumTotalAmountOver = Double.valueOf(totalFinancialAuditDTO.getTotalAmountOver()) + 
					Double.valueOf(totalAmountOver);
			totalFinancialAuditDTO.setTotalAmountOver(String.format("%.2f", sumTotalAmountOver));
			
			double sumSystemTotalCollectionAmount = Double.valueOf(totalFinancialAuditDTO.getSystemTotalCollectionAmount()) + 
					Double.valueOf(systemTotalCollectionAmount);
			totalFinancialAuditDTO.setSystemTotalCollectionAmount(String.format("%.2f", sumSystemTotalCollectionAmount));
			
			double sumSystemTotalSettlementAmount = Double.valueOf(totalFinancialAuditDTO.getSystemTotalSettlementAmount()) + 
					Double.valueOf(systemTotalSettlementAmount);
			totalFinancialAuditDTO.setSystemTotalSettlementAmount(String.format("%.2f", sumSystemTotalSettlementAmount));
			
			double sumSystemTotalAmountRefund = Double.valueOf(totalFinancialAuditDTO.getSystemTotalAmountRefund()) + 
					Double.valueOf(systemTotalAmountRefund);
			totalFinancialAuditDTO.setSystemTotalAmountRefund(String.format("%.2f", sumSystemTotalAmountRefund));
			
			double sumSystemTotalAmountCash = Double.valueOf(totalFinancialAuditDTO.getSystemTotalAmountCash()) + 
					Double.valueOf(systemTotalAmountCash);
			totalFinancialAuditDTO.setSystemTotalAmountCash(String.format("%.2f", sumSystemTotalAmountCash));
			
			double sumSystemTotalAmountVisa = Double.valueOf(totalFinancialAuditDTO.getSystemTotalAmountVisa()) + 
					Double.valueOf(systemTotalAmountVisa);
			totalFinancialAuditDTO.setSystemTotalAmountVisa(String.format("%.2f", sumSystemTotalAmountVisa));
			
	        result.add(tFinancialAuditDTO);
	        
	     }

	    result.add(totalFinancialAuditDTO);
	    
	    return new PageImpl<FinancialAuditDTO>(result, pageable, total);
	}

	@Override
	public Page<FinancialAuditDTO> getAuditReportByAgentAndLocation(FinancialReportRequest financialReportRequest) {
		Pageable pageable = PageRequest.of(Integer.valueOf(financialReportRequest.getPageNo()), 
				Integer.valueOf(financialReportRequest.getPageSize()));
		
		List<FinancialAuditDTO> result = new ArrayList<FinancialAuditDTO>();
		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();
		
		String selectQuery = "SELECT"
				+ " SUM(v.deposit_total_amount) as totalAmount," 
				+ " SUM(v.deposit_cash) as totalAmountCash,"
				+ " SUM(v.deposit_visa) as totalAmountVisa,"
				+ " SUM(v.deficit_amount) as totalAmountDeficit," 
				+ " SUM(v.over_amount) as totalAmountOver,"
				+ " SUM(v.system_total_collection_amount) as systemTotalCollectionAmount,"
				+ " SUM(v.system_total_settlement_amount) as systemTotalSettlementAmount,"
				+ " SUM(v.system_amount_refund) as systemTotalAmountRefund,"
				+ " SUM(v.system_amount_cash) as systemTotalAmountCash,"
				+ " SUM(v.system_amount_visa) as systemTotalAmountVisa,"
				+ " SUM(v.total_cancelled_transaction) as totalCancelledTransactions,"
				+ " SUM(v.total_settlement_transaction) as totalSettlementTransaction,"
				+ " SUM(v.total_collection_transaction) as totalCollectionTransaction,"
				+ " SUM(v.total_refund_transaction) as totalRefundTransaction,"
				+ " SUM(v.deficit_amount) as deficitAmount,"
				+ " SUM(v.over_amount) as overAmount,"
				+ " v.created_by as agentName," 
				+ " v.location_name as locationName,"
				+ " v.location_code as locationCode,"
				+ " v.service_id as serviceId,"
				+ " SUM(v.total_transaction) as totalTransaction, latest_sector.name as sectorName, c.name as courtName"
				+ " FROM pull_account v"
				+ " JOIN (SELECT p.name, p.location_fk, p.court_fk, MAX(p.id) AS max_id"
				+ " FROM sector p"
				+ " GROUP BY p.location_fk) latest_sector ON v.location_code = latest_sector.location_fk"
				+ " JOIN courts c ON latest_sector.court_fk = c.id"
				+ " WHERE v.created_at >= '" + financialReportRequest.getDurationFrom() + " 00:00:00' "
				+ " AND v.created_at <= '" + financialReportRequest.getDurationTo() + " 23:59:59' "
				+ " AND (v.status_fk = 'Approved' OR v.status_fk = 'Pending') ";
		jpql.append(selectQuery);
		
		String countQuery = "SELECT COUNT(*)"
				+ " FROM pull_account v"
				+ " JOIN (SELECT p.name, p.location_fk, p.court_fk, MAX(p.id) AS max_id"
				+ " FROM sector p"
				+ " GROUP BY p.location_fk) latest_sector ON v.location_code = latest_sector.location_fk"
				+ " JOIN courts c ON latest_sector.court_fk = c.id"
				+ " WHERE v.created_at >= '" + financialReportRequest.getDurationFrom() + " 00:00:00' "
				+ " AND v.created_at <= '" + financialReportRequest.getDurationTo() + " 23:59:59' "
				+ " AND (v.status_fk = 'Approved' OR v.status_fk = 'Pending') ";
		jpqlCount.append(selectQuery);
		
		if(financialReportRequest.getServiceId() != null && !financialReportRequest.getServiceId().isEmpty()) {
			jpql.append(" AND v.service_id = "+financialReportRequest.getServiceId()+" ");
			jpqlCount.append(" AND v.service_id = "+financialReportRequest.getServiceId()+" ");
		}
		
		jpql.append(" GROUP BY v.created_by, latest_sector.court_fk, v.service_id ORDER BY v.created_at desc");
		jpqlCount.append(" GROUP BY v.created_by, latest_sector.court_fk, v.service_id");
		
		Query query = em.createNativeQuery(jpql.toString());
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Query queryCount = em.createNativeQuery(jpqlCount.toString());
		long total = queryCount.getResultList().size();
		
		List<Object[]> list = query.getResultList();
		FinancialAuditDTO totalFinancialAuditDTO = new FinancialAuditDTO();
		totalFinancialAuditDTO.setCourtName("الإجمالى");
		totalFinancialAuditDTO.setTotalCancelledTransaction(String.valueOf(0));
		totalFinancialAuditDTO.setTotalCollectionTransaction(String.valueOf(0));
		totalFinancialAuditDTO.setTotalRefundTransaction(String.valueOf(0));
		totalFinancialAuditDTO.setTotalSettlementTransaction(String.valueOf(0));
		totalFinancialAuditDTO.setTotalTransaction(String.valueOf(0));
		totalFinancialAuditDTO.setTotalAmount(String.valueOf(0));
		totalFinancialAuditDTO.setTotalAmountCash(String.valueOf(0));
		totalFinancialAuditDTO.setTotalAmountVisa(String.valueOf(0));
		totalFinancialAuditDTO.setTotalAmountDeficit(String.valueOf(0));
		totalFinancialAuditDTO.setTotalAmountOver(String.valueOf(0));
		totalFinancialAuditDTO.setSystemTotalCollectionAmount(String.valueOf(0));
		totalFinancialAuditDTO.setSystemTotalSettlementAmount(String.valueOf(0));
		totalFinancialAuditDTO.setSystemTotalAmountRefund(String.valueOf(0));
		totalFinancialAuditDTO.setSystemTotalAmountCash(String.valueOf(0));
		totalFinancialAuditDTO.setSystemTotalAmountVisa(String.valueOf(0));
		
	    for(Object[] q1 : list){

	    	String totalAmount = String.format("%.2f", q1[0]);
	    	String totalAmountCash = String.format("%.2f", q1[1]);
	        String totalAmountVisa = String.format("%.2f", q1[2]);
	        String totalAmountDeficit = String.format("%.2f", q1[3]);
	        String totalAmountOver = String.format("%.2f", q1[4]);
	        String systemTotalCollectionAmount = String.format("%.2f", q1[5]);
	        String systemTotalSettlementAmount = String.format("%.2f", q1[6]);
	        String systemTotalAmountRefund = String.format("%.2f", q1[7]);
	        String systemTotalAmountCash = String.format("%.2f", q1[8]);
	        String systemTotalAmountVisa = String.format("%.2f", q1[9]);
	        String totalCancelledTransactions = q1[10].toString();
	        String totalSettlementTransaction = q1[11].toString();
	        String totalCollectionTransaction = q1[12].toString();
	        String totalRefundTransaction = q1[13].toString();
	        String deficitAmount = String.format("%.2f", q1[14]);
	        String overAmount = String.format("%.2f", q1[15]);
	        String agentName = q1[16].toString();
	        String locationName = q1[17].toString();
	        String locationCode = q1[18].toString();
	        String serviceId = q1[19].toString();
	        String totalTransaction = q1[20].toString();
	        String sectorName = q1[21].toString();
	        String courtName = q1[22].toString();

	        FinancialAuditDTO tFinancialAuditDTO = new FinancialAuditDTO();
	        tFinancialAuditDTO.setTotalAmount(totalAmount);
	        tFinancialAuditDTO.setTotalAmountCash(totalAmountCash);
	        tFinancialAuditDTO.setTotalAmountVisa(totalAmountVisa);
	        tFinancialAuditDTO.setTotalAmountDeficit(totalAmountDeficit);
	        tFinancialAuditDTO.setTotalAmountOver(totalAmountOver);
	        tFinancialAuditDTO.setSystemTotalCollectionAmount(systemTotalCollectionAmount);
	        tFinancialAuditDTO.setSystemTotalSettlementAmount(systemTotalSettlementAmount);
	        tFinancialAuditDTO.setSystemTotalAmountRefund(systemTotalAmountRefund);
	        tFinancialAuditDTO.setSystemTotalAmountCash(systemTotalAmountCash);
	        tFinancialAuditDTO.setSystemTotalAmountVisa(systemTotalAmountVisa);
	        tFinancialAuditDTO.setTotalCancelledTransaction(totalCancelledTransactions);
	        tFinancialAuditDTO.setTotalSettlementTransaction(totalSettlementTransaction);
	        tFinancialAuditDTO.setTotalCollectionTransaction(totalCollectionTransaction);
	        tFinancialAuditDTO.setTotalRefundTransaction(totalRefundTransaction);
	        tFinancialAuditDTO.setAgentName(agentName);
	        tFinancialAuditDTO.setLocationName(locationName);
	        tFinancialAuditDTO.setLocationCode(locationCode);
	        tFinancialAuditDTO.setSectorName(sectorName);
	        tFinancialAuditDTO.setCourtName(courtName);
	        tFinancialAuditDTO.setServiceId(serviceId);
	        if(deficitAmount != null && Double.valueOf(deficitAmount) > 0) {
	        	tFinancialAuditDTO.setOverOrDeficitAmount("-"+deficitAmount);
			}else if(overAmount != null && Double.valueOf(overAmount) > 0){
				tFinancialAuditDTO.setOverOrDeficitAmount("+"+overAmount);
			}
	        tFinancialAuditDTO.setTotalTransaction(totalTransaction);
	        
	        long sumTotalCancelledTransaction = Long.valueOf(totalFinancialAuditDTO.getTotalCancelledTransaction()) + 
					Long.valueOf(totalCancelledTransactions);
	        totalFinancialAuditDTO.setTotalCancelledTransaction(String.valueOf(sumTotalCancelledTransaction));
			
	        long sumTotalCollectionTransaction = Long.valueOf(totalFinancialAuditDTO.getTotalCollectionTransaction()) + 
					Long.valueOf(totalCollectionTransaction);
	        totalFinancialAuditDTO.setTotalCollectionTransaction(String.valueOf(sumTotalCollectionTransaction));
			
			long sumTotalRefundTransaction = Long.valueOf(totalFinancialAuditDTO.getTotalRefundTransaction()) + 
					Long.valueOf(totalRefundTransaction);
			totalFinancialAuditDTO.setTotalRefundTransaction(String.valueOf(sumTotalRefundTransaction));
			
			long sumTotalSettlementTransaction = Long.valueOf(totalFinancialAuditDTO.getTotalSettlementTransaction()) + 
					Long.valueOf(totalSettlementTransaction);
			totalFinancialAuditDTO.setTotalSettlementTransaction(String.valueOf(sumTotalSettlementTransaction));
			
			long sumTotalTransaction = Long.valueOf(totalFinancialAuditDTO.getTotalTransaction()) + 
					Long.valueOf(totalTransaction);
			totalFinancialAuditDTO.setTotalTransaction(String.valueOf(sumTotalTransaction));
			
			double sumTotalAmount = Double.valueOf(totalFinancialAuditDTO.getTotalAmount()) + 
					Double.valueOf(totalAmount);
			totalFinancialAuditDTO.setTotalAmount(String.format("%.2f", sumTotalAmount));
			
			double sumTotalAmountCash = Double.valueOf(totalFinancialAuditDTO.getTotalAmountCash()) + 
					Double.valueOf(totalAmountCash);
			totalFinancialAuditDTO.setTotalAmountCash(String.format("%.2f", sumTotalAmountCash));
			
			double sumTotalAmountVisa = Double.valueOf(totalFinancialAuditDTO.getTotalAmountVisa()) + 
					Double.valueOf(totalAmountVisa);
			totalFinancialAuditDTO.setTotalAmountVisa(String.format("%.2f", sumTotalAmountVisa));
			
			double sumTotalAmountDificit = Double.valueOf(totalFinancialAuditDTO.getTotalAmountDeficit()) + 
					Double.valueOf(totalAmountDeficit);
			totalFinancialAuditDTO.setTotalAmountDeficit(String.format("%.2f", sumTotalAmountDificit));
			
			double sumTotalAmountOver = Double.valueOf(totalFinancialAuditDTO.getTotalAmountOver()) + 
					Double.valueOf(totalAmountOver);
			totalFinancialAuditDTO.setTotalAmountOver(String.format("%.2f", sumTotalAmountOver));
			
			double sumSystemTotalCollectionAmount = Double.valueOf(totalFinancialAuditDTO.getSystemTotalCollectionAmount()) + 
					Double.valueOf(systemTotalCollectionAmount);
			totalFinancialAuditDTO.setSystemTotalCollectionAmount(String.format("%.2f", sumSystemTotalCollectionAmount));
			
			double sumSystemTotalSettlementAmount = Double.valueOf(totalFinancialAuditDTO.getSystemTotalSettlementAmount()) + 
					Double.valueOf(systemTotalSettlementAmount);
			totalFinancialAuditDTO.setSystemTotalSettlementAmount(String.format("%.2f", sumSystemTotalSettlementAmount));
			
			double sumSystemTotalAmountRefund = Double.valueOf(totalFinancialAuditDTO.getSystemTotalAmountRefund()) + 
					Double.valueOf(systemTotalAmountRefund);
			totalFinancialAuditDTO.setSystemTotalAmountRefund(String.format("%.2f", sumSystemTotalAmountRefund));
			
			double sumSystemTotalAmountCash = Double.valueOf(totalFinancialAuditDTO.getSystemTotalAmountCash()) + 
					Double.valueOf(systemTotalAmountCash);
			totalFinancialAuditDTO.setSystemTotalAmountCash(String.format("%.2f", sumSystemTotalAmountCash));
			
			double sumSystemTotalAmountVisa = Double.valueOf(totalFinancialAuditDTO.getSystemTotalAmountVisa()) + 
					Double.valueOf(systemTotalAmountVisa);
			totalFinancialAuditDTO.setSystemTotalAmountVisa(String.format("%.2f", sumSystemTotalAmountVisa));
			
	        result.add(tFinancialAuditDTO);	        
	        
	     }

	    result.add(totalFinancialAuditDTO);
	    
	    return new PageImpl<FinancialAuditDTO>(result, pageable, total);
	}

	@Override
	public Page<PullAccount> findApprovedClaimsAdvancedSearch(ApprovedClaimsSearchRequest obj) {
		
		Pageable pageable = PageRequest.of(Integer.valueOf(obj.getPageNo()), Integer.valueOf(obj.getPageSize()));

		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();

		jpql.append("SELECT mo FROM PullAccount mo WHERE " + "mo.createdAt >= '" + obj.getDurationFrom()
				+ " 00:00:00' " + "AND mo.createdAt <= '" + obj.getDurationTo() + " 23:59:59' "  
		//+		"And mo.posId IN (" + obj.getPosIds().toString().replace("]", "").replace("[", "") + ")
				  );
		jpqlCount.append("SELECT count(mo) FROM PullAccount mo WHERE " + "mo.createdAt >= '" + obj.getDurationFrom()
				+ " 00:00:00' " + "AND mo.createdAt <= '" + obj.getDurationTo() + " 23:59:59' "+
		//		"And mo.posId IN (" + obj.getPosIds().toString().replace("]", "").replace("[", "") + ") "
						  "");

		if (obj.getBranshCode() != null && !obj.getBranshCode().isEmpty()) {
			jpql.append("AND mo.locationName Like '" + '%'+ obj.getBranshCode() + '%' + "' ");
			jpqlCount.append("AND mo.locationName Like '"   +'%'+ obj.getBranshCode() +  '%'  + "' ");
		}

//		if (obj.getPosId() != null && !obj.getPosId().isEmpty()) {
//			jpql.append("AND mo.posId = '" + obj.getPosId() + "' ");
//			jpqlCount.append("AND mo.posId = '" + obj.getPosId() + "' ");
//		}

		if (obj.getUsername() != null && !obj.getUsername().isEmpty()) {
			jpql.append("AND mo.createdBy = '" + obj.getUsername() + "' ");
			jpqlCount.append("AND mo.createdBy = '" + obj.getUsername() + "' ");
		}

		 
		 

		TypedQuery<PullAccount> query = em.createQuery(jpql.toString(), PullAccount.class);
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		TypedQuery<Long> queryCount = em.createQuery(jpqlCount.toString(), Long.class);
//		BigInteger integer = new BigInteger("0");
//			integer = (BigInteger)queryCount.getSingleResult();
//			total = integer.intValue();
		long total = queryCount.getSingleResult();

		return new PageImpl<PullAccount>(query.getResultList(), pageable, total);
	}
	
	@Override
	public Page<PullAccount> getFinancialReviewReport(FinancialReportRequest financialReportRequest) {
		
		Pageable pageable = PageRequest.of(Integer.valueOf(financialReportRequest.getPageNo()), 
				Integer.valueOf(financialReportRequest.getPageSize()));

		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();

		if(financialReportRequest.getServiceId() != null && financialReportRequest.getServiceId() != "") {
			jpql.append("SELECT mo FROM PullAccount mo WHERE " + "mo.createdAt >= '" + financialReportRequest.getDurationFrom()
			+ " 00:00:00' " + "AND mo.createdAt <= '" + financialReportRequest.getDurationTo() + " 23:59:59' " +
			"And mo.serviceId = '"+financialReportRequest.getServiceId()+"' ");
			jpqlCount.append("SELECT count(mo) FROM PullAccount mo WHERE " + "mo.createdAt >= '" + financialReportRequest.getDurationFrom()
			+ " 00:00:00' " + "AND mo.createdAt <= '" + financialReportRequest.getDurationTo() + " 23:59:59' "+
			"And mo.serviceId = '"+financialReportRequest.getServiceId()+"' ");
		}else {
			jpql.append("SELECT mo FROM PullAccount mo WHERE " + "mo.createdAt >= '" + financialReportRequest.getDurationFrom()
			+ " 00:00:00' " + "AND mo.createdAt <= '" + financialReportRequest.getDurationTo() + " 23:59:59' ");
			jpqlCount.append("SELECT count(mo) FROM PullAccount mo WHERE " + "mo.createdAt >= '" + financialReportRequest.getDurationFrom()
			+ " 00:00:00' " + "AND mo.createdAt <= '" + financialReportRequest.getDurationTo() + " 23:59:59' ");
		}
		
		TypedQuery<PullAccount> query = em.createQuery(jpql.toString(), PullAccount.class);
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		TypedQuery<Long> queryCount = em.createQuery(jpqlCount.toString(), Long.class);
		long total = queryCount.getSingleResult();

		return new PageImpl<PullAccount>(query.getResultList(), pageable, total);
	}

	@Override
	public List<FinancialAuditDTO> getReportPermissionDetails(FinancialReportRequest financialReportRequest) {
		List<FinancialAuditDTO> result = new ArrayList<FinancialAuditDTO>();
		StringBuilder jpql = new StringBuilder();
		
		
		if(financialReportRequest.getReportType() != null && 
				financialReportRequest.getReportType().equals("financialReview")) {
			
			jpql.append("SELECT"
//					+ " count(v.id) as count, v.amount as amount"
					+ " SUM(IF (v.status_fk != 'Refund', 1, 0)) as totalTransactions,"
					+ " SUM(IF (v.status_fk = 'Refund', 1, 0)) as totalRefundTransaction,"
					
					+ " CONCAT('', FORMAT(SUM(IF (v.status_fk != 'Refund', v.amount, 0)),2)) as totalTransactionAmount," 
					+ " CONCAT('', FORMAT(SUM(IF (v.status_fk = 'Refund', v.amount, 0)),2)) as totalRefundAmount,"
					+ " CONCAT('', FORMAT(v.amount,2)) v.amount as amount"
					
					+ " FROM transaction v "
					+ " WHERE v.settlement_code = '"+financialReportRequest.getSettlementCode()+"'"
					+ " AND v.service_id = "+financialReportRequest.getServiceId()+" "
//					+ " AND v.status_fk != 'Refund' "
					+ " GROUP BY v.amount");
			
		}else if(financialReportRequest.getReportType() != null && 
				financialReportRequest.getReportType().equals("daily")) {
			
			jpql.append("SELECT"
//					+ " count(v.id) as count, v.amount as amount"
					+ " SUM(IF (v.status_fk != 'Refund', 1, 0)) as totalTransactions,"
					+ " SUM(IF (v.status_fk = 'Refund', 1, 0)) as totalRefundTransaction,"
					
					+ " CONCAT('', FORMAT(SUM(IF (v.status_fk != 'Refund', v.amount, 0)),2)) as totalTransactionAmount," 
					+ " CONCAT('', FORMAT(SUM(IF (v.status_fk = 'Refund', v.amount, 0)),2))  as totalRefundAmount,"
					+ " CONCAT('', FORMAT(v.amount,2)) as amount"

					+ " FROM transaction v "
					+ " JOIN pull_account p ON p.id = v.pull_account_fk"
					+ " WHERE v.created_at >= '"+financialReportRequest.getDurationFrom()+" 00:00:00' "
					+ " AND v.created_at <= '"+financialReportRequest.getDurationTo()+" 23:59:59' "
//					+ " AND v.status_fk != 'Refund' "
					+ " AND v.service_id = "+financialReportRequest.getServiceId()+" "
					+ " AND (p.status_fk = 'Approved' OR p.status_fk = 'Pending') "
					+ " GROUP BY v.amount");
			
		}else if(financialReportRequest.getReportType() != null && 
				financialReportRequest.getReportType().equals("agent")) {
			
			jpql.append("SELECT"
//					+ " count(v.id) as count, v.amount as amount"
					+ " SUM(IF (v.status_fk != 'Refund', 1, 0)) as totalTransactions,"
					+ " SUM(IF (v.status_fk = 'Refund', 1, 0)) as totalRefundTransaction,"
					
					+ " CONCAT('', FORMAT(SUM(IF (v.status_fk != 'Refund', v.amount, 0)),2)) as totalTransactionAmount," 
					+ " CONCAT('', FORMAT(SUM(IF (v.status_fk = 'Refund', v.amount, 0)),2))  as totalRefundAmount,"
					+ " CONCAT('', FORMAT(v.amount,2)) as amount"

					+ " FROM transaction v "
					+ " JOIN pull_account p ON p.id = v.pull_account_fk"
					+ " WHERE v.created_at >= '"+financialReportRequest.getDurationFrom()+" 00:00:00' "
					+ " AND v.created_at <= '"+financialReportRequest.getDurationTo()+" 23:59:59' "
//					+ " AND v.status_fk != 'Refund' "
					+ " AND v.service_id = "+financialReportRequest.getServiceId()+" "
					+ " AND v.created_by = '"+financialReportRequest.getAgentName()+"' "
					+ " AND p.location_code = '"+financialReportRequest.getLocationCode()+"' "
					+ " AND (p.status_fk = 'Approved' OR p.status_fk = 'Pending') "
					+ " GROUP BY v.amount");
			
		}else if(financialReportRequest.getReportType() != null && 
				financialReportRequest.getReportType().equals("location")) {

			jpql.append("SELECT"
//					+ " count(v.id) as count, v.amount as amount"
					+ " SUM(IF (v.status_fk != 'Refund', 1, 0)) as totalTransactions,"
					+ " SUM(IF (v.status_fk = 'Refund', 1, 0)) as totalRefundTransaction,"
					
					+ " CONCAT('', FORMAT(SUM(IF (v.status_fk != 'Refund', v.amount, 0)),2)) as totalTransactionAmount," 
					+ " CONCAT('', FORMAT(SUM(IF (v.status_fk = 'Refund', v.amount, 0)),2))  as totalRefundAmount,"
					+ " CONCAT('', FORMAT(v.amount,2)) as amount"

					+ " FROM transaction v "
					+ " JOIN pull_account p ON p.id = v.pull_account_fk"
					+ " WHERE v.created_at >= '"+financialReportRequest.getDurationFrom()+" 00:00:00' "
					+ " AND v.created_at <= '"+financialReportRequest.getDurationTo()+" 23:59:59' "
//					+ " AND v.status_fk != 'Refund' "
					+ " AND v.service_id = "+financialReportRequest.getServiceId()+" "
					+ " AND p.location_code = '"+financialReportRequest.getLocationCode()+"' "
					+ " AND (p.status_fk = 'Approved' OR p.status_fk = 'Pending') "
					+ " GROUP BY v.amount");
			
		}else if(financialReportRequest.getReportType() != null && 
				financialReportRequest.getReportType().equals("pos")) {

			jpql.append("SELECT"
//					+ " count(v.id) as count, v.amount as amount"
					+ " SUM(IF (v.status_fk != 'Refund', 1, 0)) as totalTransactions,"
					+ " SUM(IF (v.status_fk = 'Refund', 1, 0)) as totalRefundTransaction,"
					
					+ " CONCAT('', FORMAT(SUM(IF (v.status_fk != 'Refund', v.amount, 0)),2)) as totalTransactionAmount," 
					+ " CONCAT('', FORMAT(SUM(IF (v.status_fk = 'Refund', v.amount, 0)),2))  as totalRefundAmount,"
					+ " CONCAT('', FORMAT(v.amount,2)) as amount"

					+ " FROM transaction v "
					+ " JOIN pull_account p ON p.id = v.pull_account_fk"
					+ " WHERE v.created_at >= '"+financialReportRequest.getDurationFrom()+" 00:00:00' "
					+ " AND v.created_at <= '"+financialReportRequest.getDurationTo()+" 23:59:59' "
//					+ " AND v.status_fk != 'Refund' "
					+ " AND v.service_id = "+financialReportRequest.getServiceId()+" "
					+ " AND v.pos_id = "+financialReportRequest.getPosId()+" "
					+ " AND (p.status_fk = 'Approved' OR p.status_fk = 'Pending') "
					+ " GROUP BY v.amount");
			
		}
						
		jpql.append(" ORDER BY v.amount desc");

		Query query = em.createNativeQuery(jpql.toString());

		List<Object[]> list = query.getResultList();
		
	    for(Object[] q1 : list){

//	    	String count = q1[0] != null ? q1[0].toString() : "";
//	        String amount = q1[1] != null ? q1[1].toString() : "";
	    	
	    	String totalTransactions = q1[0].toString();
		    String totalRefundTransaction = q1[1].toString();
		        
		    String totalTransactionAmount = q1[2].toString();
		    String totalRefundAmount = q1[3].toString();
		    
		    String groups = q1[4].toString();
	        
	        FinancialAuditDTO financialAuditDTO = new FinancialAuditDTO();
	        financialAuditDTO.setCount(
	        		String.valueOf(Long.valueOf(totalTransactions) - Long.valueOf(totalRefundTransaction)));
	        financialAuditDTO.setAmount(
	        		String.valueOf(Double.valueOf(totalTransactionAmount) - Double.valueOf(totalRefundAmount)));
	        financialAuditDTO.setGroups(groups);
	        
	        result.add(financialAuditDTO); 	      
	        
	     }

	    return result;
	}

	
}
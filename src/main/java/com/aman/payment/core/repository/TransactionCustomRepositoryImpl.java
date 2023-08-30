package com.aman.payment.core.repository;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.codehaus.stax2.ri.typed.ValueDecoderFactory.DecimalDecoder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.aman.payment.core.model.dto.FinanacialMidsDTO;
import com.aman.payment.core.model.payload.FinancialReportRequest;
import com.aman.payment.util.StatusConstant;

@Repository
public class TransactionCustomRepositoryImpl implements TransactionCustomRepository{

	@PersistenceContext
	private EntityManager em;

	public TransactionCustomRepositoryImpl(EntityManager em) {
		super();
		this.em = em;
	}

	@Override
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByTransaction(
			FinancialReportRequest financialReportRequest) {
		
		Pageable pageable = PageRequest.of(Integer.valueOf(financialReportRequest.getPageNo()), 
				Integer.valueOf(financialReportRequest.getPageSize()));

		List<FinanacialMidsDTO> result = new ArrayList<FinanacialMidsDTO>();
		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();

		String selectQuery = null;
		String countQuery = null;
		if(Long.valueOf(financialReportRequest.getServiceId()) == StatusConstant.BOOK_SERVICE_ID) {
			selectQuery = "SELECT v.created_at AS createdAt, v.created_by AS createdBy, "
					+ "v.amount AS totalAmount, "   
					+ "v.trans_code AS transactionCode, v.settlement_code AS settlementCode, v.status_fk AS transactionStatus, " 
					+ "p.status_fk AS pullAccountStatus, po.name AS posName, s.name AS sectorName, l.name AS locationName, "
					+ "c.name_ar AS cityName, ss.name AS typeName, "
					+ "(SELECT mid_value FROM transaction_mids WHERE beneficiary='امان' AND transaction_fk = v.id) AS amanAccount, "  
					+ "(SELECT mid_value FROM transaction_mids WHERE beneficiary='الأدارة المركزية لشئون الدمغة ورسم التنمية' AND transaction_fk = v.id) AS centralAccount, "  
					+ "(SELECT mid_value FROM transaction_mids WHERE beneficiary='حساب العميل' AND transaction_fk = v.id) AS nyabaAccount, "
					+ "r.payment_code AS paymentCode, r.ref_request_number AS refRequestNumber, co.name as courtName " 
					+ "FROM transaction v "
					+ "JOIN maazoun_book_request_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pull_account p ON p.id = v.pull_account_fk "
					+ "JOIN sub_service ss ON ss.id = r.book_type_id " 
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
			
			countQuery = "SELECT COUNT(*) " 
					+ "FROM transaction v "
					+ "JOIN maazoun_book_request_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pull_account p ON p.id = v.pull_account_fk " 
					+ "JOIN sub_service ss ON ss.id = r.book_type_id "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
		}else {
			selectQuery = "SELECT v.created_at AS createdAt, v.created_by AS createdBy, "
					+ "v.amount AS totalAmount, "   
					+ "v.trans_code AS transactionCode, v.settlement_code AS settlementCode, v.status_fk AS transactionStatus, " 
					+ "p.status_fk AS pullAccountStatus, po.name AS posName, s.name AS sectorName, l.name AS locationName, "
					+ "c.name_ar AS cityName, ss.name AS typeName, "
					+ "(SELECT mid_value FROM transaction_mids WHERE beneficiary='بنك ناصر' AND transaction_fk = v.id) AS bankNaserAccount, "  
					+ "(SELECT mid_value FROM transaction_mids WHERE beneficiary='وزارة المالية' AND transaction_fk = v.id) AS ministryFinancialAccount, "  
					+ "(SELECT mid_value FROM transaction_mids WHERE beneficiary='هيئة أبنية المحاكم' AND transaction_fk = v.id) AS abnyatMhakemAccount, "
					+ "(SELECT mid_value FROM transaction_mids WHERE beneficiary='جمعية المأذونيين' AND transaction_fk = v.id) AS gamyatMazouneenAccount, "
					+ "r.payment_code AS paymentCode, r.ref_request_number AS refRequestNumber, co.name as courtName "
					+ "FROM transaction v "
					+ "JOIN maazoun_book_collection_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pull_account p ON p.id = v.pull_account_fk " 
					+ "JOIN sub_service ss ON ss.id = r.contract_type_id "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
			
			countQuery = "SELECT COUNT(*) "
					+ "FROM transaction v "
					+ "JOIN maazoun_book_collection_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pull_account p ON p.id = v.pull_account_fk " 
					+ "JOIN sub_service ss ON ss.id = r.contract_type_id "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
		}
		
		String whereCondition = " WHERE v.created_at >= '" + financialReportRequest.getDurationFrom() + " 00:00:00' "
				+ " AND v.created_at <= '" + financialReportRequest.getDurationTo() + " 23:59:59' "
				+ " AND v.status_fk = 'Final' AND v.service_id = "+financialReportRequest.getServiceId()+" ";
		
		jpql.append(selectQuery);	
		jpql.append(whereCondition);
		
		jpqlCount.append(countQuery);
		jpqlCount.append(whereCondition);
		
		
		Query query = em.createNativeQuery(jpql.toString());
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Query queryCount = em.createNativeQuery(jpqlCount.toString());
		long total = Long.valueOf(queryCount.getResultList().get(0).toString());
		
		List<Object[]> list = query.getResultList();
		
		FinanacialMidsDTO totalFinanacialMidsDTO = new FinanacialMidsDTO();
		totalFinanacialMidsDTO.setRefRequestNumber("الإجمالى");
		totalFinanacialMidsDTO.setTotalAmount("0");
		totalFinanacialMidsDTO.setBookAmanAccount("0");
		totalFinanacialMidsDTO.setBookCentralAccount("0");
		totalFinanacialMidsDTO.setBookNyabaAccount("0");
		totalFinanacialMidsDTO.setContractBankNaserAccount("0");
		totalFinanacialMidsDTO.setContractFinancialMinistryAccount("0");
		totalFinanacialMidsDTO.setContractAbnyatMhakemAccount("0");
		totalFinanacialMidsDTO.setContractGamyatMazoounenAccount("0");
		totalFinanacialMidsDTO.setTotalAmountCreditors("0");
		
	    for(Object[] q1 : list){
		
	    	FinanacialMidsDTO eFinanacialMidsDTO = new FinanacialMidsDTO();
	    	
	    	String createdAt = q1[0].toString();
	    	String createdBy = q1[1].toString();
	    	String totalAmount = q1[2].toString();
	        String transactionCode = q1[3].toString();
	        String settlementCode = q1[4].toString();
	        String transactionStatus = q1[5].toString();
	        String pullAccountStatus = q1[6].toString();
	        String posName = q1[7].toString();
	        String sectorName = q1[8].toString();
	        String locationName = q1[9].toString();
	        String cityName = q1[10].toString();
	        String typeName = q1[11].toString();
	        
	        
	        eFinanacialMidsDTO.setCreatedDate(createdAt);
	        eFinanacialMidsDTO.setCreatedBy(createdBy);
	        eFinanacialMidsDTO.setTotalAmount(totalAmount);
	        eFinanacialMidsDTO.setTransactionCode(transactionCode);
	        eFinanacialMidsDTO.setSettlementCode(settlementCode);
	        eFinanacialMidsDTO.setTransactionStatus(transactionStatus);
	        eFinanacialMidsDTO.setPullAccountStatus(pullAccountStatus);
	        eFinanacialMidsDTO.setPosName(posName);
	        eFinanacialMidsDTO.setSectorName(sectorName);
	        eFinanacialMidsDTO.setLocationName(locationName);
	        eFinanacialMidsDTO.setCityName(cityName);
	        eFinanacialMidsDTO.setTypeName(typeName);
	        eFinanacialMidsDTO.setBookAmanAccount("0");
	        eFinanacialMidsDTO.setBookCentralAccount("0");
	        eFinanacialMidsDTO.setBookNyabaAccount("0");
	        eFinanacialMidsDTO.setContractBankNaserAccount("0");
	        eFinanacialMidsDTO.setContractFinancialMinistryAccount("0");
	        eFinanacialMidsDTO.setContractAbnyatMhakemAccount("0");
	        eFinanacialMidsDTO.setContractGamyatMazoounenAccount("0");
	        eFinanacialMidsDTO.setTotalAmountCreditors("0");
	        
	        
	        if(Long.valueOf(financialReportRequest.getServiceId()) == StatusConstant.BOOK_SERVICE_ID) {
	        	String amanAccount = (q1[12]!=null)?String.format("%.2f", q1[12]):"0";
		        String centralAccount = (q1[13]!=null)?String.format("%.2f", q1[13]):"0";
		        String nyabaAccount = (q1[14]!=null)?String.format("%.2f", q1[14]):"0";
		        String paymentCode = (q1[15]!=null)?q1[15].toString():"";
		        String refRequestNumber = q1[16].toString();
		        String courtName = q1[17].toString();
		        
		        eFinanacialMidsDTO.setBookAmanAccount(amanAccount);
		        eFinanacialMidsDTO.setBookCentralAccount(centralAccount);
		        eFinanacialMidsDTO.setBookNyabaAccount(nyabaAccount);
		        eFinanacialMidsDTO.setPaymentCode(paymentCode);
		        eFinanacialMidsDTO.setRefRequestNumber(refRequestNumber);
		        eFinanacialMidsDTO.setCourtName(courtName);
	        }else {
	        	String bankNaserAccount = (q1[12]!=null)?String.format("%.2f", q1[12]):"0";
		        String ministryFinancialAccount = (q1[13]!=null)?String.format("%.2f", q1[13]):"0";
		        String abnyatMhakemAccount = (q1[14]!=null)?String.format("%.2f", q1[14]):"0";
		        String gamyatMazouneenAccount = (q1[15]!=null)?String.format("%.2f", q1[15]):"0";
		        String paymentCode = (q1[16]!=null)?q1[16].toString():"";
		        String refRequestNumber = q1[17].toString();
		        String courtName = q1[18].toString();
		        
		        eFinanacialMidsDTO.setContractBankNaserAccount(bankNaserAccount);
		        eFinanacialMidsDTO.setContractFinancialMinistryAccount(ministryFinancialAccount);
		        eFinanacialMidsDTO.setContractAbnyatMhakemAccount(abnyatMhakemAccount);
		        eFinanacialMidsDTO.setContractGamyatMazoounenAccount(gamyatMazouneenAccount);
		        eFinanacialMidsDTO.setPaymentCode(paymentCode);
		        eFinanacialMidsDTO.setRefRequestNumber(refRequestNumber);
		        eFinanacialMidsDTO.setTotalAmountCreditors(
		        		String.format("%.2f", Double.valueOf(bankNaserAccount) + 
		        				Double.valueOf(ministryFinancialAccount) + 
		        				Double.valueOf(abnyatMhakemAccount)));
		        eFinanacialMidsDTO.setCourtName(courtName);
		        
	        }
	        
	        result.add(eFinanacialMidsDTO);	   
	        
	        accamulateObject(totalFinanacialMidsDTO, eFinanacialMidsDTO);
	        
	     }
	    
	    result.add(totalFinanacialMidsDTO);

	    return new PageImpl<FinanacialMidsDTO>(result, pageable, total);
	}
	
	@Override
	public Page<FinanacialMidsDTO> getRefundAuditReportFinancialMidDetailsByTransaction(
			FinancialReportRequest financialReportRequest) {
		
		Pageable pageable = PageRequest.of(Integer.valueOf(financialReportRequest.getPageNo()), 
				Integer.valueOf(financialReportRequest.getPageSize()));

		List<FinanacialMidsDTO> result = new ArrayList<FinanacialMidsDTO>();
		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();

		String selectQuery = null;
		String countQuery = null;
		if(Long.valueOf(financialReportRequest.getServiceId()) == StatusConstant.BOOK_SERVICE_ID) {
			selectQuery = "SELECT v.created_at AS createdAt, v.created_by AS createdBy, "
					+ "v.amount AS totalAmount, "   
					+ "v.trans_code AS transactionCode, v.settlement_code AS settlementCode, v.status_fk AS transactionStatus, " 
					+ "p.status_fk AS pullAccountStatus, po.name AS posName, s.name AS sectorName, l.name AS locationName, "
					+ "c.name_ar AS cityName, ss.name AS typeName, "
					+ "(SELECT mid_value FROM transaction_mids WHERE beneficiary='امان' AND transaction_fk = v.id) AS amanAccount, "  
					+ "(SELECT mid_value FROM transaction_mids WHERE beneficiary='الأدارة المركزية لشئون الدمغة ورسم التنمية' AND transaction_fk = v.id) AS centralAccount, "  
					+ "(SELECT mid_value FROM transaction_mids WHERE beneficiary='حساب العميل' AND transaction_fk = v.id) AS nyabaAccount, " 
					+ "r.payment_code AS paymentCode, r.ref_request_number AS refRequestNumber, co.name as courtName, "
					+ "(SELECT trans_code from transaction WHERE id = v.refund_parent_id) as canceledTransCode, "
					+ "(SELECT created_at from transaction WHERE id = v.refund_parent_id) as canceledTransDate "
					+ "FROM transaction v "
					+ "JOIN maazoun_book_request_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pull_account p ON p.id = v.pull_account_fk "
					+ "JOIN sub_service ss ON ss.id = r.book_type_id " 
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
			
			countQuery = "SELECT COUNT(*) " 
					+ "FROM transaction v "
					+ "JOIN maazoun_book_request_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pull_account p ON p.id = v.pull_account_fk " 
					+ "JOIN sub_service ss ON ss.id = r.book_type_id "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
		}else {
			selectQuery = "SELECT v.created_at AS createdAt, v.created_by AS createdBy, "
					+ "v.amount AS totalAmount, "   
					+ "v.trans_code AS transactionCode, v.settlement_code AS settlementCode, v.status_fk AS transactionStatus, " 
					+ "p.status_fk AS pullAccountStatus, po.name AS posName, s.name AS sectorName, l.name AS locationName, "
					+ "c.name_ar AS cityName, ss.name AS typeName, "
					+ "(SELECT mid_value FROM transaction_mids WHERE beneficiary='بنك ناصر' AND transaction_fk = v.id) AS bankNaserAccount, "  
					+ "(SELECT mid_value FROM transaction_mids WHERE beneficiary='وزارة المالية' AND transaction_fk = v.id) AS ministryFinancialAccount, "  
					+ "(SELECT mid_value FROM transaction_mids WHERE beneficiary='هيئة أبنية المحاكم' AND transaction_fk = v.id) AS abnyatMhakemAccount, "
					+ "(SELECT mid_value FROM transaction_mids WHERE beneficiary='جمعية المأذونيين' AND transaction_fk = v.id) AS gamyatMazouneenAccount, "
					+ "r.payment_code AS paymentCode, r.ref_request_number AS refRequestNumber, co.name as courtName, "
					+ "(SELECT trans_code from transaction WHERE id = v.refund_parent_id) as canceledTransCode, "
					+ "(SELECT created_at from transaction WHERE id = v.refund_parent_id) as canceledTransDate "
					+ "FROM transaction v "
					+ "JOIN maazoun_book_collection_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pull_account p ON p.id = v.pull_account_fk " 
					+ "JOIN sub_service ss ON ss.id = r.contract_type_id "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
			
			countQuery = "SELECT COUNT(*) "
					+ "FROM transaction v "
					+ "JOIN maazoun_book_collection_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pull_account p ON p.id = v.pull_account_fk " 
					+ "JOIN sub_service ss ON ss.id = r.contract_type_id "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
		}
		
		String whereCondition = " WHERE v.created_at >= '" + financialReportRequest.getDurationFrom() + " 00:00:00' "
				+ " AND v.created_at <= '" + financialReportRequest.getDurationTo() + " 23:59:59' "
				+ " AND v.status_fk = 'Refund' AND v.service_id = "+financialReportRequest.getServiceId()+" ";
		
		jpql.append(selectQuery);	
		jpql.append(whereCondition);
		
		jpqlCount.append(countQuery);
		jpqlCount.append(whereCondition);
		
		Query query = em.createNativeQuery(jpql.toString());
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Query queryCount = em.createNativeQuery(jpqlCount.toString());
		long total = Long.valueOf(queryCount.getResultList().get(0).toString());
		
		List<Object[]> list = query.getResultList();
		
		FinanacialMidsDTO totalFinanacialMidsDTO = new FinanacialMidsDTO();
		totalFinanacialMidsDTO.setRefRequestNumber("الإجمالى");
		totalFinanacialMidsDTO.setTotalAmount("0");
		totalFinanacialMidsDTO.setBookAmanAccount("0");
		totalFinanacialMidsDTO.setBookCentralAccount("0");
		totalFinanacialMidsDTO.setBookNyabaAccount("0");
		totalFinanacialMidsDTO.setContractBankNaserAccount("0");
		totalFinanacialMidsDTO.setContractFinancialMinistryAccount("0");
		totalFinanacialMidsDTO.setContractAbnyatMhakemAccount("0");
		totalFinanacialMidsDTO.setContractGamyatMazoounenAccount("0");
		totalFinanacialMidsDTO.setTotalAmountCreditors("0");
		
	    for(Object[] q1 : list){
		
	    	FinanacialMidsDTO eFinanacialMidsDTO = new FinanacialMidsDTO();
	    	
	    	String createdAt = q1[0].toString();
	    	String createdBy = q1[1].toString();
	    	String totalAmount = q1[2].toString();
	        String transactionCode = q1[3].toString();
	        String settlementCode = q1[4].toString();
	        String transactionStatus = q1[5].toString();
	        String pullAccountStatus = q1[6].toString();
	        String posName = q1[7].toString();
	        String sectorName = q1[8].toString();
	        String locationName = q1[9].toString();
	        String cityName = q1[10].toString();
	        String typeName = q1[11].toString();
	        
	        
	        eFinanacialMidsDTO.setCreatedDate(createdAt);
	        eFinanacialMidsDTO.setCreatedBy(createdBy);
	        eFinanacialMidsDTO.setTotalAmount(totalAmount);
	        eFinanacialMidsDTO.setTransactionCode(transactionCode);
	        eFinanacialMidsDTO.setSettlementCode(settlementCode);
	        eFinanacialMidsDTO.setTransactionStatus(transactionStatus);
	        eFinanacialMidsDTO.setPullAccountStatus(pullAccountStatus);
	        eFinanacialMidsDTO.setPosName(posName);
	        eFinanacialMidsDTO.setSectorName(sectorName);
	        eFinanacialMidsDTO.setLocationName(locationName);
	        eFinanacialMidsDTO.setCityName(cityName);
	        eFinanacialMidsDTO.setTypeName(typeName);
	        eFinanacialMidsDTO.setBookAmanAccount("0");
	        eFinanacialMidsDTO.setBookCentralAccount("0");
	        eFinanacialMidsDTO.setBookNyabaAccount("0");
	        eFinanacialMidsDTO.setContractBankNaserAccount("0");
	        eFinanacialMidsDTO.setContractFinancialMinistryAccount("0");
	        eFinanacialMidsDTO.setContractAbnyatMhakemAccount("0");
	        eFinanacialMidsDTO.setContractGamyatMazoounenAccount("0");
	        eFinanacialMidsDTO.setTotalAmountCreditors("0");
	        
	        
	        if(Long.valueOf(financialReportRequest.getServiceId()) == StatusConstant.BOOK_SERVICE_ID) {
	        	String amanAccount = (q1[12]!=null)?String.format("%.2f", q1[12]):"0";
		        String centralAccount = (q1[13]!=null)?String.format("%.2f", q1[13]):"0";
		        String nyabaAccount = (q1[14]!=null)?String.format("%.2f", q1[14]):"0";
		        String paymentCode = (q1[15]!=null)?q1[15].toString():"";
		        String refRequestNumber = q1[16].toString();
		        String courtName = q1[17].toString();
		        String canceledTransCode = q1[18].toString();
		        String canceledTransDate = q1[19].toString();
		        
		        eFinanacialMidsDTO.setBookAmanAccount(amanAccount);
		        eFinanacialMidsDTO.setBookCentralAccount(centralAccount);
		        eFinanacialMidsDTO.setBookNyabaAccount(nyabaAccount);
		        eFinanacialMidsDTO.setPaymentCode(paymentCode);
		        eFinanacialMidsDTO.setRefRequestNumber(refRequestNumber);
		        eFinanacialMidsDTO.setCourtName(courtName);
		        eFinanacialMidsDTO.setCanceledCreatedDate(canceledTransDate);
		        eFinanacialMidsDTO.setCanceledTransactionCode(canceledTransCode);
	        }else {
	        	String bankNaserAccount = (q1[12]!=null)?String.format("%.2f", q1[12]):"0";
		        String ministryFinancialAccount = (q1[13]!=null)?String.format("%.2f", q1[13]):"0";
		        String abnyatMhakemAccount = (q1[14]!=null)?String.format("%.2f", q1[14]):"0";
		        String gamyatMazouneenAccount = (q1[15]!=null)?String.format("%.2f", q1[15]):"0";
		        String paymentCode = (q1[16]!=null)?q1[16].toString():"";
		        String refRequestNumber = q1[17].toString();
		        String courtName = q1[18].toString();
		        String canceledTransCode = q1[19].toString();
		        String canceledTransDate = q1[20].toString();
		        
		        eFinanacialMidsDTO.setContractBankNaserAccount(bankNaserAccount);
		        eFinanacialMidsDTO.setContractFinancialMinistryAccount(ministryFinancialAccount);
		        eFinanacialMidsDTO.setContractAbnyatMhakemAccount(abnyatMhakemAccount);
		        eFinanacialMidsDTO.setContractGamyatMazoounenAccount(gamyatMazouneenAccount);
		        eFinanacialMidsDTO.setPaymentCode(paymentCode);
		        eFinanacialMidsDTO.setRefRequestNumber(refRequestNumber);
		        eFinanacialMidsDTO.setTotalAmountCreditors(
		        		String.format("%.2f", Double.valueOf(bankNaserAccount) + 
		        				Double.valueOf(ministryFinancialAccount) + 
		        				Double.valueOf(abnyatMhakemAccount)));
		        eFinanacialMidsDTO.setCourtName(courtName);
		        eFinanacialMidsDTO.setCanceledCreatedDate(canceledTransDate);
		        eFinanacialMidsDTO.setCanceledTransactionCode(canceledTransCode);
	        }
	        
	        result.add(eFinanacialMidsDTO);	   
	        
	        accamulateObject(totalFinanacialMidsDTO, eFinanacialMidsDTO);
	        
	     }

	    result.add(totalFinanacialMidsDTO);	
	    
	    return new PageImpl<FinanacialMidsDTO>(result, pageable, total);
	}
	
	@Override
	public Page<FinanacialMidsDTO> getCanceledAuditReportFinancialMidDetailsByTransaction(
			FinancialReportRequest financialReportRequest) {
		
		Pageable pageable = PageRequest.of(Integer.valueOf(financialReportRequest.getPageNo()), 
				Integer.valueOf(financialReportRequest.getPageSize()));

		List<FinanacialMidsDTO> result = new ArrayList<FinanacialMidsDTO>();
		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();

		String selectQuery = null;
		String countQuery = null;
		if(Long.valueOf(financialReportRequest.getServiceId()) == StatusConstant.BOOK_SERVICE_ID) {
			selectQuery = "SELECT v.created_at AS createdAt, v.created_by AS createdBy, "
					+ "v.amount AS totalAmount, "   
					+ "v.trans_code AS transactionCode, v.settlement_code AS settlementCode, v.status_fk AS transactionStatus, " 
					+ "p.status_fk AS pullAccountStatus, po.name AS posName, s.name AS sectorName, l.name AS locationName, "
					+ "c.name_ar AS cityName, ss.name AS typeName, "
					+ "(SELECT mid_value FROM transaction_mids WHERE beneficiary='امان' AND transaction_fk = v.id) AS amanAccount, "  
					+ "(SELECT mid_value FROM transaction_mids WHERE beneficiary='الأدارة المركزية لشئون الدمغة ورسم التنمية' AND transaction_fk = v.id) AS centralAccount, "  
					+ "(SELECT mid_value FROM transaction_mids WHERE beneficiary='حساب العميل' AND transaction_fk = v.id) AS nyabaAccount, " 
					+ "r.payment_code AS paymentCode, r.ref_request_number AS refRequestNumber, co.name as courtName "
					+ "FROM transaction v "
					+ "JOIN maazoun_book_request_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pull_account p ON p.id = v.pull_account_fk "
					+ "JOIN sub_service ss ON ss.id = r.book_type_id " 
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
			
			countQuery = "SELECT COUNT(*) " 
					+ "FROM transaction v "
					+ "JOIN maazoun_book_request_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pull_account p ON p.id = v.pull_account_fk " 
					+ "JOIN sub_service ss ON ss.id = r.book_type_id "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
		}else {
			selectQuery = "SELECT v.created_at AS createdAt, v.created_by AS createdBy, "
					+ "v.amount AS totalAmount, "   
					+ "v.trans_code AS transactionCode, v.settlement_code AS settlementCode, v.status_fk AS transactionStatus, " 
					+ "p.status_fk AS pullAccountStatus, po.name AS posName, s.name AS sectorName, l.name AS locationName, "
					+ "c.name_ar AS cityName, ss.name AS typeName, "
					+ "(SELECT mid_value FROM transaction_mids WHERE beneficiary='بنك ناصر' AND transaction_fk = v.id) AS bankNaserAccount, "  
					+ "(SELECT mid_value FROM transaction_mids WHERE beneficiary='وزارة المالية' AND transaction_fk = v.id) AS ministryFinancialAccount, "  
					+ "(SELECT mid_value FROM transaction_mids WHERE beneficiary='هيئة أبنية المحاكم' AND transaction_fk = v.id) AS abnyatMhakemAccount, "
					+ "(SELECT mid_value FROM transaction_mids WHERE beneficiary='جمعية المأذونيين' AND transaction_fk = v.id) AS gamyatMazouneenAccount, "
					+ "r.payment_code AS paymentCode, r.ref_request_number AS refRequestNumber, co.name as courtName "
					+ "FROM transaction v "
					+ "JOIN maazoun_book_collection_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pull_account p ON p.id = v.pull_account_fk " 
					+ "JOIN sub_service ss ON ss.id = r.contract_type_id "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
			
			countQuery = "SELECT COUNT(*) "
					+ "FROM transaction v "
					+ "JOIN maazoun_book_collection_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pull_account p ON p.id = v.pull_account_fk " 
					+ "JOIN sub_service ss ON ss.id = r.contract_type_id "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
		}
		
		String whereCondition = " WHERE v.created_at >= '" + financialReportRequest.getDurationFrom() + " 00:00:00' "
				+ " AND v.created_at <= '" + financialReportRequest.getDurationTo() + " 23:59:59' "
				+ " AND v.status_fk = 'Canceled' AND v.service_id = "+financialReportRequest.getServiceId()+" ";
		
		jpql.append(selectQuery);	
		jpql.append(whereCondition);
		
		jpqlCount.append(countQuery);
		jpqlCount.append(whereCondition);
		
		
		Query query = em.createNativeQuery(jpql.toString());
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Query queryCount = em.createNativeQuery(jpqlCount.toString());
		long total = Long.valueOf(queryCount.getResultList().get(0).toString());
		
		List<Object[]> list = query.getResultList();
		
		FinanacialMidsDTO totalFinanacialMidsDTO = new FinanacialMidsDTO();
		totalFinanacialMidsDTO.setRefRequestNumber("الإجمالى");
		totalFinanacialMidsDTO.setTotalAmount("0");
		totalFinanacialMidsDTO.setBookAmanAccount("0");
		totalFinanacialMidsDTO.setBookCentralAccount("0");
		totalFinanacialMidsDTO.setBookNyabaAccount("0");
		totalFinanacialMidsDTO.setContractBankNaserAccount("0");
		totalFinanacialMidsDTO.setContractFinancialMinistryAccount("0");
		totalFinanacialMidsDTO.setContractAbnyatMhakemAccount("0");
		totalFinanacialMidsDTO.setContractGamyatMazoounenAccount("0");
		totalFinanacialMidsDTO.setTotalAmountCreditors("0");
		
	    for(Object[] q1 : list){
		
	    	FinanacialMidsDTO eFinanacialMidsDTO = new FinanacialMidsDTO();
	    	
	    	String createdAt = q1[0].toString();
	    	String createdBy = q1[1].toString();
	    	String totalAmount = q1[2].toString();
	        String transactionCode = q1[3].toString();
	        String settlementCode = q1[4].toString();
	        String transactionStatus = q1[5].toString();
	        String pullAccountStatus = q1[6].toString();
	        String posName = q1[7].toString();
	        String sectorName = q1[8].toString();
	        String locationName = q1[9].toString();
	        String cityName = q1[10].toString();
	        String typeName = q1[11].toString();
	        
	        
	        eFinanacialMidsDTO.setCreatedDate(createdAt);
	        eFinanacialMidsDTO.setCreatedBy(createdBy);
	        eFinanacialMidsDTO.setTotalAmount(totalAmount);
	        eFinanacialMidsDTO.setTransactionCode(transactionCode);
	        eFinanacialMidsDTO.setSettlementCode(settlementCode);
	        eFinanacialMidsDTO.setTransactionStatus(transactionStatus);
	        eFinanacialMidsDTO.setPullAccountStatus(pullAccountStatus);
	        eFinanacialMidsDTO.setPosName(posName);
	        eFinanacialMidsDTO.setSectorName(sectorName);
	        eFinanacialMidsDTO.setLocationName(locationName);
	        eFinanacialMidsDTO.setCityName(cityName);
	        eFinanacialMidsDTO.setTypeName(typeName);
	        eFinanacialMidsDTO.setBookAmanAccount("0");
	        eFinanacialMidsDTO.setBookCentralAccount("0");
	        eFinanacialMidsDTO.setBookNyabaAccount("0");
	        eFinanacialMidsDTO.setContractBankNaserAccount("0");
	        eFinanacialMidsDTO.setContractFinancialMinistryAccount("0");
	        eFinanacialMidsDTO.setContractAbnyatMhakemAccount("0");
	        eFinanacialMidsDTO.setContractGamyatMazoounenAccount("0");
	        eFinanacialMidsDTO.setTotalAmountCreditors("0");
	        
	        
	        if(Long.valueOf(financialReportRequest.getServiceId()) == StatusConstant.BOOK_SERVICE_ID) {
	        	String amanAccount = (q1[12]!=null)?String.format("%.2f", q1[12]):"0";
		        String centralAccount = (q1[13]!=null)?String.format("%.2f", q1[13]):"0";
		        String nyabaAccount = (q1[14]!=null)?String.format("%.2f", q1[14]):"0";
		        String paymentCode = (q1[15]!=null)?q1[15].toString():"";
		        String refRequestNumber = q1[16].toString();
		        String courtName = q1[17].toString();
		        
		        eFinanacialMidsDTO.setBookAmanAccount(amanAccount);
		        eFinanacialMidsDTO.setBookCentralAccount(centralAccount);
		        eFinanacialMidsDTO.setBookNyabaAccount(nyabaAccount);
		        eFinanacialMidsDTO.setPaymentCode(paymentCode);
		        eFinanacialMidsDTO.setRefRequestNumber(refRequestNumber);
		        eFinanacialMidsDTO.setCourtName(courtName);
	        }else {
	        	String bankNaserAccount = (q1[12]!=null)?String.format("%.2f", q1[12]):"0";
		        String ministryFinancialAccount = (q1[13]!=null)?String.format("%.2f", q1[13]):"0";
		        String abnyatMhakemAccount = (q1[14]!=null)?String.format("%.2f", q1[14]):"0";
		        String gamyatMazouneenAccount = (q1[15]!=null)?String.format("%.2f", q1[15]):"0";
		        String paymentCode = (q1[16]!=null)?q1[16].toString():"";
		        String refRequestNumber = q1[17].toString();
		        String courtName = q1[18].toString();
		        
		        eFinanacialMidsDTO.setContractBankNaserAccount(bankNaserAccount);
		        eFinanacialMidsDTO.setContractFinancialMinistryAccount(ministryFinancialAccount);
		        eFinanacialMidsDTO.setContractAbnyatMhakemAccount(abnyatMhakemAccount);
		        eFinanacialMidsDTO.setContractGamyatMazoounenAccount(gamyatMazouneenAccount);
		        eFinanacialMidsDTO.setPaymentCode(paymentCode);
		        eFinanacialMidsDTO.setRefRequestNumber(refRequestNumber);
		        eFinanacialMidsDTO.setTotalAmountCreditors(
		        		String.format("%.2f", Double.valueOf(bankNaserAccount) + 
		        				Double.valueOf(ministryFinancialAccount) + 
		        				Double.valueOf(abnyatMhakemAccount)));
		        eFinanacialMidsDTO.setCourtName(courtName);
	        }
	        
	        result.add(eFinanacialMidsDTO);	   
	        
	        accamulateObject(totalFinanacialMidsDTO, eFinanacialMidsDTO);
	        
	     }

	    result.add(totalFinanacialMidsDTO);	
	    
	    return new PageImpl<FinanacialMidsDTO>(result, pageable, total);
	}

	@Override
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByPOS(
			FinancialReportRequest financialReportRequest) {
		Pageable pageable = PageRequest.of(Integer.valueOf(financialReportRequest.getPageNo()), 
				Integer.valueOf(financialReportRequest.getPageSize()));

		List<FinanacialMidsDTO> result = new ArrayList<FinanacialMidsDTO>();
		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();

		String selectQuery = null;
		String countQuery = null;
		if(Long.valueOf(financialReportRequest.getServiceId()) == StatusConstant.BOOK_SERVICE_ID) {
			selectQuery = "SELECT SUM(v.amount) AS totalAmount, "
					+ "po.name AS posName, s.name AS sectorName, l.name AS locationName, c.name_ar AS cityName, po.id AS posId, "
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='امان' AND transaction_fk = v.id))  AS amanAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='الأدارة المركزية لشئون الدمغة ورسم التنمية' AND transaction_fk = v.id)) AS centralAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='حساب العميل' AND transaction_fk = v.id))  AS nyabaAccount, co.name as courtName "
					+ "FROM transaction v "
					+ "JOIN maazoun_book_request_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
			
			countQuery = "SELECT COUNT(*) " 
					+ "FROM transaction v "
					+ "JOIN maazoun_book_request_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
		}else {
			selectQuery = "SELECT SUM(v.amount) AS totalAmount, "  
					+ "po.name AS posName, s.name AS sectorName, l.name AS locationName, c.name_ar AS cityName, po.id AS posId, "
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='بنك ناصر' AND transaction_fk = v.id)) AS bankNaserAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='وزارة المالية' AND transaction_fk = v.id)) AS ministryFinancialAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='هيئة أبنية المحاكم' AND transaction_fk = v.id)) AS abnyatMhakemAccount, "
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='جمعية المأذونيين' AND transaction_fk = v.id)) AS gamyatMazouneenAccount, co.name as courtName "
					+ "FROM transaction v "
					+ "JOIN maazoun_book_collection_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
			
			countQuery = "SELECT COUNT(*) "
					+ "FROM transaction v "
					+ "JOIN maazoun_book_collection_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
		}
		
		String whereCondition = " WHERE v.created_at >= '" + financialReportRequest.getDurationFrom() + " 00:00:00' "
				+ " AND v.created_at <= '" + financialReportRequest.getDurationTo() + " 23:59:59' "
				+ " AND v.status_fk = 'Final' AND v.service_id = "+financialReportRequest.getServiceId()+" "
						+ " GROUP BY v.pos_id";
		
		jpql.append(selectQuery);	
		jpql.append(whereCondition);
		
		jpqlCount.append(countQuery);
		jpqlCount.append(whereCondition);
		
		
		Query query = em.createNativeQuery(jpql.toString());
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Query queryCount = em.createNativeQuery(jpqlCount.toString());
		long total = queryCount.getResultList().size();
		
		List<Object[]> list = query.getResultList();
		
		FinanacialMidsDTO totalFinanacialMidsDTO = new FinanacialMidsDTO();
		totalFinanacialMidsDTO.setPosName("الإجمالى");
		totalFinanacialMidsDTO.setTotalAmount("0");
		totalFinanacialMidsDTO.setBookAmanAccount("0");
		totalFinanacialMidsDTO.setBookCentralAccount("0");
		totalFinanacialMidsDTO.setBookNyabaAccount("0");
		totalFinanacialMidsDTO.setContractBankNaserAccount("0");
		totalFinanacialMidsDTO.setContractFinancialMinistryAccount("0");
		totalFinanacialMidsDTO.setContractAbnyatMhakemAccount("0");
		totalFinanacialMidsDTO.setContractGamyatMazoounenAccount("0");
		totalFinanacialMidsDTO.setTotalAmountCreditors("0");
		
	    for(Object[] q1 : list){
		
	    	FinanacialMidsDTO eFinanacialMidsDTO = new FinanacialMidsDTO();
	    	
	    	String totalAmount = String.format("%.2f", q1[0]);
	        String posName = q1[1].toString();
	        String sectorName = q1[2].toString();
	        String locationName = q1[3].toString();
	        String cityName = q1[4].toString();
	        String posId = q1[5].toString();

	        eFinanacialMidsDTO.setTotalAmount(totalAmount);
	        eFinanacialMidsDTO.setPosName(posName);
	        eFinanacialMidsDTO.setPosId(posId);
	        eFinanacialMidsDTO.setSectorName(sectorName);
	        eFinanacialMidsDTO.setLocationName(locationName);
	        eFinanacialMidsDTO.setCityName(cityName);
	        eFinanacialMidsDTO.setBookAmanAccount("0");
	        eFinanacialMidsDTO.setBookCentralAccount("0");
	        eFinanacialMidsDTO.setBookNyabaAccount("0");
	        eFinanacialMidsDTO.setContractBankNaserAccount("0");
	        eFinanacialMidsDTO.setContractFinancialMinistryAccount("0");
	        eFinanacialMidsDTO.setContractAbnyatMhakemAccount("0");
	        eFinanacialMidsDTO.setContractGamyatMazoounenAccount("0");
	        eFinanacialMidsDTO.setTotalAmountCreditors("0");
	        
	        if(Long.valueOf(financialReportRequest.getServiceId()) == StatusConstant.BOOK_SERVICE_ID) {
	        	String amanAccount = (q1[6]!=null)?String.format("%.2f", q1[6]):"0";
		        String centralAccount = (q1[7]!=null)?String.format("%.2f", q1[7]):"0";
		        String nyabaAccount = (q1[8]!=null)?String.format("%.2f", q1[8]):"0";
		        String courtName = q1[9].toString();
		        
		        eFinanacialMidsDTO.setBookAmanAccount(amanAccount);
		        eFinanacialMidsDTO.setBookCentralAccount(centralAccount);
		        eFinanacialMidsDTO.setBookNyabaAccount(nyabaAccount);
		        eFinanacialMidsDTO.setCourtName(courtName);
	        }else {
	        	String bankNaserAccount = (q1[6]!=null)?String.format("%.2f", q1[6]):"0";
		        String ministryFinancialAccount = (q1[7]!=null)?String.format("%.2f", q1[7]):"0";
		        String abnyatMhakemAccount = (q1[8]!=null)?String.format("%.2f", q1[8]):"0";
		        String gamyatMazouneenAccount = (q1[9]!=null)?String.format("%.2f", q1[9]):"0";
		        String courtName = q1[10].toString();
		        
		        eFinanacialMidsDTO.setContractBankNaserAccount(bankNaserAccount);
		        eFinanacialMidsDTO.setContractFinancialMinistryAccount(ministryFinancialAccount);
		        eFinanacialMidsDTO.setContractAbnyatMhakemAccount(abnyatMhakemAccount);
		        eFinanacialMidsDTO.setContractGamyatMazoounenAccount(gamyatMazouneenAccount);
		        eFinanacialMidsDTO.setTotalAmountCreditors(
		        		String.format("%.2f", Double.valueOf(bankNaserAccount) + 
		        				Double.valueOf(ministryFinancialAccount) + 
		        				Double.valueOf(abnyatMhakemAccount)));
		        eFinanacialMidsDTO.setCourtName(courtName);
	        }
	        
	        result.add(eFinanacialMidsDTO);	    
	        
	        accamulateObject(totalFinanacialMidsDTO, eFinanacialMidsDTO);
	        
	     }
	    
	    result.add(totalFinanacialMidsDTO);	

	    return new PageImpl<FinanacialMidsDTO>(result, pageable, total);
	}

	@Override
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsBySector(
			FinancialReportRequest financialReportRequest) {
		Pageable pageable = PageRequest.of(Integer.valueOf(financialReportRequest.getPageNo()), 
				Integer.valueOf(financialReportRequest.getPageSize()));

		List<FinanacialMidsDTO> result = new ArrayList<FinanacialMidsDTO>();
		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();

		String selectQuery = null;
		String countQuery = null;
		if(Long.valueOf(financialReportRequest.getServiceId()) == StatusConstant.BOOK_SERVICE_ID) {
			selectQuery = "SELECT SUM(IF (v.status_fk = 'Final' || v.status_fk = 'Canceled', v.amount, 0)) AS totalAmountIncludingCanceled, "
					+ "s.name AS sectorName, l.name AS locationName, c.name_ar AS cityName, "
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='امان' AND transaction_fk = v.id)) AS amanAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='الأدارة المركزية لشئون الدمغة ورسم التنمية' AND transaction_fk = v.id)) AS centralAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='حساب العميل' AND transaction_fk = v.id)) AS nyabaAccount, co.name as courtName, " 
					+ "SUM(IF (v.status_fk = 'Refund', v.amount, 0)) AS totalAmountRefund, "
					+ "SUM(IF (v.status_fk = 'Canceled', v.amount, 0)) AS totalAmountCanceled "
					+ "FROM transaction v "
					+ "JOIN maazoun_book_request_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
			
			countQuery = "SELECT COUNT(*) " 
					+ "FROM transaction v "
					+ "JOIN maazoun_book_request_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
		}else {
			selectQuery = "SELECT SUM(IF (v.status_fk = 'Final' || v.status_fk = 'Canceled', v.amount, 0)) AS totalAmountIncludingCanceled, "  
					+ "s.name AS sectorName, l.name AS locationName, c.name_ar AS cityName, "
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='بنك ناصر' AND transaction_fk = v.id)) AS bankNaserAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='وزارة المالية' AND transaction_fk = v.id)) AS ministryFinancialAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='هيئة أبنية المحاكم' AND transaction_fk = v.id)) AS abnyatMhakemAccount, "
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='جمعية المأذونيين' AND transaction_fk = v.id)) AS gamyatMazouneenAccount, co.name as courtName, "
					+ "SUM(IF (v.status_fk = 'Refund', v.amount, 0)) AS totalAmountRefund, "
					+ "SUM(IF (v.status_fk = 'Canceled', v.amount, 0)) AS totalAmountCanceled "
					+ "FROM transaction v "
					+ "JOIN maazoun_book_collection_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
			
			countQuery = "SELECT COUNT(*) "
					+ "FROM transaction v "
					+ "JOIN maazoun_book_collection_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
		}
		
		String whereCondition = " WHERE v.created_at >= '" + financialReportRequest.getDurationFrom() + " 00:00:00' "
				+ " AND v.created_at <= '" + financialReportRequest.getDurationTo() + " 23:59:59' "
				+ " AND v.service_id = "+financialReportRequest.getServiceId()+" "
						+ " GROUP BY r.sector_fk";
		
		jpql.append(selectQuery);	
		jpql.append(whereCondition);
		
		jpqlCount.append(countQuery);
		jpqlCount.append(whereCondition);
		
		
		Query query = em.createNativeQuery(jpql.toString());
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Query queryCount = em.createNativeQuery(jpqlCount.toString());
		long total = queryCount.getResultList().size();
		
		List<Object[]> list = query.getResultList();
		
		FinanacialMidsDTO totalFinanacialMidsDTO = new FinanacialMidsDTO();
		totalFinanacialMidsDTO.setSectorName("الإجمالى");
		totalFinanacialMidsDTO.setTotalAmountAndCanceled("0");
		totalFinanacialMidsDTO.setTotalCanceledAmount("0");
		totalFinanacialMidsDTO.setTotalRefundAmount("0");
		totalFinanacialMidsDTO.setTotalAmount("0");
		totalFinanacialMidsDTO.setBookAmanAccount("0");
		totalFinanacialMidsDTO.setBookCentralAccount("0");
		totalFinanacialMidsDTO.setBookNyabaAccount("0");
		totalFinanacialMidsDTO.setContractBankNaserAccount("0");
		totalFinanacialMidsDTO.setContractFinancialMinistryAccount("0");
		totalFinanacialMidsDTO.setContractAbnyatMhakemAccount("0");
		totalFinanacialMidsDTO.setContractGamyatMazoounenAccount("0");
		totalFinanacialMidsDTO.setTotalAmountCreditors("0");
		
	    for(Object[] q1 : list){
		
	    	FinanacialMidsDTO eFinanacialMidsDTO = new FinanacialMidsDTO();
	    	
	    	String totalAmountIncludingCanceled = String.format("%.2f", q1[0]);
	        String sectorName = q1[1].toString();
	        String locationName = q1[2].toString();
	        String cityName = q1[3].toString();
	        String totalRefundAmount;
	        String totalCanceledAmount;

	        eFinanacialMidsDTO.setTotalAmountAndCanceled(totalAmountIncludingCanceled);
	        eFinanacialMidsDTO.setSectorName(sectorName);
	        eFinanacialMidsDTO.setLocationName(locationName);
	        eFinanacialMidsDTO.setCityName(cityName);
	        eFinanacialMidsDTO.setBookAmanAccount("0");
	        eFinanacialMidsDTO.setBookCentralAccount("0");
	        eFinanacialMidsDTO.setBookNyabaAccount("0");
	        eFinanacialMidsDTO.setContractBankNaserAccount("0");
	        eFinanacialMidsDTO.setContractFinancialMinistryAccount("0");
	        eFinanacialMidsDTO.setContractAbnyatMhakemAccount("0");
	        eFinanacialMidsDTO.setContractGamyatMazoounenAccount("0");
	        eFinanacialMidsDTO.setTotalAmountCreditors("0");
	        
	        
	        if(Long.valueOf(financialReportRequest.getServiceId()) == StatusConstant.BOOK_SERVICE_ID) {
	        	String amanAccount = (q1[4]!=null)?String.format("%.2f", q1[4]):"0";
		        String centralAccount = (q1[5]!=null)?String.format("%.2f", q1[5]):"0";
		        String nyabaAccount = (q1[6]!=null)?String.format("%.2f", q1[6]):"0";
		        String courtName = q1[7].toString();
		        totalRefundAmount = String.format("%.2f", q1[8]);
		        totalCanceledAmount = String.format("%.2f", q1[9]);
		        
		        eFinanacialMidsDTO.setBookAmanAccount(amanAccount);
		        eFinanacialMidsDTO.setBookCentralAccount(centralAccount);
		        eFinanacialMidsDTO.setBookNyabaAccount(nyabaAccount);
		        eFinanacialMidsDTO.setCourtName(courtName);
		        eFinanacialMidsDTO.setTotalCanceledAmount(totalCanceledAmount);
		        eFinanacialMidsDTO.setTotalRefundAmount(totalRefundAmount);
	        }else {
	        	String bankNaserAccount = (q1[4]!=null)?String.format("%.2f", q1[4]):"0";
		        String ministryFinancialAccount = (q1[5]!=null)?String.format("%.2f", q1[5]):"0";
		        String abnyatMhakemAccount = (q1[6]!=null)?String.format("%.2f", q1[6]):"0";
		        String gamyatMazouneenAccount = (q1[7]!=null)?String.format("%.2f", q1[7]):"0";
		        String courtName = q1[8].toString();
		        totalRefundAmount = String.format("%.2f", q1[9]);
		        totalCanceledAmount = String.format("%.2f", q1[10]);
		        
		        eFinanacialMidsDTO.setContractBankNaserAccount(bankNaserAccount);
		        eFinanacialMidsDTO.setContractFinancialMinistryAccount(ministryFinancialAccount);
		        eFinanacialMidsDTO.setContractAbnyatMhakemAccount(abnyatMhakemAccount);
		        eFinanacialMidsDTO.setContractGamyatMazoounenAccount(gamyatMazouneenAccount);
		        eFinanacialMidsDTO.setTotalAmountCreditors(
		        		String.format("%.2f", Double.valueOf(bankNaserAccount) + 
		        				Double.valueOf(ministryFinancialAccount) + 
		        				Double.valueOf(abnyatMhakemAccount)));
		        eFinanacialMidsDTO.setCourtName(courtName);
		        eFinanacialMidsDTO.setTotalCanceledAmount(totalCanceledAmount);
		        eFinanacialMidsDTO.setTotalRefundAmount(totalRefundAmount);
	        }
	        Float totalAmount = Float.valueOf(totalAmountIncludingCanceled) - 
	        		Float.valueOf(totalRefundAmount);
	        eFinanacialMidsDTO.setTotalAmount(String.format("%.2f", totalAmount));
	        
	        result.add(eFinanacialMidsDTO);	   
	        
	        accamulateObject(totalFinanacialMidsDTO, eFinanacialMidsDTO);
	        
	     }

	    result.add(totalFinanacialMidsDTO);
	    
	    return new PageImpl<FinanacialMidsDTO>(result, pageable, total);
	}
	
	@Override
	public Page<FinanacialMidsDTO> getRefundAuditReportFinancialMidDetailsBySector(
			FinancialReportRequest financialReportRequest) {
		Pageable pageable = PageRequest.of(Integer.valueOf(financialReportRequest.getPageNo()), 
				Integer.valueOf(financialReportRequest.getPageSize()));

		List<FinanacialMidsDTO> result = new ArrayList<FinanacialMidsDTO>();
		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();

		String selectQuery = null;
		String countQuery = null;
		if(Long.valueOf(financialReportRequest.getServiceId()) == StatusConstant.BOOK_SERVICE_ID) {
			selectQuery = "SELECT SUM(v.amount) AS totalAmount, "
					+ "s.name AS sectorName, l.name AS locationName, c.name_ar AS cityName, "
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='امان' AND transaction_fk = v.id)) AS amanAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='الأدارة المركزية لشئون الدمغة ورسم التنمية' AND transaction_fk = v.id)) AS centralAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='حساب العميل' AND transaction_fk = v.id)) AS nyabaAccount, co.name as courtName " 
					+ "FROM transaction v "
					+ "JOIN maazoun_book_request_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
			
			countQuery = "SELECT COUNT(*) " 
					+ "FROM transaction v "
					+ "JOIN maazoun_book_request_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
		}else {
			selectQuery = "SELECT SUM(v.amount) AS totalAmount, "  
					+ "s.name AS sectorName, l.name AS locationName, c.name_ar AS cityName, "
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='بنك ناصر' AND transaction_fk = v.id)) AS bankNaserAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='وزارة المالية' AND transaction_fk = v.id)) AS ministryFinancialAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='هيئة أبنية المحاكم' AND transaction_fk = v.id)) AS abnyatMhakemAccount, "
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='جمعية المأذونيين' AND transaction_fk = v.id)) AS gamyatMazouneenAccount, co.name as courtName "
					+ "FROM transaction v "
					+ "JOIN maazoun_book_collection_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
			
			countQuery = "SELECT COUNT(*) "
					+ "FROM transaction v "
					+ "JOIN maazoun_book_collection_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
		}
		
		String whereCondition = " WHERE v.created_at >= '" + financialReportRequest.getDurationFrom() + " 00:00:00' "
				+ " AND v.created_at <= '" + financialReportRequest.getDurationTo() + " 23:59:59' "
				+ " AND v.status_fk = 'Refund' AND v.service_id = "+financialReportRequest.getServiceId()+" "
						+ " GROUP BY r.sector_fk";
		
		jpql.append(selectQuery);	
		jpql.append(whereCondition);
		
		jpqlCount.append(countQuery);
		jpqlCount.append(whereCondition);
		
		
		Query query = em.createNativeQuery(jpql.toString());
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Query queryCount = em.createNativeQuery(jpqlCount.toString());
		long total = queryCount.getResultList().size();
		
		List<Object[]> list = query.getResultList();
		
		FinanacialMidsDTO totalFinanacialMidsDTO = new FinanacialMidsDTO();
		totalFinanacialMidsDTO.setSectorName("الإجمالى");
		totalFinanacialMidsDTO.setTotalAmount("0");
		totalFinanacialMidsDTO.setBookAmanAccount("0");
		totalFinanacialMidsDTO.setBookCentralAccount("0");
		totalFinanacialMidsDTO.setBookNyabaAccount("0");
		totalFinanacialMidsDTO.setContractBankNaserAccount("0");
		totalFinanacialMidsDTO.setContractFinancialMinistryAccount("0");
		totalFinanacialMidsDTO.setContractAbnyatMhakemAccount("0");
		totalFinanacialMidsDTO.setContractGamyatMazoounenAccount("0");
		totalFinanacialMidsDTO.setTotalAmountCreditors("0");
		
	    for(Object[] q1 : list){
		
	    	FinanacialMidsDTO eFinanacialMidsDTO = new FinanacialMidsDTO();
	    	
	    	String totalAmount = String.format("%.2f", q1[0]);
	        String sectorName = q1[1].toString();
	        String locationName = q1[2].toString();
	        String cityName = q1[3].toString();

	        eFinanacialMidsDTO.setTotalAmount(totalAmount);
	        eFinanacialMidsDTO.setSectorName(sectorName);
	        eFinanacialMidsDTO.setLocationName(locationName);
	        eFinanacialMidsDTO.setCityName(cityName);
	        eFinanacialMidsDTO.setBookAmanAccount("0");
	        eFinanacialMidsDTO.setBookCentralAccount("0");
	        eFinanacialMidsDTO.setBookNyabaAccount("0");
	        eFinanacialMidsDTO.setContractBankNaserAccount("0");
	        eFinanacialMidsDTO.setContractFinancialMinistryAccount("0");
	        eFinanacialMidsDTO.setContractAbnyatMhakemAccount("0");
	        eFinanacialMidsDTO.setContractGamyatMazoounenAccount("0");
	        eFinanacialMidsDTO.setTotalAmountCreditors("0");
	        
	        
	        if(Long.valueOf(financialReportRequest.getServiceId()) == StatusConstant.BOOK_SERVICE_ID) {
	        	String amanAccount = (q1[4]!=null)?String.format("%.2f", q1[4]):"0";
		        String centralAccount = (q1[5]!=null)?String.format("%.2f", q1[5]):"0";
		        String nyabaAccount = (q1[6]!=null)?String.format("%.2f", q1[6]):"0";
		        String courtName = q1[7].toString();
		        
		        eFinanacialMidsDTO.setBookAmanAccount(amanAccount);
		        eFinanacialMidsDTO.setBookCentralAccount(centralAccount);
		        eFinanacialMidsDTO.setBookNyabaAccount(nyabaAccount);
		        eFinanacialMidsDTO.setCourtName(courtName);
	        }else {
	        	String bankNaserAccount = (q1[4]!=null)?String.format("%.2f", q1[4]):"0";
		        String ministryFinancialAccount = (q1[5]!=null)?String.format("%.2f", q1[5]):"0";
		        String abnyatMhakemAccount = (q1[6]!=null)?String.format("%.2f", q1[6]):"0";
		        String gamyatMazouneenAccount = (q1[7]!=null)?String.format("%.2f", q1[7]):"0";
		        String courtName = q1[8].toString();
		        
		        eFinanacialMidsDTO.setContractBankNaserAccount(bankNaserAccount);
		        eFinanacialMidsDTO.setContractFinancialMinistryAccount(ministryFinancialAccount);
		        eFinanacialMidsDTO.setContractAbnyatMhakemAccount(abnyatMhakemAccount);
		        eFinanacialMidsDTO.setContractGamyatMazoounenAccount(gamyatMazouneenAccount);
		        eFinanacialMidsDTO.setTotalAmountCreditors(
		        		String.format("%.2f", Double.valueOf(bankNaserAccount) + 
		        				Double.valueOf(ministryFinancialAccount) + 
		        				Double.valueOf(abnyatMhakemAccount)));
		        eFinanacialMidsDTO.setCourtName(courtName);
	        }
	        
	        result.add(eFinanacialMidsDTO);	  
	        
	        accamulateObject(totalFinanacialMidsDTO, eFinanacialMidsDTO);
	        
	     }
	    
	    result.add(totalFinanacialMidsDTO);

	    return new PageImpl<FinanacialMidsDTO>(result, pageable, total);
	}

	@Override
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByLocation(
			FinancialReportRequest financialReportRequest) {
		Pageable pageable = PageRequest.of(Integer.valueOf(financialReportRequest.getPageNo()), 
				Integer.valueOf(financialReportRequest.getPageSize()));

		List<FinanacialMidsDTO> result = new ArrayList<FinanacialMidsDTO>();
		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();

		String selectQuery = null;
		String countQuery = null;
		if(Long.valueOf(financialReportRequest.getServiceId()) == StatusConstant.BOOK_SERVICE_ID) {
			selectQuery = "SELECT SUM(v.amount) AS totalAmount, "
					+ "s.name AS sectorName, l.name AS locationName, c.name_ar AS cityName, "
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='امان' AND transaction_fk = v.id)) AS amanAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='الأدارة المركزية لشئون الدمغة ورسم التنمية' AND transaction_fk = v.id)) AS centralAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='حساب العميل' AND transaction_fk = v.id)) AS nyabaAccount, co.name as courtName " 
					+ "FROM transaction v "
					+ "JOIN maazoun_book_request_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
			
			countQuery = "SELECT COUNT(*) " 
					+ "FROM transaction v "
					+ "JOIN maazoun_book_request_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
		}else {
			selectQuery = "SELECT SUM(v.amount) AS totalAmount, "  
					+ "s.name AS sectorName, l.name AS locationName, c.name_ar AS cityName, "
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='بنك ناصر' AND transaction_fk = v.id)) AS bankNaserAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='وزارة المالية' AND transaction_fk = v.id)) AS ministryFinancialAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='هيئة أبنية المحاكم' AND transaction_fk = v.id)) AS abnyatMhakemAccount, "
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='جمعية المأذونيين' AND transaction_fk = v.id)) AS gamyatMazouneenAccount, co.name as courtName "
					+ "FROM transaction v "
					+ "JOIN maazoun_book_collection_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
			
			countQuery = "SELECT COUNT(*) "
					+ "FROM transaction v "
					+ "JOIN maazoun_book_collection_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
		}
		
		String whereCondition = " WHERE v.created_at >= '" + financialReportRequest.getDurationFrom() + " 00:00:00' "
				+ " AND v.created_at <= '" + financialReportRequest.getDurationTo() + " 23:59:59' "
				+ " AND v.status_fk = 'Final' AND v.service_id = "+financialReportRequest.getServiceId()+" "
						+ " GROUP BY l.id";
		
		jpql.append(selectQuery);	
		jpql.append(whereCondition);
		
		jpqlCount.append(countQuery);
		jpqlCount.append(whereCondition);
		
		
		Query query = em.createNativeQuery(jpql.toString());
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Query queryCount = em.createNativeQuery(jpqlCount.toString());
		long total = queryCount.getResultList().size();
		
		List<Object[]> list = query.getResultList();
		
		FinanacialMidsDTO totalFinanacialMidsDTO = new FinanacialMidsDTO();
		totalFinanacialMidsDTO.setLocationName("الإجمالى");
		totalFinanacialMidsDTO.setTotalAmount("0");
		totalFinanacialMidsDTO.setBookAmanAccount("0");
		totalFinanacialMidsDTO.setBookCentralAccount("0");
		totalFinanacialMidsDTO.setBookNyabaAccount("0");
		totalFinanacialMidsDTO.setContractBankNaserAccount("0");
		totalFinanacialMidsDTO.setContractFinancialMinistryAccount("0");
		totalFinanacialMidsDTO.setContractAbnyatMhakemAccount("0");
		totalFinanacialMidsDTO.setContractGamyatMazoounenAccount("0");
		totalFinanacialMidsDTO.setTotalAmountCreditors("0");
		
	    for(Object[] q1 : list){
		
	    	FinanacialMidsDTO eFinanacialMidsDTO = new FinanacialMidsDTO();
	    	
	    	String totalAmount = String.format("%.2f", q1[0]);
	        String sectorName = q1[1].toString();
	        String locationName = q1[2].toString();
	        String cityName = q1[3].toString();

	        eFinanacialMidsDTO.setTotalAmount(totalAmount);
	        eFinanacialMidsDTO.setSectorName(sectorName);
	        eFinanacialMidsDTO.setLocationName(locationName);
	        eFinanacialMidsDTO.setCityName(cityName);
	        eFinanacialMidsDTO.setBookAmanAccount("0");
	        eFinanacialMidsDTO.setBookCentralAccount("0");
	        eFinanacialMidsDTO.setBookNyabaAccount("0");
	        eFinanacialMidsDTO.setContractBankNaserAccount("0");
	        eFinanacialMidsDTO.setContractFinancialMinistryAccount("0");
	        eFinanacialMidsDTO.setContractAbnyatMhakemAccount("0");
	        eFinanacialMidsDTO.setContractGamyatMazoounenAccount("0");
	        eFinanacialMidsDTO.setTotalAmountCreditors("0");
	        
	        
	        if(Long.valueOf(financialReportRequest.getServiceId()) == StatusConstant.BOOK_SERVICE_ID) {
	        	String amanAccount = (q1[4]!=null)?String.format("%.2f", q1[4]):"0";
		        String centralAccount = (q1[5]!=null)?String.format("%.2f", q1[5]):"0";
		        String nyabaAccount = (q1[6]!=null)?String.format("%.2f", q1[6]):"0";
		        String courtName = q1[7].toString();
		        
		        eFinanacialMidsDTO.setBookAmanAccount(amanAccount);
		        eFinanacialMidsDTO.setBookCentralAccount(centralAccount);
		        eFinanacialMidsDTO.setBookNyabaAccount(nyabaAccount);
		        eFinanacialMidsDTO.setCourtName(courtName);
	        }else {
	        	String bankNaserAccount = (q1[4]!=null)?String.format("%.2f", q1[4]):"0";
		        String ministryFinancialAccount = (q1[5]!=null)?String.format("%.2f", q1[5]):"0";
		        String abnyatMhakemAccount = (q1[6]!=null)?String.format("%.2f", q1[6]):"0";
		        String gamyatMazouneenAccount = (q1[7]!=null)?String.format("%.2f", q1[7]):"0";
		        String courtName = q1[8].toString();
		        
		        eFinanacialMidsDTO.setContractBankNaserAccount(bankNaserAccount);
		        eFinanacialMidsDTO.setContractFinancialMinistryAccount(ministryFinancialAccount);
		        eFinanacialMidsDTO.setContractAbnyatMhakemAccount(abnyatMhakemAccount);
		        eFinanacialMidsDTO.setContractGamyatMazoounenAccount(gamyatMazouneenAccount);
		        eFinanacialMidsDTO.setTotalAmountCreditors(
		        		String.format("%.2f", Double.valueOf(bankNaserAccount) + 
		        				Double.valueOf(ministryFinancialAccount) + 
		        				Double.valueOf(abnyatMhakemAccount)));
		        eFinanacialMidsDTO.setCourtName(courtName);
	        }
	        
	        result.add(eFinanacialMidsDTO);	      
	        
	        accamulateObject(totalFinanacialMidsDTO, eFinanacialMidsDTO);
	     }

	    result.add(totalFinanacialMidsDTO);	
	    
	    return new PageImpl<FinanacialMidsDTO>(result, pageable, total);
	}
	
	@Override
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByCourt(
			FinancialReportRequest financialReportRequest) {
		Pageable pageable = PageRequest.of(Integer.valueOf(financialReportRequest.getPageNo()), 
				Integer.valueOf(financialReportRequest.getPageSize()));

		List<FinanacialMidsDTO> result = new ArrayList<FinanacialMidsDTO>();
		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();

		String selectQuery = null;
		String countQuery = null;
		if(Long.valueOf(financialReportRequest.getServiceId()) == StatusConstant.BOOK_SERVICE_ID) {
			selectQuery = "SELECT SUM(v.amount) AS totalAmount, "
					+ "s.name AS sectorName, l.name AS locationName, c.name_ar AS cityName, "
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='امان' AND transaction_fk = v.id)) AS amanAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='الأدارة المركزية لشئون الدمغة ورسم التنمية' AND transaction_fk = v.id)) AS centralAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='حساب العميل' AND transaction_fk = v.id)) AS nyabaAccount, co.name as courtName " 
					+ "FROM transaction v "
					+ "JOIN maazoun_book_request_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
			
			countQuery = "SELECT COUNT(*) " 
					+ "FROM transaction v "
					+ "JOIN maazoun_book_request_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
		}else {
			selectQuery = "SELECT SUM(v.amount) AS totalAmount, "  
					+ "s.name AS sectorName, l.name AS locationName, c.name_ar AS cityName, "
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='بنك ناصر' AND transaction_fk = v.id)) AS bankNaserAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='وزارة المالية' AND transaction_fk = v.id)) AS ministryFinancialAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='هيئة أبنية المحاكم' AND transaction_fk = v.id)) AS abnyatMhakemAccount, "
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='جمعية المأذونيين' AND transaction_fk = v.id)) AS gamyatMazouneenAccount, co.name as courtName "
					+ "FROM transaction v "
					+ "JOIN maazoun_book_collection_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
			
			countQuery = "SELECT COUNT(*) "
					+ "FROM transaction v "
					+ "JOIN maazoun_book_collection_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN courts co ON s.court_fk = co.id "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
		}
		
		String whereCondition = " WHERE v.created_at >= '" + financialReportRequest.getDurationFrom() + " 00:00:00' "
				+ " AND v.created_at <= '" + financialReportRequest.getDurationTo() + " 23:59:59' "
				+ " AND v.status_fk = 'Final' AND v.service_id = "+financialReportRequest.getServiceId()+" "
						+ " GROUP BY co.id";
		
		jpql.append(selectQuery);	
		jpql.append(whereCondition);
		
		jpqlCount.append(countQuery);
		jpqlCount.append(whereCondition);
		
		
		Query query = em.createNativeQuery(jpql.toString());
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Query queryCount = em.createNativeQuery(jpqlCount.toString());
		long total = queryCount.getResultList().size();
		
		List<Object[]> list = query.getResultList();
		
		FinanacialMidsDTO totalFinanacialMidsDTO = new FinanacialMidsDTO();
		totalFinanacialMidsDTO.setCourtName("الإجمالى");
		totalFinanacialMidsDTO.setTotalAmount("0");
		totalFinanacialMidsDTO.setBookAmanAccount("0");
		totalFinanacialMidsDTO.setBookCentralAccount("0");
		totalFinanacialMidsDTO.setBookNyabaAccount("0");
		totalFinanacialMidsDTO.setContractBankNaserAccount("0");
		totalFinanacialMidsDTO.setContractFinancialMinistryAccount("0");
		totalFinanacialMidsDTO.setContractAbnyatMhakemAccount("0");
		totalFinanacialMidsDTO.setContractGamyatMazoounenAccount("0");
		totalFinanacialMidsDTO.setTotalAmountCreditors("0");
		
	    for(Object[] q1 : list){
		
	    	FinanacialMidsDTO eFinanacialMidsDTO = new FinanacialMidsDTO();
	    	
	    	String totalAmount = String.format("%.2f", q1[0]);
	        String sectorName = q1[1].toString();
	        String locationName = q1[2].toString();
	        String cityName = q1[3].toString();

	        eFinanacialMidsDTO.setTotalAmount(totalAmount);
	        eFinanacialMidsDTO.setSectorName(sectorName);
	        eFinanacialMidsDTO.setLocationName(locationName);
	        eFinanacialMidsDTO.setCityName(cityName);
	        eFinanacialMidsDTO.setBookAmanAccount("0");
	        eFinanacialMidsDTO.setBookCentralAccount("0");
	        eFinanacialMidsDTO.setBookNyabaAccount("0");
	        eFinanacialMidsDTO.setContractBankNaserAccount("0");
	        eFinanacialMidsDTO.setContractFinancialMinistryAccount("0");
	        eFinanacialMidsDTO.setContractAbnyatMhakemAccount("0");
	        eFinanacialMidsDTO.setContractGamyatMazoounenAccount("0");
	        eFinanacialMidsDTO.setTotalAmountCreditors("0");
	        
	        
	        if(Long.valueOf(financialReportRequest.getServiceId()) == StatusConstant.BOOK_SERVICE_ID) {
	        	String amanAccount = (q1[4]!=null)?String.format("%.2f", q1[4]):"0";
		        String centralAccount = (q1[5]!=null)?String.format("%.2f", q1[5]):"0";
		        String nyabaAccount = (q1[6]!=null)?String.format("%.2f", q1[6]):"0";
		        String courtName = q1[7].toString();
		        
		        eFinanacialMidsDTO.setBookAmanAccount(amanAccount);
		        eFinanacialMidsDTO.setBookCentralAccount(centralAccount);
		        eFinanacialMidsDTO.setBookNyabaAccount(nyabaAccount);
		        eFinanacialMidsDTO.setCourtName(courtName);
	        }else {
	        	String bankNaserAccount = (q1[4]!=null)?String.format("%.2f", q1[4]):"0";
		        String ministryFinancialAccount = (q1[5]!=null)?String.format("%.2f", q1[5]):"0";
		        String abnyatMhakemAccount = (q1[6]!=null)?String.format("%.2f", q1[6]):"0";
		        String gamyatMazouneenAccount = (q1[7]!=null)?String.format("%.2f", q1[7]):"0";
		        String courtName = q1[8].toString();
		        
		        eFinanacialMidsDTO.setContractBankNaserAccount(bankNaserAccount);
		        eFinanacialMidsDTO.setContractFinancialMinistryAccount(ministryFinancialAccount);
		        eFinanacialMidsDTO.setContractAbnyatMhakemAccount(abnyatMhakemAccount);
		        eFinanacialMidsDTO.setContractGamyatMazoounenAccount(gamyatMazouneenAccount);
		        eFinanacialMidsDTO.setTotalAmountCreditors(
		        		String.format("%.2f", Double.valueOf(bankNaserAccount) + 
		        				Double.valueOf(ministryFinancialAccount) + 
		        				Double.valueOf(abnyatMhakemAccount)));
		        eFinanacialMidsDTO.setCourtName(courtName);
	        }
	        
	        result.add(eFinanacialMidsDTO);	      
	        
	        accamulateObject(totalFinanacialMidsDTO, eFinanacialMidsDTO);
	     }

	    result.add(totalFinanacialMidsDTO);	
	    
	    return new PageImpl<FinanacialMidsDTO>(result, pageable, total);
	}
	
	@Override
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByCity(
			FinancialReportRequest financialReportRequest) {
		Pageable pageable = PageRequest.of(Integer.valueOf(financialReportRequest.getPageNo()), 
				Integer.valueOf(financialReportRequest.getPageSize()));

		List<FinanacialMidsDTO> result = new ArrayList<FinanacialMidsDTO>();
		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();

		String selectQuery = null;
		String countQuery = null;
		if(Long.valueOf(financialReportRequest.getServiceId()) == StatusConstant.BOOK_SERVICE_ID) {
			selectQuery = "SELECT SUM(v.amount) AS totalAmount, "
					+ "c.name_ar AS cityName, "
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='امان' AND transaction_fk = v.id)) AS amanAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='الأدارة المركزية لشئون الدمغة ورسم التنمية' AND transaction_fk = v.id)) AS centralAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='حساب العميل' AND transaction_fk = v.id)) AS nyabaAccount " 
					+ "FROM transaction v "
					+ "JOIN maazoun_book_request_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
			
			countQuery = "SELECT COUNT(*) " 
					+ "FROM transaction v "
					+ "JOIN maazoun_book_request_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
		}else {
			selectQuery = "SELECT SUM(v.amount) AS totalAmount, "  
					+ "c.name_ar AS cityName, "
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='بنك ناصر' AND transaction_fk = v.id)) AS bankNaserAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='وزارة المالية' AND transaction_fk = v.id)) AS ministryFinancialAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='هيئة أبنية المحاكم' AND transaction_fk = v.id)) AS abnyatMhakemAccount, "
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='جمعية المأذونيين' AND transaction_fk = v.id)) AS gamyatMazouneenAccount "
					+ "FROM transaction v "
					+ "JOIN maazoun_book_collection_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
			
			countQuery = "SELECT COUNT(*) "
					+ "FROM transaction v "
					+ "JOIN maazoun_book_collection_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
		}
		
		String whereCondition = " WHERE v.created_at >= '" + financialReportRequest.getDurationFrom() + " 00:00:00' "
				+ " AND v.created_at <= '" + financialReportRequest.getDurationTo() + " 23:59:59' "
				+ " AND v.status_fk = 'Final' AND v.service_id = "+financialReportRequest.getServiceId()+" "
						+ " GROUP BY c.id";
		
		jpql.append(selectQuery);	
		jpql.append(whereCondition);
		
		jpqlCount.append(countQuery);
		jpqlCount.append(whereCondition);
		
		
		Query query = em.createNativeQuery(jpql.toString());
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Query queryCount = em.createNativeQuery(jpqlCount.toString());
		long total = queryCount.getResultList().size();
		
		List<Object[]> list = query.getResultList();
		
		FinanacialMidsDTO totalFinanacialMidsDTO = new FinanacialMidsDTO();
		totalFinanacialMidsDTO.setCityName("الإجمالى");
		totalFinanacialMidsDTO.setTotalAmount("0");
		totalFinanacialMidsDTO.setBookAmanAccount("0");
		totalFinanacialMidsDTO.setBookCentralAccount("0");
		totalFinanacialMidsDTO.setBookNyabaAccount("0");
		totalFinanacialMidsDTO.setContractBankNaserAccount("0");
		totalFinanacialMidsDTO.setContractFinancialMinistryAccount("0");
		totalFinanacialMidsDTO.setContractAbnyatMhakemAccount("0");
		totalFinanacialMidsDTO.setContractGamyatMazoounenAccount("0");
		totalFinanacialMidsDTO.setTotalAmountCreditors("0");
		
		
	    for(Object[] q1 : list){
		
	    	FinanacialMidsDTO eFinanacialMidsDTO = new FinanacialMidsDTO();
	    	
	    	String totalAmount = String.format("%.2f", q1[0]);
	        String cityName = q1[1].toString();

	        eFinanacialMidsDTO.setTotalAmount(totalAmount);
	        eFinanacialMidsDTO.setCityName(cityName);
	        eFinanacialMidsDTO.setBookAmanAccount("0");
	        eFinanacialMidsDTO.setBookCentralAccount("0");
	        eFinanacialMidsDTO.setBookNyabaAccount("0");
	        eFinanacialMidsDTO.setContractBankNaserAccount("0");
	        eFinanacialMidsDTO.setContractFinancialMinistryAccount("0");
	        eFinanacialMidsDTO.setContractAbnyatMhakemAccount("0");
	        eFinanacialMidsDTO.setContractGamyatMazoounenAccount("0");
	        eFinanacialMidsDTO.setTotalAmountCreditors("0");
	        
	        
	        if(Long.valueOf(financialReportRequest.getServiceId()) == StatusConstant.BOOK_SERVICE_ID) {

	        	String amanAccount = (q1[2]!=null)?String.format("%.2f", q1[2]):"0";
		        String centralAccount = (q1[3]!=null)?String.format("%.2f", q1[3]):"0";
		        String nyabaAccount = (q1[4]!=null)?String.format("%.2f", q1[4]):"0";
		        
		        eFinanacialMidsDTO.setBookAmanAccount(amanAccount);
		        eFinanacialMidsDTO.setBookCentralAccount(centralAccount);
		        eFinanacialMidsDTO.setBookNyabaAccount(nyabaAccount);
	        }else {
	        	String bankNaserAccount = (q1[2]!=null)?String.format("%.2f", q1[2]):"0";
		        String ministryFinancialAccount = (q1[3]!=null)?String.format("%.2f", q1[3]):"0";
		        String abnyatMhakemAccount = (q1[4]!=null)?String.format("%.2f", q1[4]):"0";
		        String gamyatMazouneenAccount = (q1[5]!=null)?String.format("%.2f", q1[5]):"0";
		        
		        eFinanacialMidsDTO.setContractBankNaserAccount(bankNaserAccount);
		        eFinanacialMidsDTO.setContractFinancialMinistryAccount(ministryFinancialAccount);
		        eFinanacialMidsDTO.setContractAbnyatMhakemAccount(abnyatMhakemAccount);
		        eFinanacialMidsDTO.setContractGamyatMazoounenAccount(gamyatMazouneenAccount);
		        eFinanacialMidsDTO.setTotalAmountCreditors(
		        		String.format("%.2f", Double.valueOf(bankNaserAccount) + 
		        				Double.valueOf(ministryFinancialAccount) + 
		        				Double.valueOf(abnyatMhakemAccount)));
	        }
	        
	        result.add(eFinanacialMidsDTO);	   
	        
	        accamulateObject(totalFinanacialMidsDTO, eFinanacialMidsDTO);
	        
	     }

	    result.add(totalFinanacialMidsDTO);	
	    
	    return new PageImpl<FinanacialMidsDTO>(result, pageable, total);
	}
	
	@Override
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByDaily(
			FinancialReportRequest financialReportRequest) {
		Pageable pageable = PageRequest.of(Integer.valueOf(financialReportRequest.getPageNo()), 
				Integer.valueOf(financialReportRequest.getPageSize()));

		List<FinanacialMidsDTO> result = new ArrayList<FinanacialMidsDTO>();
		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();

		String selectQuery = null;
		String countQuery = null;
		if(Long.valueOf(financialReportRequest.getServiceId()) == StatusConstant.BOOK_SERVICE_ID) {
			selectQuery = "SELECT SUM(v.amount) AS totalAmount, "
					+ " DATE_FORMAT(v.created_at, '%Y/%m/%d') as createDate, " 
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='امان' AND transaction_fk = v.id)) AS amanAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='الأدارة المركزية لشئون الدمغة ورسم التنمية' AND transaction_fk = v.id)) AS centralAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='حساب العميل' AND transaction_fk = v.id)) AS nyabaAccount " 
					+ "FROM transaction v "
					+ "JOIN maazoun_book_request_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
			
			countQuery = "SELECT COUNT(*) " 
					+ "FROM transaction v "
					+ "JOIN maazoun_book_request_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
		}else {
			selectQuery = "SELECT SUM(v.amount) AS totalAmount, "  
					+ " DATE_FORMAT(v.created_at, '%Y/%m/%d') as createDate, " 
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='بنك ناصر' AND transaction_fk = v.id)) AS bankNaserAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='وزارة المالية' AND transaction_fk = v.id)) AS ministryFinancialAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='هيئة أبنية المحاكم' AND transaction_fk = v.id)) AS abnyatMhakemAccount, "
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='جمعية المأذونيين' AND transaction_fk = v.id)) AS gamyatMazouneenAccount "
					+ "FROM transaction v "
					+ "JOIN maazoun_book_collection_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
			
			countQuery = "SELECT COUNT(*) "
					+ "FROM transaction v "
					+ "JOIN maazoun_book_collection_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
		}
		
		String whereCondition = " WHERE v.created_at >= '" + financialReportRequest.getDurationFrom() + " 00:00:00' "
				+ " AND v.created_at <= '" + financialReportRequest.getDurationTo() + " 23:59:59' "
				+ " AND v.status_fk = 'Final' AND v.service_id = "+financialReportRequest.getServiceId()+" "
						+ " GROUP BY date(v.created_at) ORDER BY v.created_at DESC";
		
		jpql.append(selectQuery);	
		jpql.append(whereCondition);
		
		jpqlCount.append(countQuery);
		jpqlCount.append(whereCondition);
		
		
		Query query = em.createNativeQuery(jpql.toString());
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Query queryCount = em.createNativeQuery(jpqlCount.toString());
		long total = queryCount.getResultList().size();
		
		List<Object[]> list = query.getResultList();
		
		FinanacialMidsDTO totalFinanacialMidsDTO = new FinanacialMidsDTO();
		totalFinanacialMidsDTO.setCreatedDate("الإجمالى");
		totalFinanacialMidsDTO.setTotalAmount("0");
		totalFinanacialMidsDTO.setBookAmanAccount("0");
		totalFinanacialMidsDTO.setBookCentralAccount("0");
		totalFinanacialMidsDTO.setBookNyabaAccount("0");
		totalFinanacialMidsDTO.setContractBankNaserAccount("0");
		totalFinanacialMidsDTO.setContractFinancialMinistryAccount("0");
		totalFinanacialMidsDTO.setContractAbnyatMhakemAccount("0");
		totalFinanacialMidsDTO.setContractGamyatMazoounenAccount("0");
		totalFinanacialMidsDTO.setTotalAmountCreditors("0");
		
		
	    for(Object[] q1 : list){
		
	    	FinanacialMidsDTO eFinanacialMidsDTO = new FinanacialMidsDTO();
	    	
	    	String totalAmount = String.format("%.2f", q1[0]);
	        String createdAt = q1[1].toString();

	        eFinanacialMidsDTO.setTotalAmount(totalAmount);
	        eFinanacialMidsDTO.setCreatedDate(createdAt);
	        eFinanacialMidsDTO.setBookAmanAccount("0");
	        eFinanacialMidsDTO.setBookCentralAccount("0");
	        eFinanacialMidsDTO.setBookNyabaAccount("0");
	        eFinanacialMidsDTO.setContractBankNaserAccount("0");
	        eFinanacialMidsDTO.setContractFinancialMinistryAccount("0");
	        eFinanacialMidsDTO.setContractAbnyatMhakemAccount("0");
	        eFinanacialMidsDTO.setContractGamyatMazoounenAccount("0");
	        eFinanacialMidsDTO.setTotalAmountCreditors("0");
	        
	        
	        if(Long.valueOf(financialReportRequest.getServiceId()) == StatusConstant.BOOK_SERVICE_ID) {
	        	String amanAccount = (q1[2]!=null)?String.format("%.2f", q1[2]):"0";
		        String centralAccount = (q1[3]!=null)?String.format("%.2f", q1[3]):"0";
		        String nyabaAccount = (q1[4]!=null)?String.format("%.2f", q1[4]):"0";
		        
		        eFinanacialMidsDTO.setBookAmanAccount(amanAccount);
		        eFinanacialMidsDTO.setBookCentralAccount(centralAccount);
		        eFinanacialMidsDTO.setBookNyabaAccount(nyabaAccount);
	        }else {
	        	String bankNaserAccount = (q1[2]!=null)?String.format("%.2f", q1[2]):"0";
		        String ministryFinancialAccount = (q1[3]!=null)?String.format("%.2f", q1[3]):"0";
		        String abnyatMhakemAccount = (q1[4]!=null)?String.format("%.2f", q1[4]):"0";
		        String gamyatMazouneenAccount = (q1[5]!=null)?String.format("%.2f", q1[5]):"0";
		        
		        eFinanacialMidsDTO.setContractBankNaserAccount(bankNaserAccount);
		        eFinanacialMidsDTO.setContractFinancialMinistryAccount(ministryFinancialAccount);
		        eFinanacialMidsDTO.setContractAbnyatMhakemAccount(abnyatMhakemAccount);
		        eFinanacialMidsDTO.setContractGamyatMazoounenAccount(gamyatMazouneenAccount);
		        eFinanacialMidsDTO.setTotalAmountCreditors(
		        		String.format("%.2f", Double.valueOf(bankNaserAccount) + 
		        				Double.valueOf(ministryFinancialAccount) + 
		        				Double.valueOf(abnyatMhakemAccount)));
	        }
	        
	        result.add(eFinanacialMidsDTO);	      
	        
			accamulateObject(totalFinanacialMidsDTO, eFinanacialMidsDTO);
	     }

	    result.add(totalFinanacialMidsDTO);	
	    
	    return new PageImpl<FinanacialMidsDTO>(result, pageable, total);
	}
	
	@Override
	public Page<FinanacialMidsDTO> getRefundAuditReportFinancialMidDetailsByDaily(
			FinancialReportRequest financialReportRequest) {
		Pageable pageable = PageRequest.of(Integer.valueOf(financialReportRequest.getPageNo()), 
				Integer.valueOf(financialReportRequest.getPageSize()));

		List<FinanacialMidsDTO> result = new ArrayList<FinanacialMidsDTO>();
		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();

		String selectQuery = null;
		String countQuery = null;
		if(Long.valueOf(financialReportRequest.getServiceId()) == StatusConstant.BOOK_SERVICE_ID) {
			selectQuery = "SELECT SUM(v.amount) AS totalAmount, "
					+ " DATE_FORMAT(v.created_at, '%Y/%m/%d') as createDate, " 
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='امان' AND transaction_fk = v.id)) AS amanAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='الأدارة المركزية لشئون الدمغة ورسم التنمية' AND transaction_fk = v.id)) AS centralAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='حساب العميل' AND transaction_fk = v.id)) AS nyabaAccount " 
					+ "FROM transaction v "
					+ "JOIN maazoun_book_request_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
			
			countQuery = "SELECT COUNT(*) " 
					+ "FROM transaction v "
					+ "JOIN maazoun_book_request_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
		}else {
			selectQuery = "SELECT SUM(v.amount) AS totalAmount, "  
					+ " DATE_FORMAT(v.created_at, '%Y/%m/%d') as createDate, " 
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='بنك ناصر' AND transaction_fk = v.id)) AS bankNaserAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='وزارة المالية' AND transaction_fk = v.id)) AS ministryFinancialAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='هيئة أبنية المحاكم' AND transaction_fk = v.id)) AS abnyatMhakemAccount, "
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='جمعية المأذونيين' AND transaction_fk = v.id)) AS gamyatMazouneenAccount "
					+ "FROM transaction v "
					+ "JOIN maazoun_book_collection_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
			
			countQuery = "SELECT COUNT(*) "
					+ "FROM transaction v "
					+ "JOIN maazoun_book_collection_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
		}
		
		String whereCondition = " WHERE v.created_at >= '" + financialReportRequest.getDurationFrom() + " 00:00:00' "
				+ " AND v.created_at <= '" + financialReportRequest.getDurationTo() + " 23:59:59' "
				+ " AND v.status_fk = 'Refund' AND v.service_id = "+financialReportRequest.getServiceId()+" "
						+ " GROUP BY date(v.created_at) ORDER BY v.created_at DESC";
		
		jpql.append(selectQuery);	
		jpql.append(whereCondition);
		
		jpqlCount.append(countQuery);
		jpqlCount.append(whereCondition);
		
		
		Query query = em.createNativeQuery(jpql.toString());
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Query queryCount = em.createNativeQuery(jpqlCount.toString());
		long total = queryCount.getResultList().size();
		
		List<Object[]> list = query.getResultList();
		
		FinanacialMidsDTO totalFinanacialMidsDTO = new FinanacialMidsDTO();
		totalFinanacialMidsDTO.setCreatedDate("الإجمالى");
		totalFinanacialMidsDTO.setTotalAmount("0");
		totalFinanacialMidsDTO.setBookAmanAccount("0");
		totalFinanacialMidsDTO.setBookCentralAccount("0");
		totalFinanacialMidsDTO.setBookNyabaAccount("0");
		totalFinanacialMidsDTO.setContractBankNaserAccount("0");
		totalFinanacialMidsDTO.setContractFinancialMinistryAccount("0");
		totalFinanacialMidsDTO.setContractAbnyatMhakemAccount("0");
		totalFinanacialMidsDTO.setContractGamyatMazoounenAccount("0");
		totalFinanacialMidsDTO.setTotalAmountCreditors("0");
		
		
	    for(Object[] q1 : list){
		
	    	FinanacialMidsDTO eFinanacialMidsDTO = new FinanacialMidsDTO();
	    	
	    	String totalAmount = String.format("%.2f", q1[0]);
	        String createdAt = q1[1].toString();

	        eFinanacialMidsDTO.setTotalAmount(totalAmount);
	        eFinanacialMidsDTO.setCreatedDate(createdAt);
	        eFinanacialMidsDTO.setBookAmanAccount("0");
	        eFinanacialMidsDTO.setBookCentralAccount("0");
	        eFinanacialMidsDTO.setBookNyabaAccount("0");
	        eFinanacialMidsDTO.setContractBankNaserAccount("0");
	        eFinanacialMidsDTO.setContractFinancialMinistryAccount("0");
	        eFinanacialMidsDTO.setContractAbnyatMhakemAccount("0");
	        eFinanacialMidsDTO.setContractGamyatMazoounenAccount("0");
	        eFinanacialMidsDTO.setTotalAmountCreditors("0");
		
	        
	        if(Long.valueOf(financialReportRequest.getServiceId()) == StatusConstant.BOOK_SERVICE_ID) {
	        	String amanAccount = (q1[2]!=null)?String.format("%.2f", q1[2]):"0";
		        String centralAccount = (q1[3]!=null)?String.format("%.2f", q1[3]):"0";
		        String nyabaAccount = (q1[4]!=null)?String.format("%.2f", q1[4]):"0";
		        
		        eFinanacialMidsDTO.setBookAmanAccount(amanAccount);
		        eFinanacialMidsDTO.setBookCentralAccount(centralAccount);
		        eFinanacialMidsDTO.setBookNyabaAccount(nyabaAccount);
	        }else {
	        	String bankNaserAccount = (q1[2]!=null)?String.format("%.2f", q1[2]):"0";
		        String ministryFinancialAccount = (q1[3]!=null)?String.format("%.2f", q1[3]):"0";
		        String abnyatMhakemAccount = (q1[4]!=null)?String.format("%.2f", q1[4]):"0";
		        String gamyatMazouneenAccount = (q1[5]!=null)?String.format("%.2f", q1[5]):"0";
		        
		        eFinanacialMidsDTO.setContractBankNaserAccount(bankNaserAccount);
		        eFinanacialMidsDTO.setContractFinancialMinistryAccount(ministryFinancialAccount);
		        eFinanacialMidsDTO.setContractAbnyatMhakemAccount(abnyatMhakemAccount);
		        eFinanacialMidsDTO.setContractGamyatMazoounenAccount(gamyatMazouneenAccount);
		        eFinanacialMidsDTO.setTotalAmountCreditors(
		        		String.format("%.2f", Double.valueOf(bankNaserAccount) + 
		        				Double.valueOf(ministryFinancialAccount) + 
		        				Double.valueOf(abnyatMhakemAccount)));
	        }
	        
	        result.add(eFinanacialMidsDTO);	      
	        
	        accamulateObject(totalFinanacialMidsDTO, eFinanacialMidsDTO);
	        
	     }

	    result.add(totalFinanacialMidsDTO);
	    
	    return new PageImpl<FinanacialMidsDTO>(result, pageable, total);
	}

	@Override
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByAgent(
			FinancialReportRequest financialReportRequest) {
		Pageable pageable = PageRequest.of(Integer.valueOf(financialReportRequest.getPageNo()), 
				Integer.valueOf(financialReportRequest.getPageSize()));

		List<FinanacialMidsDTO> result = new ArrayList<FinanacialMidsDTO>();
		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();

		String selectQuery = null;
		String countQuery = null;
		if(Long.valueOf(financialReportRequest.getServiceId()) == StatusConstant.BOOK_SERVICE_ID) {
			selectQuery = "SELECT SUM(v.amount) AS totalAmount, "
					+ "v.created_by, "
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='امان' AND transaction_fk = v.id)) AS amanAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='الأدارة المركزية لشئون الدمغة ورسم التنمية' AND transaction_fk = v.id)) AS centralAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='حساب العميل' AND transaction_fk = v.id)) AS nyabaAccount " 
					+ "FROM transaction v "
					+ "JOIN maazoun_book_request_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
			
			countQuery = "SELECT COUNT(*) " 
					+ "FROM transaction v "
					+ "JOIN maazoun_book_request_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
		}else {
			selectQuery = "SELECT SUM(v.amount) AS totalAmount, "  
					+ "v.created_by, "
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='بنك ناصر' AND transaction_fk = v.id)) AS bankNaserAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='وزارة المالية' AND transaction_fk = v.id)) AS ministryFinancialAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='هيئة أبنية المحاكم' AND transaction_fk = v.id)) AS abnyatMhakemAccount, "
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='جمعية المأذونيين' AND transaction_fk = v.id)) AS gamyatMazouneenAccount "
					+ "FROM transaction v "
					+ "JOIN maazoun_book_collection_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
			
			countQuery = "SELECT COUNT(*) "
					+ "FROM transaction v "
					+ "JOIN maazoun_book_collection_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
		}
		
		String whereCondition = " WHERE v.created_at >= '" + financialReportRequest.getDurationFrom() + " 00:00:00' "
				+ " AND v.created_at <= '" + financialReportRequest.getDurationTo() + " 23:59:59' "
				+ " AND v.status_fk = 'Final' AND v.service_id = "+financialReportRequest.getServiceId()+" "
						+ " GROUP BY v.created_by";
		
		jpql.append(selectQuery);	
		jpql.append(whereCondition);
		
		jpqlCount.append(countQuery);
		jpqlCount.append(whereCondition);
		
		
		Query query = em.createNativeQuery(jpql.toString());
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Query queryCount = em.createNativeQuery(jpqlCount.toString());
		long total = queryCount.getResultList().size();
		
		List<Object[]> list = query.getResultList();
		
		FinanacialMidsDTO totalFinanacialMidsDTO = new FinanacialMidsDTO();
		totalFinanacialMidsDTO.setCreatedBy("الإجمالى");
		totalFinanacialMidsDTO.setTotalAmount("0");
		totalFinanacialMidsDTO.setBookAmanAccount("0");
		totalFinanacialMidsDTO.setBookCentralAccount("0");
		totalFinanacialMidsDTO.setBookNyabaAccount("0");
		totalFinanacialMidsDTO.setContractBankNaserAccount("0");
		totalFinanacialMidsDTO.setContractFinancialMinistryAccount("0");
		totalFinanacialMidsDTO.setContractAbnyatMhakemAccount("0");
		totalFinanacialMidsDTO.setContractGamyatMazoounenAccount("0");
		totalFinanacialMidsDTO.setTotalAmountCreditors("0");
		
		
	    for(Object[] q1 : list){
		
	    	FinanacialMidsDTO eFinanacialMidsDTO = new FinanacialMidsDTO();
	    	
	    	String totalAmount = String.format("%.2f", q1[0]);
	        String createdBy = q1[1].toString();

	        eFinanacialMidsDTO.setTotalAmount(totalAmount);
	        eFinanacialMidsDTO.setCreatedBy(createdBy);
	        eFinanacialMidsDTO.setBookAmanAccount("0");
	        eFinanacialMidsDTO.setBookCentralAccount("0");
	        eFinanacialMidsDTO.setBookNyabaAccount("0");
	        eFinanacialMidsDTO.setContractBankNaserAccount("0");
	        eFinanacialMidsDTO.setContractFinancialMinistryAccount("0");
	        eFinanacialMidsDTO.setContractAbnyatMhakemAccount("0");
	        eFinanacialMidsDTO.setContractGamyatMazoounenAccount("0");
	        eFinanacialMidsDTO.setTotalAmountCreditors("0");
	        
	        
	        if(Long.valueOf(financialReportRequest.getServiceId()) == StatusConstant.BOOK_SERVICE_ID) {
	        	String amanAccount = (q1[2]!=null)?String.format("%.2f", q1[2]):"0";
		        String centralAccount = (q1[3]!=null)?String.format("%.2f", q1[3]):"0";
		        String nyabaAccount = (q1[4]!=null)?String.format("%.2f", q1[4]):"0";
		        
		        eFinanacialMidsDTO.setBookAmanAccount(amanAccount);
		        eFinanacialMidsDTO.setBookCentralAccount(centralAccount);
		        eFinanacialMidsDTO.setBookNyabaAccount(nyabaAccount);
	        }else {
	        	String bankNaserAccount = (q1[2]!=null)?String.format("%.2f", q1[2]):"0";
		        String ministryFinancialAccount = (q1[3]!=null)?String.format("%.2f", q1[3]):"0";
		        String abnyatMhakemAccount = (q1[4]!=null)?String.format("%.2f", q1[4]):"0";
		        String gamyatMazouneenAccount = (q1[5]!=null)?String.format("%.2f", q1[5]):"0";
		        
		        eFinanacialMidsDTO.setContractBankNaserAccount(bankNaserAccount);
		        eFinanacialMidsDTO.setContractFinancialMinistryAccount(ministryFinancialAccount);
		        eFinanacialMidsDTO.setContractAbnyatMhakemAccount(abnyatMhakemAccount);
		        eFinanacialMidsDTO.setContractGamyatMazoounenAccount(gamyatMazouneenAccount);
		        eFinanacialMidsDTO.setTotalAmountCreditors(
		        		String.format("%.2f", Double.valueOf(bankNaserAccount) + 
		        				Double.valueOf(ministryFinancialAccount) + 
		        				Double.valueOf(abnyatMhakemAccount)));
	        }
	        
	        result.add(eFinanacialMidsDTO);	      
	        
	        accamulateObject(totalFinanacialMidsDTO, eFinanacialMidsDTO);
	     }

	    result.add(totalFinanacialMidsDTO);	
	    
	    return new PageImpl<FinanacialMidsDTO>(result, pageable, total);
	}
	
	@Override
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByTypeName(
			FinancialReportRequest financialReportRequest) {
		
		Pageable pageable = PageRequest.of(Integer.valueOf(financialReportRequest.getPageNo()), 
				Integer.valueOf(financialReportRequest.getPageSize()));

		List<FinanacialMidsDTO> result = new ArrayList<FinanacialMidsDTO>();
		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();

		String selectQuery = null;
		String countQuery = null;
		if(Long.valueOf(financialReportRequest.getServiceId()) == StatusConstant.BOOK_SERVICE_ID) {
			selectQuery = "SELECT SUM(v.amount) AS totalAmount, "   
					+ "ss.name AS typeName, "
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='امان' AND transaction_fk = v.id)) AS amanAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='الأدارة المركزية لشئون الدمغة ورسم التنمية' AND transaction_fk = v.id)) AS centralAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='حساب العميل' AND transaction_fk = v.id)) AS nyabaAccount " 
					+ "FROM transaction v "
					+ "JOIN maazoun_book_request_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pull_account p ON p.id = v.pull_account_fk "
					+ "JOIN sub_service ss ON ss.id = r.book_type_id " 
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
			
			countQuery = "SELECT COUNT(*) " 
					+ "FROM transaction v "
					+ "JOIN maazoun_book_request_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pull_account p ON p.id = v.pull_account_fk " 
					+ "JOIN sub_service ss ON ss.id = r.book_type_id "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
		}else {
			selectQuery = "SELECT SUM(v.amount) AS totalAmount, "   
					+ "ss.name AS typeName, "
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='بنك ناصر' AND transaction_fk = v.id)) AS bankNaserAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='وزارة المالية' AND transaction_fk = v.id)) AS ministryFinancialAccount, "  
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='هيئة أبنية المحاكم' AND transaction_fk = v.id)) AS abnyatMhakemAccount, "
					+ "SUM((SELECT mid_value FROM transaction_mids WHERE beneficiary='جمعية المأذونيين' AND transaction_fk = v.id)) AS gamyatMazouneenAccount "
					+ "FROM transaction v "
					+ "JOIN maazoun_book_collection_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pull_account p ON p.id = v.pull_account_fk " 
					+ "JOIN sub_service ss ON ss.id = r.contract_type_id "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
			
			countQuery = "SELECT COUNT(*) "
					+ "FROM transaction v "
					+ "JOIN maazoun_book_collection_info r ON r.transaction_code = v.trans_code "
					+ "JOIN pull_account p ON p.id = v.pull_account_fk " 
					+ "JOIN sub_service ss ON ss.id = r.contract_type_id "
					+ "JOIN pos po ON po.id = v.pos_id " 
					+ "JOIN sector s ON s.id = r.sector_fk "
					+ "JOIN location l ON l.id = s.location_fk "
					+ "JOIN city c ON c.id = l.city_fk ";
		}
		
		String whereCondition = " WHERE v.created_at >= '" + financialReportRequest.getDurationFrom() + " 00:00:00' "
				+ " AND v.created_at <= '" + financialReportRequest.getDurationTo() + " 23:59:59' "
				+ " AND v.status_fk = 'Final' AND v.service_id = "+financialReportRequest.getServiceId()+" "
				+ " GROUP BY ss.id";
		
		jpql.append(selectQuery);	
		jpql.append(whereCondition);
		
		jpqlCount.append(countQuery);
		jpqlCount.append(whereCondition);
		
		
		Query query = em.createNativeQuery(jpql.toString());
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Query queryCount = em.createNativeQuery(jpqlCount.toString());
		long total = queryCount.getResultList().size();
		
		List<Object[]> list = query.getResultList();
		
		FinanacialMidsDTO totalFinanacialMidsDTO = new FinanacialMidsDTO();
		totalFinanacialMidsDTO.setTypeName("الإجمالى");
		totalFinanacialMidsDTO.setTotalAmount("0");
		totalFinanacialMidsDTO.setBookAmanAccount("0");
		totalFinanacialMidsDTO.setBookCentralAccount("0");
		totalFinanacialMidsDTO.setBookNyabaAccount("0");
		totalFinanacialMidsDTO.setContractBankNaserAccount("0");
		totalFinanacialMidsDTO.setContractFinancialMinistryAccount("0");
		totalFinanacialMidsDTO.setContractAbnyatMhakemAccount("0");
		totalFinanacialMidsDTO.setContractGamyatMazoounenAccount("0");
		totalFinanacialMidsDTO.setTotalAmountCreditors("0");
		
		
	    for(Object[] q1 : list){
		
	    	FinanacialMidsDTO eFinanacialMidsDTO = new FinanacialMidsDTO();
	    	
	    	String totalAmount = String.format("%.2f", q1[0]);
	        String typeName = q1[1].toString();

	        eFinanacialMidsDTO.setTotalAmount(totalAmount);
	        eFinanacialMidsDTO.setTypeName(typeName);
	        eFinanacialMidsDTO.setBookAmanAccount("0");
	        eFinanacialMidsDTO.setBookCentralAccount("0");
	        eFinanacialMidsDTO.setBookNyabaAccount("0");
	        eFinanacialMidsDTO.setContractBankNaserAccount("0");
	        eFinanacialMidsDTO.setContractFinancialMinistryAccount("0");
	        eFinanacialMidsDTO.setContractAbnyatMhakemAccount("0");
	        eFinanacialMidsDTO.setContractGamyatMazoounenAccount("0");
	        eFinanacialMidsDTO.setTotalAmountCreditors("0");
			
	        
	        if(Long.valueOf(financialReportRequest.getServiceId()) == StatusConstant.BOOK_SERVICE_ID) {
	        	String amanAccount = (q1[2]!=null)?String.format("%.2f", q1[2]):"0";
		        String centralAccount = (q1[3]!=null)?String.format("%.2f", q1[3]):"0";
		        String nyabaAccount = (q1[4]!=null)?String.format("%.2f", q1[4]):"0";
		        
		        eFinanacialMidsDTO.setBookAmanAccount(amanAccount);
		        eFinanacialMidsDTO.setBookCentralAccount(centralAccount);
		        eFinanacialMidsDTO.setBookNyabaAccount(nyabaAccount);
	        }else {
	        	String bankNaserAccount = (q1[2]!=null)?String.format("%.2f", q1[2]):"0";
		        String ministryFinancialAccount = (q1[3]!=null)?String.format("%.2f", q1[3]):"0";
		        String abnyatMhakemAccount = (q1[4]!=null)?String.format("%.2f", q1[4]):"0";
		        String gamyatMazouneenAccount = (q1[5]!=null)?String.format("%.2f", q1[5]):"0";
		        
		        eFinanacialMidsDTO.setContractBankNaserAccount(bankNaserAccount);
		        eFinanacialMidsDTO.setContractFinancialMinistryAccount(ministryFinancialAccount);
		        eFinanacialMidsDTO.setContractAbnyatMhakemAccount(abnyatMhakemAccount);
		        eFinanacialMidsDTO.setContractGamyatMazoounenAccount(gamyatMazouneenAccount);
		        eFinanacialMidsDTO.setTotalAmountCreditors(
		        		String.format("%.2f", Double.valueOf(bankNaserAccount) + 
		        				Double.valueOf(ministryFinancialAccount) + 
		        				Double.valueOf(abnyatMhakemAccount)));
	        }
	        
	        result.add(eFinanacialMidsDTO);	      
	        
	        accamulateObject(totalFinanacialMidsDTO, eFinanacialMidsDTO);
	     }

	    result.add(totalFinanacialMidsDTO);	
	    
	    return new PageImpl<FinanacialMidsDTO>(result, pageable, total);
	}
	
	private void accamulateObject(FinanacialMidsDTO totalFinanacialMidsDTO, FinanacialMidsDTO eFinanacialMidsDTO) {

		if(totalFinanacialMidsDTO.getTotalAmountAndCanceled() != null) {
			String totalAmountAndCanceled = String.format("%.2f", Double.valueOf(totalFinanacialMidsDTO.getTotalAmountAndCanceled()) + 
					Double.valueOf(eFinanacialMidsDTO.getTotalAmountAndCanceled()));
			totalFinanacialMidsDTO.setTotalAmountAndCanceled(totalAmountAndCanceled);
		}
		if(totalFinanacialMidsDTO.getTotalRefundAmount() != null) {
			String totalRefundAmount = String.format("%.2f", Double.valueOf(totalFinanacialMidsDTO.getTotalRefundAmount()) + 
					Double.valueOf(eFinanacialMidsDTO.getTotalRefundAmount()));
			totalFinanacialMidsDTO.setTotalRefundAmount(totalRefundAmount);
		}
		if(totalFinanacialMidsDTO.getTotalCanceledAmount() != null) {
			String totalCanceledAmount = String.format("%.2f", Double.valueOf(totalFinanacialMidsDTO.getTotalCanceledAmount()) + 
					Double.valueOf(eFinanacialMidsDTO.getTotalCanceledAmount()));
			totalFinanacialMidsDTO.setTotalCanceledAmount(totalCanceledAmount);
		}
		
		String totalAmount = String.format("%.2f", Double.valueOf(totalFinanacialMidsDTO.getTotalAmount()) + 
				Double.valueOf(eFinanacialMidsDTO.getTotalAmount()));
		totalFinanacialMidsDTO.setTotalAmount(totalAmount);
		
		String totalBookAmanAccount = String.format("%.2f", Double.valueOf(totalFinanacialMidsDTO.getBookAmanAccount()) + 
				Double.valueOf(eFinanacialMidsDTO.getBookAmanAccount()));
		totalFinanacialMidsDTO.setBookAmanAccount(totalBookAmanAccount);
		
		String totalBookCentralAccount = String.format("%.2f", Double.valueOf(totalFinanacialMidsDTO.getBookCentralAccount()) + 
				Double.valueOf(eFinanacialMidsDTO.getBookCentralAccount()));
		totalFinanacialMidsDTO.setBookCentralAccount(totalBookCentralAccount);
		
		String totalBookNyabaAccount = String.format("%.2f", Double.valueOf(totalFinanacialMidsDTO.getBookNyabaAccount()) + 
				Double.valueOf(eFinanacialMidsDTO.getBookNyabaAccount()));
		totalFinanacialMidsDTO.setBookNyabaAccount(totalBookNyabaAccount);
		
		String totalContractBankNaserAccount = String.format("%.2f", Double.valueOf(totalFinanacialMidsDTO.getContractBankNaserAccount()) + 
				Double.valueOf(eFinanacialMidsDTO.getContractBankNaserAccount()));
		totalFinanacialMidsDTO.setContractBankNaserAccount(totalContractBankNaserAccount);
		
		String totalContractFinancialMinistryAccoun = String.format("%.2f", Double.valueOf(totalFinanacialMidsDTO.getContractFinancialMinistryAccount()) + 
				Double.valueOf(eFinanacialMidsDTO.getContractFinancialMinistryAccount()));
		totalFinanacialMidsDTO.setContractFinancialMinistryAccount(totalContractFinancialMinistryAccoun);
		
		String totalContractAbnyatMhakemAccount = String.format("%.2f", Double.valueOf(totalFinanacialMidsDTO.getContractAbnyatMhakemAccount()) + 
				Double.valueOf(eFinanacialMidsDTO.getContractAbnyatMhakemAccount()));
		totalFinanacialMidsDTO.setContractAbnyatMhakemAccount(totalContractAbnyatMhakemAccount);
		
		String totalContractGamyatMazoounenAccount = String.format("%.2f", Double.valueOf(totalFinanacialMidsDTO.getContractGamyatMazoounenAccount()) + 
				Double.valueOf(eFinanacialMidsDTO.getContractGamyatMazoounenAccount()));
		totalFinanacialMidsDTO.setContractGamyatMazoounenAccount(totalContractGamyatMazoounenAccount);
		
		String totalAmountCreditors = String.format("%.2f", Double.valueOf(totalFinanacialMidsDTO.getTotalAmountCreditors()) + 
				Double.valueOf(eFinanacialMidsDTO.getTotalAmountCreditors()));
		totalFinanacialMidsDTO.setTotalAmountCreditors(totalAmountCreditors);
		
	}
	
}
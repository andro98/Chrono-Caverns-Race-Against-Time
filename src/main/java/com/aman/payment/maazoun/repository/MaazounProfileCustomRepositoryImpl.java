package com.aman.payment.maazoun.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.aman.payment.maazoun.model.dto.MaazounProfileAuditDTO;
import com.aman.payment.maazoun.model.payload.MaazounProfileAuditRequest;
import com.aman.payment.util.StatusConstant;

@Repository
public class MaazounProfileCustomRepositoryImpl implements MaazounProfileCustomRepository {

	@PersistenceContext
	private EntityManager em;

	public MaazounProfileCustomRepositoryImpl(EntityManager em) {
		super();
		this.em = em;
	}
	
	@Override
	public Page<MaazounProfileAuditDTO> getMaazounCustomAudit(MaazounProfileAuditRequest maazounProfileAuditRequest) {
		
		Pageable pageable = PageRequest.of(Integer.valueOf(maazounProfileAuditRequest.getPageNo()), 
				Integer.valueOf(maazounProfileAuditRequest.getPageSize()));
		
		Set<MaazounProfileAuditDTO> result = new HashSet<MaazounProfileAuditDTO>();
		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();
		
		jpql.append("SELECT COUNT(DISTINCT r.book_serial_number) AS bookCounts," 
				+" w.book_type_id AS bookTypeId, su.is_custody AS custody, w.book_type_name AS typeName,"
				+" p.name AS maazounName, "
				+" p.national_id AS maazounNationalId, w.contract_count, r.maazoun_profile_fk"
				+" FROM maazoun_book_request_info r" 
				+" JOIN maazoun_profile p ON p.id = r.maazoun_profile_fk" 
				+" JOIN maazoun_book_warehouse w ON w.maazoun_book_request_info_fk = r.id"
				+" JOIN maazoun_book_supply_order su ON su.id = w.maazoun_book_supply_order_fk ");

		jpqlCount.append("SELECT COUNT(*) "
				+ " FROM maazoun_book_request_info r "
				+" JOIN maazoun_profile p ON p.id = r.maazoun_profile_fk" 
				+" JOIN maazoun_book_warehouse w ON w.maazoun_book_request_info_fk = r.id"
				+" JOIN maazoun_book_supply_order su ON su.id = w.maazoun_book_supply_order_fk ");

		if(maazounProfileAuditRequest.getMaazounNationalId() != null && !maazounProfileAuditRequest.getMaazounNationalId().isEmpty()) {
			jpql.append(" WHERE p.national_id = '"+maazounProfileAuditRequest.getMaazounNationalId()+"' ");
			jpqlCount.append(" WHERE p.national_id = '"+maazounProfileAuditRequest.getMaazounNationalId()+"' ");
		}
		jpql.append(" GROUP BY w.book_type_name, su.is_custody");
		jpqlCount.append(" GROUP BY w.book_type_name, su.is_custody");
		
//		jpql.append("SELECT"
//				+ " COUNT(i.book_serial_number) as bookSoldCount,"
//				+ " i.book_type_id as bookTypeId,"
//				+ " m.name as maazounName,"
//				+ " m.national_id as maazounNationalId,"
//				+ " (SELECT DISTINCT(w.contract_count) FROM maazoun_book_warehouse w WHERE w.book_financial_number = i.book_serial_number OR w.serial_number = i.book_serial_number) as bookContractsNumber "
//				
//				+ " FROM maazoun_book_request_info i "
//				+ " JOIN maazoun_profile m ON m.id = i.maazoun_profile_fk");
//				
//				if(maazounProfileAuditRequest.getMaazounNationalId() != null && !maazounProfileAuditRequest.getMaazounNationalId().isEmpty()) {
//					jpql.append(" WHERE m.national_id = '"+maazounProfileAuditRequest.getMaazounNationalId()+"' ");
//				}
//				jpql.append(" GROUP BY i.maazoun_profile_fk, i.book_type_id");
				
//				StringBuilder jpqlCount = new StringBuilder();
//				jpqlCount.append("SELECT COUNT(*) "
//				+ " FROM maazoun_book_request_info i "
//				+ " JOIN maazoun_profile m ON m.id = i.maazoun_profile_fk");
//		
//				if(maazounProfileAuditRequest.getMaazounNationalId() != null && !maazounProfileAuditRequest.getMaazounNationalId().isEmpty()) {
//					jpqlCount.append(" WHERE m.national_id = '"+maazounProfileAuditRequest.getMaazounNationalId()+"' ");
//				}
//				jpqlCount.append(" GROUP BY i.maazoun_profile_fk, i.book_type_id");
		
		
		Query query = em.createNativeQuery(jpql.toString());
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Query queryCount = em.createNativeQuery(jpqlCount.toString());
		long total = queryCount.getResultList().size();
		
		List<Object[]> list = query.getResultList();
		
	    for(Object[] q1 : list){

	    	String bookSoldCount = q1[0].toString();
	        String bookTypeId = q1[1].toString();
	        String custody = q1[2].toString();
	        String bookTypeName = q1[3].toString();
	        String maazounName = q1[4].toString();
	        
	        String maazounNationalId = q1[5].toString();
	        String bookContractsNumber = q1[6].toString();
	        String maazounId = q1[7].toString();
	        
	        MaazounProfileAuditDTO eMaazounProfileAuditDTO = result.stream()
					.filter(x -> x.getMaazounId().equals(maazounId)).findAny()
					.orElse(null);
	        
	        if(eMaazounProfileAuditDTO == null) {
	        	eMaazounProfileAuditDTO = new MaazounProfileAuditDTO();
	        	
	        	eMaazounProfileAuditDTO.setMaazounName(maazounName);
	        	eMaazounProfileAuditDTO.setMaazounNationalId(maazounNationalId);
	        	eMaazounProfileAuditDTO.setMaazounId(maazounId);
	        	eMaazounProfileAuditDTO.setTypeId(Long.valueOf(bookTypeId));
	        	eMaazounProfileAuditDTO.setCustody(custody);
	        	
	        	eMaazounProfileAuditDTO.setBookMoragaaSoldCountSerial((long) 0);
	        	eMaazounProfileAuditDTO.setBookMoragaaSoldCountCustody((long) 0);
	        	eMaazounProfileAuditDTO.setBookMoragaaContractCount((long) 0);
	        	
	        	eMaazounProfileAuditDTO.setBookTalakContractCount((long) 0);
	        	eMaazounProfileAuditDTO.setBookTalakSoldCountSerial((long) 0);
	        	eMaazounProfileAuditDTO.setBookTalakSoldCountCustody((long) 0);
	        	
	        	eMaazounProfileAuditDTO.setBookTasadokContractCount((long) 0);
	        	eMaazounProfileAuditDTO.setBookTasadokSoldCountSerial((long) 0);
	        	eMaazounProfileAuditDTO.setBookTasadokSoldCountCustody((long) 0);
	        	
	        	eMaazounProfileAuditDTO.setBookZawagMelalyContractCount((long) 0);
	        	eMaazounProfileAuditDTO.setBookZawagMelalySoldCountSerial((long) 0);
	        	eMaazounProfileAuditDTO.setBookZawagMelalySoldCountCustody((long) 0);
	        	
	        	eMaazounProfileAuditDTO.setBookZawagMuslimContractCount((long) 0);
	        	eMaazounProfileAuditDTO.setBookZawagMuslimSoldCountSerial((long) 0);
	        	eMaazounProfileAuditDTO.setBookZawagMuslimSoldCountCustody((long) 0);
	        	
	        	eMaazounProfileAuditDTO.setContractMoragaaCollectedCount((long) 0);
	        	eMaazounProfileAuditDTO.setContractMoragaaReceivedCount((long) 0);
	        	eMaazounProfileAuditDTO.setContractMoragaaWithMaazounCount((long) 0);
	        	
	        	eMaazounProfileAuditDTO.setContractTalakCollectedCount((long) 0);
	        	eMaazounProfileAuditDTO.setContractTalakReceivedCount((long) 0);
	        	eMaazounProfileAuditDTO.setContractTalakWithMaazounCount((long) 0);
	        	
	        	eMaazounProfileAuditDTO.setContractTasadokCollectedCount((long) 0);
	        	eMaazounProfileAuditDTO.setContractTasadokReceivedCount((long) 0);
	        	eMaazounProfileAuditDTO.setContractTasadokWithMaazounCount((long) 0);
	        	
	        	eMaazounProfileAuditDTO.setContractZawagMelalyCollectedCount((long) 0);
	        	eMaazounProfileAuditDTO.setContractZawagMelalyReceivedCount((long) 0);
	        	eMaazounProfileAuditDTO.setContractZawagMelalyWithMaazounCount((long) 0);
	        	
	        	eMaazounProfileAuditDTO.setContractZawagMuslimCollectedCount((long) 0);
	        	eMaazounProfileAuditDTO.setContractZawagMuslimReceivedCount((long) 0);
	        	eMaazounProfileAuditDTO.setContractZawagMuslimWithMaazounCount((long) 0);	        			    
		        
	        }

	        if(bookTypeName.trim().equals(StatusConstant.BOOK_MORAGAA_NAME)) {	   
	        	eMaazounProfileAuditDTO.setBookMoragaaContractCount(eMaazounProfileAuditDTO.getBookMoragaaContractCount() + 
	        			(Long.valueOf(bookContractsNumber) * Long.valueOf(bookSoldCount)));
	        	if(Boolean.valueOf(custody)) {
	        		eMaazounProfileAuditDTO.setBookMoragaaSoldCountCustody(eMaazounProfileAuditDTO.getBookMoragaaSoldCountCustody() + 
	        				Long.valueOf(bookSoldCount));
	        	}else {
	        		eMaazounProfileAuditDTO.setBookMoragaaSoldCountSerial(eMaazounProfileAuditDTO.getBookMoragaaSoldCountSerial() + 
	        				Long.valueOf(bookSoldCount));
	        	}
	        	
		      
	        }else if(bookTypeName.trim().equals(StatusConstant.BOOK_TALAK_NAME)) {
	        	eMaazounProfileAuditDTO.setBookTalakContractCount(eMaazounProfileAuditDTO.getBookTalakContractCount() + 
	        			(Long.valueOf(bookContractsNumber) * Long.valueOf(bookSoldCount)));
	        	if(Boolean.valueOf(custody)) {
	        		eMaazounProfileAuditDTO.setBookTalakSoldCountCustody(eMaazounProfileAuditDTO.getBookTalakSoldCountCustody() + 
	        				Long.valueOf(bookSoldCount));
	        	}else {
	        		eMaazounProfileAuditDTO.setBookTalakSoldCountSerial(eMaazounProfileAuditDTO.getBookTalakSoldCountSerial() + 
	        				Long.valueOf(bookSoldCount));
	        	}
	     
	        	
	        }else if(bookTypeName.trim().equals(StatusConstant.BOOK_TASADOK_NAME)) {
	        	eMaazounProfileAuditDTO.setBookTasadokContractCount(eMaazounProfileAuditDTO.getBookTasadokContractCount() + 
	        			(Long.valueOf(bookContractsNumber) * Long.valueOf(bookSoldCount)));
	        	if(Boolean.valueOf(custody)) {
	        		eMaazounProfileAuditDTO.setBookTasadokSoldCountCustody(eMaazounProfileAuditDTO.getBookTasadokSoldCountCustody() + 
	        				Long.valueOf(bookSoldCount));
	        	}else {
	        		eMaazounProfileAuditDTO.setBookTasadokSoldCountSerial(eMaazounProfileAuditDTO.getBookTasadokSoldCountSerial() + 
	        				Long.valueOf(bookSoldCount));
	        	}
	        	
	        }else if(bookTypeName.trim().equals(StatusConstant.BOOK_ZAWAG_MELALY_NAME)) {
	        	eMaazounProfileAuditDTO.setBookZawagMelalyContractCount(eMaazounProfileAuditDTO.getBookZawagMelalyContractCount() + 
	        			(Long.valueOf(bookContractsNumber) * Long.valueOf(bookSoldCount)));
	        	if(Boolean.valueOf(custody)) {
	        		eMaazounProfileAuditDTO.setBookZawagMelalySoldCountCustody(eMaazounProfileAuditDTO.getBookZawagMelalySoldCountCustody() + 
	        				Long.valueOf(bookSoldCount));
	        	}else {
	        		eMaazounProfileAuditDTO.setBookZawagMelalySoldCountSerial(eMaazounProfileAuditDTO.getBookZawagMelalySoldCountSerial() + 
	        				Long.valueOf(bookSoldCount));
	        	}
	        	
		        
	        }else if(bookTypeName.trim().equals(StatusConstant.BOOK_ZAWAG_MUSLIM_NAME)) {
	        	eMaazounProfileAuditDTO.setBookZawagMuslimContractCount(eMaazounProfileAuditDTO.getBookZawagMuslimContractCount() + 
	        			(Long.valueOf(bookContractsNumber) * Long.valueOf(bookSoldCount)));
	        	if(Boolean.valueOf(custody)) {
	        		eMaazounProfileAuditDTO.setBookZawagMuslimSoldCountCustody(eMaazounProfileAuditDTO.getBookZawagMuslimSoldCountCustody() + 
	        				Long.valueOf(bookSoldCount));
	        	}else {
	        		eMaazounProfileAuditDTO.setBookZawagMuslimSoldCountSerial(eMaazounProfileAuditDTO.getBookZawagMuslimSoldCountSerial() + 
	        				Long.valueOf(bookSoldCount));
	        	}
	        	   
	        }
	        
	        result.add(eMaazounProfileAuditDTO); 	      
	        
	     }
	    
//	    for(MaazounProfileAuditDTO obj : result) {
//	    	System.out.println("BookMoragaaSoldCountSerial = "+obj.getBookMoragaaSoldCountSerial()+", "+
//	    			"TalakSoldCountSerial = "+obj.getBookTalakSoldCountSerial()+", "+
//	    			"TasadokSoldCountSerial = "+obj.getBookTasadokSoldCountSerial()+", "+
//	    			"BookZawagMelalySoldCountSerial = "+obj.getBookZawagMelalySoldCountSerial()+", "+
//	    			"BookZawagMuslimSoldCountSerial = "+obj.getBookZawagMuslimSoldCountSerial()+", ");
//	    	System.out.println("BookMoragaaSoldCountCustody = "+obj.getBookMoragaaSoldCountCustody()+", "+
//	    			"TalakSoldCountCustody = "+obj.getBookTalakSoldCountCustody()+", "+
//	    			"TasadokSoldCountCustody = "+obj.getBookTasadokSoldCountCustody()+", "+
//	    			"BookZawagMelalySoldCountCustody = "+obj.getBookZawagMelalySoldCountCustody()+", "+
//	    			"BookZawagMuslimSoldCountCustody = "+obj.getBookZawagMuslimSoldCountCustody()+", ");
//	    }
	    //---------------------------get remaining data from collection table
	    StringBuilder jpql2 = new StringBuilder();
	    jpql2.append("SELECT"
				+ " SUM(IF (i.status_fk = 'Collected', 1, 0)) as contractCollectedCount,"
				+ " SUM(IF (i.status_fk = 'Received', 1, 0)) as contractReceivedCount,"
				+ " i.contract_type_id as contractTypeId,"
				+ " i.maazoun_national_id as maazounNationalId, i.maazoun_profile_fk as maazounId"
				
				+ " FROM maazoun_book_collection_info i "
				//+ " JOIN maazoun_profile m ON m.id = i.maazoun_profile_fk");
				+" JOIN maazoun_book_warehouse w ON w.maazoun_book_collection_info_fk = i.id");
			    if(maazounProfileAuditRequest.getMaazounNationalId() != null && !maazounProfileAuditRequest.getMaazounNationalId().isEmpty()) {
			    	jpql2.append(" WHERE i.maazoun_national_id = '"+maazounProfileAuditRequest.getMaazounNationalId()+"' ");
				}
			    jpql2.append(" GROUP BY i.maazoun_profile_fk, i.contract_type_id");
	    
	    Query query2 = em.createNativeQuery(jpql2.toString());
		
		List<Object[]> coolectionList = query2.getResultList();
		
	    for(Object[] q1 : coolectionList){

	    	String contractCollectedCount = q1[0].toString();
	        String contractReceivedCount = q1[1].toString();
	        String contractTypeId = q1[2].toString();
	        String contarctMaazounNationalId = q1[3].toString();
	        String maazounId = q1[4].toString();
	        
	        MaazounProfileAuditDTO eMaazounProfileAuditDTO = result.stream()
					.filter(x -> x.getMaazounId().equals(maazounId)).findAny()
					.orElse(null);
	        if(eMaazounProfileAuditDTO != null) {
	        	if(contractTypeId.equals(StatusConstant.CONTRACT_MORAGAA_ID)) {	   
		        	eMaazounProfileAuditDTO.setContractMoragaaCollectedCount(Long.valueOf(contractCollectedCount));
		        	eMaazounProfileAuditDTO.setContractMoragaaReceivedCount(Long.valueOf(contractReceivedCount));
		        	eMaazounProfileAuditDTO.setContractMoragaaWithMaazounCount(eMaazounProfileAuditDTO.getBookMoragaaContractCount() - Long.valueOf(contractReceivedCount));
		        	
		        }else if(contractTypeId.equals(StatusConstant.CONTRACT_TALAK_ID)) {
		        	eMaazounProfileAuditDTO.setContractTalakCollectedCount(Long.valueOf(contractCollectedCount));
		        	eMaazounProfileAuditDTO.setContractTalakReceivedCount(Long.valueOf(contractReceivedCount));
		        	eMaazounProfileAuditDTO.setContractTalakWithMaazounCount(eMaazounProfileAuditDTO.getBookTalakContractCount() - Long.valueOf(contractReceivedCount));
		        	
		        }else if(contractTypeId.equals(StatusConstant.CONTRACT_TASADOK_ID)) {
		        	eMaazounProfileAuditDTO.setContractTasadokCollectedCount(Long.valueOf(contractCollectedCount));
		        	eMaazounProfileAuditDTO.setContractTasadokReceivedCount(Long.valueOf(contractReceivedCount));
		        	eMaazounProfileAuditDTO.setContractTasadokWithMaazounCount(eMaazounProfileAuditDTO.getBookTasadokContractCount() - Long.valueOf(contractReceivedCount));
		        		        	
		        }else if(contractTypeId.equals(StatusConstant.CONTRACT_ZAWAG_MELALY_ID)) {
		        	eMaazounProfileAuditDTO.setContractZawagMelalyCollectedCount(Long.valueOf(contractCollectedCount));
		        	eMaazounProfileAuditDTO.setContractZawagMelalyReceivedCount(Long.valueOf(contractReceivedCount));
		        	eMaazounProfileAuditDTO.setContractZawagMelalyWithMaazounCount(eMaazounProfileAuditDTO.getBookZawagMelalyContractCount() - Long.valueOf(contractReceivedCount));
		        		        	
		        }else if(contractTypeId.equals(StatusConstant.CONTRACT_ZAWAG_MUSLIM_ID)) {
		        	eMaazounProfileAuditDTO.setContractZawagMuslimCollectedCount(Long.valueOf(contractCollectedCount));
		        	eMaazounProfileAuditDTO.setContractZawagMuslimReceivedCount(Long.valueOf(contractReceivedCount));
		        	eMaazounProfileAuditDTO.setContractZawagMuslimWithMaazounCount(eMaazounProfileAuditDTO.getBookZawagMuslimContractCount() - Long.valueOf(contractReceivedCount));
		        	
		        }
	        }
	        
	    }

	    return new PageImpl<MaazounProfileAuditDTO>(new ArrayList<>(result), pageable, total);
	}

	@Override
	public Page<MaazounProfileAuditDTO> getMaazounCustodyCustomAudit(MaazounProfileAuditRequest maazounProfileAuditRequest) {
		
		Pageable pageable = PageRequest.of(Integer.valueOf(maazounProfileAuditRequest.getPageNo()), 
				Integer.valueOf(maazounProfileAuditRequest.getPageSize()));
		
		Set<MaazounProfileAuditDTO> result = new HashSet<MaazounProfileAuditDTO>();
//		StringBuilder jpql = new StringBuilder();
//		jpql.append("SELECT"
//				+ " COUNT(DISTINCT i.book_serial_number) as bookSoldCount,"
//				+ " i.book_type_id as bookTypeId,"
//				+ " m.name as maazounName,"
//				+ " m.national_id as maazounNationalId,"
//				+ " (SELECT DISTINCT(w.contract_count) FROM maazoun_book_warehouse w WHERE w.book_financial_number = i.book_serial_number OR w.serial_number = i.book_serial_number) as bookContractsNumber "
//				
//				+ " FROM maazoun_book_request_info i "
//				+ " JOIN maazoun_profile m ON m.id = i.maazoun_profile_fk"
//				+ " JOIN maazoun_book_warehouse w ON w.maazoun_book_request_info_fk = i.id"
//				+ " JOIN maazoun_book_supply_order s ON w.maazoun_book_supply_order_fk = s.id"
//				+ " WHERE s.is_custody IS TRUE");
//		
//				if(maazounProfileAuditRequest.getMaazounNationalId() != null && !maazounProfileAuditRequest.getMaazounNationalId().isEmpty()) {
//					jpql.append(" AND m.national_id = '"+maazounProfileAuditRequest.getMaazounNationalId()+"' ");
//				}
//				jpql.append(" GROUP BY i.maazoun_profile_fk, i.book_type_id");
//		
//				StringBuilder jpqlCount = new StringBuilder();
//				jpqlCount.append("SELECT COUNT(*) "
//				+ " FROM maazoun_book_request_info i "
//				+ " JOIN maazoun_profile m ON m.id = i.maazoun_profile_fk");
//		
//				if(maazounProfileAuditRequest.getMaazounNationalId() != null && !maazounProfileAuditRequest.getMaazounNationalId().isEmpty()) {
//					jpqlCount.append(" WHERE m.national_id = '"+maazounProfileAuditRequest.getMaazounNationalId()+"' ");
//				}
//				jpqlCount.append(" GROUP BY i.maazoun_profile_fk, i.book_type_id");
//		
//		
//		Query query = em.createNativeQuery(jpql.toString());
//		query.setFirstResult((int) pageable.getOffset());
//		query.setMaxResults(pageable.getPageSize());
//
//		Query queryCount = em.createNativeQuery(jpqlCount.toString());
//		long total = queryCount.getResultList().size();
//		
//		List<Object[]> list = query.getResultList();
//		
//	    for(Object[] q1 : list){
//
//	    	String bookSoldCount = q1[0].toString();
//	        String bookTypeId = q1[1].toString();
//	        String maazounName = q1[2].toString();
//	        
//	        String maazounNationalId = q1[3].toString();
//	        String bookContractsNumber = q1[4].toString();
//	        
//	        
//	        MaazounProfileAuditDTO eMaazounProfileAuditDTO = result.stream()
//					.filter(x -> x.getMaazounNationalId().equals(maazounNationalId)).findAny()
//					.orElse(null);
//	        
//	        if(eMaazounProfileAuditDTO == null) {
//	        	eMaazounProfileAuditDTO = new MaazounProfileAuditDTO();
//	        	
//	        	eMaazounProfileAuditDTO.setMaazounName(maazounName);
//	        	eMaazounProfileAuditDTO.setMaazounNationalId(maazounNationalId);
//	        	eMaazounProfileAuditDTO.setTypeId(Long.valueOf(bookTypeId));
//	        	
//	        	eMaazounProfileAuditDTO.setBookMoragaaSoldCount((long) 0);
//	        	eMaazounProfileAuditDTO.setBookMoragaaContractCount((long) 0);
//	        	
//	        	eMaazounProfileAuditDTO.setBookTalakContractCount((long) 0);
//	        	eMaazounProfileAuditDTO.setBookTalakSoldCount((long) 0);
//	        	
//	        	eMaazounProfileAuditDTO.setBookTasadokContractCount((long) 0);
//	        	eMaazounProfileAuditDTO.setBookTasadokSoldCount((long) 0);
//	        	
//	        	eMaazounProfileAuditDTO.setBookZawagMelalyContractCount((long) 0);
//	        	eMaazounProfileAuditDTO.setBookZawagMelalySoldCount((long) 0);
//	        	
//	        	eMaazounProfileAuditDTO.setBookZawagMuslimContractCount((long) 0);
//	        	eMaazounProfileAuditDTO.setBookZawagMuslimSoldCount((long) 0);
//	        	
//	        	eMaazounProfileAuditDTO.setContractMoragaaCollectedCount((long) 0);
//	        	eMaazounProfileAuditDTO.setContractMoragaaReceivedCount((long) 0);
//	        	eMaazounProfileAuditDTO.setContractMoragaaWithMaazounCount((long) 0);
//	        	
//	        	eMaazounProfileAuditDTO.setContractTalakCollectedCount((long) 0);
//	        	eMaazounProfileAuditDTO.setContractTalakReceivedCount((long) 0);
//	        	eMaazounProfileAuditDTO.setContractTalakWithMaazounCount((long) 0);
//	        	
//	        	eMaazounProfileAuditDTO.setContractTasadokCollectedCount((long) 0);
//	        	eMaazounProfileAuditDTO.setContractTasadokReceivedCount((long) 0);
//	        	eMaazounProfileAuditDTO.setContractTasadokWithMaazounCount((long) 0);
//	        	
//	        	eMaazounProfileAuditDTO.setContractZawagMelalyCollectedCount((long) 0);
//	        	eMaazounProfileAuditDTO.setContractZawagMelalyReceivedCount((long) 0);
//	        	eMaazounProfileAuditDTO.setContractZawagMelalyWithMaazounCount((long) 0);
//	        	
//	        	eMaazounProfileAuditDTO.setContractZawagMuslimCollectedCount((long) 0);
//	        	eMaazounProfileAuditDTO.setContractZawagMuslimReceivedCount((long) 0);
//	        	eMaazounProfileAuditDTO.setContractZawagMuslimWithMaazounCount((long) 0);	        			    
//		        
//	        }
//	        
//	        if(bookTypeId.equals(StatusConstant.BOOK_MORAGAA_ID_8)) {	   
//	        	eMaazounProfileAuditDTO.setBookMoragaaContractCount(Long.valueOf(bookContractsNumber) * Long.valueOf(bookSoldCount));
//	        	eMaazounProfileAuditDTO.setBookMoragaaSoldCount(Long.valueOf(bookSoldCount));
//		      
//	        }else if(bookTypeId.equals(StatusConstant.BOOK_TALAK_ID_8)) {
//	        	eMaazounProfileAuditDTO.setBookTalakContractCount(Long.valueOf(bookContractsNumber) * Long.valueOf(bookSoldCount));
//	        	eMaazounProfileAuditDTO.setBookTalakSoldCount(Long.valueOf(bookSoldCount));
//	        	
//	        	
//	        }else if(bookTypeId.equals(StatusConstant.BOOK_TASADOK_ID_8)) {
//	        	eMaazounProfileAuditDTO.setBookTasadokContractCount(Long.valueOf(bookContractsNumber) * Long.valueOf(bookSoldCount));
//	        	eMaazounProfileAuditDTO.setBookTasadokSoldCount(Long.valueOf(bookSoldCount));
//	        	
//	 	       
//	        }else if(bookTypeId.equals(StatusConstant.BOOK_ZAWAG_MELALY_ID_8)) {
//	        	eMaazounProfileAuditDTO.setBookZawagMelalyContractCount(Long.valueOf(bookContractsNumber) * Long.valueOf(bookSoldCount));
//	        	eMaazounProfileAuditDTO.setBookZawagMelalySoldCount(Long.valueOf(bookSoldCount));
//	        	
//		        
//	        }else if(bookTypeId.equals(StatusConstant.BOOK_ZAWAG_MUSLIM_ID_8)) {
//	        	eMaazounProfileAuditDTO.setBookZawagMuslimContractCount(Long.valueOf(bookContractsNumber) * Long.valueOf(bookSoldCount));
//	        	eMaazounProfileAuditDTO.setBookZawagMuslimSoldCount(Long.valueOf(bookSoldCount));
//		        
//		        
//	        }
//	        
//	        result.add(eMaazounProfileAuditDTO); 	      
//	        
//	     }
//	    
//	    //---------------------------get remaining data from collection table
//	    StringBuilder jpql2 = new StringBuilder();
//	    jpql2.append("SELECT COUNT(DISTINCT i.id),"
//				+ " SUM(IF (i.status_fk = 'Collected', 1, 0)) as contractCollectedCount,"
//				+ " SUM(IF (i.status_fk = 'Received', 1, 0)) as contractReceivedCount,"
//				+ " i.contract_type_id as contractTypeId,"
//				+ " m.national_id as maazounNationalId"
//				
//				+ " FROM maazoun_book_collection_info i "
//				+ " JOIN maazoun_profile m ON m.id = i.maazoun_profile_fk"
//				+ " JOIN maazoun_book_warehouse w ON w.maazoun_book_request_info_fk = i.id"
//				+ " JOIN maazoun_book_supply_order s ON w.maazoun_book_supply_order_fk = s.id"
//				+ " WHERE s.is_custody IS TRUE");
//			    if(maazounProfileAuditRequest.getMaazounNationalId() != null && !maazounProfileAuditRequest.getMaazounNationalId().isEmpty()) {
//			    	jpql2.append(" AND m.national_id = '"+maazounProfileAuditRequest.getMaazounNationalId()+"' ");
//				}
//			    jpql2.append(" GROUP BY i.maazoun_profile_fk, i.contract_type_id");
//	    
//	    Query query2 = em.createNativeQuery(jpql2.toString());
//		
//		List<Object[]> coolectionList = query2.getResultList();
//		
//	    for(Object[] q1 : coolectionList){
//
//	    	String contractCollectedCount = q1[1].toString();
//	        String contractReceivedCount = q1[2].toString();
//	        String contractTypeId = q1[3].toString();
//	        String contarctMaazounNationalId = q1[4].toString();
//	        
//	        MaazounProfileAuditDTO eMaazounProfileAuditDTO = result.stream()
//					.filter(x -> x.getMaazounNationalId().equals(contarctMaazounNationalId)).findAny()
//					.orElse(null);
//	        if(eMaazounProfileAuditDTO != null) {
//	        	if(contractTypeId.equals(StatusConstant.CONTRACT_MORAGAA_ID)) {	   
//		        	eMaazounProfileAuditDTO.setContractMoragaaCollectedCount(Long.valueOf(contractCollectedCount));
//		        	eMaazounProfileAuditDTO.setContractMoragaaReceivedCount(Long.valueOf(contractReceivedCount));
//		        	eMaazounProfileAuditDTO.setContractMoragaaWithMaazounCount(eMaazounProfileAuditDTO.getBookMoragaaContractCount() - Long.valueOf(contractReceivedCount));
//		        	
//			      
//		        }else if(contractTypeId.equals(StatusConstant.CONTRACT_TALAK_ID)) {
//		        	eMaazounProfileAuditDTO.setContractTalakCollectedCount(Long.valueOf(contractCollectedCount));
//		        	eMaazounProfileAuditDTO.setContractTalakReceivedCount(Long.valueOf(contractReceivedCount));
//		        	eMaazounProfileAuditDTO.setContractTalakWithMaazounCount((long) 0);
//		        	
//		        }else if(contractTypeId.equals(StatusConstant.CONTRACT_TASADOK_ID)) {
//		        	eMaazounProfileAuditDTO.setContractTasadokCollectedCount(Long.valueOf(contractCollectedCount));
//		        	eMaazounProfileAuditDTO.setContractTasadokReceivedCount(Long.valueOf(contractReceivedCount));
//		        	eMaazounProfileAuditDTO.setContractTasadokWithMaazounCount((long) 0);
//		        		        	
//		        }else if(contractTypeId.equals(StatusConstant.CONTRACT_ZAWAG_MELALY_ID)) {
//		        	eMaazounProfileAuditDTO.setContractZawagMelalyCollectedCount(Long.valueOf(contractCollectedCount));
//		        	eMaazounProfileAuditDTO.setContractZawagMelalyReceivedCount(Long.valueOf(contractReceivedCount));
//		        	eMaazounProfileAuditDTO.setContractZawagMelalyWithMaazounCount((long) 0);
//		        		        	
//		        }else if(contractTypeId.equals(StatusConstant.CONTRACT_ZAWAG_MUSLIM_ID)) {
//		        	eMaazounProfileAuditDTO.setContractZawagMuslimCollectedCount(Long.valueOf(contractCollectedCount));
//		        	eMaazounProfileAuditDTO.setContractZawagMuslimReceivedCount(Long.valueOf(contractReceivedCount));
//		        	eMaazounProfileAuditDTO.setContractZawagMuslimWithMaazounCount((long) 0);
//		        	
//		        }
//	        }
//	        
//	    }
	    

	    return new PageImpl<MaazounProfileAuditDTO>(new ArrayList<>(result), pageable, 0);
	}
	
	@Override
	public Page<MaazounProfileAuditDTO> getMaazounSerialsCustomAudit(MaazounProfileAuditRequest maazounProfileAuditRequest) {
		
		Pageable pageable = PageRequest.of(Integer.valueOf(maazounProfileAuditRequest.getPageNo()), 
				Integer.valueOf(maazounProfileAuditRequest.getPageSize()));
		
		Set<MaazounProfileAuditDTO> result = new HashSet<MaazounProfileAuditDTO>();
//		StringBuilder jpql = new StringBuilder();
//		jpql.append("SELECT"
//				+ " COUNT(DISTINCT i.book_serial_number) as bookSoldCount,"
//				+ " i.book_type_id as bookTypeId,"
//				+ " m.name as maazounName,"
//				+ " m.national_id as maazounNationalId,"
//				+ " (SELECT DISTINCT(w.contract_count) FROM maazoun_book_warehouse w WHERE w.book_financial_number = i.book_serial_number OR w.serial_number = i.book_serial_number) as bookContractsNumber "
//				
//				+ " FROM maazoun_book_request_info i "
//				+ " JOIN maazoun_profile m ON m.id = i.maazoun_profile_fk"
//				+ " JOIN maazoun_book_warehouse w ON w.maazoun_book_request_info_fk = i.id"
//				+ " JOIN maazoun_book_supply_order s ON w.maazoun_book_supply_order_fk = s.id"
//				+ " WHERE s.is_custody IS FALSE");
//		
//				if(maazounProfileAuditRequest.getMaazounNationalId() != null && !maazounProfileAuditRequest.getMaazounNationalId().isEmpty()) {
//					jpql.append(" AND m.national_id = '"+maazounProfileAuditRequest.getMaazounNationalId()+"' ");
//				}
//				jpql.append(" GROUP BY i.maazoun_profile_fk, i.book_type_id");
//		
//				StringBuilder jpqlCount = new StringBuilder();
//				jpqlCount.append("SELECT COUNT(*) "
//				+ " FROM maazoun_book_request_info i "
//				+ " JOIN maazoun_profile m ON m.id = i.maazoun_profile_fk");
//		
//				if(maazounProfileAuditRequest.getMaazounNationalId() != null && !maazounProfileAuditRequest.getMaazounNationalId().isEmpty()) {
//					jpqlCount.append(" WHERE m.national_id = '"+maazounProfileAuditRequest.getMaazounNationalId()+"' ");
//				}
//				jpqlCount.append(" GROUP BY i.maazoun_profile_fk, i.book_type_id");
//		
//		
//		Query query = em.createNativeQuery(jpql.toString());
//		query.setFirstResult((int) pageable.getOffset());
//		query.setMaxResults(pageable.getPageSize());
//
//		Query queryCount = em.createNativeQuery(jpqlCount.toString());
//		long total = queryCount.getResultList().size();
//		
//		List<Object[]> list = query.getResultList();
//		
//	    for(Object[] q1 : list){
//
//	    	String bookSoldCount = q1[0].toString();
//	        String bookTypeId = q1[1].toString();
//	        String maazounName = q1[2].toString();
//	        
//	        String maazounNationalId = q1[3].toString();
//	        String bookContractsNumber = q1[4].toString();
//	        
//	        
//	        MaazounProfileAuditDTO eMaazounProfileAuditDTO = result.stream()
//					.filter(x -> x.getMaazounNationalId().equals(maazounNationalId)).findAny()
//					.orElse(null);
//	        
//	        if(eMaazounProfileAuditDTO == null) {
//	        	eMaazounProfileAuditDTO = new MaazounProfileAuditDTO();
//	        	
//	        	eMaazounProfileAuditDTO.setMaazounName(maazounName);
//	        	eMaazounProfileAuditDTO.setMaazounNationalId(maazounNationalId);
//	        	eMaazounProfileAuditDTO.setTypeId(Long.valueOf(bookTypeId));
//	        	
//	        	eMaazounProfileAuditDTO.setBookMoragaaSoldCount((long) 0);
//	        	eMaazounProfileAuditDTO.setBookMoragaaContractCount((long) 0);
//	        	
//	        	eMaazounProfileAuditDTO.setBookTalakContractCount((long) 0);
//	        	eMaazounProfileAuditDTO.setBookTalakSoldCount((long) 0);
//	        	
//	        	eMaazounProfileAuditDTO.setBookTasadokContractCount((long) 0);
//	        	eMaazounProfileAuditDTO.setBookTasadokSoldCount((long) 0);
//	        	
//	        	eMaazounProfileAuditDTO.setBookZawagMelalyContractCount((long) 0);
//	        	eMaazounProfileAuditDTO.setBookZawagMelalySoldCount((long) 0);
//	        	
//	        	eMaazounProfileAuditDTO.setBookZawagMuslimContractCount((long) 0);
//	        	eMaazounProfileAuditDTO.setBookZawagMuslimSoldCount((long) 0);
//	        	
//	        	eMaazounProfileAuditDTO.setContractMoragaaCollectedCount((long) 0);
//	        	eMaazounProfileAuditDTO.setContractMoragaaReceivedCount((long) 0);
//	        	eMaazounProfileAuditDTO.setContractMoragaaWithMaazounCount((long) 0);
//	        	
//	        	eMaazounProfileAuditDTO.setContractTalakCollectedCount((long) 0);
//	        	eMaazounProfileAuditDTO.setContractTalakReceivedCount((long) 0);
//	        	eMaazounProfileAuditDTO.setContractTalakWithMaazounCount((long) 0);
//	        	
//	        	eMaazounProfileAuditDTO.setContractTasadokCollectedCount((long) 0);
//	        	eMaazounProfileAuditDTO.setContractTasadokReceivedCount((long) 0);
//	        	eMaazounProfileAuditDTO.setContractTasadokWithMaazounCount((long) 0);
//	        	
//	        	eMaazounProfileAuditDTO.setContractZawagMelalyCollectedCount((long) 0);
//	        	eMaazounProfileAuditDTO.setContractZawagMelalyReceivedCount((long) 0);
//	        	eMaazounProfileAuditDTO.setContractZawagMelalyWithMaazounCount((long) 0);
//	        	
//	        	eMaazounProfileAuditDTO.setContractZawagMuslimCollectedCount((long) 0);
//	        	eMaazounProfileAuditDTO.setContractZawagMuslimReceivedCount((long) 0);
//	        	eMaazounProfileAuditDTO.setContractZawagMuslimWithMaazounCount((long) 0);	        			    
//		        
//	        }
//	        
//	        if(bookTypeId.equals(StatusConstant.BOOK_MORAGAA_ID_8)) {	   
//	        	eMaazounProfileAuditDTO.setBookMoragaaContractCount(Long.valueOf(bookContractsNumber) * Long.valueOf(bookSoldCount));
//	        	eMaazounProfileAuditDTO.setBookMoragaaSoldCount(Long.valueOf(bookSoldCount));
//		      
//	        }else if(bookTypeId.equals(StatusConstant.BOOK_TALAK_ID_8)) {
//	        	eMaazounProfileAuditDTO.setBookTalakContractCount(Long.valueOf(bookContractsNumber) * Long.valueOf(bookSoldCount));
//	        	eMaazounProfileAuditDTO.setBookTalakSoldCount(Long.valueOf(bookSoldCount));
//	        	
//	        	
//	        }else if(bookTypeId.equals(StatusConstant.BOOK_TASADOK_ID_8)) {
//	        	eMaazounProfileAuditDTO.setBookTasadokContractCount(Long.valueOf(bookContractsNumber) * Long.valueOf(bookSoldCount));
//	        	eMaazounProfileAuditDTO.setBookTasadokSoldCount(Long.valueOf(bookSoldCount));
//	        	
//	 	       
//	        }else if(bookTypeId.equals(StatusConstant.BOOK_ZAWAG_MELALY_ID_8)) {
//	        	eMaazounProfileAuditDTO.setBookZawagMelalyContractCount(Long.valueOf(bookContractsNumber) * Long.valueOf(bookSoldCount));
//	        	eMaazounProfileAuditDTO.setBookZawagMelalySoldCount(Long.valueOf(bookSoldCount));
//	        	
//		        
//	        }else if(bookTypeId.equals(StatusConstant.BOOK_ZAWAG_MUSLIM_ID_8)) {
//	        	eMaazounProfileAuditDTO.setBookZawagMuslimContractCount(Long.valueOf(bookContractsNumber) * Long.valueOf(bookSoldCount));
//	        	eMaazounProfileAuditDTO.setBookZawagMuslimSoldCount(Long.valueOf(bookSoldCount));
//		        
//		        
//	        }
//	        
//	        result.add(eMaazounProfileAuditDTO); 	      
//	        
//	     }
//	    
//	    //---------------------------get remaining data from collection table
//	    StringBuilder jpql2 = new StringBuilder();
//	    jpql2.append("SELECT COUNT(DISTINCT i.id),"
//				+ " SUM(IF (i.status_fk = 'Collected', 1, 0)) as contractCollectedCount,"
//				+ " SUM(IF (i.status_fk = 'Received', 1, 0)) as contractReceivedCount,"
//				+ " i.contract_type_id as contractTypeId,"
//				+ " m.national_id as maazounNationalId"
//				
//				+ " FROM maazoun_book_collection_info i "
//				+ " JOIN maazoun_profile m ON m.id = i.maazoun_profile_fk"
//				+ " JOIN maazoun_book_warehouse w ON w.maazoun_book_request_info_fk = i.id"
//				+ " JOIN maazoun_book_supply_order s ON w.maazoun_book_supply_order_fk = s.id"
//				+ " WHERE s.is_custody IS FALSE");
//			    if(maazounProfileAuditRequest.getMaazounNationalId() != null && !maazounProfileAuditRequest.getMaazounNationalId().isEmpty()) {
//			    	jpql2.append(" AND m.national_id = '"+maazounProfileAuditRequest.getMaazounNationalId()+"' ");
//				}
//			    jpql2.append(" GROUP BY i.maazoun_profile_fk, i.contract_type_id");
//	    
//	    Query query2 = em.createNativeQuery(jpql2.toString());
//		
//		List<Object[]> coolectionList = query2.getResultList();
//		
//	    for(Object[] q1 : coolectionList){
//
//	    	String contractCollectedCount = q1[1].toString();
//	        String contractReceivedCount = q1[2].toString();
//	        String contractTypeId = q1[3].toString();
//	        String contarctMaazounNationalId = q1[4].toString();
//	        
//	        MaazounProfileAuditDTO eMaazounProfileAuditDTO = result.stream()
//					.filter(x -> x.getMaazounNationalId().equals(contarctMaazounNationalId)).findAny()
//					.orElse(null);
//	        if(eMaazounProfileAuditDTO != null) {
//	        	if(contractTypeId.equals(StatusConstant.CONTRACT_MORAGAA_ID)) {	   
//		        	eMaazounProfileAuditDTO.setContractMoragaaCollectedCount(Long.valueOf(contractCollectedCount));
//		        	eMaazounProfileAuditDTO.setContractMoragaaReceivedCount(Long.valueOf(contractReceivedCount));
//		        	eMaazounProfileAuditDTO.setContractMoragaaWithMaazounCount(eMaazounProfileAuditDTO.getBookMoragaaContractCount() - Long.valueOf(contractReceivedCount));
//		        	
//			      
//		        }else if(contractTypeId.equals(StatusConstant.CONTRACT_TALAK_ID)) {
//		        	eMaazounProfileAuditDTO.setContractTalakCollectedCount(Long.valueOf(contractCollectedCount));
//		        	eMaazounProfileAuditDTO.setContractTalakReceivedCount(Long.valueOf(contractReceivedCount));
//		        	eMaazounProfileAuditDTO.setContractTalakWithMaazounCount((long) 0);
//		        	
//		        }else if(contractTypeId.equals(StatusConstant.CONTRACT_TASADOK_ID)) {
//		        	eMaazounProfileAuditDTO.setContractTasadokCollectedCount(Long.valueOf(contractCollectedCount));
//		        	eMaazounProfileAuditDTO.setContractTasadokReceivedCount(Long.valueOf(contractReceivedCount));
//		        	eMaazounProfileAuditDTO.setContractTasadokWithMaazounCount((long) 0);
//		        		        	
//		        }else if(contractTypeId.equals(StatusConstant.CONTRACT_ZAWAG_MELALY_ID)) {
//		        	eMaazounProfileAuditDTO.setContractZawagMelalyCollectedCount(Long.valueOf(contractCollectedCount));
//		        	eMaazounProfileAuditDTO.setContractZawagMelalyReceivedCount(Long.valueOf(contractReceivedCount));
//		        	eMaazounProfileAuditDTO.setContractZawagMelalyWithMaazounCount((long) 0);
//		        		        	
//		        }else if(contractTypeId.equals(StatusConstant.CONTRACT_ZAWAG_MUSLIM_ID)) {
//		        	eMaazounProfileAuditDTO.setContractZawagMuslimCollectedCount(Long.valueOf(contractCollectedCount));
//		        	eMaazounProfileAuditDTO.setContractZawagMuslimReceivedCount(Long.valueOf(contractReceivedCount));
//		        	eMaazounProfileAuditDTO.setContractZawagMuslimWithMaazounCount((long) 0);
//		        	
//		        }
//	        }
//	        
//	    }
	    

	    return new PageImpl<MaazounProfileAuditDTO>(new ArrayList<>(result), pageable, 0);
	}

	
}
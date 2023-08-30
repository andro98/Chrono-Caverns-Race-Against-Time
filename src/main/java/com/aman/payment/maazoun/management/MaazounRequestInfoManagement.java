package com.aman.payment.maazoun.management;

import java.io.InputStream;

import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.core.model.audit.TransactionECRAudit;
import com.aman.payment.core.model.payload.PosMidRequest;
import com.aman.payment.maazoun.model.dto.MaazounRequestInfoPagingDTO;
import com.aman.payment.maazoun.model.dto.PagingDTO;
import com.aman.payment.maazoun.model.payload.AddMaazounRequestInfoRequest;
import com.aman.payment.maazoun.model.payload.AdvancedSearchBookRequestInfoTransactionRequest;
import com.aman.payment.maazoun.model.payload.GetBookRequestInfoById;
import com.aman.payment.maazoun.model.payload.GetBookRequestInfoByMaazounId;
import com.aman.payment.maazoun.model.payload.MaazounAuditRequest;
import com.aman.payment.maazoun.model.payload.MaazounBookRequestInfoRefundRequest;
import com.aman.payment.maazoun.model.payload.QuickSearchBookRequestInfoTransactionRequest;
import com.aman.payment.maazoun.model.payload.URLRequest;

public interface MaazounRequestInfoManagement {
	
	public String addBookRequestInfo(CustomUserDetails customUserDetails, AddMaazounRequestInfoRequest addMaazounRequestInfoRequest,
			TransactionECRAudit eTransactionECRAudit);
	
	public String createRefundBookRequestInfo(CustomUserDetails customUserDetails,
			MaazounBookRequestInfoRefundRequest maazounBookRequestInfoRefundRequest, TransactionECRAudit eTransactionECRAudit);
	
	public MaazounRequestInfoPagingDTO quickSeracBookRequestInfoTransactions(
			QuickSearchBookRequestInfoTransactionRequest quickSearchBookRequestInfoTransactionRequest, 
			CustomUserDetails customUserDetails);
	
	public MaazounRequestInfoPagingDTO advancedSerachBookRequestInfoTransactions(
			AdvancedSearchBookRequestInfoTransactionRequest advancedSearchBookRequestInfoTransactionRequest,
			CustomUserDetails customUserDetails);
	
	public InputStream downloadFile(URLRequest uRLRequest);
	
	public MaazounRequestInfoPagingDTO getAuditByCreatedDate(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public MaazounRequestInfoPagingDTO getAuditByCreatedDateAndCourt(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public MaazounRequestInfoPagingDTO getAuditByCreatedDateAndAgentAndCourt(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public PagingDTO getBookCustomAuditBySectors(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public PagingDTO getBookCustomAuditByCourts(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public String calculateSalePosMidRequestAmountFormat(PosMidRequest posMidRequest, CustomUserDetails customUserDetails);
	
	public String calculateRefundPosMidRequestAmountFormat(PosMidRequest posMidRequest);
	
	public PagingDTO getBookRequestInfoByMaazounId(GetBookRequestInfoByMaazounId getBookRequestInfoByMaazounId);
	
	public String editBookRequestInfoMaazounId(CustomUserDetails customUserDetails, GetBookRequestInfoById getBookRequestInfoById);
}

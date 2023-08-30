package com.aman.payment.maazoun.management;

import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.core.model.audit.TransactionECRAudit;
import com.aman.payment.core.model.payload.PosMidRequest;
import com.aman.payment.maazoun.model.dto.MaazounCollectInfoPagingDTO;
import com.aman.payment.maazoun.model.dto.PagingDTO;
import com.aman.payment.maazoun.model.payload.AddMaazounCollectionInfoRequest;
import com.aman.payment.maazoun.model.payload.AddMaazounContractRequest;
import com.aman.payment.maazoun.model.payload.AddMaazounCustodyContractRequest;
import com.aman.payment.maazoun.model.payload.AddReceivedContractStatusRequest;
import com.aman.payment.maazoun.model.payload.AddReceivedMaazounBookRequest;
import com.aman.payment.maazoun.model.payload.AdvancedSearchCollectionInfoTransactionRequest;
import com.aman.payment.maazoun.model.payload.CollectionListSearchRequest;
import com.aman.payment.maazoun.model.payload.ContractByNationalIdRequest;
import com.aman.payment.maazoun.model.payload.DeleteMaazounContractRequest;
import com.aman.payment.maazoun.model.payload.EditMaazounContractRequest;
import com.aman.payment.maazoun.model.payload.MaazounAuditRequest;
import com.aman.payment.maazoun.model.payload.MaazounBookRequestInfoRefundRequest;
import com.aman.payment.maazoun.model.payload.MaazounContractInfoRefundRequest;
import com.aman.payment.maazoun.model.payload.QuickSearchCollectiontInfoTransactionRequest;
import com.aman.payment.maazoun.model.payload.ReviewRequest;
import com.aman.payment.maazoun.model.payload.URLRequest;

public interface MaazounBookCollectionInfoManagement {
	
	public List<String> getBookChecklist(long subServiceId);
	
	public String addContractRequest(CustomUserDetails customUserDetails, 
			AddMaazounContractRequest addMaazounContractRequest);
	
	public String editCustodyContractRequest(CustomUserDetails customUserDetails, 
			AddMaazounCustodyContractRequest addMaazounCustodyContractRequest);
	
	public String deleteContractRequest(CustomUserDetails customUserDetails, 
			DeleteMaazounContractRequest deleteMaazounContractRequest);
	
	public String createCollectionRequest(CustomUserDetails customUserDetails, 
			AddMaazounCollectionInfoRequest addMaazounCollectionInfoRequest, TransactionECRAudit eTransactionECRAudit);
	
	public List<String> getHoldCollectionRequest(CustomUserDetails customUserDetails,
			ContractByNationalIdRequest contractByNationalIdRequest);
	
	public MaazounCollectInfoPagingDTO quickSerachCollectionInfoTransactions(
			QuickSearchCollectiontInfoTransactionRequest quickSearchCollectiontInfoTransactionRequest,
			CustomUserDetails customUserDetails);
	
	public MaazounCollectInfoPagingDTO advancedSerachCollectionInfoTransactions(
			AdvancedSearchCollectionInfoTransactionRequest advancedSearchCollectionInfoTransactionRequest,
			CustomUserDetails customUserDetails);
	
	public List<String> getContractsUnderReview(CustomUserDetails customUserDetails);
	
	public InputStream downloadFile(URLRequest decryptURLRequest);
	
	public List<String> getCollectedDatalist(CustomUserDetails customUserDetails);
	
	public String reviewContract(CustomUserDetails customUserDetails, ReviewRequest reviewRequest);
	
	public MaazounCollectInfoPagingDTO getAuditByCreatedDate(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public MaazounCollectInfoPagingDTO getAuditByCreatedDateAndLocation(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public MaazounCollectInfoPagingDTO getAuditByCreatedDateAndCourt(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public MaazounCollectInfoPagingDTO getAuditByCreatedDateAndAgentAndLocation(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public String addReceivedBookStatus(CustomUserDetails customUserDetails, 
			AddReceivedContractStatusRequest sddReceivedContractStatusRequest, MultipartFile attContract);
	
	public String addReceivedBook(CustomUserDetails customUserDetails, 
			AddReceivedMaazounBookRequest addReceivedMaazounBookRequest);
	
	public PagingDTO getContractCustomAuditBySectors(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public PagingDTO getContractCustomAuditByCourts(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public String createRefundContractInfo(CustomUserDetails customUserDetails,
			MaazounContractInfoRefundRequest maazounContractInfoRefundRequest, TransactionECRAudit eTransactionECRAudit);

	public String editContractData(CustomUserDetails customUserDetails, 
			EditMaazounContractRequest editMaazounContractRequest);
	
	public String calculateSalePosMidRequestAmountFormat(PosMidRequest posMidRequest, CustomUserDetails customUserDetails);
	
	public String calculateRefundPosMidRequestAmountFormat(PosMidRequest posMidRequest);
	
	public MaazounCollectInfoPagingDTO getContractsListByDateAndStatus(CustomUserDetails customUserDetails ,CollectionListSearchRequest collectionListSearchRequest);

	public List<String> getContractsByMaazounAndMaazounia(CustomUserDetails customUserDetails,
			ContractByNationalIdRequest contractByNationalIdRequest);
}

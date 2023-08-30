package com.aman.payment.maazoun.management;

import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.maazoun.model.dto.MaazounDeliverInfoPagingDTO;
import com.aman.payment.maazoun.model.payload.AddMaazounDeliverInfoRequest;
import com.aman.payment.maazoun.model.payload.CloseMaazounDeliverInfoRequest;
import com.aman.payment.maazoun.model.payload.GetContractRequest;
import com.aman.payment.maazoun.model.payload.SearchDeliveredInfoTransactionRequest;
import com.aman.payment.maazoun.model.payload.URLRequest;

public interface MaazounBookDeliverInfoManagement {
	
	public List<String> getReviewedContract(CustomUserDetails customUserDetails, GetContractRequest decryptGetContractRequest);
	
	public List<String> getReceivedContract(CustomUserDetails customUserDetails, GetContractRequest decryptGetContractRequest);
	
	public InputStream downloadFile(URLRequest uRLRequest);
	
	public String createDeliverRequest(CustomUserDetails customUserDetails, AddMaazounDeliverInfoRequest decryptAddMaazounDeliverInfoRequest);
	
	public String closeActionDeliverRequest(CustomUserDetails customUserDetails, 
			CloseMaazounDeliverInfoRequest closeMaazounDeliverInfoRequest, MultipartFile deliverAssignReport);
	
	public String deleteActionDeliverRequest(CustomUserDetails customUserDetails, 
			CloseMaazounDeliverInfoRequest closeMaazounDeliverInfoRequest);
	
	public List<String> getPendingDeliverRequests(CustomUserDetails customUserDetails);
	
	public MaazounDeliverInfoPagingDTO searchDeliveredRequests(CustomUserDetails customUserDetails, SearchDeliveredInfoTransactionRequest obj);
}

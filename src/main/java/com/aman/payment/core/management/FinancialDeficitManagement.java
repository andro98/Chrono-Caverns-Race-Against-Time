package com.aman.payment.core.management;

import java.io.InputStream;
import java.util.List;

import com.aman.payment.core.model.FinancialDeficit;
import com.aman.payment.core.model.PullAccount;
import com.aman.payment.core.model.dto.FinancialDeficitDTO;
import com.aman.payment.core.model.dto.FinancialDeficitPagingDTO;
import com.aman.payment.core.model.payload.ApprovedFinancialDeficitRequest;
import com.aman.payment.core.model.payload.CloseFinancialDeficitRequest;
import com.aman.payment.core.model.payload.FinancialDeficitByUserRequest;
import com.aman.payment.core.model.payload.URLRequest;

public interface FinancialDeficitManagement {
	
	public FinancialDeficit createFinancialDeficit(PullAccount pullAccount, double deficitAmount);
	
	public FinancialDeficit saveFinancialDeficit(FinancialDeficit financialDeficit);
	
	public List<FinancialDeficitDTO> getNewOrRejectedFinancialDeficit(FinancialDeficitByUserRequest financialDeficitByUserRequest);
	
	public FinancialDeficitDTO closeFinancialDeficit(CloseFinancialDeficitRequest closeFinancialDeficitRequest);
	
	public InputStream downloadFinancialDeficitAttFile(URLRequest uRLRequest);
	
	public FinancialDeficitPagingDTO getApprovedFinancialDeficitByUser(
			ApprovedFinancialDeficitRequest approvedFinancialDeficitRequest);
	
	public FinancialDeficit findByPullAccountFk(PullAccount pullAccount);
	
	public void deleteFinancialDeficit(FinancialDeficit financialDeficitFk);
}

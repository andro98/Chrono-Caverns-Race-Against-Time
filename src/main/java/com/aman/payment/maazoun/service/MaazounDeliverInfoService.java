package com.aman.payment.maazoun.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.maazoun.model.MaazounBookDeliverInfo;
import com.aman.payment.maazoun.model.payload.SearchDeliveredInfoTransactionRequest;

public interface MaazounDeliverInfoService extends MaazounGenericService<MaazounBookDeliverInfo, Long> {
	
	public List<MaazounBookDeliverInfo> getDeliverRequests(String statusFk, Set<Long> locationIds);

	public Page<MaazounBookDeliverInfo> searchDeliveredRequests(CustomUserDetails customUserDetails, SearchDeliveredInfoTransactionRequest searchObj);
	
	public List<MaazounBookDeliverInfo> findByRefRequestNumber(String refRequestNumber);
}
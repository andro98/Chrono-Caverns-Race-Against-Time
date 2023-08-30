package com.aman.payment.maazoun.repository;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.maazoun.model.MaazounBookDeliverInfo;
import com.aman.payment.maazoun.model.payload.SearchDeliveredInfoTransactionRequest;


@Repository
public interface MaazounDeliverInfoCustomRepository {

	public Page<MaazounBookDeliverInfo> searchDeliveredRequests(CustomUserDetails customUserDetails, SearchDeliveredInfoTransactionRequest searchObj);
	
}
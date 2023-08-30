package com.aman.payment.core.model.payload;

import java.util.HashSet;
import java.util.Set;

import com.aman.payment.core.service.CryptoMngrCoreService;


public class SettlementByPosIdsRequest implements CoreBasePayload<SettlementByPosIdsRequest> {
	private Set<String> posIds=new HashSet<String>();

	public SettlementByPosIdsRequest() {
	}

	public SettlementByPosIdsRequest(Set<String> PosIds) {
		super();
		this.posIds = PosIds;
	}


	public Set<String> getPosIds() {
		return posIds;
	}

	public void setPosIds(Set<String> posIds) {
		this.posIds = posIds;
	}

	@Override
	public SettlementByPosIdsRequest decrypt(CryptoMngrCoreService cryptoMngrCoreService) {
		Set<String> ePosIds = new HashSet<String>();
		
		posIds.stream().forEach(s -> {
			ePosIds.add(cryptoMngrCoreService.decrypt(s));
		});
		//posIds=ePosIds;
		return new SettlementByPosIdsRequest(ePosIds);
	}

}

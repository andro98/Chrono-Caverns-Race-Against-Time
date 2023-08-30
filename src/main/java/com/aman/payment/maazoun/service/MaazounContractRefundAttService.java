package com.aman.payment.maazoun.service;

import java.util.Optional;

import com.aman.payment.maazoun.model.MaazounContractRefundAtt;

public interface MaazounContractRefundAttService extends MaazounGenericService<MaazounContractRefundAtt, Long> {

	public Optional<MaazounContractRefundAtt> findByMaazounBookCollectionInfoId(long maazounBookCollectionInfoId);

}
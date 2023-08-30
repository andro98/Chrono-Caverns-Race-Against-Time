package com.aman.payment.maazoun.service;

import java.util.Optional;

import com.aman.payment.maazoun.model.MaazounRefundAtt;

public interface MaazounRefundAttService extends MaazounGenericService<MaazounRefundAtt, Long> {

	public Optional<MaazounRefundAtt> findByMaazounBookRequestInfoId(long maazounBookRequestInfoId);

}
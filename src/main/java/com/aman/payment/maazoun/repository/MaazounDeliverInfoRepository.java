package com.aman.payment.maazoun.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.aman.payment.maazoun.model.MaazounBookDeliverInfo;


@Repository
public interface MaazounDeliverInfoRepository extends PagingAndSortingRepository<MaazounBookDeliverInfo, Long>,
		JpaSpecificationExecutor<MaazounBookDeliverInfo>, MaazounDeliverInfoCustomRepository {

	public List<MaazounBookDeliverInfo> findByStatusFkAndLocationIdIn(String statusFk, Set<Long> locationIds);
	
	public List<MaazounBookDeliverInfo> findByRefRequestNumber(String refRequestNumber);
}
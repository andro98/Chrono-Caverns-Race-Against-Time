package com.aman.payment.maazoun.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aman.payment.maazoun.model.MaazounContractRefundAtt;

@Repository
public interface MaazounContractRefundAttRepository extends JpaRepository<MaazounContractRefundAtt,Long> {

}

package com.aman.payment.maazoun.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aman.payment.maazoun.model.MaazounQuotaExceptionAtt;

@Repository
public interface MaazounQuotaExceptionAttRepository extends JpaRepository<MaazounQuotaExceptionAtt,Long> {

}

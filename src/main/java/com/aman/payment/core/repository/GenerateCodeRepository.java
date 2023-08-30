package com.aman.payment.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aman.payment.core.model.GenerateCode;


@Repository
public interface GenerateCodeRepository extends JpaRepository<GenerateCode, Long>{
	
//	@Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
//	@Query("SELECT c FROM GenerateCode c WHERE c.keyName = ?1")
//	@Lock(LockModeType.PESSIMISTIC_WRITE)
//	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "3000")})
	public GenerateCode findByKeyName(String keyName);

}
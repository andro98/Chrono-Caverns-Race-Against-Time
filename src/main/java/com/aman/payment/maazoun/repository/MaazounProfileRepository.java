package com.aman.payment.maazoun.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.aman.payment.auth.model.Sector;
import com.aman.payment.maazoun.model.MaazounBookRequestInfo;
import com.aman.payment.maazoun.model.MaazounProfile;

@Repository
public interface MaazounProfileRepository extends PagingAndSortingRepository<MaazounProfile, Long>,
						JpaSpecificationExecutor<MaazounBookRequestInfo>, MaazounProfileCustomRepository {

	MaazounProfile findByNationalId(String nationalId);

	MaazounProfile findByMobile(String mobile);

	MaazounProfile findByCardNumber(String cardNumber);
	
	public List<MaazounProfile> findAllByActiveTrue();
	
	public Page<MaazounProfile> findBySectorsIn(Set<Sector> sectors, Pageable pageable);
	 
    public List<MaazounProfile> findByNationalIdContainingOrNameContainingOrMobileContaining(String nationalId, String mobile, String name);
	
	public List<MaazounProfile> findBySectorsIn(Set<Sector> sectors );


}

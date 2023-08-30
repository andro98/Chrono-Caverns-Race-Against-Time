package com.aman.payment.auth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.aman.payment.auth.model.Pos;
import com.aman.payment.auth.model.Sector;

@Repository
public interface PosRepository extends PagingAndSortingRepository<Pos, Long>,PosRepositoryCustom{
	
//	List<Pos> findBystatusFkOrderBySectorFkAsc(String status);
	 
	@Query(value="SELECT u.username FROM users u  INNER JOIN user_pos up ON u.id=up.user_id INNER JOIN role r ON r.id = u.role_fk WHERE (r.name ='ROLE_AGENT' OR r.name ='ROLE_AGENT_SUPERVISOR') AND up.pos_id = ?1",nativeQuery = true)
	List<String> listAllUsersForPOSForAgent(Long posId);
	
//	List<Pos> findBySectorFk(Sector sectorFkFk);

	Page<Pos> findAll(Pageable pageable);
	
	Page<Pos> findByNameContainsOrCodeContains(
    		String param1, String param2, Pageable pageable);

	public Optional<List<Pos>> findByNameLike(String name);
}
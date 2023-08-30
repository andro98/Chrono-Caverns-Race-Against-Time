/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aman.payment.auth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aman.payment.auth.model.Pos;
import com.aman.payment.auth.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Page<User> findAll(Pageable pageable);
	
    Optional<User> findByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Boolean existsByUsername(String username);
    
    List<User> findByPosSet(Pos pos);

    Page<User> findByUsernameContainsOrMobileContainsOrFirstNameContainsOrLastNameContains(
    		String param1, String param2, String param3, String param4, Pageable pageable);
    
    @Modifying
    @Query(value = "DELETE FROM user_pos WHERE user_id =?1 AND pos_id =?2 ", nativeQuery = true)
    public void deleteUserPOS(long userId, long posId);
    
    @Modifying
    @Query(value = "insert into user_pos (user_id, pos_id) values (?, ?)", nativeQuery = true)
    public void addPosToUser(Long userId, Long posId);
    
}

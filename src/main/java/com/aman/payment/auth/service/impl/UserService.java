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
package com.aman.payment.auth.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.Pos;
import com.aman.payment.auth.model.User;
import com.aman.payment.auth.repository.UserRepository;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Finds a user in the database by username
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Finds a user in the database by email
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    /**
     * Find a user in db by id.
     */
    @Cacheable(value = "userCache",key = "#id", unless = "#result==null")
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Find all users in db.
     */
    public List<User> findAll() throws Exception{
        return userRepository.findAll();
    }

    /**
     * Save the user to the database
     */
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Check is the user exists given the email: naturalId
     */
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Check is the user exists given the username: naturalId
     */
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public Page<User> findAll(Pageable pageable){
    	
    	Page<User> entityPage = userRepository.findAll(pageable);
        List<User> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }
    
    public Page<User> lookforUser(String searchBy, Pageable pageable){
    	
    	Page<User> entityPage = userRepository.
    			findByUsernameContainsOrMobileContainsOrFirstNameContainsOrLastNameContains(
    					searchBy, searchBy, searchBy, searchBy, pageable);
        return new PageImpl<>(entityPage.getContent(), pageable, entityPage.getTotalElements());
    }
    
    public void deleteUserPOS(long userId, long posId) {
    	userRepository.deleteUserPOS(userId, posId);
    }
    
    public List<User> getPosUsers(long posId) {
    	Pos pos=new Pos();
    	pos.setId(posId);
    	return userRepository.findByPosSet(pos);

    }
    
    public void addPosToUser(Long userId, Long posId) {
    	userRepository.addPosToUser(userId, posId);
    }
    
}

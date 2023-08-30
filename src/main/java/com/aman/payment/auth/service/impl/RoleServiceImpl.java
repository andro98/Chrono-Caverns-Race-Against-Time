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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.Role;
import com.aman.payment.auth.repository.RoleRepository;
import com.aman.payment.auth.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

//    /**
//     * Find all roles from the database
//     */
//    public Collection<Role> findAll() {
//        return roleRepository.findAll();
//    }
    
    /**
     * Find all roles from the database
     */
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }
    
    /**
     * Find role by idfrom the database
     */
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

	@Override
	public Role save(Role entity) {
		// TODO Auto-generated method stub
		return roleRepository.save(entity);
	}

	@Override
	public List<Role> save(List<Role> entities) {
		// TODO Auto-generated method stub
		return roleRepository.saveAll(entities);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		roleRepository.deleteById(id);
	}

	@Override
	public List<Role> findAll() {
		// TODO Auto-generated method stub
		return roleRepository.findAll();
	}

	@Override
	public Page<Role> findAll(Pageable pageable) {
		Page<Role> entityPage = roleRepository.findAll(pageable);
        List<Role> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
	}

	@Override
	public Role update(Role entity, Long id) {
		Optional<Role> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
	}
	
	@Override
	public void deleteAll(List<Role> entities) {
		roleRepository.deleteAll(entities);
	}

}

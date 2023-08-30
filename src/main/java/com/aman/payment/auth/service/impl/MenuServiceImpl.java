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
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.Menu;
import com.aman.payment.auth.repository.MenuRepository;
import com.aman.payment.auth.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService{

    private final MenuRepository menuRepository;

    @Autowired
    public MenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

	@Override
	public Menu save(Menu entity) {
		// TODO Auto-generated method stub
		return menuRepository.save(entity);
	}

	@Override
	public List<Menu> save(List<Menu> entities) {
		// TODO Auto-generated method stub
		return menuRepository.saveAll(entities);
	}

	@Override
	public void deleteById(Long id) {
		menuRepository.deleteById(id);
		
	}

	@Override
	public void deleteAll(List<Menu> entities) {
		menuRepository.deleteAll();
	}

	@Override
	public Optional<Menu> findById(Long id) {
		return menuRepository.findById(id);
	}

	@Override
	public List<Menu> findAll() {
		// TODO Auto-generated method stub
		return menuRepository.findAll();
	}

	@Override
	public Page<Menu> findAll(Pageable pageable) {
		Page<Menu> entityPage = menuRepository.findAll(pageable);
        List<Menu> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
	}

	@Override
	public Menu update(Menu entity, Long id) {
		Optional<Menu> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
	}

	@Override
	public Set<Menu> findByServiceFk(com.aman.payment.auth.model.Service serviceFk) {
		// TODO Auto-generated method stub
		return menuRepository.findByServiceFk(serviceFk);
	}


}

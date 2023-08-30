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
package com.aman.payment.core.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aman.payment.core.mapper.PaymentTypeMapper;
import com.aman.payment.core.model.dto.PaymentTypeDTO;
import com.aman.payment.core.service.PaymentTypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/passport/lookup")
@Api(value = "Lookups Rest API", description = "Defines endpoints for the logged in lookups. It's secured by default")

public class CoreLookupController {

	private static final Logger logger = Logger.getLogger(CoreLookupController.class);
	
	
	@Autowired
	private PaymentTypeMapper paymentTypeMapper;
	@Autowired
	private PaymentTypeService paymentTypeService;


	@GetMapping("/paymentMethod")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured payment method. Requires ADMIN Access")
	public ResponseEntity<List<PaymentTypeDTO>> getAllPaymentMethod() {
		logger.info("Inside secured resource with admin");
		return ResponseEntity.ok(paymentTypeMapper.paymentTypeToPaymentTypeDTOs(paymentTypeService.findAll()));
	}

	
}

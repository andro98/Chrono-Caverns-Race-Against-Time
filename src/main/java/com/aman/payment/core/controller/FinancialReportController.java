
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

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aman.payment.core.management.CoreJsonObjectFactoryImpl;
import com.aman.payment.core.management.FinancialReportManagement;
import com.aman.payment.core.model.dto.FinancialAuditPagingDTO;
import com.aman.payment.core.model.payload.FinancialReportRequest;
import com.aman.payment.core.service.CryptoMngrCoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/financialReport")
@Api(value = "Passport Technical Reports of Manangement Rest API", description = "Defines endpoints for the Passport Technical Reports Manangement. It's secured by default")

public class FinancialReportController extends CoreJsonObjectFactoryImpl{

	private static final Logger logger = Logger.getLogger(FinancialReportController.class);

	private final FinancialReportManagement financialReportManagement;
	private final CryptoMngrCoreService cryptoMngrCoreService;

	@Autowired
	public FinancialReportController(FinancialReportManagement financialReportManagement,
			CryptoMngrCoreService cryptoMngrCoreService) {
		this.financialReportManagement = financialReportManagement;
		this.cryptoMngrCoreService = cryptoMngrCoreService;
	}
	
	/**
	 * Returns all approved settlement in the system.
	 */
	@PostMapping("/financialReviewReport")
	@ApiOperation(value = "Returns the list of all approved settlement")
	public ResponseEntity<FinancialAuditPagingDTO> getFinancialReviewReport(
			@Valid @RequestBody String jsonString) {
		
		FinancialReportRequest decryptFinancialReportRequest = 
				convertJsonStringToObject(jsonString, FinancialReportRequest.class);
		
		return ResponseEntity.ok(financialReportManagement.
				getFinancialReviewReport(decryptFinancialReportRequest));

	}
	
	/**
	 * Returns all Accumulate Data related with Created Date in the system.
	 */
	@PostMapping("/reportDaily")
//	    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Accumulate Data related with Daily.")
	public ResponseEntity<FinancialAuditPagingDTO> getAuditReportByDaily(
			@Valid @RequestBody String jsonString) {
		
		FinancialReportRequest decryptFinancialReportRequest = 
				convertJsonStringToObject(jsonString, FinancialReportRequest.class);
		
		FinancialAuditPagingDTO eFinancialAuditPagingDTO = financialReportManagement.
								getAuditReportByDaily(decryptFinancialReportRequest);
		
		return ResponseEntity.ok(eFinancialAuditPagingDTO);
	}
	
	/**
	 * Returns all Accumulate Data related with Created Date in the system.
	 */
	@PostMapping("/reportByLocation")
//	    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Accumulate Data related with Location.")
	public ResponseEntity<FinancialAuditPagingDTO> getAuditReportByLocation(
			@Valid @RequestBody String jsonString) {
		
		FinancialReportRequest decryptFinancialReportRequest = 
				convertJsonStringToObject(jsonString, FinancialReportRequest.class);
		
		return ResponseEntity.ok(financialReportManagement.
				getAuditReportByLocation(decryptFinancialReportRequest));
	}
	
	@PostMapping("/reportByCourt")
	//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Accumulate Data related with Location.")
	public ResponseEntity<FinancialAuditPagingDTO> getAuditReportByCourt(
			@Valid @RequestBody String jsonString) {
		
		FinancialReportRequest decryptFinancialReportRequest = 
				convertJsonStringToObject(jsonString, FinancialReportRequest.class);
		
		return ResponseEntity.ok(financialReportManagement.
				getAuditReportByCourt(decryptFinancialReportRequest));
	}
	
	@PostMapping("/reportBySector")
	//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Accumulate Data related with Location.")
	public ResponseEntity<FinancialAuditPagingDTO> getAuditReportBySector(
			@Valid @RequestBody String jsonString) {
		
		FinancialReportRequest decryptFinancialReportRequest = 
				convertJsonStringToObject(jsonString, FinancialReportRequest.class);
		
		return ResponseEntity.ok(financialReportManagement.
				getAuditReportBySector(decryptFinancialReportRequest));
	}
	
	/**
	 * Returns all Accumulate Data related with Created Date in the system.
	 */
	@PostMapping("/reportByPos")
//	    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Accumulate Data related with POS.")
	public ResponseEntity<FinancialAuditPagingDTO> getAuditReportByPos(
			@Valid @RequestBody String jsonString) {
		
		FinancialReportRequest decryptFinancialReportRequest = 
				convertJsonStringToObject(jsonString, FinancialReportRequest.class);
		
		return ResponseEntity.ok(financialReportManagement.
				getAuditReportByPOS(decryptFinancialReportRequest));
	}
	
	/**
	 * Returns all Accumulate Data related with Created Date in the system.
	 */
	@PostMapping("/reportByAgentAndLocation")
//	    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Accumulate Data related with Agent.")
	public ResponseEntity<FinancialAuditPagingDTO> getAuditReportByAgentAndLocation(
			@Valid @RequestBody String jsonString) {
		
		FinancialReportRequest decryptFinancialReportRequest = 
				convertJsonStringToObject(jsonString, FinancialReportRequest.class);
		
		return ResponseEntity.ok(financialReportManagement.
				getAuditReportByAgentAndLocation(decryptFinancialReportRequest));
	}
	
	@PostMapping("/reportPermissionDetails")
	public ResponseEntity<List<String>> getReportPermissionDetails(@Valid @RequestBody String jsonString) {
		FinancialReportRequest decryptFinancialReportRequest = 
				convertJsonStringToObject(jsonString, FinancialReportRequest.class);
		
		return ResponseEntity.ok(financialReportManagement.
				getReportPermissionDetails(decryptFinancialReportRequest));
	}
	//---------------------------------------------------------------------------------------------------
	@PostMapping("/financialMidDetailsByTransaction")
	//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Accumulate Data related with MIDs Accounts.")
	public ResponseEntity<FinancialAuditPagingDTO> getAuditReportFinancialMidDetailsByTransaction(
			@Valid @RequestBody String jsonString) {
		
		FinancialReportRequest decryptFinancialReportRequest = 
				convertJsonStringToObject(jsonString, FinancialReportRequest.class);
		
		return ResponseEntity.ok(financialReportManagement.
				getAuditReportFinancialMidDetailsByTransaction(decryptFinancialReportRequest));
	}
	
	@PostMapping("/refundFinancialMidDetailsByTransaction")
	//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Accumulate Data related with MIDs Accounts.")
	public ResponseEntity<FinancialAuditPagingDTO> getRefundAuditReportFinancialMidDetailsByTransaction(
			@Valid @RequestBody String jsonString) {
		
		FinancialReportRequest decryptFinancialReportRequest = 
				convertJsonStringToObject(jsonString, FinancialReportRequest.class);
		
		return ResponseEntity.ok(financialReportManagement.
				getRefundAuditReportFinancialMidDetailsByTransaction(decryptFinancialReportRequest));
	}
	
	@PostMapping("/canceledFinancialMidDetailsByTransaction")
	//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Accumulate Data related with MIDs Accounts.")
	public ResponseEntity<FinancialAuditPagingDTO> getCanceledAuditReportFinancialMidDetailsByTransaction(
			@Valid @RequestBody String jsonString) {
		
		FinancialReportRequest decryptFinancialReportRequest = 
				convertJsonStringToObject(jsonString, FinancialReportRequest.class);
		
		return ResponseEntity.ok(financialReportManagement.
				getCanceledAuditReportFinancialMidDetailsByTransaction(decryptFinancialReportRequest));
	}
	
	@PostMapping("/financialMidDetailsByPOS")
	//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Accumulate Data related with MIDs Accounts.")
	public ResponseEntity<FinancialAuditPagingDTO> getAuditReportFinancialMidDetailsByPOS(
			@Valid @RequestBody String jsonString) {
		
		FinancialReportRequest decryptFinancialReportRequest = 
				convertJsonStringToObject(jsonString, FinancialReportRequest.class);
		
		return ResponseEntity.ok(financialReportManagement.
				getAuditReportFinancialMidDetailsByPOS(decryptFinancialReportRequest));
	}
	
	@PostMapping("/financialMidDetailsBySector")
	//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Accumulate Data related with MIDs Accounts.")
	public ResponseEntity<FinancialAuditPagingDTO> getAuditReportFinancialMidDetailsBySector(
			@Valid @RequestBody String jsonString) {
		
		FinancialReportRequest decryptFinancialReportRequest = 
				convertJsonStringToObject(jsonString, FinancialReportRequest.class);
		
		return ResponseEntity.ok(financialReportManagement.
				getAuditReportFinancialMidDetailsBySector(decryptFinancialReportRequest));
	}
	
	@PostMapping("/refundFinancialMidDetailsBySector")
	//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Accumulate Data related with MIDs Accounts.")
	public ResponseEntity<FinancialAuditPagingDTO> getRefundAuditReportFinancialMidDetailsBySector(
			@Valid @RequestBody String jsonString) {
		
		FinancialReportRequest decryptFinancialReportRequest = 
				convertJsonStringToObject(jsonString, FinancialReportRequest.class);
		
		return ResponseEntity.ok(financialReportManagement.
				getRefundAuditReportFinancialMidDetailsBySector(decryptFinancialReportRequest));
	}
	
	@PostMapping("/financialMidDetailsByLocation")
	//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Accumulate Data related with MIDs Accounts.")
	public ResponseEntity<FinancialAuditPagingDTO> getAuditReportFinancialMidDetailsByLocation(
			@Valid @RequestBody String jsonString) {
		
		FinancialReportRequest decryptFinancialReportRequest = 
				convertJsonStringToObject(jsonString, FinancialReportRequest.class);
		
		return ResponseEntity.ok(financialReportManagement.
				getAuditReportFinancialMidDetailsByLocation(decryptFinancialReportRequest));
	}
	
	@PostMapping("/financialMidDetailsByCourt")
	//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Accumulate Data related with MIDs Accounts.")
	public ResponseEntity<FinancialAuditPagingDTO> getAuditReportFinancialMidDetailsByCourt(
			@Valid @RequestBody String jsonString) {
		
		FinancialReportRequest decryptFinancialReportRequest = 
				convertJsonStringToObject(jsonString, FinancialReportRequest.class);
		
		return ResponseEntity.ok(financialReportManagement.
				getAuditReportFinancialMidDetailsByCourt(decryptFinancialReportRequest));
	}
	
	@PostMapping("/financialMidDetailsByCity")
	//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Accumulate Data related with MIDs Accounts.")
	public ResponseEntity<FinancialAuditPagingDTO> getAuditReportFinancialMidDetailsByCity(
			@Valid @RequestBody String jsonString) {
		
		FinancialReportRequest decryptFinancialReportRequest = 
				convertJsonStringToObject(jsonString, FinancialReportRequest.class);
		
		return ResponseEntity.ok(financialReportManagement.
				getAuditReportFinancialMidDetailsByCity(decryptFinancialReportRequest));
	}
	
	@PostMapping("/financialMidDetailsByDaily")
	//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Accumulate Data related with MIDs Accounts.")
	public ResponseEntity<FinancialAuditPagingDTO> getAuditReportFinancialMidDetailsByDaily(
			@Valid @RequestBody String jsonString) {
		
		FinancialReportRequest decryptFinancialReportRequest = 
				convertJsonStringToObject(jsonString, FinancialReportRequest.class);
		
		return ResponseEntity.ok(financialReportManagement.
				getAuditReportFinancialMidDetailsByDaily(decryptFinancialReportRequest));
	}
	
	@PostMapping("/refundFinancialMidDetailsByDaily")
	//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Accumulate Data related with MIDs Accounts.")
	public ResponseEntity<FinancialAuditPagingDTO> getRefundAuditReportFinancialMidDetailsByDaily(
			@Valid @RequestBody String jsonString) {
		
		FinancialReportRequest decryptFinancialReportRequest = 
				convertJsonStringToObject(jsonString, FinancialReportRequest.class);
		
		return ResponseEntity.ok(financialReportManagement.
				getRefundAuditReportFinancialMidDetailsByDaily(decryptFinancialReportRequest));
	}
	
	@PostMapping("/financialMidDetailsByAgent")
	//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Accumulate Data related with MIDs Accounts.")
	public ResponseEntity<FinancialAuditPagingDTO> getAuditReportFinancialMidDetailsByAgent(
			@Valid @RequestBody String jsonString) {
		
		FinancialReportRequest decryptFinancialReportRequest = 
				convertJsonStringToObject(jsonString, FinancialReportRequest.class);
		
		return ResponseEntity.ok(financialReportManagement.
				getAuditReportFinancialMidDetailsByAgent(decryptFinancialReportRequest));
	}
	
	@PostMapping("/financialMidDetailsByTypeName")
	//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Accumulate Data related with MIDs Accounts.")
	public ResponseEntity<FinancialAuditPagingDTO> getAuditReportFinancialMidDetailsByTypeName(
			@Valid @RequestBody String jsonString) {
		
		FinancialReportRequest decryptFinancialReportRequest = 
				convertJsonStringToObject(jsonString, FinancialReportRequest.class);
		
		return ResponseEntity.ok(financialReportManagement.
				getAuditReportFinancialMidDetailsByTypeName(decryptFinancialReportRequest));
	}
	
}

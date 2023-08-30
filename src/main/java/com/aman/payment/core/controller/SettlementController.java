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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aman.payment.annotation.CurrentUser;
import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.core.management.SettlementManagement;
import com.aman.payment.core.model.dto.FinancialDeficitDTO;
import com.aman.payment.core.model.dto.FinancialDeficitPagingDTO;
import com.aman.payment.core.model.dto.SettlementDTO;
import com.aman.payment.core.model.dto.SettlementPagingDTO;
import com.aman.payment.core.model.payload.ApprovedClaimsSearchRequest;
import com.aman.payment.core.model.payload.ApprovedFinancialDeficitRequest;
import com.aman.payment.core.model.payload.ApprovedPullAccountRequest;
import com.aman.payment.core.model.payload.ReviewFinancialDeficitRequest;
import com.aman.payment.core.model.payload.ReviewSettlementRequest;
import com.aman.payment.core.model.payload.SettlementByPosIdsRequest;
import com.aman.payment.core.model.payload.URLRequest;
import com.aman.payment.core.service.CryptoMngrCoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/settlement")
@Api(value = "Settlement Rest API", description = "Defines endpoints for the Pull Account. It's secured by default")

public class SettlementController {

	private static final Logger logger = Logger.getLogger(SettlementController.class);

	private final SettlementManagement settlementManagement;
	private final CryptoMngrCoreService cryptoMngrCoreService;

	@Autowired
	public SettlementController(SettlementManagement settlementManagement,
			CryptoMngrCoreService cryptoMngrCoreService) {
		this.settlementManagement = settlementManagement;
		this.cryptoMngrCoreService = cryptoMngrCoreService;
	}

	/**
	 * Returns all Financial Deficit list By User Agent in the system.
	 */
	@PostMapping("/pendingFinancialDeficits")
//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns all Financial Deficit list By POS.")
	public ResponseEntity<List<String>> getAllPendingfinancialDeficits(
			@Valid @RequestBody SettlementByPosIdsRequest settlementByPosIdsRequest) {

		SettlementByPosIdsRequest decryptSettlementByPosIdsRequest = settlementByPosIdsRequest
				.decrypt(cryptoMngrCoreService);
		
		List<FinancialDeficitDTO> result = settlementManagement.
				getNotApprovedFinancialDeficit(decryptSettlementByPosIdsRequest);
		
		List<String> eTransaction = new ArrayList<String>();
		result.stream().forEach(s -> {
			eTransaction.add(cryptoMngrCoreService.encrypt(s.toString()));
		});

		return ResponseEntity.ok(eTransaction);
	}

	@PostMapping("/financialDeficitsApproved") // replace with finDifAprovalclaims
	// @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns all Financial Deficit list By POS.")
	public ResponseEntity<FinancialDeficitPagingDTO> getfinancialDeficitApproved(
			@Valid @RequestBody ApprovedFinancialDeficitRequest approvedFinancialDeficitRequest) {

		ApprovedFinancialDeficitRequest decryptApprovedFinancialDeficitRequest = approvedFinancialDeficitRequest
				.decrypt(cryptoMngrCoreService);

		return ResponseEntity
				.ok(settlementManagement.getApprovedFinancialDeficit(decryptApprovedFinancialDeficitRequest));
	}

	/**
	 * Deficit Approval
	 */
	@PostMapping(value = "/reviewFinancialDeficit")
//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Approval Deficit Transaction Returns the Pull Account Object Details.")
	public ResponseEntity<String> reviewFinancialDeficit(
			@Valid @RequestBody ReviewFinancialDeficitRequest reviewFinancialDeficitRequest) {

		ReviewFinancialDeficitRequest decryptReviewFinancialDeficitRequest = reviewFinancialDeficitRequest
				.decrypt(cryptoMngrCoreService);

		return ResponseEntity.ok(settlementManagement.
				reviewFinancialDeficit(decryptReviewFinancialDeficitRequest).toString());
	}

	/**
	 * Download Attachement deposit file
	 */
	@PostMapping("/download/att")
	public void downloadInsuranceFile(@Valid @RequestBody URLRequest uRLRequest, HttpServletResponse response) {

		URLRequest decryptURLRequest = uRLRequest.decrypt(cryptoMngrCoreService);

		try {

			InputStream is = settlementManagement.downloadPullAccountAttFile(decryptURLRequest);
			IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns all Calims list in the system related with assigned POS.
	 */
	@PostMapping("/pendingSettlements")
//	    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Pending or Rejected or New Settlements.")
	public ResponseEntity<List<String>> getAllPendingSettlements(
			@Valid @RequestBody SettlementByPosIdsRequest settlementByPosIdsRequest) {

		SettlementByPosIdsRequest decryptSettlementByPosIdsRequest = settlementByPosIdsRequest
				.decrypt(cryptoMngrCoreService);
		
		List<SettlementDTO> result = settlementManagement.
				getNotApprovedPullAccount(decryptSettlementByPosIdsRequest);
		
		List<String> eTransaction = new ArrayList<String>();
		result.stream().forEach(s -> {
			eTransaction.add(cryptoMngrCoreService.encrypt(s.toString()));
		});

		return ResponseEntity.ok(eTransaction);
	}

	@PostMapping("/approvedSettlements")
//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All approved settlements.")
	public ResponseEntity<SettlementPagingDTO> getAllApprovedSettlements(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody ApprovedPullAccountRequest approvedPullAccountRequest) {

		ApprovedPullAccountRequest decryptApprovedPullAccountRequest = approvedPullAccountRequest
				.decrypt(cryptoMngrCoreService);

		return ResponseEntity.ok(settlementManagement.getApprovedPullAccount(decryptApprovedPullAccountRequest,customUserDetails));
	}
	
	@PostMapping("/approvedSettlementsSearch")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All approved settlements.")
	public ResponseEntity<SettlementPagingDTO> getFilterdApprovedSettlements(
			@Valid @RequestBody ApprovedClaimsSearchRequest approvedClaimsSearchRequest) {

		ApprovedClaimsSearchRequest decryptApprovedClaimsSearchRequest = approvedClaimsSearchRequest
				.decrypt(cryptoMngrCoreService);

		return ResponseEntity.ok(settlementManagement.getApprovedPullAccountSearch(decryptApprovedClaimsSearchRequest));
	}

	/**
	 * Settlement Approval
	 */
	@PostMapping(value = "/reviewSettlement")
//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Review on settlement.")
	public ResponseEntity<String> reviewSettlement(
			@Valid @RequestBody ReviewSettlementRequest reviewSettlementRequest) {

		ReviewSettlementRequest decryptReviewSettlementRequest = reviewSettlementRequest.decrypt(cryptoMngrCoreService);

		return ResponseEntity.ok(settlementManagement.
				reviewSettlement(decryptReviewSettlementRequest).toString());
	}

}

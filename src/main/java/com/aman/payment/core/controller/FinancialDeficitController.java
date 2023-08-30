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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aman.payment.core.management.CoreJsonObjectFactoryImpl;
import com.aman.payment.core.management.FinancialDeficitManagement;
import com.aman.payment.core.model.dto.FinancialDeficitDTO;
import com.aman.payment.core.model.dto.FinancialDeficitPagingDTO;
import com.aman.payment.core.model.dto.PullAccountDTO;
import com.aman.payment.core.model.payload.ApprovedFinancialDeficitRequest;
import com.aman.payment.core.model.payload.CloseFinancialDeficitRequest;
import com.aman.payment.core.model.payload.FinancialDeficitByUserRequest;
import com.aman.payment.core.model.payload.PullAccountByUserRequest;
import com.aman.payment.core.model.payload.URLRequest;
import com.aman.payment.core.service.CryptoMngrCoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/financialDeficit")
@Api(value = "FinancialDeficit Rest API", description = "Defines endpoints for the FinancialDeficit. It's secured by default")

public class FinancialDeficitController extends CoreJsonObjectFactoryImpl{

	private static final Logger logger = Logger.getLogger(FinancialDeficitController.class);
	private final FinancialDeficitManagement financialDeficitManagement;
	private final CryptoMngrCoreService cryptoMngrCoreService;

	@Autowired
	public FinancialDeficitController(FinancialDeficitManagement financialDeficitManagement,
			CryptoMngrCoreService cryptoMngrCoreService) {
		this.financialDeficitManagement = financialDeficitManagement;
		this.cryptoMngrCoreService = cryptoMngrCoreService;
	}

	/**
	 * Returns all Financial Deficit list By User Agent in the system.
	 */
	@PostMapping("/financialDeficitsByUser")
//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns all Financial Deficit list By User Agent.")
	public ResponseEntity<List<String>> getFinancialDeficitByUser(
			@Valid @RequestBody String jsonString) {
		
		FinancialDeficitByUserRequest decryptFinancialDeficitByUserRequest = 
				convertJsonStringToObject(jsonString, FinancialDeficitByUserRequest.class);
		
		List<FinancialDeficitDTO> result = financialDeficitManagement.
				getNewOrRejectedFinancialDeficit(decryptFinancialDeficitByUserRequest);
		
		List<String> eTransaction = new ArrayList<String>();
		result.stream().forEach(s -> {
			eTransaction.add(cryptoMngrCoreService.encrypt(s.toString()));
		});

		return ResponseEntity.ok(eTransaction);
	}

	/**
	 * Pay New Deficit
	 */
	@PostMapping(value = "/payFinancialDeficitByUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Pay Deficit Details.")
	public ResponseEntity<String> payFinancialDeficitByUser(
			@Valid @RequestPart CloseFinancialDeficitRequest closeFinancialDeficitRequest,@RequestPart MultipartFile fileAtt) {
		closeFinancialDeficitRequest.setFileAtt(fileAtt);
		CloseFinancialDeficitRequest decryptCloseFinancialDeficitRequest = closeFinancialDeficitRequest
				.decrypt(cryptoMngrCoreService);

		return ResponseEntity.ok(cryptoMngrCoreService.
				encrypt(financialDeficitManagement.closeFinancialDeficit(decryptCloseFinancialDeficitRequest).toString()));
	}

	/**
	 * Download Attachement deposit file
	 */
	@PostMapping("/download/att")
	public void downloadInsuranceFile(@Valid @RequestBody URLRequest uRLRequest, HttpServletResponse response) {

		URLRequest decryptURLRequest = uRLRequest.decrypt(cryptoMngrCoreService);

		try {

			InputStream is = financialDeficitManagement.downloadFinancialDeficitAttFile(decryptURLRequest);
			IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns all Approved Financial Deficit list By User Agent in the system.
	 */
	@PostMapping("/getApprovedFinancialDeficitByUser")
//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns all Rejected Financial Deficit list By User Agent.")
	public ResponseEntity<FinancialDeficitPagingDTO> getApprovedFinancialDeficitByUser(
			@Valid @RequestBody String jsonString) {

		ApprovedFinancialDeficitRequest decryptApprovedFinancialDeficitRequest = 
				convertJsonStringToObject(jsonString, ApprovedFinancialDeficitRequest.class);

		return ResponseEntity.ok(
				financialDeficitManagement.getApprovedFinancialDeficitByUser(decryptApprovedFinancialDeficitRequest));
	}
}

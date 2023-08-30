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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aman.payment.core.management.CoreJsonObjectFactoryImpl;
import com.aman.payment.core.management.PullAccountManagement;
import com.aman.payment.core.model.dto.PullAccountDTO;
import com.aman.payment.core.model.dto.PullAccountPagingDTO;
import com.aman.payment.core.model.payload.ApprovedPullAccountRequest;
import com.aman.payment.core.model.payload.CloseDayPullAccountRequest;
import com.aman.payment.core.model.payload.GetPullAccountRequest;
import com.aman.payment.core.model.payload.OpeningPullAccountRequest;
import com.aman.payment.core.model.payload.PullAccountByUserRequest;
import com.aman.payment.core.model.payload.URLRequest;
import com.aman.payment.core.service.CryptoMngrCoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/pullAccount")
@Api(value = "Pull Account Rest API", description = "Defines endpoints for the Pull Account. It's secured by default")

public class PullAccountController extends CoreJsonObjectFactoryImpl{

	private static final Logger logger = Logger.getLogger(PullAccountController.class);
	private final PullAccountManagement pullAccountManagement;
	private final CryptoMngrCoreService cryptoMngrCoreService;

	@Value("${attachment.deposit.bank}")
	private String bankBasePackagePath;

	@Value("${attachment.deposit.visa}")
	private String visaBasePackagePath;

	@Value("${attachment.deficit}")
	private String deficitBasePackagePath;

	@Autowired
	public PullAccountController(PullAccountManagement pullAccountManagement,
			CryptoMngrCoreService cryptoMngrCoreService) {
		this.pullAccountManagement = pullAccountManagement;
		this.cryptoMngrCoreService = cryptoMngrCoreService;
	}

	/**
	 * Returns all Claims Transaction list related with user in the system.
	 */
	@PostMapping("/claimsByUser")
//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Claims Transactions related with User.")
	public ResponseEntity<List<String>> getPullAccountByUser(
			@Valid @RequestBody String jsonString) {

		PullAccountByUserRequest decryptPullAccountByUserRequest = 
				convertJsonStringToObject(jsonString, PullAccountByUserRequest.class);

		List<PullAccountDTO> result = pullAccountManagement.
				getNewOrRejectedPullAccount(decryptPullAccountByUserRequest);
		
		List<String> eTransaction = new ArrayList<String>();
		result.stream().forEach(s -> {
			eTransaction.add(cryptoMngrCoreService.encrypt(s.toString()));
		});
		
		return ResponseEntity.ok(eTransaction);
	}

	/**
	 * Add Pull Account Deposit Transaction
	 * close end of day for every agent
	 */
	@PostMapping(value = "/payPullAccountByUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Add new Pull Account Deposit Transaction Returns the Pull Account Object Details.")
	public ResponseEntity<String> payPullAccountByUser(
			@Valid @RequestPart CloseDayPullAccountRequest closeDayPullAccountRequest,
			@RequestPart MultipartFile fileBankAtt, @RequestPart MultipartFile fileVisaAtt ) {
		 
		closeDayPullAccountRequest.setFileBankAtt(fileBankAtt);
		closeDayPullAccountRequest.setFileVisaAtt(fileVisaAtt);
		CloseDayPullAccountRequest decryptCloseDayPullAccountRequest = closeDayPullAccountRequest
				.decrypt(cryptoMngrCoreService);

		return ResponseEntity.ok(cryptoMngrCoreService.encrypt(pullAccountManagement.
				closeDayPullAccount(decryptCloseDayPullAccountRequest).toString()));
	}

	/**
	 * Download Attachement deposit file
	 */
	@PostMapping("/download/att")
	public void downloadInsuranceFile(@Valid @RequestBody URLRequest uRLRequest, HttpServletResponse response) {

		URLRequest decryptURLRequest = uRLRequest.decrypt(cryptoMngrCoreService);

		try {

			InputStream is = pullAccountManagement.downloadPullAccountAttFile(decryptURLRequest);
			IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the list of All Approved Deposits Transactions related with user.
	 */
	@PostMapping("/getApprovedPullAccountByUser")
//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Deposits Transactions related with user.")
	public ResponseEntity<PullAccountPagingDTO> getApprovedPullAccountByUser(
			@Valid @RequestBody String jsonString) {
		
		ApprovedPullAccountRequest decryptApprovedPullAccountRequest = 
				convertJsonStringToObject(jsonString, ApprovedPullAccountRequest.class);

		return ResponseEntity.ok(pullAccountManagement.approvedPullAccountByUser(decryptApprovedPullAccountRequest));
	}
	
	@PostMapping("/OpeningSettlementCode")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Opened PullAccount.")
	public ResponseEntity<List<String>> getOpenSettlements(
			@Valid @RequestBody String jsonString) {
		
		OpeningPullAccountRequest decryptOpeningPullAccountRequest = 
				convertJsonStringToObject(jsonString, OpeningPullAccountRequest.class);

		return ResponseEntity.ok(pullAccountManagement.openingPullAccountByUser(decryptOpeningPullAccountRequest));
	}

	@GetMapping("/allOpeningSettlementCode")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Opened PullAccount.")
	public ResponseEntity<List<String>> getAllOpenSettlements() {
		
		return ResponseEntity.ok(pullAccountManagement.openingPullAccount());
	}
	
	@PostMapping("/getSettlementCode")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the PullAccount.")
	public ResponseEntity<String> getSettlementCode(
			@Valid @RequestBody String jsonString) {
		
		GetPullAccountRequest decryptGetPullAccountRequest = 
				convertJsonStringToObject(jsonString, GetPullAccountRequest.class);

		return ResponseEntity.ok(pullAccountManagement.getSettlementCode(decryptGetPullAccountRequest));
	}
	
	@PostMapping("/updateSettlementStatus")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the PullAccount.")
	public ResponseEntity<String> updateSettlementStatus(
			@Valid @RequestBody String jsonString) {
		
		GetPullAccountRequest decryptGetPullAccountRequest = 
				convertJsonStringToObject(jsonString, GetPullAccountRequest.class);

		return ResponseEntity.ok(pullAccountManagement.updateSettlementCode(decryptGetPullAccountRequest));
	}
}

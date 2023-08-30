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
package com.aman.payment.maazoun.controller;

import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aman.payment.annotation.CurrentUser;
import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.core.model.audit.TransactionECRAudit;
import com.aman.payment.core.model.payload.PosMidRequest;
import com.aman.payment.maazoun.management.MaazounJsonObjectFactoryImpl;
import com.aman.payment.maazoun.management.MaazounRequestInfoManagement;
import com.aman.payment.maazoun.model.dto.MaazounRequestInfoPagingDTO;
import com.aman.payment.maazoun.model.dto.PagingDTO;
import com.aman.payment.maazoun.model.payload.AddMaazounRequestInfoRequest;
import com.aman.payment.maazoun.model.payload.AdvancedSearchBookRequestInfoTransactionRequest;
import com.aman.payment.maazoun.model.payload.GetBookRequestInfoById;
import com.aman.payment.maazoun.model.payload.GetBookRequestInfoByMaazounId;
import com.aman.payment.maazoun.model.payload.MaazounBookRequestInfoRefundRequest;
import com.aman.payment.maazoun.model.payload.QuickSearchBookRequestInfoTransactionRequest;
import com.aman.payment.maazoun.model.payload.URLRequest;
import com.aman.payment.util.StatusConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/maazoun/request")
@Api(value = "Maazoun Request Rest API", description = "Defines endpoints for the Maazoun Request. It's secured by default")
public class MaazounBookRequestController extends MaazounJsonObjectFactoryImpl {

	final static Logger logger = Logger.getLogger("maazoun");

	private final MaazounRequestInfoManagement maazounRequestInfoManagement;
	
	@Autowired
	public MaazounBookRequestController(MaazounRequestInfoManagement maazounRequestInfoManagement) {
		super();
		this.maazounRequestInfoManagement = maazounRequestInfoManagement;
	}
	
	@PostMapping("/createBookRequest")
	@ApiOperation(value = "Create Book Request Returns the MaazounRequestInfo Object Details.")
	public ResponseEntity<String> createBookRequest(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		AddMaazounRequestInfoRequest decryptAddMaazounRequestInfoRequest =
				convertJsonStringToObject(jsonString, AddMaazounRequestInfoRequest.class);

		TransactionECRAudit eTransactionECRAudit = null;
		if(decryptAddMaazounRequestInfoRequest.getPaymentEcrResponse() != null &&
				!decryptAddMaazounRequestInfoRequest.getPaymentEcrResponse().isEmpty())
			eTransactionECRAudit = convertDecryptedJsonStringToObject(
					decryptAddMaazounRequestInfoRequest.getPaymentEcrResponse(), TransactionECRAudit.class);

		return ResponseEntity.ok(maazounRequestInfoManagement.
				addBookRequestInfo(customUserDetails, decryptAddMaazounRequestInfoRequest, eTransactionECRAudit));
	}
	
	@PostMapping("/bookRequestRefund")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Change he Hold Payment Transaction to Refund Transaction or to Refund Returns the Final Transaction Object Details.")
	public ResponseEntity<String> doPermissionRefund(
			@CurrentUser CustomUserDetails customUserDetails,
			@RequestPart MultipartFile attRefund,
			@Valid @RequestPart String jsonString) {
		
		MaazounBookRequestInfoRefundRequest decryptMaazounBookRequestInfoRefundRequest = 
				convertJsonStringToObject(jsonString, MaazounBookRequestInfoRefundRequest.class);
		
		decryptMaazounBookRequestInfoRefundRequest.setAttRefund(attRefund);
		
		TransactionECRAudit eTransactionECRAudit = null;
		if(decryptMaazounBookRequestInfoRefundRequest.getPaymentEcrResponse() != null &&
				!decryptMaazounBookRequestInfoRefundRequest.getPaymentEcrResponse().isEmpty())
			eTransactionECRAudit = convertJsonStringToObject(decryptMaazounBookRequestInfoRefundRequest.getPaymentEcrResponse(), TransactionECRAudit.class);

		return ResponseEntity.ok(maazounRequestInfoManagement.
				createRefundBookRequestInfo(customUserDetails, 
						decryptMaazounBookRequestInfoRefundRequest, eTransactionECRAudit));
	}
	
	@PostMapping("/quickSerachBookRequestInfoTransactions")
	public ResponseEntity<MaazounRequestInfoPagingDTO> quickSerachBookRequestInfoTransactions(
			@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		QuickSearchBookRequestInfoTransactionRequest decryptQuickSearchBookRequestInfoTransactionRequest = 
				convertJsonStringToObject(jsonString, QuickSearchBookRequestInfoTransactionRequest.class);
		
		return ResponseEntity.ok(maazounRequestInfoManagement.quickSeracBookRequestInfoTransactions(
				decryptQuickSearchBookRequestInfoTransactionRequest, customUserDetails));
	}
	
	@PostMapping("/advancedSearchBookRequestInfoTransactions")
	public ResponseEntity<MaazounRequestInfoPagingDTO> advancedSearchBookRequestInfoTransactions(
			@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {
		
		AdvancedSearchBookRequestInfoTransactionRequest decryptAdvancedSearchBookRequestInfoTransactionRequest = 
				convertJsonStringToObject(jsonString, AdvancedSearchBookRequestInfoTransactionRequest.class);

		if(customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT) ||
				customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT_SUPERVISOR)) {
			decryptAdvancedSearchBookRequestInfoTransactionRequest.setAgentName(customUserDetails.getUsername());
		}

		return ResponseEntity.ok(maazounRequestInfoManagement.advancedSerachBookRequestInfoTransactions(
				decryptAdvancedSearchBookRequestInfoTransactionRequest, customUserDetails));
	}
	
	@PostMapping("/download/att")
	public void downloadFile(@Valid @RequestBody String jsonString, 
			HttpServletResponse response) {

		URLRequest decryptURLRequest = 
				convertJsonStringToObject(jsonString, URLRequest.class);

		try {

			InputStream is = maazounRequestInfoManagement.downloadFile(decryptURLRequest);
			IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@PostMapping("/getSalePosMidRequestFormat")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Return Pos Mid Request Format.")
	public ResponseEntity<String> getSalePosMidRequestFormat(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {
		
		PosMidRequest decryptPosMidRequest =
				convertJsonStringToObject(jsonString, PosMidRequest.class);
		
		return ResponseEntity.ok(maazounRequestInfoManagement.
				calculateSalePosMidRequestAmountFormat(decryptPosMidRequest, customUserDetails));
	}
	
	@PostMapping("/getRefundPosMidRequestFormat")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Return Pos Mid Request Format.")
	public ResponseEntity<String> getRefundPosMidRequestFormat(
			@Valid @RequestBody String jsonString) {
		
		PosMidRequest decryptPosMidRequest =
				convertJsonStringToObject(jsonString, PosMidRequest.class);
		
		return ResponseEntity.ok(maazounRequestInfoManagement.calculateRefundPosMidRequestAmountFormat(decryptPosMidRequest));
	}
	
	@PostMapping("/getBookRequestInfoByMaazounId")
	public ResponseEntity<PagingDTO> getBookRequestInfoByMaazounId(
			@Valid @RequestBody String jsonString) {

		GetBookRequestInfoByMaazounId decryptGetBookRequestInfoByMaazounId = 
				convertJsonStringToObject(jsonString, GetBookRequestInfoByMaazounId.class);
		
		return ResponseEntity.ok(maazounRequestInfoManagement.getBookRequestInfoByMaazounId(
				decryptGetBookRequestInfoByMaazounId));
	}
	
	@PostMapping("/editBookRequestInfoMaazounId")
	public ResponseEntity<String> editBookRequestInfoMaazounId(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		GetBookRequestInfoById decryptGetBookRequestInfoById = 
				convertJsonStringToObject(jsonString, GetBookRequestInfoById.class);
		
		return ResponseEntity.ok(maazounRequestInfoManagement.editBookRequestInfoMaazounId(
				customUserDetails, decryptGetBookRequestInfoById));
	}

}

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aman.payment.annotation.CurrentUser;
import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.core.model.audit.TransactionECRAudit;
import com.aman.payment.core.model.payload.PosMidRequest;
import com.aman.payment.maazoun.management.MaazounBookCollectionInfoManagement;
import com.aman.payment.maazoun.management.MaazounJsonObjectFactoryImpl;
import com.aman.payment.maazoun.model.dto.MaazounCollectInfoPagingDTO;
import com.aman.payment.maazoun.model.payload.AddMaazounCollectionInfoRequest;
import com.aman.payment.maazoun.model.payload.AddMaazounContractRequest;
import com.aman.payment.maazoun.model.payload.AddMaazounCustodyContractRequest;
import com.aman.payment.maazoun.model.payload.AddReceivedContractStatusRequest;
import com.aman.payment.maazoun.model.payload.AddReceivedMaazounBookRequest;
import com.aman.payment.maazoun.model.payload.AdvancedSearchCollectionInfoTransactionRequest;
import com.aman.payment.maazoun.model.payload.BookChecklistRequest;
import com.aman.payment.maazoun.model.payload.CollectionListSearchRequest;
import com.aman.payment.maazoun.model.payload.ContractByNationalIdRequest;
import com.aman.payment.maazoun.model.payload.DeleteMaazounContractRequest;
import com.aman.payment.maazoun.model.payload.EditMaazounContractRequest;
import com.aman.payment.maazoun.model.payload.MaazounBookRequestInfoRefundRequest;
import com.aman.payment.maazoun.model.payload.MaazounContractInfoRefundRequest;
import com.aman.payment.maazoun.model.payload.QuickSearchCollectiontInfoTransactionRequest;
import com.aman.payment.maazoun.model.payload.ReviewRequest;
import com.aman.payment.maazoun.model.payload.URLRequest;
import com.aman.payment.util.StatusConstant;

import io.micrometer.core.lang.Nullable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/maazoun/collection")
@Api(value = "Maazoun Request Rest API", description = "Defines endpoints for the Maazoun Request. It's secured by default")
public class MaazounBookCollectionController extends MaazounJsonObjectFactoryImpl {

	final static Logger logger = Logger.getLogger("maazoun");

	private final MaazounBookCollectionInfoManagement maazounBookCollectionInfoManagement;
	
	@Autowired
	public MaazounBookCollectionController(MaazounBookCollectionInfoManagement maazounBookCollectionInfoManagement) {
		super();
		this.maazounBookCollectionInfoManagement = maazounBookCollectionInfoManagement;
	}
	
	@PostMapping("/bookChecklist")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the book checklist of configured checklist. Requires ADMIN Access")
	public ResponseEntity<List<String>> getBookChecklist(@Valid @RequestBody String jsonString) {
		
		BookChecklistRequest decryptBookChecklistRequest =
				convertJsonStringToObject(jsonString, BookChecklistRequest.class);
		
		return ResponseEntity.ok(maazounBookCollectionInfoManagement.
				getBookChecklist(Long.valueOf(decryptBookChecklistRequest.getBookTypeId())));
	}
	
	@PostMapping("/addContractRequest")
	@ApiOperation(value = "Add Contract Book Request Returns the MaazounBookCollectionInfo Object Details.")
	public ResponseEntity<String> addContractRequest(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestPart String jsonString,@Valid @RequestPart MultipartFile attContract) {

		AddMaazounContractRequest decryptAddMaazounContractRequest =
				convertJsonStringToObject(jsonString, AddMaazounContractRequest.class);
	 	decryptAddMaazounContractRequest.setAttContract(attContract);
		return ResponseEntity.ok(maazounBookCollectionInfoManagement.
				addContractRequest(customUserDetails, decryptAddMaazounContractRequest));
	}
	
	@PostMapping("/addCustodyContractRequest")
	@ApiOperation(value = "Add Custody Contract Book Request Returns the MaazounBookCollectionInfo Object Details.")
	public ResponseEntity<String> addCustodyContractRequest(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestPart String jsonString) {

		AddMaazounCustodyContractRequest decryptAddMaazounCustodyContractRequest =
				convertJsonStringToObject(jsonString, AddMaazounCustodyContractRequest.class);

		return ResponseEntity.ok(maazounBookCollectionInfoManagement.
				editCustodyContractRequest(customUserDetails, decryptAddMaazounCustodyContractRequest));
	}
	
	@PostMapping("/deleteContractRequest")
	@ApiOperation(value = "delete Contract Book Request Returns the MaazounBookCollectionInfo Object Details.")
	public ResponseEntity<String> deleteContractRequest(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		DeleteMaazounContractRequest decryptDeleteMaazounContractRequest =
				convertJsonStringToObject(jsonString, DeleteMaazounContractRequest.class);

		return ResponseEntity.ok(maazounBookCollectionInfoManagement.
				deleteContractRequest(customUserDetails, decryptDeleteMaazounContractRequest));
	}
	
	@PostMapping("/createCollectionRequest")
	@ApiOperation(value = "Create Collection Book Request Returns the MaazounBookCollectionInfo Object Details.")
	public ResponseEntity<String> createCollectionRequest(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		AddMaazounCollectionInfoRequest decryptAddMaazounCollectionInfoRequest =
				convertJsonStringToObject(jsonString, AddMaazounCollectionInfoRequest.class);
		
		TransactionECRAudit eTransactionECRAudit = null;
		if(decryptAddMaazounCollectionInfoRequest.getPaymentEcrResponse() != null &&
				!decryptAddMaazounCollectionInfoRequest.getPaymentEcrResponse().isEmpty())
			eTransactionECRAudit = convertDecryptedJsonStringToObject(
					decryptAddMaazounCollectionInfoRequest.getPaymentEcrResponse(), TransactionECRAudit.class);


		return ResponseEntity.ok(maazounBookCollectionInfoManagement.
				createCollectionRequest(customUserDetails, decryptAddMaazounCollectionInfoRequest, eTransactionECRAudit));
	}
	
	@PostMapping("/getHoldCollectionRequest")
	@ApiOperation(value = "Return All Hold Collection Book Request Returns the MaazounBookCollectionInfo Object Details.")
	public ResponseEntity<List<String>> getHoldCollectionRequest(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		ContractByNationalIdRequest decryptContractByNationalIdRequest =
				convertJsonStringToObject(jsonString, ContractByNationalIdRequest.class);
		
		return ResponseEntity.ok(maazounBookCollectionInfoManagement.
				getHoldCollectionRequest(customUserDetails, decryptContractByNationalIdRequest));
	}
	

	@PostMapping("/getContractsByMaazounAndMaazounia")
	@ApiOperation(value = "Return All Hold Collection Book Request Returns the MaazounBookCollectionInfo Object Details.")
	public ResponseEntity<List<String>> getContractsByMaazounAndMaazounia(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		ContractByNationalIdRequest decryptContractByNationalIdRequest =
				convertJsonStringToObject(jsonString, ContractByNationalIdRequest.class);
		
		return ResponseEntity.ok(maazounBookCollectionInfoManagement.
				getContractsByMaazounAndMaazounia(customUserDetails, decryptContractByNationalIdRequest));
	}
	
	@PostMapping("/quickSerachCollectionInfoTransactions")
	public ResponseEntity<MaazounCollectInfoPagingDTO> quickSerachCollectionInfoTransactions(
			@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		QuickSearchCollectiontInfoTransactionRequest decryptQuickSearchCollectiontInfoTransactionRequest = 
				convertJsonStringToObject(jsonString, QuickSearchCollectiontInfoTransactionRequest.class);
		
		return ResponseEntity.ok(maazounBookCollectionInfoManagement.quickSerachCollectionInfoTransactions(
				decryptQuickSearchCollectiontInfoTransactionRequest, customUserDetails));
	}
	
	@PostMapping("/advancedSearchCollectionInfoTransactions")
	public ResponseEntity<MaazounCollectInfoPagingDTO> advancedSearchCollectionInfoTransactions(
			@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {
		
		AdvancedSearchCollectionInfoTransactionRequest decryptAdvancedSearchCollectionInfoTransactionRequest = 
				convertJsonStringToObject(jsonString, AdvancedSearchCollectionInfoTransactionRequest.class);

		if(customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT) ||
				customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT_SUPERVISOR)) {
			decryptAdvancedSearchCollectionInfoTransactionRequest.setAgentName(customUserDetails.getUsername());
		}

		return ResponseEntity.ok(maazounBookCollectionInfoManagement.advancedSerachCollectionInfoTransactions(
				decryptAdvancedSearchCollectionInfoTransactionRequest, customUserDetails));
	}
	
	@PostMapping("/download/att")
	public void downloadFile(@Valid @RequestBody String jsonString, 
			HttpServletResponse response) {

		URLRequest decryptURLRequest = 
				convertJsonStringToObject(jsonString, URLRequest.class);

		try {

			InputStream is = maazounBookCollectionInfoManagement.downloadFile(decryptURLRequest);
			IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@PostMapping("/collectedDatalist")
	public ResponseEntity<MaazounCollectInfoPagingDTO> getContractsUnderReview(@Valid @RequestBody String jsonString,
			@CurrentUser CustomUserDetails customUserDetails) {

		CollectionListSearchRequest decryptCollectionListSearchRequest =
				
				convertJsonStringToObject(jsonString, CollectionListSearchRequest.class);
		return ResponseEntity.ok(maazounBookCollectionInfoManagement.
				getContractsListByDateAndStatus(customUserDetails, decryptCollectionListSearchRequest));
	}
	
	@PostMapping("/reviewContract")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All approved collected contract.")
	public ResponseEntity<String> reviewContract(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		ReviewRequest decryptReviewRequest = 
				convertJsonStringToObject(jsonString, ReviewRequest.class);

		return ResponseEntity.ok(maazounBookCollectionInfoManagement.
				reviewContract(customUserDetails, decryptReviewRequest));
	}
	
	@PostMapping("/addReceivedBookStatus")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All approved collected contract.")
	public ResponseEntity<String> addReceivedBookStatus(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestPart String jsonString, @RequestPart @Nullable MultipartFile attContract) {

		AddReceivedContractStatusRequest decryptAddReceivedContractStatusRequest = 
				convertJsonStringToObject(jsonString, AddReceivedContractStatusRequest.class);

		return ResponseEntity.ok(maazounBookCollectionInfoManagement.
				addReceivedBookStatus(customUserDetails, decryptAddReceivedContractStatusRequest, attContract));
	}
	
	@PostMapping("/addReceivedBook")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All approved collected contract.")
	public ResponseEntity<String> addReceivedBook(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		AddReceivedMaazounBookRequest decryptAddReceivedMaazounBookRequest = 
				convertJsonStringToObject(jsonString, AddReceivedMaazounBookRequest.class);

		return ResponseEntity.ok(maazounBookCollectionInfoManagement.
				addReceivedBook(customUserDetails, decryptAddReceivedMaazounBookRequest));
	}
	
	@PostMapping("/contractRefund")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Change he Hold Payment Transaction to Refund Transaction or to Refund Returns the Final Transaction Object Details.")
	public ResponseEntity<String> contractRefund(
			@CurrentUser CustomUserDetails customUserDetails,
			@RequestPart MultipartFile attRefund,
			@Valid @RequestPart String jsonString) {
		
		MaazounContractInfoRefundRequest decryptMaazounContractInfoRefundRequest = 
				convertJsonStringToObject(jsonString, MaazounContractInfoRefundRequest.class);
		
		decryptMaazounContractInfoRefundRequest.setAttRefund(attRefund);
		
		TransactionECRAudit eTransactionECRAudit = null;
		if(decryptMaazounContractInfoRefundRequest.getPaymentEcrResponse() != null &&
				!decryptMaazounContractInfoRefundRequest.getPaymentEcrResponse().isEmpty())
			eTransactionECRAudit = convertJsonStringToObject(decryptMaazounContractInfoRefundRequest.getPaymentEcrResponse(), TransactionECRAudit.class);

		return ResponseEntity.ok(maazounBookCollectionInfoManagement.
				createRefundContractInfo(customUserDetails, 
						decryptMaazounContractInfoRefundRequest, eTransactionECRAudit));
	}

	@PostMapping("/editContractdata")
	@ApiOperation(value = "Add Contract Book Request Returns the MaazounBookCollectionInfo Object Details.")
	public ResponseEntity<String> editContractdata(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestPart String jsonString,  @Valid @RequestPart MultipartFile attContract ) {

		EditMaazounContractRequest   decrypteditMaazounContractRequest =
				convertJsonStringToObject(jsonString, EditMaazounContractRequest.class);
		decrypteditMaazounContractRequest.setAttContract(attContract);
		return ResponseEntity.ok(maazounBookCollectionInfoManagement.
				editContractData(customUserDetails, decrypteditMaazounContractRequest));
	}
	
	@PostMapping("/getSalePosMidRequestFormat")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Return Pos Mid Request Format.")
	public ResponseEntity<String> getSalePosMidRequestFormat(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {
		
		PosMidRequest decryptPosMidRequest =
				convertJsonStringToObject(jsonString, PosMidRequest.class);
		
		return ResponseEntity.ok(maazounBookCollectionInfoManagement.
				calculateSalePosMidRequestAmountFormat(decryptPosMidRequest, customUserDetails));
	}
	
	@PostMapping("/getRefundPosMidRequestFormat")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Return Pos Mid Request Format.")
	public ResponseEntity<String> getRefundContractPosMidRequestFormat(
			@Valid @RequestBody String jsonString) {
		
		PosMidRequest decryptPosMidRequest =
				convertJsonStringToObject(jsonString, PosMidRequest.class);
		
		return ResponseEntity.ok(maazounBookCollectionInfoManagement.calculateRefundPosMidRequestAmountFormat(decryptPosMidRequest));
	}
	
}

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
import com.aman.payment.maazoun.management.MaazounBookDeliverInfoManagement;
import com.aman.payment.maazoun.management.MaazounJsonObjectFactoryImpl;
import com.aman.payment.maazoun.model.dto.MaazounDeliverInfoPagingDTO;
import com.aman.payment.maazoun.model.payload.AddMaazounDeliverInfoRequest;
import com.aman.payment.maazoun.model.payload.CloseMaazounDeliverInfoRequest;
import com.aman.payment.maazoun.model.payload.GetContractRequest;
import com.aman.payment.maazoun.model.payload.SearchDeliveredInfoTransactionRequest;
import com.aman.payment.maazoun.model.payload.URLRequest;
import com.aman.payment.util.StatusConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import reactor.util.annotation.Nullable;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/maazoun/deliver")
@Api(value = "Maazoun Deliver contracts Rest API", description = "Defines endpoints for the Maazoun deliver contracts. It's secured by default")
public class MaazounBookDeliverController extends MaazounJsonObjectFactoryImpl {

	final static Logger logger = Logger.getLogger("maazoun");

	private final MaazounBookDeliverInfoManagement maazounBookDeliverInfoManagement;
	
	@Autowired
	public MaazounBookDeliverController(MaazounBookDeliverInfoManagement maazounBookDeliverInfoManagement) {
		super();
		this.maazounBookDeliverInfoManagement = maazounBookDeliverInfoManagement;
	}
	
	@PostMapping("/getReviewedContract")
	@ApiOperation(value = "Get Contract Book Request Returns the MaazounBookCollectionInfo Object Details.")
	public ResponseEntity<List<String>> getReviewedContract(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		GetContractRequest decryptGetContractRequest =
				convertJsonStringToObject(jsonString, GetContractRequest.class);

		return ResponseEntity.ok(maazounBookDeliverInfoManagement.
				getReviewedContract(customUserDetails, decryptGetContractRequest));
	}
	
	@PostMapping("/getReceivedContract")
	@ApiOperation(value = "Get Received Contract Book Request Returns the MaazounBookCollectionInfo Object Details.")
	public ResponseEntity<List<String>> getReceivedContract(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		GetContractRequest decryptGetContractRequest =
				convertJsonStringToObject(jsonString, GetContractRequest.class);

		return ResponseEntity.ok(maazounBookDeliverInfoManagement.
				getReceivedContract(customUserDetails, decryptGetContractRequest));
	}
	
	@PostMapping("/createDeliverRequest")
	@ApiOperation(value = "Create Deliver Book Request Returns the MaazounBookCollectionInfo Object Details.")
	public ResponseEntity<String> createDeliverRequest(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		AddMaazounDeliverInfoRequest decryptAddMaazounDeliverInfoRequest =
				convertJsonStringToObject(jsonString, AddMaazounDeliverInfoRequest.class);

		return ResponseEntity.ok(maazounBookDeliverInfoManagement.
				createDeliverRequest(customUserDetails, decryptAddMaazounDeliverInfoRequest));
	}
	
	@PostMapping("/closeActionDeliverRequest")
	@ApiOperation(value = "Close action Deliver Book Request Returns the MaazounBookCollectionInfo Object Details.")
	public ResponseEntity<String> closeActionDeliverRequest(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestPart String jsonString,
			@Nullable @RequestPart(name="deliverAssignReport", required=false) MultipartFile deliverAssignReport) {

		CloseMaazounDeliverInfoRequest decryptCloseMaazounDeliverInfoRequest =
				convertJsonStringToObject(jsonString, CloseMaazounDeliverInfoRequest.class);
		
		return ResponseEntity.ok(maazounBookDeliverInfoManagement.
				closeActionDeliverRequest(customUserDetails, decryptCloseMaazounDeliverInfoRequest, deliverAssignReport));
	}
	
	@PostMapping("/deleteActionDeliverRequest")
	@ApiOperation(value = "Close action Deliver Book Request Returns the MaazounBookCollectionInfo Object Details.")
	public ResponseEntity<String> deleteActionDeliverRequest(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		CloseMaazounDeliverInfoRequest decryptCloseMaazounDeliverInfoRequest =
				convertJsonStringToObject(jsonString, CloseMaazounDeliverInfoRequest.class);
		
		return ResponseEntity.ok(maazounBookDeliverInfoManagement.
				deleteActionDeliverRequest(customUserDetails, decryptCloseMaazounDeliverInfoRequest));
	}
	
	@GetMapping("/getPendingDeliverRequests")
	@ApiOperation(value = "Get List of deliver pending requests Returns the MaazounBookCollectionInfo Object Details.")
	public ResponseEntity<List<String>> getPendingDeliverRequests(@CurrentUser CustomUserDetails customUserDetails) {
		
		return ResponseEntity.ok(maazounBookDeliverInfoManagement.
				getPendingDeliverRequests(customUserDetails));
	}
	
	@PostMapping("/searchDeliveredRequests")
	public ResponseEntity<MaazounDeliverInfoPagingDTO> searchDeliveredRequests(
			@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {
		
		SearchDeliveredInfoTransactionRequest decryptSearchDeliveredInfoTransactionRequest = 
				convertJsonStringToObject(jsonString, SearchDeliveredInfoTransactionRequest.class);

		return ResponseEntity.ok(maazounBookDeliverInfoManagement.searchDeliveredRequests(
				customUserDetails, decryptSearchDeliveredInfoTransactionRequest));
	}
	
	@PostMapping("/download/att")
	public void downloadFile(@Valid @RequestBody String jsonString, HttpServletResponse response) {

		URLRequest decryptURLRequest = 
				convertJsonStringToObject(jsonString, URLRequest.class);

		try {

			InputStream is = maazounBookDeliverInfoManagement.downloadFile(decryptURLRequest);
			IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

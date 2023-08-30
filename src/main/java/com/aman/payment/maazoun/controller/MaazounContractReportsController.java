
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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aman.payment.annotation.CurrentUser;
import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.maazoun.management.MaazounBookCollectionInfoManagement;
import com.aman.payment.maazoun.management.MaazounJsonObjectFactoryImpl;
import com.aman.payment.maazoun.model.dto.MaazounCollectInfoPagingDTO;
import com.aman.payment.maazoun.model.dto.PagingDTO;
import com.aman.payment.maazoun.model.payload.MaazounAuditRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/maazoun/contract/technicalReport")
@Api(value = "Maazoun contracts Technical Reports of Manangement Rest API", description = "Defines endpoints for the Maazoun contracts Technical Reports Manangement. It's secured by default")

public class MaazounContractReportsController extends MaazounJsonObjectFactoryImpl{

	private final MaazounBookCollectionInfoManagement maazounBookCollectionInfoManagement;

	@Autowired
	public MaazounContractReportsController(MaazounBookCollectionInfoManagement maazounBookCollectionInfoManagement) {
		this.maazounBookCollectionInfoManagement = maazounBookCollectionInfoManagement;
	}
	

	
	@PostMapping("/auditByCreatedDate")
	//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Accumulate Data related with Created At.")
	public ResponseEntity<MaazounCollectInfoPagingDTO> auditByCreatedDate(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {
		
		MaazounAuditRequest decryptMaazounAuditRequest = 
				convertJsonStringToObject(jsonString, MaazounAuditRequest.class);
		
		return ResponseEntity.ok(maazounBookCollectionInfoManagement.
				getAuditByCreatedDate(decryptMaazounAuditRequest, customUserDetails));
	}
	
	@PostMapping("/auditByCreatedDateAndLocation")
	//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Accumulate Data related with Agent.")
	public ResponseEntity<MaazounCollectInfoPagingDTO> auditByCreatedDateAndLocation(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {
		
		MaazounAuditRequest decryptMaazounAuditRequest = 
				convertJsonStringToObject(jsonString, MaazounAuditRequest.class);
		
		return ResponseEntity.ok(maazounBookCollectionInfoManagement.
				getAuditByCreatedDateAndLocation(decryptMaazounAuditRequest, customUserDetails));
	}
	
	@PostMapping("/auditByCreatedDateAndCourt")
	//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Accumulate Data related with Agent.")
	public ResponseEntity<MaazounCollectInfoPagingDTO> auditByCreatedDateAndCourt(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {
		
		MaazounAuditRequest decryptMaazounAuditRequest = 
				convertJsonStringToObject(jsonString, MaazounAuditRequest.class);
		
		return ResponseEntity.ok(maazounBookCollectionInfoManagement.
				getAuditByCreatedDateAndCourt(decryptMaazounAuditRequest, customUserDetails));
	}
	
	@PostMapping("/auditByCreatedDateAndAgentAndLocation")
	//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Accumulate Data related with Agent.")
	public ResponseEntity<MaazounCollectInfoPagingDTO> auditByCreatedDateAndAgentAndLocation(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {
		
		MaazounAuditRequest decryptMaazounAuditRequest = 
				convertJsonStringToObject(jsonString, MaazounAuditRequest.class);
		
		return ResponseEntity.ok(maazounBookCollectionInfoManagement.
				getAuditByCreatedDateAndAgentAndLocation(decryptMaazounAuditRequest, customUserDetails));
	}
	
	@PostMapping("/getContractCustomAuditBySectors")
	//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Accumulate Data related with Contract Sectors.")
	public ResponseEntity<PagingDTO> getBookCustomAuditBySectors(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {
		
		MaazounAuditRequest decryptMaazounAuditRequest = 
				convertJsonStringToObject(jsonString, MaazounAuditRequest.class);
		
		return ResponseEntity.ok(maazounBookCollectionInfoManagement.getContractCustomAuditBySectors(
				decryptMaazounAuditRequest, customUserDetails));
	}
	
	@PostMapping("/getContractCustomAuditByCourts")
	//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Accumulate Data related with Contract Courts.")
	public ResponseEntity<PagingDTO> getBookCustomAuditByCourts(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {
		
		MaazounAuditRequest decryptMaazounAuditRequest = 
				convertJsonStringToObject(jsonString, MaazounAuditRequest.class);
		
		return ResponseEntity.ok(maazounBookCollectionInfoManagement.getContractCustomAuditByCourts(
				decryptMaazounAuditRequest, customUserDetails));
	}
	
	
}

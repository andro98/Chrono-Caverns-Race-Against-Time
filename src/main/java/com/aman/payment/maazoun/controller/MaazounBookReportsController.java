
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
import com.aman.payment.maazoun.management.MaazounJsonObjectFactoryImpl;
import com.aman.payment.maazoun.management.MaazounRequestInfoManagement;
import com.aman.payment.maazoun.model.dto.MaazounRequestInfoPagingDTO;
import com.aman.payment.maazoun.model.dto.PagingDTO;
import com.aman.payment.maazoun.model.payload.MaazounAuditRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/maazoun/book/technicalReport")
@Api(value = "Maazoun books Technical Reports of Manangement Rest API", description = "Defines endpoints for the Maazoun books Technical Reports Manangement. It's secured by default")

public class MaazounBookReportsController extends MaazounJsonObjectFactoryImpl{

	private final MaazounRequestInfoManagement maazounRequestInfoManagement;

	@Autowired
	public MaazounBookReportsController(MaazounRequestInfoManagement maazounRequestInfoManagement) {
		this.maazounRequestInfoManagement = maazounRequestInfoManagement;
	}

	
	@PostMapping("/auditByCreatedDate")
	//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Accumulate Data related with Created At.")
	public ResponseEntity<MaazounRequestInfoPagingDTO> auditByCreatedDate(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {
		
		MaazounAuditRequest decryptMaazounAuditRequest = 
				convertJsonStringToObject(jsonString, MaazounAuditRequest.class);
		
		return ResponseEntity.ok(maazounRequestInfoManagement.
				getAuditByCreatedDate(decryptMaazounAuditRequest, customUserDetails));
	}
	
	@PostMapping("/auditByCreatedDateAndCourt")
	//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Accumulate Data related with Agent.")
	public ResponseEntity<MaazounRequestInfoPagingDTO> auditByCreatedDateAndCourt(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {
		
		MaazounAuditRequest decryptMaazounAuditRequest = 
				convertJsonStringToObject(jsonString, MaazounAuditRequest.class);
		
		return ResponseEntity.ok(maazounRequestInfoManagement.
				getAuditByCreatedDateAndCourt(decryptMaazounAuditRequest, customUserDetails));
	}
	
	@PostMapping("/auditByCreatedDateAndAgentAndCourt")
	//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Accumulate Data related with Agent.")
	public ResponseEntity<MaazounRequestInfoPagingDTO> auditByCreatedDateAndAgentAndCourt(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {
		
		MaazounAuditRequest decryptMaazounAuditRequest = 
				convertJsonStringToObject(jsonString, MaazounAuditRequest.class);
		
		return ResponseEntity.ok(maazounRequestInfoManagement.
				getAuditByCreatedDateAndAgentAndCourt(decryptMaazounAuditRequest, customUserDetails));
	}
	
	@PostMapping("/getBookCustomAuditBySectors")
	//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Accumulate Data related with Agent.")
	public ResponseEntity<PagingDTO> getBookCustomAuditBySectors(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {
		
		MaazounAuditRequest decryptMaazounAuditRequest = 
				convertJsonStringToObject(jsonString, MaazounAuditRequest.class);
		
		return ResponseEntity.ok(maazounRequestInfoManagement.
				getBookCustomAuditBySectors(decryptMaazounAuditRequest, customUserDetails));
	}
	
	@PostMapping("/getBookCustomAuditByCourts")
	//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Accumulate Data related with Agent.")
	public ResponseEntity<PagingDTO> getBookCustomAuditByCourts(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {
		
		MaazounAuditRequest decryptMaazounAuditRequest = 
				convertJsonStringToObject(jsonString, MaazounAuditRequest.class);
		
		return ResponseEntity.ok(maazounRequestInfoManagement.
				getBookCustomAuditByCourts(decryptMaazounAuditRequest, customUserDetails));
	}
	
	
	
}

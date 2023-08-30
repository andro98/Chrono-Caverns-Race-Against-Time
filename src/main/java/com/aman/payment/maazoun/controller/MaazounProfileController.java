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

import java.util.List;

import javax.validation.Valid;

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
import com.aman.payment.maazoun.management.MaazounJsonObjectFactoryImpl;
import com.aman.payment.maazoun.management.MaazounProfileManagement;
import com.aman.payment.maazoun.model.dto.MaazounProfilePagingDTO;
import com.aman.payment.maazoun.model.dto.PagingDTO;
import com.aman.payment.maazoun.model.payload.AddEditMaazounMaazouniaChurchRequest;
import com.aman.payment.maazoun.model.payload.AddEditMaazounProfileRequest;
import com.aman.payment.maazoun.model.payload.AddEditMaazounSectorRequest;
import com.aman.payment.maazoun.model.payload.DeleteMaazounMaazouniaChurchRequest;
import com.aman.payment.maazoun.model.payload.EditMaazounCloseCustody;
import com.aman.payment.maazoun.model.payload.MaazounByIDRequest;
import com.aman.payment.maazoun.model.payload.MaazounProfileAuditRequest;
import com.aman.payment.maazoun.model.payload.MaazounProfileRequest;
import com.aman.payment.maazoun.model.payload.SearchmaazounProfileRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import reactor.util.annotation.Nullable;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/maazoun/profile")
@Api(value = "Maazoun Profile Rest API", description = "Defines endpoints for the Maazoun Profile. It's secured by default")
public class MaazounProfileController extends MaazounJsonObjectFactoryImpl {

	final static Logger logger = Logger.getLogger("maazoun");

	private final MaazounProfileManagement maazounProfileManagement;

	@Autowired
	public MaazounProfileController(MaazounProfileManagement maazounProfileManagement) {
		this.maazounProfileManagement = maazounProfileManagement;
	}


	@PostMapping("/maazouns")
//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured maazouns. Requires ADMIN Access")
	public ResponseEntity<MaazounProfilePagingDTO> getAllMaazouns(
			@Valid @RequestBody String jsonString, @CurrentUser CustomUserDetails customUserDetails) {
		
		MaazounProfileRequest decryptMaazounProfileRequest = 
				convertJsonStringToObject(jsonString, MaazounProfileRequest.class);
		
		return ResponseEntity.ok(maazounProfileManagement.getAllMaazouns(decryptMaazounProfileRequest, customUserDetails));
	}


	@GetMapping("/maazounsList")
//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured maazouns. Requires ADMIN Access")
	public ResponseEntity<List<String>> getAllMaazounsList(@CurrentUser CustomUserDetails customUserDetails) {
		
		return ResponseEntity.ok(maazounProfileManagement.getAllMaazounsList(customUserDetails));
	}
	
	@GetMapping("/activeMaazounsList")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured maazouns. Requires ADMIN Access")
	public ResponseEntity<List<String>> getAllActiveMaazounsList() {
		
		return ResponseEntity.ok(maazounProfileManagement.getAllMaazounsListByStatus(true));
	}
	
	@PostMapping("/add")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Add new maazoun. Requires ADMIN Access")
	public ResponseEntity<String> addMaazounProfile(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestPart String jsonString,
			@Nullable @RequestPart(name="maazounImge", required=false) MultipartFile maazounImge) {

		AddEditMaazounProfileRequest decryptAddEditMaazounProfileRequest = 
				convertJsonStringToObject(jsonString, AddEditMaazounProfileRequest.class);
		
		decryptAddEditMaazounProfileRequest.setFileMaazounImgAtt(maazounImge);
		return ResponseEntity
				.ok(maazounProfileManagement.
						addMaazounProfile(decryptAddEditMaazounProfileRequest, customUserDetails.getUsername()));
	}

	@PostMapping("/edit")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Edit configured Maazoun Profile. Requires ADMIN Access")
	public ResponseEntity<String> editApplicant(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestPart String jsonString,
			@Nullable @RequestPart(name="maazounImge", required=false) MultipartFile maazounImge,
			@Nullable @RequestPart(name="exeptionAtt", required=false) MultipartFile exeptionAtt) {

		AddEditMaazounProfileRequest decryptAddEditMaazounProfileRequest = 
				convertJsonStringToObject(jsonString, AddEditMaazounProfileRequest.class);
		
		decryptAddEditMaazounProfileRequest.setFileMaazounImgAtt(maazounImge);
		decryptAddEditMaazounProfileRequest.setExeptionAtt(exeptionAtt);
		return ResponseEntity
				.ok(maazounProfileManagement.
						editMaazounProfile(decryptAddEditMaazounProfileRequest, customUserDetails.getUsername()));
	}
	
	@PostMapping("/editMaazounBookQuota")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Edit configured Maazoun Profile. Requires ADMIN Access")
	public ResponseEntity<String> editMaazounBookQuota(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString
		 ) {

		AddEditMaazounProfileRequest decryptAddEditMaazounProfileRequest = 
				convertJsonStringToObject(jsonString, AddEditMaazounProfileRequest.class);
		
 		return ResponseEntity
				.ok(maazounProfileManagement.
						editMaazounBookQuota(decryptAddEditMaazounProfileRequest, customUserDetails.getUsername()));
	}
	

	@PostMapping("/addEditMaazounSector")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Add Maazoun Sector. Requires ADMIN Access")
	public ResponseEntity<String> addEditMaazounSector(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		AddEditMaazounSectorRequest decryptAddEditMaazounSectorRequest = 
				convertJsonStringToObject(jsonString, AddEditMaazounSectorRequest.class);
		
		return ResponseEntity
				.ok(maazounProfileManagement.
						addEditMaazounSector(decryptAddEditMaazounSectorRequest));
	}
	
	@PostMapping("/addEditMaazounMaazouniaChurch")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Add Maazoun Sector. Requires ADMIN Access")
	public ResponseEntity<String> addEditMaazounMaazouniaChurch(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		AddEditMaazounMaazouniaChurchRequest decryptAddEditMaazounMaazouniaChurchRequest = 
				convertJsonStringToObject(jsonString, AddEditMaazounMaazouniaChurchRequest.class);
		
		return ResponseEntity
				.ok(maazounProfileManagement.
						addEditMaazounMaazouniaChurch(decryptAddEditMaazounMaazouniaChurchRequest));
	}
	
	@PostMapping("/deleteMaazounMaazouniaChurch")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Delete Maazoun Sector. Requires ADMIN Access")
	public ResponseEntity<String> deleteMaazounMaazouniaChurch(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		DeleteMaazounMaazouniaChurchRequest decryptDeleteMaazounMaazouniaChurchRequest = 
				convertJsonStringToObject(jsonString, DeleteMaazounMaazouniaChurchRequest.class);
		
		return ResponseEntity
				.ok(maazounProfileManagement.
						deleteMaazounMaazouniaChurch(decryptDeleteMaazounMaazouniaChurchRequest));
	}
	
	@PostMapping("/maazounById")
	@ApiOperation(value = "Returns Maazoun Profile by Id. Requires ADMIN Access")
	public ResponseEntity<String> getMaazounById(
			@Valid @RequestBody String jsonString) {

		MaazounByIDRequest decryptMaazounByIDRequest = 
				convertJsonStringToObject(jsonString, MaazounByIDRequest.class);

		return ResponseEntity.ok(maazounProfileManagement.findById(decryptMaazounByIDRequest));
	}

	@PostMapping("/maazounByNationalId")
	@ApiOperation(value = "Returns Maazoun By National Id. Requires ADMIN Access")
	public ResponseEntity<String> getMaazounByNationalId(
			@Valid @RequestBody String jsonString) {

		MaazounByIDRequest decryptMaazounByIDRequest = 
				convertJsonStringToObject(jsonString, MaazounByIDRequest.class);

		return ResponseEntity.ok(maazounProfileManagement.findByNationalId(decryptMaazounByIDRequest));
	}
	
	@PostMapping("/maazounByCardNumber")
	@ApiOperation(value = "Returns Maazoun By Card Number. Requires ADMIN Access")
	public ResponseEntity<String> getMaazounByCardNumber(
			@Valid @RequestBody String jsonString) {

		MaazounByIDRequest decryptMaazounByIDRequest = 
				convertJsonStringToObject(jsonString, MaazounByIDRequest.class);

		return ResponseEntity.ok(maazounProfileManagement.findByCardNumber(decryptMaazounByIDRequest));
	}
	
	@PostMapping("/maazounByMobileNumber")
	@ApiOperation(value = "Returns Maazoun By Card Number. Requires ADMIN Access")
	public ResponseEntity<String> getMaazounByMobileNumber(
			@Valid @RequestBody String jsonString) {

		MaazounByIDRequest decryptMaazounByIDRequest = 
				convertJsonStringToObject(jsonString, MaazounByIDRequest.class);

		return ResponseEntity.ok(maazounProfileManagement.findByMobileNumber(decryptMaazounByIDRequest));
	}
	
	@PostMapping("/maazouniaChurchsByMaazounId")
	@ApiOperation(value = "Returns Maazoun By Card Number. Requires ADMIN Access")
	public ResponseEntity<List<String>> getMaazouniaChurchsByMaazounId(
			@Valid @RequestBody String jsonString) {

		MaazounByIDRequest decryptMaazounByIDRequest = 
				convertJsonStringToObject(jsonString, MaazounByIDRequest.class);

		return ResponseEntity.ok(maazounProfileManagement.getMaazouniaChurchsByMaazounId(decryptMaazounByIDRequest));
	}
	
	@PostMapping("/getMaazounCustomAudit")
	//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All Accumulate Data related with Contract Sectors.")
	public ResponseEntity<PagingDTO> getMaazounCustomAudit(
			@Valid @RequestBody String jsonString) {
		
		MaazounProfileAuditRequest decryptMaazounProfileAuditRequest = 
				convertJsonStringToObject(jsonString, MaazounProfileAuditRequest.class);
		
		return ResponseEntity.ok(maazounProfileManagement.
				getMaazounCustomAudit(decryptMaazounProfileAuditRequest));
	}
	
	@PostMapping("/editCloseCustody")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Edit configured Maazoun Profile. Requires ADMIN Access")
	public ResponseEntity<String> editCloseCustody(
			@Valid @RequestBody String jsonString) {

		EditMaazounCloseCustody decryptEditMaazounCloseCustodyRequest = 
				convertJsonStringToObject(jsonString, EditMaazounCloseCustody.class);
 		return ResponseEntity
				.ok(maazounProfileManagement.
						closeMaazounProfileCustody(decryptEditMaazounCloseCustodyRequest));
	}
	
	@PostMapping("/maazounSearch")
	@ApiOperation(value = "Returns Maazoun By National Id. Requires ADMIN Access")
	public ResponseEntity< List<String>> findByNationalIdOrNameOrMobile(
			@Valid @RequestBody String jsonString) {

		SearchmaazounProfileRequest decSearchmaazounProfileRequest = 
				convertJsonStringToObject(jsonString, SearchmaazounProfileRequest.class);

		return ResponseEntity.ok(maazounProfileManagement.findByNationalIdOrNameOrMobile(decSearchmaazounProfileRequest));
	}
	
	
	
}

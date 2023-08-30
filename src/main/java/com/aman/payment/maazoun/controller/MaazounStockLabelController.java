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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aman.payment.annotation.CurrentUser;
import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.maazoun.management.MaazounBookStockLabelManagement;
import com.aman.payment.maazoun.management.MaazounJsonObjectFactoryImpl;
import com.aman.payment.maazoun.model.dto.PagingDTO;
import com.aman.payment.maazoun.model.payload.EditStockLabelRequest;
import com.aman.payment.maazoun.model.payload.EditStockLabelStatusRequest;
import com.aman.payment.maazoun.model.payload.SearchStockLabelRequest;
import com.aman.payment.maazoun.model.payload.StockLabelRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/maazoun/stockLabel")
@Api(value = "Maazoun Book Stock Label Rest API", description = "Defines endpoints for the Maazoun Stock Label. It's secured by default")
public class MaazounStockLabelController extends MaazounJsonObjectFactoryImpl {

	final static Logger logger = Logger.getLogger("maazoun");

	private final MaazounBookStockLabelManagement maazounBookStockLabelManagement;

	@Autowired
	public MaazounStockLabelController(MaazounBookStockLabelManagement maazounBookStockLabelManagement) {
		this.maazounBookStockLabelManagement = maazounBookStockLabelManagement;
	}


	@PostMapping("/generateSerialNumber")
//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured stock labels. Requires ADMIN Access")
	public ResponseEntity<String> generateSerialNumber(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {
		
		StockLabelRequest decryptStockLabelRequest = 
				convertJsonStringToObject(jsonString, StockLabelRequest.class);
		
		return ResponseEntity.ok(maazounBookStockLabelManagement.generateSerialNumber(customUserDetails, decryptStockLabelRequest));
	}

	@PostMapping("/editSerialNumberStatus")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Update the list of configured stock labels Status to be printed. Requires ADMIN Access")
	public ResponseEntity<String> editSerialNumberStatus(@CurrentUser  CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {
		
		EditStockLabelStatusRequest decryptEditStockLabelStatusRequest = 
				convertJsonStringToObject(jsonString, EditStockLabelStatusRequest.class);
		
		return ResponseEntity.ok(maazounBookStockLabelManagement.editSerialNumberStatus(decryptEditStockLabelStatusRequest));
	}
	
	@PostMapping("/searchStockLabel")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured stock labels. Requires ADMIN Access")
	public ResponseEntity<PagingDTO> searchStockLabel(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {
		
		SearchStockLabelRequest decryptSearchStockLabelRequest = 
				convertJsonStringToObject(jsonString, SearchStockLabelRequest.class);
		
		return ResponseEntity.ok(maazounBookStockLabelManagement.searchStockLabel(decryptSearchStockLabelRequest, customUserDetails));
	}
	

	@PostMapping("/stockLabelByStatus")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured stock labels. Requires ADMIN Access")
	public ResponseEntity<String> stockLabelByStatus(@Valid @RequestBody String jsonString,@CurrentUser CustomUserDetails customUserDetails) {
		
		SearchStockLabelRequest decryptSearchStockLabelRequest = 
				convertJsonStringToObject(jsonString, SearchStockLabelRequest.class);
		
		return ResponseEntity.ok(maazounBookStockLabelManagement.
				findByStatusFkByLocations(decryptSearchStockLabelRequest.getStatus(), customUserDetails));
	}
	
	@PostMapping("/editStockLabelStatus")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Update the list of configured stock labels Status to be printed. Requires ADMIN Access")
	public ResponseEntity<String> editStockLabelStatus(@CurrentUser  CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {
		
		EditStockLabelRequest decryptEditStockLabelStatusRequest = 
				convertJsonStringToObject(jsonString, EditStockLabelRequest.class);
		
		return ResponseEntity.ok(maazounBookStockLabelManagement.editStockLabelStatus(decryptEditStockLabelStatusRequest));
	}
}

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
package com.aman.payment.auth.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aman.payment.annotation.CurrentUser;
import com.aman.payment.auth.management.LookupManagement;
import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.auth.model.dto.CityDTO;
import com.aman.payment.auth.model.dto.LocationDTO;
import com.aman.payment.auth.model.dto.LocationPagingDTO;
import com.aman.payment.auth.model.dto.MaazouniaChurchDTO;
import com.aman.payment.auth.model.dto.MaazouniaChurchsPagingDTO;
import com.aman.payment.auth.model.dto.MenuDTO;
import com.aman.payment.auth.model.dto.MerchantDTO;
import com.aman.payment.auth.model.dto.MerchantPagingDTO;
import com.aman.payment.auth.model.dto.PosDTO;
import com.aman.payment.auth.model.dto.PosPagingDTO;
import com.aman.payment.auth.model.dto.RoleDTO;
import com.aman.payment.auth.model.dto.SectorDTO;
import com.aman.payment.auth.model.dto.SectorPagingDTO;
import com.aman.payment.auth.model.dto.ServiceDTO;
import com.aman.payment.auth.model.dto.SettingDTO;
import com.aman.payment.auth.model.dto.SettingPagingDTO;
import com.aman.payment.auth.model.dto.SubServiceDTO;
import com.aman.payment.auth.model.payload.AddEditLocationRequest;
import com.aman.payment.auth.model.payload.AddEditMaazouniaChurchRequest;
import com.aman.payment.auth.model.payload.AddEditMerchantRequest;
import com.aman.payment.auth.model.payload.AddEditPosRequest;
import com.aman.payment.auth.model.payload.AddEditRoleRequest;
import com.aman.payment.auth.model.payload.AddEditSectorRequest;
import com.aman.payment.auth.model.payload.AddEditServiceRequest;
import com.aman.payment.auth.model.payload.AddEditSettingRequest;
import com.aman.payment.auth.model.payload.AddEditSubServiceQuotaRequest;
import com.aman.payment.auth.model.payload.MerchantByIdRequest;
import com.aman.payment.auth.model.payload.PagingRequest;
import com.aman.payment.auth.model.payload.PagingSearchRequest;
import com.aman.payment.auth.model.payload.PosByIDRequest;
import com.aman.payment.auth.model.payload.PosByLocationRequest;
import com.aman.payment.auth.model.payload.PosByUserRequest;
import com.aman.payment.auth.model.payload.ServiceMenuRequest;
import com.aman.payment.auth.model.payload.SubServiceByIDRequest;
import com.aman.payment.auth.model.payload.SubServiceByServiceIDRequest;
import com.aman.payment.auth.model.payload.SupplyOrderReferenceNumberRequest;
import com.aman.payment.auth.service.CryptoMngrAuthService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/lookup")
@Api(value = "Lookups Rest API", description = "Defines endpoints for the logged in lookups. It's secured by default")

public class LookupController {

	private static final Logger logger = Logger.getLogger(LookupController.class);

	private final LookupManagement lookupManagement;
	private CryptoMngrAuthService cryptoMngrAuthService;

	@Autowired
	public LookupController(LookupManagement lookupManagement,
			CryptoMngrAuthService cryptoMngrAuthService) {
		this.lookupManagement = lookupManagement;
		this.cryptoMngrAuthService = cryptoMngrAuthService;
	}

	/**
	 * Returns all services and sub-service list in the system. Requires Admin access
	 * ==========================================================================================
	 */
	@GetMapping("/services")
//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured service. Requires ADMIN Access")
	public ResponseEntity<Set<ServiceDTO>> getAllService() {
		
		Set<ServiceDTO> eServiceDTO = new HashSet<ServiceDTO>();
		lookupManagement.getAllService().stream().forEach(s -> {
			eServiceDTO.add(s.encrypt(cryptoMngrAuthService));
		});
		
		return ResponseEntity.ok(eServiceDTO);
	}
	
	@GetMapping("/subservices")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured service. Requires ADMIN Access")
	public ResponseEntity<Set<ServiceDTO>> getsubAllService() {
		
		Set<ServiceDTO> eServiceDTO = new HashSet<ServiceDTO>();
		lookupManagement.getAllSubService().stream().forEach(s -> {
			eServiceDTO.add(s.encrypt(cryptoMngrAuthService));
		});
		
		return ResponseEntity.ok(eServiceDTO);
	}
	
	@GetMapping("/mainServices")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured service. Requires ADMIN Access")
	public ResponseEntity<Set<ServiceDTO>> getAllMainServices() {
		
		Set<ServiceDTO> eServiceDTO = new HashSet<ServiceDTO>();
		lookupManagement.getAllMainServices().stream().forEach(s -> {
			eServiceDTO.add(s.encrypt(cryptoMngrAuthService));
		});
		
		return ResponseEntity.ok(eServiceDTO);
	}

	@PostMapping("/subServicesByParent")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured sub service by parent id. Requires ADMIN Access")
	public ResponseEntity<Set<ServiceDTO>> getSubServicesByParent(
			@Valid @RequestBody SubServiceByServiceIDRequest subServiceByServiceIDRequest) {
		
		SubServiceByServiceIDRequest decryptSubServiceByServiceIDRequest = 
				subServiceByServiceIDRequest.decrypt(cryptoMngrAuthService);
		
		Set<ServiceDTO> eServiceDTO = new HashSet<ServiceDTO>();
		lookupManagement.getSubServicesByParent(decryptSubServiceByServiceIDRequest).stream().forEach(s -> {
			eServiceDTO.add(s.encrypt(cryptoMngrAuthService));
		});
		
		return ResponseEntity.ok(eServiceDTO);
	}

	@PostMapping("/service/add")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Add new service. Requires ADMIN Access")
	public ResponseEntity<ServiceDTO> addService(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody AddEditServiceRequest addEditServiceRequest) {
		AddEditServiceRequest decryptAddEditServiceRequestt = 
				addEditServiceRequest.decrypt(cryptoMngrAuthService);
		
		return ResponseEntity.ok(lookupManagement.addService(customUserDetails.getUsername(),
				decryptAddEditServiceRequestt).encrypt(cryptoMngrAuthService));
	}

	@PostMapping("/service/edit")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Edit configured service. Requires ADMIN Access")
	public ResponseEntity<ServiceDTO> editService(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody AddEditServiceRequest addEditServiceRequest) {

		AddEditServiceRequest decryptAddEditServiceRequestt = 
				addEditServiceRequest.decrypt(cryptoMngrAuthService);
		
		return ResponseEntity.ok(lookupManagement.editService(customUserDetails.getUsername(), 
				decryptAddEditServiceRequestt).encrypt(cryptoMngrAuthService));
	}
	
	@PostMapping("/subServiceById")
	@ApiOperation(value = "Returns subService by Id. Requires ADMIN Access")
	public ResponseEntity<SubServiceDTO> getsubServiceById(
			@Valid @RequestBody SubServiceByIDRequest subServiceByIDRequest) {
		
		SubServiceByIDRequest decryptSubServiceByIDRequest = 
				subServiceByIDRequest.decrypt(cryptoMngrAuthService);
		
		return ResponseEntity.ok(lookupManagement.getSubServiceById(decryptSubServiceByIDRequest).
				encrypt(cryptoMngrAuthService));
	}

	/**
	 * Returns all merchant list in the system. Requires Admin access
	 * ===========================================================================================
	 */
	@PostMapping("/merchants")
//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured Merchant. Requires ADMIN Access")
	public ResponseEntity<MerchantPagingDTO> getAllMerchants(@Valid @RequestBody PagingRequest pagingRequest) {
		
		PagingRequest decryptPagingRequest = 
				pagingRequest.decrypt(cryptoMngrAuthService);
		
		MerchantPagingDTO merchantPagingDTO = lookupManagement.getAllMerchant(decryptPagingRequest);
		
		List<MerchantDTO> eMerchantDTO = new ArrayList<MerchantDTO>();
		merchantPagingDTO.getTransactions().stream().forEach(s -> {
			eMerchantDTO.add(s.encrypt(cryptoMngrAuthService));
		});
		merchantPagingDTO.setTransactions(eMerchantDTO);
		
		return ResponseEntity.ok(merchantPagingDTO);
	}

	@PostMapping("/merchant/add")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Add new Merchant. Requires ADMIN Access")
	public ResponseEntity<MerchantDTO> addMerchant(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody AddEditMerchantRequest addEditMerchantRequest) {
		
		AddEditMerchantRequest decryptAddEditMerchantRequest = 
				addEditMerchantRequest.decrypt(cryptoMngrAuthService);
		
		return ResponseEntity
				.ok(lookupManagement.addMerchant(
						customUserDetails.getUsername(), decryptAddEditMerchantRequest).
						encrypt(cryptoMngrAuthService));
	}

	@PostMapping("/merchant/edit")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Edit configured Merchant. Requires ADMIN Access")
	public ResponseEntity<MerchantDTO> editMerchant(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody AddEditMerchantRequest addEditMerchantRequest) {
		
		AddEditMerchantRequest decryptAddEditMerchantRequest = 
				addEditMerchantRequest.decrypt(cryptoMngrAuthService);
		
		return ResponseEntity
				.ok(lookupManagement.editMerchant(customUserDetails.getUsername(), 
						decryptAddEditMerchantRequest).encrypt(cryptoMngrAuthService));
	}

	@PostMapping("/merchant")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns of configured merchant by id. Requires ADMIN Access")
	public ResponseEntity<MerchantDTO> getMerchant(@Valid @RequestBody MerchantByIdRequest merchantByIdRequest) {

		MerchantByIdRequest decryptMerchantByIdRequest = 
				merchantByIdRequest.decrypt(cryptoMngrAuthService);
		
		return ResponseEntity.ok(lookupManagement.getMerchant(decryptMerchantByIdRequest)
				.encrypt(cryptoMngrAuthService));
	}
	/**
	 * Returns all merchant list in the system. Requires Admin access
	 * ===========================================================================================
	 */
	@PostMapping("/branches")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured Branches. Requires ADMIN Access")
	public ResponseEntity<LocationPagingDTO> getAllLocations(@Valid @RequestBody PagingRequest pagingRequest) {
		
		PagingRequest decryptPagingRequest = 
				pagingRequest.decrypt(cryptoMngrAuthService);
		
		LocationPagingDTO locationPagingDTO = lookupManagement.getAllLocations(decryptPagingRequest);
		
		List<LocationDTO> eLocationDTO = new ArrayList<LocationDTO>();
		locationPagingDTO.getTransactions().stream().forEach(s -> {
			eLocationDTO.add(s.encrypt(cryptoMngrAuthService));
		});
		locationPagingDTO.setTransactions(eLocationDTO);
		
		return ResponseEntity.ok(locationPagingDTO);
	}
	
	@PostMapping("/lookForBranch")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured pos info. Requires ADMIN Access")
	public ResponseEntity<LocationPagingDTO> lookForBranch(@Valid @RequestBody PagingSearchRequest pagingSearchPosRequest) {
		
		PagingSearchRequest decryptPagingSearchPosrRequest = pagingSearchPosRequest.decrypt(cryptoMngrAuthService);
		
		LocationPagingDTO locationPagingDTO = lookupManagement.lookforLocation(decryptPagingSearchPosrRequest);
		
		List<LocationDTO> eLocationDTO = new ArrayList<LocationDTO>();
		locationPagingDTO.getTransactions().stream().forEach(s -> {
			eLocationDTO.add(s.encrypt(cryptoMngrAuthService));
		});
		locationPagingDTO.setTransactions(eLocationDTO);
		
		return ResponseEntity.ok(locationPagingDTO);
	}
	
	
	
	@GetMapping("/branchesList")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured Branches. Requires ADMIN Access without pagganation")
	public ResponseEntity<List<LocationDTO>> getbranchesList() {
		 
		 
		List<LocationDTO> list = lookupManagement.getAllLocations();
		
		List<LocationDTO> eLocationDTO = new ArrayList<LocationDTO>();
		list.stream().forEach(s -> {
			eLocationDTO.add(s.encrypt(cryptoMngrAuthService));
		});
		 
		return ResponseEntity.ok(eLocationDTO);
	}
	
	@PostMapping("/branch/add")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Add new Branch. Requires ADMIN Access")
	public ResponseEntity<LocationDTO> addLocation(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody AddEditLocationRequest addEditLocationRequest) {

		AddEditLocationRequest decryptAddEditLocationRequest = 
				addEditLocationRequest.decrypt(cryptoMngrAuthService);
		
		return ResponseEntity.ok(lookupManagement.addLocation( 
				customUserDetails.getUsername(), decryptAddEditLocationRequest).
				encrypt(cryptoMngrAuthService));
	}

	@PostMapping("/branch/edit")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Edit configured Branch. Requires ADMIN Access")
	public ResponseEntity<LocationDTO> editLocation(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody AddEditLocationRequest addEditLocationRequest) {

		AddEditLocationRequest decryptAddEditLocationRequest = 
				addEditLocationRequest.decrypt(cryptoMngrAuthService);
		
		return ResponseEntity.ok(lookupManagement.editLocation(customUserDetails.getUsername(), 
				decryptAddEditLocationRequest).encrypt(cryptoMngrAuthService));
	}

	/**
	 * Returns all POS list in the system. Requires Admin access
	 * ========================================================================================
	 */
	@PostMapping("/poslist")
//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured pos. Requires ADMIN Access")
	public ResponseEntity<PosPagingDTO> getAllPos(@Valid @RequestBody PagingRequest pagingRequest) {
		
		PagingRequest decryptPagingRequest = 
				pagingRequest.decrypt(cryptoMngrAuthService);
		
		PosPagingDTO posPagingDTO = lookupManagement.getAllPos(decryptPagingRequest);
		
		return ResponseEntity.ok(posPagingDTO);
	}
	
	
	
	@PostMapping("/lookforPos")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured pos info. Requires ADMIN Access")
	public ResponseEntity<PosPagingDTO> lookforPos(@Valid @RequestBody PagingSearchRequest pagingSearchPosRequest) {
		
		PagingSearchRequest decryptPagingSearchPosrRequest = 
				pagingSearchPosRequest.decrypt(cryptoMngrAuthService);
		
		PosPagingDTO posPagingDTO = lookupManagement.lookforPos(decryptPagingSearchPosrRequest);
		
		return ResponseEntity.ok(posPagingDTO);
	}
	

	@PostMapping("/pos/add")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Add new POS. Requires ADMIN Access")
	public ResponseEntity<String> addPos(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody AddEditPosRequest addEditPosRequest) {

		AddEditPosRequest decryptAddEditPosRequest = 
				addEditPosRequest.decrypt(cryptoMngrAuthService);
		
		return ResponseEntity.ok(cryptoMngrAuthService.encrypt(lookupManagement.addPos( 
				customUserDetails.getUsername(), decryptAddEditPosRequest).toString()));
	}

	@PostMapping("/pos/edit")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Edit configured POS. Requires ADMIN Access")
	public ResponseEntity<String> editPos(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody AddEditPosRequest addEditPosRequest) {

		AddEditPosRequest decryptAddEditPosRequest = 
				addEditPosRequest.decrypt(cryptoMngrAuthService);
		
		return ResponseEntity.ok(cryptoMngrAuthService.encrypt(lookupManagement.editPos( 
				customUserDetails.getUsername(), decryptAddEditPosRequest).toString()));
	}

	@PostMapping("/sectorLocation")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured pos by location. Requires ADMIN Access")
	public ResponseEntity<Set<SectorDTO>> getAllPosByLocation(@Valid @RequestBody PosByLocationRequest posByLocationRequest) {

		PosByLocationRequest decryptPosByLocationRequest = 
				posByLocationRequest.decrypt(cryptoMngrAuthService);
		
		Set<SectorDTO> eSectorDTO = new HashSet<SectorDTO>();
		lookupManagement.getAllSectorByLocation(decryptPosByLocationRequest).stream().forEach(s -> {
			eSectorDTO.add(s.encrypt(cryptoMngrAuthService));
		});
		
		return ResponseEntity.ok(eSectorDTO);
	}
	
	@PostMapping("/posUser")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured pos by user. Requires ADMIN Access")
	public ResponseEntity<Set<String>> getAllPosByUser(@Valid @RequestBody PosByUserRequest posByUserRequest) {

		PosByUserRequest decryptPosByUserRequest = 
				posByUserRequest.decrypt(cryptoMngrAuthService);
		
		Set<String> ePosDTO = new HashSet<String>();
		lookupManagement.getAllPosByUser(decryptPosByUserRequest).stream().forEach(s -> {
			ePosDTO.add(cryptoMngrAuthService.encrypt(s.toString()));
		});
		
		return ResponseEntity.ok(ePosDTO);
	}

	@GetMapping("/poslistbyUser")
	public ResponseEntity<Set<String>> getAllPoslistbyUser(@CurrentUser CustomUserDetails userDetails) {
		PosByUserRequest posByUserRequest = new PosByUserRequest();
		posByUserRequest.setUserId(userDetails.getId()+"");
		
		Set<String> ePosDTO = new HashSet<String>();
		lookupManagement.getAllPosByUser(posByUserRequest).stream().forEach(s -> {
			ePosDTO.add(cryptoMngrAuthService.encrypt(s.toString()));
		});
		
		return ResponseEntity.ok(ePosDTO);
	}
	
	@GetMapping("/searchPOS")
	@ApiOperation(value = "Returns the list of configured pos by user. Requires ADMIN Access")
	public ResponseEntity<List<String>> searchPOS(@Valid @RequestParam String keyword) {

//		keyword = cryptoMngrAuthService.decrypt(keyword);		
		List<String> ePosDTO = new ArrayList<String>();
		List<PosDTO> poses = lookupManagement.searchPOS(keyword);
		if(poses != null) {
			poses.stream().forEach(s -> {
				ePosDTO.add(cryptoMngrAuthService.encrypt(s.toString()));
		});
		}
		return ResponseEntity.ok(ePosDTO);
	}

	@PostMapping("/posById")
	@ApiOperation(value = "Returns pos by Id. Requires ADMIN Access")
	public ResponseEntity<String> getPosById(@Valid @RequestBody PosByIDRequest posByIDRequest) {
		
		PosByIDRequest decryptPosByIDRequest = 
				posByIDRequest.decrypt(cryptoMngrAuthService);
		
		return ResponseEntity.ok(cryptoMngrAuthService.encrypt(lookupManagement.
				getPosByPosId(decryptPosByIDRequest).toString()));
	}

	
	@PostMapping("/lightposById")
	@ApiOperation(value = "Returns pos by Id. Requires ADMIN Access")
	public ResponseEntity<String> getLightPosById(@Valid @RequestBody PosByIDRequest posByIDRequest) {
		
		PosByIDRequest decryptPosByIDRequest = 
				posByIDRequest.decrypt(cryptoMngrAuthService);
		
		return ResponseEntity.ok(cryptoMngrAuthService.encrypt(lookupManagement.
				getLightPosByPosId(decryptPosByIDRequest).toString()));
	}

	
	
	
	/**
	 * Returns all Role list in the system. Requires Admin access
	 * =========================================================================================
	 */
	@GetMapping("/rolelist")
//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured roles. Requires ADMIN Access")
	public ResponseEntity<Set<RoleDTO>> getAllRoles() {
		
		Set<RoleDTO> eRoleDTO = new HashSet<RoleDTO>();
		lookupManagement.getAllRoles().stream().forEach(s -> {
			eRoleDTO.add(s.encrypt(cryptoMngrAuthService));
		});
		
		return ResponseEntity.ok(eRoleDTO);
	}

	@PostMapping("/role/add")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Add new Role. Requires ADMIN Access")
	public ResponseEntity<RoleDTO> addRole(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody AddEditRoleRequest addEditRoleRequest) {
		
		AddEditRoleRequest decryptAddEditRoleRequest = 
				addEditRoleRequest.decrypt(cryptoMngrAuthService);
		
		return ResponseEntity
				.ok(lookupManagement.
						addRole(customUserDetails.getUsername(), decryptAddEditRoleRequest).
						encrypt(cryptoMngrAuthService));
	}

	@PostMapping("/role/edit")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Edit configured Role. Requires ADMIN Access")
	public ResponseEntity<RoleDTO> editRole(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody AddEditRoleRequest addEditRoleRequest) {
		
		AddEditRoleRequest decryptAddEditRoleRequest = 
				addEditRoleRequest.decrypt(cryptoMngrAuthService);
		
		return ResponseEntity
				.ok(lookupManagement.
						editRole(customUserDetails.getUsername(), decryptAddEditRoleRequest).
						encrypt(cryptoMngrAuthService));
	}

	/**
	 * Returns all Menu list in the system. Requires Admin access
	 * =========================================================================================
	 */
	@PostMapping("/menus")
//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured menus. Requires ADMIN Access BY Service")
	public ResponseEntity<Set<MenuDTO>> getAllMenus(@Valid @RequestBody ServiceMenuRequest serviceMenuRequest) {
		
		ServiceMenuRequest decryptServiceMenuRequest = 
				serviceMenuRequest.decrypt(cryptoMngrAuthService);
		
		Set<MenuDTO> eMenuDTO = new HashSet<MenuDTO>();
		lookupManagement.getAllMenus(decryptServiceMenuRequest).stream().forEach(s -> {
			eMenuDTO.add(s.encrypt(cryptoMngrAuthService));
		});
		
		return ResponseEntity.ok(eMenuDTO);
	}
	
	@GetMapping("/menusList")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured menus. Requires ADMIN Access ; Return All Menus For All Services")
	public ResponseEntity<Set<MenuDTO>> getMenus() {
		
		Set<MenuDTO> eMenuDTO = new HashSet<MenuDTO>();
		lookupManagement.getMenus().stream().forEach(s -> {
			eMenuDTO.add(s.encrypt(cryptoMngrAuthService));
		});
		
		return ResponseEntity.ok(eMenuDTO);
	}
	
	@PostMapping("/authMenus")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured menus related with service and role. Requires ADMIN Access BY Service")
	public ResponseEntity<Set<MenuDTO>> getAuthMenus(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody ServiceMenuRequest serviceMenuRequest) {
		
		ServiceMenuRequest decryptServiceMenuRequest = 
				serviceMenuRequest.decrypt(cryptoMngrAuthService);
		
		Set<MenuDTO> eMenuDTO = new HashSet<MenuDTO>();
		lookupManagement.getAuthMenus(decryptServiceMenuRequest, customUserDetails.getRoleFk()).stream().forEach(s -> {
			eMenuDTO.add(s.encrypt(cryptoMngrAuthService));
		});
		
		return ResponseEntity.ok(eMenuDTO);
	}
	
	/**
	 * Returns all Cities list in the system. Requires Admin access
	 * =========================================================================================
	 */
	@GetMapping("/cities")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured City. Requires ADMIN Access")
	public ResponseEntity<Set<CityDTO>> getAllCities() {
		logger.info("Inside secured resource with admin");
		
		Set<CityDTO> eCityDTO = new HashSet<CityDTO>();
		lookupManagement.getAllCities().stream().forEach(s -> {
			eCityDTO.add(s.encrypt(cryptoMngrAuthService));
		});
		
		return ResponseEntity.ok(eCityDTO);
	}
	
	/**
	 * Returns all merchant list in the system. Requires Admin access
	 * ===========================================================================================
	 */
	@PostMapping("/sectors")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured Sectore. Requires ADMIN Access")
	public ResponseEntity<SectorPagingDTO> getAllSector(@Valid @RequestBody PagingRequest pagingRequest) {
		
		PagingRequest decryptPagingRequest = 
				pagingRequest.decrypt(cryptoMngrAuthService);
		
		SectorPagingDTO sectorPagingDTO = lookupManagement.getAllSector(decryptPagingRequest);
		
		List<SectorDTO> eSectorDTO = new ArrayList<SectorDTO>();
		sectorPagingDTO.getSectors().stream().forEach(s -> {
			eSectorDTO.add(s.encrypt(cryptoMngrAuthService));
		});
		sectorPagingDTO.setSectors(eSectorDTO);
		
		return ResponseEntity.ok(sectorPagingDTO);
	}

	@PostMapping("/addSector")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured Sectore. Requires ADMIN Access")
	public ResponseEntity<SectorDTO> addSectore(@Valid @RequestBody AddEditSectorRequest addEditSectorRequest ) {
		
		AddEditSectorRequest decryptAddEditSectorRequest = addEditSectorRequest.decrypt(cryptoMngrAuthService);
		
		SectorDTO sectorDTO = lookupManagement.addSector(decryptAddEditSectorRequest.getCreatedBy(), decryptAddEditSectorRequest);
		return ResponseEntity.ok(sectorDTO);
	}

	
	@PostMapping("/editSector")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured Sectore. Requires ADMIN Access")
	public ResponseEntity<SectorDTO> editSectore(@Valid @RequestBody AddEditSectorRequest addEditSectorRequest ) {
		
		AddEditSectorRequest decryptAddEditSectorRequest = addEditSectorRequest.decrypt(cryptoMngrAuthService);
		
		SectorDTO sectorDTO = lookupManagement.editSector(decryptAddEditSectorRequest.getCreatedBy(), decryptAddEditSectorRequest);
		return ResponseEntity.ok(sectorDTO);
	}

	@PostMapping("/lookForSector")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured pos info. Requires ADMIN Access")
	public ResponseEntity<SectorPagingDTO> lookForSector(@Valid @RequestBody PagingSearchRequest pagingSearchRequest) {
		
		PagingSearchRequest decryptPagingSearchrRequest = pagingSearchRequest.decrypt(cryptoMngrAuthService);
		
		SectorPagingDTO sectorPagingDTO = lookupManagement.lookforSector(decryptPagingSearchrRequest);
		
		List<SectorDTO> eSectorDTO = new ArrayList<SectorDTO>();
		
		sectorPagingDTO.getSectors().stream().forEach(s -> {
			eSectorDTO.add(s.encrypt(cryptoMngrAuthService));
		});
		sectorPagingDTO.setSectors(eSectorDTO);
		
		return ResponseEntity.ok(sectorPagingDTO);
	}
	
	
	@GetMapping("/sectorsLookup")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured Sectore. Requires ADMIN Access")
	public ResponseEntity<List<SectorDTO>> getallSectoreList() {
		 
		List <SectorDTO> sectors = lookupManagement.findAllSector();
		List<SectorDTO> eSectorDTOs = new ArrayList<SectorDTO>();

		sectors.stream().forEach(s -> {
			eSectorDTOs.add(s.encrypt(cryptoMngrAuthService));
		});
 		
		  
		
		return ResponseEntity.ok(eSectorDTOs);
	}
	/**
	 * Returns all Maazounia Churchs list in the system. Requires Admin access
	 * ===========================================================================================
	 */
	@GetMapping("/maazouniaChurchs")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured maazouniaChurchs. Requires ADMIN Access")
	public ResponseEntity<Set<String>> getAllMaazouniaChurchs() {
		
		Set<String> eMaazouniaChurchDTO = new HashSet<String>();
		lookupManagement.getAllMaazouniaChurchs().stream().forEach(s -> {
			eMaazouniaChurchDTO.add(cryptoMngrAuthService.encrypt(s.toString()));
		});
		
		return ResponseEntity.ok(eMaazouniaChurchDTO);
	}
	
	@PostMapping("/maazouniaChurchsPaging")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured MaazouniaChurchs info. Requires ADMIN Access")
	public ResponseEntity<MaazouniaChurchsPagingDTO> maazouniaChurchsPaging(@Valid @RequestBody PagingSearchRequest pagingSearchRequest) {
		
		PagingSearchRequest decryptPagingSearchrRequest = pagingSearchRequest.decrypt(cryptoMngrAuthService);
		
		MaazouniaChurchsPagingDTO maazouniaChurchsPagingDTO = lookupManagement.maazouniaChurchsPaging(decryptPagingSearchrRequest);
		
		List<MaazouniaChurchDTO> eMaazouniaChurchDTO = new ArrayList<MaazouniaChurchDTO>();
		
		maazouniaChurchsPagingDTO.getMaazouniaChurchs().stream().forEach(s -> {
			eMaazouniaChurchDTO.add(s.encrypt(cryptoMngrAuthService));
		});
		
//		maazouniaChurchsPagingDTO.getMaazouniaChurchs().stream().forEach(s -> {
//			eMaazouniaChurchDTO.add(s.encrypt(cryptoMngrAuthService));
//		});
		maazouniaChurchsPagingDTO.setMaazouniaChurchs(eMaazouniaChurchDTO);
		
		return ResponseEntity.ok(maazouniaChurchsPagingDTO);
	}
	
	@PostMapping("/addMaazouniaChurch")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured addMaazouniaChurch. Requires ADMIN Access")
	public ResponseEntity<MaazouniaChurchDTO> addMaazouniaChurch(@Valid @RequestPart AddEditMaazouniaChurchRequest addEditMaazouniaChurchRequest,
			  @Valid @RequestPart MultipartFile maazouniaAtt) {
		
		AddEditMaazouniaChurchRequest decryptAddEditMaazouniaChurchRequest = addEditMaazouniaChurchRequest.decrypt(cryptoMngrAuthService);
		decryptAddEditMaazouniaChurchRequest.setCreationAttacment(maazouniaAtt);
		MaazouniaChurchDTO maazouniaChurchDTO = lookupManagement.addMaazouniaChurch(
				decryptAddEditMaazouniaChurchRequest.getCreatedBy(), decryptAddEditMaazouniaChurchRequest);
		return ResponseEntity.ok(maazouniaChurchDTO);
	}
	
	@PostMapping("/editMaazouniaChurch")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured editMaazouniaChurch. Requires ADMIN Access")
	public ResponseEntity<MaazouniaChurchDTO> editMaazouniaChurch(@Valid @RequestBody AddEditMaazouniaChurchRequest addEditMaazouniaChurchRequest ) {
		
		AddEditMaazouniaChurchRequest decryptAddEditMaazouniaChurchRequest = addEditMaazouniaChurchRequest.decrypt(cryptoMngrAuthService);
		
		MaazouniaChurchDTO maazouniaChurchDTO = lookupManagement.editMaazouniaChurch(
				decryptAddEditMaazouniaChurchRequest.getCreatedBy(), decryptAddEditMaazouniaChurchRequest);
		return ResponseEntity.ok(maazouniaChurchDTO);
	}
	
	
	@PostMapping("/lookForMaazouniaChurch")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured maazouniaChurchDTO List. Requires ADMIN Access")
	public ResponseEntity<List<MaazouniaChurchDTO>> lookForMaazouniaChurch(@Valid @RequestBody PagingSearchRequest pagingSearchRequest ) {
		
		PagingSearchRequest decryptMaazouniaChurchRequest = pagingSearchRequest.decrypt(cryptoMngrAuthService);
		
		List<MaazouniaChurchDTO> maazouniaChurchDTOList = lookupManagement.lookForMaazounizChruch(decryptMaazouniaChurchRequest);
        List<MaazouniaChurchDTO> list = new ArrayList<MaazouniaChurchDTO>();
		
        maazouniaChurchDTOList.stream().forEach(s -> {
			list.add(s.encrypt(cryptoMngrAuthService));
		});
		
		return ResponseEntity.ok(list);
	}
	
	/**
	 * setting. Requires Admin access
	 * ===========================================================================================
	 */
	
	@PostMapping("/setting")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured Sectore. Requires ADMIN Access")
	public ResponseEntity<SettingPagingDTO> getAllSetting(@Valid @RequestBody PagingRequest pagingRequest) {
		
		PagingRequest decryptPagingRequest = 
				pagingRequest.decrypt(cryptoMngrAuthService);
		
		SettingPagingDTO settingrPagingDTO = lookupManagement.getAllSetting(decryptPagingRequest);
		List<SettingDTO> settingsDTO = new ArrayList<SettingDTO>();
		
		settingrPagingDTO.getSettings().stream().forEach(s -> {
			settingsDTO.add(s.encrypt(cryptoMngrAuthService));
		});
		settingrPagingDTO.setSettings(settingsDTO);
		
		return ResponseEntity.ok(settingrPagingDTO);
	}
	
	@PostMapping("/editSetting")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured editMaazouniaChurch. Requires ADMIN Access")
	public ResponseEntity<SettingDTO> editSetting(@Valid @RequestBody AddEditSettingRequest addEditSettingRequest ) {
		
		AddEditSettingRequest decAddEditSettingRequest  = addEditSettingRequest.decrypt(cryptoMngrAuthService);
		
		SettingDTO  settingDTO = lookupManagement.editSetting(decAddEditSettingRequest);
				 
		return ResponseEntity.ok(settingDTO.encrypt(cryptoMngrAuthService));
	}
	
	/**
	 * Returns all Sub service quota list in the system. Requires Super Admin access
	 * ===========================================================================================
	 */
	@GetMapping("/getAllSubServiceQuota")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured maazouniaChurchs. Requires ADMIN Access")
	public ResponseEntity<Set<String>> getAllSubServiceQuota() {
		
		Set<String> eSubServiceQuota = new HashSet<String>();
		lookupManagement.getAllSubServiceQuota().stream().forEach(s -> {
			eSubServiceQuota.add(cryptoMngrAuthService.encrypt(s.toString()));
		});
		
		return ResponseEntity.ok(eSubServiceQuota);
	}
	
	@PostMapping("/addSubServiceQuota")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "add Sub Service Quota. Requires ADMIN Access")
	public ResponseEntity<String> addSubServiceQuota(@Valid @RequestBody AddEditSubServiceQuotaRequest decryptAddEditSubServiceQuotaRequest) {

		AddEditSubServiceQuotaRequest addEditSubServiceQuotaRequest =
				decryptAddEditSubServiceQuotaRequest.decrypt(cryptoMngrAuthService);
		 
    return ResponseEntity.ok(cryptoMngrAuthService.encrypt(
    		lookupManagement.addSubServiceQuota(addEditSubServiceQuotaRequest).toString()));
	}
	
	@PostMapping("/editSubServiceQuota")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "edit Sub Service Quota. Requires ADMIN Access")
	public ResponseEntity<String> editSubServiceQuota(@Valid @RequestBody AddEditSubServiceQuotaRequest decryptAddEditSubServiceQuotaRequest) {
		
		AddEditSubServiceQuotaRequest addEditSubServiceQuotaRequest =
				decryptAddEditSubServiceQuotaRequest.decrypt(cryptoMngrAuthService);
		 
    return ResponseEntity.ok(cryptoMngrAuthService.encrypt(
    		lookupManagement.editSubServiceQuota(addEditSubServiceQuotaRequest).toString()));
	}
	
	@PostMapping("/getSupplyReferenceNumber")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "edit Sub Service Quota. Requires ADMIN Access")
	public ResponseEntity<String> getSupplyReferenceNumber(@Valid @RequestBody SupplyOrderReferenceNumberRequest obj) {
		
		SupplyOrderReferenceNumberRequest supplyOrderReferenceNumberRequest =
				obj.decrypt(cryptoMngrAuthService);
		 
    return ResponseEntity.ok(cryptoMngrAuthService.encrypt(
    		lookupManagement.getSupplyReferenceNumber(supplyOrderReferenceNumberRequest)));
	}
}

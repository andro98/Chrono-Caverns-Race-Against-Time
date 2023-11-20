package com.aman.payment.auth.management;

import java.util.List;
import java.util.Set;

import com.aman.payment.auth.model.Role;
import com.aman.payment.auth.model.dto.*;
import com.aman.payment.auth.model.payload.*;

public interface LookupManagement {
	
	public String getCode(String key);
	/*
	 * ====================================Service CRUD=============================================
	 */
	public List<ServiceDTO> getAllService();
	public List<ServiceDTO> getAllSubService();
	
	public List<ServiceDTO> getAllMainServices();
	
	public List<ServiceDTO> getSubServicesByParent(SubServiceByServiceIDRequest subServiceByServiceIDRequest);

	List<SubServicePriceTierDTO> getAllSubServicesPriceTier();
	
	public ServiceDTO addService(String username, AddEditServiceRequest addEditServiceRequest);
	
	public ServiceDTO editService(String username, AddEditServiceRequest addEditServiceRequest);
	
	public SubServiceDTO getSubServiceById(SubServiceByIDRequest subServiceByIDRequest);
	
	public SubServiceDTO getSubServiceById(long id);
	
	public ServiceDTO getParentServiceById(String serviceId);
	
	/*
	 * ====================================Merchant CRUD=============================================
	 */
	public MerchantPagingDTO getAllMerchant(PagingRequest pagingRequest);
	
	public MerchantDTO addMerchant(String createdBy, AddEditMerchantRequest addEditMerchantRequest);
	
	public MerchantDTO editMerchant(String updatedBy, AddEditMerchantRequest addEditMerchantRequest);
	
	public MerchantDTO getMerchant(MerchantByIdRequest merchantByIdRequest);
	
	public MerchantPagingDTO lookforMerchant(PagingSearchRequest pagingSearchPosRequest);

	
	/*
	 * ====================================Location CRUD=============================================
	 */
	public LocationPagingDTO getAllLocations(PagingRequest pagingRequest);
	public List<LocationDTO> getAllLocations();

	
	public LocationDTO getLocationById(String locationId);
	
	public LocationDTO addLocation(String createdBy, AddEditLocationRequest addEditLocationRequest);
	
	public LocationDTO editLocation(String updatedBy, AddEditLocationRequest addEditLocationRequest);
	
	public LocationPagingDTO lookforLocation(PagingSearchRequest pagingSearchRequest);
	/*
	 * ====================================POS CRUD=============================================
	 */
	public PosPagingDTO getAllPos(PagingRequest pagingRequest);
	
	public PosDTO addPos(String createdBy, AddEditPosRequest addEditPosRequest);
	
	public PosDTO editPos(String updatedBy, AddEditPosRequest addEditPosRequest);
	
	public Set<SectorDTO> getAllSectorByLocation(PosByLocationRequest posByLocationRequest);
	
	public Set<PosDTO> getAllPosByUser(PosByUserRequest posByUserRequest);
	
	public List<PosDTO> searchPOS(String keyword);
	
	public PosDTO getPosByPosId(PosByIDRequest posByIDRequest);
	
	public PosWithUserAgentDTO getLightPosByPosId(PosByIDRequest posByIDRequest);
	
	public PosPagingDTO lookforPos(PagingSearchRequest pagingSearchPosRequest);
	 
	/*
	 * ====================================Role CRUD=============================================
	 */
	public List<RoleDTO> getAllRoles();
	
	public RoleDTO addRole(String createdBy, AddEditRoleRequest addEditRoleRequest);
	
	public RoleDTO editRole(String updatedBy, AddEditRoleRequest addEditRoleRequest);
	/*
	 * ====================================Menu CRUD=============================================
	 */
	public Set<MenuDTO> getAllMenus(ServiceMenuRequest serviceMenuRequest);
	
	public List<MenuDTO> getMenus();
	
	public Set<MenuDTO> getAuthMenus(ServiceMenuRequest serviceMenuRequest, Role role);

	/*
	 * ====================================City CRUD=============================================
	 */
	public List<CityDTO> getAllCities();
	/*
	 * ====================================Insurance Number CRUD=============================================
	 */
	public Long getInsuranceNumber(long serviceId, long locationId);
	
	public Long getInsuranceNumber(long bookTypeId, long locationId, long serviceId);
	
	public Long getInsuranceNumberWithoutIncrement(long serviceId, long locationId);
	
	public Long getInsuranceNumberWithoutIncrement(long bookTypeId, long locationId, long serviceId);
	
	public void editInsuranceNumber(long serviceId, long locationId, long incrementNumber);
	
	public void editInsuranceNumber(long serviceId, long locationId, long incrementNumber, long bookTypeId);
	
	/*
	 * ====================================Sector CRUD=============================================
	 */
	public SectorPagingDTO getAllSector(PagingRequest pagingRequest);
	
	public List<SectorDTO> getAllSector();
	
	public List<JwtAuthSectorDTO> getAllAuthSector(long locationId);
	
	public List<SectorDTO> getAllSector(long locationId);
	
	public SectorDTO getSectorById(long id);
	
	public SectorDTO getSectorByName(String name);
	
	public SectorDTO addSector(String createdBy, AddEditSectorRequest addEditSectorRequest);
	
	public SectorDTO editSector(String updatedBy, AddEditSectorRequest addEditSectorRequest);
	 
	public SectorPagingDTO lookforSector(PagingSearchRequest pagingSearchRequest);
	
	public List<SectorDTO> findAllSector();

	public List<MaazouniaChurchDTO> getAllMaazouniaChurchs();
	
	public MaazouniaChurchsPagingDTO maazouniaChurchsPaging(PagingSearchRequest pagingSearchRequest);
	
	public MaazouniaChurchDTO addMaazouniaChurch(String createdBy, AddEditMaazouniaChurchRequest addEditMaazouniaChurchRequest);
	
	public MaazouniaChurchDTO editMaazouniaChurch(String updatedBy, AddEditMaazouniaChurchRequest addEditMaazouniaChurchRequest);

	public List<MaazouniaChurchDTO> lookForMaazounizChruch(PagingSearchRequest pagingSearchRequest);
	
	public String getSupplyReferenceNumber(SupplyOrderReferenceNumberRequest supplyOrderReferenceNumberRequest);
	
	/*
	 * ====================================Setting CRUD=============================================
	 */
	public SettingPagingDTO getAllSetting(PagingRequest pagingRequest);
	
	public SettingDTO editSetting(AddEditSettingRequest setting);
	/*
	 * ====================================Sub Service Quota CRUD=============================================
	 */
	public List<SubServiceQuotaDTO> getAllSubServiceQuota();
	
	public SubServiceQuotaDTO addSubServiceQuota(AddEditSubServiceQuotaRequest addEditSubServiceQuotaRequest);
	
	public SubServiceQuotaDTO editSubServiceQuota(AddEditSubServiceQuotaRequest addEditSubServiceQuotaRequest);


//	List<SubServicePriceTierDTO> getAllSubServicesPriceTier();

	public SubServicePriceTierDTO addSubServiceTier(String createdBy, AddEditSubServiceTierRequest addEditSubServiceTierRequest);
}

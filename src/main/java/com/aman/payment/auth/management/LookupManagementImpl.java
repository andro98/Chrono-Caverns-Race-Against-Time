package com.aman.payment.auth.management;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.aman.payment.auth.model.*;
import com.aman.payment.auth.model.dto.*;
import com.aman.payment.auth.model.payload.*;
import com.aman.payment.auth.service.*;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.aman.payment.auth.mapper.CityMapper;
import com.aman.payment.auth.mapper.LocationMapper;
import com.aman.payment.auth.mapper.MaazouniaChurchMapper;
import com.aman.payment.auth.mapper.MenuMapper;
import com.aman.payment.auth.mapper.MerchantMapper;
import com.aman.payment.auth.mapper.PosMapper;
import com.aman.payment.auth.mapper.RoleMapper;
import com.aman.payment.auth.mapper.SectorMapper;
import com.aman.payment.auth.mapper.ServiceMapper;
import com.aman.payment.auth.mapper.SettingMapper;
import com.aman.payment.auth.mapper.SubServiceQuotaMapper;
import com.aman.payment.auth.service.impl.MenuServiceImpl;
import com.aman.payment.auth.service.impl.RoleServiceImpl;
import com.aman.payment.auth.service.impl.UserService;
import com.aman.payment.core.exception.TransactionException;
import com.aman.payment.core.model.GenerateCode;
import com.aman.payment.core.service.GenerateCodeService;
import com.aman.payment.core.util.UtilCore;
import com.aman.payment.util.StatusConstant;
import com.aman.payment.util.Util;


@Component
public class LookupManagementImpl implements LookupManagement {
    final static Logger logger = Logger.getLogger("lookup");

    private final MenuServiceImpl menuService;
    private final RoleServiceImpl roleService;
    private final ServiceService serviceService;
    private final SubServiceService subServiceService;
    private final SubServiceQuotaService subServiceQuotaService;
    private final SubServicePriceTierService subServicePriceTierService;
    private final PosService posService;
    private final MerchantService merchantService;
    private final LocationService locationService;
    private final CityService cityService;
    private final ServiceMapper serviceMapper;
    private final PosMapper posMapper;
    private final RoleMapper roleMapper;
    private final MenuMapper menuMapper;
    private final MerchantMapper merchantMapper;
    private final LocationMapper locationMapper;
    private final CryptoMngrAuthService cryptoMngrAuthService;
    private final GenerateCodeService generateCodeService;
    private final CityMapper cityMapper;
    private final UserService userService;
    private final InsuranceNumberService insuranceNumberService;
    private final SectorService sectorService;
    private final SectorMapper sectorMapper;
    private final MaazouniaChurchService maazouniaChurchService;
    private final MaazouniaChurchMapper maazouniaChurchMapper;
    private final SettingService settingService;
    private final SettingMapper settingMapper;
    private final SubServiceQuotaMapper subServiceQuotaMapper;

    @Value("${attachment.maazoun.maazouniaChurch}")
    private String maazouniaChurchPathAtt;

    @Autowired
    public LookupManagementImpl(MenuServiceImpl menuService, RoleServiceImpl roleService, ServiceService serviceService,
                                SubServiceService subServiceService, PosService posService, MerchantService merchantService,
                                LocationService locationService, CityService cityService, ServiceMapper serviceMapper, PosMapper posMapper,
                                RoleMapper roleMapper, MenuMapper menuMapper, MerchantMapper merchantMapper, LocationMapper locationMapper,
                                CryptoMngrAuthService cryptoMngrAuthService, GenerateCodeService generateCodeService, CityMapper cityMapper,
                                UserService userService, InsuranceNumberService insuranceNumberService, SectorService sectorService,
                                SectorMapper sectorMapper, MaazouniaChurchService maazouniaChurchService,
                                MaazouniaChurchMapper maazouniaChurchMapper, SettingService settingService,
                                SettingMapper settingMapper, SubServiceQuotaService subServiceQuotaService,
                                SubServiceQuotaMapper subServiceQuotaMapper, SubServicePriceTierService subServicePriceTierService) {

        super();
        this.menuService = menuService;
        this.roleService = roleService;
        this.serviceService = serviceService;
        this.subServiceService = subServiceService;
        this.posService = posService;
        this.merchantService = merchantService;
        this.locationService = locationService;
        this.cityService = cityService;
        this.serviceMapper = serviceMapper;
        this.posMapper = posMapper;
        this.roleMapper = roleMapper;
        this.menuMapper = menuMapper;
        this.merchantMapper = merchantMapper;
        this.locationMapper = locationMapper;
        this.cryptoMngrAuthService = cryptoMngrAuthService;
        this.generateCodeService = generateCodeService;
        this.cityMapper = cityMapper;
        this.userService = userService;
        this.insuranceNumberService = insuranceNumberService;
        this.sectorService = sectorService;
        this.sectorMapper = sectorMapper;
        this.maazouniaChurchService = maazouniaChurchService;
        this.maazouniaChurchMapper = maazouniaChurchMapper;
        this.settingService = settingService;
        this.settingMapper = settingMapper;
        this.subServiceQuotaService = subServiceQuotaService;
        this.subServiceQuotaMapper = subServiceQuotaMapper;
        this.subServicePriceTierService = subServicePriceTierService;
    }

    /*
     * ====================================Service and
     * SubServiceCRUD=============================================
     */
    @Override
    public List<ServiceDTO> getAllService() {
        List<ServiceDTO> services = new ArrayList<ServiceDTO>();
        services.addAll(serviceMapper.subServicesToServiceDTOs(subServiceService.findAll()));
        services.addAll(serviceMapper.servicesToServiceDTOs(serviceService.findAll()));
        return services;
    }

    @Override
    public List<ServiceDTO> getAllSubService() {
        // TODO Auto-generated method stub
        return serviceMapper.subServicesToServiceDTOs(subServiceService.findAll());
    }

    @Override
    public List<ServiceDTO> getAllMainServices() {
        return serviceMapper.servicesToServiceDTOs(serviceService.findAll());
    }

    @Override
    public List<ServiceDTO> getSubServicesByParent(SubServiceByServiceIDRequest subServiceByServiceIDRequest) {

        Service eService = serviceService.findById(Long.valueOf(subServiceByServiceIDRequest.getServiceId())).get();
        return serviceMapper.subServicesToServiceDTOs(subServiceService.getSubServiceByServiceFk(eService));
    }

    @Override
    public List<SubServicePriceTierDTO> getAllSubServicesPriceTier() {
        List<SubServicePriceTier> subServicePriceTier = subServicePriceTierService.findAll();
        return subServicePriceTier.stream().map(s -> serviceMapper.subServicePriceTierToDto(s, getPriceTierCurrentQuota(s))).collect(Collectors.toList());
    }

    private String getPriceTierCurrentQuota(SubServicePriceTier subServicePriceTier) {
        return subServiceQuotaService
                .findBySubServiceFkAndSubServicePriceTierFK(subServicePriceTier.getSubServiceFk(), subServicePriceTier)
                .stream()
                .map(SubServiceQuota::getFees)
                .reduce(0D, Double::sum)
                .toString();
    }

    @Override
    public ServiceDTO getParentServiceById(String serviceId) {
        return serviceMapper.serviceToServiceDTO(serviceService.findById(Long.parseLong(serviceId)).get());
    }

    @Override
    public ServiceDTO addService(String username, AddEditServiceRequest addEditServiceRequest) {

        if (addEditServiceRequest.getServiceType().equals("main")) {
            Service service = new Service();
            service.setCode(getCode(StatusConstant.SER));
            service.setCreatedAt(Date.from(Instant.now()));
            service.setCreatedBy(username);
            service.setDescription(addEditServiceRequest.getDescription());
            service.setName(addEditServiceRequest.getName());
            service.setTax(Long.parseLong(addEditServiceRequest.getTax()));
            service.setMerchantFk(new Merchant(Long.valueOf(addEditServiceRequest.getMerchantId())));
            service.setStatusFk(StatusConstant.STATUS_ACTIVE);

            return serviceMapper.serviceToServiceDTO(serviceService.save(service));

        } else if (addEditServiceRequest.getServiceType().equals("sub")) {

            SubService subService = new SubService();
            subService.setCreatedAt(Date.from(Instant.now()));
            subService.setCreatedBy(username);
            subService.setDescription(addEditServiceRequest.getDescription());
            subService.setFees(Double.valueOf(addEditServiceRequest.getFees()));
            subService.setName(addEditServiceRequest.getName());
            subService.setServiceFk(new Service(Long.valueOf(addEditServiceRequest.getMainServiceId())));
            subService.setStatusFk(StatusConstant.STATUS_ACTIVE);
            subService.setRequiredService(Boolean.valueOf(addEditServiceRequest.getRequiredService()));

            return serviceMapper.subServiceToServiceDTO(subServiceService.save(subService));

        }
        return null;
    }

    @Override
    public ServiceDTO editService(String username, AddEditServiceRequest addEditServiceRequest) {
        if (addEditServiceRequest.getServiceType().equals("main")) {
            Service service = serviceService.findById(Long.valueOf(addEditServiceRequest.getId())).get();
            // service.setId(Long.parseLong(cryptoMngrAuthService.decrypt(serviceId)));
            service.setUpdatedAt(Date.from(Instant.now()));
            service.setUpdatedBy(username);
            service.setDescription(addEditServiceRequest.getDescription());
            service.setName(addEditServiceRequest.getName());
            service.setTax(Long.valueOf(addEditServiceRequest.getTax()));
            service.setMerchantFk(new Merchant(Long.valueOf(addEditServiceRequest.getMerchantId())));

            return serviceMapper.serviceToServiceDTO(serviceService.save(service));

        } else if (addEditServiceRequest.getServiceType().equals("sub")) {

            SubService subService = subServiceService.findById(Long.valueOf(addEditServiceRequest.getId())).get();
            // subService.setId(Long.parseLong(cryptoMngrAuthService.decrypt(serviceId)));
            subService.setUpdatedAt(Date.from(Instant.now()));
            subService.setUpdatedBy(username);
            subService.setDescription(addEditServiceRequest.getDescription());
            subService.setFees(Double.valueOf(addEditServiceRequest.getFees()));
            subService.setName(addEditServiceRequest.getName());
            subService.setServiceFk(new Service(Long.valueOf(addEditServiceRequest.getMainServiceId())));
            subService.setRequiredService(Boolean.valueOf(addEditServiceRequest.getRequiredService()));
//			Set<Location> locationSet = new HashSet<Location>();
//			for(String locationId : locationIds) {
//				locationSet.add(new Location(Long.parseLong(cryptoMngrAuthService.decrypt(locationId))));
//			}
//			subService.setLocations(locationSet);

            return serviceMapper.subServiceToServiceDTO(subServiceService.save(subService));

        }
        return null;
    }

    @Override
    public SubServiceDTO getSubServiceById(SubServiceByIDRequest subServiceByIDRequest) {
        return serviceMapper.subServiceToSubServiceDTO(
                subServiceService.findById(Long.valueOf(subServiceByIDRequest.getSubServiceId())).get());
    }

    @Override
    public SubServiceDTO getSubServiceById(long id) {
        return serviceMapper.subServiceToSubServiceDTO(subServiceService.findById(id).get());
    }

    /*
     * ====================================Merchant
     * CRUD=============================================
     */
    @Override
    public MerchantPagingDTO getAllMerchant(PagingRequest pagingRequest) {

        Pageable pageable = PageRequest.of(Integer.valueOf(pagingRequest.getPageNo()),
                Integer.valueOf(pagingRequest.getPageSize()));

        Page<Merchant> pageResult = merchantService.findAll(pageable);

        MerchantPagingDTO merchantPagingDTO = new MerchantPagingDTO();
        merchantPagingDTO.setCount(pageResult.getTotalElements());
        merchantPagingDTO.setTotalPages(pageResult.getTotalPages());
        merchantPagingDTO.setTransactions(merchantMapper.merchantsToMerchantDTOs(pageResult.getContent()));

        return merchantPagingDTO;

    }

    @Override
    public MerchantDTO addMerchant(String createdBy, AddEditMerchantRequest addEditMerchantRequest) {
        Merchant merchant = new Merchant();
        merchant.setAddress(addEditMerchantRequest.getAddress());
        merchant.setCode(getCode(StatusConstant.MER));
        merchant.setCreatedAt(Date.from(Instant.now()));
        merchant.setCreatedBy(createdBy);
        merchant.setDescription(addEditMerchantRequest.getDescription());
        merchant.setEmail(addEditMerchantRequest.getEmail());
        merchant.setFax(addEditMerchantRequest.getFax());
        merchant.setMobile(addEditMerchantRequest.getMobile());
        merchant.setName(addEditMerchantRequest.getName());
        merchant.setPhone1(addEditMerchantRequest.getPhone1());
        merchant.setPhone2(addEditMerchantRequest.getPhone2());
        merchant.setStatusFk(StatusConstant.STATUS_ACTIVE);

        return merchantMapper.merchantToMerchantDTO(merchantService.save(merchant));
    }

    @Override
    public MerchantDTO editMerchant(String updatedBy, AddEditMerchantRequest addEditMerchantRequest) {
        Merchant merchant = new Merchant();
        merchant.setId(Long.valueOf(addEditMerchantRequest.getId()));
        merchant.setAddress(addEditMerchantRequest.getAddress());
        merchant.setUpdatedAt(Date.from(Instant.now()));
        merchant.setUpdatedBy(updatedBy);
        merchant.setDescription(addEditMerchantRequest.getDescription());
        merchant.setEmail(addEditMerchantRequest.getEmail());
        merchant.setFax(addEditMerchantRequest.getFax());
        merchant.setMobile(addEditMerchantRequest.getMobile());
        merchant.setName(addEditMerchantRequest.getName());
        merchant.setPhone1(addEditMerchantRequest.getPhone1());
        merchant.setPhone2(addEditMerchantRequest.getPhone2());
        merchant.setStatusFk(addEditMerchantRequest.getStatus());

        return merchantMapper.merchantToMerchantDTO(merchantService.save(merchant));
    }

    @Override
    public MerchantDTO getMerchant(MerchantByIdRequest merchantByIdRequest) {
        return merchantMapper.merchantToMerchantDTO(
                merchantService.findById(Long.valueOf(merchantByIdRequest.getMerchantId())).get());
    }

    @Override
    public MerchantPagingDTO lookforMerchant(PagingSearchRequest pagingSearchPosRequest) {
        Pageable pageable = PageRequest.of(Integer.valueOf(pagingSearchPosRequest.getPageNo()),
                Integer.valueOf(pagingSearchPosRequest.getPageSize()));

        Page<Merchant> pageResult = merchantService.lookforMerchant(pagingSearchPosRequest.getSearchBy(), pageable);

        MerchantPagingDTO MerchantPagingDTO = new MerchantPagingDTO();
        MerchantPagingDTO.setCount(pageResult.getTotalElements());
        MerchantPagingDTO.setTotalPages(pageResult.getTotalPages());
        MerchantPagingDTO.setTransactions(merchantMapper.merchantsToMerchantDTOs(pageResult.getContent()));

        return MerchantPagingDTO;
    }

    /*
     * ====================================Location
     * CRUD=============================================
     */

    @Override
    public LocationPagingDTO getAllLocations(PagingRequest pagingRequest) {

        Pageable pageable = PageRequest.of(Integer.valueOf(pagingRequest.getPageNo()),
                Integer.valueOf(pagingRequest.getPageSize()));

        Page<Location> pageResult = locationService.findAll(pageable);

        LocationPagingDTO locationPagingDTO = new LocationPagingDTO();
        locationPagingDTO.setCount(pageResult.getTotalElements());
        locationPagingDTO.setTotalPages(pageResult.getTotalPages());
        locationPagingDTO.setTransactions(locationMapper.locationsToLocationDTOs(pageResult.getContent()));

        return locationPagingDTO;
    }

    public LocationPagingDTO lookforLocation(PagingSearchRequest pagingSearchRequest) {
        Pageable pageable = PageRequest.of(Integer.valueOf(pagingSearchRequest.getPageNo()),
                Integer.valueOf(pagingSearchRequest.getPageSize()));

        Page<Location> pageResult = locationService.lookforLocation(pagingSearchRequest.getSearchBy(), pageable);

        LocationPagingDTO locationPagingDTO = new LocationPagingDTO();
        locationPagingDTO.setCount(pageResult.getTotalElements());
        locationPagingDTO.setTotalPages(pageResult.getTotalPages());
        locationPagingDTO.setTransactions(locationMapper.locationsToLocationDTOs(pageResult.getContent()));

        return locationPagingDTO;
    }

    @Override
    public List<LocationDTO> getAllLocations() {
        // TODO Auto-generated method stub
        List<Location> location = locationService.findAll();
        List<LocationDTO> locations = locationMapper.locationsToLocationDTOs(location);
        return locations;
    }

    @Override
    public LocationDTO getLocationById(String locationId) {
        return locationMapper.locationToLocationDTO(locationService.findById(Long.parseLong(locationId)).get());
    }

    @Override
    public LocationDTO addLocation(String createdBy, AddEditLocationRequest addEditLocationRequest) {
        Location location = new Location();
        location.setCityFk(new City(Long.valueOf(addEditLocationRequest.getCityId())));
        location.setCode(getCode(StatusConstant.LOC));
        location.setCreatedAt(Date.from(Instant.now()));
        location.setCreatedBy(createdBy);
        location.setDescription(addEditLocationRequest.getDescription());
        location.setName(addEditLocationRequest.getName());
        location.setStatusFk(addEditLocationRequest.getStatus());
        location.setSeqValue((long) 0);

        return locationMapper.locationToLocationDTO(locationService.save(location));
    }

    @Override
    public LocationDTO editLocation(String updatedBy, AddEditLocationRequest addEditLocationRequest) {
        Optional<Location> optionalLocation = locationService.findById(Long.valueOf(addEditLocationRequest.getId()));
        Location location = optionalLocation.isPresent() ? optionalLocation.get() : new Location();
        // location.setId(Long.parseLong(cryptoMngrAuthService.decrypt(locationId)));
        location.setCityFk(new City(Long.valueOf(addEditLocationRequest.getCityId())));
        location.setUpdatedAt(Date.from(Instant.now()));
        location.setUpdatedBy(updatedBy);
        location.setName(addEditLocationRequest.getName());
        location.setStatusFk(addEditLocationRequest.getStatus());
        location.setDescription(addEditLocationRequest.getDescription());

        return locationMapper.locationToLocationDTO(locationService.save(location));
    }

    /*
     * ====================================Generate
     * Code=============================================
     */
    @Override
    public String getCode(String key) {
        GenerateCode generateCode = generateCodeService.findByKeyName(key);
        long sequance = generateCode.getSeqValue() + 1;
        generateCode.setSeqValue(sequance);
        String code = key + String.format("%03d", sequance);
        generateCodeService.save(generateCode);

        return code;
    }

    /*
     * ====================================POS
     * CRUD=============================================
     */
    @Override
    public PosPagingDTO getAllPos(PagingRequest pagingRequest) {

        Pageable pageable = PageRequest.of(Integer.valueOf(pagingRequest.getPageNo()),
                Integer.valueOf(pagingRequest.getPageSize()));

        Page<Pos> pageResult = posService.findAll(pageable);

        PosPagingDTO posPagingDTO = new PosPagingDTO();
        posPagingDTO.setCount(pageResult.getTotalElements());
        posPagingDTO.setTotalPages(pageResult.getTotalPages());
        List<String> ePosDTO = new ArrayList<String>();
        pageResult.getContent().stream().forEach(s -> {
            ePosDTO.add(cryptoMngrAuthService.encrypt(posMapper.posToPosDTO(s).toString()));
        });
        posPagingDTO.setTransactions(ePosDTO);

        return posPagingDTO;
    }

    @Override
    public PosPagingDTO lookforPos(PagingSearchRequest pagingSearchPosRequest) {

        Pageable pageable = PageRequest.of(Integer.valueOf(pagingSearchPosRequest.getPageNo()),
                Integer.valueOf(pagingSearchPosRequest.getPageSize()));

        Page<Pos> pageResult = posService.lookforPos(pagingSearchPosRequest.getSearchBy(), pageable);

        PosPagingDTO posPagingDTO = new PosPagingDTO();
        posPagingDTO.setCount(pageResult.getTotalElements());
        posPagingDTO.setTotalPages(pageResult.getTotalPages());
        List<String> ePosDTO = new ArrayList<String>();
        pageResult.getContent().stream().forEach(s -> {
            ePosDTO.add(cryptoMngrAuthService.encrypt(posMapper.posToPosDTO(s).toString()));
        });
        posPagingDTO.setTransactions(ePosDTO);

        return posPagingDTO;
    }

    @Override
    public PosDTO addPos(String createdBy, AddEditPosRequest addEditPosRequest) {
        Pos pos = new Pos();
        pos.setCode(getCode(StatusConstant.POS));
        pos.setCreatedAt(Date.from(Instant.now()));
        pos.setCreatedBy(createdBy);
        pos.setName(addEditPosRequest.getName());
        pos.setStatusFk(StatusConstant.STATUS_ACTIVE);
//		pos.setSectorFk(sectorService.findById(Long.valueOf(addEditPosRequest.getSectorId())).get());
        return posMapper.posToPosDTO(posService.save(pos));
    }

    @Override
    public PosDTO editPos(String updatedBy, AddEditPosRequest addEditPosRequest) {
        Pos pos = posService.findById(Long.valueOf(addEditPosRequest.getId())).get();
        // pos.setId(Long.parseLong(cryptoMngrAuthService.decrypt(posId)));
        // pos.setCode(getCode(StatusConstant.POS));
        pos.setUpdatedAt(Date.from(Instant.now()));
        pos.setUpdatedBy(updatedBy);
        pos.setName(addEditPosRequest.getName());
        pos.setStatusFk(addEditPosRequest.getStatus());
//		pos.setSectorFk(sectorService.findById(Long.valueOf(addEditPosRequest.getSectorId())).get());
        Set<Service> serviceSet = new HashSet<Service>();
        if (addEditPosRequest.getServiceIds() != null) {
            for (String serviceId : addEditPosRequest.getServiceIds()) {
                serviceSet.add(new Service(Long.valueOf(serviceId)));
            }
            pos.setServices(serviceSet);
        }

        Set<Sector> sectorSet = new HashSet<Sector>();
        if (addEditPosRequest.getSectorIds() != null) {
            for (String sectorId : addEditPosRequest.getSectorIds()) {
                sectorSet.add(new Sector(Long.valueOf(sectorId)));
            }
            pos.setSectors(sectorSet);
        }

        return posMapper.posToPosDTO(posService.save(pos));
    }

    @Override
    public Set<SectorDTO> getAllSectorByLocation(PosByLocationRequest posByLocationRequest) {
        Location location = locationService.findById(Long.valueOf(posByLocationRequest.getLocationId())).get();
        return sectorMapper.sectorsToSectorDTOs(location.getSectors());
    }

    @Override
    public Set<PosDTO> getAllPosByUser(PosByUserRequest posByUserRequest) {
        User user = userService.findById(Long.valueOf(posByUserRequest.getUserId())).get();
        return posMapper.posesToPosDTOs(user.getPosSet());
    }

    public List<PosDTO> searchPOS(String keyword) {
        List<PosDTO> posDTO = new ArrayList<PosDTO>();
        try {
            posDTO = posMapper.posesToPosDTOs(posService.serchPOS(keyword));

        } catch (Exception e) {
            return posDTO;
        }
        return posDTO;
    }

    public PosDTO getPosByPosId(PosByIDRequest posByIDRequest) {
        return posMapper.posToPosDTO(posService.getPOSById(Long.valueOf(posByIDRequest.getPosId())));
    }

    @Override
    public PosWithUserAgentDTO getLightPosByPosId(PosByIDRequest posByIDRequest) {
        return posMapper.posToLightPosDTO(posService.getPOSById(Long.valueOf(posByIDRequest.getPosId())));
    }

    /*
     * ====================================POS
     * CRUD=============================================
     */
    @Override
    public List<RoleDTO> getAllRoles() {
        return roleMapper.rolesToRoleDTOs(roleService.findAll());
    }

    @Override
    public RoleDTO addRole(String createdBy, AddEditRoleRequest addEditRoleRequest) {
        Role role = new Role();
        role.setCreatedAt(Date.from(Instant.now()));
        role.setCreatedBy(createdBy);
        role.setComment(addEditRoleRequest.getComment());
        role.setName(addEditRoleRequest.getName());
        return roleMapper.roleToRoleDTO(roleService.save(role));
    }

    @Override
    public RoleDTO editRole(String updatedBy, AddEditRoleRequest addEditRoleRequest) {
        Role role = roleService.findById(Long.valueOf(addEditRoleRequest.getId())).get();
        role.setUpdatedAt(Date.from(Instant.now()));
        role.setUpdatedBy(updatedBy);
        role.setComment(addEditRoleRequest.getComment());
        role.setName(addEditRoleRequest.getName());
        role.setMenus(new HashSet<Menu>());
        Set<Menu> menuSet = new HashSet<Menu>();
        for (String menuId : addEditRoleRequest.getMenuIds()) {
            menuSet.add(new Menu(Long.valueOf(menuId)));
        }
        role.setMenus(menuSet);
        return roleMapper.roleToRoleDTO(roleService.save(role));
    }

    /*
     * ====================================Menu
     * CRUD=============================================
     */
    @Override
    public Set<MenuDTO> getAllMenus(ServiceMenuRequest serviceMenuRequest) {
        Service eService = serviceService.findById(Long.valueOf(serviceMenuRequest.getServiceId())).get();

        return menuMapper.menusToMenuDTOs(menuService.findByServiceFk(eService));
    }

    @Override
    public List<MenuDTO> getMenus() {
        // TODO Auto-generated method stub
        return menuMapper.menusToMenuDTOs(menuService.findAll());
    }

    @Override
    public Set<MenuDTO> getAuthMenus(ServiceMenuRequest serviceMenuRequest, Role role) {
        Set<Menu> roleMenu = role.getMenus().stream()
                .filter(x -> x.getServiceFk().getId() == Long.valueOf(serviceMenuRequest.getServiceId()))
                .collect(Collectors.toSet());
        return menuMapper.menusToMenuDTOs(roleMenu);
    }

    /*
     * ====================================City
     * CRUD=============================================
     */

    @Override
    public List<CityDTO> getAllCities() {
        return cityMapper.locationsToLocationDTOs(cityService.findAll());
    }

    @Override
    public Long getInsuranceNumber(long serviceId, long locationId) {
        Optional<InsuranceNumber> OInsuranceNumber = insuranceNumberService.findByServiceIdAndLocationId(serviceId,
                locationId);

        if (OInsuranceNumber.isPresent()) {
            InsuranceNumber eInsuranceNumber = OInsuranceNumber.get();
            long newSequance = eInsuranceNumber.getSequanceNumber() + 1;
            eInsuranceNumber.setSequanceNumber(newSequance);
            insuranceNumberService.update(eInsuranceNumber, eInsuranceNumber.getId());
            return newSequance;
        } else {
            InsuranceNumber eInsuranceNumber = new InsuranceNumber();
            eInsuranceNumber.setLocationId(locationId);
            eInsuranceNumber.setServiceId(serviceId);
            eInsuranceNumber.setSequanceNumber(1);
            insuranceNumberService.save(eInsuranceNumber);
            return (long) 1;
        }
    }

    @Override
    public Long getInsuranceNumber(long bookTypeId, long locationId, long serviceId) {
        Optional<InsuranceNumber> OInsuranceNumber = insuranceNumberService
                .findByBookTypeIdAndLocationIdAndServiceId(bookTypeId, locationId, serviceId);

        if (OInsuranceNumber.isPresent()) {
            InsuranceNumber eInsuranceNumber = OInsuranceNumber.get();
            long newSequance = eInsuranceNumber.getSequanceNumber() + 1;
            eInsuranceNumber.setSequanceNumber(newSequance);
            insuranceNumberService.update(eInsuranceNumber, eInsuranceNumber.getId());
            return newSequance;
        } else {
            InsuranceNumber eInsuranceNumber = new InsuranceNumber();
            eInsuranceNumber.setBookTypeId(bookTypeId);
            eInsuranceNumber.setLocationId(locationId);
            eInsuranceNumber.setServiceId(serviceId);
            eInsuranceNumber.setSequanceNumber(1);
            insuranceNumberService.save(eInsuranceNumber);
            return (long) 1;
        }
    }

    @Override
    public Long getInsuranceNumberWithoutIncrement(long serviceId, long locationId) {
        Optional<InsuranceNumber> OInsuranceNumber = insuranceNumberService.findByServiceIdAndLocationId(serviceId,
                locationId);

        if (OInsuranceNumber.isPresent()) {
            InsuranceNumber eInsuranceNumber = OInsuranceNumber.get();
            return eInsuranceNumber.getSequanceNumber();
        } else {
            return (long) 0;
        }
    }

    @Override
    public Long getInsuranceNumberWithoutIncrement(long bookTypeId, long locationId, long serviceId) {
        Optional<InsuranceNumber> OInsuranceNumber = insuranceNumberService
                .findByBookTypeIdAndLocationIdAndServiceId(bookTypeId, locationId, serviceId);

        if (OInsuranceNumber.isPresent()) {
            InsuranceNumber eInsuranceNumber = OInsuranceNumber.get();
            return eInsuranceNumber.getSequanceNumber();
        } else {
            return (long) 0;
        }
    }

    @Override
    public void editInsuranceNumber(long serviceId, long locationId, long incrementNumber) {
        Optional<InsuranceNumber> OInsuranceNumber = insuranceNumberService.findByServiceIdAndLocationId(serviceId,
                locationId);

        if (OInsuranceNumber.isPresent()) {
            InsuranceNumber eInsuranceNumber = OInsuranceNumber.get();
            eInsuranceNumber.setSequanceNumber(eInsuranceNumber.getSequanceNumber() + incrementNumber);
            insuranceNumberService.update(eInsuranceNumber, eInsuranceNumber.getId());

        } else {
            InsuranceNumber eInsuranceNumber = new InsuranceNumber();
            eInsuranceNumber.setLocationId(locationId);
            eInsuranceNumber.setServiceId(serviceId);
            eInsuranceNumber.setSequanceNumber(incrementNumber);
            insuranceNumberService.save(eInsuranceNumber);

        }
    }

    @Override
    public void editInsuranceNumber(long serviceId, long locationId, long incrementNumber, long bookTypeId) {
        Optional<InsuranceNumber> OInsuranceNumber = insuranceNumberService
                .findByBookTypeIdAndLocationIdAndServiceId(bookTypeId, locationId, serviceId);

        if (OInsuranceNumber.isPresent()) {
            InsuranceNumber eInsuranceNumber = OInsuranceNumber.get();
            eInsuranceNumber.setSequanceNumber(eInsuranceNumber.getSequanceNumber() + incrementNumber);
            insuranceNumberService.update(eInsuranceNumber, eInsuranceNumber.getId());

        } else {
            InsuranceNumber eInsuranceNumber = new InsuranceNumber();
            eInsuranceNumber.setBookTypeId(bookTypeId);
            eInsuranceNumber.setLocationId(locationId);
            eInsuranceNumber.setServiceId(serviceId);
            eInsuranceNumber.setSequanceNumber(incrementNumber);
            insuranceNumberService.save(eInsuranceNumber);

        }
    }

    @Override
    public SectorPagingDTO getAllSector(PagingRequest pagingRequest) {
        Pageable pageable = PageRequest.of(Integer.valueOf(pagingRequest.getPageNo()),
                Integer.valueOf(pagingRequest.getPageSize()));

        Page<Sector> pageResult = sectorService.findAll(pageable);

        SectorPagingDTO sectorPagingDTO = new SectorPagingDTO();
        sectorPagingDTO.setCount(pageResult.getTotalElements());
        sectorPagingDTO.setTotalPages(pageResult.getTotalPages());
        sectorPagingDTO.setSectors(sectorMapper.sectorsToSectorDTOs(pageResult.getContent()));

        return sectorPagingDTO;

    }

    @Override
    public List<SectorDTO> getAllSector() {
        // TODO Auto-generated method stub
        return sectorMapper.sectorsToSectorDTOs(sectorService.findAll());
    }

    @Override
    public SectorDTO getSectorById(long id) {
        // TODO Auto-generated method stub
        return sectorMapper.sectorToSectorDTO(sectorService.findById(id).get());
    }

    @Override
    public SectorDTO getSectorByName(String name) {
        // TODO Auto-generated method stub
        return sectorMapper.sectorToSectorDTO(sectorService.findByName(name));
    }

    @Override
    public SectorDTO addSector(String createdBy, AddEditSectorRequest addEditSectorRequest) {
        Date createdAt = Date.from(Instant.now());

        Sector sector = new Sector();
        sector.setCreatedAt(createdAt);
        sector.setCreatedBy(createdBy);
        sector.setLocationFk(locationService.findById(Long.valueOf(addEditSectorRequest.getLocationId())).get());
        sector.setName(addEditSectorRequest.getName());
        sector.setStatusFk(addEditSectorRequest.getStatus());
        sector.setSupplyOrderSeqRef((long) 0);
        // Get the current year
        long currentYear = LocalDate.now().getYear();
        // Extract the last two digits
        long lastTwoDigits = currentYear % 100;
        sector.setSupplyOrderYearRef(lastTwoDigits);

        return sectorMapper.sectorToSectorDTO(sectorService.save(sector));
    }

    @Override
    public SectorDTO editSector(String updatedBy, AddEditSectorRequest addEditSectorRequest) {
        Date updatedAt = Date.from(Instant.now());

        Sector sector = sectorService.findById(Long.valueOf(addEditSectorRequest.getId())).get();
        sector.setUpdatedAt(updatedAt);
        sector.setUpdatedBy(updatedBy);
        sector.setLocationFk(locationService.findById(Long.valueOf(addEditSectorRequest.getLocationId())).get());
        sector.setName(addEditSectorRequest.getName());
        sector.setStatusFk(addEditSectorRequest.getStatus());

        return sectorMapper.sectorToSectorDTO(sectorService.save(sector));
    }

    @Override
    public List<JwtAuthSectorDTO> getAllAuthSector(long locationId) {
        // TODO Auto-generated method stub
        return sectorMapper
                .sectorsToJwtAuthSectorDTOs(sectorService.findByLocationFk(locationService.findById(locationId).get()));
    }

    @Override
    public List<SectorDTO> getAllSector(long locationId) {
        // TODO Auto-generated method stub
        return sectorMapper
                .sectorsToSectorDTOs(sectorService.findByLocationFk(locationService.findById(locationId).get()));
    }

    @Override
    public SectorPagingDTO lookforSector(PagingSearchRequest pagingSearchRequest) {

        Pageable pageable = PageRequest.of(Integer.valueOf(pagingSearchRequest.getPageNo()),
                Integer.valueOf(pagingSearchRequest.getPageSize()));

        Page<Sector> pageResult = sectorService.lookforSector(pagingSearchRequest.getSearchBy(), pageable);

        SectorPagingDTO sectorPagingDTO = new SectorPagingDTO();
        sectorPagingDTO.setCount(pageResult.getTotalElements());
        sectorPagingDTO.setTotalPages(pageResult.getTotalPages());
        sectorPagingDTO.setSectors(sectorMapper.sectorsToSectorDTOs(pageResult.getContent()));

        return sectorPagingDTO;
    }

    @Override
    public List<SectorDTO> findAllSector() {

        return sectorMapper.sectorsToSectorDTOs(sectorService.findAll());
    }

    @Override
    public List<MaazouniaChurchDTO> getAllMaazouniaChurchs() {
        return maazouniaChurchMapper.maazouniaChurchsToMaazouniaChurchDTOs(maazouniaChurchService.findAll());
    }

    @Override
    public MaazouniaChurchsPagingDTO maazouniaChurchsPaging(PagingSearchRequest pagingSearchRequest) {
        Pageable pageable = PageRequest.of(Integer.valueOf(pagingSearchRequest.getPageNo()),
                Integer.valueOf(pagingSearchRequest.getPageSize()));

        Page<MaazouniaChurch> pageResult = maazouniaChurchService.findAll(pageable);

        MaazouniaChurchsPagingDTO maazouniaChurchsPagingDTO = new MaazouniaChurchsPagingDTO();
        maazouniaChurchsPagingDTO.setCount(pageResult.getTotalElements());
        maazouniaChurchsPagingDTO.setTotalPages(pageResult.getTotalPages());
        maazouniaChurchsPagingDTO.setMaazouniaChurchs(
                maazouniaChurchMapper.maazouniaChurchsToMaazouniaChurchDTOs(pageResult.getContent()));

        return maazouniaChurchsPagingDTO;
    }

    @Override
    public MaazouniaChurchDTO addMaazouniaChurch(String createdBy,
                                                 AddEditMaazouniaChurchRequest addEditMaazouniaChurchRequest) {
        Date createdAt = Date.from(Instant.now());
        MaazouniaChurch maazouniaChurch = new MaazouniaChurch();
        maazouniaChurch.setCreatedAt(createdAt);
        maazouniaChurch.setCreatedBy(createdBy);
        maazouniaChurch
                .setSectorFk(sectorService.findById(Long.valueOf(addEditMaazouniaChurchRequest.getSectorId())).get());
        maazouniaChurch.setName(addEditMaazouniaChurchRequest.getName());
        maazouniaChurch.setStatusFk(addEditMaazouniaChurchRequest.getStatus());
        maazouniaChurch.setMaazouniaCreationDate(
                UtilCore.convertStringToStartDateFormatSql(addEditMaazouniaChurchRequest.getCreatetionDate()));
        maazouniaChurch.setMaazouniaType(addEditMaazouniaChurchRequest.getType());

        if (addEditMaazouniaChurchRequest.getCreationAttacment() != null
                && !addEditMaazouniaChurchRequest.getCreationAttacment().getOriginalFilename().equals("foo.txt")) {
            try {
                String AttUrl = saveAttFile(addEditMaazouniaChurchRequest.getCreationAttacment(), maazouniaChurchPathAtt,
                        addEditMaazouniaChurchRequest.getName());
                maazouniaChurch.setCreationAtt(AttUrl);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return maazouniaChurchMapper.maazouniaChurchToMaazouniaChurchDTO(maazouniaChurchService.save(maazouniaChurch));
    }

    @Override
    public MaazouniaChurchDTO editMaazouniaChurch(String updatedBy,
                                                  AddEditMaazouniaChurchRequest addEditMaazouniaChurchRequest) {

        Date updatedAt = Date.from(Instant.now());

        MaazouniaChurch maazouniaChurch = maazouniaChurchService
                .findById(Long.valueOf(addEditMaazouniaChurchRequest.getId())).get();
        maazouniaChurch.setUpdatedAt(updatedAt);
        maazouniaChurch.setUpdatedBy(updatedBy);
        maazouniaChurch
                .setSectorFk(sectorService.findById(Long.valueOf(addEditMaazouniaChurchRequest.getSectorId())).get());
        maazouniaChurch.setName(addEditMaazouniaChurchRequest.getName());
        maazouniaChurch.setStatusFk(addEditMaazouniaChurchRequest.getStatus());
        maazouniaChurch.setMaazouniaCreationDate(UtilCore.convertStringToStartDateFormatSql(addEditMaazouniaChurchRequest.getCreatetionDate()));
        maazouniaChurch.setMaazouniaType(addEditMaazouniaChurchRequest.getType());

        return maazouniaChurchMapper.maazouniaChurchToMaazouniaChurchDTO(maazouniaChurchService.save(maazouniaChurch));
    }

    @Override
    public List<MaazouniaChurchDTO> lookForMaazounizChruch(PagingSearchRequest pagingSearchRequest) {
        List<MaazouniaChurch> list = maazouniaChurchService.lookforMaazouniaChurch(pagingSearchRequest.getSearchBy());
        return maazouniaChurchMapper.maazouniaChurchsToMaazouniaChurchDTOs(list);

    }

    @Override
    public SettingPagingDTO getAllSetting(PagingRequest pagingRequest) {
        Pageable pageable = PageRequest.of(Integer.valueOf(pagingRequest.getPageNo()),
                Integer.valueOf(pagingRequest.getPageSize()));

        Page<Setting> pageResult = settingService.findAll(pageable);

        SettingPagingDTO settingPagingDTO = new SettingPagingDTO();
        settingPagingDTO.setCount(pageResult.getTotalElements());
        settingPagingDTO.setTotalPages(pageResult.getTotalPages());
        settingPagingDTO.setSettings((settingMapper.settingsToSettingDTOs(pageResult.getContent())));

        return settingPagingDTO;

    }

    @Override
    public SettingDTO editSetting(AddEditSettingRequest addEditSettingRequest) {
        Setting eSetting = settingService.findById(Long.valueOf(addEditSettingRequest.getId())).get();

        eSetting.setValueOps(addEditSettingRequest.getValueOps());
        SettingDTO settingDTO = settingMapper.settingToSettingDTO(settingService.save(eSetting));

        return settingDTO;
    }

    public String saveAttFile(MultipartFile att, String url, String fileName) throws Exception {
        Path fileStorageLocation = null;
        fileName = fileName + "_" + Date.from(Instant.now()).getTime();
        if (att != null && !att.getOriginalFilename().equals("foo.txt")) {
            try {
                fileStorageLocation = Paths.get(url + "/" + Util.dateFormat() + "/" + fileName + "." + "png")
                        .toAbsolutePath().normalize();

                Path path = Paths.get(fileStorageLocation.toString());
                // System.gc();
                File f = new File(fileStorageLocation.toString());
                if (f.exists()) {
                    FileUtils.forceDelete(new File(fileStorageLocation.toString()));

                }
//				Files.deleteIfExists(Paths.get(fileStorageLocation.toUri()));
                Files.createDirectories(path);
                Files.copy(att.getInputStream(), Paths.get(fileStorageLocation.toUri()),
                        StandardCopyOption.REPLACE_EXISTING);

            } catch (Exception ex) {
                ex.printStackTrace();
                logger.error("saveAttFile An exception when saveAttachedFile! ", ex);
                throw new TransactionException("saveAttFile An exception when saveAttachedFile with! " + url);
            }

        }
        return fileStorageLocation.toString();

    }

    @Override
    public List<SubServiceQuotaDTO> getAllSubServiceQuota() {
        return subServiceQuotaMapper.subServiceQuotaTosubServiceQuotaDTOs(
                subServiceQuotaService.findAll());
    }

    @Override
    public SubServicePriceTierDTO addSubServiceTier(String createdBy, AddEditSubServiceTierRequest addEditSubServiceTierRequest) {
        SubServicePriceTier entity = SubServicePriceTier.builder()
                .name(addEditSubServiceTierRequest.getName())
                .fees(Double.valueOf(addEditSubServiceTierRequest.getFees()))
                .subServiceFk(subServiceService.findById(Long.valueOf(addEditSubServiceTierRequest.getSubServiceFk())).get())
                .build();
        entity.setCreatedBy(createdBy);
        entity.setCreatedAt(Date.from(Instant.now()));
        return serviceMapper.subServicePriceTierToDto(subServicePriceTierService.save(entity), "");
    }

    @Override
    public SubServiceQuotaDTO addSubServiceQuota(AddEditSubServiceQuotaRequest addEditSubServiceQuotaRequest) {

        SubServiceQuota entity = new SubServiceQuota();
        entity.setBeneficiary(addEditSubServiceQuotaRequest.getMidBenficiary());
        entity.setMidBank(addEditSubServiceQuotaRequest.getMidBank());
        entity.setFees(Double.valueOf(addEditSubServiceQuotaRequest.getFees()));
        entity.setDescription(addEditSubServiceQuotaRequest.getDescription());
        entity.setFeesType(addEditSubServiceQuotaRequest.getFeesType());
        entity.setMidAccount(addEditSubServiceQuotaRequest.getMidAccount());
        entity.setName(addEditSubServiceQuotaRequest.getName());
        entity.setStatusFk(addEditSubServiceQuotaRequest.getStatusFk());
        entity.setSubServiceFk(subServiceService.findById(Long.valueOf(addEditSubServiceQuotaRequest.getSubServiceFk())).get());
        if (addEditSubServiceQuotaRequest.getSubServiceTierFk() != null) {
            entity.setSubServicePriceTierFk(subServicePriceTierService.findById(Long.valueOf(addEditSubServiceQuotaRequest.getSubServiceTierFk())).get());
        }

        return subServiceQuotaMapper.subServiceQuotaTosubServiceQuotaDTO(subServiceQuotaService.save(entity));
    }

    @Override
    public SubServiceQuotaDTO editSubServiceQuota(AddEditSubServiceQuotaRequest addEditSubServiceQuotaRequest) {

        SubServiceQuota entity = subServiceQuotaService.findById(Long.valueOf(addEditSubServiceQuotaRequest.getId())).get();

        entity.setBeneficiary(addEditSubServiceQuotaRequest.getMidBenficiary());
        entity.setMidBank(addEditSubServiceQuotaRequest.getMidBank());
        entity.setFees(Double.valueOf(addEditSubServiceQuotaRequest.getFees()));
        entity.setDescription(addEditSubServiceQuotaRequest.getDescription());
        entity.setFeesType(addEditSubServiceQuotaRequest.getFeesType());
        entity.setMidAccount(addEditSubServiceQuotaRequest.getMidAccount());
        entity.setName(addEditSubServiceQuotaRequest.getName());
        entity.setStatusFk(addEditSubServiceQuotaRequest.getStatusFk());
        //	entity.setSubServiceFk(subServiceService.findById(Long.valueOf(addEditSubServiceQuotaRequest.getSubServiceFk())).get());


        return subServiceQuotaMapper.subServiceQuotaTosubServiceQuotaDTO(subServiceQuotaService.save(entity));
    }

    @Override
    public String getSupplyReferenceNumber(SupplyOrderReferenceNumberRequest supplyOrderReferenceNumberRequest) {
        Sector sector = sectorService.findById(
                Long.valueOf(supplyOrderReferenceNumberRequest.getSectorId())).orElse(null);

        long sequance = sector.getSupplyOrderSeqRef() + 1;
        String currentYearTwoDigits = Util.getCurrentYearTwoDigits();
        if (!currentYearTwoDigits.equals(String.valueOf(sector.getSupplyOrderYearRef()))) {
            sequance = 1;
        }

//		sector.setSupplyOrderSeqRef(sequance);
//		sector.setSupplyOrderYearRef(Long.valueOf(currentYearTwoDigits));
//		sectorService.save(sector);

        String supplyOrderReferenceNumber = String.format("%03d", sector.getId()) + "-" +
                currentYearTwoDigits + String.format("%03d", sequance);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sequance", String.valueOf(sequance));
        jsonObject.put("currentYearTwoDigits", currentYearTwoDigits);
        jsonObject.put("supplyOrderReferenceNumber", supplyOrderReferenceNumber);

        return jsonObject.toString();
    }


}

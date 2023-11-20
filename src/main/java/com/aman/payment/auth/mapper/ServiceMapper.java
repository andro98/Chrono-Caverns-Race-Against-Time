package com.aman.payment.auth.mapper;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.aman.payment.auth.model.SubServicePriceTier;
import com.aman.payment.auth.model.dto.SubServicePriceTierDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.SubService;
import com.aman.payment.auth.model.dto.JwtAuthServiceDTO;
import com.aman.payment.auth.model.dto.ServiceDTO;
import com.aman.payment.auth.model.dto.SubServiceDTO;
import com.aman.payment.auth.model.lookup.ServiceType;
import com.aman.payment.auth.service.CryptoMngrAuthService;

@Service
public class ServiceMapper {

    @Autowired
    private CryptoMngrAuthService cryptoMngrAuthService;

    /*
     * service to serviceDTO
     * services to serviceDTOs
     * ****************************************************************************************************
     */
    public ServiceDTO serviceToServiceDTO(com.aman.payment.auth.model.Service service) {
        return createServiceDTO(service);
    }

    public List<ServiceDTO> servicesToServiceDTOs(List<com.aman.payment.auth.model.Service> services) {
        return services.stream().filter(Objects::nonNull)
                .map(this::serviceToServiceDTO).collect(Collectors.toList());
    }

    public Set<ServiceDTO> servicesToServiceDTOs(Set<com.aman.payment.auth.model.Service> services) {
        return services.stream().filter(Objects::nonNull)
                .map(this::serviceToServiceDTO).collect(Collectors.toSet());
    }

    private ServiceDTO createServiceDTO(com.aman.payment.auth.model.Service service) {
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setName(service.getName());
        serviceDTO.setId(String.valueOf(service.getId()));
        serviceDTO.setCode(service.getCode());
        serviceDTO.setStatus(service.getStatusFk());
        serviceDTO.setTax(String.valueOf(service.getTax()));
        serviceDTO.setMerchantName(String.valueOf(service.getMerchantFk().getName()));
        serviceDTO.setServiceType(String.valueOf(ServiceType.MAIN_SERVICE));
        serviceDTO.setMerchantName(service.getMerchantFk().getName());
        serviceDTO.setMerchantId(service.getMerchantFk().getId().toString());
        serviceDTO.setDescription(service.getDescription());
        serviceDTO.setIcon(service.getIcon());


        return serviceDTO;
    }

    /*
     * sub-service to sub-serviceDTO
     * sub-services to sub-serviceDTOs
     * ****************************************************************************************************
     */
    public SubServiceDTO subServiceToSubServiceDTO(SubService subService) {
        return createSubServiceDTO(subService);
    }

    public List<SubServiceDTO> subServicesToSubServiceDTOs(List<SubService> subServices) {
        return subServices.stream().filter(Objects::nonNull)
                .map(this::subServiceToSubServiceDTO).collect(Collectors.toList());
    }

    public Set<SubServiceDTO> subServicesToSubServiceDTOs(Set<SubService> subServices) {
        return subServices.stream().filter(Objects::nonNull)
                .map(this::subServiceToSubServiceDTO).collect(Collectors.toSet());
    }

    private SubServiceDTO createSubServiceDTO(SubService subService) {
        SubServiceDTO subServiceDTO = new SubServiceDTO();
        subServiceDTO.setName(subService.getName());
        subServiceDTO.setId(String.valueOf(subService.getId()));
        subServiceDTO.setStatus(subService.getStatusFk());
        subServiceDTO.setFees(String.valueOf(subService.getFees()));
        subServiceDTO.setServiceName(subService.getServiceFk().getName());
        subServiceDTO.setDescription(subService.getDescription());
        subServiceDTO.setContractCounts(String.valueOf(subService.getContractsCount()));

        return subServiceDTO;
    }

    /*
     * sub-service to serviceDTO
     * sub-services to serviceDTOs
     * ****************************************************************************************************
     */
    public ServiceDTO subServiceToServiceDTO(SubService subService) {
        return createServiceDTO(subService);
    }

    public List<ServiceDTO> subServicesToServiceDTOs(List<SubService> subServices) {
        return subServices.stream().filter(Objects::nonNull)
                .map(this::subServiceToServiceDTO).collect(Collectors.toList());
    }

    private ServiceDTO createServiceDTO(SubService subService) {
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setName(subService.getName());
        serviceDTO.setId(String.valueOf(subService.getId()));
        serviceDTO.setStatus(subService.getStatusFk());
        serviceDTO.setFees(String.valueOf(subService.getFees()));
        serviceDTO.setServiceType(String.valueOf(ServiceType.SUB_SERVICE));
        serviceDTO.setRequiredService(String.valueOf(subService.isRequiredService()));
        serviceDTO.setParentName(subService.getServiceFk().getName());
        serviceDTO.setParentId(String.valueOf(subService.getServiceFk().getId()));
        serviceDTO.setDescription(subService.getDescription());
        serviceDTO.setContractCount(String.valueOf(subService.getContractsCount()));

        return serviceDTO;
    }

    /*
     * service to JwtAuthServiceDTO
     * services to JwtAuthServiceDTOs
     * ****************************************************************************************************
     */
    public JwtAuthServiceDTO serviceToJwtAuthServiceDTO(com.aman.payment.auth.model.Service service) {
        return createJwtAuthServiceDTO(service);
    }

    public List<JwtAuthServiceDTO> servicesToJwtAuthServiceDTOs(List<com.aman.payment.auth.model.Service> services) {
        return services.stream().filter(Objects::nonNull)
                .map(this::serviceToJwtAuthServiceDTO).collect(Collectors.toList());
    }

    public Set<JwtAuthServiceDTO> servicesToJwtAuthServiceDTOs(Set<com.aman.payment.auth.model.Service> services) {
        return services.stream().filter(Objects::nonNull)
                .map(this::serviceToJwtAuthServiceDTO).collect(Collectors.toSet());
    }

    private JwtAuthServiceDTO createJwtAuthServiceDTO(com.aman.payment.auth.model.Service service) {
        JwtAuthServiceDTO jwtAuthServiceDTO = new JwtAuthServiceDTO();
        jwtAuthServiceDTO.setName(service.getName());
        jwtAuthServiceDTO.setId(String.valueOf(service.getId()));
        jwtAuthServiceDTO.setTax(String.valueOf(service.getTax()));
        jwtAuthServiceDTO.setIcon(service.getIcon());
        if (service.getMerchantFk() != null) {
            jwtAuthServiceDTO.setMerchantName(service.getMerchantFk().getName());
            jwtAuthServiceDTO.setMerchantId(String.valueOf(service.getMerchantFk().getId()));
        }
        return jwtAuthServiceDTO.encrypt(cryptoMngrAuthService);
    }

    public SubServicePriceTierDTO subServicePriceTierToDto(SubServicePriceTier subService, String currentQuotaFees) {
        return createSubServicePriceTierDTO(subService, currentQuotaFees);
    }

//    public List<SubServicePriceTierDTO> subServicePriceTierToSubServicePriceTierDTOS(List<SubServicePriceTier> subServices) {
//        return subServices.stream().filter(Objects::nonNull)
//                .map(this::subServicePriceTierToDto).collect(Collectors.toList());
//    }


    private SubServicePriceTierDTO createSubServicePriceTierDTO(SubServicePriceTier subServicePriceTier, String currentQuotaFees) {
        SubServicePriceTierDTO subServicePriceTierDTO = new SubServicePriceTierDTO();
        subServicePriceTierDTO.setName(subServicePriceTier.getName());
        subServicePriceTierDTO.setId(String.valueOf(subServicePriceTier.getId()));
        subServicePriceTierDTO.setFees(String.valueOf(subServicePriceTier.getFees()));
        subServicePriceTierDTO.setDescription(subServicePriceTier.getDescription());
        subServicePriceTierDTO.setSubServiceId(String.valueOf(subServicePriceTier.getSubServiceFk().getId()));
        subServicePriceTierDTO.setSubServiceName(subServicePriceTier.getSubServiceFk().getName());
        subServicePriceTierDTO.setCurrentQuotaFees(currentQuotaFees);
        return subServicePriceTierDTO;
    }
}

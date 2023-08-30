package com.aman.payment.auth.model.dto;

import java.util.Set;

import org.json.JSONObject;

public class JwtAuthPosDTO {
	private String id;
//    private String locationName;
//    private String locationId;
	private String serviceId;
	private String serviceName;
//    private String locationCode;
	private String serviceIcon;
	private String passportImageOption;
	private String permissionImageOption;
	private String posName;
	private String posId;
//    private String sectorId;
//    private String sectorName;
//    private String cityId;
//    private String cityName;
	private Set<SectorDTO> sectors;

	public JwtAuthPosDTO() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getServiceId() {
		return serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceIcon() {
		return serviceIcon;
	}

	public void setServiceIcon(String serviceIcon) {
		this.serviceIcon = serviceIcon;
	}

	public String getPassportImageOption() {
		return passportImageOption;
	}

	public void setPassportImageOption(String passportImageOption) {
		this.passportImageOption = passportImageOption;
	}

	public String getPermissionImageOption() {
		return permissionImageOption;
	}

	public void setPermissionImageOption(String permissionImageOption) {
		this.permissionImageOption = permissionImageOption;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	public Set<SectorDTO> getSectors() {
		return sectors;
	}

	public void setSectors(Set<SectorDTO> sectors) {
		this.sectors = sectors;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JwtAuthPosDTO other = (JwtAuthPosDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {

		return new JSONObject(this).toString();

	}

}
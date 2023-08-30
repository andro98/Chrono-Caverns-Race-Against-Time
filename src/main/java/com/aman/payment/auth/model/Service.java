package com.aman.payment.auth.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.aman.payment.core.model.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Service.
 */
@Entity
@Table(name = "service", 
indexes = { 
		@Index(name = "index_name", columnList="name", unique = true),
		@Index(name = "index_code", columnList="code", unique = true)
		})
public class Service extends DateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "name", nullable = false)
	private String name;

	@NotNull
	@Column(name = "code", nullable = false)
	private String code;
	
	@Column(name = "tax", nullable = false, columnDefinition = "BIGINT default 0")
	private Long tax;

	@Column(name = "description")
	private String description;

	@Column(name = "statusFk")
	private String statusFk;
	
	@NotNull
	@Column(name = "icon", nullable = false)
	private String icon;


	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "services")
	private Set<Pos> posSet = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "serviceFk")
	private Set<SubService> subServices  = new HashSet<SubService>(0);
	
	@ManyToOne
	@JoinColumn(name = "merchantFk", referencedColumnName = "id", nullable = true)
    @JsonIgnore
    private Merchant merchantFk;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "serviceFk", fetch = FetchType.EAGER)
	private Set<Menu> menus  = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "serviceFk")
	private Set<ServiceMids> serviceMids  = new HashSet<ServiceMids>(0);

	public Long getId() {
		return id;
	}

	public Service(Long id) {
		this.id = id;
	}

	public Service() {

	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public Service name(String name) {
		this.name = name;
		return this;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public Service description(String description) {
		this.description = description;
		return this;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Pos> getPosSet() {
		return posSet;
	}

	public Service posSet(Set<Pos> posSet) {
		this.posSet = posSet;
		return this;
	}

	public Service addPos(Pos pos) {
		this.posSet.add(pos);
		pos.getServices().add(this);
		return this;
	}

	public Service removePos(Pos pos) {
		this.posSet.remove(pos);
		pos.getServices().remove(this);
		return this;
	}

	public void setPosSet(Set<Pos> posSet) {
		this.posSet = posSet;
	}

	public String getStatusFk() {
		return statusFk;
	}

	public Service statusFk(String status) {
		this.statusFk = status;
		return this;
	}

	public void setStatusFk(String status) {
		this.statusFk = status;
	}

	public Set<SubService> getSubServices() {
		return subServices;
	}

	public void setSubServices(Set<SubService> subServices) {
		this.subServices = subServices;
	}
	
	

	public Long getTax() {
		return tax;
	}

	public void setTax(Long tax) {
		this.tax = tax;
	}

	public Merchant getMerchantFk() {
		return merchantFk;
	}

	public void setMerchantFk(Merchant merchantFk) {
		this.merchantFk = merchantFk;
	}

	public Set<Menu> getMenus() {
		return menus;
	}

	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}

	
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}



	public Set<ServiceMids> getServiceMids() {
		return serviceMids;
	}

	public void setServiceMids(Set<ServiceMids> serviceMids) {
		this.serviceMids = serviceMids;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Service other = (Service) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Service [id=" + id + ", name=" + name + ", code=" + code + ", desc=" + description
				+ ", statusFk=" + statusFk + ", posSet=" + posSet
				+ "]";
	}

}

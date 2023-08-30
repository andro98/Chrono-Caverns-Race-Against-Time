package com.aman.payment.auth.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Menu.
 */
@Entity
@Table(name = "menu")
public class Menu implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "name", nullable = false)
	private String name;
	
	@NotNull
	@Column(name = "nameAr", nullable = false)
	private String nameAr;

	@NotNull
	@Column(name = "keyViewName", nullable = false)
	private String keyViewName;

	@NotNull
	@Column(name = "url", nullable = false)
	private String url;
	
	@NotNull
	@Column(name = "icon", nullable = false)
	private String icon;
	
	@NotNull
	@Column(name = "ordering", nullable = false)
	private Integer ordering;
	
	@NotNull
	@Column(name = "isPrivilage", nullable = false)
	private Boolean isPrivilage;


	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "menus")
	private Set<Role> roles = new HashSet<>();
	
	@ManyToOne
	@JoinColumn(name = "service_fk", referencedColumnName = "id", nullable = true)
    @JsonIgnore
    private Service serviceFk;

	public Menu() {
		super();
	}

	public Menu(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public String getName() {
		return name;
	}

	public void setNameEn(String name) {
		this.name = name;
	}

	public String getNameAr() {
		return nameAr;
	}

	public void setNameAr(String nameAr) {
		this.nameAr = nameAr;
	}

	public String getKeyViewName() {
		return keyViewName;
	}

	public Menu keyViewName(String keyViewName) {
		this.keyViewName = keyViewName;
		return this;
	}

	public void setKeyViewName(String keyViewName) {
		this.keyViewName = keyViewName;
	}

	public String getUrl() {
		return url;
	}

	public Menu url(String url) {
		this.url = url;
		return this;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getOrdering() {
		return ordering;
	}

	public void setOrdering(Integer ordering) {
		this.ordering = ordering;
	}

	public Boolean getIsPrivilage() {
		return isPrivilage;
	}

	public void setIsPrivilage(Boolean isPrivilage) {
		this.isPrivilage = isPrivilage;
	}

	public Service getServiceFk() {
		return serviceFk;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setServiceFk(Service serviceFk) {
		this.serviceFk = serviceFk;
	}


}

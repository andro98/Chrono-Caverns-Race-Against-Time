package com.aman.payment.auth.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.aman.payment.core.model.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Pos.
 */
@Entity
@Table(name = "pos", 
indexes = { 
		@Index(name = "index_name", columnList="name", unique = false),
		@Index(name = "index_code", columnList="code", unique = true)
		})
public class Pos extends DateAudit implements Serializable {

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

    @Column(name = "statusFk")
    private String statusFk;
    
//    @ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "sectorFk", referencedColumnName = "id", nullable = true)
//    @JsonIgnore
//    private Sector sectorFk;
    
    @ManyToMany
    @JoinTable(name = "pos_sector",
               joinColumns = @JoinColumn(name = "pos_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "sector_id", referencedColumnName = "id"))
    private Set<Sector> sectors = new HashSet<>();
    
    @ManyToMany
    @JoinTable(name = "pos_service",
               joinColumns = @JoinColumn(name = "pos_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"))
    private Set<Service> services = new HashSet<>();
  
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "posSet")
    @JsonIgnore
    private Set<User> users = new HashSet<>();
    
    
    public Pos() {
		super();
	}

	public Pos(Long id) {
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

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatusFk() {
		return statusFk;
	}

	public void setStatusFk(String statusFk) {
		this.statusFk = statusFk;
	}

	public Set<Service> getServices() {
		return services;
	}

	public void setServices(Set<Service> services) {
		this.services = services;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}



	public Set<Sector> getSectors() {
		return sectors;
	}

	public void setSectors(Set<Sector> sectors) {
		this.sectors = sectors;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pos other = (Pos) obj;
		return Objects.equals(id, other.id);
	}
	
}

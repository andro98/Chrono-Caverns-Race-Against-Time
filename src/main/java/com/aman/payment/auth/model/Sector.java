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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.aman.payment.core.model.audit.DateAudit;
import com.aman.payment.maazoun.model.MaazounProfile;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Sector.
 */
@Entity
@Table(name = "sector", 
indexes = { 
		@Index(name = "index_name", columnList="name", unique = false)
		},
uniqueConstraints={
		 @UniqueConstraint( name = "idx_name_locationFk",  columnNames ={"name", "locationFk"})
		 })
public class Sector extends DateAudit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "statusFk")
    private String statusFk;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "locationFk", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Location locationFk;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "courtFk", referencedColumnName = "id", nullable = true)
    @JsonIgnore
    private Courts courtFk;
    
    @NotNull
    @Column(name = "supplyOrderSeqRef", nullable = false, columnDefinition = "BIGINT default 0")
    private Long supplyOrderSeqRef;
    
    @NotNull
    @Column(name = "supplyOrderYearRef", nullable = false, columnDefinition = "BIGINT default 23")
    private Long supplyOrderYearRef;
    
    @JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "sectors")
	private Set<MaazounProfile> maazounSet = new HashSet<>();
    
    @JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "sectors")
	private Set<Pos> posSet = new HashSet<>();
    
    
    public Sector() {
		super();
	}

	public Sector(Long id) {
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

	public String getStatusFk() {
		return statusFk;
	}

	public void setStatusFk(String statusFk) {
		this.statusFk = statusFk;
	}

	public Location getLocationFk() {
		return locationFk;
	}

	public void setLocationFk(Location locationFk) {
		this.locationFk = locationFk;
	}

	public Set<MaazounProfile> getMaazounSet() {
		return maazounSet;
	}

	public void setMaazounSet(Set<MaazounProfile> maazounSet) {
		this.maazounSet = maazounSet;
	}

	public Set<Pos> getPosSet() {
		return posSet;
	}

	public void setPosSet(Set<Pos> posSet) {
		this.posSet = posSet;
	}

	public Courts getCourtFk() {
		return courtFk;
	}

	public void setCourtFk(Courts courtFk) {
		this.courtFk = courtFk;
	}

	public Long getSupplyOrderSeqRef() {
		return supplyOrderSeqRef;
	}

	public void setSupplyOrderSeqRef(Long supplyOrderSeqRef) {
		this.supplyOrderSeqRef = supplyOrderSeqRef;
	}

	public Long getSupplyOrderYearRef() {
		return supplyOrderYearRef;
	}

	public void setSupplyOrderYearRef(Long supplyOrderYearRef) {
		this.supplyOrderYearRef = supplyOrderYearRef;
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
		Sector other = (Sector) obj;
		return Objects.equals(id, other.id);
	}
	
}

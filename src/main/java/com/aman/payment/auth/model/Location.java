package com.aman.payment.auth.model;

import java.io.Serializable;
import java.util.HashSet;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.aman.payment.core.model.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Location.
 */
@Entity
@Table(name = "location", 
indexes = { 
		@Index(name = "index_name", columnList="name", unique = true),
		@Index(name = "index_code", columnList="code", unique = true)
		})
public class Location extends DateAudit implements Serializable {

    private static final long serialVersionUID = 1L;

//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
//    @SequenceGenerator(name = "sequenceGenerator")
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

    @Column(name = "description")
    private String description;
    
    @NotNull
    @Column(name = "seqValue", nullable = false, columnDefinition = "BIGINT default 0")
    private Long seqValue;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cityFk", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private City cityFk;

    @Column(name = "statusFk")
    private String statusFk;
    
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "locationFk")
//	private Set<Pos> poses  = new HashSet<Pos>(0);
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "locationFk")
	private Set<Sector> sectors  = new HashSet<Sector>(0);
    
    public Location() {
		super();
	}

	public Location(Long id) {
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

    public Location name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public Location code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public Location description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public City getCityFk() {
        return cityFk;
    }

    public Location cityFk(City city) {
        this.cityFk = city;
        return this;
    }

    public void setCityFk(City city) {
        this.cityFk = city;
    }

    public String getStatusFk() {
        return statusFk;
    }

    public Location statusFk(String status) {
        this.statusFk = status;
        return this;
    }

    public void setStatusFk(String status) {
        this.statusFk = status;
    }

	public Long getSeqValue() {
		return seqValue;
	}

	public void setSeqValue(Long seqValue) {
		this.seqValue = seqValue;
	}

	public Set<Sector> getSectors() {
		return sectors;
	}

	public void setSectors(Set<Sector> sectors) {
		this.sectors = sectors;
	}




	
}

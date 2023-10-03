package com.aman.payment.auth.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Transient;

import com.aman.payment.core.model.audit.DateAudit;
import com.aman.payment.util.StatusConstant;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Sub Service.
 */
@Entity
@Table(name = "subService") 
public class SubService extends DateAudit implements Serializable {

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
	@Column(name = "fees", nullable = false)
	private Double fees;

	@Column(name = "description")
	private String description;

	@Column(name = "statusFk")
	private String statusFk;
	
	@Column(name = "contractsCount")
	private Long contractsCount;
	
	@Column(name = "requiredService",columnDefinition="tinyint(1) default 0")
	private boolean requiredService;

	@Column(name = "book_Type")
	private String bookType;

	@ManyToOne
	@JoinColumn(name = "serviceFk", referencedColumnName = "id", nullable = true)
    @JsonIgnore
    private Service serviceFk;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "subServiceFk")
	private Set<SubServiceQuota> subServicesQuota  = new HashSet<SubServiceQuota>(0);
	
	
	@Transient
	private double feesQuota;
	
	public SubService(Long id) {
		this.id = id;
	}

	public SubService() {

	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public SubService name(String name) {
		this.name = name;
		return this;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getFees() {
		fees = (double) 0;
		for(SubServiceQuota quota : getSubServicesQuota()) {
			if(quota.getStatusFk().equals(StatusConstant.STATUS_ACTIVE) && quota.getFeesType().equals("n"))
				fees = fees + quota.getFees();
		}
		return fees;
	}

	public SubService fees(Double fees) {
		this.fees = fees;
		return this;
	}

	public void setFees(Double fees) {
		this.fees = fees;
	}

	public String getDescription() {
		return description;
	}

	public SubService description(String description) {
		this.description = description;
		return this;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatusFk() {
		return statusFk;
	}

	public SubService statusFk(String status) {
		this.statusFk = status;
		return this;
	}

	public void setStatusFk(String status) {
		this.statusFk = status;
	}


	public Service getServiceFk() {
		return serviceFk;
	}


	public void setServiceFk(Service serviceFk) {
		this.serviceFk = serviceFk;
	}


	public Long getId() {
		return id;
	}

	public boolean isRequiredService() {
		return requiredService;
	}

	public void setRequiredService(boolean defaultService) {
		this.requiredService = defaultService;
	}

	public Set<SubServiceQuota> getSubServicesQuota() {
		return subServicesQuota;
	}

	public void setSubServicesQuota(Set<SubServiceQuota> subServicesQuota) {
		this.subServicesQuota = subServicesQuota;
	}

	public Long getContractsCount() {
		return contractsCount;
	}

	public void setContractsCount(Long contractsCount) {
		this.contractsCount = contractsCount;
	}

	public double getFeesQuota() {
		feesQuota = (double) 0;
		for(SubServiceQuota quota : getSubServicesQuota()) {
			if(quota.getStatusFk().equals(StatusConstant.STATUS_ACTIVE) && quota.getFeesType().equals("n"))
				feesQuota = feesQuota + quota.getFees();
		}
		return feesQuota;
	}

	public void setFeesQuota(double feesQuota) {
		this.feesQuota = feesQuota;
	}


	public String getBookType() {
		return bookType;
	}
}

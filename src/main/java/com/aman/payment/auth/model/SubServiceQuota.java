package com.aman.payment.auth.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Sub Service Quota.
 */
@Entity
@Table(name = "subServiceQuota") 
public class SubServiceQuota implements Serializable {

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
	
	@Column(name = "feesType")
	private String feesType;
	
	@Column(name = "midAccount")
	private String midAccount;
	
	@Column(name = "midBank")
	private String midBank;
	
	@Column(name = "beneficiary")
	private String beneficiary;
	
	@ManyToOne
	@JoinColumn(name = "subServiceFk", referencedColumnName = "id", nullable = true)
    @JsonIgnore
    private SubService subServiceFk;
	
	public SubServiceQuota(Long id) {
		this.id = id;
	}

	public SubServiceQuota() {

	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public SubServiceQuota name(String name) {
		this.name = name;
		return this;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getFees() {
		return fees;
	}

	public SubServiceQuota fees(Double fees) {
		this.fees = fees;
		return this;
	}

	public void setFees(Double fees) {
		this.fees = fees;
	}

	public String getDescription() {
		return description;
	}

	public SubServiceQuota description(String description) {
		this.description = description;
		return this;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatusFk() {
		return statusFk;
	}

	public SubServiceQuota statusFk(String status) {
		this.statusFk = status;
		return this;
	}

	public void setStatusFk(String status) {
		this.statusFk = status;
	}

	public Long getId() {
		return id;
	}

	public SubService getSubServiceFk() {
		return subServiceFk;
	}

	public void setSubServiceFk(SubService subServiceFk) {
		this.subServiceFk = subServiceFk;
	}

	public String getFeesType() {
		return feesType;
	}

	public void setFeesType(String feesType) {
		this.feesType = feesType;
	}

	public String getMidAccount() {
		return midAccount;
	}

	public void setMidAccount(String midAccount) {
		this.midAccount = midAccount;
	}

	public String getMidBank() {
		return midBank;
	}

	public void setMidBank(String midBank) {
		this.midBank = midBank;
	}

	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	


}

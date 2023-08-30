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
 * Service MIDs.
 */
@Entity
@Table(name = "ServiceMids") 
public class ServiceMids implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "accountNumber", nullable = false)
	private String accountNumber;

	@NotNull
	@Column(name = "fees", nullable = false)
	private Double fees;

	@Column(name = "statusFk")
	private String statusFk;
	
	@Column(name = "feesType")
	private String feesType;
	
	@ManyToOne
	@JoinColumn(name = "serviceFk", referencedColumnName = "id", nullable = true)
    @JsonIgnore
    private Service serviceFk;
	
	public ServiceMids(Long id) {
		this.id = id;
	}

	public ServiceMids() {

	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getFees() {
		return fees;
	}

	public ServiceMids fees(Double fees) {
		this.fees = fees;
		return this;
	}

	public void setFees(Double fees) {
		this.fees = fees;
	}

	public String getStatusFk() {
		return statusFk;
	}

	public ServiceMids statusFk(String status) {
		this.statusFk = status;
		return this;
	}

	public void setStatusFk(String status) {
		this.statusFk = status;
	}

	public Long getId() {
		return id;
	}

	

	public Service getServiceFk() {
		return serviceFk;
	}

	public void setServiceFk(Service serviceFk) {
		this.serviceFk = serviceFk;
	}

	public String getFeesType() {
		return feesType;
	}

	public void setFeesType(String feesType) {
		this.feesType = feesType;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}


}

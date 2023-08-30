package com.aman.payment.core.model;

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


@Entity
@Table(name = "transactionMids")
public class TransactionMids implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "midValue")
	private Double midValue;

	@Column(name = "midAccount")
	private String midAccount;
	
	@Column(name = "midBank")
	private String midBank;
	
	@Column(name = "beneficiary")
	private String beneficiary;

	@ManyToOne
	@JoinColumn(name = "transactionFk", referencedColumnName = "id", nullable = false)
	@JsonIgnore
	private Transaction transactionFk;

	public TransactionMids() {
		super();
	}


	public TransactionMids(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Transaction getTransactionFk() {
		return transactionFk;
	}


	public void setTransactionFk(Transaction transactionFk) {
		this.transactionFk = transactionFk;
	}


	public Double getMidValue() {
		return midValue;
	}


	public void setMidValue(Double midValue) {
		this.midValue = midValue;
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
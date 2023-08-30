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
@Table(name = "transactionItem")
public class TransactionItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "amount")
	private double amount;

	@Column(name = "subServiceId")
	private long subServiceId;

	@ManyToOne
	@JoinColumn(name = "transactionFk", referencedColumnName = "id", nullable = false)
	@JsonIgnore
	private Transaction transactionFk;

	public TransactionItem() {
		super();
	}


	public TransactionItem(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public TransactionItem amount(double amount) {
		this.amount = amount;
		return this;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}


	public Transaction getTransactionFk() {
		return transactionFk;
	}


	public void setTransactionFk(Transaction transactionFk) {
		this.transactionFk = transactionFk;
	}


	public long getSubServiceId() {
		return subServiceId;
	}


	public void setSubServiceId(long subServiceId) {
		this.subServiceId = subServiceId;
	}


	
}
package com.aman.payment.core.model;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "transactionECR")
public class TransactionECR implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;

	@Column(name = "ecrResponse")
	private String ecrResponse;
	
	@Column(name = "ecrRequest")
	private String ecrRequest;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "transactionEcrFk")
	private Set<Transaction> transactions  = new HashSet<Transaction>(0);


	public TransactionECR() {
		super();
	}
	
	public TransactionECR(String ecrResponse, String ecrRequest) {
		super();
		this.ecrResponse = ecrResponse;
		this.ecrRequest = ecrRequest;
	}


	public TransactionECR(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getEcrResponse() {
		return ecrResponse;
	}


	public void setEcrResponse(String ecrResponse) {
		this.ecrResponse = ecrResponse;
	}


	public Set<Transaction> getTransactions() {
		return transactions;
	}


	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}

	public String getEcrRequest() {
		return ecrRequest;
	}

	public void setEcrRequest(String ecrRequest) {
		this.ecrRequest = ecrRequest;
	}

	

	
	
}
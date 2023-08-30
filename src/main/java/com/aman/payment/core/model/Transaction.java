package com.aman.payment.core.model;

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
import com.aman.payment.core.model.lookup.PaymentType;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Transaction.
 */
@Entity
@Table(name = "transaction", indexes = { @Index(name = "index_transCode", columnList = "transCode", unique = true),
		@Index(name = "index_createdAt", columnList = "createdAt", unique = false),
		@Index(name = "index_settlementCode", columnList = "settlementCode") })
//@NamedQueries({
//		@NamedQuery(name = "Transaction.findByNationalId", query = "SELECT c FROM Transaction c WHERE c.applicantFk.nationalId = :nationalId") })
public class Transaction extends DateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "transCode", nullable = false)
	private String transCode;

	@NotNull
	@Column(name = "settlementCode", nullable = false)
	private String settlementCode;

	@NotNull
	@Column(name = "amount")
	private double amount;

	@NotNull
	@Column(name = "serviceId")
	private long serviceId;

	@NotNull
	@Column(name = "merchantId")
	private long merchantId;

	@NotNull
	@Column(name = "posId")
	private long posId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "paymentTypeFk", referencedColumnName = "id", nullable = false)
	@JsonIgnore
	private PaymentType paymentTypeFk;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pullAccountFk", referencedColumnName = "id", nullable = false)
	@JsonIgnore
	private PullAccount pullAccountFk;

	@Column(name = "statusFk", nullable = false)
	private String statusFk;

	@Column(name = "visaNumber")
	private String visaNumber;
	
	@Column(name = "paymentCode")
	private String paymentCode;
	
	@ManyToOne
	@JoinColumn(name = "transactionEcrFk", referencedColumnName = "id", nullable = true)
	@JsonIgnore
	private TransactionECR transactionEcrFk;
	
	@Column(name = "refundParentId")
	private Long refundParentId;
	
	@Column(name = "attRefundUrl", nullable = true)
    private String attRefundUrl;
	
	@Column(name = "tax")
	private double tax;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "transactionFk")
	private Set<TransactionItem> transactionItems  = new HashSet<TransactionItem>(0);
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "transactionFk")
	private Set<TransactionMids> transactionMids  = new HashSet<TransactionMids>(0);
	
	public Transaction() {
		super();
	}


	public Transaction(String statusFk) {
		super();
		this.statusFk = statusFk;
	}

	public Transaction(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTransCode() {
		return transCode;
	}

	public Transaction transCode(String transCode) {
		this.transCode = transCode;
		return this;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public double getAmount() {
		return amount;
	}

	public Transaction amount(double amount) {
		this.amount = amount;
		return this;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public PaymentType getPaymentTypeFk() {
		return paymentTypeFk;
	}

	public Transaction paymentTypeFk(PaymentType paymentType) {
		this.paymentTypeFk = paymentType;
		return this;
	}

	public void setPaymentTypeFk(PaymentType paymentType) {
		this.paymentTypeFk = paymentType;
	}

	public String getSettlementCode() {
		return settlementCode;
	}

	public void setSettlementCode(String settlementCode) {
		this.settlementCode = settlementCode;
	}

	public String getStatusFk() {
		return statusFk;
	}

	public void setStatusFk(String statusFk) {
		this.statusFk = statusFk;
	}

	public String getVisaNumber() {
		return visaNumber;
	}

	public void setVisaNumber(String visaNumber) {
		this.visaNumber = visaNumber;
	}



	public PullAccount getPullAccountFk() {
		return pullAccountFk;
	}

	public void setPullAccountFk(PullAccount pullAccountFk) {
		this.pullAccountFk = pullAccountFk;
	}

	public Long getRefundParentId() {
		return refundParentId;
	}

	public void setRefundParentId(Long refundParentId) {
		this.refundParentId = refundParentId;
	}

	public String getAttRefundUrl() {
		return attRefundUrl;
	}

	public void setAttRefundUrl(String attRefundUrl) {
		this.attRefundUrl = attRefundUrl;
	}


	public Set<TransactionItem> getTransactionItems() {
		return transactionItems;
	}


	public void setTransactionItems(Set<TransactionItem> transactionItems) {
		this.transactionItems = transactionItems;
	}


	public long getServiceId() {
		return serviceId;
	}


	public long getMerchantId() {
		return merchantId;
	}


	public long getPosId() {
		return posId;
	}


	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}


	public void setMerchantId(long merchantId) {
		this.merchantId = merchantId;
	}


	public void setPosId(long posId) {
		this.posId = posId;
	}


	public double getTax() {
		return tax;
	}


	public void setTax(double tax) {
		this.tax = tax;
	}


	public String getPaymentCode() {
		return paymentCode;
	}


	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}


	public Set<TransactionMids> getTransactionMids() {
		return transactionMids;
	}


	public void setTransactionMids(Set<TransactionMids> transactionMids) {
		this.transactionMids = transactionMids;
	}


	public TransactionECR getTransactionEcrFk() {
		return transactionEcrFk;
	}


	public void setTransactionEcrFk(TransactionECR transactionEcrFk) {
		this.transactionEcrFk = transactionEcrFk;
	}


	
	
}

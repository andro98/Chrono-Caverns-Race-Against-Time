package com.aman.payment.maazoun.model;

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

import com.aman.payment.auth.model.Pos;
import com.aman.payment.auth.model.Sector;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A MaazounRequestInfo.
 */
@Entity
@Table(name = "maazoun_book_request_info",
indexes = { 
		@Index(name = "idx_maazoun_book_request_info_transactionCode", columnList="transactionCode", unique = true),
		@Index(name = "idx_maazoun_book_request_info_refRequestNumber", columnList="refRequestNumber", unique = false),
		@Index(name = "idx_maazoun_book_request_info_createdAt", columnList="createdAt", unique = false),
		@Index(name = "idx_maazoun_book_request_info_statusFk", columnList="statusFk", unique = false),
		@Index(name = "idx_maazoun_book_request_info_bookSerialNumber", columnList="bookSerialNumber", unique = false)
		})
public class MaazounBookRequestInfo extends MaazounDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "transactionCode", nullable = true)
	private String transactionCode;
	
	@Column(name = "refRequestNumber", nullable = true)
	private String refRequestNumber;
	
	@Column(name = "statusFk", nullable = false)
	private String statusFk;

	@Column(name = "receiptUrl")
	private String receiptUrl;
	
	@Column(name = "refundAttachUrl")
	private String refundAttachUrl;
	
	@Column(name = "amount")
	private double amount;
	
	@Column(name = "visaNumber")
	private String visaNumber;
	
	@Column(name = "paymentMethod")
	private String paymentMethod;
	
	@Column(name = "paymentCode")
	private String paymentCode;
	
	@Column(name = "bookSerialNumber")
	private String bookSerialNumber;
	
	@Column(name = "locationId")
	public Long locationId;
	
	@Column(name = "bookTypeId")
    private Long bookTypeId;
	
	@Column(name = "bookTypeName")
    private String bookTypeName;
	
	@Column(name = "maazouniaId")
    private Long maazouniaId;
	
	@Column(name = "maazouniaName")
    private String maazouniaName;
	
	@Column(name = "paymentProcessFlag")
    private Integer paymentProcessFlag;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "posFk", referencedColumnName = "id", nullable = true)
	@JsonIgnore
	private Pos posFk;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sectorFk", referencedColumnName = "id", nullable = true)
	@JsonIgnore
	private Sector sectorFk;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maazounProfileFk", referencedColumnName = "id", nullable = true)
	@JsonIgnore
	private MaazounProfile maazounProfileFk;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "maazounBookRequestInfoFk")
	private Set<MaazounBookWarehouse> maazounBookWarehouseSet  = new HashSet<MaazounBookWarehouse>(0);


	public MaazounBookRequestInfo() {
		super();
	}

	public MaazounBookRequestInfo(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getStatusFk() {
		return statusFk;
	}

	public void setStatusFk(String statusFk) {
		this.statusFk = statusFk;
	}

	public String getReceiptUrl() {
		return receiptUrl;
	}

	public void setReceiptUrl(String receiptUrl) {
		this.receiptUrl = receiptUrl;
	}

	public Pos getPosFk() {
		return posFk;
	}

	public void setPosFk(Pos posFk) {
		this.posFk = posFk;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public MaazounProfile getMaazounProfileFk() {
		return maazounProfileFk;
	}

	public void setMaazounProfileFk(MaazounProfile maazounProfileFk) {
		this.maazounProfileFk = maazounProfileFk;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBookSerialNumber() {
		return bookSerialNumber;
	}

	public void setBookSerialNumber(String bookSerialNumber) {
		this.bookSerialNumber = bookSerialNumber;
	}

	public String getRefRequestNumber() {
		return refRequestNumber;
	}

	public void setRefRequestNumber(String refRequestNumber) {
		this.refRequestNumber = refRequestNumber;
	}

	public String getVisaNumber() {
		return visaNumber;
	}

	public void setVisaNumber(String visaNumber) {
		this.visaNumber = visaNumber;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public Long getBookTypeId() {
		return bookTypeId;
	}

	public void setBookTypeId(Long bookTypeId) {
		this.bookTypeId = bookTypeId;
	}

	public Sector getSectorFk() {
		return sectorFk;
	}

	public void setSectorFk(Sector sectorFk) {
		this.sectorFk = sectorFk;
	}

	public String getBookTypeName() {
		return bookTypeName;
	}

	public void setBookTypeName(String bookTypeName) {
		this.bookTypeName = bookTypeName;
	}

	public Long getMaazouniaId() {
		return maazouniaId;
	}

	public void setMaazouniaId(Long maazouniaId) {
		this.maazouniaId = maazouniaId;
	}

	public String getMaazouniaName() {
		return maazouniaName;
	}

	public void setMaazouniaName(String maazouniaName) {
		this.maazouniaName = maazouniaName;
	}

	public Set<MaazounBookWarehouse> getMaazounBookWarehouseSet() {
		return maazounBookWarehouseSet;
	}

	public void setMaazounBookWarehouseSet(Set<MaazounBookWarehouse> maazounBookWarehouseSet) {
		this.maazounBookWarehouseSet = maazounBookWarehouseSet;
	}

	public Integer getPaymentProcessFlag() {
		return paymentProcessFlag;
	}

	public void setPaymentProcessFlag(Integer paymentProcessFlag) {
		this.paymentProcessFlag = paymentProcessFlag;
	}

	public String getRefundAttachUrl() {
		return refundAttachUrl;
	}

	public void setRefundAttachUrl(String refundAttachUrl) {
		this.refundAttachUrl = refundAttachUrl;
	}


	
}
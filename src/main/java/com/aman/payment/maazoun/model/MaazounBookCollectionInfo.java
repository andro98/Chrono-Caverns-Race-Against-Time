package com.aman.payment.maazoun.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.aman.payment.auth.model.Pos;
import com.aman.payment.auth.model.Sector;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A MaazounReturnInfo.
 */
@Entity
@Table(name = "maazoun_book_collection_info",
indexes = { 
		@Index(name = "idx_maazoun_book_collection_info_transactionCode", columnList="transactionCode", unique = true),
		@Index(name = "idx_maazoun_book_request_info_refRequestNumber", columnList="refRequestNumber", unique = false),
		@Index(name = "idx_maazoun_book_collection_info_createdAt", columnList="createdAt", unique = false),
		@Index(name = "idx_maazoun_book_collection_info_statusFk", columnList="statusFk", unique = false),
		@Index(name = "idx_maazoun_book_collection_info_contractNumber", columnList="contractNumber", unique = false)
		})
public class MaazounBookCollectionInfo extends MaazounDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "transactionCode", nullable = true)
	private String transactionCode;
	
	@Column(name = "statusFk")
	private String statusFk;
	
	@Column(name = "isReviewed")
    private Boolean isReviewed;
	
	@Column(name = "imageUrl")
    private String imageUrl;
	
	@Column(name = "receiptUrl")
	private String receiptUrl;
	
	@Column(name = "feeAmount")
	private Double feeAmount;
	
	@Column(name = "contractPaidAmount")
	private Double contractPaidAmount;
	
	@Column(name = "contractPaidAdvanceFees")
	private Double contractPaidAdvanceFees;
	
	@Column(name = "contractPaidDelayedFees")
	private Double contractPaidDelayedFees;
	
	@Column(name = "contractPaidConditionsFees")
	private Double contractPaidConditionsFees;
	
	@Column(name = "contractPaidBetweenusFees")
	private Double contractPaidBetweenusFees;

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public Date husbandNationalIdExpiryDate;
    
    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public Date wifeNationalIdExpiryDate;
    
    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public Date contractDate;

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public Date reviewedAt;
    
    @Column(name = "reviewedBy")
    public String reviewedBy;
    
    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public Date collectedAt;
    
    @Column(name = "collectedBy")
    public String collectedBy;
    
    @Column(name = "refundAttUrl")
    public String refundAttUrl;
    
    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public Date receivedAt;
    
    @Column(name = "receivedBy")
    public String receivedBy;
    
    @Column(name = "receivedStatus")
    public String receivedStatus;
    
    @Column(name = "receivedAttUrl")
    public String receivedAttUrl;
	
	@Column(name = "bookSerialNumber")
	private String bookSerialNumber;
	
	@Column(name = "contractNumber")
	private String contractNumber;
	
	@Column(name = "refRequestNumber")
	private String refRequestNumber;
	
	@Column(name = "husbandName")
	private String husbandName;
	
	@Column(name = "wifeName")
	private String wifeName;
	
	@Column(name = "contractTypeId")
    private Long contractTypeId;
	
	@Column(name = "contractTypeName")
    private String contractTypeName;
	
	@Column(name = "paymentMethod")
	private String paymentMethod;
	
	@Column(name = "paymentCode")
	private String paymentCode;
	
	@Column(name = "visaNumber")
	private String visaNumber;
	
	@Column(name = "husbandNationalId")
	private String husbandNationalId;
	
	@Column(name = "wifeNationalId")
	private String wifeNationalId;
	
	@Column(name = "receivedComment")
	public String receivedComment;
	
	@Column(name = "locationId")
	public Long locationId;
	
	@Column(name = "maazounNationalId")
	private String maazounNationalId;
	
	@Column(name = "paymentProcessFlag")
    private Integer paymentProcessFlag;
	
	@Column(name = "talakStatus")
	private String talakStatus;
	
	@Column(name = "talakCount")
	private String talakCount;
	
	@Column(name = "wifeRepresentedStatus")
	private String wifeRepresentedStatus;
	
	@Column(name = "talakType")
	private String talakType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maazounProfileFk", referencedColumnName = "id", nullable = true)
	@JsonIgnore
	private MaazounProfile maazounProfileFk;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "posFk", referencedColumnName = "id", nullable = true)
	@JsonIgnore
	private Pos posFk;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sectorFk", referencedColumnName = "id", nullable = true)
	@JsonIgnore
	private Sector sectorFk;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "maazounBookCollectionInfoFk")
	private Set<MaazounBookWarehouse> maazounBookWarehouseSet  = new HashSet<MaazounBookWarehouse>(0);
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "maazounBookCollectionInfoFk")
	private Set<MaazounBookValidation> maazounBookValidationSet  = new HashSet<MaazounBookValidation>(0);
	

	public MaazounBookCollectionInfo() {
		super();
	}

	public MaazounBookCollectionInfo(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
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

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public Set<MaazounBookWarehouse> getMaazounBookWarehouseSet() {
		return maazounBookWarehouseSet;
	}

	public void setMaazounBookWarehouseSet(Set<MaazounBookWarehouse> maazounBookWarehouseSet) {
		this.maazounBookWarehouseSet = maazounBookWarehouseSet;
	}

	public String getStatusFk() {
		return statusFk;
	}

	public void setStatusFk(String statusFk) {
		this.statusFk = statusFk;
	}

	public String getBookSerialNumber() {
		return bookSerialNumber;
	}

	public void setBookSerialNumber(String bookSerialNumber) {
		this.bookSerialNumber = bookSerialNumber;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getRefRequestNumber() {
		return refRequestNumber;
	}

	public void setRefRequestNumber(String refRequestNumber) {
		this.refRequestNumber = refRequestNumber;
	}

	public Boolean getIsReviewed() {
		return isReviewed;
	}

	public void setIsReviewed(Boolean isReviewed) {
		this.isReviewed = isReviewed;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getReceiptUrl() {
		return receiptUrl;
	}

	public void setReceiptUrl(String receiptUrl) {
		this.receiptUrl = receiptUrl;
	}

	public Double getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(Double feeAmount) {
		this.feeAmount = feeAmount;
	}

	public Double getContractPaidAmount() {
		return contractPaidAmount;
	}

	public void setContractPaidAmount(Double contractPaidAmount) {
		this.contractPaidAmount = contractPaidAmount;
	}

	public Double getContractPaidAdvanceFees() {
		return contractPaidAdvanceFees;
	}

	public void setContractPaidAdvanceFees(Double contractPaidAdvanceFees) {
		this.contractPaidAdvanceFees = contractPaidAdvanceFees;
	}

	public Double getContractPaidDelayedFees() {
		return contractPaidDelayedFees;
	}

	public void setContractPaidDelayedFees(Double contractPaidDelayedFees) {
		this.contractPaidDelayedFees = contractPaidDelayedFees;
	}

	public Double getContractPaidConditionsFees() {
		return contractPaidConditionsFees;
	}

	public void setContractPaidConditionsFees(Double contractPaidConditionsFees) {
		this.contractPaidConditionsFees = contractPaidConditionsFees;
	}

	public Double getContractPaidBetweenusFees() {
		return contractPaidBetweenusFees;
	}

	public void setContractPaidBetweenusFees(Double contractPaidBetweenusFees) {
		this.contractPaidBetweenusFees = contractPaidBetweenusFees;
	}

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	public Date getReviewedAt() {
		return reviewedAt;
	}

	public void setReviewedAt(Date reviewedAt) {
		this.reviewedAt = reviewedAt;
	}

	public String getReceivedBy() {
		return receivedBy;
	}

	public void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}

	public String getReceivedStatus() {
		return receivedStatus;
	}

	public void setReceivedStatus(String receivedStatus) {
		this.receivedStatus = receivedStatus;
	}

	public String getReceivedAttUrl() {
		return receivedAttUrl;
	}

	public void setReceivedAttUrl(String receivedAttUrl) {
		this.receivedAttUrl = receivedAttUrl;
	}

	public Date getReceivedAt() {
		return receivedAt;
	}

	public void setReceivedAt(Date receivedAt) {
		this.receivedAt = receivedAt;
	}

	public String getReviewedBy() {
		return reviewedBy;
	}

	public void setReviewedBy(String reviewedBy) {
		this.reviewedBy = reviewedBy;
	}

	public String getHusbandName() {
		return husbandName;
	}

	public void setHusbandName(String husbandName) {
		this.husbandName = husbandName;
	}

	public String getWifeName() {
		return wifeName;
	}

	public void setWifeName(String wifeName) {
		this.wifeName = wifeName;
	}


	public Long getContractTypeId() {
		return contractTypeId;
	}

	public void setContractTypeId(Long contractTypeId) {
		this.contractTypeId = contractTypeId;
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

	public String getHusbandNationalId() {
		return husbandNationalId;
	}

	public void setHusbandNationalId(String husbandNationalId) {
		this.husbandNationalId = husbandNationalId;
	}

	public String getWifeNationalId() {
		return wifeNationalId;
	}

	public void setWifeNationalId(String wifeNationalId) {
		this.wifeNationalId = wifeNationalId;
	}

	public String getReceivedComment() {
		return receivedComment;
	}

	public void setReceivedComment(String receivedComment) {
		this.receivedComment = receivedComment;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public Pos getPosFk() {
		return posFk;
	}

	public void setPosFk(Pos posFk) {
		this.posFk = posFk;
	}

	public Sector getSectorFk() {
		return sectorFk;
	}

	public void setSectorFk(Sector sectorFk) {
		this.sectorFk = sectorFk;
	}

	public Set<MaazounBookValidation> getMaazounBookValidationSet() {
		return maazounBookValidationSet;
	}

	public void setMaazounBookValidationSet(Set<MaazounBookValidation> maazounBookValidationSet) {
		this.maazounBookValidationSet = maazounBookValidationSet;
	}

	public String getMaazounNationalId() {
		return maazounNationalId;
	}

	public void setMaazounNationalId(String maazounNationalId) {
		this.maazounNationalId = maazounNationalId;
	}

	public String getVisaNumber() {
		return visaNumber;
	}

	public void setVisaNumber(String visaNumber) {
		this.visaNumber = visaNumber;
	}

	public String getContractTypeName() {
		return contractTypeName;
	}

	public void setContractTypeName(String contractTypeName) {
		this.contractTypeName = contractTypeName;
	}

	public Integer getPaymentProcessFlag() {
		return paymentProcessFlag;
	}

	public void setPaymentProcessFlag(Integer paymentProcessFlag) {
		this.paymentProcessFlag = paymentProcessFlag;
	}

	public String getRefundAttUrl() {
		return refundAttUrl;
	}

	public void setRefundAttUrl(String refundAttUrl) {
		this.refundAttUrl = refundAttUrl;
	}

	public Date getHusbandNationalIdExpiryDate() {
		return husbandNationalIdExpiryDate;
	}

	public void setHusbandNationalIdExpiryDate(Date husbandNationalIdExpiryDate) {
		this.husbandNationalIdExpiryDate = husbandNationalIdExpiryDate;
	}

	public Date getWifeNationalIdExpiryDate() {
		return wifeNationalIdExpiryDate;
	}

	public void setWifeNationalIdExpiryDate(Date wifeNationalIdExpiryDate) {
		this.wifeNationalIdExpiryDate = wifeNationalIdExpiryDate;
	}

	public Date getCollectedAt() {
		return collectedAt;
	}

	public void setCollectedAt(Date collectedAt) {
		this.collectedAt = collectedAt;
	}

	public String getCollectedBy() {
		return collectedBy;
	}

	public void setCollectedBy(String collectedBy) {
		this.collectedBy = collectedBy;
	}

	public String getTalakStatus() {
		return talakStatus;
	}

	public void setTalakStatus(String talakStatus) {
		this.talakStatus = talakStatus;
	}

	public String getTalakCount() {
		return talakCount;
	}

	public void setTalakCount(String talakCount) {
		this.talakCount = talakCount;
	}

	public String getWifeRepresentedStatus() {
		return wifeRepresentedStatus;
	}

	public void setWifeRepresentedStatus(String wifeRepresentedStatus) {
		this.wifeRepresentedStatus = wifeRepresentedStatus;
	}

	public String getTalakType() {
		return talakType;
	}

	public void setTalakType(String talakType) {
		this.talakType = talakType;
	}
	
	
 
}
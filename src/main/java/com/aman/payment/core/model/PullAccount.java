package com.aman.payment.core.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.aman.payment.core.model.audit.DateAudit;

/**
 * A PullAccount.
 */
@Entity
@Table(name = "pullAccount", 
indexes = { 
		@Index(name = "index_pullAccount_createdBy", columnList="createdBy", unique = false),
		@Index(name = "index_pullAccount_createdAt", columnList="createdAt", unique = false),
		@Index(name = "index_pullAccount_statusFk", columnList="statusFk", unique = false),
		@Index(name = "index_pullAccount_settlementCode", columnList="settlementCode", unique = false)
		},
uniqueConstraints={
		 @UniqueConstraint( name = "idx_createdBy_settlementCode_serviceId",  columnNames ={"settlementCode", "createdBy", "serviceId"})
		 })
@NamedQueries({
    @NamedQuery(name = "PullAccount.findByCurrentMonth", query = "SELECT c FROM PullAccount c WHERE c.createdAt >= :createdFrom AND c.createdAt <= :createdTo AND c.createdBy = :createdBy AND c.statusFk = :statusFk")
//    @NamedQuery(name = "PullAccount.findByStatusFkAndPosId", query = "SELECT c FROM PullAccount c WHERE c.statusFk = :statusFk AND c.posId in :posId ")
    })

public class PullAccount extends DateAudit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "totalTransaction", columnDefinition = "integer default 0")
    private long totalTransaction;
    
    @Column(name = "totalSettlementTransaction", columnDefinition = "integer default 0")
    private long totalSettlementTransaction;
    
    @Column(name = "totalCollectionTransaction", columnDefinition = "integer default 0")
    private long totalCollectionTransaction;
    
    @Column(name = "totalRefundTransaction", columnDefinition = "integer default 0")
    private long totalRefundTransaction;
    
    @Column(name = "totalCancelledTransaction", columnDefinition = "integer default 0")
    private long totalCancelledTransaction;
    
    @NotNull
    @Column(name = "settlementCode", nullable = false)
    private String settlementCode;

    @Column(name = "systemAmountCash", columnDefinition = "double default 0")
    private double systemAmountCash;
    
    @Column(name = "systemAmountVisa", columnDefinition = "double default 0")
    private double systemAmountVisa;
    
    @Column(name = "systemTotalAmount", columnDefinition = "double default 0")
    private double systemTotalAmount;
    
    @Column(name = "systemTotalSettlementAmount", columnDefinition = "double default 0")
    private double systemTotalSettlementAmount;
    
    @Column(name = "systemTotalCollectionAmount", columnDefinition = "double default 0")
    private double systemTotalCollectionAmount;
    
    @Column(name = "systemTotalCancelledAmount", columnDefinition = "double default 0")
    private double systemTotalCancelledAmount;

    @Column(name = "systemAmountRefund", columnDefinition = "double default 0")
    private double systemAmountRefund;
    
    @Column(name = "depositCash", columnDefinition = "double default 0")
    private double depositCash;
    
    @Column(name = "depositVisa", columnDefinition = "double default 0")
    private double depositVisa;
    
    @Column(name = "depositTotalAmount", columnDefinition = "double default 0")
    private double depositTotalAmount;
    
    @Column(name = "attBankUrl", nullable = true)
    private String attBankUrl;
    
    @Column(name = "attVisaUrl", nullable = true)
    private String attVisaUrl;
    
    @Column(name = "isDeficit", columnDefinition = "boolean default false")
    private Boolean isDeficit;
    
    @Column(name = "deficitAmount", columnDefinition = "double default 0")
    private double deficitAmount;
    
    @Column(name = "isOver", columnDefinition = "boolean default false")
    private Boolean isOver;
    
    @Column(name = "overAmount", columnDefinition = "double default 0")
    private double overAmount;
    
    @Column(name = "approvedAt")
    public Date approvedAt;
    
    @Column(name = "approvedBy")
    public String approvedBy;
    
    @Column(name = "approvedComment")
    public String approvedComment;
    
    @Column(name = "deficitStatus")
    public String deficitStatus;

    @Column(name = "statusFk", nullable = false)
    private String statusFk;

    @NotNull
	@Column(name = "posId")
	private long posId;
    
    @NotNull
	@Column(name = "serviceId")
	private long serviceId;
    
    @Column(name = "locationCode", nullable = false)
    private String locationCode;
    
    @Column(name = "locationName", nullable = false)
    private String locationName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pullAccountFk")
	private Set<Transaction> transactions  = new HashSet<Transaction>(0);
    
	public PullAccount() {
		super();
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSettlementCode() {
		return settlementCode;
	}

	public void setSettlementCode(String settlementCode) {
		this.settlementCode = settlementCode;
	}

	public double getSystemAmountCash() {
		return systemAmountCash;
	}

	public void setSystemAmountCash(double systemAmountCash) {
		this.systemAmountCash = systemAmountCash;
	}

	public double getSystemAmountVisa() {
		return systemAmountVisa;
	}

	public void setSystemAmountVisa(double systemAmountVisa) {
		this.systemAmountVisa = systemAmountVisa;
	}

	

	public double getSystemTotalAmount() {
		return systemTotalAmount;
	}

	public void setSystemTotalAmount(double systemTotalAmount) {
		this.systemTotalAmount = systemTotalAmount;
	}

	public double getSystemAmountRefund() {
		return systemAmountRefund;
	}

	public void setSystemAmountRefund(double systemAmountRefund) {
		this.systemAmountRefund = systemAmountRefund;
	}

	

	public double getDepositCash() {
		return depositCash;
	}

	public void setDepositCash(double depositCash) {
		this.depositCash = depositCash;
	}

	public double getDepositVisa() {
		return depositVisa;
	}

	public void setDepositVisa(double depositVisa) {
		this.depositVisa = depositVisa;
	}

	public String getStatusFk() {
		return statusFk;
	}

	public void setStatusFk(String statusFk) {
		this.statusFk = statusFk;
	}

	public long getTotalTransaction() {
		return totalTransaction;
	}

	public void setTotalTransaction(long totalTransaction) {
		this.totalTransaction = totalTransaction;
	}

	

	public String getAttBankUrl() {
		return attBankUrl;
	}

	public void setAttBankUrl(String attBankUrl) {
		this.attBankUrl = attBankUrl;
	}

	public String getAttVisaUrl() {
		return attVisaUrl;
	}

	public void setAttVisaUrl(String attVisaUrl) {
		this.attVisaUrl = attVisaUrl;
	}

	public double getDepositTotalAmount() {
		return depositTotalAmount;
	}

	public void setDepositTotalAmount(double depositTotalAmount) {
		this.depositTotalAmount = depositTotalAmount;
	}

	public Boolean getIsDeficit() {
		return isDeficit;
	}

	public void setIsDeficit(Boolean isDeficit) {
		this.isDeficit = isDeficit;
	}

	public double getDeficitAmount() {
		return deficitAmount;
	}

	public void setDeficitAmount(double deficitAmount) {
		this.deficitAmount = deficitAmount;
	}

	public Date getApprovedAt() {
		return approvedAt;
	}

	public void setApprovedAt(Date approvedAt) {
		this.approvedAt = approvedAt;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getApprovedComment() {
		return approvedComment;
	}

	public void setApprovedComment(String approvedComment) {
		this.approvedComment = approvedComment;
	}


	public Boolean getIsOver() {
		return isOver;
	}


	public void setIsOver(Boolean isOver) {
		this.isOver = isOver;
	}


	public double getOverAmount() {
		return overAmount;
	}


	public void setOverAmount(double overAmount) {
		this.overAmount = overAmount;
	}


	public String getDeficitStatus() {
		return deficitStatus;
	}


	public void setDeficitStatus(String deficitStatus) {
		this.deficitStatus = deficitStatus;
	}


	public Set<Transaction> getTransactions() {
		return transactions;
	}


	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}


	public long getTotalCollectionTransaction() {
		return totalCollectionTransaction;
	}


	public void setTotalCollectionTransaction(long totalCollectionTransaction) {
		this.totalCollectionTransaction = totalCollectionTransaction;
	}


	public long getTotalRefundTransaction() {
		return totalRefundTransaction;
	}


	public void setTotalRefundTransaction(long totalRefundTransaction) {
		this.totalRefundTransaction = totalRefundTransaction;
	}


	public long getTotalCancelledTransaction() {
		return totalCancelledTransaction;
	}


	public void setTotalCancelledTransaction(long totalCancelledTransaction) {
		this.totalCancelledTransaction = totalCancelledTransaction;
	}


	public long getTotalSettlementTransaction() {
		return totalSettlementTransaction;
	}


	public void setTotalSettlementTransaction(long totalSettlementTransaction) {
		this.totalSettlementTransaction = totalSettlementTransaction;
	}


	public double getSystemTotalSettlemetAmount() {
		return systemTotalSettlementAmount;
	}


	public void setSystemTotalSettlemetAmount(double systemTotalSettlemetAmount) {
		this.systemTotalSettlementAmount = systemTotalSettlemetAmount;
	}


	public long getPosId() {
		return posId;
	}


	public void setPosId(long posId) {
		this.posId = posId;
	}


	public String getLocationCode() {
		return locationCode;
	}


	public String getLocationName() {
		return locationName;
	}


	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}


	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}


	public double getSystemTotalCollectionAmount() {
		return systemTotalCollectionAmount;
	}


	public double getSystemTotalCancelledAmount() {
		return systemTotalCancelledAmount;
	}


	public void setSystemTotalCollectionAmount(double systemTotalCollectionAmount) {
		this.systemTotalCollectionAmount = systemTotalCollectionAmount;
	}


	public void setSystemTotalCancelledAmount(double systemTotalCancelledAmount) {
		this.systemTotalCancelledAmount = systemTotalCancelledAmount;
	}


	public double getSystemTotalSettlementAmount() {
		return systemTotalSettlementAmount;
	}


	public long getServiceId() {
		return serviceId;
	}


	public void setSystemTotalSettlementAmount(double systemTotalSettlementAmount) {
		this.systemTotalSettlementAmount = systemTotalSettlementAmount;
	}


	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}


}

package com.aman.payment.core.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.aman.payment.core.model.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A FinancialDeficit.
 */
@Entity
@Table(name = "financialDeficit")
public class FinancialDeficit extends DateAudit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    

    @Column(name = "deficitAmount", nullable = true)
    private double deficitAmount;
    
    @Column(name = "attUrl", nullable = true)
    private String attUrl;

    @OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pullAccountFk", referencedColumnName = "id", nullable = true)
    @JsonIgnore
    private PullAccount pullAccountFk;

    @Column(name = "statusFk")
    private String statusFk;
    
    @Column(name = "approvedAt")
    public Date approvedAt;
    
    @Column(name = "approvedBy")
    public String approvedBy;
    
    @Column(name = "approvedComment")
    public String approvedComment;
    
	@Column(name = "posId")
	private long posId;
	
	@Column(name = "serviceId")
	private long serviceId;

	public FinancialDeficit() {
		super();
	}
	
	public FinancialDeficit(PullAccount obj, double deficitAmount, String attUrl) {
		super();
		this.deficitAmount = deficitAmount;
		this.pullAccountFk = obj;
		this.createdBy = obj.getCreatedBy();
		this.attUrl = attUrl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getDeficitAmount() {
		return deficitAmount;
	}

	public void setDeficitAmount(double deficitAmount) {
		this.deficitAmount = deficitAmount;
	}

	public PullAccount getPullAccountFk() {
		return pullAccountFk;
	}

	public void setPullAccountFk(PullAccount pullAccountFk) {
		this.pullAccountFk = pullAccountFk;
	}

	public String getStatusFk() {
		return statusFk;
	}

	public void setStatusFk(String statusFk) {
		this.statusFk = statusFk;
	}

	public String getAttUrl() {
		return attUrl;
	}

	public void setAttUrl(String attUrl) {
		this.attUrl = attUrl;
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

	public long getPosId() {
		return posId;
	}

	public void setPosId(long posId) {
		this.posId = posId;
	}

	public long getServiceId() {
		return serviceId;
	}

	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}

	
	

}

package com.aman.payment.maazoun.model;

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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.Nullable;

import com.aman.payment.auth.model.Sector;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "supply_order_details")

public class SupplyOrderDetails implements Serializable {
 
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
	 
	
	@Nullable
	@Column(name = "bookTypeFK")
	private String bookTypeFK;
	
	@Nullable
	@Column(name = "bookType")
	private String bookType;	

	@Nullable
	@Column(name = "bookTypeCount")
	private String bookTypeCount;

	@Nullable
	@Column(name = "currentBookTypeCount")
	private int currentBookTypeCount;
	
	@Nullable
	@Column(name = "remainingBookTypeCount", columnDefinition = "integer default 0")
	private int remainingBookTypeCount;

	@Nullable
	@Column(name = "sectorFK")
	private String sectorFK;
	
//	@Nullable
	@Column(name = "sectorName")
	private String sectorName;
	
	@CreatedDate
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date createdAt;

    @CreatedBy
    @Column(nullable = false, updatable = false)
    public String createdBy;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "supplyOrderFk", referencedColumnName = "id", nullable = true)
	@JsonIgnore
	private SupplyOrder supplyOrderFk;

    
	public SupplyOrderDetails() {
		super();
	}

	public int getCurrentBookTypeCount() {
		return currentBookTypeCount;
	}

	public void setCurrentBookTypeCount(int currentBookTypeCount) {
		this.currentBookTypeCount = currentBookTypeCount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBookTypeFK() {
		return bookTypeFK;
	}

	public void setBookTypeFK(String bookTypeFK) {
		this.bookTypeFK = bookTypeFK;
	}

	public String getBookType() {
		return bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}

	public String getBookTypeCount() {
		return bookTypeCount;
	}

	public void setBookTypeCount(String bookTypeCount) {
		this.bookTypeCount = bookTypeCount;
	}

	public String getSectorFK() {
		return sectorFK;
	}

	public void setSectorFK(String sectorFK) {
		this.sectorFK = sectorFK;
	}

	public String getSectorName() {
		return sectorName;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public SupplyOrder getSupplyOrderFk() {
		return supplyOrderFk;
	}

	public void setSupplyOrderFk(SupplyOrder supplyOrderFk) {
		this.supplyOrderFk = supplyOrderFk;
	}

	public int getRemainingBookTypeCount() {
		return remainingBookTypeCount;
	}

	public void setRemainingBookTypeCount(int remainingBookTypeCount) {
		this.remainingBookTypeCount = remainingBookTypeCount;
	}
    
    

}

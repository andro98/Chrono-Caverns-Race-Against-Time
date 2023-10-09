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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.Nullable;

import com.aman.payment.auth.model.Sector;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

	@JsonIgnore
	@Column(name = "bootTierIdFK")
	private String bootTierId;
}

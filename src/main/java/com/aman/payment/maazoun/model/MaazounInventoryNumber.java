package com.aman.payment.maazoun.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.LastModifiedDate;

/**
 * A MaazounInventoryNumber.
 */
@Entity
@Table(name = "maazoun_inventory_number",
uniqueConstraints={
		 @UniqueConstraint( name = "idxInventory_sectorId_typeId",  columnNames ={"sectorId","typeId"})
		 })
public class MaazounInventoryNumber {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "year")
    private Long year;

    @NotNull
    @Column(name = "sectorId")
    private Long sectorId;

    @Column(name = "typeId")
    private String typeId;
    
    @Column(name = "inventorySequance")
    private Long inventorySequance;
    
    @LastModifiedDate
    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public Date updatedAt;
    
	public MaazounInventoryNumber(Long id) {
		super();
		this.id = id;
	}

	public MaazounInventoryNumber() {
		super();
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public Long getSectorId() {
		return sectorId;
	}

	public void setSectorId(Long sectorId) {
		this.sectorId = sectorId;
	}

	public Long getInventorySequance() {
		return inventorySequance;
	}

	public void setInventorySequance(Long inventorySequance) {
		this.inventorySequance = inventorySequance;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	
	

}

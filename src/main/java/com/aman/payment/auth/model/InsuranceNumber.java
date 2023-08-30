package com.aman.payment.auth.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 * A InsuranceNumber.
 */
@Entity
@Table(name = "insuranceNumber", 
uniqueConstraints={
		 @UniqueConstraint( name = "idx_bookTypeId_sequanceNumber_locationId",  columnNames ={"bookTypeId","sequanceNumber","locationId"})})
public class InsuranceNumber implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "serviceId")
    private long serviceId;
    
    @NotNull
    @Column(name = "locationId")
    private long locationId;
    
    @NotNull
    @Column(name = "bookTypeId")
    private long bookTypeId;
    
    @NotNull
    @Column(name = "sequanceNumber")
    private long sequanceNumber;

	public Long getId() {
		return id;
	}

	public long getServiceId() {
		return serviceId;
	}

	public long getLocationId() {
		return locationId;
	}

	public long getSequanceNumber() {
		return sequanceNumber;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}

	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}

	public void setSequanceNumber(long sequanceNumber) {
		this.sequanceNumber = sequanceNumber;
	}

	public long getBookTypeId() {
		return bookTypeId;
	}

	public void setBookTypeId(long bookTypeId) {
		this.bookTypeId = bookTypeId;
	}
    
    

}

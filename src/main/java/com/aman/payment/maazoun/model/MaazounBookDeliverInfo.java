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
 * A MaazounDeliverInfo.
 */
@Entity
@Table(name = "maazoun_book_deliver_info",
indexes = { 
		@Index(name = "idx_maazoun_deliver_info_createdAt", columnList="createdAt", unique = false),
		@Index(name = "idx_maazoun_deliver_info_statusFk", columnList="statusFk", unique = false)
		})
public class MaazounBookDeliverInfo extends MaazounDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;

	@Column(name = "receiptUrl")
	private String receiptUrl;
	
	@Column(name = "imageUrl")
    private String imageUrl;
	
	@Column(name = "statusFk", nullable = false)
	private String statusFk;
	
	@Column(name = "isReviewed", nullable = false)
    private Boolean isReviewed;
	
	@Column(name = "refRequestNumber")
	private String refRequestNumber;
	
	@Column(name = "locationId")
	public Long locationId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "posFk", referencedColumnName = "id", nullable = true)
	@JsonIgnore
	private Pos posFk;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sectorFk", referencedColumnName = "id", nullable = true)
	@JsonIgnore
	private Sector sectorFk;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "maazounBookDeliverInfoFk")
	private Set<MaazounBookWarehouse> maazounBookWarehouseSet  = new HashSet<MaazounBookWarehouse>(0);
	

	public MaazounBookDeliverInfo() {
		super();
	}

	public MaazounBookDeliverInfo(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getReceiptUrl() {
		return receiptUrl;
	}

	public void setReceiptUrl(String receiptUrl) {
		this.receiptUrl = receiptUrl;
	}

	
	public void setId(Long id) {
		this.id = id;
	}

	public String getStatusFk() {
		return statusFk;
	}

	public void setStatusFk(String statusFk) {
		this.statusFk = statusFk;
	}

	public Set<MaazounBookWarehouse> getMaazounBookWarehouseSet() {
		return maazounBookWarehouseSet;
	}

	public void setMaazounBookWarehouseSet(Set<MaazounBookWarehouse> maazounBookWarehouseSet) {
		this.maazounBookWarehouseSet = maazounBookWarehouseSet;
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


	public String getRefRequestNumber() {
		return refRequestNumber;
	}

	public void setRefRequestNumber(String refRequestNumber) {
		this.refRequestNumber = refRequestNumber;
	}

	public Pos getPosFk() {
		return posFk;
	}

	public void setPosFk(Pos posFk) {
		this.posFk = posFk;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public Sector getSectorFk() {
		return sectorFk;
	}

	public void setSectorFk(Sector sectorFk) {
		this.sectorFk = sectorFk;
	}


	
	
}
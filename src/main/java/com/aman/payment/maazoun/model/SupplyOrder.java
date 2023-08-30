package com.aman.payment.maazoun.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.aman.payment.auth.model.Pos;
import com.aman.payment.auth.model.Sector;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A SupplyOrder.
 */
@Entity
@Table(name = "supply_order", indexes = {
		@Index(name = "idx_supply_order_createdAt", columnList = "createdAt", unique = false),
		@Index(name = "idx_supply_order_statusFk", columnList = "statusFk", unique = false) })
public class SupplyOrder extends MaazounDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;

	@Column(name = "statusFk", nullable = false)
	private String statusFk;

	@Column(name = "isReviewed", nullable = false)
	private Boolean isReviewed;

	@Column(name = "imageUrl", nullable = true)
	private String imageUrl;

	@Column(name = "refSupplyOrderNumber")
	private String refSupplyOrderNumber;

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

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "supplyOrderFk", fetch = FetchType.LAZY)
	private Set<SupplyOrderDetails> supplyOrderDetailsSet = new HashSet<SupplyOrderDetails>(0);

	public SupplyOrder() {
		super();
	}

	public SupplyOrder(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
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

	public String getRefSupplyOrderNumber() {
		return refSupplyOrderNumber;
	}

	public void setRefSupplyOrderNumber(String refSupplyOrderNumber) {
		this.refSupplyOrderNumber = refSupplyOrderNumber;
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

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public Set<SupplyOrderDetails> getSupplyOrderDetailsSet() {
		return supplyOrderDetailsSet;
	}

	public void setSupplyOrderDetailsSet(Set<SupplyOrderDetails> supplyOrderDetailsSet) {
		this.supplyOrderDetailsSet = supplyOrderDetailsSet;
	}


}
package com.aman.payment.maazoun.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * A MaazounBookStockLabel.
 */
@Entity
@Table(name = "maazoun_book_stock_label",
indexes = { 
		@Index(name = "idx_maazoun_book_stock_label_labelCode", columnList="labelCode", unique = true),
		@Index(name = "idx_maazoun_book_stock_label_createdAt", columnList="createdAt", unique = false),
		@Index(name = "idx_maazoun_book_stock_label_statusFk", columnList="statusFk", unique = false)
		})
public class MaazounBookStockLabel extends MaazounDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "labelCode")
	private String labelCode;
	
	@Column(name = "statusFk")
	private String statusFk;

	@Column(name = "locationId")
	private Long locationId;
	
	@Column(name = "bookTypeId")
	private Long bookTypeId;

	@Column(name = "bookTierId")
	private long bookTierId;


	public MaazounBookStockLabel() {
		super();
	}

	public MaazounBookStockLabel(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getLabelCode() {
		return labelCode;
	}

	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}

	public String getStatusFk() {
		return statusFk;
	}

	public void setStatusFk(String statusFk) {
		this.statusFk = statusFk;
	}


	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBookTypeId() {
		return bookTypeId;
	}

	public void setBookTypeId(Long bookTypeId) {
		this.bookTypeId = bookTypeId;
	}

	public long getBookTierId() {
		return bookTierId;
	}

	public void setBookTierId(long bookTierId) {
		this.bookTierId = bookTierId;
	}
}
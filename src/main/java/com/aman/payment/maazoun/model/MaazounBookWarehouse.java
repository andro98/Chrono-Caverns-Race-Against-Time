package com.aman.payment.maazoun.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A MaazounBookWarehouse.
 */
/**
 * @author m.fisal
 *
 */
@Entity
@Table(name = "maazoun_book_warehouse",
indexes = { 
		@Index(name = "idx_maazoun_book_warehouse_serialNumber", columnList="serialNumber", unique = false),
		@Index(name = "idx_maazoun_book_warehouse_statusFk", columnList="statusFk", unique = false),
		@Index(name = "idx_maazoun_book_warehouse_contractNumber", columnList="contractNumber", unique = true),
		@Index(name = "idx_maazoun_book_warehouse_contractFinancialNumber", columnList="contractFinancialNumber", unique = true),
		@Index(name = "idx_maazoun_book_warehouse_bookFinancialNumber", columnList="bookFinancialNumber", unique = false)
		})
public class MaazounBookWarehouse implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "bookTypeId")
	private long bookTypeId;

	@Column(name = "bookTierId")
	private long bookTierId;
	
	@Column(name = "bookTypeName")
	private String bookTypeName;
	
	@Column(name = "serialNumber")
	private String serialNumber;
	
	@Column(name = "contractNumber")
	private String contractNumber;
	
	@Column(name = "statusFk", nullable = false)
	private String statusFk;
	
	@Column(name = "contractCount")
	private Long contractCount;
	
	@Column(name = "bookFinancialNumber")
	private String bookFinancialNumber;
	
	@Column(name = "contractFinancialNumber")
	private String contractFinancialNumber;
	
	@Column(name = "bookInventoryNumber")
	private Long bookInventoryNumber;
	
	@Column(name = "bookInventoryReference")
	private Long bookInventoryReference;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maazounBookSupplyOrderFk", referencedColumnName = "id", nullable = true)
	@JsonIgnore
	private MaazounBookSupplyOrder maazounBookSupplyOrderFk;

//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "maazounBookSupplyOrderFk", referencedColumnName = "id", nullable = true)
//	@JsonIgnore
//	private MaazounBookSupplyOrder maazounBookSupplyOrderFk;



//

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maazounBookRequestInfoFk", referencedColumnName = "id", nullable = true)
	@JsonIgnore
	private MaazounBookRequestInfo maazounBookRequestInfoFk;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maazounBookCollectionInfoFk", referencedColumnName = "id", nullable = true)
	@JsonIgnore
	private MaazounBookCollectionInfo maazounBookCollectionInfoFk;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maazounBookDeliverInfoFk", referencedColumnName = "id", nullable = true)
	@JsonIgnore
	private MaazounBookDeliverInfo maazounBookDeliverInfoFk;


	public MaazounBookWarehouse() {
		super();
	}

	public MaazounBookWarehouse(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getBookTypeId() {
		return bookTypeId;
	}

	public void setBookTypeId(long bookTypeId) {
		this.bookTypeId = bookTypeId;
	}

	public String getBookTypeName() {
		return bookTypeName;
	}

	public void setBookTypeName(String bookTypeName) {
		this.bookTypeName = bookTypeName;
	}

	public MaazounBookSupplyOrder getMaazounBookSupplyOrderFk() {
		return maazounBookSupplyOrderFk;
	}

	public void setMaazounBookSupplyOrderFk(MaazounBookSupplyOrder maazounBookSupplyOrderFk) {
		this.maazounBookSupplyOrderFk = maazounBookSupplyOrderFk;
	}

	public String getStatusFk() {
		return statusFk;
	}

	public void setStatusFk(String statusFk) {
		this.statusFk = statusFk;
	}

	public MaazounBookRequestInfo getMaazounBookRequestInfoFk() {
		return maazounBookRequestInfoFk;
	}

	public void setMaazounBookRequestInfoFk(MaazounBookRequestInfo maazounBookRequestInfoFk) {
		this.maazounBookRequestInfoFk = maazounBookRequestInfoFk;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public MaazounBookCollectionInfo getMaazounBookCollectionInfoFk() {
		return maazounBookCollectionInfoFk;
	}

	public void setMaazounBookCollectionInfoFk(MaazounBookCollectionInfo maazounBookCollectionInfoFk) {
		this.maazounBookCollectionInfoFk = maazounBookCollectionInfoFk;
	}

	public MaazounBookDeliverInfo getMaazounBookDeliverInfoFk() {
		return maazounBookDeliverInfoFk;
	}

	public void setMaazounBookDeliverInfoFk(MaazounBookDeliverInfo maazounBookDeliverInfoFk) {
		this.maazounBookDeliverInfoFk = maazounBookDeliverInfoFk;
	}

	public Long getContractCount() {
		return contractCount;
	}

	public void setContractCount(Long contractCount) {
		this.contractCount = contractCount;
	}

	public String getBookFinancialNumber() {
		return bookFinancialNumber;
	}

	public void setBookFinancialNumber(String bookFinancialNumber) {
		this.bookFinancialNumber = bookFinancialNumber;
	}

	public String getContractFinancialNumber() {
		return contractFinancialNumber;
	}

	public void setContractFinancialNumber(String contractFinancialNumber) {
		this.contractFinancialNumber = contractFinancialNumber;
	}

	public Long getBookInventoryNumber() {
		return bookInventoryNumber;
	}

	public void setBookInventoryNumber(Long bookInventoryNumber) {
		this.bookInventoryNumber = bookInventoryNumber;
	}

	public Long getBookInventoryReference() {
		return bookInventoryReference;
	}

	public void setBookInventoryReference(Long bookInventoryReference) {
		this.bookInventoryReference = bookInventoryReference;
	}


	public void setBookTierId(long bookTierId) {
		this.bookTierId = bookTierId;
	}

	public long getBookTierId() {
		return bookTierId;
	}
}
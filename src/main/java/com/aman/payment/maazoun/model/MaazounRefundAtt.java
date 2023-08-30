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
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

/**
 * A MaazounRefundAtt.
 */
@Entity
@Table(name = "maazoun_refund_att",
indexes = { 
		@Index(name = "idx_maazoun_refund_att_maazounBookRequestInfoId", columnList="maazounBookRequestInfoId", unique = true)
		})
public class MaazounRefundAtt implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "maazounBookRequestInfoId", nullable = false)
	private Long maazounBookRequestInfoId;

	@Nullable
	@Column(name = "refundTransactionCode")
	private String refundTransactionCode;
	
	@Column(name = "refundUrl")
	private String refundUrl;
	
	
	public MaazounRefundAtt() {
		super();
	}

	public MaazounRefundAtt(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}


	public String getRefundTransactionCode() {
		return refundTransactionCode;
	}

	public String getRefundUrl() {
		return refundUrl;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRefundTransactionCode(String refundTransactionCode) {
		this.refundTransactionCode = refundTransactionCode;
	}

	public void setRefundUrl(String refundUrl) {
		this.refundUrl = refundUrl;
	}

	public Long getMaazounBookRequestInfoId() {
		return maazounBookRequestInfoId;
	}

	public void setMaazounBookRequestInfoId(Long maazounBookRequestInfoId) {
		this.maazounBookRequestInfoId = maazounBookRequestInfoId;
	}

	
	
	
	
}
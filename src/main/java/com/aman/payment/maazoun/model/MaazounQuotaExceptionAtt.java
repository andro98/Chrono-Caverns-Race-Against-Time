package com.aman.payment.maazoun.model;

import java.io.Serializable;

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

import com.aman.payment.core.model.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A MaazounQuotaExceptionAtt.
 */
@Entity
@Table(name = "maazoun_quota_exception_att")
public class MaazounQuotaExceptionAtt extends DateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "exceptionUrl")
	private String exceptionUrl;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maazounProfileFk", referencedColumnName = "id", nullable = true)
	@JsonIgnore
	private MaazounProfile maazounProfileFk;
	
	
	public MaazounQuotaExceptionAtt() {
		super();
	}

	public MaazounQuotaExceptionAtt(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExceptionUrl() {
		return exceptionUrl;
	}

	public void setExceptionUrl(String exceptionUrl) {
		this.exceptionUrl = exceptionUrl;
	}

	public MaazounProfile getMaazounProfileFk() {
		return maazounProfileFk;
	}

	public void setMaazounProfileFk(MaazounProfile maazounProfileFk) {
		this.maazounProfileFk = maazounProfileFk;
	}

	
	
	
	
}
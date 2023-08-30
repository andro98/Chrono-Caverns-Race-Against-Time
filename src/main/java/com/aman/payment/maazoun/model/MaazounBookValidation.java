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

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A MaazounBookValidation.
 */
@Entity
@Table(name = "maazoun_book_validation")
public class MaazounBookValidation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;

	@Column(name = "valueDesc")
	private Boolean valueDesc;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maazounBookCollectionInfoFk", referencedColumnName = "id", nullable = true)
	@JsonIgnore
	private MaazounBookCollectionInfo maazounBookCollectionInfoFk;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maazounCheckListLookupFk", referencedColumnName = "id", nullable = true)
	@JsonIgnore
	private MaazounCheckListLookup maazounCheckListLookupFk;

	public MaazounBookValidation() {
		super();
	}

	public MaazounBookValidation(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public Boolean isValueDesc() {
		return valueDesc;
	}

	public void setValueDesc(Boolean valueDesc) {
		this.valueDesc = valueDesc;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MaazounBookCollectionInfo getMaazounBookCollectionInfoFk() {
		return maazounBookCollectionInfoFk;
	}

	public void setMaazounBookCollectionInfoFk(MaazounBookCollectionInfo maazounBookCollectionInfoFk) {
		this.maazounBookCollectionInfoFk = maazounBookCollectionInfoFk;
	}

	public MaazounCheckListLookup getMaazounCheckListLookupFk() {
		return maazounCheckListLookupFk;
	}

	public void setMaazounCheckListLookupFk(MaazounCheckListLookup maazounCheckListLookupFk) {
		this.maazounCheckListLookupFk = maazounCheckListLookupFk;
	}

}
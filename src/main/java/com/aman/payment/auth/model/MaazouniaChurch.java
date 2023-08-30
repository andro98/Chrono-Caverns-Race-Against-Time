package com.aman.payment.auth.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.aman.payment.core.model.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A MaazouniaChurch.
 */
@Entity
@Table(name = "maazouniaChurch")
public class MaazouniaChurch extends DateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "statusFk")
	private String statusFk;

	@Column(name = "maazouniaType")
	private String maazouniaType;

	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	public Date maazouniaCreationDate;

	@Column(name = "creationAtt")
	private String creationAtt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sectorFk", referencedColumnName = "id", nullable = true)
	@JsonIgnore
	private Sector sectorFk;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "maazouniaChurchFk")
	private Set<MaazounMaazouniaChurch> maazounMaazouniaChurch = new HashSet<MaazounMaazouniaChurch>(0);

	public MaazouniaChurch() {
		super();
	}

	public MaazouniaChurch(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<MaazounMaazouniaChurch> getMaazounMaazouniaChurch() {
		return maazounMaazouniaChurch;
	}

	public void setMaazounMaazouniaChurch(Set<MaazounMaazouniaChurch> maazounMaazouniaChurch) {
		this.maazounMaazouniaChurch = maazounMaazouniaChurch;
	}

	public Sector getSectorFk() {
		return sectorFk;
	}

	public void setSectorFk(Sector sectorFk) {
		this.sectorFk = sectorFk;
	}

	public String getStatusFk() {
		return statusFk;
	}

	public void setStatusFk(String statusFk) {
		this.statusFk = statusFk;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMaazouniaType() {
		return maazouniaType;
	}

	public void setMaazouniaType(String maazouniaType) {
		this.maazouniaType = maazouniaType;
	}

	public Date getMaazouniaCreationDate() {
		return maazouniaCreationDate;
	}

	public void setMaazouniaCreationDate(Date maazouniaCreationDate) {
		this.maazouniaCreationDate = maazouniaCreationDate;
	}

	public String getCreationAtt() {
		return creationAtt;
	}

	public void setCreationAtt(String creationAtt) {
		this.creationAtt = creationAtt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof MaazouniaChurch)) {
			return false;
		}
		return id != null && id.equals(((MaazouniaChurch) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return "MaazouniaChurch [id=" + id + ", name=" + name + "]";
	}

}

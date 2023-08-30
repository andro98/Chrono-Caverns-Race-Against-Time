package com.aman.payment.auth.model;


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

import com.aman.payment.maazoun.model.MaazounProfile;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A MaazounMaazouniaChurch.
 */
@Entity
@Table(name = "maazounMaazouniaChurch")
public class MaazounMaazouniaChurch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maazounFk", referencedColumnName = "id", nullable = true)
	@JsonIgnore
	private MaazounProfile maazounFk;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maazouniaChurchFk", referencedColumnName = "id", nullable = true)
	@JsonIgnore
	private MaazouniaChurch maazouniaChurchFk;
    
    @Column(name = "maazouniaType")
    private String maazouniaType;

    public MaazounMaazouniaChurch() {
		super();
	}

	public MaazounMaazouniaChurch(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	

	public MaazounProfile getMaazounFk() {
		return maazounFk;
	}

	public void setMaazounFk(MaazounProfile maazounFk) {
		this.maazounFk = maazounFk;
	}

	public MaazouniaChurch getMaazouniaChurchFk() {
		return maazouniaChurchFk;
	}

	public void setMaazouniaChurchFk(MaazouniaChurch maazouniaChurchFk) {
		this.maazouniaChurchFk = maazouniaChurchFk;
	}

	public String getMaazouniaType() {
		return maazouniaType;
	}

	public void setMaazouniaType(String maazouniaType) {
		this.maazouniaType = maazouniaType;
	}

    

    
}

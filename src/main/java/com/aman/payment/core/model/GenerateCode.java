package com.aman.payment.core.model;


import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * A GenerateCode.
 */
@Entity
@Table(name = "generateCode")
public class GenerateCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @NotNull
    @Column(name = "keyName", nullable = false)
    private String keyName;
    
    @NotNull
    @Column(name = "seqValue", nullable = false)
    private Long seqValue;



	public GenerateCode() {
		super();
	}

//	public GenerateCode(@NotNull String key) {
//		super();
//		this.key = key;
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public Long getSeqValue() {
		return seqValue;
	}

	public void setSeqValue(Long seqValue) {
		this.seqValue = seqValue;
	}

	

   
}

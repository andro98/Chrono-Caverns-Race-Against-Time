package com.aman.payment.auth.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;


@Entity
@Table(name = "setting", indexes = { @Index(name = "index_keyOps", columnList = "keyOps", unique = true) })
public class Setting implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;

	@Column(name = "keyOps")
	private String keyOps;

	@Column(name = "valueOps")
	private String valueOps;

	public Setting() {
		super();
	}


	public Setting(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getKeyOps() {
		return keyOps;
	}


	public void setKeyOps(String keyOps) {
		this.keyOps = keyOps;
	}


	public String getValueOps() {
		return valueOps;
	}


	public void setValueOps(String valueOps) {
		this.valueOps = valueOps;
	}

	
	
	
}
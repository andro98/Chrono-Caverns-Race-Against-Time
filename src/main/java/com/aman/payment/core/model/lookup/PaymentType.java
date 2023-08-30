package com.aman.payment.core.model.lookup;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * A PaymentType.
 */
@Entity
@Table(name = "payment_type")
public class PaymentType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    public PaymentType() {
		super();
	}

	public PaymentType(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
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

    public PaymentType name(String name) {
        this.name = name;
        return this;
    }

    public PaymentType(Long id) {
		super();
		this.id = id;
	}

	public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentType)) {
            return false;
        }
        return id != null && id.equals(((PaymentType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}

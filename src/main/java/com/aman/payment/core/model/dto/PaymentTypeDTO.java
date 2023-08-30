package com.aman.payment.core.model.dto;

public class PaymentTypeDTO{

    private String id;
    private String name;

    public PaymentTypeDTO() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
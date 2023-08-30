package com.aman.payment.core.model.dto;

public class AbstractDTO<E> {

    private E id;
    
    private String createdBy;
    private String createdAt;
    private String updatedBy;
    private String updatedAt;

    public E getId() {
        return id;
    }

    public void setId(E id) {
        this.id = id;
    }

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	
    
    
}
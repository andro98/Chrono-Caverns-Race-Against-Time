package com.aman.payment.maazoun.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A MaazounChecklist.
 */
@Entity
@Table(name = "maazoun_check_list_lookup")
public class MaazounCheckListLookup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "subServiceId")
	private Long subServiceId;

    public MaazounCheckListLookup() {
		super();
	}

	public MaazounCheckListLookup(Long id) {
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

	public Long getSubServiceId() {
		return subServiceId;
	}

	public void setSubServiceId(Long subServiceId) {
		this.subServiceId = subServiceId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MaazounCheckListLookup)) {
            return false;
        }
        return id != null && id.equals(((MaazounCheckListLookup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

	@Override
	public String toString() {
		JSONObject Checklist = new JSONObject();
		
		try {
			Checklist.put("id", id);
			Checklist.put("name", name);
			Checklist.put("subServiceId", subServiceId);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return Checklist.toString();
	}
	
    
}

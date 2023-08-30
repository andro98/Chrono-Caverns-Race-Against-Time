package com.aman.payment.maazoun.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.aman.payment.auth.model.MaazounMaazouniaChurch;
import com.aman.payment.auth.model.Sector;
import com.aman.payment.core.model.audit.DateAudit;

/**
 * A MaazounProfile.
 */
@Entity
@Table(name = "maazoun_profile", 
indexes = { 
		@Index(name = "idx_maazoun_nationalId", columnList="national_id", unique = true),
		@Index(name = "idx_maazoun_cardNumber", columnList="cardNumber", unique = true)
		})
public class MaazounProfile extends DateAudit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "national_id")
    private String nationalId;

    @Column(name = "mobile")
    private String mobile;
    
    @Column(name = "imageUrl", nullable = true)
    private String imageUrl;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "cardNumber")
    private String cardNumber;
    
    @Column(name = "maazounType")
    private String maazounType;
    
    @Column(name = "suspendedReason")
    private String suspendedReason;
    
    @Column(name = "bookMoragaaQuota")
    private Long bookMoragaaQuota;
    
    @Column(name = "bookZawagMuslimQuota")
    private Long bookZawagMuslimQuota;
    
    @Column(name = "bookZawagMellyQuota")
    private Long bookZawagMellyQuota;
    
    @Column(name = "bookTasadokQuota")
    private Long bookTasadokQuota;
    
    @Column(name = "bookTalakQuota")
    private Long bookTalakQuota;
    
    @Column(name = "bookMoragaaQuotaContractCount")
    private Long bookMoragaaQuotaContractCount;
    
    @Column(name = "bookZawagMuslimQuotaContractCount")
    private Long bookZawagMuslimQuotaContractCount;
    
    @Column(name = "bookZawagMellyQuotaContractCount")
    private Long bookZawagMellyQuotaContractCount;
    
    @Column(name = "bookTasadokQuotaContractCount")
    private Long bookTasadokQuotaContractCount;
    
    @Column(name = "bookTalakQuotaContractCount")
    private Long bookTalakQuotaContractCount;
    
    @Column(name = "active", nullable = false)
    private Boolean active;
    
    @Column(name = "isCustody", nullable = false)
    private Boolean isCustody;
    
    @Column(name = "closeCustody", nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean closeCustody;
    
    @Column(name = "hasExeption", nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean hasExeption;
	
	@ManyToMany
    @JoinTable(name = "maazoun_sector",
               joinColumns = @JoinColumn(name = "maazoun_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "sector_id", referencedColumnName = "id"))
    private Set<Sector> sectors = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "maazounFk")
	private Set<MaazounMaazouniaChurch> maazounMaazouniaChurch  = new HashSet<MaazounMaazouniaChurch>(0);
    

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public MaazounProfile(Long id) {
		super();
		this.id = id;
	}

	public MaazounProfile() {
		super();
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

	public String getNationalId() {
		return nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	

	public Set<Sector> getSectors() {
		return sectors;
	}

	public void setSectors(Set<Sector> sectors) {
		this.sectors = sectors;
	}

	public String getMaazounType() {
		return maazounType;
	}

	public void setMaazounType(String maazounType) {
		this.maazounType = maazounType;
	}


	public Set<MaazounMaazouniaChurch> getMaazounMaazouniaChurch() {
		return maazounMaazouniaChurch;
	}

	public void setMaazounMaazouniaChurch(Set<MaazounMaazouniaChurch> maazounMaazouniaChurch) {
		this.maazounMaazouniaChurch = maazounMaazouniaChurch;
	}



	public Long getBookMoragaaQuota() {
		return bookMoragaaQuota;
	}

	public void setBookMoragaaQuota(Long bookMoragaaQuota) {
		this.bookMoragaaQuota = bookMoragaaQuota;
	}

	public Long getBookZawagMuslimQuota() {
		return bookZawagMuslimQuota;
	}

	public void setBookZawagMuslimQuota(Long bookZawagMuslimQuota) {
		this.bookZawagMuslimQuota = bookZawagMuslimQuota;
	}

	public Long getBookZawagMellyQuota() {
		return bookZawagMellyQuota;
	}

	public void setBookZawagMellyQuota(Long bookZawagMellyQuota) {
		this.bookZawagMellyQuota = bookZawagMellyQuota;
	}

	public Long getBookTasadokQuota() {
		return bookTasadokQuota;
	}

	public void setBookTasadokQuota(Long bookTasadokQuota) {
		this.bookTasadokQuota = bookTasadokQuota;
	}

	public Long getBookTalakQuota() {
		return bookTalakQuota;
	}

	public void setBookTalakQuota(Long bookTalakQuota) {
		this.bookTalakQuota = bookTalakQuota;
	}

	public Boolean getIsCustody() {
		return isCustody;
	}

	public void setIsCustody(Boolean isCustody) {
		this.isCustody = isCustody;
	}

	public Boolean getCloseCustody() {
		return closeCustody;
	}

	public void setCloseCustody(Boolean closeCustody) {
		this.closeCustody = closeCustody;
	}

	public String getSuspendedReason() {
		return suspendedReason;
	}

	public void setSuspendedReason(String suspendedReason) {
		this.suspendedReason = suspendedReason;
	}
 
	public Long getBookMoragaaQuotaContractCount() {
		return bookMoragaaQuotaContractCount;
	}

	public void setBookMoragaaQuotaContractCount(Long bookMoragaaQuotaContractCount) {
		this.bookMoragaaQuotaContractCount = bookMoragaaQuotaContractCount;
	}

	public Long getBookZawagMuslimQuotaContractCount() {
		return bookZawagMuslimQuotaContractCount;
	}

	public void setBookZawagMuslimQuotaContractCount(Long bookZawagMuslimQuotaContractCount) {
		this.bookZawagMuslimQuotaContractCount = bookZawagMuslimQuotaContractCount;
	}

	public Long getBookZawagMellyQuotaContractCount() {
		return bookZawagMellyQuotaContractCount;
	}

	public void setBookZawagMellyQuotaContractCount(Long bookZawagMellyQuotaContractCount) {
		this.bookZawagMellyQuotaContractCount = bookZawagMellyQuotaContractCount;
	}

	public Long getBookTasadokQuotaContractCount() {
		return bookTasadokQuotaContractCount;
	}

	public void setBookTasadokQuotaContractCount(Long bookTasadokQuotaContractCount) {
		this.bookTasadokQuotaContractCount = bookTasadokQuotaContractCount;
	}

	public Long getBookTalakQuotaContractCount() {
		return bookTalakQuotaContractCount;
	}

	public void setBookTalakQuotaContractCount(Long bookTalakQuotaContractCount) {
		this.bookTalakQuotaContractCount = bookTalakQuotaContractCount;
	}

	public Boolean getHasExeption() {
		return hasExeption;
	}

	public void setHasExeption(Boolean hasExeption) {
		this.hasExeption = hasExeption;
	}
	


}

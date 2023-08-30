package com.aman.payment.maazoun.model.dto;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.aman.payment.auth.model.dto.JwtAuthSectorDTO;
import com.aman.payment.auth.model.dto.MaazounMaazouniaChurchDTO;

public class MaazounProfileDTO{

    private String id;
    private String name;
    private String nationalId;
    private String mobile;
    private String imageUrl;
    private String address;
    private String cardNumber;
    private String active;
    private String maazounType;
    private String maazouniaChurch;
    private Long bookMoragaaQuota;
    private Long bookZawagMuslimQuota;
    private Long bookZawagMellyQuota;
    private Long bookTasadokQuota;
    private Long bookTalakQuota;
    private String custody;
	private String closeCustody;
	private String suspendedReason;
    private ArrayList<JwtAuthSectorDTO> sectors =new ArrayList<JwtAuthSectorDTO>();
    private ArrayList<MaazounMaazouniaChurchDTO> maazouniaChurchs =new ArrayList<MaazounMaazouniaChurchDTO>();
    private Long bookMoragaaQuotaContractCount;
    private Long bookZawagMuslimQuotaContractCount;
    private Long bookZawagMellyQuotaContractCount;
    private Long bookTasadokQuotaContractCount;
    private Long bookTalakQuotaContractCount;
    private String hasExeption;
	public MaazounProfileDTO() {
		super();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	
	public ArrayList<JwtAuthSectorDTO> getSectors() {
		return sectors;
	}
	public void setSectors(ArrayList<JwtAuthSectorDTO> sectors) {
		this.sectors = sectors;
	}
	
	
	public String getMaazounType() {
		return maazounType;
	}

	public void setMaazounType(String maazounType) {
		this.maazounType = maazounType;
	}

	public String getMaazouniaChurch() {
		return maazouniaChurch;
	}

	public void setMaazouniaChurch(String maazouniaChurch) {
		this.maazouniaChurch = maazouniaChurch;
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

	public ArrayList<MaazounMaazouniaChurchDTO> getMaazouniaChurchs() {
		return maazouniaChurchs;
	}

	public void setMaazouniaChurchs(ArrayList<MaazounMaazouniaChurchDTO> maazouniaChurchs) {
		this.maazouniaChurchs = maazouniaChurchs;
	}
 
	public String getCustody() {
		return custody;
	}

	public void setCustody(String custody) {
		this.custody = custody;
	}

	public String getCloseCustody() {
		return closeCustody;
	}

	public void setCloseCustody(String closeCustody) {
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

	public String getHasExeption() {
		return hasExeption;
	}

	public void setHasExeption(String hasExeption) {
		this.hasExeption = hasExeption;
	}

	@Override
	public String toString() {

		JSONObject maazoun = new JSONObject();
		try {
			maazoun.put("id", id);
			maazoun.put("name", name);
			maazoun.put("nationalId", nationalId);
			maazoun.put("mobile", mobile);
			maazoun.put("imageUrl", imageUrl);
			maazoun.put("address", address);
			maazoun.put("cardNumber", cardNumber);
			maazoun.put("active", active);
			maazoun.put("maazounType", maazounType);
			maazoun.put("bookMoragaaQuota", bookMoragaaQuota);
			maazoun.put("bookZawagMuslimQuota", bookZawagMuslimQuota);
			maazoun.put("bookZawagMellyQuota", bookZawagMellyQuota);
			maazoun.put("bookTasadokQuota", bookTasadokQuota);
			maazoun.put("bookTalakQuota", bookTalakQuota);
			maazoun.put("custody", custody);
			maazoun.put("closeCustody", closeCustody);
			maazoun.put("suspendedReason", suspendedReason);
			maazoun.put("bookMoragaaQuotaContractCount", bookMoragaaQuotaContractCount);
			maazoun.put("bookZawagMuslimQuotaContractCount", bookZawagMuslimQuotaContractCount);
			maazoun.put("bookZawagMellyQuotaContractCount", bookZawagMellyQuotaContractCount);
			maazoun.put("bookTasadokQuotaContractCount", bookTasadokQuotaContractCount);
			maazoun.put("bookTalakQuotaContractCount", bookTalakQuotaContractCount);
			maazoun.put("hasExeption", hasExeption);
			List<JSONObject> sectorList = new ArrayList<JSONObject>();

			if(sectors!=null) {
				for (JwtAuthSectorDTO sector : sectors) {
					
					JSONObject sectorArr = new JSONObject();
					sectorArr.put("id", sector.getId());
					sectorArr.put("name", sector.getName());
					
					sectorList.add(sectorArr);
				}
			}
			
			maazoun.put("sectors", sectorList);
			
			List<JSONObject> maazouniaChuchList = new ArrayList<JSONObject>();

			if(maazouniaChurchs!=null) {
				for (MaazounMaazouniaChurchDTO maazouniaChurch : maazouniaChurchs) {
					
					JSONObject maazouniaChurchArr = new JSONObject();
					maazouniaChurchArr.put("maazouniaChurchId", maazouniaChurch.getMaazouniaChurchId());
					maazouniaChurchArr.put("maazouniaChurchName", maazouniaChurch.getMaazouniaChurchName());
					maazouniaChurchArr.put("maazouniaType", maazouniaChurch.getMaazouniaType());
					maazouniaChurchArr.put("id", maazouniaChurch.getId());

					maazouniaChuchList.add(maazouniaChurchArr);
				}
			}
			maazoun.put("maazouniaChurchs", maazouniaChuchList);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return maazoun.toString();
		
	}
    


}

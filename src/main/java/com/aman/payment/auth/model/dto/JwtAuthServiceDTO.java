package com.aman.payment.auth.model.dto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.aman.payment.auth.service.CryptoMngrAuthService;

public class JwtAuthServiceDTO implements AuthBaseDTO<JwtAuthServiceDTO>{

    private String id;
    private String name;
    private String MerchantName;
    private String MerchantId;
    private String tax;
    private String icon;

    public JwtAuthServiceDTO() {
		super();
	}

    public JwtAuthServiceDTO(String id, String name, String merchantName, String merchantId, String tax,String icon) {
		super();
		this.id = id;
		this.name = name;
		MerchantName = merchantName;
		MerchantId = merchantId;
		this.tax = tax;
		this.icon=icon;
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

	public String getMerchantName() {
		return MerchantName;
	}

	public String getMerchantId() {
		return MerchantId;
	}

	public void setMerchantName(String merchantName) {
		MerchantName = merchantName;
	}

	public void setMerchantId(String merchantId) {
		MerchantId = merchantId;
	}
	
	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}
	
	

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JwtAuthServiceDTO other = (JwtAuthServiceDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		
		JSONObject authServiceResponse = new JSONObject();
		try {
			authServiceResponse.put("id", id);
			authServiceResponse.put("name", name);
			authServiceResponse.put("MerchantName", MerchantName);
			authServiceResponse.put("MerchantId", MerchantId);
			authServiceResponse.put("tax", tax);
			authServiceResponse.put("icon", icon);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		return authServiceResponse.toString();
	}

	@Override
	public JwtAuthServiceDTO encrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		return new JwtAuthServiceDTO(
				cryptoMngrAuthService.encrypt(id), 
				cryptoMngrAuthService.encrypt(name), 
				cryptoMngrAuthService.encrypt(MerchantName), 
				cryptoMngrAuthService.encrypt(MerchantId),
				cryptoMngrAuthService.encrypt(tax),
	        	cryptoMngrAuthService.encrypt(icon));

	}
	
	

}
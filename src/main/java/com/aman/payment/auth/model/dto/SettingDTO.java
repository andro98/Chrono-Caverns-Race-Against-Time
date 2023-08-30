package com.aman.payment.auth.model.dto;

import org.json.JSONException;
import org.json.JSONObject;

import com.aman.payment.auth.service.CryptoMngrAuthService;

public class SettingDTO implements AuthBaseDTO<SettingDTO> {

	private String id;
	private String keyOps;
	private String valueOps;

	public SettingDTO() {
		// TODO Auto-generated constructor stub
	}

	public SettingDTO(String id, String keyOps, String valueOps) {
		super();
		this.id = id;
		this.keyOps = keyOps;
		this.valueOps = valueOps;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	@Override
	public SettingDTO encrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		// TODO Auto-generated method stub
		return new SettingDTO(cryptoMngrAuthService.encrypt(id), cryptoMngrAuthService.encrypt(keyOps),
				cryptoMngrAuthService.encrypt(valueOps)

		);
	}

	@Override
	public String toString() {

		JSONObject setting = new JSONObject();
		try {
			setting.put("id", id);
			setting.put("keyOps", keyOps);
			setting.put("valueOps", valueOps);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return setting.toString();

	}

}

package com.aman.payment.auth.model.payload;

import org.json.JSONException;
import org.json.JSONObject;

import com.aman.payment.auth.model.dto.SettingDTO;
import com.aman.payment.auth.service.CryptoMngrAuthService;

public class AddEditSettingRequest  implements AuthBasePayload<AddEditSettingRequest> {
	private String id;
	private String keyOps;
	private String valueOps;

	public AddEditSettingRequest() {
		// TODO Auto-generated constructor stub
	}

	public AddEditSettingRequest(String id, String keyOps, String valueOps) {
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

	@Override
	public AddEditSettingRequest decrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		// TODO Auto-generated method stub
		return new AddEditSettingRequest(
				cryptoMngrAuthService.decrypt(id),
				cryptoMngrAuthService.decrypt(keyOps),
				cryptoMngrAuthService.decrypt(valueOps)
				);
	}


}

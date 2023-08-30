/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aman.payment.auth.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDTO {

    private final String data;
    private final String response;
    private final boolean res;
    private final String timestamp;
    private final String cause;
    private final String path;

    public ApiResponseDTO(boolean res, String data, String cause, String path) {
    	this.res = res;
        this.timestamp = Instant.now().toString();
        this.data = data;
        this.response = null;
        this.cause = cause;
        this.path = path;
    }

    public ApiResponseDTO(String response) {
    	this.res = false;
        this.timestamp = null;
        this.data = null;
        this.response = response;
        this.cause = null;
        this.path = null;
    }

    public String getData() {
        return data;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getCause() {
        return cause;
    }

    public String getPath() {
        return path;
    }

	public String getResponse() {
		return response;
	}

	@Override
	public String toString() {

		JSONObject authResponse = new JSONObject();
		try {
			authResponse.put("data", data);
			authResponse.put("response", response);
			authResponse.put("res", res);
			authResponse.put("timestamp", timestamp);
			authResponse.put("cause", cause);
			authResponse.put("path", path);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		return authResponse.toString();
	}
    
}

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
package com.aman.payment.core.event;

import org.springframework.context.ApplicationEvent;

import com.aman.payment.core.model.PullAccount;

public class OnDepositRequestSuccessEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PullAccount pullAccount;
	private String createdBy;
	private String description;


	public OnDepositRequestSuccessEvent(PullAccount pullAccount, String createdBy, String description) {
		super(pullAccount);
		this.pullAccount = pullAccount;
		this.createdBy = createdBy;
		this.description = description;
	}


	public PullAccount getPullAccount() {
		return pullAccount;
	}


	public void setPullAccount(PullAccount pullAccount) {
		this.pullAccount = pullAccount;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	
	
}

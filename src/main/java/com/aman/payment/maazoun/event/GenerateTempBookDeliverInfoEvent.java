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
package com.aman.payment.maazoun.event;

import java.util.List;

import org.springframework.context.ApplicationEvent;

import com.aman.payment.maazoun.model.dto.MaazounDeliverInfoReportDTO;

public class GenerateTempBookDeliverInfoEvent extends ApplicationEvent {

	private List<MaazounDeliverInfoReportDTO> eMaazounDeliverInfoReportDTO;
	private String createdAt;
	private String receiptUrl;
	private int regenerateFlag;

	public GenerateTempBookDeliverInfoEvent(List<MaazounDeliverInfoReportDTO> eMaazounDeliverInfoReportDTO,
			String createdAt, String receiptUrl, int regenerateFlag) {
		super(eMaazounDeliverInfoReportDTO);
		this.createdAt = createdAt;
		this.receiptUrl = receiptUrl;
		this.regenerateFlag = regenerateFlag;
	}

	public List<MaazounDeliverInfoReportDTO> geteMaazounCollectInfoReportDTO() {
		return eMaazounDeliverInfoReportDTO;
	}

	public void seteMaazounCollectInfoReportDTO(List<MaazounDeliverInfoReportDTO> eMaazounCollectInfoReportDTO) {
		this.eMaazounDeliverInfoReportDTO = eMaazounCollectInfoReportDTO;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getReceiptUrl() {
		return receiptUrl;
	}

	public void setReceiptUrl(String receiptUrl) {
		this.receiptUrl = receiptUrl;
	}

	public int getRegenerateFlag() {
		return regenerateFlag;
	}

	public void setRegenerateFlag(int regenerateFlag) {
		this.regenerateFlag = regenerateFlag;
	}

	public List<MaazounDeliverInfoReportDTO> geteMaazounDeliverInfoReportDTO() {
		return eMaazounDeliverInfoReportDTO;
	}

	public void seteMaazounDeliverInfoReportDTO(List<MaazounDeliverInfoReportDTO> eMaazounDeliverInfoReportDTO) {
		this.eMaazounDeliverInfoReportDTO = eMaazounDeliverInfoReportDTO;
	}

}

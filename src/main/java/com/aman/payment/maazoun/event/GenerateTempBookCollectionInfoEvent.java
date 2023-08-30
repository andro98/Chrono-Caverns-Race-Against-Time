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

import com.aman.payment.maazoun.model.dto.MaazounCollectInfoReportDTO;

public class GenerateTempBookCollectionInfoEvent extends ApplicationEvent {

	private List<MaazounCollectInfoReportDTO> eMaazounCollectInfoReportDTO;
	private String createdAt;
	private String receiptUrl;
	private String typeId;
	private int regenerateFlag;

	public GenerateTempBookCollectionInfoEvent(List<MaazounCollectInfoReportDTO> eMaazounCollectInfoReportDTO,
			String createdAt, String receiptUrl, String typeId, int regenerateFlag) {
		super(eMaazounCollectInfoReportDTO);
		this.createdAt = createdAt;
		this.receiptUrl = receiptUrl;
		this.typeId = typeId;
		this.regenerateFlag = regenerateFlag;
	}

	public List<MaazounCollectInfoReportDTO> geteMaazounCollectInfoReportDTO() {
		return eMaazounCollectInfoReportDTO;
	}

	public void seteMaazounCollectInfoReportDTO(List<MaazounCollectInfoReportDTO> eMaazounCollectInfoReportDTO) {
		this.eMaazounCollectInfoReportDTO = eMaazounCollectInfoReportDTO;
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

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public int getRegenerateFlag() {
		return regenerateFlag;
	}

	public void setRegenerateFlag(int regenerateFlag) {
		this.regenerateFlag = regenerateFlag;
	}

}

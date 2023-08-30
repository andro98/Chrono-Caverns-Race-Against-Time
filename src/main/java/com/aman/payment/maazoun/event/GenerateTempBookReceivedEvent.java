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
import com.aman.payment.maazoun.model.dto.MaazounReceivedReportDTO;

public class GenerateTempBookReceivedEvent extends ApplicationEvent {

	private List<MaazounReceivedReportDTO> eMaazounReceivedReportDTO;
	private String receivedAt;
	private String receiptUrl;

	public GenerateTempBookReceivedEvent(List<MaazounReceivedReportDTO> eMaazounReceivedReportDTO,
			String receivedAt, String receiptUrl) {
		super(eMaazounReceivedReportDTO);
		this.receivedAt = receivedAt;
		this.receiptUrl = receiptUrl;
	}

	public List<MaazounReceivedReportDTO> geteMaazounReceivedReportDTO() {
		return eMaazounReceivedReportDTO;
	}

	public void seteMaazounReceivedReportDTO(List<MaazounReceivedReportDTO> eMaazounReceivedReportDTO) {
		this.eMaazounReceivedReportDTO = eMaazounReceivedReportDTO;
	}

	public String getReceivedAt() {
		return receivedAt;
	}

	public void setReceivedAt(String receivedAt) {
		this.receivedAt = receivedAt;
	}

	public String getReceiptUrl() {
		return receiptUrl;
	}

	public void setReceiptUrl(String receiptUrl) {
		this.receiptUrl = receiptUrl;
	}

	

}

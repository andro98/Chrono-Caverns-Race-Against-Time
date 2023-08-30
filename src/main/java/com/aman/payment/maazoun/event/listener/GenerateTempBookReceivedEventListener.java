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
package com.aman.payment.maazoun.event.listener;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.aman.payment.core.util.UtilCore;
import com.aman.payment.maazoun.event.GenerateTempBookReceivedEvent;
import com.aman.payment.maazoun.model.dto.MaazounReceivedReportDTO;
import com.aman.payment.util.ConfigurationConstant;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
public class GenerateTempBookReceivedEventListener implements ApplicationListener<GenerateTempBookReceivedEvent> {

	final static Logger logger = Logger.getLogger("maazoun");
	@Value("${reports.maazoun.received}")
    private String basePackagePath;
    
	@Override
	public synchronized void onApplicationEvent(GenerateTempBookReceivedEvent event) {
		
		List<MaazounReceivedReportDTO> collection = (List<MaazounReceivedReportDTO>) (event.getSource());
		
		InputStream receiptStream = this.getClass()
				.getResourceAsStream(ConfigurationConstant.MAAZOUN_RECEIVED_REQUEST_FORM);
		

		try {
			JasperReport receiptReport = JasperCompileManager.compileReport(receiptStream);
			
			JRBeanCollectionDataSource receiptSource = new JRBeanCollectionDataSource(collection);
			
			JasperPrint receiptPrint = JasperFillManager.fillReport(receiptReport, null, receiptSource);
			
			String basePath = basePackagePath+"/"+UtilCore.saveFolderNamingFormat(event.getReceivedAt());

			Path path = Paths.get(basePath);
			Files.createDirectories(path);
			
			JasperExportManager.exportReportToPdfFile(receiptPrint, basePath.concat(event.getReceiptUrl()));

			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("An exception when generating request info form for location id! ", e);
		}
		
	}
}

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
import com.aman.payment.maazoun.event.GenerateTempBookRequestInfoEvent;
import com.aman.payment.maazoun.model.dto.MaazounRequestInfoReportDTO;
import com.aman.payment.util.ConfigurationConstant;

import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
public class GenerateTempBookRequestInfoEventListener implements ApplicationListener<GenerateTempBookRequestInfoEvent> {

	final static Logger logger = Logger.getLogger("maazoun");
	@Value("${reports.maazoun.request.orders}")
    private String basePackagePath;
    
	@Override
	public synchronized void onApplicationEvent(GenerateTempBookRequestInfoEvent event) {
		
		List<MaazounRequestInfoReportDTO> collection = (List<MaazounRequestInfoReportDTO>) (event.getSource());
		
		InputStream receiptStreamProsecutor = this.getClass().getResourceAsStream(ConfigurationConstant.MAAZOUN_BOOK_REQUEST_PROSECUTOR_COPY_FORM);
		
		InputStream receiptStreamMaazoun = this.getClass().getResourceAsStream(ConfigurationConstant.MAAZOUN_BOOK_REQUEST_MAAZOUN_COPY_FORM);

		try {
			JasperReport receiptReportProsecutor = JasperCompileManager.compileReport(receiptStreamProsecutor);
			JasperReport receiptReportMaazoun = JasperCompileManager.compileReport(receiptStreamMaazoun);
			
			JRBeanCollectionDataSource receiptSourceProsecutor = new JRBeanCollectionDataSource(collection);
			JRBeanCollectionDataSource receiptSourceMaazoun = new JRBeanCollectionDataSource(collection);
			
			JasperPrint receiptPrintProsecutor = JasperFillManager.fillReport(receiptReportProsecutor, null, receiptSourceProsecutor);
			JasperPrint receiptPrintMaazoun = JasperFillManager.fillReport(receiptReportMaazoun, null, receiptSourceMaazoun);

			String basePath = basePackagePath+"/"+UtilCore.saveFolderNamingFormat(event.getCreatedAt());

			List<JRPrintPage> pages = receiptPrintMaazoun.getPages();
	        for (int j = 0; j < pages.size(); j++) {
	            JRPrintPage object = (JRPrintPage)pages.get(j);
	            receiptPrintProsecutor.addPage(object);
	        }

			Path path = Paths.get(basePath);
			Files.createDirectories(path);
			
			if(event.getRegenerateFlag() == 1) {
				JasperExportManager.exportReportToPdfFile(receiptPrintProsecutor, event.getReceiptUrl());
			}else {
				
				JasperExportManager.exportReportToPdfFile(receiptPrintProsecutor, basePath.concat(event.getReceiptUrl()));
			}

			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("An exception when generating request info form for location id! ", e);
		}
		
	}
}

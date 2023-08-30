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
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.aman.payment.core.util.UtilCore;
import com.aman.payment.maazoun.event.GenerateTempBookCollectionInfoEvent;
import com.aman.payment.maazoun.model.dto.MaazounCollectInfoReportDTO;
import com.aman.payment.util.ConfigurationConstant;
import com.aman.payment.util.StatusConstant;

import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
public class GenerateTempBookCollectionInfoEventListener implements ApplicationListener<GenerateTempBookCollectionInfoEvent> {

	final static Logger logger = Logger.getLogger("maazoun");
	@Value("${reports.maazoun.collection}")
    private String basePackagePath;
    
	@Override
	public synchronized void onApplicationEvent(GenerateTempBookCollectionInfoEvent event) {
		
		List<MaazounCollectInfoReportDTO> collection = (List<MaazounCollectInfoReportDTO>) (event.getSource());
		
		InputStream receiptStreamZawagMuslimProsecutor = this.getClass()
				.getResourceAsStream(ConfigurationConstant.MAAZOUN_COLLECTION_REQUEST_ZAWAG_MUSLIM_PROSECUTOR_COPY_FORM);
		InputStream receiptStreamZawagMuslimMaazoun = this.getClass()
				.getResourceAsStream(ConfigurationConstant.MAAZOUN_COLLECTION_REQUEST_ZAWAG_MUSLIM_MAAZOUN_COPY_FORM);
		
		InputStream receiptStreamTasadokProsecutor = this.getClass()
				.getResourceAsStream(ConfigurationConstant.MAAZOUN_COLLECTION_REQUEST_TASADOK_PROSECUTOR_COPY_FORM);
		InputStream receiptStreamTasadokMaazoun = this.getClass()
				.getResourceAsStream(ConfigurationConstant.MAAZOUN_COLLECTION_REQUEST_TASADOK_MAAZOUN_COPY_FORM);
		
		InputStream receiptStreamTalakProsecutor = this.getClass()
				.getResourceAsStream(ConfigurationConstant.MAAZOUN_COLLECTION_REQUEST_TALAK_PROSECUTOR_COPY_FORM);
		InputStream receiptStreamTalakMaazoun = this.getClass()
				.getResourceAsStream(ConfigurationConstant.MAAZOUN_COLLECTION_REQUEST_TALAK_MAAZOUN_COPY_FORM);
		
		InputStream receiptStreamZawagMellyProsecutor = this.getClass()
				.getResourceAsStream(ConfigurationConstant.MAAZOUN_COLLECTION_REQUEST_ZAWAG_MELLY_PROSECUTOR_COPY_FORM);
		InputStream receiptStreamZawagMellyMaazoun = this.getClass()
				.getResourceAsStream(ConfigurationConstant.MAAZOUN_COLLECTION_REQUEST_ZAWAG_MELLY_MAAZOUN_COPY_FORM);
		
		InputStream receiptStreamMoragaaProsecutor = this.getClass()
				.getResourceAsStream(ConfigurationConstant.MAAZOUN_COLLECTION_REQUEST_MORAGAA_PROSECUTOR_COPY_FORM);
		InputStream receiptStreamMoragaaMaazoun = this.getClass()
				.getResourceAsStream(ConfigurationConstant.MAAZOUN_COLLECTION_REQUEST_MORAGAA_MAAZOUN_COPY_FORM);
		

		try {
			InputStream inputStreamZawagMuslimProsecutor = null;
			InputStream inputStreamZawagMuslimMaazoun = null;
			InputStream inputStreamTasadokProsecutor = null;
			InputStream inputStreamTasadokMaazoun = null;
			InputStream inputStreamTalakProsecutor = null;
			InputStream inputStreamTalakMaazoun = null;
			InputStream inputStreamZawagMellyProsecutor = null;
			InputStream inputStreamZawagMellyMaazoun = null;
			InputStream inputStreamMoragaaProsecutor = null;
			InputStream inputStreamMoragaaMaazoun = null;
			
			List<MaazounCollectInfoReportDTO> collectionStreamZawagMuslim = collection.stream()
					  .filter(x -> x.getBookTypeId().equals(StatusConstant.CONTRACT_ZAWAG_MUSLIM_ID))
					  .collect(Collectors.toList());
			
			List<MaazounCollectInfoReportDTO> collectionStreamTasadok = collection.stream()
					  .filter(x -> x.getBookTypeId().equals(StatusConstant.CONTRACT_TASADOK_ID))
					  .collect(Collectors.toList());
			
			List<MaazounCollectInfoReportDTO> collectionStreamTalak = collection.stream()
					  .filter(x -> x.getBookTypeId().equals(StatusConstant.CONTRACT_TALAK_ID))
					  .collect(Collectors.toList());
			
			List<MaazounCollectInfoReportDTO> collectionStreamZawagMelly = collection.stream()
					  .filter(x -> x.getBookTypeId().equals(StatusConstant.CONTRACT_ZAWAG_MELALY_ID))
					  .collect(Collectors.toList());
			
			List<MaazounCollectInfoReportDTO> collectionStreamMoragaa = collection.stream()
					  .filter(x -> x.getBookTypeId().equals(StatusConstant.CONTRACT_MORAGAA_ID))
					  .collect(Collectors.toList());
			
			JasperPrint receiptPrintZawagMuslimPresecutor = null;
			JasperPrint receiptPrintTasadokPresecutor = null;
			JasperPrint receiptPrintTalakPresecutor = null;
			JasperPrint receiptPrintZawagMellyPresecutor = null;
			JasperPrint receiptPrintMoragaaPresecutor = null;
			
			if(collectionStreamZawagMuslim != null) {
				
				inputStreamZawagMuslimProsecutor = receiptStreamZawagMuslimProsecutor;
				inputStreamZawagMuslimMaazoun = receiptStreamZawagMuslimMaazoun;
				
				JasperReport receiptReportZawagMuslimProsecutor = JasperCompileManager.compileReport(inputStreamZawagMuslimProsecutor);
				JasperReport receiptReportZawagMuslimMaazoun = JasperCompileManager.compileReport(inputStreamZawagMuslimMaazoun);
				
				JRBeanCollectionDataSource receiptSourceZawagMuslimProsecutor = new JRBeanCollectionDataSource(collectionStreamZawagMuslim);
				JRBeanCollectionDataSource receiptSourceZawagMuslimMaazoun = new JRBeanCollectionDataSource(collectionStreamZawagMuslim);
				
				receiptPrintZawagMuslimPresecutor = JasperFillManager.fillReport(receiptReportZawagMuslimProsecutor, null, receiptSourceZawagMuslimProsecutor);
				JasperPrint receiptPrintZawagMuslimMaazoun = JasperFillManager.fillReport(receiptReportZawagMuslimMaazoun, null, receiptSourceZawagMuslimMaazoun);
				
				List<JRPrintPage> pages = receiptPrintZawagMuslimMaazoun.getPages();
		        for (int j = 0; j < pages.size(); j++) {
		            JRPrintPage object = (JRPrintPage)pages.get(j);
		            receiptPrintZawagMuslimPresecutor.addPage(object);
		        }
				
			}
			if(collectionStreamTasadok != null) {
				
				inputStreamTasadokProsecutor = receiptStreamTasadokProsecutor;
				inputStreamTasadokMaazoun = receiptStreamTasadokMaazoun;
				
				JasperReport receiptReportTasadokProsecutor = JasperCompileManager.compileReport(inputStreamTasadokProsecutor);
				JasperReport receiptReportTasadokMaazoun = JasperCompileManager.compileReport(inputStreamTasadokMaazoun);
				
				JRBeanCollectionDataSource receiptSourceTasadokProsecutor = new JRBeanCollectionDataSource(collectionStreamTasadok);
				JRBeanCollectionDataSource receiptSourceTasadokMaazoun = new JRBeanCollectionDataSource(collectionStreamTasadok);
				
				receiptPrintTasadokPresecutor = JasperFillManager.fillReport(receiptReportTasadokProsecutor, null, receiptSourceTasadokProsecutor);
				JasperPrint receiptPrintTasadokMaazoun = JasperFillManager.fillReport(receiptReportTasadokMaazoun, null, receiptSourceTasadokMaazoun);
				
				List<JRPrintPage> pages = receiptPrintTasadokMaazoun.getPages();
		        for (int j = 0; j < pages.size(); j++) {
		            JRPrintPage object = (JRPrintPage)pages.get(j);
		            receiptPrintTasadokPresecutor.addPage(object);
		        }
				
			}
			if(collectionStreamTalak != null) {
				
				inputStreamTalakProsecutor = receiptStreamTalakProsecutor;
				inputStreamTalakMaazoun = receiptStreamTalakMaazoun;
				
				JasperReport receiptReportTalakProsecutor = JasperCompileManager.compileReport(inputStreamTalakProsecutor);
				JasperReport receiptReportTalakMaazoun = JasperCompileManager.compileReport(inputStreamTalakMaazoun);
				
				JRBeanCollectionDataSource receiptSourceTalakProsecutor = new JRBeanCollectionDataSource(collectionStreamTalak);
				JRBeanCollectionDataSource receiptSourceTalakMaazoun = new JRBeanCollectionDataSource(collectionStreamTalak);
				
				receiptPrintTalakPresecutor = JasperFillManager.fillReport(receiptReportTalakProsecutor, null, receiptSourceTalakProsecutor);
				JasperPrint receiptPrintTalakMaazoun = JasperFillManager.fillReport(receiptReportTalakMaazoun, null, receiptSourceTalakMaazoun);
				
				List<JRPrintPage> pages = receiptPrintTalakMaazoun.getPages();
		        for (int j = 0; j < pages.size(); j++) {
		            JRPrintPage object = (JRPrintPage)pages.get(j);
		            receiptPrintTalakPresecutor.addPage(object);
		        }
		        
				
			}
			if(collectionStreamZawagMelly != null) {
				
				inputStreamZawagMellyProsecutor = receiptStreamZawagMellyProsecutor;
				inputStreamZawagMellyMaazoun = receiptStreamZawagMellyMaazoun;
				
				JasperReport receiptReportZawagMellyProsecutor = JasperCompileManager.compileReport(inputStreamZawagMellyProsecutor);
				JasperReport receiptReportZawagMellyMaazoun = JasperCompileManager.compileReport(inputStreamZawagMellyMaazoun);
				
				JRBeanCollectionDataSource receiptSourceZawagMellyProsecutor = new JRBeanCollectionDataSource(collectionStreamZawagMelly);
				JRBeanCollectionDataSource receiptSourceZawagMellyMaazoun = new JRBeanCollectionDataSource(collectionStreamZawagMelly);
				
				receiptPrintZawagMellyPresecutor = JasperFillManager.fillReport(receiptReportZawagMellyProsecutor, null, receiptSourceZawagMellyProsecutor);
				JasperPrint receiptPrintZawagMellyMaazoun = JasperFillManager.fillReport(receiptReportZawagMellyMaazoun, null, receiptSourceZawagMellyMaazoun);
				
				List<JRPrintPage> pages = receiptPrintZawagMellyMaazoun.getPages();
		        for (int j = 0; j < pages.size(); j++) {
		            JRPrintPage object = (JRPrintPage)pages.get(j);
		            receiptPrintZawagMellyPresecutor.addPage(object);
		        }
		        
			}
			if(collectionStreamMoragaa != null) {
				
				inputStreamMoragaaProsecutor = receiptStreamMoragaaProsecutor;
				inputStreamMoragaaMaazoun = receiptStreamMoragaaMaazoun;
				
				JasperReport receiptReportMoragaaProsecutor = JasperCompileManager.compileReport(inputStreamMoragaaProsecutor);
				JasperReport receiptReportMoragaaMaazoun = JasperCompileManager.compileReport(inputStreamMoragaaMaazoun);
				
				JRBeanCollectionDataSource receiptSourceMoragaaProsecutor = new JRBeanCollectionDataSource(collectionStreamMoragaa);
				JRBeanCollectionDataSource receiptSourceMoragaaMaazoun = new JRBeanCollectionDataSource(collectionStreamMoragaa);
				
				receiptPrintMoragaaPresecutor = JasperFillManager.fillReport(receiptReportMoragaaProsecutor, null, receiptSourceMoragaaProsecutor);
				JasperPrint receiptPrintMoragaaMaazoun = JasperFillManager.fillReport(receiptReportMoragaaMaazoun, null, receiptSourceMoragaaMaazoun);
				
				List<JRPrintPage> pages = receiptPrintMoragaaMaazoun.getPages();
		        for (int j = 0; j < pages.size(); j++) {
		            JRPrintPage object = (JRPrintPage)pages.get(j);
		            receiptPrintMoragaaPresecutor.addPage(object);
		        }
		        
			}
			
			JasperPrint jasperPrint = null;
			
			if(receiptPrintZawagMuslimPresecutor != null) {
				
				if(receiptPrintTasadokPresecutor != null) {
					List<JRPrintPage> pages = receiptPrintTasadokPresecutor.getPages();
			        for (int j = 0; j < pages.size(); j++) {
			            JRPrintPage object = (JRPrintPage)pages.get(j);
			            receiptPrintZawagMuslimPresecutor.addPage(object);
			        }
				}
				if(receiptPrintTalakPresecutor != null) {
					List<JRPrintPage> pages = receiptPrintTalakPresecutor.getPages();
			        for (int j = 0; j < pages.size(); j++) {
			            JRPrintPage object = (JRPrintPage)pages.get(j);
			            receiptPrintZawagMuslimPresecutor.addPage(object);
			        }
				}
				if(receiptPrintZawagMellyPresecutor != null) {
					List<JRPrintPage> pages = receiptPrintZawagMellyPresecutor.getPages();
			        for (int j = 0; j < pages.size(); j++) {
			            JRPrintPage object = (JRPrintPage)pages.get(j);
			            receiptPrintZawagMuslimPresecutor.addPage(object);
			        }
				}
				if(receiptPrintMoragaaPresecutor != null) {
					List<JRPrintPage> pages = receiptPrintMoragaaPresecutor.getPages();
			        for (int j = 0; j < pages.size(); j++) {
			            JRPrintPage object = (JRPrintPage)pages.get(j);
			            receiptPrintZawagMuslimPresecutor.addPage(object);
			        }
				}
				
				jasperPrint = receiptPrintZawagMuslimPresecutor;
				
			}else if(receiptPrintTasadokPresecutor != null) {
				
				if(receiptPrintTalakPresecutor != null) {
					List<JRPrintPage> pages = receiptPrintTalakPresecutor.getPages();
			        for (int j = 0; j < pages.size(); j++) {
			            JRPrintPage object = (JRPrintPage)pages.get(j);
			            receiptPrintZawagMuslimPresecutor.addPage(object);
			        }
				}
				if(receiptPrintZawagMellyPresecutor != null) {
					List<JRPrintPage> pages = receiptPrintZawagMellyPresecutor.getPages();
			        for (int j = 0; j < pages.size(); j++) {
			            JRPrintPage object = (JRPrintPage)pages.get(j);
			            receiptPrintZawagMuslimPresecutor.addPage(object);
			        }
				}
				if(receiptPrintMoragaaPresecutor != null) {
					List<JRPrintPage> pages = receiptPrintMoragaaPresecutor.getPages();
			        for (int j = 0; j < pages.size(); j++) {
			            JRPrintPage object = (JRPrintPage)pages.get(j);
			            receiptPrintZawagMuslimPresecutor.addPage(object);
			        }
				}
				
				jasperPrint = receiptPrintTasadokPresecutor;
				
			}else if(receiptPrintTalakPresecutor != null){
				
				if(receiptPrintZawagMellyPresecutor != null) {
					List<JRPrintPage> pages = receiptPrintZawagMellyPresecutor.getPages();
			        for (int j = 0; j < pages.size(); j++) {
			            JRPrintPage object = (JRPrintPage)pages.get(j);
			            receiptPrintZawagMuslimPresecutor.addPage(object);
			        }
				}
				if(receiptPrintMoragaaPresecutor != null) {
					List<JRPrintPage> pages = receiptPrintMoragaaPresecutor.getPages();
			        for (int j = 0; j < pages.size(); j++) {
			            JRPrintPage object = (JRPrintPage)pages.get(j);
			            receiptPrintZawagMuslimPresecutor.addPage(object);
			        }
				}
				
				jasperPrint = receiptPrintTalakPresecutor;
				
			}else if(receiptPrintZawagMellyPresecutor != null){
				
				if(receiptPrintMoragaaPresecutor != null) {
					List<JRPrintPage> pages = receiptPrintMoragaaPresecutor.getPages();
			        for (int j = 0; j < pages.size(); j++) {
			            JRPrintPage object = (JRPrintPage)pages.get(j);
			            receiptPrintZawagMuslimPresecutor.addPage(object);
			        }
				}
				
				jasperPrint = receiptPrintZawagMellyPresecutor;
				
			}else if(receiptPrintMoragaaPresecutor != null){
				
				jasperPrint = receiptPrintMoragaaPresecutor;
			}
			
			String basePath = basePackagePath+"/"+UtilCore.saveFolderNamingFormat(event.getCreatedAt());
	        
			Path path = Paths.get(basePath);
			Files.createDirectories(path);
			
			if(event.getRegenerateFlag() == 1) {
				JasperExportManager.exportReportToPdfFile(jasperPrint, event.getReceiptUrl());
			}else {
				
				JasperExportManager.exportReportToPdfFile(jasperPrint, basePath.concat(event.getReceiptUrl()));
			}

			

			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("An exception when generating request info form for location id! ", e);
		}
		
	}
}

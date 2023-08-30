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
package com.aman.payment.core.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aman.payment.core.management.CoreJsonObjectFactoryImpl;
import com.aman.payment.core.management.TransactionManagement;
import com.aman.payment.core.model.dto.TransactionDTO;
import com.aman.payment.core.model.dto.TransactionPagingDTO;
import com.aman.payment.core.model.dto.TransactionResponseDTO;
import com.aman.payment.core.model.payload.PosMidRequest;
import com.aman.payment.core.model.payload.TransactionCodeRequest;
import com.aman.payment.core.model.payload.TransactionDurationRequest;
import com.aman.payment.core.model.payload.TransactionPaymentRequest;
import com.aman.payment.core.model.payload.TransactionRefundRequest;
import com.aman.payment.core.model.payload.URLRequest;
import com.aman.payment.core.service.CryptoMngrCoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/transaction")
@Api(value = "Transaction Rest API", description = "Defines endpoints for the Transaction. It's secured by default")
public class TransactionController extends CoreJsonObjectFactoryImpl{

	final static Logger logger = Logger.getLogger("transactionAudit");

	private final TransactionManagement transactionManagement;
	private final CryptoMngrCoreService cryptoMngrCoreService;
	
	@Autowired
	public TransactionController(TransactionManagement transactionManagement,
			CryptoMngrCoreService cryptoMngrCoreService) {
		this.transactionManagement = transactionManagement;
		this.cryptoMngrCoreService = cryptoMngrCoreService;
	}

	/**
	 * Returns Transaction Response to the client.
	 */
	@PostMapping("/create")
//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Return Transaction Payment has done.")
	public ResponseEntity<TransactionResponseDTO> createTransaction(
			@Valid @RequestBody TransactionPaymentRequest transactionPaymentRequest) {
		
		TransactionPaymentRequest decryptTransactionPaymentRequest = 
				transactionPaymentRequest.decrypt(cryptoMngrCoreService);
		
		TransactionResponseDTO transactionResponseDTO = transactionManagement.
				createTransaction(decryptTransactionPaymentRequest);

		return ResponseEntity.ok(transactionResponseDTO);
	}
	
	/**
	 * Returns Transaction Object.
	 */
	@PostMapping("/transactionByCode")
//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Return Transaction payment has done.")
	public ResponseEntity<TransactionDTO> transactionByCode(
			@Valid @RequestBody TransactionCodeRequest transactionCodeRequest) {
		TransactionCodeRequest decryptTransactionCodeRequest = 
				transactionCodeRequest.decrypt(cryptoMngrCoreService);
		return ResponseEntity.ok(transactionManagement.
				findTransactionByCode(decryptTransactionCodeRequest).encrypt(cryptoMngrCoreService));
	}
	
	/**
	 * Returns All Transactions paging by duration.
	 */
	@PostMapping("/transactionByDuration")
//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Return All Transaction paging by duration.")
	public ResponseEntity<TransactionPagingDTO> transactionByDuration(
			@Valid @RequestBody TransactionDurationRequest transactionDurationRequest) {
		
		TransactionDurationRequest decryptTransactionDurationRequest = 
				transactionDurationRequest.decrypt(cryptoMngrCoreService);
		
		TransactionPagingDTO eTransactionPagingDTO = transactionManagement.
				findTransactionByDuration(decryptTransactionDurationRequest);
		
		List<TransactionDTO> eTransactionDTO = new ArrayList<TransactionDTO>();
		eTransactionPagingDTO.getTransactions().stream().forEach(s -> {
			eTransactionDTO.add(s.encrypt(cryptoMngrCoreService));
		});
		eTransactionPagingDTO.setTransactions(eTransactionDTO);
		
		return ResponseEntity.ok(eTransactionPagingDTO);
	}
	
	/**
	 * Returns Refund Transaction Response to the client.
	 */
	@PostMapping("/create/refund")
//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Return Refund Transaction.")
	public ResponseEntity<TransactionResponseDTO> refundTransaction(
			@Valid @RequestBody TransactionRefundRequest transactionRefundRequest) {
		
		TransactionRefundRequest decryptTransactionRefundRequest = 
				transactionRefundRequest.decrypt(cryptoMngrCoreService);
		
		TransactionResponseDTO transactionResponseDTO = transactionManagement.
				refundTransaction(decryptTransactionRefundRequest).encrypt(cryptoMngrCoreService);

		return ResponseEntity.ok(transactionResponseDTO);
	}
	
	@PostMapping("/refund/att")
	public void downloadRefundAttFile(@Valid @RequestBody URLRequest uRLRequest,
			HttpServletResponse response) {
		
		URLRequest decryptURLRequest = 
				uRLRequest.decrypt(cryptoMngrCoreService);
		
		try {
			InputStream  is = transactionManagement.downloadRefundAttFile(decryptURLRequest);
			IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}

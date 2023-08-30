package com.aman.payment.core.management;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.util.ReflectionTestUtils;

import com.aman.payment.core.mapper.TransactionMapper;
import com.aman.payment.core.model.dto.TransactionResponseDTO;
import com.aman.payment.core.model.payload.TransactionItemsRequest;
import com.aman.payment.core.model.payload.TransactionPaymentRequest;
import com.aman.payment.core.repository.PullAccountRepository;
import com.aman.payment.core.repository.TransactionRepository;
import com.aman.payment.core.service.PullAccountService;
import com.aman.payment.core.service.impl.PullAccountServiceImpl;
import com.aman.payment.core.service.impl.TransactionServiceImpl;
import com.aman.payment.passports.service.CryptoMngrPassportsService;
import com.aman.payment.passports.service.impl.CryptoMngrPassportsServiceImpl;


@ExtendWith(MockitoExtension.class)
class TransactionManagementImplTest {

	CryptoMngrPassportsService cryptoMngrService;
	
	@InjectMocks
	TransactionManagementImpl transactionManagement;
	
	@Mock
	PullAccountServiceImpl pullAccountService;
	
	@Mock
	PullAccountRepository pullAccountRepository;
	
	@Mock
	PullAccountManagementImpl pullAccountManagement;
	
	@Mock
	TransactionServiceImpl transactionService;
	
	@Mock
	TransactionMapper transactionMapper;
	
	@SpyBean
	TransactionRepository repository;
	
	@BeforeEach
	public void setUp() throws Exception {
		cryptoMngrService = new CryptoMngrPassportsServiceImpl();
		 ReflectionTestUtils.setField(cryptoMngrService, "KEY", "9y$B&E)H@McQfTjW");
		 ReflectionTestUtils.setField(cryptoMngrService, "IV", "7w!z%C&F)J@NcRfU");
//		 pullAccountService = new PullAccountServiceImpl(pullAccountRepository);
		 
//		 transactionManagement = new TransactionManagementImpl(null, cryptoMngrService, null, pullAccountService);
	}

	@Test
	void testCreateTransaction() {
//		String id = cryptoMngrService.encrypt("0");
		
		TransactionPaymentRequest transactionPaymentRequest = new TransactionPaymentRequest();
		transactionPaymentRequest.setServiceId("1");//("vwZMt4Z6ceoyki52nHkSug==");
		transactionPaymentRequest.setUserName("junit-test");//("sHFOpcIDv7DsbSFUuSHsLg==");
		transactionPaymentRequest.setAmount("200");//("Xtx28pjCoCq5DgfbgGxn7g==");
		transactionPaymentRequest.setVisaNumber("");
		transactionPaymentRequest.setPosId("1");//("vwZMt4Z6ceoyki52nHkSug==");
		transactionPaymentRequest.setMerchantId("1");//("vwZMt4Z6ceoyki52nHkSug==");
		transactionPaymentRequest.setPaymentTypeId("1");//("vwZMt4Z6ceoyki52nHkSug==");
		transactionPaymentRequest.setTax("0");//("RqMgU9sLio9xBrj2ReI1wQ==");
		
		 Set<TransactionItemsRequest> subServiceIds = new HashSet<TransactionItemsRequest>();
		 TransactionItemsRequest itemsRequest = new TransactionItemsRequest("1", "100");//("vwZMt4Z6ceoyki52nHkSug==", "Xtx28pjCoCq5DgfbgGxn7g==");
		 subServiceIds.add(itemsRequest);
		 
		 transactionPaymentRequest.setSubServiceIds(subServiceIds);
		 
		 TransactionResponseDTO transactionDTO = transactionManagement.createTransaction(transactionPaymentRequest);
		 
		 System.out.println(transactionDTO.getTransactionCode());
		 
	}

}



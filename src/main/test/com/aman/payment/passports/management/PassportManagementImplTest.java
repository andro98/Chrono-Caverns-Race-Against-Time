package com.aman.payment.passports.management;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import com.aman.payment.auth.service.impl.ApplicantServiceImpl;
import com.aman.payment.core.management.TransactionManagement;
import com.aman.payment.core.management.TransactionManagementImpl;
import com.aman.payment.core.model.payload.TransactionItemsRequest;
import com.aman.payment.passports.model.payload.HoldRequest;
import com.aman.payment.passports.service.CryptoMngrPassportsService;
import com.aman.payment.passports.service.impl.CryptoMngrPassportsServiceImpl;
import com.aman.payment.passports.service.impl.PassportInfoServiceImpl;


@ExtendWith(MockitoExtension.class)
public class PassportManagementImplTest {
	
	@InjectMocks
	PassportManagementImpl passportManagement;
	
//	@Mock
	CryptoMngrPassportsServiceImpl cryptoMngrService;
	
	@Mock
	PassportInfoServiceImpl passportInfoService;
	
	@Mock
	ApplicantServiceImpl applicantService;
	
	@Mock
	TransactionManagementImpl transactionManagement;
	
	
	@BeforeEach
	public void setUp() throws Exception {
		cryptoMngrService = new CryptoMngrPassportsServiceImpl();
		 ReflectionTestUtils.setField(cryptoMngrService, "KEY", "9y$B&E)H@McQfTjW");
		 ReflectionTestUtils.setField(cryptoMngrService, "IV", "7w!z%C&F)J@NcRfU");
//		 ReflectionTestUtils.invokeMethod(cryptoMngrService, "decrypt", "");
//		 passportManagement.setCryptoMngrPassportsService(cryptoMngrService);
//		 transactionManagement = new TransactionManagementImpl(null, null)
	}

	@Test
	public void test_addHoldTransaction() {

		HoldRequest holdRequest = new HoldRequest();
		
		holdRequest.setServiceId("vwZMt4Z6ceoyki52nHkSug==");
//		holdRequest.setUserName("sHFOpcIDv7DsbSFUuSHsLg==");
		holdRequest.setAmount("Xtx28pjCoCq5DgfbgGxn7g==");
		holdRequest.setVisaNumber("");
		holdRequest.setPosId("vwZMt4Z6ceoyki52nHkSug==");
		holdRequest.setMerchantId("vwZMt4Z6ceoyki52nHkSug==");
		holdRequest.setPaymentTypeId("vwZMt4Z6ceoyki52nHkSug==");
		holdRequest.setTax("RqMgU9sLio9xBrj2ReI1wQ==");
		holdRequest.setIdNumber("TjJh45iO1xHg6ZqU9bWkIA==");
		holdRequest.setApplicantName("uZHTx2NITAWCcrBA+MR/sw==");
		holdRequest.setApplicantMobileNumber("fGH41tnx8+8qbwZcZ0/3Yg=="); 
		holdRequest.setRelativePerson("t76KGlk/xg3styUmnLk2Ew==");
		holdRequest.setRelativePersonMobile("fGH41tnx8+8qbwZcZ0/3Yg=="); 
		holdRequest.setRelativeType("");
		holdRequest.setPaymentMethodId("vwZMt4Z6ceoyki52nHkSug==");
		holdRequest.setLocationId("lTk1jxTWNdWnAnjBG4nEug==");

		 Set<TransactionItemsRequest> subServiceIds = new HashSet<TransactionItemsRequest>();
		 TransactionItemsRequest itemsRequest = new TransactionItemsRequest("vwZMt4Z6ceoyki52nHkSug==", "Xtx28pjCoCq5DgfbgGxn7g==");
		 subServiceIds.add(itemsRequest);
		 
//		 holdRequest.setSubServiceIds(subServiceIds);
		
		passportManagement.addHoldTransaction(holdRequest);
		
	}

}

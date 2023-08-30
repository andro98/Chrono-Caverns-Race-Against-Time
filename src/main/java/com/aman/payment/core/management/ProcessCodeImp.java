package com.aman.payment.core.management;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.aman.payment.core.model.PullAccount;
import com.aman.payment.core.model.Transaction;
import com.aman.payment.core.model.dto.PullAccountCalculationDTO;
import com.aman.payment.util.StatusConstant;

public abstract class ProcessCodeImp {

	@Autowired
	private PullAccountManagement pullAccountManagement;

	public PullAccountCalculationDTO calculatePullAccount(Set<Transaction> transactions) {

		PullAccountCalculationDTO calcObj = new PullAccountCalculationDTO();

		long totalTransactions = transactions.stream()
				.filter(o -> !o.getStatusFk().equals(StatusConstant.STATUS_REFUND)).count();
		double totalTransactionsAmount = transactions.stream()
				.filter(o -> !o.getStatusFk().equals(StatusConstant.STATUS_REFUND)).map(x -> x.getAmount())
				.reduce((double) 0, Double::sum);
		
		double totalTransactionsAmountCash = transactions.stream()
				.filter(o -> o.getPaymentTypeFk().getName().equals(StatusConstant.PAYMENT_CASH) 
						&& !o.getStatusFk().equals(StatusConstant.STATUS_REFUND))
				.map(x -> x.getAmount()).reduce((double) 0, Double::sum);
		double totalTransactionsAmountVisa = transactions.stream()
				.filter(o -> o.getPaymentTypeFk().getName().equals(StatusConstant.PAYMENT_VISA) 
						&& !o.getStatusFk().equals(StatusConstant.STATUS_REFUND))
				.map(x -> x.getAmount()).reduce((double) 0, Double::sum);

		/*
		 * totalRefundTransactions =  Refund
		 * all transaction == refund
		 */
		long totalRefundTransactions = transactions.stream()
				.filter(o -> o.getStatusFk().equals(StatusConstant.STATUS_REFUND)).count();
		double totalRefundAmount = transactions.stream()
				.filter(o -> o.getStatusFk().equals(StatusConstant.STATUS_REFUND)).map(x -> x.getAmount())
				.reduce((double) 0, Double::sum);
		calcObj.setTotalRefundTransactions(totalRefundTransactions);
		calcObj.setTotalRefundAmount(totalRefundAmount);

		/*
		 * totalCancelledTransactions =  Canceled
		 * all transaction == Canceled
		 */
		long totalCancelledTransactions = transactions.stream()
				.filter(o -> o.getStatusFk().equals(StatusConstant.STATUS_CANCELED)).count();
		double totalCancelledAmount = transactions.stream()
				.filter(o -> o.getStatusFk().equals(StatusConstant.STATUS_CANCELED)).map(x -> x.getAmount())
				.reduce((double) 0, Double::sum);
		calcObj.setTotalCancelledTransactions(totalCancelledTransactions);
		calcObj.setTotalCancelledAmount(totalCancelledAmount);

		double totalRefundAmountCash = transactions.stream()
				.filter(o -> o.getPaymentTypeFk().getName().equals(StatusConstant.PAYMENT_CASH) 
						&& o.getStatusFk().equals(StatusConstant.STATUS_REFUND))
				.map(x -> x.getAmount()).reduce((double) 0, Double::sum);
		calcObj.setTotalRefundAmountCash(totalRefundAmountCash);
		double totalRefundAmountVisa = transactions.stream()
				.filter(o -> o.getPaymentTypeFk().getName().equals(StatusConstant.PAYMENT_VISA) 
						&& o.getStatusFk().equals(StatusConstant.STATUS_REFUND))
				.map(x -> x.getAmount()).reduce((double) 0, Double::sum);
		calcObj.setTotalRefundAmountVisa(totalRefundAmountVisa);
		
		/*
		 * total = Final + Hold + Refund  -- إجمالى عدد المبيعات
		 * remove canceled cases
		 */
		calcObj.setTotalTransactions(totalTransactions);
		calcObj.setTotalTransactionAmount(totalTransactionsAmount);
		/* 
		 * collection = total - Refund  -- صافى عدد المبيعات
		 * remove refund cases
		 */
		calcObj.setTotalCollectionTransactions(totalTransactions - totalRefundTransactions);
		double setTotalCollectionAmount = totalTransactionsAmount - totalRefundAmount;
		calcObj.setTotalCollectionAmount(setTotalCollectionAmount);
		
		double setTotalCollectionAmountCash = totalTransactionsAmountCash - totalRefundAmountCash;
		double setTotalCollectionAmountVisa = totalTransactionsAmountVisa - totalRefundAmountVisa;
		calcObj.setTotalCollectionAmountCash(setTotalCollectionAmountCash);
		calcObj.setTotalCollectionAmountVisa(setTotalCollectionAmountVisa);
		/*
		 * settlement = total - Canceled  -- صافى المؤمن عليه
		 * remove updated canceled cases
		 */
		calcObj.setTotalSettlementTransactions(totalTransactions - totalCancelledTransactions);
		double setTotalSettlementAmount = totalTransactionsAmount - totalCancelledAmount;
		calcObj.setTotalSettlementAmount(setTotalSettlementAmount);
		
		return calcObj;

	}
	
	public PullAccountCalculationDTO calculatePullAccountSettlementCashAndVisa(Set<Transaction> transactions) {

		PullAccountCalculationDTO calcObj = new PullAccountCalculationDTO();
		
		double totalCollectionAmountCash = transactions.stream()
				.filter(o -> o.getPaymentTypeFk().getName().equals(StatusConstant.PAYMENT_CASH) 
						&& !o.getStatusFk().equals(StatusConstant.STATUS_REFUND))
				.map(x -> x.getAmount()).reduce((double) 0, Double::sum);
		double totalCollectionAmountVisa = transactions.stream()
				.filter(o -> o.getPaymentTypeFk().getName().equals(StatusConstant.PAYMENT_VISA) 
						&& !o.getStatusFk().equals(StatusConstant.STATUS_REFUND))
				.map(x -> x.getAmount()).reduce((double) 0, Double::sum);
		calcObj.setTotalCollectionAmountCash(Double.valueOf(totalCollectionAmountCash));
		calcObj.setTotalCollectionAmountVisa(Double.valueOf(totalCollectionAmountVisa));

		return calcObj;

	}
	
	public PullAccount createPullAccount(Date createdAt, String createdBy, String settlementCode,
			long posId, String locationCode, String locationName, double amount, String paymentMethod,
			long serviceId) throws Exception {
		PullAccount pullAccount;
		Optional<PullAccount> pullAccountOptional = pullAccountManagement.validatePullAccount(
				createdBy, settlementCode, serviceId);
		
		if (pullAccountOptional.isPresent()) {
			return pullAccountOptional.get();
//			pullAccount = calculateUpdatingPullAccount(paymentMethod, pullAccount, amount);
		} else {
			pullAccount = new PullAccount();
			pullAccount = addNewFirstPullAccount(createdAt, createdBy, settlementCode,
					posId, locationCode, locationName, pullAccount, amount, paymentMethod, serviceId);
			
			return pullAccountManagement.savePullAccount(pullAccount);
		}
		
	}
	
	public PullAccount createExternalPullAccount(Date createdAt, String createdBy, String settlementCode,
			long posId, String locationCode, String locationName, double amount, String paymentMethod,
			long serviceId) throws Exception {
		PullAccount pullAccount;
		Optional<PullAccount> pullAccountOptional = pullAccountManagement.validatePullAccount(
				createdBy, settlementCode, serviceId);
		
		if (pullAccountOptional.isPresent()) {
			return pullAccountOptional.get();
//			pullAccount = calculateUpdatingPullAccount(paymentMethod, pullAccount, amount);
		} else {
			pullAccount = new PullAccount();
			pullAccount = addNewFirstPullAccount(createdAt, createdBy, settlementCode,
					posId, locationCode, locationName, pullAccount, amount, paymentMethod, serviceId);
			
			return pullAccountManagement.savePullAccount(pullAccount);
		}
		
	}
	
	public PullAccount getRefundPullAccount(String settlementCode, long serviceId) {
		return pullAccountManagement.validatePullAccount(settlementCode, serviceId).get();
	}
	
	public PullAccount calculateUpdatingPullAccount(String paymentMethod, PullAccount ePullAccount, double amount) {
//		if (paymentMethod.equals(StatusConstant.PAYMENT_CASH)) {
//			ePullAccount.setSystemAmountCash(ePullAccount.getSystemAmountCash() + amount);
//		} else {
//			ePullAccount.setSystemAmountVisa(ePullAccount.getSystemAmountVisa() + amount);
//		}
//		ePullAccount.setSystemTotalAmount(ePullAccount.getSystemTotalAmount() + amount);
		ePullAccount.setTotalTransaction(ePullAccount.getTotalTransaction() + 1);
		return ePullAccount;
	}
	
	public PullAccount addNewFirstPullAccount(Date createdAt, String createdBy, String settlementCode,
			long posId, String locationCode, String locationName, PullAccount ePullAccount, 
			double amount, String paymentMethod, long serviceId) {
		ePullAccount.setPosId(posId);
		ePullAccount.setServiceId(serviceId);
		ePullAccount.setLocationCode(locationCode);
		ePullAccount.setLocationName(locationName);
		ePullAccount.setSettlementCode(settlementCode);
		ePullAccount.setStatusFk(StatusConstant.STATUS_NEW);
		ePullAccount.setCreatedAt(createdAt);
		ePullAccount.setCreatedBy(createdBy);
//		if (StatusConstant.PAYMENT_CASH.equals(paymentMethod)) {
//				ePullAccount.setSystemAmountCash(amount);
//		} else {
//				ePullAccount.setSystemAmountVisa(amount);
//		}
//		ePullAccount.setSystemTotalAmount(amount);
		ePullAccount.setTotalTransaction(1);
		return ePullAccount;
	}
	
	public PullAccount createRefundPullAccount(Date createdAt, String createdBy, String settlementCode,
			long posId, String locationCode, String locationName, PullAccount ePullAccount, 
			double amount, String paymentMethod, long serviceId) {
		ePullAccount.setPosId(posId);
		ePullAccount.setServiceId(serviceId);
		ePullAccount.setLocationCode(locationCode);
		ePullAccount.setLocationName(locationName);
		ePullAccount.setSettlementCode(settlementCode);
		ePullAccount.setStatusFk(StatusConstant.STATUS_NEW);
		ePullAccount.setCreatedAt(createdAt);
		ePullAccount.setCreatedBy(createdBy);
		if (paymentMethod.equals(StatusConstant.PAYMENT_CASH)) {
				ePullAccount.setSystemAmountCash(-amount);
		} else {
				ePullAccount.setSystemAmountVisa(-amount);
		}
		return ePullAccount;
	}

}

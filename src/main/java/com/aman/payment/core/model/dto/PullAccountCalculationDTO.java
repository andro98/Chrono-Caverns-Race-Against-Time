package com.aman.payment.core.model.dto;

public class PullAccountCalculationDTO {

	private long totalTransactions;
	private long totalCollectionTransactions;
	private long totalRefundTransactions;
	private long totalCancelledTransactions;
	private long totalSettlementTransactions;
	private long totalDepositTransactions;

	private Double totalTransactionAmount;
	private Double totalCollectionAmount;
	private Double totalCollectionAmountCash;
	private Double totalCollectionAmountVisa;
	private Double totalRefundAmount;
	private Double totalRefundAmountCash;
	private Double totalRefundAmountVisa;
	private Double totalCancelledAmount;
	
	private Double totalSettlementAmount;
	private Double totalSettlementAmountCash;
	private Double totalSettlementAmountVisa;
	private Double totalDepositAmount;
	
	public long getTotalCollectionTransactions() {
		return totalCollectionTransactions;
	}
	public void setTotalCollectionTransactions(long totalCollectionTransactions) {
		this.totalCollectionTransactions = totalCollectionTransactions;
	}
	public long getTotalRefundTransactions() {
		return totalRefundTransactions;
	}
	public void setTotalRefundTransactions(long totalRefundTransactions) {
		this.totalRefundTransactions = totalRefundTransactions;
	}
	public long getTotalCancelledTransactions() {
		return totalCancelledTransactions;
	}
	public void setTotalCancelledTransactions(long totalCancelledTransactions) {
		this.totalCancelledTransactions = totalCancelledTransactions;
	}
	public long getTotalSettlementTransactions() {
		return totalSettlementTransactions;
	}
	public void setTotalSettlementTransactions(long totalSettlementTransactions) {
		this.totalSettlementTransactions = totalSettlementTransactions;
	}
	public Double getTotalCollectionAmount() {
		return totalCollectionAmount;
	}
	public void setTotalCollectionAmount(Double totalCollectionAmount) {
		this.totalCollectionAmount = totalCollectionAmount;
	}
	public Double getTotalRefundAmount() {
		return totalRefundAmount;
	}
	public void setTotalRefundAmount(Double totalRefundAmount) {
		this.totalRefundAmount = totalRefundAmount;
	}
	public Double getTotalCancelledAmount() {
		return totalCancelledAmount;
	}
	public void setTotalCancelledAmount(Double totalCancelledAmount) {
		this.totalCancelledAmount = totalCancelledAmount;
	}
	public Double getTotalSettlementAmount() {
		return totalSettlementAmount;
	}
	public void setTotalSettlementAmount(Double totalSettlementAmount) {
		this.totalSettlementAmount = totalSettlementAmount;
	}
	public Double getTotalSettlementAmountCash() {
		return totalSettlementAmountCash;
	}
	public void setTotalSettlementAmountCash(Double totalSettlementAmountCash) {
		this.totalSettlementAmountCash = totalSettlementAmountCash;
	}
	public Double getTotalSettlementAmountVisa() {
		return totalSettlementAmountVisa;
	}
	public void setTotalSettlementAmountVisa(Double totalSettlementAmountVisa) {
		this.totalSettlementAmountVisa = totalSettlementAmountVisa;
	}
	public long getTotalTransactions() {
		return totalTransactions;
	}
	public void setTotalTransactions(long totalTransactions) {
		this.totalTransactions = totalTransactions;
	}
	public Double getTotalCollectionAmountCash() {
		return totalCollectionAmountCash;
	}
	public Double getTotalCollectionAmountVisa() {
		return totalCollectionAmountVisa;
	}
	public void setTotalCollectionAmountCash(Double totalCollectionAmountCash) {
		this.totalCollectionAmountCash = totalCollectionAmountCash;
	}
	public void setTotalCollectionAmountVisa(Double totalCollectionAmountVisa) {
		this.totalCollectionAmountVisa = totalCollectionAmountVisa;
	}
	public Double getTotalTransactionAmount() {
		return totalTransactionAmount;
	}
	public void setTotalTransactionAmount(Double totalTransactionAmount) {
		this.totalTransactionAmount = totalTransactionAmount;
	}
	public long getTotalDepositTransactions() {
		return totalDepositTransactions;
	}
	public Double getTotalDepositAmount() {
		return totalDepositAmount;
	}
	public void setTotalDepositTransactions(long totalDepositTransactions) {
		this.totalDepositTransactions = totalDepositTransactions;
	}
	public void setTotalDepositAmount(Double totalDepositAmount) {
		this.totalDepositAmount = totalDepositAmount;
	}
	public Double getTotalRefundAmountCash() {
		return totalRefundAmountCash;
	}
	public Double getTotalRefundAmountVisa() {
		return totalRefundAmountVisa;
	}
	public void setTotalRefundAmountCash(Double totalRefundAmountCash) {
		this.totalRefundAmountCash = totalRefundAmountCash;
	}
	public void setTotalRefundAmountVisa(Double totalRefundAmountVisa) {
		this.totalRefundAmountVisa = totalRefundAmountVisa;
	}

	

}
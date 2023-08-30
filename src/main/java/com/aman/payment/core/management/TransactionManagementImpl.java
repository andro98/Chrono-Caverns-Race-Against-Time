package com.aman.payment.core.management;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aman.payment.auth.model.SubService;
import com.aman.payment.auth.model.SubServiceQuota;
import com.aman.payment.auth.service.ServiceService;
import com.aman.payment.auth.service.SubServiceService;
import com.aman.payment.core.exception.TransactionException;
import com.aman.payment.core.mapper.TransactionMapper;
import com.aman.payment.core.model.PullAccount;
import com.aman.payment.core.model.Transaction;
import com.aman.payment.core.model.TransactionECR;
import com.aman.payment.core.model.TransactionItem;
import com.aman.payment.core.model.TransactionMids;
import com.aman.payment.core.model.dto.TransactionDTO;
import com.aman.payment.core.model.dto.TransactionMidsDTO;
import com.aman.payment.core.model.dto.TransactionPagingDTO;
import com.aman.payment.core.model.dto.TransactionResponseDTO;
import com.aman.payment.core.model.lookup.PaymentType;
import com.aman.payment.core.model.payload.PosMidRequest;
import com.aman.payment.core.model.payload.TransactionCodeRequest;
import com.aman.payment.core.model.payload.TransactionDurationRequest;
import com.aman.payment.core.model.payload.TransactionPaymentRequest;
import com.aman.payment.core.model.payload.TransactionRefundRequest;
import com.aman.payment.core.model.payload.URLRequest;
import com.aman.payment.core.service.CryptoMngrCoreService;
import com.aman.payment.core.service.PullAccountService;
import com.aman.payment.core.service.TransactionECRService;
import com.aman.payment.core.service.TransactionService;
import com.aman.payment.core.util.UtilCore;
import com.aman.payment.util.StatusConstant;
import com.aman.payment.util.Util;

@Component
public class TransactionManagementImpl extends ProcessCodeImp implements TransactionManagement {

	final static Logger logger = Logger.getLogger("TransactionManagementImpl");

	private TransactionService transactionService;
	private PullAccountService pullAccountService;
	private TransactionMapper transactionMapper;
	private SubServiceService subServiceService;
	private ServiceService serviceService;
	private CryptoMngrCoreService cryptoMngrCoreService;
	private TransactionECRService transactionECRService;

	@Value("${attachment.refund}")
	private String refundBasePackagePath;

	@Autowired
	public TransactionManagementImpl(TransactionService transactionService, TransactionMapper transactionMapper,
			PullAccountService pullAccountService, SubServiceService subServiceService,
			CryptoMngrCoreService cryptoMngrCoreService, ServiceService serviceService,
			TransactionECRService transactionECRService) {
		super();
		this.transactionService = transactionService;
		this.transactionMapper = transactionMapper;
		this.pullAccountService = pullAccountService;
		this.subServiceService = subServiceService;
		this.cryptoMngrCoreService = cryptoMngrCoreService;
		this.serviceService = serviceService;
		this.transactionECRService = transactionECRService;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public TransactionResponseDTO createTransaction(TransactionPaymentRequest transactionPaymentRequest) {

		Date createdAt = Date.from(Instant.now());
		Transaction transaction = new Transaction();
		transaction.setAmount(Double.valueOf(transactionPaymentRequest.getAmount()));
		transaction.setCreatedAt(createdAt);
		transaction.setCreatedBy(transactionPaymentRequest.getUserName());
		transaction.setMerchantId(Long.valueOf(transactionPaymentRequest.getMerchantId()));
		transaction.setPaymentTypeFk(
				new PaymentType(Long.parseLong(transactionPaymentRequest.getPaymentTypeId()), "test"));
		transaction.setPosId(Long.valueOf(transactionPaymentRequest.getPosId()));
		transaction.setServiceId(Long.valueOf(transactionPaymentRequest.getServiceId()));
		transaction.setStatusFk(StatusConstant.STATUS_FINAL);
		transaction.setVisaNumber(transactionPaymentRequest.getVisaNumber());
		transaction.setPaymentCode(transactionPaymentRequest.getPaymentCode());
		transaction.setTax(
				transactionPaymentRequest.getTax() != null ? Double.valueOf(transactionPaymentRequest.getTax()) : 0);
		transaction.setTransactionEcrFk(transactionPaymentRequest.getTransactionECRFk());

		Set<TransactionItem> transactionItems = new HashSet<TransactionItem>();
		Set<TransactionMids> transactionMids = new HashSet<TransactionMids>();

		if (transactionPaymentRequest.getSubServiceIds() != null) {
			transactionPaymentRequest.getSubServiceIds().stream().forEach(s -> {

//				SubService subService = subServiceService.findById(Long.parseLong(s.getSubSrviceId())).get();

				TransactionItem transactionItem = new TransactionItem();
				transactionItem.setAmount(s.getFees() != null ? Double.valueOf(s.getFees()) : 0);
				transactionItem.setSubServiceId(Long.parseLong(s.getSubSrviceId()));
				transactionItem.setTransactionFk(transaction);
				transactionItems.add(transactionItem);
				
//				transactionMids.addAll(calculateMids(transactionPaymentRequest.getServiceId(), transaction, 
//						transactionMids, subService, transactionPaymentRequest.getContractPaidAmount(),
//						transactionPaymentRequest.getContractCount()));

			});
		}
		
		for(TransactionMidsDTO mids : transactionPaymentRequest.getTransactionMids()) {
			
			TransactionMids eTransactionMidFkQouta = new TransactionMids();
			eTransactionMidFkQouta.setTransactionFk(transaction);
			eTransactionMidFkQouta.setMidValue(mids.getMidValue());
			eTransactionMidFkQouta.setMidAccount(mids.getMidAccountEnc());
			eTransactionMidFkQouta.setMidBank(mids.getMidBank());
			eTransactionMidFkQouta.setBeneficiary(mids.getBeneficiary());
			transactionMids.add(eTransactionMidFkQouta);
			
		}

		transaction.setTransactionItems(transactionItems);
		transaction.setTransactionMids(transactionMids);
		transaction.setTransCode(UtilCore.generateTransactionCode(transactionPaymentRequest.getPosId(),
				transactionPaymentRequest.getServiceId(), createdAt));
		transaction.setSettlementCode(UtilCore.generateSettelmentCode(transactionPaymentRequest.getPosId(),
				transactionPaymentRequest.getServiceId(), createdAt));

		PullAccount pullAccountFk = null;
		try {
			pullAccountFk = createPullAccount(transaction.getCreatedAt(), transaction.getCreatedBy(),
					transaction.getSettlementCode(), Long.valueOf(transactionPaymentRequest.getPosId()),
					transactionPaymentRequest.getLocationCode(), transactionPaymentRequest.getLocationName(),
					Double.valueOf(transactionPaymentRequest.getAmount()), transaction.getPaymentTypeFk().getName(),
					Long.valueOf(transactionPaymentRequest.getServiceId()));

		} catch (Exception e) {
//			e.printStackTrace();
			throw new IllegalArgumentException("لا يمكن اتمام العملية حيث تم عمل تقفيل اخر اليوم");
		}

		transaction.setPullAccountFk(pullAccountFk);

		return transactionMapper.transactionToTransactionResponseDTO(transactionService.save(transaction));

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public TransactionResponseDTO createExternalTransaction(TransactionPaymentRequest transactionPaymentRequest) {

		Date createdAt = Date.from(Instant.now());
		Transaction transaction = new Transaction();
		transaction.setAmount(Double.valueOf(transactionPaymentRequest.getAmount()));
		transaction.setCreatedAt(createdAt);
		transaction.setCreatedBy(transactionPaymentRequest.getUserName());
		transaction.setMerchantId(Long.valueOf(transactionPaymentRequest.getMerchantId()));
		transaction.setPaymentTypeFk(new PaymentType((long) 2, "Visa"));
		transaction.setPosId(Long.valueOf(transactionPaymentRequest.getPosId()));
		transaction.setServiceId(Long.valueOf(transactionPaymentRequest.getServiceId()));
		transaction.setStatusFk(StatusConstant.STATUS_FINAL);
//		transaction.setVisaNumber(transactionPaymentRequest.getVisaNumber());
		transaction.setTax(
				transactionPaymentRequest.getTax() != null ? Double.valueOf(transactionPaymentRequest.getTax()) : 0);

		Set<TransactionItem> transactionItems = new HashSet<TransactionItem>();
		Set<TransactionMids> transactionMids = new HashSet<TransactionMids>();

		if (transactionPaymentRequest.getSubServiceIds() != null) {
			transactionPaymentRequest.getSubServiceIds().stream().forEach(s -> {

				SubService subService = subServiceService.findById(Long.parseLong(s.getSubSrviceId())).get();

				TransactionItem transactionItem = new TransactionItem();
				transactionItem.setAmount(s.getFees() != null ? Double.valueOf(s.getFees()) : 0);
				transactionItem.setSubServiceId(Long.parseLong(s.getSubSrviceId()));
				transactionItem.setTransactionFk(transaction);
				transactionItems.add(transactionItem);
				
				transactionMids.addAll(calculateMids(transactionPaymentRequest.getServiceId(), transaction, 
						transactionMids, subService, transactionPaymentRequest.getContractPaidAmount(), 
						transactionPaymentRequest.getContractCount()));

			});
		}

//		Service service = serviceService.findById(Long.valueOf(transactionPaymentRequest.getServiceId())).get();
//
//		service.getServiceMids().stream().forEach(s -> {
//			if (s.getStatusFk().equals(StatusConstant.STATUS_ACTIVE)) {
//				TransactionMids eTransactionMids = new TransactionMids();
//				eTransactionMids.setServiceMidFk(s);
//				eTransactionMids.setTransactionFk(transaction);
//				String amountSt = String.format("%.2f",
//						(s.getFees() / 100) * Double.valueOf(transactionPaymentRequest.getAmount()));
//				eTransactionMids.setAmount(Double.valueOf(amountSt));
//				transactionMids.add(eTransactionMids);
//			}
//		});

		transaction.setTransactionItems(transactionItems);
		transaction.setTransactionMids(transactionMids);
		transaction.setTransCode(UtilCore.generateTransactionCode(transactionPaymentRequest.getPosId(),
				transactionPaymentRequest.getServiceId(), createdAt));
		transaction.setSettlementCode(UtilCore.generateSettelmentCode(transactionPaymentRequest.getPosId(),
				transactionPaymentRequest.getServiceId(), createdAt));

		PullAccount pullAccountFk = null;
		try {
			pullAccountFk = createExternalPullAccount(transaction.getCreatedAt(), transaction.getCreatedBy(),
					transaction.getSettlementCode(), Long.valueOf(transactionPaymentRequest.getPosId()),
					transactionPaymentRequest.getLocationCode(), transactionPaymentRequest.getLocationName(),
					Double.valueOf(transactionPaymentRequest.getAmount()), transaction.getPaymentTypeFk().getName(),
					Long.valueOf(transactionPaymentRequest.getServiceId()));

		} catch (Exception e) {
			e.printStackTrace();
		}

		transaction.setPullAccountFk(pullAccountFk);

		return transactionMapper.transactionToTransactionResponseDTO(transactionService.save(transaction));

	}

	@Override
	public TransactionDTO findTransactionByCode(TransactionCodeRequest transactionCodeRequest) {
		return transactionMapper.transactionToTransactionDTO(
				transactionService.findByTransCode(transactionCodeRequest.getTransactionCode()).get());
	}

	@Override
	public TransactionPagingDTO findTransactionByDuration(TransactionDurationRequest transactionDurationRequest) {

		Page<Transaction> pageResult = transactionService.findByDuration(transactionDurationRequest);

		TransactionPagingDTO transactionPagingDTO = new TransactionPagingDTO();
		transactionPagingDTO.setCount(pageResult.getTotalElements());
		transactionPagingDTO.setTotalPages(pageResult.getTotalPages());
		transactionPagingDTO.setTransactions(transactionMapper.transactionsToTransactionDTOs(pageResult.getContent()));

		return transactionPagingDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public TransactionResponseDTO refundTransaction(TransactionRefundRequest transactionRefundRequest) {

		Date createdAt = Date.from(Instant.now());
		Transaction cancelledTransaction = transactionService
				.findByTransCode(transactionRefundRequest.getRefundTransactionCode()).get();

		TransactionECR transactionECRFk = null;
		if (transactionRefundRequest.getRefundECRresponse() != null
				&& !transactionRefundRequest.getRefundECRresponse().isEmpty()) {
			transactionECRFk = transactionECRService
					.save(new TransactionECR(transactionRefundRequest.getRefundECRresponse(),
							transactionRefundRequest.getRefundECRrequest()));
		}

		Transaction eRefundTransaction = new Transaction();
		eRefundTransaction.setAmount(Double.valueOf(cancelledTransaction.getAmount()));
		eRefundTransaction.setCreatedAt(createdAt);
		eRefundTransaction.setCreatedBy(transactionRefundRequest.getUserName());
		eRefundTransaction.setMerchantId(Long.valueOf(cancelledTransaction.getMerchantId()));
		eRefundTransaction.setPaymentTypeFk(cancelledTransaction.getPaymentTypeFk());
		eRefundTransaction.setServiceId(Long.valueOf(cancelledTransaction.getServiceId()));
		eRefundTransaction.setStatusFk(StatusConstant.STATUS_REFUND);
		eRefundTransaction.setVisaNumber(cancelledTransaction.getVisaNumber());
		eRefundTransaction.setTax(Double.valueOf(cancelledTransaction.getTax()));

		eRefundTransaction.setPaymentCode(transactionRefundRequest.getRefundPaymentCode());
		eRefundTransaction.setTransactionEcrFk(transactionECRFk);

		Set<TransactionItem> eTransactionItem = new HashSet<>(cancelledTransaction.getTransactionItems());
		eRefundTransaction.setTransactionItems(eTransactionItem);

		PullAccount pullAccountFk = getRefundPullAccount(transactionRefundRequest.getSettlementCode().trim(),
				cancelledTransaction.getServiceId());
		eRefundTransaction.setPullAccountFk(pullAccountFk);
		eRefundTransaction.setPosId(pullAccountFk.getPosId());
		eRefundTransaction.setTransCode(UtilCore.generateTransactionCode(String.valueOf(pullAccountFk.getPosId()),
				String.valueOf(pullAccountFk.getServiceId()), createdAt));
		eRefundTransaction.setSettlementCode(transactionRefundRequest.getSettlementCode());

		String attUrl = null;
		try {
			attUrl = transactionService.saveAttachedFile(transactionRefundRequest.getAttRefund(),
					eRefundTransaction.getSettlementCode(), refundBasePackagePath);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(
					"refundTransaction An exception when saveAttachedFile! " + eRefundTransaction.getSettlementCode(),
					ex);
			throw new TransactionException(
					"refundTransaction An exception when saveAttachedFile! " + eRefundTransaction.getSettlementCode());
		}
		eRefundTransaction.setAttRefundUrl(attUrl);
		eRefundTransaction.setRefundParentId(cancelledTransaction.getId());
		eRefundTransaction.setComment(transactionRefundRequest.getComment());

		Transaction eTransaction = transactionService.save(eRefundTransaction);

		cancelledTransaction.setRefundParentId(eTransaction.getId());
		cancelledTransaction.setStatusFk(StatusConstant.STATUS_CANCELED);
		cancelledTransaction.setUpdatedAt(Date.from(Instant.now()));
		cancelledTransaction.setUpdatedBy(transactionRefundRequest.getUserName());

		cancelledTransaction = transactionService.save(cancelledTransaction);

		/*
		 * reflected values after refund operation in source pull_account have cancelled
		 * operation
		 */
//		PullAccount updatePullAccountFk = pullAccountService.findById(cancelledTransaction.getPullAccountFk().getId())
//				.get();
		pullAccountFk.setTotalCancelledTransaction(pullAccountFk.getTotalCancelledTransaction() + 1);
		pullAccountFk.setSystemTotalCancelledAmount(
				pullAccountFk.getSystemTotalCancelledAmount() + cancelledTransaction.getAmount());
		pullAccountFk.setTotalSettlementTransaction(pullAccountFk.getTotalSettlementTransaction() - 1);
		pullAccountFk.setSystemTotalSettlemetAmount(
				pullAccountFk.getSystemTotalSettlemetAmount() - cancelledTransaction.getAmount());
		pullAccountFk.setOverAmount(pullAccountFk.getOverAmount() + cancelledTransaction.getAmount());
		pullAccountFk.setIsOver(true);
		pullAccountService.save(pullAccountFk);

		return transactionMapper.transactionToTransactionResponseDTO(eTransaction);
	}

	@Override
	public InputStream downloadRefundAttFile(URLRequest uRLRequest) {

		File pdfFile = new File(uRLRequest.getUrl());
		InputStream is = null;
		try {
			is = new FileInputStream(pdfFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return is;
	}

	@Override
	public Set<TransactionDTO> findTransactionByPosId(TransactionCodeRequest transactionCodeRequest) {
		return transactionService.findTransactionByPosId(Long.valueOf(transactionCodeRequest.getPosId()));
	}

	@Override
	public void deleteTransaction(String transactionCode) {
		transactionService.deleteTransaction(transactionCode);
	}

	@Override
	public void updateTransactionStatus(String transactionCode, String updatedBy, Date updatedAt, String statusFk) {
		// TODO Auto-generated method stub
		Transaction eTransaction = transactionService.findByTransCode(transactionCode).get();
		eTransaction.setStatusFk(statusFk);
		eTransaction.setUpdatedAt(updatedAt);
		eTransaction.setUpdatedBy(updatedBy);

		transactionService.save(eTransaction);
	}

	@Override
	public void updateTransactionPaymentCodeAndVisaNumber(String transactionCode, String paymentCode,
			TransactionECR transactionECRFk, String visaNumber, String updatedBy, Date updatedAt) {
		// TODO Auto-generated method stub
		Transaction eTransaction = transactionService.findByTransCode(transactionCode).get();
		eTransaction.setUpdatedAt(updatedAt);
		eTransaction.setUpdatedBy(updatedBy);
		eTransaction.setVisaNumber(visaNumber);
		eTransaction.setPaymentCode(paymentCode);
		eTransaction.setTransactionEcrFk(transactionECRFk);

		transactionService.save(eTransaction);
	}

	private Set<TransactionMids> calculateMids(String serviceId, Transaction transactionFk,
			Set<TransactionMids> transactionMids, SubService subService, double contractPaidAmount, long contractCount) {

		if (serviceId.equals(String.valueOf(StatusConstant.BOOK_SERVICE_ID))) {
			for (SubServiceQuota mid : subService.getSubServicesQuota()) {

				TransactionMids eTransactionMids = transactionMids.stream()
						.filter(x -> x.getTransactionFk().getId().equals(transactionFk.getId())
								&& x.getMidAccount().equals(mid.getMidAccount()))
						.findAny().orElse(null);

				if (mid.getStatusFk().equals(StatusConstant.STATUS_ACTIVE) && mid.getFeesType().equals("n")) {

					if (eTransactionMids == null) {
						TransactionMids eTransactionMidFkQouta = new TransactionMids();
						eTransactionMidFkQouta.setTransactionFk(transactionFk);
						eTransactionMidFkQouta.setMidAccount(mid.getMidAccount());
						eTransactionMidFkQouta.setMidBank(mid.getMidBank());
						if(mid.getName().equals(StatusConstant.BOOK_QUOTA_TAHWEEL_RAKMY_AMAN) ||
								mid.getName().equals(StatusConstant.BOOK_QUOTA_TAHWEEL_RAKMY_NYABA)) {
							eTransactionMidFkQouta.setMidValue(Double.valueOf(String.format("%.2f", mid.getFees() * contractCount)));
						}else {
							eTransactionMidFkQouta.setMidValue(Double.valueOf(String.format("%.2f", mid.getFees())));
						}
						
						eTransactionMidFkQouta.setBeneficiary(mid.getBeneficiary());
						transactionMids.add(eTransactionMidFkQouta);
					} else {
						eTransactionMids.setMidValue(Double.valueOf(String.format("%.2f", (eTransactionMids.getMidValue() + mid.getFees()))));
						transactionMids.removeIf(
								v -> v.getTransactionFk().getId().equals(eTransactionMids.getTransactionFk().getId())
										&& v.getMidAccount().equals(eTransactionMids.getMidAccount()));
						transactionMids.add(eTransactionMids);
					}

				} 
//				else if (mid.getStatusFk().equals(StatusConstant.STATUS_ACTIVE) && mid.getFeesType().equals("%")) {
//				}

			}
			
			SubServiceQuota eSubServiceQuotaPercentage = subService.getSubServicesQuota().stream()
					.filter(x -> x.getStatusFk().equals(StatusConstant.STATUS_ACTIVE)
							&& x.getFeesType().equals("%"))
					.findAny().orElse(null);
			
			if(eSubServiceQuotaPercentage != null) {
				
				TransactionMids eTransactionMids = transactionMids.stream()
						.filter(x -> x.getTransactionFk().getId().equals(transactionFk.getId())
								&& x.getMidAccount().equals(eSubServiceQuotaPercentage.getMidAccount()))
						.findAny().orElse(null);
				if(eTransactionMids != null) {
					double darebtMabeatValue = eTransactionMids.getMidValue() / 100 * eSubServiceQuotaPercentage.getFees();
					eTransactionMids.setMidValue(
							Double.valueOf(String.format("%.2f", (eTransactionMids.getMidValue() + darebtMabeatValue))));
					transactionMids.removeIf(
							v -> v.getTransactionFk().getId().equals(eTransactionMids.getTransactionFk().getId())
									&& v.getMidAccount().equals(eTransactionMids.getMidAccount()));
					transactionMids.add(eTransactionMids);
				}
				
			}

		} else if (serviceId.equals(String.valueOf(StatusConstant.CONTRACT_SERVICE_ID))) {

			for (SubServiceQuota mid : subService.getSubServicesQuota()) {

				TransactionMids eTransactionMids = transactionMids.stream()
						.filter(x -> x.getTransactionFk().getId().equals(transactionFk.getId())
								&& x.getMidAccount().equals(mid.getMidAccount()))
						.findAny().orElse(null);

				if (mid.getStatusFk().equals(StatusConstant.STATUS_ACTIVE) && mid.getFeesType().equals("n")) {

					if (eTransactionMids == null) {
						TransactionMids eTransactionMidFkQouta = new TransactionMids();
						eTransactionMidFkQouta.setTransactionFk(transactionFk);
						eTransactionMidFkQouta.setMidAccount(mid.getMidAccount());
						eTransactionMidFkQouta.setMidBank(mid.getMidBank());
						eTransactionMidFkQouta.setMidValue(Double.valueOf(String.format("%.2f", mid.getFees())));
						eTransactionMidFkQouta.setBeneficiary(mid.getBeneficiary());
						transactionMids.add(eTransactionMidFkQouta);
					} else {
						eTransactionMids.setMidValue(Double.valueOf(String.format("%.2f", (eTransactionMids.getMidValue() + mid.getFees()))));
						transactionMids.removeIf(
								v -> v.getTransactionFk().getId().equals(eTransactionMids.getTransactionFk().getId())
										&& v.getMidAccount().equals(eTransactionMids.getMidAccount()));
						transactionMids.add(eTransactionMids);
					}

				} else if (mid.getStatusFk().equals(StatusConstant.STATUS_ACTIVE) && mid.getFeesType().equals("%")) {

					double perecntFirstThousand = 0;
					double perecntRemainingAmount = 0;
					
					if (eTransactionMids == null) {
						TransactionMids eTransactionMidFkQouta = new TransactionMids();
						eTransactionMidFkQouta.setTransactionFk(transactionFk);
						eTransactionMidFkQouta.setMidAccount(mid.getMidAccount());
						eTransactionMidFkQouta.setMidBank(mid.getMidBank());
						
						if(mid.getFees() == 1.5) {
							perecntFirstThousand = Util.calculateFirstThousandAndRemainingContractPaid(
									contractPaidAmount, mid.getFees());
						}else if(mid.getFees() == 2) {
							perecntRemainingAmount = Util.calculateSecondRemainingContractPaid(
									contractPaidAmount, mid.getFees());
						}
						
						eTransactionMidFkQouta.setMidValue(Double.valueOf(String.format("%.2f", (perecntFirstThousand + perecntRemainingAmount))));
						eTransactionMidFkQouta.setBeneficiary(mid.getBeneficiary());
						transactionMids.add(eTransactionMidFkQouta);
					} else {
						
						if(mid.getFees() == 1.5) {
							perecntFirstThousand = Util.calculateFirstThousandAndRemainingContractPaid(
									contractPaidAmount, mid.getFees());
						}else if(mid.getFees() == 2) {
							perecntRemainingAmount = Util.calculateSecondRemainingContractPaid(
									contractPaidAmount, mid.getFees());
						}
						
						eTransactionMids.setMidValue(Double.valueOf(String.format("%.2f", (eTransactionMids.getMidValue() + perecntFirstThousand + perecntRemainingAmount))));
						transactionMids.removeIf(
								v -> v.getTransactionFk().getId().equals(eTransactionMids.getTransactionFk().getId())
										&& v.getMidAccount().equals(eTransactionMids.getMidAccount()));
						transactionMids.add(eTransactionMids);
					}

				}

			}
			
		}

		return transactionMids;
	}

}

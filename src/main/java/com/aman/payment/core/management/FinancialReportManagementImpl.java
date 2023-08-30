package com.aman.payment.core.management;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.aman.payment.core.mapper.PullAccountMapper;
import com.aman.payment.core.mapper.TransactionMapper;
import com.aman.payment.core.model.PullAccount;
import com.aman.payment.core.model.dto.FinanacialMidsDTO;
import com.aman.payment.core.model.dto.FinancialAuditDTO;
import com.aman.payment.core.model.dto.FinancialAuditPagingDTO;
import com.aman.payment.core.model.payload.FinancialReportRequest;
import com.aman.payment.core.service.CryptoMngrCoreService;
import com.aman.payment.core.service.PullAccountService;
import com.aman.payment.core.service.TransactionService;

@Component
public class FinancialReportManagementImpl implements FinancialReportManagement {

	final static Logger logger = Logger.getLogger("FinancialReportManagementImpl");

	private final PullAccountService pullAccountService;
	private final PullAccountMapper pullAccountMapper;
	private final CryptoMngrCoreService cryptoMngrCoreService;
	private final TransactionService transactionService;
	private final TransactionMapper transactionMapper;


	@Autowired
	public FinancialReportManagementImpl(PullAccountService pullAccountService, CryptoMngrCoreService cryptoMngrCoreService,
			PullAccountMapper pullAccountMapper, TransactionService transactionService, TransactionMapper transactionMapper) {
		super();
		this.pullAccountService = pullAccountService;
		this.cryptoMngrCoreService = cryptoMngrCoreService;
		this.pullAccountMapper = pullAccountMapper;
		this.transactionService = transactionService;
		this.transactionMapper = transactionMapper;
	}

	@Override
	public FinancialAuditPagingDTO getFinancialReviewReport(FinancialReportRequest financialReportRequest) {
		Page<PullAccount> pageResult = pullAccountService.getFinancialReviewReport(financialReportRequest);
		
		FinancialAuditPagingDTO financialAuditPagingDTO = new FinancialAuditPagingDTO();
		financialAuditPagingDTO.setCount(pageResult.getTotalElements());
		financialAuditPagingDTO.setTotalPages(pageResult.getTotalPages());
		
		List<String> eTransaction = new ArrayList<String>();
		pullAccountMapper.pullAccountsToFinancialAuditDTOs(pageResult.getContent()).stream().forEach(s -> {
			eTransaction.add(cryptoMngrCoreService.encrypt(s.toString()));
		});
		financialAuditPagingDTO.setTransactions(eTransaction);

		return financialAuditPagingDTO;
	}

	@Override
	public FinancialAuditPagingDTO getAuditReportByDaily(FinancialReportRequest financialReportRequest) {
		Page<FinancialAuditDTO> pageResult = pullAccountService.getAuditReportByDaily(financialReportRequest);
		
		FinancialAuditPagingDTO financialAuditPagingDTO = new FinancialAuditPagingDTO();
		financialAuditPagingDTO.setCount(pageResult.getTotalElements());
		financialAuditPagingDTO.setTotalPages(pageResult.getTotalPages());
		
		List<String> eTransaction = new ArrayList<String>();
		pageResult.getContent().stream().forEach(s -> {
			eTransaction.add(cryptoMngrCoreService.encrypt(s.toString()));
		});
		financialAuditPagingDTO.setTransactions(eTransaction);

		return financialAuditPagingDTO;
	}

	@Override
	public FinancialAuditPagingDTO getAuditReportByLocation(FinancialReportRequest financialReportRequest) {
		Page<FinancialAuditDTO> pageResult = pullAccountService.getAuditReportByLocation(financialReportRequest);
		
		FinancialAuditPagingDTO financialAuditPagingDTO = new FinancialAuditPagingDTO();
		financialAuditPagingDTO.setCount(pageResult.getTotalElements());
		financialAuditPagingDTO.setTotalPages(pageResult.getTotalPages());

		List<String> eTransaction = new ArrayList<String>();
		pageResult.getContent().stream().forEach(s -> {
			eTransaction.add(cryptoMngrCoreService.encrypt(s.toString()));
		});
		financialAuditPagingDTO.setTransactions(eTransaction);

		return financialAuditPagingDTO;
	}
	
	@Override
	public FinancialAuditPagingDTO getAuditReportByCourt(FinancialReportRequest financialReportRequest) {
		Page<FinancialAuditDTO> pageResult = pullAccountService.getAuditReportByCourt(financialReportRequest);
		
		FinancialAuditPagingDTO financialAuditPagingDTO = new FinancialAuditPagingDTO();
		financialAuditPagingDTO.setCount(pageResult.getTotalElements());
		financialAuditPagingDTO.setTotalPages(pageResult.getTotalPages());

		List<String> eTransaction = new ArrayList<String>();
		pageResult.getContent().stream().forEach(s -> {
			eTransaction.add(cryptoMngrCoreService.encrypt(s.toString()));
		});
		financialAuditPagingDTO.setTransactions(eTransaction);

		return financialAuditPagingDTO;
	}
	
	@Override
	public FinancialAuditPagingDTO getAuditReportBySector(FinancialReportRequest financialReportRequest) {
		Page<FinancialAuditDTO> pageResult = pullAccountService.getAuditReportBySector(financialReportRequest);
		
		FinancialAuditPagingDTO financialAuditPagingDTO = new FinancialAuditPagingDTO();
		financialAuditPagingDTO.setCount(pageResult.getTotalElements());
		financialAuditPagingDTO.setTotalPages(pageResult.getTotalPages());

		List<String> eTransaction = new ArrayList<String>();
		pageResult.getContent().stream().forEach(s -> {
			eTransaction.add(cryptoMngrCoreService.encrypt(s.toString()));
		});
		financialAuditPagingDTO.setTransactions(eTransaction);

		return financialAuditPagingDTO;
	}

	@Override
	public FinancialAuditPagingDTO getAuditReportByPOS(FinancialReportRequest financialReportRequest) {
		Page<FinancialAuditDTO> pageResult = pullAccountService.getAuditReportByPOS(financialReportRequest);
		
		FinancialAuditPagingDTO financialAuditPagingDTO = new FinancialAuditPagingDTO();
		financialAuditPagingDTO.setCount(pageResult.getTotalElements());
		financialAuditPagingDTO.setTotalPages(pageResult.getTotalPages());

		List<String> eTransaction = new ArrayList<String>();
		pageResult.getContent().stream().forEach(s -> {
			eTransaction.add(cryptoMngrCoreService.encrypt(s.toString()));
		});
		financialAuditPagingDTO.setTransactions(eTransaction);

		return financialAuditPagingDTO;
	}

	@Override
	public FinancialAuditPagingDTO getAuditReportByAgentAndLocation(FinancialReportRequest financialReportRequest) {
		Page<FinancialAuditDTO> pageResult = pullAccountService.getAuditReportByAgentAndLocation(financialReportRequest);
		
		FinancialAuditPagingDTO financialAuditPagingDTO = new FinancialAuditPagingDTO();
		financialAuditPagingDTO.setCount(pageResult.getTotalElements());
		financialAuditPagingDTO.setTotalPages(pageResult.getTotalPages());

		List<String> eTransaction = new ArrayList<String>();
		pageResult.getContent().stream().forEach(s -> {
			eTransaction.add(cryptoMngrCoreService.encrypt(s.toString()));
		});
		financialAuditPagingDTO.setTransactions(eTransaction);

		return financialAuditPagingDTO;
	}

	@Override
	public List<String> getReportPermissionDetails(FinancialReportRequest financialReportRequest) {
		List<FinancialAuditDTO> result = pullAccountService.
				getReportPermissionDetails(financialReportRequest);
		
		List<String> eTransaction = new ArrayList<String>();
		result.stream().forEach(s -> {
			eTransaction.add(cryptoMngrCoreService.encrypt(s.toString()));
		});

		return eTransaction;
	}

	@Override
	public FinancialAuditPagingDTO getAuditReportFinancialMidDetailsByTransaction(
			FinancialReportRequest financialReportRequest) {
		Page<FinanacialMidsDTO> pageResult = transactionService.
				getAuditReportFinancialMidDetailsByTransaction(financialReportRequest);
		
		FinancialAuditPagingDTO financialAuditPagingDTO = new FinancialAuditPagingDTO();
		financialAuditPagingDTO.setCount(pageResult.getTotalElements());
		financialAuditPagingDTO.setTotalPages(pageResult.getTotalPages());

		List<String> eTransaction = new ArrayList<String>();
		pageResult.getContent().stream().forEach(transaction -> {
			eTransaction.add(cryptoMngrCoreService.encrypt(transaction.toString()));
		});
		financialAuditPagingDTO.setTransactions(eTransaction);

		return financialAuditPagingDTO;
	}
	
	@Override
	public FinancialAuditPagingDTO getRefundAuditReportFinancialMidDetailsByTransaction(
			FinancialReportRequest financialReportRequest) {
		Page<FinanacialMidsDTO> pageResult = transactionService.
				getRefundAuditReportFinancialMidDetailsByTransaction(financialReportRequest);
		
		FinancialAuditPagingDTO financialAuditPagingDTO = new FinancialAuditPagingDTO();
		financialAuditPagingDTO.setCount(pageResult.getTotalElements());
		financialAuditPagingDTO.setTotalPages(pageResult.getTotalPages());

		List<String> eTransaction = new ArrayList<String>();
		pageResult.getContent().stream().forEach(transaction -> {
			eTransaction.add(cryptoMngrCoreService.encrypt(transaction.toString()));
		});
		financialAuditPagingDTO.setTransactions(eTransaction);

		return financialAuditPagingDTO;
	}
	
	@Override
	public FinancialAuditPagingDTO getCanceledAuditReportFinancialMidDetailsByTransaction(
			FinancialReportRequest financialReportRequest) {
		Page<FinanacialMidsDTO> pageResult = transactionService.
				getCanceledAuditReportFinancialMidDetailsByTransaction(financialReportRequest);
		
		FinancialAuditPagingDTO financialAuditPagingDTO = new FinancialAuditPagingDTO();
		financialAuditPagingDTO.setCount(pageResult.getTotalElements());
		financialAuditPagingDTO.setTotalPages(pageResult.getTotalPages());

		List<String> eTransaction = new ArrayList<String>();
		pageResult.getContent().stream().forEach(transaction -> {
			eTransaction.add(cryptoMngrCoreService.encrypt(transaction.toString()));
		});
		financialAuditPagingDTO.setTransactions(eTransaction);

		return financialAuditPagingDTO;
	}
	

	@Override
	public FinancialAuditPagingDTO getAuditReportFinancialMidDetailsByPOS(
			FinancialReportRequest financialReportRequest) {
		Page<FinanacialMidsDTO> pageResult = transactionService.
				getAuditReportFinancialMidDetailsByPOS(financialReportRequest);
		
		FinancialAuditPagingDTO financialAuditPagingDTO = new FinancialAuditPagingDTO();
		financialAuditPagingDTO.setCount(pageResult.getTotalElements());
		financialAuditPagingDTO.setTotalPages(pageResult.getTotalPages());

		List<String> eTransaction = new ArrayList<String>();
		pageResult.getContent().stream().forEach(transaction -> {
			eTransaction.add(cryptoMngrCoreService.encrypt(transaction.toString()));
		});
		financialAuditPagingDTO.setTransactions(eTransaction);

		return financialAuditPagingDTO;
	}

	@Override
	public FinancialAuditPagingDTO getAuditReportFinancialMidDetailsBySector(
			FinancialReportRequest financialReportRequest) {
		Page<FinanacialMidsDTO> pageResult = transactionService.
				getAuditReportFinancialMidDetailsBySector(financialReportRequest);
		
		FinancialAuditPagingDTO financialAuditPagingDTO = new FinancialAuditPagingDTO();
		financialAuditPagingDTO.setCount(pageResult.getTotalElements());
		financialAuditPagingDTO.setTotalPages(pageResult.getTotalPages());

		List<String> eTransaction = new ArrayList<String>();
		pageResult.getContent().stream().forEach(transaction -> {
			eTransaction.add(cryptoMngrCoreService.encrypt(transaction.toString()));
		});
		financialAuditPagingDTO.setTransactions(eTransaction);

		return financialAuditPagingDTO;
	}
	
	@Override
	public FinancialAuditPagingDTO getRefundAuditReportFinancialMidDetailsBySector(
			FinancialReportRequest financialReportRequest) {
		Page<FinanacialMidsDTO> pageResult = transactionService.
				getRefundAuditReportFinancialMidDetailsBySector(financialReportRequest);
		
		FinancialAuditPagingDTO financialAuditPagingDTO = new FinancialAuditPagingDTO();
		financialAuditPagingDTO.setCount(pageResult.getTotalElements());
		financialAuditPagingDTO.setTotalPages(pageResult.getTotalPages());

		List<String> eTransaction = new ArrayList<String>();
		pageResult.getContent().stream().forEach(transaction -> {
			eTransaction.add(cryptoMngrCoreService.encrypt(transaction.toString()));
		});
		financialAuditPagingDTO.setTransactions(eTransaction);

		return financialAuditPagingDTO;
	}

	@Override
	public FinancialAuditPagingDTO getAuditReportFinancialMidDetailsByLocation(
			FinancialReportRequest financialReportRequest) {
		Page<FinanacialMidsDTO> pageResult = transactionService.
				getAuditReportFinancialMidDetailsByLocation(financialReportRequest);
		
		FinancialAuditPagingDTO financialAuditPagingDTO = new FinancialAuditPagingDTO();
		financialAuditPagingDTO.setCount(pageResult.getTotalElements());
		financialAuditPagingDTO.setTotalPages(pageResult.getTotalPages());

		List<String> eTransaction = new ArrayList<String>();
		pageResult.getContent().stream().forEach(transaction -> {
			eTransaction.add(cryptoMngrCoreService.encrypt(transaction.toString()));
		});
		financialAuditPagingDTO.setTransactions(eTransaction);

		return financialAuditPagingDTO;
	}
	
	@Override
	public FinancialAuditPagingDTO getAuditReportFinancialMidDetailsByCourt(
			FinancialReportRequest financialReportRequest) {
		Page<FinanacialMidsDTO> pageResult = transactionService.
				getAuditReportFinancialMidDetailsByCourt(financialReportRequest);
		
		FinancialAuditPagingDTO financialAuditPagingDTO = new FinancialAuditPagingDTO();
		financialAuditPagingDTO.setCount(pageResult.getTotalElements());
		financialAuditPagingDTO.setTotalPages(pageResult.getTotalPages());

		List<String> eTransaction = new ArrayList<String>();
		pageResult.getContent().stream().forEach(transaction -> {
			eTransaction.add(cryptoMngrCoreService.encrypt(transaction.toString()));
		});
		financialAuditPagingDTO.setTransactions(eTransaction);

		return financialAuditPagingDTO;
	}

	@Override
	public FinancialAuditPagingDTO getAuditReportFinancialMidDetailsByCity(
			FinancialReportRequest financialReportRequest) {
		Page<FinanacialMidsDTO> pageResult = transactionService.
				getAuditReportFinancialMidDetailsByCity(financialReportRequest);
		
		FinancialAuditPagingDTO financialAuditPagingDTO = new FinancialAuditPagingDTO();
		financialAuditPagingDTO.setCount(pageResult.getTotalElements());
		financialAuditPagingDTO.setTotalPages(pageResult.getTotalPages());

		List<String> eTransaction = new ArrayList<String>();
		pageResult.getContent().stream().forEach(transaction -> {
			eTransaction.add(cryptoMngrCoreService.encrypt(transaction.toString()));
		});
		financialAuditPagingDTO.setTransactions(eTransaction);

		return financialAuditPagingDTO;
	}

	@Override
	public FinancialAuditPagingDTO getAuditReportFinancialMidDetailsByDaily(
			FinancialReportRequest financialReportRequest) {
		Page<FinanacialMidsDTO> pageResult = transactionService.
				getAuditReportFinancialMidDetailsByDaily(financialReportRequest);
		
		FinancialAuditPagingDTO financialAuditPagingDTO = new FinancialAuditPagingDTO();
		financialAuditPagingDTO.setCount(pageResult.getTotalElements());
		financialAuditPagingDTO.setTotalPages(pageResult.getTotalPages());

		List<String> eTransaction = new ArrayList<String>();
		pageResult.getContent().stream().forEach(transaction -> {
			eTransaction.add(cryptoMngrCoreService.encrypt(transaction.toString()));
		});
		financialAuditPagingDTO.setTransactions(eTransaction);

		return financialAuditPagingDTO;
	}
	
	@Override
	public FinancialAuditPagingDTO getRefundAuditReportFinancialMidDetailsByDaily(
			FinancialReportRequest financialReportRequest) {
		Page<FinanacialMidsDTO> pageResult = transactionService.
				getRefundAuditReportFinancialMidDetailsByDaily(financialReportRequest);
		
		FinancialAuditPagingDTO financialAuditPagingDTO = new FinancialAuditPagingDTO();
		financialAuditPagingDTO.setCount(pageResult.getTotalElements());
		financialAuditPagingDTO.setTotalPages(pageResult.getTotalPages());

		List<String> eTransaction = new ArrayList<String>();
		pageResult.getContent().stream().forEach(transaction -> {
			eTransaction.add(cryptoMngrCoreService.encrypt(transaction.toString()));
		});
		financialAuditPagingDTO.setTransactions(eTransaction);

		return financialAuditPagingDTO;
	}

	@Override
	public FinancialAuditPagingDTO getAuditReportFinancialMidDetailsByAgent(
			FinancialReportRequest financialReportRequest) {
		Page<FinanacialMidsDTO> pageResult = transactionService.
				getAuditReportFinancialMidDetailsByAgent(financialReportRequest);
		
		FinancialAuditPagingDTO financialAuditPagingDTO = new FinancialAuditPagingDTO();
		financialAuditPagingDTO.setCount(pageResult.getTotalElements());
		financialAuditPagingDTO.setTotalPages(pageResult.getTotalPages());

		List<String> eTransaction = new ArrayList<String>();
		pageResult.getContent().stream().forEach(transaction -> {
			eTransaction.add(cryptoMngrCoreService.encrypt(transaction.toString()));
		});
		financialAuditPagingDTO.setTransactions(eTransaction);

		return financialAuditPagingDTO;
	}
	
	@Override
	public FinancialAuditPagingDTO getAuditReportFinancialMidDetailsByTypeName(
			FinancialReportRequest financialReportRequest) {
		Page<FinanacialMidsDTO> pageResult = transactionService.
				getAuditReportFinancialMidDetailsByTypeName(financialReportRequest);
		
		FinancialAuditPagingDTO financialAuditPagingDTO = new FinancialAuditPagingDTO();
		financialAuditPagingDTO.setCount(pageResult.getTotalElements());
		financialAuditPagingDTO.setTotalPages(pageResult.getTotalPages());

		List<String> eTransaction = new ArrayList<String>();
		pageResult.getContent().stream().forEach(transaction -> {
			eTransaction.add(cryptoMngrCoreService.encrypt(transaction.toString()));
		});
		financialAuditPagingDTO.setTransactions(eTransaction);

		return financialAuditPagingDTO;
	}

	

}

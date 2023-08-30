package com.aman.payment.core.event;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.aman.payment.core.management.ProcessCodeImp;
import com.aman.payment.core.model.PullAccount;
import com.aman.payment.core.model.dto.PullAccountCalculationDTO;
import com.aman.payment.core.service.PullAccountService;
import com.aman.payment.util.StatusConstant;

@Component
public class CloseEndDayScheduler extends ProcessCodeImp{

	@Autowired
	private PullAccountService pullAccountService;
	private String username = "wpo.moi";
	
	@Scheduled(cron = "0 0 1 * * ?")
	public void cronJobSch() {
		
		List<PullAccount> newPullAccounts = pullAccountService
				.findByStatusFkAndCreatedByAndServiceId(StatusConstant.STATUS_NEW, username, 2);
        
		LocalDate currentDate = LocalDate.now();
		
		System.out.println("Fixed Rate scheduler::=================================== " + currentDate);
		
		for(PullAccount ePullAccount : newPullAccounts) {
			
			LocalDate pullAccountCreatedDate = ePullAccount.getCreatedAt().toInstant()
				      .atZone(ZoneId.systemDefault())
				      .toLocalDate();

			if(pullAccountCreatedDate.isBefore(currentDate)) {
				
				ePullAccount.setUpdatedAt(Date.from(Instant.now()));
				ePullAccount.setUpdatedBy(username);
		
				PullAccountCalculationDTO dPullAccountCalculationDTO = calculatePullAccount(ePullAccount.getTransactions());

				ePullAccount.setTotalRefundTransaction(dPullAccountCalculationDTO.getTotalRefundTransactions());
				ePullAccount.setSystemAmountRefund(dPullAccountCalculationDTO.getTotalRefundAmount());
				
				ePullAccount.setTotalCancelledTransaction(dPullAccountCalculationDTO.getTotalCancelledTransactions());
				ePullAccount.setSystemTotalCancelledAmount(dPullAccountCalculationDTO.getTotalCancelledAmount());
				
				ePullAccount.setTotalSettlementTransaction(dPullAccountCalculationDTO.getTotalSettlementTransactions());
				ePullAccount.setSystemTotalSettlemetAmount(dPullAccountCalculationDTO.getTotalSettlementAmount());
				
				ePullAccount.setTotalCollectionTransaction(dPullAccountCalculationDTO.getTotalCollectionTransactions());
				ePullAccount.setSystemTotalCollectionAmount(dPullAccountCalculationDTO.getTotalCollectionAmount());
				
				ePullAccount.setTotalTransaction(dPullAccountCalculationDTO.getTotalTransactions());
				
				ePullAccount.setSystemAmountVisa(dPullAccountCalculationDTO.getTotalCollectionAmountVisa());
				ePullAccount.setSystemAmountCash(dPullAccountCalculationDTO.getTotalCollectionAmountCash());
				
				ePullAccount.setDepositVisa(dPullAccountCalculationDTO.getTotalCollectionAmountVisa());
				
				ePullAccount.setSystemTotalAmount(dPullAccountCalculationDTO.getTotalTransactionAmount());
				
				ePullAccount.setDepositTotalAmount(dPullAccountCalculationDTO.getTotalTransactionAmount());

				ePullAccount.setIsDeficit(false);
				ePullAccount.setDeficitAmount(0);
				ePullAccount.setIsOver(false);
				ePullAccount.setOverAmount(0);

				ePullAccount.setStatusFk(StatusConstant.STATUS_PENDING);
				pullAccountService.save(ePullAccount);
				
			}
			
		}

	}

}

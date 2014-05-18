package com.beeInvestment.ddd;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

import pl.com.bottega.ecommerce.sharedkernel.Money;

import com.beeInvestment.application.ApproveTargetCommand;
import com.beeInvestment.application.InvestCommand;
import com.beeInvestment.application.PublishTargetCommand;
import com.beeInvestment.investment.domain.TargetStatus;

public class InvestmentTests extends BaseTestCase {
	
	
	@Test
	/**
	 * 1.1) User login
	 * 1.2) Go to view "loaded" targets, totally there are 3 targets(Tar1&2&3)
	 * 1.2) Tar1's total amount is 10000. Tar2's total amount is 50000.
	 * 1.3) Publish 2 targets by clicking "Publish" 
	 * 1.4) User logout
	 * 
	 * 3.1) Customer A log in.
	 * 3.2) Go to "my investment" page. There are 2 published target (Tar1&2) for investment.
	 * 3.3) Customer A invest 5000 on Tar 1; 
	 * 3.3.1) 5000 is "Freezing" status, Customer A able-to-investment amount reduce 5000
	 * 3.3.2) Tar 1 goes to "Fulfilling" status from " Published" status.
	 * 3.4) Customer A invest 8000 on Tar 2;
	 * 3.4.1) 8000 is "Freezing" status, Customer A able-to-investment amount reduce 8000
	 * 3.4.2) Tar 2 go to "Fulfilling" status from "Published" status.
	 * 3.5) Customer A log out.
	 * 
	 * 4.1) Customer B log in.
	 * 4.2) Go to "my investment" page. There are 2 published targets for investment.
	 * 4.3) Customer B invest 5000 on Tar 1; 
	 * 4.3.1) 5000 is "Freezing" status, Customer B able-to-investment amount reduce 5000
	 * 4.3.2) Tar 1 status is changed from "fulfilling" to "Waiting for approval" status.
	 * 3.4) Customer B invest 7000 on Tar 2;
	 * 3.4.1)  7000 is "Freezing" status, Customer B able-to-investment amount reduce 7000
	 * 3.5) Customer B log out. 
	 * 
	 * 6.1) User log in 
	 * 6.2) Go to view a list of published targets. there are 0 targets 
	 * 6.2.1) Go to view a list of "fulfilling" target, 1 target is there.
	 * 6.2.2) Go to view a list of "waiting for approval" target, 1 target is there.
	 * 6.3) Tar 1 is in "Waiting for approval". User click "Approve" on tar 1. 
	 * 6.3.1) Money 5000 is transfered from account of both Customer A and B to corporate account
	 * 6.3.2) Tar 1 go to "Rewarding" status
	 * 6.3.3) Two investments are created. They are invest A and invest B.
	 * 6.3.4) Two rewards RA1 and RA2 are created for invest A.
	 * 6.3.5) Two rewards RB1 and RB2 are created for invest B.
	 * 
	 * 6.4) Tar 2 has not been fully fulfilled, wait for 5 minutes (Pre-configured).
	 * 6.4.1) 8000 of Customer A un-freeze. able to investment amount of Customer A increase 8000
	 * 6.4.2) 7000 of Customer B un-freeze. able to investment amount of Customer B increase 7000
	 * 6.4.3) Tar 2 status go back to "Published" status from "Fulfilling" status
	 * 6.5) User logout.
	*/
	public void makeInvetmentTest() {
		gate.dispatch(new PublishTargetCommand(target1.getAggregateId().getId()));
		gate.dispatch(new PublishTargetCommand(target2.getAggregateId().getId()));
		gate.dispatch(new InvestCommand(target1.getAggregateId().getId(),customerA.getAggregateId().getId(),new BigDecimal(5000)));
		assertThat(getAccountA().getAvailableBalance(),equalTo(new Money(new BigDecimal(25000))));
		assertThat(getAccountA().getTotalBalance(),equalTo(new Money(new BigDecimal(30000))));
		transactionService.processTransaction();
		assertThat(getAccountA().getAvailableBalance(),equalTo(new Money(new BigDecimal(25000))));
		assertThat(getAccountA().getTotalBalance(),equalTo(new Money(new BigDecimal(30000))));
		gate.dispatch(new InvestCommand(target2.getAggregateId().getId(),customerA.getAggregateId().getId(),new BigDecimal(8000)));
		assertThat(getAccountA().getAvailableBalance(),equalTo(new Money(new BigDecimal(17000))));
		assertThat(getAccountA().getTotalBalance(),equalTo(new Money(new BigDecimal(30000))));
		transactionService.processTransaction();
		assertThat(getAccountA().getAvailableBalance(),equalTo(new Money(new BigDecimal(17000))));
		assertThat(getAccountA().getTotalBalance(),equalTo(new Money(new BigDecimal(30000))));
		
		gate.dispatch(new InvestCommand(target1.getAggregateId().getId(),customerB.getAggregateId().getId(),new BigDecimal(5000)));
		assertThat(getAccountB().getAvailableBalance(),equalTo(new Money(new BigDecimal(45000))));
		assertThat(getAccountB().getTotalBalance(),equalTo(new Money(new BigDecimal(50000))));
		transactionService.processTransaction();
		assertThat(getAccountB().getAvailableBalance(),equalTo(new Money(new BigDecimal(45000))));
		assertThat(getAccountB().getTotalBalance(),equalTo(new Money(new BigDecimal(50000))));
		gate.dispatch(new InvestCommand(target2.getAggregateId().getId(),customerB.getAggregateId().getId(),new BigDecimal(7000)));
		assertThat(getAccountB().getAvailableBalance(),equalTo(new Money(new BigDecimal(38000))));
		assertThat(getAccountB().getTotalBalance(),equalTo(new Money(new BigDecimal(50000))));
		transactionService.processTransaction();
		assertThat(getAccountB().getAvailableBalance(),equalTo(new Money(new BigDecimal(38000))));
		assertThat(getAccountB().getTotalBalance(),equalTo(new Money(new BigDecimal(50000))));
		assertThat(getTarget1().getStatus(),equalTo(TargetStatus.WAITING_APPROVAL));
		assertThat(getTarget2().getStatus(),equalTo(TargetStatus.OFFERRING));
		gate.dispatch(new ApproveTargetCommand(target1.getAggregateId().getId()));
		assertThat(getAccountA().getTotalBalance(),equalTo(new Money(new BigDecimal(25000))));
		assertThat(getAccountB().getTotalBalance(),equalTo(new Money(new BigDecimal(45000))));
		assertThat(getTarget1().getStatus(),equalTo(TargetStatus.REWARDING));
		assertThat(getTarget1().getOutstandingInvestments().size(),equalTo(2));
		assertThat(getTarget1().getOutstandingInvestments().get(0).getOutstandingRewards().size(),equalTo(2));
		assertThat(getTarget1().getOutstandingInvestments().get(1).getOutstandingRewards().size(),equalTo(2));
		assertThat(getAccountA().getAvailableBalance(),equalTo(new Money(new BigDecimal(17000))));
		assertThat(getAccountB().getAvailableBalance(),equalTo(new Money(new BigDecimal(38000))));
		targetService.targetTimeout();
		assertThat(getAccountA().getAvailableBalance(),equalTo(new Money(new BigDecimal(25000))));
		assertThat(getAccountB().getAvailableBalance(),equalTo(new Money(new BigDecimal(45000))));
	}
		
	



}

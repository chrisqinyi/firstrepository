package com.beeInvestment.ddd;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

import pl.com.bottega.ecommerce.sharedkernel.Money;

import com.beeInvestment.application.ApproveTargetCommand;
import com.beeInvestment.application.InvestCommand;
import com.beeInvestment.application.PublishTargetCommand;
import com.beeInvestment.application.RewardCommand;
import com.beeInvestment.investment.domain.TargetStatus;

public class RewardTests extends BaseTestCase {
	
	@Test
	/**
	 * 1.1) User login
	 * 1.2) Go to view "loaded" targets, totally there are 3 targets(Tar1&2&3)
	 * 1.2) Tar1's total amount is 10000. And click "publish".
	 * 1.4) User logout
	 * 
	 * 2.1) Customer A log in.
	 * 2.2) Go to "my investment" page. There is 1 published target for investment.
	 * 2.3) Customer A invest 5000 on Tar 1; 
	 * 3.3.1) 5000 is "Freezing" status, Customer A able-to-investment amount reduce 5000
	 * 3.3.2) Tar 1 goes to "Fulfilling" status from " Published" status.
	 * 4.1) Customer B log in.
	 * 4.2) Go to "my investment" page. There are 1 published target 1 for investment.
	 * 4.3) Customer B invest 5000 on Tar 1; 
	 * 4.3.1) 5000 is "Freezing" status, Customer B able-to-investment amount reduce 5000
	 * 4.3.2) Tar 1 status is changed from "fulfilling" to "Waiting for approval" status.
	 *
	 * 6.1) User log in 
	 * 6.2.2) Go to view a list of "waiting for approval" target, tar 1 is there, click "Approve"
	 * 6.3.1) Money 5000 is transfered from account of both Customer A and B to corporate account
	 * 6.3.2) Tar 1 go to "Rewarding" status
	 * 6.3.3) Two investments are created. They are invest A and invest B. The status of both of them are outstanding
	 * 6.3.4) Two rewards RA1 and RA2 are created for invest A.The status of both of them are "outstanding".
	 * 6.3.5) Two rewards RB1 and RB2 are created for invest B.The status of both of them are "outstanding"
	 * 6.4) User view target in "Rewarding" status. Target 1 is there and two period need to be rewarded.
	 * 6.5) User reward two periods. RA1, RA2, RB1 and RB2 are rewarded.
	 * 6.6) RA1,RA2,RB1,RB2 status are "closed" Invest A, B's status are "closed" 
	 * 6.7) The status of Target 1 goes to "success". 
	 * 6.8) User log out.
	 * 
	 */
	public void rewardTest() {
		gate.dispatch(new PublishTargetCommand(target1.getAggregateId().getId()));
		gate.dispatch(new InvestCommand(target1.getAggregateId().getId(),customerA.getAggregateId().getId(),new BigDecimal(5000)));
		assertThat(getAccountA().getAvailableBalance(),equalTo(new Money(new BigDecimal(25000))));
		assertThat(getAccountA().getTotalBalance(),equalTo(new Money(new BigDecimal(30000))));
		transactionService.processTransaction();
		assertThat(getAccountA().getAvailableBalance(),equalTo(new Money(new BigDecimal(25000))));
		assertThat(getAccountA().getTotalBalance(),equalTo(new Money(new BigDecimal(30000))));
		gate.dispatch(new InvestCommand(target1.getAggregateId().getId(),customerB.getAggregateId().getId(),new BigDecimal(5000)));
		assertThat(getAccountB().getAvailableBalance(),equalTo(new Money(new BigDecimal(45000))));
		assertThat(getAccountB().getTotalBalance(),equalTo(new Money(new BigDecimal(50000))));
		transactionService.processTransaction();
		assertThat(getAccountB().getAvailableBalance(),equalTo(new Money(new BigDecimal(45000))));
		assertThat(getAccountB().getTotalBalance(),equalTo(new Money(new BigDecimal(50000))));
		assertThat(getTarget1().getStatus(),equalTo(TargetStatus.WAITING_APPROVAL));
		gate.dispatch(new ApproveTargetCommand(target1.getAggregateId().getId()));
		assertThat(getAccountA().getTotalBalance(),equalTo(new Money(new BigDecimal(25000))));
		assertThat(getAccountB().getTotalBalance(),equalTo(new Money(new BigDecimal(45000))));
		assertThat(getTarget1().getStatus(),equalTo(TargetStatus.REWARDING));
		assertThat(getTarget1().getStatus(),equalTo(TargetStatus.REWARDING));
		assertThat(getTarget1().getOutstandingInvestments().size(),equalTo(2));
		assertThat(getTarget1().getOutstandingInvestments().get(0).getOutstandingRewards().size(),equalTo(2));
		assertThat(getTarget1().getOutstandingInvestments().get(1).getOutstandingRewards().size(),equalTo(2));
		gate.dispatch(new RewardCommand(getTarget1().getAggregateId().getId(),new BigDecimal(1)));
		gate.dispatch(new RewardCommand(getTarget1().getAggregateId().getId(),new BigDecimal(2)));
		assertThat(getTarget1().getStatus(),equalTo(TargetStatus.SUCCESS));
		assertThat(getAccountA().getAvailableBalance(),equalTo(new Money(25000)));
		assertThat(getAccountA().getTotalBalance(),equalTo(new Money(25000)));
		assertThat(getAccountB().getAvailableBalance(),equalTo(new Money(45000)));
		assertThat(getAccountB().getTotalBalance(),equalTo(new Money(45000)));
		transactionService.processTransaction();
		assertThat(getAccountA().getAvailableBalance(),equalTo(new Money(31500)));
		assertThat(getAccountA().getTotalBalance(),equalTo(new Money(31500)));
		assertThat(getAccountB().getAvailableBalance(),equalTo(new Money(51500)));
		assertThat(getAccountB().getTotalBalance(),equalTo(new Money(51500)));
		assertThat(getTarget2().getOutstandingInvestments().isEmpty(),equalTo(true));
	}

	
	
	
}

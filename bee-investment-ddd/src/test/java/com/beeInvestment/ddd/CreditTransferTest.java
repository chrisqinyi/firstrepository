package com.beeInvestment.ddd;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Test;

import pl.com.bottega.ecommerce.sharedkernel.Money;

import com.beeInvestment.application.ApproveTargetCommand;
import com.beeInvestment.application.InvestCommand;
import com.beeInvestment.application.PublishTargetCommand;
import com.beeInvestment.application.RewardCommand;
import com.beeInvestment.application.TransferCommand;
import com.beeInvestment.investment.domain.Investment;
import com.beeInvestment.investment.domain.TargetStatus;

public class CreditTransferTest extends BaseTestCase{
	
	@Test
	/**
	 *
	 * 1.1) User login
	 * 1.2) Go to view "loaded" targets, totally there are 3 targets(Tar1&2&3)
	 * 1.2) Tar2's total amount is 50000. And click "publish".
	 * 1.4) User logout
	 * 
	 * 2.1) Customer A log in.
	 * 2.2) Go to "my investment" page. There is 1 published target 2 for investment.
	 * 2.3) Customer A invest 20000 on Tar 2; 
	 * 3.3.1) 20000 is "Freezing" status, Customer A able-to-investment amount reduce 20000
	 * 3.3.2) Tar 1 goes to "Fulfilling" status from " Published" status.
	 * 4.1) Customer B log in.
	 * 4.2) Go to "my investment" page. There are 1 published target 2 for investment.
	 * 4.3) Customer B invest 30000 on Tar 2; 
	 * 4.3.1) 30000 is "Freezing" status, Customer B able-to-investment amount reduce 30000
	 * 4.3.2) Tar 2 status is changed from "fulfilling" to "Waiting for approval" status.
	 *
	 * 6.1) User log in 
	 * 6.2.2) Go to view a list of "waiting for approval" target, tar 2 is there, click "Approve"
	 * 6.3.1) Money 20000/30000 is transfered from account of both Customer A and B to corporate account
	 * 6.3.2) Tar 2 go to "Rewarding" status
	 * 6.3.3) Two investments are created. They are invest A and invest B. The status of both of them are "outstanding"
	 * 6.3.4) Two rewards RA11 and RA12 are created for invest A.The status of both of them are "outstanding".
	 * 6.3.5) Two rewards RB11 and RB12 are created for invest B.The status of both of them are "outstanding"
	 * 6.4) User log out
	 * 
	 * 7.0) Customer A log in.
	 * 7.1) Goes to "My investment page", from where goes to "My investment details" page. Invest A is there.
	 * 7.2) click on "Invest A", and then click "Credit transfer" button. After that, Invest A is ready for transferring.
	 * 7.3) Customer A log out
	 * 8.0) Customer B log in. 
	 * 8.1) Goes to "My investment page", Invest A is ready for investment.
	 * 8.2) Customer B  buy invest A
	 * 8.3) Status of Invest A goes to "Close". RA11 and RA12 goes to "Close" too. 
	 * 8.3.1) Customer A "Available for investment" money increased 20000
	 * 8.4) A new invest "Invest B2" is created at an "outstanding" status. Rewards RB21 and RB22 are created at an "outstanding" status.
	 * 8.4.1) Customer B "Availabe for investment" money reduced 20000 
	 * 8.5) Customer B logout.
	 * 
	 * 9.0) User log in
	 * 6.4) User view target in "Rewarding" status. Target 2 is there and two period need to be rewarded.
	 * 6.5) User reward two periods. RB11, RB12, RB21 and RB22 are rewarded.
	 * 6.6) RB11, RB12, RB21 and RB22 status are "closed" Invest B, B2s status are "closed" 
	 * 6.7) The status of Target 2 goes to "success". 
	 * 6.8) User log out. 
	 *  
	 * 
	 *
	 */
	
	
	public void creditTransferTest() {
		gate.dispatch(new PublishTargetCommand(target2.getAggregateId().getId()));
		gate.dispatch(new InvestCommand(target2.getAggregateId().getId(),customerA.getAggregateId().getId(),new BigDecimal(20000)));
		assertThat(new ArrayList<Investment>(getTarget2().getInvestments()).get(0).getAccount().getAggregateId(),equalTo(customerA.getAggregateId()));
		assertThat(getAccountA().getAvailableBalance(),equalTo(new Money(new BigDecimal(10000))));
		assertThat(getAccountA().getTotalBalance(),equalTo(new Money(new BigDecimal(30000))));
		assertThat(getTarget2().getStatus(),equalTo(TargetStatus.OFFERRING));
		gate.dispatch(new InvestCommand(target2.getAggregateId().getId(),customerB.getAggregateId().getId(),new BigDecimal(30000)));
		assertThat(new ArrayList<Investment>(getTarget2().getInvestments()).get(1).getAccount().getAggregateId(),equalTo(customerB.getAggregateId()));
		assertThat(getAccountB().getAvailableBalance(),equalTo(new Money(new BigDecimal(20000))));
		assertThat(getAccountB().getTotalBalance(),equalTo(new Money(new BigDecimal(50000))));
		assertThat(getTarget2().getStatus(),equalTo(TargetStatus.WAITING_APPROVAL));
		gate.dispatch(new ApproveTargetCommand(target2.getAggregateId().getId()));
		transactionService.processTransaction();
		assertThat(getAccountA().getAvailableBalance(),equalTo(new Money(new BigDecimal(10000))));
		assertThat(getAccountA().getTotalBalance(),equalTo(new Money(new BigDecimal(10000))));
		assertThat(getAccountB().getAvailableBalance(),equalTo(new Money(new BigDecimal(20000))));
		assertThat(getAccountB().getTotalBalance(),equalTo(new Money(new BigDecimal(20000))));
		assertThat(getTarget2().getStatus(),equalTo(TargetStatus.REWARDING));
		assertThat(getTarget2().getRewards().size(),equalTo(6));
		assertThat(getTarget2().getInvestments().size(),equalTo(2));
		gate.dispatch(new TransferCommand(target2.getAggregateId().getId(),new BigDecimal(0),customerB.getAggregateId().getId()));
		//transactionService.processTransaction();
		assertThat(new ArrayList<Investment>(getTarget2().getInvestments()).get(0).getAccount().getAggregateId(),equalTo(getAccountB().getAggregateId()));
		assertThat(new ArrayList<Investment>(getTarget2().getInvestments()).get(1).getAccount().getAggregateId(),equalTo(getAccountB().getAggregateId()));
		assertThat(getAccountA().getAvailableBalance(),equalTo(new Money(new BigDecimal(30000))));
		assertThat(getAccountA().getTotalBalance(),equalTo(new Money(new BigDecimal(30000))));
		assertThat(getAccountB().getAvailableBalance(),equalTo(new Money(new BigDecimal(0))));
		assertThat(getAccountB().getTotalBalance(),equalTo(new Money(new BigDecimal(0))));
		gate.dispatch(new RewardCommand(getTarget2().getAggregateId().getId(),new BigDecimal(1)));
		gate.dispatch(new RewardCommand(getTarget2().getAggregateId().getId(),new BigDecimal(2)));
		gate.dispatch(new RewardCommand(getTarget2().getAggregateId().getId(),new BigDecimal(3)));
		//assertThat(getTarget2().getInvestments().size(),equalTo(0));

	}
 
	
	

}

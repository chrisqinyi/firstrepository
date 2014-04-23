package org.beeInvestment.model;

import java.util.List;

import org.junit.Test;

public class CreditTransferTests extends BaseTestCase{
	
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
//		Investment originalInvestment=null;
//		CreditAssignment assignment=customer.offerInvestment(originalInvestment);
	}
 
	
	

}

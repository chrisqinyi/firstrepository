package com.beeInvestment.ddd;

import org.junit.Test;

import com.beeInvestment.application.CancelTargetCommand;
import com.beeInvestment.application.PublishTargetCommand;


public class TargetTests extends BaseTestCase {
	
	@Test
	/**
	 * 2.1) User login
	 * 2.2) Go to view a list of latest "loaded" targets, totally there are 3 targets.
	 * 2.2.1) Target 1(10000-amount, 2 periods to reward all, 2 minute per reward ).
	 * 2.2.2) Target 2(50000-amount, 3 Periods to reward all, 2 minutes per reward),
	 * 2.2.3) Target 3(30000-amount, 4 Periods to reward all, 1 minute per reward);
	 * 2.3) Get target 1, and click "Publish" 
	 * 2.3.1) Get target 2, click "Publish";
	 * 2.4) User logout
	 * 
	 * 3.1) Cust A log in again. 
	 * 3.2) Go to "my investment" page. There are 2 "Published" target1&2 for investment.
	 * 3.3) Cust A log out
	 * 
	 * 4.1) User log in 
	 * 4.2) Go to view  "published" targets. totally there are target 1&2 there.
	 * 4.3) Click "edit" on target 1, make modification and click "Publish" again.
	 * 4.4) Go to view "loaded" targets, only target 3 there.
	 * 4.5) User log out
	 * 
	 */
	public void publishTargetTest(){
		gate.dispatch(new PublishTargetCommand(target1.getAggregateId().getId()));
		gate.dispatch(new PublishTargetCommand(target2.getAggregateId().getId()));
		gate.dispatch(new CancelTargetCommand(target1.getAggregateId().getId()));
		gate.dispatch(new PublishTargetCommand(target1.getAggregateId().getId()));
	}
	
			
	
	
	

}

package com.beeInvestment.ddd;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import com.beeInvestment.account.domain.Account;
import com.beeInvestment.account.domain.Withdraw;
import com.beeInvestment.application.ConfirmTransactionCommand;
import com.beeInvestment.application.FreezeAccountCommand;
import com.beeInvestment.application.WithdrawCommand;
public class AccountTest extends BaseTestCase {
	
	@Test
	/**
	 * 1)user login
	 * 2)user go to my account page
	 * 3)user can see his account summary
	 */
	public void AccountSummary() {
		assertThat(customerA.getAggregateId(),notNullValue());
		//Account getAccountA()=accountRepository.load(new AggregateId(testHelper.getCustomerAId()));
		assertThat(getAccountA().getAvailableBalance(),equalTo(new Money(new BigDecimal(30000))));
		assertThat(customerB.getAggregateId(),notNullValue());
		//Account getAccountB()=accountRepository.load(new AggregateId(testHelper.getCustomerBId()));
		assertThat(getAccountB().getAvailableBalance(),equalTo(new Money(new BigDecimal(50000))));
	}

	@Test
	/**
	 *  1) user login
	 * 2) user go to my account page
	 * 3) user choose deposit function
	 * 4) deposit a certain amount to his account from his external bank account.
	 */
	public void deposit() {
		assertThat(customerA.getAggregateId(),notNullValue());
		//Account getAccountA()=accountRepository.load(new AggregateId(testHelper.getCustomerAId()));
		assertThat(getAccountA().getAvailableBalance(),equalTo(new Money(new BigDecimal(30000))));
		assertThat(customerB.getAggregateId(),notNullValue());
		//Account getAccountB()=accountRepository.load(new AggregateId(testHelper.getCustomerBId()));
		assertThat(getAccountB().getAvailableBalance(),equalTo(new Money(new BigDecimal(50000))));
	}

	
	@Test
	/**
	 *  1) user login
	 * 2) user go to my account page
	 * 3) user choose withdraw function
	 * 4) transfer a certain amount from his account to his external bank account.
	 */
	public void applyForWithdraw() {
		gate.dispatch(new WithdrawCommand(customerA.getAggregateId().getId(),new BigDecimal(30000)));
		assertThat(getAccountA().getAvailableBalance(),equalTo(new Money(new BigDecimal(0))));
		assertThat(getAccountA().getTotalBalance(),equalTo(new Money(new BigDecimal(30000))));
	}
	@Test
	public void approveWithdraw() {
		Withdraw withdraw=(Withdraw) gate.dispatch(new WithdrawCommand(customerA.getAggregateId().getId(),new BigDecimal(30000)));
		gate.dispatch(new ConfirmTransactionCommand(withdraw.getAggregateId().getId()));
		assertThat(getAccountA().getAvailableBalance(),equalTo(new Money(new BigDecimal(0))));
		assertThat(getAccountA().getTotalBalance(),equalTo(new Money(new BigDecimal(0))));
	}
	

	@Test
	/**
	 * 1) Staff login BOA
	 * 2) Enter search criteria and click "search" button
	 * 3) Get a list of investment accounts.
	 * 4) View details of a particular account.
	 * 5) Choose freeze button to freeze account.
	 */
	public void freezeAccount() {
		Withdraw withdraw=(Withdraw)gate.dispatch(new WithdrawCommand(customerA.getAggregateId().getId(),new BigDecimal(1000)));
		gate.dispatch(new FreezeAccountCommand(getAccountA().getAggregateId().getId()));
		expectedEx.expect(RuntimeException.class);
	    expectedEx.expectMessage("Account frozen!");
	    gate.dispatch(new WithdrawCommand(customerA.getAggregateId().getId(),new BigDecimal(1000)));
		expectedEx.expect(RuntimeException.class);
	    expectedEx.expectMessage("Account frozen!");
	    gate.dispatch(new ConfirmTransactionCommand(withdraw.getAggregateId().getId()));
	}

	

}

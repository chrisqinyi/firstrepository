package org.beeInvestment.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.beeInvestment.customer.domain.Credential;

public class AccountTests extends BaseTestCase {
	
	@Test
	/**
	 * 1)user login
	 * 2)user go to my account page
	 * 3)user can see his account summary
	 */
	public void AccountSummary() {
		
//		Credential credential = new TestValidCredential();
//		Customer customer = bee.authenticate(credential);
//		Account account=customer.getAccount();
		
//        assertNotNull(account);
	}

	@Test
	/**
	 *  1) user login
	 * 2) user go to my account page
	 * 3) user choose deposit function
	 * 4) deposit a certain amount to his account from his external bank account.
	 */
	public void deposit() {
//		Account account = customer.getAccount();
//		account.setBalance(BigDecimal.valueOf(5000));
//		BigDecimal balance = account.getBalance();
//		Deposit deposit=customer.deposit(new Deposit(new BigDecimal(5000)));
//		assertTrue("Account balane should increase properly after deposity transaction",5000 == customer.getAccount().getBalance().doubleValue()-balance.doubleValue());

	}

	
	@Test
	/**
	 *  1) user login
	 * 2) user go to my account page
	 * 3) user choose withdraw function
	 * 4) transfer a certain amount from his account to his external bank account.
	 */
	public void applyForWithdraw() {
//		Withdraw withdraw=customer.applyForWithdraw(new Withdraw(new BigDecimal(5000)));
	}
	@Test
	public void approveWithdraw() {
		
//		List<Withdraw> withdraws= user.getWithdrawList(null);
//		user.approveWithdraw(new Withdraw(new BigDecimal(5000)));
//		Account account=user.getAccount(customer);
//		customer.getAccount();
	}
	
	@Test
	/**
	 * 1) Staff login BOA
	 * 2) Enter search criteria and click "search" button
	 * 3) Get a list of investment accounts.
	 */
	public void getInvestAccountList() {
//		Map criteria = null;
//		List<Account> accounts=user.getAccountList(criteria);
	}
	
	@Test
	/**
	 * 1) Staff login BOA
	 * 2) Enter search criteria and click "search" button
	 * 3) Get a list of investment accounts.
	 * 4) View details of a particular account.
	 */
	public void viewInvestmentAccount() {
//		Map criteria=null;
//		Account account=user.getAccount(criteria);
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
//		Map criteria=null;
//		Account account=user.getAccount(criteria);
//		user.freezeAccount(account);
	}

	

}

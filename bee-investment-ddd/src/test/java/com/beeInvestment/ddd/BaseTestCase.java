package com.beeInvestment.ddd;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.com.bottega.cqrs.command.Gate;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import com.beeInvestment.account.domain.Account;
import com.beeInvestment.account.domain.AccountRepository;
import com.beeInvestment.application.DepositCommand;
import com.beeInvestment.application.LoadTargetCommand;
import com.beeInvestment.application.RegisterCommand;
import com.beeInvestment.customer.domain.Customer;
import com.beeInvestment.investment.domain.Target;
import com.beeInvestment.investment.domain.TargetRepository;
import com.beeInvestment.transaction.domain.TransactionService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/functionalTestsContext.xml")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public abstract class BaseTestCase {
	@Inject
	protected Gate gate;
	protected Customer customerA;
	protected Account accountA;
	protected Customer customerB;
	protected Account accountB;
	protected Target target1;
	protected Target target2;
	protected Target target3;
//	@Inject
//	protected TestHelper testHelper;
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	@Inject
	protected AccountRepository accountRepository;
	@Inject
	protected TargetRepository targetRepository;
	@Inject
	protected TransactionService transactionService;
	@Before
	/**
	 * 1) Create two customers Cust A and Cust B
	 * 2) Cust A with total balance 30000. Cust B with total balance 50000.
	 * 3) Create three targets.Tar 1, Tar 2 and Tar 3
	 * 3.1) Target 1(10000-amount, 2 periods to reward all, 2 minute per reward ). what's the interest rate?
	 * 3.2) Target 2(50000-amount, 3 Periods to reward all, 2 minutes per reward), what's the interest rate?
	 * 3.3) Target 3(30000-amount, 4 Periods to reward all, 1 minute per reward); what's the interest rate?
	 * 4) Verify above are correct. 
	 * 
	 */
	public void setup(){
		customerA=(Customer) gate.dispatch(new RegisterCommand());
		assertThat(customerA.getAggregateId(),notNullValue());
		gate.dispatch(new DepositCommand(customerA.getAggregateId().getId(),new BigDecimal(30000)));
		assertThat(getAccountA().getAvailableBalance(),equalTo(new Money(new BigDecimal(30000))));
		customerB=(Customer)gate.dispatch(new RegisterCommand());
		assertThat(customerB.getAggregateId(),notNullValue());
		gate.dispatch(new DepositCommand(customerB.getAggregateId().getId(),new BigDecimal(50000)));
		assertThat(getAccountB().getAvailableBalance(),equalTo(new Money(new BigDecimal(50000))));
		target1=(Target) gate.dispatch(new LoadTargetCommand(new BigDecimal(10000),new BigDecimal(1.3), new BigDecimal(2)));
		assertThat(target1.getAggregateId(),notNullValue());
		target2=(Target)gate.dispatch(new LoadTargetCommand(new BigDecimal(50000),new BigDecimal(1.3), new BigDecimal(3)));
		assertThat(target2.getAggregateId(),notNullValue());
		target3=(Target)gate.dispatch(new LoadTargetCommand(new BigDecimal(30000),new BigDecimal(1.3), new BigDecimal(4)));
		assertThat(target3.getAggregateId(),notNullValue());
	}


	protected Account getAccountB() {
		return accountRepository.load(customerB.getAggregateId());
	}


	protected Account getAccountA() {
		return accountRepository.load(customerA.getAggregateId());
	}
	
	protected Target getTarget1(){
		return targetRepository.load(target1.getAggregateId());
	}
	protected Target getTarget2(){
		return targetRepository.load(target2.getAggregateId());
	}
	protected Target getTarget3(){
		return targetRepository.load(target3.getAggregateId());
	}
	@After
	/**
	 * 1) Delete Cust A, Cust B and all associated data
	 * 2) Delete Target 1,2,3 and all associated data.
	 * 
	 */	
	public void cleanup(){
//		customer.setAccount(new Account());
	}
	
}

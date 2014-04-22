package org.beeInvestment.model;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.beeInvestment.customer.domain.Customer;
@ContextConfiguration(locations = {"classpath:spring/business-config.xml","classpath:spring/abstractSessionTest.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"jpa","model"})
public abstract class BaseTestCase {
//	@Autowired
//	protected Customer customer;
//	@Autowired
//	protected User user;
//	@Autowired
//	protected Bee bee;
//	
	
	
	@Before
	/**
	 * 1) Create two customers Cust A and Cust B
	 * 2) Cust A with total balance 30000. Cust B with total balance 50000.
	 * 3) Create three targets.Tar 1, Tar 2 and Tar 3
	 * 3.1) Target 1(10000-amount, 2 periods to reward all, 2 minute per reward ).
	 * 3.2) Target 2(50000-amount, 3 Periods to reward all, 2 minutes per reward),
	 * 3.3) Target 3(30000-amount, 4 Periods to reward all, 1 minute per reward);
	 * 4) Verify above are correct. 
	 * 
	 */
	public void setup(){
//		customer.setAccount(new Account());
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

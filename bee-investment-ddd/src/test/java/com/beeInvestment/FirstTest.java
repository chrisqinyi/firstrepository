/*
 * Copyright 2011-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.beeInvestment;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.com.bottega.cqrs.command.Gate;
import pl.com.bottega.ecommerce.sales.acceptancetests.TestHelper;

import com.beeInvestment.application.ApproveTargetCommand;
import com.beeInvestment.application.DepositCommand;
import com.beeInvestment.application.InvestCommand;
import com.beeInvestment.application.LoadTargetCommand;
import com.beeInvestment.application.RegisterCommand;
import com.beeInvestment.application.RewardCommand;
import com.beeInvestment.application.TransferCommand;
import com.beeInvestment.application.UpdateLoginCredentialCommand;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/functionalTestsContext.xml")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class FirstTest {
	@Inject
	private Gate gate;
	@Inject
	private TestHelper testHelper;
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
@Test
	public void test1(){

	
	
//	Target target = targetFactory.create(new Money(new BigDecimal(10000)), new BigDecimal(12));
//	InvestCommand command = new InvestCommand();
//	command.setFund(new Money(new BigDecimal(5000)));
//	target.invest(command);
//	targetRepository.save(target);
	
	gate.dispatch(new RegisterCommand());
	assertThat(testHelper.getCustomerId(),notNullValue());
	gate.dispatch(new UpdateLoginCredentialCommand(testHelper.getCustomerId()));
	gate.dispatch(new LoadTargetCommand(new BigDecimal(10000),new BigDecimal(1.3), new BigDecimal(12)));
	assertThat(testHelper.getTargetId(),notNullValue());
	gate.dispatch(new DepositCommand(testHelper.getCustomerId(),new BigDecimal(10001)));
	expectedEx.expect(RuntimeException.class);
    expectedEx.expectMessage("investment fund cannot be less than 100.00");
    gate.dispatch(new InvestCommand(testHelper.getTargetId(),testHelper.getCustomerId(),new BigDecimal(10)));
	
}
@Test
public void test2(){

	
	
//	Target target = targetFactory.create(new Money(new BigDecimal(10000)), new BigDecimal(12));
//	InvestCommand command = new InvestCommand();
//	command.setFund(new Money(new BigDecimal(5000)));
//	target.invest(command);
//	targetRepository.save(target);
	
	gate.dispatch(new RegisterCommand());
	assertThat(testHelper.getCustomerId(),notNullValue());
	gate.dispatch(new UpdateLoginCredentialCommand(testHelper.getCustomerId()));
	gate.dispatch(new LoadTargetCommand(new BigDecimal(10000),new BigDecimal(1.3), new BigDecimal(12)));
	assertThat(testHelper.getTargetId(),notNullValue());
	gate.dispatch(new DepositCommand(testHelper.getCustomerId(),new BigDecimal(10001)));
	expectedEx.expect(RuntimeException.class);
    expectedEx.expectMessage("remaining investment fund cannot be less than 100.00");
    gate.dispatch(new InvestCommand(testHelper.getTargetId(),testHelper.getCustomerId(),new BigDecimal(9990)));
	
	
}
@Test
public void test3(){

	
	
//	Target target = targetFactory.create(new Money(new BigDecimal(10000)), new BigDecimal(12));
//	InvestCommand command = new InvestCommand();
//	command.setFund(new Money(new BigDecimal(5000)));
//	target.invest(command);
//	targetRepository.save(target);
	
	gate.dispatch(new RegisterCommand());
	assertThat(testHelper.getCustomerId(),notNullValue());
	gate.dispatch(new UpdateLoginCredentialCommand(testHelper.getCustomerId()));
	gate.dispatch(new LoadTargetCommand(new BigDecimal(10000),new BigDecimal(1.3), new BigDecimal(12)));
	assertThat(testHelper.getTargetId(),notNullValue());
	gate.dispatch(new DepositCommand(testHelper.getCustomerId(),new BigDecimal(20001)));
	expectedEx.expect(RuntimeException.class);
    expectedEx.expectMessage("no enough remaining investment fund");
    gate.dispatch(new InvestCommand(testHelper.getTargetId(),testHelper.getCustomerId(),new BigDecimal(10300)));
	
}
@Test
public void test4(){

	
	
//	Target target = targetFactory.create(new Money(new BigDecimal(10000)), new BigDecimal(12));
//	InvestCommand command = new InvestCommand();
//	command.setFund(new Money(new BigDecimal(5000)));
//	target.invest(command);
//	targetRepository.save(target);
	
	gate.dispatch(new RegisterCommand());
	assertThat(testHelper.getCustomerId(),notNullValue());
	gate.dispatch(new UpdateLoginCredentialCommand(testHelper.getCustomerId()));
	gate.dispatch(new LoadTargetCommand(new BigDecimal(10000),new BigDecimal(1.3), new BigDecimal(12)));
	gate.dispatch(new DepositCommand(testHelper.getCustomerId(),new BigDecimal(10101)));
	assertThat(testHelper.getTargetId(),notNullValue());
    gate.dispatch(new InvestCommand(testHelper.getTargetId(),testHelper.getCustomerId(),new BigDecimal(10000)));
	expectedEx.expect(RuntimeException.class);
    expectedEx.expectMessage("target full");
    gate.dispatch(new InvestCommand(testHelper.getTargetId(),testHelper.getCustomerId(),new BigDecimal(100)));	
	
}
@Test
public void test5(){

	
	
//	Target target = targetFactory.create(new Money(new BigDecimal(10000)), new BigDecimal(12));
//	InvestCommand command = new InvestCommand();
//	command.setFund(new Money(new BigDecimal(5000)));
//	target.invest(command);
//	targetRepository.save(target);
	
	gate.dispatch(new RegisterCommand());
	assertThat(testHelper.getCustomerId(),notNullValue());
	gate.dispatch(new UpdateLoginCredentialCommand(testHelper.getCustomerId()));
	gate.dispatch(new LoadTargetCommand(new BigDecimal(10000),new BigDecimal(1.3), new BigDecimal(12)));
	assertThat(testHelper.getTargetId(),notNullValue());
	gate.dispatch(new DepositCommand(testHelper.getCustomerId(),new BigDecimal(10001)));
	gate.dispatch(new InvestCommand(testHelper.getTargetId(),testHelper.getCustomerId(),new BigDecimal(5000)));
    gate.dispatch(new InvestCommand(testHelper.getTargetId(),testHelper.getCustomerId(),new BigDecimal(5000)));
	//expectException();
	gate.dispatch(new ApproveTargetCommand(testHelper.getTargetId()));
	gate.dispatch(new RewardCommand(testHelper.getTargetId(),new BigDecimal(1)));
	
}
@Test
public void test6(){

	
	
//	Target target = targetFactory.create(new Money(new BigDecimal(10000)), new BigDecimal(12));
//	InvestCommand command = new InvestCommand();
//	command.setFund(new Money(new BigDecimal(5000)));
//	target.invest(command);
//	targetRepository.save(target);
	
	gate.dispatch(new RegisterCommand());
	assertThat(testHelper.getCustomerId(),notNullValue());
	gate.dispatch(new UpdateLoginCredentialCommand(testHelper.getCustomerId()));
	gate.dispatch(new LoadTargetCommand(new BigDecimal(10000),new BigDecimal(1.3), new BigDecimal(12)));
	assertThat(testHelper.getTargetId(),notNullValue());
	gate.dispatch(new DepositCommand(testHelper.getCustomerId(),new BigDecimal(10001)));
	gate.dispatch(new InvestCommand(testHelper.getTargetId(),testHelper.getCustomerId(),new BigDecimal(5000)));
    gate.dispatch(new InvestCommand(testHelper.getTargetId(),testHelper.getCustomerId(),new BigDecimal(5000)));
	//expectException();
	gate.dispatch(new ApproveTargetCommand(testHelper.getTargetId()));
	gate.dispatch(new RewardCommand(testHelper.getTargetId(),new BigDecimal(1)));
	gate.dispatch(new RegisterCommand());
	assertThat(testHelper.getCustomerId(),notNullValue());
	gate.dispatch(new TransferCommand(testHelper.getTargetId(),new BigDecimal(1),testHelper.getCustomerId()));
	
}
}

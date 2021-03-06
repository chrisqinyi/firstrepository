package com.beeInvestment.application;

import javax.inject.Inject;

import pl.com.bottega.cqrs.annotations.CommandHandlerAnnotation;
import pl.com.bottega.cqrs.command.handler.CommandHandler;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import com.beeInvestment.account.domain.Account;
import com.beeInvestment.account.domain.AccountRepository;
import com.beeInvestment.account.domain.Withdraw;
@CommandHandlerAnnotation
public class WithdrawCommandHandler implements
CommandHandler<WithdrawCommand, Withdraw>{
	@Inject
	private AccountRepository accountRepository;

	@Override
	public Withdraw handle(WithdrawCommand command) {
		Account account = accountRepository.load(new AggregateId(command
				.getCustomerId()));
		Withdraw withdraw=account.withdraw(new Money(command.getAmount()));
		//accountRepository.save(account);
		//deposit.process();
		accountRepository.save(account);
		return withdraw;
	}
}

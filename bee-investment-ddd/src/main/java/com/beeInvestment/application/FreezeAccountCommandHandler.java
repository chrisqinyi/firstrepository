package com.beeInvestment.application;

import javax.inject.Inject;

import pl.com.bottega.cqrs.annotations.CommandHandlerAnnotation;
import pl.com.bottega.cqrs.command.handler.CommandHandler;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;

import com.beeInvestment.account.domain.Account;
import com.beeInvestment.account.domain.AccountRepository;

@CommandHandlerAnnotation
public class FreezeAccountCommandHandler implements
		CommandHandler<FreezeAccountCommand, Void> {
	@Inject
	private AccountRepository accountRepository;

	@Override
	public Void handle(FreezeAccountCommand command) {
		Account account=accountRepository.load(new AggregateId(command.getAccountId()));
		account.freeze();
		accountRepository.save(account);
		return null;
	}

}

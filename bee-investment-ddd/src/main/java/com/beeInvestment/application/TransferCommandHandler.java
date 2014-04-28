package com.beeInvestment.application;

import javax.inject.Inject;

import pl.com.bottega.cqrs.annotations.CommandHandlerAnnotation;
import pl.com.bottega.cqrs.command.handler.CommandHandler;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;

import com.beeInvestment.account.domain.Account;
import com.beeInvestment.account.domain.AccountRepository;
import com.beeInvestment.customer.domain.CustomerRepository;
import com.beeInvestment.investment.domain.Target;
import com.beeInvestment.investment.domain.TargetRepository;
@CommandHandlerAnnotation
public class TransferCommandHandler implements CommandHandler<TransferCommand,Void> {
	@Inject
	private TargetRepository targetRepository;
	@Inject
	private CustomerRepository customerRepository;
	@Inject
	private AccountRepository accountRepository;
	@Override
	public Void handle(TransferCommand command) {
		Target target=targetRepository.load(new AggregateId(command.getTargetId()));
		AggregateId customerId = new AggregateId(command.getCustomerId());
		//Customer customer = customerRepository.load(customerId);
		Account account=accountRepository.load(customerId);
		target.transferInvestment(command.getInvestmentIndex().intValue(), account);
		targetRepository.save(target);
		return null;
	}

}

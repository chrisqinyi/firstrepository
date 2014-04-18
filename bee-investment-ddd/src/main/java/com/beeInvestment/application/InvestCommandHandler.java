package com.beeInvestment.application;

import javax.inject.Inject;

import pl.com.bottega.cqrs.annotations.CommandHandlerAnnotation;
import pl.com.bottega.cqrs.command.handler.CommandHandler;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;

import com.beeInvestment.account.domain.AccountRepository;
import com.beeInvestment.customer.domain.CustomerRepository;
import com.beeInvestment.investment.domain.Target;
import com.beeInvestment.investment.domain.TargetRepository;
@CommandHandlerAnnotation
public class InvestCommandHandler implements CommandHandler<InvestCommand, Void> {
	@Inject
	private TargetRepository targetRepository;
	@Inject
	private CustomerRepository customerRepository;
	@Inject
	private AccountRepository accountRepository;
	@Override
	public Void handle(InvestCommand command) {
		
		Target target=targetRepository.load(new AggregateId(command.getTargetId()));
		target.invest(customerRepository.load(new AggregateId(command.getCustomerId())).generateSnapshot(),command.getFund());
		targetRepository.save(target);
		return null;
	}

}

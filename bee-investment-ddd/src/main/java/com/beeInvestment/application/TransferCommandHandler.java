package com.beeInvestment.application;

import java.math.BigDecimal;

import javax.inject.Inject;

import pl.com.bottega.cqrs.annotations.CommandHandlerAnnotation;
import pl.com.bottega.cqrs.command.handler.CommandHandler;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;

import com.beeInvestment.customer.domain.Customer;
import com.beeInvestment.customer.domain.CustomerRepository;
import com.beeInvestment.investment.domain.Target;
import com.beeInvestment.investment.domain.TargetRepository;
@CommandHandlerAnnotation
public class TransferCommandHandler implements CommandHandler<TransferCommand,Void> {
	@Inject
	private TargetRepository targetRepository;
	@Inject
	private CustomerRepository customerRepository;
	@Override
	public Void handle(TransferCommand command) {
		Target target=targetRepository.load(new AggregateId(command.getTargetId()));
		Customer customer = customerRepository.load(new AggregateId(command.getCustomerId()));
		target.transfer(customer.generateSnapshot(),new BigDecimal (0));
		targetRepository.save(target);
		return null;
	}

}

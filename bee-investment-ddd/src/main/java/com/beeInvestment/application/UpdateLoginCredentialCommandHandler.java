package com.beeInvestment.application;

import javax.inject.Inject;

import pl.com.bottega.cqrs.annotations.CommandHandlerAnnotation;
import pl.com.bottega.cqrs.command.handler.CommandHandler;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;

import com.beeInvestment.customer.domain.Customer;
import com.beeInvestment.customer.domain.CustomerRepository;

@CommandHandlerAnnotation
public class UpdateLoginCredentialCommandHandler implements
		CommandHandler<UpdateLoginCredentialCommand, Void> {
	@Inject
	private CustomerRepository customerRepository;

	@Override
	public Void handle(UpdateLoginCredentialCommand command) {
		Customer customer=customerRepository.load(new AggregateId(command.getCustomerId()));
		customer.updateLoginCredential();
		customerRepository.save(customer);
		return null;
	}

}

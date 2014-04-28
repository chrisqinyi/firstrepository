package com.beeInvestment.application;

import javax.inject.Inject;

import pl.com.bottega.cqrs.annotations.CommandHandlerAnnotation;
import pl.com.bottega.cqrs.command.handler.CommandHandler;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;

import com.beeInvestment.account.domain.Account;
import com.beeInvestment.account.domain.AccountFactory;
import com.beeInvestment.account.domain.AccountRepository;
import com.beeInvestment.customer.domain.Customer;
import com.beeInvestment.customer.domain.CustomerFactory;
import com.beeInvestment.customer.domain.CustomerRepository;
import com.beeInvestment.transaction.domain.TransactionRepository;
@CommandHandlerAnnotation
public class RegisterCommandHandler implements CommandHandler<RegisterCommand,Void>{
	@Inject
	private CustomerRepository customerRepository;
	@Inject
	private CustomerFactory customerFactory;
	@Inject
	private AccountRepository accountRepository;
	@Inject
	private AccountFactory accountFactory;
	@Inject
	private TransactionRepository transactionRepository;
	@Override
	public Void handle(RegisterCommand command) {
		Customer customer=customerFactory.create();
		customer.register();
		Account account=accountFactory.create(customer.generateSnapshot());
		customerRepository.save(customer);
		//customerRepository.save(customer);
		accountRepository.save(account);
		//transactionRepository.load(new AggregateId(""));
		return null;
	}
}

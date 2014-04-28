package com.beeInvestment.application;

import javax.inject.Inject;

import pl.com.bottega.cqrs.annotations.CommandHandlerAnnotation;
import pl.com.bottega.cqrs.command.handler.CommandHandler;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import com.beeInvestment.account.domain.Account;
import com.beeInvestment.account.domain.AccountRepository;
import com.beeInvestment.customer.domain.CustomerRepository;
import com.beeInvestment.investment.domain.Investment;
import com.beeInvestment.investment.domain.Target;
import com.beeInvestment.investment.domain.TargetRepository;
import com.beeInvestment.payment.domain.PaymentRepository;
import com.beeInvestment.transaction.domain.Transaction;
import com.beeInvestment.transaction.domain.TransactionRepository;

@CommandHandlerAnnotation
public class InvestCommandHandler implements
		CommandHandler<InvestCommand, Investment> {
	@Inject
	private TransactionRepository transactionRepository;
	@Inject
	private TargetRepository targetRepository;
	@Inject
	private CustomerRepository customerRepository;
	@Inject
	private AccountRepository accountRepository;
//	@Inject
	private PaymentRepository paymentRepository;
	@Override
	public Investment handle(InvestCommand command) {
		Target target = targetRepository.load(new AggregateId(command
				.getTargetId()));
		AggregateId customerId = new AggregateId(command.getCustomerId());
		//Customer customer = customerRepository.load(customerId);
		Account account=accountRepository.load(customerId);
		
		
//		Account account=accountRepository.load(customerId);
		if (! account.canAfford(new Money(command.getFund())))
			throw new RuntimeException("customer has insufficent money");
//		Payment payment = account.charge(new Money(command.getFund()));
//		paymentRepository.save(payment);
		Transaction t=target.invest(account, new Money(command.getFund()));
		//accountRepository.save(account);
		//targetRepository.save(target);
		transactionRepository.save(t);
		return null;
	}

}

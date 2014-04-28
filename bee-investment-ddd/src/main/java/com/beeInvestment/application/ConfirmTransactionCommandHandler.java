package com.beeInvestment.application;

import javax.inject.Inject;

import pl.com.bottega.cqrs.annotations.CommandHandlerAnnotation;
import pl.com.bottega.cqrs.command.handler.CommandHandler;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;

import com.beeInvestment.transaction.domain.Transaction;
import com.beeInvestment.transaction.domain.TransactionRepository;

@CommandHandlerAnnotation
public class ConfirmTransactionCommandHandler implements
		CommandHandler<ConfirmTransactionCommand, Transaction> {
	@Inject
	private TransactionRepository transactionRepository;

	@Override
	public Transaction handle(ConfirmTransactionCommand command) {
		Transaction transaction = transactionRepository.load(new AggregateId(
				command.getTransactionId()));
		transaction.confirm();
		transactionRepository.save(transaction);
		return transaction;
	}

}

package com.beeInvestment.application;

import javax.inject.Inject;

import pl.com.bottega.cqrs.annotations.CommandHandlerAnnotation;
import pl.com.bottega.cqrs.command.handler.CommandHandler;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import com.beeInvestment.investment.domain.Target;
import com.beeInvestment.investment.domain.TargetFactory;
import com.beeInvestment.investment.domain.TargetRepository;

@CommandHandlerAnnotation
public class LoadTargetCommandHandler implements
		CommandHandler<LoadTargetCommand, Void> {
	@Inject
	private TargetFactory targetFactory;
	@Inject
	private TargetRepository targetRepository;

	@Override
	public Void handle(LoadTargetCommand command) {
		Target target = targetFactory.create(new Money(command.getTotalFund()),
				command.getInterestRate(), command.getPeriods());
		// InvestCommand command = new InvestCommand();
		// command.setFund(new Money(new BigDecimal(5000)));
		// target.invest(command);
		target.load();
		targetRepository.save(target);
		return null;
	}

}

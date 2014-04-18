package com.beeInvestment.application;

import javax.inject.Inject;

import pl.com.bottega.cqrs.annotations.CommandHandlerAnnotation;
import pl.com.bottega.cqrs.command.handler.CommandHandler;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;

import com.beeInvestment.investment.domain.Target;
import com.beeInvestment.investment.domain.TargetRepository;
@CommandHandlerAnnotation
public class RewardCommandHandler implements CommandHandler<RewardCommand, Void> {
	@Inject
	private TargetRepository targetRepository;
	@Override
	public Void handle(RewardCommand command) {
		Target target=targetRepository.load(new AggregateId(command.getTargetId()));
		target.reward(command.getPeriodIndex());
		targetRepository.save(target);
		return null;
	}

}

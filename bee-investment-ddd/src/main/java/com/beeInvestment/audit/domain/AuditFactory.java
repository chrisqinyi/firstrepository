package com.beeInvestment.audit.domain;

import java.io.Serializable;

import javax.inject.Inject;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import pl.com.bottega.ddd.annotations.domain.DomainFactory;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;

@DomainFactory
public class AuditFactory {
	@Inject
	private AutowireCapableBeanFactory spring;

	public Audit create(Serializable event) {
		AggregateId aggregateId = AggregateId.generate();
		Audit audit = new Audit(aggregateId, event);
		spring.autowireBean(audit);
		return audit;
	}
}

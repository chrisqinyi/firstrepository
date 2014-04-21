package com.beeInvestment.audit.domain;

import java.io.Serializable;

import javax.persistence.Entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pl.com.bottega.ddd.support.domain.BaseAggregateRoot;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;

@Entity
public class Audit extends BaseAggregateRoot {
	private Audit() {
	}

	Audit(AggregateId aggregateId, Serializable event) {
		this.aggregateId=aggregateId;
	}
}

package com.beeInvestment.payment.domain;

import pl.com.bottega.ddd.annotations.domain.DomainRepository;

@DomainRepository
public interface PaymentRepository {

	void save(Payment payment);

}

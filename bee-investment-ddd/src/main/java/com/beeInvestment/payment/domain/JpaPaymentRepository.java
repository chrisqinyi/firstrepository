package com.beeInvestment.payment.domain;

import pl.com.bottega.ddd.support.infrastructure.repository.jpa.GenericJpaRepository;

public class JpaPaymentRepository extends GenericJpaRepository<Payment> implements PaymentRepository{

}

package com.beeInvestment.account.domain;

import pl.com.bottega.ddd.annotations.domain.DomainRepository;

@DomainRepository
public interface AccountRepository{

	void save(Account account);

}

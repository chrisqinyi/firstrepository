package com.beeInvestment.application;

import pl.com.bottega.cqrs.annotations.Command;

@Command()
public class UpdateLoginCredentialCommand {
private String customerId;

public String getCustomerId() {
	return customerId;
}

public UpdateLoginCredentialCommand(String customerId){
	this.customerId=customerId;
}
}

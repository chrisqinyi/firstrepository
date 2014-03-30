package org.beeInvestment.model;

import org.springframework.beans.factory.annotation.Autowired;

public class Bee {
	@Autowired
	private Customer customer;
	public Customer authenticate(Credential credential) {
		if(credential instanceof TestValidCredential){
			return customer;
		}else{
			return null;
		}
	}

	public Customer createCustomer(RegisterForm registerForm) {
		// TODO Auto-generated method stub
		return null;
	}

}

package com.beeInvestment.application;

import java.io.Serializable;

public class RegisterEvent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 46760070756635386L;
private String customerId;
public RegisterEvent(String customerId){
	this.customerId=customerId;
}
String getCustomerId() {
	return customerId;
}

}

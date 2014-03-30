package org.beeInvestment.model;

import java.util.Map;

import org.junit.Test;

public class UserMaintenanceTests extends BaseTestCase {
	@Test
	public void userRegistration() {
		Customer newCustomer=bee.createCustomer(new RegisterForm());
		Credential credential=new Credential();
		Customer customer=bee.authenticate(credential);
	}

	@Test
	public void userLogin() {
		String username = null,password = null;
		Credential credential=new Credential();
		//customer.login(username,password);
		Customer customer=bee.authenticate(credential);
	}

	@Test
	public void passwordChange() {
		//String oldPassword = null,newPassword = null;
		Credential credential=new Credential();
		CredentialHistory credentialHistory=customer.updateCredential(credential);
		Credential credential1=new Credential();
		bee.authenticate(credential1);
		
		//Credential credential=new Credential();
	}

	@Test
	public void identityAuthentication() {
		Map criteria = null;
		user.getCustomerList(criteria);
		user.viewCustomer(new Customer());
		user.authenticate(customer);
	}

	@Test
	public void setupSecurityQuestions() {
		Credential credential=new Credential();
		CredentialHistory credentialHistory=customer.updateCredential(credential);
		Credential credential1=new Credential();
		bee.authenticate(credential1);
	}

	@Test
	public void mobileBinding() {
		Profile profile=new Profile();
		customer.updateProfile(profile);
	}

	@Test
	public void porfileEdit() {
		Profile profile=new Profile();
		customer.updateProfile(profile);
	}

}

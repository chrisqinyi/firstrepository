package org.beeInvestment.model;

import java.util.List;
import java.util.Map;

public class Customer {
	private boolean authenticated;
	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	private Account account;

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public CreditAssignment offerInvestment(Investment originalInvestment) {
		// TODO Auto-generated method stub
		return null;
	}

	public org.beeInvestment.model.Investment acceptInvestment(
			CreditAssignment assignment) {
		// TODO Auto-generated method stub
		return null;
	}

	public Deposit deposit(Deposit deposit) {
		this.getAccount().setBalance(this.getAccount().getBalance().add(deposit.getAmount()));
		return deposit;
	}

	public Withdraw applyForWithdraw(Withdraw withdraw) {
		// TODO Auto-generated method stub
		return null;
	}

	public Investment invest(Target target) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Target> getTargetList(Map criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateProfile(Profile profile) {
		// TODO Auto-generated method stub
		
	}

	public void changePassword(String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		
	}

	public CredentialHistory updateCredential(Credential credential) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<CreditAssignment> getCreditAssignmentList() {
		// TODO Auto-generated method stub
		return null;
	}

}

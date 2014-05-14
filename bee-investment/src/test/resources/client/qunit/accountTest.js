test("view Account Detail", 2, function() {	
	var accountIndex = "001";
	var accnoutObj = accountController.viewAccountDetail(accountIndex);
	equal(accnoutObj.accountNumber, "ahsfh109u", "We expect value to be ahsfh109u");
	
	var returnObj = accountController.deleteAccount(accountIndex);
	equal(returnObj.status, 1, "deleteAccount return status 1" );
	
});
var accountController = {
	addAccount : function(accountObj) {

	},
	deleteAccount : function(accountIndex) {
		if (!accountIndex || accountIndex == null) {
			return "";
		}
		return {
			status : 1,
			message : "Success"
		};

	},
	viewAccountDetail : function(accountIndex) {
		if (!accountIndex || accountIndex == null) {
			return "";
		}
		return {
			accountIndex : 001,
			accountName : "Ferris",
			accountNumber : "ahsfh109u"
		};
	},
	viewAccountLise : function() {
	}
};
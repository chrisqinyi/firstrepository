App.HeaderController = Ember.Controller.extend({
    actions : {
        showLoginDialog : function() {
            console.log("showLoginDialog");
            $("#loginDialog").dialog("open");
        }
    }
});

App.IndexController = Ember.Controller.extend({
    isLoading : false,
    actions : {
        submit : function() {
            var self = this;
            console.log("submit");
            var result = $("#loginForm").valid();
            console.log("result = " + result);
            if(result){
                location.href = "overview.html";
            }
        }
    }
});

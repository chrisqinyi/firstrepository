App.indexView = Ember.View.extend({
    didInsertElement : function() {
        $("#loginForm").validate({
            rules : {
                username : {
                    required : true,
                    minlength : 2
                },
                password : "required"
            },
            messages : {
                username : {
                    required : "Please enter a username",
                    minlength : "Your username must consist of at least 2 characters"
                },
                password : "Please provide a password"
            }
        });
    }
});

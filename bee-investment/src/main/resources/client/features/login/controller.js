App.LoginController = Ember.Controller.extend({
    isLoading : false,
    actions : {
        submit : function() {
            var self = this;
            Ember.run.later(function() {
                self.set('isLoading', false);
            }, 3000);
        }
    }
});
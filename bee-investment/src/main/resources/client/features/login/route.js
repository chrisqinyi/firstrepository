App.LoginRoute = Ember.Route.extend({
    model : function() {
        return App.storage.find('notice');
    }
});
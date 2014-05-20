App.Router.map(function() {
    // this.resource('todos', {
    // path : '/'
    // }, function() {
    // this.route('active');
    // this.route('completed');
    // });
    this.route("login", {
        path : "/"
    });
});

App.LoginRoute = App.BaseRoute.extend({
    model : function() {
        return App.storage.find('notice');
    }
});
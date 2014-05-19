App.Router.map(function() {
    // this.resource('todos', {
    // path : '/'
    // }, function() {
    // this.route('active');
    // this.route('completed');
    // });
    this.route("overview", {
        path : "/"
    });
});

App.OverviewRoute = Ember.Route.extend({
    
});
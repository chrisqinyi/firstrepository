App = Em.Application.create({
    LOG_TRANSITIONS : true,
    LOG_TRANSITIONS_INTERNAL : true,
    LOG_VIEW_LOOKUPS : true,
    LOG_ACTIVE_GENERATION : true
});

App.ResetScroll = Em.Mixin.create({
    activate : function() {
        this._super();
        window.scrollTo(0, 0);
    }
});

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

App.ApplicationRoute = Em.Route.extend(App.ResetScroll, {
    activate : function() {
        this._super.apply(this, arguments);
    }
});

Em.RSVP.configure('onerror', function(error) {
    Em.Logger.assert(false, error);
});
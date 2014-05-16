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


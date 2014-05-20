App.ApplicationRoute = App.BaseRoute.extend(App.ResetScroll, {
    activate : function() {
        this._super.apply(this, arguments);
    }
});

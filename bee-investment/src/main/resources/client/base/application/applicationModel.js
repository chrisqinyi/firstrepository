App.BaseModel = Ember.Object.extend({
    'type' : null,
    'data' : {},
    'save' : function() {
        var self = this;
        return App.WS.ajax('save-' + self.type, self.data, function(json) {
            self.data = $.extend({}, self.data, json);
            return self;
        });
    },
    'refresh' : function() {
        var self = this;
        return App.WS.ajax('refresh-' + self.type, self.data, function(json) {
            self.data = $.extend({}, self.data, json);
            return self;
        });
    },
    'update' : function() {
        var self = this;
        return App.WS.ajax('update-' + self.type, self.data, function(json) {
            self.data = $.extend({}, self.data, json);
            return self;
        });
    },
    'delete' : function() {
        var self = this;
        return App.WS.ajax('delete-' + self.type, self.data, function(json) {
            self.data = json;
            return self;
        });
    }
});

App.BaseFeatureURLMapping = Em.Object.create();
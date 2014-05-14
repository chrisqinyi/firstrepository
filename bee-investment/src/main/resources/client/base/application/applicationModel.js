App.BaseFeatureURLMapping = Em.Object.create();

App.storage = Ember.Object.create({
    find : function(type) {
        var self = this;
        var storage = self._obtain('sessionStorage');
        if (storage) {
            self = storage;
        }
        var obj = self[type];
        if (!obj) {
            self[type] = App.BaseModel.create();
            self[type].type = type;
            obj = self[type];
        } else {
            console.log('[find] get info from local');
            return obj.data;
        }
        return App.WS.ajax(type, null, function(json) {
            console.log('[find] get info from server');
            obj.data = json;
            self._save('sessionStorage', self);
            return obj.data;
        });
    },
    findMany : function(type, varName, query) {
        var self = this;
        var storage = self._obtain('sessionStorage');
        if (storage) {
            self = storage;
        }
        var many = self[varName];
        var obj;
        if (!many) {
            obj = App.BaseModel.create();
            obj.type = type;
        } else {
            console.log('[find] get info from local');
            return many;
        }

        return App.WS.ajax('query-' + type, query, function(json) {
            console.log('[find] get info from server');
            var list = [];
            if (json && json.length > 0) {
                for (var i = 0; i < json.length; i++) {
                    var item = $.extend({}, obj, json[i]);
                    list.push(item);
                }
            }
            self[varName] = list;
            self._save('sessionStorage', self);
            return self[varName];
        });
    },
    _save : function(key, value) {
        if (window.sessionStorage) {
            window.sessionStorage.setItem(key, JSON.stringify(value));
        }
    },
    _obtain : function(key) {
        if (window.sessionStorage) {
            return JSON.parse(window.sessionStorage.getItem(key));
        }
        return null;
    }
});

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
App.storage = Ember.Object.create({
	namingSpace : "bee-investment_",
	find : function(type) {
		var self = this;
		var key = self.namingSpace + type;
		var storage = self._obtain(key);
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
			self._save(key, self);
			return obj.data;
		});
	},
	findMany : function(type, varName, query) {
		var self = this;
		var key = self.namingSpace + type;
		var storage = self._obtain(key);
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
			self._save(key, self);
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
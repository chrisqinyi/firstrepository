App.WS = {
    host : 'http://my.first.call',
    namespace : 'bee',
    suffix : '.do',
    ajaxTimeout : 30 * 1000,

    ajax : function(type, data, callback) {
        if (typeof callback !== 'function') {
            alert("callback is not function!");
            return;
        }

        var url = this.buildURL(type);

        // TODO: data object, setHeader and setBody
        var dataObj = {
            header : {
                status : 0,
                message : null
            },
            body : data
        };

        var error = function(xhr, errorMessage, errorThrown) {
            alert(errorMessage);
        };

        var promise = new Ember.RSVP.Promise(function(resolve, reject) {
            var hash = {
                type : 'POST',
                url : url,
                data : data,
                success : resolve,
                dataType : 'json',
                cache : false,
                error : error,
                timeout : this.ajaxTimeout,
            };
            Em.$.ajax(hash);
        });

        return promise.then(callback);
    },

    getFeatureURL : function(type) {
        if (App.BaseFeatureURLMapping.hasOwnProperty(type)) {
            return App.BaseFeatureURLMapping[type];
        } else {
            console.error("There is no URL mapping to " + type);
            return;
        }
    },

    buildURL : function(type) {
        var host = this.host;
        var namespace = this.namespace;
        var url = [];

        if (host) {
            url.push(host);
        }
        if (namespace) {
            url.push(namespace);
        }

        if (type) {
            url.push(this.getFeatureURL(type));
        }

        url = url.join('/');

        return url + this.suffix;
    }

};

App.SpinButtonComponent = Ember.Component.extend({
    classNames : [ 'button' ],
    isLoading : false,
    actions : {
        showLoading : function() {
            if (!this.get('isLoading')) {
                this.set('isLoading', true);
                this.sendAction('action');
            }
        }
    }
});
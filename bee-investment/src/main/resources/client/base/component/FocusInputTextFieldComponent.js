App.FocusInputTextFieldComponent = Ember.TextField.extend({
    becomeFocused : function() {
        this.$().focus();
    }.on('didInsertElement')
});
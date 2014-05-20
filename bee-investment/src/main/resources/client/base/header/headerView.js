App.HeaderView = Ember.View.extend({
    didInsertElement : function() {
        $("#loginDialog").dialog({
            autoOpen : false,
            width : 400,
            buttons : [ {
                text : "Ok",
                click : function() {
                    $(this).dialog("close");
                }
            }, {
                text : "Cancel",
                click : function() {
                    $(this).dialog("close");
                }
            } ]
        });
    }
});

App.BaseRoute = Em.Route.extend({
    renderTemplate : function() {
        this.renderComponents();
        this._super();
    },
    
    renderComponents : function() {

        // post login
        headerTemplate = "header";
        footerTemplate = "footer";

        // render login common header
        this.render(headerTemplate, {
            into : 'application',
            outlet : 'header'
        });
        // render login common navigation
        this.render(footerTemplate, {
            into : 'application',
            outlet : 'footer'
        });
    }
});

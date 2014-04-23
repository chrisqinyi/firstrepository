Todos.TodoController = Ember.ObjectController.extend({
    isEditing : false,
    actions : {
        isCompleted : function(key, value) {
            var model = this.get('model');

            if (value === undefined) {
                // property being used as a getter
                return model.get('isCompleted');
            } else {
                // property being used as a setter
                model.set('isCompleted', value);
                model.save();
                return value;
            }
        }.property('model.isCompleted'),
        editTodo : function() {
            this.set('isEditing', true);
        },
        acceptChanges : function() {
            this.set('isEditing', false);
            if (Ember.isEmpty(this.get('model.title'))) {
                this.send('removeTodo');
            } else {
                this.get('model').save();
            }
        },
        removeTodo : function() {
            var todo = this.get('model');
            todo.deleteRecord();
            todo.save();
        }
    }

});

Todos.EditTodoView = Ember.TextField.extend({
    didInsertElement : function() {
        this.$().focus();
    }
});

Ember.Handlebars.helper('edit-todo', Todos.EditTodoView);

Todos.TodosActiveRoute = Ember.Route.extend({
    model : function() {
        return this.store.filter('todo', function(todo) {
            return !todo.get('isCompleted');
        });
    },
    renderTemplate : function(controller) {
        this.render('todos/index', {
            controller : controller
        });
    }
});

Todos.TodosCompletedRoute = Ember.Route.extend({
    model : function() {
        return this.store.filter('todo', function(todo) {
            return todo.get('isCompleted');
        });
    },
    renderTemplate : function(controller) {
        this.render('todos/index', {
            controller : controller
        });
    }
});

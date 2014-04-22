Todos.Router.map(function() {
    this.resource('todos', {
        path : '/'
    }, function() {
        this.route('active');
        this.route('completed');
    });
});

Todos.TodosIndexRoute = Ember.Route.extend({
    model : function() {
        return this.modelFor('todos');
    }
});

Todos.Todo = DS.Model.extend({
    title : DS.attr('string'),
    isCompleted : DS.attr('boolean')
});

Todos.Todo.FIXTURES = [ {
    id : 1,
    title : 'Learn Ember.js',
    isCompleted : true
}, {
    id : 2,
    title : '...',
    isCompleted : false
}, {
    id : 3,
    title : 'Profit!',
    isCompleted : false
} ];

Todos.TodosRoute = Ember.Route.extend({
    model : function() {
        return this.store.find('todo');
    }
});

Todos.TodosController = Ember.ArrayController.extend({
    actions : {
        clearCompleted : function() {
            var completed = this.filterBy('isCompleted', true);
            completed.invoke('deleteRecord');

            completed.invoke('save');
        },
        createTodo : function() {
            // Get the todo title set by the "New Todo" text field
            var title = this.get('newTitle');
            if (!title.trim()) {
                return;
            }

            // Create the new Todo model
            var todo = this.get('store').createRecord('todo', {
                title : title,
                isCompleted : false
            });

            // Clear the "New Todo" text field
            this.set('newTitle', '');

            // Save the new model
            todo.save();
        }
    },

    remaining : function() {
        return this.filterBy('isCompleted', false).get('length');
    }.property('@each.isCompleted'),

    inflection : function() {
        var remaining = this.get('remaining');
        return remaining === 1 ? 'item' : 'items';
    }.property('remaining'),

    hasCompleted : function() {
        return this.get('completed') > 0;
    }.property('completed'),

    completed : function() {
        return this.filterBy('isCompleted', true).get('length');
    }.property('@each.isCompleted'),

    allAreDone : function(key, value) {
        if (value === undefined) {
            return !!this.get('length') && this.everyProperty('isCompleted', true);
        } else {
            this.setEach('isCompleted', value);
            this.invoke('save');
            return value;
        }
    }.property('@each.isCompleted')
});

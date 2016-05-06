define(['js/ParthenonApp'], function(app) {
    app.filter('range', function() {
        /** Simple range filter in the range [0, max). */
        return function(input, max) {
            for (var i = 0; i < parseInt(max); i++) {
                input.push(i);
            }
            return input;
        }
    });
});
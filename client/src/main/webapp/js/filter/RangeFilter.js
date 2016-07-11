define(['js/ParthenonApp'], function(app) {
    app.filter('range', function() {
        /** Simple range filter in the range [min, max). */
        return function(input, min, max) {
            for (var i = parseInt(min); i < parseInt(max); i++) {
                input.push(i);
            }
            return input;
        }
    });
});
Array.prototype.clear =
    Array.prototype.clear ||
    function () {
        this.splice(0, this.length);
    };

Array.prototype.groupBy = function (keyFunction) {
    var groups = {};
    this.forEach(function (el) {
        var key = keyFunction(el);
        if (key in groups == false) {
            groups[key] = [];
        }
        groups[key].push(el);
    });
    return Object.keys(groups).map(function (key) {
        return {
            key: key,
            values: groups[key]
        };
    });
};
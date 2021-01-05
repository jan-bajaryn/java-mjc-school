"use strict";
exports.__esModule = true;
var Frontend = (function () {
    function Frontend(currentProject) {
        this.name = 'frontend';
        this.currentProject = currentProject;
    }
    Frontend.prototype.getCurrentProject = function () {
        return this.currentProject;
    };
    Frontend.prototype.getName = function () {
        return this.name;
    };
    return Frontend;
}());
exports["default"] = Frontend;
//# sourceMappingURL=Frontend.js.map
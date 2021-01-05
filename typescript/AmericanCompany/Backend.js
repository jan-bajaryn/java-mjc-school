"use strict";
exports.__esModule = true;
var Backend = (function () {
    function Backend(currentProject) {
        this.name = 'backend';
        this.currentProject = currentProject;
    }
    Backend.prototype.getCurrentProject = function () {
        return this.currentProject;
    };
    Backend.prototype.getName = function () {
        return this.name;
    };
    return Backend;
}());
exports["default"] = Backend;
//# sourceMappingURL=Backend.js.map
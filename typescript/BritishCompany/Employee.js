"use strict";
exports.__esModule = true;
var Employee = (function () {
    function Employee(currentProject, name) {
        this._currentProject = currentProject;
        this._name = name;
    }
    Employee.prototype.getCurrentProject = function () {
        return this._currentProject;
    };
    Employee.prototype.getName = function () {
        return this._name;
    };
    return Employee;
}());
exports["default"] = Employee;
//# sourceMappingURL=Employee.js.map
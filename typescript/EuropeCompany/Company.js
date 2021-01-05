"use strict";
exports.__esModule = true;
var Company = (function () {
    function Company(employees) {
        if (employees === void 0) { employees = []; }
        this.employees = employees;
    }
    Company.prototype.add = function (employee) {
        this.employees.push(employee);
    };
    Company.prototype.getProjectList = function () {
        return this.employees
            .map(function (e) { return e.getCurrentProject(); });
    };
    Company.prototype.getNameList = function () {
        return this.employees
            .map(function (e) { return e.getName(); });
    };
    return Company;
}());
exports["default"] = Company;
//# sourceMappingURL=Company.js.map
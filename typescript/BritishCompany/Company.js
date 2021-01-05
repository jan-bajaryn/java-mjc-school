"use strict";
exports.__esModule = true;
var Company = (function () {
    function Company(location, employees) {
        if (employees === void 0) { employees = []; }
        this.location = location;
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
    Company.prototype.getLocationPeople = function () {
        var result = [];
        for (var i = 0; i < this.location.getCount(); i++) {
            result.push(this.location.getPerson(i));
        }
        return result;
    };
    return Company;
}());
exports["default"] = Company;
//# sourceMappingURL=Company.js.map
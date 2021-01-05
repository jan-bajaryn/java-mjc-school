"use strict";
exports.__esModule = true;
var Company_1 = require("./Company");
var Employee_1 = require("./Employee");
var Frontend_1 = require("./Frontend");
var Backend_1 = require("./Backend");
var company = new Company_1["default"]();
company.add(new Frontend_1["default"]('medic'));
company.add(new Backend_1["default"]('valentin'));
company.add(new Backend_1["default"]('pikassoq'));
company.add(new Employee_1["default"]('pikasso', 'ahaha'));
console.log("company.getNameList() = " + company.getNameList());
console.log('company.getProjectList() = ' + company.getProjectList());
//# sourceMappingURL=SomeRunner.js.map
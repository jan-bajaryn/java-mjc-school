"use strict";
exports.__esModule = true;
var CompanyArrayLocation = (function () {
    function CompanyArrayLocation(personList) {
        if (personList === void 0) { personList = []; }
        this.personList = [];
        this.personList = personList;
    }
    CompanyArrayLocation.prototype.addPerson = function (person) {
        this.personList.push(person);
    };
    CompanyArrayLocation.prototype.getCount = function () {
        return this.personList.length;
    };
    CompanyArrayLocation.prototype.getPerson = function (index) {
        return this.personList[index];
    };
    return CompanyArrayLocation;
}());
exports["default"] = CompanyArrayLocation;
//# sourceMappingURL=CompanyArrayLocation.js.map
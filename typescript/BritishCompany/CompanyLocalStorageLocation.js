"use strict";
exports.__esModule = true;
var CompanyLocalStorageLocation = (function () {
    function CompanyLocalStorageLocation(key) {
        this.key = key;
    }
    CompanyLocalStorageLocation.prototype.addPerson = function (person) {
        var strings = this.findAll();
        strings.push(person);
        localStorage.setItem(this.key, JSON.stringify(strings));
    };
    CompanyLocalStorageLocation.prototype.getCount = function () {
        return this.findAll().length;
    };
    CompanyLocalStorageLocation.prototype.getPerson = function (index) {
        return this.findAll()[index];
    };
    CompanyLocalStorageLocation.prototype.findAll = function () {
        var item = localStorage.getItem(this.key);
        console.log('item = ' + item);
        console.log('item json parse = ' + JSON.parse(item));
        if (item == null) {
            return [];
        }
        return JSON.parse(item);
    };
    return CompanyLocalStorageLocation;
}());
exports["default"] = CompanyLocalStorageLocation;
//# sourceMappingURL=CompanyLocalStorageLocation.js.map
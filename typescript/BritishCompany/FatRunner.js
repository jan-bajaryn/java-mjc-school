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
        if (item == null) {
            return [];
        }
        return JSON.parse(item);
    };
    return CompanyLocalStorageLocation;
}());
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
var location1 = new CompanyArrayLocation(['a', 'b', 'c']);
var location2 = new CompanyArrayLocation(['c', 'd', 'e']);
var location3 = new CompanyLocalStorageLocation('a');
location3.addPerson('Piter');
location3.addPerson('Parker');
var location4 = new CompanyLocalStorageLocation('b');
location4.addPerson('Vasia');
location4.addPerson('Petia');
var company1 = new Company(location1);
var company2 = new Company(location2);
var company3 = new Company(location3);
var company4 = new Company(location4);
company1.add(new Employee('pikasso1', 'ahaha1'));
company1.add(new Employee('pikasso2', 'ahaha2'));
company1.add(new Employee('pikasso3', 'ahaha3'));
company2.add(new Employee('cur project1', 'ahaha4'));
company2.add(new Employee('cur project2', 'ahaha5'));
company2.add(new Employee('cur project3', 'ahaha6'));
company3.add(new Employee('b1', 'ahaha7'));
company3.add(new Employee('b2', 'ahaha8'));
company3.add(new Employee('b4', 'ahaha9'));
company4.add(new Employee('c1', 'ahaha11'));
company4.add(new Employee('c2', 'ahaha12'));
company4.add(new Employee('c3', 'ahaha13'));
console.log("company1.getNameList() = " + company1.getNameList());
console.log('company1.getProjectList() = ' + company1.getProjectList());
console.log('company1.getLocationPeople() = ' + company1.getLocationPeople());
console.log("company2.getNameList() = " + company2.getNameList());
console.log('company2.getProjectList() = ' + company2.getProjectList());
console.log('company2.getLocationPeople() = ' + company2.getLocationPeople());
console.log("company3.getNameList() = " + company3.getNameList());
console.log('company3.getProjectList() = ' + company3.getProjectList());
console.log('company3.getLocationPeople() = ' + company3.getLocationPeople());
console.log("company4.getNameList() = " + company4.getNameList());
console.log('company4.getProjectList() = ' + company4.getProjectList());
console.log('company4.getLocationPeople() = ' + company4.getLocationPeople());
//# sourceMappingURL=FatRunner.js.map
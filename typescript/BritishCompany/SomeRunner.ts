import Company from "./Company";
import Employee from "./Employee";
import CompanyArrayLocation from "./CompanyArrayLocation";
import CompanyLocalStorageLocation from "./CompanyLocalStorageLocation";
import ILocation from "./ILocation";


let location1: ILocation = new CompanyArrayLocation(['a', 'b', 'c']);
let location2: ILocation = new CompanyArrayLocation(['c', 'd', 'e']);
let location3: ILocation = new CompanyLocalStorageLocation('a');
location3.addPerson('Piter');
location3.addPerson('Parker');

let location4: ILocation = new CompanyLocalStorageLocation('b');
location4.addPerson('Vasia');
location4.addPerson('Petia');

let company1 = new Company(location1);
let company2 = new Company(location2);
let company3 = new Company(location3);
let company4 = new Company(location4);
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

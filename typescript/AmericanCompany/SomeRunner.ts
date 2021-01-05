import Company from "./Company";
import IEmployee from "./IEmployee";
import Frontend from "./Frontend";
import Backend from "./Backend";


let company = new Company();
company.add(new Frontend('medic'))
company.add(new Backend('valentin'));
company.add(new Backend('pikassoq'));
console.log("company.getNameList() = " + company.getNameList());
console.log('company.getProjectList() = ' + company.getProjectList());
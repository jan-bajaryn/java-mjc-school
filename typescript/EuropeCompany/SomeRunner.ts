import Company from "./Company";
import Employee from "./Employee";
import Frontend from "./Frontend";
import Backend from "./Backend";


let company = new Company();
company.add(new Frontend('medic'))
company.add(new Backend('valentin'));
company.add(new Backend('pikassoq'));
company.add(new Employee('pikasso', 'ahaha'));
console.log("company.getNameList() = " + company.getNameList());
console.log('company.getProjectList() = ' + company.getProjectList());
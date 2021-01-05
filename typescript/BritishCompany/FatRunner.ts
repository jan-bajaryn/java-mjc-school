class Company {
    private employees: Employee[];
    private location: ILocation;

    constructor(location: ILocation, employees: Employee[] = []) {
        this.location = location;
        this.employees = employees;
    }

    add(employee: Employee): void {
        this.employees.push(employee);
    }

    getProjectList(): string[] {
        return this.employees
            .map(e => e.getCurrentProject());
    }

    getNameList(): string[] {
        return this.employees
            .map(e => e.getName());
    }
    getLocationPeople(): string[] {
        let result: string[] = [];
        for (let i = 0; i < this.location.getCount(); i++) {
            result.push(this.location.getPerson(i));
        }
        return result;
    }
}


class CompanyArrayLocation implements ILocation {

    private personList: string[] = [];


    constructor(personList: string[]=[]) {
        this.personList = personList;
    }

    addPerson(person: string): void {
        this.personList.push(person);
    }

    getCount(): number {
        return this.personList.length;
    }

    getPerson(index: number): string {
        return this.personList[index];
    }

}

class CompanyLocalStorageLocation implements ILocation {

    private key: string;


    constructor(key: string) {
        this.key = key;
    }

    addPerson(person: string): void {
        let strings: string[] = this.findAll();
        strings.push(person);
        localStorage.setItem(this.key, JSON.stringify(strings));
    }

    getCount(): number {
        return this.findAll().length;
    }

    getPerson(index: number): string {
        return this.findAll()[index];
    }

    findAll(): string[] {
        let item = localStorage.getItem(this.key);
        if (item == null) {
            return [];
        }
        return JSON.parse(item);
    }


}

class Employee {
    private _currentProject: string;
    private _name: string;

    constructor(currentProject:string, name:string) {
        this._currentProject = currentProject;
        this._name = name;
    }

    getCurrentProject(): string {
        return this._currentProject;
    }

    getName(): string {
        return this._name;
    }
}

interface ILocation {
    addPerson(person: string): void;

    getPerson(index: number): string;

    getCount(): number;

}


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

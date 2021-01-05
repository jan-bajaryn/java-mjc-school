import Employee from "./Employee";
import ILocation from "./ILocation";

export default class Company {
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
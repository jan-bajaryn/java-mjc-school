import IEmployee from "./IEmployee";

export default class Company {
    private employees: IEmployee[];

    constructor(employees: IEmployee[] = []) {
        this.employees = employees;
    }

    add(employee: IEmployee): void {
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
}
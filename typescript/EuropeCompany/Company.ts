import Employee from "./Employee";

export default class Company {
    private employees: Employee[];

    constructor(employees: Employee[] = []) {
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
}
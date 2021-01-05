import Employee from "./Employee";

export default class Backend extends Employee {


    constructor(currentProject: string) {
        super(currentProject, "backend");
    }
}
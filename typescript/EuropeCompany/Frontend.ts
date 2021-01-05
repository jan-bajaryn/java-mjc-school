import Employee from "./Employee";

export default class Frontend extends Employee {

    constructor(currentProject: string) {
        super(currentProject, 'frontend');
    }
}
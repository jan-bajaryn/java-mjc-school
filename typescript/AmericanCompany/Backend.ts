import IEmployee from "./IEmployee";

export default class Backend implements IEmployee {

    private name: string = 'backend';
    private currentProject: string;

    constructor(currentProject: string) {
        this.currentProject = currentProject;
    }

    getCurrentProject(): string {
        return this.currentProject;
    }

    getName(): string {
        return this.name;
    }
}
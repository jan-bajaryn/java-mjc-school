import IEmployee from "./IEmployee";

export default class Frontend implements IEmployee {

    private name: string = 'frontend';
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
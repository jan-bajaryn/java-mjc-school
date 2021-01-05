export default class Employee {
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
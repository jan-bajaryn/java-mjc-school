import ILocation from "./ILocation";

export default class CompanyArrayLocation implements ILocation {

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
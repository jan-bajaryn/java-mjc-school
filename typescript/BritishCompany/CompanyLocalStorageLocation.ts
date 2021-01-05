import ILocation from "./ILocation";

export default class CompanyLocalStorageLocation implements ILocation {

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
        console.log('item = ' + item)
        console.log('item json parse = ' + JSON.parse(item))
        if (item == null) {
            return [];
        }
        return JSON.parse(item);
    }


}
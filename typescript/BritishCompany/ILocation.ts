export default interface ILocation {
    addPerson(person: string): void;

    getPerson(index: number): string;

    getCount(): number;

}
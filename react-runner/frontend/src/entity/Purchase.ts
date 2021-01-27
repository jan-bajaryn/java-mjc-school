export default class Purchase {
    certificateId: number | null;
    priceForOne: number;
    name: string;
    count: number;

    constructor(certificateId: number | null, priceForOne: number, name: string, count: number) {
        this.certificateId = certificateId;
        this.priceForOne = priceForOne;
        this.name = name;
        this.count = count;
    }

    static parseList(data: any): Purchase[] {
        let list: Purchase[] = []
        for (let i = 0; i < data.length; i++) {
            list.push(this.parse(data[i]))
        }
        return list;
    }

    private static parse(obj: any): Purchase {
        return new Purchase(obj.certificateId, obj.priceForOne, obj.name, obj.count);
    }
}
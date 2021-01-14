export default class Certificate {
    private _id: number = 0;
    private _name: string = '';
    private _description: string = '';
    private _price: number = 0;
    private _createDate: Date = new Date();
    private _lastUpdateDate: Date = new Date();
    private _duration: number = 0;
    private _tags: Array<string> = [];

    get id(): number {
        return this._id;
    }

    set id(value: number) {
        this._id = value;
    }

    get name(): string {
        return this._name;
    }

    set name(value: string) {
        this._name = value;
    }

    get description(): string {
        return this._description;
    }

    set description(value: string) {
        this._description = value;
    }

    get price(): number {
        return this._price;
    }

    set price(value: number) {
        this._price = value;
    }

    get createDate(): Date {
        return this._createDate;
    }

    set createDate(value: Date) {
        this._createDate = value;
    }

    get lastUpdateDate(): Date {
        return this._lastUpdateDate;
    }

    set lastUpdateDate(value: Date) {
        this._lastUpdateDate = value;
    }

    get duration(): number {
        return this._duration;
    }

    set duration(value: number) {
        this._duration = value;
    }

    get tags(): Array<string> {
        return this._tags;
    }

    set tags(value: Array<string>) {
        this._tags = value;
    }

    static parse(obj): Certificate {
        let cert = new Certificate();
        cert.id = obj.id;
        cert.name = obj.name;
        cert.description = obj.description;
        cert.price = obj.price;
        cert.duration = obj.duration;
        cert.createDate = new Date(obj.createDate)
        cert.lastUpdateDate = new Date(obj.lastUpdateDate)
        cert.tags = obj.tags.map(t => t.name);
        return cert;
    }

    static parseCertificateList(data: any): Certificate[] {
        let list: Certificate[] = [];
        for (let i = 0; i < data.length; i++) {
            let obj = data[i];
            console.log('obj.name = ' + obj.name)
            list.push(Certificate.parse(obj));
        }
        return list;
    }
}
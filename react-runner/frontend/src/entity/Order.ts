import Purchase from "./Purchase";

export default class Order {
    id: number;
    createDate: Date;
    price: number;
    purchases: Purchase[];

    constructor(id: number, createDate: Date, price: number, purchases: Purchase[]) {
        this.id = id;
        this.createDate = createDate;
        this.price = price;
        this.purchases = purchases;
    }

    static parseList(data: any) {
        let list: Order[] = []
        for (let i = 0; i < data.length; i++) {
            let obj = data[i];
            list.push(Order.parse(obj));
        }
        return list;
    }

    static parse(obj: any): Order {
        return new Order(obj.id, new Date(obj.createDate), obj.price, Purchase.parseList(obj.purchases));
    }
}
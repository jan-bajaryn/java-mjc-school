export default class LocalStorageHelper {
    static calcItemCount() {
        return Array.from(this.getBasket().values())
            .reduce((previousValue, currentValue) => previousValue + currentValue, 0)
    }

    static putItemBasket(id: number) {
        let basket = this.getBasket();
        let current = basket.get(id);
        if (current) {
            basket.set(id, current + 1);
        } else {
            basket.set(id, 1);
        }
        this.saveBasket(basket);
    }

    static removeItemBasket(id: number) {
        let basket = this.getBasket();
        let current = basket.get(id);
        if (current) {
            let newCount = current - 1;
            if (newCount < 1) {
                basket.delete(id);
            } else {
                basket.set(id, newCount);
            }
            this.saveBasket(basket);
        }
    }

    private static saveBasket(map: Map<number, number>) {
        console.log('save basket = ', JSON.stringify(Array.from(map.entries())));
        localStorage.setItem('cart', JSON.stringify(Array.from(map.entries())));
    }

    static logout() {
        localStorage.removeItem("authorization");
        localStorage.removeItem("refresh_token");
        localStorage.removeItem("role");
        localStorage.removeItem("userId");
        localStorage.removeItem("username");
        localStorage.removeItem("name");
    }

    static login(access_token, refresh_token, role, id, username, name) {
        localStorage.setItem("authorization", access_token);
        localStorage.setItem("refresh_token", refresh_token);
        localStorage.setItem("role", role);
        localStorage.setItem("userId", id);
        localStorage.setItem("username", username);
        if (name) {
            localStorage.setItem("name", name);
        } else {
            localStorage.removeItem("name");
        }
    }

    static isLogged(): boolean {
        return localStorage.getItem('authorization') !== null;
    }

    static getBasket(): Map<number, number> {
        let item = localStorage.getItem('cart');
        return item === null ? new Map([]) : new Map(JSON.parse(item));
    }

    static getUserId() {
        return localStorage.getItem('userId');
    }

    static clearBasket() {
        localStorage.removeItem('cart');
    }

    static getRole(): string | null {
        return localStorage.getItem('role');
    }

    static getName(): string | null {
        return localStorage.getItem('name');
    }

    static removeAllItemBasket(id: number) {
        let basket = this.getBasket();
        basket.delete(id);
        this.saveBasket(basket);
    }
}
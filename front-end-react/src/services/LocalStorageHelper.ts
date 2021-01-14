export default class LocalStorageHelper {
    static calcItemCount() {
        let item = localStorage.getItem('cart');
        if (item == null) {
            return null;
        } else {
            let parse = JSON.parse(item);
            return parse.length
        }
    }

    static logout() {
        localStorage.removeItem("authorization");
        localStorage.removeItem("refresh_token");
        localStorage.removeItem("role");
        localStorage.removeItem("userId");
        localStorage.removeItem("username");
    }

    static login(access_token, refresh_token, role, id, username) {
        localStorage.setItem("authorization", access_token);
        localStorage.setItem("refresh_token", refresh_token);
        localStorage.setItem("role", role);
        localStorage.setItem("userId", id);
        localStorage.setItem("username", username);
    }

    static isLogged(): boolean {
        return localStorage.getItem('authorization') !== null;
    }
}
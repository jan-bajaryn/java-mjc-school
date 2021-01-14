import axios from "axios";
import LocalStorageHelper from "./LocalStorageHelper";

export default class AuthorizationHandleService {
    static handleTokenExpired(error, successCallback, badCallback) {
        if (error && error.response && error.response.status && error.response.data && error.response.data.error) {
            if (error.response.status === 401 && error.response.data.error === 'invalid_token') {
                AuthorizationHandleService.tryRefreshToken(successCallback, badCallback);
            }
        }
    }

    private static tryRefreshToken(successCallback, badCallback) {
        let refresh = localStorage.getItem("refresh_token");
        console.log("refresh token to use = " + refresh)
        if (refresh) {
            console.log("enter refresh procedure");
            const endpoint = "http://localhost:9000/oauth/token";
            const headers = {
                'Content-Type': 'application/x-www-form-urlencoded',
                'Authorization': 'Basic Y2xpZW50aWQ6cGFzc3dvcmQ='
            }
            const params = new URLSearchParams();
            params.append('grant_type', 'refresh_token');
            params.append('refresh_token', refresh);
            console.log("before send request refresh procedure");
            axios.post(
                endpoint, params,
                {
                    headers: headers
                }
            ).then(res => {
                console.log("refresh procedure success")
                LocalStorageHelper.login(
                    res.data.access_token, res.data.refresh_token, res.data.role, res.data.id, res.data.username
                );
                successCallback();
            }).catch((error) => {
                console.log("refresh procedure error")
                console.log("error refresh procedure = " + error);
                LocalStorageHelper.logout();
                badCallback();
            });
        }
    }

}
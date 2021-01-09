const axios = require("axios");


axios.interceptors.request.use(
    function (config) {
        let jwtToken = localStorage.getItem("authorization");
        console.log("jwtToken = " + jwtToken);
        if (jwtToken) {
            config.headers["Authorization"] = "Bearer " + jwtToken;
        }
        return config;
    },
    function (err) {
        console.log("err = " + err)
        return Promise.reject(err);
    }
);
const prod = {
    urlApi: 'http://localhost:8081/',
    urlAuth: 'http://localhost:9001/'
}
const dev = {
    urlApi: 'http://localhost:8080/',
    urlAuth: 'http://localhost:9000/'
}
export const config = process.env.NODE_ENV === 'development' ? dev : prod;
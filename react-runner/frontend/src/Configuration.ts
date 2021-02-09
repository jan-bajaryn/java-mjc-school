const prod = {
    urlApi: 'http://ec2-100-25-21-39.compute-1.amazonaws.com:8080/',
    urlAuth: 'http://ec2-100-25-21-39.compute-1.amazonaws.com:9000/'
}
const dev = {
    urlApi: 'http://localhost:8080/',
    urlAuth: 'http://localhost:9000/'
}
export const config = process.env.NODE_ENV === 'development' ? dev : prod;
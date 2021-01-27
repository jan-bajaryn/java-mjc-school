export default class QueryUrlParamHelper {
    static setParamQueryArray(query: URLSearchParams, values: string[], name: string) {
        console.log("enter method setParamQueryArray")
        if (values.length !== 0) {
            console.log("set something")
            query.set(name, values.join(','));
        } else {
            query.delete(name);
        }
    }

    static setParamQuery(query: URLSearchParams, value: any, name: string) {
        if (value) {
            query.set(name, value);
        } else {
            query.delete(name);
        }
    }
}
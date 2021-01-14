import {RefObject} from "react";

export default class RefExtractor {
    static exctractRef(ref: RefObject<HTMLInputElement>) {
        if (ref.current) {
            return ref.current.value;
        }
        return '';
    }
}
export default class Parser {
    static dateParseString(cell: Date) {
        return '' + cell.getFullYear() + '-' + (cell.getMonth() + 1) + '-' + cell.getDay() + ' ' + cell.getHours() + ':' + cell.getMinutes();
    }
}
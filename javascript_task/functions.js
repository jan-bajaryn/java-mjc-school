'use strict';

/**
 * You must return a date that comes in a predetermined number of seconds after 01.06.2020 00:00:002020
 * @param {number} seconds
 * @returns {Date}
 *
 * @example
 *      31536000 -> 01.06.2021
 *      0 -> 01.06.2020
 *      86400 -> 02.06.2020
 */
function secondsToDate(seconds) {
    if (seconds == null || Number.isNaN(seconds) || seconds < 0) {
        throw 1;
    }
    let value = new Date(2020, 6, 1, 0, 0, 0, 0);
    value.setSeconds(seconds)
    return value;
}

/**
 * You must create a function that returns a base 2 (binary) representation of a base 10 (decimal) string number
 * ! Numbers will always be below 1024 (not including 1024)
 * ! You are not able to use parseInt
 * @param {number} decimal
 * @return {string}
 *
 * @example
 *      5 -> "101"
 *      10 -> "1010"
 */
function toBase2Converter(decimal) {
    return decimal.toString(2);
}

/**
 * You must create a function that takes two strings as arguments and returns the number of times the first string
 * is found in the text.
 * @param {string} substring
 * @param {string} text
 * @return {number}
 *
 * @example
 *      'a', 'test it' -> 0
 *      't', 'test it' -> 2
 *      'T', 'test it' -> 2
 */
function substringOccurrencesCounter(substring, text) {
    return (text.match(new RegExp(substring, 'gi')) || []).length;
}

/**
 * You must create a function that takes a string and returns a string in which each character is repeated once.
 *
 * @param {string} string
 * @return {string}
 *
 * @example
 *      "Hello" -> "HHeelloo"
 *      "Hello world" -> "HHeello  wworrldd" // o, l is repeated more then once. Space was also repeated
 */
function repeatingLitters(string) {
    let result = [];

    for (let i = 0; i < string.length; i++) {
        let substring = string.charAt(i);
        let count = substringOccurrencesCounter(substring, string);

        if (count > 1) {
            result.push(substring)
        } else {
            result.push(substring)
            result.push(substring)
        }
    }
    return result.join('');
}

/**
 * You must write a function redundant that takes in a string str and returns a function that returns str.
 * ! Your function should return a function, not a string.
 *
 * @param {string} str
 * @return {function}
 *
 * @example
 *      const f1 = redundant("apple")
 *      f1() ➞ "apple"
 *
 *      const f2 = redundant("pear")
 *      f2() ➞ "pear"
 *
 *      const f3 = redundant("")
 *      f3() ➞ ""
 */
function redundant(str) {
    return () => str;
}

/**
 * https://en.wikipedia.org/wiki/Tower_of_Hanoi
 *
 * @param {number} disks
 * @return {number}
 */
function towerHanoi(disks) {
    let result = 0;
    for (let i = 0; i < disks; i++) {
        result = result * 2 + 1;
    }
    return result;
}

/**
 * You must create a function that multiplies two matricies (n x n each).
 *
 * @param {array} matrix1
 * @param {array} matrix2
 * @return {array}
 *
 */
function matrixMultiplication(matrix1, matrix2) {
    let length = matrix1.length;
    let result = new Array(length);
    for (let i = 0; i < length; ++i) {
        result[i] = new Array(length);
        for (let j = 0; j < length; ++j) {
            result[i][j] = 0;
            for (let k = 0; k < length; ++k) {
                result[i][j] += matrix1[i][k] * matrix2[k][j];
            }
        }
    }
    return result;
}

/**
 * Create a gather function that accepts a string argument and returns another function.
 * The function calls should support continued chaining until order is called.
 * order should accept a number as an argument and return another function.
 * The function calls should support continued chaining until get is called.
 * get should return all of the arguments provided to the gather functions as a string in the order specified in the order functions.
 *
 * @param {string} str
 * @return {string}
 *
 * @example
 *      gather("a")("b")("c").order(0)(1)(2).get() ➞ "abc"
 *      gather("a")("b")("c").order(2)(1)(0).get() ➞ "cba"
 *      gather("e")("l")("o")("l")("!")("h").order(5)(0)(1)(3)(2)(4).get()  ➞ "hello"
 */
function gather(str) {
    // let gatherObj = new GatherObj(str);
    // return (s) => new GatherObj();

    let strings = [];

    return (s) => {
        return {
            order: function () {

            },
            gather: function () {

            }
        }
    }
}

//
// function func(val) {
//     var self = this;
//     this._optional = false;
//     this._check = false;
//
//     const doStaff = (message = 'Doing staff') => {
//         console.log(message);
//         return;
//     };
//
//
//     return {
//         check: function (n) {
//             this._check = true;
//             return this;
//         },
//         optional: function (n) {
//             this._check = false;
//             this._optional = true;
//             return this;
//         },
//         exec: function () {
//             if (this._check) doStaff();
//             if (this._optional) doStaff('Maybe not');
//         }
//     }
// }
//
// class GatherObj {
//     constructor(str) {
//         this.str = str;
//     }
//
//     result = 0;
//
//     get(a) {
//         this.result = a + b;
//         return this;
//     }
//
//     order(a) {
//         this.result = this.result * a;
//         return this;
//     }
// }


const expect = require('chai')
    .use(require('chai-datetime'))
    .expect;

describe('Seconds to date test', () => {
    it('Zero input', () => {
        let expected = new Date(2020, 6, 1, 0, 0, 0, 0);
        expect(expected).to.equalDate(secondsToDate(0));
    });

    it('31536000', () => {
        let expected = new Date(2021, 6, 1, 0, 0, 0, 0);
        expect(expected).to.equalDate(secondsToDate(31536000));
    });

    it('86400', () => {
        let expected = new Date(2020, 6, 2, 0, 0, 0, 0);
        expect(expected).to.equalDate(secondsToDate(86400));
    });
    it('null exception', () => {
        expect(() => secondsToDate(null)).to.throw();
    });

    it('negative input', () => {
        expect(() => secondsToDate(-2)).to.throw();
    });

});

describe('toBase2Converter', () => {
    it('5 -> 101', () => {
        expect(toBase2Converter(5)).to.equal("101");
    });
    it('10 -> 1010', () => {
        expect(toBase2Converter(10)).to.equal("1010");
    });
});

// TODO report mistake in task text
describe('substringOccurrencesCounter', () => {
    it('\'a\', \'test it\' -> 0', () => {
        expect(substringOccurrencesCounter('a', 'test it')).to.equal(0);
    });
    it('\'t\', \'test it\' -> 3', () => {
        expect(substringOccurrencesCounter('t', 'test it')).to.equal(3);
    });
    it('\'T\', \'test it\' -> 3', () => {
        expect(substringOccurrencesCounter('T', 'test it')).to.equal(3);
    });
});

describe('repeatingLitters', () => {
    it('"Hello" -> "HHeelloo"', () => {
        expect(repeatingLitters("Hello")).to.equal("HHeelloo");
    });
    it('"Hello world" -> "HHeello  wworrldd"', () => {
        expect(repeatingLitters("Hello world")).to.equal("HHeello  wworrldd");
    });
});

describe('redundant', () => {
    it('"a"-> ()=>"a"', () => {
        expect(redundant("a")).to.be.a("function");
    });
});

describe('towerHanoi', () => {
    it('1', () => {
        expect(towerHanoi(1)).to.equal(1);
    });

    it('2', () => {
        expect(towerHanoi(2)).to.equal(3);
    });
    it('3', () => {
        expect(towerHanoi(3)).to.equal(7);
    });
    it('4', () => {
        expect(towerHanoi(4)).to.equal(15);
    });
});



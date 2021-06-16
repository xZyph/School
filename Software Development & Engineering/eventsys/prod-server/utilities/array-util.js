"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.ArrayUtil = void 0;

class ArrayUtil {
  static contains(array, value) {
    let seen = false;
    array.forEach(element => {
      if (element == value) seen = true;
    });
    return seen;
  }

}

exports.ArrayUtil = ArrayUtil;
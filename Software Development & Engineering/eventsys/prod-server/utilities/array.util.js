"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.ArrayUtil = void 0;

class ArrayUtil {
  static contains(array, value) {
    array.forEach(element => {
      if (element == value) return true;
    });
    return false;
  }

}

exports.ArrayUtil = ArrayUtil;
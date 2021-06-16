export class ArrayUtil {
  static contains(array, value) {
    let seen = false;

    array.forEach(element => {
      if(element == value)
        seen = true
    });
    
    return seen
  }
}
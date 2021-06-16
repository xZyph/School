function tullball(a,b) {
  if( b > a ) {
    return b-a;
  }
  else {
    return a-b;
  }
 }
 var a = tullball(3,4); // 1
 var b = tullball(2,6); // 4

 console.log(tullball(a,b)); // (1, 4) - 3
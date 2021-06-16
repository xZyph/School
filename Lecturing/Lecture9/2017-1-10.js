var a = "8!2,4!2,9!3,12!4";
var b = a.split(",");
var c = 0;
for(var i = 0; i < b.length; i++) {
 var d = b[i].split("!");
 c += d[0] / d[1];
}

console.log(c);
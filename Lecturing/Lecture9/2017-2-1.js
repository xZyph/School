var subjekt = ["Ole","katten","ballen","Kari"];
var verbal = ["liker","savnet","mistet","malte"];
var adverbial = ["forsiktig","raskt","bestemt","undrende"];

for(let i = 0; i < 50; i++) {
  let sub1 = Math.floor(Math.random() * subjekt.length);
  let sub2 = Math.floor(Math.random() * subjekt.length);
  let verb = Math.floor(Math.random() * verbal.length);
  let adverb = Math.floor(Math.random() * adverbial.length);

  console.log(subjekt[sub1] + " " + verbal[verb] + " " + subjekt[sub2] + " " + adverbial[adverb] + ", sa brura.");
}
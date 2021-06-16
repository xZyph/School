let ages = [29, 23, 41, 21, 35, 65, 9, 14, 52, 62, 16, 14, 12, 19, 95, 6];

// Filtrere ut alle aldre som er under 18
// let only18 = [];

// for(let i = 0; i < ages.length; i++) {
//   if(ages[i] >= 18) {
//     only18.push(ages[i]);
//   }
// }

// console.log(only18);


// Filter
let only18;

only18 = ages.filter(age => age >= 18);

console.log(only18);

// Legge sammen alle aldre i arrayet

// let agesTotal = 0;

// for(let i = 0; i < ages.length; i++) {
//   agesTotal += ages[i];
// }

// console.log(agesTotal / ages.length);

// Reduce
let agesTotal;

agesTotal = ages.reduce((total, num) => total + num);

console.log(agesTotal)

// Gjøre noe med alle aldre i arrayet

// console.log(ages)

// for(let i = 0; i < ages.length; i++) {
//   ages[i] = "Alderen er: " + ages[i];
// }

// Map
let ageStrings;

ageStrings += ages.map(age => {
  age += 10
  return 'Alderen er: ' + age
});

console.log(ageStrings);

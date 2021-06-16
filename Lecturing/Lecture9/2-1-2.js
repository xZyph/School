let tall = [2,4,2,3,1,2,5,6,5];

for(let x = 0; x < tall.length; x++) {
  let timesFound = 0;

  for(let y = 0; y < tall.length; y++) {
    if(tall[x] === tall[y]) {
      timesFound++;
    }
  }

  if(timesFound === 1) {
    console.log(tall[x])
  }
}
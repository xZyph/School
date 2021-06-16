let tall = [2, 4, 2, 3, 1, 2, 5, 6, 5]

for(let x = 0; x < tall.length; x++) {
  let tempTall = tall.slice();
  tempTall.splice(x, 1);

  if(tempTall.indexOf(tall[x]) === -1)
    console.log(tall[x])
}
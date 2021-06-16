let chess = (n) => {
  let string;

  for(let x = n; x > 0; x--) {
    string = ""

    for(let y = x; y > (x - 8); y--) {
      string += y % 2 ? "  " : "# "
    }

    console.log(string)
  }
}

chess(8)
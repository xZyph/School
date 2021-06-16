let tree = ( height ) => {
  let string, spaces

  for( var x = height; x > 0; x-- ) {
    spaces = string = ""

    for( let y = ( height - x ); y > 0; y-- )
      spaces += " "

    for( let y = x * 2; y > 1; y-- )
      string += "*"

    console.log( spaces + string )
  }
}

tree(8)
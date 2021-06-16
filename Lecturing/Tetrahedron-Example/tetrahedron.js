let volumeTetrahedron = (a) => {

  if(!Number.isInteger(a) || a < 0 )
    return undefined

  return Math.pow(a, 3) / (6 * Math.sqrt(2)).toFixed(2)
}

console.log(volumeTetrahedron(5))
console.log(volumeTetrahedron("hello"))
console.log(volumeTetrahedron(-1))
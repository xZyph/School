window.onload = startup;

function startup() {

  // Fetching table element from DOM
  let output = document.getElementById("output");

  // Creating triangle with n = 10 - returns 2D array
  let triangle = pascalsTriangle(10);

  // Looping over each array in the triangle
  for(let x = 0; x < triangle.length; x++) {

    // Creating a new row for each array
    let tr = document.createElement("tr");

    // Looping over each element in the inner arrays
    for(let y = 0; y < triangle[x].length; y++) {

      // Checking if the element is a number to remove undefined values
      if(Number.isInteger(triangle[x][y])) {

        // Creating a new column for each number
        let td = document.createElement("td");

        // Setting the innerHTML to be the number from the triangle
        td.innerHTML = triangle[x][y];

        // Adding the column to the table row
        tr.appendChild(td);
      }
    }

    // Adding the filled row element to the table
    output.appendChild(tr);
  }
}

// Function that returns 2D-array containing the pascals triangle with the height of n
function pascalsTriangle(n) {

  // Creating a new array with the length of n
  let triangle = new Array(n);

  // Looping over each element in the array
  for(let i = 0; i < triangle.length; i++) {

    // Creating a new array with the length of n
    triangle[i] = new Array(n);
  }

  // Counting from 0 to n-1
  for(let x = 0; x < n; x++) {

    // Counting from 0 to whatever x is at the moment to create a triangle form
    for(let y = 0; y <= x; y++) {

      // Checking if the number is either 0 or x, and setting the value to 1
      if(y === 0 || y === x) {
        triangle[x][y] = 1;
      }
      else {
        // If the element is not an edge-element, then we have to find the previous row and do our calculation
        triangle[x][y] = (triangle[x-1][y-1] + triangle[x-1][y]);
      }

    }

  }

  // Returning the whole 2D-array
  return triangle;
}
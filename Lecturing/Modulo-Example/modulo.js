window.onload = startup;

function startup() {
  // Uncomment the function you want to be active.

  // table();
  // everyNth(6);
  // hours();
}

// Function to display table
function table() {
  // Changing title text of the page
  document.getElementById("output").innerHTML = "// Farge annenhver rad i en tabell";

  // Fetching my outputbox
  var output = document.getElementById("text");

  // Creating 8 rows
  for(var i = 0; i < 8; i++) {

    // Creating new P element
    var text = document.createElement("p");

    // Setting the text of the paragraph
    text.innerHTML = "Paragraf #" + (i + 1) + " - Dummy text...";

    // Creating new DIV element
    var row = document.createElement("div");

    // Setting class of each paragraph depending on odd or even numbers
    row.classList.add(i % 2 ? "orange" : "green");

    // Adding the paragraph to the div
    row.appendChild(text);

    // Adding the div to the outputbox
    output.appendChild(row);
  }
}

// Function to output every nth number
function everyNth(x) {
  // Changing title text of the page
  document.getElementById("output").innerHTML = "// Hvert " + x + " tall";

  // Fetching the outputbox
  var output = document.getElementById("text");

  // Counting from 1 to 1000
  for(var i = 1; i <= 1000; i++) {

    // Check if the number I am currently on can be divided by X
    if(i % x == 0 || i == 1) {

      // If it is, add it to the outputbox
      output.innerHTML += i + ", ";
    }
  }
}

// Function to display the hours, minuts and seconds from total seconds
function hours() {

  // Defining where the title is going to be
  var title = document.getElementById("output");

  // Defining the outputbox
  var output = document.getElementById("text");

  // Calling my prep function to generate the inputfield
  prep();

  // Function to generate an inputfield with a keypress event
  function prep() {

    // Creating input element
    var input = document.createElement("input");

    // Setting the type to be number
    input.type = "number";

    // Setting the ID to be seconds
    input.id = "seconds";

    // Setting the placeholder
    input.placeholder = "3301 seconds";

    // Adding an onkeyup event to trigger the calculate function
    input.onkeyup = calculate;

    // Adding the inputfield to the outputbox
    output.appendChild(input);
  }

  function calculate() {

    // Fetching the seconds written, then parsing it as an int
    var sec = parseInt(document.getElementById("seconds").value, 10);
    
    // Check if the number is actually a number
    if(Number.isInteger(sec)) {

      // Divide seconds into hours, then removing decimals
      var hours = (sec / 3600).toFixed(0);

      // Divide seconds into hours, then eliminating the hours, then removing decimals
      var minutes = ((sec / 60) % 60).toFixed(0);

      // Find how many seconds we are left with
      var seconds = sec % 60;

      // Updating the title with the calculation result
      title.innerHTML = "// " + (hours > 0 ? hours + " timer, " : "") + (minutes > 0 ? minutes + " minutter & " : "") + seconds + " sekunder";
    }
    else {
      // Updating the title with a message that the program doesn't understand your gibberish
      title.innerHTML = "// wat? o.O";
    }
  }
}
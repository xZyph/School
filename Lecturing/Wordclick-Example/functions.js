window.onload = startup;

function startup() {
  generateText();
}

function generateText() {

  // Defining the text we are working on
  var text = "Hide from vacuum cleaner. Wake up human for food at 4am. Kitten is playing with dead mouse. Pretend you want to go out but then don't gimme attention gimme attention gimme attention gimme attention gimme attention gimme attention just kidding i don't want it anymore meow bye for attack dog, run away and pretend to be victim.";

  // Defining where to output the word clicked
  var output = document.getElementById("output");

  // Setting title
  output.innerHTML = "// Forferdelig mange events";

  // Fetching the root node we are going to fill with words
  var textbox = document.getElementById("text");
  
  // Splitting the text on spaces to get each word
  var wordArray = text.split(" ");

  // Iterating over each word in the array
  wordArray.forEach(function(word) {

    // Creating a paragraph element
    var paragraph = document.createElement("p");

    // Assigning the innerHTML of the paragraph to be whatever the word is this iteration
    paragraph.innerHTML = word;

    // Assigning a function to be invoked on the click event
    paragraph.onclick = function() {

      // Overwriting the innerHTML of the output to display the word clicked, also removing punctuation and commas.
      output.innerHTML = "// Du trykka på <span>«" + this.innerHTML.replace(/[.,]/, "") + "»</span>";
    };

    // Adding the paragraph to the root node
    textbox.appendChild(paragraph);
  });
}
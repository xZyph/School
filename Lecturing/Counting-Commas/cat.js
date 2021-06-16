window.onload = startCatApp;

function startCatApp() {
  let file = document.getElementById("file");
  file.addEventListener('change', handleFile);
}

function handleFile(event) {
  let file = event.target.files[0];
  let reader = new FileReader();

  reader.readAsText(file);

  reader.onload = () => {
    let output = document.getElementById("output"),
        charArray = reader.result.split(''),
        commas = 0;

    charArray.forEach(char => {
      if(char === ",") {
        commas++;
      }
    });

    output.innerHTML = "There are " + commas + " commas in this text.";
  }
}
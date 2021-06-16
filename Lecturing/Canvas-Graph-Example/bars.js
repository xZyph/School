window.onload = startup;

function startup() {

  let data = [
    ["Ost", 2304123],
    ["Kjeks", 302123],
    ["Vin", 329328],
    ["Netflix", 100123]
  ];

  drawStatistics(data);
}

function drawStatistics(data) {
  let canvas = document.getElementById("canvasExample");
  let context = canvas.getContext("2d");
  let indent = canvas.width / (data.length + 1);
  let xPosition = indent;
  let total = 0;

  context.font = "18px monospace";

  // Find total amount
  data.forEach(stat => {
    total += stat[1];
  });

  // Create bars
  data.forEach(stat => {
    let height = canvas.height * (stat[1] / total);

    context.fillStyle = "#1e1e1e";
    context.fillText(stat[0], xPosition - (stat[0].length * 3), canvas.height - 10);

    context.fillRect(xPosition, canvas.height - height - 40, 15, height);
    xPosition += indent;
  });
}
window.onload = startup;

function startup() {
  drawRectangles();
}

function drawRectangles() {
  let canvas = document.getElementById("canvasExample");
  let context = canvas.getContext("2d");

  for(let x = 0; x < canvas.width; x += 50) {
    context.moveTo(x, 0);
    context.lineTo(400, 400);
    context.stroke();
  }

//   let boxHeight = 300;
//   let boxWidth = 300;

//   context.fillRect((canvas.width - boxWidth) / 2, (canvas.height - boxHeight) / 2, boxWidth, boxHeight);
}
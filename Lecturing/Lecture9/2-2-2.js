window.onload = startup;

function startup() {
  let canvas = document.getElementById("tegneflate");
  let ctx = canvas.getContext("2d");

  let indent = (400 - 80) / 8;

  ctx.beginPath();
  ctx.rect(0, 0, 400, 400);
  ctx.stroke();

  for(let x = 1; x <= 9; x++) {
    for(let y = 1; y <= 9; y++) {
      ctx.beginPath();
      ctx.arc(indent * x, indent * y, x * y, 0, 2 * Math.PI);
      ctx.stroke();
    }
  }
}
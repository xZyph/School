window.onload = startup;

function startup() {
  let data = [
    ["Ost", 2304],
    ["Kjeks", 302],
    ["Vin", 3293],
    ["Netflix", 101]
  ];

  drawStatistics(data);
}

function drawStatistics(data) {
  let canvas = document.getElementById("canvasExample");
  let ctx = canvas.getContext("2d");
  let indent = canvas.width / (data.length + 1);
  let xPos = indent;
  let total = 0;

  data.forEach(stat => {
    total += stat[1];
  });

  data.forEach(stat => {
    let height = canvas.height * (stat[1] / total);

    ctx.fillStyle = "#1e1e1e";
    ctx.fillText(stat[0], xPos - (stat[0].length), canvas.height - 10);

    ctx.fillRect(xPos, canvas.height - height - 30, 15, height);

    xPos += indent;
  });

}
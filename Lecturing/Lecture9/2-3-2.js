// FRA EKSAMEN
var forelesningsstart = "2019-11-06T10:15:00+01:00";
var forelesningsslutt = "2019-11-06T12:00:00+01:00";

var naa = new Date();

if(aktiv(forelesningsstart, forelesningsslutt, naa) === true) {
  console.log("Kom deg på forelesning!");
}

// LØSNING
function aktiv(start, slutt, naa) {
  start = new Date(start);
  slutt = new Date(slutt);

  if(naa > start && naa < slutt) {
    return true;
  }
  else {
    return false;
  }
}
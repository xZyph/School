function aktiv(start, slutt, tidspunkt) {
  start = new Date(start)
  slutt = new Date(slutt)
  
  if(tidspunkt > start && tidspunkt < slutt)
    return true
  else
    return false
}
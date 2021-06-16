package helpers

trait DistanceCalculations {
  def haversine(userLocation: Location, warehouseLocation: Location): Int
}

class Calculation extends DistanceCalculations {
  private val earthRadius = 6373

  override def haversine(start: Location, end: Location): Int = {
    val lat1 = Math.toRadians(start.lat)
    val lat2 = Math.toRadians(end.lat)

    val deltaLat = Math.toRadians(end.lat - start.lat)
    val deltaLong = Math.toRadians(end.lon - start.lon)

    val a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) + Math.cos(lat1) * Math.cos(lat2) * Math.sin(deltaLong / 2) * Math.sin(deltaLong / 2)
    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

    (earthRadius * c).toInt
  }
}
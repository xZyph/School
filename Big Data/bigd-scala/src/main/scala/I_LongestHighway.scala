import helpers.{Calculation, Location}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

import scala.collection.mutable

object I_LongestHighway {
  def main(args: Array[String]): Unit = {
    val filePath = "/home/student/Downloads/stavanger.osm"

    val session = SparkSession
      .builder
      .appName("LongestHighway")
      .master("local[*]")
      .getOrCreate()

    val nodes = session
      .read
      .format("com.databricks.spark.xml")
      .option("root", "osm")
      .option("rowTag", "node")
      .load(filePath)

    val ways = session
      .read
      .format("com.databricks.spark.xml")
      .option("root", "osm")
      .option("rowTag", "way")
      .load(filePath)

    session.udf.register("haversine", (v1: mutable.WrappedArray[Double], v2: mutable.WrappedArray[Double]) => {
      var sum: Int = 0
      val distance = new Calculation()
      if (v1.length == v2.length) {
        for (i <- -1 until v1.length-1) yield {
          if (i >= 0){
            sum += distance.haversine(Location(v1(i), v2(i)), Location(v1(i+1), v2(i+1)))
          }
        }
      }
      sum: Int
    })

    val highwaysWithNodes = ways
      .selectExpr("_id as id", "tag as t", "explode(nd._ref) as node")
      .select("id", "t._k", "node")
      .filter(array_contains(col("_k"), "highway"))
      .join(nodes.selectExpr("_id as node", "_lat as lat", "_lon as long"), "node")
      .groupBy("id")
      .agg(collect_set("node").alias("nodes"),
        collect_set("lat").alias("lat"),
        collect_set("long").alias("long"))
      .selectExpr("id", "haversine(lat, long) as length")
      .sort(desc("length"))
      .limit(1)

    highwaysWithNodes.show()
  }
}
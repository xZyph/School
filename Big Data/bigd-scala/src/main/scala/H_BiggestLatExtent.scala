import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object H_BiggestLatExtent {
  def main(args: Array[String]): Unit ={
    val filePath = "/home/student/Downloads/stavanger.osm"

    val session = SparkSession
      .builder
      .appName("BiggestLatExtent")
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

    val buildingLat = ways
      .selectExpr("_id as id", "tag._k as key", "explode(nd._ref) as node")
      .where(array_contains(col("key"), "building"))
      .join(nodes.selectExpr("_id as node", "_lat as lat"), "node")
      .groupBy("id")
      .agg(max("lat").alias("maximum"), min("lat").alias("minimum"))
      .selectExpr("id", "maximum - minimum as difference")
      .sort(desc("difference"))
      .limit(1)

    buildingLat.show(false)
  }
}

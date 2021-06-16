import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{array_contains, col}

object A_BuildingCounter {
  def main(args: Array[String]): Unit = {
    val filePath = "hdfs://localhost:9000/stavanger.osm"

    val session = SparkSession
        .builder
        .appName("BuildingCounter")
        .master("local[*]")
        .getOrCreate

    val ways = session
        .read
        .format("com.databricks.spark.xml")
        .option("root", "osm")
        .option("rowTag", "way")
        .load(filePath)

    val buildings = ways
      .select("tag._k")
      .where(array_contains(col("tag._k"), "building"))
      .count()

    println("\n\n # Total amount of buildings: " + buildings + " # \n\n ")

    session.stop()
  }
}

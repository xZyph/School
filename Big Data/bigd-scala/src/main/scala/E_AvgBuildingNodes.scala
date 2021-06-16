import org.apache.spark.sql.functions.{array_contains, col}
import org.apache.spark.sql.{Row, SparkSession}

object E_AvgBuildingNodes {
  def main(args: Array[String]): Unit = {
    val filePath = "/home/student/Downloads/stavanger.osm"

    val session = SparkSession
      .builder
      .appName("AvgBuildingNodes")
      .master("local[*]")
      .getOrCreate

    val ways = session
      .read
      .format("com.databricks.spark.xml")
      .option("root", "osm")
      .option("rowTag", "way")
      .load(filePath)

    val buildingCount = ways
      .select("tag._k")
      .where(array_contains(col("tag._k"), "building"))
      .count()

    val nodeSum = ways
      .selectExpr("_id as id", "tag as t", "size(nd) as nodes")
      .select("id", "t._k", "nodes")
      .where(array_contains(col("t._k"), "building"))
      .collect
      .map({case Row(_, _, nodes: Int) => nodes})
      .sum

    val avgNodeCount = nodeSum / buildingCount

    println("\n\n # The average amount of nodes forming building ways: " + avgNodeCount + " # \n\n")

    session.stop()
  }
}

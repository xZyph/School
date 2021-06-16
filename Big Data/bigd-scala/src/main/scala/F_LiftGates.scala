import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.{array_contains, col, udf}
import org.apache.spark.sql.{Row, SparkSession}

import scala.collection.mutable

object F_LiftGates {
  def main(args: Array[String]): Unit ={
    val filePath = "/home/student/Downloads/stavanger.osm"

    val session = SparkSession
      .builder
      .appName("LiftGates")
      .master("local[*]")
      .getOrCreate()

    import session.implicits._

    val ways = session
      .read
      .format("com.databricks.spark.xml")
      .option("root", "osm")
      .option("rowTag", "way")
      .load(filePath)

    val nodes = session
      .read
      .format("com.databricks.spark.xml")
      .option("root", "osm")
      .option("rowTag", "node")
      .load(filePath)

    val liftGates = nodes
      .selectExpr("_id as id", "tag")
      .select("id", "tag._k", "tag._v")
      .where(array_contains(col("_k"), "barrier")
        && array_contains(col("_v"), "lift_gate"))
      .collect
      .map({case Row(id: Long,_,_) => id})

    val acceptedTypes = List("service", "path", "unclassified", "road")

    val highwayGateCount = ways
      .selectExpr("_id as id", "tag as t", "nd as nodes")
      .select("id", "t._k", "t._v", "nodes")
      .filter(array_contains(col("_k"), "highway")
        && array_contains_any_string(acceptedTypes)($"t._v")
        && array_contains_any_long(liftGates)($"nodes._ref"))
      .collect()
      .length

    println("\n\n # Number of ways (path, service, road or unclassified) that contain a lift_gate: " + highwayGateCount + " # \n\n")
  }

  def array_contains_any_string(s: Seq[String]): UserDefinedFunction = udf((c: mutable.WrappedArray[String]) => c.toList.intersect(s).nonEmpty)
  def array_contains_any_long(s: Seq[Long]): UserDefinedFunction = udf((c: mutable.WrappedArray[Long]) => c.toList.intersect(s).nonEmpty)
}

import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.{array_contains, col, desc, udf}
import org.apache.spark.sql.{Row, SparkSession}

import scala.collection.mutable

object G_HighwayHumps {
  def main(args: Array[String]): Unit ={
    val filePath = "/home/student/Downloads/stavanger.osm"

    val session = SparkSession
      .builder
      .appName("HighwayHumps")
      .master("local[*]")
      .getOrCreate()

    import session.implicits._

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

    val humps = nodes
      .selectExpr("_id as id", "tag")
      .select("id", "tag._k", "tag._v")
      .where(array_contains(col("_k"), "traffic_calming")
        && array_contains(col("_v"), "hump"))
      .collect
      .map({case Row(id: Long,_,_) => id})

    val highwaysContainingHumps = ways
      .selectExpr("_id as id", "tag as t", "nd as nodes")
      .select("id", "t._k", "nodes")
      .filter(array_contains(col("_k"), "highway")
        && array_contains_any_long(humps)($"nodes._ref"))

    val highwayHumps = highwaysContainingHumps
      .selectExpr("id", "explode(nodes._ref) as ref")
      .where($"ref".isin(humps:_*))
      .groupBy("id")
      .count()
      .as("count")
      .sort(desc("count"))
      .limit(15)

    highwayHumps.show()
  }

  def array_contains_any_long(s: Seq[Long]): UserDefinedFunction = udf((c: mutable.WrappedArray[Long]) => c.toList.intersect(s).nonEmpty)
}

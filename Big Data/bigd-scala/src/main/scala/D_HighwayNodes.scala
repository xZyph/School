import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.desc

object D_HighwayNodes {
  def main(args: Array[String]): Unit ={
    val filePath = "/home/student/Downloads/stavanger.osm"

    val session = SparkSession
      .builder
      .appName("HighwayNodes")
      .master("local[*]")
      .getOrCreate

    import session.implicits._

    val ways = session
      .read
      .format("com.databricks.spark.xml")
      .option("root", "osm")
      .option("rowTag", "way")
      .load(filePath)

    val highways = ways
      .selectExpr("_id as id", "explode(tag) as t", "size(nd) as nodecount")
      .select("id", "nodecount")
      .where($"t._k" === "highway")
      .sort(desc("nodecount"))
      .limit(20)

    highways.show()
  }
}

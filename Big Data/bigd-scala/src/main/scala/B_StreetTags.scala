import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.asc
import org.apache.spark.sql.types._

object B_StreetTags {
  def main(args: Array[String]): Unit ={
    val filePath = "/home/student/Downloads/stavanger.osm"

    val session = SparkSession
      .builder
      .appName("StreetTags")
      .master("local[*]")
      .getOrCreate

    import session.implicits._

    val schema = StructType(
      Array(
        StructField("_id", LongType, true),
        StructField("_lat", DoubleType, true),
        StructField("_lon", DoubleType, true),
        StructField("_version", LongType, true),
        StructField("tag", ArrayType(
          StructType(Array(
              StructField("_VALUE", StringType, true),
              StructField("_k", StringType, true),
              StructField("_v", StringType, true)
            )),
            containsNull = true
          ), true
        )
      )
    )

    val nodes = session.read
      .format("com.databricks.spark.xml")
      .option("root", "osm")
      .option("rowTag", "node")
      .schema(schema)
      .load(filePath)

    val streets = nodes
      .selectExpr("explode(tag) as t", "t._v as street")
      .select("t._k", "street")
      .where($"_k" === "addr:street")
      .groupBy("street")
      .count
      .as("street_count")
      .orderBy(asc("street"))

    streets.show()

    session.stop()
  }
}

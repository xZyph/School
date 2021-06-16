import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{desc, lit}

object C_MostUpdated {
  def main(args: Array[String]): Unit ={
    val filePath = "/home/student/Downloads/stavanger.osm"

    val session = SparkSession
      .builder
      .appName("MostUpdated")
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

    val relations = session
      .read
      .format("com.databricks.spark.xml")
      .option("root", "osm")
      .option("rowTag", "relation")
      .load(filePath)

    val nodeVersion = nodes
      .select("_id", "_version")
      .withColumn("type", lit("node"))

    val wayVersion = ways
      .select("_id", "_version")
      .withColumn("type", lit("way"))

    val relationVersion = relations
      .select("_id", "_version")
      .withColumn("type", lit("relation"))

    val mostUpdated = nodeVersion.union(wayVersion).union(relationVersion)
      .select("_id", "_version", "type")
      .orderBy(desc("_version"))
      .limit(1)

    mostUpdated.show()

    session.stop()
  }
}

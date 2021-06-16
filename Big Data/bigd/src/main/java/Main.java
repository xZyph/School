import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import tasks.counters.*;
import tasks.finders.*;
import helpers.*;

public class Main {
    public static void main(String[] args) throws Exception {
        long startTime, endTime;

        // Setting up file config
//        String filePath = "hdfs://localhost:9000/stavanger.osm";
        String filePath = "/home/student/Downloads/stavanger.osm";
        Path osmFile = new Path(filePath);

        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);

        fs.delete(new Path("output"), true);

        // How many buildings is it in the extract you selected?
        new ConsoleHeader("1. Counting buildings...").show();

        startTime = System.nanoTime();
        Job countBuildings = Job.getInstance(conf, "CountBuildings");
        countBuildings.setJarByClass(Buildings.class);
        countBuildings.setMapperClass(Buildings.BuildingMapper.class);
        countBuildings.setReducerClass(Buildings.IntWritableSumReducer.class);
        countBuildings.setOutputKeyClass(Text.class);
        countBuildings.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(countBuildings, osmFile);
        FileOutputFormat.setOutputPath(countBuildings, new Path("output/1-counted-buildings"));

        countBuildings.waitForCompletion(false);
        endTime = System.nanoTime();

        System.out.println("The program took: " + ((endTime - startTime) / 1000000) + "ms");

        // How many addr:street tags exist for each street?
        new ConsoleHeader("2. Counting street-tags...").show();

        startTime = System.nanoTime();
        Job countStreetTags = Job.getInstance(conf, "CountStreetTags");
        countStreetTags.setJarByClass(StreetTags.class);
        countStreetTags.setMapperClass(StreetTags.StreetMapper.class);
        countStreetTags.setReducerClass(StreetTags.IntWritableSumReducer.class);
        countStreetTags.setOutputKeyClass(Text.class);
        countStreetTags.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(countStreetTags, osmFile);
        FileOutputFormat.setOutputPath(countStreetTags, new Path("output/2-counted-street-tags"));

        countStreetTags.waitForCompletion(false);
        endTime = System.nanoTime();

        System.out.println("The program took: " + ((endTime - startTime) / 1000000) + "ms");

        // Which object in the extract has been updated the most times, and what object is that?
        new ConsoleHeader("3. Finding object that has been most updated...").show();

        startTime = System.nanoTime();
        Job findMostUpdated = Job.getInstance(conf, "FindMostUpdated");
        findMostUpdated.setJarByClass(MostUpdated.class);
        findMostUpdated.setMapperClass(MostUpdated.UpdateMapper.class);
        findMostUpdated.setReducerClass(MostUpdated.MaxVersionReducer.class);
        findMostUpdated.setOutputKeyClass(Text.class);
        findMostUpdated.setOutputValueClass(MostUpdated.TypeVersionRecord.class);

        FileInputFormat.addInputPath(findMostUpdated, osmFile);
        FileOutputFormat.setOutputPath(findMostUpdated, new Path("output/3-most-updated"));

        findMostUpdated.waitForCompletion(false);
        endTime = System.nanoTime();

        System.out.println("The program took: " + ((endTime - startTime) / 1000000) + "ms");

        // Which 20 highways contains the most nodes?
        new ConsoleHeader("4. Finding highways that has the most nodes...").show();

        startTime = System.nanoTime();
        Job highwaysMostNodes = Job.getInstance(conf, "HighwaysMostNodes");
        highwaysMostNodes.setJarByClass(HighwayNodes.class);
        highwaysMostNodes.setMapperClass(HighwayNodes.HighwayNodeMapper.class);
        highwaysMostNodes.setReducerClass(HighwayNodes.NodeSumReducer.class);
        highwaysMostNodes.setOutputKeyClass(Text.class);
        highwaysMostNodes.setOutputValueClass(HighwayNodes.Node.class);

        FileInputFormat.addInputPath(highwaysMostNodes, osmFile);
        FileOutputFormat.setOutputPath(highwaysMostNodes, new Path("output/4-highways-nodes"));

        highwaysMostNodes.waitForCompletion(false);
        endTime = System.nanoTime();

        System.out.println("The program took: " + ((endTime - startTime) / 1000000) + "ms");

        // What is the average number of nodes used to form the building ways in the extract?
        new ConsoleHeader("5. Counting average of nodes used to form building ways...").show();

        startTime = System.nanoTime();
        Job avgNodesBuildingWays = Job.getInstance(conf, "AvgNodesBuildingWays");
        avgNodesBuildingWays.setJarByClass(AverageBuildingNodes.class);
        avgNodesBuildingWays.setMapperClass(AverageBuildingNodes.AvgBuildingNodesMapper.class);
        avgNodesBuildingWays.setReducerClass(AverageBuildingNodes.NodeSumReducer.class);
        avgNodesBuildingWays.setOutputKeyClass(Text.class);
        avgNodesBuildingWays.setOutputValueClass(AverageBuildingNodes.Node.class);

        FileInputFormat.addInputPath(avgNodesBuildingWays, osmFile);
        FileOutputFormat.setOutputPath(avgNodesBuildingWays, new Path("output/5-avg-nodes-building-ways"));

        avgNodesBuildingWays.waitForCompletion(false);
        endTime = System.nanoTime();

        System.out.println("The program took: " + ((endTime - startTime) / 1000000) + "ms");

        // How many ways of types ”highway=path”, ”highway=service”, ”high-way=road”,
        // ”highway=unclassified” contains a node with the tag ”barrier=lift gate”?
        new ConsoleHeader("6. Counting lift-gates...").show();

        startTime = System.nanoTime();
        Job countLiftGates = Job.getInstance(conf, "CountLiftGates");
        countLiftGates.setJarByClass(LiftGates.class);
        countLiftGates.setMapperClass(LiftGates.LiftGateMapper.class);
        countLiftGates.setReducerClass(LiftGates.NodeCompareReducer.class);
        countLiftGates.setOutputKeyClass(Text.class);
        countLiftGates.setOutputValueClass(LiftGates.Node.class);

        FileInputFormat.addInputPath(countLiftGates, osmFile);
        FileOutputFormat.setOutputPath(countLiftGates, new Path("output/6-lift-gates"));

        countLiftGates.waitForCompletion(false);
        endTime = System.nanoTime();

        System.out.println("The program took: " + ((endTime - startTime) / 1000000) + "ms");

        // Which 15 highways contains the most number of traffic calming=hump?
        new ConsoleHeader("7. Finding highways that contain the most traffic humps...").show();

        startTime = System.nanoTime();
        Job findMostTrafficHumps = Job.getInstance(conf, "FindMostTrafficHumps");
        findMostTrafficHumps.setJarByClass(HighwayHumps.class);
        findMostTrafficHumps.setMapperClass(HighwayHumps.HumpMapper.class);
        findMostTrafficHumps.setReducerClass(HighwayHumps.NodeCompareReducer.class);
        findMostTrafficHumps.setOutputKeyClass(Text.class);
        findMostTrafficHumps.setOutputValueClass(HighwayHumps.Node.class);

        FileInputFormat.addInputPath(findMostTrafficHumps, osmFile);
        FileOutputFormat.setOutputPath(findMostTrafficHumps, new Path("output/7-traffic-humps"));

        findMostTrafficHumps.waitForCompletion(false);
        endTime = System.nanoTime();

        System.out.println("The program took: " + ((endTime - startTime) / 1000000) + "ms");

        // Which building has the largest latitudinal extent? ( biggest difference
        // between the northernmost and southernmost node )
        new ConsoleHeader("8. Finding largest latitudinal extent...").show();

        startTime = System.nanoTime();
        Job findLargestLat = Job.getInstance(conf, "FindLargestLatExtent");
        findLargestLat.setJarByClass(BiggestLatExtent.class);
        findLargestLat.setMapperClass(BiggestLatExtent.LatExtentMapper.class);
        findLargestLat.setReducerClass(BiggestLatExtent.NodeCompareReducer.class);
        findLargestLat.setOutputKeyClass(Text.class);
        findLargestLat.setOutputValueClass(BiggestLatExtent.Node.class);

        FileInputFormat.addInputPath(findLargestLat, osmFile);
        FileOutputFormat.setOutputPath(findLargestLat, new Path("output/8-latextent"));

        findLargestLat.waitForCompletion(false);
        endTime = System.nanoTime();

        System.out.println("The program took: " + ((endTime - startTime) / 1000000) + "ms");

        // What is the longest way with tag highway?
        new ConsoleHeader("9. Finding longest highway...").show();

        startTime = System.nanoTime();
        Job longestWay = Job.getInstance(conf, "FindLongestHighway");
        longestWay.setJarByClass(LongestHighway.class);
        longestWay.setMapperClass(LongestHighway.LongestHighwayMapper.class);
        longestWay.setReducerClass(LongestHighway.NodeCompareReducer.class);
        longestWay.setOutputKeyClass(Text.class);
        longestWay.setOutputValueClass(LongestHighway.Node.class);

        FileInputFormat.addInputPath(longestWay, osmFile);
        FileOutputFormat.setOutputPath(longestWay, new Path("output/9-longest-highway"));

        longestWay.waitForCompletion(false);
        endTime = System.nanoTime();

        System.out.println("The program took: " + ((endTime - startTime) / 1000000) + "ms");

        new ConsoleHeader(new String[] { "Done...", "To view the results you open the 'part-r-00000'-files in the output folder." } ).show();
    }
}

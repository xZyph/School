package tasks.finders;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;

public class LongestHighway {

    public static class Node implements Writable{
        private Text nodeId;
        private DoubleWritable nodeLat;
        private DoubleWritable nodeLong;
        private BooleanWritable correct;

        // Constructors
        public Node(){
            this.nodeId = new Text();
            this.nodeLat = new DoubleWritable();
            this.nodeLong = new DoubleWritable();
            this.correct = new BooleanWritable(false);
        }

        Node(Text nodeId, DoubleWritable nodeLat, DoubleWritable nodeLong){
            this.nodeId = nodeId;
            this.nodeLat = nodeLat;
            this.nodeLong = nodeLong;
            this.correct = new BooleanWritable(false);
        }

        Node(Text nodeId, BooleanWritable correct){
            this.nodeId = nodeId;
            this.nodeLat = new DoubleWritable();
            this.nodeLong = new DoubleWritable();
            this.correct = correct;
        }

        // Getters
        String getNodeId(){ return nodeId.toString(); }
        double getNodeLat(){ return nodeLat.get(); }
        double getNodeLong(){ return nodeLong.get(); }
        boolean getCorrect(){ return correct.get(); }

        // Methods
        public void write(DataOutput dataOutput) throws IOException {
            nodeId.write(dataOutput);
            nodeLat.write(dataOutput);
            nodeLong.write(dataOutput);
            correct.write(dataOutput);
        }

        public void readFields(DataInput dataInput) throws IOException {
            nodeId.readFields(dataInput);
            nodeLat.readFields(dataInput);
            nodeLong.readFields(dataInput);
            correct.readFields(dataInput);
        }

        @Override
        public String toString() { return "Node id=" + nodeId.toString(); }
    }

    public static class HighwayHelper {
        private Text key;
        private HashSet<Text> values;

        HighwayHelper(Text key, HashSet<Text> values){
            this.key = key;
            this.values = values;
        }

        Text getKey() { return key; }

        HashSet<Text> getValues() { return values; }
    }

    public static class LatLongHelper {
        private double lat;
        private double lon;

        LatLongHelper(double lat, double lon){
            this.lat = lat;
            this.lon = lon;
        }

        double getLat(){ return lat; }

        double getLon(){ return lon; }
    }

    public static class LongestHighwayMapper extends Mapper<Object, Text, Text, Node> {
        private String wayId;
        private String nodeId;
        private double nodeLat;
        private double nodeLong;
        private boolean correctWay = false;

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

            StringTokenizer itr = new StringTokenizer(value.toString(), "\t\n\r\f");

            while (itr.hasMoreTokens()) {
                String currentToken = itr.nextToken().trim();

                if(currentToken.matches(".*<node.*")) {
                    nodeId = currentToken.split("\"|'")[1];

                    if(currentToken.matches(".*lat=.*")) {
                        nodeLat = Double.parseDouble(currentToken.split("\"|'")[15]);
                    }

                    if(currentToken.matches(".*lon=.*")) {
                        nodeLong = Double.parseDouble(currentToken.split("\"|'")[17]);
                    }
                }

                if(nodeId != null && nodeLat != 0 && nodeLong != 0) {
                    context.write(new Text("Node"),
                            new Node(new Text(nodeId),
                            new DoubleWritable(nodeLat),
                            new DoubleWritable(nodeLong)));
                }

                if(currentToken.matches(".*(</node)|(/>).*")) {
                    nodeId = null;
                    nodeLat = 0;
                }

                if (currentToken.matches(":*<way.*")) {
                    wayId = currentToken.split("\"|'")[1];
                }

                if (currentToken.matches(".*<nd.*")) {
                    nodeId = currentToken.split("\"|'")[1];
                }

                if (currentToken.matches(".*k=(\"|')highway(\"|').*")) {
                    correctWay = true;
                    nodeId = "some-generic-value";
                }

                if (wayId != null && nodeId != null) {
                    context.write(new Text(wayId), new Node(new Text(nodeId), new BooleanWritable(correctWay)));
                    correctWay = false;
                }

                if (currentToken.matches(".*</way.*")) {
                    wayId = null;
                    nodeId = null;
                }
            }
        }
    }

    public static class NodeCompareReducer extends Reducer<Text, Node, Text, DoubleWritable> {
        private Map<Text, LatLongHelper> idMap = new HashMap<Text, LatLongHelper>();
        private List<HighwayHelper> highwayMap = new LinkedList<HighwayHelper>();
        boolean correct = false;

        public void reduce(Text key, Iterable<Node> values, Context context) throws IOException, InterruptedException {
            if(key.toString().contains("Node")) {
                for(Node val : values){
                    idMap.put(new Text(val.getNodeId()), new LatLongHelper(val.getNodeLat(), val.getNodeLong()));
                }
            }
            else {
                HashSet<Text> tempList = new HashSet<Text>();

                for(Node val : values) {

                    tempList.add(new Text(val.getNodeId()));

                    if(val.getCorrect()) {
                        correct = true;
                    }
                }

                if(correct) {
                    highwayMap.add(new HighwayHelper(new Text(key.toString()), tempList));
                    correct = false;
                }
            }
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            Text longest = new Text();
            double longestLength = 0;
            LatLongHelper startPoint = null;
            LatLongHelper endPoint;

            for(HighwayHelper highway : highwayMap) {

                double sum = 0;

                for(Text val : highway.getValues()) {

                    double currentLat = idMap.get(val).getLat();
                    double currentLong = idMap.get(val).getLon();

                    endPoint = new LatLongHelper(currentLat, currentLong);
                    sum += calculateDistance(startPoint, endPoint);
                    startPoint = endPoint;

                }

                if(highway.getKey().toString().equals("132766467")) {
                    System.out.println(highway.getValues().size());
                    System.out.println(sum);
                }

                if(longestLength < sum) {
                    longestLength = sum;
                    longest = new Text("Highway with id=" + highway.getKey() + ", length: ");
                }
            }

            context.write(longest, new DoubleWritable(longestLength));
        }

        private double calculateDistance(LatLongHelper startPoint, LatLongHelper endPoint) {
            double earthRadius = 6373;

            if(startPoint != null && endPoint != null) {
                double lat1 = Math.toRadians(startPoint.getLat());
                double lat2 = Math.toRadians(endPoint.getLat());

                double deltaLat = Math.toRadians(endPoint.getLat() - startPoint.getLat());
                double deltaLong = Math.toRadians(endPoint.getLon() - startPoint.getLon());

                double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) + Math.cos(lat1) * Math.cos(lat2) * Math.sin(deltaLong/2) * Math.sin(deltaLong/2);
                double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

                return earthRadius * c;
            }
            else {
                return 0;
            }

        }
    }
}
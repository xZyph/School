package tasks.finders;

import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;

public class BiggestLatExtent {

    public static class Node implements Writable {
        private Text nodeId;
        private DoubleWritable nodeLat;
        private BooleanWritable correct;

        // Constructors
        public Node(){
            this.nodeId = new Text();
            this.nodeLat = new DoubleWritable();
            this.correct = new BooleanWritable(false);
        }

        Node(Text nodeId, DoubleWritable nodeLat){
            this.nodeId = nodeId;
            this.nodeLat = nodeLat;
            this.correct = new BooleanWritable(false);
        }

        Node(Text nodeId, BooleanWritable correct){
            this.nodeId = nodeId;
            this.nodeLat = new DoubleWritable();
            this.correct = correct;
        }

        // Getters
        String getNodeId(){ return nodeId.toString(); }
        double getNodeLat(){ return nodeLat.get(); }
        boolean getCorrect(){ return correct.get(); }

        // Methods
        public void write(DataOutput dataOutput) throws IOException {
            nodeId.write(dataOutput);
            nodeLat.write(dataOutput);
            correct.write(dataOutput);
        }

        public void readFields(DataInput dataInput) throws IOException {
            nodeId.readFields(dataInput);
            nodeLat.readFields(dataInput);
            correct.readFields(dataInput);
        }

        @Override
        public String toString() { return "Node id=" + nodeId.toString(); }
    }

    public static class BuildingLats {
        private Text key;
        private HashSet<Text> values;

        // Constructors
        BuildingLats(Text key, HashSet<Text> values){
            this.key = key;
            this.values = values;
        }

        // Getters
        Text getKey() { return key; }
        HashSet<Text> getValues() { return values; }

        @Override
        public String toString() {
            return key + ": " + values;
        }
    }

    public static class LatExtentMapper extends Mapper<Object, Text, Text, Node> {
        private String wayId;
        private String nodeId;
        private double nodeLat;
        private boolean isBuilding = false;

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            StringTokenizer itr = new StringTokenizer(value.toString(), "\t\n\r\f");

            while (itr.hasMoreTokens()) {
                String currentToken = itr.nextToken().trim();

                if(currentToken.matches(".*<node.*")) {
                    nodeId = currentToken.split("\"|'")[1];

                    if(currentToken.matches(".*lat=.*")) {
                        nodeLat = Double.parseDouble(currentToken.split("\"|'")[15]);
                    }
                }

                if(nodeId != null && nodeLat != 0) {
                    context.write(new Text("Node"), new Node(new Text(nodeId), new DoubleWritable(nodeLat)));
                }

                if(currentToken.matches(".*(</node)|(/>).*")) {
                    nodeId = null;
                    nodeLat = 0;
                }

                if(currentToken.matches(".*<way.*")) {
                    wayId = currentToken.split("\"|'")[1];
                }

                if (currentToken.matches(".*<nd.*")) {
                    nodeId = currentToken.split("\"|'")[1];
                }

                if (currentToken.matches(".*k=(\"|')building(\"|').*")) {
                    isBuilding = true;
                    nodeId = "some-generic-value";
                }

                if (wayId != null && nodeId != null) {
                    context.write(new Text(wayId), new Node(new Text(nodeId), new BooleanWritable(isBuilding)));
                }

                if (currentToken.matches(":*</way.*")) {
                    isBuilding = false;
                    wayId = null;
                    nodeId = null;
                }
            }
        }
    }

    public static class NodeCompareReducer extends Reducer<Text, Node, Text, DoubleWritable> {
        private Map<Text, DoubleWritable> idMap = new HashMap<Text, DoubleWritable>();
        private List<BuildingLats> buildingMap = new LinkedList<BuildingLats>();
        boolean correct = false;

        public void reduce(Text key, Iterable<Node> values, Context context) throws IOException, InterruptedException {

            if(key.toString().contains("Node")) {
                for(Node val : values){
                    idMap.put(new Text(val.getNodeId()), new DoubleWritable(val.getNodeLat()));
                }
            }
            else {
                HashSet<Text> tempList = new HashSet<Text>();

                for(Node val : values) {

                    if(val.getCorrect()) {
                        correct = true;
                    }
                    else {
                        tempList.add(new Text(val.getNodeId()));
                    }
                }
                if(correct) {
                    buildingMap.add(new BuildingLats(new Text(key.toString()), tempList));
                    correct = false;
                }
            }
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            String biggest = "";
            double difference = 0;

            for(BuildingLats building : buildingMap) {
                double biggestLat = 0;
                double smallestLat = Double.MAX_VALUE;

                for(Text val : building.getValues()) {
                    Double currentLat = idMap.get(val).get();

                    if (currentLat > biggestLat) {
                        biggestLat = currentLat;
                    }

                    if (currentLat < smallestLat) {
                        smallestLat = currentLat;
                    }
                }
                if(difference < (biggestLat - smallestLat)) {
                    difference = biggestLat - smallestLat;
                    biggest = building.getKey().toString();
                }
            }
            context.write(new Text("Building with id=" + biggest + ", Latitudinal difference: "), new DoubleWritable(difference));
        }
    }
}
package tasks.counters;

import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;

public class HighwayHumps {

    public static class Node implements Writable {
        private Text nodeId;
        private BooleanWritable isHighway;

        // Constructors
        public Node(){
            this.nodeId = new Text();
            this.isHighway = new BooleanWritable(false);
        }

        Node(Text nodeId){
            this.nodeId = nodeId;
            this.isHighway = new BooleanWritable(true);
        }

        Node(Text nodeId, BooleanWritable isHighway){
            this.nodeId = nodeId;
            this.isHighway = isHighway;
        }

        // Getters
        String getNodeId() {return nodeId.toString(); }
        boolean getIsHighway(){ return isHighway.get(); }

        // Methods
        public void write(DataOutput dataOutput) throws IOException {
            nodeId.write(dataOutput);
            isHighway.write(dataOutput);
        }

        public void readFields(DataInput dataInput) throws IOException {
            nodeId.readFields(dataInput);
            isHighway.readFields(dataInput);
        }

        @Override
        public String toString() { return "Node id=" + nodeId.toString(); }
    }

    public static class WayNds {
        private Text way;
        private HashSet<Text> nds;

        // Constructors
        WayNds(Text way, HashSet<Text> nds){
            this.way = way;
            this.nds = nds;
        }

        // Getters
        Text getWay() { return way; }
        HashSet<Text> getNds() { return nds; }
    }

    public static class HumpMapper extends Mapper<Object, Text, Text, Node> {
        private String wayId;
        private String nodeId;
        private boolean hasHump = false;
        private boolean isHighway = false;

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            StringTokenizer itr = new StringTokenizer(value.toString(), "\t\n\r\f");

            while (itr.hasMoreTokens()) {

                String nextToken = itr.nextToken().trim();

                if(nextToken.matches(".*<node.*")) {
                    nodeId = nextToken.split("\"|'")[1];
                }

                if(nextToken.matches(".*k=(\"|')traffic_calming(\"|').*v=(\"|')hump(\"|').*")) {
                    hasHump = true;
                }

                if(nodeId != null && hasHump) {
                    context.write(new Text("Hump"), new Node(new Text(nodeId)));
                    hasHump = false;
                }

                if(nextToken.matches("(.*</node.*)|(.*/>.*)")) {
                    nodeId = null;
                }

                if (nextToken.matches(".*<way.*")) {
                    wayId = nextToken.split("\"|'")[1];
                }

                if (nextToken.matches(".*<nd.*")) {
                    nodeId = nextToken.split("\"|'")[1];
                }

                if (nextToken.matches(".*k=(\"|')highway(\"|').*")) {
                    isHighway = true;
                    nodeId = "a-generic-value";
                }

                if (wayId != null && nodeId != null) {
                    context.write(new Text(wayId), new Node(new Text(nodeId), new BooleanWritable(isHighway)));
                    isHighway = false;
                }

                if (nextToken.matches(".*</way.*")) {
                    wayId = null;
                    nodeId = null;
                }
            }
        }
    }

    public static class NodeCompareReducer extends Reducer<Text, Node, Text, Text> {
        private HashSet<String> nodeMap = new HashSet<String>();
        private List<WayNds> wayMap = new LinkedList<WayNds>();
        boolean isHighway = false;

        public void reduce(Text key, Iterable<Node> values, Context context) throws IOException, InterruptedException {
            if(key.toString().contains("Hump")) {
                for (Node val : values) {
                    nodeMap.add(val.getNodeId());
                }
            }
            else {
                HashSet<Text> tempList = new HashSet<Text>();

                for(Node val : values) {

                    tempList.add(new Text(val.getNodeId()));

                    if(val.getIsHighway()) {
                        isHighway = true;
                    }
                }

                if(isHighway) {
                    wayMap.add(new WayNds(new Text(key.toString()), tempList));
                    isHighway = false;
                }
            }
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            Map<Text, IntWritable> unsortedMap = new HashMap<Text, IntWritable>();
            List<Map.Entry<Text, IntWritable>> sortedList;

            for(WayNds way : wayMap) {
                int sum = 0;

                for (Text val : way.getNds()) {
                    if(nodeMap.contains(val.toString())) {
                        sum += 1;
                    }
                }

                if(sum > 0) {
                    unsortedMap.put(new Text(way.way.toString()), new IntWritable(sum));
                }
            }

            sortedList = sortList(unsortedMap);

            if(sortedList.size() > 0) {
                for (int i = 0; i < 15; i++) {
                    try {
                        context.write(new Text("Way with id=" + sortedList.get(i).getKey().toString()), new Text("Amount of humps: " + sortedList.get(i).getValue().toString()));
                    }
                    catch (IndexOutOfBoundsException ignored) {
                        // Just ignoring this
                    }
                }
            }
        }

        private List<Map.Entry<Text, IntWritable>> sortList(Map<Text, IntWritable> unsortedMap) {
            List<Map.Entry<Text,IntWritable>> list = new LinkedList<Map.Entry<Text,IntWritable>>(unsortedMap.entrySet());

            Collections.sort(list, new Comparator<Map.Entry<Text,IntWritable>>() {
                public int compare (Map.Entry<Text , IntWritable> o1 , Map.Entry<Text , IntWritable> o2 ) {
                    return o2.getValue().compareTo(o1.getValue());
                }
            });

            return list;
        }
    }
}
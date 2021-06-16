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

public class LiftGates {

    public static class Node implements Writable {
        private Text nodeId;
        private BooleanWritable meetsCriteria;

        // Constructors
        public Node(){
            this.nodeId = new Text();
            this.meetsCriteria = new BooleanWritable(false);
        }

        Node(Text nodeId){
            this.nodeId = nodeId;
            this.meetsCriteria = new BooleanWritable(true);
        }

        Node(Text nodeId, BooleanWritable meetsCriteria){
            this.nodeId = nodeId;
            this.meetsCriteria = meetsCriteria;
        }

        // Getters
        String getNodeId() { return nodeId.toString(); }
        boolean meetsCriteria() { return meetsCriteria.get(); }

        // Methods
        public void write(DataOutput dataOutput) throws IOException {
            nodeId.write(dataOutput);
            meetsCriteria.write(dataOutput);
        }

        public void readFields(DataInput dataInput) throws IOException {
            nodeId.readFields(dataInput);
            meetsCriteria.readFields(dataInput);
        }

        @Override
        public String toString() { return "Node with id=" + nodeId.toString(); }
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
        HashSet<Text> getNds() { return nds; }
    }

    public static class LiftGateMapper extends Mapper<Object, Text, Text, Node> {
        private static final Text node = new Text("Node");
        private String wayId;
        private String nodeId;

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

            StringTokenizer itr = new StringTokenizer(value.toString(), "\t\n\r\f");
            boolean isCorrect = false;

            while (itr.hasMoreTokens()) {
                String currentToken = itr.nextToken().trim();

                if(currentToken.matches(".*<node.*")) {
                    nodeId = currentToken.split("\"|'")[1];
                }

                if(currentToken.matches(".*k=(\"|')barrier(\"|').*v=(\"|')lift_gate(\"|').*")) {
                    isCorrect = true;
                }

                if(nodeId != null && isCorrect) {
                    context.write(node, new Node(new Text(nodeId)));
                }

                if(currentToken.matches(".*</node.*")) {
                    wayId = null;
                    nodeId = null;
                    isCorrect = false;
                }

                if(currentToken.matches(".*<way.*")) {
                    wayId = currentToken.split("\"|'")[1];
                }

                if(wayId != null && currentToken.matches(".*<nd.*")) {
                    nodeId = currentToken.split("\"|'")[1];
                }

                if(currentToken.matches(".*k=(\"|')highway(\"|').*v=(\"|')(path|service|road|unclassified)(\"|').*")) {
                    isCorrect = true;
                }

                if(wayId != null && nodeId != null) {
                    context.write(new Text(wayId), new Node(new Text(nodeId), new BooleanWritable(isCorrect)));
                }

                if(currentToken.matches(".*</way.*")) {
                    wayId = null;
                    nodeId = null;
                    isCorrect = false;
                }
            }
        }
    }

    public static class NodeCompareReducer extends Reducer<Text, Node, Text, IntWritable> {
        private HashSet<String> nodeMap = new HashSet<String>();
        private List<WayNds> wayMap = new LinkedList<WayNds>();

        public void reduce(Text key, Iterable<Node> values, Context context) throws IOException, InterruptedException {

            if(key.toString().contains("Node")) {
                for (Node val : values) {
                    nodeMap.add(val.nodeId.toString());
                }
            }
            else {
                boolean correct = false;
                HashSet<Text> temp = new HashSet<Text>();

                for(Node val : values) {

                    temp.add(new Text(val.getNodeId()));

                    if(val.meetsCriteria()) {
                        correct = true;
                    }
                }

                if(correct) {
                    wayMap.add(new WayNds(new Text(key.toString()), temp));
                }
            }
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            final Text text = new Text("Number of ways (path, service, road or unclassified) that contain a lift_gate: ");
            int sum = 0;

            for(WayNds way : wayMap) {
                for (Text id : way.getNds()) {
                    if (nodeMap.contains(id.toString())) {
                        sum += 1;
                        break;
                    }
                }
            }

            context.write(text, new IntWritable(sum));
        }
    }
}

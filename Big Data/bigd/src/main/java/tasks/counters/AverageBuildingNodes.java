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

public class AverageBuildingNodes {

    public static class Node implements Writable {
        private IntWritable count;
        private BooleanWritable isBuilding;

        // Constructors
        public Node(){
            this.count = new IntWritable();
            this.isBuilding = new BooleanWritable();
        }

        public Node(IntWritable count){
            this.count = count;
            this.isBuilding = new BooleanWritable(false);
        }

        public Node(BooleanWritable isBuilding){
            this.count = new IntWritable(0);
            this.isBuilding = isBuilding;
        }

        // Getters
        int getCount() { return count.get(); }
        boolean getIsBuilding() { return isBuilding.get(); }

        // Methods
        public void write(DataOutput dataOutput) throws IOException {
            count.write(dataOutput);
            isBuilding.write(dataOutput);
        }

        public void readFields(DataInput dataInput) throws IOException {
            count.readFields(dataInput);
            isBuilding.readFields(dataInput);
        }

        @Override
        public String toString() { return count.get() + " " + isBuilding.toString(); }
    }

    public static class AvgBuildingNodesMapper extends Mapper<Object, Text, Text, Node> {

        private static final Node count = new Node(new IntWritable(1));
        private static final Node building = new Node(new BooleanWritable(true));
        private Text identity;

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

            StringTokenizer itr = new StringTokenizer(value.toString(), "\t\n\r\f");

            while (itr.hasMoreTokens()) {

                String nextToken = itr.nextToken();

                if(nextToken.contains("<way")) {
                    identity = new Text(value.toString().trim().split(" ")[1]);
                }

                if(nextToken.contains("<nd") && identity != null) {
                    context.write(identity, count);
                }

                if((nextToken.contains("k=\"building\"") || nextToken.contains("k='building'")) && identity != null) {
                    context.write(identity, building);
                }

                if(nextToken.contains("</way")) {
                    identity = null;
                }
            }
        }
    }

    public static class NodeSumReducer extends Reducer<Text, Node, Text, IntWritable> {
        Map<Text, IntWritable> wayMap = new HashMap<Text, IntWritable>();

        public void reduce(Text key, Iterable<Node> values, Context context) throws IOException, InterruptedException {

            int sum = 0;
            boolean isBuilding = false;

            for (Node val : values) {

                sum += val.getCount();

                if(val.getIsBuilding()) {
                    isBuilding = true;
                }
            }

            if(isBuilding) {
                wayMap.put(new Text("Building " + key.toString()), new IntWritable(sum));
            }
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {

            int averageNodes = 0;

            for (Map.Entry<Text, IntWritable> val: wayMap.entrySet()) {
                averageNodes += val.getValue().get();
            }

            averageNodes /= wayMap.size();
            context.write(new Text("Average: "), new IntWritable(averageNodes));
        }
    }
}

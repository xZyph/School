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

public class HighwayNodes {

    public static class Node implements Writable {
        private IntWritable count;
        private BooleanWritable isHighway;

        // Constructors
        public Node() {
            this.count = new IntWritable();
            this.isHighway = new BooleanWritable();
        }

        public Node(IntWritable count) {
            this.count = count;
            this.isHighway = new BooleanWritable(false);
        }

        public Node(BooleanWritable isHighway) {
            this.count = new IntWritable(0);
            this.isHighway = isHighway;
        }

        // Getters
        int getCount() { return count.get(); }
        boolean getIsHighway() { return isHighway.get(); }

        // Methods
        public void write(DataOutput dataOutput) throws IOException {
            count.write(dataOutput);
            isHighway.write(dataOutput);
        }

        public void readFields(DataInput dataInput) throws IOException {
            count.readFields(dataInput);
            isHighway.readFields(dataInput);
        }

        @Override
        public String toString() { return count.get() + " " + isHighway.toString(); }
    }

    public static class HighwayNodeMapper extends Mapper<Object, Text, Text, Node> {

        private static final IntWritable startValue = new IntWritable(1);
        private Text identity;

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

            StringTokenizer itr = new StringTokenizer(value.toString(), "\t\n\r\f");
            while (itr.hasMoreTokens()) {

                String nextToken = itr.nextToken();


                if(nextToken.contains("<way")) {
                    identity = new Text("way with " + value.toString().trim().split(" ")[1] + " has a nodecount of: ");
                }

                if(nextToken.contains("<nd") && identity != null) {
                    context.write(identity, new Node(startValue));
                }

                if((nextToken.contains("k=\"highway\"") || nextToken.contains("k='highway'")) && identity != null) {
                    context.write(identity, new Node(new BooleanWritable(true)));
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
            boolean isHighway = false;

            for (Node val : values) {
                sum += val.getCount();
                if(val.getIsHighway()) isHighway = true;
            }

            if(isHighway) wayMap.put(new Text("High" + key.toString()), new IntWritable(sum));
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            List<Map.Entry<Text, IntWritable>> sortedList = sortList(wayMap);

            for (int i = 0; i < 20; i++) {
                context.write(sortedList.get(i).getKey(), sortedList.get(i).getValue());
            }
        }

        private List<Map.Entry<Text, IntWritable>> sortList(Map<Text, IntWritable> unsortedMap){
            List<Map.Entry<Text,IntWritable>> list = new LinkedList<Map.Entry<Text,IntWritable>>(unsortedMap.entrySet());

            Collections.sort(list , new Comparator<Map.Entry<Text,IntWritable>>(){
                public int compare (Map.Entry<Text , IntWritable> o1 , Map.Entry<Text , IntWritable> o2 ){
                    return o2.getValue().compareTo(o1.getValue());
                }
            });

            return list;
        }
    }
}

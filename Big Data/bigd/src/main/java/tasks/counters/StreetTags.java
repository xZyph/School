package tasks.counters;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.StringTokenizer;

public class StreetTags {

    public static class StreetMapper extends Mapper<Object, Text, Text, IntWritable> {

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

            StringTokenizer itr = new StringTokenizer(value.toString(), "\t\n\r\f");

            while(itr.hasMoreTokens()) {
                String nextToken = itr.nextToken();

                if(nextToken.contains("addr:street")) {
                    context.write(new Text(nextToken.split("'|\"")[3]), new IntWritable(1));
                }
            }
        }
    }

    public static class IntWritableSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

        private IntWritable result = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> allNumbers, Context context) throws IOException, InterruptedException {

            int totalSum = 0;

            for (IntWritable number : allNumbers) {
                totalSum += number.get();
            }

            result.set(totalSum);
            context.write(key, result);
        }
    }
}

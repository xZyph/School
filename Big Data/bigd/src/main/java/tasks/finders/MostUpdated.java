package tasks.finders;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class MostUpdated {

    public static class TypeVersionRecord implements Writable {
        private Text type;
        private Text version;

        TypeVersionRecord(){
            this.type = new Text();
            this.version = new Text();
        }

        // Getters
        String getVersion(){ return version.toString(); }
        String getType() { return type.toString(); }

        // Setters
        void setVersion(String version){ this.version.set(version); }
        void setType(String type){ this.type.set(type); }

        // Methods
        public void write(DataOutput output) throws IOException {
            type.write(output);
            version.write(output);
        }

        public void readFields(DataInput input) throws IOException {
            type.readFields(input);
            version.readFields(input);
        }

        @Override
        public String toString() { return type + ", which was updated " + version + " times."; }
    }

    public static class UpdateMapper extends Mapper<Object, Text, Text, TypeVersionRecord> {

        private TypeVersionRecord record = new TypeVersionRecord();

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

            if(!(value.toString().startsWith("<?xml")) && !(value.toString().startsWith("<osm")) && !(value.toString().startsWith("<bounds"))) {

                StringTokenizer itr = new StringTokenizer(value.toString(), "\t\n\r\f");

                while(itr.hasMoreTokens()){

                    String nextToken = itr.nextToken();

                    if(nextToken.contains("version=")){

                        String objectType = value.toString().trim().split(" ")[0].replace("<", "");
                        List<String> objectData = new ArrayList<String>(Arrays.asList(nextToken.split("'|\"")));
                        int versionIndex = objectData.indexOf(" version=") + 1;

                        record.setType(objectType);
                        record.setVersion(objectData.get(versionIndex));

                        context.write(new Text("Most updated record was:"), record);
                    }
                }
            }
        }
    }

    public static class MaxVersionReducer extends Reducer<Text, TypeVersionRecord, Text, Text> {

        public void reduce(Text key, Iterable<TypeVersionRecord> allRecords, Context context) throws IOException, InterruptedException {

            int timesUpdated = 0;
            Text id = new Text();

            for (TypeVersionRecord currentRecord : allRecords) {

                if(timesUpdated < Integer.parseInt(currentRecord.getVersion())){

                    timesUpdated = Integer.parseInt(currentRecord.getVersion());
                    id.set(currentRecord.toString());
                }
            }

            context.write(key, id);
        }
    }
}

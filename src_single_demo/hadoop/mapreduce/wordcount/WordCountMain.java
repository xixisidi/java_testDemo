package hadoop.mapreduce.wordcount;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class WordCountMain {

	public static void main(String[] args) throws Exception {
	    String input = "/user/zhangxz/in";
        String output = "/user/zhangxz/out1";
		
		Job job = new Job();
		job.setJarByClass(WordCountMain.class);
		//指定任务名称
		job.setJobName("Word Count");
		
		//指定输入和输出路径
		FileInputFormat.setInputPaths(job, new Path(input));
		FileOutputFormat.setOutputPath(job, new Path(output));
		
		//指定job的Mapper类
		job.setMapperClass(WordMapper.class);
		job.setReducerClass(SumReducer.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		boolean success = job.waitForCompletion(true);
		System.out.println(success ? 0 : 1);
	}
}

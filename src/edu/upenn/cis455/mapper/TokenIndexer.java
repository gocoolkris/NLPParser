package edu.upenn.cis455.mapper;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

import edu.upenn.cis455.global.GlobalData;
import edu.upenn.cis455.preprocessor.RawContentExtractor;
import edu.upenn.cis455.preprocessor.SegmentedRawContent;
import edu.upenn.cis455.preprocessor.StopWordsLoader;
import edu.upenn.cis455.preprocessor.TokenizedRawContent;
import edu.upenn.cis455.tokenizer.ContentTokenizer;

public class TokenIndexer {

	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
		//		private final static IntWritable one = new IntWritable(1);
		//		private Text word = new Text();

		public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
			if(value != null){
				String line = value.toString();
				//	        StringTokenizer tokenizer = new StringTokenizer(line);
				//	        while (tokenizer.hasMoreTokens()) {
				//	          word.set(tokenizer.nextToken());
				//	          output.collect(word, one);
				//	        }
				if(line.trim().isEmpty()) return;
				System.out.println("Entering Rawcontent Extractor");
				System.out.println("The input is:" + line);
				RawContentExtractor extractor = new RawContentExtractor(line);
				SegmentedRawContent src = extractor.extract();
				System.out.println("Mapper : Extracted Segmented Content");
				ContentTokenizer ct = new ContentTokenizer(src); 
				ct.tokenizeSegmentedRawContent();
				ct.print();
				TokenizedRawContent trc = ct.getTokenizedRawContent();
				System.out.println("Mapper: Tokenized the Segmented Content");
				System.out.println("Started to Transform");
				TokenTransformer tt = new TokenTransformer(trc);
				System.out.println("Mapper : Transforming into amicable format for Reducer");
				tt.serializeTokens();
				System.out.println("printing all tokens..");
				tt.print();
				System.out.println("Mapper : finished Transforming.");
				HashMap<String,TokenDocument> tokenDocumentMap = tt.getTokenDocumentMapping();
				for(String token : tokenDocumentMap.keySet()){
					Text tokenkey = new Text();
					Text tokenvalue = new Text();
					System.out.println("Mapper: emitting key" + token);
					tokenkey.set(token);
					tokenvalue.set(tokenDocumentMap.get(token).getTokenDocument());
					output.collect(tokenkey, tokenvalue);
				}
				System.out.println("Mapper: done...going to Reducer");

			}
		}
	}

	public static class Reduce extends MapReduceBase implements Reducer<Text, Text, Text, Text> {
		public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
			//			int sum = 0;
			//			while (values.hasNext()) {
			//				sum += values.next().get();
			//			}
			//			output.collect(key, new IntWritable(sum));
			Text collector = new Text();
			StringBuffer document = new StringBuffer();
			document.append("<documents>");
			while(values.hasNext()){
				document.append("<doc>" + values.next().toString() + "</doc>");
			}
			document.append("</documents>");
			System.out.println("Reducer output :" + key);
			collector.set(document.toString());
			output.collect(key, collector);
			System.out.println("finished reducing..");
		}
	}

	public static void main(String[] args) throws Exception {
//		StopWordsLoader.loadDocumentStopWords();
//		StopWordsLoader.loadDomainFilterWords();
//		StopWordsLoader.loadPunctuationList();
		for(String token : GlobalData.documentStopWords){
			System.out.print(token+",");
		}
		System.out.println();
		for(String tok : GlobalData.domainFilterWords){
			System.out.print(tok + ",");
		}
		System.out.println();
		JobConf conf = new JobConf(TokenIndexer.class);
		conf.setJobName("Indexer");
		//conf.set("mapred.job.tracker", "file:///");
		//conf.set("fs.default.name", "file:///");
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);

		conf.setMapperClass(Map.class);
		//conf.setCombinerClass(Reduce.class);
		conf.setReducerClass(Reduce.class);

		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);

		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));

		JobClient.runJob(conf);
	}
}
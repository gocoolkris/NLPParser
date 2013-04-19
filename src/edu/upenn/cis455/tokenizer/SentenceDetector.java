package edu.upenn.cis455.tokenizer;

import java.io.FileInputStream;
import java.io.InputStream;


import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

public class SentenceDetector {

	String rawcontent;
	String[] sentences;
	
	public SentenceDetector(String content){
		this.rawcontent = content;
	}
	
	public void tokenizeSentences(){
		try{
			InputStream is = new FileInputStream("en-sent.bin");
			SentenceModel model = new SentenceModel(is);
			SentenceDetectorME sentenceDetector = new SentenceDetectorME(model);
			sentences = sentenceDetector.sentDetect(rawcontent);
			is.close();
		}catch(Exception e){}
	}
	
	public void printSentences(){
		try{
			
		}catch(Exception e){}
	}
	
}

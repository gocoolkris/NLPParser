package edu.upenn.cis455.preprocessor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import edu.upenn.cis455.global.GlobalData;

public class StopWordsLoader {

	
	public static void loadDocumentStopWords(){
		try{
			InputStream is = StopWordsLoader.class.getResourceAsStream("/stopwords");
			InputStreamReader irs = new InputStreamReader(is);
			BufferedReader reader = new BufferedReader(irs);
			String word = "";
			while((word = reader.readLine())!= null){
				if(!word.trim().isEmpty()){
					GlobalData.documentStopWords.add(word.trim());
				}
			}
			reader.close();
		}catch(Exception e){}
	}
	
	public static void loadDomainFilterWords(){
		try{
			InputStream is = StopWordsLoader.class.getResourceAsStream("/domainstopwords");
			InputStreamReader irs = new InputStreamReader(is);
			BufferedReader reader = new BufferedReader(irs);
			String word = "";
			while((word = reader.readLine())!= null){
				if(!word.trim().isEmpty()){
					GlobalData.domainFilterWords.add(word.trim());
				}
			}
			reader.close();
		}catch(Exception e){}
	}
	
	public static void loadPunctuationList(){
		try{
			InputStream is = StopWordsLoader.class.getResourceAsStream("/punctuationlist");
			InputStreamReader irs = new InputStreamReader(is);
			BufferedReader reader = new BufferedReader(irs);
			String word = "";
			while((word = reader.readLine())!= null){
				if(!word.trim().isEmpty()){
					GlobalData.punctuationFilterWords.add(word.trim());
				}
			}
			reader.close();
		}catch(Exception e){}
	}
}

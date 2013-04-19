package test.edu.upenn.cis455;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import org.junit.Test;

import edu.upenn.cis455.mapper.TokenDocument;
import edu.upenn.cis455.mapper.TokenTransformer;
import edu.upenn.cis455.preprocessor.RawContentExtractor;
import edu.upenn.cis455.preprocessor.SegmentedRawContent;
import edu.upenn.cis455.preprocessor.StopWordsLoader;
import edu.upenn.cis455.preprocessor.TokenizedRawContent;
import edu.upenn.cis455.tokenizer.ContentTokenizer;

public class RawContentExtractorTest {

	@Test
	public void test() {
	try{
		FileReader freader = new FileReader("sampledocument");
		BufferedReader reader = new BufferedReader(freader);
		StringBuffer strbuf = new StringBuffer();
		String line = "";
		while((line = reader.readLine())!= null){
			strbuf.append(line);
		}
		StopWordsLoader.loadDocumentStopWords();
		StopWordsLoader.loadDomainFilterWords();
		StopWordsLoader.loadPunctuationList();
		RawContentExtractor e = new RawContentExtractor(strbuf.toString());
		SegmentedRawContent sc = e.extract();
		e.print();
		ContentTokenizer ct = new ContentTokenizer(sc);
		ct.tokenizeSegmentedRawContent();
		ct.print();
		TokenizedRawContent trc = ct.getTokenizedRawContent();
		TokenTransformer tt = new TokenTransformer(trc);
		tt.serializeTokens();
		System.out.println("Entering TokenTransformer");
		tt.print();
		HashMap<String, TokenDocument> mapping = tt.getTokenDocumentMapping();
		for(String key : mapping.keySet()){
			System.out.println(key + ":" + mapping.get(key).getTokenDocument());
		}
//		ct.print();
//		reader.close();
	}catch(Exception e){}
	}
}

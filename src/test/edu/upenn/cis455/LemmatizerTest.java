package test.edu.upenn.cis455;

import java.util.List;

import org.junit.Test;

import edu.upenn.cis455.lemmatizer.Lemmatizer;

public class LemmatizerTest {

	@Test
	public void test() {
		Lemmatizer l = new Lemmatizer();
		List<String> strings = l.lemmatize("IS THIS crying YOUR yours ours their this than that i and the of or");
		for(String s : strings){
			System.out.println(s);
		}
	}

}

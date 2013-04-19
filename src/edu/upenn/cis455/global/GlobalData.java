package edu.upenn.cis455.global;

import java.util.HashSet;

public class GlobalData {

	//words that needs to be filtered while indexing
	public static HashSet<String> documentStopWords = new HashSet<String>();
	//words that needs to be filtered while indexing the url
	public static HashSet<String> domainFilterWords = new HashSet<String>();
	//words that needs to be filtered while indexing(puntuation marks)
	public static HashSet<String> punctuationFilterWords = new HashSet<String>();
	
}

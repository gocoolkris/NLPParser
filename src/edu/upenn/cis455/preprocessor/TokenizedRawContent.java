package edu.upenn.cis455.preprocessor;

import java.util.ArrayList;

public class TokenizedRawContent {

	private static long documentlength;
	private boolean isDocumentlengthSet = false;
	private String url;

	
	
	public String getUrl(){
		return url;
	}
	
	
	ArrayList<Token> titleTokens;
	public ArrayList<Token> getTitleTokens() {
		return titleTokens;
	}


	public void setTitleTokens(ArrayList<Token> titleTokens) {
		this.titleTokens = titleTokens;
	}


	public ArrayList<Token> getHeaderMetaDataTokens() {
		return headerMetaDataTokens;
	}


	public void setHeaderMetaDataTokens(ArrayList<Token> headerMetaDataTokens) {
		this.headerMetaDataTokens = headerMetaDataTokens;
	}


	public ArrayList<Token> getSectionHeaderTokens() {
		return sectionHeaderTokens;
	}


	public void setSectionHeaderTokens(ArrayList<Token> sectionHeaderTokens) {
		this.sectionHeaderTokens = sectionHeaderTokens;
	}


	public ArrayList<Token> getHighlightedTextTokens() {
		return highlightedTextTokens;
	}


	public void setHighlightedTextTokens(ArrayList<Token> highlightedTextTokens) {
		this.highlightedTextTokens = highlightedTextTokens;
	}


	public ArrayList<Token> getRemainingTextTokens() {
		return remainingTextTokens;
	}


	public void setRemainingTextTokens(ArrayList<Token> remainingTextTokens) {
		this.remainingTextTokens = remainingTextTokens;
	}


	ArrayList<Token> headerMetaDataTokens;
	ArrayList<Token> sectionHeaderTokens;
	ArrayList<Token> highlightedTextTokens;
	ArrayList<Token> remainingTextTokens;
	
	
	public long getDocumentLength(){
		if(!isDocumentlengthSet){
			documentlength = titleTokens.size() + headerMetaDataTokens.size() + 
					sectionHeaderTokens.size() + highlightedTextTokens.size() +
					remainingTextTokens.size();
			isDocumentlengthSet = true;
		}
		return documentlength;
	}
	
	public TokenizedRawContent(String url){this.url = url;}
}

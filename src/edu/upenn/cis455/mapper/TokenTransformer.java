package edu.upenn.cis455.mapper;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import edu.upenn.cis455.global.GlobalData;
import edu.upenn.cis455.preprocessor.Token;
import edu.upenn.cis455.preprocessor.TokenizedRawContent;

public class TokenTransformer {

	private TokenizedRawContent trc;
	private HashSet<String> allTokens;
	private HashSet<String> urlTokens;
	private HashMap<String, TokenDocument> tokenDocumentMapping;
	private static HashSet<String> domainFilterWords = new HashSet<String>(Arrays.asList(new String[]{
			"www","biz","cat","com","coop","info","int","jobs","mobi","museum",
			"name","net","org","post","pro","tel","xxx","travel","edu","gov",
			"mil"}));
	
	
	public TokenTransformer(TokenizedRawContent trc){
		this.trc = trc;
		allTokens = new HashSet<String>();
		urlTokens = new HashSet<String>();
		tokenDocumentMapping = new HashMap<String,TokenDocument>();
		loadUrlTokens();
		extractAllTokens();
	}
	
	private void loadUrlTokens(){
		try{
			System.out.println("Got Url:" + trc.getUrl());
			URL durl = new URL(trc.getUrl());
			String[] domain = durl.getHost().split("\\.");
			for(String subdomain : domain){
				if(!domainFilterWords.contains(subdomain.trim())){
					urlTokens.add(subdomain);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	private void extractAllTokens(){
		System.out.println("TokenTransformer..starting to ");
		allTokens.addAll(urlTokens);
		addTokens(trc.getTitleTokens());
		addTokens(trc.getHeaderMetaDataTokens());
		addTokens(trc.getSectionHeaderTokens());
		addTokens(trc.getHighlightedTextTokens());
		addTokens(trc.getRemainingTextTokens());
	}
	
	private void addTokens(ArrayList<Token> tokens){
		try{
			if(tokens == null || tokens.size() == 0) return;
			for(Token token : tokens){
				allTokens.add(token.getToken());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void serializeTokens(){
		try{
			for(String token : allTokens){
				constructTokenDocument(token);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void constructTokenDocument(String token){
		try{
			TokenDocument td = new TokenDocument(trc.getUrl());
			td.numOfTimesInUrl = Collections.frequency(urlTokens, token);
			if(trc.getTitleTokens()!= null)
				td.numOfTimesInTitle = Collections.frequency(trc.getTitleTokens(), token);
			if(trc.getHeaderMetaDataTokens()!=null && trc.getSectionHeaderTokens()!=null){
				td.numOfTimesInHeader = Collections.frequency(trc.getSectionHeaderTokens(), token) + 
				Collections.frequency(trc.getHeaderMetaDataTokens(), token);
			}
			else if(trc.getSectionHeaderTokens()!=null){
				td.numOfTimesInHeader = Collections.frequency(trc.getSectionHeaderTokens(), token);
			}
			else if(trc.getHeaderMetaDataTokens()!= null){
				td.numOfTimesInHeader = Collections.frequency(trc.getHeaderMetaDataTokens(), token);
			}
			if(trc.getHighlightedTextTokens()!=null){
			td.numOfTimesInhighlightedText = Collections.frequency(trc.getHighlightedTextTokens(), token);
			}
			if(trc.getRemainingTextTokens()!=null){
			td.numOfTimesInbodyText = Collections.frequency(trc.getRemainingTextTokens(), token);
			}
			td.setPositions(collectPositions(token));
			tokenDocumentMapping.put(token, td);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private ArrayList<Integer> collectPositions(String token){
		try{
			ArrayList<Integer> positionlist = new ArrayList<Integer>();
			for(Token t : trc.getTitleTokens()){
				if(t.getToken().trim().equals(token)){
					positionlist.add(t.getPosition());
				}
			}
			for(Token t : trc.getHeaderMetaDataTokens()){
				if(t.getToken().trim().equals(token)){
					positionlist.add(t.getPosition());
				}
			}
			for(Token t : trc.getSectionHeaderTokens()){
				if(t.getToken().trim().equals(token)){
					positionlist.add(t.getPosition());
				}
			}
			for(Token t : trc.getHighlightedTextTokens()){
				if(t.getToken().trim().equals(token)){
					positionlist.add(t.getPosition());
				}
			}
			for(Token t : trc.getRemainingTextTokens()){
				if(t.getToken().trim().equals(token)){
					positionlist.add(t.getPosition());
				}
			}
			return positionlist;
		}catch(Exception e){}
		return null;
	}
	public HashMap<String,TokenDocument> getTokenDocumentMapping(){
		return this.tokenDocumentMapping;
	}
	
	public void print(){
		print(allTokens);
	}
	
	private void print(HashSet<String> tokens){
		for(String s : tokens){
			System.out.print(s + " ");
		}
		System.out.println();
	}
}

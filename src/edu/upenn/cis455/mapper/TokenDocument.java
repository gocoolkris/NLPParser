package edu.upenn.cis455.mapper;

import java.util.ArrayList;

public class TokenDocument {

	String url;
	int numOfTimesInUrl;
	int numOfTimesInTitle;
	int numOfTimesInHeader;
	int numOfTimesInhighlightedText;
	int numOfTimesInbodyText;
	int numOfTokens;
	ArrayList<Integer> positions;
	public ArrayList<Integer> getPositions() {
		return positions;
	}

	public void setPositions(ArrayList<Integer> positions) {
		this.positions = positions;
	}

	float cosinesimilarity;
	int tf;
	StringBuffer tokenDocAsString;
	
	public TokenDocument(String url){
		tokenDocAsString = new StringBuffer();
		this.url = url;
	}
	
	public void setnumOfTimesOccursInBody(int times){
		this.numOfTimesInbodyText = times;
	}
	
	public void setnumOfTimesOccursInHighlightedText(int times){
		this.numOfTimesInhighlightedText = times;
	}
	
	public void setnumOfTimesOccursInUrl(int times){
		this.numOfTimesInUrl = times;
	}
	public void setnumOfTimesOccursInTitle(int times){
		this.numOfTimesInTitle = times;
	}
	public void setnumOfTimesOccursInHeader(int times){
		this.numOfTimesInHeader = times;
	}
	
	public String getTokenDocument(){
		try{
			tokenDocAsString.append("<url>" + url +"</url>");
			tokenDocAsString.append("<positions>"+ positionList() + "</positions>");
			tokenDocAsString.append("<urlOccur>"+ numOfTimesInUrl + "</urlOccur");
			tokenDocAsString.append("<titleOccur>"+ numOfTimesInTitle +"</titleOccur>");
			tokenDocAsString.append("<headerOccur>" +numOfTimesInHeader +"</headerOccur>");
			tokenDocAsString.append("<highlightedOccur>"+ numOfTimesInhighlightedText + "</highlightedOccur>");
			tokenDocAsString.append("<bodyOccur>" + numOfTimesInbodyText + "</bodyOccur>");
			tokenDocAsString.append("<cossim>" +cosinesimilarity +"</cossim>");
			return tokenDocAsString.toString();
		}catch(Exception e){}
		return null;
	}
	
	private String positionList(){
		if(this.positions == null) return "";
		StringBuffer str = new StringBuffer();
		for(int i = 0; i < positions.size(); ++i){
			if(positions.get(i)!= null)
				str.append(positions.get(i) + ",");
		}
		String res = str.toString();
		if(res.length() > 0)
			return res.substring(0, res.lastIndexOf(','));
		else return "";
	}
	
}

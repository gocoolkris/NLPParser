package edu.upenn.cis455.preprocessor;


public class Token{
	String tokenname;
	int position;
	
	public Token(String tokenname,int position){
		this.tokenname = tokenname;
		this.position = position;
	}
	public String getToken(){
		return tokenname;
	}
	
	public int getPosition(){
		return position;
	}
}


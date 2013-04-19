package edu.upenn.cis455.mapper;

public enum TokenPositionWeight {

	bodyText(1), highlightedSection(3), sectionheader(5),
	title(7), url(8);
	
	private int weight;
	TokenPositionWeight(int wt){
		this.weight = wt;
	}
	
	public int getWeight(){return weight;}
	
}

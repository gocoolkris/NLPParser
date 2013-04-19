package edu.upenn.cis455.preprocessor;

public class SegmentedRawContent {
	String url="";
	String title="";
	String headerMetadata="";
	StringBuffer sectionHeaders;
	StringBuffer highlightText;
	StringBuffer remainingText;

	public SegmentedRawContent(String url){
		this.url = url;
		sectionHeaders = new StringBuffer();
		highlightText = new StringBuffer();
		remainingText = new StringBuffer();
	}

	
	public String getUrl(){
		return url;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHeaderMetadata() {
		return headerMetadata;
	}

	public void setHeaderMetadata(String headerMetadata) {
		this.headerMetadata = headerMetadata;
	}

	public StringBuffer getSectionHeaders() {
		return sectionHeaders;
	}

	public void setSectionHeaders(StringBuffer sectionHeaders) {
		this.sectionHeaders = sectionHeaders;
	}

	public StringBuffer getHighlightText() {
		return highlightText;
	}

	public void setHighlightText(StringBuffer highlightText) {
		this.highlightText = highlightText;
	}

	public StringBuffer getRemainingText() {
		return remainingText;
	}

	public void setRemainingText(StringBuffer remainingText) {
		this.remainingText = remainingText;
	}


}

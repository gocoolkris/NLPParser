package edu.upenn.cis455.preprocessor;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RawContentExtractor {

	SegmentedRawContent document;
	String url;
	String contents;
	Document doc;

	public RawContentExtractor(String contents){
		parseUrl(contents);
		document = new SegmentedRawContent(url);
		doc = Jsoup.parse(this.contents);
	}

	private void parseUrl(String contents) {
		try{
			int startIndex = contents.indexOf("<url>") + 5;
			int endIndex = contents.indexOf("</url>");
			url = contents.substring(startIndex, endIndex);
			this.contents = contents.substring(endIndex+6);
			System.out.println("parsed url is :" + url);
			System.out.println("contents:" + this.contents);
		}catch(Exception e){}

	}

	private void extractHeaderData(){
		try{

			document.title = doc.title().toLowerCase();
			System.out.println("document title is : " + document.title);
			//Elements heads= doc.getElementsByTag("head");
			Element headElement = doc.head();
			
			//if(e.nodeName().equals("title")) document.title = e.text();
			for(Element child : headElement.children()){
				if(child.nodeName().equals("meta")){
					Element attr = child.attr("name", "Keywords");
					if(attr != null){
						document.headerMetadata += child.attr("content").toLowerCase();
					}
					attr = child.attr("name","Description");
					if(attr != null){
						document.headerMetadata += child.attr("content").toLowerCase();
					}
				}
			}
		}catch(Exception e){}
	}


	public SegmentedRawContent extract(){
		System.out.println("extracting data from html..");
		extractHeaderData();
		extractBodyData();
		System.out.println("Finished Extracting data");
		System.out.println("document headermetadata is: " + document.headerMetadata);
		System.out.println("document section header is:" + document.sectionHeaders);
		System.out.println("document hightlightedText is:" + document.highlightText);
		System.out.println("document body text is : " + document.remainingText);
		return document;
	}

	private void extractBodyData() {
		try{
			Element body = doc.body();
			System.out.println(body.ownText());
			this.extractHighlightedText(body);
			this.extractSectionHeaders(body);
			this.extractBodyText(body);
		}catch(Exception e){}

	}
	
	private void extractHighlightedText(Element body){
		try{
			Elements hText = body.getElementsByTag("a");
			for(Element h : hText){
				document.highlightText.append(h.text().toLowerCase());
			}
			hText = body.getElementsByTag("i");
			for(Element h : hText){
				document.highlightText.append(h.text().toLowerCase());
			}
			hText = body.getElementsByTag("b");
			for(Element h : hText){
				document.highlightText.append(h.text().toLowerCase());
			}
		}catch(Exception e){}
	}
	
	private void extractSectionHeaders(Element body){
		try{
			Elements sectionheaders = body.getElementsByTag("h1");
			for(Element h : sectionheaders){
				document.sectionHeaders.append(h.text().toLowerCase());
			}
			sectionheaders = body.getElementsByTag("h2");
			for(Element h : sectionheaders){
				document.sectionHeaders.append(h.text().toLowerCase());
			}
			sectionheaders = body.getElementsByTag("h3");
			for(Element h : sectionheaders){
				document.sectionHeaders.append(h.text().toLowerCase());
			}
			sectionheaders = body.getElementsByTag("h4");
			for(Element h : sectionheaders){
				document.sectionHeaders.append(h.text().toLowerCase());
			}
			sectionheaders = body.getElementsByTag("h5");
			for(Element h : sectionheaders){
				document.sectionHeaders.append(h.text().toLowerCase());
			}

			sectionheaders = body.getElementsByTag("h6");
			for(Element h : sectionheaders){
				document.sectionHeaders.append(h.text().toLowerCase());
			}
		}catch(Exception e){}
	}

	private void extractBodyText(Element body){
		try{
			Elements elements = body.children();
			for(Element e : elements){
				recursivelyParseData(e);
			}
		}catch(Exception e){}
	}

	private void recursivelyParseData(Element element){
		if(element == null) return;
		if(!isSectionHeader(element) && !isHighlightedElement(element)) {
			if(element.ownText()!= null) document.remainingText.append(element.ownText().toLowerCase());
			if(element.children()!=null){
				for(Element ee : element.children())
					if(ee != null){
					recursivelyParseData(ee);
				}
			}
		}
	}


	private boolean isSectionHeader(Element element){
		try{
			if(element == null) return false;
			if(element.nodeName().equalsIgnoreCase("h1") ||element.nodeName().equalsIgnoreCase("h2")
					|| element.nodeName().equalsIgnoreCase("h3") || 
					element.nodeName().equalsIgnoreCase("h4") ||
					element.nodeName().equalsIgnoreCase("h5") ||
					element.nodeName().equalsIgnoreCase("h6") ){
				return true;
			}
		}catch(Exception e){}
		return false;
	}

	private boolean isHighlightedElement(Element element){
		try{
			if(element.nodeName().equals("b") || element.nodeName().equals("i")||
					element.nodeName().equals("a"))
				return true;
		}catch(Exception e){}
		return false;
	}



	public void print(){
		System.out.println("Title :" + document.title);
		System.out.println("HeaderMetaData :" + document.headerMetadata);
		System.out.println("Section Headers : " + document.sectionHeaders);
		System.out.println("\nHightlighted Text : " + document.highlightText);
		System.out.println("Body Text : " + document.remainingText);
	}


}

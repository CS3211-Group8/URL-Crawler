package UrlCrawlerP3;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Url {
	private String url,linkedUrl,domain;
	public static Url urlObj = new Url();
	
	public Url(String crawledUrl, String prevUrl) {
		this.url = crawledUrl;
		this.linkedUrl = prevUrl;
		setDomain();
	}
	
	public Url() {
		
	}
	
	
   public String getUrl() {
	   return this.url; 
   }
   
   public String getLinkedUrl() {
	   return this.linkedUrl; 
   }
   
   public String getDomain() {
	   return this.domain;
   }
   
   
   /*
    * Given url, returns the http response code
	*/
	public int getResponseCode(String url) {
		   
	   int responseCode = 0;
	   CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
		
		try {
			URL urlObj = new URL(url);
			HttpURLConnection huc = (HttpURLConnection) urlObj.openConnection();
			huc.setRequestMethod("HEAD");
			responseCode = huc.getResponseCode();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.err.println("Malformed Url err For url: " + url + ",");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("For url: " + url + ",");
			e.printStackTrace();
		} catch (ClassCastException e) {
			System.err.println("Class Cast err For url: " + url + ",");
			e.printStackTrace();
		}
		
		return responseCode;
	}
	
	
	/*
	 * Given url, return the root url
	 */
	public String getRootUrl(String url) {
			
		String rootUrl = url;
		url = url.replace(" ", "_");
		url = url.replace("{", "(");
		url = url.replace("}", ")");
		url = url.replace("[", "(");
		url = url.replace("]", ")");
		url = url.replace("%", "/");
		url = url.replace("|", "-");
		url = url.replace("#", "-");
		url = url.replace("`", "-");
		url = url.replace("^", "-");
		url = url.replace("<", "-");
		url = url.replace(">", "-");
		
		try {
			URI uri = new URI(url);
			String domain = uri.getHost();
			rootUrl = "https://" + domain + "/";
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rootUrl;
	}
   
  
	@Override
	public boolean equals(Object obj) {
		
		boolean match = false;
		Url url = (Url)obj;
		
		match = ( ((this.getUrl()).equalsIgnoreCase(url.getUrl()))  &&  ((this.getLinkedUrl()).equalsIgnoreCase(url.getLinkedUrl())) );
		
		return match;
	}
	
	public void setDomain() {
			
		String url = this.getUrl();
		String domainVal = "";
		url = url.replace(" ", "_");
		url = url.replace("{", "(");
		url = url.replace("}", ")");
		url = url.replace("[", "(");
		url = url.replace("]", ")");
		url = url.replace("%", "/");
		url = url.replace("|", "-");
		url = url.replace("#", "-");
		url = url.replace("`", "-");
		url = url.replace("^", "-");
		url = url.replace("<", "-");
		url = url.replace(">", "-");
		
		try {
			URI uri = new URI(url);
			domainVal = uri.getHost();														// www.google.com
			domainVal = domainVal.startsWith("www.") ? domainVal.substring(4) : domainVal;	// google.com
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			System.err.println("For url: " + url + ",");
			e.printStackTrace();
		}
		
		this.domain = domainVal;
	}
	
	
	public String getHtml() {
		String url = this.getUrl();
		String html = "";
		
		
		try {
			Document doc = Jsoup.connect(url)
					.ignoreContentType(true)
					.ignoreHttpErrors(true)
					.get();
			html = doc.html();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("For url: " + url + ",");
			e.printStackTrace();
		}
		
		return html;
	}
	
	/*
	 * Given two strings, return boolean value
	 * indicating if they differ by a '/' character at the end
	 */
	public boolean urlMatch(String url, String toCompare) {
		
		boolean match = false;
		
		String addedUrl = url + "/";
		String addedCompare = toCompare + "/";
		
		if((url.equals(addedCompare)) || (toCompare.equals(addedUrl))){
			match = true;
		}
		
		return match;
	}
	
	/*
	 * Given url, execute crawling of url.
	 */
	public Elements getCrawled(String url) {
		
		Elements linkedUrls = new Elements();
		
		Document doc;
		try {
			doc = Jsoup.connect(url).ignoreHttpErrors(true).get();
			linkedUrls = doc.select("a[href]");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		return linkedUrls;
	}
	
	
	/*
	 * Given url, returns boolean if it is able to be crawled
	 */
	public boolean isCrawlable(String url) {
		boolean crawlable = false;
		
		Elements linkedUrls = Url.urlObj.getCrawled(url);
		crawlable = linkedUrls.size() == 0 ? false : true;
		
		if(!crawlable) {
			url = Url.urlObj.getRootUrl(url);
			linkedUrls = Url.urlObj.getCrawled(url);
			crawlable = linkedUrls.size() == 0 ? false : true;
		}		
		
		return crawlable;		
	}
	
}
	
	

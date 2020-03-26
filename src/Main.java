package UrlCrawler;
import java.util.*;
import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	
	public static void main(String[] args) {
		
		Crawler.obj = new Object();
		
		List<Url> bul1 = Collections.synchronizedList(new LinkedList<Url>());
		List<Url> bul2 = Collections.synchronizedList(new LinkedList<Url>());
		List<Url> bul3 = Collections.synchronizedList(new LinkedList<Url>());
		
		// Initialize urlList with seed urls
		Crawler.urlList.add("https://www.baidu.com");
		Crawler.urlList.add("https://www.google.com");
		Crawler.urlList.add("https://github.com");
		Crawler.urlList.add("www.wikipedia.org");
		
		CrawlingThread ct1 = new CrawlingThread(bul1);
		IndexBuildingThread ibt1 = new IndexBuildingThread(bul1);

		Thread ct = new Thread(ct1,"CT1");
		Thread ibt = new Thread(ibt1,"IBT1");
		
		ct.start();
		ibt.start();
		
		
		
	}
	
	


}

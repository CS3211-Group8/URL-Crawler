package UrlCrawler;

import java.util.*;

public class Main {
	
	public static void main(String[] args) {
		
		Crawler.obj = new Object();
		Scanner sc = new Scanner(System.in);
		
		List<Url> bul1 = Collections.synchronizedList(new LinkedList<Url>());
//		List<Url> bul2 = Collections.synchronizedList(new LinkedList<Url>());
//		List<Url> bul3 = Collections.synchronizedList(new LinkedList<Url>());
		
		// Initialize urlList with seed urls
		while(sc.hasNextLine()){
			Crawler.urlList.add(sc.nextLine());
		}
		sc.close();
		
		CrawlingThread ct1 = new CrawlingThread(bul1);
		IndexBuildingThread ibt1 = new IndexBuildingThread(bul1);

		Thread ct = new Thread(ct1,"CT1");
		Thread ibt = new Thread(ibt1,"IBT1");
		
		ct.start();
		ibt.start();
		
		
	}
	
	


}

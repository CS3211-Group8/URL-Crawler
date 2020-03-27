package UrlCrawler;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
	
	public static void main(String[] args) {
		
		Crawler.obj = new Object();
		System.out.println("start time: " + Crawler.prevTime);
		Scanner sc = new Scanner(System.in);
		
		BlockingQueue<Url> bul1 = new LinkedBlockingQueue<Url>(1000);
		BlockingQueue<Url> bul2 = new LinkedBlockingQueue<Url>(1000);
		BlockingQueue<Url> bul3 = new LinkedBlockingQueue<Url>(1000);

		
		
		// Initialize urlList with seed urls
		while(sc.hasNextLine()){
			Crawler.urlList.add(sc.nextLine());
		}
		sc.close();
		
		CrawlingThread ct1 = new CrawlingThread(bul1);
		CrawlingThread ct2 = new CrawlingThread(bul1);
		IndexBuildingThread ibt1 = new IndexBuildingThread(bul1);
		
		CrawlingThread ct3 = new CrawlingThread(bul2);
		CrawlingThread ct4 = new CrawlingThread(bul2);
		IndexBuildingThread ibt2 = new IndexBuildingThread(bul2);

		CrawlingThread ct5 = new CrawlingThread(bul3);
		CrawlingThread ct6 = new CrawlingThread(bul3);
		IndexBuildingThread ibt3 = new IndexBuildingThread(bul3);


		Thread tct1 = new Thread(ct1,"CT1");
		Thread tct2 = new Thread(ct2,"CT2");
		Thread tibt1 = new Thread(ibt1,"IBT1");
		Thread tct3 = new Thread(ct3,"CT3");
		Thread tct4 = new Thread(ct4,"CT4");
		Thread tibt2 = new Thread(ibt2,"IBT2");
		Thread tct5 = new Thread(ct5,"CT5");
		Thread tct6 = new Thread(ct6,"CT6");
		Thread tibt3 = new Thread(ibt3,"IBT3");
		
		
		tct1.start();
		tct2.start();
		tibt1.start();
		tct3.start();
		tct4.start();
		tibt2.start();
		tct5.start();
		tct6.start();
		tibt3.start();
		
		
	}
	
	


}

package UrlCrawler;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
	
	public static void main(String[] args) {
		
		@SuppressWarnings("unused")
		IndexedUrlTree iut = new IndexedUrlTree();
		
		
		Scanner sc = new Scanner(System.in);
		
		BlockingQueue<Url> bul1 = new LinkedBlockingQueue<Url>(100);
		BlockingQueue<Url> bul2 = new LinkedBlockingQueue<Url>(100);
		BlockingQueue<Url> bul3 = new LinkedBlockingQueue<Url>(100);

		
		
		// Initialize urlList with seed urls
		while(sc.hasNextLine()){
			String currentUrl = sc.nextLine();
			Crawler.urlSet.add(currentUrl);
		}
		sc.close();
		
		
		CrawlingThread ct1 = new CrawlingThread(bul1);
		CrawlingThread ct2 = new CrawlingThread(bul1);
		CrawlingThread ct3 = new CrawlingThread(bul1);
		IndexBuildingThread ibt1 = new IndexBuildingThread(bul1);
		
		CrawlingThread ct4 = new CrawlingThread(bul2);
		CrawlingThread ct5 = new CrawlingThread(bul2);
		CrawlingThread ct6 = new CrawlingThread(bul2);
		IndexBuildingThread ibt2 = new IndexBuildingThread(bul2);
		
		CrawlingThread ct7 = new CrawlingThread(bul3);
		CrawlingThread ct8 = new CrawlingThread(bul3);
		CrawlingThread ct9 = new CrawlingThread(bul3);
		IndexBuildingThread ibt3 = new IndexBuildingThread(bul3);
		

//		CrawlingThread ct1 = new CrawlingThread(bul1);
//		CrawlingThread ct2 = new CrawlingThread(bul1);
//		IndexBuildingThread ibt1 = new IndexBuildingThread(bul1);
		
//		CrawlingThread ct3 = new CrawlingThread(bul2);
//		CrawlingThread ct4 = new CrawlingThread(bul2);
//		IndexBuildingThread ibt2 = new IndexBuildingThread(bul2);
//
//		CrawlingThread ct5 = new CrawlingThread(bul3);
//		CrawlingThread ct6 = new CrawlingThread(bul3);
//		IndexBuildingThread ibt3 = new IndexBuildingThread(bul3);
		
		
		Thread tct1 = new Thread(ct1,"CT1");
		Thread tct2 = new Thread(ct2,"CT2");
		Thread tct3 = new Thread(ct3,"CT3");
		Thread tibt1 = new Thread(ibt1,"IBT1");
		Thread tibt4 = new Thread(ibt1,"IBT1");
		Thread tct4 = new Thread(ct4,"CT4");
		Thread tct5 = new Thread(ct5,"CT5");
		Thread tct6 = new Thread(ct6,"CT6");
		Thread tibt2 = new Thread(ibt2,"IBT2");
		Thread tibt5 = new Thread(ibt2,"IBT2");
		Thread tct7 = new Thread(ct7,"CT7");
		Thread tct8 = new Thread(ct8,"CT8");
		Thread tct9 = new Thread(ct9,"CT9");
		Thread tibt3 = new Thread(ibt3,"IBT3");
		Thread tibt6 = new Thread(ibt3,"IBT3");
		
		
	
		
		
		tct1.start();
		tct2.start();
		tct3.start();
		tibt1.start();
		tibt4.start();
		tct4.start();
		tct5.start();
		tct6.start();
		tibt2.start();
		tibt5.start();
		tct7.start();
		tct8.start();
		tct9.start();
		tibt3.start();
		tibt6.start();
		
		
	}
	
	
}

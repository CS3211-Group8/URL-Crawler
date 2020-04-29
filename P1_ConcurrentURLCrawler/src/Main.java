package UrlCrawler;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
	
	public static void main(String[] args) {
		
		String input = "",output = "";
		int time = 0,storedPageNum = 0;
		
		for(int i=0;i<args.length;i+=2) {
			switch(args[i]) {
				case "-input":	input = args[i+1];
								break;
								
				case "-output": output = args[i+1];
								break;
								
				case "-time":	time = Integer.parseInt(args[i+1].substring(0,args[i+1].length()-1));
								break;
								
				case "-storedPageNum": 	storedPageNum = Integer.parseInt(args[i+1]);
										break;
										
				default:	System.out.println("Invalid argument option: " + args[i]);
							break;
			}
		}
		System.out.println("input: " + input);
		System.out.println("output: " + output);
		System.out.println("time: " + time);
		System.out.println("storedPageNum: " + storedPageNum);
		
		
		Crawler.crawlerObj.initCrawler(output, storedPageNum, time);
		
		// Initialize urlList with seed urls
		try {  
			FileInputStream fis=new FileInputStream(input);       
			Scanner sc=new Scanner(fis);    //file to be scanned  
			//returns true if there is another line to read  
			while(sc.hasNextLine())  
			{ 
				String currentUrl = sc.nextLine();
				Crawler.urlSet.add(currentUrl);
			}  
			sc.close();     //closes the scanner  
		}  
		catch(IOException e)  {  
			e.printStackTrace();  
		}  

		
		@SuppressWarnings("unused")
		IndexedUrlTree iut = new IndexedUrlTree();
		
		BlockingQueue<Url> bul1 = new LinkedBlockingQueue<Url>(Crawler.BUL_MAX);
		BlockingQueue<Url> bul2 = new LinkedBlockingQueue<Url>(Crawler.BUL_MAX);
		BlockingQueue<Url> bul3 = new LinkedBlockingQueue<Url>(Crawler.BUL_MAX);
		BlockingQueue<Url> bul4 = new LinkedBlockingQueue<Url>(Crawler.BUL_MAX);		
		
		CrawlingThread ct1 = new CrawlingThread(bul1);
		CrawlingThread ct2 = new CrawlingThread(bul1);
		CrawlingThread ct3 = new CrawlingThread(bul1);
		CrawlingThread ct4 = new CrawlingThread(bul1);
		IndexBuildingThread ibt1 = new IndexBuildingThread(bul1);
		
		
		CrawlingThread ct5 = new CrawlingThread(bul2);
		CrawlingThread ct6 = new CrawlingThread(bul2);
		CrawlingThread ct7 = new CrawlingThread(bul2);
		CrawlingThread ct8 = new CrawlingThread(bul2);
		IndexBuildingThread ibt2 = new IndexBuildingThread(bul2);
		
		CrawlingThread ct9 = new CrawlingThread(bul3);
		CrawlingThread ct10 = new CrawlingThread(bul3);
		CrawlingThread ct11 = new CrawlingThread(bul3);
		CrawlingThread ct12 = new CrawlingThread(bul3);
		IndexBuildingThread ibt3 = new IndexBuildingThread(bul3);
		
		CrawlingThread ct13 = new CrawlingThread(bul4);
		CrawlingThread ct14 = new CrawlingThread(bul4);
		CrawlingThread ct15 = new CrawlingThread(bul4);
		CrawlingThread ct16 = new CrawlingThread(bul4);
		IndexBuildingThread ibt4 = new IndexBuildingThread(bul4);
		

		
		Thread tct1 = new Thread(ct1,"CT1");
		Thread tct2 = new Thread(ct2,"CT2");
		Thread tct3 = new Thread(ct3,"CT3");
		Thread tct4 = new Thread(ct4,"CT4");
		Thread tibt1 = new Thread(ibt1,"IBT1");
		
		Thread tct5 = new Thread(ct5,"CT5");
		Thread tct6 = new Thread(ct6,"CT6");
		Thread tct7 = new Thread(ct7,"CT7");
		Thread tct8 = new Thread(ct8,"CT8");
		Thread tibt2 = new Thread(ibt2,"IBT2");
		
		Thread tct9 = new Thread(ct9,"CT9");
		Thread tct10 = new Thread(ct10,"CT10");
		Thread tct11 = new Thread(ct11,"CT11");
		Thread tct12 = new Thread(ct12,"CT12");
		Thread tibt3 = new Thread(ibt3,"IBT3");
		
		Thread tct13 = new Thread(ct13,"CT13");
		Thread tct14 = new Thread(ct14,"CT14");
		Thread tct15 = new Thread(ct15,"CT15");
		Thread tct16 = new Thread(ct16,"CT16");
		Thread tibt4 = new Thread(ibt4,"IBT4");
		
	
		
		
		tct1.start();
		tct2.start();
		tct3.start();
		tct4.start();
		tibt1.start();
		
		tct5.start();
		tct6.start();
		tct7.start();
		tct8.start();
		tibt2.start();
		
		tct9.start();
		tct10.start();
		tct11.start();
		tct12.start();
		tibt3.start();
		
		tct13.start();
		tct14.start();
		tct15.start();
		tct16.start();
		tibt4.start();
		
		
	}
	
	
}

package UrlCrawlerP3;

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
		
		CrawlingThread ct1 = new CrawlingThread(bul1);
		CrawlingThread ct2 = new CrawlingThread(bul1);
		IndexBuildingThread ibt1 = new IndexBuildingThread(bul1);
		
		CrawlingThread ct4 = new CrawlingThread(bul2);
		CrawlingThread ct5 = new CrawlingThread(bul2);
		IndexBuildingThread ibt2 = new IndexBuildingThread(bul2);
		
		
		Thread tct1 = new Thread(ct1,"CT1");
		Thread tct2 = new Thread(ct2,"CT2");
		Thread tibt1 = new Thread(ibt1,"IBT1");
		Thread tct4 = new Thread(ct4,"CT4");
		Thread tct5 = new Thread(ct5,"CT5");
		Thread tibt2 = new Thread(ibt2,"IBT2");
		
		
	
		
		
		tct1.start();
		tct2.start();
		tibt1.start();
		
		tct4.start();
		tct5.start();
		tibt2.start();
		
		
	}
	
	
}

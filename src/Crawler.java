import java.util.*;
import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
	private static List<String> urlList = Collections.synchronizedList(new LinkedList<String>());
	private static Set<Url> urlSet = new HashSet<Url>();
	
	public static void main(String[] args) {
		
		
		List<Url> bul1 = Collections.synchronizedList(new LinkedList<Url>());
		List<Url> bul2 = Collections.synchronizedList(new LinkedList<Url>());
		List<Url> bul3 = Collections.synchronizedList(new LinkedList<Url>());
		
		Crawler.CrawlingThread ct1 = new Crawler().new CrawlingThread(bul1);
		Crawler.CrawlingThread ct2 = new Crawler().new CrawlingThread(bul1);
		Crawler.CrawlingThread ct3 = new Crawler().new CrawlingThread(bul2);
		Crawler.CrawlingThread ct4 = new Crawler().new CrawlingThread(bul2);
		Crawler.CrawlingThread ct5 = new Crawler().new CrawlingThread(bul3);
		Crawler.CrawlingThread ct6 = new Crawler().new CrawlingThread(bul3);
		
		Crawler.IndexBuildingThread ibt1 = new Crawler().new IndexBuildingThread(bul1);
		Crawler.IndexBuildingThread ibt2 = new Crawler().new IndexBuildingThread(bul2);
		Crawler.IndexBuildingThread ibt3 = new Crawler().new IndexBuildingThread(bul3);

		// Initialize urlList with seed urls
		urlList.add("https://www.baidu.com");
		
		new Thread(ct1,"CT1").start();
		new Thread(ct2,"CT2").start();
//		new Thread(ct3,"CT3").start();
//		new Thread(ct4,"CT4").start();
//		new Thread(ct5,"CT5").start();
//		new Thread(ct6,"CT6").start();
		new Thread(ibt1,"IBT1").start();
//		new Thread(ibt2,"IBT2").start();
//		new Thread(ibt3,"IBT3").start(); 
		
		
		 
		
	}
	
	
	public class CrawlingThread implements Runnable{
		private List<Url> bul;
		
		public CrawlingThread(List<Url> bufferedList) {
			bul = bufferedList;
		}
		
		@Override
		public void run() {
			String url;
			
			// get first url from urlList; wait if list is empty;
			synchronized(this) {
				while(urlList.isEmpty()) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} 
				url = urlList.remove(0);
				System.out.println("removed url: " + url);
			}
			
			// Obtain and parse html; 
			try{
				Document html = Jsoup.connect(url).get();
				Elements linkedUrls = html.select("a[href]");
				
				for (Element page : linkedUrls) {
                    Url link = new Url(page.attr("abs:href"),url);
                    System.out.println("crawled url: " + link.getUrl());
                    synchronized(this) {
                    	bul.add(link);
                    	this.notify();
                    }
				}
			}
			catch(IOException e) {
				System.err.println("For '" + url + "': " + e.getMessage());
			}
		}
	}
	
	public class IndexBuildingThread implements Runnable{
		private List<Url> bul;
		
		public IndexBuildingThread(List<Url> bufferedList) {
			bul = bufferedList;
		}
		
		@Override
		public void run() {
			Url current;
			String currentUrl;
			// Get first Url object
			synchronized(this) {
				while(bul.isEmpty()) {

					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				current = bul.remove(0);

			}
			// check for duplicates; if no duplicates add to hashset and urlList
			if((current != null) && (!urlSet.contains(current))) {
				urlSet.add(current);
				currentUrl = current.getUrl();
				synchronized(this) {
					urlList.add(currentUrl);
					this.notify();
				}
			}
		}

	}
	


}

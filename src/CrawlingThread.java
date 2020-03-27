package UrlCrawler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlingThread implements Runnable {
	private BlockingQueue<Url> bul;
	private List<String> urlList;
	
	public CrawlingThread(BlockingQueue<Url> bufferedList) {
		bul = bufferedList;
		this.urlList = Crawler.urlList;
	}
	
	@Override
	public void run() {
		while(true) {
			String url;
			
			// get first url from urlList; wait if list is empty;
			synchronized(Crawler.urlList) {
				while(urlList.isEmpty()) {
					try {
						Crawler.urlList.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} 
				url = urlList.remove(0);	
			}
			
			// Insertion of crawled urls into BUL
			try{
				// Obtain and parse html; 
				Document html = Jsoup.connect(url).get();
				Elements linkedUrls = html.select("a[href]");
				for (Element page : linkedUrls) {
					if(!((page.attr("abs:href")).equals(""))) {
	                    Url link = new Url(page.attr("abs:href"),url);
	                    synchronized(bul) {
	                    	// wait if bul is full
	                    	while(bul.size() == Crawler.BUL_MAX) {
	                    		bul.wait();
	                    	}
	                    	bul.offer(link);
	                    	bul.notify();
	                    }
					}
				}
			} catch(IOException e) {
				System.err.println("For '" + url + "': " + e.getMessage());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
		}
	}
	
}
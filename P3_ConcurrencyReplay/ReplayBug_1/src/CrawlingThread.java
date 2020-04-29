package UrlCrawlerP3;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlingThread implements Runnable {
	private BlockingQueue<Url> bul;
	private Set<String> urlSet;
	
	public CrawlingThread(BlockingQueue<Url> bufferedList) {
		bul = bufferedList;
		this.urlSet = Crawler.urlSet;
	}
	
	@Override
	public void run() {
		while(true) {
			String url;
			
			url = getUrl();
			crawl(url);
			
			
			synchronized(Crawler.crawlerObj) {
				if(Crawler.numHours == Crawler.TIME_MAX) {
					System.out.println("Program terminating...");
					System.exit(0);
				}
			}
	
		}
	}
	
	/*
	 * Retrieve url from urlSet
	 * waits if set is empty
	 */
	public String getUrl() {
		
		String url;
		Iterator<String> it = urlSet.iterator();
		
		// get first url from urlList; wait if list is empty;
		synchronized(Crawler.urlSet) {
			while(urlSet.isEmpty()) {
				try {
					Crawler.urlSet.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} 
			
			url = it.next();
			urlSet.remove(url);
		}
		
		return url;
	}
	
	/*
	 * Given url, extract linked urls
	 * and store Url object into bul
	 */
	public void crawl(String url) {
		
		int responseCode = Url.urlObj.getResponseCode(url);
		boolean crawlable;
		
		if(responseCode != 0) {
			
			Elements linkedUrls = Url.urlObj.getCrawled(url);
			crawlable = linkedUrls.size() == 0 ? false : true;
			
			if(!crawlable) {
				url = Url.urlObj.getRootUrl(url);
				linkedUrls = Url.urlObj.getCrawled(url);
				crawlable = linkedUrls.size() == 0 ? false : true;
			}
			
			if(crawlable) {
			
				try{
					// Obtain and parse html; 
					for (Element page : linkedUrls) {
						String currUrl = page.attr("abs:href");
						
	
						if((currUrl.equals(""))) {
							continue;
						}
						int checkValid = Url.urlObj.getResponseCode(currUrl);
						if(checkValid == 0) {
							continue;
						}
						else {
				            Url link = new Url(currUrl,url);
				            synchronized(bul) {
			                	while(bul.size() == Crawler.BUL_MAX) {
			                		bul.wait();
			                	}
			                	bul.offer(link);
			                	bul.notify();
				            }
				            
						}
					}
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	

	
}
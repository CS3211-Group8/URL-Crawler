package UrlCrawler;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
		
		if(responseCode == 404) {
			url = Url.urlObj.getRootUrl(url);
			responseCode = Url.urlObj.getResponseCode(url);
			if(responseCode == 404) {
				responseCode = 0;
			}
		}

		if(responseCode != 0) {
			
			try{
				// Obtain and parse html; 
				Document doc = Jsoup.connect(url).ignoreHttpErrors(true).get();
				Elements linkedUrls = doc.select("a[href]");
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
			            	if(!contains(bul,link)) {
			            		// wait if bul is full
			                	while(bul.size() == Crawler.BUL_MAX) {
			                		bul.wait();
			                	}
			                	bul.offer(link);
			                	bul.notify();
			            	}
			            }
			            
					}
				}
				
			} catch(IOException e) {
				System.err.println(e.getMessage());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	/*
	 * Given BUL and Url object, checks if Url object exists
	 * in the BUL
	 */
	public boolean contains(BlockingQueue<Url> bul, Url url) {
		
		boolean contains = false;
		Iterator<Url> it = bul.iterator();
		
		while(it.hasNext()) {
			boolean check = url.equals(it.next());
			if(check) {
				contains = true;
				break;
			}
		}
	
		return contains;
	}
	

	
}
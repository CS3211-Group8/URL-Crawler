package UrlCrawler;

import java.util.Set;
import java.util.concurrent.BlockingQueue;

public class IndexBuildingThread implements Runnable{
	private BlockingQueue<Url> bul;
	private Set<String> urlSet;

	
	public IndexBuildingThread(BlockingQueue<Url> bufferedList) {
		bul = bufferedList;
		this.urlSet = Crawler.urlSet;
	}
	
	@Override
	public void run() {
		while(true) {
			Object[] tempBul;
			
			tempBul = getTempBul();
			indexUrls(tempBul);
	
		}
	}
	
	/*
	 * Return Object[] containing 1000 Url objects and clear bul
	 * if bul is not full, wait
	 */
	public Object[] getTempBul() {
		
		Object[] tempBul;
		
		// Clear BUL if full
		synchronized(bul) {
			while(bul.size() != Crawler.BUL_MAX) {
				try {
					bul.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			tempBul =  bul.toArray();
			bul.clear();
			bul.notifyAll();
		}
		
		
		return tempBul;
	}
	
	/* 
	 * Given Array of Objects (Url),
	 * and html to directory and index entry
	 * print "found-url" --> "linked-url"
	 */
	public void indexUrls(Object[] urlArr) {
		
		String currentUrl, linkedUrl,domain,html;
		
		// check for duplicates; if no duplicates add to urlSet and iut, store html and record url
		for(Object current : urlArr) {
			currentUrl = ((Url)current).getUrl();
			linkedUrl = ((Url)current).getLinkedUrl();
			domain = ((Url)current).getDomain();
			html = ((Url)current).getHtml();
			boolean added;
			
			synchronized(IndexedUrlTree.iutObj) {
				added = IndexedUrlTree.iutObj.addNewEntry(currentUrl, domain, html);
			}
			
			if(added) {
				System.out.println(currentUrl + " --> " + linkedUrl);
			}
			
			synchronized(Crawler.urlSet) {
				if(!( (currentUrl.equals(linkedUrl)) || (Url.urlObj.urlMatch(currentUrl, linkedUrl)) || (currentUrl.equals(linkedUrl)) ) ) {
					this.urlSet.add(currentUrl);
				}
			}
			
			
			long t2 = System.currentTimeMillis();
			synchronized(Crawler.crawlerObj) {
				if((t2 - Crawler.prevTime) >= 3600000) {
					(Crawler.numHours)++;
					Crawler.prevTime = t2;
					System.out.println(Crawler.numUrl + " Urls crawled after " + Crawler.numHours + " hours");
				}
			}
			
		}
		
	}
	

}
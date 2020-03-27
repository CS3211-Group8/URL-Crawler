package UrlCrawler;

import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

public class IndexBuildingThread implements Runnable{
	private BlockingQueue<Url> bul;
	private List<String> urlList;
	private Set<String> urlSet;

	
	public IndexBuildingThread(BlockingQueue<Url> bufferedList) {
		bul = bufferedList;
		this.urlList = Crawler.urlList;
		this.urlSet = Crawler.urlSet;
	}
	
	@Override
	public void run() {
		while(true) {
			Object[] tempBul;
			String currentUrl, linkedUrl;
			
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
			// check for duplicates; if no duplicates add to hashset and urlList
			for(Object current : tempBul) {
				currentUrl = ((Url)current).getUrl();
				linkedUrl = ((Url)current).getLinkedUrl();
				synchronized(Crawler.urlSet) {
					if(!urlSet.contains(currentUrl)) {
						urlSet.add(currentUrl);
						Crawler.numUrl = urlSet.size();
						System.out.println(currentUrl + " --> " + linkedUrl);
						synchronized(Crawler.urlList) {
							urlList.add(currentUrl);
							Crawler.urlList.notifyAll();
						}
					}
				}
				long t2 = System.currentTimeMillis();
				synchronized(Crawler.obj) {
					if((t2 - Crawler.prevTime) >= 3600000) {
						(Crawler.numHours)++;
						Crawler.prevTime = t2;
						System.out.println("current time: " + t2);
						System.out.println(Crawler.numUrl + " Urls crawled after " + Crawler.numHours + " hours");
					}
				}
			}
			
			
		}
	}

}
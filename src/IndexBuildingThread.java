package UrlCrawler;

import java.util.List;
import java.util.Set;

public class IndexBuildingThread implements Runnable{
	private List<Url> bul;
	private List<String> urlList;
	private Set<Url> urlSet;
	
	public IndexBuildingThread(List<Url> bufferedList) {
		bul = bufferedList;
		this.urlList = Crawler.urlList;
		this.urlSet = Crawler.urlSet;
	}
	
	@Override
	public void run() {
		Url current;
		String currentUrl;
		// Get first Url object
		synchronized(Crawler.obj) {
			while(bul.isEmpty()) {
				try {
					System.out.println("waiting in ibt");
					Crawler.obj.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("ibt starts");
			current = bul.remove(0);

		}
		// check for duplicates; if no duplicates add to hashset and urlList
		if((current != null) && (!urlSet.contains(current))) {
			urlSet.add(current);
			currentUrl = current.getUrl();
			System.out.println(currentUrl + " --> " + current.getLinkedUrl());
			synchronized(Crawler.obj) {
				urlList.add(currentUrl);
				Crawler.obj.notify();
			}
		}
	}

}
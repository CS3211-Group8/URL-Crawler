package UrlCrawler;

import java.io.IOException;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlingThread implements Runnable {
	private List<Url> bul;
	private List<String> urlList;
	
	public CrawlingThread(List<Url> bufferedList) {
		bul = bufferedList;
		this.urlList = Crawler.urlList;
	}
	
	@Override
	public void run() {
		String url;
		
		// get first url from urlList; wait if list is empty;
		synchronized(Crawler.obj) {
			while(urlList.isEmpty()) {
				try {
					System.out.println("waiting in ct");
					Crawler.obj.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} 
			System.out.println("ct starts");
			url = urlList.remove(0);
			System.out.println("removed url: " + url);
		}
		
		// Obtain and parse html; 
		try{
			Document html = Jsoup.connect(url).get();
			Elements linkedUrls = html.select("a[href]");
			System.out.println("size: " + linkedUrls.size());
			int i = 1;
			for (Element page : linkedUrls) {
				if(!((page.attr("abs:href")).equals(""))) {
                    Url link = new Url(page.attr("abs:href"),url);
                    System.out.println(i + " crawled url: " + page.attr("abs:href"));
                    i++;
                    synchronized(Crawler.obj) {
                    	bul.add(link);
                    	Crawler.obj.notify();
                    }
				}
			}
		}
		catch(IOException e) {
			System.err.println("For '" + url + "': " + e.getMessage());
		}
	}
}
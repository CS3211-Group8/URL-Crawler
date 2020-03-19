import java.util.*;
import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
	
	
	public class CrawlingThread implements Runnable{
		private List<Url> bul;
		private List<String> urlList;
		
		public CrawlingThread(List<String> seedUrl) {
			urlList = Collections.synchronizedList(new LinkedList<String>());
			bul = Collections.synchronizedList(new LinkedList<Url>());
			urlList.addAll(seedUrl);
		}
		
		@Override
		public void run() {
			String url;
			
			synchronized(this) {
				url = urlList.remove(0);
			}
			try{
				Document html = Jsoup.connect(url).get();
				Elements linkedUrls = html.select("a[href]");
				
				for (Element page : linkedUrls) {
                    Url link = new Url(page.attr("abs:href"),url);
                    synchronized(this) {
                    	bul.add(link);
                    }
				}
			}
			catch(IOException e) {
				System.err.println("For '" + url + "': " + e.getMessage());
			}
		}
	}
	
	public class IndexBuildingThread implements Runnable{

		@Override
		public void run() {
	
		}
	}
	
	public static void main(String[] args) {
		List<String> urlList = Collections.synchronizedList(new LinkedList<String>());
		Set<Url> urlSet = new HashSet<Url>();
		
		urlList.add("https://www.baidu.com");
		
		
	}
	


}

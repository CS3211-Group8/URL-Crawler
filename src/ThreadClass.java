import java.util.*;

public class ThreadClass {
	public static List<String> urlList = Collections.synchronizedList(new LinkedList<String>());
	public static Set<Url> urlSet = new HashSet<Url>();
	List<Url> bul = Collections.synchronizedList(new LinkedList<Url>());
	
	
	
	public class IndexBuildingThread implements Runnable{

		@Override
		public void run() {

		}

	}
	
	public class CrawlingThread implements Runnable{

		@Override
		public void run() {

		}

	}
	
	public void initThreads() {
		
		CrawlingThread ct1 = new CrawlingThread();
		CrawlingThread ct2 = new CrawlingThread();
		IndexBuildingThread ibt = new IndexBuildingThread();
		
		new Thread(ct1).start();
		new Thread(ct2).start();
		new Thread(ibt).start();
		

	}

}

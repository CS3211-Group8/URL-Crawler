
public class Main {

	public static void main(String[] args) {
		ThreadClass.urlList.add("https://www.baidu.com");
		
		ThreadClass tc1 = new ThreadClass();
		ThreadClass tc2 = new ThreadClass();
		ThreadClass tc3 = new ThreadClass();
		
		tc1.initThreads();
		tc2.initThreads();
		tc3.initThreads();
		
	}
}

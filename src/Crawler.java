package UrlCrawler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Crawler {
	public static Set<String> urlSet = Collections.synchronizedSet(new HashSet<String>());
	public static Object crawlerObj = new Object();
	public static final int BUL_MAX = 1000;
	public static int numHours = 0;
	public static int numUrl = 0;
	public static long prevTime = System.currentTimeMillis();
	
}

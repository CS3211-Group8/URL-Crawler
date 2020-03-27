package UrlCrawler;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Crawler {
	public static List<String> urlList = Collections.synchronizedList(new LinkedList<String>());
	public static Set<String> urlSet = new HashSet<String>();
	public static Object obj;
	public static final int BUL_MAX = 1000;
	public static int numHours = 0;
	public static int numUrl = 0;
	public static long prevTime = System.currentTimeMillis();
	
}

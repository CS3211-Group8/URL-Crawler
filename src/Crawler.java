package UrlCrawler;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Crawler {
	public static List<String> urlList = Collections.synchronizedList(new LinkedList<String>());
	public static Set<Url> urlSet = new HashSet<Url>();
	public static Object obj;
	
}

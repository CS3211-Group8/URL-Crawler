package UrlCrawlerP3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Crawler {
	public static Set<String> urlSet = Collections.synchronizedSet(new HashSet<String>());
	public static String outputDir = System.getProperty("user.dir") + "/";
	public static BufferedWriter writer;  
	public static Crawler crawlerObj = new Crawler();
	public static final int BUL_MAX = 50;
	public static  int STORED_PAGE_NUM;
	public static  int TIME_MAX;
	public static int numHours = 0;
	public static int numUrl = 0;
	public static int numStored = 0;
	public static long prevTime = System.currentTimeMillis();
	
	public void initCrawler(String output, int maxPages, int maxTime) {
		outputDir = outputDir + output;
		STORED_PAGE_NUM = maxPages;
		TIME_MAX = maxTime;
	}
	
	public void recordHtml(String record) {
		
		try {
			writer = new BufferedWriter(new FileWriter(outputDir, true));
		    writer.write(record);
		    writer.newLine();   //Add new line
		    writer.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
}

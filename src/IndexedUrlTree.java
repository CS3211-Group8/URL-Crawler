package UrlCrawler;
import java.util.*;
import java.io.*;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;


public class IndexedUrlTree {
	
	public static HashMap<String,String> prefixMap = new HashMap<String,String>();
	public static String prefixIndexDir = System.getProperty("user.dir") + "/prefix_index/";
	public static String domainIndexDir = System.getProperty("user.dir") + "/domain_index/";
	public static String htmlDir = System.getProperty("user.dir") + "/html/";
	public static IndexedUrlTree iutObj = new IndexedUrlTree("obj");
	
	public IndexedUrlTree() {
		
		initDir(prefixIndexDir);
		initDir(domainIndexDir);
		initDir(htmlDir);
		initPrefixMap();
		
	}
	
	public IndexedUrlTree(String obj) {
		
	}
	
	
	/*
	 * Given path, creates new directory
	 */
	public void initDir(String path) {
			
		File dir = new File(path);
		
		if(!dir.exists()) {
			if(!dir.mkdir()) {
				System.err.println("Error to create directory: " + path);
			}
		}
		
	}
	
	
	/*
	 * Initialise HashMap with alphabets and others as key and value to represent
	 * possible prefix of domain
	 */
	public void initPrefixMap() {
		
		String start = "a";
		int startValue = start.charAt(0);
		
		
		for(int i =0 ;i < 26;i++) {
			String currentKey = String.valueOf((char)startValue);		// key = "a"
			String currentValue = currentKey + "*.xml";					// value = "a*.xml"
			String prefixFilePath = prefixIndexDir + currentValue;			// path = ".../index/a*.xml
		
			prefixMap.put(currentKey, currentValue);
			initIndexFile(prefixFilePath);

			startValue++;
		}
		
		// to accommodate for domain that does not begin with alphabet
		String currentKey = "others";
		String currentValue = currentKey + ".xml";					// value = "a*.xml"
		String prefixFilePath = prefixIndexDir + currentValue;			// path = ".../index/a*.xml
		prefixMap.put(currentKey, currentValue);
		initIndexFile(prefixFilePath);
		
		
	}

	
	/*
	 * Given path, create new index xml file and 
	 * writes HashMap to it
	 */
	public void initIndexFile(String path) {
			
		File indexFile = new File(path);
		
		if(!indexFile.exists()) {
			try {
				indexFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			setIndexHashMap(new HashMap<String,String>(),path);
		}
			
	}
	
	
	/*
	 * Given HashMap and directory path, 
	 * writes HashMap as input to file
	 */
	public void setIndexHashMap(HashMap<String,String> map, String path) {
		
		try{
			FileOutputStream fileOut = new FileOutputStream(path);
			XMLEncoder out = new XMLEncoder(fileOut);
			out.writeObject(map);
			out.close();
			fileOut.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	
  
	/*
	 * Add New Entry to IUT
	 * Takes Url Object as argument
	 */
	public boolean addNewEntry(String url,String domain, String html) {
		
		boolean added = false;
		String urlPrefix = domain.substring(0, 1);
		HashMap<String,String> domainMap,urlMap;
		String prefixFile,domainFile,prefixPath,domainPath, htmlPath, htmlFile;
		
		
		prefixFile = getPrefixFile(urlPrefix);		// a*.xml
		prefixPath = prefixIndexDir + prefixFile;	// .../prefix_index/a*.xml
		
		domainMap = getMap(prefixPath);
		
		if(!domainMap.containsKey(domain)) {
			domainFile = domain + ".xml";			// map value
			domainMap.put(domain, domainFile);
			setIndexHashMap(domainMap,prefixPath);
		}
		else {
			domainFile = domainMap.get(domain);			// abc.com.xml (map value)
		}
			
		domainPath = domainIndexDir + domainFile;		// .../domain_index/abc.com.xml
		initIndexFile(domainPath);
		
		urlMap = getMap(domainPath);
		
		if(!urlMap.containsKey(url)) {
			htmlFile = url.replace("://", "--");
			htmlFile = htmlFile.replace("/", "-");
			htmlPath = htmlDir + htmlFile;
			initHtmlFile(htmlPath, html);
			urlMap.put(url, htmlPath);
			setIndexHashMap(urlMap,domainPath);
			added = true;
		
		}
		
		return added;
	}
        
       
	/* Using prefix HashMap, return prefix file name
	 * using prefix of domain as the key.
	 * Always returns known file name value
	 */ 
	public String getPrefixFile(String prefix) {
		
		String prefixFile;
		
		if(prefixMap.containsKey(prefix)) {
			prefixFile = prefixMap.get(prefix);
		}
		else {
			prefixFile = prefixMap.get("others");
		}
		
		return prefixFile;
		
	}
	
	
	/* Returns HashMap from specific index file
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String,String> getMap(String indexPath) {
		
		HashMap<String,String> map = new HashMap<String,String>();
		
		try{
			  FileInputStream fileIn = new FileInputStream(indexPath);
			  XMLDecoder in = new XMLDecoder(fileIn);
			  map = (HashMap<String, String>) in.readObject();
			  in.close();
			  fileIn.close();
		  }
		  catch(FileNotFoundException e){
			  e.printStackTrace();
		  }
		  catch(IOException e){
			  e.printStackTrace();
		  }
		
		return map;
		
	}
	
	
	/*
	 * Given Html path and input,
	 * Creates and writes new HTML file in fixed html directory
	 */
	public void initHtmlFile(String path, String htmlDoc) {
		
		File htmlFile = new File(path);
		
		if(!htmlFile.exists()) {
			try {
				htmlFile.createNewFile();
				FileWriter myWriter = new FileWriter(path);
				myWriter.write(htmlDoc);
				myWriter.close();
		    } 
			catch (IOException e) {
				System.err.println("For path: " + path);
		    	e.printStackTrace();
		    }
			catch(NullPointerException e) {
				System.err.println("For path: " + path);
				e.printStackTrace();
			}
		}
		
			
	}
	
    
}
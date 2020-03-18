
public class Url {
	private String url,linkedUrl;
	
	public Url(String crawledUrl, String prevUrl) {
		url = crawledUrl;
		linkedUrl = prevUrl;
		
	}
	
   public String getUrl() {
	   return url; 
   }
   
   public String getLinkedUrl() {
	   return linkedUrl; 
   }
	
}	

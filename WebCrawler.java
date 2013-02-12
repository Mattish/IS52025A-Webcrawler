import java.util.*;
import java.io.*;
import java.net.*;

public class WebCrawler{
	Table table;
	HashMap<String, Integer> urlMap;
	ArrayList<String> urlsToDo;
	
	
	int amountOfUrls;
	public WebCrawler(String startUrl, String domain){
		urlsToDo = new ArrayList<String>(10);
		urlMap = new HashMap<String,Integer>();
		table = new Table();
		amountOfUrls = 0;
		urlsToDo.add("http://www.gold.ac.uk");
		urlsToDo.add("http://www.gold.ac.uk/news/");
		// If Urls to parse
		while (urlsToDo.size() > 0){
			ListIterator li = urlsToDo.listIterator();
			while (li.hasNext()){
				String url = (String)li.next();
				if (urlMap.get(url) != null){
					System.out.println("Removing :" + url);
					li.remove();
				}
				else{
					System.out.println("Getting :" + url);
					urlMap.put(url,amountOfUrls);
					amountOfUrls++;
					try{
						URL address = new URL(url);
						String pageBody = getWebPage(address);
						li.remove();
						//check body for links
						//loop through links
						//    check if link already in urlMap 
						//    if in urlMap 
						//       add relationships to table for both ways
						//    if not in urlMap 
						//       add to urlMap 
						//       add relationships to table for both ways
						//       add url to urlsToDO
					}
					catch(Exception e){}
				}
				
			}
		}
	}
	
	public static ArrayList<String> getUrlsFromString(String input){
		Pattern p = Pattern.compile("ref=\@(^\\)");
		matches = p.matcher(input);
		ArrayList<String> matches = new ArrayList<String>(1);
		int matchCounter = 0;
		while (matcher.find()) {
			matches.add(matcher.group(matchCounter));
		}
	}
	
	public static String getWebpage(URL url){
		HttpURLConnection connection;
		BufferedReader reader;
		StringBuilder stringBuilder;
		String line;
		try{
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.setReadTimeout(2000);
			connection.connect();
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			stringBuilder = new StringBuilder();
			while ((line = reader.readLine()) != null){
				stringBuilder.append(line + "\n");
			}
			return stringBuilder.toString();

		}
		catch(Exception e){
			e.printStackTrace();
		}	
		return "";
	}

	public WebCrawler(){
		urlMap = new HashMap<String,Integer>();
		table = new Table();
		amountOfUrls = 0;
		urlMap.put("start", amountOfUrls);
		amountOfUrls++;
		//start has middle in it
		urlMap.put("middle",amountOfUrls);
		amountOfUrls++;
		//start can see middle, so add it in
		table.put(urlMap.get("start"),urlMap.get("middle"),1);
		table.put(urlMap.get("middle"),urlMap.get("start"),1);
		// middle has end in it
		urlMap.put("end",amountOfUrls);
		amountOfUrls++;
		//middle can see end, so add it
		table.put(urlMap.get("middle"),urlMap.get("end"),1);
		table.put(urlMap.get("end"),urlMap.get("middle"),1);

		System.out.println("start can see:");
		for(int i = 0; i < amountOfUrls; i++){
			System.out.println(table.get(urlMap.get("start"),i));
		}
		System.out.println("middle can see:");
		for(int i = 0; i < amountOfUrls; i++){
			System.out.println(table.get(urlMap.get("middle"),i));
		}
		System.out.println("end can see:");
		for(int i = 0; i < amountOfUrls; i++){
			System.out.println(table.get(urlMap.get("end"),i));
		}
	}

	public static void main(String[] args){
		if (args.length == 2){
			new WebCrawler(args[0], args[1]);
		}
		else if (args.length == 1){
			if (args[0].equals("-test"))
				new WebCrawler();
		}
		else
			System.out.println("arguments should be in the form of 'WebCrawler \"http://starturl.com\" \"domainname.com\" ");
	}
}

import java.util.*;
import java.io.*;
import java.net.*;
import java.util.regex.*;

public class WebCrawler{
	Table table;
	HashMap<String, Integer> urlMap;
	HashMap<Integer, String> urlMapReverse;
	ArrayList<String> urlsToDo;
	int amountOfUrls;

	public WebCrawler(String startUrl, String domain){
		urlsToDo = new ArrayList<String>(10);
		urlMap = new HashMap<String,Integer>();
		urlMapReverse = new HashMap<Integer,String>();
		table = new Table();
		amountOfUrls = 0;
		urlsToDo.add(startUrl);
		urlMap.put(startUrl,amountOfUrls);
		urlMapReverse.put(amountOfUrls,startUrl);
		amountOfUrls++;
		// If Urls to parse
		while (urlsToDo.size() > 0){
			ListIterator li = urlsToDo.listIterator();
			while (li.hasNext()){
				String url = (String)li.next();
				try{
					URL address = new URL(url);
					int addressNumber = urlMap.get(address.toString());
					String pageBody = getWebpage(address);
					li.remove(); //check body for links
					List refs = getUrlsFromString(pageBody);
					System.out.println(url + " has " + refs.size() + " hrefs");

					if (refs.size() > 0){
						ListIterator li2 = refs.listIterator(); //loop through links
						while(li2.hasNext()){
							String refUrl = (String)li2.next();
							refUrl = refUrl.substring(6);
							refUrl = refUrl.split("#",2)[0];
							try{
								URL refAddress = new URL(address,refUrl);
								String filePath = refAddress.getFile();
								if (filePath.endsWith(".html") || filePath.endsWith(".htm") || filePath.endsWith(".php") || filePath.endsWith("/")){
									if (refAddress.toString().contains(domain)){//check if link is in domain ROUGH CHECK
										int refAddressNumber = urlMap.get(refAddress.toString()) != null ? urlMap.get(refAddress.toString()) : -1;
										urlMap.get(refAddress.toString());//check if link already in urlMap
										if (refAddressNumber != -1){ //if in urlMap 
										}
										else{//if not in urlMap 
											urlMap.put(refAddress.toString(),amountOfUrls);//add to urlMap 
											urlMapReverse.put(amountOfUrls,refAddress.toString());//add to urlMapReverse
											System.out.println("Adding " + refAddress.toString() + " to urlMap");
											amountOfUrls++;
											refAddressNumber = urlMap.get(refAddress.toString());
											li.add(refAddress.toString());
										}
										//table.put(refAddressNumber, addressNumber, 1);//add relationships to table for both ways
										table.put(addressNumber, refAddressNumber, 1);//add relationships to table for both ways
										//System.out.println((String)li2.next());
									}
								}
							}
							catch(MalformedURLException e){}
						}
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
				try{
					//Thread.sleep(50);
					System.out.println("urlMap.size()=" + urlMap.size());
				}
				catch(Exception e){}
			}
		}

		// DO CHECK STUFF HERE
		writeToFile();
	}

	private void writeToFile(){
		String file = "digraph urlGraph {\nnode [shape=box]\noverlap=false\n";
		Set<Integer> keys = urlMapReverse.keySet();
		Iterator iterator = keys.iterator();
		while(iterator.hasNext()){
			Integer inputAddressNumber = (Integer)iterator.next();
			file += inputAddressNumber + " [label=\"" + urlMapReverse.get(inputAddressNumber) + "\"]\n";
			if (inputAddressNumber != null){
				HashMap<Integer,Integer> inputHashMap = table.getHashMapFromIndex(inputAddressNumber);
				if(inputHashMap != null){
					Set<Integer> inputKeys = inputHashMap.keySet();
					Iterator it = inputKeys.iterator();
					while(it.hasNext()){
						int key = (int)it.next();
						int relationshipNum = table.get(inputAddressNumber,key);
						if (relationshipNum == 1){ // Pretty sure this will always be 1 if it even has a key
							file += inputAddressNumber + " -> " + key + "\n";
						}
					}
				}
			}	
		}
		file += "}";
		try{
			FileOutputStream fos = new FileOutputStream("derp.gv");
			byte[] bytes = file.getBytes("UTF-8");
			fos.write(bytes, 0, bytes.length);
			fos.flush();
			fos.close();
		}
		catch(Exception e){

		}
		//System.out.println(file);
	}
	
	private ArrayList<String> getUrlsFromString(String input){
		Pattern p = Pattern.compile("href=\"[^\"]*", Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(input);
		ArrayList<String> matches = new ArrayList<String>(1);
		int matchCounter = 0;
		while (matcher.find()) {
			matches.add(matcher.group(matchCounter));
		}
		return matches;
	}
	
	private String getWebpage(URL url){
		HttpURLConnection connection;
		BufferedReader reader;
		StringBuilder stringBuilder;
		String line;
		try{
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.setReadTimeout(2000);
			connection.connect();
			if (connection.getContentType().contains("text/html")){
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				stringBuilder = new StringBuilder();
				while ((line = reader.readLine()) != null){
					stringBuilder.append(line + "\n");
				}
				return stringBuilder.toString();
			}
			else
				return "";

		}
		catch(Exception e){
			//e.printStackTrace();
		}	
		return "";
	}

	public static void main(String[] args){
		if (args.length == 2)
			new WebCrawler(args[0], args[1]);
		else
			System.out.println("arguments should be in the form of 'WebCrawler \"http://starturl.com\" \"domainname.com\" ");
	}
}

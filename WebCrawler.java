import java.util.HashMap;

public class WebCrawler{
	Table table;
	HashMap<String, Integer> urlMap;
	int amountOfUrls;
	public WebCrawler(String startUrl, String domain){

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

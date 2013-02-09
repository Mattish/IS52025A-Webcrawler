public class WebCrawler{
	
	public WebCrawler(String startUrl, String domain){

	}

	public static void main(String[] args){
		if (args.length == 2)
			new WebCrawler(args[0], args[1]);
		else
			System.out.println("arguments should be in the form of 'WebCrawler \"http://starturl.com\" \"domainname.com\" ");
	}
}
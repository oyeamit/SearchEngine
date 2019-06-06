package de.intsys.krestel.SearchEngine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class WebFile {
	// Saved response.
	private java.util.Map<String,java.util.List<String>> responseHeader = null;
	private java.net.URL responseURL = null;
	private int responseCode = -1;
	private String MIMEtype  = null;
	private String charset   = null;
	private Object content   = null;

	/** Open a web file. */
	public WebFile( String urlString )
	throws java.io.IOException,java.net.UnknownServiceException,java.net.SocketTimeoutException {
		// Open a URL connection.
		final java.net.URL url = new java.net.URL( urlString );
		final java.net.URLConnection uconn = url.openConnection( );
		if ( !(uconn instanceof java.net.HttpURLConnection) )
			throw new java.lang.IllegalArgumentException(
					"URL protocol must be HTTP." );
		final java.net.HttpURLConnection conn =
			(java.net.HttpURLConnection)uconn;

		// Set up a request.
		conn.setConnectTimeout( 10000 );    // 10 sec
		conn.setReadTimeout( 10000 );       // 10 sec
		conn.setInstanceFollowRedirects( true );
		conn.setRequestProperty( "User-agent", "ich" );

		// Send the request.
		conn.connect( );

		// Get the response.
		responseHeader    = conn.getHeaderFields( );
		responseCode      = conn.getResponseCode( );
		responseURL       = conn.getURL( );
		final int length  = conn.getContentLength( );
		final String type = conn.getContentType( );
		if ( type != null ) {
			final String[] parts = type.split( ";" );
			MIMEtype = parts[0].trim( );
			for ( int i = 1; i < parts.length && charset == null; i++ ) {
				final String t  = parts[i].trim( );
				final int index = t.toLowerCase( ).indexOf( "charset=" );
				if ( index != -1 )
					charset = t.substring( index+8 );
			}
		}

		// Get the content.
		final java.io.InputStream stream = conn.getErrorStream( );
		if ( stream != null )
			content = readStream( length, stream );
		else if ( (content = conn.getContent( )) != null &&
				content instanceof java.io.InputStream )
			content = readStream( length, (java.io.InputStream)content );
		conn.disconnect( );
	}

	/** Read stream bytes and transcode. */
	private Object readStream( int length, java.io.InputStream stream )
	throws java.io.IOException {
		final int buflen = Math.max( 1024, Math.max( length, stream.available() ) );
		byte[] buf   = new byte[buflen];;
		byte[] bytes = null;

		for ( int nRead = stream.read(buf); nRead != -1; nRead = stream.read(buf) ) {
			if ( bytes == null ) {
				bytes = buf;
				buf   = new byte[buflen];
				continue;
			}
			final byte[] newBytes = new byte[ bytes.length + nRead ];
			System.arraycopy( bytes, 0, newBytes, 0, bytes.length );
			System.arraycopy( buf, 0, newBytes, bytes.length, nRead );
			bytes = newBytes;
		}

		if ( charset == null )
			return new String(bytes);
		try {
			return new String( bytes, charset );
		}
		catch ( java.io.UnsupportedEncodingException e ) { }
		return bytes;
	}

	/** Get the content. */
	public Object getContent( ) {
		return content;
	}

	/** Get the response code. */
	public int getResponseCode( ) {
		return responseCode;
	}

	/** Get the response header. */
	public java.util.Map<String,java.util.List<String>> getHeaderFields( ) {
		return responseHeader;
	}

	/** Get the URL of the received page. */
	public java.net.URL getURL( ) {
		return responseURL;
	}
	
	/** Get the MIME type. */
	public String getMIMEType( ) {
		return MIMEtype;
	}

	public static void main(String[] args) throws Exception{
		WebFile webFile= new WebFile("https://www.thetimes.co.uk/");
		//WebFile webFile= new WebFile("https://www.thetimes.co.uk/topic/politics");
		Object webContent = webFile.getContent();
		Document doc = Jsoup.parse(webContent.toString());
		Elements links = doc.select("a[href*=https://www.thetimes.co]");
		Elements DateOnPage = doc.select("time[class^=\"Dateline\"]");
		System.out.println("Last Modified: " + DateOnPage.text());
		String DateOfArticle = DateOnPage.text();
		System.out.println(webFile.getHeaderFields().get("Date"));
		if (DateOfArticle.contains(("May 20 2019"))) {
			System.out.println("We did it");
		}
		List listA = new ArrayList();
		List listC = new ArrayList();
		List listB = new ArrayList();
		List listD = new ArrayList();
		int count = 0;

		for (Element link : links) {
			if (!listB.contains(link.attr("href"))) {
				// get the value from the href attribute
				WebFile webFile_1= new WebFile(link.attr("href"));
				System.out.println("Crawling: "+link.attr("href").toString());
				Object webContent_1 = webFile_1.getContent();
				Document doc_1 = Jsoup.parse(webContent_1.toString());
				Thread.sleep(1000);
				Elements DateOnPage_1 = doc_1.select("time[class^=\"Dateline\"]");
				String DateOfArticle_1 = DateOnPage_1.text();
				listB.add(link.attr("href"));
				Elements metaTags = doc_1.getElementsByTag("meta");
				if (DateOfArticle_1.contains(("May 20 2019"))){//(DateOnPage_1.size() != 0) {
					String authors = doc_1.select("meta[name^=\"author\"]").first().attr("content");
					String description = doc_1.select("meta[name*=\"description\"]").first().attr("content");
					String title = doc_1.select("meta[name*=\"article:title\"]").first().attr("content");
					Elements cat = doc_1.select("li[class^=\"Topics-item\"]");
					//String caption = doc_1.select("span[class^=\"Media-captionContainer\"").text();
					String cat_text = "";
					count += 1;
					for(int l=0;l<cat.size()/2;l++)
						cat_text = cat_text + cat.get(l).text()+ ", ";
					System.out.println("\nArticle ID: " + count);
					System.out.println("article url: " + link.attr("href"));
					System.out.println("article authors: " + authors);
					System.out.println("article headline: " + title);
					System.out.println("article description: " + description);
					System.out.println("publication timestamp: " + DateOfArticle_1);
					//System.out.println(webFile_1.getHeaderFields().get("Content-Type"));
					//System.out.println("Image caption: " + caption);
					System.out.println("article categories: " + cat_text);
					listC.add(link.attr("href").toString());
				}
				Elements links_1 = doc_1.select("a[href*=https://www.thetimes.co]");
				for (Element link_1 : links_1) {
					if(!listA.contains(link_1.attr("href")))
						listA.add(link_1.attr("href"));
				}
			}
		}
		//System.out.println(count);
		//listA.add("");
		for (int i = 0; i < listA.size(); i++) {
			//System.out.println(listA.get(i) + "\n");
			if (!listB.contains(listA.get(i).toString())) {
				//System.out.println(listA.get(i) + "\n");
				// get the value from the href attribute
				WebFile webFile_1 = new WebFile(listA.get(i).toString());
				System.out.println("Crawling: "+listA.get(i).toString());
				Object webContent_1 = webFile_1.getContent();
				Document doc_1 = Jsoup.parse(webContent_1.toString());
				Thread.sleep(60000);
				Elements DateOnPage_1 = doc_1.select("time[class^=\"Dateline\"]");
				String DateOfArticle_1 = DateOnPage_1.text();
				Elements metaTags = doc_1.getElementsByTag("meta");
				listB.add(listA.get(i));
				if (DateOfArticle_1.contains(("May 14 2019"))) {//(DateOnPage_1.size() != 0) {
					String authors = doc_1.select("meta[name^=\"author\"]").first().attr("content");
					String description = doc_1.select("meta[name*=\"description\"]").first().attr("content");
					String title = doc_1.select("meta[name*=\"article:title\"]").first().attr("content");
					Elements cat = doc_1.select("li[class^=\"Topics-item\"]");
					String cat_text = "";
					count += 1;
					for(int l=0;l<cat.size()/2;l++)
						cat_text = cat_text + cat.get(l).text()+ ", ";
					System.out.println("\nArticle ID: " + count);
					System.out.println("link: " + listA.get(i).toString());
					System.out.println("article authors: " + authors);
					System.out.println("article headline: " + title);
					System.out.println("article description: " + description);
					System.out.println("publication timestamp: " + DateOfArticle_1);
					//System.out.println(webFile_1.getHeaderFields().get("Content-Type"));
					System.out.println("article categories: " + cat_text);
					listC.add(listA.get(i).toString());
				}
				Elements links_1 = doc_1.select("a[href*=https://www.thetimes.co]");
				for (Element link_1 : links_1) {
					//System.out.println(link_1.attr("href"));
					if ((!listA.contains(link_1.attr("href"))) && ((!listD.contains(link_1.attr("href"))))){
						listD.add(link_1.attr("href"));}
				}
				//listC.add(doc.select("a[href*=https://www.thetimes.co]"));

			}
		}

		for (int i = 0; i < listD.size(); i++) {
			//System.out.println(listD.get(i) + "\n");
			if (!listB.contains(listD.get(i).toString())) {
				//System.out.println(listD.get(i) + "\n");
				// get the value from the href attribute
				WebFile webFile_1 = new WebFile(listD.get(i).toString());
				System.out.println("Crawling: "+listD.get(i).toString());
				Object webContent_1 = webFile_1.getContent();
				Document doc_1 = Jsoup.parse(webContent_1.toString());
				Thread.sleep(1000);
				Elements DateOnPage_1 = doc_1.select("time[class^=\"Dateline\"]");
				String DateOfArticle_1 = DateOnPage_1.text();
				listB.add(listD.get(i));
				Elements metaTags = doc_1.getElementsByTag("meta");
				if (DateOfArticle_1.contains(("May 20 2019"))) {//(DateOnPage_1.size() != 0) {
					String authors = doc_1.select("meta[name^=\"author\"]").first().attr("content");
					String description = doc_1.select("meta[name*=\"description\"]").first().attr("content");
					String title = doc_1.select("meta[name*=\"article:title\"]").first().attr("content");
					Elements cat = doc_1.select("li[class^=\"Topics-item\"]");
					String cat_text = "";
					count += 1;
					for(int l=0;l<cat.size()/2;l++)
						cat_text = cat_text + cat.get(l).text()+ ", ";
					System.out.println("\nArticle ID: " + count);
					System.out.println("link: " + listD.get(i).toString());
					System.out.println("article authors: " + authors);
					System.out.println("article headline: " + title);
					System.out.println("article description: " + description);
					System.out.println("publication timestamp: " + DateOfArticle_1);
					//System.out.println(webFile_1.getHeaderFields().get("Content-Type"));
					System.out.println("article categories: " + cat_text);
					listC.add(listD.get(i).toString());

				}
                /*Elements links_1 = doc_1.select("a[href*=https://www.thetimes.co]");
                for (Element link_1 : links_1) {
                    System.out.println(link_1.attr("href"));
                    if ((!listA.contains(link_1.attr("href"))) && ((!listD.contains(link_1.attr("href"))))){
                        listD.add(link_1.attr("href"));}
                }*/
				//listC.add(doc.select("a[href*=https://www.thetimes.co]"));

			}
		}
	}
}

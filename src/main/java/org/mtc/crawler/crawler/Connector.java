package org.mtc.crawler.crawler;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.mtc.crawler.data.CrawlerData;

/**
 * 	获取网页内容的工具类
 */
public class Connector {

	private Connector() {
	}

	public static Document connect(String url, CrawlerData data) throws IOException {
		SocketTimeoutException exception = null;
		
		for (int i = 0; i < data.retryCount; i++) {
			try {
				return Jsoup.connect(url).userAgent(data.userAgent).timeout(data.timeOutMillisecond).get();
			} catch (SocketTimeoutException e) {
				exception = e;
			}
		}
		
		throw exception;
	}
}

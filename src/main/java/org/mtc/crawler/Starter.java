package org.mtc.crawler;

import java.io.IOException;

import org.mtc.crawler.data.BiQuGeData;

/**
 * 图形界面出来前的启动器
 * 
 * @author MtC
 *
 */
public class Starter {
	public static void main(String[] args) {

		CrawlerData data = new BiQuGeData();

		data.mainPageUrl = "http://www.biquge.info/11_11131/";
		data.savePath = System.getProperty("user.dir") + "\\save";

		data.minInterval = 0;
		data.maxInterval = 1000;
		data.timeOutMillisecond = 60000;
		data.retryCount = 5;
		data.crawlerNumber = 10;

		try {
			new CrawlerController(data).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

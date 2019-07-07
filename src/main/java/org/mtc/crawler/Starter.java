package org.mtc.crawler;

import java.io.IOException;

/**
 * 图形界面出来前的启动器
 * 
 * @author MtC
 *
 */
public class Starter {
	public static void main(String[] args)  {

		CrawlerData data = new CrawlerData();

		data.startUrl = "http://www.shushuwuxs.org/5_5348/";
		data.savePath = "D:\\Projects\\Spring-Tool-Suite-WorkSpace\\Universal-Novel-Crawler\\UniversalNovelCrawler\\save";
		data.userAgent = "I,crawler"; // 这是个错误示范，但这个网站真的就是没有任何检测，有需要请自行百度如何查浏览器的用户代理
		
		data.minInterval=0;
		data.maxInterval=1000;

		NiversalNovelCrawler crawler= new NiversalNovelCrawler(data);

		try {
			crawler.doCrawler();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

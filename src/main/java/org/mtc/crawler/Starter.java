package org.mtc.crawler;

import java.io.IOException;

import org.mtc.crawler.data.ShuShuWuData;

/**
 * 图形界面出来前的启动器
 * 
 * @author MtC
 *
 */
public class Starter {
	public static void main(String[] args) {

		CrawlerData data = new ShuShuWuData();

		data.agreement = "http://";
		data.mainPageUrl = "http://www.shushuwuxs.org/1_1035/";
		data.savePath = System.getProperty("user.dir") + "\\save";
		data.userAgent = "I,crawler"; // 这是个错误示范，但这个网站真的就是没有任何检测，有需要请自行百度如何查浏览器的用户代理

		data.bookNameQuery = "div#maininfo>div#info>h1"; // id为"maininfo"的div的直接子元素中的id为"info"的div的直接子类中的h1
		data.chapterQuery = "div#list>dl>dd+dt~dd>a"; // id为"list"的div的直接子元素中的dl的直接子元素中的dd后面的dt后面的所有同父级的dd的直接子元素中的a标签
		data.chapterNameQuery = "div.bookname>h1"; // 类名为"bookname"的div的直接子元素中的h1
		data.textQuery = "div#content"; // id为"content"的div

		data.minInterval = 0;
		data.maxInterval = 1000;
		data.timeOutMillisecond = 60000;
		data.retryCount = 5;
		data.crawlerNumber = 50;

		try {
			new CrawlerController(data).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

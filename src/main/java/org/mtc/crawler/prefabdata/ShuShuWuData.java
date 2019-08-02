package org.mtc.crawler.prefabdata;

import org.mtc.crawler.data.CrawlerData;
import org.mtc.crawler.data.UrlType;

/**
 * 书书屋 （http://www.shushuwuxs.org/） 的基本配置
 */
public class ShuShuWuData extends CrawlerData {

	public ShuShuWuData() {
		
		agreement = "http://";
		userAgent = "I,crawler"; // 这是个错误示范，但这个网站真的就是没有任何检测，有需要请自行百度如何查浏览器的用户代理

		urlType = UrlType.RELATIVE_TO_HOST; // 网页上的url是相对于网站host的

		bookNameQuery = "div#maininfo>div#info>h1"; // id为"maininfo"的div的直接子元素中的id为"info"的div的直接子类中的h1
		chapterQuery = "div#list>dl>dd+dt~dd>a"; // id为"list"的div的直接子元素中的dl的直接子元素中的dd后面的dt后面的所有同父级的dd的直接子元素中的a标签
		chapterNameQuery = "div.bookname>h1"; // 类名为"bookname"的div的直接子元素中的h1
		textQuery = "div#content"; // id为"content"的div
	}
}

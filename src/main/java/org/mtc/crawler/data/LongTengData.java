package org.mtc.crawler.data;

import org.mtc.crawler.CrawlerData;
import org.mtc.crawler.UrlType;

/**
 * 龙腾小说的基本配置
 */
public class LongTengData extends CrawlerData {

	public LongTengData() {

		agreement = "http://";
		userAgent = "I,crawler"; // 这是个错误示范，但这个网站真的就是没有任何检测，有需要请自行百度如何查浏览器的用户代理

		urlType = UrlType.RELATIVE_TO_HOST; // 网页上的url是相对于网站host的

		bookNameQuery = "h1.bookTitle"; // 类名为"bookTitle"的h1
		chapterQuery = "div#list-chapterAll>dl>dd>a"; // id为"list-chapterAll"的div的直接子元素中的dl的直接子元素中的dd里面的a标签
		chapterNameQuery = "h1.readTitle"; // 类名为"readTitle"的h1
		textQuery = "div.panel-body"; // 类名为"panel-body"的div
	}
}

package org.mtc.crawler.data;

import java.util.Random;

/**
 * 	爬虫的设置
 * 
 * 	@author MtC
 *
 */
public class CrawlerData {
	/**
	 * 	小说第一页的网址
	 */
	public String mainPageUrl;
	/**
	 * 	存储到的文件夹的路径
	 */
	public String savePath;
	/**
	 * 	用户代理，网站用来判断访问者使用平台、浏览器等信息的依据，网站通过这些信息发送页面给访问者。<br/>
	 * 	但不是所有网站都对这个信息做了精心设计和处理，有很多网站这个信息传什么都没影响。
	 */
	public String userAgent = "userAgent";
	/**
	 * 	超时时间，以毫秒为单位。当访问网址时间超过此时间仍没有获取到网页的相应时，视为连接超时
	 */
	public int timeOutMillisecond = 10000;
	/**
	 * 	超时重连次数，如果发生连接超时，会尝试连接这里设置的次数
	 */
	public int retryCount = 3;
	/**
	 * 	协议
	 */
	public String agreement = "http://";
	/**
	 * 	章节URL分页类型
	 */
	public ChapterUrlPageType chapterUrlPageType = ChapterUrlPageType.SINGLE_PAGE;
	/**
	 * 	章节URL页的下一页的按钮的查询规则
	 */
	public String nextChapterUrlPageQuery;
	/**
	 * 	url类型
	 */
	public UrlType urlType = UrlType.RELATIVE_TO_HOST;
	/**
	 * 	从小说首页查询书名的查询规则
	 */
	public String bookNameQuery;
	/**
	 * 	从小说首页查询章节链接元素的查询规则
	 */
	public String chapterQuery;
	/**
	 * 	从章节页面查询章节标题的查询规则
	 */
	public String chapterNameQuery;
	/**
	 * 	从章节页面查询含有正文的【单独】元素的查询规则，此元素是所有正文共同的父级元素，同时这个元素不能包含正文之外的文本内容
	 */
	public String textQuery;
	/**
	 * 	【以毫秒为单位】爬取两页之间的最短间隔时间，用于应对有访问频率检测的网站
	 */
	public int minInterval = 0;
	/**
	 * 	【以毫秒为单位】爬取两页之间的最长间隔时间，用于应对有访问频率检测的网站
	 */
	public int maxInterval = 0;
	/**
	 * 	爬取线程数量
	 */
	public int crawlerNumber = 10;

	/**
	 * 	【以毫秒为单位】根据最长最短间隔时间获取爬取下一页需要等待的间隔时间
	 */
	public int getInterval() {
		return new Random().nextInt(maxInterval - minInterval) + minInterval;
	}
}

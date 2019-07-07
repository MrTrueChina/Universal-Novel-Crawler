package org.mtc.crawler;

import java.util.Random;

/**
 * 爬虫的设置
 * 
 * @author MtC
 *
 */
public class CrawlerData {
	/**
	 * 小说第一页的网址
	 */
	public String startUrl;
	/**
	 * 存储到的文件夹的路径
	 */
	public String savePath;
	/**
	 * 用户代理，网站用来判断访问者使用平台、浏览器等信息的依据，网站通过这些信息发送页面给访问者。<br/>
	 * 但不是所有网站都对这个信息做了精心设计和处理，有很多网站这个信息传什么都没影响。
	 */
	public String userAgent ="userAgent";
	/**
	 * 【以毫秒为单位】爬取两页之间的最短间隔时间，用于应对有访问频率检测的网站
	 */
	public int minInterval = 0;
	/**
	 * 【以毫秒为单位】爬取两页之间的最长间隔时间，用于应对有访问频率检测的网站
	 */
	public int maxInterval = 0;

	/**
	 * 【以毫秒为单位】根据最长最短间隔时间获取爬取下一页需要等待的间隔时间
	 * 
	 * @return
	 */
	public int getInterval() {
		return new Random().nextInt(maxInterval - minInterval) + minInterval;
	}
}

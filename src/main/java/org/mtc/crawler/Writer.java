package org.mtc.crawler;

/**
 * 通用型爬虫负责将小说写入txt的类
 */
public class Writer implements Runnable {

	/**
	 * 爬取设置
	 */
	private CrawlerData _data;
	/**
	 * 章节容器
	 */
	private NovelContainer _container;

	public Writer(CrawlerData data, NovelContainer container) {
		_data = data;
		_container = container;
	}

	public void run() {
		// TODO Auto-generated method stub

	}
}

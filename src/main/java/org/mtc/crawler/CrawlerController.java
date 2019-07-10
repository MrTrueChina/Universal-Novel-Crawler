package org.mtc.crawler;

import java.io.IOException;
import java.util.List;

public class CrawlerController {

	private CrawlerData _data;
	private NovelContainer _container;

	public CrawlerController(CrawlerData data) {
		_data = data;
	}

	/**
	 * 开始爬取
	 * 
	 * @throws IOException
	 */
	public void start() throws IOException {
		/*
		 * 	获取所有章节链接
		 * 	创建容器
		 * 	创建并启动爬取线程
		 * 	创建并启动写线程
		 */
		List<String> urls = ChapterUrlGetter.getChapterUrls(_data);
		_container = new NovelContainer(urls);
		startCrawlerThreads();
		startWriteThread();
	}
	
	private void startCrawlerThreads() {
		for (int i = 0; i < _data.crawlerNumber; i++)
			startCrawlerThread();
	}

	private void startCrawlerThread() {
		new Crawler(_data, _container).start();
	}

	private void startWriteThread() {
		new Writer(_data, _container).start();
	}
}

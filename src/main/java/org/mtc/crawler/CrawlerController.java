package org.mtc.crawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
		List<String> urls = getChapterUrls();
		_container = new NovelContainer(urls);
		startCrawlerThreads();
		startWriteThread();
	}

	private List<String> getChapterUrls() throws IOException {
		/*
		 * 	获取域名
		 * 	从小说首页获取所有章节的url
		 * 	拼接域名和url
		 * 	返回
		 */
		String host = getHost();
		List<String> urls = getOriginChapterUrls();
		return connectHostAndUrls(host, urls);
	}

	private String getHost() throws MalformedURLException {
		return _data.agreement + new URL(_data.mainPageUrl).getHost();
	}

	private List<String> getOriginChapterUrls() throws IOException {
		/*
		 * 	获取页面
		 * 	筛选有章节链接的元素
		 * 	取出来存入List
		 * 	返回
		 */
		List<String> urls = new ArrayList<String>();

		Document mainPage = Connector.connect(_data.mainPageUrl,_data);

		Elements urlElements = mainPage.select(_data.chapterQuery);

		for (Element element : urlElements)
			urls.add(element.attr("href"));

		return urls;
	}

	private List<String> connectHostAndUrls(String host, List<String> urls) {

		List<String> connectedUrls = new ArrayList<String>();

		for (int i = 0; i < urls.size(); i++)
			connectedUrls.add(host + urls.get(i));

		return connectedUrls;
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

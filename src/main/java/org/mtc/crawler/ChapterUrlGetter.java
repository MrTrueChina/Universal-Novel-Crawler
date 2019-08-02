package org.mtc.crawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 	获取章节URL的工具类
 */
public class ChapterUrlGetter {

	private CrawlerData _data;

	private ChapterUrlGetter(CrawlerData data) {
		_data = data;
	}

	public static List<String> getChapterUrls(CrawlerData data) throws IOException {

		return new ChapterUrlGetter(data).getUrls();
	}

	private List<String> getUrls() throws IOException {
		/*
		 * 	获取url
		 * 	获取前缀
		 * 	拼接返回
		 */
		List<String> urls = getUrlsFromPage();
		String prefix = getPrefix();
		return connectPrefixAndUrls(urls, prefix);
	}

	private List<String> getUrlsFromPage() throws IOException {
		/*
		 * 	获取页面
		 * 	筛选有章节链接的元素
		 * 	取出来存入List
		 * 	返回
		 */
		Document mainPage = Connector.connect(_data.mainPageUrl, _data);

		Elements urlElements = mainPage.select(_data.chapterQuery);

		List<String> urls = new ArrayList<String>();

		for (Element element : urlElements)
			urls.add(element.attr("href"));

		return urls;
	}

	private String getPrefix() throws MalformedURLException {

		switch (_data.urlType) {

		case RELATIVE_TO_HOST:
			return getPrefixRelativeToHost();

		case RELATIVE_TO_PAGE:
			return getPrefixRelativeToPage();

		default:
			throw new IllegalArgumentException("链接模式错误");
		}
	}

	private String getPrefixRelativeToHost() throws MalformedURLException {
		return _data.agreement + new URL(_data.mainPageUrl).getHost();
	}

	private String getPrefixRelativeToPage() {
		return _data.mainPageUrl;
	}

	private List<String> connectPrefixAndUrls(List<String> urls, String prefix) {

		List<String> connectedUrls = new ArrayList<String>();

		for (String url : urls)
			connectedUrls.add(prefix + url);

		return connectedUrls;
	}
}

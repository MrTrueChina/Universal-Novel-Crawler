package org.mtc.crawler.chapterurlgetter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.mtc.crawler.data.CrawlerData;

/**
 * 	从小说首页获取章节URL的工具类
 */
public abstract class ChapterUrlGetter {

	protected CrawlerData _data;

	public ChapterUrlGetter(CrawlerData data) {
		_data = data;
	}

	public final List<String> getChapterUrls() throws IOException {
		/*
		 * 	获取url
		 * 	获取前缀
		 * 	拼接返回
		 */
		List<String> urls = getOriginUrls();
		String prefix = getPrefix();
		return connectPrefixAndUrls(urls, prefix);
	}

	/**
	 * 	获取网站上的原始URL
	 * 	@return 原始URL
	 * 	@throws IOException Jsoup 最有可能引发的异常就是 IOException
	 */
	protected abstract List<String> getOriginUrls() throws IOException;

	private String getPrefix() throws MalformedURLException {

		switch (_data.urlType) {

		case RELATIVE_TO_HOST:
			return getPrefixRelativeToHost();

		case RELATIVE_TO_PAGE:
			return getPrefixRelativeToPage();

		default:
			throw new IllegalArgumentException("该链接模式尚未支持");
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

package org.mtc.crawler.chapterurlgetter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.mtc.crawler.crawler.Connector;
import org.mtc.crawler.data.CrawlerData;

/**
 * 	章节URL在单个页面的小说首页的获取章节URL的工具类
 */
public class MultiPageChapterUrlGetter extends ChapterUrlGetter {

	private List<String> _originUrls;

	public MultiPageChapterUrlGetter(CrawlerData data) {
		super(data);
	}

	@Override
	protected List<String> getOriginUrls() throws IOException {

		try {
			setup();
			return getUrls();
		} finally {
			clear();
		}
	}

	private void setup() {
		_originUrls = new ArrayList<String>();
	}

	private List<String> getUrls() throws IOException {
		/*
		 * 	do
		 * 	{
		 * 		保存这一页的章节URL并获取下一页的URL
		 * 	}
		 * 	while (还有下一页)
		 */
		String nextPageUrl = _data.mainPageUrl;

		do {
			nextPageUrl = saveCharacterUrlsAndGetNextPageUrl(nextPageUrl);
		} while (nextPageUrl != null);

		return _originUrls;
	}

	private String saveCharacterUrlsAndGetNextPageUrl(String pageUrl) throws IOException {
		/*
		 * 	获取页面
		 * 	保存这一页的章节URL
		 * 	获取下一页的URL并返回
		 */
		Document document = Connector.connect(pageUrl, _data);

		saveUrlsOnPage(document);

		return getNextPageUrl(document);
	}

	private void saveUrlsOnPage(Document document) throws IOException {
		/*
		 * 	根据规则选择出元素
		 * 	取出href并保存
		 */
		Elements chapterElements = document.select(_data.chapterQuery);

		_originUrls.addAll(chapterElements.eachAttr("href"));
	}

	private String getNextPageUrl(Document document) {
		/*
		 * 	最大问题在于链接的拼接
		 */
		/*
		 * 	根据规则选择出元素
		 * 	取出href返回
		 */
		return document.selectFirst(_data.nextChapterUrlPageQuery).attr("href");
	}

	private void clear() {
		_originUrls = null;
	}
}

package org.mtc.crawler.chapterurlgetter;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.mtc.crawler.crawler.Connector;
import org.mtc.crawler.data.CrawlerData;

/**
 * 	章节URL在单个页面的小说首页的获取章节URL的工具类
 */
public class SinglePageChapterUrlGetter extends ChapterUrlGetter{

	public SinglePageChapterUrlGetter(CrawlerData data) {
		super(data);
	}

	@Override
	protected List<String> getOriginUrls() throws IOException {
		/*
		 * 	获取页面
		 * 	筛选有章节链接的元素
		 * 	取出所有href属性，返回
		 */
		Document mainPage = Connector.connect(_data.mainPageUrl, _data);

		Elements urlElements = mainPage.select(_data.chapterQuery);

		return urlElements.eachAttr("href"); // Elements.eachAttr：获取所有元素的指定属性，并以 List<String> 返回
	}
}

package org.mtc.crawler;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;

/**
 * 通用型爬虫负责爬取小说的类
 */
public class Crawler extends Thread {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private static final String CHAPTER_SEPARATOR = System.getProperty("line.separator")
			+ System.getProperty("line.separator");

	private CrawlerData _data;
	private NovelContainer _container;

	/*
	 * 	设置
	 * 	容器
	 * 
	 * 	从容器里取出章节任务的方法
	 * 	爬取正文的方法
	 * 	向容器交任务的方法
	 */

	public Crawler(CrawlerData data, NovelContainer container) {
		_data = data;
		_container = container;
	}

	@Override
	public void run() {
		System.out.println("爬取线程 " + Thread.currentThread().getName() + " 启动");

		try {
			String url;
			while ((url = _container.getChapterUrl()) != null) {
				System.out.println("爬取线程 " + Thread.currentThread().getName() + " 获取url：" + url);

				_container.setChapterText(url, crawlText(url));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("爬取线程 " + Thread.currentThread().getName() + " 获取不到url，线程结束");
	}

	private String crawlText(String url) throws IOException {
		/*
		 * 	爬取章节标题
		 * 	加标题和正文之间的间隔
		 * 	爬取章节正文
		 * 	加章节间间隔
		 */
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append(crawlAChapterName(url));
		stringBuilder.append(CHAPTER_SEPARATOR);
		stringBuilder.append(CHAPTER_SEPARATOR);
		stringBuilder.append(crawlAChapterText(url));
		stringBuilder.append(CHAPTER_SEPARATOR);

		return stringBuilder.toString();
	}

	private String crawlAChapterName(String url) throws IOException {
		/*
		 * 	获取整个页面
		 * 	查询到章节标题所在的元素
		 * 	取出标题，返回
		 */
		Document page = Jsoup.connect(url).userAgent(_data.userAgent).get();

		Element chapterNameElement = page.selectFirst(_data.chapterNameQuery);

		return chapterNameElement.text();
	}

	private String crawlAChapterText(String url) throws IOException {
		/*
		 * 	获取整个页面
		 * 	筛选出有正文的元素
		 * 	从正文元素里取出正文
		 * 	返回
		 */
		Document chapterPage = Jsoup.connect(url).userAgent(_data.userAgent).get();

		Element textElement = chapterPage.selectFirst(_data.textQuery);

		StringBuilder stringBuilder = new StringBuilder();

		for (TextNode textNode : textElement.textNodes()) {
			stringBuilder.append(textNode.text());
			stringBuilder.append(LINE_SEPARATOR);
		}

		return stringBuilder.toString();
	}
}

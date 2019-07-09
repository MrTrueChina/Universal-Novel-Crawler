package org.mtc.crawler;

import java.util.LinkedList;
import java.util.List;

/**
 * 通用型爬虫的小说暂存类
 */
public class NovelContainer {
	/**
	 * 链接 : 正文
	 */
	private List<Chapter> _chapters = new LinkedList<Chapter>();
	/**
	 * 准备爬取的章节的索引
	 */
	private int _readyToCrawlChapterIndex = 0;
	/**
	 * 准备写入txt的章节的索引
	 */
	private int _readyToWriteChapterIndex = 0;

	public NovelContainer(List<String> urls) {
		
		for (String url : urls)
			_chapters.add(new Chapter(url));
	}

	/**
	 * 获取最靠前的未分配爬取的章节的网址
	 * 
	 * @return
	 */
	public synchronized String getChapterUrl() {

		try {
			return _chapters.get(_readyToCrawlChapterIndex).url;
		} finally {
			_readyToCrawlChapterIndex++;
		}
	}

	/**
	 * 获取最靠前的、已爬取的、未分配写入的，章节的正文
	 * 
	 * @return
	 */
	public synchronized String getChapterText() {

		try {
			return _chapters.get(_readyToWriteChapterIndex).text;
		} finally {
			_readyToWriteChapterIndex++;
			_chapters.get(_readyToWriteChapterIndex).text = null;
		}
	}
	
	/**
	 * 将正文存入容器中指定url的位置
	 * @param url
	 * @param text
	 */
	public void setChapterText(String url,String text) {
		
	}
}

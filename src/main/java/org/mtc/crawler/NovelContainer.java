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
	/**
	 * 是否处于写入状态，如果还有章节没有被写入线程取走则认为处于写入状态
	 */
	private boolean _writing = true;

	public NovelContainer(List<String> urls) {

		for (String url : urls)
			_chapters.add(new Chapter(url));

		for (int i = 0; i < _chapters.size(); i++)
			System.out.println(i + " " + _chapters.get(i));

//		for (Chapter chapter : _chapters) {
//			System.out.println(chapter);
//		}
	}

	/**
	 * 获取最靠前的未分配爬取的章节的网址
	 * 
	 * @return
	 */
	public synchronized String getChapterUrl() {

		if (_readyToCrawlChapterIndex >= _chapters.size())
			return null;

		try {
			return _chapters.get(_readyToCrawlChapterIndex).url;
		} finally {
			_readyToCrawlChapterIndex++;
		}
	}

	/**
	 * 将正文存入容器中指定url的位置
	 * 
	 * @param url
	 * @param text
	 */
	public synchronized void setChapterText(String url, String text) {
		if (url == null)
			System.out.println("null");
		for (Chapter chapter : _chapters) {
			
			if(chapter == null)
				continue;

			if (chapter.url == null)
				System.out.println("Chapter Null!");

			if (url == null)
				System.out.println("URL Null!");

			if (chapter.url.equals(url)) {
				System.out.println("存入url为 " + url + " 的正文");
				chapter.text = text;
				return;
			}
		}
	}

	/**
	 * 获取最靠前的、已爬取的、未分配写入的，章节的正文
	 * 
	 * @return
	 */
	public synchronized String getChapterText() {
		if (_readyToWriteChapterIndex >= _chapters.size())
			return null;

		if (_chapters.get(_readyToWriteChapterIndex).text == null)
			return null;
		System.out.println("_readyToWriteChapterIndex = " + _readyToWriteChapterIndex);

		try {
			return _chapters.get(_readyToWriteChapterIndex).text;
		} finally {
			System.out.println(
					"移除下标为 " + _readyToWriteChapterIndex + " 的，url为 " + _chapters.get(_readyToWriteChapterIndex).url
							+ " 的章节：");
			_chapters.set(_readyToWriteChapterIndex, null);
			_readyToWriteChapterIndex++;

			if (_readyToWriteChapterIndex >= _chapters.size())
				_writing = false;
		}
	}

	public boolean isWriting() {
		return _writing;
	}
}

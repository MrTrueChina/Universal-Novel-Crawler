package org.mtc.crawler.crawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.nodes.Document;
import org.mtc.crawler.data.CrawlerData;

/**
 * 	通用型爬虫负责将小说写入txt的类
 */
public class Writer extends Thread {

	/**
	 * 	爬取设置
	 */
	private CrawlerData _data;
	/**
	 * 	章节容器
	 */
	private NovelContainer _container;
	/**
	 * 	将正文输入到txt中的流
	 */
	private PrintWriter _writer;

	public Writer(CrawlerData data, NovelContainer container) {
		_data = data;
		_container = container;
	}

	@Override
	public void run() {
		/*
		 * 	创建写入流
		 * 	进入写入循环
		 * 	关闭写入流
		 */
		System.out.println("写入线程 " + Thread.currentThread().getName() + " 启动");

		try {
			createAndSetWriter();
			write();
			closeWriter();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void createAndSetWriter() throws FileNotFoundException, IOException {
		/*
		 * 	创建文件夹
		 * 	创建写入流
		 */
		createDir();
		doCreateWriter();
	}

	private void createDir() {

		File dir = new File(_data.savePath);
		if (!dir.exists())
			dir.mkdirs();
	}

	private void doCreateWriter() throws IOException, FileNotFoundException {
		/*
		 *	抓取小说首页
		 *	获取标题
		 *	根据标题创建写入流
		 */

		Document mainPage = Connector.connect(_data.mainPageUrl,_data); // 获取首页整个页面

		String bookName = mainPage.select(_data.bookNameQuery).text();
		// Elements Document.select(String cssQuery)：选择所有指定的元素，参数和 CSS、JQuery 的选择器格式相同
		// ".bookname h1" ：CSS 的选择器，意思是： "选择类名为 bookname 的 div 标签内部的 h1 标签"
		// Elements.text()：获取所有文本数据，文本数据就是不是HTML标签的数据

		_writer = new PrintWriter(_data.savePath + File.separator + bookName + ".txt");
	}

	private void write() {

		String text;
		while (_container.isWriting()) {
			text = _container.getChapterText();
			if (text != null) {
				_writer.print(text);
				_writer.flush();
			} else {
				// TODO：线程等待的方法
			}
		}
	}

	private void closeWriter() {
		_writer.close();
	}
}

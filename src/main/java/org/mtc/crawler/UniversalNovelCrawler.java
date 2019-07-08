package org.mtc.crawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

/**
 * 通用型小说爬虫中央控制类
 * 
 * @author MtC
 */
public class UniversalNovelCrawler {

	/**
	 * 爬虫设置
	 */
	private CrawlerData _data;
	/**
	 * 字符串的换行符
	 */
	private String _lineSeparator = System.getProperty("line.separator");
	/**
	 * 文件路径的间隔符
	 */
	private String _fileSeparator = File.separator;
	/**
	 * 网站域名
	 */
	private String _host;
	/**
	 * 把小说写进txt的写入流
	 */
	private PrintWriter _writer;

	public UniversalNovelCrawler(CrawlerData data) {
		_data = data;
	}

	/**
	 * 开始按照设置爬取
	 * @throws IOException
	 */
	public void doCrawler() throws IOException {
		/*
		 * 	获取并保存域名
		 *	创建写入流（此时txt文件就有了）
		 *	写入正文
		 *	关闭流
		 */
		getAndSetHost();
		createWriter();
		crawlText();
		_writer.close();

		System.out.println("完成，存储在 " + _data.savePath);
	}

	private void getAndSetHost() throws MalformedURLException {
		_host = _data.agreement + new URL(_data.mainPageUrl).getHost();
	}

	private void createWriter() throws IOException {
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

		Document mainPage = getDocument(_data.mainPageUrl); // 获取首页整个页面

		String bookName = mainPage.select(_data.bookNameQuery).text();
		// Elements Document.select(String cssQuery)：选择所有指定的元素，参数和 CSS、JQuery 的选择器格式相同
		// ".bookname h1" ：CSS 的选择器，意思是： "选择类名为 bookname 的 div 标签内部的 h1 标签"
		// Elements.text()：获取所有文本数据，文本数据就是不是HTML标签的数据

		_writer = new PrintWriter(_data.savePath + _fileSeparator + bookName + ".txt");
	}

	private Document getDocument(String url) throws IOException {

		return Jsoup.connect(url).userAgent(_data.userAgent).get();
		// Connection Jsoup.connect(String url)：建立和指定的页面的连接
		// Connection Connection.userAgent(String
		// userAgent)：存入用户代理信息，这个方法的巧妙之处在于他返回调用对象本身，这就使得他可以接在别的方法前面而不用另起一行
		// Document Connection.get()：发送 GET 请求，返回网站返回的信息
	}

	private void crawlText() throws IOException {
		/*
		 * 	遍历所有章节链接
		 * 	{
		 * 		爬取章节
		 * 	}
		 */
		for (String url : getChapterPaths())
			crawlAChapter(url);
	}

	private List<String> getChapterPaths() throws IOException {
		/*
		 * 	抓取首页
		 * 	筛选出所有有章节链接的元素
		 * 	取出他们的属性并存入列表里
		 * 	返回列表
		 */
		List<String> urls = new ArrayList<String>();

		Document mainPage = getDocument(_data.mainPageUrl);

		Elements chapterElements = mainPage.select(_data.chapterQuery);

		for (Element element : chapterElements)
			urls.add(_host + element.attr("href"));

		return urls;
	}

	private void crawlAChapter(String url) throws IOException {
		/*
		 * 	爬取章节标题
		 * 	爬取章节正文
		 * 	写入章节间的间隔
		 */
		long lastTime=System.currentTimeMillis();
		crawlAChapterName(url);
		System.out.println("爬取章节标题，耗时 " + (System.currentTimeMillis()-lastTime) + " 毫秒");
		lastTime=System.currentTimeMillis();
		crawlAChapterText(url);
		System.out.println("爬取章节正文，耗时 " + (System.currentTimeMillis()-lastTime) + " 毫秒");
		writeChapterSeparator();
	}

	private void crawlAChapterName(String url) throws IOException {
		/*
		 * 	获取整个页面
		 * 	查询到章节标题所在的元素
		 * 	取出标题，写入
		 */
		Document page = getDocument(url);

		Element chapterNameElement = page.selectFirst(_data.chapterNameQuery);

		_writer.println(chapterNameElement.text() + _lineSeparator);
		
		System.out.println("开始爬取章节 " + chapterNameElement.text());
	}

	private void crawlAChapterText(String url) throws IOException {
		/*
		 * 	获取整个页面
		 * 	筛选出有正文的元素
		 * 	从正文元素里取出正文
		 * 	通过写入流写入
		 */
		Document chapterPage = getDocument(url);

		Element textElement = chapterPage.selectFirst(_data.textQuery);

		List<TextNode> textNodes = textElement.textNodes();
		
		writeTextNodes(textNodes);
	}
	
	private void writeTextNodes(List<TextNode> textNodes) {
		
		long lastTime=System.currentTimeMillis();
		
		StringBuilder stringBuilder = new StringBuilder();
		
		for (TextNode textNode : textNodes) {
			stringBuilder.append(textNode.text());
			stringBuilder.append(_lineSeparator);
		}
		
		_writer.print(stringBuilder.toString().replaceAll("&nbsp;", " "));
		
		_writer.flush();
		
		System.out.println("写入正文，耗时 " + (System.currentTimeMillis()-lastTime) + " 毫秒");
	}

	private void writeChapterSeparator() {
		_writer.print(_lineSeparator + _lineSeparator);
	}
}

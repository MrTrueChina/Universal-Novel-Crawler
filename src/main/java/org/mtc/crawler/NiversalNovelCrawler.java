package org.mtc.crawler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;

/**
 * 通用型小说爬虫
 * 
 * @author MtC
 */
public class NiversalNovelCrawler {

	private CrawlerData _data;

	private String _separator = System.getProperty("line.separator");

	private PrintWriter _writer;

	/**
	 * 爬取过的url
	 */
	private List<String> usedUrl = new ArrayList<String>();

	public NiversalNovelCrawler(CrawlerData data) {
		_data = data;
	}

	/**
	 * 
	 * 
	 * @param startUrl 要爬取的小说的第一页
	 * @param savePath 要存储到的文件夹
	 * @throws IOException
	 */
	public void doCrawler() throws IOException {
		/*
		 * 创建写入流 
		 * 写入正文
		 */
		createWriter();
		crawlText();
		_writer.close();

		System.out.println("完成");
	}

	private void createWriter() throws IOException {
		Document startPage = Jsoup.connect(_data.startUrl).userAgent(_data.userAgent).get(); // 获取第一页整个页面
		// Connection Jsoup.connect(String url)：建立和指定的页面的连接
		// Connection Connection.userAgent(String
		// userAgent)：存入用户代理信息，这个方法的巧妙之处在于他返回调用对象本身，这就使得他可以接在别的方法前面而不用另起一行
		// Document Connection.get()：发送 GET 请求，返回网站返回的信息

		String bookName = startPage.select("div.bookname h1").text();
		// Elements Document.select(String cssQuery)：选择所有指定的元素，参数和 CSS、JQuery 的选择器格式相同
		// ".bookname h1" ：CSS 的选择器，意思是： "选择类名为 bookname 的 div 标签内部的 h1 标签"
		// Elements.text()：获取所有文本数据，文本数据就是不是HTML标签的数据

		_writer = new PrintWriter(_data.savePath + "\\" + bookName + ".txt");
	}

	private void crawlText() throws IOException {
		/*
		 * 	while(这一页还能爬)
		 * 	{
		 * 		爬这一页的文本
		 * 		获取下一页的url
		 * 	}
		 */
		Document currentPageDocument = Jsoup.connect(_data.startUrl).userAgent(_data.userAgent).get();

		while (pageCanCrawl(currentPageDocument)) {
			crawlAPageText(currentPageDocument);
			currentPageDocument = getNextPageDocument(currentPageDocument);
		}
	}

	private boolean pageCanCrawl(Document pageDocument) {

		if (pageDocument == null)
			return false;

		if (getTextElements(pageDocument) != null)
			return true;

		return false;
	}

	private void crawlAPageText(Document pageDocument) {
		/*
		 * 	遍历(获取到目标文本的TextNode)
		 * 		输出一个TextNode
		 * 	输出页面结束的换行
		 */
		for (TextNode texeNode : getTextNodes(pageDocument))
			writeATextNode(texeNode);
		_writer.println(_separator+_separator); //TODO：这里建立在一页一章的前提上，如果一页多章则需要获取章节名的方法
	}

	private List<TextNode> getTextNodes(Document pageDocument) {
		Element textElement = getTextElements(pageDocument);

		if (textElement != null)
			return textElement.textNodes();
		// Element.textNodes()：获取元素内所有的文本节点，文本节点是封装文本的对象，文本则是HTML中不是标签的部分，也就是普通的文字

		return new ArrayList<TextNode>();
	}

	private Element getTextElements(Document pageDocument) {
		return pageDocument.selectFirst("#content");
	}

	private void writeATextNode(TextNode textNode) {
		
		String printString = textNode.text().replace("&nbsp;", " ") + _separator;
		
		_writer.print(printString);
		_writer.flush();
	}

	private Document getNextPageDocument(Document pageDocument) {
		String nextPageUrl = pageDocument.select("div.bottem2 a:matches(^下一章$)").attr("abs:href");
		// div.boottem2 a:matches(^下一章$)
		// "div.boottem2 a" ：这部分简单，选择类名是"boottem2"的div内部的a标签
		// ":matches(regex)" ：伪选择器之一，用于【查找拥有和指定的正则表达式匹配的文本】的元素
		// ":macthes(^下一章&)" ：匹配以"下一章"开头、以"下一章"结尾、内容是"下一章"的文本，也就是匹配"下一章"三个字
		
		try {
			return Jsoup.connect(nextPageUrl).userAgent(_data.userAgent).get();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}

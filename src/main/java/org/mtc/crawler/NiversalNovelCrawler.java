package org.mtc.crawler;

/**
 * 通用型小说爬虫
 * 
 * @author MtC
 */
public class NiversalNovelCrawler {
	
	private String _spector = System.getProperty("line.spector");
	
	public static void main(String[] args) {
		
		final String startUrl="http://www.shushuwuxs.org/5_5348/121235.html";
		
		final String savePath=""; 
		
		new NiversalNovelCrawler().doCrawler(startUrl, savePath);
	}
	
	/**
	 * 
	 * 
	 * @param startUrl 要爬取的小说的第一页
	 * @param savePath 要存储到的文件夹
	 */
	public void doCrawler(String startUrl,String savePath) {
		
	}
}

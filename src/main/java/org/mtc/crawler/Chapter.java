package org.mtc.crawler;

/**
 * 	章节，包含网址和正文
 */
public class Chapter {

	/**
	 * 	章节网址
	 */
	public String url;
	/**
	 * 	章节正文
	 */
	public String text;

	public Chapter(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Chapter [url=" + url + ", text=" + text + "]";
	}
}

package org.mtc.crawler;

/**
 * 	网站上的url的类型
 */
public enum UrlType {
	/**
	 * 	相对于host（网站总网址/url）（www.abc.com/url）
	 */
	RELATIVE_TO_HOST,
	/**
	 * 	相对于所在网页（网页网址/url）（www.abc.com/page/url）
	 */
	RELATIVE_TO_PAGE,
}

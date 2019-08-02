package test;

import java.io.IOException;
import java.util.logging.Level;

import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.AjaxController;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ConnectTest {
	public static void main(String[] args) throws IOException {

//		System.out.println("开始连接");
//
//		Document document = Jsoup.connect("http://www.pixiv.net/novel/series/1014714?p=1").timeout(10000)
//				.proxy("127.0.0.1", 4580).get();
//
//		System.out.println(document);

		LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",
				"org.apache.commons.logging.impl.NoOpLog");
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);

		String url = "http://www.pixiv.net/novel/series/1014714?p=1";
		
		System.out.println("开始模拟");

		// HtmlUnit 模拟浏览器
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		
		webClient.getOptions().setJavaScriptEnabled(true); // 启用JS解释器，默认为true
		webClient.getOptions().setCssEnabled(false); // 禁用css支持
		webClient.getOptions().setThrowExceptionOnScriptError(false); // js运行错误时，是否抛出异常
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setTimeout(10000); // 设置连接超时时间
		webClient.getOptions().setProxyConfig(new ProxyConfig("127.0.0.1", 4580)); // 设置代理
		
		HtmlPage page = webClient.getPage(url);
		
		webClient.waitForBackgroundJavaScript(30000); // 等待js后台执行
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		
		System.out.println("获取网页结束");

		String pageAsXml = page.asXml(); // 将网页转为 xml

		// Jsoup解析处理
		Document doc = Jsoup.parse(pageAsXml, "https://bluetata.com/"); // 将 xml 字符串解析为 Document
		
		System.out.println(doc);
	}
}

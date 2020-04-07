import org.openqa.selenium.PageLoadStrategy
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.chrome.ChromeDriver

import net.ruippeixotog.scalascraper.browser.JsoupBrowser

object TestingScrap {
  def main(args: Array[String]): Unit = {
    val baseUrl = "https://kambista.com/"
    val browser = JsoupBrowser()
    val doc     = browser.get(baseUrl)
    println(doc)

    val chromeOptions = new ChromeOptions()
    chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL)
    chromeOptions.setHeadless(true)
    val driver = new ChromeDriver(chromeOptions)
    println(driver)
  }
}

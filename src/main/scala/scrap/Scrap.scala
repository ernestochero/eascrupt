package scrap

import net.ruippeixotog.scalascraper.browser.{ Browser, JsoupBrowser }
import org.openqa.selenium.PageLoadStrategy
import org.openqa.selenium.chrome.{ ChromeDriver, ChromeOptions }

sealed trait ScrapT {
  def browser: Browser = JsoupBrowser()
  def driver: ChromeDriver = {
    val chromeOptions = new ChromeOptions()
    chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL)
    chromeOptions.setHeadless(true)
    new ChromeDriver(chromeOptions)
  }
  def baseUrl: String
}

trait Scrap[T] extends ScrapT

object Scrap {
  // generics validations
  def verifyUrl(url: String): Boolean = true
}

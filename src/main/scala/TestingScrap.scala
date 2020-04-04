import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.model._
object TestingScrap {
  def main(args: Array[String]): Unit = {
    val baseUrl = "https://elcomercio.pe"
    val browser = JsoupBrowser()
    val doc     = browser.get(s"$baseUrl/noticias/coronavirus/")
    println(doc)
    /*    val storyItemList = doc >> elements(".story-item")
    val storyItemContentTitle =
      storyItemList.map(
        _ >> element(".story-item__content-title") >> element("a") >> (attr("href"), text("a"))
      )
    val mapped = storyItemContentTitle.map {
      case (url, summary) => (baseUrl + url, summary)
    }.toMap
    mapped.keys.foreach(println(_))*/
  }
}

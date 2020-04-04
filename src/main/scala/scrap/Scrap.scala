package scrap
import net.ruippeixotog.scalascraper.browser.{ Browser, JsoupBrowser }
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.model._

trait ScrapT {
  def browser: Browser = JsoupBrowser()
  def baseUrl: String
  def doc: Document
}

trait ScrapOperation {
  def getSomething: Map[String, String]
  // put here more operations to get from web page ...
}

trait Scrap[T] extends ScrapT with ScrapOperation

// Adding case class for other sources
case class ScrapComercio()
case class ScrapLaRepublica()

object ScrapComercio {
  implicit class ScrapComerciOps(scrap: ScrapComercio) extends Scrap[ScrapComercio] {
    override def baseUrl: String = "https://www.elcomercio.pe"
    override def doc: Document   = browser.get(s"$baseUrl/noticias/coronavirus/")
    override def getSomething: Map[String, String] = {
      val storyItemList = doc >> elements(".story-item")
      val storyItemContentTitle =
        storyItemList.map(
          _ >> element(".story-item__content-title") >> element("a") >> (attr("href"), text("a"))
        )
      val mapped = storyItemContentTitle.map {
        case (url, summary) => (baseUrl + url, summary)
      }.toMap
      mapped
    }
  }
}

object ScrapLaRepublica {
  implicit class ScrapLaRepublicaOps(scrap: ScrapLaRepublica) extends Scrap[ScrapLaRepublica] {
    override def baseUrl: String                   = "https://larepublica.pe"
    override def doc: Document                     = browser.get(s"$baseUrl/tag/covid-19/")
    override def getSomething: Map[String, String] = Map("key" -> "value")
  }
}

object Scrap {
  // generics validations
  def verifyUrl(url: String): Boolean = true
}

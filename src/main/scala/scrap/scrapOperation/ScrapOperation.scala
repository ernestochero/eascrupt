package scrap.scrapOperation

import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model._
import scrap.Scrap

trait ScrapOperation {
  def getSomething: Map[String, String]
}

case class ScrapComercio()
case class ScrapLaRepublica()

object ScrapComercio {
  implicit class ScrapComerciOps(scrap: ScrapComercio)
      extends Scrap[ScrapComercio]
      with ScrapOperation {
    override def baseUrl: String = "https://www.elcomercio.pe"
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
    def doc: Document = browser.get(s"$baseUrl/noticias/coronavirus/")
  }
}

object ScrapLaRepublica {
  implicit class ScrapLaRepublicaOps(scrap: ScrapLaRepublica)
      extends Scrap[ScrapLaRepublica]
      with ScrapOperation {
    override def baseUrl: String                   = "https://larepublica.pe"
    override def getSomething: Map[String, String] = Map("key" -> "value")
    def doc: Document                              = browser.get(s"$baseUrl/tag/covid-19/")
  }
}

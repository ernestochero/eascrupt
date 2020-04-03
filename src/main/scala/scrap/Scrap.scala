package scrap
import net.ruippeixotog.scalascraper.browser.Browser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.model._

case class Scrap(browser: Browser, url: String)
trait ScrapT {
  def getMetaContentAttr: String
  def createDocument: Option[Document]
}
object Scrap {
  def verifyUrl(url: String): Boolean = true
  implicit class ScrapOps(scrap: Scrap) extends ScrapT {
    val browser: Browser = scrap.browser
    val url: String = scrap.url
    override def getMetaContentAttr: String =
      createDocument.fold("error")(doc => {
        println(doc)
        val metaElements = doc >> elements("head meta")
        val metaElementsContent = metaElements.map(_ >?> attr("content"))
        metaElementsContent.mkString(",")
      })
    override def createDocument: Option[Document] =
      if (verifyUrl(url)) Some(browser.get(url))
      else None
  }
}
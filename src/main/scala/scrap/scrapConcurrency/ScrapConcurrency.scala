package scrap.scrapConcurrency

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.By
import scrap.Scrap
import zio._
trait ScrapConcurrency {
  def getDollarPrice: ZIO[Any, Throwable, Map[String, String]]
}

object ScrapConcurrency {
  def showInformation(shopName: String, price: Map[String, String]): Unit = {
    val buyPrice  = price("buy")
    val sellPrice = price("sell")
    println(s"[$shopName Prices] = { buy: $buyPrice , sell: $sellPrice}")
  }
}

case class ScrapKambista()
case class ScrapRextie()
object ScrapKambista {
  implicit class ScrapKambistaOps(scrap: ScrapKambista)
      extends Scrap[ScrapKambista]
      with ScrapConcurrency {
    override def baseUrl: String = "https://kambista.com/"
    override def getDollarPrice: ZIO[Any, Throwable, Map[String, String]] = {
      val closeDriver      = ZIO.effectTotal(driver.quit())
      val fetchInformation = ZIO.effect(fetchingKambistaInformation(driver))
      fetchInformation.ensuring(closeDriver)
    }

    def fetchingKambistaInformation(driver: ChromeDriver): Map[String, String] = {
      driver.get(baseUrl)
      val divSelector = driver.findElement(
        By.xpath(
          "/html/body/header/div/div[2]/div[3]/div[2]/div[2]/div[1]/div"
        )
      )
      val buyPrice  = divSelector.findElement(By.id("valcompra")).getText
      val sellPrice = divSelector.findElement(By.id("valventa")).getText
      Map(
        "sell" -> sellPrice,
        "buy"  -> buyPrice
      )
    }
  }
}

object ScrapRextie {
  implicit class ScrapKambistaOps(scrap: ScrapRextie)
      extends Scrap[ScrapRextie]
      with ScrapConcurrency {
    override def baseUrl: String = "https://www.rextie.com/"
    override def getDollarPrice: ZIO[Any, Throwable, Map[String, String]] = {
      val closeDriver      = ZIO.effectTotal(driver)
      val fetchInformation = ZIO.effect(fetchingRextieInformation(driver))
      fetchInformation.ensuring(closeDriver)
    }
    def fetchingRextieInformation(driver: ChromeDriver): Map[String, String] = {
      driver.get(baseUrl)
      val buyPrice = driver
        .findElement(
          By.xpath(
            "/html/body/app-root/app-home-layout/div[2]/div/app-home-page/app-save-money-partial/div/div/div[1]/div[2]/app-fx-rates/div/div/div[1]/div/div[1]/div[2]/span[2]"
          )
        )
        .getText
      val sellPrice = driver
        .findElement(
          By.xpath(
            "/html/body/app-root/app-home-layout/div[2]/div/app-home-page/app-save-money-partial/div/div/div[1]/div[2]/app-fx-rates/div/div/div[1]/div/div[2]/div[2]/span[2]"
          )
        )
        .getText
      Map(
        "sell" -> sellPrice,
        "buy"  -> buyPrice
      )
    }
  }
}

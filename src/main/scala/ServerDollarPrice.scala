import zio._
import zio.ZIO
import commons.Helper._
import zio.console._
import modules.scrapModule._
import scrap.scrapConcurrency.ScrapConcurrency.showInformation
import scrap.scrapConcurrency.{ ScrapKambista, ScrapRextie }

object ServerDollarPrice extends App {
  val appRunTime: EnvironmentType = Runtime.unsafeFromLayer(liveEnvironments, platform).environment
  val services: ZIO[EnvironmentType, Throwable, Unit] =
    for {
      _             <- putStr("Fetching information [$ price] ... ")
      kambistaPrice <- dollarPrice(ScrapKambista())
      rextiePrice   <- dollarPrice(ScrapRextie())
      _ = showInformation("Kambista", kambistaPrice)
      _ = showInformation("Rextie", rextiePrice)
    } yield ()
  override def run(args: List[String]): ZIO[zio.ZEnv, Nothing, Int] =
    services.provide(appRunTime).fold(_ => 1, _ => 0)
}

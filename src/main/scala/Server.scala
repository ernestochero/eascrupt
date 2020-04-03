import zio._
import zio.ZIO
import commons.Helper._
import zio.console._
import modules.scrapModule._
import modules.kafkaProducerModule._
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import scrap._
object Server extends App {
  val appRunTime: EnvironmentType = Runtime.unsafeFromLayer(liveEnvironments, platform).environment
  implicit val scrap: Scrap = Scrap(JsoupBrowser(), "https://elcomercio.pe/")
  val services: ZIO[EnvironmentType, Throwable, Unit] =
    for {
      _ <- putStr("init the application")
      attr <- bodyAttr
      _ <- putStr(s"Body Attr $attr")
      _ <- writeToKafka("eascrupt", "[scrupt]", attr)
    } yield ()
  override def run(args: List[String]): ZIO[zio.ZEnv, Nothing, Int] =
    services.provide(appRunTime).fold(_ => 1, _ => 0)
}

import zio._
import zio.ZIO
import commons.Helper._
import zio.console._
import modules.scrapModule._
import modules.kafkaProducerModule._

import scrap.scrapOperation.ScrapComercio
object ServerToKafka extends App {
  val appRunTime: EnvironmentType = Runtime.unsafeFromLayer(liveEnvironments, platform).environment
  val services: ZIO[EnvironmentType, Throwable, Unit] =
    for {
      _    <- putStr("init the application")
      attr <- someInformation(ScrapComercio())
      _    <- putStr(s"Body Attr $attr")
      _    <- writeToKafka("eascrupt", "[scrupt]", attr.mkString(","))
    } yield ()
  override def run(args: List[String]): ZIO[zio.ZEnv, Nothing, Int] =
    services.provide(appRunTime).fold(_ => 1, _ => 0)
}

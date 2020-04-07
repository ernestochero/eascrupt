package modules

import scrap.scrapConcurrency.ScrapConcurrency
import scrap.scrapOperation.ScrapOperation
import zio.{ Has, ZIO, ZLayer }
package object scrapModule {
  type ScrapModuleType = Has[ScrapModule.Service]
  object ScrapModule {
    trait Service {
      def someInformation(
        scrapOperation: ScrapOperation
      ): ZIO[ScrapModuleType, Nothing, Map[String, String]]

      def dollarPrice(
        scrapConcurrency: ScrapConcurrency
      ): ZIO[ScrapModuleType, Throwable, Map[String, String]]
    }
    val live: ZLayer.NoDeps[Nothing, ScrapModuleType] = {
      ZLayer.succeed {
        new Service {
          override def someInformation(
            scrapOperation: ScrapOperation
          ): ZIO[ScrapModuleType, Nothing, Map[String, String]] =
            ZIO.succeed(
              scrapOperation.getSomething
            )
          override def dollarPrice(
            scrapConcurrency: ScrapConcurrency
          ): ZIO[ScrapModuleType, Throwable, Map[String, String]] =
            scrapConcurrency.getDollarPrice
        }
      }
    }
  }
  def someInformation(
    scrapOperation: ScrapOperation
  ): ZIO[ScrapModuleType, Nothing, Map[String, String]] =
    ZIO.accessM[ScrapModuleType](_.get.someInformation(scrapOperation))

  def dollarPrice(
    scrapConcurrency: ScrapConcurrency
  ): ZIO[ScrapModuleType, Throwable, Map[String, String]] =
    ZIO.accessM[ScrapModuleType](_.get.dollarPrice(scrapConcurrency))

}

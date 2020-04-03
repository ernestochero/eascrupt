package modules
import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import zio.{Has, ZIO, ZLayer}

package object kafkaProducerModule {
  type KafkaProducerType = Has[KafkaProducerModule.Service]
  object KafkaProducerModule {
    trait Service {
      def writeToKafka(topic: String, key: String, value: String): ZIO[KafkaProducerType, Nothing, Unit]
    }
    val live: ZLayer.NoDeps[Nothing, KafkaProducerType] = {
      ZLayer.succeed {
        new Service {
          override def writeToKafka(topic: String, key: String, value: String): ZIO[KafkaProducerType, Nothing, Unit] = {
            ZIO.succeed {
              val props = new Properties()
              props.put("bootstrap.servers", "localhost:9092")
              props.put(
                "key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer"
              )
              props.put(
                "value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer"
              )
              val producer = new KafkaProducer[String, String](props)
              val record = new ProducerRecord[String, String]( topic, key, value)
              producer.send(record)
              producer.close()
            }
          }
        }
      }
    }
  }

  def writeToKafka(topic: String, key: String, value: String): ZIO[KafkaProducerType, Nothing, Unit] =
    ZIO.accessM[KafkaProducerType](_.get.writeToKafka(topic, key, value))

}

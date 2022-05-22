package intro

import zio._
import zio.console._

object CombiningEffects extends zio.App {

  override def run(args: List[String]): URIO[Console, ExitCode] = {

    //    (for {
    //      _ <- putStrLn("Hello")
    //      _ <- putStrLn("World")
    //    } yield ()).exitCode // same as what's written on line 15

    // *> is same as zipLeft
    //    (putStrLn("Helllllo") *> putStrLn("World!"))

    // zipLeft does the first half first and then the second half

//    (putStrLn("Hello") zipLeft
//      putStrLn("World!") zipLeft
//      putStrLn("Welcome to ") zipLeft
//      putStrLn("ZIO!")).exitCode

//    val zioStr = for {
//      strTup <- ZIO.succeed(("str", "ing"))
//      str    <- ZIO.succeed(strTup._1 + strTup._2)
//      _      <- putStrLn(str)
//    } yield ()
//
//    zioStr.exitCode

    val res: ZIO[Any, Nothing, String] = ZIO.succeed(("str", "ing")).flatMap { tup =>
      combine(tup).flatMap { concat =>
        ZIO.succeed(concat.toUpperCase).map { upper =>
          upper + upper
        }
      }
    }

    val result: ZIO[Console, java.io.IOException, Unit] = for {
      tup <- ZIO.succeed(("Scala", "rocks!"))
      _   <- putStrLn(s"initial input = ${tup._1} and ${tup._2}")
      str <- ZIO.succeed(tup._1 concat tup._2)
      upp <- ZIO.succeed(str.toUpperCase)
      _   <- putStrLn(s"The result is $upp")
    } yield ()

    result.exitCode
    
  }

  def combine(tup: (String, String)): ZIO[Any, Nothing, String] =
    ZIO.succeed(tup._1 concat tup._2)

}

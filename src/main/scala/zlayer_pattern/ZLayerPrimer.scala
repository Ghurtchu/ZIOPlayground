package zlayer_pattern

import zio.*

object ZLayerPrimer extends zio.App {

  // ZIO[R, E, A]
  // R => environment, E = failure, A = success
  // ZIO => "functional effects" => wrappers around functional effects,
  // data structures that describe effects, by name params are heavily used for laziness.
  // ZIO instances are monadic types (flatMap, map)

  val meaningOfLife: UIO[Int] = ZIO.succeed(42)
  val failure: ZIO[Any, String, Nothing] = ZIO.fail("Something went wrong")

  // ZIO instance whose result value is Unit
  val greeting: ZIO[zio.console.Console, java.io.IOException, Unit] = for {
    _      <- zio.console.putStrLn("Hi, what's up?")
    answer <- zio.console.getStrLn // sky
    _      <- zio.console.putStrLn(s"Up is the $answer")
  } yield ()

  override def run(args: List[String]): URIO[zio.console.Console, ExitCode] =
    greeting.exitCode

}

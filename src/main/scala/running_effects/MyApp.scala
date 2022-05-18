package running_effects

// ZIO provides several different ways of running effects in your application.

// If you construct a single effect for you whole program, then the most natural way to run the effect is to
// extend zio.App

// this class provides Scala with a main function, so it can be called from IDEs and launched from the command-line
// All you have to do is implement the run method, which will be passed all command-line arguments in a List:

import zio._
import zio.console._

object MyApp extends zio.App {

  val program: ZIO[Console, java.io.IOException, Unit] = for {
    _ <- putStrLn("Hello, Who are you?")
    handle <- getStrLn
    _ <- putStrLn(s"Hello $handle, welcome to ZIO!")
  } yield ()

  override def run(args: List[String]): URIO[Console, ExitCode] = program.exitCode

  // If we are using a custom env for my app, we will have to supply our env to the effect using ZIO#provide
  // before returning it from run, because App does not know how to supply custom envs

}

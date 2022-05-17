package intro

import zio.console.{Console, getStrLn, putStrLn}
import zio.{ExitCode, Runtime, Task, URIO, ZIO}

import java.io.IOException

object GettingStarted:

  object MyApp extends zio.App {

    val myAppLogic: ZIO[Console, IOException, Unit] = for
      _ <- putStrLn("Hello! Welcome to ZIO Playground")
      _ <- putStrLn("What is your name?")
      userName <- getStrLn
      _ <- putStrLn(s"Nice to meet you $userName")
    yield ()

    override def run(args: List[String]): URIO[Console, ExitCode] = myAppLogic.exitCode

  }


  object IntegrationExample {

    val runtime = Runtime.default

    runtime.unsafeRun(Task(println("Hello world")))

  }

package basic_operations

import zio._
import zio.console._
import java.io.IOException

object ForComprehensions extends zio.App {

  // ZIO supports flatMap & map so it can be used in for comprehensions to build sequential effects
  // Example

  val program: ZIO[Console, IOException, Unit] = for {
    _ <- putStrLn("Hello, Do you love Scala?")
    _ <- putStrLn("answer 'yes' or 'no'")
    userAnswer <- getStrLn
    output <- ZIO.succeed(
      userAnswer.toLowerCase match
        case "yes" => "WOW, we'll be good friends"
        case "no" => "Why the F[_]?"
        case _ => "What?..."
    )
    _ <- putStrLn(s"I must say $output")
  } yield ()

  override def run(args: List[String]): URIO[Console, ExitCode] = program.exitCode
}

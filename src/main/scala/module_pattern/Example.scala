package module_pattern

package object Common {

  import Example._

  object ConsoleLogging {

    val live: Console with Logging = new Console with Logging:
      override def console: Console.Service = Console.live
      override def logging: Logging.Service = Logging.live


  }

}

object Example {

  def main(args: Array[String]): Unit = {
    moduleComposition(Common.ConsoleLogging.live).unsafeRun()
  }

  def moduleComposition(dependency: Console with Logging): IO[Unit] =
    for {
      _        <- dependency.console.putStrLn("Hello there, what is your name?")
      _        <- dependency.logging.log("I am logging some crap")
      userName <- dependency.console.getStrLn
      _        <- dependency.console.putStrLn(s"Welcome $userName, ZIO is going to change the world")
    } yield ()


  trait Logging {
    def logging: Logging.Service
  }

  object Logging {
    trait Service {
      def log(msg: String): IO[Unit]
    }

    val live = new Service {
      override def log(msg: String): IO[Unit] = IO(println(msg))
    }
  }


  trait Console {
    def console: Console.Service
  }

  object Console {
    trait Service {
      def putStrLn(line: String): IO[Unit]

      def getStrLn: IO[String]
    }

    val live = new Service {
      override def putStrLn(line: String): IO[Unit] = IO(println(line))

      override def getStrLn: IO[String] = IO(scala.io.StdIn.readLine())
    }
  }

  trait IO[+A] {

    def unsafeRun(): A

    def map[B](f: A => B): IO[B] = IO {
      f(unsafeRun())
    }

    def flatMap[B](f: A => IO[B]): IO[B] = IO {
      val a = unsafeRun()
      val iob = f(a)
      val b = iob.unsafeRun()
      b
    }

  }

  object IO {
    def apply[A](a: => A): IO[A] = () => a
  }


}

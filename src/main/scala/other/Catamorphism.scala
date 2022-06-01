package other

import scala.annotation.targetName

object Catamorphism:

  val program: IO[Unit] =
    for
      _        <- console.show("Hello, what is your name?")
      userName <- console.in
      _        <- console.show(s"Welcome $userName")
    yield ()

  def main(args: Array[String]): Unit = program.evaluate

  case class IO[+A](private val program: () => A):
    def evaluate: A = program()
    def map[B](mapper: A => B): IO[B] = IO(mapper(program()))
    def flatMap[B](flatMapper: A => IO[B]): IO[B] = IO(flatMapper(program()).program)

  object IO:
    @targetName("IO smart constructor")
    def apply[A](block: => A): IO[A] = new IO(() => block)

  object console:
    def show(line: String): IO[Unit] = IO(println(line))
    def in: IO[String] = IO(scala.io.StdIn.readLine())


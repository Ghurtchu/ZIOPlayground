package handling_errors

import zio.*

object EitherExample {

  // we can surface failures with Z#Eitehr, which takes ZIO[R, E, A] and produces
  // ZIO[R, Nothing, Either[E, A]].

  val zeither: UIO[Either[String, Int]] = IO.fail("Uh oh!").either

  // submerge failures with ZIO.absolve which is the opposite of either and turns
  // ZIO[R, Nothing, Either[E, A]] into ZIO[R, E, A]

  def sqrt(io: UIO[Double]): IO[String, Double] =
    ZIO.absolve(
      io.map(value =>
        if (value < 0.0) Left("Value must be >= 0.0")
        else Right(Math.sqrt(value))
      )
    )

}

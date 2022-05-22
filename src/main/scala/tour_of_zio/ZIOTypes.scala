package tour_of_zio

import zio.ZIO

object ZIOTypes {

  // Effect = Jon / Workflow / Task
  // ZIO[-R, +E, +A] ~ R => Either[E, A]

  type ??? = Nothing

  // Task does not need anything, can fail with any throwable and can succeed with any A
  type Task[+A] = ZIO[Any, Throwable, A]

  // UIO does not require anything, can not fail and can succeed with any type A
  // There are no values for type Nothing, so we know for sure that
  // UIO[A] = Right(value) of Either[E, A]
  type UIO[+A] = ZIO[Any, Nothing, A]
  
  // RIO refers to ZIO effect that requires environment of type R, may throw any throwable
  // and may succeed with type A
  type RIO[-R, +A] = ZIO[R, Throwable, A]


  // IO does not require anything, may throw with throwable of type E, may succeed with A
  type IO[+E, +A] = ZIO[Any, E, A]

  // URIO requires environment of R, does not fail, may succeed with type of A
  type URIO[-R, +A] = ZIO[R, Nothing, A]

}

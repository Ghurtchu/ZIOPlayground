package basic_concurrency

object ComposingFibers {

  // ZIO lets you compose fibers with Fiber#zip or Fiber#zipWith

  // These methods combine two fibers into a single fiber that produces the results of obth. If either fiber fails
  // then the composed fiber will fial.

  for {
    f1  <- zio.IO.succeed("Hi!").fork
    f2  <- zio.IO.succeed("Bye!").fork
    f   = f1.zip(f2)
    tup <- f.join
  } yield tup

  // another way fibers compose is with Fiber#orElse. If the first fiber succeeds the composed fiber
  // will succeed with its result; otherwise the composed fiber will complete with the exit value
  // of the second fiber.

  for {
    f1    <- zio.IO.fail("Uh oh!").fork
    f2    <- zio.IO.succeed("Hurray!").fork
    fiber =  f1.orElse(f2)
    tup   <- fiber.join
  } yield tup

}

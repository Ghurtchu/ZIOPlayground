package basic_concurrency

object Parallelism {

  // ZIO provides many operations for performing effects in parallel. These methods are all named with a `Par` suffix
  // that helps you identify opportunities to parallelize your code.

  // For example. the ordinary ZIO#zip method zips two effects together, sequentially. But there is also ZIO#zipPar method,
  // which zips two effects together in parallel.

  // for all the parallel operations, if one effect fails, then others will be interrupted, to minimize unnecessary computation.

  // if the fail-fast behaviour is not desired, potentially failing effects can be first converted into infallible effects using ZIO#either or ZIO#Option methods.

  object Racing {

    // ZIO lets you race multiple effects in parallel, returning the first successful result

    for {
      winner <- zio.IO.succeed("Hello").race(zio.IO.succeed("Goodbye"))
    } yield winner

    zio.IO.succeed("Hello").race(zio.IO.succeed("Goodbye")).map(identity)
  }

  object TimeOut {

    // ZIO lets you timeout any effect using the ZIO#timeout method, which returns a new effect that succeeds with an Option.
    // A value of None indicates the timeout elapsed before the effect completed.

    import zio.duration._

    zio.IO.succeed("Hello").timeout(10.seconds)

  }
}

package running_effects

object DefaultRuntime {

  // Most apps are not greenfield and must integrate with legacy code and procedural libraries and frameworks.
  // In these cases a better solution for running effects isto create a Runtime which can be passed around and used to run effects wherever required

  // ZIO contains a default runtime called Runtime.default. This Runtime bundles together production implementations of all ZIO modules
  // including (Console, System, Clock, Random, Scheduler and on the JVM, Blocking) and it can run effects that require any combination of these modules.

  // To access it, merely use

  val runtime = zio.Runtime.default

  // we can run

  runtime.unsafeRun(zio.ZIO.succeed(println("Hello world")))

  // in addition to the unsafeRun method, there are other methods that allow executing effects asynchronously or into Future values.

}

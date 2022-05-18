package running_effects

object CustomRuntime {

  // If you are using a custom env for your app, then you may find it useful to create a Runtime
  // specifically tailored for that env

  // A custom Runtime[R] can be created with two values:

  // 1) R - env. This is the env that will be provided to the effects when they are executed.
  // 2) Platform. This is a platform that is required by ZIO in order to bootstrap the runtime system.

  // e.g the following creates a Runtime that can provide an Int to effects, using the default Platform provided by ZIO:

  import zio.internal.Platform

  val myRuntime: zio.Runtime[Int] = zio.Runtime(42, Platform.default)

}

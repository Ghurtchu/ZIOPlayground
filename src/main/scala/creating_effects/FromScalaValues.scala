package creating_effects

import zio.*

object FromScalaValues {

  object OptionExamples {

    val zoption = ZIO.fromOption(Some(5))

    // change Option[Nothing] to more specific error
    val zoption2 = zoption.mapError(_ => "It wasn't there")

    val maybeId: IO[Option[Nothing], String] = ZIO.fromOption(Some("id"))
    def getUser(userId: String): IO[Throwable, Option[User]] = ???
    def getTeam(teamId: String): IO[Throwable, Team] = ???

    case class User(userId: String, teamId: String, name: String)
    case class Team(teamId: String, users: List[User])

    val result: IO[Throwable, Option[(User, Team)]] = (for {
      id <- maybeId
      user <- getUser(id).some
      team <- getTeam(user.teamId).asSomeError
    } yield (user, team)).optional

  }

  object EitherExamples {
    val zeither = ZIO.fromEither(Right("Success!"))
  }

  object TryExamples {
    val ztry = ZIO.fromTry(scala.util.Try(42 / 0))
  }

  object FunctionExamples {
    val zfun: URIO[Int, Int] = ZIO.fromFunction(_ * 2)
  }

  object FutureExamples {
    import scala.concurrent.Future

    lazy val future = Future.successful("Hello")

    val zfuture: Task[String] = ZIO.fromFuture(implicit ec => future.map(_ => "GoodBye!"))
  }

}

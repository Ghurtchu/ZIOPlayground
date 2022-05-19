package zlayer_pattern

import zio.*
import zlayer_pattern.ZLayerPattern.UserDatabase.UserDatabaseEnv
import zlayer_pattern.ZLayerPattern.UserEmailer.UserEmailerEnv

object ZLayerPattern extends zio.App {

  // In the real apps it always involves to have quite many services interacting with each other
  // - interacting with storage layer (database)
  // - business logic
  // - front-facing APIS through HTTP
  // - communicating with other services

  case class User(name: String, email: String)

  object UserEmailer {

    type UserEmailerEnv = Has[UserEmailer.Service]

    // service def
    trait Service {
      def notify(user: User, message: String): Task[Unit] // ZIO[Any, Throwable, Unit]
    }

    // service impl
    val live: ZLayer[Any, Nothing, UserEmailerEnv] = ZLayer.succeed {
      new Service {
        override def notify(user: User, message: String): Task[Unit] = Task {
          println(s"[UserEmailer] Sending $message to ${user.email}")
        }
      }
    }

    // front facing API
    def notify(user: User, message: String): ZIO[UserEmailerEnv, Throwable, Unit] =
      ZIO.accessM(_.get.notify(user, message))

  }

  object UserDatabase {

    type UserDatabaseEnv = Has[UserDatabase.Service]

    trait Service {
      def insert(user: User): Task[Unit]
    }

    val live: ZLayer[Any, Nothing, UserDatabaseEnv] = ZLayer.succeed(new Service {
      override def insert(user: User): Task[Unit] = Task {
        println(s"[Database] insert into public.user values ('${user.email}')")
      }
    })

    def insert(user: User): ZIO[UserDatabaseEnv, Throwable, Unit] =
      ZIO.accessM(_.get.insert(user))

  }

  // HORIZONTAL composition
  // ZLayer[R1, E1, A1] ++ ZLayer[R2, E2, A2] => ZLayer[R1 with R2, SuperType(E1, E2), A1 with A2]

  val userBackendLayer: ZLayer[Any, Nothing, UserDatabaseEnv & UserEmailerEnv] =
    UserDatabase.live ++ UserEmailer.live

  // VERTICAL composition
  object UserSubscription {

    type UserSubscriptionEnv = Has[UserSubscription.Service]

    class Service(notifier: UserEmailer.Service, userDatabase: UserDatabase.Service) {

      def subscribe(user: User): Task[User] = for {
        _ <- userDatabase.insert(user)
        _ <- notifier.notify(user, s"Welcome to ZIO, ${user.name}!")
      } yield user

    }

    val live = ZLayer.fromServices[UserEmailer.Service, UserDatabase.Service, UserSubscription.Service] {
      (em, db) => new Service(em, db)
    }

    // front-facing API

    def subscribe(user: User): ZIO[UserSubscriptionEnv, Throwable, User] =
      ZIO.accessM(_.get.subscribe(user))

  }

  override def run(args: List[String]) =

    import UserSubscription._

    val userSubscription: ZLayer[Any, Nothing, UserSubscriptionEnv] = userBackendLayer >>> UserSubscription.live

    val user = User("Nika", "nghurtch@broadinstitute.org")
    val message = "Welcome to ZIO"

//    UserEmailer.notify(user, message)
//      .provideLayer(userBackendLayer)
//      .exitCode

    UserSubscription.subscribe(user)
      .provideLayer(userSubscription)
      .exitCode

}

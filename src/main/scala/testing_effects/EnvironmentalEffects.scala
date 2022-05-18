package testing_effects

import testing_effects.EnvironmentalEffects.Database.Database
import zio.*

object EnvironmentalEffects {

  // The fundamental ide behind environmental effects is to program to an interface not an implementation.
  // In the case of functional Scala, interfaces do not contain any methods that perform side-effects
  // although they may contain methods that return functional effects.

  // Rather than passing interfaces throughout our code base manually, injecting them using dependency injection
  // or threading them using incoherent implicits, we use ZIO Environment to do the heavy lifting which resulting, inferrable and painless code

  // In this section we'll explore how to use environmental effects by developing a testable database service

  // Define the Service

  // we will define the database service with the help of a module, which is an interface that contains only a single field,
  // which provides access to the service

  case class UserID(id: Int)
  case class UserProfile(userID: UserID, name: String)

  object Database {

    trait Service {
      def lookup(userID: UserID): Task[UserProfile]
      def update(userID: UserID, profile: UserProfile): Task[Unit]
    }

    trait Database {
      def database: Database.Service
    }

  }

  // In this example `Database` is the module which contains the Database.Service service. The service is just an ordinary interface
  // placed inside the companion object of the module, which contains functions provide the capabilities of the service

  // Provide Helpers

  // in order to make it easier to access the database service as an environmental effect, we will define
  // helper functions that use ZIO.accessM

  object db {
    def lookup(userID: UserID): RIO[Database, UserProfile] =
      ZIO.accessM(_.database.lookup(userID))

    def update(id: UserID, profile: UserProfile): RIO[Database, Unit] =
      ZIO.accessM(_.database.update(id, profile))
  }

  // While these helpers are not required, because we can access the database module directly
  // through ZIO.accessM, the helpers are easy to write and make use-site code simpler.

  // Use the service.

  // Now that we have defined a module and helper functions, we are now ready to build an example that uses the database service

  val lookedUpProfile: RIO[Database, UserProfile] = for {
    profile <- db.lookup(UserID(5))
  } yield profile

  // The effect in this example interacts with the database solely through the environment, which in this case
  // is a module that provides access to the database service.

  // To actually run such an effect, we need to provide an implementation of the database module.

  // Implement Live Service

  // Now we will implement a live DB module, which will actually interact with our production database:

  trait DatabaseLive extends Database {
    override def database: Database.Service = new Database.Service :
      override def lookup(userID: UserID): Task[UserProfile] = ???

      override def update(userID: UserID, profile: UserProfile): Task[Unit] = ???
  }

  object DatabaseLive extends DatabaseLive

  // Run the Database Effect
  // We now have a database module, helpers to interact with the database module and a live implementation of the database module
  // We can now provide the live database module to our application using ZIO.provide

  def main: RIO[Database, Unit] = ???
  def main2: Task[Unit] = main.provide(DatabaseLive)

  // Implement Test service

  class TestService extends Database.Service {
    private var map: Map[UserID, UserProfile] = Map()

    def setTestData(map0: Map[UserID, UserProfile]): Task[Unit] =
      Task { map = map0 }

    def getTestData: Task[Map[UserID, UserProfile]] =
      Task(map)

    def lookup(id: UserID): Task[UserProfile] =
      Task(map(id))

    def update(id: UserID, profile: UserProfile): Task[Unit] =
      Task.effect { map = map + (id -> profile) }
  }

  trait TestDatabase extends Database {
    val database: TestService = new TestService
  }

  object TestDatabase extends TestDatabase

  // Test Database code

  def code: RIO[Database, Unit] = ???

  def code2: Task[Unit] =
    code.provide(TestDatabase)

}

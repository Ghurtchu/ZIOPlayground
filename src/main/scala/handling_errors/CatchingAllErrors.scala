package handling_errors

import java.io.IOException
import java.io.File
import zio.*

import java.nio.file.Files
import java.nio.file.Paths

object CatchingAllErrors {

  val z: IO[IOException, Array[Byte]] =
    openFile("primary.json")
      .catchAll(_ => openFile("backup.json"))

  def openFile(path: String): IO[IOException, Array[Byte]] =
    ZIO.effectTotal(Files.readAllBytes(Paths.get(path)))

}

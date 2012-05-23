import sbt._
import Keys._

object Build extends Build {
  lazy val root = Project(id = "transformer",
   base = file("."), settings = Project.defaultSettings).settings(
    mainClass in (Compile, run) := Some("play.core.server.NettyServer"))
}

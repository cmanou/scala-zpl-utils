name := "zpl-utils"

organization := "com.github.cmanou"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.sksamuel.scrimage" %%    "scrimage-core"     % "2.1.0",
  "com.sksamuel.scrimage" %%    "scrimage-io-extra" % "2.1.0",
  "com.sksamuel.scrimage" %%    "scrimage-filters"  % "2.1.0",
  "org.scalatest"         %     "scalatest_2.11"    % "2.2.4"     % "test"
)
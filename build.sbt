crossScalaVersions in Global := Seq("2.12.4", "2.11.11", "2.10.6")

scalaVersion in Global := crossScalaVersions.value.head

organization in Global := "com.hypertino"

lazy val library = crossProject.settings(publishSettings:_*).settings(
  name := "inflector",
  version := "1.0.8",
  libraryDependencies += "org.scalatest" %%% "scalatest" % "3.0.1" % "test",
  publishArtifact := true,
  publishArtifact in Test := false
).jsSettings(
  // JS-specific settings here
).jvmSettings(
  // JVM-specific settings here
)

lazy val js = library.js

lazy val jvm = library.jvm

lazy val publishSettings = Seq(
  pomExtra := <url>https://github.com/hypertino/inflector</url>
    <licenses>
      <license>
        <name>BSD-style</name>
        <url>http://opensource.org/licenses/BSD-3-Clause</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:hypertino/inflector.git</url>
      <connection>scm:git:git@github.com:hypertino/inflector.git</connection>
    </scm>
    <developers>
      <developer>
        <id>maqdev</id>
        <name>Magomed Abdurakhmanov</name>
        <url>https://github.com/maqdev</url>
      </developer>
      <developer>
        <id>hypertino</id>
        <name>Hypertino</name>
        <url>https://github.com/hypertino</url>
      </developer>
    </developers>,
  pgpSecretRing := file("./travis/script/ht-oss-private.asc"),
  pgpPublicRing := file("./travis/script/ht-oss-public.asc"),
  usePgpKeyHex("F8CDEF49B0EDEDCC"),
  pgpPassphrase := Option(System.getenv().get("oss_gpg_passphrase")).map(_.toCharArray),
  publishMavenStyle := true,
  pomIncludeRepository := { _ => false},
  publishTo := Some("Dealermade Maven Repository" at "https://mymavenrepo.com/repo/3RngugNS1G2SDIOzSUwZ") 
)

credentials ++= (for {
  username <- Option(System.getenv().get("sonatype_username"))
  password <- Option(System.getenv().get("sonatype_password"))
} yield Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", username, password)).toSeq

lazy val `inflector-root` = project
  .in(file("."))
  .settings(publishSettings:_*)
  .aggregate(js, jvm)
  .settings(
    publish := {},
    publishLocal := {},
    publishArtifact in Test := false,
    publishArtifact := false
  )

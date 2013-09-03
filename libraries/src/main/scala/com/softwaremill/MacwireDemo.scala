package com.softwaremill

object MacwireDemo extends App {
  // Case classes so that we have nice toString()
  case class DatabaseAccess()
  case class SecurityFilter()
  case class UserFinder(databaseAccess: DatabaseAccess, securityFilter: SecurityFilter)
  case class UserStatusReader(userFinder: UserFinder)

  trait UserModule {
    import com.softwaremill.macwire.MacwireMacros._

    lazy val theDatabaseAccess   = wire[DatabaseAccess]
    lazy val theSecurityFilter   = wire[SecurityFilter]
    lazy val theUserFinder       = wire[UserFinder]
    lazy val theUserStatusReader = wire[UserStatusReader]
  }

  val app = new UserModule {}

  println(app.theUserStatusReader)
}

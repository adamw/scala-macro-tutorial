package com.softwaremill

object MacwireDemo extends App {
  // Case classes so that we have nice toString()
  case class DatabaseAccess()
  case class SecurityFilter()
  case class UserFinder(databaseAccess: DatabaseAccess, securityFilter: SecurityFilter)
  case class UserStatusReader(userFinder: UserFinder)

  trait UserModule {
    import com.softwaremill.macwire.MacwireMacros._

    // Will expand to new DatabaseAccess()
    lazy val theDatabaseAccess   = wire[DatabaseAccess]
    lazy val theSecurityFilter   = wire[SecurityFilter]
    lazy val theUserFinder       = wire[UserFinder]
    // Will expand to new UserStatusReader(theUserFinder)
    lazy val theUserStatusReader = wire[UserStatusReader]
  }

  val app = new UserModule {}

  println(app.theUserStatusReader)
}

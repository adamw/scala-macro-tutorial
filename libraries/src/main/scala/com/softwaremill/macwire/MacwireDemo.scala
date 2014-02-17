package com.softwaremill.macwire

object MacwireDemo extends App {
  // Case classes so that we have nice toString()
  case class DatabaseAccess()
  case class SecurityFilter()
  case class UserFinder(databaseAccess: DatabaseAccess, securityFilter: SecurityFilter)
  case class UserStatusReader(userFinder: UserFinder)

  // todo
}

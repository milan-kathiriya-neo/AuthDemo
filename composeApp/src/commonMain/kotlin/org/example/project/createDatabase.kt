package org.example.project

fun createDatabase(driverFactory: DatabaseDriverFactory): MyDataBase {
    val driver = driverFactory.createDriver()
    return MyDataBase(driver)
}
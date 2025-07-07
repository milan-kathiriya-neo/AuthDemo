package com.example.authdemo

fun createDatabase(driverFactory: DatabaseDriverFactory): MyDataBase {
    val driver = driverFactory.createDriver()
    return MyDataBase(driver)
}
package com.ctseducare.expensebook.di

import com.ctseducare.expensebook.properties.DatabaseProperties
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariDataSource
import org.kodein.di.DI
import org.kodein.di.bindSingleton

val databaseModule = DI.Module("database") {
    val properties = loadDatabaseProperties(ConfigFactory.load().getConfig("database"))
    bindSingleton { buildDataSource(properties) }
}

private fun loadDatabaseProperties(config: Config) = DatabaseProperties(
    driver = config.getString("driver"),
    url = config.getString("url"),
    username = config.getString("username"),
    password = config.getString("password")
)

private fun buildDataSource(properties: DatabaseProperties): HikariDataSource {
    val dataSource = HikariDataSource()
    dataSource.driverClassName = properties.driver
    dataSource.jdbcUrl = properties.url
    dataSource.username = properties.username
    dataSource.password = properties.password
    return dataSource
}


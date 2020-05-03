package ca.alexleung.skratchpad.di

import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

val datastoreModule = module {
    single { createDatabase() }
}

fun createDatabase() : Database {
    return Database.connect(
        "jdbc:postgresql://localhost:5432/skratchpad",
        driver = "org.postgresql.Driver",
        user = "postgres", password = "postgres"
    )
}

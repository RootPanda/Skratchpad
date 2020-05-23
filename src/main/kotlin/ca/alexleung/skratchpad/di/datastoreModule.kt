package ca.alexleung.skratchpad.di

import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

val datastoreModule = module {
    single { createDatabase() }
}

fun createDatabase(): Database {
    val databaseUrl: String = System.getenv("SKRATCHPAD_DATABASE_URL") ?: "jdbc:postgresql://localhost:5432/skratchpad"
    return Database.connect(
        databaseUrl,
        driver = "org.postgresql.Driver",
        user = "postgres", password = "postgres"
    )
}

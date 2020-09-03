package ca.alexleung.skratchpad.di

import ca.alexleung.skratchpad.data.DbNoteData
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.dsl.module

val datastoreModule = module {
    single { createAndSetupDatabase() }
}

fun createAndSetupDatabase(): Database {
    val databaseUrl: String = System.getenv("SKRATCHPAD_DATABASE_URL") ?: "jdbc:postgresql://localhost:5432/skratchpad"
    val db = Database.connect(
        databaseUrl,
        driver = "org.postgresql.Driver",

        // TODO: Read at runtime.
        user = "postgres",

        // TODO: Read at runtime.
        password = "postgres"
    )
    transaction(db) {
        SchemaUtils.create(DbNoteData)
    }
    return db
}

package ca.alexleung.skratchpad

import ca.alexleung.skratchpad.controllers.NotesController
import ca.alexleung.skratchpad.routes.NotesRoutes
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>) {
    val db = Database.connect(
        "jdbc:postgresql://localhost:5432/skratchpad",
        driver = "org.postgresql.Driver",
        user = "postgres", password = "postgres"
    )

    val notesController = NotesController(db)
    val notesRoutes = NotesRoutes(notesController)
    notesRoutes.init()
}


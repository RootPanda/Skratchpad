package ca.alexleung.skratchpad

import io.javalin.Javalin
import org.jetbrains.exposed.sql.*

fun main(args: Array<String>) {
    val db = Database.connect(
        "jdbc:postgresql://localhost:5432/skratchpad",
        driver = "org.postgresql.Driver",
        user = "postgres", password = "postgres"
    )

    val app = Javalin.create().start()

    val restfulNote = RestfulNote(db)

    app.get("/notes/:id", restfulNote::get)
    app.post("/notes", restfulNote::post)
    app.delete("/notes/:id", restfulNote::delete)
}


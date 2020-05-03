package ca.alexleung.skratchpad.routes

import ca.alexleung.skratchpad.controllers.NotesController
import io.javalin.Javalin

class NotesRoutes(val notesController: NotesController) {
    lateinit var app: Javalin

    fun init() {
        app = Javalin.create().start()
        app.get("/notes/:id", notesController::get)
        app.post("/notes", notesController::post)
        app.delete("/notes/:id", notesController::delete)
    }
}
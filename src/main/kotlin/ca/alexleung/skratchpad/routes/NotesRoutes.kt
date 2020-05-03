package ca.alexleung.skratchpad.routes

import ca.alexleung.skratchpad.controllers.NotesController
import io.javalin.Javalin

interface NotesRoutes {
    fun init()
}

class NotesRoutesImpl(val notesController: NotesController) : NotesRoutes{
    lateinit var app: Javalin

    override fun init() {
        app = Javalin.create().start()
        app.get("/notes/:id", notesController::get)
        app.post("/notes", notesController::post)
        app.delete("/notes/:id", notesController::delete)
    }
}
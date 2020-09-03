package ca.alexleung.skratchpad.routes

import ca.alexleung.skratchpad.controllers.NotesController
import io.javalin.Javalin

class RealNotesRoutes(
    private val notesController: NotesController
) : NotesRoutes{
    lateinit var app: Javalin

    override fun init() {
        app = Javalin.create().start()
        app.get("/all-notes", notesController::getAllNotes)
        app.get("/note/:id", notesController::getNote)
        app.post("/note", notesController::createNote)
        app.delete("/note/:id", notesController::deleteNote)
    }
}

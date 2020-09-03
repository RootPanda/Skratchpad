package ca.alexleung.skratchpad.controllers

import io.javalin.http.Context

interface NotesController {
    fun getAllNotes(ctx: Context)
    fun getNote(ctx: Context)
    fun createNote(ctx: Context)
    fun deleteNote(ctx: Context)
}


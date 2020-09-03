package ca.alexleung.skratchpad.controllers

import ca.alexleung.skratchpad.data.NoteData
import ca.alexleung.skratchpad.data.DbNoteData
import ca.alexleung.skratchpad.data.toNoteData
import com.google.gson.Gson
import io.javalin.http.Context
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.NumberFormatException

class RealNotesController(
    private val db: Database
) : NotesController {
    private val gson = Gson()

    data class ErrorData(
        var error: String
    )

    override fun getAllNotes(ctx: Context) {
        val noteData = transaction(db) {
            DbNoteData.selectAll().map(ResultRow::toNoteData)
        }
        ctx.result(gson.toJson(noteData)).contentType("application/json")
    }

    override fun getNote(ctx: Context) {
        var id: Int
        try {
            id = ctx.pathParam("id").toInt()
        } catch (nfe: NumberFormatException) {
            ctx.result(gson.toJson(ErrorData("Invalid id"))).contentType("application/json")
            return
        }
        val noteData = transaction(db) {
            DbNoteData.select { DbNoteData.id eq id }.map(ResultRow::toNoteData)
        }
        ctx.result(gson.toJson(noteData)).contentType("application/json")
    }

    override fun createNote(ctx: Context) {
        val note = gson.fromJson(ctx.body(), NoteData::class.java)
        transaction(db) {
            val exists = !DbNoteData.select { DbNoteData.id eq note.id }.empty()
            if (exists) {
                DbNoteData.update({ DbNoteData.id eq note.id }) {
                    it[body] = note.body
                }
                println("Updated note '${note.id}'")
            } else {
                note.id = DbNoteData.insertAndGetId {
                    it[body] = note.body
                }.value
                println("Inserted note '${note.id}'")
            }
        }
        ctx.result(gson.toJson(note)).contentType("application/json")
    }

    override fun deleteNote(ctx: Context) {
        var id: Int
        try {
            id = ctx.pathParam("id").toInt()
        } catch (nfe: NumberFormatException) {
            ctx.result(gson.toJson(ErrorData("Invalid id"))).contentType("application/json")
            return
        }
        transaction(db) {
            DbNoteData.deleteWhere { DbNoteData.id eq id }
            println("Deleted note '${id}'")
        }
    }
}

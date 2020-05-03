package ca.alexleung.skratchpad.controllers

import com.google.gson.Gson
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.*
import io.javalin.http.Context
import java.lang.NumberFormatException
import ca.alexleung.skratchpad.models.*

class NotesController {
    private var db: Database

    constructor(db: Database) {
        this.db = db
        transaction(db) {
            SchemaUtils.create(NotesTable)
        }
    }

    fun get(ctx: Context) {
        val gson = Gson()
        var id: Int
        try {
            id = ctx.pathParam("id").toInt()
        } catch (nfe: NumberFormatException) {
            ctx.result(gson.toJson(ErrorData("Invalid id")))
            return
        }
        val noteData = transaction(db) {
            NotesTable.select { NotesTable.id eq id }.map {
                it.toNoteData()
            }
        }
        if (noteData.isEmpty()) {
            ctx.result(gson.toJson(ErrorData("Not found")))
        } else {
            ctx.result(gson.toJson(noteData[0]))
        }
    }

    fun post(ctx: Context) {
        val gson = Gson()
        val note = gson.fromJson(ctx.body(), NoteData::class.java)
        transaction(db) {
            val exists = !NotesTable.select { NotesTable.id eq note.id }.empty()
            if (exists) {
                NotesTable.update({ NotesTable.id eq note.id }) {
                    it[body] = note.body
                }
                println("Updated note '${note.id}'")
            } else {
                note.id = NotesTable.insertAndGetId {
                    it[body] = note.body
                }.value
                println("Inserted note '${note.id}'")
            }
        }
        ctx.result(gson.toJson(note))
    }

    fun delete(ctx: Context) {
        val gson = Gson()
        var id: Int
        try {
            id = ctx.pathParam("id").toInt()
        } catch (nfe: NumberFormatException) {
            ctx.result(gson.toJson(ErrorData("Invalid id")))
            return
        }
        transaction(db) {
            NotesTable.deleteWhere { NotesTable.id eq id }
            println("Deleted note '${id}'")
        }
    }
}


package ca.alexleung.skratchpad

import com.google.gson.Gson
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.transactions.transaction
import io.javalin.http.Context
import org.jetbrains.exposed.sql.*
import java.lang.NumberFormatException

object NotesTable : IntIdTable() {
    val body: Column<String> = varchar("body", 255)
}

data class ErrorData(
    var error: String
)

data class NoteData(
    var id: Int?,
    var body: String
)

fun ResultRow.toNoteData() = NoteData(this[NotesTable.id].value, this[NotesTable.body])

class RestfulNote {
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


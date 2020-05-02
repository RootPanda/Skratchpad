package ca.alexleung.skratchpad

import io.javalin.Javalin
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.NumberFormatException
import com.google.gson.Gson

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

fun main(args: Array<String>) {
    val db = Database.connect(
        "jdbc:postgresql://localhost:5432/skratchpad",
        driver = "org.postgresql.Driver",
        user = "postgres", password = "postgres"
    )

    transaction(db) {
        SchemaUtils.create(NotesTable)
    }

    val app = Javalin.create().start()
    app.get("/notes/:id", fun(ctx) {
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
    })

    app.post("/notes", fun(ctx) {
        val gson = Gson()
        val note = gson.fromJson(ctx.body(), NoteData::class.java)
        transaction(db) {
            val exists = !NotesTable.select { NotesTable.id eq note.id }.empty()
            if (exists) {
                val updatedId = NotesTable.update({ NotesTable.id eq note.id }) {
                    it[body] = note.body
                }
                println("Updated note '${updatedId}'")
            } else {
                note.id = NotesTable.insertAndGetId {
                    it[body] = note.body
                }.value
                println("Inserted note '${note.id}'")
            }
        }
        ctx.result(gson.toJson(note))
    })

    app.delete("/notes/:id", fun(ctx) {
        val gson = Gson()
        var id: Int
        try {
            id = ctx.pathParam("id").toInt()
        } catch (nfe: NumberFormatException) {
            ctx.result(gson.toJson(ErrorData("Invalid id")))
            return
        }
        val noteData = transaction(db) {
            NotesTable.deleteWhere { NotesTable.id eq id }
            println("Deleted note '${id}'")
        }
    })
}


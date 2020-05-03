package ca.alexleung.skratchpad.models

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow

object NotesTable : IntIdTable() {
    val body: Column<String> = varchar("body", 255)
}

data class ErrorData(
    var error: String
)

data class NoteData(
    var id: Int,
    var body: String
)

fun ResultRow.toNoteData() = NoteData(
    this[NotesTable.id].value,
    this[NotesTable.body]
)


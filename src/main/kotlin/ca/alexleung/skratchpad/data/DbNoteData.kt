package ca.alexleung.skratchpad.data

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow

object DbNoteData : IntIdTable() {
    val body: Column<String> = varchar("body", 255)
}

data class NoteData(
    var id: Int,
    var body: String
)

fun ResultRow.toNoteData() = NoteData(
    this[DbNoteData.id].value,
    this[DbNoteData.body]
)


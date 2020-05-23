package ca.alexleung.skratchpad

import ca.alexleung.skratchpad.routes.NotesRoutes
import org.koin.core.KoinComponent
import org.koin.core.inject

class SkratchpadApplication : KoinComponent {
    val notesRoutes by inject<NotesRoutes>()

    fun init() = notesRoutes.init();
}

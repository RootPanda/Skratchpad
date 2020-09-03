package ca.alexleung.skratchpad.di

import ca.alexleung.skratchpad.controllers.NotesController
import ca.alexleung.skratchpad.controllers.RealNotesController
import ca.alexleung.skratchpad.routes.NotesRoutes
import ca.alexleung.skratchpad.routes.RealNotesRoutes
import org.koin.dsl.module

val skratchpadApplicationModule = module {
    single { RealNotesController(get()) as NotesController }
    single { RealNotesRoutes(get()) as NotesRoutes }
}
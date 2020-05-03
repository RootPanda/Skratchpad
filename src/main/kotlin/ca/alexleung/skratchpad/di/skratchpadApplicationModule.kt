package ca.alexleung.skratchpad.di

import ca.alexleung.skratchpad.controllers.NotesController
import ca.alexleung.skratchpad.controllers.NotesControllerImpl
import ca.alexleung.skratchpad.routes.NotesRoutes
import ca.alexleung.skratchpad.routes.NotesRoutesImpl
import org.koin.dsl.module

val skratchpadApplicationModule = module {
    single { NotesControllerImpl(get()) as NotesController }
    single { NotesRoutesImpl(get()) as NotesRoutes }
}
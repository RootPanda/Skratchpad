package ca.alexleung.skratchpad

import ca.alexleung.skratchpad.di.datastoreModule
import ca.alexleung.skratchpad.di.skratchpadApplicationModule
import org.koin.core.context.startKoin

fun main(args: Array<String>) {
    startKoin {
        modules(datastoreModule, skratchpadApplicationModule)
    }
    SkratchpadApplication().init()
}


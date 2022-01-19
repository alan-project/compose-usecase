package net.alanproject.compose_usecase

import android.app.Application
import timber.log.Timber

class ComposeApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

    }
}
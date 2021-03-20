package com.aaronbruckner.geoquiz

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import javax.inject.Named

private const val TAG = "GeoQuizApplication"

@HiltAndroidApp
class GeoQuizApplication : Application() {
    @Inject @Named("injectedString1")lateinit var injectedString1: String

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Injected String: $injectedString1")
    }
}
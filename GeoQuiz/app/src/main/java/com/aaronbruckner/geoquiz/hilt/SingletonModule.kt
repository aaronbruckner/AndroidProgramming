package com.aaronbruckner.geoquiz.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
internal class SingletonModule {
    @Provides
    @Named("injectedString1")
    fun provideInjectedString1(): String {
        return "Example Injected String 1"
    }
}
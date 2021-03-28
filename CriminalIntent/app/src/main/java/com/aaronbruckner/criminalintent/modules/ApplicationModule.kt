package com.aaronbruckner.criminalintent.modules

import android.content.Context
import com.aaronbruckner.criminalintent.data.CrimeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    @Singleton
    @Provides
    fun provideCrimeRepository(@ApplicationContext context: Context): CrimeRepository {
        return CrimeRepository(context)
    }
}
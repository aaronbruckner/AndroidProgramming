package com.aaronbruckner.criminalintent.modules

import com.aaronbruckner.criminalintent.data.Crime
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {
    @Provides
    fun provideCrimeList(): MutableList<Crime> {
        val crimes = mutableListOf<Crime>()
        for(i in 0 until 100) {
            crimes += Crime().apply {
                this.title = "Crime #$i"
                this.isSolved = i % 2 == 0
            }
        }
        return crimes
    }
}
package com.aaronbruckner.criminalintent.viewmodels

import androidx.lifecycle.ViewModel
import com.aaronbruckner.criminalintent.data.Crime

class CrimeListViewModel : ViewModel() {
    val crimes = mutableListOf<Crime>()

    init {
        for(i in 0 until 100) {
            crimes += Crime().apply {
                this.title = "Crime #$i"
                this.isSolved = i % 2 == 0
            }
        }
    }
}
package com.aaronbruckner.criminalintent.viewmodels

import androidx.lifecycle.ViewModel
import com.aaronbruckner.criminalintent.data.Crime
import com.aaronbruckner.criminalintent.data.CrimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CrimeListViewModel @Inject constructor(private val crimeRepository: CrimeRepository): ViewModel() {
    val crimeListLiveData = crimeRepository.getCrimes()
}
package com.aaronbruckner.criminalintent.viewmodels

import androidx.lifecycle.ViewModel
import com.aaronbruckner.criminalintent.data.Crime
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CrimeListViewModel @Inject constructor(val crimes: MutableList<Crime>): ViewModel() {
}
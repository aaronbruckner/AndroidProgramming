package com.aaronbruckner.criminalintent.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.aaronbruckner.criminalintent.R
import com.aaronbruckner.criminalintent.viewmodels.CrimeListViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "CrimeListFragment"

/**
 * A simple [Fragment] subclass.
 * Use the [CrimeListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class CrimeListFragment : Fragment() {
    companion object {
        fun newInstance(): CrimeListFragment = CrimeListFragment()
    }
    private val viewModel: CrimeListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total Crimes: ${viewModel.crimes.size}")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_crime_list, container, false)
    }
}
package com.aaronbruckner.criminalintent.fragments

import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aaronbruckner.criminalintent.R
import com.aaronbruckner.criminalintent.data.Crime
import com.aaronbruckner.criminalintent.viewmodels.CrimeListViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

private const val TAG = "CrimeListFragment"

/**
 * A simple [Fragment] subclass.
 * Use the [CrimeListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class CrimeListFragment : Fragment() {
    private lateinit var crimeRecyclerView: RecyclerView
    private var callbacks: Callbacks? = null
    private val viewModel: CrimeListViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate()")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)

        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view)
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        updateUI(emptyList())

        viewModel.crimeListLiveData.observe(viewLifecycleOwner) { crimes ->
            crimes?.let {
                Log.d(TAG, "New Crime list - Size: ${crimes.size}")
                updateUI(crimes)
            }
        }

        return view
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun updateUI(crimes: List<Crime>) {
        crimeRecyclerView.adapter = CrimeAdapter(crimes)
    }

    companion object {
        fun newInstance(): CrimeListFragment = CrimeListFragment()
    }

    interface Callbacks {
        fun onCrimeSelected(crimeId: UUID)
    }

    private inner class CrimeAdapter(private val crimes: List<Crime>) : RecyclerView.Adapter<CrimeHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CrimeHolder(
            layoutInflater.inflate(R.layout.list_item_crime, parent, false)
        )

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            holder.bind(crimes[position])
        }

        override fun getItemCount() = crimes.size

    }

    private inner class CrimeHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var crime: Crime
        private val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
        private val isSolvedImageView: ImageView = itemView.findViewById(R.id.is_solved_image_view)
        private val dayDateFormat = DateFormat.getLongDateFormat(this@CrimeListFragment.context)
        private val timeDateFormat = DateFormat.getTimeFormat(this@CrimeListFragment.context)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(c: Crime) {
            crime = c
            titleTextView.text = crime.title
            dateTextView.text = "${dayDateFormat.format(crime.date)} - ${timeDateFormat.format(crime.date)}"
            isSolvedImageView.visibility = if (crime.isSolved) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        override fun onClick(v: View?) {
            callbacks?.onCrimeSelected(crime.id)
        }
    }
}
package com.aaronbruckner.criminalintent.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aaronbruckner.criminalintent.R
import com.aaronbruckner.criminalintent.fragments.CrimeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment == null) {
            val fragment = CrimeFragment()
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit()
        }
    }
}
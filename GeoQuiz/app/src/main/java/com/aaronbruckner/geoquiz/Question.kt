package com.aaronbruckner.geoquiz

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Question(@StringRes val textResId: Int, val answer: Boolean, var userAnswer: Boolean? = null) : Parcelable
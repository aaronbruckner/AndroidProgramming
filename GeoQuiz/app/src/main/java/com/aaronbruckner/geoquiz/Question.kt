package com.aaronbruckner.geoquiz

import androidx.annotation.StringRes

data class Question(@StringRes val textResId: Int, val answer: Boolean, var userAnswer: Boolean? = null)
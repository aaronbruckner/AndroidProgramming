package com.aaronbruckner.geoquiz.hilt

import com.aaronbruckner.geoquiz.Question
import com.aaronbruckner.geoquiz.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {
    @Provides
    @Named("defaultQuestionBank")
    fun providesDefaultQuestionBank(): List<Question> = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
}
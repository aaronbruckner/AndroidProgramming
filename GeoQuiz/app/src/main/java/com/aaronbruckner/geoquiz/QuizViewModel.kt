package com.aaronbruckner.geoquiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

private const val KEY_CURRENT_INDEX = "KEY_CURRENT_INDEX"
private const val KEY_QUESTION_BANK = "KEY_QUESTION_BANK"
private const val MAX_CHEAT_COUNT = 3

@HiltViewModel
class QuizViewModel @Inject constructor(private val state: SavedStateHandle, @Named("defaultQuestionBank") defaultQuestionBank: List<Question>) : ViewModel() {
    val remainingCheats: Int
        get() = (MAX_CHEAT_COUNT - questionBank.count { it.didCheat }).coerceAtLeast(0)
    private var currentIndex: Int = state.get(KEY_CURRENT_INDEX) ?: 0
    private val questionBank: List<Question> = state.get(KEY_QUESTION_BANK) ?: defaultQuestionBank

    val currentQuestion: Question
        get() = questionBank[currentIndex]

    val isLastQuestion: Boolean
        get() = currentIndex == questionBank.size - 1

    val totalQuestionsAnswered: Int
        get() = questionBank.count { it.userAnswer != null }

    val totalQuestionsCorrect: Int
        get() = questionBank.count {it.userAnswer == it.answer}

    fun moveToNextQuestion(direction: Int) {
        currentIndex = (currentIndex + direction).coerceAtLeast(0) % questionBank.size
    }

    fun save() {
        state.set(KEY_CURRENT_INDEX, currentIndex)
        state.set(KEY_QUESTION_BANK, questionBank)
    }
}
package com.aaronbruckner.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: View
    private lateinit var prevButton: View
    private lateinit var questionTextView: TextView
    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate()")
        setContentView(R.layout.activity_main)

        // UI References
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)

        // On Click Handlers
        trueButton.setOnClickListener {
            checkAnswer(true)
        }
        falseButton.setOnClickListener {
            checkAnswer(false)
        }
        questionTextView.setOnClickListener {
            moveToNextQuestion(1)
        }
        nextButton.setOnClickListener {
            moveToNextQuestion(1)
        }
        prevButton.setOnClickListener {
            moveToNextQuestion(-1)
        }

        // Init
        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy()")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(TAG, "onSaveInstanceState()")
        quizViewModel.save()
        super.onSaveInstanceState(outState)
    }

    private fun moveToNextQuestion(direction: Int) {
        if (quizViewModel.isLastQuestion) {
            showUserScore()
        }
        quizViewModel.moveToNextQuestion(direction)
        updateQuestion()
    }

    private fun showUserScore() {
        Toast.makeText(this, "You got ${quizViewModel.totalQuestionsAnswered} out of ${quizViewModel.totalQuestionsCorrect} correct", Toast.LENGTH_LONG).show()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val question: Question = quizViewModel.currentQuestion
        question.userAnswer = userAnswer
        val isUserCorrect = question.answer == userAnswer
        showResultToast(if (isUserCorrect) R.string.correct_toast else R.string.incorrect_toast)
        updateQuestion()
    }

    private fun showResultToast(@StringRes message: Int): Unit {
        val toast: Toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP, 0, 0)
        toast.show()
    }

    private fun updateQuestion() {
        val question: Question = quizViewModel.currentQuestion
        questionTextView.setText(question.textResId)
        trueButton.isEnabled = question.userAnswer == null
        falseButton.isEnabled = question.userAnswer == null
    }
}
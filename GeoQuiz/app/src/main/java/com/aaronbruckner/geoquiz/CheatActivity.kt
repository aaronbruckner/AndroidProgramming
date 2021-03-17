package com.aaronbruckner.geoquiz

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

private const val EXTRA_IS_ANSWER_TRUE = "com.aaronbruckner.geoquiz.is_answer_true"

class CheatActivity : AppCompatActivity() {
    companion object {
        fun newIntent(context: Context, answerIsTrue: Boolean): Intent {
            return Intent(context, CheatActivity::class.java).apply {
                putExtra(EXTRA_IS_ANSWER_TRUE, answerIsTrue)
            }
        }
    }

    private lateinit var showAnswerButton: View
    private lateinit var answerTextView: TextView

    private val isAnswerTrue: Boolean by lazy {
        intent.getBooleanExtra(EXTRA_IS_ANSWER_TRUE, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        showAnswerButton = findViewById(R.id.show_answer_button)
        answerTextView = findViewById(R.id.answer_text_view)

        showAnswerButton.setOnClickListener {
            showAnswer()
        }
    }

    private fun showAnswer() {
        answerTextView.setText(when {
            isAnswerTrue -> R.string.true_button
            else -> R.string.false_button
        })
    }
}
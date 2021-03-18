package com.aaronbruckner.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContract

private const val EXTRA_IS_ANSWER_TRUE = "com.aaronbruckner.geoquiz.is_answer_true"
private const val EXTRA_DID_CHEAT = "com.aaronbruckner.geoquiz.did_cheat"

class CheatActivity : AppCompatActivity() {
    class DidCheatContract: ActivityResultContract<Boolean, Boolean>() {
        override fun createIntent(context: Context, isAnswerTrue: Boolean): Intent {
            return Intent(context, CheatActivity::class.java).apply {
                putExtra(EXTRA_IS_ANSWER_TRUE, isAnswerTrue)
            }
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
            return when(resultCode) {
                Activity.RESULT_OK -> intent?.getBooleanExtra(EXTRA_DID_CHEAT, false) ?: false
                else -> false
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
        setResult(Activity.RESULT_OK, Intent().apply {
            putExtra(EXTRA_DID_CHEAT, true)
        })
        answerTextView.setText(when {
            isAnswerTrue -> R.string.true_button
            else -> R.string.false_button
        })
    }
}
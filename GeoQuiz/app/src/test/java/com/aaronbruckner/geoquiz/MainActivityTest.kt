package com.aaronbruckner.geoquiz

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import com.aaronbruckner.geoquiz.hilt.ViewModelModule
import dagger.hilt.android.testing.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.After

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowToast
import javax.inject.Named

@UninstallModules(ViewModelModule::class)
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
class MainActivityTest {
    @get:Rule val hiltRule = HiltAndroidRule(this)

    private lateinit var scenario: ActivityScenario<MainActivity>

    @BindValue @Named("defaultQuestionBank") val mockDefaultQuestionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_mideast, false),
    )

    @Before
    fun beforeEach() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun afterEach() {
        scenario.close()
    }

    @Test
    fun `renders the activity properly`() {
        scenario.onActivity { activity ->
            val questionTextView = activity.findViewById<TextView>(R.id.question_text_view)
            assertThat(questionTextView.text).isEqualTo("Canberra is the capital of Australia.")
        }
    }

    @Test
    fun `navigates questions when pressing next and previous buttons`() {
        scenario.onActivity { activity ->
            val questionTextView = activity.findViewById<TextView>(R.id.question_text_view)
            val nextButton = activity.findViewById<View>(R.id.next_button)
            val prevButton = activity.findViewById<View>(R.id.prev_button)

            nextButton.performClick()
            assertThat(questionTextView.text).isEqualTo("The Suez Canal connects the Red Sea and the Indian Ocean.")
            prevButton.performClick()
            assertThat(questionTextView.text).isEqualTo("Canberra is the capital of Australia.")
        }
    }

    @Test
    fun `toasts success if correct answer is provided for question`() {
        scenario.onActivity { activity ->
            val trueButton = activity.findViewById<View>(R.id.true_button)

            assertThat(trueButton.isEnabled).isEqualTo(true)

            trueButton.performClick()

            assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo("Correct!")
        }
    }

    @Test
    fun `toasts failure if incorrect answer is provided for question`() {
        scenario.onActivity { activity ->
            val falseButton = activity.findViewById<View>(R.id.false_button)

            assertThat(falseButton.isEnabled).isEqualTo(true)

            falseButton.performClick()

            assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo("Incorrect!")
        }
    }

    @Test
    fun `disables buttons after answering question (and maintains disabled state between navigation)`() {
        scenario.onActivity { activity ->
            val trueButton = activity.findViewById<View>(R.id.true_button)
            val falseButton = activity.findViewById<View>(R.id.false_button)
            val nextButton = activity.findViewById<View>(R.id.next_button)
            val prevButton = activity.findViewById<View>(R.id.prev_button)

            trueButton.performClick()

            assertThat(trueButton.isEnabled).isEqualTo(false)
            assertThat(falseButton.isEnabled).isEqualTo(false)

            nextButton.performClick()

            assertThat(trueButton.isEnabled).isEqualTo(true)
            assertThat(falseButton.isEnabled).isEqualTo(true)

            prevButton.performClick()

            assertThat(trueButton.isEnabled).isEqualTo(false)
            assertThat(falseButton.isEnabled).isEqualTo(false)
        }
    }

    @Test
    fun `toasts quiz results on the last question`() {
        scenario.onActivity { activity ->
            val trueButton = activity.findViewById<View>(R.id.true_button)
            val nextButton = activity.findViewById<View>(R.id.next_button)

            trueButton.performClick()
            nextButton.performClick()

            trueButton.performClick()
            nextButton.performClick()

            assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo("You got 1 out of 2 correct")
        }
    }

    @Test
    fun `cheat button starts cheat activity`() {
        scenario.onActivity { activity ->
            val shadowActivity = shadowOf(activity)
            val cheatButton = activity.findViewById<View>(R.id.cheat_button)

            cheatButton.performClick()

            val intent = shadowActivity.peekNextStartedActivityForResult().intent
            assertThat(shadowOf(intent).intentClass).isEqualTo(CheatActivity::class.java)
            assertThat(intent.getBooleanExtra("com.aaronbruckner.geoquiz.is_answer_true", false)).isTrue()
        }
    }

    @Test
    fun `catches cheating user`() {
        scenario.onActivity { activity ->
            val shadowActivity = shadowOf(activity)
            val trueButton = activity.findViewById<View>(R.id.true_button)
            val cheatButton = activity.findViewById<View>(R.id.cheat_button)

            cheatButton.performClick()
            val intent = shadowActivity.peekNextStartedActivityForResult().intent
            val resultIntent = Intent().putExtra("com.aaronbruckner.geoquiz.did_cheat", true)

            shadowActivity.receiveResult(intent, Activity.RESULT_OK, resultIntent)

            trueButton.performClick()

            assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo("Cheating is wrong.")
        }
    }
}
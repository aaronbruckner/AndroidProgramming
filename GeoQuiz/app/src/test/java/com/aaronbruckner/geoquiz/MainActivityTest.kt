package com.aaronbruckner.geoquiz

import android.view.View
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import org.assertj.core.api.Assertions.assertThat
import org.junit.After

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowToast

@RunWith(RobolectricTestRunner::class)
class MainActivityTest {
    private lateinit var scenario: ActivityScenario<MainActivity>

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
            assertThat(questionTextView.text).isEqualTo("The Pacific Ocean is larger than the Atlantic Ocean.")
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
}
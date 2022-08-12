package com.google.codelabs.buildyourfirstmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.google.codelabs.buildyourfirstmap.databinding.ActivityQuizGameBinding

class QuizGame : AppCompatActivity() {

    private lateinit var binding: ActivityQuizGameBinding
    private var rightAnswer: String? = null
    private var rightAnswerCount = 0
    private var quizCount = 1
    private val QUIZ_COUNT = 5

    private val quizData = mutableListOf(
        // question, answer, choice1
        mutableListOf("Does a refrigerator count as electronic waste?", "Yes", "No"),
        mutableListOf("Does a laptop count as electronic waste?", "Yes", "No"),
        mutableListOf("Does a portable charger count as electronic waste?", "Yes", "No"),
        mutableListOf("Does Apple Airpods  count as electronic waste?", "No", "Yes"),
        mutableListOf("Does CDs count as electronic waste?", "No", "Yes"),
        mutableListOf("Do batteries count as electronic waste?", "Yes", "No"),
        mutableListOf("Do light bulbs count as electronic waste?", "Yes", "No"),
        mutableListOf("Do clocks count as electronic waste?", "No", "Yes"),
        mutableListOf("Do mobile phones count as electronic waste?", "Yes", "No"),
        mutableListOf("Must I wipe all data on the devices whenever possible before disposing ICT equipment?", "Yes", "No"),
        mutableListOf("Are there ICT equipment, batteries and bulbs ALBA E-waste bins around Singapore?", "Yes", "No"),
        mutableListOf("Do personal mobility devices count as electronic waste?", "Yes", "No")


    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_quiz_game)
        binding = ActivityQuizGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        // shuffle quiz
        quizData.shuffle()

        showNextQuiz()
    }

    fun showNextQuiz() {
        // update countlabel
        binding.countLabel.text = getString(R.string.count_label, quizCount)

        // Pick one quiz set
        val quiz = quizData[0]

        // set question & right answer
        binding.questionLabel.text = quiz[0]
        rightAnswer = quiz[1]

        //remove "Country" from quiz
        quiz.removeAt(0)

        // shuffle answer & choices
        quiz.shuffle()

        // set choices
        binding.answerBtn1.text= quiz[0]
        binding.answerBtn2.text= quiz[1]

        // remove this quiz from QuizData
        quizData.removeAt(0)

    }

    fun checkAnswer(view: View){
        // get pushed button
        val answerBtn: Button = findViewById(view.id)
        val btnText = answerBtn.text.toString()

        val alertTitle: String
        if (btnText == rightAnswer){
            // correct
            alertTitle = "Correct!"
            rightAnswerCount++
        } else {
            // wrong
            alertTitle = "Wrong..."
        }

        // create dialog
        AlertDialog.Builder(this)
            .setTitle(alertTitle)
            .setMessage("Answer: $rightAnswer")
            .setPositiveButton("OK") {dialogInterface, i ->
                checkQuizCount()
            }
            .setCancelable(false)
            .show()


    }

    fun checkQuizCount() {
        if (quizCount == QUIZ_COUNT){
            // show Result
            val intent = Intent(this@QuizGame, Results::class.java)
            intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount)
            startActivity(intent)


        } else {
            quizCount++
            showNextQuiz()

        }

    }
}
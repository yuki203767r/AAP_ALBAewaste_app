package com.google.codelabs.buildyourfirstmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.codelabs.buildyourfirstmap.databinding.ActivityResultsBinding

class Results : AppCompatActivity() {

    private lateinit var binding: ActivityResultsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_results)

        binding = ActivityResultsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val score = intent.getIntExtra("RIGHT_ANSWER_COUNT", 0)
        binding.resultLabel.text = getString(R.string.result_score, score)

        binding.tryAgainBtn.setOnClickListener{
            startActivity(Intent(this@Results, QuizGame::class.java))
        }
    }
}
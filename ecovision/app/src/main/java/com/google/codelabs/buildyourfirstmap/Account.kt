package com.google.codelabs.buildyourfirstmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_rewards.*

class Account : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        RewardsBtn.setOnClickListener {
            var myIntent = Intent(this, Rewards::class.java)
            startActivity(myIntent)
        }

        Education.setOnClickListener {
            var myIntent = Intent(this, EducationalVideos::class.java)
            startActivity(myIntent)
        }
        Games.setOnClickListener {
            var myIntent = Intent(this, GameListPage::class.java)
            startActivity(myIntent)
        }
    }
}
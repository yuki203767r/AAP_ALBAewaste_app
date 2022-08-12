package com.google.codelabs.buildyourfirstmap

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_rewards.*

class Rewards : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewards)

        RewardsBtn.setOnClickListener {
            var myIntent = Intent(this, Rewards::class.java)
            startActivity(myIntent)
        }

        AccountBtn.setOnClickListener {
            var myIntent = Intent(this, Account::class.java)
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
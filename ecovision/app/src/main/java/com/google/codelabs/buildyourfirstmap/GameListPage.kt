package com.google.codelabs.buildyourfirstmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_rewards.*

class GameListPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_list_page)

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

    }

    fun clickGame1(view: View) {
//        val intent = Intent(this,CelGame::class.java)
//        startActivity(intent)
    }
    fun clickGame2(view: View) {
        val intent = Intent(this,MemoryGame::class.java)
        startActivity(intent)
    }
}
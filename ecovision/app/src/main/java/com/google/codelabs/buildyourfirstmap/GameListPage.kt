package com.google.codelabs.buildyourfirstmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class GameListPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_list_page)
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
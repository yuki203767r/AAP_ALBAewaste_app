package com.google.codelabs.buildyourfirstmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_rewards.*

class RewardsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewards)

        RewardsBtn.setOnClickListener {
            var myIntent = Intent(this, RewardsActivity::class.java)

        }
    }
}
package com.example.tic_tac_toe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate

class Result : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_result)

        val winnerText = intent.getStringExtra("winnerText")
        val winnerImageView: ImageView = findViewById(R.id.winnerImageView)

        when (winnerText) {
            "x" -> winnerImageView.setImageResource(R.drawable.winnerx)
            "o" -> winnerImageView.setImageResource(R.drawable.winnero)
            else -> winnerImageView.setImageResource(R.drawable.draw)
        }

        val restartButton: Button = findViewById(R.id.restartButton)
        restartButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}


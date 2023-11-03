package com.noureldin.cinemaxplay.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.noureldin.cinemaxplay.R

class IntroActivity : AppCompatActivity() {
    private lateinit var getStarted : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        getStarted = findViewById(R.id.getStarted_button)
        getStarted.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }


    }
}
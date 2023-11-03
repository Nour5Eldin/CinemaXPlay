package com.noureldin.cinemaxplay.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.noureldin.cinemaxplay.R

class LoginActivity : AppCompatActivity() {
    private lateinit var userEdt: EditText
    private lateinit var passEdt: EditText
    private lateinit var loginBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
    }
    private fun initView() {
        userEdt = findViewById(R.id.userName_editText)
        passEdt = findViewById(R.id.userPassword_editText)
        loginBtn = findViewById(R.id.button2_login)

        loginBtn.setOnClickListener {
            if (userEdt.text.toString().isEmpty() || passEdt.text.toString().isEmpty()) {
                Toast.makeText(this@LoginActivity, "Please Fill your user and password", Toast.LENGTH_SHORT).show()
            } else if (userEdt.text.toString() == "test" && passEdt.text.toString() == "test") {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            } else {
                Toast.makeText(this@LoginActivity, "Your user and password is not correct", Toast.LENGTH_SHORT).show()
            }
        }


    }
}
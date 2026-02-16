package com.example.driverlinkexno2c

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBtn: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn = findViewById(R.id.loginBtn)
        progressBar = findViewById(R.id.loginProgress)

        loginBtn.setOnClickListener {

            // Show ProgressBar
            progressBar.visibility = View.VISIBLE
            loginBtn.isEnabled = false

            // Delay 2 seconds (simulate authentication)
            Handler(Looper.getMainLooper()).postDelayed({

                progressBar.visibility = View.GONE
                loginBtn.isEnabled = true

                startActivity(Intent(this, DashboardActivity::class.java))
                finish()

            }, 2000)
        }
    }
}

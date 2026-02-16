package com.example.driverlinkexno2c

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class homeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)   // OR activity_home (use what actually exists)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, AuthChoiceActivity::class.java))
            finish()
        }, 2500) // 2.5 seconds splash delay
    }
}

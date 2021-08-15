package com.example.minitweet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val show_profile_btn = findViewById<Button>(R.id.view_profile_btn)
        val twitter_handle = findViewById<EditText>(R.id.twitter_handle)

        show_profile_btn.setOnClickListener {
            val twitter_handle_string: String = twitter_handle.text.toString().substring(1)
            val intent = Intent(this, DisplayProfile::class.java)
            intent.putExtra("twitter_handle", twitter_handle_string)
            startActivity(intent)
        }

    }
}
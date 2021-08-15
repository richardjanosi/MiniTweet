package com.example.minitweet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.minitweet.DataFetch as DataFetch1


class DisplayProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

    }

    override fun onStart() {
        super.onStart()

        val twitter_handle = intent.getStringExtra("twitter_handle").toString() //fetching handle data from intent

        val profileData = DataFetch1(this)
        profileData.fetchProfileData(twitter_handle,this)

        val closeButton = findViewById<Button>(R.id.closeBtn)
        closeButton.setOnClickListener{
            finish()
        }
    }
}
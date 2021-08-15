package com.example.minitweet

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide

class DataFetch(val activity: Activity) {

    val profileNameText = activity.findViewById<TextView>(R.id.profile_name)
    val profileHandleText = activity.findViewById<TextView>(R.id.tweeterProfileHandle)
    val profilePic = activity.findViewById<ImageView>(R.id.profile_pic)
    val scrollist = activity.findViewById<LinearLayout>(R.id.listLayout)

    fun fetchProfileData (twitter_handle: String, context: Context) {
        val queue = Volley.newRequestQueue(context)
        val url = "https://api.twitter.com/2/users/by/username/"+twitter_handle+"?user.fields=profile_image_url" // GET request url
        val jsonObjectRequest =object : JsonObjectRequest(
            Method.GET, url, null,
            Response.Listener { response ->
                val nameStr = response.getJSONObject("data").getString("name")
                profileNameText.text = nameStr
                profileHandleText.text = "@"+twitter_handle
                val profilePicURL = response.getJSONObject("data").getString("profile_image_url")
                Glide.with(context).load(profilePicURL).into(profilePic)
                val userID = response.getJSONObject("data").getString("id")
                fetchtweets(activity,twitter_handle,userID,nameStr,profilePicURL)
            },
            Response.ErrorListener { error ->
                // TODO: Handle error
            }
        )
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] =
                    "Bearer AAAAAAAAAAAAAAAAAAAAAN9aSgEAAAAAIEdk1Mn23xRLmB51siV4xdlWxNI%3DZxe1shFmq4ALw20iQAbwkTnJjxJk7O1Qi6n7OwOM433xPfOqUF"
                return headers
            }
        }
        queue.add(jsonObjectRequest)


    }

    fun fetchtweets (context: Context, handle: String,id: String,userName: String,userPic: String) {
        val queue = Volley.newRequestQueue(context.applicationContext)

        val url = "https://api.twitter.com/2/users/"+id+"/tweets?tweet.fields=created_at&expansions=attachments.media_keys&media.fields=preview_image_url,url" // GET request url

        val jsonObjectRequest =object : JsonObjectRequest(
            Method.GET, url, null,
            Response.Listener { response ->

                val userId = response.getJSONArray("data")
                //val media = response.getJSONObject("includes")
                //Log.i("Array",media.toString())
                for (i in 0 until userId.length()) {
                    val jsonVal = userId.getJSONObject(i)
                    val layoutinflater: LayoutInflater = LayoutInflater.from(context)
                    val view: View = layoutinflater.inflate(R.layout.card,scrollist,false)
                    val tweetpost = view.findViewById<TextView>(R.id.profileName)
                    val tweethandle = view.findViewById<TextView>(R.id.twitterhandle)
                    val tweetPic = view.findViewById<ImageView>(R.id.profilePic)
                    val tweetText = view.findViewById<TextView>(R.id.tweetcontent)
                    val tweetDate = view.findViewById<TextView>(R.id.date)
                    tweetpost.text = userName
                    tweethandle.text = "@"+handle
                    tweetDate.text = jsonVal.getString("created_at").substring(0,10)
                    tweetText.text = jsonVal.getString("text")
                    Glide.with(context).load(userPic).into(tweetPic)
                    scrollist.addView(view,0)

                }

            },
            Response.ErrorListener { error ->
                // TODO: Handle error
            }
        )
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer AAAAAAAAAAAAAAAAAAAAAN9aSgEAAAAAIEdk1Mn23xRLmB51siV4xdlWxNI%3DZxe1shFmq4ALw20iQAbwkTnJjxJk7O1Qi6n7OwOM433xPfOqUF"
                return headers
            }
        }

        queue.add(jsonObjectRequest)
    }
}
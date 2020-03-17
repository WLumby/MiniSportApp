package com.example.minisportapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.sport_tile.view.*

class DisplayStoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_story)

        val data = intent.getStringExtra("com.minisportapp.storydata")

        val gson = Gson()
        val item = gson.fromJson(data, Item::class.java)

        findViewById<TextView>(R.id.story_title).text = item.title
        Picasso.get().load(item.image.large).into(findViewById<ImageView>(R.id.story_image));
    }
}

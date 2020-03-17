package com.example.minisportapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.sport_tile.view.*

class DisplayStoryActivity : AppCompatActivity() {

    private lateinit var item: Item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_story)

        val data = intent.getStringExtra("com.minisportapp.storydata")

        Log.d("TEST", data)

        val gson = Gson()
        item = gson.fromJson(data, Item::class.java)

        findViewById<TextView>(R.id.story_title).text = item.title
        findViewById<TextView>(R.id.story_time).text = item.lastUpdatedText
        findViewById<TextView>(R.id.story_sport).text = item.sectionLabel
        findViewById<ImageView>(R.id.story_image).contentDescription = item.image.altText
        Picasso.get().load(item.image.large).into(findViewById<ImageView>(R.id.story_image));
    }

    fun onShare(view: View) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, item.url)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}

package com.example.minisportapp

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.sport_tile.view.*
import okhttp3.OkHttpClient


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var sportData: SportData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        threadPermitAll()

        val wrapper = HTTPWrapper()
        val rawData = wrapper.getRawData(
            OkHttpClient(),
            "https://bbc.github.io/sport-app-dev-tech-challenge/data.json"
        )
        sportData = parseSportData(rawData)


        // Configure recycler view
        viewManager = LinearLayoutManager(this)
        viewAdapter = SportAdapter(sportData)

        recyclerView = findViewById<RecyclerView>(R.id.sport_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(DividerItemDecoration(recyclerView.context, layoutManager.orientation))
        }
    }

    /**
     * Function executed by story click handler
     */
    private fun expandStory(item: Item) {
        val gson = Gson()
        val serialisedItem = gson.toJson(item)

        val intent = Intent(this, DisplayStoryActivity::class.java).apply {
            putExtra("com.minisportapp.storydata", serialisedItem)
        }
        startActivity(intent)
    }

    /**
     * Click handler for sport stories
     */
    fun onStoryClick(view: View) {
        expandStory(sportData.data.items[view.tag as Int])
    }

    /**
     * Function to permit all for thread policy
     */
    private fun threadPermitAll() {
        val policy = StrictMode.ThreadPolicy.Builder()
            .permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }
}

package com.example.minisportapp


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import okhttp3.OkHttpClient
import java.util.*


class MainActivity : AppCompatActivity(), OnSPortDataResultListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var sportData: SportData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createHomepage()
    }

    private fun createHomepage() {
        val sportDataRepository = SportDataRepositoryFactory.create()
        sportDataRepository.listener = this
        sportDataRepository.getAndParseSportData()
    }

    /**
     * Function to create and schedule a random story notification
     */
    private fun createRandomNotification(context: Context, notificationWrapper: NotificationWrapper, delay: Long, period: Long) {
        val notificationTask = NotificationTimerTask(notificationWrapper, sportData, context)
        Timer().schedule(notificationTask, delay, period)
    }

    /**
     * Click handler for sport stories
     * Starts new activity with intent of clicked story
     */
    fun onStoryClick(view: View) {
        val serialisedItem = Gson().toJson(sportData.data.items[view.tag as Int])

        val intent = Intent(this, DisplayStoryActivity::class.java).apply {
            putExtra("com.minisportapp.storydata", serialisedItem)
        }
        startActivity(intent)
    }

    override fun onResult(sportData: SportData) {
        val notificationWrapper = NotificationWrapper()
        val statsManager = Stats()

        notificationWrapper.createNotificationChannel(
            this.applicationContext,
            "minisport.channel.id"
        )

        createRandomNotification(this.applicationContext, notificationWrapper, 60000, 60000)

        // Configure recycler view
        viewManager = LinearLayoutManager(this)
        viewAdapter = SportAdapter(sportData)

        recyclerView = findViewById<RecyclerView>(R.id.sport_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )

        statsManager.sendStats(
            OkHttpClient(),
            "load",
            "data",
            System.currentTimeMillis().toString()
        )
    }
}



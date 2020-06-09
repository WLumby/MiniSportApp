package com.example.minisportapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minisportapp.observable.SportObserver
import com.example.minisportapp.repository.SportData
import com.google.gson.Gson
import java.util.*


class MainActivity : AppCompatActivity(), SportObserver<SportData?> {

    private lateinit var recyclerView: RecyclerView

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var viewModel: DataRepositoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = DataRepositoryViewModel()
        viewModel.attachToRepository()
        viewModel.data.observe(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.data.unsubscribe()
    }

    /**
     * Function to create and schedule a random story notification
     */
    private fun createRandomNotification(context: Context, notificationWrapper: NotificationWrapper, delay: Long, period: Long) {
        viewModel.data.value?.also { data ->
            val notificationTask = NotificationTimerTask(notificationWrapper, data, context)
            Timer().schedule(notificationTask, delay, period)
        }
    }

    /**
     * Click handler for sport stories
     * Starts new activity with intent of clicked story
     */
    fun onStoryClick(view: View) {
        val sportData = viewModel.data.value
        sportData?.data?.also { data ->
            val serialisedItem = Gson().toJson(data.items[view.tag as Int])

            val intent = Intent(this, DisplayStoryActivity::class.java).apply {
                putExtra("com.minisportapp.storydata", serialisedItem)
            }
            startActivity(intent)
        }
    }

    override fun onValueChanged(sportData: SportData?) {

        sportData ?: return

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

        // FIXME: this crashes with android.os.NetworkOnMainThreadException (Stats uses the httpClient on the main thread)
//        statsManager.sendStats(
//            OkHttpClient(),
//            "load",
//            "data",
//            System.currentTimeMillis().toString()
//        )
    }
}



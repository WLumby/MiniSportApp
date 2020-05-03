package com.example.minisportapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minisportapp.observable.Observer
import com.example.minisportapp.repository.SportData
import com.google.gson.Gson
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var sportData: SportData
    private lateinit var viewModel: DataRepositoryViewModel
    private lateinit var disposableObserver: DisposableObserver<SportData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = DataRepositoryViewModel()
    }

    override fun onResume() {
        super.onResume()
        disposableObserver = viewModel.data.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableObserver<SportData>() {
                override fun onComplete() {
                    Log.d("", "")
                }

                override fun onNext(data: SportData) {
                    Log.d("", "")
                    onData(data)
                }

                override fun onError(e: Throwable) {
                    Log.d("", "")
                }
            });
    }

    override fun onDestroy() {
        super.onDestroy()
        disposableObserver.dispose()
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

    private fun onData(value: SportData?) {
        value?.let {
            this.sportData = it
        }
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



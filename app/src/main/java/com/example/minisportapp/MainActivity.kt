package com.example.minisportapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minisportapp.repository.SportData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.observers.DisposableObserver

sealed class MainViewEvent
data class Resumed(private val bla: Int) : MainViewEvent()

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    private lateinit var viewModel: MainViewModel
    private lateinit var disposableObserver: DisposableObserver<MainViewDisplayData>
    lateinit var viewEventEmitter: ObservableEmitter<MainViewEvent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = MainViewModel()
        val viewEvents = Observable.create { emitter : ObservableEmitter<MainViewEvent> ->
            viewEventEmitter = emitter
        }
        disposableObserver = viewEvents
            .flatMap { viewModel.displayData }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableObserver<MainViewDisplayData>() {
                override fun onComplete() {
                    Log.d("", "")
                }

                override fun onNext(data: MainViewDisplayData) {
                    when (data) {
                        is Success -> onDisplaySuccess(data.data)
                        is Error -> onDisplayError(data.message)
                    }
                }

                override fun onError(e: Throwable) {
                    onDisplayError(e.toString())
                }
            })
    }

    override fun onResume() {
        super.onResume()
        viewEventEmitter.onNext(Resumed(1))
    }

    override fun onDestroy() {
        super.onDestroy()
        disposableObserver.dispose()
    }

    private fun onDisplaySuccess(sportData: SportData) {
        // FIXME: do not do this on every callback, should only update the adapter
        recyclerView = findViewById<RecyclerView>(R.id.sport_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = SportAdapter(sportData)
        }
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }

    private fun onDisplayError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}



package com.example.minisportapp.mvasterisk

import android.os.Bundle
import android.widget.Toast
import com.example.minisportapp.repository.Item
import com.example.minisportapp.repository.SportDataRepositoryFactory

class TopicItemListMVPActivity : BaseMVAsteriskActivity(), TopicItemListView {

    private lateinit var presenter: TopicItemListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = TopicItemListPresenter(SportDataRepositoryFactory.create())
        presenter.view = this
    }

    override fun onResume() {
        super.onResume()
        presenter.onViewReady()
    }

    override fun displayTopicItems(items: List<Item>) {
        updateListItems(items)
    }

    override fun displayError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}

package com.example.minisportapp.mvasterisk.mvvm

import android.os.Bundle
import android.widget.Toast
import com.example.minisportapp.mvasterisk.BaseMVAsteriskActivity
import com.example.minisportapp.repository.SportDataRepositoryFactory

class TopicItemListMVVMActivity : BaseMVAsteriskActivity(), TopicItemListViewModel.Observer {

    private lateinit var viewModel: TopicItemListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = TopicItemListViewModel(SportDataRepositoryFactory.create())
        viewModel.observer = this
    }

    override fun onResume() {
        super.onResume()
        viewModel.onViewReady()
    }

    override fun onDataChanged(data: TopicListItemData) {
        when(data) {
            is TopicListItems -> updateListItems(data.items)
            is TopicListError -> Toast.makeText(this, data.message, Toast.LENGTH_LONG).show()
        }
    }
}

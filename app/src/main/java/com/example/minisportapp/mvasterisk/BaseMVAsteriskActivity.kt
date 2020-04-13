package com.example.minisportapp.mvasterisk

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minisportapp.R
import com.example.minisportapp.repository.Item
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_topic_item_list.*
import kotlinx.android.synthetic.main.sport_tile.view.*

@SuppressLint("Registered")
open class BaseMVAsteriskActivity : AppCompatActivity() {

    private lateinit var viewAdapter: TopicItemListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_item_list)

        viewAdapter = TopicItemListAdapter()
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@BaseMVAsteriskActivity)
            adapter = viewAdapter
        }

        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }

    protected fun updateListItems(items: List<Item>) {
        viewAdapter.updateDate(items)
        viewAdapter.notifyDataSetChanged()
    }
}

class TopicItemListAdapter : RecyclerView.Adapter<TopicItemListAdapter.ViewHolder>() {
    private var items: List<Item> = listOf()

    fun updateDate(items: List<Item>) {
        this.items = items
    }

    class ViewHolder(val tile: ConstraintLayout) : RecyclerView.ViewHolder(tile)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val tile = LayoutInflater.from(parent.context).inflate(R.layout.sport_tile, parent, false) as ConstraintLayout
        return ViewHolder(tile)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tile.getViewById(R.id.tile_title).tile_title.text = items[position].title
        holder.tile.getViewById(R.id.tile_time).tile_time.text = items[position].lastUpdatedText
        holder.tile.getViewById(R.id.tile_title).tag = position
        holder.tile.getViewById(R.id.tile_image).contentDescription = items[position].image.altText
        Picasso.get().load(items[position].image.medium).into(holder.tile.getViewById(R.id.tile_image).tile_image);
    }

    override fun getItemCount() = items.size
}

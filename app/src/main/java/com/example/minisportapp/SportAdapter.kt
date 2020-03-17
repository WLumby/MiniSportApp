package com.example.minisportapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.sport_tile.view.*

class SportAdapter(private val dataset: SportData) :
    RecyclerView.Adapter<SportAdapter.MyViewHolder>() {
        class MyViewHolder(val tile: ConstraintLayout) : RecyclerView.ViewHolder(tile)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val tile = LayoutInflater.from(parent.context)
                .inflate(R.layout.sport_tile, parent, false) as ConstraintLayout
            return MyViewHolder(tile)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.tile.getViewById(R.id.tile_title).tile_title.text = dataset.data.items[position].title
            holder.tile.getViewById(R.id.tile_time).tile_time.text = dataset.data.items[position].lastUpdatedText
            holder.tile.getViewById(R.id.tile_title).tag = position
            Picasso.get().load(dataset.data.items[position].image.medium).into(holder.tile.getViewById(R.id.tile_image).tile_image);
        }

        override fun getItemCount() = dataset.data.items.size
}
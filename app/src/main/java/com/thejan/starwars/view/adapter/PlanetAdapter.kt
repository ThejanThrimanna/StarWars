package com.thejan.starwars.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thejan.starwars.databinding.LayoutPlanetItemBinding
import com.thejan.starwars.model.Planet

/**
 * Created by Thejan Thrimanna on 11/13/21.
 */
class PlanetAdapter(private var list: ArrayList<Planet>, private val listener: ClickItem) :
    RecyclerView.Adapter<PlanetAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val itemBinding =
            LayoutPlanetItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val view = itemBinding.root
        return ViewHolder(
            view,
            itemBinding
        )
    }

    fun setList(list: List<Planet>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list[position].let { holder.bind(it) }
    }

    inner class ViewHolder(
        itemView: View,
        private val itemBinding: LayoutPlanetItemBinding
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(planet: Planet) {
            itemBinding.tvName.text = planet.name
            itemBinding.tvClimate.text = planet.climate

            itemView.setOnClickListener {
                listener.itemClick(adapterPosition, planet)
            }
        }
    }

    interface ClickItem {
        fun itemClick(position: Int, lead: Planet)
    }
}
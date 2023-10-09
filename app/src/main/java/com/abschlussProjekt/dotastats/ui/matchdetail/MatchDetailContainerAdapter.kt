package com.abschlussProjekt.dotastats.ui.matchdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abschlussProjekt.dotastats.data.datamodels.Player
import com.abschlussProjekt.dotastats.databinding.DetailMatchListItemContainerBinding


class MatchDetailContainerAdapter(val dataset: List<Player>) :
    RecyclerView.Adapter<MatchDetailContainerAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: DetailMatchListItemContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            DetailMatchListItemContainerBinding.inflate(
                LayoutInflater.from(
                    parent
                        .context
                ), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val player = dataset[position]

        holder.binding.detailMatchRV.adapter = MatchDetailAdapter(player.toList())

    }
}
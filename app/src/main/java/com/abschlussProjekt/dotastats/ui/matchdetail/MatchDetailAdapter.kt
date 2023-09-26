package com.abschlussProjekt.dotastats.ui.matchdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abschlussProjekt.dotastats.data.datamodels.Player
import com.abschlussProjekt.dotastats.databinding.DetailMatchListItemBinding


class MatchDetailAdapter(val dataset: List<Player>) :
    RecyclerView.Adapter<MatchDetailAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: DetailMatchListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            DetailMatchListItemBinding.inflate(
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

        with(holder.binding) {
            stringItemTV.text = if (player.name?.isNotBlank() == true)
                player.name
            else
                player.personaname
        }
    }
}
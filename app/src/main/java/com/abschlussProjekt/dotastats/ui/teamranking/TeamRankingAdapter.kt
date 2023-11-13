package com.abschlussProjekt.dotastats.ui.teamranking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.abschlussProjekt.dotastats.R
import com.abschlussProjekt.dotastats.data.datamodels.ProTeam
import com.abschlussProjekt.dotastats.databinding.TeamListItemBinding

class TeamRankingAdapter(private val dataset: List<ProTeam>) :
    RecyclerView.Adapter<TeamRankingAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding: TeamListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            TeamListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]

        with(holder.binding)
        {
            rankTV.text = "${position + 1}."
            logoIV.load(item.logo_url) {
                error(R.drawable.baseline_image_not_supported_24)
            }
            teamNameTV.text = item.name
            ratingTV.text = item.rating.toInt().toString()

            teamListItemLayout.setOnClickListener {
                it.findNavController().navigate(
                    TeamRankingFragmentDirections.actionTeamRankingFragmentToTeamProfileFragment(
                        item.team_id
                    )
                )
            }
        }
    }
}
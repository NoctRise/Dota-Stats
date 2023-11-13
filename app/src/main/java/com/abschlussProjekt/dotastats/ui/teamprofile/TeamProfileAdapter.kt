package com.abschlussProjekt.dotastats.ui.teamprofile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.abschlussProjekt.dotastats.R
import com.abschlussProjekt.dotastats.data.datamodels.TeamRecentMatch
import com.abschlussProjekt.dotastats.databinding.TeamRecentMatchListItemBinding
import com.abschlussProjekt.dotastats.util.getDurationBetween
import com.abschlussProjekt.dotastats.util.getMatchDuration

class TeamProfileAdapter(
    private val dataset: List<TeamRecentMatch>
) :
    RecyclerView.Adapter<TeamProfileAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: TeamRecentMatchListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            TeamRecentMatchListItemBinding.inflate(
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
        val match = dataset[position]

        with(holder.binding) {

            val won = match.radiant == match.radiant_win

            matchResultTV.text = if (won) "Won" else "Lost"


            teamRecentMatchTeamNameIV.text = match.opposing_team_name
            teamRecentMatchLogoIV.load(match.opposing_team_logo)
            {
                error(R.drawable.baseline_image_not_supported_24)
            }
            teamMatchDuration.text = getMatchDuration(match.duration)
            lastMatchTimeTV.text = getDurationBetween(match.start_time)


            teamRecentMatchLayout.setOnClickListener {
                it.findNavController().navigate(
                    TeamProfileFragmentDirections.actionTeamProfileFragmentToMatchDetailFragment(
                        match.match_id
                    )
                )
            }
        }
    }
}

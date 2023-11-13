package com.abschlussProjekt.dotastats.ui.player

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.abschlussProjekt.dotastats.data.datamodels.PlayerRecentMatch
import com.abschlussProjekt.dotastats.databinding.PlayerRecentMatchListItemBinding
import com.abschlussProjekt.dotastats.util.getDurationBetween
import com.abschlussProjekt.dotastats.util.getMatchDuration
import com.abschlussProjekt.dotastats.util.res_url

class PlayerAdapter(
    private val dataset: List<PlayerRecentMatch>
) :
    RecyclerView.Adapter<PlayerAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: PlayerRecentMatchListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            PlayerRecentMatchListItemBinding.inflate(
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
            playerHeroIV.load(res_url + match.hero!!.img)
            heroNameTV.text = match.hero!!.name
            kdaTV.text = "${match.kills}/${match.deaths}/${match.assists}"
            matchDurationTV.text = getMatchDuration(match.duration)
            playerLastMatchTimeTV.text = getDurationBetween(match.start_time)

            playerRecentMatchItem.setOnClickListener {
                it.findNavController().navigate(
                    PlayerFragmentDirections.actionPlayerFragmentToMatchDetailFragment(match.match_id)
                )
            }
        }
    }
}

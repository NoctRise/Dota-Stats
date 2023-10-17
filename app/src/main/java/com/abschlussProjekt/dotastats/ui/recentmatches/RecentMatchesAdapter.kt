package com.abschlussProjekt.dotastats.ui.recentmatches

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abschlussProjekt.dotastats.data.datamodels.ProMatch
import com.abschlussProjekt.dotastats.databinding.MatchListItemBinding
import com.abschlussProjekt.dotastats.ui.DotaViewModel
import com.abschlussProjekt.dotastats.util.getMatchDuration

class RecentMatchesAdapter(
    private val dataset: List<ProMatch>,
    private val viewModel: DotaViewModel
) :
    RecyclerView.Adapter<RecentMatchesAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: MatchListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            MatchListItemBinding.inflate(
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
            radiantNameTV.text = match.radiant_name
            direNameTV.text = match.dire_name

            durationTV.text = getMatchDuration(match.duration)
            if (match.radiant_win) {
                radiantWinIV.visibility = View.VISIBLE
                direWinIV.visibility = View.INVISIBLE
            } else {
                radiantWinIV.visibility = View.INVISIBLE
                direWinIV.visibility = View.VISIBLE
            }

            constraintLayout.setOnClickListener {
                viewModel.getMatchById(match.match_id)

                // Listener entfernen, um Navigation Exception zu verhindern
                it.setOnClickListener(null)
            }
        }
    }
}
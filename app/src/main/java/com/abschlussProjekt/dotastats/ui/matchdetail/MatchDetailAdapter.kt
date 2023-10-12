package com.abschlussProjekt.dotastats.ui.matchdetail


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.abschlussProjekt.dotastats.R
import com.abschlussProjekt.dotastats.data.datamodels.Player
import com.abschlussProjekt.dotastats.databinding.DetailMatchListItemBinding
import com.abschlussProjekt.dotastats.util.getFormattedValue
import com.abschlussProjekt.dotastats.util.res_url


class MatchDetailAdapter(val dataset: List<*>) :
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
            when (player) {
                is Player -> {


                    playerNameTV.text = when {
                        player.name?.isNotBlank() == true -> player.name
                        player.personaname?.isNotBlank() == true -> player.personaname
                        else -> "Anonymous"
                    }

                    heroIV.load(res_url + player.hero.img)
                    levelTV.text = player.level.toString()
                    killsTV.text = player.kills.toString()
                    deathsTV.text = player.deaths.toString()
                    assistsTV.text = player.assists.toString()
                    lastHitDeniesTV.text = "${player.last_hits}/${player.denies}"
                    networthTV.text = getFormattedValue(player.net_worth)

                    gxpmTV.text =
                        "${getFormattedValue(player.gold_per_min)}/${getFormattedValue(player.xp_per_min)}"
                    heroDMGTV.text = getFormattedValue(player.hero_damage)
                    towerDMGTV.text = getFormattedValue(player.tower_damage)
                    heroHealingTV.text = getFormattedValue(player.hero_healing)

                    val inventoryList = listOf(item0IV, item1IV, item2IV, item3IV, item4IV, item5IV)

                    player.inventory.forEachIndexed { index, item ->
                        item?.let {
                            inventoryList[index].visibility = View.VISIBLE
                            inventoryList[index].load(res_url + item.img)
                        }
                    }

                    player.item_neutral?.let {
                        neutralItemIV.visibility = View.VISIBLE
                        neutralItemIV.load(res_url + it.img) {
                            transformations(RoundedCornersTransformation(75f))
                        }
                    }

                    val backpackList = listOf(backpack0IV, backpack1IV, backpack2IV)

                    player.backpack.forEachIndexed { index, item ->
                        item?.let {
                            backpackList[index].visibility = View.VISIBLE
                            backpackList[index].load(res_url + item.img)
                        }
                    }
                }

                else -> {

                    matchCL.background =
                        ContextCompat.getColor(matchCL.context, R.color.blue).toDrawable()
                    playerNameTV.text = "Player"
                    heroIV.visibility = View.INVISIBLE
                    levelTV.text = "Lvl"
                    killsTV.text = "K"
                    deathsTV.text = "D"
                    assistsTV.text = "A"
                    lastHitDeniesTV.text = "LH / D"
                    networthTV.text = "NET"

                    gxpmTV.text = "GPM/XMP"
                    heroDMGTV.text = "HD"
                    towerDMGTV.text = "TD"
                    heroHealingTV.text = "HH"
                    inventoryTV.visibility = View.VISIBLE
                    inventoryTV.text = "Items"
                    backpackTV.visibility = View.VISIBLE
                    backpackTV.text = "Backpack"
                }
            }

        }


    }
}
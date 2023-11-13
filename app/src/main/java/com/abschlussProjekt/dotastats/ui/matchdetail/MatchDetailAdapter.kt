package com.abschlussProjekt.dotastats.ui.matchdetail


import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.abschlussProjekt.dotastats.R
import com.abschlussProjekt.dotastats.data.datamodels.Player
import com.abschlussProjekt.dotastats.databinding.DetailMatchListItemBinding
import com.abschlussProjekt.dotastats.util.getFormattedValue
import com.abschlussProjekt.dotastats.util.res_url


class MatchDetailAdapter(
    private val dataset: List<*>, private val context: Context
) :
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val player = dataset[position]

        with(holder.binding) {
            when (player) {
                is Player -> {

                    val onClickListener = OnClickListener {
                        it.findNavController().navigate(
                            MatchDetailFragmentDirections.actionMatchDetailFragmentToPlayerFragment(
                                player.account_id!!
                            )
                        )
                    }
                    heroIV.setOnClickListener(onClickListener)
                    playerNameTV.setOnClickListener(onClickListener)

                    playerNameTV.text = when {
                        player.name?.isNotBlank() == true -> player.name
                        player.personaname?.isNotBlank() == true -> player.personaname
                        else -> {
                            playerNameTV.setOnClickListener(null)
                            heroIV.setOnClickListener(null)
                            playerNameTV.setTextColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.textColor
                                )
                            )
                            "Anonymous"
                        }
                    }

                    if (player.account_id == null) {
                        playerNameTV.setOnClickListener(null)
                        heroIV.setOnClickListener(null)
                        playerNameTV.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.textColor
                            )
                        )
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

                    matchCL.background = context.getColor(R.color.blue).toDrawable()

                    val viewList = listOf(
                        playerNameTV,
                        levelTV,
                        killsTV,
                        deathsTV,
                        assistsTV,
                        lastHitDeniesTV,
                        networthTV,
                        gxpmTV,
                        heroDMGTV,
                        towerDMGTV,
                        heroHealingTV,
                        inventoryTV,
                        backpackTV
                    )
                    val nameList = context.resources.getStringArray(R.array.match_detail_title)
                    val toolTipTextList =
                        context.resources.getStringArray(R.array.match_detail_tooltip)
                    viewList.forEachIndexed { index, textView ->
                        textView.setTextColor(context.getColor(R.color.white))
                        textView.text = nameList[index]
                        textView.tooltipText = toolTipTextList[index]
                    }

                    heroIV.visibility = View.INVISIBLE
                    inventoryTV.visibility = View.VISIBLE
                    backpackTV.visibility = View.VISIBLE
                }
            }
        }
    }
}
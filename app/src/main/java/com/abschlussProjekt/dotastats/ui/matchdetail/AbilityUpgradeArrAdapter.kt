package com.abschlussProjekt.dotastats.ui.matchdetail

import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.abschlussProjekt.dotastats.R
import com.abschlussProjekt.dotastats.data.datamodels.Player
import com.abschlussProjekt.dotastats.databinding.AbilityUpgradeArrListItemBinding
import com.abschlussProjekt.dotastats.util.res_url


class AbilityUpgradeArrAdapter(
    private val dataset: List<*>, val context: Context, private val maxLevel: Int
) :
    RecyclerView.Adapter<AbilityUpgradeArrAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: AbilityUpgradeArrListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val density = context.resources.displayMetrics.density

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            AbilityUpgradeArrListItemBinding.inflate(
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

        when (val player = dataset[position]) {
            is Player -> {

                val onClickListener = View.OnClickListener {
                    it.findNavController().navigate(
                        MatchDetailFragmentDirections.actionMatchDetailFragmentToPlayerFragment(
                            player.account_id!!
                        )
                    )
                }

                with(holder.binding) {
                    abilityUpgradeArrLayout.setBackgroundResource(R.drawable.layout_border)

                    val heroImage = ImageView(context)
                    abilityUpgradeArrLayout.addView(heroImage)

                    heroImage.load(res_url + player.hero.img) {
                        error(R.drawable.baseline_image_not_supported_24)
                    }

                    setParams(heroImage)
                    heroImage.scaleType = ImageView.ScaleType.FIT_XY

                    val playerName = TextView(context)

                    abilityUpgradeArrLayout.addView(playerName)
                    playerName.gravity = Gravity.CENTER or Gravity.START
                    setParams(playerName, width = 150)
                    playerName.maxLines = 1
                    playerName.ellipsize = TextUtils.TruncateAt.END

                    playerName.setOnClickListener(onClickListener)
                    heroImage.setOnClickListener(onClickListener)
                    playerName.setTextColor(
                        context.getColor(R.color.light_blue)
                    )
                    playerName.text = when {

                        player.name?.isNotBlank() == true -> player.name
                        player.personaname?.isNotBlank() == true -> player.personaname
                        else -> {
                            playerName.setOnClickListener(null)
                            heroImage.setOnClickListener(null)
                            playerName.setTextColor(
                                context.getColor(R.color.textColor)
                            )
                            "Anonymous"
                        }
                    }


                    player.ability_upgrades_arr?.forEach {
                        val skillIV = ImageView(context)
                        abilityUpgradeArrLayout.addView(skillIV)
                        setParams(skillIV)

                        when {
                            it.img?.isNotBlank() == true -> skillIV.load(res_url + it.img)
                            !it.skill_id_name?.contains("attributes")!! -> skillIV.load(R.drawable.talent_tree)
                        }


                        skillIV.scaleType = ImageView.ScaleType.FIT_XY
                    }

                }
            }

            else -> {
                with(holder.binding)
                {
                    abilityUpgradeArrLayout.background = context.getColor(R.color.blue).toDrawable()

                    val placeHolderView = View(context)
                    abilityUpgradeArrLayout.addView(placeHolderView)

                    setParams(placeHolderView)


                    val playerNamePlaceHolder = TextView(context)
                    abilityUpgradeArrLayout.addView(playerNamePlaceHolder)

                    playerNamePlaceHolder.gravity = Gravity.CENTER or Gravity.START
                    playerNamePlaceHolder.text =
                        context.resources.getStringArray(R.array.match_detail_title)[0]
                    playerNamePlaceHolder.setTextColor(context.getColor(R.color.white))
                    setParams(playerNamePlaceHolder, width = 150)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        playerNamePlaceHolder.tooltipText =
                            context.resources.getStringArray(R.array.match_detail_tooltip)[0]
                    }


                    for (i in (1..maxLevel)) {
                        val levelTV = TextView(context)
                        levelTV.text = "$i"
                        levelTV.setTextColor(context.getColor(R.color.white))
                        abilityUpgradeArrLayout.addView(levelTV)
                        setParams(playerNamePlaceHolder, width = 150)
                        levelTV.gravity = Gravity.CENTER

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            levelTV.tooltipText = context.getString(R.string.tool_tip_level)
                        }



                        setParams(levelTV)
                    }
                }
            }
        }
    }

    private fun setParams(
        view: View,
        height: Int = 45,
        width: Int = 55,
        marginStart: Int = 16,
        marginEnd: Int = 8
    ) {
        val layoutParams = view.layoutParams as LinearLayout.LayoutParams
        layoutParams.height = (height * density).toInt()
        layoutParams.width = (width * density).toInt()
        layoutParams.marginStart = marginStart
        layoutParams.marginEnd = marginEnd
    }


}
package com.abschlussProjekt.dotastats.ui.matchdetail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.abschlussProjekt.dotastats.MainActivity
import com.abschlussProjekt.dotastats.R
import com.abschlussProjekt.dotastats.data.datamodels.ProMatchDetail
import com.abschlussProjekt.dotastats.databinding.FragmentMatchDetailBinding
import com.abschlussProjekt.dotastats.ui.DotaViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


class MatchDetailFragment : Fragment() {

    private val binding: FragmentMatchDetailBinding by lazy {
        FragmentMatchDetailBinding.inflate(
            layoutInflater
        )
    }

    private lateinit var playerColors: IntArray

    private val viewModel: DotaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        // Rufe Ladescreen auf
        (requireContext() as MainActivity).showLoadingScreen(true)

        val matchId = requireArguments().getLong("id")
        viewModel.getMatchById(matchId)


        playerColors = requireContext().resources.getIntArray(R.array.playerColors)

        with(binding) {

            radiantTeamRV.setHasFixedSize(true)
            direTeamRV.setHasFixedSize(true)

            viewModel.detailProMatch.observe(viewLifecycleOwner) {
                it?.let { proMatchDetail ->

                    detailRadiantTV.text = proMatchDetail.radiant_team?.name ?: "Radiant"
                    direDetailTV.text = proMatchDetail.dire_team?.name ?: "Dire"
                    radiantTeamRV.adapter =
                        MatchDetailAdapter(
                            listOf(null) + proMatchDetail.players.take(5),
                            requireContext()
                        )
                    direTeamRV.adapter =
                        MatchDetailAdapter(
                            listOf(null) + proMatchDetail.players.takeLast(5),
                            requireContext()
                        )
                    // Wenn Daten vorhanden sind, zeige Chart an
                    proMatchDetail.radiant_gold_adv?.let { goldAdvantageList ->
                        initChart()

                        val goldColor = requireContext().getColor(R.color.gold)
                        val expColor = requireContext().getColor(R.color.textBlue)

                        advantageChip.setOnClickListener {
                            // reset Chart
                            chart.data = null

                            // Zeige Goldvorteil im Graph an
                            addToChart(goldAdvantageList, goldColor, "Gold Advantage")

                            proMatchDetail.radiant_xp_adv?.let { expAdvantageList ->
                                // Zeige Expvorteil im Graph an
                                addToChart(expAdvantageList, expColor, "Exp Advantage")
                            }
                            chart.legend.isEnabled = true
                            chart.animateXY(3000, 3000)
                        }

                        advantageChip.performClick()
                    }

                    if (proMatchDetail.players.filter { it.gold_t != null }.size == 10) {
                        graphChipGroup.visibility = View.VISIBLE
                        goldChip.visibility = View.VISIBLE
                        goldChip.setOnClickListener(
                            getChipListener(
                                proMatchDetail,
                                true
                            )
                        )
                    }

                    if (proMatchDetail.players.filter { it.xp_t != null }.size == 10) {
                        graphChipGroup.visibility = View.VISIBLE
                        expChip.visibility = View.VISIBLE

                        expChip.setOnClickListener(
                            getChipListener(
                                proMatchDetail,
                                false
                            )
                        )
                    }
                    // Blende Loading aus
                    (requireContext() as MainActivity).showLoadingScreen(false, 750L)
                }
            }
            return root
        }

    }


    private fun initChart() {
        binding.chart.visibility = View.VISIBLE

        with(binding)
        {
            chart.description.isEnabled = false

            chart.legend.textColor = Color.WHITE

            // blende Werte auf der rechten Seite aus & verschiebe X Werte nach unten
            chart.axisRight.isEnabled = false
            chart.xAxis.position = XAxis.XAxisPosition.BOTTOM

            chart.xAxis.textColor = Color.WHITE
            chart.axisLeft.textColor = Color.WHITE
        }
    }

    private fun addToChart(yDataList: List<Int>, color: Int, label: String) {

        val entryList = mutableListOf<Entry>()

        yDataList.forEachIndexed { index, i ->
            entryList.add(Entry(index.toFloat(), i.toFloat()))
        }

        val dataSet = LineDataSet(entryList, label)

        // blende Werte und Punkte im Graphen aus
        dataSet.setDrawValues(false)
        dataSet.setDrawCircles(false)

        dataSet.color = color
        dataSet.highLightColor = requireContext().getColor(R.color.textBlue)

        binding.chart.data?.let {
            binding.chart.data.addDataSet(dataSet)
        } ?: run { binding.chart.data = LineData(dataSet) }

        // Update y min/max für Darstellung
        binding.chart.axisLeft.axisMinimum = binding.chart.data.yMin
        binding.chart.axisLeft.axisMaximum = binding.chart.data.yMax
    }

    private fun getChipListener(
        proMatchDetail: ProMatchDetail,
        isGold: Boolean
    ): OnClickListener =
        OnClickListener {
            // reset Chart
            binding.chart.data = null


            // Iteriere Playerlist und füge die Daten dem Graphen hinzu
            proMatchDetail.players.forEachIndexed { index, player ->
                val dataList = if (isGold) proMatchDetail.players[index].gold_t!!
                else proMatchDetail.players[index].xp_t!!

                addToChart(
                    dataList,
                    playerColors[index],
                    player.account_id.toString()
                )
            }
            binding.chart.legend.isEnabled = false

            binding.chart.animateXY(3000, 3000)
        }

}
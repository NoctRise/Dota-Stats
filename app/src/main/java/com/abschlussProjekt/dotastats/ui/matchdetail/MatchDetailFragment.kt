package com.abschlussProjekt.dotastats.ui.matchdetail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.abschlussProjekt.dotastats.MainActivity
import com.abschlussProjekt.dotastats.R
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

    private val viewModel: DotaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        // Rufe Ladescreen auf
        (requireContext() as MainActivity).showLoadingScreen(true)

        binding.radiantTeamRV.setHasFixedSize(true)
        binding.direTeamRV.setHasFixedSize(true)

        viewModel.detailProMatch.observe(viewLifecycleOwner) {
            it?.let {
                binding.detailRadiantTV.text = it.radiant_team?.name ?: "Radiant"
                binding.direDetailTV.text = it.dire_team?.name ?: "Dire"
                binding.radiantTeamRV.adapter =
                    MatchDetailAdapter(
                        listOf(null) + it.players.take(5),
                        requireContext(),
                        viewModel
                    )
                binding.direTeamRV.adapter =
                    MatchDetailAdapter(
                        listOf(null) + it.players.takeLast(5),
                        requireContext(),
                        viewModel
                    )
                // Wenn Daten vorhanden sind, zeige Chart an
                it.radiant_gold_adv?.let { goldAdvantageList ->
                    initChart()
                    val color = requireContext().getColor(R.color.gold)
                    showChart(goldAdvantageList, color, "Gold Advantage")
                }

                it.radiant_xp_adv?.let { expAdvantageList ->
                    val color = requireContext().getColor(R.color.textBlue)
                    showChart(expAdvantageList, color, "Exp Advantage")
                }

                (requireContext() as MainActivity).showLoadingScreen(false)
            }
        }

        return binding.root
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

    private fun showChart(dataList: List<Int>, color: Int, label: String) {

        val entryList = mutableListOf<Entry>()

        dataList.forEachIndexed { index, i ->
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


    }
}
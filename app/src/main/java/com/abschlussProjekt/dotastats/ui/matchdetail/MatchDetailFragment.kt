package com.abschlussProjekt.dotastats.ui.matchdetail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import com.abschlussProjekt.dotastats.MainActivity
import com.abschlussProjekt.dotastats.R
import com.abschlussProjekt.dotastats.data.datamodels.ProMatchDetail
import com.abschlussProjekt.dotastats.databinding.FragmentMatchDetailBinding
import com.abschlussProjekt.dotastats.ui.DotaViewModel
import com.abschlussProjekt.dotastats.util.res_url
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlin.math.abs


class MatchDetailFragment : Fragment() {

    private val binding: FragmentMatchDetailBinding by lazy {
        FragmentMatchDetailBinding.inflate(
            layoutInflater
        )
    }

    private lateinit var playerColors: IntArray

    private val viewModel: DotaViewModel by activityViewModels()

    private lateinit var graphIconList: List<ImageView>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        // Rufe Ladescreen auf
        (requireContext() as MainActivity).showLoadingScreen(true)

        val matchId = requireArguments().getLong("id")
        viewModel.getMatchById(matchId)

        graphIconList = listOf(
            binding.radiantIcon1,
            binding.radiantIcon2,
            binding.radiantIcon3,
            binding.radiantIcon4,
            binding.radiantIcon5,
            binding.direIcon1,
            binding.direIcon2,
            binding.direIcon3,
            binding.direIcon4,
            binding.direIcon5,
        )


        playerColors = requireContext().resources.getIntArray(R.array.playerColors)

        with(binding) {

            radiantTeamRV.setHasFixedSize(true)
            direTeamRV.setHasFixedSize(true)

            viewModel.detailProMatch.observe(viewLifecycleOwner) {
                it?.let { proMatchDetail ->

                    detailRadiantTV.text = proMatchDetail.radiant_team?.name ?: "Unknown"
                    direDetailTV.text = proMatchDetail.dire_team?.name ?: "Unknown"

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

                    when (it.radiant_win) {
                        true -> {
                            radiantWonIV.visibility = View.VISIBLE
                            direWonIV.visibility = View.GONE
                        }

                        else -> {
                            radiantWonIV.visibility = View.GONE
                            direWonIV.visibility = View.VISIBLE
                        }
                    }


                    // Füge "min" zu den X Achsenwerten hinzu
                    chart.xAxis.valueFormatter = object : ValueFormatter() {

                        override fun getFormattedValue(value: Float): String {
                            return "${value.toInt()}min"
                        }
                    }

                    // Zeige absolut Werte auf der Y Achse an
                    chart.axisLeft.valueFormatter = object : ValueFormatter() {

                        override fun getFormattedValue(value: Float): String {
                            return "${abs(value.toInt())}"
                        }
                    }
                    // Erhöhe Abstand zwischen der Chart und der Legende
                    chart.extraBottomOffset = 8F

                    // Wenn Daten vorhanden sind, zeige Chart an
                    proMatchDetail.radiant_gold_adv?.let { goldAdvantageList ->
                        initChart()

                        val goldColor = requireContext().getColor(R.color.gold)
                        val expColor = requireContext().getColor(R.color.textBlue)

                        advantageChip.setOnClickListener {
                            // reset Chart
                            chart.data = null

                            graphIcons.visibility = View.GONE

                            // Zeige Goldvorteil im Graph an
                            addToChart(goldAdvantageList, goldColor, "Gold Advantage")

                            proMatchDetail.radiant_xp_adv?.let { expAdvantageList ->
                                // Zeige Expvorteil im Graph an
                                addToChart(expAdvantageList, expColor, "Exp Advantage")
                            }

                            //reset Zoom
                            chart.fitScreen()
                            chart.legend.isEnabled = true

                            // Notify damit sich Legend updated und korrekte Anzahl anzeigt
                            chart.notifyDataSetChanged()

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
                    // Blende Loading Screen aus
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

            chart.apply {
                this.description.isEnabled = false
                this.legend.textColor = Color.WHITE

                // blende Werte auf der rechten Seite aus & verschiebe X Werte nach unten
                this.axisRight.isEnabled = false
                this.xAxis.position = XAxis.XAxisPosition.BOTTOM

                this.xAxis.textColor = Color.WHITE
                this.axisLeft.textColor = Color.WHITE
            }

        }
    }

    private fun addToChart(yDataList: List<Int>, color: Int, label: String) {

        val entryList = mutableListOf<Entry>()

        yDataList.forEachIndexed { index, i ->
            entryList.add(Entry(index.toFloat(), i.toFloat()))
        }

        val dataSet = LineDataSet(entryList, label)

        // blende Werte und Punkte im Graphen aus

        dataSet.apply {
            this.setDrawValues(false)
            this.setDrawCircles(false)
            this.color = color
            this.highLightColor = requireContext().getColor(R.color.textBlue)
        }

        // Wenn Chart null ist, initialisiere mit LineData, ansonsten füge neue Daten hinzu
        binding.chart.data?.let {
            binding.chart.data.addDataSet(dataSet)
        } ?: run { binding.chart.data = LineData(dataSet) }

    }

    private fun getChipListener(
        proMatchDetail: ProMatchDetail,
        isGold: Boolean
    ): OnClickListener =
        OnClickListener {
            // reset Chart
            binding.chart.data = null
            binding.graphIcons.visibility = View.VISIBLE

            // Iteriere Playerlist und füge die Daten dem Graphen hinzu
            proMatchDetail.players.forEachIndexed { index, player ->
                val dataList = if (isGold) proMatchDetail.players[index].gold_t!!
                else proMatchDetail.players[index].xp_t!!

                addToChart(
                    dataList,
                    playerColors[index],
                    player.account_id.toString()
                )

                graphIconList[index].load(res_url + player.hero.img)
                val cardView = (graphIconList[index].parent as CardView)

                cardView.apply {
                    this.background = playerColors[index].toDrawable()
                    this.setOnClickListener {


                        // Blende jeweilige Dataset aus, wenn auf die Cardview gedrückt wird
                        when {
                            binding.chart.data.getDataSetByIndex(index).isVisible -> {

                                binding.chart.data.getDataSetByIndex(index).isVisible = false
                                binding.chart.invalidate()
                                it.background =
                                    requireContext().getColor(R.color.white).toDrawable()
                            }

                            !(binding.chart.data.getDataSetByIndex(index).isVisible) -> {
                                binding.chart.invalidate()
                                binding.chart.data.getDataSetByIndex(index).isVisible = true
                                it.background = playerColors[index].toDrawable()
                            }
                        }

                    }
                }


            }
            binding.chart.legend.isEnabled = false

            //reset Zoom
            binding.chart.fitScreen()

            binding.chart.notifyDataSetChanged()
            binding.chart.animateXY(3000, 3000)
        }
}
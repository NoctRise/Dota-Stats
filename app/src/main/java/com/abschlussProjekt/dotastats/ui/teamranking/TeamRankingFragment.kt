package com.abschlussProjekt.dotastats.ui.teamranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.abschlussProjekt.dotastats.MainActivity
import com.abschlussProjekt.dotastats.databinding.FragmentTeamRankingBinding
import com.abschlussProjekt.dotastats.ui.DotaViewModel
import java.util.Calendar
import java.util.Date

class TeamRankingFragment : Fragment() {


    private val binding: FragmentTeamRankingBinding by lazy {
        FragmentTeamRankingBinding.inflate(
            layoutInflater
        )
    }

    private val viewModel: DotaViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (requireContext() as MainActivity).showLoadingScreen(true)


        viewModel.proTeams.observe(viewLifecycleOwner) {
            (requireContext() as MainActivity).showLoadingScreen(false)
            binding.teamRankingRV.adapter =
                TeamRankingAdapter(
                    it.filter { team -> isActive(team.last_match_time) }.take(100)
                )
        }
        return binding.root
    }


    private fun isActive(timeStamp: Long): Boolean {
        val currentDate = Calendar.getInstance().time
        val lastMatchDate = Date(timeStamp * 1000)

        val timeDiff = currentDate.time - lastMatchDate.time
        val timeDiffInDays: Long = timeDiff / 1000 / 60 / 60 / 24

        return when {
            // True, wenn letztes Match innerhalb eines halben Jahres stattgefunden hat
            timeDiffInDays <= 180 -> true
            else -> false
        }
    }

    override fun onStart() {
        super.onStart()

        if (viewModel.proTeams.value == null)
            viewModel.getTeams()
    }
}
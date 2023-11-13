package com.abschlussProjekt.dotastats.ui.teamprofile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import com.abschlussProjekt.dotastats.MainActivity
import com.abschlussProjekt.dotastats.R
import com.abschlussProjekt.dotastats.databinding.FragmentTeamProfileBinding
import com.abschlussProjekt.dotastats.ui.DotaViewModel
import com.abschlussProjekt.dotastats.util.calcWinRate


class TeamProfileFragment : Fragment() {


    private val binding: FragmentTeamProfileBinding by lazy {
        FragmentTeamProfileBinding.inflate(
            layoutInflater
        )
    }

    private val viewModel: DotaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (requireContext() as MainActivity).showLoadingScreen(true)
        val teamID = requireArguments().getLong("id")
        viewModel.getTeamRecentMatches(teamID)

        viewModel.proTeamRecentMatches.observe(viewLifecycleOwner) {teamRecentMatchesList ->
            teamRecentMatchesList?.let {
                Log.e("Team", teamRecentMatchesList.toString())
                val teamProfile = viewModel.proTeams.value?.find { team ->
                    team.team_id == teamID
                }

                with(binding) {
                    teamIV.load(teamProfile?.logo_url) {
                        error(R.drawable.baseline_image_not_supported_24)
                    }
                    teamProfile?.let {
                        teamProfileNameTV.text = teamProfile.name
                        teamWinsValueTV.text = "${teamProfile.wins}"
                        teamLossValueTV.text = "${teamProfile.losses}"
                        teamsRatingValueTV.text = "${teamProfile.rating.toInt()}"
                        teamWinRateValueTV.text = calcWinRate(
                            mapOf(
                                "win" to teamProfile.wins,
                                "lose" to teamProfile.losses
                            )
                        )
                    }
                    teamRecentMatchesRV.adapter = TeamProfileAdapter(teamRecentMatchesList)
                }


                (requireContext() as MainActivity).showLoadingScreen(false)
                Log.e("TeamPro", "$teamProfile")
            }
        }

        return binding.root
    }
}
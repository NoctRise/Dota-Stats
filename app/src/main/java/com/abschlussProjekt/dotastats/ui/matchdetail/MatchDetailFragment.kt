package com.abschlussProjekt.dotastats.ui.matchdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.abschlussProjekt.dotastats.databinding.FragmentMatchDetailBinding
import com.abschlussProjekt.dotastats.ui.DotaViewModel


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
        val matchID = requireArguments().getLong("id")
        viewModel.getMatchById(matchID)
        binding.radiantTeamRV.setHasFixedSize(true)
        binding.direTeamRV.setHasFixedSize(true)

        viewModel.detailProMatch.observe(viewLifecycleOwner) {
            binding.detailRadiantTV.text = it.radiant_team?.name ?: "Radiant"
            binding.direDetailTV.text = it.dire_team?.name ?: "Dire"
            binding.radiantTeamRV.adapter =
                MatchDetailAdapter(listOf(null) + it.players.take(5), requireContext())
            binding.direTeamRV.adapter =
                MatchDetailAdapter(listOf(null) + it.players.takeLast(5), requireContext())
        }

        return binding.root
    }
}

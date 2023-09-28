package com.abschlussProjekt.dotastats.ui.matchdetail

import android.os.Bundle
import android.util.Log
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val matchID = requireArguments().getLong("id")
        viewModel.getMatchById(matchID)

        viewModel.detailProMatch.observe(viewLifecycleOwner) {
            binding.radiantTeamRC.adapter = MatchDetailAdapter(it.players.take(5))
            binding.direTeamRC.adapter = MatchDetailAdapter(it.players.takeLast(5))
            Log.e("MDF", it.toString())
        }
    }
}

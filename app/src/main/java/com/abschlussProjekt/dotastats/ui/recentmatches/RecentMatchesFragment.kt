package com.abschlussProjekt.dotastats.ui.recentmatches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.abschlussProjekt.dotastats.databinding.FragmentRecentMatchesBinding
import com.abschlussProjekt.dotastats.ui.DotaViewModel


class RecentMatchesFragment : Fragment() {

    private val binding: FragmentRecentMatchesBinding by lazy {
        FragmentRecentMatchesBinding.inflate(
            layoutInflater
        )
    }

    private val viewModel: DotaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getRecentProMatches()

        viewModel.getMatchById()


        viewModel.recentProMatches.observe(viewLifecycleOwner) {
            binding.matchRecyclerView.adapter = RecentMatchesAdapter(it)
        }
        return binding.root
    }
}
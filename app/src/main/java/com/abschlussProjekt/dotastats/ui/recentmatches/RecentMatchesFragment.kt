package com.abschlussProjekt.dotastats.ui.recentmatches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.abschlussProjekt.dotastats.MainActivity
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
        // Rufe Ladescreen auf und lade Daten von API
        (requireContext() as MainActivity).showLoadingScreen(true)

        viewModel.recentProMatches.observe(viewLifecycleOwner) {
            (requireContext() as MainActivity).showLoadingScreen(false)
            binding.matchRecyclerView.adapter = RecentMatchesAdapter(
                it
            )
        }

        return binding.root
    }


    override fun onStart() {
        super.onStart()
        viewModel.getRecentProMatches()
    }
}
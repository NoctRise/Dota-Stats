package com.abschlussProjekt.dotastats.ui.player

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import coil.transform.RoundedCornersTransformation
import com.abschlussProjekt.dotastats.MainActivity
import com.abschlussProjekt.dotastats.databinding.FragmentPlayerBinding
import com.abschlussProjekt.dotastats.ui.DotaViewModel


class PlayerFragment : Fragment() {

    private val binding: FragmentPlayerBinding by lazy {
        FragmentPlayerBinding.inflate(
            layoutInflater
        )
    }

    private val viewModel: DotaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (requireContext() as MainActivity).showLoadingScreen(true)

        // Inflate the layout for this fragment
        viewModel.playerProfile.observe(viewLifecycleOwner) {
            binding.profileIV.load(it.avatarfull) {
                transformations(RoundedCornersTransformation(250F))
            }
            binding.nameTV.text = it.name ?: it.personaname
        }

        viewModel.playerWinLose.observe(viewLifecycleOwner)
        {
            it?.let {
                with(binding)
                {
                    statsLayout.visibility = View.VISIBLE
                    winValueTV.text = it["win"].toString()
                    lossValueTV.text = it["lose"].toString()
                    winrateValueTV.text = calcWinRate(it)
                    (requireContext() as MainActivity).showLoadingScreen(false)
                }
            }
        }

        viewModel.playerRecentMatches.observe(viewLifecycleOwner) {
                binding.playerProfileRV.adapter = PlayerAdapter(it)
                Log.e("PlayerMatches", it.toString())
        }
        return binding.root
    }

    private fun calcWinRate(stats: Map<String, Int>): String {
        val totalMatches = stats["win"]!! + stats["lose"]!!
        val winRate = ((stats["win"]!!.toDouble() / totalMatches) * 100)
        return "${String.format("%.2f", winRate)}%"
    }
}
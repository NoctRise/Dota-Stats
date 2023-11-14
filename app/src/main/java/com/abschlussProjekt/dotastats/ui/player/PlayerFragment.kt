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
import com.abschlussProjekt.dotastats.util.calcWinRate


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


        viewModel.playerProfile.observe(viewLifecycleOwner) {
            it?.let {

                Log.e("profile", it.toString())

                with(binding) {

                    profileIV.load(it.playerProfileAPI.avatarfull) {
                        transformations(RoundedCornersTransformation(250F))
                    }
                    nameTV.text =
                        it.playerProfileAPI.name ?: it.playerProfileAPI.personaname

                    winValueTV.text = it.winLose["win"].toString()
                    lossValueTV.text = it.winLose["lose"].toString()
                    winrateValueTV.text = calcWinRate(it.winLose)
                    statsLayout.visibility = View.VISIBLE


                    binding.playerProfileRV.adapter = PlayerAdapter(it.recentMatches)
                }

                (requireContext() as MainActivity).showLoadingScreen(false, 1000L)

            }
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val accountID = requireArguments().getLong("accId")

        viewModel.getPlayerProfileByID(accountID)
    }
}
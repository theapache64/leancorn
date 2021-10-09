package com.theapache64.leancorn.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.theapache64.leancorn.R
import com.theapache64.leancorn.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentSplashBinding.inflate(inflater, container, false)

        viewModel.shouldGoToHome.asLiveData().observe(viewLifecycleOwner) { shouldGoToHome ->
            if (shouldGoToHome) {
                goToHome()
            }
        }

        return binding.root
    }

    private fun goToHome() {
        val action = SplashFragmentDirections.actionSplashToHome()
        findNavController().navigate(
            action,
            NavOptions.Builder()
                .setPopUpTo(R.id.splash_fragment, true)
                .build()
        )
    }

    companion object {
        fun newInstance() = SplashFragment()
    }
}
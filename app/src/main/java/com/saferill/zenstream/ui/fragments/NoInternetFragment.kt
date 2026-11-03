package com.saferill.zenstream.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.saferill.zenstream.R
import com.saferill.zenstream.databinding.FragmentNointernetBinding
import com.saferill.zenstream.helpers.NavigationHelper
import com.saferill.zenstream.helpers.NetworkHelper
import com.saferill.zenstream.ui.activities.SettingsActivity
import com.google.android.material.snackbar.Snackbar

class NoInternetFragment: Fragment(R.layout.fragment_nointernet) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentNointernetBinding.bind(view)
        binding.retryButton.setOnClickListener {
            if (NetworkHelper.isNetworkAvailable(requireContext())) {
                NavigationHelper.restartMainActivity(requireContext())
            } else {
                Snackbar.make(binding.root, R.string.turnInternetOn, Snackbar.LENGTH_LONG).show()
            }
        }
        binding.noInternetSettingsImageView.setOnClickListener {
            val intent = Intent(requireContext(), SettingsActivity::class.java)
            startActivity(intent)
        }

        binding.downloads.setOnClickListener {
            findNavController().navigate(R.id.downloadsFragment)
        }
    }
}
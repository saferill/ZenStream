package com.saferill.zenstream.ui.activities

import android.os.Bundle
import com.saferill.zenstream.databinding.ActivityHelpBinding
import com.saferill.zenstream.helpers.IntentHelper
import com.saferill.zenstream.ui.base.BaseActivity
import com.google.android.material.card.MaterialCardView

class HelpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        setupCard(binding.faq, FAQ_URL)
        setupCard(binding.matrix, MATRIX_URL)
        setupCard(binding.mastodon, MASTODON_URL)
        setupCard(binding.lemmy, LEMMY_URL)
    }

    private fun setupCard(card: MaterialCardView, link: String) {
        card.setOnClickListener {
            IntentHelper.openLinkFromHref(this, supportFragmentManager, link)
        }
    }

    companion object {
        private const val FAQ_URL = "https://github.com/saferill/ZenStream#readme"
        private const val MATRIX_URL = "https://github.com/saferill/ZenStream/issues"
        private const val MASTODON_URL = "https://github.com/saferill/ZenStream"
        private const val LEMMY_URL = "https://github.com/saferill/ZenStream"
    }
}

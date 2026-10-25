package com.saferill.zenstream.ui.activities

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import androidx.core.text.HtmlCompat
import androidx.core.text.parseAsHtml
import com.saferill.zenstream.R
import com.saferill.zenstream.databinding.ActivityAboutBinding
import com.saferill.zenstream.helpers.ClipboardHelper
import com.saferill.zenstream.helpers.IntentHelper
import com.saferill.zenstream.ui.base.BaseActivity
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class AboutActivity : BaseActivity() {
    private lateinit var binding: ActivityAboutBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        setupCard(binding.donate, DONATE_URL)
        setupCard(binding.website, WEBSITE_URL)
        setupCard(binding.piped, PIPED_GITHUB_URL)
        setupCard(binding.translate, WEBLATE_URL)
        setupCard(binding.github, GITHUB_URL)

        binding.license.setOnClickListener {
            showLicense()
        }
        binding.license.setOnLongClickListener {
            onLongClick(LICENSE_URL)
            true
        }

        binding.device.setOnClickListener {
            showDeviceInfo()
        }
    }

    private fun setupCard(card: MaterialCardView, link: String) {
        card.setOnClickListener {
            IntentHelper.openLinkFromHref(this, supportFragmentManager, link)
        }
        card.setOnLongClickListener {
            onLongClick(link)
            true
        }
    }

    private fun onLongClick(href: String) {
        // copy the link to the clipboard
        ClipboardHelper.save(this, text = href)
        // show the snackBar with open action
        Snackbar.make(
            binding.root,
            R.string.copied_to_clipboard,
            Snackbar.LENGTH_LONG
        )
            .setAction(R.string.open_copied) {
                IntentHelper.openLinkFromHref(this, supportFragmentManager, href)
            }
            .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
            .show()
    }

    private fun showLicense() {
        val licenseHtml = assets.open("gpl3.html")
            .bufferedReader()
            .use { it.readText() }
            .parseAsHtml(HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH)

        MaterialAlertDialogBuilder(this)
            .setPositiveButton(getString(R.string.okay)) { _, _ -> }
            .setMessage(licenseHtml)
            .create()
            .show()
    }

    private fun showDeviceInfo() {
        val metrics = Resources.getSystem().displayMetrics

        val text = "Manufacturer: ${Build.MANUFACTURER}\n" +
                "Board: ${Build.BOARD}\n" +
                "Arch: ${Build.SUPPORTED_ABIS[0]}\n" +
                "Android SDK: ${Build.VERSION.SDK_INT}\n" +
                "OS: Android ${Build.VERSION.RELEASE}\n" +
                "Display: ${metrics.widthPixels}x${metrics.heightPixels}\n" +
                "Font scale: ${Resources.getSystem().configuration.fontScale}"

        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.device_info)
            .setMessage(text)
            .setNegativeButton(R.string.copy_tooltip) { _, _ ->
                ClipboardHelper.save(this@AboutActivity, text = text)
            }
            .setPositiveButton(R.string.okay, null)
            .show()
    }

    companion object {
        const val DONATE_URL = "https://github.com/saferill/ZenStream"
        private const val WEBSITE_URL = "https://github.com/saferill/ZenStream#readme"
        const val GITHUB_URL = "https://github.com/saferill/ZenStream"
        private const val PIPED_GITHUB_URL = "https://github.com/TeamPiped/Piped"
        private const val WEBLATE_URL = "https://github.com/saferill/ZenStream"
        private const val LICENSE_URL = "https://www.gnu.org/licenses/gpl-3.0.html"
    }
}

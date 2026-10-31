package com.saferill.zenstream.ui.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.saferill.zenstream.R
import com.saferill.zenstream.constants.IntentData
import com.saferill.zenstream.helpers.PreferenceHelper
import com.saferill.zenstream.ui.preferences.InstanceSettings
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LogoutDialog : DialogFragment() {
    @SuppressLint("SetTextI18n")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val user = PreferenceHelper.getUsername()

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.logout)
            .setMessage(getString(R.string.already_logged_in) + " ($user)")
            .setPositiveButton(R.string.logout) { _, _ ->
                Toast.makeText(context, R.string.loggedout, Toast.LENGTH_SHORT).show()

                setFragmentResult(
                    InstanceSettings.INSTANCE_DIALOG_REQUEST_KEY,
                    bundleOf(IntentData.logoutTask to true)
                )
            }
            .show()
    }
}

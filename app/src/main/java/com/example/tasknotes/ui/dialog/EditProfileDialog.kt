package com.example.tasknotes.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import androidx.fragment.app.FragmentManager
import com.example.tasknotes.R
import com.example.tasknotes.data.local.LocalStorage
import com.example.tasknotes.data.models.ProfileData
import com.example.tasknotes.utils.SingleBlock
import kotlinx.android.synthetic.main.edit_profile_dialog.*
import kotlinx.android.synthetic.main.edit_profile_dialog.view.*

class EditProfileDialog(context: Context, actionName: String, fragmentManager: FragmentManager) :
    AlertDialog(context) {
    private val contentView =
        LayoutInflater.from(context).inflate(R.layout.edit_profile_dialog, null, false)
    private var listener: SingleBlock<ProfileData>? = null
    private var contact: ProfileData? = null
    private var data = ProfileData(LocalStorage.instance.personProfilePicture,LocalStorage.instance.personProfileName,LocalStorage.instance.personProfileEmail)

    init {
        setView(contentView)
        contentView.editDialog.text = "Edit Profile"
        contentView.editProfileDialogButton.text = actionName
        contentView.editProfileDialogButton.setOnClickListener {
            when {
                contentView.userImageLink.text.toString().isEmpty() -> {
                    userImageLinkLayout.error = "Enter profile image link"
                }
                contentView.userNameDialog.text.toString().isEmpty() -> {
                    userEmailLayout.error = "Enter user name"
                    userImageLinkLayout.error = null
                }
                contentView.userEmailDialog.text.toString().isEmpty() -> {
                    userEmailLayout.error = "Enter user email"
                    userEmailLayout.error = null
                    userImageLinkLayout.error = null
                }

            }
            LocalStorage.instance.personProfilePicture = contentView.userImageLink.text.toString()
            LocalStorage.instance.personProfileName = contentView.userNameDialog.text.toString()
            LocalStorage.instance.personProfileEmail = contentView.userEmailDialog.text.toString()
            listener?.invoke(data)
            dismiss()
        }
        contentView.cancelProfileDialogButton.text = "Cancel"
        contentView.cancelProfileDialogButton.setOnClickListener {
            dismiss()
        }

    }

    fun setProfileData(data: ProfileData) = with(contentView) {
        this@EditProfileDialog.contact = data.copy()
        userEmailDialog.setText(LocalStorage.instance.personProfileEmail)
        userNameDialog.setText(LocalStorage.instance.personProfileName)
        userImageLink.setText(LocalStorage.instance.personProfilePicture)
        listener?.invoke(data)
        dismiss()
    }

    fun setEditClickListener(block: SingleBlock<ProfileData>) {
        listener = block
    }

}
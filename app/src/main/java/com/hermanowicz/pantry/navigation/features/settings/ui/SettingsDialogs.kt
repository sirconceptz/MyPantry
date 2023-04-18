package com.hermanowicz.pantry.navigation.features.settings.ui

import android.content.Context
import androidx.compose.runtime.Composable
import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.components.common.dialog.DialogAuthorInfo
import com.hermanowicz.pantry.components.common.dialog.DialogChangeEmail
import com.hermanowicz.pantry.components.common.dialog.DialogClearDatabase
import com.hermanowicz.pantry.navigation.features.settings.state.SettingsState

@Composable
fun ShowSettingsDialogs(
    state: SettingsState,
    context: Context,
    onAuthorDialogDismiss: () -> Unit,
    onConfirmClearDatabase: () -> Unit,
    onChangeEmailForNotifications: (String) -> Unit,
    onChangeEmailDialogDismiss: () -> Unit
) {
    if (state.showAuthorDialog) {
        DialogAuthorInfo(
            onClickFacebook = {
                openInBrowser(
                    context,
                    context.getString(R.string.author_facebook)
                )
            },
            onClickLinkedIn = {
                openInBrowser(
                    context,
                    context.getString(R.string.author_linkedin)
                )
            },
            onClickAppWebsite = {
                openInBrowser(
                    context,
                    context.getString(R.string.author_app_website)
                )
            },
            onDismissRequest = onAuthorDialogDismiss
        )
    }

    if (state.showClearDatabaseDialog) {
        DialogClearDatabase(
            onPositiveRequest = onConfirmClearDatabase,
            onDismissRequest = onAuthorDialogDismiss
        )
    }

    if (state.showChangeNotificationsEmailDialog) {
        DialogChangeEmail(
            emailAddress = state.emailAddressForNotifications,
            onPositiveRequest = onChangeEmailForNotifications,
            onDismissRequest = onChangeEmailDialogDismiss
        )
    }
}
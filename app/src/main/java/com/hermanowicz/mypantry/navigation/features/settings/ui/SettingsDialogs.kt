package com.hermanowicz.mypantry.navigation.features.settings.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hermanowicz.mypantry.R
import com.hermanowicz.mypantry.components.common.dialog.DialogAuthorInfo
import com.hermanowicz.mypantry.components.common.dialog.DialogBackup
import com.hermanowicz.mypantry.navigation.features.settings.state.SettingsState

@Composable
fun ShowSettingsDialogs(
    state: SettingsState,
    context: Context,
    onAuthorDialogDismiss: () -> Unit,
    onConfirmClearDatabase: () -> Unit
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
        DialogBackup(
            label = stringResource(id = R.string.clear_database), statement = stringResource(
                id = R.string.statement_clear_database_warning
            ), onPositiveRequest = onConfirmClearDatabase
        ) {

        }
    }
}
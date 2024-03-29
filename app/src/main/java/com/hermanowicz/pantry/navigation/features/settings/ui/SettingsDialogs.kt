package com.hermanowicz.pantry.navigation.features.settings.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.components.common.dialog.DialogAuthorInfo
import com.hermanowicz.pantry.components.common.dialog.DialogTextfield
import com.hermanowicz.pantry.components.common.dialog.DialogWarning
import com.hermanowicz.pantry.navigation.features.settings.state.SettingsState

@Composable
fun ShowSettingsDialogs(
    state: SettingsState,
    context: Context,
    onAuthorDialogDismiss: () -> Unit,
    onConfirmClearDatabase: () -> Unit,
    onExportDatabaseToCloudDialogDismiss: () -> Unit,
    onConfirmExportDatabaseToCloud: () -> Unit,
    onChangeEmailForNotifications: () -> Unit,
    onChangeEmailDialogDismiss: () -> Unit,
    onConfirmDeleteAccount: () -> Unit,
    onConfirmDialogDismiss: () -> Unit,
    onTempEmailAddressForNotificationsChange: (String) -> Unit
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
        DialogWarning(
            label = stringResource(id = R.string.clear_database),
            warning = stringResource(id = R.string.statement_clear_database_warning),
            onPositiveRequest = onConfirmClearDatabase,
            onDismissRequest = onAuthorDialogDismiss
        )
    }

    if (state.showExportDatabaseToCloudDialog) {
        DialogWarning(
            label = stringResource(id = R.string.export_local_database_to_cloud),
            warning = stringResource(id = R.string.statement_import_local_db_to_cloud_warning),
            onPositiveRequest = onConfirmExportDatabaseToCloud,
            onDismissRequest = onExportDatabaseToCloudDialogDismiss
        )
    }

    if (state.showChangeNotificationsEmailDialog) {
        DialogTextfield(
            label = stringResource(id = R.string.email_address_for_notifications),
            value = state.tempEmailAddressForNotifications,
            onPositiveRequest = onChangeEmailForNotifications,
            onTempChangeValue = onTempEmailAddressForNotificationsChange,
            onDismissRequest = onChangeEmailDialogDismiss
        )
    }

    if (state.showDeleteAccountDialog) {
        DialogWarning(
            label = stringResource(id = R.string.delete_account),
            warning = stringResource(id = R.string.statement_delete_account_warning),
            onPositiveRequest = onConfirmDeleteAccount,
            onDismissRequest = onConfirmDialogDismiss
        )
    }
}

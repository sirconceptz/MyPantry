package com.hermanowicz.mypantry.navigation.features.settings.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hermanowicz.mypantry.BuildConfig
import com.hermanowicz.mypantry.R
import com.hermanowicz.mypantry.components.common.button.ButtonPrimary
import com.hermanowicz.mypantry.components.common.cards.CardWhiteBgWithBorder
import com.hermanowicz.mypantry.components.common.divider.DividerCardInside
import com.hermanowicz.mypantry.components.common.dropdown.DropdownSettings
import com.hermanowicz.mypantry.components.common.slider.SliderSettings
import com.hermanowicz.mypantry.components.common.spacer.SpacerLarge
import com.hermanowicz.mypantry.components.common.spacer.SpacerMedium
import com.hermanowicz.mypantry.components.common.switch.SwitchPrimary
import com.hermanowicz.mypantry.components.common.text.TextLabel
import com.hermanowicz.mypantry.components.common.text.TextSettingsButton
import com.hermanowicz.mypantry.components.common.text.TextSettingsButtonWithValue
import com.hermanowicz.mypantry.components.common.topBarScaffold.TopBarScaffold
import com.hermanowicz.mypantry.ui.theme.LocalSpacing
import com.hermanowicz.mypantry.ui.theme.MyPantryTheme
import com.hermanowicz.mypantry.utils.enums.CameraMode
import com.hermanowicz.mypantry.utils.enums.DatabaseMode
import com.hermanowicz.mypantry.utils.enums.SizePrintedQRCodes


@Composable
fun SettingsScreen(
    openDrawer: () -> Unit,
    onClickUserAccount: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state by viewModel.settingsState.collectAsState()
    val context = LocalContext.current

    TopBarScaffold(
        topBarText = stringResource(id = R.string.settings), openDrawer = openDrawer
    ) {
        ShowSettingsDialogs(
            state = state,
            context = context,
            onAuthorDialogDismiss = { viewModel.showAuthorDialog(false) },
            onConfirmClearDatabase = { viewModel.onConfirmClearDatabase() }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LocalSpacing.current.medium)
        ) {
            item {
                Row(
                    modifier = Modifier.padding(vertical = LocalSpacing.current.large),
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = state.userEmailOrUnlogged,
                        style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center
                    )
                }
            }
            item {
                ButtonPrimary(
                    text = stringResource(id = R.string.user_account), onClick = onClickUserAccount
                )
            }
            item {
                SpacerMedium()
                TextLabel(text = stringResource(id = R.string.main_settings))
                CardWhiteBgWithBorder {
                    DropdownSettings(label = stringResource(id = R.string.database_mode),
                        mapKey = state.databaseMode,
                        itemMap = DatabaseMode.toMap(),
                        onClick = { viewModel.showDatabaseMode(true) },
                        onChange = { viewModel.onChangeDatabaseMode(it) },
                        visibleDropdown = state.showDatabaseModeDropdown,
                        onDismiss = { viewModel.showDatabaseMode(false) })
                    DividerCardInside()
                    TextSettingsButton(label = stringResource(id = R.string.export_local_database_to_cloud),
                        onClick = { /*TODO*/ })
                    DividerCardInside()
                    DropdownSettings(label = stringResource(id = R.string.camera_to_scan_codes),
                        mapKey = state.cameraToScanCodes,
                        itemMap = CameraMode.toMap(),
                        onClick = { viewModel.showCameraMode(true) },
                        onChange = { viewModel.onChangeCameraMode(it) },
                        visibleDropdown = state.showCameraModeDropdown,
                        onDismiss = { viewModel.showCameraMode(false) })
                    DividerCardInside()
                    SwitchPrimary(label = stringResource(id = R.string.scanner_sound),
                        state = state.scannerSound,
                        onStateChange = { viewModel.onChangeScannerSoundMode(!state.scannerSound) })
                    DividerCardInside()
                    DropdownSettings(label = stringResource(id = R.string.qr_code_size),
                        mapKey = state.sizeQrCodes,
                        itemMap = SizePrintedQRCodes.toMap(),
                        onClick = { viewModel.showQrCodeSizeMode(true) },
                        onChange = { viewModel.onChangeQrCodeSizeMode(it) },
                        visibleDropdown = state.showSizeQrCodesDropdown,
                        onDismiss = { viewModel.showQrCodeSizeMode(false) })
                }
            }
            item {
                SpacerMedium()
                TextLabel(text = stringResource(id = R.string.notification_settings))
                CardWhiteBgWithBorder {
                    SliderSettings(
                        label = stringResource(id = R.string.days_to_notify_before_expiration),
                        value = state.daysToNotifyBeforeExpiration,
                        valueRange = 1f..14f,
                        onValueChange = { viewModel.onChangeDaysToNotifyBeforeExpiration(it) },
                        steps = 12
                    )
                    DividerCardInside()
                    TextSettingsButtonWithValue(
                        label = stringResource(id = R.string.email_address_for_notifications),
                        value = state.emailAddressForNotifications,
                        onClick = { /*TODO*/ },
                        enabled = true
                    )
                    DividerCardInside()
                    SwitchPrimary(
                        label = stringResource(id = R.string.push_notifications),
                        state = state.pushNotifications,
                        onStateChange = { viewModel.onChangePushNotifications(!state.pushNotifications) },
                        enabled = true
                    )
                    DividerCardInside()
                    SwitchPrimary(
                        label = stringResource(id = R.string.email_notifications),
                        state = state.emailNotifications,
                        onStateChange = { viewModel.onChangeEmailNotifications(!state.emailNotifications) },
                        enabled = false
                    )
                }
            }
            item {
                SpacerMedium()
                TextLabel(text = stringResource(id = R.string.advanced))
                CardWhiteBgWithBorder {
                    TextSettingsButton(label = stringResource(id = R.string.clear_database),
                        onClick = { viewModel.showClearDatabaseDialog(true) })
                }
            }
            item {
                SpacerLarge()
                ButtonPrimary(text = stringResource(id = R.string.contact_with_author),
                    onClick = { viewModel.showAuthorDialog(true) })
                SpacerMedium()
                AppVersion()
            }
        }
    }
}

fun openInBrowser(context: Context, url: String) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(browserIntent)
}

@Composable
fun AppVersion() {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.app_version) + ": ",
            style = TextStyle(fontSize = 15.sp),
            textAlign = TextAlign.Center
        )
        Text(
            text = BuildConfig.VERSION_NAME,
            style = TextStyle(fontSize = 15.sp),
        )
    }
}

@Preview
@Composable
private fun Preview() {
    MyPantryTheme {
        SettingsScreen({}, {})
    }
}

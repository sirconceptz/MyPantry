package com.hermanowicz.pantry.navigation.features.settings.ui

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig.EmailBuilder
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth
import com.hermanowicz.pantry.BuildConfig
import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.components.common.button.ButtonPrimary
import com.hermanowicz.pantry.components.common.cards.CardWhiteBgWithBorder
import com.hermanowicz.pantry.components.common.divider.DividerCardInside
import com.hermanowicz.pantry.components.common.dropdown.DropdownSettings
import com.hermanowicz.pantry.components.common.slider.SliderSettings
import com.hermanowicz.pantry.components.common.spacer.SpacerLarge
import com.hermanowicz.pantry.components.common.spacer.SpacerMedium
import com.hermanowicz.pantry.components.common.switch.SwitchPrimary
import com.hermanowicz.pantry.components.common.text.TextLabel
import com.hermanowicz.pantry.components.common.text.TextSettingsButton
import com.hermanowicz.pantry.components.common.text.TextSettingsButtonWithValue
import com.hermanowicz.pantry.components.common.topBarScaffold.TopBarScaffold
import com.hermanowicz.pantry.navigation.features.settings.state.SettingsState
import com.hermanowicz.pantry.ui.theme.LocalSpacing
import com.hermanowicz.pantry.ui.theme.MyPantryTheme
import com.hermanowicz.pantry.utils.enums.CameraMode
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import com.hermanowicz.pantry.utils.enums.QrCodeSize

@Composable
fun SettingsScreen(
    openDrawer: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state by viewModel.settingsState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = state.pushNotificationsStateChanged) {
        if (state.pushNotificationsStateChanged) {
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", context.packageName, null)
            )
            context.startActivity(intent)
            viewModel.onChangeNotificationsStateChanged(false)
        }
    }

    val systemUiController = rememberSystemUiController()
    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                viewModel.updateAppSettings()
            }

            Lifecycle.Event.ON_START -> {
                systemUiController.isStatusBarVisible = false
            }

            else -> Unit
        }
    }

    TopBarScaffold(
        topBarText = stringResource(id = R.string.settings), openDrawer = openDrawer
    ) {
        ShowSettingsDialogs(
            state = state,
            context = context,
            onAuthorDialogDismiss = { viewModel.showAuthorDialog(false) },
            onConfirmClearDatabase = { viewModel.onConfirmClearDatabase() },
            onExportDatabaseToCloudDialogDismiss = { viewModel.showExportDatabaseToCloudDialog(false) },
            onConfirmExportDatabaseToCloud = { viewModel.onConfirmExportDatabaseToCloud() },
            onChangeEmailForNotifications = { viewModel.onChangeEmailAddressForNotifications(it) },
            onChangeEmailDialogDismiss = { viewModel.showEmailAddressDialog(false) },
            onConfirmDeleteAccount = { viewModel.onConfirmDeleteAccount() },
            onConfirmDialogDismiss = { viewModel.showDeleteAccountDialog(false) }
        )
        SignInForm(
            state = state,
            hideSignInForm = { viewModel.showSignInOrSignOut(false) },
            showUserEmail = { viewModel.showUserEmail() })

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
                    text = if (!state.isUserLogged) stringResource(id = R.string.sign_in) else stringResource(
                        id = R.string.sign_out
                    ),
                    onClick = { viewModel.showSignInOrSignOut(true) }
                )
                if (state.isUserLogged) {
                    ButtonPrimary(
                        text = stringResource(id = R.string.delete_account),
                        onClick = { viewModel.showDeleteAccountDialog(true) }
                    )
                }
            }
            item {
                SpacerMedium()
                TextLabel(text = stringResource(id = R.string.main_settings))
                CardWhiteBgWithBorder {
                    DropdownSettings(
                        label = stringResource(id = R.string.database_mode),
                        mapKey = state.databaseMode,
                        itemMap = DatabaseMode.toMap(),
                        onClick = { viewModel.showDatabaseMode(true) },
                        onChange = { viewModel.onChangeDatabaseMode(it) },
                        visibleDropdown = state.showDatabaseModeDropdown,
                        onDismiss = { viewModel.showDatabaseMode(false) },
                        enabled = state.databaseModeDropdownEnabled
                    )
                    DividerCardInside()
                    TextSettingsButton(
                        label = stringResource(id = R.string.export_local_database_to_cloud),
                        onClick = { viewModel.showExportDatabaseToCloudDialog(true) },
                        enabled = state.exportDatabaseToCloudEnabled
                    )
                    DividerCardInside()
                    DropdownSettings(label = stringResource(id = R.string.camera_to_scan_codes),
                        mapKey = state.cameraToScanCodes,
                        itemMap = CameraMode.toMap(),
                        onClick = { viewModel.showCameraMode(true) },
                        onChange = { viewModel.onChangeCameraMode(it) },
                        visibleDropdown = state.showCameraModeDropdown,
                        onDismiss = { viewModel.showCameraMode(false) })
                    DividerCardInside()
                    DropdownSettings(label = stringResource(id = R.string.qr_code_size),
                        mapKey = state.sizeQrCodes,
                        itemMap = QrCodeSize.toMap(),
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
                    SwitchPrimary(
                        label = stringResource(id = R.string.push_notifications),
                        state = state.pushNotifications,
                        onStateChange = { viewModel.onChangePushNotificationsEnabled(!state.pushNotifications) },
                        enabled = true
                    )
                    DividerCardInside()
                    SwitchPrimary(
                        label = stringResource(id = R.string.email_notifications),
                        state = state.emailNotifications,
                        onStateChange = { viewModel.onChangeEmailNotifications(!state.emailNotifications) },
                        enabled = state.emailNotificationsCheckboxEnabled
                    )
                    DividerCardInside()
                    TextSettingsButtonWithValue(
                        label = stringResource(id = R.string.email_address_for_notifications),
                        value = state.emailAddressForNotifications,
                        onClick = { viewModel.showEmailAddressDialog(true) },
                        enabled = true
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
private fun AppVersion() {
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

@Composable
fun SignInForm(state: SettingsState, hideSignInForm: () -> Unit, showUserEmail: () -> Unit) {
    val result = remember { mutableStateOf<FirebaseAuthUIAuthenticationResult?>(null) }
    val launcher = rememberLauncherForActivityResult(FirebaseAuthUIActivityResultContract()) {
        result.value = it
    }

    val providers = listOf(
        EmailBuilder().build()
    )

    result.let {
        val response = it.value?.resultCode
        if (response == RESULT_OK)
            showUserEmail()
    }

    if (state.showSignInForm) {
        if (!state.isUserLogged) {
            val signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()
            launcher.launch(signInIntent)
            hideSignInForm()
        } else {
            FirebaseAuth.getInstance().signOut()
            showUserEmail()
            hideSignInForm()
        }
    }
}

@Composable
fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}

@Preview
@Composable
private fun Preview() {
    MyPantryTheme {
        SettingsScreen({})
    }
}

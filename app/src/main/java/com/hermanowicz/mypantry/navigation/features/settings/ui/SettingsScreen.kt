package com.hermanowicz.mypantry.navigation.features.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import com.hermanowicz.mypantry.utils.enums.CameraValueType
import com.hermanowicz.mypantry.utils.enums.DatabaseValueType
import com.hermanowicz.mypantry.utils.enums.QRCodeSizeValueType

@Composable
fun SettingsScreen(
    openDrawer: () -> Unit,
    onClickUserAccount: () -> Unit,
    onClickContactWithAuthor: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    TopBarScaffold(
        topBarText = stringResource(id = R.string.settings),
        openDrawer = openDrawer
    ) {
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
                        text = "mateusz.hermanowicz@icloud.com",
                        style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center
                    )
                }
            }
            item {
                ButtonPrimary(
                    text = stringResource(id = R.string.user_account),
                    onClick = onClickUserAccount
                )
            }
            item {
                SpacerMedium()
                TextLabel(text = stringResource(id = R.string.main_settings))
                CardWhiteBgWithBorder {
                    DropdownSettings(
                        label = stringResource(id = R.string.database_mode),
                        mapKey = "LOCAL",
                        itemMap = DatabaseValueType.toMap(),
                        onClick = { /*TODO*/ },
                        onChange = { /*TODO*/ },
                        visibleDropdown = false,
                        onDismiss = { /*TODO*/ }
                    )
                    DividerCardInside()
                    TextSettingsButton(
                        label = stringResource(id = R.string.export_local_database_to_cloud),
                        onClick = { /*TODO*/ }
                    )
                    DividerCardInside()
                    DropdownSettings(
                        label = stringResource(id = R.string.camera_to_scan_codes),
                        mapKey = "REAR",
                        itemMap = CameraValueType.toMap(),
                        onClick = { /*TODO*/ },
                        onChange = { /*TODO*/ },
                        visibleDropdown = false,
                        onDismiss = { /*TODO*/ }
                    )
                    DividerCardInside()
                    SwitchPrimary(
                        label = stringResource(id = R.string.scanner_sound),
                        state = false,
                        onStateChange = { /*TODO*/ }
                    )
                    DividerCardInside()
                    DropdownSettings(
                        label = stringResource(id = R.string.qr_code_size),
                        mapKey = "BIG",
                        itemMap = QRCodeSizeValueType.toMap(),
                        onClick = { /*TODO*/ },
                        onChange = { /*TODO*/ },
                        visibleDropdown = false,
                        onDismiss = { /*TODO*/ }
                    )
                }
            }
            item {
                SpacerMedium()
                TextLabel(text = stringResource(id = R.string.notification_settings))
                CardWhiteBgWithBorder {
                    SliderSettings(
                        label = stringResource(id = R.string.days_to_notify_before_expiration),
                        value = 3f,
                        valueRange = 0f..14f,
                        onValueChange = {},
                        steps = 15
                    )
                    DividerCardInside()
                    TextSettingsButtonWithValue(
                        label = stringResource(id = R.string.email_address_for_notifications),
                        value = "None",
                        onClick = { /*TODO*/ },
                        enabled = true
                    )
                    DividerCardInside()
                    SwitchPrimary(
                        label = stringResource(id = R.string.push_notifications),
                        state = true,
                        onStateChange = { /*TODO*/ },
                        enabled = true
                    )
                    DividerCardInside()
                    SwitchPrimary(
                        label = stringResource(id = R.string.email_notifications),
                        state = false,
                        onStateChange = { /*TODO*/ },
                        enabled = false
                    )
                }
            }
            item {
                SpacerMedium()
                TextLabel(text = stringResource(id = R.string.backup_settings))
                CardWhiteBgWithBorder {
                    TextSettingsButton(
                        label = stringResource(id = R.string.import_product_database),
                        onClick = { /*TODO*/ }
                    )
                    DividerCardInside()
                    TextSettingsButton(
                        label = stringResource(id = R.string.export_product_database),
                        onClick = { /*TODO*/ }
                    )
                    DividerCardInside()
                    TextSettingsButton(
                        label = stringResource(id = R.string.clean_product_database),
                        onClick = { /*TODO*/ }
                    )
                    DividerCardInside()
                    TextSettingsButton(
                        label = stringResource(id = R.string.import_category_database),
                        onClick = { /*TODO*/ }
                    )
                    DividerCardInside()
                    TextSettingsButton(
                        label = stringResource(id = R.string.export_category_database),
                        onClick = { /*TODO*/ }
                    )
                    DividerCardInside()
                    TextSettingsButton(
                        label = stringResource(id = R.string.clean_category_database),
                        onClick = { /*TODO*/ }
                    )
                    DividerCardInside()
                    TextSettingsButton(
                        label = stringResource(id = R.string.import_storage_locations_database),
                        onClick = { /*TODO*/ }
                    )
                    DividerCardInside()
                    TextSettingsButton(
                        label = stringResource(id = R.string.export_storage_locations_database),
                        onClick = { /*TODO*/ }
                    )
                    DividerCardInside()
                    TextSettingsButton(
                        label = stringResource(id = R.string.clean_storage_locations_database),
                        onClick = { /*TODO*/ }
                    )
                }
            }
            item {
                SpacerLarge()
                ButtonPrimary(
                    text = stringResource(id = R.string.contact_with_author),
                    onClick = onClickContactWithAuthor
                )
                SpacerMedium()
                AppVersion()
            }
        }
    }
}

@Composable
fun AppVersion() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
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
        SettingsScreen({}, {}, {})
    }
}

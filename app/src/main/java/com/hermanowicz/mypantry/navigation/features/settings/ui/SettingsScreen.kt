package com.hermanowicz.mypantry.navigation.features.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hermanowicz.mypantry.R

@Composable
fun SettingsScreen() {
    Column() {
        Text(text = stringResource(id = R.string.settings))
    }
}

package com.hermanowicz.pantry.components.common.spacer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hermanowicz.pantry.ui.theme.LocalSpacing

@Composable
fun SpacerSmall() {
    Spacer(modifier = Modifier.height(LocalSpacing.current.small))
}

@Composable
fun SpacerMedium() {
    Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
}

@Composable
fun SpacerLarge() {
    Spacer(modifier = Modifier.height(LocalSpacing.current.large))
}

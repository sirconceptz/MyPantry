package com.hermanowicz.mypantry.navigation.features.ownCategories.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hermanowicz.mypantry.R
import com.hermanowicz.mypantry.components.common.topBarScaffold.TopBarScaffold

@Composable
fun OwnCategoriesScreen(openDrawer: () -> Unit) {
    TopBarScaffold(
        topBarText = stringResource(id = R.string.own_categories),
        openDrawer = openDrawer
    ) {
    }
}

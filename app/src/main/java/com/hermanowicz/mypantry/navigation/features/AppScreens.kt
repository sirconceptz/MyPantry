package com.hermanowicz.mypantry.navigation.features

sealed class AppScreens(
    val route: String
) {
    object App : AppScreens("my_pantry")
    object NewProduct : AppScreens("new_product")
    object FilterProduct : AppScreens("filter_product")
    object OwnCategories : AppScreens("own_categories")
    object ScanProduct : AppScreens("scan_product")
    object Settings : AppScreens("settings")
    object StorageLocations : AppScreens("storage_locations")
    object EditProduct : AppScreens("edit_product")
    object SplashScreen : AppScreens("splash_screen")
}

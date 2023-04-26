package com.hermanowicz.pantry.navigation.features

sealed class AppScreens(
    val route: String
) {
    object MyPantry : AppScreens("my_pantry") // home screen
    object NewProduct : AppScreens("new_product")
    object PrintQRCodes : AppScreens("print_qr_codes")
    object FilterProduct : AppScreens("filter_product")
    object ProductDetails : AppScreens("filter_product")
    object OwnCategories : AppScreens("own_categories")
    object ScanProduct : AppScreens("scan_product")
    object Settings : AppScreens("settings")
    object StorageLocations : AppScreens("storage_locations")
    object EditProduct : AppScreens("edit_product")
    object SplashScreen : AppScreens("splash_screen")
}

package com.hermanowicz.pantry.navigation.features

sealed class AppScreens(
    val route: String
) {
    object AddPhoto : AppScreens("add_photo")
    object EditProduct : AppScreens("edit_product")
    object FilterProduct : AppScreens("filter_product")
    object MyPantry : AppScreens("my_pantry")               // home screen
    object NewProduct : AppScreens("new_product")
    object OwnCategories : AppScreens("own_categories")
    object PrintQRCodes : AppScreens("print_qr_codes")
    object ProductDetails : AppScreens("product_details")
    object ScanProduct : AppScreens("scan_product")
    object Settings : AppScreens("settings")
    object SplashScreen : AppScreens("splash_screen")
    object StorageLocations : AppScreens("storage_locations")
}

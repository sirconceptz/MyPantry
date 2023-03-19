package com.hermanowicz.mypantry.navigation.features

sealed class MyPantryScreens(
    val route: String
) {
    object MyPantry : MyPantryScreens("my_pantry")
    object FilterProduct : MyPantryScreens("filter_product")
    object OwnCategories : MyPantryScreens("own_categories")
    object ScanProduct : MyPantryScreens("scan_product")
    object Settings : MyPantryScreens("settings")
    object StorageLocations : MyPantryScreens("storage_locations")
    object EditProduct : MyPantryScreens("edit_product")
    object SplashScreen : MyPantryScreens("splash_screen")
}
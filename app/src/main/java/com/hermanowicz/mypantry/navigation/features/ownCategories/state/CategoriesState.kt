package com.hermanowicz.mypantry.navigation.features.ownCategories.state

data class CategoriesState(
    var name: String = "",
    var description: String = "",
    var showDialogAddNewCategory: Boolean = false,
    var isEditMode: Boolean = false
)

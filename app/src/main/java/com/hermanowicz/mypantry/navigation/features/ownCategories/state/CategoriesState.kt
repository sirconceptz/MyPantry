package com.hermanowicz.mypantry.navigation.features.ownCategories.state

import com.hermanowicz.mypantry.data.model.Category

data class CategoriesState(
    var name: String = "",
    var description: String = "",
    var showDialogAddNewCategory: Boolean = false,
    var showDialogEditCategory: Boolean = false,
    var editedCategory: Category = Category(),
    var isEditMode: Boolean = false
)

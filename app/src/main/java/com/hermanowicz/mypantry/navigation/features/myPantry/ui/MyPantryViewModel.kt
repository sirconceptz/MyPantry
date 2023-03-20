package com.hermanowicz.mypantry.navigation.features.myPantry.ui

import androidx.lifecycle.ViewModel
import com.hermanowicz.mypantry.data.local.model.ProductEntity
import com.hermanowicz.mypantry.data.model.GroupProduct
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MyPantryViewModel @Inject constructor() : ViewModel() {
    private val _products = MutableStateFlow<List<GroupProduct>>(
        listOf(
            GroupProduct(ProductEntity(id = 0, name = "Test 1"), 5),
            GroupProduct(ProductEntity(id = 1, name = "Test 2"), 3),
            GroupProduct(ProductEntity(id = 2, name = "Test 3"), 1)
        )
    )
    val products: StateFlow<List<GroupProduct>> = _products
}

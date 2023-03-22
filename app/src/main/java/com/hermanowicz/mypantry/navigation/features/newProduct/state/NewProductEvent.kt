package com.hermanowicz.mypantry.navigation.features.newProduct.state

sealed interface NewProductEvent {
    data class ChangeName(val name: String) : NewProductEvent
}

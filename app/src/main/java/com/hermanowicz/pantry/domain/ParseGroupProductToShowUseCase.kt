package com.hermanowicz.pantry.domain

import com.hermanowicz.pantry.data.model.GroupProduct
import com.hermanowicz.pantry.utils.category.MainCategories
import javax.inject.Inject

class ParseGroupProductToShowUseCase @Inject constructor(
    private val getOwnCategoriesUseCase: GetOwnCategoriesUseCase,
    private val getMainCategoriesUseCase: GetMainCategoriesUseCase,
    private val getDetailCategoriesUseCase: GetDetailsCategoriesUseCase,
) : (GroupProduct) -> GroupProduct {
    override fun invoke(groupProduct: GroupProduct): GroupProduct {
        var parsedProduct = groupProduct.product
        val mainCategory = try {
            MainCategories.valueOf(groupProduct.product.mainCategory).toString()
        } catch (e: IllegalArgumentException) {
            groupProduct.product.mainCategory
        }
        parsedProduct = parsedProduct.copy(
            mainCategory = mainCategory
        )
        return groupProduct.copy(product = parsedProduct)
    }
}
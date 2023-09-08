package com.hermanowicz.pantry.domain.product

import android.content.Context
import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.domain.category.GetDetailCategoryUseCase
import com.hermanowicz.pantry.domain.product.utils.GetMainCategoryString.Companion.getMainCategoryFromResourcesFromProduct
import com.hermanowicz.pantry.domain.product.utils.GetTasteString.Companion.getTasteStringFromResourcesForProduct
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ParseDeprecatedDatabaseProductsUseCase @Inject constructor(
    @ApplicationContext val context: Context,
    private val getDetailCategoryUseCase: GetDetailCategoryUseCase
) : (List<Product>, List<Category>) -> List<Product> {
    override fun invoke(products: List<Product>, ownCategories: List<Category>): List<Product> {
        val parsedProducts: MutableList<Product> = mutableListOf()
        products.forEach { product ->
            val taste = getTasteStringFromResourcesForProduct(context, product)
            val mainCategory = getMainCategoryFromResourcesFromProduct(context, product)
            val detailCategory = getDetailCategoryUseCase(ownCategories, product)
            parsedProducts.add(
                product.copy(
                    taste = taste,
                    mainCategory = mainCategory,
                    detailCategory = detailCategory
                )
            )
        }
        return parsedProducts.toList()
    }
}

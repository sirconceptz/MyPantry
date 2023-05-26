package com.hermanowicz.pantry.domain.product

import android.content.Context
import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.domain.category.GetDetailCategoryUseCase
import com.hermanowicz.pantry.utils.category.MainCategories
import com.hermanowicz.pantry.utils.enums.Taste
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ParseDeprecatedDatabaseProductsUseCase @Inject constructor(
    @ApplicationContext val context: Context,
    private val getDetailCategoryUseCase: GetDetailCategoryUseCase
) : (List<Product>, List<Category>) -> List<Product> {
    override fun invoke(products: List<Product>, ownCategories: List<Category>): List<Product> {
        val parsedProducts: MutableList<Product> = mutableListOf()
        products.forEach { product ->
            val taste = getTaste(product)
            val mainCategory = getMainCategory(product)
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

    private fun getTaste(product: Product): String {
        return try {
            context.getString(enumValueOf<Taste>(product.taste).nameResId)
        } catch (e: Exception) {
            product.taste
        }
    }

    private fun getMainCategory(product: Product): String {
        return try {
            context.getString(enumValueOf<MainCategories>(product.mainCategory).nameResId)
        } catch (e: Exception) {
            product.mainCategory
        }
    }
}

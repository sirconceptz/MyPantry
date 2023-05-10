package com.hermanowicz.pantry.domain

import android.content.Context
import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.utils.enums.Taste
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ParseDeprecatedDatabaseProductsUseCase @Inject constructor(
    @ApplicationContext val context: Context
) : (List<Product>) -> List<Product> {
    override fun invoke(products: List<Product>): List<Product> {
        val parsedProducts: MutableList<Product> = mutableListOf()
        products.forEach { product ->
            val taste = try {
                context.getString(enumValueOf<Taste>(product.taste).nameResId)
            } catch (e: Exception) {
                product.taste
            }
            parsedProducts.add(product.copy(taste = taste))
        }
        return parsedProducts.toList()
    }
}
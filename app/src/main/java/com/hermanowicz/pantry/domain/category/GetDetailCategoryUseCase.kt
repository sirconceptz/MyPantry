package com.hermanowicz.pantry.domain.category

import android.content.Context
import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.utils.category.detailCategory.ChemicalProductsCategoryTypes
import com.hermanowicz.pantry.utils.category.detailCategory.FruitsCategoryTypes
import com.hermanowicz.pantry.utils.category.detailCategory.HerbsCategoryTypes
import com.hermanowicz.pantry.utils.category.detailCategory.LiqueursCategoryTypes
import com.hermanowicz.pantry.utils.category.detailCategory.MushroomsCategoryTypes
import com.hermanowicz.pantry.utils.category.detailCategory.OtherCategoryTypes
import com.hermanowicz.pantry.utils.category.detailCategory.ReadyMealsCategoryTypes
import com.hermanowicz.pantry.utils.category.detailCategory.StoreProductsCategoryTypes
import com.hermanowicz.pantry.utils.category.detailCategory.VegetablesCategoryTypes
import com.hermanowicz.pantry.utils.category.detailCategory.VinegarsCategoryTypes
import com.hermanowicz.pantry.utils.category.detailCategory.WinesCategoryTypes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetDetailCategoryUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) : (List<Category>, Product) -> String {
    override fun invoke(ownCategories: List<Category>, product: Product): String {
        if (product.mainCategory.isEmpty() && product.detailCategory.isEmpty())
            return product.detailCategory
        else {
            ownCategories.forEach { category ->
                if (category.name == product.detailCategory)
                    return category.name
            }

            enumValues<StoreProductsCategoryTypes>().forEach { category ->
                if (category.name == product.detailCategory) {
                    return context.getString(category.nameResId)
                }
            }

            enumValues<ReadyMealsCategoryTypes>().forEach { category ->
                if (category.name == product.detailCategory) {
                    return context.getString(category.nameResId)
                }
            }

            enumValues<VegetablesCategoryTypes>().forEach { category ->
                if (category.name == product.detailCategory) {
                    return context.getString(category.nameResId)
                }
            }

            enumValues<FruitsCategoryTypes>().forEach { category ->
                if (category.name == product.detailCategory) {
                    return context.getString(category.nameResId)
                }
            }

            enumValues<HerbsCategoryTypes>().forEach { category ->
                if (category.name == product.detailCategory) {
                    return context.getString(category.nameResId)
                }
            }

            enumValues<LiqueursCategoryTypes>().forEach { category ->
                if (category.name == product.detailCategory) {
                    return context.getString(category.nameResId)
                }
            }

            enumValues<WinesCategoryTypes>().forEach { category ->
                if (category.name == product.detailCategory) {
                    return context.getString(category.nameResId)
                }
            }

            enumValues<MushroomsCategoryTypes>().forEach { category ->
                if (category.name == product.detailCategory) {
                    return context.getString(category.nameResId)
                }
            }

            enumValues<VinegarsCategoryTypes>().forEach { category ->
                if (category.name == product.detailCategory) {
                    return context.getString(category.nameResId)
                }
            }

            enumValues<ChemicalProductsCategoryTypes>().forEach { category ->
                if (category.name == product.detailCategory) {
                    return context.getString(category.nameResId)
                }
            }

            enumValues<OtherCategoryTypes>().forEach { category ->
                if (category.name == product.detailCategory) {
                    return context.getString(category.nameResId)
                }
            }
            return product.detailCategory
        }
    }
}

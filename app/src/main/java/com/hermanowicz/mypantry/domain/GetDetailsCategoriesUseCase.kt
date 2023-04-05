package com.hermanowicz.mypantry.domain

import android.content.Context
import com.hermanowicz.mypantry.data.model.Category
import com.hermanowicz.mypantry.utils.category.MainCategoriesTypes
import com.hermanowicz.mypantry.utils.category.detailCategory.ChemicalProductsCategoryTypes
import com.hermanowicz.mypantry.utils.category.detailCategory.ChooseCategoryTypes
import com.hermanowicz.mypantry.utils.category.detailCategory.FruitsCategoryTypes
import com.hermanowicz.mypantry.utils.category.detailCategory.HerbsCategoryTypes
import com.hermanowicz.mypantry.utils.category.detailCategory.LiqueursCategoryTypes
import com.hermanowicz.mypantry.utils.category.detailCategory.MushroomsCategoryTypes
import com.hermanowicz.mypantry.utils.category.detailCategory.OtherCategoryTypes
import com.hermanowicz.mypantry.utils.category.detailCategory.ReadyMealsCategoryTypes
import com.hermanowicz.mypantry.utils.category.detailCategory.StoreProductsCategoryTypes
import com.hermanowicz.mypantry.utils.category.detailCategory.VegetablesCategoryTypes
import com.hermanowicz.mypantry.utils.category.detailCategory.VinegarsCategoryTypes
import com.hermanowicz.mypantry.utils.category.detailCategory.WinesCategoryTypes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetDetailsCategoriesUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) : (List<Category>, String) -> Map<String, String> {
    override fun invoke(categories: List<Category>, mainCategory: String): Map<String, String> {
        val map: MutableMap<String, String> = mutableMapOf()
        when (enumValueOf<MainCategoriesTypes>(mainCategory)) {
            MainCategoriesTypes.CHOOSE -> {
                enumValues<ChooseCategoryTypes>().forEach { category ->
                    map[category.name] = context.getString(category.nameResId)
                }
            }
            MainCategoriesTypes.OWN_CATEGORIES -> {
                map["CHOOSE"] = context.getString(MainCategoriesTypes.CHOOSE.nameResId)
                categories.forEach { category ->
                    map[category.name] = category.name
                }
            }
            MainCategoriesTypes.STORE_PRODUCTS -> {
                enumValues<StoreProductsCategoryTypes>().forEach { category ->
                    map[category.name] = context.getString(category.nameResId)
                }
            }
            MainCategoriesTypes.READY_MEALS -> {
                enumValues<ReadyMealsCategoryTypes>().forEach { category ->
                    map[category.name] = context.getString(category.nameResId)
                }
            }
            MainCategoriesTypes.VEGETABLES -> {
                enumValues<VegetablesCategoryTypes>().forEach { category ->
                    map[category.name] = context.getString(category.nameResId)
                }
            }
            MainCategoriesTypes.FRUITS -> {
                enumValues<FruitsCategoryTypes>().forEach { category ->
                    map[category.name] = context.getString(category.nameResId)
                }
            }
            MainCategoriesTypes.HERBS -> {
                enumValues<HerbsCategoryTypes>().forEach { category ->
                    map[category.name] = context.getString(category.nameResId)
                }
            }
            MainCategoriesTypes.LIQUORS -> {
                enumValues<LiqueursCategoryTypes>().forEach { category ->
                    map[category.name] = context.getString(category.nameResId)
                }
            }
            MainCategoriesTypes.WINES -> {
                enumValues<WinesCategoryTypes>().forEach { category ->
                    map[category.name] = context.getString(category.nameResId)
                }
            }
            MainCategoriesTypes.MUSHROOMS -> {
                enumValues<MushroomsCategoryTypes>().forEach { category ->
                    map[category.name] = context.getString(category.nameResId)
                }
            }
            MainCategoriesTypes.VINEGARS -> {
                enumValues<VinegarsCategoryTypes>().forEach { category ->
                    map[category.name] = context.getString(category.nameResId)
                }
            }
            MainCategoriesTypes.CHEMICAL_PRODUCTS -> {
                enumValues<ChemicalProductsCategoryTypes>().forEach { category ->
                    map[category.name] = context.getString(category.nameResId)
                }
            }
            MainCategoriesTypes.OTHER -> {
                enumValues<OtherCategoryTypes>().forEach { category ->
                    map[category.name] = context.getString(category.nameResId)
                }
            }
        }
        return map
    }
}

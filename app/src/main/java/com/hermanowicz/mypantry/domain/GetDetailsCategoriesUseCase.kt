package com.hermanowicz.mypantry.domain

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
import javax.inject.Inject

class GetDetailsCategoriesUseCase @Inject constructor() : (String) -> Map<String, Int> {
    override fun invoke(mainCategory: String): Map<String, Int> {
        val map: MutableMap<String, Int> = mutableMapOf()
        when (enumValueOf<MainCategoriesTypes>(mainCategory)) {
            MainCategoriesTypes.CHOOSE -> {
                enumValues<ChooseCategoryTypes>().forEach { category ->
                    map[category.name] = category.nameResId
                }
            }
            MainCategoriesTypes.OWN_CATEGORIES -> {
                enumValues<ChooseCategoryTypes>().forEach { category ->
                    map[category.name] = category.nameResId
                }
            }
            MainCategoriesTypes.STORE_PRODUCTS -> {
                enumValues<StoreProductsCategoryTypes>().forEach { category ->
                    map[category.name] = category.nameResId
                }
            }
            MainCategoriesTypes.READY_MEALS -> {
                enumValues<ReadyMealsCategoryTypes>().forEach { category ->
                    map[category.name] = category.nameResId
                }
            }
            MainCategoriesTypes.VEGETABLES -> {
                enumValues<VegetablesCategoryTypes>().forEach { category ->
                    map[category.name] = category.nameResId
                }
            }
            MainCategoriesTypes.FRUITS -> {
                enumValues<FruitsCategoryTypes>().forEach { category ->
                    map[category.name] = category.nameResId
                }
            }
            MainCategoriesTypes.HERBS -> {
                enumValues<HerbsCategoryTypes>().forEach { category ->
                    map[category.name] = category.nameResId
                }
            }
            MainCategoriesTypes.LIQUORS -> {
                enumValues<LiqueursCategoryTypes>().forEach { category ->
                    map[category.name] = category.nameResId
                }
            }
            MainCategoriesTypes.WINES -> {
                enumValues<WinesCategoryTypes>().forEach { category ->
                    map[category.name] = category.nameResId
                }
            }
            MainCategoriesTypes.MUSHROOMS -> {
                enumValues<MushroomsCategoryTypes>().forEach { category ->
                    map[category.name] = category.nameResId
                }
            }
            MainCategoriesTypes.VINEGARS -> {
                enumValues<VinegarsCategoryTypes>().forEach { category ->
                    map[category.name] = category.nameResId
                }
            }
            MainCategoriesTypes.CHEMICAL_PRODUCTS -> {
                enumValues<ChemicalProductsCategoryTypes>().forEach { category ->
                    map[category.name] = category.nameResId
                }
            }
            MainCategoriesTypes.OTHER -> {
                enumValues<OtherCategoryTypes>().forEach { category ->
                    map[category.name] = category.nameResId
                }
            }
        }
        return map
    }
}

package com.hermanowicz.pantry.domain

import android.content.Context
import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.utils.category.MainCategories
import com.hermanowicz.pantry.utils.category.detailCategory.ChemicalProductsCategoryTypes
import com.hermanowicz.pantry.utils.category.detailCategory.ChooseCategoryTypes
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

class GetDetailsCategoriesUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) : (List<Category>, String) -> Map<String, String> {
    override fun invoke(categories: List<Category>, mainCategory: String): Map<String, String> {
        val map: MutableMap<String, String> = mutableMapOf()
        if (mainCategory.isEmpty())
            enumValues<ChooseCategoryTypes>().forEach { category ->
                map[category.name] = context.getString(category.nameResId)
            }
        else {
            when (enumValueOf<MainCategories>(mainCategory)) {
                MainCategories.CHOOSE -> {
                    enumValues<ChooseCategoryTypes>().forEach { category ->
                        map[category.name] = context.getString(category.nameResId)
                    }
                }

                MainCategories.OWN_CATEGORIES -> {
                    map["CHOOSE"] = context.getString(MainCategories.CHOOSE.nameResId)
                    categories.forEach { category ->
                        map[category.name] = category.name
                    }
                }

                MainCategories.STORE_PRODUCTS -> {
                    enumValues<StoreProductsCategoryTypes>().forEach { category ->
                        map[category.name] = context.getString(category.nameResId)
                    }
                }

                MainCategories.READY_MEALS -> {
                    enumValues<ReadyMealsCategoryTypes>().forEach { category ->
                        map[category.name] = context.getString(category.nameResId)
                    }
                }

                MainCategories.VEGETABLES -> {
                    enumValues<VegetablesCategoryTypes>().forEach { category ->
                        map[category.name] = context.getString(category.nameResId)
                    }
                }

                MainCategories.FRUITS -> {
                    enumValues<FruitsCategoryTypes>().forEach { category ->
                        map[category.name] = context.getString(category.nameResId)
                    }
                }

                MainCategories.HERBS -> {
                    enumValues<HerbsCategoryTypes>().forEach { category ->
                        map[category.name] = context.getString(category.nameResId)
                    }
                }

                MainCategories.LIQUORS -> {
                    enumValues<LiqueursCategoryTypes>().forEach { category ->
                        map[category.name] = context.getString(category.nameResId)
                    }
                }

                MainCategories.WINES -> {
                    enumValues<WinesCategoryTypes>().forEach { category ->
                        map[category.name] = context.getString(category.nameResId)
                    }
                }

                MainCategories.MUSHROOMS -> {
                    enumValues<MushroomsCategoryTypes>().forEach { category ->
                        map[category.name] = context.getString(category.nameResId)
                    }
                }

                MainCategories.VINEGARS -> {
                    enumValues<VinegarsCategoryTypes>().forEach { category ->
                        map[category.name] = context.getString(category.nameResId)
                    }
                }

                MainCategories.CHEMICAL_PRODUCTS -> {
                    enumValues<ChemicalProductsCategoryTypes>().forEach { category ->
                        map[category.name] = context.getString(category.nameResId)
                    }
                }

                MainCategories.OTHER -> {
                    enumValues<OtherCategoryTypes>().forEach { category ->
                        map[category.name] = context.getString(category.nameResId)
                    }
                }
            }
        }
        return map
    }
}

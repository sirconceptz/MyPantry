package com.hermanowicz.pantry.domain.category

import android.content.Context
import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.utils.enums.category.MainCategories
import com.hermanowicz.pantry.utils.enums.category.detailCategory.ChemicalProductsCategoryTypes
import com.hermanowicz.pantry.utils.enums.category.detailCategory.ChooseCategoryTypes
import com.hermanowicz.pantry.utils.enums.category.detailCategory.FruitsCategoryTypes
import com.hermanowicz.pantry.utils.enums.category.detailCategory.HerbsCategoryTypes
import com.hermanowicz.pantry.utils.enums.category.detailCategory.LiqueursCategoryTypes
import com.hermanowicz.pantry.utils.enums.category.detailCategory.MushroomsCategoryTypes
import com.hermanowicz.pantry.utils.enums.category.detailCategory.OtherCategoryTypes
import com.hermanowicz.pantry.utils.enums.category.detailCategory.ReadyMealsCategoryTypes
import com.hermanowicz.pantry.utils.enums.category.detailCategory.StoreProductsCategoryTypes
import com.hermanowicz.pantry.utils.enums.category.detailCategory.VegetablesCategoryTypes
import com.hermanowicz.pantry.utils.enums.category.detailCategory.VinegarsCategoryTypes
import com.hermanowicz.pantry.utils.enums.category.detailCategory.WinesCategoryTypes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetDetailCategoriesUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) : (List<Category>, String) -> Map<String, String> {
    override fun invoke(ownCategories: List<Category>, mainCategory: String): Map<String, String> {
        val map: MutableMap<String, String> = mutableMapOf()
        if (mainCategory.isEmpty()) {
            enumValues<ChooseCategoryTypes>().forEach { category ->
                map[category.name] = context.getString(category.nameResId)
            }
        } else {
            when (enumValueOf<MainCategories>(mainCategory)) {
                MainCategories.CHOOSE -> {
                    enumValues<ChooseCategoryTypes>().forEach { category ->
                        map[category.name] = context.getString(category.nameResId)
                    }
                }

                MainCategories.OWN_CATEGORIES -> {
                    map["CHOOSE"] = context.getString(MainCategories.CHOOSE.nameResId)
                    ownCategories.forEach { category ->
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

package com.hermanowicz.pantry.domain.product

import com.hermanowicz.pantry.data.model.FilterProduct
import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.domain.product.utils.IsDetailCategoryValid.Companion.isDetailCategoryValid
import com.hermanowicz.pantry.domain.product.utils.IsExpirationDateInRange.Companion.isExpirationDateInRange
import com.hermanowicz.pantry.domain.product.utils.IsMainCategoryValid.Companion.isMainCategoryValid
import com.hermanowicz.pantry.domain.product.utils.IsProductAttributesValid.Companion.isProductAttributesValid
import com.hermanowicz.pantry.domain.product.utils.IsProductDateInRange.Companion.isProductionDateInRange
import com.hermanowicz.pantry.domain.product.utils.IsTasteValid.Companion.isTasteValid
import javax.inject.Inject

class GetFilteredProductListUseCase @Inject constructor() :
    (List<Product>, FilterProduct) -> List<Product> {
    override fun invoke(
        products: List<Product>,
        filterProduct: FilterProduct
    ): List<Product> {
        val mutableProducts: MutableList<Product> = mutableListOf()
        val volumeMin = filterProduct.volumeMin ?: 0
        val volumeMax = filterProduct.volumeMax ?: Integer.MAX_VALUE
        val weightMin = filterProduct.weightMin ?: 0
        val weightMax = filterProduct.weightMax ?: Integer.MAX_VALUE
        products.forEach { product ->
            if (product.name.contains(filterProduct.name) && isMainCategoryValid(
                    product.mainCategory,
                    filterProduct.mainCategory
                ) && isDetailCategoryValid(
                        product.detailCategory,
                        filterProduct.detailCategory
                    ) && product.storageLocation.contains(filterProduct.storageLocation) && product.composition.contains(
                        filterProduct.composition
                    ) && product.healingProperties.contains(filterProduct.healingProperties) && product.dosage.contains(
                        filterProduct.dosage
                    ) && product.volume in volumeMin..volumeMax && product.weight in weightMin..weightMax && isProductAttributesValid(
                        product.isBio,
                        product.isVege,
                        product.hasSugar,
                        product.hasSalt,
                        filterProduct.isBio,
                        filterProduct.isVege,
                        filterProduct.hasSugar,
                        filterProduct.hasSalt
                    ) && isTasteValid(product, filterProduct) &&
                isExpirationDateInRange(
                        product.expirationDate,
                        filterProduct.expirationDateMin,
                        filterProduct.expirationDateMax
                    ) && isProductionDateInRange(
                        product.productionDate,
                        filterProduct.productionDateMin,
                        filterProduct.productionDateMax
                    )
            ) {
                mutableProducts.add(product)
            }
        }
        return mutableProducts.toList()
    }
}

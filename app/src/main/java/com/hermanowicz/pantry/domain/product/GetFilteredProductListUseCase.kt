package com.hermanowicz.pantry.domain.product

import android.annotation.SuppressLint
import com.hermanowicz.pantry.data.model.FilterProduct
import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.utils.category.MainCategories
import com.hermanowicz.pantry.utils.category.detailCategory.ChooseCategoryTypes
import com.hermanowicz.pantry.utils.enums.ProductAttributesValueType
import com.hermanowicz.pantry.utils.enums.Taste
import java.text.SimpleDateFormat
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
                    ) && tasteIsValid(product, filterProduct) &&
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

    private fun tasteIsValid(product: Product, filterProduct: FilterProduct): Boolean {
        return (
            (filterProduct.sweet && product.taste == Taste.SWEET.name) ||
                (filterProduct.sour && product.taste == Taste.SOUR.name) ||
                (filterProduct.sweetAndSour && product.taste == Taste.SWEET_AND_SOUR.name) ||
                (filterProduct.salty && product.taste == Taste.SALTY.name) ||
                (filterProduct.spicy && product.taste == Taste.SPICY.name) ||
                (
                    !filterProduct.sweet && !filterProduct.sour && !filterProduct.sweetAndSour &&
                        !filterProduct.salty && !filterProduct.spicy
                    )
            )
    }

    private fun isMainCategoryValid(
        productMainCategory: String,
        filterProductMainCategory: String
    ): Boolean {
        return filterProductMainCategory == MainCategories.CHOOSE.name || filterProductMainCategory.isEmpty() || productMainCategory == filterProductMainCategory || productMainCategory.isEmpty()
    }

    private fun isDetailCategoryValid(
        productDetailCategory: String,
        filterProductDetailCategory: String
    ): Boolean {
        return filterProductDetailCategory == ChooseCategoryTypes.CHOOSE.name || filterProductDetailCategory.isEmpty() || productDetailCategory == filterProductDetailCategory || productDetailCategory.isEmpty()
    }

    private fun isExpirationDateInRange(
        productExpirationDate: String,
        filterExpirationDateSince: String,
        filterExpirationDateFor: String
    ): Boolean {
        val isExpirationDateSinceValid =
            isDateAfter(productExpirationDate, filterExpirationDateSince)
        val isExpirationDateForValid = isDateAfter(filterExpirationDateFor, productExpirationDate)
        return isExpirationDateSinceValid && isExpirationDateForValid
    }

    private fun isProductionDateInRange(
        productProductionDate: String,
        filterProductionDateSince: String,
        filterProductionDateFor: String
    ): Boolean {
        val isProductionDateSinceValid =
            isDateAfter(productProductionDate, filterProductionDateSince)
        val isProductionDateForValid = isDateAfter(filterProductionDateFor, productProductionDate)
        return isProductionDateSinceValid && isProductionDateForValid
    }

    @SuppressLint("SimpleDateFormat")
    private fun isDateAfter(productDateString: String, filterProductDateString: String): Boolean {
        var isDateAfter = true
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        if (productDateString.isNotEmpty() && filterProductDateString.isNotEmpty()) {
            val productDate = sdf.parse(productDateString)
            val filterProductDate = sdf.parse(filterProductDateString)
            if (productDate != null) {
                isDateAfter = productDate.after(filterProductDate)
            }
        }
        return isDateAfter
    }

    private fun isProductAttributesValid(
        productIsBio: Boolean,
        productIsVege: Boolean,
        productHasSugar: Boolean,
        productHasSalt: Boolean,
        filterProductIsBio: String,
        filterProductIsVege: String,
        filterProductHasSugar: String,
        filterProductHasSalt: String
    ): Boolean {
        val isVege = enumValueOf<ProductAttributesValueType>(filterProductIsVege)
        val isBio = enumValueOf<ProductAttributesValueType>(filterProductIsBio)
        val hasSugar = enumValueOf<ProductAttributesValueType>(filterProductHasSugar)
        val hasSalt = enumValueOf<ProductAttributesValueType>(filterProductHasSalt)
        return checkAttribute(isVege, productIsVege) && checkAttribute(
            isBio,
            productIsBio
        ) && checkAttribute(hasSugar, productHasSugar) && checkAttribute(hasSalt, productHasSalt)
    }

    private fun checkAttribute(
        attributeEnum: ProductAttributesValueType,
        attribute: Boolean
    ): Boolean {
        return attributeEnum == ProductAttributesValueType.ALL || (attributeEnum == ProductAttributesValueType.YES && attribute) || (attributeEnum == ProductAttributesValueType.NO && !attribute)
    }
}

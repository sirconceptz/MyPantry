package com.hermanowicz.pantry.domain.product

import com.hermanowicz.pantry.data.model.FilterProduct
import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.utils.enums.ProductAttributesValueType
import com.hermanowicz.pantry.utils.enums.Taste
import com.hermanowicz.pantry.utils.enums.category.MainCategories
import com.hermanowicz.pantry.utils.enums.category.detailCategory.ChooseCategoryTypes
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date

class GetFilteredProductListUseCaseTest {

    private val useCase = GetFilteredProductListUseCase()

    @Test
    fun `test invoke filters products based on provided conditions`() {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val today = sdf.format(Date())

        val products = listOf(
            Product(
                name = "Product 1",
                mainCategory = MainCategories.CHOOSE.name,
                detailCategory = ChooseCategoryTypes.CHOOSE.name,
                storageLocation = "Location",
                composition = "Composition",
                healingProperties = "Healing",
                dosage = "Dosage",
                volume = 200,
                weight = 500,
                isBio = true,
                isVege = false,
                hasSugar = true,
                hasSalt = false,
                taste = Taste.SWEET.name,
                expirationDate = today,
                productionDate = today
            ),
            Product(
                name = "Product 2",
                mainCategory = MainCategories.OWN_CATEGORIES.name,
                detailCategory = "Fruits",
                storageLocation = "Location 2",
                composition = "Composition 2",
                healingProperties = "Healing Properties 2",
                dosage = "Dosage 2",
                volume = 300,
                weight = 700,
                isBio = false,
                isVege = true,
                hasSugar = false,
                hasSalt = true,
                taste = Taste.SOUR.name,
                expirationDate = today,
                productionDate = today
            )
        )

        val filter = FilterProduct(
            name = "Product",
            mainCategory = MainCategories.CHOOSE.name,
            detailCategory = ChooseCategoryTypes.CHOOSE.name,
            storageLocation = "Location",
            composition = "Composition",
            healingProperties = "Healing",
            dosage = "",
            volumeMin = 100,
            volumeMax = 400,
            weightMin = 300,
            weightMax = 800,
            isBio = ProductAttributesValueType.ALL.name,
            isVege = ProductAttributesValueType.ALL.name,
            hasSugar = ProductAttributesValueType.ALL.name,
            hasSalt = ProductAttributesValueType.ALL.name,
            sweet = true,
            sour = false,
            sweetAndSour = false,
            salty = false,
            spicy = false,
            expirationDateMin = "",
            expirationDateMax = "",
            productionDateMin = "",
            productionDateMax = ""
        )

        val filteredProducts = useCase(products, filter)

        assertEquals(1, filteredProducts.size)
        assertEquals("Product 1", filteredProducts[0].name)
    }
}

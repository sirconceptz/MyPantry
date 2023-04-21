package com.hermanowicz.pantry.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("hashCode")
    val hashCode: String = "",
    @SerializedName("typeOfProduct")
    val mainCategory: String = "",
    @SerializedName("productFeatures")
    val detailCategory: String = "",
    @SerializedName("storageLocation")
    val storageLocation: String = "",
    @SerializedName("expirationDate")
    val expirationDate: String = "",
    @SerializedName("productionDate")
    val productionDate: String = "",
    @SerializedName("composition")
    val composition: String = "",
    @SerializedName("healingProperties")
    val healingProperties: String = "",
    @SerializedName("dosage")
    val dosage: String = "",
    @SerializedName("volume")
    val volume: Int = 0,
    @SerializedName("weight")
    val weight: Int = 0,
    @SerializedName("hasSugar")
    val hasSugar: Boolean = false,
    @SerializedName("hasSalt")
    val hasSalt: Boolean = false,
    @SerializedName("isVege")
    val isVege: Boolean = false,
    @SerializedName("isBio")
    val isBio: Boolean = false,
    @SerializedName("taste")
    val taste: String = "",
    @SerializedName("photoName")
    val photoName: String = "",
    @SerializedName("photoDescription")
    val photoDescription: String = "",
    @SerializedName("barcode")
    val barcode: String = ""
)

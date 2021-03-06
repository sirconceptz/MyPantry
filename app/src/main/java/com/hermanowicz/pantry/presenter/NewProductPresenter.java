/*
 * Copyright (c) 2019-2021
 * Mateusz Hermanowicz - All rights reserved.
 * My Pantry
 * https://www.mypantry.eu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hermanowicz.pantry.presenter;

import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hermanowicz.pantry.db.product.Product;
import com.hermanowicz.pantry.interfaces.NewProductView;
import com.hermanowicz.pantry.model.NewProductModel;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewProductPresenter {

    private final NewProductView view;
    private final NewProductModel model;

    private final Calendar calendar = Calendar.getInstance();
    private final DateFormat dateFormat = DateFormat.getDateInstance();

    public NewProductPresenter(@NonNull NewProductView view, @NonNull NewProductModel model) {
        this.view = view;
        this.model = model;
    }

    public void setExpirationDate(int year, int month, int day) {
        model.setExpirationDate(year, month, day);
    }

    public void setProductionDate(int year, int month, int day) {
        model.setProductionDate(year, month, day);
    }

    public void showExpirationDate(int day, int month, int year) {
        calendar.set(year, month, day);
        Date date = calendar.getTime();
        view.showExpirationDate(dateFormat.format(date));
    }

    public void showProductionDate(int day, int month, int year) {
        calendar.set(year, month, day);
        Date date = calendar.getTime();
        view.showProductionDate(dateFormat.format(date));
    }

    public void setTaste(@Nullable RadioButton selectedTasteButton){
        model.setTaste(selectedTasteButton);
    }

    public void setQuantity(@NonNull String quantity){
        model.parseQuantityProducts(quantity);
    }

    public void addProducts(@NonNull Product product) {
        if (model.isProductNameNotValid(product))
            view.showErrorNameNotSet();
        else if (!model.isTypeOfProductValid(product))
            view.showErrorCategoryNotSelected();
        else {
            model.createProductsList(product);
            model.addProducts();
            List<Product> productList = model.getProductList();
            view.onProductsAdd(productList);
            view.showStatementOnAreProductsAdded(model.getOnProductAddStatement());
            view.navigateToPrintQRCodesActivity(productList);
        }
    }

    public void updateProductFeaturesAdapter(@NonNull String typeOfProductSpinnerValue) {
        view.updateProductFeaturesAdapter(typeOfProductSpinnerValue);
    }

    public String[] getOwnCategoryArray(){
        return model.getOwnCategoriesArray();
    }

    public String[] getStorageLocationsArray(){
        return model.getStorageLocationsArray();
    }

    public int[] getExpirationDateArray() {
        return model.getExpirationDateArray();
    }

    public int[] getProductionDateArray() {
        return model.getProductionDateArray();
    }

    public void navigateToMainActivity() {
        view.navigateToMainActivity();
    }

    public void onClickAddProduct() {
        view.onClickAddProduct();
    }

    public boolean isFormNotFilled() {
        boolean isFormNotFilled = view.isFormNotFilled();
        return isFormNotFilled;
    }

    public void showCancelProductAddDialog() {
        view.showCancelProductAddDialog();
    }
}
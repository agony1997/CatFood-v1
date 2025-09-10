package com.example.catfoodv1.view.wet;

import com.example.catfoodv1.view.component.BaseDialog;
import com.example.catfoodv1.view.component.BrandComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;

public class CreateDialog extends BaseDialog {
    TextField productNameField = new TextField("系列名");
    TextField flavorNameField = new TextField("口味名");
    TextField meatField = new TextField("主要肉底");
    TextField qtyField = new TextField("包裝數量");
    TextField unitField = new TextField("包裝單位");
    TextField storeField = new TextField("銷售平台");
    IntegerField priceField = new IntegerField("價格");
    BrandComboBox brandComboBox = new BrandComboBox();
    FormLayout formLayout = new FormLayout(productNameField, flavorNameField, meatField, qtyField, unitField, storeField, priceField, brandComboBox);

    public CreateDialog(String title) {
        super(title);
        setCancelButtonDefaultEvent();
        add(formLayout);
    }

}

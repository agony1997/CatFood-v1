package com.example.catfoodv1.view.wet;

import com.example.catfoodv1.model.SelectType;
import com.example.catfoodv1.service.CommonService;
import com.example.catfoodv1.view.component.BaseDialog;
import com.example.catfoodv1.view.component.select.CommonSelect;
import com.example.catfoodv1.view.component.select.UnitSelect;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;

public class CreateDialog extends BaseDialog {
    public CreateDialog(String title, CommonService commonService) {
        super(title);
        setCancelButtonDefaultEvent();
        setWidth(50, Unit.PERCENTAGE);

        TextField productNameField = new TextField("系列名");
        TextField flavorNameField = new TextField("口味名");
        IntegerField qtyField = new IntegerField("基本單位數量");
        IntegerField priceField = new IntegerField("價格");
        UnitSelect unitSelect = new UnitSelect();
        CommonSelect meatSelect = new CommonSelect(commonService, SelectType.INGREDIENT);
        CommonSelect storeSelect = new CommonSelect(commonService, SelectType.STORE);
        CommonSelect brandSelect = new CommonSelect(commonService, SelectType.BRAND);

        FormLayout formLayout = new FormLayout(
                productNameField, flavorNameField,
                meatSelect, qtyField,
                unitSelect, storeSelect,
                priceField, brandSelect);

        add(formLayout);
    }

}

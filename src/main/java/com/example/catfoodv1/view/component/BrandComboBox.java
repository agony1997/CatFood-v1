package com.example.catfoodv1.view.component;

import com.example.catfoodv1.model.dto.bussines.BrandDto;
import com.vaadin.flow.component.combobox.ComboBox;

public class BrandComboBox extends ComboBox<BrandDto> {

    public BrandComboBox() {
        setLabel("品牌");
        setPlaceholder("選擇品牌");
        setClearButtonVisible(true);
        setAllowCustomValue(true);
    }

    public String getCode() {
        return getValue() != null ? getValue().getBrandCode() : null;
    }

}

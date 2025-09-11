package com.example.catfoodv1.view.component.select;

import com.example.catfoodv1.model.type.PackageUnit;
import com.vaadin.flow.component.select.Select;

public class UnitSelect extends Select<PackageUnit> {
    public UnitSelect() {
        setLabel("包裝方式");
        setItems(PackageUnit.values());
        setItemLabelGenerator(p -> p.text);
    }
}

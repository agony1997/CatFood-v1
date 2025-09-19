package com.example.catfoodv1.view.component.select;

import com.example.catfoodv1.model.SelectType;
import com.example.catfoodv1.model.dto.bussines.CommonDto;
import com.example.catfoodv1.service.CommonService;
import com.vaadin.flow.component.select.Select;

public class CommonSelect extends Select<CommonDto> {

    public CommonSelect(CommonService commonService, SelectType type) {
        setLabel(type.getLabel());
        setPlaceholder("選擇" + type.getLabel());
        setItems(commonService.getAll(type));
        setItemLabelGenerator(CommonDto::getName);
        setWidthFull();
    }

    public void addItem(CommonDto dto) {
        getListDataView().addItem(dto);
        setValue(dto);
    }

}

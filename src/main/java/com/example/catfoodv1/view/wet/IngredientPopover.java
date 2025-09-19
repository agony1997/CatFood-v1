package com.example.catfoodv1.view.wet;

import com.example.catfoodv1.model.dto.bussines.CommonDto;
import com.example.catfoodv1.service.product.IngredientService;
import com.example.catfoodv1.view.component.SimpleNameCodePopover;
import com.vaadin.flow.component.popover.PopoverPosition;

public class IngredientPopover extends SimpleNameCodePopover {
    private final IngredientService ingredientService;

    public IngredientPopover(IngredientService ingredientService) {
        super("主成分名稱", "主成分代碼");
        this.ingredientService = ingredientService;
        setPosition(PopoverPosition.START_TOP);
    }

    @Override
    protected String getFormTitle() {
        return "建立新主成分";
    }

    @Override
    protected CommonDto performSave(CommonDto bean) {
        return ingredientService.save(bean);
    }
}

package com.example.catfoodv1.view.wet;

import com.example.catfoodv1.model.dto.bussines.CommonDto;
import com.example.catfoodv1.service.business.StoreService;
import com.example.catfoodv1.view.component.SimpleNameCodePopover;

public class StorePopover extends SimpleNameCodePopover {
    private final StoreService storeService;

    public StorePopover(StoreService storeService) {
        super("販售處名稱", "販售處代碼");
        this.storeService = storeService;
    }

    @Override
    protected String getFormTitle() {
        return "建立新販售處";
    }

    @Override
    protected CommonDto performSave(CommonDto bean) {
        return storeService.save(bean);
    }
}

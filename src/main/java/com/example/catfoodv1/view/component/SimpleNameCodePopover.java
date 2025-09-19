package com.example.catfoodv1.view.component;

import com.example.catfoodv1.model.dto.bussines.CommonDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.function.Function;

/**
 * 一個通用的 Popover，用於創建具有「名稱」和「代碼」欄位的實體。
 * 它封裝了 UI 創建、資料綁定和佈局的通用邏輯。
 */
public abstract class SimpleNameCodePopover extends FormPopover<CommonDto> {

    protected final TextField nameField = new TextField();
    protected final TextField codeField = new TextField();

    /**
     * @param nameLabel Name field's label.
     * @param codeLabel Code field's label.
     */
    public SimpleNameCodePopover(String nameLabel, String codeLabel) {
        super(CommonDto.class);

        // Configure fields
        nameField.setLabel(nameLabel);
        codeField.setLabel(codeLabel);
        nameField.setWidthFull();
        codeField.setWidthFull();
        nameField.getStyle().setPaddingTop("0");

        // Bind fields
        binder.forField(nameField).asRequired("此為必填欄位").bind(CommonDto::getName, CommonDto::setName);
        binder.forField(codeField).asRequired("此為必填欄位").bind(CommonDto::getCode, CommonDto::setCode);

        // Create layout
        Button confirmButton = getConfirmButton();
        confirmButton.getStyle().setWidth("100%").setMarginTop("20px");

        VerticalLayout formLayout = new VerticalLayout(nameField, codeField, confirmButton);
        formLayout.setPadding(true);
        formLayout.setSpacing(false);
        formLayout.getStyle().setPaddingTop("2px").setPaddingBottom("0");

        setFormContent(formLayout);
    }
}
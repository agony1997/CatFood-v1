package com.example.catfoodv1.view.wet;

import com.example.catfoodv1.model.SelectType;
import com.example.catfoodv1.model.dto.bussines.CommonDto;
import com.example.catfoodv1.model.type.PackageUnit;
import com.example.catfoodv1.service.CommonService;
import com.example.catfoodv1.service.business.BrandService;
import com.example.catfoodv1.service.business.CompanyService;
import com.example.catfoodv1.service.business.StoreService;
import com.example.catfoodv1.service.product.IngredientService;
import com.example.catfoodv1.view.component.BaseDialog;
import com.example.catfoodv1.view.component.select.CommonSelect;
import com.example.catfoodv1.view.component.select.QtySelect;
import com.example.catfoodv1.view.component.select.UnitSelect;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
public class CreateDialog extends BaseDialog {
    // Fields
    private final TextField productNameField = new TextField("系列名");
    private final TextField flavorNameField = new TextField("口味名");
    private final IntegerField priceField = new IntegerField("價格");
    private final IntegerField gramField = new IntegerField("單罐克數");
    private final QtySelect qtySelect = new QtySelect();
    private final UnitSelect unitSelect = new UnitSelect();
    private final CommonSelect meatSelect;
    private final CommonSelect storeSelect;
    private final CommonSelect brandSelect;
    // Buttons
    private final Button addBrandBtn = new Button(VaadinIcon.PLUS.create());
    private final Button addStoreBtn = new Button(VaadinIcon.PLUS.create());
    private final Button addMeatBtn = new Button(VaadinIcon.PLUS.create());

    public CreateDialog(String title, CommonService commonService, CompanyService companyService, BrandService brandService, StoreService storeService, IngredientService ingredientService) {
        super(title);
        this.meatSelect = new CommonSelect(commonService, SelectType.INGREDIENT);
        this.storeSelect = new CommonSelect(commonService, SelectType.STORE);
        this.brandSelect = new CommonSelect(commonService, SelectType.BRAND);

        BrandPopover brandPopover = new BrandPopover(commonService, companyService, brandService);
        StorePopover storePopover = new StorePopover(storeService);
        IngredientPopover ingredientPopover = new IngredientPopover(ingredientService);
        brandPopover.setTarget(addBrandBtn);
        storePopover.setTarget(addStoreBtn);
        ingredientPopover.setTarget(addMeatBtn);

        priceField.setMin(0);
        gramField.setMin(0);

        addBrandBtn.addClickListener(e -> {
            brandPopover.refresh(new CommonDto());
            brandPopover.open();
        });

        addStoreBtn.addClickListener(e -> {
            storePopover.refresh(new CommonDto());
            storePopover.open();
        });

        addMeatBtn.addClickListener(e -> {
            ingredientPopover.refresh(new CommonDto());
            ingredientPopover.open();
        });

        brandPopover.addSaveListener(brandSelect::addItem);
        storePopover.addSaveListener(storeSelect::addItem);
        ingredientPopover.addSaveListener(meatSelect::addItem);

        unitSelect.addValueChangeListener(event -> {
            if (Objects.equals(event.getValue(), PackageUnit.CAN)) {
                qtySelect.setValue(1);
                qtySelect.setReadOnly(true);
            } else {
                qtySelect.setReadOnly(false);
                qtySelect.clear();
            }
        });

        this.configureLayout();
    }

    private void configureLayout() {
        Component brandRow = flexRow(brandSelect, addBrandBtn);
        Component storeRow = flexRow(storeSelect, addStoreBtn);
        Component meatRow = flexRow(meatSelect, addMeatBtn);
        FlexLayout nameLayout = flexRow(productNameField, flavorNameField);
        FlexLayout priceLayout = flexRow(priceField, gramField);
        FlexLayout qtyLayout = flexRow(qtySelect, unitSelect);

        FormLayout formLayout = new FormLayout(
                brandRow,
                storeRow,
                meatRow,
                nameLayout,
                priceLayout,
                qtyLayout
        );

        formLayout.getStyle().setPaddingTop("6px");
        formLayout.setWidthFull();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("800px", 2));

        brandSelect.getStyle().setPaddingTop("0");
        setWidth(50, Unit.VW);
        add(formLayout);
    }

    private FlexLayout flexRow(Component... components) {
        FlexLayout row = new FlexLayout();
        row.add(components);
        row.setWidthFull();
        row.getStyle()
                .set("display", "flex")
                .set("align-items", "flex-end")
                .set("gap", "8px")
                .set("flex-wrap", "wrap");
        Arrays.stream(components).forEach(c -> c.getElement().getStyle().set("flex", "1 1 auto"));
        return row;
    }

    private Component flexRow(Component main, Button btn) {
        FlexLayout row = new FlexLayout(main, btn);
        row.setWidthFull();
        row.getStyle()
                .set("display", "flex")
                .set("align-items", "flex-end")
                .set("gap", "8px");
        // 主體撐滿，其它元件自然尺寸
        main.getElement().getStyle().set("flex", "1 1 auto");
        btn.getElement().getStyle().remove("width");
        return row;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        UI.getCurrent().getPage().retrieveExtendedClientDetails(d -> {
            int w = d.getBodyClientWidth();
            if (w < 600) {
                setWidth("95vw");
            } else if (w < 768) {
                setWidth("80vw");
            } else if (w < 1200) {
                setWidth("60vw");
            } else {
                setWidth("40vw");
            }
        });
    }

}

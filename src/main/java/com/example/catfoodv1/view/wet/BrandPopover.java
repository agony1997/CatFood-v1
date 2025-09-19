package com.example.catfoodv1.view.wet;

import com.example.catfoodv1.model.SelectType;
import com.example.catfoodv1.model.dto.bussines.CommonDto;
import com.example.catfoodv1.service.CommonService;
import com.example.catfoodv1.service.business.BrandService;
import com.example.catfoodv1.service.business.CompanyService;
import com.example.catfoodv1.view.component.FormPopover;
import com.example.catfoodv1.view.component.select.CommonSelect;
import com.example.catfoodv1.view.util.NotificationUtil;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class BrandPopover extends FormPopover<CommonDto> {
    // Binders
    private final Binder<CommonDto> companyBinder = new Binder<>(CommonDto.class);
    // Services
    private final CompanyService companyService;
    private final BrandService brandService;
    // UI Components
    private final TextField brandName = new TextField("品牌名稱");
    private final TextField brandCode = new TextField("品牌代碼");
    private final TextField companyName = new TextField("公司名稱");
    private final TextField companyCode = new TextField("公司代碼");
    private final CommonSelect companySelect;
    private final Button addCompanyBtn = new Button(); // Icon is set dynamically
    // State variable to track the current view
    private boolean isCompanyViewActive = false;
    // Layout for swapping views
    private final VerticalLayout contentLayout = new VerticalLayout();

    public BrandPopover(CommonService commonService, CompanyService companyService, BrandService brandService) {
        super(CommonDto.class);
        this.companyService = companyService;
        this.brandService = brandService;
        this.companySelect = new CommonSelect(commonService, SelectType.COMPANY);

        // Configure main content layout
        contentLayout.setPadding(false);
        contentLayout.setSpacing(false);
        setFormContent(contentLayout);

        // Configure and add the header button
        addCompanyBtn.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY_INLINE,ButtonVariant.LUMO_ERROR,ButtonVariant.LUMO_SMALL);
        // Set ONE listener that handles both states. This is the key change.
        addCompanyBtn.addClickListener(e -> {
            if (isCompanyViewActive) {
                // If we are in the company view, switch back to brand view.
                showBrandForm(null);
            } else {
                // If we are in the brand view, switch to company view.
                showCompanyForm();
            }
        });
        getHeaderActions().add(addCompanyBtn);

        // Bind all fields once
        bindBrandFields();
        bindCompanyFields();

        // Set the initial view in the constructor. This is the fix.
        showBrandForm(null);
    }

    private void bindBrandFields() {
        binder.forField(brandName).asRequired("必填").bind(CommonDto::getName, CommonDto::setName);
        binder.forField(brandCode).asRequired("必填").bind(CommonDto::getCode, CommonDto::setCode);
        binder.forField(companySelect).asRequired("必填").bind(
                dto -> null,
                (dto, item) -> dto.setParentId(item != null ? item.getId() : null));
    }

    private void bindCompanyFields() {
        companyBinder.forField(companyName).asRequired("必填").bind(CommonDto::getName, CommonDto::setName);
        companyBinder.forField(companyCode).asRequired("必填").bind(CommonDto::getCode, CommonDto::setCode);
    }

    @Override
    public void refresh(CommonDto bean) {
        super.refresh(bean);
        // The view is already set. If we are in the company view, switch back.
        if (isCompanyViewActive) {
            showBrandForm(null);
        }
    }

    private void showBrandForm(CommonDto newCompany) {
        contentLayout.removeAll();
        contentLayout.add(createBrandFormComponent());

        if (newCompany != null) {
            companySelect.addItem(newCompany);
        }

        // Update UI state for Brand View
        getConfirmButton().setVisible(true);
        addCompanyBtn.setIcon(VaadinIcon.ARROW_CIRCLE_RIGHT.create());
        addCompanyBtn.setTooltipText("新增公司");
        setTitle(getFormTitle());
        isCompanyViewActive = false;
    }

    private void showCompanyForm() {
        contentLayout.removeAll();
        contentLayout.add(createCompanyFormComponent(this::showBrandForm));

        // Update UI state for Company View
        getConfirmButton().setVisible(false);
        addCompanyBtn.setIcon(VaadinIcon.ARROW_CIRCLE_LEFT.create());
        addCompanyBtn.setTooltipText("返回");
        setTitle("建立新公司");
        isCompanyViewActive = true;
    }

    private Component createBrandFormComponent() {
        brandName.setWidthFull();
        brandCode.setWidthFull();
        companySelect.setWidthFull();
        brandName.getStyle().setPaddingTop("0");

        Button confirmButton = getConfirmButton();
        confirmButton.setWidthFull();
        confirmButton.getStyle().setMarginTop("20px");

        VerticalLayout layout = new VerticalLayout(brandName, brandCode, companySelect, confirmButton);
        layout.setPadding(true);
        layout.setSpacing(false);
        layout.getStyle().setPaddingTop("2px").setPaddingBottom("0");
        return layout;
    }

    private Component createCompanyFormComponent(Consumer<CommonDto> onSave) {
        companyName.setWidthFull();
        companyCode.setWidthFull();
        companyBinder.setBean(new CommonDto());
        companyName.getStyle().setPaddingTop("0");

        Button saveCompanyBtn = new Button("新增公司", e -> {
            if (companyBinder.validate().isOk()) {
                CommonDto savedCompany = companyService.save(companyBinder.getBean());
                NotificationUtil.showSuccess("公司已新增");
                onSave.accept(savedCompany); // Execute callback to switch back
            }
        });

        saveCompanyBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveCompanyBtn.setWidthFull();
        saveCompanyBtn.getStyle().setMarginTop("20px");

        VerticalLayout layout = new VerticalLayout(companyName, companyCode, saveCompanyBtn);
        layout.setPadding(true);
        layout.setSpacing(false);
        layout.getStyle().set("padding-top", "2px").set("padding-bottom", "0");
        return layout;
    }

    @Override
    protected String getFormTitle() {
        return "建立新品牌";
    }

    @Override
    protected CommonDto performSave(CommonDto bean) {
        return brandService.save(bean);
    }
}

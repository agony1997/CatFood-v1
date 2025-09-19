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
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BrandPopover extends FormPopover<CommonDto> {
    private final Binder<CommonDto> companyBinder = new Binder<>(CommonDto.class);

    // Services
    private final CompanyService companyService;
    private final BrandService brandService;
    
    // UI Components
    private final Button addCompany = new Button(VaadinIcon.ARROW_CIRCLE_RIGHT.create());
    private final TextField companyName = new TextField("公司名稱");
    private final TextField companyCode = new TextField("公司代碼");
    private final TextField brandName = new TextField("品牌名稱");
    private final TextField brandCode = new TextField("品牌代碼");
    private final CommonSelect companySelect;
    private final Button confirmCompany = new Button("新增公司");
    private final VerticalLayout left = new VerticalLayout();
    private final VerticalLayout right = new VerticalLayout();

    public BrandPopover(CommonService commonService, CompanyService companyService, BrandService brandService) {
        // 1. 呼叫父類別建構子，建立 Popover 框架
        super(CommonDto.class);

        // 2. 初始化 Services 和子類別獨有的 UI 元件
        this.companyService = companyService;
        this.brandService = brandService;
        this.companySelect = new CommonSelect(commonService, SelectType.COMPANY);

        // 3. 配置 UI 元件
        configureComponents();

        // 4. 綁定資料和監聽器
        bindFields();
        addListeners();

        // 5. 創建表單內容並注入到父類別框架中
        Component formContent = createCustomFormComponent();
        setFormContent(formContent);

        // 6. 將特定於子類別的動作按鈕附加到父類別的標頭
        getHeaderActions().add(addCompany);
    }

    private void bindFields() {
        companyBinder.forField(companyName).asRequired("必填").bind(CommonDto::getName, CommonDto::setName);
        companyBinder.forField(companyCode).asRequired("必填").bind(CommonDto::getCode, CommonDto::setCode);
        binder.forField(brandName).asRequired("必填").bind(CommonDto::getName, CommonDto::setName);
        binder.forField(brandCode).asRequired("必填").bind(CommonDto::getCode, CommonDto::setCode);
        binder.forField(companySelect).asRequired("必填").bind(
                dto -> null,
                (dto, select) -> {
                    if (select != null) {
                        dto.setParentId(select.getId());
                    }
                });
    }

    private void addListeners() {
        addCompany.addClickListener(event -> {
            addCompany.setIcon(right.isVisible() ? VaadinIcon.ARROW_CIRCLE_RIGHT.create() : VaadinIcon.ARROW_CIRCLE_LEFT.create());
            right.setVisible(!right.isVisible());
        });

        confirmCompany.addClickListener(event -> {
            try {
                if (!companyBinder.validate().isOk()) {
                    NotificationUtil.showWarning("請檢查公司表單必填項！");
                    return;
                }
                CommonDto savedCompany = companyService.save(companyBinder.getBean());
                companySelect.addItem(savedCompany);
                right.setVisible(false);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                NotificationUtil.showFailure("新增公司失敗");
            }
        });
    }

    @Override
    public void refresh(CommonDto bean) {
        super.refresh(bean);
        right.setVisible(false);
        companyBinder.setBean(new CommonDto());
    }

    private Component createCustomFormComponent() {
        // 此方法現在只負責「組裝」，而不是「創建」或「配置」
        left.setSpacing(false);
        right.setSpacing(false);
        left.getStyle().setPaddingTop("2px").setPaddingBottom("0");
        right.getStyle().setPaddingTop("2px").setPaddingBottom("0");
        right.setVisible(false);

        HorizontalLayout body = new HorizontalLayout();
        body.getStyle().setMarginBottom("0");
        body.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.START);

        // Add components to layouts
        left.add(brandName, brandCode, companySelect, getConfirmButton());
        right.add(companyName, companyCode, confirmCompany);
        body.add(left, right);

        return body;
    }

    private void configureComponents() {
        addCompany.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY_INLINE, ButtonVariant.LUMO_WARNING);
        brandName.getStyle().setPaddingTop("0px");
        companyName.getStyle().setPaddingTop("0px");
        getConfirmButton().getStyle().setWidth("100%").setMarginTop("20px");

        confirmCompany.getStyle().setWidth("100%").setMarginTop("36px");
        confirmCompany.addThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
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

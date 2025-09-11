package com.example.catfoodv1.view.wet;

import com.example.catfoodv1.model.dto.product.WetFoodViewDto;
import com.example.catfoodv1.service.CommonService;
import com.example.catfoodv1.service.product.ProductService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;

@PageTitle("咪貓罐頭")
@AnonymousAllowed
@Route(value = "")
@RouteAlias(value = "wet-food")
public class WetFoodView extends VerticalLayout {
    private final ProductService productService;
    private HorizontalLayout header = new HorizontalLayout();
    private HorizontalLayout body = new HorizontalLayout();
    private Button createBtn = new Button(VaadinIcon.PLUS.create());
    private CreateDialog createDialog;
    private final Grid<WetFoodViewDto> grid = new Grid<>(WetFoodViewDto.class, false);
    private final CommonService commonService;

    public WetFoodView(ProductService productService, CommonService commonService) {
        this.productService = productService;
        this.commonService = commonService;
        initGrid();
        setListener();
        configureLayout();
        search();
    }

    private void search() {
        grid.setItems(productService.getAllToDto());
    }

    private void initGrid() {
        grid.setAllRowsVisible(true);
        grid.setSelectionMode(Grid.SelectionMode.NONE);

        grid.setDetailsVisibleOnClick(false);

        grid.addColumn(WetFoodViewDto::getBrandName).setHeader("品牌")
                .setRenderer(LitRenderer.<WetFoodViewDto>of(
                                """
                                        <div style="display: flex; align-items: center; gap: var(--lumo-space-s);">
                                            <vaadin-button theme="tertiary-inline icon" @click="${handleClick}" aria-label="Toggle details">
                                                <vaadin-icon icon="${item.detailsOpened ? 'lumo:angle-down' : 'lumo:angle-right'}"></vaadin-icon>
                                            </vaadin-button>
                                            <span>${item.displayName}</span>
                                        </div>
                                        """)
                        .withProperty("displayName", WetFoodViewDto::getDisplayName)
                        .withProperty("detailsOpened", grid::isDetailsVisible)
                        .withFunction("handleClick", dto -> grid.setDetailsVisible(dto, !grid.isDetailsVisible(dto))));

        grid.addColumn(WetFoodViewDto::getDisplayName).setHeader("品名");
        grid.addColumn(WetFoodViewDto::getStoreName).setHeader("販售處");
        grid.addColumn(WetFoodViewDto::getPrice).setHeader("價格");
        grid.addColumn(WetFoodViewDto::getPricePer).setHeader("百克價格");
        grid.addColumn(WetFoodViewDto::getUpdateDT).setHeader("最後更新");

        grid.setItemDetailsRenderer(new ComponentRenderer<>(dto -> {
            VerticalLayout detailsLayout = new VerticalLayout();
            detailsLayout.setSpacing(false);
            detailsLayout.setPadding(false);
            detailsLayout.addClassName(LumoUtility.Padding.Left.LARGE);

            // 檢查是否有明細資料
            if (dto.getDetails() != null && !dto.getDetails().isEmpty()) {
                dto.getDetails().forEach(detail -> {
                    // 為每一筆明細創建一個顯示標籤
                    String detailText = String.format("販售處: %s, 價格: %s, 更新於: %s",
                            detail.getStoreName(),
                            detail.getPrice(),
                            detail.getUpdateDT().toLocalDate());
                    detailsLayout.add(detailText);
                });
            } else {
                detailsLayout.add("無其他價格資訊");
            }
            return detailsLayout;
        }));

        grid.getColumns().forEach(c -> c.setResizable(true).setAutoWidth(true));
    }

    private void setListener() {
        createBtn.addClickListener(event -> {
            createDialog = new CreateDialog("新增產品", commonService);
            createDialog.setCancelButtonDefaultEvent();
            createDialog.setConfirmButtonEvent(() -> {
                // add
                createDialog.close();
            });
            createDialog.open();
        });
    }

    private void configureLayout() {
        add(header, body);
        header.add(createBtn);
        body.add(grid);
        grid.setWidthFull();

        setSizeFull();
        header.setWidthFull();
        body.setWidthFull();

        createBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    }

}

package com.example.catfoodv1.view.wet;

import com.example.catfoodv1.model.dto.product.WetFoodViewDto;
import com.example.catfoodv1.service.product.ProductService;
import com.example.catfoodv1.view.component.BaseDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@Route("/wet-food")
public class WetFoodView extends VerticalLayout {
    private final ProductService productService;
    private HorizontalLayout header = new HorizontalLayout();
    private HorizontalLayout body = new HorizontalLayout();
    private Button createBtn = new Button(VaadinIcon.PLUS.create());
    private CreateDialog createDialog;
    private final Grid<WetFoodViewDto> grid = new Grid<>(WetFoodViewDto.class, false);

    public WetFoodView(ProductService productService) {
        this.productService = productService;
        initGrid();

        setListener();
        configureLayout();
        search();
    }

    private void search() {
        grid.setItems(productService.getFakeData());
    }

    private void initGrid() {
        grid.setAllRowsVisible(true);
        grid.setSelectionMode(Grid.SelectionMode.NONE);

        grid.addColumn(WetFoodViewDto::getBrandName).setHeader("品牌");
        grid.addColumn(WetFoodViewDto::getProductName).setHeader("系列名");
        grid.addColumn(WetFoodViewDto::getVariantDisplayName).setHeader("口味名");
        grid.addColumn(WetFoodViewDto::getStoreName).setHeader("販售處");
        grid.addColumn(WetFoodViewDto::getPrice).setHeader("價格");
        grid.addColumn(WetFoodViewDto::getPricePer100g).setHeader("每100g單價").setSortable(true);
        grid.addColumn(dto -> dto.getUnitWeightGrams() != null ? dto.getUnitWeightGrams() + " g" : "").setHeader("單位重量");
        grid.addColumn(WetFoodViewDto::getPackSize).setHeader("包裝數量");
        grid.addColumn(dto -> dto.getPriceUpdateDT().toLocalDate()).setHeader("價格更新日");

        grid.getColumns().forEach(c -> c.setResizable(true).setAutoWidth(true));
    }

    private void setListener() {
        createBtn.addClickListener(event -> {
            createDialog = new CreateDialog("新增產品");
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

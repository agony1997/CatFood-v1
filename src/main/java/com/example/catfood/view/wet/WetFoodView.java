package com.example.catfood.view.wet;

import com.example.catfood.application.ProductApplicationService;
import com.example.catfood.application.dto.WetFoodViewDto;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.List;

@PageTitle("咪貓罐頭")
@AnonymousAllowed
@Route(value = "")
@RouteAlias(value = "wet-food")
public class WetFoodView extends VerticalLayout {

    private final ProductApplicationService productApplicationService;
    private final TreeGrid<WetFoodViewDto> grid = new TreeGrid<>(WetFoodViewDto.class, false);

    public WetFoodView(ProductApplicationService productApplicationService) {
        this.productApplicationService = productApplicationService;
        initGrid();
        search();
        setSizeFull();
    }

    private void search() {
        List<WetFoodViewDto> list = productApplicationService.getWetFoodList();
        grid.setItems(list, WetFoodViewDto::getDetails);
    }

    private void initGrid() {
        grid.setAllRowsVisible(true);
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.addHierarchyColumn(WetFoodViewDto::getBrandName).setHeader("品牌");
        grid.addColumn(WetFoodViewDto::getDisplayName).setHeader("品名");
        grid.addColumn(WetFoodViewDto::getStoreName).setHeader("販售處");
        grid.addColumn(WetFoodViewDto::getUnit).setHeader("單位")
                .setRenderer(new TextRenderer<>(dto -> dto.getUnit() != null ? dto.getUnit().text : ""));
        grid.addColumn(WetFoodViewDto::getPrice).setHeader("價格");
        grid.addColumn(WetFoodViewDto::getPricePer).setHeader("百克價格");
        grid.addColumn(WetFoodViewDto::getUpdateDT).setHeader("最後更新")
                .setRenderer(new LocalDateTimeRenderer<>(WetFoodViewDto::getUpdateDT));
        grid.getColumns().forEach(c -> c.setResizable(true).setAutoWidth(true));
        grid.setWidthFull();
        add(grid);
    }
}

package com.example.catfoodv1.view.wet;

import com.example.catfoodv1.model.dto.product.WetFoodViewDto;
import com.example.catfoodv1.service.CommonService;
import com.example.catfoodv1.service.business.BrandService;
import com.example.catfoodv1.service.business.CompanyService;
import com.example.catfoodv1.service.business.StoreService;
import com.example.catfoodv1.service.product.IngredientService;
import com.example.catfoodv1.service.product.ProductService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.Comparator;
import java.util.List;

// TODO 建立罐頭 -> 新公司 -> 新品牌 -> 新TAG -> 新主成分 ->新販售處
// TODO 可以輸入多個價格
// TODO 成分、價格趨勢

@PageTitle("咪貓罐頭")
@AnonymousAllowed
@Route(value = "")
@RouteAlias(value = "wet-food")
public class WetFoodView extends VerticalLayout {
    private final ProductService productService;
    private final CompanyService companyService;
    private final BrandService brandService;
    private final StoreService storeService;
    private final IngredientService ingredientService;
    private final HorizontalLayout header = new HorizontalLayout();
    private final HorizontalLayout body = new HorizontalLayout();
    private final Button createBtn = new Button(VaadinIcon.PLUS.create());
    private CreateDialog createDialog;
    private final TreeGrid<WetFoodViewDto> grid = new TreeGrid<>(WetFoodViewDto.class, false);
    private List<WetFoodViewDto> productList; // 用於存放 Grid 資料的 List
    private final CommonService commonService;

    public WetFoodView(ProductService productService, CompanyService companyService, BrandService brandService, StoreService storeService, IngredientService ingredientService, CommonService commonService) {
        this.productService = productService;
        this.companyService = companyService;
        this.brandService = brandService;
        this.storeService = storeService;
        this.ingredientService = ingredientService;
        this.commonService = commonService;
        initGrid();
        setListener();
        configureLayout();
        search();
    }

    private void search() {
        this.productList = productService.getAllToDto();
        grid.setItems(this.productList, WetFoodViewDto::getDetails);
    }

    private void initGrid() {
        grid.setAllRowsVisible(true);
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.addHierarchyColumn(WetFoodViewDto::getBrandName).setHeader("品牌");
        grid.addColumn(WetFoodViewDto::getDisplayName).setHeader("品名");
        grid.addColumn(WetFoodViewDto::getStoreName).setHeader("販售處");
        grid.addColumn(WetFoodViewDto::getUnit).setHeader("單位").setRenderer(new TextRenderer<>(dto -> dto.getUnit().text));
        grid.addColumn(WetFoodViewDto::getPrice).setHeader("價格");
        grid.addColumn(WetFoodViewDto::getPricePer).setHeader("百克價格");
        grid.addColumn(WetFoodViewDto::getUpdateDT).setHeader("最後更新").setRenderer(new LocalDateTimeRenderer<>(WetFoodViewDto::getUpdateDT));
        grid.getColumns().forEach(c -> c.setResizable(true).setAutoWidth(true));
    }

    private void setListener() {
        createBtn.addClickListener(event -> {
            createDialog = new CreateDialog("新增罐頭", productService, commonService, companyService, brandService, storeService, ingredientService);
            createDialog.addSaveListener(e -> search()); // 新增成功後，直接重新查詢以確保資料結構正確
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

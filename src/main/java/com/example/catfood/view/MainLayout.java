package com.example.catfood.view;

import com.example.catfood.view.kibble.KibbleView;
import com.example.catfood.view.sand.SandView;
import com.example.catfood.view.wet.WetFoodView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.LinkedHashMap;
import java.util.Map;

@PageTitle("網站")
@org.springframework.stereotype.Component
@UIScope
@AnonymousAllowed
@Layout
public class MainLayout extends AppLayout implements BeforeEnterObserver, AfterNavigationObserver {
    private final HorizontalLayout loginArea;
    private final Map<Class<? extends Component>, RouterLink> navLinks = new LinkedHashMap<>();

    public MainLayout() {
        H1 logo = new H1("CatFood");
        logo.addClassNames("text-l", "m-m");

        HorizontalLayout routerArea = createAndRegisterRouterArea();
        this.loginArea = new HorizontalLayout();
        loginArea.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        HorizontalLayout header = new HorizontalLayout(logo, routerArea, loginArea);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.addClassNames("py-0", "px-m");
        addToNavbar(header);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        updateLoginArea();
        Class<?> targetView = event.getNavigationTarget();
        navLinks.forEach((viewClass, link) ->
                link.setEnabled(!viewClass.equals(targetView))
        );
    }

    private void updateLoginArea() {
        loginArea.removeAll();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal());

        if (isLoggedIn) {
            Button userName = new Button(auth.getName());
            Button logoutButton = new Button("登出", e -> getUI().ifPresent(ui -> {
                SecurityContextHolder.clearContext();
                ui.getPage().setLocation("/logout");
            }));
            loginArea.add(userName, logoutButton);
        } else {
            Button loginButton = new Button("登入", e -> getUI().ifPresent(ui -> ui.navigate("login")));
            loginArea.add(loginButton);
        }
    }

    private HorizontalLayout createAndRegisterRouterArea() {
        HorizontalLayout layout = new HorizontalLayout();
        navLinks.put(WetFoodView.class, createRouterLink("罐頭", WetFoodView.class));
        navLinks.put(KibbleView.class, createRouterLink("飼料", KibbleView.class));
        navLinks.put(SandView.class, createRouterLink("貓砂", SandView.class));

        layout.add(navLinks.values().toArray(new RouterLink[0]));
        return layout;
    }

    private RouterLink createRouterLink(String name, Class<? extends Component> viewClass) {
        RouterLink routerLink = new RouterLink();
        routerLink.add(name);
        routerLink.setRoute(viewClass);
        routerLink.addClassNames(
                LumoUtility.Display.FLEX,
                LumoUtility.FontSize.MEDIUM,
                LumoUtility.FontWeight.BOLD,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.AlignContent.CENTER,
                LumoUtility.Width.XLARGE
        );
        routerLink.addClassName("nav-link");
        return routerLink;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        getUI().ifPresent(ui -> ui.getPage().setTitle("咪貓"));
    }
}

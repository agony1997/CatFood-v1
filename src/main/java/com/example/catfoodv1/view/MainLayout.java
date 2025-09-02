package com.example.catfoodv1.view;

import com.example.catfoodv1.service.auth.SecurityService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
@AnonymousAllowed
public class MainLayout extends AppLayout implements BeforeEnterObserver {

    private final SecurityService securityService;
    private final HorizontalLayout header;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        this.header = new HorizontalLayout();
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");
        addToNavbar(header);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // 在每次導覽前重新建立上方欄內容
        header.removeAll();

        H1 logo = new H1("CatFood-v1");
        logo.addClassNames("text-l", "m-m");

        var userOptional = securityService.getAuthenticatedUserEntity();

        HorizontalLayout rightSideLayout = new HorizontalLayout();
        rightSideLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        if (userOptional.isPresent()) {
            var user = userOptional.get();
            Button userName = new Button(user.getDisplayName());
            Button logoutButton = new Button("登出", e -> securityService.logout());
            rightSideLayout.add(userName, logoutButton);
        } else {
            Button loginButton = new Button("登入", e -> getUI().ifPresent(ui -> ui.navigate("login")));
            rightSideLayout.add(loginButton);
        }

        header.add(logo, rightSideLayout);
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
    }
}

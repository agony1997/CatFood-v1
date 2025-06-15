package com.example.catfoodv1.view;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@Route("")
public class MainView extends VerticalLayout {
    public MainView() {
        add(new H2("Hello Vaadin!"));
    }
}

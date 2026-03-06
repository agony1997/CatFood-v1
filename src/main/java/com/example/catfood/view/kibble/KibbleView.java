package com.example.catfood.view.kibble;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("飼料")
@AnonymousAllowed
@Route(value = "kibble")
public class KibbleView extends VerticalLayout {
}

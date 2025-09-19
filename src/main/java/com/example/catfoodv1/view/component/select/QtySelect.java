package com.example.catfoodv1.view.component.select;

import com.vaadin.flow.component.select.Select;

import java.util.List;

public class QtySelect extends Select<Integer> {
    public QtySelect() {
        setLabel("基本單位數量");
        setItems(List.of(1, 6, 8, 12, 24));
    }
}
